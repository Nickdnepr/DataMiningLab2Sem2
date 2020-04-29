package com.nickdnepr;

import com.nickdnepr.core.HardBassIndexCalculator;
import com.nickdnepr.core.cluster_builders.HierarchicalClusterBuilder;
import com.nickdnepr.core.Item;
import com.nickdnepr.core.cluster_builders.KMeansClusterBuilder;

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
        ArrayList<Item> items2 = new ArrayList<>();
        items2.add(new Item(1,1));
        items2.add(new Item(1,2));
        items2.add(new Item(1,3));
        items2.add(new Item(10,1));
        items2.add(new Item(10,2));
        items2.add(new Item(10,3));
        HierarchicalClusterBuilder builder = new HierarchicalClusterBuilder(items2,2);
        System.out.println(builder.getProcessedClusters());
        System.out.println(builder.getProcessedClusters().size());
        System.out.println(HardBassIndexCalculator.calculateIndex(builder.getProcessedClusters()));
//        builder.process();
        KMeansClusterBuilder kMeansClusterBuilder = new KMeansClusterBuilder(items, 6);
//        kMeansClusterBuilder.process();
//        System.out.println(HardBassIndexCalculator.calculateIndex(builder.getProcessedClusters()));
    }
}
