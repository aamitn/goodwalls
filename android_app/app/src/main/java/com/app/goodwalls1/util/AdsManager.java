package com.app.goodwalls1.util;

import static com.solodroid.ads.sdk.util.Constant.AD_STATUS_ON;
import static com.solodroid.ads.sdk.util.Constant.IRONSOURCE;

import android.app.Activity;

import com.app.goodwalls1.BuildConfig;
import com.app.goodwalls1.Config;
import com.app.goodwalls1.R;
import com.app.goodwalls1.database.prefs.AdsPref;
import com.app.goodwalls1.database.prefs.SharedPref;
import com.app.goodwalls1.model.Ads;
import com.app.goodwalls1.model.Placement;
import com.solodroid.ads.sdk.format.AdNetwork;
import com.solodroid.ads.sdk.format.AppOpenAd;
import com.solodroid.ads.sdk.format.BannerAd;
import com.solodroid.ads.sdk.format.InterstitialAd;
import com.solodroid.ads.sdk.format.NativeAd;
import com.solodroid.ads.sdk.format.RewardedAd;
import com.solodroid.ads.sdk.gdpr.GDPR;
import com.solodroid.ads.sdk.util.OnRewardedAdCompleteListener;
import com.solodroid.ads.sdk.util.OnRewardedAdDismissedListener;
import com.solodroid.ads.sdk.util.OnRewardedAdErrorListener;
import com.solodroid.ads.sdk.util.OnShowAdCompleteListener;

public class AdsManager {

    Activity activity;
    AdNetwork.Initialize adNetwork;

    AppOpenAd.Builder appOpenAd;
    BannerAd.Builder bannerAd;
    InterstitialAd.Builder interstitialAd;
    RewardedAd.Builder rewardedAd;
    NativeAd.Builder nativeAd;
    SharedPref sharedPref;
    AdsPref adsPref;
    GDPR gdpr;

    public AdsManager(Activity activity) {
        this.activity = activity;
        this.sharedPref = new SharedPref(activity);
        this.adsPref = new AdsPref(activity);
        this.gdpr = new GDPR(activity);
        adNetwork = new AdNetwork.Initialize(activity);
        appOpenAd = new AppOpenAd.Builder(activity);
        bannerAd = new BannerAd.Builder(activity);
        interstitialAd = new InterstitialAd.Builder(activity);
        rewardedAd = new RewardedAd.Builder(activity);
        nativeAd = new NativeAd.Builder(activity);
    }

    public void initializeAd() {
        adNetwork.setAdStatus(adsPref.getAdStatus())
                .setAdNetwork(adsPref.getMainAds())
                .setBackupAdNetwork(adsPref.getBackupAds())
                .setStartappAppId(adsPref.getStartappAppID())
                .setUnityGameId(adsPref.getUnityGameId())
                .setIronSourceAppKey(adsPref.getIronSourceAppKey())
                .setWortiseAppId(adsPref.getWortiseAppId())
                .setDebug(BuildConfig.DEBUG)
                .build();
    }

    public void loadAppOpenAd(boolean placement, OnShowAdCompleteListener onShowAdCompleteListener) {
        if (placement) {
            appOpenAd = new AppOpenAd.Builder(activity)
                    .setAdStatus(adsPref.getAdStatus())
                    .setAdNetwork(adsPref.getMainAds())
                    .setBackupAdNetwork(adsPref.getBackupAds())
                    .setAdMobAppOpenId(adsPref.getAdMobAppOpenAdId())
                    .setAdManagerAppOpenId(adsPref.getAdManagerAppOpenAdId())
                    .setApplovinAppOpenId(adsPref.getAppLovinAppOpenAdUnitId())
                    .setWortiseAppOpenId(adsPref.getWortiseAppOpenId())
                    .build(onShowAdCompleteListener);
        } else {
            onShowAdCompleteListener.onShowAdComplete();
        }
    }

