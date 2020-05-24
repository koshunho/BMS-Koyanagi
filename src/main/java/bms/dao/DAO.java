package bms.dao;

import com.alibaba.fastjson.JSON;
import bms.model.WithID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.charset.Charset;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@PropertySource("classpath:config.properties")
public abstract class DAO<T extends WithID> {
    @Value("${data_source_sequence}")
    private String seqFilePath;

    protected abstract String getFilePath();

    private synchronized Long newId(String name) {
        Map<String, Long> map = new HashMap<>();
        Long id = 0L;
        List<String> lines = readFromFile(seqFilePath);
        try {
            for (String line : lines) {
                if (line.isEmpty()) continue;
                String[] kv = line.split(" ");
                map.put(kv[0], Long.valueOf(kv[1]));
            }

            id = map.get(name);
            if (id == null) {
                id = 0L;
            }

            id += 1;
            map.put(name, id);

        } catch (Exception e) {
            e.printStackTrace();
        }

        List<String> list = new ArrayList<>();
        map.forEach((k, v) -> list.add(k + " " + v));
        writeToFile(list, seqFilePath, false);
        return id;
    }

    public Long insert(T record) {
        List<String> list = new ArrayList<String>();
        record.setId(newId(record.getClass().getName()));
        list.add(JSON.toJSONString(record));
        writeToFile(list, getFilePath(), true);
        return record.getId();
    }

    public boolean deleteById(Long id, Class<T> clazz) {
        List<String> lines = readFromFile(getFilePath());

        List<T> list = lines.stream().filter(l -> !l.isEmpty()).map(s -> JSON.parseObject(s, clazz)).collect(Collectors.toList());
        List<T> newList = list.stream().filter(t -> !t.getId().equals(id)).collect(Collectors.toList());
        if (newList.size() >= list.size()) {
            return false;
        }
        writeToFile(newList.stream().map(t -> JSON.toJSONString(t)).collect(Collectors.toList()), getFilePath(), false);
        return true;
    }

    public boolean updateById(T record, Class<T> clazz) {
        List<String> lines = readFromFile(getFilePath());
        List<T> list = lines.stream().filter(l -> !l.isEmpty()).map(s -> JSON.parseObject(s, clazz)).filter(t -> !t.getId().equals(record.getId())).collect(Collectors.toList());
        list.add(record);

        writeToFile(list.stream().map(t -> JSON.toJSONString(t)).collect(Collectors.toList()), getFilePath(), false);
        return true;
    }

    public List<T> selectByExample(Predicate<? super T> predicate, Class<T> clazz) {
        List<String> lines = readFromFile(getFilePath());
        List<T> list = lines.stream().filter(l -> !l.isEmpty()).map(s -> JSON.parseObject(s, clazz)).filter(predicate).collect(Collectors.toList());
        return list;
    }

    private List<String> readFromFile(String filePath) {
        List<String> lines = new ArrayList<>();
        try {
            FileInputStream stream = new FileInputStream(filePath);
            FileChannel channel = stream.getChannel();
            FileLock lock = channel.lock(0, Long.MAX_VALUE, true);
            ByteBuffer buffer = ByteBuffer.allocate(10240);
            if (channel.read(buffer) != -1)
            {
                buffer.flip();
                lines = Arrays.asList(Charset.forName("UTF-8").newDecoder().decode(buffer).toString().split("\r\n"));
            }
            lock.release();
            channel.close();
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lines;
    }

    private void writeToFile(List<String> list, String filePath, boolean append) {
        try {
            FileOutputStream stream = new FileOutputStream(filePath, append);
            FileChannel channel = stream.getChannel();
            FileLock lock = channel.lock(0, Long.MAX_VALUE, false);
            ByteBuffer buffer = ByteBuffer.allocate(10240);
            for (String s : list) {
                buffer.put((s + "\r\n").getBytes());
            }
            buffer.flip();
            channel.write(buffer);
            lock.release();
            channel.close();
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
