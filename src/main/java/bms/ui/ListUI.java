package bms.ui;

import bms.Main;
import bms.model.Book;
import bms.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ListUI {
    @Autowired
    private BookService bookService;

    private JFrame frame = new JFrame("List");
    private JPanel panel = new JPanel();
    private JList<String> list = new JList<>();
    private JButton addButton = new JButton("add");
    private JButton deleteButton = new JButton("delete");
    private JButton editButton = new JButton("edit");
    private JButton findButton = new JButton("find");
    private JButton refreshButton = new JButton("refresh");
    private JButton exitButton = new JButton("exit");

    private Map<Integer, Book> bookMap = new HashMap<>();

    public void build() {
        initUI();
        initRelation();
        initLogic();
    }

    private void initLogic() {
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.refreshListData(null);
        addButton.addActionListener(e -> {
            AddUI addUI = Main.context.getBean(AddUI.class);
            addUI.build();
        });
        deleteButton.addActionListener(e -> {
            if (list.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(frame, "No item is selected!");
                return;
            }
            bookService.delete(bookMap.get(list.getSelectedIndex()).getId());
            this.refreshListData(null);
        });
        editButton.addActionListener(e -> {
            if (list.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(frame, "No item is selected!");
                return;
            }
            EditUI editUI = Main.context.getBean(EditUI.class);
            editUI.setBook(bookMap.get(list.getSelectedIndex()));
            editUI.build();
        });
        findButton.addActionListener(e -> {
            FindUI findUI = Main.context.getBean(FindUI.class);
            findUI.build();
        });
        refreshButton.addActionListener(e -> this.refreshListData(null));
        exitButton.addActionListener(e -> frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING)));
        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (list.getSelectedIndex() != -1 && e.getClickCount() == 2) {
                    EditUI editUI = Main.context.getBean(EditUI.class);
                    editUI.setBook(bookMap.get(list.getSelectedIndex()));
                    editUI.setReadOnly();
                    editUI.build();
                }
                super.mouseClicked(e);
            }
        });
    }

    private void initUI() {
        frame.setLocationByPlatform(true);
        list.setBounds(10, 10, 280, 250);
        frame.setSize(320, 400);
        frame.setVisible(true);
        addButton.setBounds(10, 280, 80, 25);
        editButton.setBounds(110, 280, 80, 25);
        deleteButton.setBounds(210, 280, 80, 25);
        findButton.setBounds(10, 320, 80, 25);
        refreshButton.setBounds(110, 320, 80, 25);
        exitButton.setBounds(210, 320, 80, 25);
    }

    private void initRelation() {
        frame.add(panel);
        panel.setLayout(null);
        panel.add(list);
        panel.add(addButton);
        panel.add(editButton);
        panel.add(deleteButton);
        panel.add(findButton);
        panel.add(refreshButton);
        panel.add(exitButton);
    }

    public void refreshListData(Book example) {
        List<Book> books = bookService.getList(example);
        String[] listData = new String[books.size()];
        for (int i = 0; i < books.size(); i++) {
            bookMap.put(i, books.get(i));
            listData[i] = books.get(i).toString();
        }
        list.setListData(listData);
    }
}
