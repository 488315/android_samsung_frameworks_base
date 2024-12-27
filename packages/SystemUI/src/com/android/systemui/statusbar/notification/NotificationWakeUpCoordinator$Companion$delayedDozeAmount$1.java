package com.android.systemui.statusbar.notification;

import android.util.FloatProperty;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class NotificationWakeUpCoordinator$Companion$delayedDozeAmount$1 extends FloatProperty {
    public NotificationWakeUpCoordinator$Companion$delayedDozeAmount$1() {
        super("delayedDozeAmount");
    }

    public static void setValue(NotificationWakeUpCoordinator notificationWakeUpCoordinator, float f) {
        notificationWakeUpCoordinator.delayedDozeAmountOverride = f;
        NotificationWakeUpCoordinatorLogger notificationWakeUpCoordinatorLogger = notificationWakeUpCoordinator.logger;
        notificationWakeUpCoordinatorLogger.getClass();
        boolean z = (f == 1.0f || f == 0.0f) ? false : true;
        if (!notificationWakeUpCoordinatorLogger.lastSetDelayDozeAmountOverrideLogWasFractional || !z || !notificationWakeUpCoordinatorLogger.allowThrottle) {
            notificationWakeUpCoordinatorLogger.lastSetDelayDozeAmountOverrideLogWasFractional = z;
            LogLevel logLevel = LogLevel.DEBUG;
            NotificationWakeUpCoordinatorLogger$logSetDelayDozeAmountOverride$2 notificationWakeUpCoordinatorLogger$logSetDelayDozeAmountOverride$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.NotificationWakeUpCoordinatorLogger$logSetDelayDozeAmountOverride$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return "setDelayDozeAmountOverride(" + ((LogMessage) obj).getDouble1() + ")";
                }
            };
            LogBuffer logBuffer = notificationWakeUpCoordinatorLogger.buffer;
            LogMessage obtain = logBuffer.obtain("NotificationWakeUpCoordinator", logLevel, notificationWakeUpCoordinatorLogger$logSetDelayDozeAmountOverride$2, null);
            ((LogMessageImpl) obtain).double1 = f;
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
