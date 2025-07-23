package com.android.systemui.statusbar.policy;

import androidx.constraintlayout.widget.ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import com.samsung.android.sdk.scs.ai.visual.c2pa.C2paManifestList;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class DeviceStateRotationLockSettingControllerLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DeviceStateRotationLockSettingControllerLogger f$0;

    public /* synthetic */ DeviceStateRotationLockSettingControllerLogger$$ExternalSyntheticLambda0(DeviceStateRotationLockSettingControllerLogger deviceStateRotationLockSettingControllerLogger, int i) {
        this.$r8$classId = i;
        this.f$0 = deviceStateRotationLockSettingControllerLogger;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                return KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "onRotationLockStateChanged: state=", " [", this.f$0.toDevicePostureString(logMessage.getInt1()), "], newRotationLocked="), logMessage.getBool1(), ", currentRotationLocked=", logMessage.getBool2());
            case 1:
                String str1 = logMessage.getStr1();
                int int1 = logMessage.getInt1();
                String devicePostureString = this.f$0.toDevicePostureString(logMessage.getInt1());
                int int2 = logMessage.getInt2();
                String str = int2 != 0 ? int2 != 1 ? int2 != 2 ? C2paManifestList.UNKNOWN_VALUE : "UNLOCKED" : "LOCKED" : "IGNORED";
                boolean bool1 = logMessage.getBool1();
                boolean bool2 = logMessage.getBool2();
                StringBuilder m888m = ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m888m(int1, "readPersistedSetting: caller=", str1, ", state=", " [");
                MoveResult$$ExternalSyntheticOutline0.m(m888m, devicePostureString, "], rotationLockSettingForState: ", str, ", shouldBeLocked=");
                return KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(m888m, bool1, ", isLocked=", bool2);
            default:
                int int12 = logMessage.getInt1();
                int int13 = logMessage.getInt1();
                DeviceStateRotationLockSettingControllerLogger deviceStateRotationLockSettingControllerLogger = this.f$0;
                String devicePostureString2 = deviceStateRotationLockSettingControllerLogger.toDevicePostureString(int13);
                int int22 = logMessage.getInt2();
                String devicePostureString3 = deviceStateRotationLockSettingControllerLogger.toDevicePostureString(logMessage.getInt2());
                StringBuilder m = KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0.m(int12, "updateDeviceState: current=", " [", devicePostureString2, "], new=");
                m.append(int22);
                m.append(" [");
                m.append(devicePostureString3);
                m.append("]");
                return m.toString();
        }
    }
}
