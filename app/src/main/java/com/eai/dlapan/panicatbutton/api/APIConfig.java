package com.eai.dlapan.panicatbutton.api;

public class APIConfig {
    public static final String API_BASE_URL = "https://emeract-api.herokuapp.com/";
    //public static final String API_BASE_URL = "http://10.140.26.17:8100/";
    public static final String API_SHARED_USER = "API_SHARED_USER";

    public static final EndpointEmeract RETROFIT_SERVICE = RetrofitClient.getRetrofitInstance().create(EndpointEmeract.class);
}
