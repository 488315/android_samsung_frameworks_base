package com.android.keyguard.logging;

import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class DeviceEntryIconLogger {
    public final LogBuffer logBuffer;

    public DeviceEntryIconLogger(LogBuffer logBuffer) {
        this.logBuffer = logBuffer;
    }

    public final void logDeviceEntryUdfpsTouchOverlayShouldHandleTouches(boolean z, boolean z2, boolean z3, boolean z4) {
        LogLevel logLevel = LogLevel.DEBUG;
        DeviceEntryIconLogger$logDeviceEntryUdfpsTouchOverlayShouldHandleTouches$2 deviceEntryIconLogger$logDeviceEntryUdfpsTouchOverlayShouldHandleTouches$2 = new Function1() { // from class: com.android.keyguard.logging.DeviceEntryIconLogger$logDeviceEntryUdfpsTouchOverlayShouldHandleTouches$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                boolean bool4 = logMessage.getBool4();
                boolean bool1 = logMessage.getBool1();
                return KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("shouldHandleTouches=", " canTouchDeviceEntryViewAlpha=", " alternateBouncerVisible=", bool4, bool1), logMessage.getBool2(), " hideAffordancesRequest=", logMessage.getBool3());
            }
        };
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("DeviceEntryUdfpsTouchOverlay", logLevel, deviceEntryIconLogger$logDeviceEntryUdfpsTouchOverlayShouldHandleTouches$2, null);
        ((LogMessageImpl) obtain).bool1 = z2;
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.bool2 = z3;
        logMessageImpl.bool3 = z4;
        logMessageImpl.bool4 = z;
        logBuffer.commit(obtain);
    }
}
