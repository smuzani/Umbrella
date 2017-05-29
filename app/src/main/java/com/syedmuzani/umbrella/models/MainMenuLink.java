package com.syedmuzani.umbrella.models;

/**
 * Created by Muz on 2016-08-13.
 */
public class MainMenuLink {
    String title;
    java.lang.Class activityClass;

    public MainMenuLink(String title, java.lang.Class activityClass) {
        this.title = title;
        this.activityClass = activityClass;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Class getActivityClass() {
        return activityClass;
    }

    public void setActivityClass(Class activityClass) {
        this.activityClass = activityClass;
    }
}
