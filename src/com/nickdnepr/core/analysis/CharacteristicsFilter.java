package com.nickdnepr.core.analysis;

import com.nickdnepr.core.Item;
import com.nickdnepr.utils.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class CharacteristicsFilter {

    public static ArrayList<Item> removeUnnecessaryCharacteristics(ArrayList<Item> items) {
        Pair<ArrayList<String>, double[][]> processingResult = calculateMatrix(items);
        ArrayList<String> original = processingResult.getKey();
        ArrayList<String> processed = new ArrayList<>(original);
        double[][] matrix = processingResult.getValue();
        for (int i = 0; i < original.size(); i++) {
            for (int j = 0; j < original.size(); j++) {
                if (i != j) {
                    double correlation = matrix[i][j];
                    if (Math.abs(correlation) > 0.9) {

                    }
                }
            }
        }
        return null;
    }

    public static Pair<ArrayList<String>, double[][]> calculateMatrix(ArrayList<Item> items) {
        HashMap<String, ArrayList<Double>> characteristics = new LinkedHashMap<>();
        for (Item item : items) {
            for (String characteristic : item.getCharacteristics()) {
                characteristics.putIfAbsent(characteristic, new ArrayList<>());
                characteristics.get(characteristic).add(item.getCharacteristic(characteristic));
            }
        }
        double[][] coefficientMatrix = new double[items.size()][items.size()];
        ArrayList<String> orderedCharacteristics = new ArrayList<>(characteristics.keySet());
        for (String characteristic1 : orderedCharacteristics) {
            for (String characteristic2 : orderedCharacteristics) {
                coefficientMatrix[orderedCharacteristics.indexOf(characteristic1)][orderedCharacteristics.indexOf(characteristic1)] = calculateCoefficient(characteristics.get(characteristic1), characteristics.get(characteristic2));
                System.out.println(characteristic1 + " and " + characteristic2 + " correlation: " + coefficientMatrix[orderedCharacteristics.indexOf(characteristic1)][orderedCharacteristics.indexOf(characteristic1)]);
            }
        }
        return new Pair<>(orderedCharacteristics, coefficientMatrix);
    }

    public static double calculateCoefficient(ArrayList<Double> first, ArrayList<Double> second) {
        if (first.size() != second.size()) {
            throw new IllegalStateException("Not equal size");
        }
        int size = first.size();
        double avgFirst = 0.0;
        double avgSecond = 0.0;
        for (int i = 0; i < size; i++) {
            avgFirst += first.get(i);
            avgSecond += second.get(i);
        }
        avgFirst /= size;
        avgSecond /= size;
        double numerator = 0.0;
        double denominatorFirstSum = 0.0;
        double denominatorSecondSum = 0.0;
        double denominator = 0.0;
        for (int i = 0; i < size; i++) {
            numerator += (first.get(i) - avgFirst) * (second.get(i) - avgSecond);
            denominatorFirstSum += Math.pow(first.get(i) - avgFirst, 2);
            denominatorSecondSum += Math.pow(second.get(i) - avgSecond, 2);
        }
        denominator = Math.sqrt(denominatorFirstSum * denominatorSecondSum);
        return denominator == 0 ? 0.0 : numerator / denominator;
    }
}
