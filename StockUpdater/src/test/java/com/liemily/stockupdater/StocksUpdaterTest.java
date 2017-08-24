package com.liemily.stockupdater;

import com.liemily.stock.Stock;
import com.liemily.stock.StockRepository;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.invoke.MethodHandles;
import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Created by Emily Li on 20/08/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class StocksUpdaterTest {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StocksUpdater stocksUpdater;

    private Stock writtenStock;

    @Before
    public void setup() {
        writtenStock = new Stock("SYM" + UUID.randomUUID(), new BigDecimal("1.1"), 2);
    }

    @After
    public void tearDown() {
        try {
            stockRepository.delete(writtenStock.getSymbol());
        } catch (EmptyResultDataAccessException e) {
            logger.info("Failed to delete stock " + writtenStock.getSymbol() + " when teraing down the class as it already exists");
        }
    }

    @Test
    public void testStockUpdaterUpdatesStockValue() throws Exception {
        stockRepository.save(writtenStock);
        BigDecimal expectedValue = writtenStock.getValue().multiply(new BigDecimal(2));
        stocksUpdater.updateStockValue(writtenStock.getSymbol(), expectedValue);
        Stock updatedStock = stockRepository.findOne(writtenStock.getSymbol());
        assertEquals(expectedValue.doubleValue(), updatedStock.getValue().doubleValue(), Double.MIN_VALUE);
    }

    @Test
    public void testStockUpdaterUpdatesStockVolume() throws Exception {
        stockRepository.save(writtenStock);
        int expectedVolume = writtenStock.getVolume() * 2;
        stocksUpdater.updateStockVolume(writtenStock.getSymbol(), expectedVolume);
        Stock updatedStock = stockRepository.findOne(writtenStock.getSymbol());
        assertEquals(expectedVolume, updatedStock.getVolume());
    }

    @Test
    public void testRegisterStock() throws Exception {
        stocksUpdater.generateStock(writtenStock.getSymbol());
        assertNotNull(stockRepository.findOne(writtenStock.getSymbol()));
    }

    @Test
    public void testFailRegisterDuplicateStock() throws Exception {
        stocksUpdater.generateStock(writtenStock.getSymbol());
        stocksUpdater.generateStock(writtenStock.getSymbol());
    }


    @Test
    public void testValueModulation() {
        stockRepository.save(writtenStock);
        double modulatedValue = modulateValue();
        assertNotEquals(modulatedValue, writtenStock.getValue());
    }

    @Test
    public void testValueModulationRandomised() {
        stockRepository.save(writtenStock);
        double modulatedValue1 = modulateValue();
        double modulatedValue2 = modulateValue();
        assertNotEquals(modulatedValue1, modulatedValue2);
    }

    private double modulateValue() {
        stocksUpdater.run();
        Stock stock = stockRepository.findOne(writtenStock.getSymbol());
        return stock.getValue().doubleValue();
    }
}
