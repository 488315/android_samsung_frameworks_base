package com.android.systemui.statusbar.events;

import com.android.keyguard.logging.CarrierTextManagerLogger$logCallbackSentFromUpdate$2$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SystemStatusAnimationSchedulerLogger {
    public final LogBuffer logBuffer;

    public SystemStatusAnimationSchedulerLogger(LogBuffer logBuffer) {
        this.logBuffer = logBuffer;
    }

    public static String name(int i) {
        return i != 0 ? i != 1 ? i != 2 ? i != 3 ? i != 4 ? i != 5 ? "UNKNOWN_ANIMATION_STATE" : "SHOWING_PERSISTENT_DOT" : "ANIMATING_OUT" : "RUNNING_CHIP_ANIM" : "ANIMATING_IN" : "ANIMATION_QUEUED" : "IDLE";
    }

    public final void logUpdateEvent(StatusEvent statusEvent, final int i) {
        LogLevel logLevel = LogLevel.DEBUG;
        Function1 function1 = new Function1() { // from class: com.android.systemui.statusbar.events.SystemStatusAnimationSchedulerLogger$logUpdateEvent$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                String str1 = logMessage.getStr1();
                boolean bool1 = logMessage.getBool1();
                int int1 = logMessage.getInt1();
                boolean bool2 = logMessage.getBool2();
                SystemStatusAnimationSchedulerLogger systemStatusAnimationSchedulerLogger = SystemStatusAnimationSchedulerLogger.this;
                int i2 = i;
                systemStatusAnimationSchedulerLogger.getClass();
                String name = SystemStatusAnimationSchedulerLogger.name(i2);
                StringBuilder m = CarrierTextManagerLogger$logCallbackSentFromUpdate$2$$ExternalSyntheticOutline0.m("Updating current event from: ", str1, "(forceVisible=", ", priority=", bool1);
                m.append(int1);
                m.append(", showAnimation=");
                m.append(bool2);
                m.append("), animationState=");
                m.append(name);
                return m.toString();
            }
        };
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("SystemStatusAnimationSchedulerLog", logLevel, function1, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = statusEvent.getClass().getSimpleName();
        logMessageImpl.int1 = statusEvent.getPriority();
        logMessageImpl.bool1 = statusEvent.getForceVisible();
        logMessageImpl.bool2 = statusEvent.getShowAnimation();
        logMessageImpl.int2 = i;
        logBuffer.commit(obtain);
    }
}
