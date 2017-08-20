package com.liemily.stockupdater;

/**
 * Created by Emily Li on 20/08/2017.
 */
public interface StockValueGenerator {
    double generateValue(double min, double max);

    int generateVolume(int min, int max);

    double modulateValue(double scaleFactor);
}
