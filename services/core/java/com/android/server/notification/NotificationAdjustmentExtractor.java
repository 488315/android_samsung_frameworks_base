package com.android.server.notification;

import android.content.Context;

public class NotificationAdjustmentExtractor implements NotificationSignalExtractor {
    @Override // com.android.server.notification.NotificationSignalExtractor
    public final void initialize(Context context, NotificationUsageStats notificationUsageStats) {}

    @Override // com.android.server.notification.NotificationSignalExtractor
    public final RankingReconsideration process(NotificationRecord notificationRecord) {
        if (notificationRecord.sbn.getNotification() == null) {
            return null;
        }
        notificationRecord.applyAdjustments();
        return null;
    }

    @Override // com.android.server.notification.NotificationSignalExtractor
    public final void setConfig(RankingConfig rankingConfig) {}

    @Override // com.android.server.notification.NotificationSignalExtractor
    public final void setZenHelper(ZenModeHelper zenModeHelper) {}
}
