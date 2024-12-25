package DbManger;

import shopDb.his;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;

public class hisManager {
    private static final String url = "jdbc:mysql://localhost:3306/table";
    private static final String username = "root";
    private static final String password = "703527";

    public static ArrayList<his> load() {
        ArrayList<his> goods = new ArrayList<>();
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

                goods.add(new his(username, id, name, price, num));
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return goods;
    }

    public static void save(ArrayList<his> goods) {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);

            // 更新或插入数据
            String upsertSql = "INSERT INTO purchaseRecord (username, id, name, price, num) VALUES (?, ?, ?, ?, ?) "
                    + "ON DUPLICATE KEY UPDATE name = VALUES(name), price = VALUES(price), num = VALUES(num)";
            PreparedStatement preparedStatement = connection.prepareStatement(upsertSql);

            for (his good : goods) {
                preparedStatement.setString(1, good.getUsername());
                preparedStatement.setInt(2, good.getId());
                preparedStatement.setString(3, good.getName());
                preparedStatement.setBigDecimal(4, good.getPrice());
                preparedStatement.setInt(5, good.getNum());
                preparedStatement.executeUpdate();
            }

            // 删除数据库中多余的数据
            String selectSql = "SELECT id FROM purchaseRecord";
            Statement selectStatement = connection.createStatement();
            ResultSet resultSet = selectStatement.executeQuery(selectSql);

            ArrayList<Integer> databaseIds = new ArrayList<>();
            while (resultSet.next()) {
                databaseIds.add(resultSet.getInt("id"));
            }

            for (int dbId : databaseIds) {
                boolean existsInGoods = goods.stream().anyMatch(good -> good.getId() == dbId);
                if (!existsInGoods) {
                    String deleteSql = "DELETE FROM student WHERE id = ?";
                    PreparedStatement deleteStatement = connection.prepareStatement(deleteSql);
                    deleteStatement.setInt(1, dbId);
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