package com.app.goodwalls1.activity;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentSender;
import android.content.res.AssetManager;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ProcessLifecycleOwner;
import androidx.viewpager.widget.ViewPager;

import com.app.goodwalls1.BuildConfig;
import com.app.goodwalls1.Config;
import com.app.goodwalls1.R;
import com.app.goodwalls1.component.AppBarLayoutBehavior;
import com.app.goodwalls1.component.RtlViewPager;
import com.app.goodwalls1.database.dao.AppDatabase;
import com.app.goodwalls1.database.dao.DAO;
import com.app.goodwalls1.database.prefs.AdsPref;
import com.app.goodwalls1.database.prefs.SharedPref;
import com.app.goodwalls1.fragment.FragmentCategory;
import com.app.goodwalls1.fragment.FragmentFavorite;
import com.app.goodwalls1.fragment.FragmentTabLayout;
import com.app.goodwalls1.fragment.FragmentWallpaper;
import com.app.goodwalls1.util.AdsManager;
import com.app.goodwalls1.util.Constant;
import com.app.goodwalls1.util.Tools;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.solodroid.ads.sdk.format.AppOpenAd;
import com.solodroid.push.sdk.provider.OneSignalPush;

public class MainActivity extends AppCompatActivity implements DefaultLifecycleObserver {

    public static final String TAG = "MainActivity";
    AppBarLayout appBarLayout;
    ImageButton btnSearch;
    TextView titleToolbar;
    CardView lytSearchBar;
    LinearLayout searchBar;
    ImageView btnMoreOptions;
    private ViewPager viewPager;
    private RtlViewPager viewPagerRTL;
    private long exitTime = 0;
    private CoordinatorLayout coordinatorLayout;
    MenuItem prevMenuItem;
    int pagerNumber = 3;
    private BottomNavigationView bottomNavigationView;
    AdsPref adsPref;
    SharedPref sharedPref;
    AdsManager adsManager;
    private AppUpdateManager appUpdateManager;
    View lyt_dialog_exit;
    LinearLayout lyt_panel_view;
    LinearLayout lyt_panel_dialog;
    DAO db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Tools.getTheme(this);
        setContentView(R.layout.activity_main);
        Tools.setNavigation(this);
        sharedPref = new SharedPref(this);
        adsPref = new AdsPref(this);
        adsManager = new AdsManager(this);
        db = AppDatabase.getDb(this).get();

        if (Config.FORCE_TO_SHOW_APP_OPEN_AD_ON_START) {
            ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
            adsPref.setIsOpenAd(true);
        }

        adsManager.initializeAd();
        adsManager.updateConsentStatus();
        adsManager.loadAppOpenAd(adsPref.getIsAppOpenAdOnResume());
        adsManager.loadBannerAd(adsPref.getIsBannerHome());
        adsManager.loadInterstitialAd(adsPref.getIsInterstitialPostList(), adsPref.getInterstitialAdInterval());

