package com.android.server.notification;

import android.content.Context;

/* loaded from: classes2.dex */
public class ImportanceExtractor implements NotificationSignalExtractor {
    public RankingConfig mConfig;
    public PreferencesHelper mPreferencesHelper;

    @Override // com.android.server.notification.NotificationSignalExtractor
    public void initialize(Context context, NotificationUsageStats notificationUsageStats) {
    }

    @Override // com.android.server.notification.NotificationSignalExtractor
    public void setZenHelper(ZenModeHelper zenModeHelper) {
    }

    @Override // com.android.server.notification.NotificationSignalExtractor
    public RankingReconsideration process(NotificationRecord notificationRecord) {
        if (notificationRecord == null || notificationRecord.getNotification() == null || this.mConfig == null) {
            return null;
        }
        notificationRecord.calculateImportance();
        if (!this.mPreferencesHelper.getNotificationAlertsEnabledForPackage(notificationRecord.getSbn().getPackageName(), notificationRecord.getUid()) && notificationRecord.getImportance() > 2) {
            notificationRecord.setImportance(2);
        }
        if (!this.mPreferencesHelper.isAllowNotificationPopUpForPackage(notificationRecord.getSbn().getPackageName(), notificationRecord.getUid()) && notificationRecord.getImportance() > 3) {
            notificationRecord.setImportance(3);
        }
        return null;
    }

    public void setPreferenceHelper(PreferencesHelper preferencesHelper) {
        this.mPreferencesHelper = preferencesHelper;
    }

    @Override // com.android.server.notification.NotificationSignalExtractor
    public void setConfig(RankingConfig rankingConfig) {
        this.mConfig = rankingConfig;
    }
}
