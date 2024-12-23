package Fileio;

import shopDb.User;
import java.io.*;
import java.util.*;

public class userFile {
    private static final String file_path = "userdata";

    public static ArrayList<User> load() throws FileNotFoundException {
        File file = new File(file_path);
        Scanner scanner = new Scanner(file);
        ArrayList<User> users = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] split = line.split(",");
            users.add(new User(split[0], split[1]));
        }
        return users;
    }

    public static void save(ArrayList<User> users) throws FileNotFoundException {
        File file = new File(file_path);
        try (PrintWriter printWriter = new PrintWriter(file)) {  // 使用 try-with-resources 自动关闭 PrintWriter
            for (User user : users) {
                printWriter.println(user);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
