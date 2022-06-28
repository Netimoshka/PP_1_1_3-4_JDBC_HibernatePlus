package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.HyberUtil;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Main {
    public static void main(String[] args) throws SQLException, IOException {

        User user1 = new User("Bender", "Rodriges", (byte) 111);
        User user2 = new User("Kenny", "Mccormick", (byte) 11);
        User user3 = new User("Kyle", "Broflovsky", (byte) 12);
        User user4 = new User("Eric", "Cartmann", (byte) 12);
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);

        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();

        for (User u : users) {
            userService.saveUser(u.getName(), u.getLastName(), u.getAge());
        }

        List<User> retUser = userService.getAllUsers();
        for (User user : retUser) {
            System.out.println(user);
        }

        userService.cleanUsersTable();

        userService.dropUsersTable();

        //userService.removeUserById(1);



    }
}