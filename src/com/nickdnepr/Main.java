package com.nickdnepr;

import com.nickdnepr.core.HierarchicalClusterBuilder;
import com.nickdnepr.core.Item;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        Item item1 = new Item(29,24);
        Item item2 = new Item(29,32);
        Item item3 = new Item(30,25);
        Item item4 = new Item(30,35);
        Item item5 = new Item(28,27);
        Item item6 = new Item(31,33);
        ArrayList<Item> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);
        items.add(item3);
        items.add(item4);
        items.add(item5);
        items.add(item6);
        HierarchicalClusterBuilder builder = new HierarchicalClusterBuilder(items);
        builder.process();
    }
}
