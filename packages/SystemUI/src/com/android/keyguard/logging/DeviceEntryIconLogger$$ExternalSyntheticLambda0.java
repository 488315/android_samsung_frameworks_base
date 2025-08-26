package com.android.keyguard.logging;

import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public final /* synthetic */ class DeviceEntryIconLogger$$ExternalSyntheticLambda0 implements Function1 {
    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        boolean bool4 = logMessage.getBool4();
        boolean bool1 = logMessage.getBool1();
        return KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(EmergencyButtonController$$ExternalSyntheticOutline0.m("shouldHandleTouches=", " canTouchDeviceEntryViewAlpha=", " alternateBouncerVisible=", bool4, bool1), logMessage.getBool2(), " hideAffordancesRequest=", logMessage.getBool3());
    }
}
