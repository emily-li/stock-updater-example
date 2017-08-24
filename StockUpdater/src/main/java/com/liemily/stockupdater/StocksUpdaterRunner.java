package com.liemily.stockupdater;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.invoke.MethodHandles;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Emily Li on 23/08/2017.
 */
@Component
public class StocksUpdaterRunner {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private ScheduledExecutorService scheduledExecutorService;
    private StocksUpdater stocksUpdater;
    private int updateRateMs;

    @Autowired
    public StocksUpdaterRunner(ScheduledExecutorService scheduledExecutorService,
                               StocksUpdater stocksUpdater,
                               @Value("${stockupdater.modulation.rateMs}") int updateRateMs) {
        this.scheduledExecutorService = scheduledExecutorService;
        this.stocksUpdater = stocksUpdater;
        this.updateRateMs = updateRateMs;
    }

    @PostConstruct
    void run() {
        logger.info("Running " + getClass().getSimpleName() + " at a rate of " + updateRateMs + "ms");
        scheduledExecutorService.scheduleAtFixedRate(stocksUpdater, 0, updateRateMs, TimeUnit.MILLISECONDS);
    }
}
