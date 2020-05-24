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
public class AddUI {

    @Autowired
    private BookService bookService;

    @Autowired
    private ListUI listUI;

    public void build() {
        initUI();
        initRelation();
        initLogic();
    }

    private JFrame frame = new JFrame("Add");
    private JPanel panel = new JPanel();
    private JLabel nameLabel = new JLabel("Name:");
    private JLabel authorLabel =  new JLabel("Author:");
    private JLabel publisherLabel =  new JLabel("publisher:");
    private JLabel ISBNLabel =  new JLabel("ISBN:");
    private JLabel priceLabel =  new JLabel("Price:");
    private JLabel explanationLabel =  new JLabel("Explanation:");
    private JTextField nameText = new JTextField(32);
    private JTextField authorText = new JTextField(32);
    private JTextField publisherText = new JTextField(32);
    private JTextField ISBNText = new JTextField(32);
    private JTextField priceText = new JTextField(32);
    private JTextField explanationText = new JTextField(1024);
    private JButton addButton = new JButton("add");

    private void initLogic() {
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        addButton.addActionListener(e -> {
            Book book = new Book();
            book.setName(nameText.getText());
            book.setAuthor(authorText.getText());
            book.setPublisher(publisherText.getText());
            book.setISBN(ISBNText.getText());
            book.setPrice((int) Math.round(Double.valueOf(priceText.getText()) * 100));
            book.setExplanation(explanationText.getText());
            book.setNumber(1);
            bookService.add(book);
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
        panel.add(addButton);
    }

    private void initUI() {
        frame.setLocationByPlatform(true);
        frame.setSize(320, 350);
        frame.setVisible(true);
        nameLabel.setBounds(10, 10, 80, 25);
        nameText.setBounds(150, 10, 150, 25);
        authorLabel.setBounds(10, 50, 80, 25);
        authorText.setBounds(150, 50, 150, 25);
        publisherLabel.setBounds(10, 90, 80, 25);
        publisherText.setBounds(150, 90, 150, 25);
        ISBNLabel.setBounds(10, 130, 80, 25);
        ISBNText.setBounds(150, 130, 150, 25);
        priceLabel.setBounds(10, 170, 80, 25);
        priceText.setBounds(150, 170, 150, 25);
        explanationLabel.setBounds(10, 210, 80, 25);
        explanationText.setBounds(150, 210, 150, 25);
        addButton.setBounds(110, 260, 80, 25);
    }
}
