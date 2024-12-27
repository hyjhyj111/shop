package dbManger;

import shopDb.His;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;

public class hisManager implements Manager{
    public static ArrayList<His> load() {
        ArrayList<His> goods = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM purchaseRecord";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String username = resultSet.getString("username");
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                BigDecimal price = resultSet.getBigDecimal("price");
                int num = resultSet.getInt("num");

                goods.add(new His(username, id, name, price, num));
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return goods;
    }

    public static void save(ArrayList<His> goods) {
        try {
            // 建立数据库连接
            Connection connection = DriverManager.getConnection(url, username, password);

            // 1. 删除所有数据
            String deleteSql = "TRUNCATE TABLE purchaseRecord"; // 更高效
            Statement deleteStatement = connection.createStatement();
            deleteStatement.executeUpdate(deleteSql);
            deleteStatement.close();

            // 2. 插入新数据
            String insertSql = "INSERT INTO purchaseRecord (username, id, name, price, num) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertSql);

            for (His good : goods) {
                // 设置参数
                preparedStatement.setString(1, good.getUsername());
                preparedStatement.setInt(2, good.getId());
                preparedStatement.setString(3, good.getName());
                preparedStatement.setBigDecimal(4, good.getPrice());
                preparedStatement.setInt(5, good.getNum());

                // 执行插入操作
                preparedStatement.executeUpdate();
            }

            // 关闭资源
            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}