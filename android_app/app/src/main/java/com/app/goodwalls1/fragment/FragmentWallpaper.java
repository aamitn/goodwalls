package com.app.goodwalls1.fragment;

import static com.solodroid.ads.sdk.util.Constant.ADMOB;
import static com.solodroid.ads.sdk.util.Constant.APPLOVIN;
import static com.solodroid.ads.sdk.util.Constant.APPLOVIN_DISCOVERY;
import static com.solodroid.ads.sdk.util.Constant.APPLOVIN_MAX;
import static com.solodroid.ads.sdk.util.Constant.FAN;
import static com.solodroid.ads.sdk.util.Constant.GOOGLE_AD_MANAGER;
import static com.solodroid.ads.sdk.util.Constant.STARTAPP;
import static com.solodroid.ads.sdk.util.Constant.WORTISE;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.app.goodwalls1.Config;
import com.app.goodwalls1.R;
import com.app.goodwalls1.activity.MainActivity;
import com.app.goodwalls1.adapter.AdapterFeatured;
import com.app.goodwalls1.adapter.AdapterWallpaper;
import com.app.goodwalls1.callback.CallbackWallpaper;
import com.app.goodwalls1.database.prefs.AdsPref;
import com.app.goodwalls1.database.prefs.SharedPref;
import com.app.goodwalls1.model.Menu;
import com.app.goodwalls1.model.Wallpaper;
import com.app.goodwalls1.rest.ApiInterface;
import com.app.goodwalls1.rest.RestAdapter;
import com.app.goodwalls1.util.AdsManager;
import com.app.goodwalls1.util.Constant;
import com.app.goodwalls1.util.Tools;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentWallpaper extends Fragment {

    public static final String ARG_ORDER = "order";
    public static final String ARG_FILTER = "filter";
    public static final String ARG_CATEGORY = "category";
    View rootView;
    private RecyclerView recyclerView;
    private RecyclerView recyclerViewMenu;
    private AdapterWallpaper adapterWallpaper;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ShimmerFrameLayout lytShimmer;
    private Call<CallbackWallpaper> callbackCall = null;
    private int postTotal = 0;
    private int failedPage = 0;
    private SharedPref sharedPref;
    private AdsPref adsPref;
    List<Wallpaper> wallpapers;
    String order;
    String filter;
    String category;
    AdsManager adsManager;
    Activity activity;
    int wallpaperColumnCount;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (Activity) context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_wallpaper, container, false);

        wallpapers = new ArrayList<>();
        sharedPref = new SharedPref(activity);
        wallpaperColumnCount = sharedPref.getWallpaperSpanCount();
        adsPref = new AdsPref(activity);
        adsManager = new AdsManager(activity);

        swipeRefreshLayout = rootView.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.color_light_primary);
        lytShimmer = rootView.findViewById(R.id.shimmer_view_container);
        initShimmerLayout();

        recyclerViewMenu = rootView.findViewById(R.id.recycler_view_menu);

        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(wallpaperColumnCount, StaggeredGridLayoutManager.VERTICAL));
        adapterWallpaper = new AdapterWallpaper(activity, recyclerView, new ArrayList<>());
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
            if (callbackCall != null && callbackCall.isExecuted()) {
                callbackCall.cancel();
            }
            adapterWallpaper.resetListData();
            requestAction(1);
        });

        initMenu();

        return rootView;
    }

    private void initMenu() {
        if (sharedPref.getMenuList() != null) {
            List<Menu> menus = sharedPref.getMenuList();
            if (menus.size() > 0) {
                if (menus.size() == 1) {
                    filter = menus.get(0).menu_filter;
                    order = menus.get(0).menu_order;
                    category = menus.get(0).menu_category;
                    requestAction(1);
                } else {
                    recyclerViewMenu.setVisibility(View.VISIBLE);
                    AdapterFeatured adapterCategoryFeatured = new AdapterFeatured(activity, new ArrayList<>());
                    adapterCategoryFeatured.setListData(sharedPref.getMenuList());
                    recyclerViewMenu.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
                    recyclerViewMenu.setAdapter(adapterCategoryFeatured);
                    recyclerViewMenu.postDelayed(() -> Objects.requireNonNull(recyclerViewMenu.findViewHolderForAdapterPosition(0)).itemView.performClick(), 10);
                    adapterCategoryFeatured.setOnItemClickListener((view, menu, position) -> {
                        if (position != Constant.CURRENT_POSITION) {
                            if (callbackCall != null && callbackCall.isExecuted()) {
                                callbackCall.cancel();
                            }
                            adapterWallpaper.resetListData();
                            filter = menu.menu_filter;
                            order = menu.menu_order;
                            category = menu.menu_category;
                            requestAction(1);
                        }
                    });
                }
                Constant.CURRENT_POSITION = -1;
            } else {
                recyclerViewMenu.setVisibility(View.GONE);
                filter = Constant.Filter.WALLPAPER;
                order = Constant.Order.RECENT;
                category = "0";
                requestAction(1);
            }
        } else {
            recyclerViewMenu.setVisibility(View.GONE);
        }
    }

    private void setOnWallpaperClickListener(boolean click) {
        if (click) {
            adapterWallpaper.setOnItemClickListener((v, obj, position) -> {
                Tools.openWallpaperDetailActivity(activity, wallpapers, position);
                ((MainActivity) activity).showInterstitialAd();
                ((MainActivity) activity).destroyBannerAd();
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

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }

    private void requestApi(final int page_no) {
        ApiInterface apiInterface = RestAdapter.createAPI(sharedPref.getBaseUrl());

        if (wallpaperColumnCount == 3) {
            callbackCall = apiInterface.getWallpapers(page_no, Constant.LOAD_MORE_3_COLUMNS, filter, order, category);
        } else {
            callbackCall = apiInterface.getWallpapers(page_no, Constant.LOAD_MORE_2_COLUMNS, filter, order, category);
        }

        callbackCall.enqueue(new Callback<CallbackWallpaper>() {
            @Override
            public void onResponse(@NonNull Call<CallbackWallpaper> call, @NonNull Response<CallbackWallpaper> response) {
                CallbackWallpaper resp = response.body();
                if (resp != null && resp.status.equals("ok")) {
                    postTotal = resp.count_total;
                    insertData(resp.posts);
                    swipeProgress(false);
                    if (resp.posts.size() == 0) {
                        showNoItemView(true);
                    }
                    for (int i = 0; i < resp.posts.size(); i++) {
                        wallpapers.add(new Wallpaper(
                                System.currentTimeMillis(),
                                resp.posts.get(i).image_id,
                                resp.posts.get(i).image_name,
                                resp.posts.get(i).image_thumb,
                                resp.posts.get(i).image_upload,
                                resp.posts.get(i).image_url,
                                resp.posts.get(i).type,
                                resp.posts.get(i).resolution,
                                resp.posts.get(i).size,
                                resp.posts.get(i).mime,
                                resp.posts.get(i).views,
                                resp.posts.get(i).downloads,
                                resp.posts.get(i).featured,
                                resp.posts.get(i).tags,
                                resp.posts.get(i).category_id,
                                resp.posts.get(i).category_name,
                                resp.posts.get(i).last_update,
                                resp.posts.get(i).rewarded
                        ));
                    }
                    setOnWallpaperClickListener(true);
                } else {
                    onFailRequest(page_no);
                }
            }

            @Override
            public void onFailure(@NonNull Call<CallbackWallpaper> call, @NonNull Throwable t) {
                swipeProgress(false);
                onFailRequest(page_no);
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

    public void requestAction(final int page_no) {
        showFailedView(false, "");
        showNoItemView(false);
        setOnWallpaperClickListener(false);
        if (page_no == 1) {
            swipeProgress(true);
            wallpapers.clear();
            Constant.wallpapers.clear();
        } else {
            adapterWallpaper.setLoading();
        }
        Tools.postDelayed(() -> requestApi(page_no), Constant.DELAY_TIME);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        swipeProgress(false);
        if (callbackCall != null && callbackCall.isExecuted()) {
            callbackCall.cancel();
        }
        lytShimmer.stopShimmer();
    }

    private void showFailedView(boolean show, String message) {
        View lyt_failed = rootView.findViewById(R.id.lyt_failed);
        ((TextView) rootView.findViewById(R.id.failed_message)).setText(message);
        if (show) {
            recyclerView.setVisibility(View.GONE);
            lyt_failed.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            lyt_failed.setVisibility(View.GONE);
        }
        rootView.findViewById(R.id.failed_retry).setOnClickListener(view -> requestAction(failedPage));
    }

    private void showNoItemView(boolean show) {
        View lyt_no_item = rootView.findViewById(R.id.lyt_no_item);
        ((TextView) rootView.findViewById(R.id.no_item_message)).setText(R.string.msg_no_item);
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
        ViewStub stub = rootView.findViewById(R.id.lytShimmerView);
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

    @Override
    public void onResume() {
        super.onResume();
    }

}