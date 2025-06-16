package com.app.goodwalls.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

@Entity(tableName = "tbl_favorite")
public class Wallpaper implements Serializable {

    @PrimaryKey
    public long saved_date = -1;

    @Expose
    @ColumnInfo(name = "image_id")
    public String image_id;

    @Expose
    @ColumnInfo(name = "image_name")
    public String image_name;

    @Expose
    @ColumnInfo(name = "image_thumb")
    public String image_thumb;

    @Expose
    @ColumnInfo(name = "image_upload")
    public String image_upload;

    @Expose
    @ColumnInfo(name = "image_url")
    public String image_url;

    @Expose
    @ColumnInfo(name = "type")
    public String type;

    @Expose
    @ColumnInfo(name = "resolution")
    public String resolution;

    @Expose
    @ColumnInfo(name = "size")
    public String size;

    @Expose
    @ColumnInfo(name = "mime")
    public String mime;

    @Expose
    @ColumnInfo(name = "views")
    public int views;

    @Expose
    @ColumnInfo(name = "downloads")
    public int downloads;

    @Expose
    @ColumnInfo(name = "featured")
    public String featured;

    @Expose
    @ColumnInfo(name = "tags")
    public String tags;

    @Expose
    @ColumnInfo(name = "category_id")
    public String category_id;

    @Expose
    @ColumnInfo(name = "category_name")
    public String category_name;

    @Expose
    @ColumnInfo(name = "last_update")
    public String last_update;

    @Expose
    @ColumnInfo(name = "rewarded")
    public int rewarded;

    @Ignore
    public Wallpaper() {

    }

    public Wallpaper(long saved_date, String image_id, String image_name, String image_thumb, String image_upload, String image_url, String type, String resolution, String size, String mime, int views, int downloads, String featured, String tags, String category_id, String category_name, String last_update, int rewarded) {
        this.saved_date = saved_date;
        this.image_id = image_id;
        this.image_name = image_name;
        this.image_thumb = image_thumb;
        this.image_upload = image_upload;
        this.image_url = image_url;
        this.type = type;
        this.resolution = resolution;
        this.size = size;
        this.mime = mime;
        this.views = views;
        this.downloads = downloads;
        this.featured = featured;
        this.tags = tags;
        this.category_id = category_id;
        this.category_name = category_name;
        this.last_update = last_update;
        this.rewarded = rewarded;
    }
}
