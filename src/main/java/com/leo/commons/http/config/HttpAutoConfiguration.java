package com.leo.commons.http.config;

import com.leo.commons.http.HttpExecutorManage;
import com.leo.commons.http.okhttp.OkHttpExecutorManageImpl;
import com.leo.commons.http.okhttp.interceptor.HttpTimeoutInterceptor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

/**
 * @author : LEO
 * @Description :
 * @Date :  2019/8/29
 */
@Slf4j
@Configuration
public class HttpAutoConfiguration {


    @Configuration
    public static class OkHttpConfiguration {

        private static final long DEFAULT_TIMEOUT = 3000;
        private static final long DEFAULT_CONNECTION_TIMEOUT = 2000;

        public OkHttpClient okHttpClient() {
            return new OkHttpClient.Builder()
                    .sslSocketFactory(getSslSocketFactory(), getX509TrustManager())
                    .retryOnConnectionFailure(true)
                    // 连接池
                    .connectionPool(getConnPool())
                    // 默认连接超时时间
                    .connectTimeout(DEFAULT_CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
                    .readTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                    .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                    // 拦截器
                    .addInterceptor(new HttpTimeoutInterceptor())
                    .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                    .build();
        }

        @Bean
        public HttpExecutorManage httpExecutorManage() {
            return new OkHttpExecutorManageImpl(okHttpClient());
        }

        public X509TrustManager getX509TrustManager() {
            return new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                }

                @Override
                public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            };
        }

        public SSLSocketFactory getSslSocketFactory() {
            try {
                // 信任任何链接
                SSLContext sslContext = SSLContext.getInstance("TLS");
                sslContext.init(null, new TrustManager[]{getX509TrustManager()}, new SecureRandom());
                return sslContext.getSocketFactory();
            } catch (KeyManagementException | NoSuchAlgorithmException e) {
                log.error("okhttp3 ssl error >> ", e);
                return null;
            }
        }

        /**
         * Create a new connection pool with tuning parameters appropriate for a single-user application.
         * The tuning parameters in this pool are subject to change in future OkHttp releases. Currently
         */
        public ConnectionPool getConnPool() {
            return new ConnectionPool(200, 900L, TimeUnit.SECONDS);
        }
    }


}
