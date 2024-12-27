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
import android.os.IBinder;
import android.os.UserHandle;
import android.util.Log;

import com.android.internal.R;

public class MaintenanceModeNotificationService extends Service {
    private static final String CHANNEL_ID = "maintenance_mode_channel";
    private static final String CHANNEL_NAME = "maintenance_mode";
    private static final int NOTIFICATION_ID = 150707;
    private static final ComponentName OUTRO_COMPONENT =
            new ComponentName("android", MaintenanceModeOutroActivity.class.getName());
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

    public static void createNotificationChannel(Context context) {
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(NotificationManager.class);
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, 3);
        notificationManager.createNotificationChannel(channel);
    }

    private static Notification buildNotification(Context context) {
        String string;
        Intent contentIntent = new Intent().setComponent(OUTRO_COMPONENT);
        PendingIntent contentPendingIntent =
                PendingIntent.getActivity(context, 0, contentIntent, 67108864);
        Intent deleteIntent = new Intent(context, (Class<?>) DismissalReceiver.class);
        PendingIntent deletePendingIntent =
                PendingIntent.getBroadcast(context, 0, deleteIntent, 67108864);
        Notification.Builder smallIcon =
                new Notification.Builder(context, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_maintenance_mode_notification);
        if (MaintenanceModeUtils.isTablet()) {
            string =
                    context.getResources()
                            .getString(R.string.maintenance_mode_notification_title_tablet);
        } else {
            string =
                    context.getResources()
                            .getString(R.string.maintenance_mode_notification_title_phone);
        }
        Notification.Builder builder =
                smallIcon
                        .setContentTitle(string)
                        .setContentText(
                                context.getResources()
                                        .getString(R.string.maintenance_mode_notification_message))
                        .setOngoing(true)
                        .setContentIntent(contentPendingIntent)
                        .setDeleteIntent(deletePendingIntent);
        return builder.build();
    }

    public static void registerNotification(Context context) {
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(NotificationManager.class);
        notificationManager.notify(NOTIFICATION_ID, buildNotification(context));
    }

    public static class DismissalReceiver extends BroadcastReceiver {
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            Log.i("MaintenanceMode", "Notification has been dismissed!");
            Context maintenanceModeContext = context.createContextAsUser(UserHandle.of(77), 0);
            MaintenanceModeNotificationService.createNotificationChannel(maintenanceModeContext);
            MaintenanceModeNotificationService.registerNotification(maintenanceModeContext);
        }
    }
}
