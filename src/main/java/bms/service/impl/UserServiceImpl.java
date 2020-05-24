package bms.service.impl;

import bms.dao.impl.UserDAO;
import bms.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import bms.service.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public boolean login(String username, String password) {
        List<User> users = userDAO.selectByExample(u -> u.getUsername().equals(username) && u.getPassword().equals(password), User.class);
        return !users.isEmpty();
    }

}
