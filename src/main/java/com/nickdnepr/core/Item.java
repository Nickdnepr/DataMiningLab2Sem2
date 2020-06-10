package com.nickdnepr.core;

import com.nickdnepr.utils.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Item {

    private static long idGen = 1;

    private long id;
    private HashMap<String, Double> characteristics;

    public Item() {
        this.id = idGen++;
        this.characteristics = new HashMap<>();
    }

    public Item(double... values) {
        this();
        for (int i = 0; i < values.length; i++) {
            addCharacteristic("q" + i, values[i]);
        }
    }

    private Item(long id, HashMap<String, Double> characteristics) {
        this.id = id;
        this.characteristics = characteristics;
    }

    public double getDistance(Item item) {
        double calc = 0;
        for (String characteristic : characteristics.keySet()) {
            calc += Math.pow(characteristics.get(characteristic) - item.characteristics.get(characteristic), 2);
        }
        return Math.sqrt(calc);
    }

    public Double getCharacteristic(String characteristic) {
        return characteristics.get(characteristic);
    }

    public void addCharacteristic(String characteristic, Double value) {
        characteristics.put(characteristic, value);
    }

    public Set<String> getCharacteristics() {
        return characteristics.keySet();
    }

    public Item newCharacteristics(ArrayList<String> characteristics) {
        HashMap<String, Double> newCharacteristics = new HashMap<>();
        for (String characteristic: characteristics){
            newCharacteristics.putIfAbsent(characteristic, getCharacteristic(characteristic));
        }
        return new Item(id, newCharacteristics);
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
//                ", characteristics=" + characteristics +
                '}';
    }

    public String toLongString() {
        return "Item{" +
                "id=" + id +
                ", characteristics=" + characteristics +
                '}';
    }
}
