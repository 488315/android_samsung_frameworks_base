package com.android.systemui.pluginlock.model;

import com.google.gson.annotations.SerializedName;

public class MusicData {

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

    public Integer getGravity() {
        Integer num = this.mGravity;
        return Integer.valueOf(num == null ? -1 : num.intValue());
    }

    public Integer getGravityLand() {
        Integer num = this.mGravityLand;
        return Integer.valueOf(num == null ? -1 : num.intValue());
    }

    public Integer getHeight() {
        Integer num = this.mHeight;
        return Integer.valueOf(num == null ? -1 : num.intValue());
    }

    public Integer getHeightLand() {
        Integer num = this.mHeightLand;
        return Integer.valueOf(num == null ? -1 : num.intValue());
    }

    public Integer getPaddingEnd() {
        Integer num = this.mPaddingEnd;
        return Integer.valueOf(num == null ? -1 : num.intValue());
    }

    public Integer getPaddingEndLand() {
        Integer num = this.mPaddingEndLand;
        return Integer.valueOf(num == null ? -1 : num.intValue());
    }

    public Integer getPaddingStart() {
        Integer num = this.mPaddingStart;
        return Integer.valueOf(num == null ? -1 : num.intValue());
    }

    public Integer getPaddingStartLand() {
        Integer num = this.mPaddingStartLand;
        return Integer.valueOf(num == null ? -1 : num.intValue());
    }

    public Integer getTopY() {
        return this.mTopY;
    }

    public Integer getTopYLand() {
        return this.mTopYLand;
    }

    public Integer getVisibility() {
        Integer num = this.mVisibility;
        return Integer.valueOf(num == null ? -1 : num.intValue());
    }

    public Integer getVisibilityLand() {
        Integer num = this.mVisibilityLand;
        return Integer.valueOf(num == null ? -1 : num.intValue());
    }

    public Integer getWidth() {
        Integer num = this.mWidth;
        return Integer.valueOf(num == null ? -1 : num.intValue());
    }

    public Integer getWidthLand() {
        Integer num = this.mWidthLand;
        return Integer.valueOf(num == null ? -1 : num.intValue());
    }

    public void setGravity(Integer num) {
        this.mGravity = num;
    }

    public void setGravityLand(Integer num) {
        this.mGravityLand = num;
    }

    public void setHeight(Integer num) {
        this.mHeight = num;
    }

    public void setHeightLand(Integer num) {
        this.mHeightLand = num;
    }

    public void setPaddingEnd(Integer num) {
        this.mPaddingEnd = num;
    }

    public void setPaddingEndLand(Integer num) {
        this.mPaddingEndLand = num;
    }

    public void setPaddingStart(Integer num) {
        this.mPaddingStart = num;
    }

    public void setPaddingStartLand(Integer num) {
        this.mPaddingStartLand = num;
    }

    public void setTopY(Integer num) {
        this.mTopY = num;
    }

    public void setTopYLand(Integer num) {
        this.mTopYLand = num;
    }

    public void setVisibility(Integer num) {
        this.mVisibility = num;
    }

    public void setVisibilityLand(Integer num) {
        this.mVisibilityLand = num;
    }

    public void setWidth(Integer num) {
        this.mWidth = num;
    }

    public void setWidthLand(Integer num) {
        this.mWidthLand = num;
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public MusicData m2021clone() throws CloneNotSupportedException {
        return (MusicData) super.clone();
    }
}
