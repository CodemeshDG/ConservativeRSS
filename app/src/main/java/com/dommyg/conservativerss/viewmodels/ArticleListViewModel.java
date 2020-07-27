package com.dommyg.conservativerss.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.dommyg.conservativerss.models.Article;
import com.dommyg.conservativerss.repositories.ArticleRepository;
import com.dommyg.conservativerss.util.Resource;

import java.util.List;

public class ArticleListViewModel extends AndroidViewModel {

    public enum ViewState {SOURCE, SEARCH}

    private MutableLiveData<ViewState> viewState;
    private MediatorLiveData<Resource<List<Article>>> articles = new MediatorLiveData<>();

    private ArticleRepository articleRepository;

    public ArticleListViewModel(@NonNull Application application) {
        super(application);
        articleRepository = ArticleRepository.getInstance(application);
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

    public LiveData<Resource<List<Article>>> getArticles() {
        return articles;
    }

    public void retrieveRss(String query) {
        final LiveData<Resource<List<Article>>> repositorySource =
                articleRepository.retrieveRss(query);

        articles.addSource(repositorySource, new Observer<Resource<List<Article>>>() {
            @Override
            public void onChanged(Resource<List<Article>> listResource) {

                // TODO: React to data if needed.

                articles.setValue(listResource);
            }
        });
    }
}
