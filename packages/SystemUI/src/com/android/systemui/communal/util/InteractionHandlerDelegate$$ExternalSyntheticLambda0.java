package com.android.systemui.communal.util;

import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class InteractionHandlerDelegate$$ExternalSyntheticLambda0 implements Function1 {
    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        return MotionLayout$$ExternalSyntheticOutline0.m("Starting ", logMessage.getStr1(), " (", logMessage.getStr2(), ")");
    }
}
