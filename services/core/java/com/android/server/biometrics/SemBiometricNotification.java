package com.android.server.biometrics;

import android.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Icon;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Slog;
import com.android.internal.notification.SystemNotificationChannels;

/* loaded from: classes.dex */
public class SemBiometricNotification {
  public final int mAuthenticatorType;
  public final Context mContext;
  public int mIcon;
  public String mMessage;
  public final NotificationManager mNotificationManager;
  public final String mNotificationTag;
  public final String mPackageName;
  public String mTitle;

  public SemBiometricNotification(Context context, int i, String str) {
    this.mContext = context;
    this.mAuthenticatorType = i;
    this.mNotificationManager = (NotificationManager) context.getSystemService("notification");
    this.mPackageName = str;
    this.mNotificationTag = "BackgroundBiometricsNotification_" + i + "_" + str;
  }

  public void postNotification(Intent intent) {
    Slog.i("SemBiometricNotification", "postNotification start, " + this.mNotificationTag);
    setContent();
    if (TextUtils.isEmpty(this.mTitle) || TextUtils.isEmpty(this.mMessage)) {
      Slog.d("SemBiometricNotification", "postNotification: No content title or message");
      return;
    }
    try {
      this.mNotificationManager.notifyAsUser(
          this.mNotificationTag,
          this.mAuthenticatorType,
          new Notification.Builder(this.mContext, SystemNotificationChannels.SECURITY)
              .setOngoing(true)
              .setContentTitle(this.mTitle)
              .setContentText(this.mMessage)
              .setSmallIcon(this.mIcon)
              .setCategory("sys")
              .setColor(this.mContext.getColor(R.color.system_notification_accent_color))
              .setStyle(new Notification.BigTextStyle().bigText(this.mMessage))
              .addAction(
                  new Notification.Action.Builder(
                          (Icon) null,
                          this.mContext.getString(R.string.cancel),
                          PendingIntent.getBroadcast(this.mContext, 0, intent, 1140850688))
                      .build())
              .build(),
          UserHandle.CURRENT);
    } catch (Exception e) {
      Slog.w("SemBiometricNotification", "postNotification: " + e.getMessage());
    }
  }

  public void cancelNotification() {
    Slog.i("SemBiometricNotification", "cancelNotification start, " + this.mNotificationTag);
    this.mNotificationManager.cancelAsUser(
        this.mNotificationTag, this.mAuthenticatorType, UserHandle.CURRENT);
  }

  public final void setContent() {
    PackageManager packageManager = this.mContext.getPackageManager();
    ApplicationInfo applicationInfo = getApplicationInfo(packageManager, this.mPackageName);
    String charSequence =
        applicationInfo != null
            ? packageManager.getApplicationLabel(applicationInfo).toString()
            : this.mPackageName;
    int i = this.mAuthenticatorType;
    if (i == 2) {
      this.mTitle = this.mContext.getString(17042744);
      this.mMessage = this.mContext.getString(17042743, charSequence);
      this.mIcon = R.drawable.text_cursor_holo_light;
    } else if (i == 8) {
      this.mTitle = this.mContext.getString(17042740);
      this.mMessage = this.mContext.getString(17042739, charSequence);
      this.mIcon = R.drawable.text_cursor_holo_dark;
    } else {
      Slog.d("SemBiometricNotification", "Unknown authenticator type, " + this.mAuthenticatorType);
    }
  }

  public final ApplicationInfo getApplicationInfo(PackageManager packageManager, String str) {
    try {
      return packageManager.getApplicationInfo(str, 0);
    } catch (PackageManager.NameNotFoundException unused) {
      return null;
    }
  }
}
