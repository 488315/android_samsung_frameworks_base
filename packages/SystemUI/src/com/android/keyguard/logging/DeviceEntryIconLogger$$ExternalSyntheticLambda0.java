package com.android.keyguard.logging;

import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class DeviceEntryIconLogger$$ExternalSyntheticLambda0 implements Function1 {
    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        boolean bool4 = logMessage.getBool4();
        boolean bool1 = logMessage.getBool1();
        return KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(EmergencyButtonController$$ExternalSyntheticOutline0.m("shouldHandleTouches=", " canTouchDeviceEntryViewAlpha=", " alternateBouncerVisible=", bool4, bool1), logMessage.getBool2(), " hideAffordancesRequest=", logMessage.getBool3());
    }
}
