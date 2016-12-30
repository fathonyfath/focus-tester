package com.papbg.focustester.menu;

import android.support.annotation.DrawableRes;

/**
 * Created by bradhawk on 12/27/2016.
 */

public class MenuItem {

    private String menu;

    public MenuItem(String menu) {
        this.menu = menu;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }
}
