package com.dommyg.conservativerss.repositories;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.dommyg.conservativerss.models.Article;
import com.dommyg.conservativerss.persistence.ArticleDao;
import com.dommyg.conservativerss.persistence.ArticleDatabase;
import com.dommyg.conservativerss.requests.responses.NetworkResponse;
import com.dommyg.conservativerss.requests.responses.RssResponse;
import com.dommyg.conservativerss.util.AppExecutors;
import com.dommyg.conservativerss.util.NetworkBoundResource;
import com.dommyg.conservativerss.util.Resource;

import java.util.List;

public class ArticleRepository {

    private static ArticleRepository instance;
    private ArticleDao articleDao;

    public static ArticleRepository getInstance(Context context) {
        if (instance == null) {
            instance = new ArticleRepository(context);
        }
        return instance;
    }

    public ArticleRepository(Context context) {
        articleDao = ArticleDatabase.getInstance(context).getArticleDao();
    }

    public LiveData<Resource<List<Article>>> retrieveRss(final String query) {
        return new NetworkBoundResource<List<Article>, RssResponse>(AppExecutors.getInstance()) {
            @Override
            protected void saveCallResult(@NonNull RssResponse item) {

            }

            @Override
            protected boolean shouldFetch(@Nullable List<Article> data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<Article>> loadFromDb() {
                return articleDao.getByQuery(query);
            }

            @NonNull
            @Override
            protected LiveData<NetworkResponse<RssResponse>> createCall() {
                return null;
            }
        }.getAsLiveData();
    }
}
