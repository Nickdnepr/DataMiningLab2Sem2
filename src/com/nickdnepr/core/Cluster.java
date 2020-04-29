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

    public void removeItem(Item item) {
        items.remove(item);
    }

    public boolean containsItem(Item item) {
        return items.contains(item);
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

    public Item getCentroid() {
        if (items.isEmpty()) {
            return null;
        }
        Item centroid = new Item();
        for (Item item : items) {
            for (String characteristic : item.getCharacteristics()) {
                centroid.addCharacteristic(characteristic, (centroid.getCharacteristic(characteristic) != null ? centroid.getCharacteristic(characteristic) : 0.0) + item.getCharacteristic(characteristic));
            }
        }
        for (String characteristic : centroid.getCharacteristics()) {
            centroid.addCharacteristic(characteristic, centroid.getCharacteristic(characteristic) / items.size());
        }
        return centroid;
    }

    public Cluster union(Cluster cluster) {
        Cluster newCluster = new Cluster();
        newCluster.items.addAll(items);
        newCluster.items.addAll(cluster.items);
        return newCluster;
    }

    public int size() {
        return items.size();
    }

    public double avgSquareDistance() {
        double result = 0;
        for (Item outer : items) {
            for (Item inner : items) {
                result += Math.pow(outer.getDistance(inner), 2);
            }
        }
        result /= items.size();
        return result;
    }

    @Override
    public String toString() {
        return "Cluster{" +
                "items=" + items +
                '}';
    }
}
