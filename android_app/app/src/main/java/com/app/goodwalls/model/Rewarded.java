package com.app.goodwalls.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

@Entity(tableName = "tbl_rewarded")
public class Rewarded implements Serializable {

    @PrimaryKey
    public long saved_date = -1;

    @Expose
    @ColumnInfo(name = "image_id")
    public String image_id;

}
