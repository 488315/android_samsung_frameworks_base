package com.android.systemui.stylus;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.hardware.input.InputManager;
import android.os.Build;
import android.view.InputDevice;
import androidx.core.app.NotificationCompat$Builder;
import com.android.systemui.R;
import com.android.systemui.log.DebugLogger;
import com.android.systemui.shared.hardware.InputManagerKt$$ExternalSyntheticLambda0;
import com.android.systemui.util.NotificationChannels;
import java.text.NumberFormat;
import kotlin.collections.ArraysKt___ArraysKt$asSequence$$inlined$Sequence$4;
import kotlin.jvm.internal.Reflection;
import kotlin.sequences.EmptySequence;
import kotlin.sequences.FilteringSequence$iterator$1;
import kotlin.sequences.SequencesKt___SequencesKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class StylusUsiPowerUI$refresh$1 implements Runnable {
    public final /* synthetic */ StylusUsiPowerUI this$0;

    public StylusUsiPowerUI$refresh$1(StylusUsiPowerUI stylusUsiPowerUI) {
        this.this$0 = stylusUsiPowerUI;
    }

    @Override // java.lang.Runnable
    public final void run() {
        StylusUsiPowerUI stylusUsiPowerUI = this.this$0;
        boolean z = !Float.isNaN(stylusUsiPowerUI.batteryCapacity) && stylusUsiPowerUI.batteryCapacity <= 0.16f;
        StylusUsiPowerUI stylusUsiPowerUI2 = this.this$0;
        if (!stylusUsiPowerUI2.suppressed) {
            InputManager inputManager = stylusUsiPowerUI2.inputManager;
            int[] inputDeviceIds = inputManager.getInputDeviceIds();
            FilteringSequence$iterator$1 filteringSequence$iterator$1 = new FilteringSequence$iterator$1(SequencesKt___SequencesKt.mapNotNull(inputDeviceIds.length == 0 ? EmptySequence.INSTANCE : new ArraysKt___ArraysKt$asSequence$$inlined$Sequence$4(inputDeviceIds), new InputManagerKt$$ExternalSyntheticLambda0(inputManager)));
            while (true) {
                if (filteringSequence$iterator$1.hasNext()) {
                    InputDevice inputDevice = (InputDevice) filteringSequence$iterator$1.next();
                    if (inputDevice.supportsSource(16386) && inputDevice.getBluetoothAddress() != null) {
                        break;
                    }
                } else if (z) {
                    StylusUsiPowerUI stylusUsiPowerUI3 = this.this$0;
                    NotificationCompat$Builder notificationCompat$Builder = new NotificationCompat$Builder(stylusUsiPowerUI3.context, NotificationChannels.BATTERY);
                    notificationCompat$Builder.mNotification.icon = R.drawable.ic_power_low;
                    notificationCompat$Builder.mNotification.deleteIntent = PendingIntent.getBroadcast(stylusUsiPowerUI3.context, 0, new Intent("StylusUsiPowerUI.dismiss").setPackage(stylusUsiPowerUI3.context.getPackageName()), 67108864);
                    notificationCompat$Builder.mContentIntent = PendingIntent.getBroadcast(stylusUsiPowerUI3.context, 0, new Intent("StylusUsiPowerUI.click").setPackage(stylusUsiPowerUI3.context.getPackageName()), 67108864);
                    notificationCompat$Builder.mContentTitle = NotificationCompat$Builder.limitCharSequenceLength(stylusUsiPowerUI3.context.getString(R.string.stylus_battery_low_percentage, NumberFormat.getPercentInstance().format(Float.valueOf(stylusUsiPowerUI3.batteryCapacity))));
                    notificationCompat$Builder.mContentText = NotificationCompat$Builder.limitCharSequenceLength(stylusUsiPowerUI3.context.getString(R.string.stylus_battery_low_subtitle));
                    notificationCompat$Builder.mPriority = 0;
                    notificationCompat$Builder.mLocalOnly = true;
                    notificationCompat$Builder.setFlag(8, true);
                    notificationCompat$Builder.setFlag(16, true);
                    Notification build = notificationCompat$Builder.build();
                    DebugLogger debugLogger = DebugLogger.INSTANCE;
                    boolean z2 = Build.IS_DEBUGGABLE;
                    Reflection.getOrCreateKotlinClass(StylusUsiPowerUI.class).getSimpleName();
                    stylusUsiPowerUI3.logUiEvent(StylusUiEvent.STYLUS_LOW_BATTERY_NOTIFICATION_SHOWN);
                    stylusUsiPowerUI3.notificationManager.notify(StylusUsiPowerUI.USI_NOTIFICATION_ID, build);
                    return;
                }
            }
        }
        StylusUsiPowerUI stylusUsiPowerUI4 = this.this$0;
        if (stylusUsiPowerUI4.suppressed || !z) {
            DebugLogger debugLogger2 = DebugLogger.INSTANCE;
            boolean z3 = Build.IS_DEBUGGABLE;
            Reflection.getOrCreateKotlinClass(StylusUsiPowerUI.class).getSimpleName();
            stylusUsiPowerUI4.instanceId = null;
            stylusUsiPowerUI4.notificationManager.mNotificationManager.cancel(null, StylusUsiPowerUI.USI_NOTIFICATION_ID);
        }
        if (z) {
            return;
        }
        this.this$0.suppressed = false;
    }
}
