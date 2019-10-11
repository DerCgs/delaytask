package com.dercg.delaytask.service.delaytask;

import com.dercg.delaytask.service.delaytask.impl.DelayHandle1;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * delay factory
 */
@Component
public class DelayFactory {

    /**
     * get DelayHandle1
     *
     * @param key
     * @return
     */
    public DelayHandle1 getHandle(String key) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        String businessFlag = BaseDelayHandle.getBusinessFlag(key, BaseDelayHandle.DEFAULT_DELIMITER);
        switch (businessFlag) {
            case DelayHandle1.BUSINESS_FLAG:
                return new DelayHandle1(key);
            default:
                return null;
        }
    }
}
