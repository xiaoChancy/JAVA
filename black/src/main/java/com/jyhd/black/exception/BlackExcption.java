package com.jyhd.black.exception;


import com.jyhd.black.enums.ResultEnum;

public class BlackExcption extends RuntimeException{

    private Integer code;

    public BlackExcption(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
