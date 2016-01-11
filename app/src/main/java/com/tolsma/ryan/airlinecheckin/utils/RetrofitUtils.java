package com.tolsma.ryan.airlinecheckin.utils;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import java.net.CookieManager;
import java.net.CookiePolicy;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by ryantolsma on 12/29/15.
 */
public class RetrofitUtils {

    private static OkHttpClient ok = new OkHttpClient();
    private static CookieManager cm = new CookieManager();
    public static <T> T createRetrofitService(Class<T> clazz, String baseUrl) {

        cm.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        ok.setCookieHandler(cm);
        ok.setFollowRedirects(true);
        HttpLoggingInterceptor log = new HttpLoggingInterceptor();
        log.setLevel(HttpLoggingInterceptor.Level.BASIC);
        ok.interceptors().add(log);
        Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .client(ok)
                .baseUrl(baseUrl).build();
        return retrofit.create(clazz);
    }

}
