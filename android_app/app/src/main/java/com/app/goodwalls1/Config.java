package com.app.goodwalls1;

import com.app.goodwalls1.util.Constant;

public class Config {

    //server key obtained from the admin panel //Generate in backend->apps->add new app->package name/active
    public static final String SERVER_KEY = "WVVoU01HTklUVFpNZVRsdVlqSTVhMlF5Um5OaVNFMTFXVzFzTUdKWVZqQmFXR2QxV1RJNWRFd3hPV2hqU0VKellWZE9hR1JIYkhaaWEyeHJXREpPZG1KVE5XaGpTRUYxV2pJNWRscElaR2hpUjNoNlRWRTlQUT09";

    //use the launcher image as the top right home icon
    public static final boolean SET_LAUNCHER_IMAGE_AS_HOME_TOP_RIGHT_ICON = true;

    //set true to returns the menu swipe tab layout feature
    public static final boolean ENABLE_WALLPAPER_MENU_WITH_SWIPE_TAB_LAYOUT = true;

    //UI Config
    public static final boolean ENABLE_VIEWPAGER2_SWIPE_DETAIL_WALLPAPER = true;
    public static final boolean SHOW_FULL_SCREEN_WALLPAPER_DETAILS_VIEW = false;
    public static final boolean ENABLE_CENTER_CROP_IN_DETAIL_WALLPAPER = true;
    public static final boolean SHOW_WALLPAPER_NAME = true;
    public static final boolean SHOW_CATEGORY_NAME = true;
    public static final boolean SHOW_WALLPAPER_COUNT_ON_CATEGORY = true;

    //default theme in first launch
    public static final boolean SET_DARK_MODE_AS_DEFAULT_THEME = false;

    //if true, all ad unit ids are configured from res/values/ads.xml
    public static final boolean ENABLE_OFFLINE_ADS_MODE = false;

    //column count
    public static final int DEFAULT_WALLPAPER_VIEW_TYPE = Constant.WALLPAPER_2_COLUMNS;
    public static final int DEFAULT_CATEGORY_VIEW_TYPE = Constant.CATEGORY_LIST;

    //display grid wallpaper style
    public static final int DISPLAY_WALLPAPER = Constant.DISPLAY_WALLPAPER_RECTANGLE;

    //set category as main screen
    public static final boolean SET_CATEGORY_AS_MAIN_SCREEN = false;

    //Wallpaper set action visibility
    public static final boolean ENABLE_WALLPAPER_SET_ACTION_INFO = true;
    public static final boolean ENABLE_WALLPAPER_SET_ACTION_SAVE = true;
    public static final boolean ENABLE_WALLPAPER_SET_ACTION_APPLY = true;
    public static final boolean ENABLE_WALLPAPER_SET_ACTION_APPLY_WITH = false;
    public static final boolean ENABLE_WALLPAPER_SET_ACTION_FAVORITE = true;
    public static final boolean ENABLE_WALLPAPER_SET_ACTION_SHARE = true;

    //RTL Mode
    public static final boolean ENABLE_RTL_MODE = false;

    //Show dialog close app
    public static final boolean ENABLE_EXIT_DIALOG = true;

    //GDPR Consent
    public static final boolean ENABLE_GDPR_EU_CONSENT = true;

    //Set false if you do not want to use dynamic menu management from the admin panel
    //If false, you need to setup your tab menu name in the strings.xml
    public static final boolean ENABLE_ONLINE_TAB_MENU_IN_ADMIN_PANEL = true;

    //Enable it with true value if want to the app will force to display open ads first before start the main menu
    //Longer duration to start the app may occur depending on internet connection or open ad response time itself
    public static final boolean FORCE_TO_SHOW_APP_OPEN_AD_ON_START = false;

    //set false to prevent the app accessed using vpn or packet capture apps for security improvement
    public static final boolean ALLOW_VPN_ACCESS = false;

    //reset watched rewarded ad history when user exit the app
    public static final boolean RESET_REWARDED_AD_HISTORY_ON_EXIT_APP = false;

    //delay splash screen
    public static final int DELAY_SPLASH = 500;

}
