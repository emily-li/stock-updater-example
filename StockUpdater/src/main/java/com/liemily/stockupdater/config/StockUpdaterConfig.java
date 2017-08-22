package com.liemily.stockupdater.config;

import com.liemily.stock.config.StockConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.Random;

/**
 * Created by Emily Li on 21/08/2017.
 */
@Configuration
@Import(StockConfig.class)
public class StockUpdaterConfig {
    @Bean
    Random random() {
        return new Random();
    }
}
