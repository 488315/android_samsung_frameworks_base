package com.android.systemui.statusbar.notification;

import android.util.FloatProperty;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NotificationWakeUpCoordinator$Companion$delayedDozeAmount$1 extends FloatProperty {
    public NotificationWakeUpCoordinator$Companion$delayedDozeAmount$1() {
        super("delayedDozeAmount");
    }

    /* JADX WARN: Code restructure failed: missing block: B:8:0x001c, code lost:
    
        if ((r6 == 0.0f) == false) goto L14;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void setValue(NotificationWakeUpCoordinator notificationWakeUpCoordinator, float f) {
        notificationWakeUpCoordinator.delayedDozeAmountOverride = f;
        NotificationWakeUpCoordinatorLogger notificationWakeUpCoordinatorLogger = notificationWakeUpCoordinator.logger;
        notificationWakeUpCoordinatorLogger.getClass();
        boolean z = true;
        if (!(f == 1.0f)) {
        }
        z = false;
        if (!notificationWakeUpCoordinatorLogger.lastSetDelayDozeAmountOverrideLogWasFractional || !z || !notificationWakeUpCoordinatorLogger.allowThrottle) {
            notificationWakeUpCoordinatorLogger.lastSetDelayDozeAmountOverrideLogWasFractional = z;
            LogLevel logLevel = LogLevel.DEBUG;
            C2724x39cc873f c2724x39cc873f = new Function1() { // from class: com.android.systemui.statusbar.notification.NotificationWakeUpCoordinatorLogger$logSetDelayDozeAmountOverride$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return "setDelayDozeAmountOverride(" + ((LogMessage) obj).getDouble1() + ")";
                }
            };
            LogBuffer logBuffer = notificationWakeUpCoordinatorLogger.buffer;
            LogMessage obtain = logBuffer.obtain("NotificationWakeUpCoordinator", logLevel, c2724x39cc873f, null);
            obtain.setDouble1(f);
            logBuffer.commit(obtain);
        }
        notificationWakeUpCoordinator.updateDozeAmount();
    }

    @Override // android.util.Property
    public final Float get(Object obj) {
        return Float.valueOf(((NotificationWakeUpCoordinator) obj).delayedDozeAmountOverride);
    }

    @Override // android.util.FloatProperty
    public final /* bridge */ /* synthetic */ void setValue(Object obj, float f) {
        setValue((NotificationWakeUpCoordinator) obj, f);
    }
}
