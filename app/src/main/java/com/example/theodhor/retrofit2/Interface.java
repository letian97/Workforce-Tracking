package com.example.theodhor.retrofit2;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Dori on 12/28/2016.
 */

public interface Interface {

    @FormUrlEncoded
    @POST("/site.php")
    Call<ServerResponse> post(
            @Field("method") String method,
            @Field("name") String name,
            @Field("fromSite") String fromSite,
            @Field("toSite") String toSite,
            @Field("duration") String duration,
            @Field("description") String description


    );



}