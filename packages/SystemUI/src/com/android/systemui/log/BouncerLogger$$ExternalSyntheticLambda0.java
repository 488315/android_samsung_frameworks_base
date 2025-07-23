package com.android.systemui.log;

import com.android.keyguard.KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0;
import com.android.systemui.globalactions.presentation.features.FakeFeatures$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class BouncerLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                return FakeFeatures$$ExternalSyntheticOutline0.m("state changed: ", logMessage.getStr1(), ": ", logMessage.getBool1());
            default:
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
    }
}
