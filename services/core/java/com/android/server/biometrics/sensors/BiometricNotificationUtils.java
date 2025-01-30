package com.android.server.biometrics.sensors;

import android.R;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.os.UserHandle;
import android.util.Slog;
import com.android.server.backup.BackupManagerConstants;

/* loaded from: classes.dex */
public abstract class BiometricNotificationUtils {
  public static long sLastAlertTime;

  public static void showReEnrollmentNotification(Context context) {
    String string = context.getString(R.string.kg_invalid_confirm_pin_hint);
    String string2 = context.getString(R.string.kg_invalid_puk);
    String string3 = context.getString(R.string.kg_forgot_pattern_button_text);
    Intent intent = new Intent("android.settings.FACE_SETTINGS");
    intent.setPackage("com.android.settings");
    intent.putExtra("re_enroll_face_unlock", true);
    showNotificationHelper(
        context,
        string,
        string2,
        string3,
        PendingIntent.getActivityAsUser(context, 0, intent, 67108864, null, UserHandle.CURRENT),
        "FaceEnrollNotificationChannel",
        "FaceService");
  }

  public static void showBadCalibrationNotification(Context context) {
    long elapsedRealtime = SystemClock.elapsedRealtime();
    long j = sLastAlertTime;
    long j2 = elapsedRealtime - j;
    if (j != 0 && j2 < BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS) {
      Slog.v("BiometricNotificationUtils", "Skipping calibration notification : " + j2);
      return;
    }
    sLastAlertTime = elapsedRealtime;
    String string = context.getString(R.string.lockscreen_emergency_call);
    String string2 = context.getString(R.string.lockscreen_failed_attempts_almost_at_wipe);
    String string3 = context.getString(R.string.lockscreen_carrier_default);
    Intent intent = new Intent("android.settings.FINGERPRINT_SETTINGS");
    intent.setPackage("com.android.settings");
    showNotificationHelper(
        context,
        string,
        string2,
        string3,
        PendingIntent.getActivityAsUser(context, 0, intent, 67108864, null, UserHandle.CURRENT),
        "FingerprintBadCalibrationNotificationChannel",
        "FingerprintService");
  }

  public static void showNotificationHelper(
      Context context,
      String str,
      String str2,
      String str3,
      PendingIntent pendingIntent,
      String str4,
      String str5) {
    NotificationManager notificationManager =
        (NotificationManager) context.getSystemService(NotificationManager.class);
    NotificationChannel notificationChannel = new NotificationChannel(str4, str, 4);
    Notification build =
        new Notification.Builder(context, str4)
            .setSmallIcon(R.drawable.ic_ime_switcher)
            .setContentTitle(str2)
            .setContentText(str3)
            .setSubText(str)
            .setOnlyAlertOnce(true)
            .setLocalOnly(true)
            .setAutoCancel(true)
            .setCategory("sys")
            .setContentIntent(pendingIntent)
            .setVisibility(-1)
            .build();
    notificationManager.createNotificationChannel(notificationChannel);
    notificationManager.notifyAsUser(str5, 1, build, UserHandle.CURRENT);
  }

  public static void cancelReEnrollNotification(Context context) {
    ((NotificationManager) context.getSystemService(NotificationManager.class))
        .cancelAsUser("FaceService", 1, UserHandle.CURRENT);
  }

  public static void cancelBadCalibrationNotification(Context context) {
    ((NotificationManager) context.getSystemService(NotificationManager.class))
        .cancelAsUser("FingerprintService", 1, UserHandle.CURRENT);
  }
}
