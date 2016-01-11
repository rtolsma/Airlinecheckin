package com.tolsma.ryan.airlinecheckin.services.retrofit;

import com.squareup.okhttp.ResponseBody;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by ryan on 1/4/16.
 */
public interface SouthwestAPI {
    @FormUrlEncoded

    @POST("/flight/retrieveCheckinDoc.html")
    Call<ResponseBody> sendLogin(@Field("confirmationNumber") String confirmationNumber,
                                 @Field("firstName") String firstName,
                                 @Field("lastName") String lastName,
                                 @Field("submitButton") String button);

}
