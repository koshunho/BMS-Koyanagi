package bms.ui;

import bms.model.Book;
import bms.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.swing.*;
import java.awt.event.WindowEvent;

@Controller
@Scope("prototype")
public class EditUI {

    @Autowired
    private BookService bookService;

    @Autowired
    private ListUI listUI;

    private Book book;

    public void setBook(Book book) {
        this.book = book;
    }

    public void setReadOnly() {
        nameText.setEditable(false);
        authorText.setEditable(false);
        publisherText.setEditable(false);
        ISBNText.setEditable(false);
        priceText.setEditable(false);
        explanationText.setEditable(false);
        numberText.setEditable(false);
        frame.setTitle("Detail");
    }

    public void build() {
        initUI();
        initRelation();
        initLogic();
    }


    private JFrame frame = new JFrame("Edit");
    private JPanel panel = new JPanel();
    private JLabel nameLabel = new JLabel("Name:");
    private JLabel authorLabel =  new JLabel("Author:");
    private JLabel publisherLabel =  new JLabel("publisher:");
    private JLabel ISBNLabel =  new JLabel("ISBN:");
    private JLabel priceLabel =  new JLabel("Price:");
    private JLabel explanationLabel =  new JLabel("Explanation:");
    private JLabel numberLabel =  new JLabel("Number:");
    private JTextField nameText = new JTextField(32);
    private JTextField authorText = new JTextField(32);
    private JTextField publisherText = new JTextField(32);
    private JTextField ISBNText = new JTextField(32);
    private JTextField priceText = new JTextField(32);
    private JTextField explanationText = new JTextField(1024);
    private JTextField numberText = new JTextField(32);
    private JButton editButton = new JButton("OK");

    private void initLogic() {
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        editButton.addActionListener(e -> {
            book.setName(nameText.getText());
            book.setAuthor(authorText.getText());
            book.setPublisher(publisherText.getText());
            book.setISBN(ISBNText.getText());
            book.setPrice((int) Math.round(Double.valueOf(priceText.getText()) * 100));
            book.setExplanation(explanationText.getText());
            book.setNumber(Integer.valueOf(numberText.getText()));
            bookService.edit(book);
            listUI.refreshListData(null);
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        });
    }

    private void initRelation() {
        frame.add(panel);
        panel.setLayout(null);
        panel.add(nameLabel);
        panel.add(nameText);
        panel.add(authorLabel);
        panel.add(authorText);
        panel.add(publisherLabel);
        panel.add(publisherText);
        panel.add(ISBNLabel);
        panel.add(ISBNText);
        panel.add(priceLabel);
        panel.add(priceText);
        panel.add(explanationLabel);
        panel.add(explanationText);
        panel.add(numberLabel);
        panel.add(numberText);
        panel.add(editButton);
    }

    private void initUI() {
        frame.setLocationByPlatform(true);
        frame.setSize(320, 380);
        frame.setVisible(true);
        nameLabel.setBounds(10, 10, 80, 25);
        nameText.setBounds(150, 10, 150, 25);
        nameText.setText(book.getName());
        authorLabel.setBounds(10, 50, 80, 25);
        authorText.setBounds(150, 50, 150, 25);
        authorText.setText(book.getAuthor());
        publisherLabel.setBounds(10, 90, 80, 25);
        publisherText.setBounds(150, 90, 150, 25);
        publisherText.setText(book.getPublisher());
        ISBNLabel.setBounds(10, 130, 80, 25);
        ISBNText.setBounds(150, 130, 150, 25);
        ISBNText.setText(book.getISBN());
        priceLabel.setBounds(10, 170, 80, 25);
        priceText.setBounds(150, 170, 150, 25);
        priceText.setText(String.valueOf(book.getPrice()/100.0));
        explanationLabel.setBounds(10, 210, 80, 25);
        explanationText.setBounds(150, 210, 150, 25);
        explanationText.setText(book.getExplanation());
        numberLabel.setBounds(10, 250, 80, 25);
        numberText.setBounds(150, 250, 150, 25);
        numberText.setText(book.getNumber().toString());
        editButton.setBounds(110, 290, 80, 25);
    }
}
