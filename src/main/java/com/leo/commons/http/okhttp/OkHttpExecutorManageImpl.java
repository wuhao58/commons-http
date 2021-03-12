package com.leo.commons.http.okhttp;

import com.leo.commons.http.HttpExecutorManage;
import com.leo.commons.http.okhttp.interceptor.HttpTimeoutInterceptor;
import com.leo.commons.http.support.HttpMethods;
import com.leo.commons.http.support.json.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.Map;

/**
 * @author : LEO
 * @Description :
 * @Date :  2019/8/29
 */
@Slf4j
public class OkHttpExecutorManageImpl implements HttpExecutorManage {

    private OkHttpClient okHttpClient;

    public OkHttpExecutorManageImpl(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    private static int DEF_TIME_OUT = 1000;


    @Override
    public <T> T doGet(String url, Class<T> returnClass) {
        return doGet(url, null, returnClass);
    }

    @Override
    public <T> T doGet(String url, Map<String, String> headers, Class<T> returnClass) {
        return doGet(url, headers, returnClass, DEF_TIME_OUT);
    }

    @Override
    public <T> T doGet(String url, Map<String, String> headers, Class<T> returnClass, int timeout) {
        try (Response response = doExecute(url, null, headers, HttpMethods.GET, timeout)) {
            if (response.isSuccessful()) {
                String body = response.body().string();
                return JacksonUtil.readValue(body, returnClass);
            }
        } catch (IOException e) {
            log.error("okhttp3 get error >> url={} ", url, e);
        }
        return null;
    }

    @Override
    public String doGet(String url, int timeout) {
        try (Response response = doExecute(url, null, null, HttpMethods.GET, timeout)) {
            if (response.isSuccessful()) {
                String body = response.body().string();
                return body;
            }
        } catch (IOException e) {
            log.error("okhttp3 get error >> url={} ", url, e);
        }
        return null;
    }

    @Override
    public <T> T doPost(String url, Object request, Class<T> returnClass) {
        return doPost(url, request, null, DEF_TIME_OUT, returnClass);
    }

    @Override
    public <T> T doPost(String url, Object request, int timeout, Class<T> returnClass) {
        return doPost(url, request, null, timeout, returnClass);
    }

    @Override
    public <T> T doPost(String url, Object request, Map<String, String> headers, Class<T> returnClass) {
        return doPost(url, request, headers, DEF_TIME_OUT, returnClass);
    }

    @Override
    public <T> T doPost(String url, Object request, Map<String, String> headers, int timeout, Class<T> returnClass) {
        try (Response response = doExecute(url, request, headers, HttpMethods.POST, timeout)) {
            if (response.isSuccessful()) {
                String body = response.body().string();
                return JacksonUtil.readValue(body, returnClass);
            }
        } catch (IOException e) {
            log.error("okhttp3 post error >> url={} ", url, e);
        }
        return null;
    }

    @Override
    public String doPost(String url, Object request) {
        return doPost(url, request, null, DEF_TIME_OUT);
    }

    @Override
    public String doPost(String url, Object request, Map<String, String> headers, int timeout) {
        try (Response response = doExecute(url, request, headers, HttpMethods.POST, timeout)) {
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (IOException e) {
            log.error("okhttp3 post error >> url={} ", url, e);
        }
        return null;
    }

    @Override
    public <T> T doPut(String url, Object request, int timeout, Class<T> returnClass) {
        return doPut(url, request, null, timeout, returnClass);
    }

    @Override
    public <T> T doPut(String url, Object request, Map<String, String> headers, int timeout, Class<T> returnClass) {
        try (Response response = doExecute(url, request, headers, HttpMethods.PUT, timeout)) {
            if (response.isSuccessful()) {
                String body = response.body().string();
                return JacksonUtil.readValue(body, returnClass);
            }
        } catch (IOException e) {
            log.error("okhttp3 put error >> url={} ", url, e);
        }
        return null;
    }

    @Override
    public String doFormPost(String url, Map<String, String> request) {
        return doFormPost(url, request, DEF_TIME_OUT);
    }

    @Override
    public String doFormPost(String url, Map<String, String> request, int timeout) {
        try (Response response = doFormExecute(url, request, null, timeout)) {
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (IOException e) {
            log.error("okhttp3 form post error >> url={} ", url, e);
        }
        return null;
    }

    @Override
    public <T> T doFormPost(String url, Map<String, String> request, Map<String, String> headers, int timeout, Class<T> returnClass) {
        try (Response response = doFormExecute(url, request, headers, timeout)) {
            if (response.isSuccessful()) {
                String body = response.body().string();
                return JacksonUtil.readValue(body, returnClass);
            }
        } catch (IOException e) {
            log.error("okhttp3 form post error >> url={} ", url, e);
        }
        return null;
    }

    private Response doExecute(String url, Object jsonParams, Map<String, String> headers,
                               HttpMethods httpMethods, int timeout) throws IOException {
        Request.Builder builder = new Request.Builder().url(url);
        // 参数处理
        switch (httpMethods) {
            case POST:
                String postJson = JacksonUtil.writeValue(jsonParams);
                RequestBody postRequest = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), postJson);
                builder.post(postRequest);
                break;
            case PUT:
                String putJson = JacksonUtil.writeValue(jsonParams);
                RequestBody putRequest = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), putJson);
                builder.put(putRequest);
                break;
            default:
                break;
        }
        // 添加请求头
        if (!CollectionUtils.isEmpty(headers)) {
            for (String key : headers.keySet()) {
                builder.addHeader(key, headers.get(key));
            }
        }
        // 设置请求超时时间
        String times = String.valueOf(DEF_TIME_OUT);
        if (timeout > 0) {
            times = String.valueOf(timeout);
        }
        builder.addHeader(HttpTimeoutInterceptor.VN_TIMEOUT, times);
        final Request request = builder.build();
        return okHttpClient.newCall(request).execute();
    }

    private Response doFormExecute(String url, Map<String, String> params, Map<String, String> headers, int timeout) throws IOException {
        Request.Builder builder = new Request.Builder().url(url);
        // 参数处理
        FormBody.Builder formBuilder = new FormBody.Builder();
        for (Map.Entry<String, String> m : params.entrySet()) {
            formBuilder.add(m.getKey(), m.getValue());
        }
        RequestBody body = formBuilder.build();
        builder.post(body);

        // 添加请求头
        if (!CollectionUtils.isEmpty(headers)) {
            for (String key : headers.keySet()) {
                builder.addHeader(key, headers.get(key));
            }
        }

        // 设置请求超时时间
        String times = String.valueOf(DEF_TIME_OUT);
        if (timeout > 0) {
            times = String.valueOf(timeout);
        }
        builder.addHeader(HttpTimeoutInterceptor.VN_TIMEOUT, times);
        final Request request = builder.build();
        return okHttpClient.newCall(request).execute();
    }

}
