package com.android.systemui.stylus;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.view.InputDevice;
import androidx.core.app.NotificationCompat$Builder;
import com.android.systemui.R;
import com.android.systemui.log.DebugLogger;
import com.android.systemui.shared.hardware.InputManagerKt;
import com.android.systemui.util.NotificationChannels;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.text.NumberFormat;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Reflection;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class StylusUsiPowerUI$refresh$1 implements Runnable {
    public final /* synthetic */ StylusUsiPowerUI this$0;

    public StylusUsiPowerUI$refresh$1(StylusUsiPowerUI stylusUsiPowerUI) {
        this.this$0 = stylusUsiPowerUI;
    }

    @Override // java.lang.Runnable
    public final void run() {
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
        NotificationCompat$Builder notificationCompat$Builder = new NotificationCompat$Builder(stylusUsiPowerUI3.context, NotificationChannels.BATTERY);
        notificationCompat$Builder.mNotification.icon = R.drawable.ic_power_low;
        notificationCompat$Builder.mNotification.deleteIntent = PendingIntent.getBroadcast(stylusUsiPowerUI3.context, 0, new Intent("StylusUsiPowerUI.dismiss").setPackage(stylusUsiPowerUI3.context.getPackageName()), QuickStepContract.SYSUI_STATE_REQUESTED_RECENT_KEY);
        notificationCompat$Builder.mContentIntent = PendingIntent.getBroadcast(stylusUsiPowerUI3.context, 0, new Intent("StylusUsiPowerUI.click").setPackage(stylusUsiPowerUI3.context.getPackageName()), QuickStepContract.SYSUI_STATE_REQUESTED_RECENT_KEY);
        notificationCompat$Builder.mContentTitle = NotificationCompat$Builder.limitCharSequenceLength(stylusUsiPowerUI3.context.getString(R.string.stylus_battery_low_percentage, NumberFormat.getPercentInstance().format(Float.valueOf(stylusUsiPowerUI3.batteryCapacity))));
        notificationCompat$Builder.mContentText = NotificationCompat$Builder.limitCharSequenceLength(stylusUsiPowerUI3.context.getString(R.string.stylus_battery_low_subtitle));
        notificationCompat$Builder.mPriority = 0;
        notificationCompat$Builder.mLocalOnly = true;
        notificationCompat$Builder.setFlag(8, true);
        notificationCompat$Builder.setFlag(16, true);
        Notification build = notificationCompat$Builder.build();
        int i2 = DebugLogger.$r8$clinit;
        boolean z3 = Build.IS_DEBUGGABLE;
        Reflection.getOrCreateKotlinClass(StylusUsiPowerUI.class).getSimpleName();
        stylusUsiPowerUI3.logUiEvent(StylusUiEvent.STYLUS_LOW_BATTERY_NOTIFICATION_SHOWN);
        stylusUsiPowerUI3.notificationManager.notify(StylusUsiPowerUI.USI_NOTIFICATION_ID, build);
    }
}
