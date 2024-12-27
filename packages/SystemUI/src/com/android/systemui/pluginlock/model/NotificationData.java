package com.android.systemui.pluginlock.model;

import com.google.gson.annotations.SerializedName;

public class NotificationData {

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

    public class NotificationCardData {

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

        public NotificationCardData() {
        }

        public Integer getNotiCardNumbers() {
            Integer num = this.mNotiCardNumbers;
            return Integer.valueOf(num == null ? -1 : num.intValue());
        }

        public Integer getNotiCardNumbersLand() {
            Integer num = this.mNotiCardNumbersLand;
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
            Integer num = this.mTopY;
            return Integer.valueOf(num == null ? -1 : num.intValue());
        }

        public Integer getTopYLand() {
            Integer num = this.mTopYLand;
            return Integer.valueOf(num == null ? -1 : num.intValue());
        }

        public void setNotiCardNumbers(Integer num) {
            this.mNotiCardNumbers = num;
        }

        public void setNotiCardNumbersLand(Integer num) {
            this.mNotiCardNumbersLand = num;
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

        /* renamed from: clone, reason: merged with bridge method [inline-methods] */
        public NotificationCardData m2024clone() throws CloneNotSupportedException {
            return (NotificationCardData) super.clone();
        }
    }

    public class NotificationIconOnlyData {

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

        public Integer getGravity() {
            Integer num = this.mGravity;
            return Integer.valueOf(num == null ? -1 : num.intValue());
        }

        public Integer getGravityLand() {
            Integer num = this.mGravityLand;
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
            Integer num = this.mTop;
            return Integer.valueOf(num == null ? -1 : num.intValue());
        }

        public Integer getTopYLand() {
            Integer num = this.mTopLand;
            return Integer.valueOf(num == null ? -1 : num.intValue());
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

        public void setTopY(Integer num) {
            this.mTop = num;
        }

        public void setTopYLand(Integer num) {
            this.mTopLand = num;
        }

        /* renamed from: clone, reason: merged with bridge method [inline-methods] */
        public NotificationIconOnlyData m2025clone() throws CloneNotSupportedException {
            return (NotificationIconOnlyData) super.clone();
        }
    }

    public NotificationCardData getCardData() {
        if (this.mNotificationCardData == null) {
            this.mNotificationCardData = new NotificationCardData();
        }
        return this.mNotificationCardData;
    }

    public NotificationIconOnlyData getIconOnlyData() {
        if (this.mNotificationIconOnlyData == null) {
            this.mNotificationIconOnlyData = new NotificationIconOnlyData();
        }
        return this.mNotificationIconOnlyData;
    }

    public Integer getNotiType() {
        return this.mNotiType;
    }

    public Integer getRecoverType() {
        return this.mRecoverType;
    }

    public Integer getVisibility() {
        Integer num = this.mVisibility;
        return Integer.valueOf(num == null ? -1 : num.intValue());
    }

    public void setCardData(NotificationCardData notificationCardData) {
        this.mNotificationCardData = notificationCardData;
    }

    public void setIconOnlyData(NotificationIconOnlyData notificationIconOnlyData) {
        this.mNotificationIconOnlyData = notificationIconOnlyData;
    }

    public void setNotiType(Integer num) {
        this.mNotiType = num;
    }

    public void setRecoverType(Integer num) {
        this.mRecoverType = num;
    }

    public void setVisibility(Integer num) {
        this.mVisibility = num;
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public NotificationData m2023clone() throws CloneNotSupportedException {
        return (NotificationData) super.clone();
    }
}
