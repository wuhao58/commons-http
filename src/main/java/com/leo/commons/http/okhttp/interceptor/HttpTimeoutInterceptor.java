package com.leo.commons.http.okhttp.interceptor;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author : LEO
 * @Description : 设置超时拦截器
 * @Date :  2019/8/29
 */
public class HttpTimeoutInterceptor implements Interceptor {

    public static final String VN_TIMEOUT = "VN_TIMEOUT";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        int connectTimeout = chain.connectTimeoutMillis();
        int readTimeout = chain.readTimeoutMillis();
        int writeTimeout = chain.writeTimeoutMillis();

        String timeout = request.header(VN_TIMEOUT);
        if (StringUtils.isNotBlank(timeout)) {
            int times = Integer.valueOf(timeout);
            readTimeout = times;
            writeTimeout = times;
        }
        // 设置超时时间
        return chain.withConnectTimeout(connectTimeout, TimeUnit.MILLISECONDS)
                .withReadTimeout(readTimeout, TimeUnit.MILLISECONDS)
                .withWriteTimeout(writeTimeout, TimeUnit.MILLISECONDS)
                .proceed(request);
    }

}
