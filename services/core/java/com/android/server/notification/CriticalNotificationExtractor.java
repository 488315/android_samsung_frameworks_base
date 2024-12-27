package com.android.server.notification;

import android.content.Context;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public class CriticalNotificationExtractor implements NotificationSignalExtractor {
    public boolean mSupportsCriticalNotifications = false;

    @Override // com.android.server.notification.NotificationSignalExtractor
    public final void initialize(Context context, NotificationUsageStats notificationUsageStats) {
        this.mSupportsCriticalNotifications =
                context.getPackageManager().hasSystemFeature("android.hardware.type.automotive", 0);
    }

    @Override // com.android.server.notification.NotificationSignalExtractor
    public final RankingReconsideration process(NotificationRecord notificationRecord) {
        if (!this.mSupportsCriticalNotifications
                || notificationRecord.sbn.getNotification() == null) {
            return null;
        }
        if (notificationRecord.isCategory("car_emergency")) {
            notificationRecord.mCriticality = 0;
        } else if (notificationRecord.isCategory("car_warning")) {
            notificationRecord.mCriticality = 1;
        } else {
            notificationRecord.mCriticality = 2;
        }
        return null;
    }

    @Override // com.android.server.notification.NotificationSignalExtractor
    public final void setConfig(RankingConfig rankingConfig) {}

    @Override // com.android.server.notification.NotificationSignalExtractor
    public final void setZenHelper(ZenModeHelper zenModeHelper) {}
}