        coordinatorLayout = findViewById(R.id.coordinator_layout);
        appBarLayout = findViewById(R.id.appbar_layout);
        ((CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams()).setBehavior(new AppBarLayoutBehavior());

        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.getMenu().clear();
        if (Config.SET_CATEGORY_AS_MAIN_SCREEN) {
            bottomNavigationView.inflateMenu(R.menu.navigation_category);
        } else {
            bottomNavigationView.inflateMenu(R.menu.navigation_wallpaper);
        }
        lytSearchBar = findViewById(R.id.lyt_search_bar);
        if (sharedPref.getIsDarkTheme()) {
            lytSearchBar.setCardBackgroundColor(ContextCompat.getColor(this, R.color.color_dark_search_bar));
        } else {
            lytSearchBar.setCardBackgroundColor(ContextCompat.getColor(this, R.color.color_light_search_bar));
        }
        searchBar = findViewById(R.id.search_bar);
        searchBar.setOnClickListener(view -> {
            openSearchPage();
            destroyBannerAd();
        });

        btnSearch = findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(view -> {
            openSearchPage();
            destroyBannerAd();
        });

        titleToolbar = findViewById(R.id.title_toolbar);
        btnMoreOptions = findViewById(R.id.btn_more_options);
        if (Config.SET_LAUNCHER_IMAGE_AS_HOME_TOP_RIGHT_ICON) {
            btnMoreOptions.setImageResource(R.mipmap.ic_launcher);
            int padding = getResources().getDimensionPixelSize(R.dimen.padding_small);
            btnMoreOptions.setPadding(padding, padding, padding, padding);
        } else {
            btnMoreOptions.setImageResource(R.drawable.ic_settings);
            int padding = getResources().getDimensionPixelSize(R.dimen.padding_medium);
            btnMoreOptions.setPadding(padding, padding, padding, padding);
            if (sharedPref.getIsDarkTheme()) {
                btnMoreOptions.setColorFilter(ContextCompat.getColor(this, R.color.color_dark_icon), PorterDuff.Mode.SRC_IN);
            } else {
                btnMoreOptions.setColorFilter(ContextCompat.getColor(this, R.color.color_light_icon), PorterDuff.Mode.SRC_IN);
            }
        }

        titleToolbar.setText(getString(R.string.app_name));

        btnMoreOptions.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), ActivitySettings.class));
        });

        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setLabelVisibilityMode(BottomNavigationView.LABEL_VISIBILITY_LABELED);
        if (sharedPref.getIsDarkTheme()) {
            bottomNavigationView.setBackgroundColor(ContextCompat.getColor(this, R.color.color_dark_bottom_navigation));
            btnSearch.setColorFilter(ContextCompat.getColor(this, R.color.color_dark_icon), PorterDuff.Mode.SRC_IN);
            titleToolbar.setTextColor(ContextCompat.getColor(this, R.color.color_dark_icon));
        } else {
            bottomNavigationView.setBackgroundColor(ContextCompat.getColor(this, R.color.color_light_bottom_navigation));
            btnSearch.setColorFilter(ContextCompat.getColor(this, R.color.color_light_icon), PorterDuff.Mode.SRC_IN);
            titleToolbar.setTextColor(ContextCompat.getColor(this, R.color.color_light_text_default));
        }
        bottomNavigationView.setLabelVisibilityMode(BottomNavigationView.LABEL_VISIBILITY_LABELED);
        initViewPager();
        initExitDialog();

        Tools.getWallpaperPosition(this, getIntent());
        Tools.getCategoryPosition(this, getIntent());
        Tools.notificationOpenHandler(this, getIntent());

        new OneSignalPush.Builder(this).requestNotificationPermission();

        if (!BuildConfig.DEBUG) {
            appUpdateManager = AppUpdateManagerFactory.create(getApplicationContext());
            inAppUpdate();
            inAppReview();
        }

    }

    @Override
    public void onStart(@NonNull LifecycleOwner owner) {
        DefaultLifecycleObserver.super.onStart(owner);
        Tools.postDelayed(() -> {
            if (AppOpenAd.isAppOpenAdLoaded) {
                adsManager.showAppOpenAd(adsPref.getIsAppOpenAdOnResume());
            }
        }, 100);
    }

    public void showInterstitialAd() {
        adsManager.showInterstitialAd();
    }

    public void destroyAppOpenAd() {
        if (Config.FORCE_TO_SHOW_APP_OPEN_AD_ON_START) {
            adsManager.destroyAppOpenAd(adsPref.getIsAppOpenAdOnResume());
            ProcessLifecycleOwner.get().getLifecycle().removeObserver(this);
        }
        Constant.isAppOpen = false;
    }

    @SuppressLint("NonConstantResourceId")
    public void initViewPager() {
        viewPagerRTL = findViewById(R.id.view_pager_rtl);
        viewPager = findViewById(R.id.view_pager);

        if (Config.ENABLE_RTL_MODE) {
            viewPager.setVisibility(View.GONE);
            viewPagerRTL.setVisibility(View.VISIBLE);
        } else {
            viewPager.setVisibility(View.VISIBLE);
            viewPagerRTL.setVisibility(View.GONE);
        }

        if (Config.ENABLE_RTL_MODE) {
            viewPagerRTL.setAdapter(new MyAdapter(getSupportFragmentManager()));
            viewPagerRTL.setOffscreenPageLimit(pagerNumber);

            if (Config.SET_CATEGORY_AS_MAIN_SCREEN) {
                bottomNavigationView.setOnItemSelectedListener(item -> {
                    int itemId = item.getItemId();
                    if (itemId == R.id.navigation_category) {
                        viewPagerRTL.setCurrentItem(0);
                        return true;
                    } else if (itemId == R.id.navigation_home) {
                        viewPagerRTL.setCurrentItem(1);
                        return true;
                    } else if (itemId == R.id.navigation_favorite) {
                        viewPagerRTL.setCurrentItem(2);
                        return true;
                    }
                    return false;
                });
            } else {
                bottomNavigationView.setOnItemSelectedListener(item -> {
                    int itemId = item.getItemId();
                    if (itemId == R.id.navigation_home) {
                        viewPagerRTL.setCurrentItem(0);
                        return true;
                    } else if (itemId == R.id.navigation_category) {
                        viewPagerRTL.setCurrentItem(1);
                        return true;
                    } else if (itemId == R.id.navigation_favorite) {
                        viewPagerRTL.setCurrentItem(2);
                        return true;
                    }
                    return false;
                });
            }

            viewPagerRTL.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    if (prevMenuItem != null) {
                        prevMenuItem.setChecked(false);
                    } else {
                        bottomNavigationView.getMenu().getItem(0).setChecked(false);
                    }
                    bottomNavigationView.getMenu().getItem(position).setChecked(true);
                    prevMenuItem = bottomNavigationView.getMenu().getItem(position);

                    if (viewPagerRTL.getCurrentItem() == 0) {
                        titleToolbar.setText(getResources().getString(R.string.app_name));
                    } else if (viewPagerRTL.getCurrentItem() == 1) {
                        if (Config.SET_CATEGORY_AS_MAIN_SCREEN) {
                            titleToolbar.setText(getResources().getString(R.string.title_nav_wallpaper));
                        } else {
                            titleToolbar.setText(getResources().getString(R.string.title_nav_category));
                        }
                    } else if (viewPagerRTL.getCurrentItem() == 2) {
                        titleToolbar.setText(getResources().getString(R.string.title_nav_favorite));
                    }

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        } else {
            viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
            viewPager.setOffscreenPageLimit(pagerNumber);

            if (Config.SET_CATEGORY_AS_MAIN_SCREEN) {
                bottomNavigationView.setOnItemSelectedListener(item -> {
                    int itemId = item.getItemId();
                    if (itemId == R.id.navigation_category) {
                        viewPager.setCurrentItem(0);
                        return true;
                    } else if (itemId == R.id.navigation_home) {
                        viewPager.setCurrentItem(1);
                        return true;
                    } else if (itemId == R.id.navigation_favorite) {
                        viewPager.setCurrentItem(2);
                        return true;
                    }
                    return false;
                });
            } else {
                bottomNavigationView.setOnItemSelectedListener(item -> {
                    int itemId = item.getItemId();
                    if (itemId == R.id.navigation_home) {
                        viewPager.setCurrentItem(0);
                        return true;
                    } else if (itemId == R.id.navigation_category) {
                        viewPager.setCurrentItem(1);
                        return true;
                    } else if (itemId == R.id.navigation_favorite) {
                        viewPager.setCurrentItem(2);
                        return true;
                    }
                    return false;
                });
            }

            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    if (prevMenuItem != null) {
                        prevMenuItem.setChecked(false);
                    } else {
                        bottomNavigationView.getMenu().getItem(0).setChecked(false);
                    }
                    bottomNavigationView.getMenu().getItem(position).setChecked(true);
                    prevMenuItem = bottomNavigationView.getMenu().getItem(position);

                    if (viewPager.getCurrentItem() == 0) {
                        titleToolbar.setText(getResources().getString(R.string.app_name));
                    } else if (viewPager.getCurrentItem() == 1) {
                        if (Config.SET_CATEGORY_AS_MAIN_SCREEN) {
                            titleToolbar.setText(getResources().getString(R.string.title_nav_wallpaper));
                        } else {
                            titleToolbar.setText(getResources().getString(R.string.title_nav_category));
                        }
                    } else if (viewPager.getCurrentItem() == 2) {
                        titleToolbar.setText(getResources().getString(R.string.title_nav_favorite));
                    }

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }
    }

    @SuppressWarnings("deprecation")
    public class MyAdapter extends FragmentPagerAdapter {

        MyAdapter(FragmentManager fm) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            if (Config.SET_CATEGORY_AS_MAIN_SCREEN) {
                if (position == 0) {
                    return new FragmentCategory();
                } else if (position == 1) {
                    if (Config.ENABLE_WALLPAPER_MENU_WITH_SWIPE_TAB_LAYOUT) {
                        return new FragmentTabLayout();
                    } else {
                        return new FragmentWallpaper();
                    }
                } else {
                    return new FragmentFavorite();
                }
            } else {
                if (position == 0) {
                    if (Config.ENABLE_WALLPAPER_MENU_WITH_SWIPE_TAB_LAYOUT) {
                        return new FragmentTabLayout();
                    } else {
                        return new FragmentWallpaper();
                    }
                } else if (position == 1) {
                    return new FragmentCategory();
                } else {
                    return new FragmentFavorite();
                }
            }
        }

        @Override
        public int getCount() {
            return pagerNumber;
        }

    }

    @Override
    public AssetManager getAssets() {
        return getResources().getAssets();
    }

    @Override
    public void onBackPressed() {
        if (Config.ENABLE_RTL_MODE) {
            if (viewPagerRTL.getCurrentItem() != 0) {
                viewPagerRTL.setCurrentItem((0), true);
            } else {
                exitApp();
            }
        } else {
            if (viewPager.getCurrentItem() != 0) {
                viewPager.setCurrentItem((0), true);
            } else {
                exitApp();
            }
        }
    }

    public void exitApp() {
        if (Config.ENABLE_EXIT_DIALOG) {
            if (lyt_dialog_exit.getVisibility() != View.VISIBLE) {
                showDialog(true);
            }
        } else {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                showSnackBar(getString(R.string.press_again_to_exit));
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                destroyBannerAd();
                destroyAppOpenAd();
                Constant.isAppOpen = false;
                if (Config.RESET_REWARDED_AD_HISTORY_ON_EXIT_APP) {
                    if (db.getAllRewarded().size() > 0) {
                        db.deleteAllRewarded();
                    }
                }
            }
        }
    }

    public void showSnackBar(String message) {
        Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void openSearchPage() {
        if (Config.ENABLE_RTL_MODE) {
            if (Config.SET_CATEGORY_AS_MAIN_SCREEN) {
                if (viewPagerRTL.getCurrentItem() == 0) {
                    Intent intent = new Intent(getApplicationContext(), ActivitySearch.class);
                    intent.putExtra(Constant.EXTRA_OBJC, "category");
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getApplicationContext(), ActivitySearch.class);
                    intent.putExtra(Constant.EXTRA_OBJC, "wallpaper");
                    startActivity(intent);
                }
            } else {
                if (viewPagerRTL.getCurrentItem() == 1) {
                    Intent intent = new Intent(getApplicationContext(), ActivitySearch.class);
                    intent.putExtra(Constant.EXTRA_OBJC, "category");
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getApplicationContext(), ActivitySearch.class);
                    intent.putExtra(Constant.EXTRA_OBJC, "wallpaper");
                    startActivity(intent);
                }
            }
        } else {
            if (Config.SET_CATEGORY_AS_MAIN_SCREEN) {
                if (viewPager.getCurrentItem() == 0) {
                    Intent intent = new Intent(getApplicationContext(), ActivitySearch.class);
                    intent.putExtra(Constant.EXTRA_OBJC, "category");
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getApplicationContext(), ActivitySearch.class);
                    intent.putExtra(Constant.EXTRA_OBJC, "wallpaper");
                    startActivity(intent);
                }
            } else {
                if (viewPager.getCurrentItem() == 1) {
                    Intent intent = new Intent(getApplicationContext(), ActivitySearch.class);
                    intent.putExtra(Constant.EXTRA_OBJC, "category");
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getApplicationContext(), ActivitySearch.class);
                    intent.putExtra(Constant.EXTRA_OBJC, "wallpaper");
                    startActivity(intent);
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyBannerAd();
        Constant.isAppOpen = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        adsManager.resumeBannerAd(adsPref.getIsBannerHome());
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    public void destroyBannerAd() {
        adsManager.destroyBannerAd();
    }

    private void inAppReview() {
        if (sharedPref.getInAppReviewToken() <= 3) {
            sharedPref.updateInAppReviewToken(sharedPref.getInAppReviewToken() + 1);
            Log.d(TAG, "in app update token");
        } else {
            ReviewManager manager = ReviewManagerFactory.create(this);
            Task<ReviewInfo> request = manager.requestReviewFlow();
            request.addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    ReviewInfo reviewInfo = task.getResult();
                    manager.launchReviewFlow(MainActivity.this, reviewInfo).addOnFailureListener(e -> {
                    }).addOnCompleteListener(complete -> {
                                Log.d(TAG, "Success");
                            }
                    ).addOnFailureListener(failure -> {
                        Log.d(TAG, "Rating Failed");
                    });
                }
            }).addOnFailureListener(failure -> Log.d(TAG, "In-App Request Failed " + failure));
            Log.d(TAG, "in app token complete, show in app review if available");
        }
        Log.d(TAG, "in app review token : " + sharedPref.getInAppReviewToken());
    }

    private void inAppUpdate() {
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                startUpdateFlow(appUpdateInfo);
            } else if (appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                startUpdateFlow(appUpdateInfo);
            }
        });
    }

    @SuppressWarnings("deprecation")
    private void startUpdateFlow(AppUpdateInfo appUpdateInfo) {
        try {
            appUpdateManager.startUpdateFlowForResult(appUpdateInfo, AppUpdateType.IMMEDIATE, this, Constant.IMMEDIATE_APP_UPDATE_REQ_CODE);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.IMMEDIATE_APP_UPDATE_REQ_CODE) {
            if (resultCode == RESULT_CANCELED) {
                showSnackBar(getString(R.string.msg_cancel_update));
            } else if (resultCode == RESULT_OK) {
                showSnackBar(getString(R.string.msg_success_update));
            } else {
                showSnackBar(getString(R.string.msg_failed_update));
                inAppUpdate();
            }
        }
    }

    public void initExitDialog() {

        lyt_dialog_exit = findViewById(R.id.lyt_dialog_exit);
        lyt_panel_view = findViewById(R.id.lyt_panel_view);
        lyt_panel_dialog = findViewById(R.id.lyt_panel_dialog);

        if (sharedPref.getIsDarkTheme()) {
            lyt_panel_view.setBackgroundColor(getResources().getColor(R.color.color_dialog_background_dark_overlay));
            lyt_panel_dialog.setBackgroundResource(R.drawable.bg_rounded_dark);
        } else {
            lyt_panel_view.setBackgroundColor(getResources().getColor(R.color.color_dialog_background_light));
            lyt_panel_dialog.setBackgroundResource(R.drawable.bg_rounded_light);
        }

        lyt_panel_view.setOnClickListener(view -> {
            //empty state
        });
        Tools.verifyAPI(this);
        LinearLayout nativeAdView = findViewById(R.id.native_ad_view);
        Tools.setNativeAdStyle(this, nativeAdView, adsPref.getNativeAdStyleExitDialog());
        adsManager.loadNativeAd(adsPref.getIsNativeAdExitDialog(), adsPref.getNativeAdStyleExitDialog());

        Button btnCancel = findViewById(R.id.btn_cancel);
        Button btnExit = findViewById(R.id.btn_exit);

        FloatingActionButton btnRate = findViewById(R.id.btn_rate);
        FloatingActionButton btnShare = findViewById(R.id.btn_share);

        btnCancel.setOnClickListener(view -> showDialog(false));

        btnExit.setOnClickListener(view -> {
            showDialog(false);
            Tools.postDelayed(() -> {
                finish();
                destroyBannerAd();
                destroyAppOpenAd();
                if (Config.RESET_REWARDED_AD_HISTORY_ON_EXIT_APP) {
                    if (db.getAllRewarded().size() > 0) {
                        db.deleteAllRewarded();
                    }
                }
            }, 300);
        });

        btnRate.setOnClickListener(v -> {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID)));
            showDialog(false);
        });

        btnShare.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
            intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_text) + "\n" + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
            intent.setType("text/plain");
            startActivity(intent);
            showDialog(false);
        });
    }

    private void showDialog(boolean show) {
        if (show) {
            lyt_dialog_exit.setVisibility(View.VISIBLE);
            slideUp(findViewById(R.id.dialog_card_view));
            ObjectAnimator.ofFloat(lyt_dialog_exit, View.ALPHA, 0.1f, 1.0f).setDuration(300).start();
            Tools.fullScreenMode(this, true);
        } else {
            slideDown(findViewById(R.id.dialog_card_view));
            ObjectAnimator.ofFloat(lyt_dialog_exit, View.ALPHA, 1.0f, 0.1f).setDuration(300).start();
            Tools.postDelayed(() -> {
                lyt_dialog_exit.setVisibility(View.INVISIBLE);
                Tools.fullScreenMode(this, false);
                Tools.setNavigation(this);
            }, 300);
        }
    }

    public void slideUp(View view) {
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(0, 0, findViewById(R.id.main_content).getHeight(), 0);
        animate.setDuration(300);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    public void slideDown(View view) {
        TranslateAnimation animate = new TranslateAnimation(0, 0, 0, findViewById(R.id.main_content).getHeight());
        animate.setDuration(300);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    public void selectWallpaper() {
        if (Config.SET_CATEGORY_AS_MAIN_SCREEN) {
            if (Config.ENABLE_RTL_MODE) {
                viewPagerRTL.setCurrentItem(1);
            } else {
                viewPager.setCurrentItem(1);
            }
        } else {
            if (Config.ENABLE_RTL_MODE) {
                viewPagerRTL.setCurrentItem(0);
            } else {
                viewPager.setCurrentItem(0);
            }
        }
    }

    public void selectCategory() {
        if (Config.SET_CATEGORY_AS_MAIN_SCREEN) {
            if (Config.ENABLE_RTL_MODE) {
                viewPagerRTL.setCurrentItem(0);
            } else {
                viewPager.setCurrentItem(0);
            }
        } else {
            if (Config.ENABLE_RTL_MODE) {
                viewPagerRTL.setCurrentItem(1);
            } else {
                viewPager.setCurrentItem(1);
            }
        }
    }

}