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

import static org.junit.Assert.assertEquals;

/**
 * Created by Emily Li on 20/08/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class StockUpdaterTest {
    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StockUpdater stockUpdater;

    private Stock writtenStock;

    @Before
    public void setup() {
        writtenStock = new Stock("SYM", new BigDecimal("1.1"), 2);
        stockRepository.save(writtenStock);
    }

    @After
    public void tearDown() {
        stockRepository.delete(writtenStock.getSymbol());
    }

    @Test
    public void testStockUpdaterUpdatesStockValue() throws Exception {
        BigDecimal expectedValue = writtenStock.getValue().multiply(new BigDecimal(2));
        stockUpdater.updateStockValue(writtenStock.getSymbol(), expectedValue);
        Stock updatedStock = stockRepository.findOne(writtenStock.getSymbol());
        assertEquals(expectedValue.doubleValue(), updatedStock.getValue().doubleValue(), Double.MIN_VALUE);
    }

    @Test
    public void testStockUpdaterUpdatesStockVolume() throws Exception {
        int expectedVolume = writtenStock.getVolume() * 2;
        stockUpdater.updateStockVolume(writtenStock.getSymbol(), expectedVolume);
        Stock updatedStock = stockRepository.findOne(writtenStock.getSymbol());
        assertEquals(expectedVolume, updatedStock.getVolume());
    }
}
