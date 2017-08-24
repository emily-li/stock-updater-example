package com.liemily.stockupdater;

import com.liemily.stock.Stock;
import com.liemily.stock.StockService;
import com.liemily.stock.exceptions.InvalidStockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Emily Li on 20/08/2017.
 */
@Component
@Lazy
public class StocksUpdater implements Runnable {
    private StockService stockService;
    private StockGenerator stockGenerator;
    private double scaleFactorMin;
    private double scaleFactorMax;

    @Autowired
    public StocksUpdater(StockService stockService,
                         StockGenerator stockGenerator,
                         @Value("${stockupdater.modulation.scalefactor.min}") double scaleFactorMin,
                         @Value("${stockupdater.modulation.scalefactor.max}") double scaleFactorMax) {
        this.stockService = stockService;
        this.stockGenerator = stockGenerator;
        this.scaleFactorMin = scaleFactorMin;
        this.scaleFactorMax = scaleFactorMax;
    }

    @Override
    public void run() {
        modulateStocks();
    }

    private void modulateStocks() {
        Collection<Stock> stocks = stockService.getStocks();
        for (Stock stock : stocks) {
            double newValue = modulateValue(stock.getValue().doubleValue());
            stock.setValue(BigDecimal.valueOf(newValue));
        }
        stockService.save(stocks);
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

    private double modulateValue(double initialValue) {
        return initialValue * scaleFactor();
    }

    private double scaleFactor() {
        return ThreadLocalRandom.current().nextDouble(scaleFactorMin, scaleFactorMax);
    }
}
