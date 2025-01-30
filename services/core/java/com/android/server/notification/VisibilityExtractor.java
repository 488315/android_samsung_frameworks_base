package com.android.server.notification;

import android.app.admin.DevicePolicyManager;
import android.content.Context;

/* loaded from: classes2.dex */
public class VisibilityExtractor implements NotificationSignalExtractor {
    public RankingConfig mConfig;
    public DevicePolicyManager mDpm;
    public PreferencesHelper mPreferencesHelper;

    @Override // com.android.server.notification.NotificationSignalExtractor
    public void setZenHelper(ZenModeHelper zenModeHelper) {
    }

    @Override // com.android.server.notification.NotificationSignalExtractor
    public void initialize(Context context, NotificationUsageStats notificationUsageStats) {
        this.mDpm = (DevicePolicyManager) context.getSystemService(DevicePolicyManager.class);
    }

    @Override // com.android.server.notification.NotificationSignalExtractor
    public RankingReconsideration process(NotificationRecord notificationRecord) {
        if (notificationRecord == null || notificationRecord.getNotification() == null || this.mConfig == null) {
            return null;
        }
        int userId = notificationRecord.getUserId();
        if (userId == -1) {
            notificationRecord.setPackageVisibilityOverride(notificationRecord.getChannel().getLockscreenVisibility());
        } else {
            boolean canShowNotificationsOnLockscreen = this.mConfig.canShowNotificationsOnLockscreen(userId);
            boolean adminAllowsKeyguardFeature = adminAllowsKeyguardFeature(userId, 4);
            boolean z = notificationRecord.getChannel().getLockscreenVisibility() != -1;
            boolean z2 = this.mPreferencesHelper.getLockScreenNotificationVisibilityForPackage(notificationRecord.getSbn().getPackageName(), notificationRecord.getUid()) != -1;
            if (!canShowNotificationsOnLockscreen || !adminAllowsKeyguardFeature || !z || !z2) {
                notificationRecord.setPackageVisibilityOverride(-1);
            } else {
                boolean canShowPrivateNotificationsOnLockScreen = this.mConfig.canShowPrivateNotificationsOnLockScreen(userId);
                boolean adminAllowsKeyguardFeature2 = adminAllowsKeyguardFeature(userId, 8);
                boolean z3 = notificationRecord.getChannel().getLockscreenVisibility() != 0;
                boolean z4 = this.mPreferencesHelper.getLockScreenNotificationVisibilityForPackage(notificationRecord.getSbn().getPackageName(), notificationRecord.getUid()) != 0;
                if (!canShowPrivateNotificationsOnLockScreen || !adminAllowsKeyguardFeature2 || !z3 || !z4) {
                    notificationRecord.setPackageVisibilityOverride(0);
                } else {
                    notificationRecord.setPackageVisibilityOverride(-1000);
                }
            }
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

    public final boolean adminAllowsKeyguardFeature(int i, int i2) {
        return i == -1 || (this.mDpm.getKeyguardDisabledFeatures(null, i) & i2) == 0;
    }
}
