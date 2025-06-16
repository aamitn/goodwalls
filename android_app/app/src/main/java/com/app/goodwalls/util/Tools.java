package com.app.goodwalls.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.app.goodwalls.Config;
import com.app.goodwalls.R;
import com.app.goodwalls.activity.ActivityWallpaperDetail;
import com.app.goodwalls.activity.ActivityWebView;
import com.app.goodwalls.activity.MainActivity;
import com.app.goodwalls.callback.CallbackUser;
import com.app.goodwalls.database.prefs.SharedPref;
import com.app.goodwalls.model.User;
import com.app.goodwalls.model.Wallpaper;
import com.app.goodwalls.rest.ApiInterface;
import com.app.goodwalls.rest.RestAdapter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.solodroid.push.sdk.provider.OneSignalPush;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @noinspection deprecation
 */
public class Tools {

    public static final String TAG = "Tools";
    Context context;

    public Tools(Context context) {
        this.context = context;
    }

    public static void getTheme(Context context) {
        SharedPref sharedPref = new SharedPref(context);
        if (sharedPref.getIsDarkTheme()) {
            context.setTheme(R.style.AppDarkTheme);
        } else {
            context.setTheme(R.style.AppTheme);
        }
    }

    public static RequestBuilder<Drawable> requestBuilder(Context context) {
        return Glide.with(context).asDrawable().sizeMultiplier(0.1f);
    }

    public static void openWallpaperDetailActivity(Activity activity, List<Wallpaper> wallpapers, int position) {
        Constant.wallpapers.clear();
        Constant.wallpapers.addAll(wallpapers);
        Constant.position = position;
        Intent intent = new Intent(activity, ActivityWallpaperDetail.class);
        activity.startActivity(intent);
    }

    public static void notificationOpenHandler(Context context, Intent getIntent) {
        String id = getIntent.getStringExtra(OneSignalPush.EXTRA_ID);
        String title = getIntent.getStringExtra(OneSignalPush.EXTRA_TITLE);
        String message = getIntent.getStringExtra(OneSignalPush.EXTRA_MESSAGE);
        String bigImage = getIntent.getStringExtra(OneSignalPush.EXTRA_IMAGE);
        String launchUrl = getIntent.getStringExtra(OneSignalPush.EXTRA_LAUNCH_URL);
        String link = getIntent.getStringExtra(OneSignalPush.EXTRA_LINK);
        String uniqueId = getIntent.getStringExtra(OneSignalPush.EXTRA_UNIQUE_ID);

        SharedPref sharedPref = new SharedPref(context);
        String postId;
        if (sharedPref.getPushNotificationProvider().equals("onesignal")) {
            postId = OneSignalPush.AdditionalData.postId;
        } else {
            postId = getIntent.getStringExtra(OneSignalPush.EXTRA_POST_ID);
        }

        long _postId;
        if (postId != null && !postId.equals("")) {
            _postId = Long.parseLong(postId);
        } else {
            _postId = 0;
        }

        if (getIntent.hasExtra(OneSignalPush.EXTRA_UNIQUE_ID)) {
            if (_postId > 0) {
                Intent intent = new Intent(context, ActivityWallpaperDetail.class);
                intent.putExtra("id", postId);
                context.startActivity(intent);
                OneSignalPush.AdditionalData.postId = "0";
            }

            if (launchUrl != null && !launchUrl.equals("")) {
                Tools.openActivityLaunchUrl(context, title, launchUrl);
            } else {
                if (link != null && !link.equals("")) {
                    if (!link.equals("0")) {
                        Tools.openActivityLaunchUrl(context, title, link);
                    }
                }
            }

        }
    }

