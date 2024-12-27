package com.android.systemui.pluginlock.model;

import com.google.gson.annotations.SerializedName;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
        if (!(obj instanceof WallpaperData)) {
            return false;
        }
        WallpaperData wallpaperData = (WallpaperData) obj;
        Integer num = this.mUpdateStyle;
        if (!(num == null && wallpaperData.mUpdateStyle == null) && (num == null || !num.equals(wallpaperData.mUpdateStyle))) {
            return false;
        }
        String str = this.mPath;
        if (!(str == null && wallpaperData.mPath == null) && (str == null || !str.equals(wallpaperData.mPath))) {
            return false;
        }
        Integer num2 = this.mId;
        if (!(num2 == null && wallpaperData.mId == null) && (num2 == null || !num2.equals(wallpaperData.mId))) {
            return false;
        }
        Integer num3 = this.mRecoverType;
        return (num3 == null && wallpaperData.mRecoverType == null) || (num3 != null && num3.equals(wallpaperData.mRecoverType));
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
    public WallpaperData m2029clone() throws CloneNotSupportedException {
        return (WallpaperData) super.clone();
    }
}
