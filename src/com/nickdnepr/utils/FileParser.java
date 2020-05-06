package com.nickdnepr.utils;

import com.nickdnepr.core.Item;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileParser {

    public static ArrayList<Item> readFile(String path) throws IOException {
        ArrayList<Item> items = new ArrayList<>();
        File file = new File(path);
        if (!file.exists()) {
            System.out.println("File does not exist");
            return null;
        }
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = reader.readLine();
        while (line != null) {
            line = line.trim().replaceAll(" +", " ");
//            System.out.println(line);
            String [] chars = line.split(" ");
            double [] parsedChars = new double[chars.length];
            for (int i=0; i<chars.length; i++){
                parsedChars[i] = Double.parseDouble(chars[i]);
            }
            items.add(new Item(parsedChars));
            line = reader.readLine();
        }

        return items;
    }
}
