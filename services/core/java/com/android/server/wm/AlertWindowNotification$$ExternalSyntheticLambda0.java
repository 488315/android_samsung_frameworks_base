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

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class AlertWindowNotification$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ AlertWindowNotification f$0;

    public /* synthetic */ AlertWindowNotification$$ExternalSyntheticLambda0(
            AlertWindowNotification alertWindowNotification) {
        this.f$0 = alertWindowNotification;
    }

    @Override // java.lang.Runnable
    public final void run() {
        ApplicationInfo applicationInfo;
        AlertWindowNotification alertWindowNotification = this.f$0;
        String str = alertWindowNotification.mPackageName;
        if (alertWindowNotification.mPosted) {
            return;
        }
        alertWindowNotification.mPosted = true;
        WindowManagerService windowManagerService = alertWindowNotification.mService;
        Context context = windowManagerService.mContext;
        PackageManager packageManager = context.getPackageManager();
        try {
            applicationInfo = packageManager.getApplicationInfo(str, 0);
        } catch (PackageManager.NameNotFoundException unused) {
            applicationInfo = null;
        }
        String charSequence =
                applicationInfo != null
                        ? packageManager.getApplicationLabel(applicationInfo).toString()
                        : str;
        if (AlertWindowNotification.sChannelGroup == null) {
            NotificationChannelGroup notificationChannelGroup =
                    new NotificationChannelGroup(
                            "com.android.server.wm.AlertWindowNotification - ",
                            windowManagerService.mContext.getString(
                                    R.string.call_notification_decline_action));
            AlertWindowNotification.sChannelGroup = notificationChannelGroup;
            alertWindowNotification.mNotificationManager.createNotificationChannelGroup(
                    notificationChannelGroup);
        }
        String string = context.getString(R.string.call_notification_hang_up_action, charSequence);
        NotificationManager notificationManager = alertWindowNotification.mNotificationManager;
        String str2 = alertWindowNotification.mNotificationTag;
        if (notificationManager.getNotificationChannel(str2) == null) {
            NotificationChannel notificationChannel = new NotificationChannel(str2, string, 1);
            notificationChannel.enableLights(false);
            notificationChannel.enableVibration(false);
            notificationChannel.setBlockable(true);
            notificationChannel.setGroup(AlertWindowNotification.sChannelGroup.getId());
            notificationChannel.setBypassDnd(true);
            alertWindowNotification.mNotificationManager.createNotificationChannel(
                    notificationChannel);
        }
        String string2 = context.getString(R.string.call_notification_incoming_text, charSequence);
        Bundle bundle = new Bundle();
        bundle.putStringArray("android.foregroundApps", new String[] {str});
        Notification.Builder addExtras =
                new Notification.Builder(context, str2)
                        .setOngoing(true)
                        .setContentTitle(
                                context.getString(
                                        R.string.call_notification_ongoing_text, charSequence))
                        .setContentText(string2)
                        .setSmallIcon(R.drawable.archived_app_cloud_overlay)
                        .setColor(context.getColor(R.color.system_notification_accent_color))
                        .setStyle(new Notification.BigTextStyle().bigText(string2))
                        .setLocalOnly(true)
                        .addExtras(bundle);
        Intent intent =
                new Intent(
                        "android.settings.MANAGE_APP_OVERLAY_PERMISSION",
                        Uri.fromParts("package", str, null));
        intent.setFlags(268468224);
        Notification.Builder contentIntent =
                addExtras.setContentIntent(
                        PendingIntent.getActivity(
                                context, alertWindowNotification.mRequestCode, intent, 335544320));
        if (applicationInfo != null) {
            Drawable applicationIcon = packageManager.getApplicationIcon(applicationInfo);
            int dimensionPixelSize =
                    context.getResources().getDimensionPixelSize(R.dimen.app_icon_size);
            Bitmap buildScaledBitmap =
                    ImageUtils.buildScaledBitmap(
                            applicationIcon, dimensionPixelSize, dimensionPixelSize);
            if (buildScaledBitmap != null) {
                contentIntent.setLargeIcon(buildScaledBitmap);
            }
        }
        alertWindowNotification.mNotificationManager.notify(str2, 0, contentIntent.build());
    }
}
