package dbManger;


import shopDb.User;

import java.sql.*;
import java.util.ArrayList;

public class userManager implements Manager{
    // 从数据库加载用户数据
    public static ArrayList<User> load() {
        ArrayList<User> users = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            String sql = "SELECT username, password FROM User";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                users.add(new User(username, password));
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    // 将用户数据保存到数据库
    public static void save(ArrayList<User> users) {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);

            // 插入或更新用户数据
            String upsertSql = "INSERT INTO User (username, password) VALUES (?, ?) "
                    + "ON DUPLICATE KEY UPDATE password = VALUES(password)";
            PreparedStatement preparedStatement = connection.prepareStatement(upsertSql);

            for (User user : users) {
                preparedStatement.setString(1, user.getUsername());
                preparedStatement.setString(2, user.getPassword());
                preparedStatement.executeUpdate();
            }

            // 删除数据库中多余的用户
            String selectSql = "SELECT username FROM User";
            Statement selectStatement = connection.createStatement();
            ResultSet resultSet = selectStatement.executeQuery(selectSql);

            ArrayList<String> databaseUsernames = new ArrayList<>();
            while (resultSet.next()) {
                databaseUsernames.add(resultSet.getString("username"));
            }

            for (String dbUsername : databaseUsernames) {
                boolean existsInUsers = users.stream().anyMatch(user -> user.getUsername().equals(dbUsername));
                if (!existsInUsers) {
                    String deleteSql = "DELETE FROM User WHERE username = ?";
                    PreparedStatement deleteStatement = connection.prepareStatement(deleteSql);
                    deleteStatement.setString(1, dbUsername);
                    deleteStatement.executeUpdate();
                    deleteStatement.close();
                }
            }

            preparedStatement.close();
            resultSet.close();
            selectStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}