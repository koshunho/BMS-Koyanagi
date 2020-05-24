package bms.ui;

import bms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.swing.*;
import java.awt.event.WindowEvent;

@Controller
public class LoginUI {

    @Autowired
    private UserService userService;

    @Autowired
    private ListUI listUI;

    private JFrame frame = new JFrame("Login");
    private JPanel panel = new JPanel();
    private JLabel userLabel = new JLabel("User:");
    private JLabel passwordLabel =  new JLabel("Password:");
    private JTextField userText = new JTextField(20);
    private JPasswordField passwordText = new JPasswordField(20);
    private JButton loginButton = new JButton("login");

    public void build() {
        initUI();
        initRelation();
        initLogic();
    }

    private void initLogic() {
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        loginButton.isDefaultButton();
        loginButton.addActionListener(e -> {
            if (userService.login(userText.getText(), String.valueOf(passwordText.getPassword()))) {
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                listUI.build();
            }
            else {
                JOptionPane.showMessageDialog(frame, "login fail!");
            }

        });

    }

    private void initUI() {
        frame.setLocationByPlatform(true);
        frame.setSize(320, 170);
        frame.setVisible(true);
        userLabel.setBounds(10,20,80,25);
        userText.setBounds(100,20,165,25);
        passwordLabel.setBounds(10,50,80,25);
        passwordText.setBounds(100,50,165,25);
        loginButton.setBounds(100, 90, 80, 25);
    }

    private void initRelation() {
        frame.add(panel);
        panel.setLayout(null);
        panel.add(userLabel);
        panel.add(userText);
        panel.add(passwordLabel);
        panel.add(passwordText);
        panel.add(loginButton);
    }
}
