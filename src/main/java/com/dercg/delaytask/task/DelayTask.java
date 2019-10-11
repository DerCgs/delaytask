package com.dercg.delaytask.task;

import com.dercg.delaytask.common.Utils;
import com.dercg.delaytask.service.delaytask.DelayFactory;
import com.dercg.delaytask.service.delaytask.DelayHandle;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@Slf4j
public class DelayTask implements Runnable {
    private final Thread thread = new Thread(this);
    private volatile boolean runCondition = true;
    public static final String DELAY_TASK_KEY = "delay_task_key";
    private static final int DELAY_SLEEP_ONE_SECOND = 1000;

    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    DelayFactory delayFactory;

    @Override
    public void run() {
        while (runCondition) {
            try {
                // 获取
                Set<ZSetOperations.TypedTuple<String>> item = stringRedisTemplate.opsForZSet().rangeWithScores(DELAY_TASK_KEY, 0, 1);
                if (item == null || item.isEmpty()) {
                    Utils.sleep(DELAY_SLEEP_ONE_SECOND);
                    log.info("no task exec....");
                    continue;
                }

                double scoreTime = ((ZSetOperations.TypedTuple<String>) item.toArray()[0]).getScore();
                String member = ((ZSetOperations.TypedTuple<String>) item.toArray()[0]).getValue();

                double currentTime = System.currentTimeMillis() / 1000;
                log.info("get task from redis：" + member + ",score：" + scoreTime);
                if (currentTime < scoreTime) {
                    Utils.sleep(DELAY_SLEEP_ONE_SECOND);
                    continue;
                }

                Long removeResult = stringRedisTemplate.opsForZSet().remove(DELAY_TASK_KEY, member);
                if (removeResult == null || removeResult <= 0) {
                    continue;
                }

                DelayHandle handle = delayFactory.getHandle(member);
                handle.exec();
            } catch (Exception e) {
                log.error(Throwables.getStackTraceAsString(e));
            }
        }
    }

    public void start() {
        thread.start();
        log.info("delay task start ....");
    }

    public void stop() throws Exception {
        if (runCondition) {
            runCondition = false;
        }
        log.info("delay task ending ....");
        thread.join();
        log.info("delay task end ....");
    }
}
