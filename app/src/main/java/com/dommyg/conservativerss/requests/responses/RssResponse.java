package com.dommyg.conservativerss.requests.responses;

import java.io.IOException;

import retrofit2.Response;

/**
 * A generic class which reads raw responses from Retrofit and assigns it one of three possible
 * statuses: success, error, or empty.
 */
public class RssResponse<T> {

    public RssResponse<T> create(Throwable error) {
        return new RssErrorResponse<>(!error.getMessage().equals("") ?
                error.getMessage() : "Unknown error\nCheck network connection");
    }

    public RssResponse<T> create(Response<T> response) {
        if (response.isSuccessful()) {
            T body = response.body();

            if (body == null || response.code() == 204) {
                return new RssEmptyResponse<>();
            } else {
                return new RssSuccessResponse<>(body);
            }
        } else {
            String errorMessage = "";
            try {
                errorMessage = response.errorBody().string();
            } catch (IOException e) {
                e.printStackTrace();
                errorMessage = response.message();
            }
            return new RssErrorResponse<>(errorMessage);
        }
    }

    /**
     * Used for handling responses which were successful.
     */
    public class RssSuccessResponse<T> extends RssResponse<T> {
        private T body;

        RssSuccessResponse(T body) {
            this.body = body;
        }

        public T getBody() {
            return body;
        }
    }

    /**
     * Used for handling responses which resulted in an error.
     */
    public class RssErrorResponse<T> extends RssResponse<T> {
        private String errorMessage;

        RssErrorResponse(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public String getErrorMessage() {
            return errorMessage;
        }
    }

    /**
     * Used for handling empty responses.
     */
    public class RssEmptyResponse<T> extends RssResponse<T> {

    }
}
