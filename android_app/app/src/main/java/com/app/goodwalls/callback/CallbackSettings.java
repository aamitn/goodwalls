package com.app.goodwalls.callback;

import com.app.goodwalls.model.AdStatus;
import com.app.goodwalls.model.Ads;
import com.app.goodwalls.model.App;
import com.app.goodwalls.model.License;
import com.app.goodwalls.model.Menu;
import com.app.goodwalls.model.Placement;
import com.app.goodwalls.model.Settings;

import java.util.ArrayList;
import java.util.List;

public class CallbackSettings {

    public String status;
    public App app = null;
    public List<Menu> menus = new ArrayList<>();
    public Settings settings = null;
    public Ads ads = null;
    public AdStatus ads_status = null;
    public Placement ads_placement = null;
    public License license = null;

}
