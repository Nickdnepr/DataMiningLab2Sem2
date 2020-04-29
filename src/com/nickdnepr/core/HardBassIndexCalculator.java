package com.nickdnepr.core;

import java.util.ArrayList;

public class HardBassIndexCalculator {

    public static double calculateIndex(ArrayList<Cluster> clusters) {
        Cluster general = null;
        if (clusters.isEmpty()) {
            return 0.0;
        }
        for (Cluster cluster : clusters) {
            general = general == null ? cluster : general.union(cluster);
        }
        int clusterNum = clusters.size(); // c
        int elementsNum = general.size(); // N
        double avgGenDistance = general.avgSquareDistance(); // d2
        double inGroupDistanceSum = 0.0; // WGSS
        double avgCentresDistance = 0.0; // Ac
        for (Cluster cluster : clusters) {
            inGroupDistanceSum += (cluster.size() - 1) * cluster.avgSquareDistance();
            avgCentresDistance += (cluster.size() - 1) * (avgGenDistance - cluster.avgSquareDistance());
        }
        inGroupDistanceSum /= 2;
        avgCentresDistance /= (elementsNum - clusterNum);
        double betweenGroupsDistanceSum = ((clusterNum - 1) * avgGenDistance + (elementsNum - clusterNum) * avgCentresDistance) / 2;

        return (betweenGroupsDistanceSum * (elementsNum - clusterNum)) / (inGroupDistanceSum * (clusterNum - 1));
    }
}
