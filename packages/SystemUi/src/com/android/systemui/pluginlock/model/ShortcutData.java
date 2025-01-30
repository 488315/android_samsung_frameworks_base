package com.android.systemui.pluginlock.model;

import com.google.gson.annotations.SerializedName;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ShortcutData {

    @SerializedName("image_size")
    private Integer mImageSize;

    @SerializedName("shortcutInfo")
    private String mShortcutInfo;

    @SerializedName("visibility")
    private Integer mVisibility = -1;

    @SerializedName("margin_bottom")
    private Integer mPaddingBottom = -1;

    @SerializedName("margin_side")
    private Integer mPaddingSide = -1;

    @SerializedName("margin_bottom_land")
    private Integer mPaddingBottomLand = -1;

    @SerializedName("margin_side_land")
    private Integer mPaddingSideLand = -1;

    public final Object clone() {
        return (ShortcutData) super.clone();
    }

    public final Integer getVisibility() {
        Integer num = this.mVisibility;
        return Integer.valueOf(num == null ? -1 : num.intValue());
    }

    public final void setImageSize(Integer num) {
        this.mImageSize = num;
    }

    public final void setPaddingBottom(Integer num) {
        this.mPaddingBottom = num;
    }

    public final void setPaddingBottomLand(Integer num) {
        this.mPaddingBottomLand = num;
    }

    public final void setPaddingSide(Integer num) {
        this.mPaddingSide = num;
    }

    public final void setPaddingSideLand(Integer num) {
        this.mPaddingSideLand = num;
    }

    public final void setShortcutInfo(String str) {
        this.mShortcutInfo = str;
    }

    public final void setVisibility(Integer num) {
        this.mVisibility = num;
    }
}
