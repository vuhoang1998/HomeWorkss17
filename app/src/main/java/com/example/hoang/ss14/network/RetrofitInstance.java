package com.example.hoang.ss14.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Created by hoang on 4/15/2018.
 */

public class RetrofitInstance {
    private static Retrofit retrofit;
    private static Retrofit retrofitXML;

    public static Retrofit getRetrofitInstance(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://music-api-for-tk.herokuapp.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static Retrofit getRetrofitXMLInstance(){
        if (retrofitXML == null){
            retrofitXML = new Retrofit.Builder()
                    .baseUrl("https://www.nhaccuatui.com/flash/")
                    .addConverterFactory(SimpleXmlConverterFactory.create())
                    .build();
        }
        return retrofitXML;
    }



}
