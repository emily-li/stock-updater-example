package com.liemily.stockupdater;

import com.liemily.stock.Stock;
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
public class StocksUpdater {
    private StockService stockService;
    private StockGenerator stockGenerator;

    @Autowired
    public StocksUpdater(StockService stockService, StockGenerator stockGenerator) {
        this.stockService = stockService;
        this.stockGenerator = stockGenerator;
    }

    void updateStockValue(String stockSymbol, BigDecimal value) throws InvalidStockException {
        stockService.updateValue(stockSymbol, value);
    }

    void updateStockVolume(String stockSymbol, int volume) throws InvalidStockException {
        stockService.updateVolume(stockSymbol, volume);
    }

    void generateStock(String stockSymbol) {
        Stock stock = stockGenerator.generateStock(stockSymbol);
        stockService.save(stock);
    }
}
