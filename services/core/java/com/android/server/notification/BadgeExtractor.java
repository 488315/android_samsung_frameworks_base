package com.android.server.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.content.Context;
import android.provider.Settings;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public class BadgeExtractor implements NotificationSignalExtractor {
    public RankingConfig mConfig;

    @Override // com.android.server.notification.NotificationSignalExtractor
    public final void initialize(Context context, NotificationUsageStats notificationUsageStats) {}

    @Override // com.android.server.notification.NotificationSignalExtractor
    public final RankingReconsideration process(NotificationRecord notificationRecord) {
        RankingConfig rankingConfig;
        boolean z;
        boolean z2;
        if (notificationRecord.sbn.getNotification() == null
                || (rankingConfig = this.mConfig) == null) {
            return null;
        }
        PreferencesHelper preferencesHelper = (PreferencesHelper) rankingConfig;
        int identifier = notificationRecord.sbn.getUser().getIdentifier();
        if (identifier == -1) {
            z = false;
        } else {
            if (preferencesHelper.mBadgingEnabled.indexOfKey(identifier) < 0) {
                preferencesHelper.mBadgingEnabled.put(
                        identifier,
                        Settings.Secure.getIntForUser(
                                        preferencesHelper.mContext.getContentResolver(),
                                        "notification_badging",
                                        1,
                                        identifier)
                                != 0);
            }
            z = preferencesHelper.mBadgingEnabled.get(identifier, true);
        }
        RankingConfig rankingConfig2 = this.mConfig;
        String packageName = notificationRecord.sbn.getPackageName();
        int uid = notificationRecord.sbn.getUid();
        PreferencesHelper preferencesHelper2 = (PreferencesHelper) rankingConfig2;
        synchronized (preferencesHelper2.mLock) {
            z2 = preferencesHelper2.getOrCreatePackagePreferencesLocked(uid, packageName).showBadge;
        }
        if (z && z2) {
            NotificationChannel notificationChannel = notificationRecord.mChannel;
            if (notificationChannel != null) {
                notificationRecord.mShowBadge = notificationChannel.canShowBadge() && z2;
            } else {
                notificationRecord.mShowBadge = z2;
            }
        } else {
            notificationRecord.mShowBadge = false;
        }
        if (notificationRecord.mIntercept
                && (notificationRecord.mSuppressedVisualEffects & 64) != 0) {
            notificationRecord.mShowBadge = false;
        }
        Notification.BubbleMetadata bubbleMetadata =
                notificationRecord.sbn.getNotification().getBubbleMetadata();
        if (bubbleMetadata != null && bubbleMetadata.isNotificationSuppressed()) {
            notificationRecord.mShowBadge = false;
        }
        if (((PreferencesHelper) this.mConfig).mIsMediaNotificationFilteringEnabled
                && notificationRecord.sbn.getNotification().isMediaNotification()) {
            notificationRecord.mShowBadge = false;
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
