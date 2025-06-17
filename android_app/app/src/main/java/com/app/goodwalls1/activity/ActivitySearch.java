package com.app.goodwalls1.activity;

import static com.app.goodwalls1.util.Constant.EXTRA_OBJC;
import static com.solodroid.ads.sdk.util.Constant.ADMOB;
import static com.solodroid.ads.sdk.util.Constant.APPLOVIN;
import static com.solodroid.ads.sdk.util.Constant.APPLOVIN_DISCOVERY;
import static com.solodroid.ads.sdk.util.Constant.APPLOVIN_MAX;
import static com.solodroid.ads.sdk.util.Constant.FAN;
import static com.solodroid.ads.sdk.util.Constant.GOOGLE_AD_MANAGER;
import static com.solodroid.ads.sdk.util.Constant.STARTAPP;
import static com.solodroid.ads.sdk.util.Constant.WORTISE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.app.goodwalls1.Config;
import com.app.goodwalls1.R;
import com.app.goodwalls1.adapter.AdapterCategory;
import com.app.goodwalls1.adapter.AdapterSearch;
import com.app.goodwalls1.adapter.AdapterWallpaper;
import com.app.goodwalls1.callback.CallbackCategory;
import com.app.goodwalls1.callback.CallbackWallpaper;
import com.app.goodwalls1.component.ItemOffsetDecoration;
import com.app.goodwalls1.database.prefs.AdsPref;
import com.app.goodwalls1.database.prefs.SharedPref;
import com.app.goodwalls1.model.Category;
import com.app.goodwalls1.model.Wallpaper;
import com.app.goodwalls1.rest.ApiInterface;
import com.app.goodwalls1.rest.RestAdapter;
import com.app.goodwalls1.util.AdsManager;
import com.app.goodwalls1.util.Constant;
import com.app.goodwalls1.util.Tools;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivitySearch extends AppCompatActivity {

    private EditText edtSearch;
    private EditText edtIndex;
    private RecyclerView recyclerViewWallpaper;
    private RecyclerView recyclerViewCategory;
    private RecyclerView recyclerViewSuggestion;
    private AdapterWallpaper adapterWallpaper;
    private AdapterCategory adapterCategory;
    private AdapterSearch adapterSearch;
    private LinearLayout lytSuggestion;
    private ImageButton btClear;
    private ShimmerFrameLayout lytShimmer;
    private int postTotal = 0;
    private int failedPage = 0;
    Call<CallbackWallpaper> callbackCallWallpaper = null;
    Call<CallbackCategory> callbackCallCategory = null;
    String tags = "";
    List<Wallpaper> wallpapers = new ArrayList<>();
    List<Category> categories = new ArrayList<>();
    SharedPref sharedPref;
    AdsPref adsPref;
    CoordinatorLayout parentView;
    RadioGroup radioGroupSearch;
    String data;
    String flagType;
    AdsManager adsManager;
    LinearLayout lytBannerAd;
    ViewStub viewStubWallpaper;
    ViewStub viewStubCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Tools.getTheme(this);
        setContentView(R.layout.activity_search);
        Tools.setNavigation(this);
        adsPref = new AdsPref(this);
        sharedPref = new SharedPref(this);
        lytShimmer = findViewById(R.id.shimmer_view_container);
        viewStubWallpaper = findViewById(R.id.lytShimmerViewWallpaper);
        viewStubCategory = findViewById(R.id.lytShimmerViewCategory);
        initShimmerLayout();
        initComponent();
        setupToolbar();

        adsManager = new AdsManager(this);
        adsManager.loadBannerAd(adsPref.getIsBannerSearch());
        adsManager.loadInterstitialAd(adsPref.getIsInterstitialPostList(), adsPref.getInterstitialAdInterval());
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initComponent() {

        Intent intent = getIntent();
        data = intent.getStringExtra(Constant.EXTRA_OBJC);

        parentView = findViewById(R.id.coordinatorLayout);
        radioGroupSearch = findViewById(R.id.radioGroupSearch);
        edtIndex = findViewById(R.id.edt_index);

        initRecyclerView();

        lytBannerAd = findViewById(R.id.lyt_banner_ad);

        lytSuggestion = findViewById(R.id.lyt_suggestion);
        if (sharedPref.getIsDarkTheme()) {
            lytSuggestion.setBackgroundColor(getResources().getColor(R.color.color_dark_background));
        } else {
            lytSuggestion.setBackgroundColor(getResources().getColor(R.color.color_light_background));
        }

        edtSearch = findViewById(R.id.et_search);
        btClear = findViewById(R.id.bt_clear);
        btClear.setVisibility(View.GONE);

        edtSearch.addTextChangedListener(textWatcher);
        if (getIntent().hasExtra("tags")) {
            tags = getIntent().getStringExtra("tags");
            Tools.postDelayed(() -> {
                hideKeyboard();
                searchActionTags(1);
            }, 100);
        } else {
            edtSearch.requestFocus();
            swipeProgress(false);
        }

        recyclerViewSuggestion.setLayoutManager(new LinearLayoutManager(this));

        adapterSearch = new AdapterSearch(this);
        recyclerViewSuggestion.setAdapter(adapterSearch);
        showSuggestionSearch();

        adapterSearch.setOnItemClickListener((view, viewModel, pos) -> {
            edtSearch.setText(viewModel);
            edtSearch.setSelection(viewModel.length());
            lytSuggestion.setVisibility(View.GONE);
            hideKeyboard();
            if (radioGroupSearch.getCheckedRadioButtonId() == R.id.radio_button_wallpaper) {
                recyclerViewWallpaper.setVisibility(View.VISIBLE);
                recyclerViewCategory.setVisibility(View.GONE);
                adapterWallpaper.resetListData();
                hideKeyboard();
                searchActionWallpaper(1);
            } else if (radioGroupSearch.getCheckedRadioButtonId() == R.id.radio_button_category) {
                recyclerViewWallpaper.setVisibility(View.GONE);
                recyclerViewCategory.setVisibility(View.VISIBLE);
                adapterCategory.resetListData();
                hideKeyboard();
                searchActionCategory();
            }
        });

        adapterSearch.setOnItemActionClickListener((view, viewModel, pos) -> {
            edtSearch.setText(viewModel);
            edtSearch.setSelection(viewModel.length());
        });

        btClear.setOnClickListener(view -> edtSearch.setText(""));

        edtSearch.setOnTouchListener((view, motionEvent) -> {
            if (adapterSearch.getItemCount() > 0) {
                showSuggestionSearch();
            } else {
                lytSuggestion.setVisibility(View.GONE);
                showEmptySearch();
            }
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            return false;
        });

        if (adapterSearch.getItemCount() <= 0) {
            lytSuggestion.setVisibility(View.GONE);
            showEmptySearch();
        }

        if (data.equals("category")) {
            radioGroupSearch.check(radioGroupSearch.getChildAt(1).getId());
            requestSearchCategory();
        } else {
            radioGroupSearch.check(radioGroupSearch.getChildAt(0).getId());
            requestSearchWallpaper();
        }

        flagType = edtIndex.getText().toString();
        edtIndex.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                flagType = edtIndex.getText().toString();
                showKeyboard();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        radioGroupSearch.setOnCheckedChangeListener((radioGroup, i) -> {
            if (i == R.id.radio_button_wallpaper) {
                requestSearchWallpaper();
                recyclerViewWallpaper.setVisibility(View.VISIBLE);
                recyclerViewCategory.setVisibility(View.GONE);
                if (adapterSearch.getItemCount() <= 0) {
                    lytSuggestion.setVisibility(View.GONE);
                    showEmptySearch();
                } else {
                    findViewById(R.id.lyt_no_item).setVisibility(View.GONE);
                }
            } else if (i == R.id.radio_button_category) {
                requestSearchCategory();
                recyclerViewWallpaper.setVisibility(View.GONE);
                recyclerViewCategory.setVisibility(View.VISIBLE);
                if (adapterSearch.getItemCount() <= 0) {
                    lytSuggestion.setVisibility(View.GONE);
                    showEmptySearch();
                } else {
                    findViewById(R.id.lyt_no_item).setVisibility(View.GONE);
                }
            }
        });

    }

    public void initRecyclerView() {
        recyclerViewWallpaper = findViewById(R.id.recycler_view_wallpaper);
        recyclerViewCategory = findViewById(R.id.recycler_view_category);
        recyclerViewSuggestion = findViewById(R.id.recycler_view_suggestion);
    }

    public void requestSearchWallpaper() {
        edtIndex.setText("0");
        recyclerViewWallpaper.setVisibility(View.VISIBLE);
        recyclerViewCategory.setVisibility(View.GONE);

        viewStubWallpaper.setVisibility(View.VISIBLE);
        viewStubCategory.setVisibility(View.GONE);

        recyclerViewWallpaper.setLayoutManager(new StaggeredGridLayoutManager(sharedPref.getWallpaperSpanCount(), StaggeredGridLayoutManager.VERTICAL));

        adapterWallpaper = new AdapterWallpaper(this, recyclerViewWallpaper, wallpapers);
        recyclerViewWallpaper.setAdapter(adapterWallpaper);

        setOnWallpaperClickListener(true);

        recyclerViewWallpaper.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

        edtSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (edtSearch.getText().toString().equals("")) {
                    Snackbar.make(parentView, getString(R.string.msg_search_input), Snackbar.LENGTH_SHORT).show();
                    hideKeyboard();
                    swipeProgress(false);
                } else {
                    adapterWallpaper.resetListData();
                    hideKeyboard();
                    searchActionWallpaper(1);
                }
                return true;
            }
            return false;
        });

    }

    private void setOnWallpaperClickListener(boolean click) {
        if (click) {
            adapterWallpaper.setOnItemClickListener((view, obj, position) -> {
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
            searchActionWallpaper(next_page);
        } else {
            adapterWallpaper.setLoaded();
        }
    }

    public void setLoadMore(int current_page) {
        if (postTotal > adapterWallpaper.getItemCount() && current_page != 0) {
            int next_page = current_page + 1;
            searchActionWallpaper(next_page);
        } else {
            adapterWallpaper.setLoaded();
        }
    }

    public void requestSearchCategory() {
        edtIndex.setText("1");
        recyclerViewWallpaper.setVisibility(View.GONE);
        recyclerViewCategory.setVisibility(View.VISIBLE);

        viewStubWallpaper.setVisibility(View.GONE);
        viewStubCategory.setVisibility(View.VISIBLE);

        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.grid_space_wallpaper);
        int padding = getResources().getDimensionPixelOffset(R.dimen.grid_space_wallpaper);
        recyclerViewCategory.setPadding(padding, padding, padding, padding);
        recyclerViewCategory.setLayoutManager(new StaggeredGridLayoutManager(sharedPref.getCategorySpanCount(), LinearLayoutManager.VERTICAL));
        if (0 == recyclerViewCategory.getItemDecorationCount()) {
            recyclerViewCategory.addItemDecoration(itemDecoration);
        }

        adapterCategory = new AdapterCategory(this, categories);
        recyclerViewCategory.setAdapter(adapterCategory);

        adapterCategory.setOnItemClickListener((v, obj, position) -> {
            Intent intent = new Intent(getApplicationContext(), ActivityCategoryDetails.class);
            intent.putExtra(EXTRA_OBJC, obj);
            intent.putExtra("filter", Constant.DEFAULT_FILTER);
            intent.putExtra("order", Constant.DEFAULT_ORDER);
            startActivity(intent);
            adsManager.showInterstitialAd();
        });

        edtSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (edtSearch.getText().toString().equals("")) {
                    Snackbar.make(parentView, getString(R.string.msg_search_input), Snackbar.LENGTH_SHORT).show();
                    hideKeyboard();
                    swipeProgress(false);
                } else {
                    adapterCategory.resetListData();
                    hideKeyboard();
                    searchActionCategory();
                }
                return true;
            }
            return false;
        });

    }

    private void setupToolbar() {
        Tools.setupToolbar(this, findViewById(R.id.appbar_layout), findViewById(R.id.toolbar), "", true);
        if (sharedPref.getIsDarkTheme()) {
            edtSearch.setTextColor(ContextCompat.getColor(this, R.color.color_white));
            btClear.setColorFilter(ContextCompat.getColor(this, R.color.color_dark_icon), PorterDuff.Mode.SRC_IN);
            lytSuggestion.setBackgroundColor(ContextCompat.getColor(this, R.color.color_dark_background));
        } else {
            btClear.setColorFilter(ContextCompat.getColor(this, R.color.color_light_icon), PorterDuff.Mode.SRC_IN);
            lytSuggestion.setBackgroundColor(ContextCompat.getColor(this, R.color.color_light_background));
        }
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence c, int i, int i1, int i2) {
            if (c.toString().trim().length() == 0) {
                btClear.setVisibility(View.GONE);
            } else {
                btClear.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void beforeTextChanged(CharSequence c, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    };

    private void requestSearchApiWallpaper(final int page_no, final String query) {
        ApiInterface apiInterface = RestAdapter.createAPI(sharedPref.getBaseUrl());

        if (sharedPref.getWallpaperSpanCount() == 3) {
            callbackCallWallpaper = apiInterface.getSearch(page_no, Constant.LOAD_MORE_3_COLUMNS, query, "recent");
        } else {
            callbackCallWallpaper = apiInterface.getSearch(page_no, Constant.LOAD_MORE_2_COLUMNS, query, "recent");
        }

        callbackCallWallpaper.enqueue(new Callback<CallbackWallpaper>() {
            @Override
            public void onResponse(@NonNull Call<CallbackWallpaper> call, @NonNull Response<CallbackWallpaper> response) {
                CallbackWallpaper resp = response.body();
                if (resp != null && resp.status.equals("ok")) {
                    postTotal = resp.count_total;
                    setOnWallpaperClickListener(true);
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
                                adapterWallpaper.insertDataWithNativeAd(resp.posts);
                                break;
                            default:
                                adapterWallpaper.insertData(resp.posts);
                                break;
                        }
                    } else {
                        adapterWallpaper.insertData(resp.posts);
                    }
                    if (resp.posts.size() == 0) {
                        showNotFoundViewWallpaper(true);
                    } else {
                        lytBannerAd.setVisibility(View.VISIBLE);
                    }
                } else {
                    onFailRequestWallpaper(page_no);
                }
                swipeProgress(false);
            }

            @Override
            public void onFailure(@NonNull Call<CallbackWallpaper> call, @NonNull Throwable t) {
                onFailRequestWallpaper(page_no);
                swipeProgress(false);
            }

        });
    }

    private void requestSearchApiCategory(final String query) {
        ApiInterface apiInterface = RestAdapter.createAPI(sharedPref.getBaseUrl());
        callbackCallCategory = apiInterface.getSearchCategory(query);
        callbackCallCategory.enqueue(new Callback<CallbackCategory>() {
            @Override
            public void onResponse(@NonNull Call<CallbackCategory> call, @NonNull Response<CallbackCategory> response) {
                CallbackCategory resp = response.body();
                if (resp != null && resp.status.equals("ok")) {
                    adapterCategory.insertData(resp.categories);
                    swipeProgress(false);
                    if (resp.categories.size() == 0) {
                        showNotFoundViewCategory(true);
                    } else {
                        lytBannerAd.setVisibility(View.VISIBLE);
                    }
                } else {
                    onFailRequestCategory();
                }
            }

            @Override
            public void onFailure(@NonNull Call<CallbackCategory> call, @NonNull Throwable t) {
                onFailRequestCategory();
                swipeProgress(false);
            }

        });
    }

    private void onFailRequestWallpaper(int page_no) {
        failedPage = page_no;
        adapterWallpaper.setLoaded();
        swipeProgress(false);
        if (Tools.isConnect(this)) {
            showFailedViewWallpaper(true, getString(R.string.failed_text));
        } else {
            showFailedViewWallpaper(true, getString(R.string.failed_text));
        }
    }

    private void onFailRequestCategory() {
        swipeProgress(false);
        if (Tools.isConnect(this)) {
            showFailedViewCategory(true, getString(R.string.failed_text));
        } else {
            showFailedViewCategory(true, getString(R.string.failed_text));
        }
    }

    private void searchActionWallpaper(final int page_no) {
        lytSuggestion.setVisibility(View.GONE);
        showFailedViewWallpaper(false, "");
        showNotFoundViewWallpaper(false);
        setOnWallpaperClickListener(false);
        final String query = edtSearch.getText().toString().trim();
        if (!query.equals("")) {
            if (page_no == 1) {
                swipeProgress(true);
                adapterSearch.addSearchHistory(query);
            } else {
                adapterWallpaper.setLoading();
            }
            Tools.postDelayed(() -> requestSearchApiWallpaper(page_no, query), Constant.DELAY_TIME);
        } else {
            Snackbar.make(parentView, getString(R.string.msg_search_input), Snackbar.LENGTH_SHORT).show();
            swipeProgress(false);
        }
    }

    private void searchActionCategory() {
        lytSuggestion.setVisibility(View.GONE);
        showFailedViewCategory(false, "");
        showNotFoundViewCategory(false);
        final String query = edtSearch.getText().toString().trim();
        if (!query.equals("")) {
            swipeProgress(true);
            adapterSearch.addSearchHistory(query);
            Tools.postDelayed(() -> requestSearchApiCategory(query), Constant.DELAY_TIME);
        } else {
            Snackbar.make(parentView, getString(R.string.msg_search_input), Snackbar.LENGTH_SHORT).show();
            swipeProgress(false);
        }
    }

    private void searchActionTags(final int page_no) {
        lytSuggestion.setVisibility(View.GONE);
        showFailedViewWallpaper(false, "");
        showNotFoundViewWallpaper(false);
        edtSearch.setText(tags);
        final String query = edtSearch.getText().toString().trim();
        if (!query.equals("")) {
            if (page_no == 1) {
                swipeProgress(true);
            } else {
                adapterWallpaper.setLoading();
            }
            Tools.postDelayed(() -> requestSearchApiWallpaper(page_no, query), Constant.DELAY_TIME);
        } else {
            Snackbar.make(parentView, getString(R.string.msg_search_input), Snackbar.LENGTH_SHORT).show();
            swipeProgress(false);
        }
    }

    private void showSuggestionSearch() {
        adapterSearch.refreshItems();
        lytSuggestion.setVisibility(View.VISIBLE);
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void showKeyboard() {
        edtSearch.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    private void showFailedViewWallpaper(boolean show, String message) {
        View lyt_failed = findViewById(R.id.lyt_failed);
        ((TextView) findViewById(R.id.failed_message)).setText(message);
        if (show) {
            recyclerViewWallpaper.setVisibility(View.GONE);
            lyt_failed.setVisibility(View.VISIBLE);
        } else {
            recyclerViewWallpaper.setVisibility(View.VISIBLE);
            lyt_failed.setVisibility(View.GONE);
        }
        findViewById(R.id.failed_retry).setOnClickListener(view -> searchActionWallpaper(failedPage));
    }

    private void showFailedViewCategory(boolean show, String message) {
        View lyt_failed = findViewById(R.id.lyt_failed);
        ((TextView) findViewById(R.id.failed_message)).setText(message);
        if (show) {
            recyclerViewCategory.setVisibility(View.GONE);
            lyt_failed.setVisibility(View.VISIBLE);
        } else {
            recyclerViewCategory.setVisibility(View.VISIBLE);
            lyt_failed.setVisibility(View.GONE);
        }
        findViewById(R.id.failed_retry).setOnClickListener(view -> searchActionCategory());
    }

    private void showNotFoundViewWallpaper(boolean show) {
        View lyt_no_item = findViewById(R.id.lyt_no_item);
        ((TextView) findViewById(R.id.no_item_message)).setText(R.string.no_search_wallpaper_found);
        if (show) {
            recyclerViewWallpaper.setVisibility(View.GONE);
            lyt_no_item.setVisibility(View.VISIBLE);
        } else {
            recyclerViewWallpaper.setVisibility(View.VISIBLE);
            lyt_no_item.setVisibility(View.GONE);
        }
    }

    private void showNotFoundViewCategory(boolean show) {
        View lyt_no_item = findViewById(R.id.lyt_no_item);
        ((TextView) findViewById(R.id.no_item_message)).setText(R.string.no_search_category_found);
        if (show) {
            recyclerViewCategory.setVisibility(View.GONE);
            lyt_no_item.setVisibility(View.VISIBLE);
        } else {
            recyclerViewCategory.setVisibility(View.VISIBLE);
            lyt_no_item.setVisibility(View.GONE);
        }
    }

    private void showEmptySearch() {
        View lytNotFound = findViewById(R.id.lyt_no_item);
        ((TextView) findViewById(R.id.no_item_title)).setText(getString(R.string.search));
        ((TextView) findViewById(R.id.no_item_message)).setText(getString(R.string.msg_search));
        lytNotFound.setVisibility(View.VISIBLE);
    }

    private void swipeProgress(final boolean show) {
        if (!show) {
            lytShimmer.setVisibility(View.GONE);
            lytShimmer.stopShimmer();
            return;
        } else {
            lytShimmer.setVisibility(View.VISIBLE);
            lytShimmer.startShimmer();
        }
    }

    @Override
    public void onBackPressed() {
        if (getIntent().hasExtra("tags")) {
            super.onBackPressed();
            adsManager.destroyBannerAd();
        } else {
            if (edtSearch.length() > 0) {
                edtSearch.setText("");
            } else {
                super.onBackPressed();
                adsManager.destroyBannerAd();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            onBackPressed();
            hideKeyboard();
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
        adsManager.resumeBannerAd(adsPref.getIsBannerSearch());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adsManager.destroyBannerAd();
    }

    private void initShimmerLayout() {
        if (Config.DISPLAY_WALLPAPER == 2) {
            if (sharedPref.getWallpaperSpanCount() == Constant.WALLPAPER_3_COLUMNS) {
                viewStubWallpaper.setLayoutResource(R.layout.shimmer_wallpaper_grid3_square);
            } else {
                viewStubWallpaper.setLayoutResource(R.layout.shimmer_wallpaper_grid2_square);
            }
        } else {
            if (sharedPref.getWallpaperSpanCount() == Constant.WALLPAPER_3_COLUMNS) {
                viewStubWallpaper.setLayoutResource(R.layout.shimmer_wallpaper_grid3_rectangle);
            } else {
                viewStubWallpaper.setLayoutResource(R.layout.shimmer_wallpaper_grid2_rectangle);
            }
        }
        if (sharedPref.getCategorySpanCount() == Constant.CATEGORY_GRID_2) {
            viewStubCategory.setLayoutResource(R.layout.shimmer_category_grid2);
        } else {
            viewStubCategory.setLayoutResource(R.layout.shimmer_category_list);
        }
        viewStubWallpaper.inflate();
        viewStubCategory.inflate();
    }

}
