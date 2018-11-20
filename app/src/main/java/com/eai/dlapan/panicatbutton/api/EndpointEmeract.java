package com.eai.dlapan.panicatbutton.api;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface EndpointEmeract {
    // Weather
    @GET("/api/weather")
    Call<Map<String,Object>> getCurrentWeather();
    @GET("/api/weather")
    Call<Map<String,Object>> getCurrentWeather(@Query("latlon") String latlon);

    // News
//    @GET("/api/news")
//    Call<ArticleResponse> getListNews();
}
