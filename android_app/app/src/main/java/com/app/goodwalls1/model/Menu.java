package com.app.goodwalls1.model;

import java.io.Serializable;

public class Menu implements Serializable {

    public String menu_id = "";
    public String menu_title = "";
    public String menu_order = "";
    public String menu_filter = "";
    public String menu_category = "";

    public Menu(String title, String order, String filter, String category) {
        this.menu_title = title;
        this.menu_order = order;
        this.menu_filter = filter;
        this.menu_category = category;
    }
}
