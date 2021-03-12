package com.leo.commons.http;

import java.util.Map;

/**
 * @author : LEO
 * @Description :
 * @Date :  2019/8/29
 */
public interface HttpExecutorManage {

    /**
     * get请求
     * @param url
     * @param returnClass
     * @return T.class
     */
    <T> T doGet(String url, Class<T> returnClass);

    /**
     * get请求
     * @param url
     * @param headers
     * @param returnClass
     * @return T.class
     */
    <T> T doGet(String url, Map<String, String> headers, Class<T> returnClass);

    /**
     * get请求
     * @param url
     * @param headers
     * @param returnClass
     * @param timeout 设置超时时间(毫秒)
     * @return T.class
     */
    <T> T doGet(String url, Map<String, String> headers, Class<T> returnClass, int timeout);

    /**
     * get请求
     * @param url
     * @param timeout 设置超时时间(毫秒)
     * @return String.class
     */
    String doGet(String url, int timeout);

    /**
     * post请求
     * @param url
     * @param request
     * @param returnClass
     * @return T.class
     */
    <T> T doPost(String url, Object request, Class<T> returnClass);

    /**
     * post请求
     * @param url
     * @param request 请求参数(json)
     * @param timeout 设置超时时间(毫秒)
     * @param returnClass
     * @return T.class
     */
    <T> T doPost(String url, Object request, int timeout, Class<T> returnClass);

    /**
     * post请求
     * @param url
     * @param request 请求参数(json)
     * @param headers 请求头
     * @param returnClass
     * @return T.class
     */
    <T> T doPost(String url, Object request, Map<String, String> headers, Class<T> returnClass);

    /**
     * post请求
     * @param url
     * @param request 请求参数(json)
     * @param headers 请求头
     * @param timeout 设置超时时间(毫秒)
     * @param returnClass
     * @return T.class
     */
    <T> T doPost(String url, Object request, Map<String, String> headers, int timeout, Class<T> returnClass);

    /**
     * post请求
     * @param url
     * @param request 请求参数(json)
     * @return String.class
     */
    String doPost(String url, Object request);

    /**
     * post请求
     * @param url
     * @param request 请求参数(json)
     * @param headers 请求头
     * @param timeout 设置超时时间(毫秒)
     * @return String.class
     */
    String doPost(String url, Object request, Map<String, String> headers, int timeout);

    /**
     * put请求
     * @param url
     * @param request 请求参数(json)
     * @param timeout 设置超时时间(毫秒)
     * @param returnClass
     * @return T.class
     */
    <T> T doPut(String url, Object request, int timeout, Class<T> returnClass);

    /**
     * put请求
     * @param url
     * @param request 请求参数(json)
     * @param headers 请求头
     * @param timeout 设置超时时间(毫秒)
     * @param returnClass
     * @return T.class
     */
    <T> T doPut(String url, Object request, Map<String, String> headers, int timeout, Class<T> returnClass);

    /**
     * form post请求
     * @param url
     * @param request 请求参数
     * @return
     */
    String doFormPost(String url, Map<String, String> request);

    /**
     * form post请求
     * @param url
     * @param request 请求参数
     * @param timeout 设置超时时间(毫秒)
     * @return
     */
    String doFormPost(String url, Map<String, String> request, int timeout);

    /**
     * form post请求
     * @param url
     * @param request 请求参数
     * @param headers 请求头
     * @param timeout 设置超时时间(毫秒)
     * @param returnClass
     * @return
     */
    <T> T doFormPost(String url, Map<String, String> request, Map<String, String> headers, int timeout, Class<T> returnClass);
}
