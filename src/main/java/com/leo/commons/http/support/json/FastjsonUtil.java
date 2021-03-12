package com.leo.commons.http.support.json;

import com.alibaba.fastjson.JSON;

/**
 * @author : LEO
 * @Description : fastjson工具类
 * @Date :  2019/8/29
 */
public class FastjsonUtil {

    public static String toJSONString(Object object) {
        return JSON.toJSONString(object);
    }

    public static <T> T parseObject(String text, Class<T> clazz) {
        return JSON.parseObject(text, clazz);
    }

}
