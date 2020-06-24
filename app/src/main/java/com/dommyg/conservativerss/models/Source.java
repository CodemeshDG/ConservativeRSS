package com.dommyg.conservativerss.models;

// TODO: Update this description.
/**
 * Holds information for a source (the name, description, homepage URL, RSS feed URL, and a
 * reference to an icon stored in drawable. This information (except for the RSS feed URL) is
 * displayed in the sourceBanner at the top of the UI. The RSS feed URL is passed to downloadUrl in
 * the MainActivity class so that the contents could be parsed and displayed in the UI as selectable
 * articles.
 */
class Source {

    private int name;
    private int description;
    private String homepageURL;
    private String feedURL;
    private int icon;
    private int id;

    Source(int name, int description, String homepageURL, String feedURL, int icon,
           int id) {
        this.name = name;
        this.description = description;
        this.homepageURL = homepageURL;
        this.feedURL = feedURL;
        this.icon = icon;
        this.id = id;
    }

    int getName() {
        return name;
    }

    int getDescription() {
        return description;
    }

    String getHomepageURL() {
        return homepageURL;
    }

    String getFeedURL() {
        return feedURL;
    }

    int getIcon() {
        return icon;
    }

    int getId() {
        return id;
    }
}
