package com.liemily.stockupdater;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * Created by Emily Li on 20/08/2017.
 */
@Component
@Lazy
public class RandomStockValueGenerator implements StockValueGenerator {
    private Random random;

    @Autowired
    public RandomStockValueGenerator(Random random) {
        this.random = random;
    }

    @Override
    public double generateValue(double min, double max) {
        return 0;
    }

    @Override
    public int generateVolume(int min, int max) {
        return 0;
    }

    @Override
    public double modulateValue(double scaleFactor) {
        return 0;
    }
}
