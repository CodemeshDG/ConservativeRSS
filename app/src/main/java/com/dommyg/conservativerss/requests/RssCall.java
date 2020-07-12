package com.dommyg.conservativerss.requests;

import androidx.lifecycle.LiveData;

import com.dommyg.conservativerss.models.Article;
import com.dommyg.conservativerss.models.Channel;
import com.dommyg.conservativerss.models.Rss;
import com.dommyg.conservativerss.requests.responses.RssResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RssCall {

    @GET("RSS/")
    Call<Rss> getRss();
//    LiveData<List<Article>> getArticles();
}
