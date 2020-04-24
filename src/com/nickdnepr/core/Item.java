package com.nickdnepr.core;

import com.nickdnepr.utils.Pair;

import java.util.HashMap;

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

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
//                ", characteristics=" + characteristics +
                '}';
    }
}
