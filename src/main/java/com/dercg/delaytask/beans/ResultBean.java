package com.dercg.delaytask.beans;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ResultBean<T> {
    private boolean isSuccess;
    private String message;
    private T data;

    public ResultBean(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public ResultBean(boolean isSuccess, String message, T data) {
        this.isSuccess = isSuccess;
        this.message = message;
        this.data = data;
    }

    public ResultBean(boolean isSuccess, String message) {
        this.isSuccess = isSuccess;
        this.message = message;
    }

}
