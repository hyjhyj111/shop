package shopDb;

import DbManger.goodManager;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Scanner;

public class Good {
    private int id;
    private String name;
    private BigDecimal price;
    private int num;
    public Good(int id, String name, BigDecimal price, int num) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.num = num;
    }
    public Good(Good a){
        this.id = a.id;
        this.name = a.name;
        this.price = a.price;
        this.num = a.num;
    }
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getNum() {
        return num;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return id + "," + name + "," + price.setScale(3, RoundingMode.HALF_UP) + "," + num;
    }
    public String s() {
        return String.format(
                "Good [id=%-5d, name=%-10s, price=%-10.3f, num=%-5d]",
                id,
                name,
                price.setScale(3, RoundingMode.HALF_UP),
                num
        );
    }

    public static void Goodadd(Scanner sc) {
        ArrayList<Good> goods = goodManager.load();
        int id,num;
        String name;
        BigDecimal price;
        String choic = "";

        do{
            System.out.println("*********开始添加商品*********");
            System.out.print("请输入商品编号:");
            id = sc.nextInt();
            System.out.print("请输入商品名称:");
            name = sc.next();
            if(isExist(id,name,goods)){
                System.out.println("该商品编号或名称重复");
                System.out.println("请重新输入");
                continue;
            }
            System.out.print("请输入商品价格:");
            price = sc.nextBigDecimal();
            System.out.print("请输入商品数量:");
            num = sc.nextInt();
            System.out.println("*********商品添加成功!*********");
            Good newGood = new Good(id,name,price,num);
            goods.add(newGood);
            goodManager.save(goods);
            System.out.println("是否继续添加?y/n");
            choic = sc.next();
        }while(choic.equals("Y") || choic.equals("y"));
    }

    public static void modifygoods(Scanner sc) {
        int ID;
        String name1;
        String choic="Y";
        BigDecimal price1;
        int num1;
        ArrayList<Good> goods = goodManager.load();

        do {int flag=0;
            System.out.print("请输入需要修改的商品编号:");
            ID = sc.nextInt();

            for(Good good : goods){
                if(good.getId()==ID){
                    System.out.println("该商品信息如下:");
                    System.out.println("商品ID\t商品名称\t商品价格\t商品数量");
                    flag=1;
                    System.out.printf("%-8d%-8s%-8.2f%-8d\n",good.id,good.name,good.price,good.num);
                    System.out.print("请输入商品名称:");
                    name1 = sc.next();
                    System.out.print("请输入商品价格:");
                    price1 = sc.nextBigDecimal();
                    System.out.print("请输入商品数量:");
                    num1 = sc.nextInt();
                    goods.remove(good);
                    goods.add(new Good(ID,name1,price1,num1));
                    goodManager.save(goods);
                    break;
                }
            }
            if(flag==0){System.out.println("未查找到该商品");}
            else{
                System.out.println("修改成功!");

                System.out.println("是否继续修改?y/n");
                choic = sc.next();
            }
        }while(choic.equals("Y") || choic.equals("y"));
    }

    public static void delete(Scanner sc) {
        int ID;
        String name1;
        String choic="Y";
        double price1;
        int num1;
        ArrayList<Good> goods = goodManager.load();
        System.out.println("*******开始删除商品******");
        do {int flag=0;String confirm;
            System.out.print("请输入需要删除的商品编号:");
            ID = sc.nextInt();

            for(Good good : goods){
                if(good.getId()==ID){
                    System.out.println("*******你删除的商品列表如下*******");
                    System.out.println("商品ID\t商品名称\t商品价格\t商品数量");
                    flag=1;
                    System.out.printf("%-8d%-8s%-8.2f%-8d\n",good.id,good.name,good.price,good.num);
                    System.out.println("是否确认删除?y/n");
                    confirm = sc.next();
                    if(confirm.equals("Y") || confirm.equals("y")){
                        goods.remove(good);
                        goodManager.save(goods);

                        break;}else{
                        flag=3;
                    }
                }
            }
            if(flag==0){System.out.println("未查找到该商品");}
            else if(flag==1){
                System.out.println("*******商品删除成功******");

                System.out.println("是否继续删除商品?y/n");
                choic = sc.next();
            }else{
                System.out.println("取消删除");
                break;
            }
        }while(choic.equals("Y") || choic.equals("y"));

    }
    private static boolean isExist(int id ,String name,ArrayList<Good>goods){
        for(Good good:goods){
            if(good.getId()==id||good.getName().equals(name)){return true;}
        }
        return false;
    }

    public static void List(Scanner sc) {
        String choice;
        System.out.println("********商品列表*******");
        ArrayList<Good> goods = goodManager.load();
        goods.sort((a, b) -> Integer.compare(a.id, b.id));
        System.out.println("商品ID\t商品名称\t商品价格\t商品数量");
        for(Good good:goods){
            System.out.printf("%-8d%-8s%-8.2f%-8d\n",good.id,good.name,good.price,good.num);
        }
        System.out.println("是否按价格排序?y/n");
        choice = sc.next();
        if(choice.equals("Y") || choice.equals("y")){
            goods.sort((a, b) -> a.getPrice().compareTo(b.getPrice()));
            System.out.println("商品ID\t商品名称\t商品价格\t商品数量");
            for(Good good:goods){
                System.out.printf("%-8d%-8s%-8.2f%-8d\n",good.id,good.name,good.price,good.num);
            }
        }
    }
}
