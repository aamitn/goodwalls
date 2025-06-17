package com.app.goodwalls1.activity;

import static com.app.goodwalls1.util.Constant.APPLY;
import static com.app.goodwalls1.util.Constant.BOTH;
import static com.app.goodwalls1.util.Constant.DOWNLOAD;
import static com.app.goodwalls1.util.Constant.HOME_SCREEN;
import static com.app.goodwalls1.util.Constant.LOCK_SCREEN;
import static com.app.goodwalls1.util.Constant.SET_CROP;
import static com.app.goodwalls1.util.Constant.SET_GIF;
import static com.app.goodwalls1.util.Constant.SET_MP4;
import static com.app.goodwalls1.util.Constant.SET_WITH;
import static com.app.goodwalls1.util.Constant.SHARE;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.app.goodwalls1.BuildConfig;
import com.app.goodwalls1.Config;
import com.app.goodwalls1.R;
import com.app.goodwalls1.adapter.AdapterTags;
import com.app.goodwalls1.adapter.AdapterWallpaperDetail;
import com.app.goodwalls1.callback.CallbackWallpaper;
import com.app.goodwalls1.database.dao.AppDatabase;
import com.app.goodwalls1.database.dao.DAO;
import com.app.goodwalls1.database.prefs.AdsPref;
import com.app.goodwalls1.database.prefs.SharedPref;
import com.app.goodwalls1.model.Wallpaper;
import com.app.goodwalls1.rest.RestAdapter;
import com.app.goodwalls1.service.SetGIFAsWallpaperService;
import com.app.goodwalls1.service.SetMp4AsWallpaperService;
import com.app.goodwalls1.util.AdsManager;
import com.app.goodwalls1.util.Constant;
import com.app.goodwalls1.util.MaterialProgressDialog;
import com.app.goodwalls1.util.Tools;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityWallpaperDetail extends AppCompatActivity {

    Toolbar toolbar;
    ActionBar actionBar;
    private String actionType;
    CoordinatorLayout parentView;
    private BottomSheetDialog mBottomSheetDialogRewarded;
    private BottomSheetDialog mBottomSheetDialogApply;
    private BottomSheetDialog mBottomSheetDialogInfo;
    private BottomSheetDialog mBottomSheetDialogProgress;
    Call<CallbackWallpaper> callbackCall = null;
    SharedPref sharedPref;
    AdsPref adsPref;
    RelativeLayout bgShadowTop;
    RelativeLayout bgShadowBottom;
    AdsManager adsManager;
    ViewPager2 viewPager2;
    AdapterWallpaperDetail adapterWallpaperDetail;
    TextView titleToolbar;
    TextView subTitleToolbar;
    FloatingActionButton fabShare;
    FloatingActionButton fabDownload;
    FloatingActionButton fabApply;
    FloatingActionButton fabFavorite;
    FloatingActionButton fabInfo;
    ImageButton btnLock;
    ImageView imgBlurryBackground;
    LinearLayout viewBackgroundOverlay;
    RelativeLayout lytSetActionWallpaper;
    LinearLayout lytBannerAd;
    RelativeLayout liveWallpaperView;
    CardView playerView;
    StyledPlayerView styledPlayerView;
    private ExoPlayer exoPlayer;
    private DefaultDataSource.Factory dataSourceFactory;
    private ProgressBar progressBar;
    DAO db;
    boolean flagReadLater;
    Wallpaper wallpaper;
    MaterialProgressDialog.Builder progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Tools.getTheme(this);
        Tools.transparentStatusBarNavigation(this);
        if (Config.SHOW_FULL_SCREEN_WALLPAPER_DETAILS_VIEW) {
            setContentView(R.layout.activity_wallpaper_detail_full);
        } else {
            setContentView(R.layout.activity_wallpaper_detail);
        }
        sharedPref = new SharedPref(this);
        adsPref = new AdsPref(this);
        adsManager = new AdsManager(this);
        db = AppDatabase.getDb(this).get();

        if (!BuildConfig.DEBUG) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        }

        Tools.getRtlDirection(this);

        initView();
        setupToolbar();

        if (getIntent().hasExtra("id")) {
            String wallpaperId = getIntent().getStringExtra("id");
            requestWallpaperDetail(wallpaperId);
            loadRewardedAd(wallpaperId);
        } else {
            if (Constant.wallpapers != null && Constant.wallpapers.size() > 0) {
                if (Config.ENABLE_VIEWPAGER2_SWIPE_DETAIL_WALLPAPER) {
                    wallpaper = Constant.wallpapers.get(Constant.position);
                    setupViewPager(Constant.wallpapers);
                    loadView(Constant.position);
                    loadRewardedAd(wallpaper.image_id);
                } else {
                    String wallpaperId = Constant.wallpapers.get(Constant.position).image_id;
                    requestWallpaperDetail(wallpaperId);
                    loadRewardedAd(wallpaperId);
                }
            }
        }

        adsManager.loadBannerAd(adsPref.getIsBannerPostDetails());
        hideMenuIfDisabled();

    }

    private void loadRewardedAd(String wallpaperId) {
        adsManager.loadRewardedAd(adsPref.getIsRewardedPostDetails(), () -> {
            Constant.isRewarded = true;
            db.addRewarded(System.currentTimeMillis(), wallpaperId);
        }, () -> {
            if (Constant.isRewarded) {
                switch (actionType) {
                    case SHARE:
                        setApply(SHARE);
                        break;
                    case DOWNLOAD:
                        setApply(DOWNLOAD);
                        break;
                    default:
                        showBottomSheetApplyDialog(wallpaper);
                        break;
                }
                Constant.isRewarded = false;
            }
        });
    }

    private void requestWallpaperDetail(String wallpaperId) {
        callbackCall = RestAdapter.createAPI(sharedPref.getBaseUrl()).getWallpaperDetails(wallpaperId);
        callbackCall.enqueue(new Callback<CallbackWallpaper>() {
            public void onResponse(@NonNull Call<CallbackWallpaper> call, @NonNull Response<CallbackWallpaper> response) {
                CallbackWallpaper resp = response.body();
                if (resp != null && resp.status.equals("ok")) {
                    Constant.wallpapers.clear();
                    Constant.wallpapers.addAll(resp.posts);
                    Constant.position = 0;
                    wallpaper = Constant.wallpapers.get(Constant.position);
                    setupViewPager(Constant.wallpapers);
                    loadView(Constant.position);
                }
            }

            public void onFailure(@NonNull Call<CallbackWallpaper> call, @NonNull Throwable th) {
                Log.e("onFailure", "error: " + th.getMessage());
            }
        });
    }

    private void initView() {
        parentView = findViewById(R.id.parent_view);
        bgShadowTop = findViewById(R.id.bg_shadow_top);
        bgShadowBottom = findViewById(R.id.bg_shadow_bottom);

        titleToolbar = findViewById(R.id.title_toolbar);
        subTitleToolbar = findViewById(R.id.sub_title_toolbar);

        ViewStub viewStub = findViewById(R.id.view_set_action_button);
        if (sharedPref.getIsDarkTheme()) {
            viewStub.setLayoutResource(R.layout.include_set_action_dark);
        } else {
            viewStub.setLayoutResource(R.layout.include_set_action_light);
        }
        viewStub.inflate();
        fabShare = findViewById(R.id.fab_share);
        fabDownload = findViewById(R.id.fab_download);
        fabApply = findViewById(R.id.fab_set_action);
        fabFavorite = findViewById(R.id.fab_favorite);
        fabInfo = findViewById(R.id.fab_info);
        btnLock = findViewById(R.id.btn_lock);

        progressDialog = new MaterialProgressDialog.Builder(this);
        progressDialog.build();
        viewPager2 = findViewById(R.id.view_pager2);

        imgBlurryBackground = findViewById(R.id.img_blurry_background);
        viewBackgroundOverlay = findViewById(R.id.view_background_overlay);
        lytSetActionWallpaper = findViewById(R.id.lyt_set_action_wallpaper);

        liveWallpaperView = findViewById(R.id.live_wallpaper_view);
        playerView = findViewById(R.id.player_view);
        styledPlayerView = findViewById(R.id.styled_player_view);
        progressBar = findViewById(R.id.exo_progress_bar);

        lytBannerAd = findViewById(R.id.lyt_banner_ad);

        initExoPlayer();
    }

    private void hideMenuIfDisabled() {
        if (!Config.ENABLE_WALLPAPER_SET_ACTION_SAVE) {
            fabDownload.setVisibility(View.GONE);
        }
        if (!Config.ENABLE_WALLPAPER_SET_ACTION_APPLY) {
            fabApply.setVisibility(View.GONE);
        }
        if (!Config.ENABLE_WALLPAPER_SET_ACTION_FAVORITE) {
            fabFavorite.setVisibility(View.GONE);
        }
        if (!Config.ENABLE_WALLPAPER_SET_ACTION_SHARE) {
            fabShare.setVisibility(View.GONE);
        }
        if (!Config.ENABLE_WALLPAPER_SET_ACTION_INFO) {
            fabInfo.setVisibility(View.GONE);
        }
    }

    public void setupViewPager(final List<Wallpaper> wallpapers) {

        adapterWallpaperDetail = new AdapterWallpaperDetail(this, wallpapers);
        viewPager2.setAdapter(adapterWallpaperDetail);

        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);

        if (!Config.SHOW_FULL_SCREEN_WALLPAPER_DETAILS_VIEW) {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindow().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int paddingToSet = displayMetrics.widthPixels / 7;
            viewPager2.setPadding(paddingToSet, 0, paddingToSet, 40);
            liveWallpaperView.setPadding(paddingToSet, 0, paddingToSet, 40);
        }

        viewPager2.setOffscreenPageLimit(3);
        viewPager2.setCurrentItem(Constant.position, false);

        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer((page, positions) -> {
            float r = 1 - Math.abs(positions);
            page.setScaleY(0.85f + r * 0.15f);
        });
        viewPager2.setPageTransformer(compositePageTransformer);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                Wallpaper wallpaper = Constant.wallpapers.get(position);
                if (wallpaper.image_name != null && wallpaper.mime.contains("octet-stream")) {
                    if (positionOffset == 0.0) {
                        if (exoPlayer != null && exoPlayer.isPlaying()) {
                            playerView.setVisibility(View.VISIBLE);
                        }
                    } else {
                        playerView.setVisibility(View.INVISIBLE);
                    }
                } else {
                    playerView.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (viewPager2 == null) return;
                Constant.position = position;
                adapterWallpaperDetail.setSelectedAdsPosition(position);
                loadView(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }

        });

    }

    public void loadView(int position) {

        wallpaper = Constant.wallpapers.get(position);

        if (wallpaper.image_name != null) {

            String blurryWallpaperUrl;
            if (wallpaper.mime.contains("octet-stream")) {
                blurryWallpaperUrl = sharedPref.getBaseUrl() + "/upload/thumbs/" + wallpaper.image_thumb;
            } else {
                if (wallpaper.type.equals("url")) {
                    blurryWallpaperUrl = wallpaper.image_url;
                } else {
                    blurryWallpaperUrl = sharedPref.getBaseUrl() + "/upload/" + wallpaper.image_upload;
                }
            }
            setImgBlurryBackground(wallpaper, blurryWallpaperUrl);

            String wallpaperUrl;
            if (wallpaper.type.equals("url")) {
                wallpaperUrl = wallpaper.image_url;
            } else {
                wallpaperUrl = sharedPref.getBaseUrl() + "/upload/" + wallpaper.image_upload;
            }
            Constant.wallpaperUrl = wallpaperUrl;

            if (Config.SHOW_WALLPAPER_NAME) {
                ObjectAnimator.ofFloat(titleToolbar, View.ALPHA, 0.1f, 1.0f).setDuration(500).start();
            } else {
                titleToolbar.setVisibility(View.GONE);
                subTitleToolbar.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size_large));
            }

            if (Config.SHOW_CATEGORY_NAME) {
                ObjectAnimator.ofFloat(subTitleToolbar, View.ALPHA, 0.1f, 1.0f).setDuration(500).start();
            } else {
                subTitleToolbar.setVisibility(View.GONE);
            }

            if (wallpaper.image_name.equals("")) {
                titleToolbar.setText(wallpaper.category_name);
                subTitleToolbar.setVisibility(View.GONE);
            } else {
                titleToolbar.setText(wallpaper.image_name);
                subTitleToolbar.setText(wallpaper.category_name);
            }

            if (wallpaper.mime.contains("octet-stream")) {
                String videoUrl;
                if (wallpaper.type.equals("url")) {
                    videoUrl = wallpaper.image_url;
                } else {
                    videoUrl = sharedPref.getBaseUrl() + "/upload/" + wallpaper.image_upload;
                }
                MediaSource mediaSource = buildMediaSource(Uri.parse(videoUrl));
                exoPlayer.setRepeatMode(Player.REPEAT_MODE_ONE);
                exoPlayer.setMediaSource(mediaSource);
                exoPlayer.prepare();
                exoPlayer.setPlayWhenReady(true);
                exoPlayer.setVolume(0f);
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.GONE);
                playerView.setVisibility(View.INVISIBLE);
                if (exoPlayer != null) {
                    exoPlayer.stop();
                }
            }

            Tools.updateView(this, wallpaper.image_id);
            Constant.wallpaperId = wallpaper.image_id;
            showViews(true);
        } else {
            showViews(false);
        }

    }

    private void setActionButton(Wallpaper wallpaper, boolean isWallpaperReady) {
        if (isWallpaperReady) {
            if (wallpaper.rewarded == 1 && adsPref.getIsRewardedPostDetails()) {
                btnLock.setVisibility(View.VISIBLE);
                ObjectAnimator.ofFloat(btnLock, View.ALPHA, 0.1f, 1.0f).setDuration(500).start();
                btnLock.setOnClickListener(view -> {
                    if (!verifyPermissions()) {
                        actionType = APPLY;
                        return;
                    }
                    if (wallpaper.rewarded == 1 && adsPref.getIsRewardedPostDetails()) {
                        if (db.getRewarded(wallpaper.image_id) != null) {
                            showBottomSheetApplyDialog(wallpaper);
                        } else {
                            actionType = APPLY;
                            showRewardedDialog();
                        }
                    } else {
                        showBottomSheetApplyDialog(wallpaper);
                    }
                });
                if (db.getRewarded(wallpaper.image_id) != null) {
                    btnLock.setImageResource(R.drawable.ic_lock_open);
                } else {
                    btnLock.setImageResource(R.drawable.ic_lock);
                }
            } else {
                btnLock.setVisibility(View.GONE);
            }

            fabShare.setOnClickListener(view -> {
                if (!verifyPermissions()) {
                    actionType = SHARE;
                    return;
                }
                if (wallpaper.rewarded == 1 && adsPref.getIsRewardedPostDetails()) {
                    actionType = SHARE;
                    if (db.getRewarded(wallpaper.image_id) != null) {
                        setApply(SHARE);
                    } else {
                        actionType = SHARE;
                        showRewardedDialog();
                    }
                } else {
                    setApply(SHARE);
                }
            });

            fabDownload.setOnClickListener(view -> {
                if (!verifyPermissions()) {
                    actionType = DOWNLOAD;
                    return;
                }
                if (wallpaper.rewarded == 1 && adsPref.getIsRewardedPostDetails()) {
                    if (db.getRewarded(wallpaper.image_id) != null) {
                        setApply(DOWNLOAD);
                    } else {
                        actionType = DOWNLOAD;
                        showRewardedDialog();
                    }
                } else {
                    setApply(DOWNLOAD);
                }
            });

            fabApply.setOnClickListener(view -> {
                if (!verifyPermissions()) {
                    actionType = APPLY;
                    return;
                }
                if (wallpaper.rewarded == 1 && adsPref.getIsRewardedPostDetails()) {
                    if (db.getRewarded(wallpaper.image_id) != null) {
                        showBottomSheetApplyDialog(wallpaper);
                    } else {
                        actionType = APPLY;
                        showRewardedDialog();
                    }
                } else {
                    showBottomSheetApplyDialog(wallpaper);
                }
            });

            favToggle(wallpaper);
            fabFavorite.setOnClickListener(view -> {
                if (db.getFavorite(wallpaper.image_id) != null) {
                    db.deleteFavorite(wallpaper.image_id);
                    fabFavorite.setImageResource(R.drawable.ic_action_fav_outline);
                    Snackbar.make(parentView, getString(R.string.snack_bar_favorite_removed), Snackbar.LENGTH_SHORT).show();
                } else {
                    db.addFavorite(
                            System.currentTimeMillis(),
                            wallpaper.image_id,
                            wallpaper.image_name,
                            wallpaper.image_thumb,
                            wallpaper.image_upload,
                            wallpaper.image_url,
                            wallpaper.type,
                            wallpaper.resolution,
                            wallpaper.size,
                            wallpaper.mime,
                            wallpaper.views,
                            wallpaper.downloads,
                            wallpaper.featured,
                            wallpaper.tags,
                            wallpaper.category_id,
                            wallpaper.category_name,
                            wallpaper.rewarded,
                            wallpaper.last_update
                    );
                    fabFavorite.setImageResource(R.drawable.ic_action_fav);
                    Snackbar.make(parentView, getString(R.string.snack_bar_favorite_added), Snackbar.LENGTH_SHORT).show();
                }
                favToggle(wallpaper);
            });

            fabInfo.setOnClickListener(view -> showBottomSheetDialog(wallpaper));
        } else {
            btnLock.setOnClickListener(view -> showSnackBar(getString(R.string.msg_wallpaper_not_ready)));
            fabShare.setOnClickListener(view -> showSnackBar(getString(R.string.msg_wallpaper_not_ready)));
            fabDownload.setOnClickListener(view -> showSnackBar(getString(R.string.msg_wallpaper_not_ready)));
            fabApply.setOnClickListener(view -> showSnackBar(getString(R.string.msg_wallpaper_not_ready)));
            fabFavorite.setOnClickListener(view -> showSnackBar(getString(R.string.msg_wallpaper_not_ready)));
            fabInfo.setOnClickListener(view -> showSnackBar(getString(R.string.msg_wallpaper_not_ready)));
        }
    }

    private void favToggle(Wallpaper wallpaper) {
        flagReadLater = db.getFavorite(wallpaper.image_id) != null;
        if (flagReadLater) {
            fabFavorite.setImageResource(R.drawable.ic_action_fav);
        } else {
            fabFavorite.setImageResource(R.drawable.ic_action_fav_outline);
        }
    }

    private void initExoPlayer() {
        HttpDataSource.Factory httpDataSourceFactory = new DefaultHttpDataSource.Factory()
                .setAllowCrossProtocolRedirects(true)
                .setUserAgent(Tools.getUserAgent());

        dataSourceFactory = new DefaultDataSource.Factory(getApplicationContext(), httpDataSourceFactory);

        LoadControl loadControl = new DefaultLoadControl();
        AdaptiveTrackSelection.Factory trackSelectionFactory = new AdaptiveTrackSelection.Factory();
        DefaultTrackSelector trackSelector = new DefaultTrackSelector(this, trackSelectionFactory);

        exoPlayer = new ExoPlayer.Builder(this).setTrackSelector(trackSelector).setLoadControl(loadControl).build();
        exoPlayer.addListener(new Player.Listener() {
            @Override
            public void onPlaybackStateChanged(int state) {
                if (state == PlaybackStateCompat.STATE_PLAYING) {
                    progressBar.setVisibility(View.GONE);
                    playerView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPlayerError(@NonNull PlaybackException error) {
                Player.Listener.super.onPlayerError(error);
                exoPlayer.stop();
                playerView.setVisibility(View.INVISIBLE);
            }
        });

        styledPlayerView.setPlayer(exoPlayer);
        styledPlayerView.requestFocus();
        styledPlayerView.setUseController(false);

        styledPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_ZOOM);

    }

    private MediaSource buildMediaSource(Uri uri) {
        MediaItem mMediaItem = MediaItem.fromUri(Uri.parse(String.valueOf(uri)));
        return new ProgressiveMediaSource.Factory(dataSourceFactory, new DefaultExtractorsFactory()).createMediaSource(mMediaItem);
    }

    private void showViews(boolean show) {
        if (show) {
            lytSetActionWallpaper.setVisibility(View.VISIBLE);
            lytBannerAd.setVisibility(View.VISIBLE);
            if (Config.SHOW_WALLPAPER_NAME) {
                titleToolbar.setVisibility(View.VISIBLE);
            } else {
                titleToolbar.setVisibility(View.GONE);
            }
            if (Config.SHOW_CATEGORY_NAME) {
                subTitleToolbar.setVisibility(View.VISIBLE);
            } else {
                subTitleToolbar.setVisibility(View.GONE);
            }
        } else {
            lytSetActionWallpaper.setVisibility(View.GONE);
            lytBannerAd.setVisibility(View.GONE);
            titleToolbar.setVisibility(View.GONE);
            subTitleToolbar.setVisibility(View.GONE);
            btnLock.setVisibility(View.GONE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Wallpaper wallpaper = Constant.wallpapers.get(Constant.position);
                if (wallpaper.rewarded == 1 && adsPref.getIsRewardedPostDetails()) {
                    if (db.getRewarded(wallpaper.image_id) != null) {
                        onPermissionGranted();
                    } else {
                        showRewardedDialog();
                    }
                } else {
                    onPermissionGranted();
                }
            }
        }
    }

    private void onPermissionGranted() {
        switch (actionType) {
            case SHARE:
                setApply(SHARE);
                break;
            case DOWNLOAD:
                setApply(DOWNLOAD);
                break;
            default:
                showBottomSheetApplyDialog(wallpaper);
                break;
        }
    }

    private void setImgBlurryBackground(Wallpaper wallpaper, String url) {
        Glide.with(getApplicationContext())
                .asBitmap()
                .load(url.replace(" ", "%20"))
                .transition(BitmapTransitionOptions.withCrossFade())
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                        Constant.bitmap = bitmap;
                        Constant.bitmapCropped = bitmap;
                        setActionButton(wallpaper, true);
                        Bitmap blurImage = Tools.blurImage(ActivityWallpaperDetail.this, bitmap);
                        imgBlurryBackground.setImageBitmap(blurImage);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                        setActionButton(wallpaper, false);
                    }
                });

        ObjectAnimator.ofFloat(imgBlurryBackground, View.ALPHA, 0.6f, 1.0f).setDuration(1000).start();
    }

    public void setupToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
    }

    @SuppressLint("SetTextI18n")
    @SuppressWarnings("rawtypes")
    private void showBottomSheetDialog(Wallpaper wallpaper) {
        @SuppressLint("InflateParams") final View view = getLayoutInflater().inflate(R.layout.include_info, null);
        FrameLayout lyt_bottom_sheet = view.findViewById(R.id.bottom_sheet);

        if (sharedPref.getIsDarkTheme()) {
            lyt_bottom_sheet.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_sheet_dark));
        } else {
            lyt_bottom_sheet.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_sheet_light));
        }

        if (wallpaper.image_name.equals("")) {
            ((TextView) view.findViewById(R.id.txt_wallpaper_name)).setText("-");
        } else {
            ((TextView) view.findViewById(R.id.txt_wallpaper_name)).setText(wallpaper.image_name);
        }

        ((TextView) view.findViewById(R.id.txt_category_name)).setText(wallpaper.category_name);

        if (wallpaper.resolution.equals("0")) {
            ((TextView) view.findViewById(R.id.txt_resolution)).setText("-");
        } else {
            ((TextView) view.findViewById(R.id.txt_resolution)).setText(wallpaper.resolution);
        }

        if (wallpaper.size.equals("0")) {
            ((TextView) view.findViewById(R.id.txt_size)).setText("-");
        } else {
            ((TextView) view.findViewById(R.id.txt_size)).setText(wallpaper.size);
        }

        if (wallpaper.mime.equals("")) {
            ((TextView) view.findViewById(R.id.txt_mime_type)).setText("image/jpeg");
        } else {
            if (wallpaper.mime.contains("octet-stream")) {
                ((TextView) view.findViewById(R.id.txt_mime_type)).setText("video/mp4");
            } else {
                ((TextView) view.findViewById(R.id.txt_mime_type)).setText(wallpaper.mime);
            }
        }

        ((TextView) view.findViewById(R.id.txt_view_count)).setText(Tools.withSuffix(wallpaper.views));
        ((TextView) view.findViewById(R.id.txt_download_count)).setText(Tools.withSuffix(wallpaper.downloads));

        LinearLayout lyt_tags = view.findViewById(R.id.lyt_tags);
        if (wallpaper.tags.equals("")) {
            lyt_tags.setVisibility(View.GONE);
        } else {
            lyt_tags.setVisibility(View.VISIBLE);
        }

        ArrayList<String> arrayListTags = new ArrayList<>(Arrays.asList(wallpaper.tags.split(",")));
        AdapterTags adapterTags = new AdapterTags(this, arrayListTags);
        RecyclerView recycler_view_tags = view.findViewById(R.id.recycler_view_tags);
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setAlignItems(AlignItems.FLEX_START);
        recycler_view_tags.setLayoutManager(layoutManager);
        recycler_view_tags.setAdapter(adapterTags);

        adapterTags.setOnItemClickListener((v, keyword, position) -> {
            Intent intent = new Intent(getApplicationContext(), ActivitySearch.class);
            intent.putExtra("tags", keyword);
            intent.putExtra(Constant.EXTRA_OBJC, "wallpaper");
            startActivity(intent);

            mBottomSheetDialogInfo.dismiss();
            adsManager.destroyBannerAd();
        });

        if (Config.ENABLE_RTL_MODE) {
            if (sharedPref.getIsDarkTheme()) {
                mBottomSheetDialogInfo = new BottomSheetDialog(this, R.style.SheetDialogDarkRtl);
            } else {
                mBottomSheetDialogInfo = new BottomSheetDialog(this, R.style.SheetDialogDetailLightRtl);
            }
        } else {
            if (sharedPref.getIsDarkTheme()) {
                mBottomSheetDialogInfo = new BottomSheetDialog(this, R.style.SheetDialogDark);
            } else {
                mBottomSheetDialogInfo = new BottomSheetDialog(this, R.style.SheetDialogDetailLight);
            }
        }
        mBottomSheetDialogInfo.setContentView(view);

        BottomSheetBehavior bottomSheetBehavior = mBottomSheetDialogInfo.getBehavior();
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        mBottomSheetDialogInfo.show();
        mBottomSheetDialogInfo.setOnDismissListener(dialog -> mBottomSheetDialogInfo = null);
    }

    public Boolean verifyPermissions() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            int permissionExternalMemory = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (permissionExternalMemory != PackageManager.PERMISSION_GRANTED) {
                String[] STORAGE_PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                ActivityCompat.requestPermissions(this, STORAGE_PERMISSIONS, 1);
                return false;
            }
            return true;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        adsManager.destroyBannerAd();
        if (exoPlayer != null) {
            exoPlayer.stop();
        }
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
        adsManager.resumeBannerAd(adsPref.getIsBannerPostDetails());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adsManager.destroyBannerAd();
        if (adsPref.getIsRewardedPostDetails()) {
            adsManager.destroyRewardedAd();
        }
    }

    private void showRewardedDialog() {
        @SuppressLint("InflateParams") final View view = getLayoutInflater().inflate(R.layout.dialog_rewarded, null);

        FrameLayout lytBottomSheet = view.findViewById(R.id.bottom_sheet_rewarded);

        view.findViewById(R.id.btn_watch_ad).setOnClickListener(v -> {
            Wallpaper wallpaper = Constant.wallpapers.get(Constant.position);
            mBottomSheetDialogRewarded.dismiss();
            adsManager.showRewardedAd(() -> {
                Constant.isRewarded = true;
                db.addRewarded(System.currentTimeMillis(), wallpaper.image_id);
            }, () -> {
                if (Constant.isRewarded) {
                    switch (actionType) {
                        case SHARE:
                            setApply(SHARE);
                            break;
                        case DOWNLOAD:
                            setApply(DOWNLOAD);
                            break;
                        default:
                            showBottomSheetApplyDialog(wallpaper);
                            break;
                    }
                    Constant.isRewarded = false;
                }
            }, () -> showSnackBar(getString(R.string.msg_rewarded_error)));
        });
        view.findViewById(R.id.btn_later).setOnClickListener(v -> mBottomSheetDialogRewarded.dismiss());

        if (this.sharedPref.getIsDarkTheme()) {
            lytBottomSheet.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_sheet_dark));
        } else {
            lytBottomSheet.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_sheet_light));
        }

        if (Config.ENABLE_RTL_MODE) {
            if (sharedPref.getIsDarkTheme()) {
                mBottomSheetDialogRewarded = new BottomSheetDialog(this, R.style.SheetDialogDarkRtl);
            } else {
                mBottomSheetDialogRewarded = new BottomSheetDialog(this, R.style.SheetDialogDetailLightRtl);
            }
        } else {
            if (sharedPref.getIsDarkTheme()) {
                mBottomSheetDialogRewarded = new BottomSheetDialog(this, R.style.SheetDialogDark);
            } else {
                mBottomSheetDialogRewarded = new BottomSheetDialog(this, R.style.SheetDialogDetailLight);
            }
        }
        mBottomSheetDialogRewarded.setContentView(view);
        mBottomSheetDialogRewarded.show();
    }

    private void showBottomSheetApplyDialog(Wallpaper wallpaper) {
        @SuppressLint("InflateParams") final View view = getLayoutInflater().inflate(R.layout.dialog_set_action, null);

        FrameLayout lytBottomSheet = view.findViewById(R.id.bottom_sheet);
        LinearLayout lytSetWallpaper = view.findViewById(R.id.lyt_set_wallpaper);
        LinearLayout lytSetLiveWallpaper = view.findViewById(R.id.lyt_set_live_wallpaper);

        if (wallpaper.image_upload.contains(".gif") || wallpaper.image_upload.contains(".mp4") || wallpaper.image_url.contains(".gif") || wallpaper.image_url.contains(".mp4")) {
            lytSetWallpaper.setVisibility(View.GONE);
            lytSetLiveWallpaper.setVisibility(View.VISIBLE);
        } else {
            lytSetWallpaper.setVisibility(View.VISIBLE);
            lytSetLiveWallpaper.setVisibility(View.GONE);
        }

        LinearLayout btnSetHomeScreen = view.findViewById(R.id.btn_set_home_screen);
        LinearLayout btnSetLockScreen = view.findViewById(R.id.btn_set_lock_screen);
        LinearLayout btnSetBothScreen = view.findViewById(R.id.btn_set_both);
        LinearLayout btnSetWith = view.findViewById(R.id.btn_set_with);
        LinearLayout btnSetCrop = view.findViewById(R.id.btn_set_crop);
        LinearLayout btnSetLive = view.findViewById(R.id.btn_set_live);
        LinearLayout btnSetSave = view.findViewById(R.id.btn_set_save);

        ImageView imgSetHomeScreen = view.findViewById(R.id.img_set_home_screen);
        ImageView imgSetLockScreen = view.findViewById(R.id.img_set_lock_screen);
        ImageView imgSetBothScreen = view.findViewById(R.id.img_set_both);
        ImageView imgSetWith = view.findViewById(R.id.img_set_with);
        ImageView imgSetCrop = view.findViewById(R.id.img_set_crop);
        ImageView imgSetLive = view.findViewById(R.id.img_set_live);
        ImageView imgSetSave = view.findViewById(R.id.img_set_save);

        TextView txtSetWallpaper = view.findViewById(R.id.txt_set_wallpaper);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            txtSetWallpaper.setText(getString(R.string.txt_set_both_screen));
        } else {
            txtSetWallpaper.setText(getString(R.string.txt_set_wallpaper));
        }

        if (sharedPref.getIsDarkTheme()) {
            imgSetHomeScreen.setColorFilter(getResources().getColor(R.color.color_dark_icon), PorterDuff.Mode.SRC_IN);
            imgSetLockScreen.setColorFilter(getResources().getColor(R.color.color_dark_icon), PorterDuff.Mode.SRC_IN);
            imgSetBothScreen.setColorFilter(getResources().getColor(R.color.color_dark_icon), PorterDuff.Mode.SRC_IN);
            imgSetWith.setColorFilter(getResources().getColor(R.color.color_dark_icon), PorterDuff.Mode.SRC_IN);
            imgSetCrop.setColorFilter(getResources().getColor(R.color.color_dark_icon), PorterDuff.Mode.SRC_IN);
            imgSetLive.setColorFilter(getResources().getColor(R.color.color_dark_icon), PorterDuff.Mode.SRC_IN);
            imgSetSave.setColorFilter(getResources().getColor(R.color.color_dark_icon), PorterDuff.Mode.SRC_IN);
        } else {
            imgSetHomeScreen.setColorFilter(getResources().getColor(R.color.color_light_icon), PorterDuff.Mode.SRC_IN);
            imgSetLockScreen.setColorFilter(getResources().getColor(R.color.color_light_icon), PorterDuff.Mode.SRC_IN);
            imgSetBothScreen.setColorFilter(getResources().getColor(R.color.color_light_icon), PorterDuff.Mode.SRC_IN);
            imgSetWith.setColorFilter(getResources().getColor(R.color.color_light_icon), PorterDuff.Mode.SRC_IN);
            imgSetCrop.setColorFilter(getResources().getColor(R.color.color_light_icon), PorterDuff.Mode.SRC_IN);
            imgSetLive.setColorFilter(getResources().getColor(R.color.color_light_icon), PorterDuff.Mode.SRC_IN);
            imgSetSave.setColorFilter(getResources().getColor(R.color.color_light_icon), PorterDuff.Mode.SRC_IN);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            btnSetHomeScreen.setOnClickListener(v -> {
                setApply(HOME_SCREEN);
                mBottomSheetDialogApply.dismiss();
            });
            btnSetLockScreen.setOnClickListener(v -> {
                setApply(LOCK_SCREEN);
                mBottomSheetDialogApply.dismiss();
            });
        } else {
            btnSetHomeScreen.setVisibility(View.GONE);
            btnSetLockScreen.setVisibility(View.GONE);
        }

        btnSetBothScreen.setOnClickListener(v -> {
            setApply(BOTH);
            mBottomSheetDialogApply.dismiss();
        });

        if (Config.ENABLE_WALLPAPER_SET_ACTION_APPLY_WITH) {
            btnSetWith.setOnClickListener(v -> {
                setApply(SET_WITH);
                mBottomSheetDialogApply.dismiss();
            });
        } else {
            btnSetWith.setVisibility(View.GONE);
        }

        btnSetCrop.setOnClickListener(v -> {
            setApply(SET_CROP);
            mBottomSheetDialogApply.dismiss();
        });

        btnSetLive.setOnClickListener(v -> {
            if (wallpaper.image_upload.contains(".gif") || wallpaper.image_url.contains(".gif")) {
                setApply(SET_GIF);
            } else if (wallpaper.image_upload.contains(".mp4") || wallpaper.image_url.contains(".mp4")) {
                setApply(SET_MP4);
            } else {
                showSnackBar("Invalid live wallpaper format");
            }
            mBottomSheetDialogApply.dismiss();
        });
        btnSetSave.setOnClickListener(v -> {
            setApply(DOWNLOAD);
            mBottomSheetDialogApply.dismiss();
        });

        if (this.sharedPref.getIsDarkTheme()) {
            lytBottomSheet.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_sheet_dark));
        } else {
            lytBottomSheet.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_sheet_light));
        }

        if (Config.ENABLE_RTL_MODE) {
            if (sharedPref.getIsDarkTheme()) {
                mBottomSheetDialogApply = new BottomSheetDialog(this, R.style.SheetDialogDarkRtl);
            } else {
                mBottomSheetDialogApply = new BottomSheetDialog(this, R.style.SheetDialogDetailLightRtl);
            }
        } else {
            if (sharedPref.getIsDarkTheme()) {
                mBottomSheetDialogApply = new BottomSheetDialog(this, R.style.SheetDialogDark);
            } else {
                mBottomSheetDialogApply = new BottomSheetDialog(this, R.style.SheetDialogDetailLight);
            }
        }
        mBottomSheetDialogApply.setContentView(view);
        mBottomSheetDialogApply.show();
        mBottomSheetDialogApply.setOnDismissListener(dialog -> mBottomSheetDialogApply = null);

    }

    private void showProgressDialog() {
        @SuppressLint("InflateParams") final View view = getLayoutInflater().inflate(R.layout.dialog_progress, null);

        FrameLayout lytBottomSheet = view.findViewById(R.id.bottom_sheet_progress);

        if (this.sharedPref.getIsDarkTheme()) {
            lytBottomSheet.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_sheet_dark));
        } else {
            lytBottomSheet.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_sheet_light));
        }

        if (Config.ENABLE_RTL_MODE) {
            if (sharedPref.getIsDarkTheme()) {
                mBottomSheetDialogProgress = new BottomSheetDialog(this, R.style.SheetDialogDarkRtl);
            } else {
                mBottomSheetDialogProgress = new BottomSheetDialog(this, R.style.SheetDialogDetailLightRtl);
            }
        } else {
            if (sharedPref.getIsDarkTheme()) {
                mBottomSheetDialogProgress = new BottomSheetDialog(this, R.style.SheetDialogDark);
            } else {
                mBottomSheetDialogProgress = new BottomSheetDialog(this, R.style.SheetDialogDetailLight);
            }
        }
        mBottomSheetDialogProgress.setContentView(view);
        mBottomSheetDialogProgress.show();
    }

    private void setApply(String actionType) {
        showProgressDialog();

        Glide.with(getApplicationContext())
                .download(Constant.wallpaperUrl.replace(" ", "%20"))
                .listener(new RequestListener<File>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, @NonNull Target<File> target, boolean isFirstResource) {
                        showSnackBar(getString(R.string.msg_wallpaper_failed));
                        if (mBottomSheetDialogProgress != null && mBottomSheetDialogProgress.isShowing()) {
                            mBottomSheetDialogProgress.dismiss();
                        }
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(@NonNull File file, @NonNull Object model, Target<File> target, @NonNull DataSource dataSource, boolean isFirstResource) {
                        if (mBottomSheetDialogProgress != null && mBottomSheetDialogProgress.isShowing()) {
                            mBottomSheetDialogProgress.dismiss();
                        }
                        switch (actionType) {
                            case HOME_SCREEN:
                                setWallpaper(HOME_SCREEN);
                                break;

                            case LOCK_SCREEN:
                                setWallpaper(LOCK_SCREEN);
                                break;

                            case BOTH:
                                setWallpaper(BOTH);
                                break;

                            case SET_CROP:
                                setWallpaper(SET_CROP);
                                break;

                            case DOWNLOAD:
                                setAction(file, DOWNLOAD);
                                break;

                            case SHARE:
                                setAction(file, SHARE);
                                break;

                            case SET_WITH:
                                setAction(file, SET_WITH);
                                break;

                            case SET_GIF:
                                setAction(file, SET_GIF);
                                break;

                            case SET_MP4:
                                setAction(file, SET_MP4);
                                break;
                        }
                        return true;
                    }
                }).submit();
    }

    private void setWallpaper(String actionType) {
        switch (actionType) {
            case HOME_SCREEN:
                try {
                    WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        wallpaperManager.setBitmap(Constant.bitmap, null, true, WallpaperManager.FLAG_SYSTEM);
                        showSnackBar(getString(R.string.msg_wallpaper_success));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    showSnackBar(getString(R.string.msg_wallpaper_failed));
                }
                break;

            case LOCK_SCREEN:
                try {
                    WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        wallpaperManager.setBitmap(Constant.bitmap, null, true, WallpaperManager.FLAG_LOCK);
                        showSnackBar(getString(R.string.msg_wallpaper_success));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    showSnackBar(getString(R.string.msg_wallpaper_failed));
                }
                break;

            case BOTH:
                try {
                    WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
                    wallpaperManager.setBitmap(Constant.bitmap);
                    showSnackBar(getString(R.string.msg_wallpaper_success));
                } catch (IOException e) {
                    e.printStackTrace();
                    showSnackBar(getString(R.string.msg_wallpaper_failed));
                }
                break;

            case SET_CROP:
                Intent intent = new Intent(getApplicationContext(), ActivityCropWallpaper.class);
                intent.putExtra("image_url", Constant.wallpaperUrl);
                startActivity(intent);
                break;
        }
    }

    private void setAction(File file, String actionType) {
        try {
            setAction(Tools.getBytesFromFile(file), Tools.createName(Constant.wallpaperUrl), actionType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setAction(byte[] bytes, String imgName, String action) {
        try {
            File directory;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + getString(R.string.app_name));
            } else {
                directory = new File(Environment.getExternalStorageDirectory() + "/" + getString(R.string.app_name));
            }
            boolean success = true;
            if (!directory.exists()) {
                success = directory.mkdirs();
            }
            if (success) {
                File imageFile = new File(directory, imgName);
                FileOutputStream fileWriter = new FileOutputStream(imageFile);
                fileWriter.write(bytes);
                fileWriter.flush();
                fileWriter.close();

                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                File file = new File(imageFile.getAbsolutePath());
                mediaScanIntent.setData(Uri.fromFile(file));
                sendBroadcast(mediaScanIntent);

                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());

                switch (action) {
                    case DOWNLOAD:
                        Tools.updateDownload(this, Constant.wallpaperId);
                        showSnackBar(getString(R.string.msg_wallpaper_saved));
                        break;

                    case SHARE:
                        Intent share = new Intent(Intent.ACTION_SEND);
                        share.setType("image/*");
                        share.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.share_text) + "\n" + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
                        share.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + imageFile.getAbsolutePath()));
                        startActivity(Intent.createChooser(share, "Share Image"));
                        break;

                    case SET_WITH:
                        Intent setWith = new Intent(Intent.ACTION_ATTACH_DATA);
                        setWith.addCategory(Intent.CATEGORY_DEFAULT);
                        setWith.setDataAndType(Uri.parse("file://" + file.getAbsolutePath()), "image/*");
                        setWith.putExtra("mimeType", "image/*");
                        startActivity(Intent.createChooser(setWith, "Set as:"));
                        break;

                    case SET_GIF:
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                            Constant.gifPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + getString(R.string.app_name);
                        } else {
                            Constant.gifPath = Environment.getExternalStorageDirectory() + "/" + getString(R.string.app_name);
                        }
                        Constant.gifName = file.getName();
                        sharedPref.saveGif(Constant.gifPath, Constant.gifName);
                        try {
                            WallpaperManager.getInstance(ActivityWallpaperDetail.this).clear();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Intent gifIntent = new Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
                        gifIntent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT, new ComponentName(ActivityWallpaperDetail.this, SetGIFAsWallpaperService.class));
                        startActivity(gifIntent);
                        break;

                    case SET_MP4:
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                            Constant.mp4Path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + getString(R.string.app_name);
                        } else {
                            Constant.mp4Path = Environment.getExternalStorageDirectory() + "/" + getString(R.string.app_name);
                        }
                        Constant.mp4Name = file.getName();
                        sharedPref.saveMp4(Constant.mp4Path, Constant.mp4Name);
                        try {
                            WallpaperManager.getInstance(ActivityWallpaperDetail.this).clear();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Intent mp4Intent = new Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
                        mp4Intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT, new ComponentName(ActivityWallpaperDetail.this, SetMp4AsWallpaperService.class));
                        startActivity(mp4Intent);
                        break;
                }
            } else {
                showSnackBar(getString(R.string.msg_wallpaper_error));
            }

        } catch (Exception e) {
            showSnackBar(getString(R.string.msg_wallpaper_error));
            e.printStackTrace();
        }
    }

    private void showSnackBar(String message) {
        Snackbar.make(parentView, message, Snackbar.LENGTH_SHORT).show();
    }

}
