package com.dommyg.conservativerss.util;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.dommyg.conservativerss.requests.responses.NetworkResponse;

/**
 * Generic class for handling network requests.
 * @param <CacheObject> Type for the Resource data. (database cache)
 * @param <RequestObject> Type for the API response. (network request)
 */
public abstract class NetworkBoundResource<CacheObject, RequestObject> {

    /**
     * Needed for work on background thread as well as posting onto the main thread.
     */
    private AppExecutors appExecutors;

    /**
     * Observes and retrieves from the cache. Follows the single source of truth principle; this
     * data (and only this data) will be observed in the UI for the user to see.
     */
    private MediatorLiveData<Resource<CacheObject>> results = new MediatorLiveData<>();

    public NetworkBoundResource(AppExecutors appExecutors) {
        this.appExecutors = appExecutors;
        init();
    }

    /**
     * Observes the local database and checks if the network needs to be queried. If it does, then
     * fetches data from the network.
     */
    private void init() {
        // Update LiveData to loading status.
        results.setValue((Resource<CacheObject>) Resource.loading(null));

        // Observe LiveData source from local database.
        final LiveData<CacheObject> dbSource = loadFromDb();

        results.addSource(dbSource, new Observer<CacheObject>() {
            @Override
            public void onChanged(CacheObject cacheObject) {
                // Data exists and was loaded from the database. Remove this data source and
                // determine if a refresh is required.
                results.removeSource(dbSource);

                if (shouldFetch(cacheObject)) {
                    // Refresh required; get refreshed data from the network.
                    fetchFromNetwork(dbSource);
                } else {
                    results.addSource(dbSource, new Observer<CacheObject>() {
                        @Override
                        public void onChanged(CacheObject cacheObject) {
                            // Refresh not required; use current data in the database. Mark as
                            // success and send to UI.
                            setValue(Resource.success(cacheObject));
                        }
                    });
                }
            }
        });
    }

    /**
     * Retrieves data from the network and handles the possible cases (success, empty, error).
     */
    private void fetchFromNetwork(final LiveData<CacheObject> dbSource) {
        // Update LiveData to loading status.
        results.addSource(dbSource, new Observer<CacheObject>() {
            @Override
            public void onChanged(CacheObject cacheObject) {
                setValue(Resource.loading(cacheObject));
            }
        });

        // Get the LiveData object from the network request.
        final LiveData<NetworkResponse<RequestObject>> rssResponse = createCall();

        results.addSource(rssResponse, new Observer<NetworkResponse<RequestObject>>() {
            @Override
            public void onChanged(final NetworkResponse<RequestObject> requestObjectNetworkResponse) {
                // Remove the data sources so that, depending on the result, the data can be
                // properly handled.
                results.removeSource(dbSource);
                results.removeSource(rssResponse);

                if (requestObjectNetworkResponse instanceof NetworkResponse.NetworkSuccessResponse) {
                    // Response was successful.
                    appExecutors.getDiskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            // Save the response to the local database using the background thread.
                            saveCallResult(
                                    (RequestObject) processResponse(
                                    (NetworkResponse.NetworkSuccessResponse) requestObjectNetworkResponse)
                            );

                            appExecutors.getMainThreadExecutor().execute(new Runnable() {
                                @Override
                                public void run() {
                                    results.addSource(loadFromDb(), new Observer<CacheObject>() {
                                        @Override
                                        public void onChanged(CacheObject cacheObject) {
                                            // Immediately set results to main thread.
                                            setValue(Resource.success(cacheObject));
                                        }
                                    });
                                }
                            });
                        }
                    });
                } else if (requestObjectNetworkResponse instanceof NetworkResponse.NetworkEmptyResponse) {
                    // Response was empty.
                    appExecutors.getMainThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            // Use the cache data because nothing was retrieved from the network.
                            results.addSource(loadFromDb(), new Observer<CacheObject>() {
                                @Override
                                public void onChanged(CacheObject cacheObject) {
                                    setValue(Resource.success(cacheObject));
                                }
                            });
                        }
                    });
                } else if (requestObjectNetworkResponse instanceof NetworkResponse.NetworkErrorResponse) {
                    // Response resulted in an error.
                    results.addSource(dbSource, new Observer<CacheObject>() {
                        @Override
                        public void onChanged(CacheObject cacheObject) {
                            // Use the cache data and pass the error message.
                            setValue(Resource.error(
                                    ((NetworkResponse.NetworkErrorResponse) requestObjectNetworkResponse)
                                            .getErrorMessage(), cacheObject
                                    )
                            );
                        }
                    });
                }
            }
        });
    }

    /**
     * Retrieves the body (JSON) from the network response.
     */
    private CacheObject processResponse(NetworkResponse.NetworkSuccessResponse response) {
        return (CacheObject) response.getBody();
    }

    private void setValue(Resource<CacheObject> newValue) {
        if (results.getValue() != newValue) {
            results.setValue(newValue);
        }
    }

    // Called to save the result of the API response into the database.
    @WorkerThread
    protected abstract void saveCallResult(@NonNull RequestObject item);

    // Called with the data in the database to decide whether to fetch potentially updated data from
    // the network.
    @MainThread
    protected abstract boolean shouldFetch(@Nullable CacheObject data);

    // Called to get the cached data from the database.
    @NonNull @MainThread
    protected abstract LiveData<CacheObject> loadFromDb();

    // Called to create the API call.
    @NonNull @MainThread
    protected abstract LiveData<NetworkResponse<RequestObject>> createCall();

    // Returns a LiveData object that represents the resource that's implemented in the base class.
    public final LiveData<Resource<CacheObject>> getAsLiveData(){
        return results;
    }
}
