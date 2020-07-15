package com.dommyg.conservativerss.requests;

import com.dommyg.conservativerss.models.Rss;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RssCall {

    @GET("feed/")
    Call<Rss> getRss();
//    LiveData<List<Article>> getArticles();
}
