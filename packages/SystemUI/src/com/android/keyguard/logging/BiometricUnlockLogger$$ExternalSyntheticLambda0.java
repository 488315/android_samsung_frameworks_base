package com.android.keyguard.logging;

import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardFMMViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public final /* synthetic */ class BiometricUnlockLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ BiometricUnlockLogger$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                boolean bool1 = logMessage.getBool1();
                int int1 = logMessage.getInt1();
                boolean bool2 = logMessage.getBool2();
                boolean bool3 = logMessage.getBool3();
                boolean bool4 = logMessage.getBool4();
                boolean bool5 = logMessage.getBool5();
                StringBuilder sbM = KeyguardFMMViewController$$ExternalSyntheticOutline0.m("calculateModeForFingerprint unlockingAllowed=false strongBiometric=", int1, " strongAuthFlags=", bool1, " nonStrongBiometricAllowed=");
                KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sbM, bool2, " deviceInteractive=", bool3, " isKeyguardShowing=");
                return KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sbM, bool4, " bypass=", bool5);
            case 1:
                return KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(EmergencyButtonController$$ExternalSyntheticOutline0.m("calculateModeForPassiveAuth unlockingAllowed=true deviceInteractive=", " isKeyguardShowing=", " deviceDreaming=", logMessage.getBool1(), logMessage.getBool2()), logMessage.getBool3(), " bypass=", logMessage.getBool4());
            case 2:
                boolean z = logMessage.getInt1() == 1;
                int int2 = logMessage.getInt2();
                boolean bool12 = logMessage.getBool1();
                boolean bool22 = logMessage.getBool2();
                boolean bool32 = logMessage.getBool3();
                boolean bool42 = logMessage.getBool4();
                StringBuilder sbM2 = KeyguardFMMViewController$$ExternalSyntheticOutline0.m("calculateModeForPassiveAuth unlockingAllowed=false strongBiometric=", int2, " strongAuthFlags=", z, " nonStrongBiometricAllowed=");
                KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sbM2, bool12, " deviceInteractive=", bool22, " isKeyguardShowing=");
                return KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sbM2, bool32, " bypass=", bool42);
            case 3:
                return KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(EmergencyButtonController$$ExternalSyntheticOutline0.m("calculateModeForFingerprint unlockingAllowed=true deviceInteractive=", " isKeyguardShowing=", " deviceDreaming=", logMessage.getBool1(), logMessage.getBool2()), logMessage.getBool3(), " bypass=", logMessage.getBool4());
            default:
                int int12 = logMessage.getInt1();
                String str1 = logMessage.getStr1();
                boolean bool23 = logMessage.getBool2();
                StringBuilder sbM3 = KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0.m(int12, "onBiometricAuthenticated, deferring auth: userId: ", ", biometricSourceType: ", str1, ", goingToSleep: true, mPendingAuthentication != null: ");
                sbM3.append(bool23);
                return sbM3.toString();
        }
    }
}
