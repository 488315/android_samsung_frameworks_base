package com.android.systemui.log;

import com.android.keyguard.KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0;
import com.android.systemui.bouncer.shared.model.BouncerMessageModel;
import com.android.systemui.bouncer.shared.model.Message;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

public final class BouncerLogger {
    public final LogBuffer buffer;

    public BouncerLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }

    public final void bouncerMessageUpdated(BouncerMessageModel bouncerMessageModel) {
        Message message;
        Message message2;
        Integer num;
        Message message3;
        Message message4;
        Integer num2;
        LogLevel logLevel = LogLevel.DEBUG;
        BouncerLogger$bouncerMessageUpdated$2 bouncerLogger$bouncerMessageUpdated$2 = new Function1() { // from class: com.android.systemui.log.BouncerLogger$bouncerMessageUpdated$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                int int1 = logMessage.getInt1();
                String str1 = logMessage.getStr1();
                int int2 = logMessage.getInt2();
                String str2 = logMessage.getStr2();
                StringBuilder m = KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0.m(int1, "Bouncer message update received: ", ", ", str1, ", ");
                m.append(int2);
                m.append(", ");
                m.append(str2);
                return m.toString();
            }
        };
        LogBuffer logBuffer = this.buffer;
        String str = null;
        LogMessage obtain = logBuffer.obtain("BouncerLog", logLevel, bouncerLogger$bouncerMessageUpdated$2, null);
        int i = -1;
        ((LogMessageImpl) obtain).int1 = (bouncerMessageModel == null || (message4 = bouncerMessageModel.message) == null || (num2 = message4.messageResId) == null) ? -1 : num2.intValue();
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = (bouncerMessageModel == null || (message3 = bouncerMessageModel.message) == null) ? null : message3.message;
        if (bouncerMessageModel != null && (message2 = bouncerMessageModel.secondaryMessage) != null && (num = message2.messageResId) != null) {
            i = num.intValue();
        }
        logMessageImpl.int2 = i;
        if (bouncerMessageModel != null && (message = bouncerMessageModel.secondaryMessage) != null) {
            str = message.message;
        }
        logMessageImpl.str2 = str;
        logBuffer.commit(obtain);
    }

    public final void interestedStateChanged(String str, boolean z) {
        LogLevel logLevel = LogLevel.DEBUG;
        BouncerLogger$interestedStateChanged$2 bouncerLogger$interestedStateChanged$2 = new Function1() { // from class: com.android.systemui.log.BouncerLogger$interestedStateChanged$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return "state changed: " + logMessage.getStr1() + ": " + logMessage.getBool1();
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("BouncerLog", logLevel, bouncerLogger$interestedStateChanged$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = str;
        logMessageImpl.bool1 = z;
        logBuffer.commit(obtain);
    }
}