    public void loadAppOpenAd(boolean placement) {
        if (placement) {
            appOpenAd = new AppOpenAd.Builder(activity)
                    .setAdStatus(adsPref.getAdStatus())
                    .setAdNetwork(adsPref.getMainAds())
                    .setBackupAdNetwork(adsPref.getBackupAds())
                    .setAdMobAppOpenId(adsPref.getAdMobAppOpenAdId())
                    .setAdManagerAppOpenId(adsPref.getAdManagerAppOpenAdId())
                    .setApplovinAppOpenId(adsPref.getAppLovinAppOpenAdUnitId())
                    .setWortiseAppOpenId(adsPref.getWortiseAppOpenId())
                    .build();
        }
    }

    public void showAppOpenAd(boolean placement) {
        if (placement) {
            appOpenAd.show();
        }
    }

    public void destroyAppOpenAd(boolean placement) {
        if (placement) {
            appOpenAd.destroyOpenAd();
        }
    }

    public void loadBannerAd(boolean placement) {
        if (placement) {
            bannerAd.setAdStatus(adsPref.getAdStatus())
                    .setAdNetwork(adsPref.getMainAds())
                    .setBackupAdNetwork(adsPref.getBackupAds())
                    .setAdMobBannerId(adsPref.getAdMobBannerId())
                    .setGoogleAdManagerBannerId(adsPref.getAdManagerBannerId())
                    .setFanBannerId(adsPref.getFanBannerUnitId())
                    .setUnityBannerId(adsPref.getUnityBannerPlacementId())
                    .setAppLovinBannerId(adsPref.getAppLovinBannerAdUnitId())
                    .setAppLovinBannerZoneId(adsPref.getAppLovinBannerZoneId())
                    .setIronSourceBannerId(adsPref.getIronSourceBannerId())
                    .setWortiseBannerId(adsPref.getWortiseBannerId())
                    .setDarkTheme(sharedPref.getIsDarkTheme())
                    .setPlacementStatus(adsPref.getPlacementStatus())
                    .build();
        }
    }

    public void loadInterstitialAd(boolean placement, int interval) {
        if (placement) {
            interstitialAd.setAdStatus(adsPref.getAdStatus())
                    .setAdNetwork(adsPref.getMainAds())
                    .setBackupAdNetwork(adsPref.getBackupAds())
                    .setAdMobInterstitialId(adsPref.getAdMobInterstitialId())
                    .setGoogleAdManagerInterstitialId(adsPref.getAdManagerInterstitialId())
                    .setFanInterstitialId(adsPref.getFanInterstitialUnitId())
                    .setUnityInterstitialId(adsPref.getUnityInterstitialPlacementId())
                    .setAppLovinInterstitialId(adsPref.getAppLovinInterstitialAdUnitId())
                    .setAppLovinInterstitialZoneId(adsPref.getAppLovinInterstitialZoneId())
                    .setIronSourceInterstitialId(adsPref.getIronSourceInterstitialId())
                    .setWortiseInterstitialId(adsPref.getWortiseInterstitialId())
                    .setInterval(interval)
                    .setPlacementStatus(adsPref.getPlacementStatus())
                    .build();
        }
    }

    public void showInterstitialAd() {
        interstitialAd.show();
    }

    public void loadRewardedAd(boolean placement, OnRewardedAdCompleteListener onComplete, OnRewardedAdDismissedListener onDismiss) {
        if (placement) {
            rewardedAd.setAdStatus(adsPref.getAdStatus())
                    .setMainAds(adsPref.getMainAds())
                    .setBackupAds(adsPref.getBackupAds())
                    .setAdMobRewardedId(adsPref.getAdMobRewardedId())
                    .setAdManagerRewardedId(adsPref.getAdManagerRewardedId())
                    .setFanRewardedId(adsPref.getFanRewardedUnitId())
                    .setUnityRewardedId(adsPref.getUnityRewardedPlacementId())
                    .setApplovinMaxRewardedId(adsPref.getAppLovinRewardedAdUnitId())
                    .setApplovinDiscRewardedZoneId(adsPref.getAppLovinRewardedZoneId())
                    .setIronSourceRewardedId(adsPref.getIronSourceRewardedId())
                    .setWortiseRewardedId(adsPref.getWortiseRewardedId())
                    .setPlacementStatus(adsPref.getPlacementStatus())
                    .build(onComplete, onDismiss);
        }
    }

