package com.android.systemui.pluginlock.model;

import com.google.gson.annotations.SerializedName;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class WallpaperData {

    @SerializedName("update_style")
    private Integer mUpdateStyle = 0;

    @SerializedName("recover_type")
    private Integer mRecoverType = 0;

    @SerializedName("path")
    private String mPath = "";

    @SerializedName("id")
    private Integer mId = -1;

    public boolean equals(Object obj) {
        String str;
        Integer num;
        if (obj instanceof WallpaperData) {
            WallpaperData wallpaperData = (WallpaperData) obj;
            Integer num2 = this.mUpdateStyle;
            if (((num2 == null && wallpaperData.mUpdateStyle == null) || (num2 != null && num2.equals(wallpaperData.mUpdateStyle))) && ((((str = this.mPath) == null && wallpaperData.mPath == null) || (str != null && str.equals(wallpaperData.mPath))) && (((num = this.mId) == null && wallpaperData.mId == null) || (num != null && num.equals(wallpaperData.mId))))) {
                Integer num3 = this.mRecoverType;
                if (num3 == null && wallpaperData.mRecoverType == null) {
                    return true;
                }
                if (num3 != null && num3.equals(wallpaperData.mRecoverType)) {
                    return true;
                }
            }
        }
        return false;
    }

    public Integer getId() {
        return this.mId;
    }

    public String getPath() {
        return this.mPath;
    }

    public Integer getRecoverType() {
        Integer num = this.mRecoverType;
        if (num != null) {
            return num;
        }
        return 1;
    }

    public Integer getUpdateStyle() {
        return this.mUpdateStyle;
    }

    public void setId(Integer num) {
        this.mId = num;
    }

    public void setPath(String str) {
        this.mPath = str;
    }

    public void setRecoverType(Integer num) {
        this.mRecoverType = num;
    }

    public void setUpdateStyle(Integer num) {
        this.mUpdateStyle = num;
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public WallpaperData m2667clone() throws CloneNotSupportedException {
        return (WallpaperData) super.clone();
    }
}
