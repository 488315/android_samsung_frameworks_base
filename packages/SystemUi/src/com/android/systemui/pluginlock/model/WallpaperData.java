package com.android.systemui.pluginlock.model;

import com.google.gson.annotations.SerializedName;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WallpaperData {

    @SerializedName("update_style")
    private Integer mUpdateStyle = 0;

    @SerializedName("recover_type")
    private Integer mRecoverType = 0;

    @SerializedName("path")
    private String mPath = "";

    @SerializedName("id")
    private Integer mId = -1;

    public final Object clone() {
        return (WallpaperData) super.clone();
    }

    public final boolean equals(Object obj) {
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

    public final Integer getRecoverType() {
        Integer num = this.mRecoverType;
        if (num != null) {
            return num;
        }
        return 1;
    }

    public final Integer getUpdateStyle() {
        return this.mUpdateStyle;
    }
}