    public void showRewardedAd(OnRewardedAdCompleteListener onComplete, OnRewardedAdDismissedListener onDismiss, OnRewardedAdErrorListener onError) {
        rewardedAd.show(onComplete, onDismiss, onError);
    }

    public void loadNativeAd(boolean placement, String nativeAdStyle) {
        if (placement) {
            nativeAd.setAdStatus(adsPref.getAdStatus())
                    .setAdNetwork(adsPref.getMainAds())
                    .setBackupAdNetwork(adsPref.getBackupAds())
                    .setAdMobNativeId(adsPref.getAdMobNativeId())
                    .setAdManagerNativeId(adsPref.getAdManagerNativeId())
                    .setFanNativeId(adsPref.getFanNativeUnitId())
                    .setAppLovinNativeId(adsPref.getAppLovinNativeAdManualUnitId())
                    .setAppLovinDiscoveryMrecZoneId(adsPref.getAppLovinBannerMrecZoneId())
                    .setWortiseNativeId(adsPref.getWortiseNativeId())
                    .setPlacementStatus(1)
                    .setDarkTheme(sharedPref.getIsDarkTheme())
                    .setNativeAdBackgroundColor(R.color.color_light_native_ad_background, R.color.color_dark_native_ad_background)
                    .setNativeAdStyle(nativeAdStyle)
                    .build();
        }
    }

    public void destroyBannerAd() {
        bannerAd.destroyAndDetachBanner();
    }

    public void resumeBannerAd(boolean placement) {
        if (adsPref.getAdStatus().equals(AD_STATUS_ON) && !adsPref.getIronSourceBannerId().equals("0")) {
            if (adsPref.getMainAds().equals(IRONSOURCE) || adsPref.getBackupAds().equals(IRONSOURCE)) {
                loadBannerAd(placement);
            }
        }
    }

    public void destroyRewardedAd() {
        rewardedAd.destroyRewardedAd();
    }

