package com.android.keyguard.logging;

import android.hardware.biometrics.BiometricSourceType;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardFMMViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class BiometricUnlockLogger {
    public final LogBuffer logBuffer;

    public BiometricUnlockLogger(LogBuffer logBuffer) {
        this.logBuffer = logBuffer;
    }

    public final void deferringAuthenticationDueToSleep(int i, BiometricSourceType biometricSourceType, boolean z) {
        LogLevel logLevel = LogLevel.DEBUG;
        BiometricUnlockLogger$deferringAuthenticationDueToSleep$2 biometricUnlockLogger$deferringAuthenticationDueToSleep$2 = new Function1() { // from class: com.android.keyguard.logging.BiometricUnlockLogger$deferringAuthenticationDueToSleep$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                int int1 = logMessage.getInt1();
                String str1 = logMessage.getStr1();
                boolean bool2 = logMessage.getBool2();
                StringBuilder m = KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0.m(int1, "onBiometricAuthenticated, deferring auth: userId: ", ", biometricSourceType: ", str1, ", goingToSleep: true, mPendingAuthentication != null: ");
                m.append(bool2);
                return m.toString();
            }
        };
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("BiometricUnlockLogger", logLevel, biometricUnlockLogger$deferringAuthenticationDueToSleep$2, null);
        ((LogMessageImpl) obtain).int1 = i;
        String name = biometricSourceType.name();
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = name;
        logMessageImpl.bool2 = z;
        logBuffer.commit(obtain);
    }

    public final void logCalculateModeForFingerprintUnlockingAllowed(boolean z, boolean z2, boolean z3, boolean z4) {
        LogLevel logLevel = LogLevel.DEBUG;
        BiometricUnlockLogger$logCalculateModeForFingerprintUnlockingAllowed$2 biometricUnlockLogger$logCalculateModeForFingerprintUnlockingAllowed$2 = new Function1() { // from class: com.android.keyguard.logging.BiometricUnlockLogger$logCalculateModeForFingerprintUnlockingAllowed$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                boolean bool1 = logMessage.getBool1();
                boolean bool2 = logMessage.getBool2();
                return KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("calculateModeForFingerprint unlockingAllowed=true deviceInteractive=", " isKeyguardShowing=", " deviceDreaming=", bool1, bool2), logMessage.getBool3(), " bypass=", logMessage.getBool4());
            }
        };
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("BiometricUnlockLogger", logLevel, biometricUnlockLogger$logCalculateModeForFingerprintUnlockingAllowed$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.bool1 = z;
        logMessageImpl.bool2 = z2;
        logMessageImpl.bool3 = z3;
        logMessageImpl.bool4 = z4;
        logBuffer.commit(obtain);
    }

    public final void logCalculateModeForFingerprintUnlockingNotAllowed(int i, boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
        LogLevel logLevel = LogLevel.DEBUG;
        BiometricUnlockLogger$logCalculateModeForFingerprintUnlockingNotAllowed$2 biometricUnlockLogger$logCalculateModeForFingerprintUnlockingNotAllowed$2 = new Function1() { // from class: com.android.keyguard.logging.BiometricUnlockLogger$logCalculateModeForFingerprintUnlockingNotAllowed$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                boolean bool1 = logMessage.getBool1();
                int int1 = logMessage.getInt1();
                boolean bool2 = logMessage.getBool2();
                boolean bool3 = logMessage.getBool3();
                boolean bool4 = logMessage.getBool4();
                boolean bool5 = logMessage.getBool5();
                StringBuilder m = KeyguardFMMViewController$$ExternalSyntheticOutline0.m("calculateModeForFingerprint unlockingAllowed=false strongBiometric=", int1, " strongAuthFlags=", bool1, " nonStrongBiometricAllowed=");
                KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(m, bool2, " deviceInteractive=", bool3, " isKeyguardShowing=");
                return KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(m, bool4, " bypass=", bool5);
            }
        };
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("BiometricUnlockLogger", logLevel, biometricUnlockLogger$logCalculateModeForFingerprintUnlockingNotAllowed$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.int1 = i;
        logMessageImpl.bool1 = z;
        logMessageImpl.bool2 = z2;
        logMessageImpl.bool3 = z3;
        logMessageImpl.bool4 = z4;
        logMessageImpl.bool5 = z5;
        logBuffer.commit(obtain);
    }

    public final void logCalculateModeForPassiveAuthUnlockingAllowed(boolean z, boolean z2, boolean z3, boolean z4) {
        LogLevel logLevel = LogLevel.DEBUG;
        BiometricUnlockLogger$logCalculateModeForPassiveAuthUnlockingAllowed$2 biometricUnlockLogger$logCalculateModeForPassiveAuthUnlockingAllowed$2 = new Function1() { // from class: com.android.keyguard.logging.BiometricUnlockLogger$logCalculateModeForPassiveAuthUnlockingAllowed$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                boolean bool1 = logMessage.getBool1();
                boolean bool2 = logMessage.getBool2();
                return KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("calculateModeForPassiveAuth unlockingAllowed=true deviceInteractive=", " isKeyguardShowing=", " deviceDreaming=", bool1, bool2), logMessage.getBool3(), " bypass=", logMessage.getBool4());
            }
        };
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("BiometricUnlockLogger", logLevel, biometricUnlockLogger$logCalculateModeForPassiveAuthUnlockingAllowed$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.bool1 = z;
        logMessageImpl.bool2 = z2;
        logMessageImpl.bool3 = z3;
        logMessageImpl.bool4 = z4;
        logBuffer.commit(obtain);
    }

    public final void logCalculateModeForPassiveAuthUnlockingNotAllowed(int i, boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
        LogLevel logLevel = LogLevel.DEBUG;
        BiometricUnlockLogger$logCalculateModeForPassiveAuthUnlockingNotAllowed$2 biometricUnlockLogger$logCalculateModeForPassiveAuthUnlockingNotAllowed$2 = new Function1() { // from class: com.android.keyguard.logging.BiometricUnlockLogger$logCalculateModeForPassiveAuthUnlockingNotAllowed$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                boolean z6 = logMessage.getInt1() == 1;
                int int2 = logMessage.getInt2();
                boolean bool1 = logMessage.getBool1();
                boolean bool2 = logMessage.getBool2();
                boolean bool3 = logMessage.getBool3();
                boolean bool4 = logMessage.getBool4();
                StringBuilder m = KeyguardFMMViewController$$ExternalSyntheticOutline0.m("calculateModeForPassiveAuth unlockingAllowed=false strongBiometric=", int2, " strongAuthFlags=", z6, " nonStrongBiometricAllowed=");
                KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(m, bool1, " deviceInteractive=", bool2, " isKeyguardShowing=");
                return KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(m, bool3, " bypass=", bool4);
            }
        };
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("BiometricUnlockLogger", logLevel, biometricUnlockLogger$logCalculateModeForPassiveAuthUnlockingNotAllowed$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.int1 = z ? 1 : 0;
        logMessageImpl.int2 = i;
        logMessageImpl.bool1 = z2;
        logMessageImpl.bool2 = z3;
        logMessageImpl.bool3 = z4;
        logMessageImpl.bool4 = z5;
        logBuffer.commit(obtain);
    }

    public final void logUdfpsAttemptThresholdMet(int i) {
        LogLevel logLevel = LogLevel.DEBUG;
        BiometricUnlockLogger$logUdfpsAttemptThresholdMet$2 biometricUnlockLogger$logUdfpsAttemptThresholdMet$2 = new Function1() { // from class: com.android.keyguard.logging.BiometricUnlockLogger$logUdfpsAttemptThresholdMet$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(((LogMessage) obj).getInt1(), "udfpsAttemptThresholdMet consecutiveFailedAttempts=");
            }
        };
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("BiometricUnlockLogger", logLevel, biometricUnlockLogger$logUdfpsAttemptThresholdMet$2, null);
        ((LogMessageImpl) obtain).int1 = i;
        logBuffer.commit(obtain);
    }
}
