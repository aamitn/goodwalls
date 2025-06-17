package com.app.goodwalls1.callback;

import com.app.goodwalls1.model.AdStatus;
import com.app.goodwalls1.model.Ads;
import com.app.goodwalls1.model.App;
import com.app.goodwalls1.model.License;
import com.app.goodwalls1.model.Menu;
import com.app.goodwalls1.model.Placement;
import com.app.goodwalls1.model.Settings;

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
