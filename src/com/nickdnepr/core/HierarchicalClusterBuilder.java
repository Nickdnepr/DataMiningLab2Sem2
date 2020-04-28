package com.nickdnepr.core;

import com.nickdnepr.utils.Pair;

import java.util.ArrayList;

public class HierarchicalClusterBuilder {

    private ArrayList<Cluster> clusters;

    public HierarchicalClusterBuilder(ArrayList<Item> items) {
        this.clusters = new ArrayList<>();
        for (Item item : items) {
            Cluster cluster = new Cluster();
            cluster.addItem(item);
            clusters.add(cluster);
        }
    }

    public void process() {
        StringBuilder log = new StringBuilder();
        while (clusters.size() > 1) {
            log.append("Clusters: ");
            log.append(clusters.size());
            Pair<Cluster, Cluster> closestPair = getClosestPair();
            Cluster first = closestPair.getKey();
            Cluster second = closestPair.getValue();
            clusters.remove(first);
            clusters.remove(second);
            clusters.add(first.union(second));
            log.append(" ");
            log.append(first.toString());
            log.append(" ");
            log.append(second.toString());
            log.append(" ");
            log.append(first.toString());
            log.append("\n");
        }
        System.out.println(log.toString());
    }

    private Pair<Cluster, Cluster> getClosestPair() {
        Double minDistance = null;
        Pair<Cluster, Cluster> closestPair = new Pair<>();
        for (int y = 0; y < clusters.size(); y++) {
            for (int x = 0; x < clusters.size(); x++) {
                if (x != y) {
                    Cluster first = clusters.get(y);
                    Cluster second = clusters.get(x);
                    double distance = first.getDistance(second);
                    if (minDistance == null || minDistance > distance) {
                        minDistance = distance;
                        closestPair.setKey(first);
                        closestPair.setValue(second);
                    }
                }
            }
        }
        return closestPair;
    }
}
