package com.dommyg.conservativerss.util;

import androidx.lifecycle.LiveData;

import com.dommyg.conservativerss.requests.responses.NetworkResponse;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;

public class LiveDataCallAdapter<R> implements CallAdapter<R, LiveData<NetworkResponse<R>>> {

    private Type responseType;

    public LiveDataCallAdapter(Type responseType) {
        this.responseType = responseType;
    }

    @Override
    public Type responseType() {
        return responseType;
    }

    @Override
    public LiveData<NetworkResponse<R>> adapt(final Call<R> call) {
        return new LiveData<NetworkResponse<R>>() {
            @Override
            protected void onActive() {
                super.onActive();
                final NetworkResponse networkResponse = new NetworkResponse();
                call.enqueue(new Callback<R>() {
                    @Override
                    public void onResponse(Call<R> call, Response<R> response) {
                        postValue(networkResponse.create(response));
                    }

                    @Override
                    public void onFailure(Call<R> call, Throwable t) {
                        postValue(networkResponse.create(t));
                    }
                });
            }
        };
    }
}
