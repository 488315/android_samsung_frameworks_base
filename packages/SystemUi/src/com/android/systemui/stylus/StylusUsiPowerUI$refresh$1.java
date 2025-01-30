package com.android.systemui.stylus;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.InputDevice;
import androidx.core.app.NotificationCompat$Builder;
import androidx.core.app.NotificationCompat$Style;
import androidx.core.app.NotificationCompatBuilder;
import androidx.core.app.NotificationManagerCompat;
import com.android.systemui.R;
import com.android.systemui.log.DebugLogger;
import com.android.systemui.shared.hardware.InputManagerKt;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.text.NumberFormat;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Reflection;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class StylusUsiPowerUI$refresh$1 implements Runnable {
    public final /* synthetic */ StylusUsiPowerUI this$0;

    public StylusUsiPowerUI$refresh$1(StylusUsiPowerUI stylusUsiPowerUI) {
        this.this$0 = stylusUsiPowerUI;
    }

    @Override // java.lang.Runnable
    public final void run() {
        Bundle bundle;
        StylusUsiPowerUI stylusUsiPowerUI = this.this$0;
        boolean z = stylusUsiPowerUI.batteryCapacity <= 0.16f;
        if (stylusUsiPowerUI.suppressed || InputManagerKt.hasInputDevice(stylusUsiPowerUI.inputManager, new Function1() { // from class: com.android.systemui.stylus.StylusUsiPowerUI$hasConnectedBluetoothStylus$1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                InputDevice inputDevice = (InputDevice) obj;
                return Boolean.valueOf(inputDevice.supportsSource(16386) && inputDevice.getBluetoothAddress() != null);
            }
        }) || !z) {
            StylusUsiPowerUI stylusUsiPowerUI2 = this.this$0;
            if (stylusUsiPowerUI2.suppressed || !z) {
                int i = DebugLogger.$r8$clinit;
                boolean z2 = Build.IS_DEBUGGABLE;
                Reflection.getOrCreateKotlinClass(StylusUsiPowerUI.class).getSimpleName();
                stylusUsiPowerUI2.instanceId = null;
                stylusUsiPowerUI2.notificationManager.mNotificationManager.cancel(null, StylusUsiPowerUI.USI_NOTIFICATION_ID);
            }
            if (z) {
                return;
            }
            this.this$0.suppressed = false;
            return;
        }
        StylusUsiPowerUI stylusUsiPowerUI3 = this.this$0;
        Context context = stylusUsiPowerUI3.context;
        NotificationCompat$Builder notificationCompat$Builder = new NotificationCompat$Builder(context, "BAT");
        Notification notification2 = notificationCompat$Builder.mNotification;
        notification2.icon = R.drawable.ic_power_low;
        Intent intent = new Intent("StylusUsiPowerUI.dismiss");
        Context context2 = stylusUsiPowerUI3.context;
        notification2.deleteIntent = PendingIntent.getBroadcast(context2, 0, intent.setPackage(context2.getPackageName()), QuickStepContract.SYSUI_STATE_REQUESTED_RECENT_KEY);
        Intent intent2 = new Intent("StylusUsiPowerUI.click");
        Context context3 = stylusUsiPowerUI3.context;
        notificationCompat$Builder.mContentIntent = PendingIntent.getBroadcast(context3, 0, intent2.setPackage(context3.getPackageName()), QuickStepContract.SYSUI_STATE_REQUESTED_RECENT_KEY);
        notificationCompat$Builder.mContentTitle = NotificationCompat$Builder.limitCharSequenceLength(context.getString(R.string.stylus_battery_low_percentage, NumberFormat.getPercentInstance().format(Float.valueOf(stylusUsiPowerUI3.batteryCapacity))));
        notificationCompat$Builder.mContentText = NotificationCompat$Builder.limitCharSequenceLength(context.getString(R.string.stylus_battery_low_subtitle));
        notificationCompat$Builder.mPriority = 0;
        notificationCompat$Builder.mLocalOnly = true;
        notificationCompat$Builder.setFlag(16, true);
        NotificationCompatBuilder notificationCompatBuilder = new NotificationCompatBuilder(notificationCompat$Builder);
        NotificationCompat$Builder notificationCompat$Builder2 = notificationCompatBuilder.mBuilderCompat;
        NotificationCompat$Style notificationCompat$Style = notificationCompat$Builder2.mStyle;
        if (notificationCompat$Style != null) {
            notificationCompat$Style.apply(notificationCompatBuilder);
        }
        if (notificationCompat$Style != null) {
            notificationCompat$Style.makeContentView();
        }
        Notification build = notificationCompatBuilder.mBuilder.build();
        if (notificationCompat$Style != null) {
            notificationCompat$Style.makeBigContentView();
        }
        if (notificationCompat$Style != null) {
            notificationCompat$Builder2.mStyle.makeHeadsUpContentView();
        }
        if (notificationCompat$Style != null && (bundle = build.extras) != null) {
            notificationCompat$Style.addCompatExtras(bundle);
        }
        int i2 = DebugLogger.$r8$clinit;
        boolean z3 = Build.IS_DEBUGGABLE;
        Reflection.getOrCreateKotlinClass(StylusUsiPowerUI.class).getSimpleName();
        stylusUsiPowerUI3.logUiEvent(StylusUiEvent.STYLUS_LOW_BATTERY_NOTIFICATION_SHOWN);
        NotificationManagerCompat notificationManagerCompat = stylusUsiPowerUI3.notificationManager;
        notificationManagerCompat.getClass();
        Bundle bundle2 = build.extras;
        boolean z4 = bundle2 != null && bundle2.getBoolean("android.support.useSideChannel");
        int i3 = StylusUsiPowerUI.USI_NOTIFICATION_ID;
        NotificationManager notificationManager = notificationManagerCompat.mNotificationManager;
        if (!z4) {
            notificationManager.notify(null, i3, build);
            return;
        }
        NotificationManagerCompat.NotifyTask notifyTask = new NotificationManagerCompat.NotifyTask(notificationManagerCompat.mContext.getPackageName(), i3, null, build);
        synchronized (NotificationManagerCompat.sLock) {
            if (NotificationManagerCompat.sSideChannelManager == null) {
                NotificationManagerCompat.sSideChannelManager = new NotificationManagerCompat.SideChannelManager(notificationManagerCompat.mContext.getApplicationContext());
            }
            NotificationManagerCompat.sSideChannelManager.mHandler.obtainMessage(0, notifyTask).sendToTarget();
        }
        notificationManager.cancel(null, i3);
    }
}
