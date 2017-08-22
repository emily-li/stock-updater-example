package com.liemily.stockupdater;

import com.liemily.stock.Stock;

/**
 * Created by Emily Li on 20/08/2017.
 */
public interface StockGenerator {
    Stock generateStock(String stockSymbol);

    double generateValue(double min, double max);

    int generateVolume(int min, int max);

    double modulateValue(double initialValue);
}
