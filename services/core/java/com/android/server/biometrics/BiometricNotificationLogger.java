package com.android.server.biometrics;

import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Slog;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.biometrics.log.BiometricFrameworkStatsLogger;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class BiometricNotificationLogger extends NotificationListenerService {
    public final BiometricFrameworkStatsLogger mLogger;

    public BiometricNotificationLogger(BiometricFrameworkStatsLogger biometricFrameworkStatsLogger) {
        this.mLogger = biometricFrameworkStatsLogger;
    }

    @Override // android.service.notification.NotificationListenerService
    public final void onNotificationPosted(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap) {
        if (statusBarNotification == null || statusBarNotification.getTag() == null) {
            return;
        }
        String tag = statusBarNotification.getTag();
        tag.getClass();
        if (tag.equals("FaceEnroll") || tag.equals("FingerprintEnroll")) {
            int i = statusBarNotification.getTag() == "FaceEnroll" ? 4 : 1;
            Slog.d("FRRNotificationListener", "onNotificationPosted, tag=(" + statusBarNotification.getTag() + ")");
            this.mLogger.getClass();
            FrameworkStatsLog.write(FrameworkStatsLog.BIOMETRIC_FRR_NOTIFICATION, 1, i);
        }
    }

    @Override // android.service.notification.NotificationListenerService
    public final void onNotificationRemoved(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap, int i) {
        if (statusBarNotification == null || statusBarNotification.getTag() == null) {
            return;
        }
        String tag = statusBarNotification.getTag();
        tag.getClass();
        if (tag.equals("FaceEnroll") || tag.equals("FingerprintEnroll")) {
            Slog.d("FRRNotificationListener", "onNotificationRemoved, tag=(" + statusBarNotification.getTag() + "), reason=(" + i + ")");
            int i2 = statusBarNotification.getTag() == "FaceEnroll" ? 4 : 1;
            if (i == 1) {
                this.mLogger.getClass();
                FrameworkStatsLog.write(FrameworkStatsLog.BIOMETRIC_FRR_NOTIFICATION, 2, i2);
            } else if (i != 2) {
                Slog.d("FRRNotificationListener", "unhandled reason, ignoring logging");
            } else {
                this.mLogger.getClass();
                FrameworkStatsLog.write(FrameworkStatsLog.BIOMETRIC_FRR_NOTIFICATION, 3, i2);
            }
        }
    }
}
