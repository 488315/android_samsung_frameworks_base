package com.android.systemui.shade;

import androidx.compose.runtime.external.kotlinx.collections.immutable.internal.ListImplementation$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class ShadeStateTraceLogger$start$1$5$1$$ExternalSyntheticLambda0 implements Function1 {
    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        return ListImplementation$$ExternalSyntheticOutline0.m(logMessage.getInt1(), logMessage.getInt2(), "New configuration change from Shade window. smallestScreenWidthDp: ", ", densityDpi: ");
    }
}
