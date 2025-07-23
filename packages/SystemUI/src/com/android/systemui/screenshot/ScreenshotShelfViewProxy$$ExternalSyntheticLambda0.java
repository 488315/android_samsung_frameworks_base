package com.android.systemui.screenshot;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class ScreenshotShelfViewProxy$$ExternalSyntheticLambda0 implements Function2 {
    public final /* synthetic */ ScreenshotShelfViewProxy f$0;

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        this.f$0.requestDismissal((ScreenshotEvent) obj, (Float) obj2);
        return Unit.INSTANCE;
    }
}
