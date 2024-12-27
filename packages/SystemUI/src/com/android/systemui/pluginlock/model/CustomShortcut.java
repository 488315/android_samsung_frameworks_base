package com.android.systemui.pluginlock.model;

import com.android.systemui.util.SystemUIAnalytics;
import com.google.gson.annotations.SerializedName;

public class CustomShortcut {

    @SerializedName("direction")
    private Integer mDirection;

    @SerializedName("icon_size")
    private Integer mIconSize;

    @SerializedName(SystemUIAnalytics.QPPE_KEY_EDITED_BUTTON_POSITION)
    private Integer mPosition;

    @SerializedName("shortcut_info")
    private String mShortCutInfo;

    @SerializedName("margin_bottom")
    private Integer mPaddingBottom = -1;

    @SerializedName("margin_side")
    private Integer mPaddingSide = -1;

    @SerializedName("margin_bottom_land")
    private Integer mPaddingBottomLand = -1;

    @SerializedName("margin_side_land")
    private Integer mPaddingSideLand = -1;

    public Integer getDirection() {
        return this.mDirection;
    }

    public Integer getIconSize() {
        return this.mIconSize;
    }

    public Integer getPaddingBottom() {
        Integer num = this.mPaddingBottom;
        if (num == null) {
            return -1;
        }
        return num;
    }

    public Integer getPaddingBottomLand() {
        Integer num = this.mPaddingBottomLand;
        if (num == null) {
            return -1;
        }
        return num;
    }

    public Integer getPaddingSide() {
        Integer num = this.mPaddingSide;
        if (num == null) {
            return -1;
        }
        return num;
    }

    public Integer getPaddingSideLand() {
        Integer num = this.mPaddingSideLand;
        if (num == null) {
            return -1;
        }
        return num;
    }

    public Integer getPosition() {
        return this.mPosition;
    }

    public String getShortCutInfo() {
        return this.mShortCutInfo;
    }

    public void setDirection(Integer num) {
        this.mDirection = num;
    }

    public void setIconSize(Integer num) {
        this.mIconSize = num;
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

    public void setPosition(Integer num) {
        this.mPosition = num;
    }

    public void setShortCutInfo(String str) {
        this.mShortCutInfo = str;
    }
}
