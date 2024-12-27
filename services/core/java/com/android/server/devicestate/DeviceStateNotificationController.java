package com.android.server.devicestate;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.drawable.Icon;
import android.os.Handler;
import android.util.SparseArray;
import java.util.Locale;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class DeviceStateNotificationController extends BroadcastReceiver {
    static final String CHANNEL_ID = "DeviceStateManager";
    static final String INTENT_ACTION_CANCEL_STATE = "com.android.server.devicestate.INTENT_ACTION_CANCEL_STATE";
    static final int NOTIFICATION_ID = 1;
    static final String NOTIFICATION_TAG = "DeviceStateManager";
    public final Runnable mCancelStateRunnable;
    public final Context mContext;
    public final Handler mHandler;
    public final NotificationInfoProvider mNotificationInfoProvider;
    public final NotificationManager mNotificationManager;
    public final PackageManager mPackageManager;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class NotificationInfo {
        public final String activeNotificationContent;
        public final String activeNotificationTitle;
        public final String name;
        public final String powerSaveModeNotificationContent;
        public final String powerSaveModeNotificationTitle;
        public final String thermalCriticalNotificationContent;
        public final String thermalCriticalNotificationTitle;

        public NotificationInfo(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
            this.name = str;
            this.activeNotificationTitle = str2;
            this.activeNotificationContent = str3;
            this.thermalCriticalNotificationTitle = str4;
            this.thermalCriticalNotificationContent = str5;
            this.powerSaveModeNotificationTitle = str6;
            this.powerSaveModeNotificationContent = str7;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class NotificationInfoProvider {
        Locale mCachedLocale;
        public SparseArray mCachedNotificationInfos;
        public final Context mContext;
        public final Object mLock = new Object();

        public NotificationInfoProvider(Context context) {
            this.mContext = context;
        }

        public Locale getCachedLocale() {
            Locale locale;
            synchronized (this.mLock) {
                locale = this.mCachedLocale;
            }
            return locale;
        }

        public SparseArray loadNotificationInfos() {
            SparseArray sparseArray = new SparseArray();
            int[] intArray = this.mContext.getResources().getIntArray(17236441);
            String[] stringArray = this.mContext.getResources().getStringArray(17236438);
            String[] stringArray2 = this.mContext.getResources().getStringArray(17236437);
            String[] stringArray3 = this.mContext.getResources().getStringArray(17236436);
            String[] stringArray4 = this.mContext.getResources().getStringArray(17236443);
            String[] stringArray5 = this.mContext.getResources().getStringArray(17236442);
            String[] stringArray6 = this.mContext.getResources().getStringArray(17236440);
            String[] stringArray7 = this.mContext.getResources().getStringArray(17236439);
            if (intArray.length != stringArray.length || intArray.length != stringArray2.length || intArray.length != stringArray3.length || intArray.length != stringArray4.length || intArray.length != stringArray5.length || intArray.length != stringArray6.length || intArray.length != stringArray7.length) {
                throw new IllegalStateException("The length of state identifiers and notification texts must match!");
            }
            for (int i = 0; i < intArray.length; i++) {
                int i2 = intArray[i];
                if (i2 != -1) {
                    sparseArray.put(i2, new NotificationInfo(stringArray[i], stringArray2[i], stringArray3[i], stringArray4[i], stringArray5[i], stringArray6[i], stringArray7[i]));
                }
            }
            return sparseArray;
        }

        public void refreshNotificationInfos(Locale locale) {
            synchronized (this.mLock) {
                this.mCachedLocale = locale;
                this.mCachedNotificationInfos = loadNotificationInfos();
            }
        }
    }

    public DeviceStateNotificationController(Context context, Handler handler, Runnable runnable, NotificationInfoProvider notificationInfoProvider, PackageManager packageManager, NotificationManager notificationManager) {
        this.mContext = context;
        this.mHandler = handler;
        this.mCancelStateRunnable = runnable;
        this.mNotificationInfoProvider = notificationInfoProvider;
        this.mPackageManager = packageManager;
        this.mNotificationManager = notificationManager;
        context.registerReceiver(this, new IntentFilter(INTENT_ACTION_CANCEL_STATE), "android.permission.CONTROL_DEVICE_STATE", handler, 4);
    }

    public final SparseArray getNotificationInfos() {
        SparseArray sparseArray;
        Locale locale = this.mContext.getResources().getConfiguration().getLocales().get(0);
        NotificationInfoProvider notificationInfoProvider = this.mNotificationInfoProvider;
        synchronized (notificationInfoProvider.mLock) {
            try {
                if (!locale.equals(notificationInfoProvider.mCachedLocale)) {
                    notificationInfoProvider.refreshNotificationInfos(locale);
                }
                sparseArray = notificationInfoProvider.mCachedNotificationInfos;
            } catch (Throwable th) {
                throw th;
            }
        }
        return sparseArray;
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        if (intent == null || !INTENT_ACTION_CANCEL_STATE.equals(intent.getAction())) {
            return;
        }
        this.mCancelStateRunnable.run();
    }

    public final void showNotification(String str, String str2, String str3, boolean z, int i, PendingIntent pendingIntent, String str4) {
        final NotificationChannel notificationChannel = new NotificationChannel("DeviceStateManager", str, 4);
        final Notification.Builder category = new Notification.Builder(this.mContext, "DeviceStateManager").setSmallIcon(i).setContentTitle(str2).setContentText(str3).setSubText(str).setLocalOnly(true).setOngoing(z).setCategory("sys");
        if (pendingIntent != null && str4 != null) {
            category.addAction(new Notification.Action.Builder((Icon) null, str4, pendingIntent).build());
        }
        this.mHandler.post(new Runnable() { // from class: com.android.server.devicestate.DeviceStateNotificationController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                DeviceStateNotificationController deviceStateNotificationController = DeviceStateNotificationController.this;
                NotificationChannel notificationChannel2 = notificationChannel;
                Notification.Builder builder = category;
                deviceStateNotificationController.mNotificationManager.createNotificationChannel(notificationChannel2);
                deviceStateNotificationController.mNotificationManager.notify("DeviceStateManager", 1, builder.build());
            }
        });
    }
}
