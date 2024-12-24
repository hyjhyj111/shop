package Fileio;

import shopDb.Good;
import shopDb.Good;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class goodFile {
    protected static final String file_path = "gooddata";
    public static ArrayList<Good> load() throws FileNotFoundException {
        File file = new File(file_path);
        Scanner scanner = new Scanner(file);
        ArrayList<Good> goods = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] split = line.split(",");
            goods.add(new Good(Integer.parseInt(split[0]), split[1], Double.parseDouble(split[2]), Integer.parseInt(split[3])));
        }
        return goods;
    }

    public static void save(ArrayList<Good> goods) {
        File file = new File(file_path);
        try (PrintWriter printWriter = new PrintWriter(file)) {
            for (Good good : goods) {
                printWriter.println(good);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


}
