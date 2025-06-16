package com.app.goodwalls.adapter;

import static com.solodroid.ads.sdk.util.Constant.ADMOB;
import static com.solodroid.ads.sdk.util.Constant.APPLOVIN;
import static com.solodroid.ads.sdk.util.Constant.APPLOVIN_DISCOVERY;
import static com.solodroid.ads.sdk.util.Constant.APPLOVIN_MAX;
import static com.solodroid.ads.sdk.util.Constant.FAN;
import static com.solodroid.ads.sdk.util.Constant.GOOGLE_AD_MANAGER;
import static com.solodroid.ads.sdk.util.Constant.STARTAPP;
import static com.solodroid.ads.sdk.util.Constant.WORTISE;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.app.goodwalls.Config;
import com.app.goodwalls.R;
import com.app.goodwalls.database.dao.AppDatabase;
import com.app.goodwalls.database.dao.DAO;
import com.app.goodwalls.database.prefs.AdsPref;
import com.app.goodwalls.database.prefs.SharedPref;
import com.app.goodwalls.model.Wallpaper;
import com.app.goodwalls.util.Constant;
import com.app.goodwalls.util.Tools;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.solodroid.ads.sdk.format.NativeAdViewHolder;

import java.util.List;

public class AdapterWallpaper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_PROG = 0;
    private final int VIEW_ITEM = 1;
    private final int VIEW_AD = 2;
    private List<Wallpaper> items;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;
    Context context;
    private OnItemClickListener mOnItemClickListener;
    boolean scrolling = false;
    SharedPref sharedPref;
    AdsPref adsPref;
    DAO db;
    int wallpaperColumnCount;

    public interface OnItemClickListener {
        void onItemClick(View view, Wallpaper obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public AdapterWallpaper(Context context, RecyclerView view, List<Wallpaper> items) {
        this.items = items;
        this.context = context;
        this.sharedPref = new SharedPref(context);
        this.wallpaperColumnCount = sharedPref.getWallpaperSpanCount();
        this.adsPref = new AdsPref(context);
        this.db = AppDatabase.getDb(context).get();
        lastItemViewDetector(view);
        view.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    scrolling = true;
                } else if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    scrolling = false;
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    public static class OriginalViewHolder extends RecyclerView.ViewHolder {

        public ImageView wallpaper_image;
        public TextView wallpaper_name;
        public TextView category_name;
        public RelativeLayout lyt_live;
        public RelativeLayout lyt_lock;
        public ImageView ic_lock;
        public CardView card_view;
        LinearLayout bg_shadow;
        ProgressBar progress_bar;
        RelativeLayout lyt_parent;

        public OriginalViewHolder(View v) {
            super(v);
            wallpaper_image = v.findViewById(R.id.wallpaper_image);
            wallpaper_name = v.findViewById(R.id.wallpaper_name);
            category_name = v.findViewById(R.id.category_name);
            lyt_live = v.findViewById(R.id.lyt_live);
            lyt_lock = v.findViewById(R.id.lyt_lock);
            ic_lock = v.findViewById(R.id.ic_lock);
            card_view = v.findViewById(R.id.card_view);
            bg_shadow = v.findViewById(R.id.bg_shadow_bottom);
            progress_bar = v.findViewById(R.id.progress_bar);
            lyt_parent = v.findViewById(R.id.lyt_parent);
        }

    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        ProgressViewHolder(View v) {
            super(v);
            progressBar = v.findViewById(R.id.load_more);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            if (Config.DISPLAY_WALLPAPER == 2) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wallpaper_square, parent, false);
                vh = new OriginalViewHolder(v);
            } else if (Config.DISPLAY_WALLPAPER == 3) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wallpaper_dynamic, parent, false);
                vh = new OriginalViewHolder(v);
            } else {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wallpaper_rectangle, parent, false);
                vh = new OriginalViewHolder(v);
            }
        } else if (viewType == VIEW_AD) {
            View v = LayoutInflater.from(parent.getContext()).inflate(com.solodroid.ads.sdk.R.layout.view_native_ad_medium, parent, false);
            vh = new NativeAdViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_load_more, parent, false);
            vh = new ProgressViewHolder(v);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof OriginalViewHolder) {
            final Wallpaper wallpaper = items.get(position);
            final OriginalViewHolder vItem = (OriginalViewHolder) holder;

            vItem.wallpaper_name.setText(wallpaper.image_name);
            vItem.category_name.setText(wallpaper.category_name);

            if (!Config.SHOW_WALLPAPER_NAME) {
                vItem.wallpaper_name.setVisibility(View.GONE);
                vItem.category_name.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.text_size_medium));
            }

            if (!Config.SHOW_CATEGORY_NAME) {
                vItem.category_name.setVisibility(View.GONE);
            }

            if (sharedPref.getIsDarkTheme()) {
                vItem.card_view.setCardBackgroundColor(context.getResources().getColor(R.color.color_dark_toolbar));
            } else {
                vItem.card_view.setCardBackgroundColor(context.getResources().getColor(R.color.color_grey_soft));
            }

            if (!Config.SHOW_WALLPAPER_NAME && !Config.SHOW_CATEGORY_NAME) {
                vItem.bg_shadow.setBackgroundResource(R.drawable.ic_transparent);
            }

            String imageUrl;
            if (wallpaper.type.equals("url")) {
                if (wallpaper.mime.contains("octet-stream")) {
                    imageUrl = sharedPref.getBaseUrl() + "/upload/thumbs/" + wallpaper.image_thumb;
                } else {
                    imageUrl = wallpaper.image_url;
                }
            } else {
                if (wallpaper.mime.contains("webp") || wallpaper.mime.contains("bmp")) {
                    imageUrl = sharedPref.getBaseUrl() + "/upload/" + wallpaper.image_upload;
                } else if (wallpaper.mime.contains("octet-stream")) {
                    imageUrl = sharedPref.getBaseUrl() + "/upload/thumbs/" + wallpaper.image_thumb;
                } else {
                    if (!wallpaper.image_thumb.equals("")) {
                        imageUrl = sharedPref.getBaseUrl() + "/upload/thumbs/" + wallpaper.image_thumb;
                    } else {
                        imageUrl = sharedPref.getBaseUrl() + "/upload/thumbs/" + wallpaper.image_upload;
                    }
                }
            }
            loadImage(vItem, wallpaper, imageUrl);

            vItem.lyt_parent.setOnClickListener(view -> {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(view, wallpaper, position);
                }
            });

        } else if (holder instanceof NativeAdViewHolder) {

            final NativeAdViewHolder vItem = (NativeAdViewHolder) holder;

            if (adsPref.getIsNativeAdPostList()) {
                vItem.loadNativeAd(context,
                        adsPref.getAdStatus(),
                        adsPref.getPlacementStatus(),
                        adsPref.getMainAds(),
                        adsPref.getBackupAds(),
                        adsPref.getAdMobNativeId(),
                        adsPref.getAdManagerNativeId(),
                        adsPref.getFanNativeUnitId(),
                        adsPref.getAppLovinNativeAdManualUnitId(),
                        adsPref.getAppLovinBannerMrecZoneId(),
                        adsPref.getWortiseNativeId(),
                        sharedPref.getIsDarkTheme(),
                        adsPref.getLegacyGDPR(),
                        adsPref.getNativeAdStylePostList(),
                        android.R.color.transparent,
                        android.R.color.transparent
                );

                if (sharedPref.getIsDarkTheme()) {
                    vItem.setNativeAdBackgroundResource(R.drawable.bg_native_dark);
                } else {
                    vItem.setNativeAdBackgroundResource(R.drawable.bg_native_light);
                }

                int margin = context.getResources().getDimensionPixelOffset(R.dimen.grid_space_wallpaper);
                vItem.setNativeAdMargin(margin, margin, margin, margin);

                int padding = context.getResources().getDimensionPixelOffset(R.dimen.grid_space_wallpaper);
                vItem.setNativeAdPadding(padding, padding, padding, padding);
            }

        } else {
            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }

        StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
        if (getItemViewType(position) == VIEW_PROG || getItemViewType(position) == VIEW_AD) {
            layoutParams.setFullSpan(true);
        } else {
            layoutParams.setFullSpan(false);
        }

    }

    private void loadImage(OriginalViewHolder vItem, Wallpaper wallpaper, String imageUrl) {
        Glide.with(context)
                .load(imageUrl.replace(" ", "%20"))
                .thumbnail(Tools.requestBuilder(context))
                .addListener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        vItem.progress_bar.setVisibility(View.GONE);
                        if (wallpaper.mime.contains("gif") || wallpaper.mime.contains("octet-stream")) {
                            vItem.lyt_live.setVisibility(View.VISIBLE);
                        } else {
                            vItem.lyt_live.setVisibility(View.GONE);
                        }
                        if (wallpaper.rewarded == 1 && adsPref.getIsRewardedPostDetails()) {
                            vItem.lyt_lock.setVisibility(View.VISIBLE);
                            if (db.getRewarded(wallpaper.image_id) != null) {
                                vItem.ic_lock.setImageResource(R.drawable.ic_lock_open);
                            } else {
                                vItem.ic_lock.setImageResource(R.drawable.ic_lock);
                            }
                        } else {
                            vItem.lyt_lock.setVisibility(View.GONE);
                        }
                        return false;
                    }
                })
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_transparent)
                .into(vItem.wallpaper_image);
    }

    public void insertDataWithNativeAd(List<Wallpaper> items) {
        setLoaded();
        int positionStart = getItemCount();
        if (wallpaperColumnCount == 3) {
            if (items.size() >= adsPref.getNativeAdIndex3())
                items.add(adsPref.getNativeAdIndex3(), new Wallpaper());
            Log.d("INSERT_DATA", "3 columns");
        } else {
            if (items.size() >= adsPref.getNativeAdIndex2())
                items.add(adsPref.getNativeAdIndex2(), new Wallpaper());
            Log.d("INSERT_DATA", "2 columns");
        }

        int itemCount = items.size();
        this.items.addAll(items);
        notifyItemRangeInserted(positionStart, itemCount);
    }

    public void insertData(List<Wallpaper> items) {
        setLoaded();
        int positionStart = getItemCount();
        int itemCount = items.size();
        this.items.addAll(items);
        notifyItemRangeInserted(positionStart, itemCount);
    }

    public void setItems(List<Wallpaper> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void setLoaded() {
        loading = false;
        for (int i = 0; i < getItemCount(); i++) {
            if (items.get(i) == null) {
                items.remove(i);
                notifyItemRemoved(i);
            }
        }
    }

    public void setLoading() {
        if (getItemCount() != 0) {
            this.items.add(null);
            notifyItemInserted(getItemCount() - 1);
            loading = true;
        }
    }

    public void insertAd() {
        if (getItemCount() != 0) {
            this.items.add(new Wallpaper());
            notifyItemInserted(getItemCount() - 1);
        }
    }

    public void resetListData() {
        this.items.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        Wallpaper wallpaper = items.get(position);
        if (wallpaper != null) {
            // Real Wallpaper should contain some data such as title, desc, and so on.
            // A Wallpaper having no title etc is assumed to be a fake Wallpaper which represents an Native Ad view
            if (wallpaper.image_name == null) {
                return VIEW_AD;
            }
            return VIEW_ITEM;
        } else {
            return VIEW_PROG;
        }
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    private void lastItemViewDetector(RecyclerView recyclerView) {
        if (recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            final StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    int lastPos = getLastVisibleItem(layoutManager.findLastVisibleItemPositions(null));
                    if (!loading && lastPos == getItemCount() - 1 && onLoadMoreListener != null) {
                        if (wallpaperColumnCount == 3) {
                            if (adsPref.getIsNativeAdPostList()) {
                                switch (adsPref.getMainAds()) {
                                    case ADMOB:
                                    case GOOGLE_AD_MANAGER:
                                    case FAN:
                                    case APPLOVIN:
                                    case APPLOVIN_MAX:
                                    case APPLOVIN_DISCOVERY:
                                    case STARTAPP:
                                    case WORTISE: {
                                        int current_page = getItemCount() / (Constant.LOAD_MORE_3_COLUMNS + 1); //posts per page plus 1 Ad
                                        onLoadMoreListener.onLoadMore(current_page);
                                        break;
                                    }
                                    default: {
                                        int current_page = getItemCount() / (Constant.LOAD_MORE_3_COLUMNS);
                                        onLoadMoreListener.onLoadMore(current_page);
                                        break;
                                    }
                                }
                            } else {
                                int current_page = getItemCount() / (Constant.LOAD_MORE_3_COLUMNS);
                                onLoadMoreListener.onLoadMore(current_page);
                            }
                        } else {
                            if (adsPref.getIsNativeAdPostList()) {
                                switch (adsPref.getMainAds()) {
                                    case ADMOB:
                                    case GOOGLE_AD_MANAGER:
                                    case FAN:
                                    case APPLOVIN:
                                    case APPLOVIN_MAX:
                                    case APPLOVIN_DISCOVERY:
                                    case STARTAPP:
                                    case WORTISE: {
                                        int current_page = getItemCount() / (Constant.LOAD_MORE_2_COLUMNS + 1); //posts per page plus 1 Ad
                                        onLoadMoreListener.onLoadMore(current_page);
                                        break;
                                    }
                                    default: {
                                        int current_page = getItemCount() / (Constant.LOAD_MORE_2_COLUMNS);
                                        onLoadMoreListener.onLoadMore(current_page);
                                        break;
                                    }
                                }
                            } else {
                                int current_page = getItemCount() / (Constant.LOAD_MORE_2_COLUMNS);
                                onLoadMoreListener.onLoadMore(current_page);
                            }
                        }
                        loading = true;
                    }
                }
            });
        }
    }

    public interface OnLoadMoreListener {
        void onLoadMore(int current_page);
    }

    private int getLastVisibleItem(int[] into) {
        int last_idx = into[0];
        for (int i : into) {
            if (last_idx < i) last_idx = i;
        }
        return last_idx;
    }

}