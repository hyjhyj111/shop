package shopDb;

import java.io.FileNotFoundException;
import java.util.Scanner;

class Admin extends User {
    // 继承自User类
    public Admin(String username, String password) {
        super(username, password);
    }

    public static void adminLogin(Scanner sc) {
        boolean isValid = false;
        do {
            System.out.println("请输入管理员用户名：");
            String adminUsername = sc.next();

            System.out.println("请输入管理员密码：");
            String adminPassword = sc.next();

            if ("admin".equals(adminUsername) && "admin".equals(adminPassword)) {
                System.out.println("管理员登陆成功！");
                isValid = true;
            } else {
                System.out.println("管理员用户名或密码错误，请重新输入。");
            }
        }while(!isValid);
    }

    public static void adminMenu(Scanner sc) throws FileNotFoundException {
        boolean adminFlag = true;
        while (adminFlag) {
            System.out.println("*****管理员菜单*****");
            System.out.println("     1.添加商品 ");
            System.out.println("     2.修改商品 ");
            System.out.println("     3.删除商品 ");
            System.out.println("     4.查看商品列表 ");
            System.out.println("     5.返回主菜单");
            System.out.println("*******************");
            System.out.println("请选择菜单");
            int choice = sc.nextInt();
            System.out.print("您选择的菜单是：");
            switch (choice) {
                case 1:
                    System.out.println("添加商品");
                    Good.Goodadd(sc);
                    break;
                case 2:
                    System.out.println("修改商品");
                    Good.modifygoods(sc);
                    break;
                case 3:
                    System.out.println("删除商品");
                    Good.delete(sc);
                    break;
                case 4:
                    System.out.println("查看商品列表");
                    Good.List(sc);
                    break;
                case 5:
                    System.out.println("返回主菜单");
                    adminFlag = false;
                    break;
                default:
                    System.out.println("您输入的选项不存在请重新输入：");
                    break;
            }
        }
    }

}