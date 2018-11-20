package com.eai.dlapan.panicatbutton.sevices;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.eai.dlapan.panicatbutton.api.EndpointEmeract;
import com.eai.dlapan.panicatbutton.api.RetrofitClient;
import com.eai.dlapan.panicatbutton.domain.NewsModel.News;
import com.eai.dlapan.panicatbutton.ext.NewsRecycler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsService {
    private final String NEWS_DATA_RESPONSE = "NEWS_DATA_RESPONSE";
    private EndpointEmeract service;
    private List<News> list;

    public NewsService() {
        this.service = RetrofitClient.getRetrofitInstance().create(EndpointEmeract.class);
        this.list=new ArrayList<>();
    }

    public void load(final RecyclerView ele){
//        Call<Map<String, Object>> call = service.getListNews();
//        call.enqueue(new Callback<Map<String, Object>>() {
//            @Override
//            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
//                Log.d(NEWS_DATA_RESPONSE, "Response "+response.body().toString());
//                list=new ArrayList<>(((Map)response.body().get("articles")).values());
//                NewsRecycler adapter = new NewsRecycler(list);
//                ele.setAdapter(adapter);
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
//                Log.d(NEWS_DATA_RESPONSE, "Failed! Cause "+t.getMessage());
//            }
//        });
    }

    public List<News> getList(RecyclerView ele){
//        load();
        return this.list;
    }
}
