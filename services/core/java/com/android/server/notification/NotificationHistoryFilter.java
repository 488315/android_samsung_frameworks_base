package com.android.server.notification;

import android.app.NotificationHistory;
import android.text.TextUtils;

/* loaded from: classes2.dex */
public final class NotificationHistoryFilter {
    public String mChannel;
    public int mNotificationCount;
    public String mPackage;
    public String mSbnKey;

    public NotificationHistoryFilter() {
    }

    public String getPackage() {
        return this.mPackage;
    }

    public String getChannel() {
        return this.mChannel;
    }

    public String getSbnKey() {
        return this.mSbnKey;
    }

    public boolean isSbnFilter() {
        return getSbnKey() != null;
    }

    public boolean isFiltering() {
        return (getPackage() == null && getChannel() == null && this.mNotificationCount >= Integer.MAX_VALUE) ? false : true;
    }

    public boolean matchesPackageAndChannelFilter(NotificationHistory.HistoricalNotification historicalNotification) {
        if (TextUtils.isEmpty(getPackage())) {
            return true;
        }
        if (getPackage().equals(historicalNotification.getPackage())) {
            return TextUtils.isEmpty(getChannel()) || getChannel().equals(historicalNotification.getChannelId());
        }
        return false;
    }

    public boolean matchesPackageAndSbnKeyFilter(NotificationHistory.HistoricalNotification historicalNotification) {
        if (TextUtils.isEmpty(getPackage())) {
            return true;
        }
        if (getPackage().equals(historicalNotification.getPackage())) {
            return TextUtils.isEmpty(getSbnKey()) || getSbnKey().equals(historicalNotification.getSbnKey());
        }
        return false;
    }

    public boolean matchesCountFilter(NotificationHistory notificationHistory) {
        return notificationHistory.getHistoryCount() < this.mNotificationCount;
    }

    public final class Builder {
        public String mPackage = null;
        public String mChannel = null;
        public String mSbnKey = null;
        public int mNotificationCount = Integer.MAX_VALUE;

        public Builder setPackage(String str) {
            this.mPackage = str;
            return this;
        }

        public Builder setSbnKey(String str) {
            this.mSbnKey = str;
            return this;
        }

        public Builder setMaxNotifications(int i) {
            this.mNotificationCount = i;
            return this;
        }

        public NotificationHistoryFilter build() {
            NotificationHistoryFilter notificationHistoryFilter = new NotificationHistoryFilter();
            notificationHistoryFilter.mPackage = this.mPackage;
            notificationHistoryFilter.mChannel = this.mChannel;
            notificationHistoryFilter.mSbnKey = this.mSbnKey;
            notificationHistoryFilter.mNotificationCount = this.mNotificationCount;
            return notificationHistoryFilter;
        }
    }
}
