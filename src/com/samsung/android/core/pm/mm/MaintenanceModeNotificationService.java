package com.samsung.android.core.pm.mm;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.IBinder;
import android.os.UserHandle;
import android.util.Log;
import com.android.internal.R;

/* loaded from: classes6.dex */
public class MaintenanceModeNotificationService extends Service {
    private static final String CHANNEL_ID = "maintenance_mode_channel";
    private static final String CHANNEL_NAME = "maintenance_mode";
    private static final int NOTIFICATION_ID = 150707;
    private static final ComponentName OUTRO_COMPONENT = new ComponentName("android", MaintenanceModeOutroActivity.class.getName());
    private static final String TAG = "MaintenanceMode";

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        createNotificationChannel(this);
        startForeground(NOTIFICATION_ID, buildNotification(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void createNotificationChannel(Context context) {
        ((NotificationManager) context.getSystemService(NotificationManager.class)).createNotificationChannel(new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, 3));
    }

    private static Notification buildNotification(Context context) throws Resources.NotFoundException {
        String string;
        PendingIntent activity = PendingIntent.getActivity(context, 0, new Intent().setComponent(OUTRO_COMPONENT), 67108864);
        PendingIntent broadcast = PendingIntent.getBroadcast(context, 0, new Intent(context, (Class<?>) DismissalReceiver.class), 67108864);
        Notification.Builder smallIcon = new Notification.Builder(context, CHANNEL_ID).setSmallIcon(R.drawable.ic_maintenance_mode_notification);
        if (MaintenanceModeUtils.isTablet()) {
            string = context.getResources().getString(R.string.maintenance_mode_notification_title_tablet);
        } else {
            string = context.getResources().getString(R.string.maintenance_mode_notification_title_phone);
        }
        return smallIcon.setContentTitle(string).setContentText(context.getResources().getString(R.string.maintenance_mode_notification_message)).setOngoing(true).setContentIntent(activity).setDeleteIntent(broadcast).build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void registerNotification(Context context) {
        ((NotificationManager) context.getSystemService(NotificationManager.class)).notify(NOTIFICATION_ID, buildNotification(context));
    }

    public static class DismissalReceiver extends BroadcastReceiver {
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            Log.i("MaintenanceMode", "Notification has been dismissed!");
            Context contextCreateContextAsUser = context.createContextAsUser(UserHandle.of(77), 0);
            MaintenanceModeNotificationService.createNotificationChannel(contextCreateContextAsUser);
            MaintenanceModeNotificationService.registerNotification(contextCreateContextAsUser);
        }
    }
}
