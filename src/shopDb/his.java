package shopDb;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class His {
    private String username;
    private int id;
    private String name;
    private BigDecimal price;
    private int num;

    public His(String username, int id, String name, BigDecimal price, int num) {
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
        return String.format(
                "Good [id=%-5d, name=%-10s, price=%-10.3f, num=%-5d]",
                id,
                name,
                price.setScale(3, RoundingMode.HALF_UP),
                num
        );
    }

}
