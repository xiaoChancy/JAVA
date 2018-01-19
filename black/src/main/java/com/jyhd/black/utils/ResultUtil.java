package com.jyhd.black.utils;

import com.jyhd.black.domain.Result;
import com.jyhd.black.enums.ResultEnum;

public class ResultUtil {

    public static Result success()
    {
        return success(null,null);
    }

    public static Result success(ResultEnum resultEnum ,Object object)
    {
        Result result = new Result();
        result.setCode(resultEnum.getCode());
        result.setMsg(resultEnum.getMsg());
        result.setData(object);

        return result;
    }

    public static Result error(Integer integer,String msg)
    {
        Result result = new Result();
        result.setCode(integer);
        result.setMsg(msg);

        return result;
    }

}
