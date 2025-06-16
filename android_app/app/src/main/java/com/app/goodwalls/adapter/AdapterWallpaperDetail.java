package com.app.goodwalls.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.app.goodwalls.Config;
import com.app.goodwalls.R;
import com.app.goodwalls.database.prefs.AdsPref;
import com.app.goodwalls.database.prefs.SharedPref;
import com.app.goodwalls.model.Wallpaper;
import com.app.goodwalls.util.Tools;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.chrisbanes.photoview.PhotoView;
import com.solodroid.ads.sdk.format.NativeAdViewHolder;

import java.util.List;

public class AdapterWallpaperDetail extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_ITEM = 1;
    private final int VIEW_AD = 2;
    List<Wallpaper> items;
    Context context;
    OnItemClickListener mOnItemClickListener;
    boolean loading;
    SharedPref sharedPref;
    AdsPref adsPref;
    private int selectedAdsPosition;

    public interface OnItemClickListener {
        void onItemClick(View view, Wallpaper obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public AdapterWallpaperDetail(Context context, List<Wallpaper> items) {
        this.items = items;
        this.context = context;
        this.sharedPref = new SharedPref(context);
        this.adsPref = new AdsPref(context);
        this.selectedAdsPosition = 0;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout lytView;
        public PhotoView wallpaperImage;
        public ProgressBar progressBar;

        public OriginalViewHolder(View v) {
            super(v);
            lytView = v.findViewById(R.id.lyt_view);
            wallpaperImage = v.findViewById(R.id.image_view);
            progressBar = v.findViewById(R.id.progress_bar);
        }
    }

    public class NativeViewHolder extends NativeAdViewHolder {
        public View viewAdOverlay;

        public NativeViewHolder(View v) {
            super(v);
            viewAdOverlay = v.findViewById(R.id.view_ad_overlay);
        }

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_AD) {
            View v;
            if (Config.SHOW_FULL_SCREEN_WALLPAPER_DETAILS_VIEW) {
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_native_ad_details_medium, parent, false);
            } else {
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_native_ad_details_large, parent, false);
            }
            vh = new NativeViewHolder(v);
        } else {
            View v;
            if (Config.ENABLE_CENTER_CROP_IN_DETAIL_WALLPAPER) {
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_slider_wallpaper, parent, false);
            } else {
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_slider_wallpaper_dynamic, parent, false);
            }
            vh = new OriginalViewHolder(v);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof OriginalViewHolder) {
            final Wallpaper wallpaper = items.get(position);
            final OriginalViewHolder vItem = (OriginalViewHolder) holder;

            if (wallpaper.image_url.endsWith(".png") || wallpaper.image_upload.endsWith(".png")) {
                if (sharedPref.getIsDarkTheme()) {
                    vItem.lytView.setBackgroundColor(ContextCompat.getColor(context, R.color.color_dark_toolbar));
                } else {
                    vItem.lytView.setBackgroundColor(ContextCompat.getColor(context, R.color.color_background_image));
                }
            }

            String imageUrl;
            if (wallpaper.type.equals("url")) {
                if (wallpaper.mime.contains("octet-stream")) {
                    imageUrl = sharedPref.getBaseUrl() + "/upload/thumbs/" + wallpaper.image_thumb;
                } else {
                    imageUrl = wallpaper.image_url;
                }
            } else {
                if (wallpaper.mime.contains("octet-stream")) {
                    imageUrl = sharedPref.getBaseUrl() + "/upload/thumbs/" + wallpaper.image_thumb;
                } else {
                    imageUrl = sharedPref.getBaseUrl() + "/upload/" + wallpaper.image_upload;
                }
            }

            if (!Config.ENABLE_CENTER_CROP_IN_DETAIL_WALLPAPER) {
                final RelativeLayout.LayoutParams layoutParams;
                if (wallpaper.mime.contains("octet-stream")) {
                    layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    vItem.wallpaperImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
                } else {
                    layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    vItem.wallpaperImage.setScaleType(ImageView.ScaleType.FIT_XY);
                }
                vItem.wallpaperImage.setLayoutParams(layoutParams);
            }

            loadImage(vItem, imageUrl, vItem.wallpaperImage);

        } else if (holder instanceof NativeViewHolder) {
            final NativeViewHolder vItem = (NativeViewHolder) holder;

            if (position == selectedAdsPosition) {
                vItem.viewAdOverlay.animate().alpha(0f).setDuration(500);
            } else {
                vItem.viewAdOverlay.animate().alpha(0.8f).setDuration(500);
            }

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
                        adsPref.getNativeAdStylePostDetails(),
                        android.R.color.transparent,
                        android.R.color.transparent
                );

                int padding = context.getResources().getDimensionPixelOffset(R.dimen.spacing_xsmall);
                vItem.setNativeAdPadding(padding, padding, padding, padding);

                if (sharedPref.getIsDarkTheme()) {
                    vItem.setNativeAdBackgroundResource(R.drawable.bg_native_dark);
                } else {
                    vItem.setNativeAdBackgroundResource(R.drawable.bg_native_light);
                }
            }

        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setSelectedAdsPosition(int position) {
        this.selectedAdsPosition = position;
        notifyDataSetChanged();
    }

    private void loadImage(OriginalViewHolder vItem, String imageUrl, ImageView imageView) {
        Glide.with(context)
                .load(imageUrl.replace(" ", "%20"))
                .placeholder(R.drawable.ic_transparent)
                .thumbnail(Tools.requestBuilder(context))
                .addListener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        vItem.progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        vItem.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    public void insertData(List<Wallpaper> items) {
        setLoaded();
        int positionStart = getItemCount();
        int itemCount = items.size();
        this.items.addAll(items);
        notifyItemRangeInserted(positionStart, itemCount);
    }

    @SuppressWarnings("SuspiciousListRemoveInLoop")
    public void setLoaded() {
        loading = false;
        for (int i = 0; i < getItemCount(); i++) {
            if (items.get(i) == null) {
                items.remove(i);
                notifyItemRemoved(i);
            }
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
        Wallpaper post = items.get(position);
        if (post != null) {
            if (post.image_name == null) {
                return VIEW_AD;
            }
        }
        return VIEW_ITEM;
    }

}