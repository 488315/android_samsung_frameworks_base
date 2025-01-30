package com.android.systemui.pluginlock.model;

import com.google.gson.annotations.SerializedName;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class MusicData {

    @SerializedName("top_y")
    Integer mTopY = -1;

    @SerializedName("padding_start")
    Integer mPaddingStart = -1;

    @SerializedName("padding_end")
    Integer mPaddingEnd = -1;

    @SerializedName("height")
    Integer mHeight = -1;

    @SerializedName("width")
    Integer mWidth = -1;

    @SerializedName("gravity")
    Integer mGravity = -1;

    @SerializedName("visibility")
    Integer mVisibility = 0;

    @SerializedName("top_y_land")
    Integer mTopYLand = -1;

    @SerializedName("padding_start_land")
    Integer mPaddingStartLand = -1;

    @SerializedName("padding_end_land")
    Integer mPaddingEndLand = -1;

    @SerializedName("height_land")
    Integer mHeightLand = -1;

    @SerializedName("width_land")
    Integer mWidthLand = -1;

    @SerializedName("gravity_land")
    Integer mGravityLand = -1;

    @SerializedName("visibility_land")
    Integer mVisibilityLand = 0;

    public final Object clone() {
        return (MusicData) super.clone();
    }

    public final Integer getGravity() {
        Integer num = this.mGravity;
        return Integer.valueOf(num == null ? -1 : num.intValue());
    }

    public final Integer getGravityLand() {
        Integer num = this.mGravityLand;
        return Integer.valueOf(num == null ? -1 : num.intValue());
    }

    public final Integer getPaddingEnd() {
        Integer num = this.mPaddingEnd;
        return Integer.valueOf(num == null ? -1 : num.intValue());
    }

    public final Integer getPaddingEndLand() {
        Integer num = this.mPaddingEndLand;
        return Integer.valueOf(num == null ? -1 : num.intValue());
    }

    public final Integer getPaddingStart() {
        Integer num = this.mPaddingStart;
        return Integer.valueOf(num == null ? -1 : num.intValue());
    }

    public final Integer getPaddingStartLand() {
        Integer num = this.mPaddingStartLand;
        return Integer.valueOf(num == null ? -1 : num.intValue());
    }

    public final Integer getTopY() {
        return this.mTopY;
    }

    public final Integer getTopYLand() {
        return this.mTopYLand;
    }

    public final Integer getVisibility() {
        Integer num = this.mVisibility;
        return Integer.valueOf(num == null ? -1 : num.intValue());
    }

    public final Integer getVisibilityLand() {
        Integer num = this.mVisibilityLand;
        return Integer.valueOf(num == null ? -1 : num.intValue());
    }

    public final void setGravity(Integer num) {
        this.mGravity = num;
    }

    public final void setGravityLand(Integer num) {
        this.mGravityLand = num;
    }

    public final void setHeight(Integer num) {
        this.mHeight = num;
    }

    public final void setHeightLand(Integer num) {
        this.mHeightLand = num;
    }

    public final void setPaddingEnd(Integer num) {
        this.mPaddingEnd = num;
    }

    public final void setPaddingEndLand(Integer num) {
        this.mPaddingEndLand = num;
    }

    public final void setPaddingStart(Integer num) {
        this.mPaddingStart = num;
    }

    public final void setPaddingStartLand(Integer num) {
        this.mPaddingStartLand = num;
    }

    public final void setTopY(Integer num) {
        this.mTopY = num;
    }

    public final void setTopYLand(Integer num) {
        this.mTopYLand = num;
    }

    public final void setVisibility(Integer num) {
        this.mVisibility = num;
    }

    public final void setVisibilityLand(Integer num) {
        this.mVisibilityLand = num;
    }

    public final void setWidth(Integer num) {
        this.mWidth = num;
    }

    public final void setWidthLand(Integer num) {
        this.mWidthLand = num;
    }
}
