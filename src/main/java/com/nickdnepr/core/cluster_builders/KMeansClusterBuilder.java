package com.nickdnepr.core.cluster_builders;

import com.nickdnepr.core.Cluster;
import com.nickdnepr.core.Item;
import com.nickdnepr.utils.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class KMeansClusterBuilder extends AbstractClusterBuilder{

    private HashMap<Item, Cluster> clusters;

    public KMeansClusterBuilder(ArrayList<Item> items, int clusterNum) {
        super(items, clusterNum);
    }

    @Override
    protected void init() {
        this.clusters = new HashMap<>();
        Item highest = new Item();
        Item lowest = new Item();
        for (Item item : items) {
            for (String characteristic : item.getCharacteristics()) {
                Double value = item.getCharacteristic(characteristic);
                if (highest.getCharacteristic(characteristic) == null || highest.getCharacteristic(characteristic) < value) {
                    highest.addCharacteristic(characteristic, value);
                }
                if (lowest.getCharacteristic(characteristic) == null || lowest.getCharacteristic(characteristic) > value) {
                    lowest.addCharacteristic(characteristic, value);
                }
            }
        }
        for (int i = 0; i < clusterNum; i++) {
            Item centroid = new Item();
            for (String characteristic : items.get(0).getCharacteristics()) {
                Random random = new Random();
                double value = lowest.getCharacteristic(characteristic) + random.nextInt((int) (highest.getCharacteristic(characteristic) - lowest.getCharacteristic(characteristic)));
                centroid.addCharacteristic(characteristic, value);
            }
            clusters.put(centroid, new Cluster());
        }
        System.out.println();
    }

    protected void process() {
        boolean stable = true;
        int iterations = 0;
        do {
            stable = true;
            for (Item item : items) {
                Pair<Double, Item> closestPair = null;
                Cluster containingCluster = null;
                for (Item centroid : clusters.keySet()) {
                    double distance = centroid.getDistance(item);
                    if (closestPair == null || closestPair.getKey() > distance) {
                        closestPair = new Pair<>(distance, centroid);
                    }
                    if (clusters.get(centroid).containsItem(item)) {
                        containingCluster = clusters.get(centroid);
                    }
                }
                Cluster closestCluster = clusters.get(closestPair.getValue());
                if (!closestCluster.containsItem(item)) {
                    if (containingCluster != null) {
                        containingCluster.removeItem(item);
                    }
                    closestCluster.addItem(item);
                    stable = false;
                }
            }
            for (Item centroid : clusters.keySet()) {
                Cluster cluster = clusters.get(centroid);
                Item recalculatedCentroid = cluster.getCentroid();
                if (recalculatedCentroid != null) {
                    for (String characteristic : centroid.getCharacteristics()) {
                        centroid.addCharacteristic(characteristic, recalculatedCentroid.getCharacteristic(characteristic));
                    }
                }
            }
            iterations++;
        } while (!stable && iterations < 25);
        System.out.println(clusters.toString());
    }

    public ArrayList<Cluster> getProcessedClusters() {
        return new ArrayList<>(clusters.values());
    }
}
