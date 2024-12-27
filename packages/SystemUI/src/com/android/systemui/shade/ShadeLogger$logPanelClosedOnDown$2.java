package com.android.systemui.shade;

import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

final class ShadeLogger$logPanelClosedOnDown$2 extends Lambda implements Function1 {
    public static final ShadeLogger$logPanelClosedOnDown$2 INSTANCE = new ShadeLogger$logPanelClosedOnDown$2();

    public ShadeLogger$logPanelClosedOnDown$2() {
        super(1);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        return logMessage.getStr1() + "; mPanelClosedOnDown=" + logMessage.getBool1() + "; mExpandedFraction=" + logMessage.getDouble1();
    }
}
