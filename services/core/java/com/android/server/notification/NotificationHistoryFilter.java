package com.android.server.notification;

import android.app.NotificationHistory;
import android.text.TextUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class NotificationHistoryFilter {
    public String mChannel;
    public int mNotificationCount;
    public String mPackage;
    public String mSbnKey;

    public final boolean matchesPackageAndChannelFilter(NotificationHistory.HistoricalNotification historicalNotification) {
        if (TextUtils.isEmpty(this.mPackage)) {
            return true;
        }
        if (this.mPackage.equals(historicalNotification.getPackage())) {
            return TextUtils.isEmpty(this.mChannel) || this.mChannel.equals(historicalNotification.getChannelId());
        }
        return false;
    }

    public final boolean matchesPackageAndSbnKeyFilter(NotificationHistory.HistoricalNotification historicalNotification) {
        if (TextUtils.isEmpty(this.mPackage)) {
            return true;
        }
        if (this.mPackage.equals(historicalNotification.getPackage())) {
            return TextUtils.isEmpty(this.mSbnKey) || this.mSbnKey.equals(historicalNotification.getSbnKey());
        }
        return false;
    }
}
