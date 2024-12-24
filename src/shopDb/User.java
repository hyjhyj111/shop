package shopDb;

import DbManger.userManager;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.List;

public class User {
    private String username;
    private String password;

    // 构造函数
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return username + "," + password;
    }

    // username的getter和setter
    public String getUsername() {
        return username;
    }

    // password的getter和setter
    public String getPassword() {
        return password;
    }

    public static void register(Scanner sc) {
        String username;
        ArrayList<User> users = userManager.load();
        do {
            System.out.println("请输入用户名（至少3个字符）：");
            username = sc.next();
            if (!isValidUsername(username)) {
                System.out.println("用户名无效，请确保用户名至少3个字符长。");
                continue;
            }
            if (isUsernameExists(username, users)) {
                System.out.println("用户名已存在，请选择其他用户名。");
            }
        } while (!isValidUsername(username) || isUsernameExists(username, users));

        String password;
        do {
            System.out.println("请输入密码（必须包含字母和数字，至少6个字符）：");
            password = sc.next();
            if (!isValidPassword(password)) {
                System.out.println("密码无效，请确保密码包含字母和数字并且至少6个字符长。");
            }
        } while (!isValidPassword(password));

        System.out.println("请确认密码：");
        String confirmPassword = sc.next();
        if (!password.equals(confirmPassword)) {
            System.out.println("两次输入的密码不一致，请重新尝试注册。");
            return ;
        }

        User newUser = new User(username, password);
        users.add(newUser);
        userManager.save(users);
        System.out.println("注册成功！");
    }

    public static String login(Scanner sc) {
        List<User> users = userManager.load();
        System.out.println("请输入用户名：");
        String username = sc.next();

        System.out.println("请输入密码：");
        String password = sc.next();

        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                System.out.println("登录成功！");
                return username;
            }
        }
        System.out.println("用户名或密码错误，请重新输入。");
        return null;
    }

    private static boolean isValidUsername(String username) {
        return username.length() >= 3;
    }

    private static boolean isValidPassword(String password) {
        Pattern pattern = Pattern.compile("^(?=.*[a-zA-Z])(?=.*\\d).{6,}$");
        return pattern.matcher(password).matches();
    }

    private static boolean isUsernameExists(String username, List<User> users) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }
}


