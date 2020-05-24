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
public class FindUI {

    @Autowired
    private BookService bookService;

    @Autowired
    private ListUI listUI;

    public void build() {
        initUI();
        initRelation();
        initLogic();
    }

    private JFrame frame = new JFrame("Find");
    private JPanel panel = new JPanel();
    private JLabel nameLabel = new JLabel("Name:");
    private JLabel authorLabel =  new JLabel("Author:");
    private JLabel publisherLabel =  new JLabel("publisher:");
    private JLabel ISBNLabel =  new JLabel("ISBN:");
    private JLabel explanationLabel =  new JLabel("Explanation:");
    private JTextField nameText = new JTextField(32);
    private JTextField authorText = new JTextField(32);
    private JTextField publisherText = new JTextField(32);
    private JTextField ISBNText = new JTextField(32);
    private JTextField explanationText = new JTextField(1024);
    private JButton findButton = new JButton("find");

    private void initLogic() {
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        findButton.addActionListener(e -> {
            Book example = new Book();
            if (!nameText.getText().isEmpty()) {
                example.setName(nameText.getText());
            }
            if (!authorText.getText().isEmpty()) {
                example.setAuthor(authorText.getText());
            }
            if (!publisherText.getText().isEmpty()) {
                example.setPublisher(publisherText.getText());
            }
            if (!ISBNText.getText().isEmpty()) {
                example.setISBN(nameText.getText());
            }
            if (!explanationText.getText().isEmpty()) {
                example.setExplanation(nameText.getText());
            }
            listUI.refreshListData(example);
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
        panel.add(explanationLabel);
        panel.add(explanationText);
        panel.add(findButton);
    }

    private void initUI() {
        frame.setLocationByPlatform(true);
        frame.setSize(320, 300);
        frame.setVisible(true);
        nameLabel.setBounds(10, 10, 80, 25);
        nameText.setBounds(150, 10, 150, 25);
        authorLabel.setBounds(10, 50, 80, 25);
        authorText.setBounds(150, 50, 150, 25);
        publisherLabel.setBounds(10, 90, 80, 25);
        publisherText.setBounds(150, 90, 150, 25);
        ISBNLabel.setBounds(10, 130, 80, 25);
        ISBNText.setBounds(150, 130, 150, 25);
        explanationLabel.setBounds(10, 170, 80, 25);
        explanationText.setBounds(150, 170, 150, 25);
        findButton.setBounds(110, 220, 80, 25);
    }
}
