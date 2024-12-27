package com.android.server.notification;

import android.app.NotificationHistory;
import android.text.TextUtils;

public final class NotificationHistoryFilter {
    public String mChannel;
    public int mNotificationCount;
    public String mPackage;
    public String mSbnKey;

    public final boolean matchesPackageAndChannelFilter(
            NotificationHistory.HistoricalNotification historicalNotification) {
        if (TextUtils.isEmpty(this.mPackage)) {
            return true;
        }
        if (this.mPackage.equals(historicalNotification.getPackage())) {
            return TextUtils.isEmpty(this.mChannel)
                    || this.mChannel.equals(historicalNotification.getChannelId());
        }
        return false;
    }

    public final boolean matchesPackageAndSbnKeyFilter(
            NotificationHistory.HistoricalNotification historicalNotification) {
        if (TextUtils.isEmpty(this.mPackage)) {
            return true;
        }
        if (this.mPackage.equals(historicalNotification.getPackage())) {
            return TextUtils.isEmpty(this.mSbnKey)
                    || this.mSbnKey.equals(historicalNotification.getSbnKey());
        }
        return false;
    }
}
