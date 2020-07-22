package com.dommyg.conservativerss.requests;

import androidx.lifecycle.LiveData;

import com.dommyg.conservativerss.requests.responses.NetworkResponse;
import com.dommyg.conservativerss.requests.responses.RssResponse;

import retrofit2.http.GET;

public interface RssCall {

    @GET("feed/")
    LiveData<NetworkResponse<RssResponse>> getRss();
}
