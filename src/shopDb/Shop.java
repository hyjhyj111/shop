package shopDb;
import Fileio.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Shop {
    public static Scanner sc = new Scanner(System.in);
    public static void chooseMenu() throws FileNotFoundException {
        String username = null;
        while (true) {
            System.out.println("*****欢迎进入电子商城*****");
            System.out.println("     1.注册            ");
            System.out.println("     2.登录            ");
            System.out.println("     3.查看商城         ");
            System.out.println("     4.查看我购买的商品   ");
            System.out.println("     5.管理员登录        ");
            System.out.println("     6.退出系统         ");
            System.out.println("***********************");
            System.out.print("请选择菜单:");
            int choice = sc.nextInt();
            switch (choice){
                case 1:
                    System.out.println("您选择的菜单是：注册");
                    User.register(sc);
                    break;
                case 2:
                    System.out.println("您选择的菜单是：登录");
                    username = User.login(sc);
                    break;
                case 3:
                    if (username == null) {
                        System.out.println("请登陆");
                        break;
                    }
                    System.out.println("您选择的菜单是：查看商城");
                    check();
                    break;
                case 4:
                    if (username == null) {
                        System.out.println("请登陆");
                        break;
                    }
                    System.out.println("您选择的菜单是：查看我购买的商品");
                    showPurchased(username);
                    break;
                case 5:
                    System.out.println("您选择的菜单是：管理员登录");
                    adminLogin();
                    adminMenu();
                    break;
                case 6:
                    System.out.println("退出系统");
                    System.out.println("谢谢使用");
                    System.exit(0);
                    break;
                default:
                    System.out.println("您输入的选项不存在请重新输入：");
                    break;
            }
        }
    }

    private static void check() throws FileNotFoundException {
        ArrayList<Good> goods = goodFile.load();
        for (Good good : goods) {
            System.out.println(good);
        }
    }

    private static void showPurchased(String username) throws FileNotFoundException {
        ArrayList<his> arr = purchaseRecord.load();
        for (his h : arr) {
            if (h.getUsername().equals(username)) {
                System.out.println(h.toString2());
            }
        }
    }
    
    private static void adminLogin() {
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

    private static void adminMenu(){
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

                    ;
                    break;
                case 2:
                    ;
                    break;
                case 3:
                    ;
                    break;
                case 4:
                    ;
                    break;
                case 5:
                    adminFlag = false;
                    break;
                default:
                    System.out.println("您输入的选项不存在请重新输入：");
                    break;
            }
        }
    }


    private static boolean isValidUsername(String username) {
        return username.length() >= 3;
    }

    private static boolean isValidPassword(String password) {
        Pattern pattern = Pattern.compile("^(?=.*[a-zA-Z])(?=.*\\d).{6,}$");
        return pattern.matcher(password).matches();
    }

    private static boolean isUsernameExists(String username) throws FileNotFoundException {
        ArrayList<User> users = userFile.load();
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }
}
