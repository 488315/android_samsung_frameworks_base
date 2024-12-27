package com.android.systemui.pluginlock.model;

import com.google.gson.annotations.SerializedName;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class IndicationData {

    @SerializedName("help_text")
    private HelpTextData mHelpTextData;

    @SerializedName("lock_icon")
    private LockIconData mLockIconData;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public class HelpTextData {

        @SerializedName("visibility")
        Integer mVisibility = 0;

        @SerializedName("padding_bottom")
        Integer mPaddingBottom = -1;

        @SerializedName("height")
        Integer mHeight = -1;

        @SerializedName("visibility_land")
        Integer mVisibilityLand = 0;

        @SerializedName("padding_bottom_land")
        Integer mPaddingBottomLand = -1;

        public HelpTextData() {
        }

        public Integer getHeight() {
            Integer num = this.mHeight;
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

        public Integer getVisibility() {
            Integer num = this.mVisibility;
            return Integer.valueOf(num == null ? 0 : num.intValue());
        }

        public Integer getVisibilityLand() {
            Integer num = this.mVisibilityLand;
            return Integer.valueOf(num == null ? 0 : num.intValue());
        }

        public void setHeight(Integer num) {
            this.mHeight = num;
        }

        public void setPaddingBottom(Integer num) {
            this.mPaddingBottom = num;
        }

        public void setPaddingBottomLand(Integer num) {
            this.mPaddingBottomLand = num;
        }

        public void setVisibility(Integer num) {
            this.mVisibility = num;
        }

        public void setVisibilityLand(Integer num) {
            this.mVisibilityLand = num;
        }

        /* renamed from: clone, reason: merged with bridge method [inline-methods] */
        public HelpTextData m2019clone() throws CloneNotSupportedException {
            return (HelpTextData) super.clone();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public class LockIconData {

        @SerializedName("visibility")
        Integer mVisibility = 0;

        @SerializedName("visibility_land")
        Integer mVisibilityLand = 0;

        public LockIconData() {
        }

        public Integer getVisibility() {
            Integer num = this.mVisibility;
            return Integer.valueOf(num == null ? 0 : num.intValue());
        }

        public Integer getVisibilityLand() {
            Integer num = this.mVisibilityLand;
            return Integer.valueOf(num == null ? 0 : num.intValue());
        }

        public void setVisibility(Integer num) {
            this.mVisibility = num;
        }

        public void setVisibilityLand(Integer num) {
            this.mVisibilityLand = num;
        }

        /* renamed from: clone, reason: merged with bridge method [inline-methods] */
        public LockIconData m2020clone() throws CloneNotSupportedException {
            return (LockIconData) super.clone();
        }
    }

    public HelpTextData getHelpTextData() {
        if (this.mHelpTextData == null) {
            this.mHelpTextData = new HelpTextData();
        }
        return this.mHelpTextData;
    }

    public LockIconData getLockIconData() {
        if (this.mLockIconData == null) {
            this.mLockIconData = new LockIconData();
        }
        return this.mLockIconData;
    }

    public void setHelpTextData(HelpTextData helpTextData) {
        this.mHelpTextData = helpTextData;
    }

    public void setLockIconData(LockIconData lockIconData) {
        this.mLockIconData = lockIconData;
    }
}
