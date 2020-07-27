package com.dommyg.conservativerss.repositories;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.dommyg.conservativerss.models.Article;
import com.dommyg.conservativerss.persistence.ArticleDao;
import com.dommyg.conservativerss.persistence.ArticleDatabase;
import com.dommyg.conservativerss.requests.ServiceGenerator;
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
                if (item.getChannel() != null) {
                    Article[] articles = new Article[item.getChannel().getArticleList().size()];

                    item.getChannel().getArticleList().toArray(articles);

                    for (Article article : articles) {
                        // TODO: Update to use proper source id.
                        article.setSourceId(1);
                    }
                    // TODO: Delete existing articles.

                    articleDao.insertArticles(articles);
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Article> data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<Article>> loadFromDb() {
                // TODO: Update this to work with different queries.
                return articleDao.getAllBySource(Integer.parseInt(query));
            }

            @NonNull
            @Override
            protected LiveData<NetworkResponse<RssResponse>> createCall() {
                return ServiceGenerator.getRssCall().getRss();
            }
        }.getAsLiveData();
    }
}
