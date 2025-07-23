package com.android.systemui.shared.animation;

import android.view.View;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class UnfoldConstantTranslateAnimator$ViewIdToTranslate$$ExternalSyntheticLambda0 implements Function2 {
    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        ((View) obj).setTranslationX(((Float) obj2).floatValue());
        return Unit.INSTANCE;
    }
}
