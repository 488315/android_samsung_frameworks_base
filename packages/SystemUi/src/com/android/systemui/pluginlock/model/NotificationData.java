package com.android.systemui.pluginlock.model;

import com.google.gson.annotations.SerializedName;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NotificationData {

    @SerializedName("noti_card_info")
    private NotificationCardData mNotificationCardData;

    @SerializedName("noti_icon_info")
    private NotificationIconOnlyData mNotificationIconOnlyData;

    @SerializedName("noti_type")
    private Integer mNotiType = 0;

    @SerializedName("recover_type")
    private Integer mRecoverType = 0;

    @SerializedName("visibility")
    private Integer mVisibility = -1;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class NotificationCardData {

        @SerializedName("top_y")
        Integer mTopY = -1;

        @SerializedName("padding_start")
        Integer mPaddingStart = -1;

        @SerializedName("card_numbers")
        Integer mNotiCardNumbers = -1;

        @SerializedName("top_y_land")
        Integer mTopYLand = -1;

        @SerializedName("padding_start_land")
        Integer mPaddingStartLand = -1;

        @SerializedName("card_numbers_land")
        Integer mNotiCardNumbersLand = -1;

        public NotificationCardData(NotificationData notificationData) {
        }

        public final Object clone() {
            return (NotificationCardData) super.clone();
        }

        public final Integer getNotiCardNumbers() {
            Integer num = this.mNotiCardNumbers;
            return Integer.valueOf(num == null ? -1 : num.intValue());
        }

        public final Integer getNotiCardNumbersLand() {
            Integer num = this.mNotiCardNumbersLand;
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
            Integer num = this.mTopY;
            return Integer.valueOf(num == null ? -1 : num.intValue());
        }

        public final Integer getTopYLand() {
            Integer num = this.mTopYLand;
            return Integer.valueOf(num == null ? -1 : num.intValue());
        }

        public final void setNotiCardNumbers(Integer num) {
            this.mNotiCardNumbers = num;
        }

        public final void setNotiCardNumbersLand(Integer num) {
            this.mNotiCardNumbersLand = num;
        }

        public final void setTopY(Integer num) {
            this.mTopY = num;
        }

        public final void setTopYLand(Integer num) {
            this.mTopYLand = num;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class NotificationIconOnlyData {

        @SerializedName("gravity")
        Integer mGravity = -1;

        @SerializedName("padding_start")
        Integer mPaddingStart = -1;

        @SerializedName("padding_end")
        Integer mPaddingEnd = -1;

        @SerializedName("top")
        Integer mTop = -1;

        @SerializedName("gravity_land")
        Integer mGravityLand = -1;

        @SerializedName("padding_start_land")
        Integer mPaddingStartLand = -1;

        @SerializedName("padding_end_land")
        Integer mPaddingEndLand = -1;

        @SerializedName("top_land")
        Integer mTopLand = -1;

        public NotificationIconOnlyData(NotificationData notificationData) {
        }

        public final Object clone() {
            return (NotificationIconOnlyData) super.clone();
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
            Integer num = this.mTop;
            return Integer.valueOf(num == null ? -1 : num.intValue());
        }

        public final Integer getTopYLand() {
            Integer num = this.mTopLand;
            return Integer.valueOf(num == null ? -1 : num.intValue());
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

        public final void setTopY(Integer num) {
            this.mTop = num;
        }

        public final void setTopYLand(Integer num) {
            this.mTopLand = num;
        }
    }

    public final Object clone() {
        return (NotificationData) super.clone();
    }

    public final NotificationCardData getCardData() {
        if (this.mNotificationCardData == null) {
            this.mNotificationCardData = new NotificationCardData(this);
        }
        return this.mNotificationCardData;
    }

    public final NotificationIconOnlyData getIconOnlyData() {
        if (this.mNotificationIconOnlyData == null) {
            this.mNotificationIconOnlyData = new NotificationIconOnlyData(this);
        }
        return this.mNotificationIconOnlyData;
    }

    public final Integer getNotiType() {
        return this.mNotiType;
    }

    public final Integer getVisibility() {
        Integer num = this.mVisibility;
        return Integer.valueOf(num == null ? -1 : num.intValue());
    }

    public final void setNotiType(Integer num) {
        this.mNotiType = num;
    }

    public final void setVisibility(Integer num) {
        this.mVisibility = num;
    }
}
