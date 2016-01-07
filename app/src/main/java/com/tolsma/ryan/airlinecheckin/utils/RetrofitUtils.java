package com.tolsma.ryan.airlinecheckin.utils;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by ryantolsma on 12/29/15.
 */
public class RetrofitUtils {


    public static <T> T createRetrofitService(Class<T> clazz, String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl).build();
        return retrofit.create(clazz);
    }

}
