package com.nickdnepr.core;

import java.util.ArrayList;

public class Cluster {

    private ArrayList<Item> items;

    public Cluster() {
        this.items = new ArrayList<>();
    }


    public void addItem(Item item) {
        if (!items.contains(item)) {
            items.add(item);
        }
    }

    public double getDistance(Cluster cluster) {
        int pairs = 0;
        double distanceSum = 0;
        for (Item internal : items) {
            for (Item external : cluster.items) {
                pairs++;
                distanceSum += internal.getDistance(external);
            }
        }
        return distanceSum / pairs;
    }

    public Cluster union(Cluster cluster) {
        Cluster newCluster = new Cluster();
        newCluster.items.addAll(items);
        newCluster.items.addAll(cluster.items);
        return newCluster;
    }

    @Override
    public String toString() {
        return "Cluster{" +
                "items=" + items +
                '}';
    }
}
