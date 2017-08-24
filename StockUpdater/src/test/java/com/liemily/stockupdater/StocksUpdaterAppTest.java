package com.liemily.stockupdater;

import com.liemily.stock.Stock;
import com.liemily.stock.StockRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Created by Emily Li on 22/08/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class StocksUpdaterAppTest {
    @Autowired
    private StockRepository stockRepository;

    final String symbol = "SYM" + UUID.randomUUID();

    @After
    public void tearDown() {
        stockRepository.delete(symbol);
    }

    @Test
    public void testStockValueModulatesOverTime() throws Exception {
        BigDecimal initialValue = BigDecimal.valueOf(1.23);

        stockRepository.save(new Stock(symbol, initialValue, 1));
        Thread.sleep(1 * 1000);
        BigDecimal alteredValue = stockRepository.findOne(symbol).getValue();

        Assert.assertNotEquals(alteredValue, initialValue);
    }
}
