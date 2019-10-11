package com.dercg.delaytask.service.delaytask.impl;

import com.dercg.delaytask.beans.ResultBean;
import com.dercg.delaytask.service.delaytask.BaseDelayHandle;

/**
 * task handle : handle1
 */
public class DelayHandle1 extends BaseDelayHandle {
    public static final String BUSINESS_FLAG = "flag_handle1";

    public DelayHandle1(String key) {
        super(key, DEFAULT_DELIMITER);
        super.business_flag = BUSINESS_FLAG;
    }

    @Override
    public ResultBean exec() {
        return null;
    }
}
