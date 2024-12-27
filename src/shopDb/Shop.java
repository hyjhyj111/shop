package shopDb;

import dbManger.goodManager;
import dbManger.hisManager;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;

public class Shop {
    public static Scanner sc = new Scanner(System.in);
    public static void chooseMenu() throws IOException {
        String username = null;
        while (true) {
            System.out.println("*****欢迎进入电子商城*****");
            System.out.println("     1.注册            ");
            System.out.println("     2.登录            ");
            System.out.println("     3.查看商城         ");
            System.out.println("     4.查看我购买的商品   ");
            System.out.println("     5.管理员登录        ");
            System.out.println("     6.切换用户         ");
            System.out.println("     7.退出系统         ");
            System.out.println("***********************");
            System.out.println("请选择菜单:");
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
                    check(sc, username);
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
                    Admin.adminLogin(sc);
                    Admin.adminMenu(sc);
                    break;
                case 6:
                    System.out.println("您选择的菜单是:切换用户");
                    username = User.login(sc);
                    break;
                case 7:
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

    private static void check(Scanner sc, String username) {
        ArrayList<Good> goods = goodManager.load(); // 加载商品列表
        ArrayList<His> his = hisManager.load();

        while (true) {
            // 显示商品信息
            for (Good good : goods) {
                System.out.println(good.s());
            }
            System.out.println("请输入您要购买的商品编号:");

            int id;
            while (true) {
                id = sc.nextInt(); // 输入商品编号
                int ind = isExistId(id); // 检查商品是否存在
                if (ind != -1) {  // 如果商品存在
                    System.out.println("请输入购买数量:");
                    int num;
                    while (true) {
                        num = sc.nextInt(); // 输入购买数量
                        if (num >= 1 && num <= goods.get(ind).getNum()) {
                            // 显示购买成功信息
                            System.out.println("******* 商品购买成功! *******");
                            System.out.println("********** 您购买的商品列表如下 *********");
                            Good t = new Good(goods.get(ind));
                            t.setNum(num);
                            goods.get(ind).setNum(goods.get(ind).getNum() - num);
                            System.out.println(t.s());
                            System.out.println("总价格为: " + t.getPrice().multiply(new BigDecimal(num)));

                            his.add(new His(username, t.getId(), t.getName(), t.getPrice(), t.getNum()));
                            break;
                        } else {
                            System.out.println("购买数量无效，请重新输入:");
                        }
                    }
                    break;
                } else {
                    System.out.println("商品编号不存在，请重新输入:");
                }
            }

            // 询问是否继续购买
            System.out.println("是否继续购买商品？(y/n)");
            String choice = sc.next();
            if (!choice.equalsIgnoreCase("y")) {
                System.out.println("谢谢您的购买！");
                break;
            }
        }
        goodManager.save(goods);
        hisManager.save(his);
    }

    private static void showPurchased(String username) {
        ArrayList<His> arr = hisManager.load();
        if (arr.isEmpty()) System.out.println("无购买历史");
        for (His h : arr) {
            if (h.getUsername().equals(username)) {
                System.out.println(h.toString2());
            }
        }
    }

    private static int isExistId(int id) {
        ArrayList<Good> goods = goodManager.load();
        for (int ind = 0; ind < goods.size(); ind++) {
            if (goods.get(ind).getId() == id) {
                return ind;
            }
        }
        return -1;
    }
}
