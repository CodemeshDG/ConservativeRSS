package com.dommyg.conservativerss.util;

import androidx.lifecycle.LiveData;

import com.dommyg.conservativerss.requests.responses.NetworkResponse;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import retrofit2.CallAdapter;
import retrofit2.Retrofit;

public class LiveDataCallAdapterFactory extends CallAdapter.Factory {

    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        if (CallAdapter.Factory.getRawType(returnType) != LiveData.class) {
            // Call adapter is not returning a type of LiveData.
            return null;
        }

        // Look inside of returnType and see what type exists. (Getting T of LiveData<T>)
        Type observableType = CallAdapter.Factory.getParameterUpperBound(
                0, (ParameterizedType) returnType);

        Type rawObservableType = CallAdapter.Factory.getRawType(observableType);
        if (rawObservableType != NetworkResponse.class) {
            // The type of LiveData is not of NetworkResponse class.
            throw new IllegalArgumentException("Type must be a defined resource.");
        }

        if (!(observableType instanceof ParameterizedType)) {
            // NetworkResponse is not parameterized. (Should wrap around RssResponse).
            throw new IllegalArgumentException("Resource must be parameterized.");
        }

        Type bodyType = CallAdapter.Factory.getParameterUpperBound(0, (ParameterizedType) observableType);
        return  new LiveDataCallAdapter<Type>(bodyType);
    }
}
