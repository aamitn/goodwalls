package com.app.goodwalls.database.prefs;

import android.content.Context;
import android.content.SharedPreferences;

public class AdsPref {

    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public AdsPref(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("ads_setting", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveAds(String adStatus, String mainAds, String backupAds, String admobPublisherId, String admobBannerUnitId, String admobInterstitialUnitId, String admobRewardedUnitId, String admobNativeUnitId, String admobAppOpenAdUnitId, String adManagerBannerUnitId, String adManagerInterstitialUnitId, String adManagerRewardedUnitId, String adManagerNativeUnitId, String adManagerAppOpenAdUnitId, String fanBannerUnitId, String fanInterstitialUnitId, String fanRewardedUnitId, String fanNativeUnitId, String startappAppId, String unityGameId, String unityBannerPlacementId, String unityInterstitialPlacementId, String unityRewardedPlacementId, String applovinBannerAdUnitId, String applovinInterstitialAdUnitId, String applovinRewardedAdUnitId, String applovinNativeAdManualUnitId, String applovinAppOpenAdUnitId, String applovinBannerZoneId, String applovinBannerMrecZoneId, String applovinInterstitialZoneId, String applovinRewardedZoneId, String ironsourceAppKey, String ironsourceBannerId, String ironsourceInterstitialId, String ironsourceRewardedId, String wortiseAppId, String wortiseBannerId, String wortiseInterstitialId, String wortiseRewardedId, String wortiseNativeId, String wortiseAppOpenId, int interstitialAdInterval, int nativeAdIndex2, int nativeAdIndex3) {
        editor.putString("ad_status", adStatus);
        editor.putString("ad_type", mainAds);
        editor.putString("backup_ads", backupAds);
        editor.putString("admob_publisher_id", admobPublisherId);
        editor.putString("admob_banner_unit_id", admobBannerUnitId);
        editor.putString("admob_interstitial_unit_id", admobInterstitialUnitId);
        editor.putString("admob_rewarded_unit_id", admobRewardedUnitId);
        editor.putString("admob_native_unit_id", admobNativeUnitId);
        editor.putString("admob_app_open_ad_unit_id", admobAppOpenAdUnitId);
        editor.putString("ad_manager_banner_unit_id", adManagerBannerUnitId);
        editor.putString("ad_manager_interstitial_unit_id", adManagerInterstitialUnitId);
        editor.putString("ad_manager_rewarded_unit_id", adManagerRewardedUnitId);
        editor.putString("ad_manager_native_unit_id", adManagerNativeUnitId);
        editor.putString("ad_manager_app_open_ad_unit_id", adManagerAppOpenAdUnitId);
        editor.putString("fan_banner_unit_id", fanBannerUnitId);
        editor.putString("fan_interstitial_unit_id", fanInterstitialUnitId);
        editor.putString("fan_rewarded_unit_id", fanRewardedUnitId);
        editor.putString("fan_native_unit_id", fanNativeUnitId);
        editor.putString("startapp_app_id", startappAppId);
        editor.putString("unity_game_id", unityGameId);
        editor.putString("unity_banner_placement_id", unityBannerPlacementId);
        editor.putString("unity_interstitial_placement_id", unityInterstitialPlacementId);
        editor.putString("unity_rewarded_placement_id", unityRewardedPlacementId);
        editor.putString("applovin_banner_ad_unit_id", applovinBannerAdUnitId);
        editor.putString("applovin_interstitial_ad_unit_id", applovinInterstitialAdUnitId);
        editor.putString("applovin_rewarded_ad_unit_id", applovinRewardedAdUnitId);
        editor.putString("applovin_native_ad_manual_unit_id", applovinNativeAdManualUnitId);
        editor.putString("applovin_app_open_ad_unit_id", applovinAppOpenAdUnitId);
        editor.putString("applovin_banner_zone_id", applovinBannerZoneId);
        editor.putString("applovin_banner_mrec_zone_id", applovinBannerMrecZoneId);
        editor.putString("applovin_interstitial_zone_id", applovinInterstitialZoneId);
        editor.putString("applovin_rewarded_zone_id", applovinRewardedZoneId);
        editor.putString("ironsource_app_key", ironsourceAppKey);
        editor.putString("ironsource_banner_id", ironsourceBannerId);
        editor.putString("ironsource_interstitial_id", ironsourceInterstitialId);
        editor.putString("ironsource_rewarded_id", ironsourceRewardedId);
        editor.putString("wortise_app_id", wortiseAppId);
        editor.putString("wortise_banner_id", wortiseBannerId);
        editor.putString("wortise_interstitial_id", wortiseInterstitialId);
        editor.putString("wortise_rewarded_id", wortiseRewardedId);
        editor.putString("wortise_native_id", wortiseNativeId);
        editor.putString("wortise_app_open_id", wortiseAppOpenId);
        editor.putInt("interstitial_ad_interval", interstitialAdInterval);
        editor.putInt("native_ad_index_2", nativeAdIndex2);
        editor.putInt("native_ad_index_3", nativeAdIndex3);
        editor.apply();
    }

    public String getAdStatus() {
        return sharedPreferences.getString("ad_status", "0");
    }

    public String getMainAds() {
        return sharedPreferences.getString("ad_type", "0");
    }

    public String getBackupAds() {
        return sharedPreferences.getString("backup_ads", "none");
    }

    public String getAdMobBannerId() {
        return sharedPreferences.getString("admob_banner_unit_id", "0");
    }

    public String getAdMobInterstitialId() {
        return sharedPreferences.getString("admob_interstitial_unit_id", "0");
    }

    public String getAdMobRewardedId() {
        return sharedPreferences.getString("admob_rewarded_unit_id", "0");
    }

    public String getAdMobNativeId() {
        return sharedPreferences.getString("admob_native_unit_id", "0");
    }

    public String getAdMobAppOpenAdId() {
        return sharedPreferences.getString("admob_app_open_ad_unit_id", "0");
    }

    public String getAdManagerBannerId() {
        return sharedPreferences.getString("ad_manager_banner_unit_id", "0");
    }

    public String getAdManagerInterstitialId() {
        return sharedPreferences.getString("ad_manager_interstitial_unit_id", "0");
    }

    public String getAdManagerRewardedId() {
        return sharedPreferences.getString("ad_manager_rewarded_unit_id", "0");
    }

    public String getAdManagerNativeId() {
        return sharedPreferences.getString("ad_manager_native_unit_id", "0");
    }

    public String getAdManagerAppOpenAdId() {
        return sharedPreferences.getString("ad_manager_app_open_ad_unit_id", "0");
    }

    public String getFanBannerUnitId() {
        return sharedPreferences.getString("fan_banner_unit_id", "0");
    }

    public String getFanInterstitialUnitId() {
        return sharedPreferences.getString("fan_interstitial_unit_id", "0");
    }

    public String getFanRewardedUnitId() {
        return sharedPreferences.getString("fan_rewarded_unit_id", "0");
    }

    public String getFanNativeUnitId() {
        return sharedPreferences.getString("fan_native_unit_id", "0");
    }

    public String getStartappAppID() {
        return sharedPreferences.getString("startapp_app_id", "0");
    }

    public String getUnityGameId() {
        return sharedPreferences.getString("unity_game_id", "0");
    }

    public String getUnityBannerPlacementId() {
        return sharedPreferences.getString("unity_banner_placement_id", "banner");
    }

    public String getUnityInterstitialPlacementId() {
        return sharedPreferences.getString("unity_interstitial_placement_id", "video");
    }

    public String getUnityRewardedPlacementId() {
        return sharedPreferences.getString("unity_rewarded_placement_id", "rewardedVideo");
    }

    public String getAppLovinBannerAdUnitId() {
        return sharedPreferences.getString("applovin_banner_ad_unit_id", "0");
    }

    public String getAppLovinInterstitialAdUnitId() {
        return sharedPreferences.getString("applovin_interstitial_ad_unit_id", "0");
    }

    public String getAppLovinRewardedAdUnitId() {
        return sharedPreferences.getString("applovin_rewarded_ad_unit_id", "0");
    }

    public String getAppLovinNativeAdManualUnitId() {
        return sharedPreferences.getString("applovin_native_ad_manual_unit_id", "0");
    }

    public String getAppLovinAppOpenAdUnitId() {
        return sharedPreferences.getString("applovin_app_open_ad_unit_id", "0");
    }

    public String getAppLovinBannerZoneId() {
        return sharedPreferences.getString("applovin_banner_zone_id", "0");
    }

    public String getAppLovinBannerMrecZoneId() {
        return sharedPreferences.getString("applovin_banner_mrec_zone_id", "0");
    }

    public String getAppLovinInterstitialZoneId() {
        return sharedPreferences.getString("applovin_interstitial_zone_id", "0");
    }

    public String getAppLovinRewardedZoneId() {
        return sharedPreferences.getString("applovin_rewarded_zone_id", "0");
    }

    public String getIronSourceAppKey() {
        return sharedPreferences.getString("ironsource_app_key", "0");
    }

    public String getIronSourceBannerId() {
        return sharedPreferences.getString("ironsource_banner_id", "0");
    }

    public String getIronSourceInterstitialId() {
        return sharedPreferences.getString("ironsource_interstitial_id", "0");
    }

    public String getIronSourceRewardedId() {
        return sharedPreferences.getString("ironsource_rewarded_id", "0");
    }

    public String getWortiseAppId() {
        return sharedPreferences.getString("wortise_app_id", "0");
    }

    public String getWortiseAppOpenId() {
        return sharedPreferences.getString("wortise_app_open_id", "0");
    }

    public String getWortiseBannerId() {
        return sharedPreferences.getString("wortise_banner_id", "0");
    }

    public String getWortiseInterstitialId() {
        return sharedPreferences.getString("wortise_interstitial_id", "0");
    }

    public String getWortiseRewardedId() {
        return sharedPreferences.getString("wortise_rewarded_id", "0");
    }

    public String getWortiseNativeId() {
        return sharedPreferences.getString("wortise_native_id", "0");
    }

    public int getInterstitialAdInterval() {
        return sharedPreferences.getInt("interstitial_ad_interval", 0);
    }

    public int getNativeAdIndex2() {
        return sharedPreferences.getInt("native_ad_index_2", 4);
    }

    public int getNativeAdIndex3() {
        return sharedPreferences.getInt("native_ad_index_3", 6);
    }

    public void setNativeAdStyle(String nativeAdStylePostList, String nativeAdStyleExitDialog) {
        editor.putString("native_ad_style_post_list", nativeAdStylePostList);
        editor.putString("native_ad_style_exit_dialog", nativeAdStyleExitDialog);
        editor.apply();
    }

    public String getNativeAdStylePostList() {
        return sharedPreferences.getString("native_ad_style_post_list", "medium");
    }

    public String getNativeAdStylePostDetails() {
        return sharedPreferences.getString("native_ad_style_post_details", "large");
    }

    public String getNativeAdStyleExitDialog() {
        return sharedPreferences.getString("native_ad_style_exit_dialog", "large");
    }

    public void setPlacement(boolean isBannerHome, boolean isBannerPostDetails, boolean isBannerCategoryDetails, boolean isBannerSearch, boolean isInterstitialPostList, boolean isRewardedPostDetails, boolean isNativeAdPostList, boolean isNativeAdExitDialog, boolean isAppOpenAdOnStart, boolean isAppOpenAdOnResume) {
        editor.putBoolean("banner_home", isBannerHome);
        editor.putBoolean("banner_post_details", isBannerPostDetails);
        editor.putBoolean("banner_category_details", isBannerCategoryDetails);
        editor.putBoolean("banner_search", isBannerSearch);
        editor.putBoolean("interstitial_post_list", isInterstitialPostList);
        editor.putBoolean("rewarded_post_details", isRewardedPostDetails);
        editor.putBoolean("native_ad_post_list", isNativeAdPostList);
        editor.putBoolean("native_ad_exit_dialog", isNativeAdExitDialog);
        editor.putBoolean("app_open_ad_on_start", isAppOpenAdOnStart);
        editor.putBoolean("app_open_ad_on_resume", isAppOpenAdOnResume);
        editor.apply();
    }

    public boolean getIsBannerHome() {
        return sharedPreferences.getBoolean("banner_home", true);
    }

    public boolean getIsBannerPostDetails() {
        return sharedPreferences.getBoolean("banner_post_details", true);
    }

    public boolean getIsBannerCategoryDetails() {
        return sharedPreferences.getBoolean("banner_category_details", true);
    }

    public boolean getIsBannerSearch() {
        return sharedPreferences.getBoolean("banner_search", true);
    }

    public boolean getIsInterstitialPostList() {
        return sharedPreferences.getBoolean("interstitial_post_list", true);
    }

    public boolean getIsRewardedPostDetails() {
        return sharedPreferences.getBoolean("rewarded_post_details", true);
    }

    public boolean getIsNativeAdPostList() {
        return sharedPreferences.getBoolean("native_ad_post_list", true);
    }

    public boolean getIsNativeAdExitDialog() {
        return sharedPreferences.getBoolean("native_ad_exit_dialog", true);
    }

    public boolean getIsAppOpenAdOnStart() {
        return sharedPreferences.getBoolean("app_open_ad_on_start", true);
    }

    public boolean getIsAppOpenAdOnResume() {
        return sharedPreferences.getBoolean("app_open_ad_on_resume", true);
    }

    public boolean getIsOpenAd() {
        return sharedPreferences.getBoolean("open_ads", false);
    }

    public void setIsOpenAd(boolean isOpenAd) {
        editor.putBoolean("open_ads", isOpenAd);
        editor.apply();
    }

    public boolean getLegacyGDPR() {
        return sharedPreferences.getBoolean("legacy_gdpr", false);
    }

    public int getPlacementStatus() {
        return sharedPreferences.getInt("placement_status", 1);
    }

}
