package com.nickdnepr.core.cluster_builders;

import com.nickdnepr.core.Cluster;
import com.nickdnepr.core.Item;

import java.util.ArrayList;

public abstract class AbstractClusterBuilder {

    protected ArrayList<Item> items;
    protected int clusterNum;


    public AbstractClusterBuilder(ArrayList<Item> items, int clusterNum) {
        this.items = items;
        this.clusterNum = clusterNum;
        init();
        process();
    }

    protected abstract void init();

    protected abstract void process();

    public abstract ArrayList<Cluster> getProcessedClusters();

}
