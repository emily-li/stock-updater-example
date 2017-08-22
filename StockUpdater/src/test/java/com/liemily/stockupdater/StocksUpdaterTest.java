package com.liemily.stockupdater;

import com.liemily.stock.Stock;
import com.liemily.stock.StockRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Emily Li on 20/08/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class StocksUpdaterTest {
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
        stockRepository.delete(writtenStock.getSymbol());
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
}
