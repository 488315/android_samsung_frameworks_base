package com.android.server.notification;

import android.content.Context;

public class ImportanceExtractor implements NotificationSignalExtractor {
    public RankingConfig mConfig;
    public PreferencesHelper mPreferencesHelper;

    @Override // com.android.server.notification.NotificationSignalExtractor
    public final void initialize(Context context, NotificationUsageStats notificationUsageStats) {}

    @Override // com.android.server.notification.NotificationSignalExtractor
    public final RankingReconsideration process(NotificationRecord notificationRecord) {
        if (notificationRecord.sbn.getNotification() == null || this.mConfig == null) {
            return null;
        }
        notificationRecord.calculateImportance();
        if (!this.mPreferencesHelper.getNotificationAlertsEnabledForPackage(
                        notificationRecord.sbn.getPackageName(), notificationRecord.sbn.getUid())
                && notificationRecord.mImportance > 2) {
            notificationRecord.mImportance = 2;
        }
        PreferencesHelper.PackagePreferences packagePreferencesLocked =
                this.mPreferencesHelper.getPackagePreferencesLocked(
                        notificationRecord.sbn.getUid(), notificationRecord.sbn.getPackageName());
        if (!(packagePreferencesLocked != null ? packagePreferencesLocked.isAllowPopup : true)
                && notificationRecord.mImportance > 3) {
            notificationRecord.mImportance = 3;
        }
        return null;
    }

    @Override // com.android.server.notification.NotificationSignalExtractor
    public final void setConfig(RankingConfig rankingConfig) {
        this.mConfig = rankingConfig;
    }

    @Override // com.android.server.notification.NotificationSignalExtractor
    public final void setZenHelper(ZenModeHelper zenModeHelper) {}
}
