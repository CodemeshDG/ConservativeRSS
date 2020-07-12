package com.dommyg.conservativerss.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

// TODO: Update this description.
/**
 * Holds information about each article that appears in the UI for a source. The title, author,
 * publication date, and summary are displayed in the UI. The URL is used in the onClickListener
 * so that it can open the article in an internet browser when the user pressed on an article in the
 * list.
 */
@Entity(tableName = "article_table")
@Root(name = "item", strict = false)
public class Article implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    @ColumnInfo(name = "source_id")
    private int sourceId;

    @ColumnInfo(name = "title")
    @Element(name = "title", required = false)
    private String title;

    @ColumnInfo(name = "author")
    @Element(name = "creator", required = false)
//    @Namespace(reference = "http://purl.org/dc/elements/1.1/", prefix = "dc")
    private String author;

    @ColumnInfo(name = "date")
    @Element(name = "pubDate", required = false)
    private String date;

    @ColumnInfo(name = "summary")
    @Element(name = "description", required = false)
    private String summary;

    @ColumnInfo(name = "url")
    @Element(name = "link", required = false)
    private String url;

    @ColumnInfo(name = "image")
    @Element(name = "enclosure", required = false)
//    @Attribute(name = "url")
    private String image;

    public Article() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSourceId() {
        return sourceId;
    }

    public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = "By " + author.replaceAll("[\\n\\t]", "");
        if (this.author.contains("By By")) {
            this.author = this.author.replace("By By", "By");
        }
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = "Published " + date;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    private Article(Parcel in) {
        id = in.readInt();
        sourceId = in.readInt();
        title = in.readString();
        author = in.readString();
        date = in.readString();
        summary = in.readString();
        url = in.readString();
        image = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(sourceId);
        dest.writeString(title);
        dest.writeString(author);
        dest.writeString(date);
        dest.writeString(summary);
        dest.writeString(url);
        dest.writeString(image);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Article> CREATOR =
            new Parcelable.Creator<Article>() {
                @Override
                public Article createFromParcel(Parcel in) {
                    return new Article(in);
                }

                @Override
                public Article[] newArray(int size) {
                    return new Article[size];
                }
            };
}