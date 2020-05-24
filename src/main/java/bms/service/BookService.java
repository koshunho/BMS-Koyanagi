package bms.service;

import bms.model.Book;

import java.util.List;

public interface BookService {
    List<Book> getList(Book example);

    Long add(Book book);

    void delete(Long id);

    void edit(Book book);
}
