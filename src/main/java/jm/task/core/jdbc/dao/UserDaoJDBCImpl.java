package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import jm.task.core.jdbc.util.Util;







public class UserDaoJDBCImpl implements UserDao {

    Util util = new Util();


    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection con = util.getConnection()) {
            PreparedStatement preparedStatement =
                    con.prepareStatement("CREATE TABLE IF NOT EXISTS users (\n" +
                            "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                            "  `name` VARCHAR(45) NOT NULL,\n" +
                            "  `lastname` VARCHAR(45) NOT NULL,\n" +
                            "  `age` INT NOT NULL,\n" +
                            "  PRIMARY KEY (`id`),\n" +
                            "  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)");
            preparedStatement.executeUpdate();
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Connection con = util.getConnection()) {
            PreparedStatement preparedStatement =
                    con.prepareStatement("DROP TABLE IF EXISTS users");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection con = util.getConnection()) {
            PreparedStatement preparedStatement =
                    con.prepareStatement("INSERT INTO users (name, lastName, age) values (?,?,?)");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Connection con = util.getConnection(); PreparedStatement pstm = con.prepareStatement("DELETE FROM users WHERE id = ?")) {
            pstm.setLong(1, id);
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List <User> users = new ArrayList<>();
        User user;
        try (Connection con = util.getConnection()) {
            PreparedStatement preparedStatement =
                    con.prepareStatement("SELECT * FROM users");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Long id = resultSet.getLong(1);
                String name = resultSet.getString(2);
                String lastname = resultSet.getString(3);
                Byte age = resultSet.getByte(4);
                user = new User(name, lastname, age);
                user.setId(id);
                users.add(user);
            }
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Connection con = util.getConnection()) {
            PreparedStatement preparedStatement =
                    con.prepareStatement("TRUNCATE TABLE users");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}