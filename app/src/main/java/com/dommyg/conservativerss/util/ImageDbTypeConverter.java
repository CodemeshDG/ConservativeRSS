package com.dommyg.conservativerss.util;

import androidx.room.TypeConverter;

import com.dommyg.conservativerss.models.Article;

public class ImageDbTypeConverter {

    @TypeConverter
    public static Article.Image fromString(String url) {
        return url == null ? null : new Article.Image(url);
    }

    @TypeConverter
    public static String fromImage(Article.Image image) {
        return image.getImage() == null ? null : image.getImage();
    }
}
