package Fileio;

import java.math.BigDecimal;

public class his {
    private String username;
    private int id;
    private String name;
    private BigDecimal price;
    private int num;

    public his(String username, int id, String name, BigDecimal price, int num) {
        this.username = username;
        this.id = id;
        this.name = name;
        this.price = price;
        this.num = num;
    }

    public String getUsername() {
        return username;
    }

    public int getNum() {
        return num;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return username + "," + id + "," + name + "," + price + "," + num;
    }

    public String toString2() {
        return "Good [id=" + id + ", name=" + name + ", price=" + price + ", num=" + num + "]";
    }

}
