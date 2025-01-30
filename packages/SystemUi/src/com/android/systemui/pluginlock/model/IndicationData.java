package com.android.systemui.pluginlock.model;

import com.google.gson.annotations.SerializedName;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class IndicationData {

    @SerializedName("help_text")
    private HelpTextData mHelpTextData;

    @SerializedName("lock_icon")
    private LockIconData mLockIconData;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class HelpTextData {

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

        public HelpTextData(IndicationData indicationData) {
        }

        public final Object clone() {
            return (HelpTextData) super.clone();
        }

        public final Integer getHeight() {
            Integer num = this.mHeight;
            return Integer.valueOf(num == null ? -1 : num.intValue());
        }

        public final Integer getPaddingBottom() {
            Integer num = this.mPaddingBottom;
            return Integer.valueOf(num == null ? -1 : num.intValue());
        }

        public final Integer getPaddingBottomLand() {
            Integer num = this.mPaddingBottomLand;
            return Integer.valueOf(num == null ? -1 : num.intValue());
        }

        public final Integer getVisibility() {
            Integer num = this.mVisibility;
            return Integer.valueOf(num == null ? 0 : num.intValue());
        }

        public final Integer getVisibilityLand() {
            Integer num = this.mVisibilityLand;
            return Integer.valueOf(num == null ? 0 : num.intValue());
        }

        public final void setHeight(Integer num) {
            this.mHeight = num;
        }

        public final void setPaddingBottom(Integer num) {
            this.mPaddingBottom = num;
        }

        public final void setPaddingBottomLand(Integer num) {
            this.mPaddingBottomLand = num;
        }

        public final void setVisibility(Integer num) {
            this.mVisibility = num;
        }

        public final void setVisibilityLand(Integer num) {
            this.mVisibilityLand = num;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class LockIconData {

        @SerializedName("visibility")
        Integer mVisibility = 0;

        @SerializedName("visibility_land")
        Integer mVisibilityLand = 0;

        public LockIconData(IndicationData indicationData) {
        }

        public final Object clone() {
            return (LockIconData) super.clone();
        }

        public final Integer getVisibility() {
            Integer num = this.mVisibility;
            return Integer.valueOf(num == null ? 0 : num.intValue());
        }

        public final Integer getVisibilityLand() {
            Integer num = this.mVisibilityLand;
            return Integer.valueOf(num == null ? 0 : num.intValue());
        }

        public final void setVisibility(Integer num) {
            this.mVisibility = num;
        }

        public final void setVisibilityLand(Integer num) {
            this.mVisibilityLand = num;
        }
    }

    public final HelpTextData getHelpTextData() {
        if (this.mHelpTextData == null) {
            this.mHelpTextData = new HelpTextData(this);
        }
        return this.mHelpTextData;
    }

    public final LockIconData getLockIconData() {
        if (this.mLockIconData == null) {
            this.mLockIconData = new LockIconData(this);
        }
        return this.mLockIconData;
    }
}
