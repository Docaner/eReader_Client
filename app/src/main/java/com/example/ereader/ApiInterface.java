package com.example.ereader;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    ////////////////////////register///////////////////////////
    @FormUrlEncoded
    @POST("register")
    Call<Users> performRegister (@Field("login") String name, @Field("password") String password);

    ////////////////////////login///////////////////////////
    @FormUrlEncoded
    @POST("auth")
    Call<Users> performLogin(@Field("login") String name, @Field("password") String password);

    @GET("books")
    Call<List<HttpBook>> getAllBooks();
}
