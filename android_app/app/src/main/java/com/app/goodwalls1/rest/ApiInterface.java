package com.app.goodwalls1.rest;

import com.app.goodwalls1.callback.CallbackAds;
import com.app.goodwalls1.callback.CallbackCategory;
import com.app.goodwalls1.callback.CallbackSettings;
import com.app.goodwalls1.callback.CallbackUser;
import com.app.goodwalls1.callback.CallbackWallpaper;
import com.app.goodwalls1.model.Wallpaper;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    String CACHE = "Cache-Control: max-age=0";
    String AGENT = "Data-Agent: Material Wallpaper";

    @Headers({CACHE, AGENT})
    @GET("api.php?get_new_wallpapers")
    Call<CallbackWallpaper> getWallpapers(
            @Query("page") int page,
            @Query("count") int count,
            @Query("filter") String filter,
            @Query("order") String order,
            @Query("category") String category
    );

    @Headers({CACHE, AGENT})
    @GET("api.php?get_wallpaper_details")
    Call<CallbackWallpaper> getWallpaperDetails(
            @Query("id") String id
    );

    @Headers({CACHE, AGENT})
    @GET("api.php?get_categories")
    Call<CallbackCategory> getCategories();

    @Headers({CACHE, AGENT})
    @GET("api.php?get_search")
    Call<CallbackWallpaper> getSearch(
            @Query("page") int page,
            @Query("count") int count,
            @Query("search") String search,
            @Query("order") String order
    );

    @Headers({CACHE, AGENT})
    @GET("api.php?get_search_category")
    Call<CallbackCategory> getSearchCategory(
            @Query("search") String search
    );

    @FormUrlEncoded
    @POST("api.php?update_view")
    Call<Wallpaper> updateView(
            @Field("image_id") String image_id
    );

    @FormUrlEncoded
    @POST("api.php?update_download")
    Call<Wallpaper> updateDownload(
            @Field("image_id") String image_id
    );

    @Headers({CACHE, AGENT})
    @GET("api.php?get_ads")
    Call<CallbackAds> getAds();

    @Headers({CACHE, AGENT})
    @GET("api.php?get_settings")
    Call<CallbackSettings> getSettings(
            @Query("package_name") String package_name
    );

    @Headers({CACHE, AGENT})
    @GET("user.json")
    Call<CallbackUser> getUsers();

}
