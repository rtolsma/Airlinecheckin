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

    //TODO test on confirmation numbers with different numbers of passengers
    @POST("/flight/selectPrintDocument.html")
    Call<ResponseBody> sendCheckinButton(
            @Field("checkinPassengers[0].selected") String selected, //Should be true or false, for checkin
            @Field("printDocuments") String checkin //Should always be "Check In"
    );

    @POST("/flight/selectCheckinDocDelivery.html")
    Call<ResponseBody> sendEmailDelivery(
            @Field("selectedOption") String selectedOption,  //should be optionEmail
            @Field("emailAddress") String emailAddress,
            @Field("book_now") String bookNow //should be blank?

    );

    @POST("/flight/selectCheckinDocDelivery.html")
    Call<ResponseBody> sendTextMessageDelivery(
            @Field("selectedOption") String selectedOption, //should be optionText
            @Field("phoneArea") String phoneArea,
            @Field("phonePrefix") String phonePrefix,
            @Field("phoneNumber") String phoneNumber,
            @Field("book_now") String bookNow //should be blank?

    );
}
