package shopDb;

import java.util.Scanner;

class Admin extends User {
    // 继承自User类
    public Admin(String username, String password) {
        super(username, password);
    }

    public static void adminLogin(Scanner sc) {
        System.out.println("请输入管理员用户名：");
        String adminUsername = sc.next();

        System.out.println("请输入管理员密码：");
        String adminPassword = sc.next();

        if ("admin".equals(adminUsername) && "admin".equals(adminPassword)) {
            System.out.println("管理员登录成功！");
            adminMenu(sc);
        } else {
            System.out.println("管理员用户名或密码错误，请重新输入。");
        }
    }

    private static void adminMenu(Scanner sc) {
        boolean adminFlag = true;
        while (adminFlag) {
            System.out.println("*****管理员菜单*****");
            System.out.println("     1. 查看所有用户");
            System.out.println("     2. 返回主菜单");
            System.out.println("*******************");
            System.out.println("请选择菜单");
            int choice = sc.nextInt();
            sc.next(); // Consume newline left-over
            System.out.print("您选择的菜单是：");
            switch (choice) {
                case 1:
                    ;
                    break;
                case 2:
                    adminFlag = false;
                    break;
                default:
                    System.out.println("您输入的选项不存在请重新输入：");
                    break;
            }
        }
    }

}