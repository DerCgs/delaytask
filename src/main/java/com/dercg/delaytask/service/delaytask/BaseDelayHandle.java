package com.dercg.delaytask.service.delaytask;

public abstract class BaseDelayHandle implements DelayHandle {
    // default key separator
    public static final String DEFAULT_DELIMITER = ":";
    // task flag
    protected String flag;
    // task params
    protected String[] key_params;
    // business flag
    protected String business_flag;

    public BaseDelayHandle(String flag, String delimiter) {
        this.flag = flag;
        this.key_params = flag.split(delimiter);
    }

    public static String getBusinessFlag(String key, String delimiter) {
        return key.split(delimiter)[0];
    }
}
