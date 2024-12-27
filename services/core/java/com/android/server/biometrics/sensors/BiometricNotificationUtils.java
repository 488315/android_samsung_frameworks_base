package com.android.server.biometrics.sensors;

import android.R;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;
import android.util.Slog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class BiometricNotificationUtils {
    public static final Intent DISMISS_FRR_INTENT = new Intent("action_biometric_frr_dismiss");
    public static long sLastAlertTime;

    public static void showNotificationHelper(Context context, String str, String str2, String str3, PendingIntent pendingIntent, Notification.Action action, Notification.Action action2, String str4, String str5, String str6, int i, boolean z, int i2) {
        Slog.v("BiometricNotificationUtils", " listenToDismissEvent = " + z);
        Intent intent = DISMISS_FRR_INTENT;
        UserHandle userHandle = UserHandle.CURRENT;
        PendingIntent activityAsUser = PendingIntent.getActivityAsUser(context, 0, intent, 67108864, null, userHandle);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NotificationManager.class);
        NotificationChannel notificationChannel = new NotificationChannel(str5, str, 4);
        Notification.Builder visibility = new Notification.Builder(context, str5).setSmallIcon(R.drawable.ic_lockscreen_silent_focused).setContentTitle(str2).setContentText(str3).setStyle(new Notification.BigTextStyle().bigText(str3)).setSubText(str).setOnlyAlertOnce(true).setLocalOnly(true).setAutoCancel(true).setCategory(str4).setContentIntent(pendingIntent).setVisibility(i);
        if (i2 > 0) {
            visibility.setFlag(i2, true);
        }
        if (action != null) {
            visibility.addAction(action);
        }
        if (action2 != null) {
            visibility.addAction(action2);
        }
        if (z) {
            visibility.setDeleteIntent(activityAsUser);
        }
        Notification build = visibility.build();
        notificationManager.createNotificationChannel(notificationChannel);
        notificationManager.notifyAsUser(str6, 1, build, userHandle);
    }
}
