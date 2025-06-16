package com.app.goodwalls.fragment;

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
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.app.goodwalls.Config;
import com.app.goodwalls.R;
import com.app.goodwalls.activity.ActivityWallpaperDetail;
import com.app.goodwalls.activity.MainActivity;
import com.app.goodwalls.adapter.AdapterWallpaper;
import com.app.goodwalls.callback.CallbackWallpaper;
import com.app.goodwalls.database.prefs.AdsPref;
import com.app.goodwalls.database.prefs.SharedPref;
import com.app.goodwalls.model.Wallpaper;
import com.app.goodwalls.rest.ApiInterface;
import com.app.goodwalls.rest.RestAdapter;
import com.app.goodwalls.util.AdsManager;
import com.app.goodwalls.util.Constant;
import com.app.goodwalls.util.Tools;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentWallpaperLegacy extends Fragment {

    public static final String ARG_ORDER = "order";
    public static final String ARG_FILTER = "filter";
    public static final String ARG_CATEGORY = "category";
    View rootView;
    private RecyclerView recyclerView;
    private AdapterWallpaper adapterWallpaper;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ShimmerFrameLayout lytShimmer;
    private Call<CallbackWallpaper> callbackCall = null;
    private int postTotal = 0;
    private int failedPage = 0;
    private SharedPref sharedPref;
    private AdsPref adsPref;
    List<Wallpaper> wallpapers = new ArrayList<>();
    String order, filter, category;
    AdsManager adsManager;
    Activity activity;
    int wallpaperColumnCount;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (Activity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        filter = getArguments() != null ? getArguments().getString(ARG_FILTER) : "";
        order = getArguments() != null ? getArguments().getString(ARG_ORDER) : "";
        category = getArguments() != null ? getArguments().getString(ARG_CATEGORY) : "";
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_wallpaper_legacy, container, false);
        setHasOptionsMenu(true);

        sharedPref = new SharedPref(activity);
        wallpaperColumnCount = sharedPref.getWallpaperSpanCount();
        adsPref = new AdsPref(activity);
        adsManager = new AdsManager(activity);

        swipeRefreshLayout = rootView.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.color_light_primary);
        lytShimmer = rootView.findViewById(R.id.shimmer_view_container);
        initShimmerLayout();

        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(wallpaperColumnCount, StaggeredGridLayoutManager.VERTICAL));

        adapterWallpaper = new AdapterWallpaper(activity, recyclerView, wallpapers);
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

        return rootView;
    }

    private void setOnWallpaperClickListener(boolean click) {
        if (click) {
            adapterWallpaper.setOnItemClickListener((v, obj, position) -> {
                Constant.wallpapers.clear();
                Constant.wallpapers.addAll(wallpapers);
                Constant.position = position;
                Intent intent = new Intent(activity, ActivityWallpaperDetail.class);
                startActivity(intent);
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

    private void displayApiResult(final List<Wallpaper> wallpapers) {
        insertData(wallpapers);
        swipeProgress(false);
        if (wallpapers.size() == 0) {
            showNoItemView(true);
        }
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
                    setOnWallpaperClickListener(true);
                    postTotal = resp.count_total;
                    displayApiResult(resp.posts);
                } else {
                    onFailRequest(page_no);
                }
            }

            @Override
            public void onFailure(@NonNull Call<CallbackWallpaper> call, @NonNull Throwable t) {
                Log.d("FragmentWallpaper", "failure: " + t.getMessage());
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