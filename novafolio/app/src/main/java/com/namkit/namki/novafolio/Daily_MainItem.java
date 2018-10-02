package com.namkit.namki.novafolio;

/**
 * Created by namki on 2018-03-05.
 */

public class Daily_MainItem {
    protected String title;
    protected String description;

    public Daily_MainItem(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
