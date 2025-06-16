package com.app.goodwalls.activity;

import static com.app.goodwalls.util.Constant.EXTRA_OBJC;
import static com.solodroid.ads.sdk.util.Constant.ADMOB;
import static com.solodroid.ads.sdk.util.Constant.APPLOVIN;
import static com.solodroid.ads.sdk.util.Constant.APPLOVIN_DISCOVERY;
import static com.solodroid.ads.sdk.util.Constant.APPLOVIN_MAX;
import static com.solodroid.ads.sdk.util.Constant.FAN;
import static com.solodroid.ads.sdk.util.Constant.GOOGLE_AD_MANAGER;
import static com.solodroid.ads.sdk.util.Constant.STARTAPP;
import static com.solodroid.ads.sdk.util.Constant.WORTISE;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.app.goodwalls.Config;
import com.app.goodwalls.R;
import com.app.goodwalls.adapter.AdapterMenu;
import com.app.goodwalls.adapter.AdapterWallpaper;
import com.app.goodwalls.callback.CallbackWallpaper;
import com.app.goodwalls.database.prefs.AdsPref;
import com.app.goodwalls.database.prefs.SharedPref;
import com.app.goodwalls.model.Category;
import com.app.goodwalls.model.Menu;
import com.app.goodwalls.model.Wallpaper;
import com.app.goodwalls.rest.ApiInterface;
import com.app.goodwalls.rest.RestAdapter;
import com.app.goodwalls.util.AdsManager;
import com.app.goodwalls.util.Constant;
import com.app.goodwalls.util.Tools;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityCategoryDetails extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdapterWallpaper adapterWallpaper;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ShimmerFrameLayout lytShimmer;
    private Call<CallbackWallpaper> callbackCall = null;
    private int postTotal = 0;
    private int failedPage = 0;
    List<Wallpaper> wallpapers = new ArrayList<>();
    Category category;
    SharedPref sharedPref;
    AdsPref adsPref;
    AdsManager adsManager;
    ImageButton btn_search;
    ImageButton btn_sort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Tools.getTheme(this);
        sharedPref = new SharedPref(this);
        adsPref = new AdsPref(this);
        setContentView(R.layout.activity_category_details);
        Tools.setNavigation(this);
        sharedPref.setDefaultSortWallpaper();

        category = (Category) getIntent().getSerializableExtra(EXTRA_OBJC);
        if (getIntent() != null) {
            Constant.FILTER = getIntent().getStringExtra("filter");
            Constant.ORDER = getIntent().getStringExtra("order");
        }
        Constant.LAST_SELECTED_ITEM_POSITION = 0;

        adsManager = new AdsManager(this);
        adsManager.loadBannerAd(adsPref.getIsBannerCategoryDetails());
        adsManager.loadInterstitialAd(adsPref.getIsInterstitialPostList(), adsPref.getInterstitialAdInterval());

        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.color_light_primary);
        lytShimmer = findViewById(R.id.shimmer_view_container);
        initShimmerLayout();

        btn_search = findViewById(R.id.btn_search);
        btn_sort = findViewById(R.id.btn_sort);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(sharedPref.getWallpaperSpanCount(), StaggeredGridLayoutManager.VERTICAL));

        adapterWallpaper = new AdapterWallpaper(this, recyclerView, wallpapers);
        recyclerView.setAdapter(adapterWallpaper);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView v, int state) {
                super.onScrollStateChanged(v, state);
            }
        });

        adapterWallpaper.setOnLoadMoreListener(current_page -> {
            if (adsPref.getIsNativeAdPostList()) {
                switch (adsPref.getMainAds()) {
                    case ADMOB:
                    case GOOGLE_AD_MANAGER:
                    case FAN:
                    case APPLOVIN:
                    case APPLOVIN_MAX:
                    case APPLOVIN_DISCOVERY:
                    case STARTAPP:
                    case WORTISE:
                        setLoadMoreNativeAd(current_page);
                        break;
                    default:
                        setLoadMore(current_page);
                        break;
                }
            } else {
                setLoadMore(current_page);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(() -> {
            if (callbackCall != null && callbackCall.isExecuted()) callbackCall.cancel();
            adapterWallpaper.resetListData();
            requestAction(1);
        });

        requestAction(1);
        setupToolbar();
        onOptionMenuClicked();

    }

    private void setOnWallpaperClickListener(boolean click) {
        if (click) {
            adapterWallpaper.setOnItemClickListener((v, obj, position) -> {
                Tools.openWallpaperDetailActivity(this, wallpapers, position);
                adsManager.showInterstitialAd();
                adsManager.destroyBannerAd();
            });
        } else {
            adapterWallpaper.setOnItemClickListener((view, obj, position) -> {
                //do nothing
            });
        }
    }

    public void setLoadMoreNativeAd(int current_page) {
        int totalItemBeforeAds = (adapterWallpaper.getItemCount() - current_page);
        if (postTotal > totalItemBeforeAds && current_page != 0) {
            int next_page = current_page + 1;
            requestAction(next_page);
        } else {
            adapterWallpaper.setLoaded();
        }
    }

    public void setLoadMore(int current_page) {
        if (postTotal > adapterWallpaper.getItemCount() && current_page != 0) {
            int next_page = current_page + 1;
            requestAction(next_page);
        } else {
            adapterWallpaper.setLoaded();
        }
    }

    private void setupToolbar() {
        Tools.setupToolbar(this, findViewById(R.id.appbar_layout), findViewById(R.id.toolbar), "", true);
        ((TextView) findViewById(R.id.toolbar_title)).setText(Html.fromHtml(category.category_name));
        if (sharedPref.getIsDarkTheme()) {
            btn_sort.setColorFilter(ContextCompat.getColor(this, R.color.color_dark_icon), PorterDuff.Mode.SRC_IN);
            btn_search.setColorFilter(ContextCompat.getColor(this, R.color.color_dark_icon), PorterDuff.Mode.SRC_IN);
        } else {
            btn_sort.setColorFilter(ContextCompat.getColor(this, R.color.color_light_icon), PorterDuff.Mode.SRC_IN);
            btn_search.setColorFilter(ContextCompat.getColor(this, R.color.color_light_icon), PorterDuff.Mode.SRC_IN);
        }
    }

    private void displayApiResult(final List<Wallpaper> wallpapers) {
        insertData(wallpapers);
        swipeProgress(false);
        if (wallpapers.size() == 0) {
            showNoItemView(true);
        }
    }

    private void requestListPostApi(final int page_no) {

        ApiInterface apiInterface = RestAdapter.createAPI(sharedPref.getBaseUrl());

        if (sharedPref.getWallpaperSpanCount() == 3) {
            callbackCall = apiInterface.getWallpapers(page_no, Constant.LOAD_MORE_3_COLUMNS, Constant.FILTER, Constant.ORDER, category.category_id);
        } else {
            callbackCall = apiInterface.getWallpapers(page_no, Constant.LOAD_MORE_2_COLUMNS, Constant.FILTER, Constant.ORDER, category.category_id);
        }

        callbackCall.enqueue(new Callback<CallbackWallpaper>() {
            @Override
            public void onResponse(@NonNull Call<CallbackWallpaper> call, @NonNull Response<CallbackWallpaper> response) {
                CallbackWallpaper resp = response.body();
                if (resp != null && resp.status.equals("ok")) {
                    setOnWallpaperClickListener(true);
                    postTotal = resp.count_total;
                    displayApiResult(resp.posts);
                } else {
                    onFailRequest(page_no);
                }
            }

            @Override
            public void onFailure(@NonNull Call<CallbackWallpaper> call, @NonNull Throwable t) {
                swipeProgress(false);
            }
        });
    }

    private void insertData(List<Wallpaper> wallpapers) {
        if (adsPref.getIsNativeAdPostList()) {
            switch (adsPref.getMainAds()) {
                case ADMOB:
                case GOOGLE_AD_MANAGER:
                case FAN:
                case APPLOVIN:
                case APPLOVIN_MAX:
                case APPLOVIN_DISCOVERY:
                case STARTAPP:
                case WORTISE:
                    adapterWallpaper.insertDataWithNativeAd(wallpapers);
                    break;
                default:
                    adapterWallpaper.insertData(wallpapers);
                    break;
            }
        } else {
            adapterWallpaper.insertData(wallpapers);
        }
    }

    private void onFailRequest(int page_no) {
        failedPage = page_no;
        adapterWallpaper.setLoaded();
        swipeProgress(false);
        showFailedView(true, getString(R.string.failed_text));
    }

    private void requestAction(final int page_no) {
        showFailedView(false, "");
        showNoItemView(false);
        setOnWallpaperClickListener(false);
        if (page_no == 1) {
            swipeProgress(true);
        } else {
            adapterWallpaper.setLoading();
        }
        new Handler(Looper.getMainLooper()).postDelayed(() -> requestListPostApi(page_no), Constant.DELAY_TIME);
    }

    private void showFailedView(boolean show, String message) {
        View lyt_failed = findViewById(R.id.lyt_failed);
        ((TextView) findViewById(R.id.failed_message)).setText(message);
        if (show) {
            recyclerView.setVisibility(View.GONE);
            lyt_failed.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            lyt_failed.setVisibility(View.GONE);
        }
        findViewById(R.id.failed_retry).setOnClickListener(view -> requestAction(failedPage));
    }

    private void showNoItemView(boolean show) {
        View lyt_no_item = findViewById(R.id.lyt_no_item);
        ((TextView) findViewById(R.id.no_item_message)).setText(R.string.msg_no_item);
        if (show) {
            recyclerView.setVisibility(View.GONE);
            lyt_no_item.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            lyt_no_item.setVisibility(View.GONE);
        }
    }

    private void swipeProgress(final boolean show) {
        if (!show) {
            swipeRefreshLayout.setRefreshing(show);
            lytShimmer.setVisibility(View.GONE);
            lytShimmer.stopShimmer();
            return;
        }
        swipeRefreshLayout.post(() -> {
            swipeRefreshLayout.setRefreshing(show);
            lytShimmer.setVisibility(View.VISIBLE);
            lytShimmer.startShimmer();
        });
    }

    private void initShimmerLayout() {
        ViewStub stub = findViewById(R.id.lytShimmerView);
        if (Config.DISPLAY_WALLPAPER == 2) {
            if (sharedPref.getWallpaperSpanCount() == Constant.WALLPAPER_3_COLUMNS) {
                stub.setLayoutResource(R.layout.shimmer_wallpaper_grid3_square);
            } else {
                stub.setLayoutResource(R.layout.shimmer_wallpaper_grid2_square);
            }
        } else {
            if (sharedPref.getWallpaperSpanCount() == Constant.WALLPAPER_3_COLUMNS) {
                stub.setLayoutResource(R.layout.shimmer_wallpaper_grid3_rectangle);
            } else {
                stub.setLayoutResource(R.layout.shimmer_wallpaper_grid2_rectangle);
            }
        }
        stub.inflate();
    }

    public void onOptionMenuClicked() {

        btn_search.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ActivitySearch.class);
            intent.putExtra(Constant.EXTRA_OBJC, "wallpaper");
            startActivity(intent);
        });

        List<Menu> menus = sharedPref.getMenuList();
        if (menus != null && menus.size() > 1) {
            btn_sort.setOnClickListener(v -> {
                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(ActivityCategoryDetails.this);
                View view = layoutInflaterAndroid.inflate(R.layout.dialog_sort, null);

                RecyclerView recyclerView = view.findViewById(R.id.recycler_view_sort);
                recyclerView.setLayoutManager(new LinearLayoutManager(ActivityCategoryDetails.this));
                AdapterMenu adapterMenu = new AdapterMenu(ActivityCategoryDetails.this, new ArrayList<>());

                List<Menu> filteredList = new ArrayList<>();
                for (Menu menu : menus) {
                    if (menu.menu_category.equals("0")) {
                        filteredList.add(menu);
                    }
                }
                adapterMenu.setListData(filteredList);

                recyclerView.setAdapter(adapterMenu);
                recyclerView.postDelayed(() -> Objects.requireNonNull(recyclerView.findViewHolderForAdapterPosition(Constant.LAST_SELECTED_ITEM_POSITION)).itemView.performClick(), 0);

                final MaterialAlertDialogBuilder alert = new MaterialAlertDialogBuilder(ActivityCategoryDetails.this);
                alert.setView(view);
                alert.setCancelable(false);
                alert.setPositiveButton(R.string.dialog_option_ok, (dialog, which) -> {
                    if (callbackCall != null && callbackCall.isExecuted()) callbackCall.cancel();
                    adapterWallpaper.resetListData();
                    requestAction(1);
                    dialog.dismiss();
                });
                alert.setNegativeButton(R.string.dialog_option_cancel, (dialog, i) -> {
                    Constant.ORDER = "recent";
                    Constant.FILTER = "both";
                    Constant.LAST_SELECTED_ITEM_POSITION = 0;
                    dialog.dismiss();
                });
                alert.show();
            });
        } else {
            btn_sort.setVisibility(View.GONE);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        adsManager.destroyBannerAd();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        adsManager.resumeBannerAd(adsPref.getIsBannerCategoryDetails());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        swipeProgress(false);
        if (callbackCall != null && callbackCall.isExecuted()) {
            callbackCall.cancel();
        }
        lytShimmer.stopShimmer();
        adsManager.destroyBannerAd();
    }

}
