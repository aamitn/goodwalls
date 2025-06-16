package com.app.goodwalls.util;

import android.graphics.Bitmap;

import com.app.goodwalls.model.Wallpaper;

import java.util.ArrayList;

public class Constant {

    public static final int DELAY_TIME = 100;
    public static final int DELAY_SET = 2500;

    public static final int LOAD_MORE_2_COLUMNS = 12;
    public static final int LOAD_MORE_3_COLUMNS = 15;

    public static final String EXTRA_OBJC = "key.EXTRA_OBJC";
    public static ArrayList<Wallpaper> wallpapers = new ArrayList<>();
    public static int position;

    //do not make any changes to the code below
    public static String FILTER = "";
    public static String DEFAULT_FILTER = "both";

    public static String ORDER = "";
    public static String DEFAULT_ORDER = "recent";

    public static int LAST_SELECTED_ITEM_POSITION = 0;

    public static final int DISPLAY_WALLPAPER_RECTANGLE = 1;
    public static final int DISPLAY_WALLPAPER_SQUARE = 2;
    public static final int DISPLAY_WALLPAPER_DYNAMIC = 3;

    public static final int SORT_RECENT = 0;
    public static final int SORT_FEATURED = 1;
    public static final int SORT_POPULAR = 2;
    public static final int SORT_RANDOM = 3;
    public static final int SORT_LIVE = 4;

    public static final int WALLPAPER_2_COLUMNS = 2;
    public static final int WALLPAPER_3_COLUMNS = 3;

    public static final int CATEGORY_LIST = 1;
    public static final int CATEGORY_GRID_2 = 2;

    public static final String APPLY = "apply";
    public static final String DOWNLOAD = "download";
    public static final String SHARE = "share";
    public static final String SET_WITH = "setWith";
    public static final String SET_CROP = "setCrop";
    public static final String SET_LIVE = "setLive";
    public static final String SET_GIF = "setGif";
    public static final String SET_MP4 = "setMp4";
    public static final String SET_UNLOCK_REWARDED = "unlock_rewarded";
    public static final String REWARDED = "";

    public static final String HOME_SCREEN = "home_screen";
    public static final String LOCK_SCREEN = "lock_screen";
    public static final String BOTH = "both";

    public static String gifName = "";
    public static String gifPath = "";

    public static String mp4Name = "";
    public static String mp4Path = "";
    public static Bitmap bitmap;
    public static Bitmap bitmapCropped;
    public static String wallpaperUrl;
    public static String wallpaperId;

    public static final String LOCALHOST_ADDRESS = "http://192.168.1.5"; //Local Server Root Address Here without basepath

    public static final int IMMEDIATE_APP_UPDATE_REQ_CODE = 124;

    public static boolean isAppOpen = false;
    public static boolean isRewarded = false;

    public static int CURRENT_POSITION = -1;

    public static class Order {
        public static String RECENT = "recent";
        public static String OLDEST = "oldest";
        public static String FEATURED = "featured";
        public static String POPULAR = "popular";
        public static String DOWNLOAD = "download";
        public static String RANDOM = "random";
    }

    public static class Filter {
        public static String WALLPAPER = "wallpaper";
        public static String LIVE_WALLPAPER = "live";
        public static String BOTH = "both";
    }

}
