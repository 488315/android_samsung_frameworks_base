package com.android.systemui.doze;

import androidx.core.provider.FontProvider$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.policy.DevicePostureController;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

final class DozeLogger$logPostureChanged$2 extends Lambda implements Function1 {
    public static final DozeLogger$logPostureChanged$2 INSTANCE = new DozeLogger$logPostureChanged$2();

    public DozeLogger$logPostureChanged$2() {
        super(1);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        return FontProvider$$ExternalSyntheticOutline0.m("Posture changed, posture=", DevicePostureController.devicePostureToString(logMessage.getInt1()), " partUpdated=", logMessage.getStr1());
    }
}
