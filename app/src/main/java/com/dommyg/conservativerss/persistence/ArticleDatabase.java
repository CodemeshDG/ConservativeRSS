package com.dommyg.conservativerss.persistence;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.dommyg.conservativerss.models.Article;
import com.dommyg.conservativerss.util.ImageDbTypeConverter;

@Database(entities = {Article.class}, version = 1, exportSchema = false)
@TypeConverters(ImageDbTypeConverter.class)
public abstract class ArticleDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "article_db";

    private static ArticleDatabase instance;

    public static ArticleDatabase getInstance(final Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    ArticleDatabase.class,
                    DATABASE_NAME
            ).build();
        }
        return instance;
    }

    public abstract ArticleDao getArticleDao();
}
