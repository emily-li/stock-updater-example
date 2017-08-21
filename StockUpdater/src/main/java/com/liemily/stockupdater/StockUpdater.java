package com.liemily.stockupdater;

import com.liemily.stock.StockService;
import com.liemily.stock.exceptions.InvalidStockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Created by Emily Li on 20/08/2017.
 */
@Component
@Lazy
public class StockUpdater {
    private StockService stockService;

    @Autowired
    public StockUpdater(StockService stockService) {
        this.stockService = stockService;
    }

    void updateStockValue(String stockSymbol, BigDecimal value) throws InvalidStockException {
        stockService.updateValue(stockSymbol, value);
    }

    void updateStockVolume(String stockSymbol, int volume) throws InvalidStockException {
        stockService.updateVolume(stockSymbol, volume);
    }
}
