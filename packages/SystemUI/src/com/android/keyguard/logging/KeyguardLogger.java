package com.android.keyguard.logging;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.core.provider.FontProvider$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.systemui.keyguard.KeyguardIndicationRotateTextViewController;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

public final class KeyguardLogger {
    public final LogBuffer buffer;

    public KeyguardLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }

    public final void delayShowingTrustAgentError(CharSequence charSequence, boolean z, boolean z2) {
        LogLevel logLevel = LogLevel.DEBUG;
        KeyguardLogger$delayShowingTrustAgentError$2 keyguardLogger$delayShowingTrustAgentError$2 = new Function1() { // from class: com.android.keyguard.logging.KeyguardLogger$delayShowingTrustAgentError$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                String str1 = logMessage.getStr1();
                boolean bool1 = logMessage.getBool1();
                return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(CarrierTextManagerLogger$logCallbackSentFromUpdate$2$$ExternalSyntheticOutline0.m("Delay showing trustAgentError:", str1, ". fpEngaged:", " faceRunning:", bool1), logMessage.getBool2(), " ");
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("KeyguardLog", logLevel, keyguardLogger$delayShowingTrustAgentError$2, null);
        ((LogMessageImpl) obtain).str1 = charSequence.toString();
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.bool1 = z;
        logMessageImpl.bool2 = z2;
        logBuffer.commit(obtain);
    }

    public final void log(String str, LogLevel logLevel, String str2, Object obj) {
        KeyguardLogger$log$2 keyguardLogger$log$2 = new Function1() { // from class: com.android.keyguard.logging.KeyguardLogger$log$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj2) {
                LogMessage logMessage = (LogMessage) obj2;
                return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(logMessage.getStr1(), ": ", logMessage.getStr2());
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain(str, logLevel, keyguardLogger$log$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = str2;
        logMessageImpl.str2 = obj.toString();
        logBuffer.commit(obtain);
    }

    public final void logBiometricMessage(String str, Integer num, String str2) {
        LogLevel logLevel = LogLevel.DEBUG;
        KeyguardLogger$logBiometricMessage$2 keyguardLogger$logBiometricMessage$2 = new Function1() { // from class: com.android.keyguard.logging.KeyguardLogger$logBiometricMessage$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return logMessage.getStr1() + " msgId: " + logMessage.getStr2() + " msg: " + logMessage.getStr3();
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("KeyguardLog", logLevel, keyguardLogger$logBiometricMessage$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = str;
        logMessageImpl.str2 = String.valueOf(num);
        logMessageImpl.str3 = str2;
        logBuffer.commit(obtain);
    }

    public final void logDropFaceMessage(CharSequence charSequence, CharSequence charSequence2) {
        LogLevel logLevel = LogLevel.DEBUG;
        KeyguardLogger$logDropFaceMessage$2 keyguardLogger$logDropFaceMessage$2 = new Function1() { // from class: com.android.keyguard.logging.KeyguardLogger$logDropFaceMessage$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return FontProvider$$ExternalSyntheticOutline0.m("droppingFaceMessage message=", logMessage.getStr1(), " followUpMessage:", logMessage.getStr2());
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("KeyguardIndication", logLevel, keyguardLogger$logDropFaceMessage$2, null);
        ((LogMessageImpl) obtain).str1 = charSequence.toString();
        ((LogMessageImpl) obtain).str2 = charSequence2 != null ? charSequence2.toString() : null;
        logBuffer.commit(obtain);
    }

    public final void logKeyguardSwitchIndication(int i, String str) {
        LogLevel logLevel = LogLevel.DEBUG;
        Function1 function1 = new Function1() { // from class: com.android.keyguard.logging.KeyguardLogger$logKeyguardSwitchIndication$2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                KeyguardLogger keyguardLogger = KeyguardLogger.this;
                int int1 = logMessage.getInt1();
                String str1 = logMessage.getStr1();
                keyguardLogger.getClass();
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("keyguardSwitchIndication ", int1 == 3 ? FontProvider$$ExternalSyntheticOutline0.m("type=", KeyguardIndicationRotateTextViewController.indicationTypeToString(int1), " message=", str1) : AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("type=", KeyguardIndicationRotateTextViewController.indicationTypeToString(int1)));
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("KeyguardIndication", logLevel, function1, null);
        ((LogMessageImpl) obtain).int1 = i;
        ((LogMessageImpl) obtain).str1 = str;
        logBuffer.commit(obtain);
    }

    public final void logRefreshBatteryInfo(boolean z, boolean z2, boolean z3, int i) {
        LogLevel logLevel = LogLevel.DEBUG;
        KeyguardLogger$logRefreshBatteryInfo$2 keyguardLogger$logRefreshBatteryInfo$2 = new Function1() { // from class: com.android.keyguard.logging.KeyguardLogger$logRefreshBatteryInfo$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                boolean bool1 = logMessage.getBool1();
                boolean bool2 = logMessage.getBool2();
                boolean bool3 = logMessage.getBool3();
                int int1 = logMessage.getInt1();
                StringBuilder m = KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("refreshBatteryInfo isChargingOrFull:", " powerPluggedIn:", " batteryOverheated:", bool1, bool2);
                m.append(bool3);
                m.append(" batteryLevel:");
                m.append(int1);
                return m.toString();
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("KeyguardIndication", logLevel, keyguardLogger$logRefreshBatteryInfo$2, null);
        ((LogMessageImpl) obtain).bool1 = z;
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.bool2 = z2;
        logMessageImpl.bool3 = z3;
        logMessageImpl.int1 = i;
        logBuffer.commit(obtain);
    }

    public final void notShowingUnlockRipple(boolean z, boolean z2) {
        LogLevel logLevel = LogLevel.DEBUG;
        KeyguardLogger$notShowingUnlockRipple$2 keyguardLogger$notShowingUnlockRipple$2 = new Function1() { // from class: com.android.keyguard.logging.KeyguardLogger$notShowingUnlockRipple$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return "Not showing unlock ripple: keyguardNotShowing: " + logMessage.getBool1() + ", unlockNotAllowed: " + logMessage.getBool2();
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("AuthRippleController", logLevel, keyguardLogger$notShowingUnlockRipple$2, null);
        ((LogMessageImpl) obtain).bool1 = z;
        ((LogMessageImpl) obtain).bool2 = z2;
        logBuffer.commit(obtain);
    }

    public final void showingUnlockRippleAt(int i, int i2, String str) {
        LogLevel logLevel = LogLevel.DEBUG;
        KeyguardLogger$showingUnlockRippleAt$2 keyguardLogger$showingUnlockRippleAt$2 = new Function1() { // from class: com.android.keyguard.logging.KeyguardLogger$showingUnlockRippleAt$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                int int1 = logMessage.getInt1();
                int int2 = logMessage.getInt2();
                String str1 = logMessage.getStr1();
                StringBuilder m = RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(int1, int2, "Showing unlock ripple with center (x, y): (", ", ", "), context: ");
                m.append(str1);
                return m.toString();
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("AuthRippleController", logLevel, keyguardLogger$showingUnlockRippleAt$2, null);
        ((LogMessageImpl) obtain).int1 = i;
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.int2 = i2;
        logMessageImpl.str1 = str;
        logBuffer.commit(obtain);
    }
}
