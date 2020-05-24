package bms.dao.impl;

import bms.dao.DAO;
import bms.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

@Repository
@PropertySource("classpath:config.properties")
public class UserDAO extends DAO<User> {
    @Value("${data_source_user}")
    private String filePath;

    @Override
    protected String getFilePath() {
        return filePath;
    }
}
