package Fileio;

import shopDb.His;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;

public class purchaseRecord {
    protected static final String file_path = "hisdata";

    public static ArrayList<His> load() throws FileNotFoundException {
        File file = new File(file_path);
        Scanner scanner = new Scanner(file);
        ArrayList<His> hisses = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] split = line.split(",");
            hisses.add(new His(split[0], Integer.parseInt(split[1]), split[2], new BigDecimal(split[3]), Integer.parseInt(split[4])));
        }
        return hisses;
    }

    public static void save(ArrayList<His> hisses) {
        File file = new File(file_path);
        try (PrintWriter printWriter = new PrintWriter(file)) {
            for (His h : hisses) {
                printWriter.println(h);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

