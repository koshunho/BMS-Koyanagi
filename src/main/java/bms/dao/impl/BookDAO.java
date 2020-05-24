package bms.dao.impl;

import bms.dao.DAO;
import bms.model.Book;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

@Repository
@PropertySource("classpath:config.properties")
public class BookDAO extends DAO<Book> {
    @Value("${data_source_book}")
    private String filePath;

    @Override
    protected String getFilePath() {
        return filePath;
    }
}
