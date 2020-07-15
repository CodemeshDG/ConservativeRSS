package com.dommyg.conservativerss.models;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "rss", strict = false)
public class Rss {

    @Element(name = "channel", required = false)
    private Channel channel;

    public Rss() {

    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    @Root(name = "channel", strict = false)
    public static class Channel {

        @ElementList(inline = true, required = false)
        private List<Article> articleList;

        @Element(name = "title", required = false)
        private String title;

        @Element(name = "link", required = false)
        private String homepageUrl;

        @Element(name = "description", required = false)
        private String description;

        public Channel() {

        }

        public List<Article> getArticleList() {
            return articleList;
        }

        public void setArticleList(List<Article> articleList) {
            this.articleList = articleList;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getHomepageUrl() {
            return homepageUrl;
        }

        public void setHomepageUrl(String homepageUrl) {
            this.homepageUrl = homepageUrl;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
