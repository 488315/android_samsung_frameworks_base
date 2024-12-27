package com.android.systemui.pluginlock.model;

import com.google.gson.annotations.SerializedName;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class ServiceBoxData {

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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public class ClockInfo {

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

        public Integer getClockType() {
            Integer num = this.mClockType;
            if (num != null) {
                return num;
            }
            return -1;
        }

        public Integer getGravity() {
            Integer num = this.mGravity;
            if (num != null) {
                return num;
            }
            return -1;
        }

        public Integer getGravityLand() {
            Integer num = this.mGravityLand;
            if (num != null) {
                return num;
            }
            return -1;
        }

        public Integer getPaddingEnd() {
            Integer num = this.mPaddingEnd;
            if (num != null) {
                return num;
            }
            return -1;
        }

        public Integer getPaddingEndLand() {
            Integer num = this.mPaddingEndLand;
            if (num != null) {
                return num;
            }
            return -1;
        }

        public Integer getPaddingStart() {
            Integer num = this.mPaddingStart;
            if (num != null) {
                return num;
            }
            return -1;
        }

        public Integer getPaddingStartLand() {
            Integer num = this.mPaddingStartLand;
            if (num != null) {
                return num;
            }
            return -1;
        }

        public Integer getRecoverType() {
            Integer num = this.mRecoverType;
            if (num != null) {
                return num;
            }
            return 1;
        }

        public float getScale() {
            return this.mClockScale;
        }

        public float getScaleLand() {
            return this.mClockScaleLand;
        }

        public void setClockType(int i) {
            this.mClockType = Integer.valueOf(i);
        }

        public void setGravity(Integer num) {
            this.mGravity = num;
        }

        public void setGravityLand(Integer num) {
            this.mGravityLand = num;
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

        public void setRecoverType(Integer num) {
            this.mRecoverType = num;
        }

        public void setScale(float f) {
            this.mClockScale = f;
        }

        public void setScaleLand(float f) {
            this.mClockScaleLand = f;
        }

        /* renamed from: clone, reason: merged with bridge method [inline-methods] */
        public ClockInfo m2027clone() throws CloneNotSupportedException {
            return (ClockInfo) super.clone();
        }
    }

    public Integer getBottomY() {
        return this.mBottomY;
    }

    public Integer getBottomYLand() {
        return this.mBottomYLand;
    }

    public ClockInfo getClockInfo() {
        if (this.mClockInfo == null) {
            this.mClockInfo = new ClockInfo();
        }
        return this.mClockInfo;
    }

    public Integer getExpandable() {
        return this.mExpandable;
    }

    public Integer getTopY() {
        return this.mTopY;
    }

    public Integer getTopYLand() {
        return this.mTopYLand;
    }

    public Integer getVisibility() {
        return this.mVisibility;
    }

    public Integer getVisibilityLand() {
        return this.mVisibilityLand;
    }

    public void setBottomY(Integer num) {
        this.mBottomY = num;
    }

    public void setBottomYLand(Integer num) {
        this.mBottomYLand = num;
    }

    public void setClockInfo(ClockInfo clockInfo) {
        this.mClockInfo = clockInfo;
    }

    public void setExpandable(Integer num) {
        this.mExpandable = num;
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

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public ServiceBoxData m2026clone() throws CloneNotSupportedException {
        return (ServiceBoxData) super.clone();
    }
}
