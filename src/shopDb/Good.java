package shopDb;

public class Good {
    private int id;
    private String name;
    private double price;
    private int num;
    public Good(int id, String name, double price, int num) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.num = num;
    }
    public void add(){
        num ++;
    }

    @Override
    public String toString() {
        return "Good [id=" + id + ", name=" + name + ", price=" + price + ", num=" + num + "]";
    }
}
