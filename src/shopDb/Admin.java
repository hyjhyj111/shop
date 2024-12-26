package shopDb;

import DbManger.userManager;

import javax.swing.*;
import java.io.Console;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

class Admin extends User {
    // 继承自User类
    public Admin(String username, String password) {
        super(username, password);
    }

    public static void adminLogin(Scanner sc) throws IOException {
        boolean isValid = false;
        do {
            System.out.println("请输入管理员用户名：");
            String adminUsername = sc.next();

            System.out.println("请输入管理员密码：");
            String adminPassword = PasswordInput.read(sc);

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
            System.out.println("     4.查看商品列表");
            System.out.println("     5.用户管理");
            System.out.println("     6.返回主菜单");
            System.out.println("*******************");
            System.out.println("请选择菜单:");
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
                    System.out.println("用户管理");
                    changeUser(sc);
                    break;
                case 6:
                    System.out.println("返回主菜单");
                    adminFlag = false;
                    break;
                default:
                    System.out.println("您输入的选项不存在请重新输入：");
                    break;
            }
        }
    }
    private static void changeUser(Scanner sc) {
        ArrayList<User> users = userManager.load(); // 加载已有用户列表
        boolean adminFlag = true;
        System.out.println("当前用户列表：");
        System.out.printf("%-10s%s\n", "username", "password");
        for (User user : users) {
            System.out.printf("%-10s%s\n", user.getUsername(), user.getPassword());
        }
        while (adminFlag) {
            System.out.println("请选择操作：");
            System.out.println("1. 添加用户");
            System.out.println("2. 删除用户");
            System.out.println("3. 陈列用户");
            System.out.println("4. 退出管理");
            System.out.println("请输入选项：");

            String choice = sc.next();
            switch (choice) {
                case "1": // 添加用户
                    System.out.println("请输入新用户名：");
                    String newUsername = sc.next();
                    // 检查用户名是否已存在
                    boolean exists = false;
                    for (User user : users) {
                        if (user.getUsername().equals(newUsername)) {
                            exists = true;
                            break;
                        }
                    }
                    if (isUsernameExists(newUsername, users)) {
                        System.out.println("用户名已存在，无法添加。");
                    }
                    else if (!isValidUsername(newUsername)) {
                        System.out.println("用户名无效，请确保用户名至少3个字符长。");
                    }
                    else {
                        System.out.println("请输入新用户密码：");
                        String newPassword = sc.next();
                        if (!isValidPassword(newPassword)) {
                            System.out.println("密码无效，请确保密码包含字母和数字并且至少6个字符长。");
                            break;
                        }
                        users.add(new User(newUsername, newPassword)); // 添加用户
                        userManager.save(users);
                        System.out.println("用户添加成功！");
                    }
                    break;
                case "2": // 删除用户
                    System.out.println("请输入要删除的用户名：");
                    String delUsername = sc.next();
                    // 检查用户名是否存在并删除
                    User userToRemove = null;
                    for (User user : users) {
                        if (user.getUsername().equals(delUsername)) {
                            userToRemove = user;
                            break;
                        }
                    }
                    if (userToRemove != null) {
                        users.remove(userToRemove); // 删除用户
                        System.out.println("用户删除成功！");
                        userManager.save(users);
                    } else {
                        System.out.println("用户名不存在，无法删除。");
                    }
                    break;
                case "3":
                    System.out.println("当前用户列表：");
                    System.out.printf("%-10s%s\n", "username", "password");
                    for (User user : users) {
                        System.out.printf("%-10s%s\n", user.getUsername(), user.getPassword());
                    }
                    break;
                case "4": // 退出管理
                    adminFlag = false;
                    System.out.println("退出用户管理。");
                    break;
                default:
                    System.out.println("无效选项，请重新选择！");
                    break;
            }
        }
        userManager.save(users);
    }
}

class PasswordInput {
    public static String read(Scanner sc) {
        Console console = System.console();
        char[] password = console.readPassword();
        return new String(password);
    }
}