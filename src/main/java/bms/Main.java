package bms;

import bms.ui.LoginUI;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"bms"})
public class Main {
    public static ApplicationContext context;
    public static void main(String[] args) {
        context = new AnnotationConfigApplicationContext(Main.class);
        LoginUI loginUI = context.getBean(LoginUI.class);
        loginUI.build();
    }
}
