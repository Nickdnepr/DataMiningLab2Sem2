package com.nickdnepr.core.analysis;

import com.nickdnepr.core.Item;
import com.nickdnepr.utils.FTable;
import com.nickdnepr.utils.Pair;

import java.util.*;

public class CharacteristicsFilter {

    // correlation

    public static ArrayList<Item> removeUnnecessaryCharacteristicsCorrelation(ArrayList<Item> items) {
        ArrayList<Item> newItems = new ArrayList<>();

        Pair<ArrayList<String>, double[][]> processingResult = calculateMatrix(items);
        ArrayList<String> original = processingResult.getKey();
        ArrayList<String> processed = new ArrayList<>(original);
        double[][] matrix = processingResult.getValue();
        System.out.println("------------");
        System.out.println("OUT MATRIX");
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                System.out.print(matrix[i][j]);
            }
            System.out.println();
        }
        System.out.println("OUT MATRIX END");
        for (int i = 0; i < original.size(); i++) {
            for (int j = 0; j < original.size(); j++) {
                System.out.println(original.get(i) + " and " + original.get(j) + " correlation: " + matrix[i][j]);
                if (i != j && processed.contains(original.get(i)) && processed.contains(original.get(j))) {
//                    System.out.println(original.get(i) + " and " + original.get(j) + " correlation: " + matrix[i][j]);
                    double correlation = matrix[i][j];
                    if (Math.abs(correlation) > 0.9) {
                        processed.remove(original.get(j));
                        System.out.println("Removing " + original.get(j));
                    }
                }
            }
        }
        for (Item item : items) {
            newItems.add(item.newCharacteristics(processed));
        }

        return newItems;
    }

    public static Pair<ArrayList<String>, double[][]> calculateMatrix(ArrayList<Item> items) {
        HashMap<String, ArrayList<Double>> characteristics = getCharacteristicsFromItems(items);

        double[][] coefficientMatrix = new double[characteristics.size()][characteristics.size()];
        ArrayList<String> orderedCharacteristics = new ArrayList<>(characteristics.keySet());
//        for (String characteristic1 : orderedCharacteristics) {
//            for (String characteristic2 : orderedCharacteristics) {
//                coefficientMatrix[orderedCharacteristics.indexOf(characteristic1)][orderedCharacteristics.indexOf(characteristic1)] = calculateCoefficient(characteristics.get(characteristic1), characteristics.get(characteristic2));
//                System.out.println(characteristic1 + " and " + characteristic2 + " correlation: " + coefficientMatrix[orderedCharacteristics.indexOf(characteristic1)][orderedCharacteristics.indexOf(characteristic1)]);
//            }
//        }


        for (int i = 0; i < orderedCharacteristics.size(); i++) {
            for (int j = 0; j < orderedCharacteristics.size(); j++) {
                double coefficient = calculateCoefficient(characteristics.get(orderedCharacteristics.get(i)), characteristics.get(orderedCharacteristics.get(j)));
                coefficientMatrix[i][j] = coefficient;
                System.out.println(orderedCharacteristics.get(i) + " " + orderedCharacteristics.get(j) + " " + coefficient);
            }
        }
        System.out.println("IN MATRIX");
        for (int i = 0; i < coefficientMatrix.length; i++) {
            for (int j = 0; j < coefficientMatrix.length; j++) {
                System.out.print(coefficientMatrix[i][j]);
            }
            System.out.println();
        }
        System.out.println("IN MATRIX END");
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

    // variance

    public static void calculateFValues(ArrayList<Item> items) {
        HashMap<String, ArrayList<Double>> characteristics = getCharacteristicsFromItems(items);
        HashMap<String, Double> means = new HashMap<>();
        ArrayList<Double> allValues = new ArrayList<>();
        for (String ch : characteristics.keySet()) {
            means.put(ch, getMean(characteristics.get(ch)));
            allValues.addAll(characteristics.get(ch));
        }
        double totalMean = getMean(allValues);
        double betweenGroupSquareSum = calculateSquareSum(new ArrayList<>(means.values()), totalMean) * items.size();
        int dfb = characteristics.size() - 1;
//        HashMap<String, Double> fValues = new HashMap<>();
        for (String ch : characteristics.keySet()) {
            double withinGroupSquareSum = calculateSquareSum(characteristics.get(ch));
            int dfw = (items.size() - 1) * characteristics.size();
            double f = (betweenGroupSquareSum / dfb) / (withinGroupSquareSum / dfw);
            if (withinGroupSquareSum == 0) {
                f = 0;
            }
            if (f > FTable.getValue(dfb, dfw)) {
                System.out.println(ch + " is relevant");
            } else {
                System.out.println(ch + " is irrelevant");
            }
        }
        return;
    }

    private static double calculateSquareSum(ArrayList<Double> values) {
        return calculateSquareSum(values, getMean(values));
    }

    private static double calculateSquareSum(ArrayList<Double> values, double mean) {
        double result = 0;
        for (double value : values) {
            result += Math.pow(value - mean, 2);
        }
        return result;
    }

    // common
    private static HashMap<String, ArrayList<Double>> getCharacteristicsFromItems(ArrayList<Item> items) {
        HashMap<String, ArrayList<Double>> characteristics = new LinkedHashMap<>();
        for (Item item : items) {
            for (String characteristic : item.getCharacteristics()) {
                characteristics.putIfAbsent(characteristic, new ArrayList<>());
                characteristics.get(characteristic).add(item.getCharacteristic(characteristic));
            }
        }
        return characteristics;
    }

    private static double getMean(ArrayList<Double> values) {
        double sum = 0;
        for (Double d : values) {
            sum += d;
        }
        return sum / values.size();
    }
}
