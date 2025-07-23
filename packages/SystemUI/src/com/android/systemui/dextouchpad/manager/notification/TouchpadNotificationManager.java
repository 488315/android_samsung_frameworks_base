package com.android.systemui.dextouchpad.manager.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import com.android.keyguard.ClockEventController$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.dextouchpad.util.Features;
import com.android.systemui.dextouchpad.util.Utils;
import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class TouchpadNotificationManager {
    public static TouchpadNotificationManager sInstance;
    public final Context mContext;
    public final DualModeReceiver mDualModeReceiver;
    public final NotificationManager mNotificationManager;
    public final Map mActiveNotifications = new HashMap();
    public boolean mDMReceiverRegistered = false;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.dextouchpad.manager.notification.TouchpadNotificationManager$1, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$android$systemui$dextouchpad$manager$notification$NotificationType;

        static {
            int[] iArr = new int[NotificationType.values().length];
            $SwitchMap$com$android$systemui$dextouchpad$manager$notification$NotificationType = iArr;
            try {
                iArr[NotificationType.TOUCHPAD.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$android$systemui$dextouchpad$manager$notification$NotificationType[NotificationType.SPEN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    private TouchpadNotificationManager(Context context) {
        this.mContext = context;
        this.mDualModeReceiver = new DualModeReceiver(context);
        NotificationChannel notificationChannel = new NotificationChannel("general", context.getString(R.string.dex_notification_general_channel), 2);
        notificationChannel.setLockscreenVisibility(1);
        notificationChannel.setShowBadge(false);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NotificationManager.class);
        this.mNotificationManager = notificationManager;
        if (notificationManager != null) {
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    public static TouchpadNotificationManager getsInstance(Context context) {
        synchronized (TouchpadNotificationManager.class) {
            try {
                if (sInstance == null) {
                    sInstance = new TouchpadNotificationManager(context);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return sInstance;
    }

    public final Notification.Builder getDefaultBuilder() {
        Bundle bundle = new Bundle();
        bundle.putString("android.substName", this.mContext.getString(R.string.samsung_dex));
        return new Notification.Builder(this.mContext, "general").setSmallIcon(R.drawable.stat_notify_touchpad).setOngoing(true).setOnlyAlertOnce(false).setGroup("group_touchpad").addExtras(bundle);
    }

    public final void remove(NotificationType notificationType) {
        boolean containsKey = ((HashMap) this.mActiveNotifications).containsKey(notificationType);
        if (Features.DEBUG) {
            EmergencyButtonController$$ExternalSyntheticOutline0.m("remove, hasNotification=", "DexTouchpadNotificationManager", containsKey);
        }
        if (containsKey) {
            this.mNotificationManager.cancel(((Integer) ((HashMap) this.mActiveNotifications).get(notificationType)).intValue());
            ((HashMap) this.mActiveNotifications).remove(notificationType);
        }
    }

    public final void show(NotificationType notificationType) {
        String string;
        if (Utils.mDesktopDisplayId == -1) {
            Log.d("DexTouchpadNotificationManager", "show, Not Desktop mode");
            return;
        }
        boolean containsKey = ((HashMap) this.mActiveNotifications).containsKey(notificationType);
        if (Features.DEBUG) {
            EmergencyButtonController$$ExternalSyntheticOutline0.m("show, hasNotification=", "DexTouchpadNotificationManager", containsKey);
        }
        if (containsKey) {
            remove(notificationType);
        }
        int i = AnonymousClass1.$SwitchMap$com$android$systemui$dextouchpad$manager$notification$NotificationType[notificationType.ordinal()];
        if (i == 1) {
            int i2 = Features.IS_SUPPORT_TABLET ? R.string.dex_touchpad_available_tablet : R.string.dex_touchpad_available;
            int i3 = Features.IS_SUPPORT_SPEN ? R.string.dex_touchpad_spen_available_description : R.string.dex_touchpad_available_description;
            String string2 = this.mContext.getString(i2);
            String string3 = this.mContext.getString(i3);
            this.mNotificationManager.notify(notificationType.id, getDefaultBuilder().setContentTitle(string2).setTicker(string2).setContentText(string3).setStyle(new Notification.BigTextStyle().bigText(string3)).setContentIntent(Utils.getPendingIntent(this.mContext, "com.samsung.android.desktopmode.action.TOUCHPAD_AVAILABLE_NOTIFICATION_PRESSED")).build());
            ((HashMap) this.mActiveNotifications).put(NotificationType.TOUCHPAD, Integer.valueOf(notificationType.id));
            return;
        }
        if (i != 2) {
            return;
        }
        int i4 = Settings.Global.getInt(this.mContext.getContentResolver(), "SPEN_INPUT_MODE_DEX", 0);
        PendingIntent pendingIntent = Utils.getPendingIntent(this.mContext, "com.samsung.android.desktopmode.action.SPEN_NOTIFICATION_PRESSED");
        PendingIntent pendingIntent2 = Utils.getPendingIntent(this.mContext, "com.samsung.android.desktopmode.action.SPEN_NOTIFICATION_CHANGE_MODE_PRESSED");
        Notification.Action build = new Notification.Action.Builder((Icon) null, this.mContext.getString(R.string.dex_spen_tips), pendingIntent).build();
        Notification.Action build2 = new Notification.Action.Builder((Icon) null, this.mContext.getString(R.string.dex_spen_change_mode), pendingIntent2).build();
        Notification.Builder defaultBuilder = getDefaultBuilder();
        if (i4 == 0) {
            string = this.mContext.getString(R.string.dex_spen_pen_mode);
            defaultBuilder.addAction(build);
            defaultBuilder.addAction(build2);
        } else if (1 != i4) {
            ClockEventController$$ExternalSyntheticOutline0.m(i4, "Invalid SPen mode,", "DexTouchpadNotificationManager");
            return;
        } else {
            string = this.mContext.getString(R.string.dex_spen_mouse_mode);
            defaultBuilder.addAction(build2);
        }
        String string4 = this.mContext.getString(R.string.dex_spen_title, string);
        defaultBuilder.setContentTitle(string4).setTicker(string4);
        this.mNotificationManager.notify(notificationType.id, defaultBuilder.build());
        ((HashMap) this.mActiveNotifications).put(NotificationType.SPEN, Integer.valueOf(notificationType.id));
    }
}
