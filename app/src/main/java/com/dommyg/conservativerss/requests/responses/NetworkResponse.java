package com.dommyg.conservativerss.requests.responses;

import java.io.IOException;

import retrofit2.Response;

/**
 * A generic class which reads raw responses from Retrofit and assigns it one of three possible
 * statuses: success, error, or empty.
 */
public class NetworkResponse<T> {

    public NetworkResponse<T> create(Throwable error) {
        return new NetworkErrorResponse<>(!error.getMessage().equals("") ?
                error.getMessage() : "Unknown error\nCheck network connection");
    }

    public NetworkResponse<T> create(Response<T> response) {
        if (response.isSuccessful()) {
            T body = response.body();

            if (body == null || response.code() == 204) {
                return new NetworkEmptyResponse<>();
            } else {
                return new NetworkSuccessResponse<>(body);
            }
        } else {
            String errorMessage = "";
            try {
                errorMessage = response.errorBody().string();
            } catch (IOException e) {
                e.printStackTrace();
                errorMessage = response.message();
            }
            return new NetworkErrorResponse<>(errorMessage);
        }
    }

    /**
     * Used for handling responses which were successful.
     */
    public class NetworkSuccessResponse<T> extends NetworkResponse<T> {
        private T body;

        NetworkSuccessResponse(T body) {
            this.body = body;
        }

        public T getBody() {
            return body;
        }
    }

    /**
     * Used for handling responses which resulted in an error.
     */
    public class NetworkErrorResponse<T> extends NetworkResponse<T> {
        private String errorMessage;

        NetworkErrorResponse(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public String getErrorMessage() {
            return errorMessage;
        }
    }

    /**
     * Used for handling empty responses.
     */
    public class NetworkEmptyResponse<T> extends NetworkResponse<T> {

    }
}
