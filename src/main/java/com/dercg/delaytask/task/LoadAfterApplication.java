package com.dercg.delaytask.task;

import com.google.common.base.Throwables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 * run task after application running
 */
@Component
public class LoadAfterApplication implements CommandLineRunner {
    private static Logger logger = LoggerFactory.getLogger(LoadAfterApplication.class);
    @Autowired
    DelayTask delayTask;

    @Override
    public void run(String... args) throws Exception {
        delayTask.start();
    }

    @PreDestroy
    public void destroyReceiver() {
        try {
            delayTask.stop();
        } catch (Exception e) {
            logger.error(Throwables.getStackTraceAsString(e));
        }
    }
}
