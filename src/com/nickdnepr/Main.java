package com.nickdnepr;

import com.nickdnepr.core.Cluster;
import com.nickdnepr.core.HardBassIndexCalculator;
import com.nickdnepr.core.cluster_builders.HierarchicalClusterBuilder;
import com.nickdnepr.core.Item;
import com.nickdnepr.core.cluster_builders.KMeansClusterBuilder;
import com.nickdnepr.utils.FileParser;

import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {
//        System.out.println("     227.5921                                 111.0161".trim().replaceAll(" +"," "));

        ArrayList<Item> items = FileParser.readFile("file");

        for (Item item: items){
            System.out.println(item.toLongString());
        }

        HierarchicalClusterBuilder builder = new HierarchicalClusterBuilder(items,2);
//        System.out.println(builder.getProcessedClusters());
        for (Cluster cluster: builder.getProcessedClusters()){
            System.out.println(cluster.toString());
        }
        System.out.println(builder.getProcessedClusters().size());
        System.out.println(HardBassIndexCalculator.calculateIndex(builder.getProcessedClusters()));
//        builder.process();
        KMeansClusterBuilder kMeansClusterBuilder = new KMeansClusterBuilder(items, 2);
        for (Cluster cluster: kMeansClusterBuilder.getProcessedClusters()){
            System.out.println(cluster.toString());
        }
//        kMeansClusterBuilder.process();
//        System.out.println(HardBassIndexCalculator.calculateIndex(builder.getProcessedClusters()));
    }
}
