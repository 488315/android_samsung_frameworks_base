package com.android.systemui.pluginlock.model;

import com.google.gson.annotations.SerializedName;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ServiceBoxData {

    @SerializedName("clock_info")
    private ClockInfo mClockInfo;

    @SerializedName("expandable")
    private Integer mExpandable = 0;

    @SerializedName("top_y")
    private Integer mTopY = -1;

    @SerializedName("bottom_y")
    private Integer mBottomY = -1;

    @SerializedName("visibility")
    private Integer mVisibility = 0;

    @SerializedName("top_y_land")
    private Integer mTopYLand = -1;

    @SerializedName("bottom_y_land")
    private Integer mBottomYLand = -1;

    @SerializedName("visibility_land")
    private Integer mVisibilityLand = 0;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ClockInfo {

        @SerializedName("clock_type")
        Integer mClockType;

        @SerializedName("gravity")
        Integer mGravity;

        @SerializedName("gravity_land")
        Integer mGravityLand;

        @SerializedName("recover_type")
        Integer mRecoverType = 0;

        @SerializedName("clock_scale")
        float mClockScale = 1.0f;

        @SerializedName("clock_padding_start")
        Integer mPaddingStart = -1;

        @SerializedName("clock_padding_end")
        Integer mPaddingEnd = -1;

        @SerializedName("clock_scale_land")
        float mClockScaleLand = 1.0f;

        @SerializedName("clock_padding_start_land")
        Integer mPaddingStartLand = -1;

        @SerializedName("clock_padding_end_land")
        Integer mPaddingEndLand = -1;

        public ClockInfo(ServiceBoxData serviceBoxData) {
        }

        public final Object clone() {
            return (ClockInfo) super.clone();
        }

        public final Integer getClockType() {
            Integer num = this.mClockType;
            if (num != null) {
                return num;
            }
            return -1;
        }

        public final Integer getGravity() {
            Integer num = this.mGravity;
            if (num != null) {
                return num;
            }
            return -1;
        }

        public final Integer getGravityLand() {
            Integer num = this.mGravityLand;
            if (num != null) {
                return num;
            }
            return -1;
        }

        public final Integer getPaddingEnd() {
            Integer num = this.mPaddingEnd;
            if (num != null) {
                return num;
            }
            return -1;
        }

        public final Integer getPaddingEndLand() {
            Integer num = this.mPaddingEndLand;
            if (num != null) {
                return num;
            }
            return -1;
        }

        public final Integer getPaddingStart() {
            Integer num = this.mPaddingStart;
            if (num != null) {
                return num;
            }
            return -1;
        }

        public final Integer getPaddingStartLand() {
            Integer num = this.mPaddingStartLand;
            if (num != null) {
                return num;
            }
            return -1;
        }

        public final Integer getRecoverType() {
            Integer num = this.mRecoverType;
            if (num != null) {
                return num;
            }
            return 1;
        }

        public final float getScale() {
            return this.mClockScale;
        }

        public final float getScaleLand() {
            return this.mClockScaleLand;
        }

        public final void setClockType(int i) {
            this.mClockType = Integer.valueOf(i);
        }

        public final void setGravity(Integer num) {
            this.mGravity = num;
        }

        public final void setGravityLand(Integer num) {
            this.mGravityLand = num;
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

        public final void setScale(float f) {
            this.mClockScale = f;
        }

        public final void setScaleLand(float f) {
            this.mClockScaleLand = f;
        }
    }

    public final Object clone() {
        return (ServiceBoxData) super.clone();
    }

    public final Integer getBottomY() {
        return this.mBottomY;
    }

    public final Integer getBottomYLand() {
        return this.mBottomYLand;
    }

    public final ClockInfo getClockInfo() {
        if (this.mClockInfo == null) {
            this.mClockInfo = new ClockInfo(this);
        }
        return this.mClockInfo;
    }

    public final Integer getTopY() {
        return this.mTopY;
    }

    public final Integer getTopYLand() {
        return this.mTopYLand;
    }

    public final Integer getVisibility() {
        return this.mVisibility;
    }

    public final Integer getVisibilityLand() {
        return this.mVisibilityLand;
    }

    public final void setBottomY(Integer num) {
        this.mBottomY = num;
    }

    public final void setBottomYLand(Integer num) {
        this.mBottomYLand = num;
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
}