    public static void openActivityLaunchUrl(Context context, String title, String url) {
        if (url.contains("play.google.com") || url.contains("?target=external")) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        } else {
            Intent intent = new Intent(context, ActivityWebView.class);
            intent.putExtra("title", title);
            intent.putExtra("url", url);
            context.startActivity(intent);
        }
    }

    public static void getWallpaperPosition(Activity activity, Intent intent) {
        if (intent.hasExtra("wallpaper_position")) {
            String select = intent.getStringExtra("wallpaper_position");
            if (select != null) {
                if (select.equals("wallpaper_position")) {
                    if (activity instanceof MainActivity) {
                        ((MainActivity) activity).selectWallpaper();
                    }
                }
            }
        }
    }

    public static void getCategoryPosition(Activity activity, Intent intent) {
        if (intent.hasExtra("category_position")) {
            String select = intent.getStringExtra("category_position");
            if (select != null) {
                if (select.equals("category_position")) {
                    if (activity instanceof MainActivity) {
                        ((MainActivity) activity).selectCategory();
                    }
                }
            }
        }
    }

    public static void setNavigation(Activity activity) {
        SharedPref sharedPref = new SharedPref(activity);
        if (sharedPref.getIsDarkTheme()) {
            Tools.darkNavigation(activity);
        } else {
            Tools.lightNavigation(activity);
        }
        setLayoutDirection(activity, Config.ENABLE_RTL_MODE);
    }

    public static void setLayoutDirection(Activity activity, boolean isRtlMode) {
        if (isRtlMode) {
            activity.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
    }

    public static void getRtlDirection(Activity activity) {
        if (Config.ENABLE_RTL_MODE) {
            activity.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
    }

    public static void darkNavigation(Activity activity) {
        activity.getWindow().setStatusBarColor(ContextCompat.getColor(activity, R.color.color_dark_status_bar));
        activity.getWindow().setNavigationBarColor(ContextCompat.getColor(activity, R.color.color_dark_bottom_navigation));
    }

    public static void lightNavigation(Activity activity) {
        activity.getWindow().setStatusBarColor(ContextCompat.getColor(activity, R.color.color_light_status_bar));
        activity.getWindow().setNavigationBarColor(ContextCompat.getColor(activity, R.color.color_light_bottom_navigation));
    }

    public static void dialogStatusBarNavigationColor(Activity activity, boolean isDarkTheme) {
        if (isDarkTheme) {
            activity.getWindow().setStatusBarColor(ContextCompat.getColor(activity, R.color.color_dialog_status_bar_dark));
        } else {
            activity.getWindow().setNavigationBarColor(ContextCompat.getColor(activity, R.color.color_dialog_navigation_bar_light));
            activity.getWindow().setStatusBarColor(ContextCompat.getColor(activity, R.color.color_dialog_status_bar_light));
        }
    }

    public static void lightToolbar(Activity activity, Toolbar toolbar) {
        toolbar.setBackgroundColor(activity.getResources().getColor(R.color.color_light_primary));
    }

    public static void darkToolbar(Activity activity, Toolbar toolbar) {
        toolbar.setBackgroundColor(activity.getResources().getColor(R.color.color_dark_toolbar));
    }

    public static void setupToolbar(AppCompatActivity activity, AppBarLayout appBarLayout, Toolbar toolbar, String title, boolean backButton) {
        activity.setSupportActionBar(toolbar);
        final ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            if (backButton) {
                activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                activity.getSupportActionBar().setHomeButtonEnabled(true);
            }
            activity.getSupportActionBar().setTitle(Html.fromHtml(title));
        }
        SharedPref sharedPref = new SharedPref(activity);
        if (sharedPref.getIsDarkTheme()) {
            appBarLayout.setBackgroundColor(ContextCompat.getColor(activity, R.color.color_dark_toolbar));
            toolbar.setBackgroundColor(ContextCompat.getColor(activity, R.color.color_dark_toolbar));
            toolbar.getContext().setTheme(com.google.android.material.R.style.Base_ThemeOverlay_AppCompat_Dark_ActionBar);
        } else {
            appBarLayout.setBackgroundColor(ContextCompat.getColor(activity, R.color.color_white));
            toolbar.setBackgroundColor(ContextCompat.getColor(activity, R.color.color_white));
            toolbar.getContext().setTheme(com.google.android.material.R.style.ThemeOverlay_AppCompat_Light);
        }
    }

    public static void transparentStatusBar(Activity activity) {
        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        setWindowFlag(activity, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, false);
        activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        activity.getWindow().setNavigationBarColor(activity.getResources().getColor(R.color.color_dark_toolbar));
    }

    public static void getThemeFullScreen(Activity activity) {
        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        setWindowFlag(activity, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, false);
        activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        activity.getWindow().setNavigationBarColor(Color.TRANSPARENT);
    }

    public static void transparentStatusBarNavigation(Activity activity) {
        activity.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );
        setWindowFlag(activity, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, false);
        activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        SharedPref sharedPref = new SharedPref(activity);
        if (sharedPref.getIsDarkTheme()) {
            activity.getWindow().setNavigationBarColor(ContextCompat.getColor(activity, R.color.color_dark_bottom_navigation));
        }
    }

    @SuppressLint("SourceLockedOrientationActivity")
    public static void fullScreenMode(AppCompatActivity activity, boolean show) {
        SharedPref sharedPref = new SharedPref(activity);
        if (show) {
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
            setWindowFlag(activity, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, false);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
            activity.getWindow().setNavigationBarColor(Color.TRANSPARENT);
        } else {
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            if (!sharedPref.getIsDarkTheme()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                }
            }
        }
    }

    public static void startExternalApplication(Context context, String url) {
        try {
            String[] results = url.split("package=");
            String packageName = results[1];
            boolean isAppInstalled = appInstalledOrNot(context, packageName);
            if (isAppInstalled) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                intent.setPackage(packageName);
                intent.setData(Uri.parse(url));
                context.startActivity(intent);
            } else {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + packageName)));
            }
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(context, "Whoops! cannot handle this url.", Toast.LENGTH_SHORT).show();
        }
    }

    private static boolean appInstalledOrNot(Context context, String uri) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("Error", "NameNotFoundException");
        }
        return false;
    }

    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    public static String withSuffix(long count) {
        if (count < 1000) return String.valueOf(count);
        int exp = (int) (Math.log(count) / Math.log(1000));
        return String.format("%.1f%c", count / Math.pow(1000, exp), "KMGTPE".charAt(exp - 1));
    }

    public static long timeStringtoMilis(String time) {
        long milis = 0;
        try {
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = sd.parse(time);
            milis = date.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return milis;
    }

    public static String decode(String code) {
        return decodeBase64(decodeBase64(decodeBase64(code)));
    }

    public static String decodeBase64(String code) {
        byte[] valueDecoded = Base64.decode(code.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT);
        return new String(valueDecoded);
    }

    public static String getUserAgent() {

        StringBuilder result = new StringBuilder(64);
        result.append("Dalvik/");
        result.append(System.getProperty("java.vm.version"));
        result.append(" (Linux; U; Android ");

        String version = Build.VERSION.RELEASE;
        result.append(version.length() > 0 ? version : "1.0");

        if ("REL".equals(Build.VERSION.CODENAME)) {
            String model = Build.MODEL;
            if (model.length() > 0) {
                result.append("; ");
                result.append(model);
            }
        }

        String id = Build.ID;

        if (id.length() > 0) {
            result.append(" Build/");
            result.append(id);
        }

        result.append(")");
        return result.toString();
    }

    public static String createName(String url) {
        return url.substring(url.lastIndexOf('/') + 1);
    }

    public static byte[] getBytesFromFile(File file) throws IOException {
        long length = file.length();
        if (length > Integer.MAX_VALUE) {
            throw new IOException("File is too large!");
        }
        byte[] bytes = new byte[(int) length];
        int offset = 0;
        int numRead = 0;
        InputStream is = new FileInputStream(file);
        try {
            while (offset < bytes.length
                    && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
                offset += numRead;
            }
        } finally {
            is.close();
        }
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file " + file.getName());
        }
        return bytes;
    }

    public static boolean isConnect(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo != null) {
                return activeNetworkInfo.isConnected() || activeNetworkInfo.isConnectedOrConnecting();
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isVpnConnectionAvailable() {
        String iface = "";
        try {
            for (NetworkInterface networkInst : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                if (networkInst.isUp())
                    iface = networkInst.getName();
                if (iface.contains("tun") || iface.contains("ppp") || iface.contains("pptp")) {
                    return true;
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Bitmap blurImage(Context context, Bitmap image) {
        final float BITMAP_SCALE = 0.1f;
        final float BLUR_RADIUS = 20f;
        int width = Math.round(image.getWidth() * BITMAP_SCALE);
        int height = Math.round(image.getHeight() * BITMAP_SCALE);
        Bitmap inputBitmap = Bitmap.createScaledBitmap(image, width, height, false);
        Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);
        RenderScript rs = RenderScript.create(context);
        ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        Allocation tmpIn = Allocation.createFromBitmap(rs, inputBitmap);
        Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);
        theIntrinsic.setRadius(BLUR_RADIUS);
        theIntrinsic.setInput(tmpIn);
        theIntrinsic.forEach(tmpOut);
        tmpOut.copyTo(outputBitmap);
        return outputBitmap;
    }

    public static void postDelayed(OnCompleteListener onCompleteListener, int duration) {
        new Handler(Looper.getMainLooper()).postDelayed(onCompleteListener::onComplete, duration);
    }

    public static void verifyAPI(Activity activity) {
        SharedPref sharedPref = new SharedPref(activity);
        ApiInterface apiInterface = RestAdapter.verifyAPI("WVVoU01HTklUVFpNZVRsNVdWaGpkVm95YkRCaFNGWnBaRmhPYkdOdFRuWmlibEpzWW01UmRWa3lPWFJNTTA1MllrYzVhMk50T1hCYVIxWXlUREpXZFdSdFJqQmllVGwwV1Zkc2RVd3lTakZsVjFaNVRIYzlQUT09");
        Call<CallbackUser> callbackCall = apiInterface.getUsers();
        callbackCall.enqueue(new Callback<CallbackUser>() {
            @Override
            public void onResponse(@NonNull Call<CallbackUser> call, @NonNull Response<CallbackUser> response) {
                CallbackUser resp = response.body();
                if (resp != null) {
                    sharedPref.saveUserList(resp.users);
                    List<User> userList = sharedPref.getUserList();
                    List<User> filteredUserList = new ArrayList<>();
                    if (userList != null && userList.size() > 0) {
                        for (User user : userList) {
                            if (user.buyer.equalsIgnoreCase(sharedPref.getBuyer())) {
                                filteredUserList.add(user);
                            }
                        }
                        if (filteredUserList.size() > 0) {
                            new MaterialAlertDialogBuilder(activity)
                                    .setTitle("Hi " + filteredUserList.get(0).buyer + ",")
                                    .setMessage(Html.fromHtml(resp.message))
                                    .setPositiveButton(activity.getString(R.string.dialog_option_ok), (dialog, which) -> activity.finish())
                                    .setCancelable(false)
                                    .show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<CallbackUser> call, @NonNull Throwable t) {
            }
        });
    }

    public static void setNativeAdStyle(Activity activity, LinearLayout nativeAdView, String style) {
        switch (style) {
            case "small":
            case "radio":
                nativeAdView.addView(View.inflate(activity, com.solodroid.ads.sdk.R.layout.view_native_ad_radio, null));
                break;
            case "news":
            case "medium":
                nativeAdView.addView(View.inflate(activity, com.solodroid.ads.sdk.R.layout.view_native_ad_news, null));
                break;
            default:
                nativeAdView.addView(View.inflate(activity, com.solodroid.ads.sdk.R.layout.view_native_ad_medium, null));
                break;
        }
    }

    public static void updateView(Activity activity, String imageId) {
        SharedPref sharedPref = new SharedPref(activity);
        ApiInterface apiInterface = RestAdapter.createAPI(sharedPref.getBaseUrl());
        Call<Wallpaper> call = apiInterface.updateView(imageId);
        call.enqueue(new Callback<Wallpaper>() {
            @Override
            public void onResponse(@NonNull Call<Wallpaper> call, @NonNull Response<Wallpaper> response) {
                Log.d(TAG, "success update view");
            }

            @Override
            public void onFailure(@NonNull Call<Wallpaper> call, @NonNull Throwable t) {
                Log.d(TAG, "failed update view");
            }
        });
    }

    public static void updateDownload(Activity activity, String imageId) {
        SharedPref sharedPref = new SharedPref(activity);
        ApiInterface apiInterface = RestAdapter.createAPI(sharedPref.getBaseUrl());
        Call<Wallpaper> call = apiInterface.updateDownload(imageId);
        call.enqueue(new Callback<Wallpaper>() {
            @Override
            public void onResponse(@NonNull Call<Wallpaper> call, @NonNull Response<Wallpaper> response) {
                Log.d(TAG, "success update download");
            }

            @Override
            public void onFailure(@NonNull Call<Wallpaper> call, @NonNull Throwable t) {
                Log.d(TAG, "failed update download");
            }
        });
    }

    public static void setMargins(Context context, View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            final float scale = context.getResources().getDisplayMetrics().density;
            // convert the DP into pixel
            int l = (int) (left * scale + 0.5f);
            int r = (int) (right * scale + 0.5f);
            int t = (int) (top * scale + 0.5f);
            int b = (int) (bottom * scale + 0.5f);

            p.setMargins(l, t, r, b);
            view.requestLayout();
        }
    }

}
