package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.util.Util;
import jm.task.core.jdbc.util.SqlString;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;




public class UserDaoJDBCImpl implements UserDao {
    Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {

    }
    @Override
    public void createUsersTable() {
        PreparedStatement preparedStatement = null;
        String sql = String.format("CREATE TABLE IF NOT EXISTS Users (\n" +
                "  `id` int NOT NULL AUTO_INCREMENT,\n" +
                "  `Name` varchar(40) NOT NULL,\n" +
                "  `lastName` varchar(45) NOT NULL,\n" +
                "  `Age` int NOT NULL,\n" +
                "  PRIMARY KEY (`id`)\n" +
                ") ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3");
        try {
            preparedStatement = connection.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    @Override
    public void dropUsersTable() {
        PreparedStatement preparedStatement = null;
        String sql = "DROP USERS TABLE";
        try {
            preparedStatement = connection.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    @Override
    public void saveUser(String name, String lastName, byte age) {
        PreparedStatement preparedStatement = null;
        String sql = "INSERT INTO USERS (name, lastName, age) VALUES (?, ?, ?)";
            try {
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, lastName);
                preparedStatement.setByte(3, age);
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("User with name " + name + " added to the database");
                }
            } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        PreparedStatement preparedStatement = null;
        String sql = "DELETE FROM USERS WHERE ID=?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT id, Name, lastName, Age FROM USERS";
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                User users = new User();
                users.setId(resultSet.getLong("Id"));
                users.setName(resultSet.getString("Name"));
                users.setLastName(resultSet.getString("lastName"));
                users.setAge(resultSet.getByte("Age"));
                userList.add(users);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }
    @Override
    public void cleanUsersTable() {
        String sql = "TRUNCATE TABLE USERS";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
