package com.android.server.wm;

import android.R;
import android.app.INotificationManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Slog;
import com.android.internal.notification.SystemNotificationChannels;
import com.android.internal.util.function.QuadConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class ActivityTaskManagerService$$ExternalSyntheticLambda26 implements QuadConsumer {
    public final void accept(Object obj, Object obj2, Object obj3, Object obj4) {
        INotificationManager service;
        ActivityTaskManagerService activityTaskManagerService = (ActivityTaskManagerService) obj;
        WindowProcessController windowProcessController = (WindowProcessController) obj2;
        Intent intent = (Intent) obj3;
        int intValue = ((Integer) obj4).intValue();
        activityTaskManagerService.getClass();
        if (windowProcessController == null || (service = NotificationManager.getService()) == null) {
            return;
        }
        try {
            Context createPackageContext = activityTaskManagerService.mContext.createPackageContext(windowProcessController.mInfo.packageName, 0);
            String string = activityTaskManagerService.mContext.getString(R.string.miniresolver_switch, createPackageContext.getApplicationInfo().loadLabel(createPackageContext.getPackageManager()));
            try {
                service.enqueueNotificationWithTag("android", "android", (String) null, 11, new Notification.Builder(createPackageContext, SystemNotificationChannels.HEAVY_WEIGHT_APP).setSmallIcon(17304445).setWhen(0L).setOngoing(true).setTicker(string).setColor(activityTaskManagerService.mContext.getColor(R.color.system_notification_accent_color)).setContentTitle(string).setContentText(activityTaskManagerService.mContext.getText(R.string.miniresolver_switch_to_work)).setContentIntent(PendingIntent.getActivityAsUser(activityTaskManagerService.mContext, 0, intent, 335544320, null, new UserHandle(intValue))).build(), intValue);
            } catch (RemoteException unused) {
            } catch (RuntimeException e) {
                Slog.w("ActivityTaskManager", "Error showing notification for heavy-weight app", e);
            }
        } catch (PackageManager.NameNotFoundException e2) {
            Slog.w("ActivityTaskManager", "Unable to create context for heavy notification", e2);
        }
    }
}
