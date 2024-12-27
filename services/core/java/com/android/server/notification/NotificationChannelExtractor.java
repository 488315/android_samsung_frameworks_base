package com.android.server.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.content.Context;
import android.media.AudioAttributes;
import android.os.Binder;
import android.os.RemoteException;
import android.util.Slog;

import com.android.internal.compat.IPlatformCompat;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public class NotificationChannelExtractor implements NotificationSignalExtractor {
    public RankingConfig mConfig;
    public IPlatformCompat mPlatformCompat;

    @Override // com.android.server.notification.NotificationSignalExtractor
    public final void initialize(Context context, NotificationUsageStats notificationUsageStats) {}

    @Override // com.android.server.notification.NotificationSignalExtractor
    public final RankingReconsideration process(NotificationRecord notificationRecord) {
        RankingConfig rankingConfig;
        if (notificationRecord.sbn.getNotification() == null
                || (rankingConfig = this.mConfig) == null) {
            return null;
        }
        notificationRecord.updateNotificationChannel(
                ((PreferencesHelper) rankingConfig)
                        .getConversationNotificationChannel(
                                notificationRecord.sbn.getPackageName(),
                                notificationRecord.sbn.getUid(),
                                notificationRecord.mChannel.getId(),
                                notificationRecord.sbn.getShortcutId(),
                                true,
                                false));
        if (android.app.Flags.restrictAudioAttributesCall()
                || android.app.Flags.restrictAudioAttributesAlarm()
                || android.app.Flags.restrictAudioAttributesMedia()) {
            AudioAttributes audioAttributes = notificationRecord.mChannel.getAudioAttributes();
            boolean z = true;
            boolean z2 =
                    android.app.Flags.restrictAudioAttributesCall()
                            && !notificationRecord
                                    .sbn
                                    .getNotification()
                                    .isStyle(Notification.CallStyle.class)
                            && audioAttributes.getUsage() == 6;
            if (android.app.Flags.restrictAudioAttributesAlarm()
                    && notificationRecord.sbn.getNotification().category != "alarm"
                    && audioAttributes.getUsage() == 4) {
                z2 = true;
            }
            if (!android.app.Flags.restrictAudioAttributesMedia()
                    || (audioAttributes.getUsage() != 0 && audioAttributes.getUsage() != 1)) {
                z = z2;
            }
            if (z) {
                int uid = notificationRecord.sbn.getUid();
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    try {
                        this.mPlatformCompat.reportChangeByUid(331793339L, uid);
                    } catch (RemoteException e) {
                        Slog.e(
                                "ChannelExtractor",
                                "Unexpected exception while reporting to changecompat",
                                e);
                    }
                    NotificationChannel copy = notificationRecord.mChannel.copy();
                    copy.setSound(
                            copy.getSound(),
                            new AudioAttributes.Builder(audioAttributes).setUsage(5).build());
                    notificationRecord.updateNotificationChannel(copy);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }
        return null;
    }

    @Override // com.android.server.notification.NotificationSignalExtractor
    public final void setCompatChangeLogger(IPlatformCompat iPlatformCompat) {
        this.mPlatformCompat = iPlatformCompat;
    }

    @Override // com.android.server.notification.NotificationSignalExtractor
    public final void setConfig(RankingConfig rankingConfig) {
        this.mConfig = rankingConfig;
    }

    @Override // com.android.server.notification.NotificationSignalExtractor
    public final void setZenHelper(ZenModeHelper zenModeHelper) {}
}
