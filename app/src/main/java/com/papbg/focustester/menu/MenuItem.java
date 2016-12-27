package com.papbg.focustester.menu;

import android.support.annotation.DrawableRes;

/**
 * Created by bradhawk on 12/27/2016.
 */

public class MenuItem {

    @DrawableRes private int iconId;
    private String menu;

    public MenuItem(@DrawableRes int iconId, String menu) {
        this.iconId = iconId;
        this.menu = menu;
    }

    @DrawableRes
    public int getIconId() {
        return iconId;
    }

    public void setIconId(@DrawableRes int iconId) {
        this.iconId = iconId;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }
}
