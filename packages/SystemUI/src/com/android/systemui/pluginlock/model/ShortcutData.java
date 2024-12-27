package com.android.systemui.pluginlock.model;

import com.google.gson.annotations.SerializedName;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public class ShortcutData {

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

    public Integer getImageSize() {
        Integer num = this.mImageSize;
        return Integer.valueOf(num == null ? -1 : num.intValue());
    }

    public Integer getPaddingBottom() {
        Integer num = this.mPaddingBottom;
        return Integer.valueOf(num == null ? -1 : num.intValue());
    }

    public Integer getPaddingBottomLand() {
        Integer num = this.mPaddingBottomLand;
        return Integer.valueOf(num == null ? -1 : num.intValue());
    }

    public Integer getPaddingSide() {
        Integer num = this.mPaddingSide;
        return Integer.valueOf(num == null ? -1 : num.intValue());
    }

    public Integer getPaddingSideLand() {
        Integer num = this.mPaddingSideLand;
        return Integer.valueOf(num == null ? -1 : num.intValue());
    }

    public String getShortcutInfo() {
        return this.mShortcutInfo;
    }

    public Integer getVisibility() {
        Integer num = this.mVisibility;
        return Integer.valueOf(num == null ? -1 : num.intValue());
    }

    public void setImageSize(Integer num) {
        this.mImageSize = num;
    }

    public void setPaddingBottom(Integer num) {
        this.mPaddingBottom = num;
    }

    public void setPaddingBottomLand(Integer num) {
        this.mPaddingBottomLand = num;
    }

    public void setPaddingSide(Integer num) {
        this.mPaddingSide = num;
    }

    public void setPaddingSideLand(Integer num) {
        this.mPaddingSideLand = num;
    }

    public void setShortcutInfo(String str) {
        this.mShortcutInfo = str;
    }

    public void setVisibility(Integer num) {
        this.mVisibility = num;
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public ShortcutData m2028clone() throws CloneNotSupportedException {
        return (ShortcutData) super.clone();
    }
}
