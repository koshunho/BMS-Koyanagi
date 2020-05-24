package bms.service.impl;

import bms.dao.impl.BookDAO;
import bms.model.Book;
import bms.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookDAO bookDAO;

    @Override
    public List<Book> getList(Book example) {
        return bookDAO.selectByExample(b -> {
            if (example == null) {
                return true;
            }
            if (example.getName() != null && !b.getName().contains(example.getName())) {
                return false;
            }
            if (example.getAuthor() != null && !b.getAuthor().contains(example.getAuthor())) {
                return false;
            }
            if (example.getPublisher() != null && !b.getPublisher().contains(example.getPublisher())) {
                return false;
            }
            if (example.getISBN() != null && !b.getISBN().contains(example.getISBN())) {
                return false;
            }
            if (example.getExplanation() != null && !b.getExplanation().contains(example.getExplanation())) {
                return false;
            }
            return true;
        }, Book.class);
    }

    @Override
    public Long add(Book book) {
        List<Book> res = bookDAO.selectByExample(b -> b.getISBN().equals(book.getISBN()), Book.class);
        if (res.isEmpty()) {
            return bookDAO.insert(book);
        }
        res.get(0).setNumber(res.get(0).getNumber()+1);
        return res.get(0).getId();
    }

    @Override
    public void delete(Long id) {
        bookDAO.deleteById(id, Book.class);
    }

    @Override
    public void edit(Book book) {
        bookDAO.updateById(book, Book.class);
    }
}
