package com.android.server.wm;

import android.R;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import com.android.internal.util.ImageUtils;

/* loaded from: classes3.dex */
public class AlertWindowNotification {
  public static NotificationChannelGroup sChannelGroup;
  public static int sNextRequestCode;
  public final NotificationManager mNotificationManager;
  public String mNotificationTag;
  public final String mPackageName;
  public boolean mPosted;
  public final int mRequestCode;
  public final WindowManagerService mService;

  public AlertWindowNotification(WindowManagerService windowManagerService, String str) {
    this.mService = windowManagerService;
    this.mPackageName = str;
    this.mNotificationManager =
        (NotificationManager) windowManagerService.mContext.getSystemService("notification");
    this.mNotificationTag = "com.android.server.wm.AlertWindowNotification - " + str;
    int i = sNextRequestCode;
    sNextRequestCode = i + 1;
    this.mRequestCode = i;
  }

  public void post() {
    this.mService.f1749mH.post(
        new Runnable() { // from class:
                         // com.android.server.wm.AlertWindowNotification$$ExternalSyntheticLambda1
          @Override // java.lang.Runnable
          public final void run() {
            AlertWindowNotification.this.onPostNotification();
          }
        });
  }

  public void cancel(final boolean z) {
    this.mService.f1749mH.post(
        new Runnable() { // from class:
                         // com.android.server.wm.AlertWindowNotification$$ExternalSyntheticLambda0
          @Override // java.lang.Runnable
          public final void run() {
            AlertWindowNotification.this.lambda$cancel$0(z);
          }
        });
  }

  /* renamed from: onCancelNotification, reason: merged with bridge method [inline-methods] */
  public final void lambda$cancel$0(boolean z) {
    if (this.mPosted) {
      this.mPosted = false;
      this.mNotificationManager.cancel(this.mNotificationTag, 0);
      if (z) {
        this.mNotificationManager.deleteNotificationChannel(this.mNotificationTag);
      }
    }
  }

  public final void onPostNotification() {
    if (this.mPosted) {
      return;
    }
    this.mPosted = true;
    Context context = this.mService.mContext;
    PackageManager packageManager = context.getPackageManager();
    ApplicationInfo applicationInfo = getApplicationInfo(packageManager, this.mPackageName);
    String charSequence =
        applicationInfo != null
            ? packageManager.getApplicationLabel(applicationInfo).toString()
            : this.mPackageName;
    createNotificationChannel(context, charSequence);
    String string = context.getString(R.string.bugreport_option_interactive_title, charSequence);
    Bundle bundle = new Bundle();
    bundle.putStringArray("android.foregroundApps", new String[] {this.mPackageName});
    Notification.Builder contentIntent =
        new Notification.Builder(context, this.mNotificationTag)
            .setOngoing(true)
            .setContentTitle(
                context.getString(R.string.bugreport_screenshot_failure_toast, charSequence))
            .setContentText(string)
            .setSmallIcon(R.drawable.$progress_indeterminate_horizontal_material__40)
            .setColor(context.getColor(R.color.system_notification_accent_color))
            .setStyle(new Notification.BigTextStyle().bigText(string))
            .setLocalOnly(true)
            .addExtras(bundle)
            .setContentIntent(getContentIntent(context, this.mPackageName));
    if (applicationInfo != null) {
      Drawable applicationIcon = packageManager.getApplicationIcon(applicationInfo);
      int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.app_icon_size);
      Bitmap buildScaledBitmap =
          ImageUtils.buildScaledBitmap(applicationIcon, dimensionPixelSize, dimensionPixelSize);
      if (buildScaledBitmap != null) {
        contentIntent.setLargeIcon(buildScaledBitmap);
      }
    }
    this.mNotificationManager.notify(this.mNotificationTag, 0, contentIntent.build());
  }

  public final PendingIntent getContentIntent(Context context, String str) {
    Intent intent =
        new Intent(
            "android.settings.MANAGE_APP_OVERLAY_PERMISSION", Uri.fromParts("package", str, null));
    intent.setFlags(268468224);
    return PendingIntent.getActivity(context, this.mRequestCode, intent, 335544320);
  }

  public final void createNotificationChannel(Context context, String str) {
    if (sChannelGroup == null) {
      NotificationChannelGroup notificationChannelGroup =
          new NotificationChannelGroup(
              "com.android.server.wm.AlertWindowNotification - ",
              this.mService.mContext.getString(R.string.bugreport_option_full_title));
      sChannelGroup = notificationChannelGroup;
      this.mNotificationManager.createNotificationChannelGroup(notificationChannelGroup);
    }
    String string = context.getString(R.string.bugreport_option_interactive_summary, str);
    if (this.mNotificationManager.getNotificationChannel(this.mNotificationTag) != null) {
      return;
    }
    NotificationChannel notificationChannel =
        new NotificationChannel(this.mNotificationTag, string, 1);
    notificationChannel.enableLights(false);
    notificationChannel.enableVibration(false);
    notificationChannel.setBlockable(true);
    notificationChannel.setGroup(sChannelGroup.getId());
    notificationChannel.setBypassDnd(true);
    this.mNotificationManager.createNotificationChannel(notificationChannel);
  }

  public final ApplicationInfo getApplicationInfo(PackageManager packageManager, String str) {
    try {
      return packageManager.getApplicationInfo(str, 0);
    } catch (PackageManager.NameNotFoundException unused) {
      return null;
    }
  }
}
