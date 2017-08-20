package com.liemily.stockupdater;

import com.liemily.stock.StockService;

/**
 * Created by Emily Li on 20/08/2017.
 */
public class StockUpdater {
    private StockService stockService;
    private StockValueGenerator stockValueGenerator;

    public StockUpdater(StockService stockService, StockValueGenerator stockValueGenerator) {
        this.stockService = stockService;
        this.stockValueGenerator = stockValueGenerator;
    }

    private void updateStockValue(String stockSymbol, double value) {

    }
}
