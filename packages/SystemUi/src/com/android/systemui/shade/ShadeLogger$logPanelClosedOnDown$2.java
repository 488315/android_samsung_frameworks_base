package com.android.systemui.shade;

import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
