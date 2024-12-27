package DbManger;

import shopDb.Good;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;

import static DbManger.Manager.*;

public class goodManager {
    public static ArrayList<Good> load() {
        ArrayList<Good> goods = new ArrayList<>();
        try{
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            String sql = "select * from Good ORDER BY price DESC;";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                BigDecimal price = resultSet.getBigDecimal("price");
                int num = resultSet.getInt("num");
                goods.add(new Good(id, name, price, num));
            }
            resultSet.close();
            statement.close();
            connection.close();
        }catch(SQLException e){
            e.printStackTrace();
        }

        return goods;
    }

    public static void save(ArrayList<Good> goods) {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);

            // Step 1: 同步新增和更新
            String upsertSql = "INSERT INTO Good (id, name, price, num) VALUES (?, ?, ?, ?) "
                    + "ON DUPLICATE KEY UPDATE name = VALUES(name), price = VALUES(price), num = VALUES(num)";
            PreparedStatement upsertStatement = connection.prepareStatement(upsertSql);

            for (Good good : goods) {
                upsertStatement.setInt(1, good.getId());
                upsertStatement.setString(2, good.getName());
                upsertStatement.setBigDecimal(3, good.getPrice());
                upsertStatement.setInt(4, good.getNum());
                upsertStatement.executeUpdate();
            }

            // Step 2: 同步删除
            // 获取数据库中所有的商品 ID
            String selectSql = "SELECT id FROM Good";
            Statement selectStatement = connection.createStatement();
            ResultSet resultSet = selectStatement.executeQuery(selectSql);

            ArrayList<Integer> databaseIds = new ArrayList<>();
            while (resultSet.next()) {
                databaseIds.add(resultSet.getInt("id"));
            }

            // 找出需要删除的商品 ID（数据库中有，但 goods 列表中没有）
            for (int dbId : databaseIds) {
                boolean existsInGoods = goods.stream().anyMatch(good -> good.getId() == dbId);
                if (!existsInGoods) {
                    String deleteSql = "DELETE FROM Good WHERE id = ?";
                    PreparedStatement deleteStatement = connection.prepareStatement(deleteSql);
                    deleteStatement.setInt(1, dbId);
                    deleteStatement.executeUpdate();
                    deleteStatement.close();
                }
            }

            // 关闭资源
            upsertStatement.close();
            resultSet.close();
            selectStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
