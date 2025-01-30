package com.android.systemui.statusbar.policy;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.android.systemui.R;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class BatteryStateNotifier implements BatteryController.BatteryStateChangeCallback {
    public final Context context;
    public final BatteryController controller;
    public final DelayableExecutor delayableExecutor;
    public final NotificationManager noMan;
    public boolean stateUnknown;

    public BatteryStateNotifier(BatteryController batteryController, NotificationManager notificationManager, DelayableExecutor delayableExecutor, Context context) {
        this.controller = batteryController;
        this.noMan = notificationManager;
        this.delayableExecutor = delayableExecutor;
        this.context = context;
    }

    @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
    public final void onBatteryUnknownStateChanged(boolean z) {
        this.stateUnknown = z;
        if (!z) {
            final Function0 function0 = new Function0() { // from class: com.android.systemui.statusbar.policy.BatteryStateNotifier$scheduleNotificationCancel$r$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    BatteryStateNotifier batteryStateNotifier = BatteryStateNotifier.this;
                    if (!batteryStateNotifier.stateUnknown) {
                        batteryStateNotifier.noMan.cancel(666);
                    }
                    return Unit.INSTANCE;
                }
            };
            this.delayableExecutor.executeDelayed(14400000L, new Runnable() { // from class: com.android.systemui.statusbar.policy.BatteryStateNotifierKt$sam$java_lang_Runnable$0
                @Override // java.lang.Runnable
                public final /* synthetic */ void run() {
                    Function0.this.invoke();
                }
            });
        } else {
            NotificationChannel notificationChannel = new NotificationChannel("battery_status", "Battery status", 3);
            NotificationManager notificationManager = this.noMan;
            notificationManager.createNotificationChannel(notificationChannel);
            Context context = this.context;
            notificationManager.notify("BatteryStateNotifier", 666, new Notification.Builder(context, notificationChannel.getId()).setAutoCancel(false).setContentTitle(context.getString(R.string.battery_state_unknown_notification_title)).setContentText(context.getString(R.string.battery_state_unknown_notification_text)).setSmallIcon(17304219).setContentIntent(PendingIntent.getActivity(context, 0, new Intent("android.intent.action.VIEW", Uri.parse(context.getString(R.string.config_batteryStateUnknownUrl))), QuickStepContract.SYSUI_STATE_REQUESTED_RECENT_KEY)).setAutoCancel(true).setOngoing(true).build());
        }
    }
}
