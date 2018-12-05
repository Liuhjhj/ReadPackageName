package com.liuhjhj.readpackagename;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

public class App implements Serializable {

    private static final long serialVersionUID = 1L;
    private String appName;
    private Drawable appIcon;
    private String appPackageName;

    public Drawable getAppIcon() {
        return appIcon;
    }

    public String getAppName() {
        return appName;
    }

    public String getAppPackageName() {
        return appPackageName;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setAppPackageName(String appPackageName) {
        this.appPackageName = appPackageName;
    }

    
}
