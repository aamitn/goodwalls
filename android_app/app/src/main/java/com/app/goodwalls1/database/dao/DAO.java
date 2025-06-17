package com.app.goodwalls1.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.app.goodwalls1.model.Rewarded;
import com.app.goodwalls1.model.Wallpaper;

import java.util.List;

@Dao
public interface DAO {

    //favorite
    @Query("INSERT INTO " +
            "tbl_favorite (" +
            "saved_date," +
            "image_id," +
            "image_name," +
            "image_thumb," +
            "image_upload," +
            "image_url," +
            "type," +
            "resolution," +
            "size," +
            "mime," +
            "views," +
            "downloads," +
            "featured," +
            "tags," +
            "category_id," +
            "category_name," +
            "rewarded," +
            "last_update) " +
            "VALUES (" +
            ":save_date," +
            ":image_id," +
            ":image_name," +
            ":image_thumb," +
            ":image_upload," +
            ":image_url," +
            ":type," +
            ":resolution," +
            ":size," +
            ":mime," +
            ":views," +
            ":downloads," +
            ":featured," +
            ":tags," +
            ":category_id," +
            ":category_name," +
            ":rewarded," +
            ":last_update)"
    )
    void addFavorite(
            long save_date,
            String image_id,
            String image_name,
            String image_thumb,
            String image_upload,
            String image_url,
            String type,
            String resolution,
            String size,
            String mime,
            int views,
            int downloads,
            String featured,
            String tags,
            String category_id,
            String category_name,
            int rewarded,
            String last_update
    );

    @Query("DELETE FROM tbl_favorite WHERE image_id = :image_id")
    void deleteFavorite(String image_id);

    @Query("DELETE FROM tbl_favorite")
    void deleteAllFavorite();

    @Query("SELECT * FROM tbl_favorite ORDER BY saved_date DESC")
    List<Wallpaper> getAllFavorite();

    @Query("SELECT COUNT(image_id) FROM tbl_favorite")
    Integer getAllFavoriteCount();

    @Query("SELECT * FROM tbl_favorite WHERE image_id = :image_id LIMIT 1")
    Wallpaper getFavorite(String image_id);

    //rewarded
    @Query("INSERT INTO tbl_rewarded (saved_date, image_id) VALUES (:save_date, :image_id)")
    void addRewarded(long save_date, String image_id);

    @Query("DELETE FROM tbl_rewarded WHERE image_id = :image_id")
    void deleteRewarded(String image_id);

    @Query("DELETE FROM tbl_rewarded")
    void deleteAllRewarded();

    @Query("SELECT * FROM tbl_rewarded ORDER BY saved_date DESC")
    List<Rewarded> getAllRewarded();

    @Query("SELECT COUNT(image_id) FROM tbl_rewarded")
    Integer getAllRewardedCount();

    @Query("SELECT * FROM tbl_rewarded WHERE image_id = :image_id LIMIT 1")
    Rewarded getRewarded(String image_id);

}