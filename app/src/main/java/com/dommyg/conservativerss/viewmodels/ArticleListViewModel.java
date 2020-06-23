package com.dommyg.conservativerss.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class ArticleListViewModel extends AndroidViewModel {

    public enum ViewState {SOURCE, SEARCH};

    private MutableLiveData<ViewState> viewState;

    public ArticleListViewModel(@NonNull Application application) {
        super(application);
        init();
    }

    private void init() {
        if (viewState == null) {
            viewState = new MutableLiveData<>();
            viewState.setValue(ViewState.SOURCE);
        }
    }

    public LiveData<ViewState> getViewState() {
        return viewState;
    }
}
