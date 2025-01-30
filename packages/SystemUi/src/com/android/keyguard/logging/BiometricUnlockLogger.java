package com.android.keyguard.logging;

import android.hardware.biometrics.BiometricSourceType;
import android.support.v4.media.AbstractC0000x2c234b15;
import com.android.keyguard.AbstractC0662xaf167275;
import com.android.keyguard.KeyguardFMMViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardFaceListenModel$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
                StringBuilder m61m = AbstractC0662xaf167275.m61m("onBiometricAuthenticated, deferring auth: userId: ", int1, ", biometricSourceType: ", str1, ", goingToSleep: true, mPendingAuthentication != null: ");
                m61m.append(bool2);
                return m61m.toString();
            }
        };
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("BiometricUnlockLogger", logLevel, biometricUnlockLogger$deferringAuthenticationDueToSleep$2, null);
        obtain.setInt1(i);
        obtain.setStr1(biometricSourceType.name());
        obtain.setBool2(z);
        logBuffer.commit(obtain);
    }

    public final void logCalculateModeForFingerprintUnlockingAllowed(boolean z, boolean z2, boolean z3) {
        LogLevel logLevel = LogLevel.DEBUG;
        C0862x1c5d17d0 c0862x1c5d17d0 = new Function1() { // from class: com.android.keyguard.logging.BiometricUnlockLogger$logCalculateModeForFingerprintUnlockingAllowed$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                boolean bool1 = logMessage.getBool1();
                boolean bool2 = logMessage.getBool2();
                boolean bool3 = logMessage.getBool3();
                StringBuilder m69m = KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m69m("calculateModeForFingerprint unlockingAllowed=true deviceInteractive=", bool1, " isKeyguardShowing=", bool2, " deviceDreaming=");
                m69m.append(bool3);
                return m69m.toString();
            }
        };
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("BiometricUnlockLogger", logLevel, c0862x1c5d17d0, null);
        obtain.setBool1(z);
        obtain.setBool2(z2);
        obtain.setBool3(z3);
        logBuffer.commit(obtain);
    }

    public final void logCalculateModeForFingerprintUnlockingNotAllowed(int i, boolean z, boolean z2, boolean z3, boolean z4) {
        LogLevel logLevel = LogLevel.DEBUG;
        C0863xe91774c9 c0863xe91774c9 = new Function1() { // from class: com.android.keyguard.logging.BiometricUnlockLogger$logCalculateModeForFingerprintUnlockingNotAllowed$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                boolean bool1 = logMessage.getBool1();
                int int1 = logMessage.getInt1();
                boolean bool2 = logMessage.getBool2();
                boolean bool3 = logMessage.getBool3();
                boolean bool4 = logMessage.getBool4();
                StringBuilder m66m = KeyguardFMMViewController$$ExternalSyntheticOutline0.m66m("calculateModeForFingerprint unlockingAllowed=false strongBiometric=", bool1, " strongAuthFlags=", int1, " nonStrongBiometricAllowed=");
                KeyguardFaceListenModel$$ExternalSyntheticOutline0.m67m(m66m, bool2, " deviceInteractive=", bool3, " isKeyguardShowing=");
                m66m.append(bool4);
                return m66m.toString();
            }
        };
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("BiometricUnlockLogger", logLevel, c0863xe91774c9, null);
        obtain.setInt1(i);
        obtain.setBool1(z);
        obtain.setBool2(z2);
        obtain.setBool3(z3);
        obtain.setBool4(z4);
        logBuffer.commit(obtain);
    }

    public final void logCalculateModeForPassiveAuthUnlockingAllowed(boolean z, boolean z2, boolean z3, boolean z4) {
        LogLevel logLevel = LogLevel.DEBUG;
        C0864xd9c0179b c0864xd9c0179b = new Function1() { // from class: com.android.keyguard.logging.BiometricUnlockLogger$logCalculateModeForPassiveAuthUnlockingAllowed$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                boolean bool1 = logMessage.getBool1();
                boolean bool2 = logMessage.getBool2();
                return KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m74m(KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m69m("calculateModeForPassiveAuth unlockingAllowed=true deviceInteractive=", bool1, " isKeyguardShowing=", bool2, " deviceDreaming="), logMessage.getBool3(), " bypass=", logMessage.getBool4());
            }
        };
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("BiometricUnlockLogger", logLevel, c0864xd9c0179b, null);
        obtain.setBool1(z);
        obtain.setBool2(z2);
        obtain.setBool3(z3);
        obtain.setBool4(z4);
        logBuffer.commit(obtain);
    }

    public final void logCalculateModeForPassiveAuthUnlockingNotAllowed(int i, boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
        LogLevel logLevel = LogLevel.DEBUG;
        C0865xcbc5d1e c0865xcbc5d1e = new Function1() { // from class: com.android.keyguard.logging.BiometricUnlockLogger$logCalculateModeForPassiveAuthUnlockingNotAllowed$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                boolean z6 = logMessage.getInt1() == 1;
                int int2 = logMessage.getInt2();
                boolean bool1 = logMessage.getBool1();
                boolean bool2 = logMessage.getBool2();
                boolean bool3 = logMessage.getBool3();
                boolean bool4 = logMessage.getBool4();
                StringBuilder m66m = KeyguardFMMViewController$$ExternalSyntheticOutline0.m66m("calculateModeForPassiveAuth unlockingAllowed=false strongBiometric=", z6, " strongAuthFlags=", int2, " nonStrongBiometricAllowed=");
                KeyguardFaceListenModel$$ExternalSyntheticOutline0.m67m(m66m, bool1, " deviceInteractive=", bool2, " isKeyguardShowing=");
                return KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m74m(m66m, bool3, " bypass=", bool4);
            }
        };
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("BiometricUnlockLogger", logLevel, c0865xcbc5d1e, null);
        obtain.setInt1(z ? 1 : 0);
        obtain.setInt2(i);
        obtain.setBool1(z2);
        obtain.setBool2(z3);
        obtain.setBool3(z4);
        obtain.setBool4(z5);
        logBuffer.commit(obtain);
    }

    public final void logUdfpsAttemptThresholdMet(int i) {
        LogLevel logLevel = LogLevel.DEBUG;
        BiometricUnlockLogger$logUdfpsAttemptThresholdMet$2 biometricUnlockLogger$logUdfpsAttemptThresholdMet$2 = new Function1() { // from class: com.android.keyguard.logging.BiometricUnlockLogger$logUdfpsAttemptThresholdMet$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return AbstractC0000x2c234b15.m0m("udfpsAttemptThresholdMet consecutiveFailedAttempts=", ((LogMessage) obj).getInt1());
            }
        };
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("BiometricUnlockLogger", logLevel, biometricUnlockLogger$logUdfpsAttemptThresholdMet$2, null);
        obtain.setInt1(i);
        logBuffer.commit(obtain);
    }
}
