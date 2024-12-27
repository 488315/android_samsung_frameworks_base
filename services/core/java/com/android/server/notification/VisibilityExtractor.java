package com.android.server.notification;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.util.SparseBooleanArray;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public class VisibilityExtractor implements NotificationSignalExtractor {
    public RankingConfig mConfig;
    public DevicePolicyManager mDpm;
    public PreferencesHelper mPreferencesHelper;

    @Override // com.android.server.notification.NotificationSignalExtractor
    public final void initialize(Context context, NotificationUsageStats notificationUsageStats) {
        this.mDpm = (DevicePolicyManager) context.getSystemService(DevicePolicyManager.class);
    }

    @Override // com.android.server.notification.NotificationSignalExtractor
    public final RankingReconsideration process(NotificationRecord notificationRecord) {
        if (notificationRecord.sbn.getNotification() == null || this.mConfig == null) {
            return null;
        }
        int userId = notificationRecord.sbn.getUserId();
        if (userId == -1) {
            notificationRecord.mPackageVisibility =
                    notificationRecord.mChannel.getLockscreenVisibility();
        } else {
            PreferencesHelper preferencesHelper = (PreferencesHelper) this.mConfig;
            if (preferencesHelper.mLockScreenShowNotifications == null) {
                preferencesHelper.mLockScreenShowNotifications = new SparseBooleanArray();
            }
            boolean z = preferencesHelper.mLockScreenShowNotifications.get(userId, true);
            boolean z2 =
                    userId == -1 || (this.mDpm.getKeyguardDisabledFeatures(null, userId) & 4) == 0;
            boolean z3 = notificationRecord.mChannel.getLockscreenVisibility() != -1;
            boolean z4 =
                    this.mPreferencesHelper.getLockScreenNotificationVisibilityForPackage(
                                    notificationRecord.sbn.getPackageName(),
                                    notificationRecord.sbn.getUid())
                            != -1;
            if (z && z2 && z3 && z4) {
                PreferencesHelper preferencesHelper2 = (PreferencesHelper) this.mConfig;
                if (preferencesHelper2.mLockScreenPrivateNotifications == null) {
                    preferencesHelper2.mLockScreenPrivateNotifications = new SparseBooleanArray();
                }
                boolean z5 = preferencesHelper2.mLockScreenPrivateNotifications.get(userId, true);
                boolean z6 =
                        userId == -1
                                || (this.mDpm.getKeyguardDisabledFeatures(null, userId) & 8) == 0;
                boolean z7 = notificationRecord.mChannel.getLockscreenVisibility() != 0;
                boolean z8 =
                        this.mPreferencesHelper.getLockScreenNotificationVisibilityForPackage(
                                        notificationRecord.sbn.getPackageName(),
                                        notificationRecord.sbn.getUid())
                                != 0;
                if (z5 && z6 && z7 && z8) {
                    notificationRecord.mPackageVisibility = -1000;
                } else {
                    notificationRecord.mPackageVisibility = 0;
                }
            } else {
                notificationRecord.mPackageVisibility = -1;
            }
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