    public void saveAds(Ads ads) {
        if (Config.ENABLE_OFFLINE_ADS_MODE) {
            adsPref.saveAds(
                    activity.getResources().getString(R.string.ad_status).replace("on", "1"),
                    activity.getResources().getString(R.string.main_ads),
                    activity.getResources().getString(R.string.backup_ads),
                    activity.getResources().getString(R.string.admob_publisher_id),
                    activity.getResources().getString(R.string.admob_banner_unit_id),
                    activity.getResources().getString(R.string.admob_interstitial_unit_id),
                    activity.getResources().getString(R.string.admob_rewarded_unit_id),
                    activity.getResources().getString(R.string.admob_native_unit_id),
                    activity.getResources().getString(R.string.admob_app_open_unit_id),
                    activity.getResources().getString(R.string.ad_manager_banner_unit_id),
                    activity.getResources().getString(R.string.ad_manager_interstitial_unit_id),
                    activity.getResources().getString(R.string.ad_manager_rewarded_unit_id),
                    activity.getResources().getString(R.string.ad_manager_native_unit_id),
                    activity.getResources().getString(R.string.ad_manager_app_open_unit_id),
                    activity.getResources().getString(R.string.fan_banner_unit_id),
                    activity.getResources().getString(R.string.fan_interstitial_unit_id),
                    activity.getResources().getString(R.string.fan_rewarded_unit_id),
                    activity.getResources().getString(R.string.fan_native_unit_id),
                    activity.getResources().getString(R.string.startapp_app_id),
                    activity.getResources().getString(R.string.unity_game_id),
                    activity.getResources().getString(R.string.unity_banner_placement_id),
                    activity.getResources().getString(R.string.unity_interstitial_placement_id),
                    activity.getResources().getString(R.string.unity_rewarded_placement_id),
                    activity.getResources().getString(R.string.applovin_banner_unit_id),
                    activity.getResources().getString(R.string.applovin_interstitial_unit_id),
                    activity.getResources().getString(R.string.applovin_rewarded_unit_id),
                    activity.getResources().getString(R.string.applovin_native_manual_unit_id),
                    activity.getResources().getString(R.string.applovin_open_ad_unit_id),
                    activity.getResources().getString(R.string.applovin_banner_zone_id),
                    activity.getResources().getString(R.string.applovin_banner_mrec_zone_id),
                    activity.getResources().getString(R.string.applovin_interstitial_zone_id),
                    activity.getResources().getString(R.string.applovin_rewarded_zone_id),
                    activity.getResources().getString(R.string.ironsource_app_key),
                    activity.getResources().getString(R.string.ironsource_banner_placement_name),
                    activity.getResources().getString(R.string.ironsource_interstitial_placement_name),
                    activity.getResources().getString(R.string.ironsource_rewarded_placement_name),
                    activity.getResources().getString(R.string.wortise_app_id),
                    activity.getResources().getString(R.string.wortise_banner_unit_id),
                    activity.getResources().getString(R.string.wortise_interstitial_unit_id),
                    activity.getResources().getString(R.string.wortise_rewarded_unit_id),
                    activity.getResources().getString(R.string.wortise_native_unit_id),
                    activity.getResources().getString(R.string.wortise_app_open_unit_id),
                    activity.getResources().getInteger(R.integer.interstitial_ad_interval),
                    activity.getResources().getInteger(R.integer.native_ad_index_2_columns),
                    activity.getResources().getInteger(R.integer.native_ad_index_3_columns)
            );
        } else {
            adsPref.saveAds(
                    ads.ad_status.replace("on", "1"),
                    ads.ad_type,
                    ads.backup_ads,
                    ads.admob_publisher_id,
                    ads.admob_banner_unit_id,
                    ads.admob_interstitial_unit_id,
                    ads.admob_rewarded_unit_id,
                    ads.admob_native_unit_id,
                    ads.admob_app_open_ad_unit_id,
                    ads.ad_manager_banner_unit_id,
                    ads.ad_manager_interstitial_unit_id,
                    ads.ad_manager_rewarded_unit_id,
                    ads.ad_manager_native_unit_id,
                    ads.ad_manager_app_open_ad_unit_id,
                    ads.fan_banner_unit_id,
                    ads.fan_interstitial_unit_id,
                    ads.fan_rewarded_unit_id,
                    ads.fan_native_unit_id,
                    ads.startapp_app_id,
                    ads.unity_game_id,
                    ads.unity_banner_placement_id,
                    ads.unity_interstitial_placement_id,
                    ads.unity_rewarded_placement_id,
                    ads.applovin_banner_ad_unit_id,
                    ads.applovin_interstitial_ad_unit_id,
                    ads.applovin_rewarded_ad_unit_id,
                    ads.applovin_native_ad_manual_unit_id,
                    ads.applovin_app_open_ad_unit_id,
                    ads.applovin_banner_zone_id,
                    ads.applovin_banner_mrec_zone_id,
                    ads.applovin_interstitial_zone_id,
                    ads.applovin_rewarded_zone_id,
                    ads.ironsource_app_key,
                    ads.ironsource_banner_placement_name,
                    ads.ironsource_interstitial_placement_name,
                    ads.ironsource_rewarded_placement_name,
                    ads.wortise_app_id,
                    ads.wortise_banner_unit_id,
                    ads.wortise_interstitial_unit_id,
                    ads.wortise_rewarded_unit_id,
                    ads.wortise_native_unit_id,
                    ads.wortise_app_open_unit_id,
                    ads.interstitial_ad_interval,
                    ads.native_ad_index_2,
                    ads.native_ad_index_3
            );
        }
    }

    public void saveAdsPlacement(Placement placement) {
        adsPref.setPlacement(
                placement.banner_home == 1,
                placement.banner_post_details == 1,
                placement.banner_category_details == 1,
                placement.banner_search == 1,
                placement.interstitial_post_list == 1,
                placement.rewarded_post_details == 1,
                placement.native_ad_post_list == 1,
                placement.native_ad_exit_dialog == 1,
                placement.app_open_ad_on_start == 1,
                placement.app_open_ad_on_resume == 1
        );
    }

    public void updateConsentStatus() {
        if (Config.ENABLE_GDPR_EU_CONSENT) {
            gdpr.updateGDPRConsentStatus(adsPref.getMainAds(), false, false);
        }
    }

}
