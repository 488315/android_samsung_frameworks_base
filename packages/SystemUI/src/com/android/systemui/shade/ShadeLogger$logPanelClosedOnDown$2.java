package com.android.systemui.shade;

import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
