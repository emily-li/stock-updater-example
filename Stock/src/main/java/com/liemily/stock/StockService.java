package com.liemily.stock;

import com.liemily.stock.exceptions.InsufficientStockException;
import com.liemily.stock.exceptions.InvalidStockException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

/**
 * Created by Emily Li on 23/07/2017.
 */
@Service
@Lazy
public class StockService {
    private static final Logger logger = LogManager.getLogger(StockService.class);

    private StockRepository stockRepository;

    @Autowired
    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Transactional
    public void remove(String stockSymbol, int volume) throws InsufficientStockException {
        boolean success = stockRepository.remove(stockSymbol, volume) > 0;
        if (!success) {
            String error = "Failed to withdraw " + volume + " " + stockSymbol + " stocks due to insufficient volume";
            logger.error(error);
            throw new InsufficientStockException(error);
        }
    }

    @Transactional
    public void add(String stockSymbol, int volume) throws InvalidStockException {
        boolean success = stockRepository.add(stockSymbol, volume) > 0;
        if (!success) {
            String error = "Failed to add stock for stock symbol " + stockSymbol + " as no stocks present with stock symbol " + stockSymbol;
            logger.error(error);
            throw new InvalidStockException(error);
        }
    }

    @Transactional
    public void updateValue(String stockSymbol, BigDecimal value) throws InvalidStockException {
        boolean success = stockRepository.updateValue(stockSymbol, value) > 0;
        if (!success) {
            String error = "Failed to update stock for stock symbol " + stockSymbol + " as no stocks present with stock symbol " + stockSymbol;
            logger.error(error);
            throw new InvalidStockException(error);
        }
    }

    @Transactional
    public void save(Stock stock) {
        stockRepository.save(stock);
    }

    @Transactional
    public void save(Iterable<Stock> stocks) {
        stockRepository.save(stocks);
    }

    @Transactional
    public void updateVolume(String stockSymbol, int volume) throws InvalidStockException {
        boolean success = stockRepository.updateVolume(stockSymbol, volume) > 0;
        if (!success) {
            String error = "Failed to update stock for stock symbol " + stockSymbol + " as no stocks present with stock symbol " + stockSymbol;
            logger.error(error);
            throw new InvalidStockException(error);
        }
    }

    @Transactional(readOnly = true)
    public Stock getStock(String stockSymbol) {
        return stockRepository.findOne(stockSymbol);
    }

    @Transactional(readOnly = true)
    public List<Stock> getStocks() {
        return stockRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Collection<Stock> getStocks(Sort sort) {
        return stockRepository.findAll(sort);
    }
}
