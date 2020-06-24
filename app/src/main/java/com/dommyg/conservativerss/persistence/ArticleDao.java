package com.dommyg.conservativerss.persistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.dommyg.conservativerss.models.Article;

import java.util.List;

@Dao
public interface ArticleDao {

    @Insert
    void insertArticles(Article... article);

    @Query("DELETE FROM article_table WHERE source_id = :sourceId")
    void deleteAllFromSource(int sourceId);

    @Query("SELECT * FROM article_table WHERE source_id = :sourceId ORDER BY id ASC")
    LiveData<List<Article>> getAllBySource(int sourceId);

    @Query("SELECT * FROM article_table WHERE title LIKE '%' || :query || '%' " +
            "OR summary LIKE '%' || :query || '%' ORDER BY id ASC")
    LiveData<List<Article>> getByQuery(String query);
}
