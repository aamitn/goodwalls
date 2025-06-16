package com.app.goodwalls.fragment;

import static com.app.goodwalls.util.Constant.EXTRA_OBJC;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.app.goodwalls.R;
import com.app.goodwalls.activity.ActivityCategoryDetails;
import com.app.goodwalls.activity.MainActivity;
import com.app.goodwalls.adapter.AdapterCategory;
import com.app.goodwalls.callback.CallbackCategory;
import com.app.goodwalls.component.ItemOffsetDecoration;
import com.app.goodwalls.database.prefs.AdsPref;
import com.app.goodwalls.database.prefs.SharedPref;
import com.app.goodwalls.model.Category;
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

public class FragmentCategory extends Fragment {

    private View rootView;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ShimmerFrameLayout lytShimmer;
    private AdapterCategory adapterCategory;
    private Call<CallbackCategory> callbackCall = null;
    private SharedPref sharedPref;
    //    DBHelper dbHelper;
    LinearLayout parentView;
    AdsPref adsPref;
    AdsManager adsManager;
    Activity activity;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (Activity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_category, container, false);

        sharedPref = new SharedPref(activity);
        adsPref = new AdsPref(activity);
        adsManager = new AdsManager(activity);

        parentView = rootView.findViewById(R.id.parent_view);
        swipeRefreshLayout = rootView.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.color_light_primary);
        lytShimmer = rootView.findViewById(R.id.shimmer_view_container);

        recyclerView = rootView.findViewById(R.id.recyclerView);
        int padding = getResources().getDimensionPixelOffset(R.dimen.grid_space_wallpaper);
        recyclerView.setPadding(padding, padding, padding, padding);

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(sharedPref.getCategorySpanCount(), LinearLayoutManager.VERTICAL));
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(activity, R.dimen.grid_space_wallpaper);
        recyclerView.addItemDecoration(itemDecoration);

        adapterCategory = new AdapterCategory(activity, new ArrayList<>());
        recyclerView.setAdapter(adapterCategory);

        adapterCategory.setOnItemClickListener((v, obj, position) -> {
            Intent intent = new Intent(activity, ActivityCategoryDetails.class);
            intent.putExtra(EXTRA_OBJC, obj);
            intent.putExtra("filter", Constant.DEFAULT_FILTER);
            intent.putExtra("order", Constant.DEFAULT_ORDER);
            startActivity(intent);

            ((MainActivity) activity).showInterstitialAd();
            ((MainActivity) activity).destroyBannerAd();
        });

        swipeRefreshLayout.setOnRefreshListener(() -> {
            adapterCategory.resetListData();
            requestAction();
        });

        requestAction();
        initShimmerLayout();

        return rootView;
    }

    private void displayApiResult(final List<Category> categories) {
        adapterCategory.setListData(categories);
        swipeProgress(false);
        if (categories.size() == 0) {
            showNoItemView(true);
        }
    }

    private void requestCategoriesApi() {
        ApiInterface apiInterface = RestAdapter.createAPI(sharedPref.getBaseUrl());
        callbackCall = apiInterface.getCategories();
        callbackCall.enqueue(new Callback<CallbackCategory>() {
            @Override
            public void onResponse(@NonNull Call<CallbackCategory> call, @NonNull Response<CallbackCategory> response) {
                CallbackCategory resp = response.body();
                if (resp != null && resp.status.equals("ok")) {
                    displayApiResult(resp.categories);
                } else {
                    onFailRequest();
                }
            }

            @Override
            public void onFailure(@NonNull Call<CallbackCategory> call, @NonNull Throwable t) {
                swipeProgress(false);
                onFailRequest();
            }

        });
    }

    private void onFailRequest() {
        swipeProgress(false);
        if (Tools.isConnect(activity)) {
            showFailedView(true, getString(R.string.failed_text));
        } else {
            showFailedView(true, getString(R.string.failed_text));
        }
    }

    private void requestAction() {
        showFailedView(false, "");
        swipeProgress(true);
        showNoItemView(false);
        new Handler(Looper.getMainLooper()).postDelayed(this::requestCategoriesApi, Constant.DELAY_TIME);
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

    private void showFailedView(boolean flag, String message) {
        View lyt_failed = rootView.findViewById(R.id.lyt_failed_category);
        ((TextView) rootView.findViewById(R.id.failed_message)).setText(message);
        if (flag) {
            recyclerView.setVisibility(View.GONE);
            lyt_failed.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            lyt_failed.setVisibility(View.GONE);
        }
        rootView.findViewById(R.id.failed_retry).setOnClickListener(view -> requestAction());
    }

    private void showNoItemView(boolean show) {
        View lyt_no_item = rootView.findViewById(R.id.lyt_no_item_category);
        ((TextView) rootView.findViewById(R.id.no_item_message)).setText(R.string.no_category_found);
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
        if (sharedPref.getCategorySpanCount() == Constant.CATEGORY_GRID_2) {
            stub.setLayoutResource(R.layout.shimmer_category_grid2);
        } else {
            stub.setLayoutResource(R.layout.shimmer_category_list);
        }
        stub.inflate();
    }

}
