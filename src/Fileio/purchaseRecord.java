package Fileio;

import shopDb.Good;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class purchaseRecord {
    protected static final String file_path = "gooddata";

    public static ArrayList<his> load() throws FileNotFoundException {
        File file = new File(file_path);
        Scanner scanner = new Scanner(file);
        ArrayList<his> hiss = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] split = line.split(",");
            hiss.add(new his(split[0], Integer.parseInt(split[1]), split[2], Double.parseDouble(split[3]), Integer.parseInt(split[4])));
        }
        return hiss;
    }

    public static void save(ArrayList<his> hiss) throws FileNotFoundException {
        File file = new File(file_path);
        try (PrintWriter printWriter = new PrintWriter(file)) {
            for (his  h : hiss) {
                printWriter.println(h);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

class his{
    private String username;
    private int id;
    private String name;
    private double price;
    private int num;
    public his(String username, int id, String name, double price, int num) {
        this.username = username;
        this.id = id;
        this.name = name;
        this.price = price;
        this.num = num;
    }

    @Override
    public String toString() {
        return username+","+id+","+name+","+price+","+num;
    }
}
