package com.leo.commons.http.support.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : LEO
 * @Description : jackson工具类
 * @Date :  2019/8/29
 */
@Slf4j
public class JacksonUtil {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        // date 转换为timestamp
        OBJECT_MAPPER.configure(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS, true);
        // 忽略未匹配属性处理
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 忽略pojo 空属性
        OBJECT_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 非空才序列化
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    /**
     * readValue
     *
     * @param jsonStr
     * @param valueType
     * @param <T>
     * @return
     */
    public static <T> T readValue(String jsonStr, Class<T> valueType) {
        try {
            return OBJECT_MAPPER.readValue(jsonStr, valueType);
        } catch (Exception e) {
            log.error("JacksonUtil#readValue: {}", jsonStr, e);
            return null;
        }
    }

    /**
     * readValue
     *
     * @param jsonStr
     * @param valueTypeRef
     * @param <T>
     * @return
     */
    public static <T> T readValue(String jsonStr, TypeReference<T> valueTypeRef) {
        try {
            return OBJECT_MAPPER.readValue(jsonStr, valueTypeRef);
        } catch (Exception e) {
            log.error("JacksonUtil#readValue: {}", jsonStr, e);
            return null;
        }
    }

    /**
     * readList
     *
     * @param jsonStr
     * @param valueType
     * @param <T>
     * @return
     */
    public static <T> List<T> readList(String jsonStr, Class<T> valueType) {
        try {
            JavaType javaType = OBJECT_MAPPER.getTypeFactory().constructParametricType(ArrayList.class, valueType);
            return OBJECT_MAPPER.readValue(jsonStr, javaType);
        } catch (Exception e) {
            log.error("JacksonUtil#readList: {}", jsonStr, e);
            return null;
        }
    }

    /**
     * writeValue
     *
     * @param object
     * @return
     */
    public static String writeValue(Object object) {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (Exception e) {
            log.error("JacksonUtil#writeValue: {}", object == null ? null : object.toString(), e);
            return null;
        }
    }
}
