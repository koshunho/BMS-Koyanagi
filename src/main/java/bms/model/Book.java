package bms.model;

import org.springframework.util.StringUtils;

public class Book implements WithID {
    private Long id;

    // book name
    private String name;

    // author name
    private String author;

    // publisher name
    private String publisher;

    // ISBN number
    private String ISBN;

    // book price in cents
    private Integer price;

    // book explanation
    private String explanation;

    // book number
    private Integer number;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Override
    public String toString() {
        String[] params = {name, author, publisher, ISBN, String.valueOf(price/100.0), explanation};
        return StringUtils.arrayToDelimitedString(params, ";");
    }
}
