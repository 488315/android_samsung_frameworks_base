package com.android.systemui.power.domain.interactor;

import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class PowerInteractor$$ExternalSyntheticLambda0 implements Function2 {
    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        boolean booleanValue2 = ((Boolean) obj2).booleanValue();
        PowerInteractor$$ExternalSyntheticLambda0 powerInteractor$$ExternalSyntheticLambda0 = PowerInteractor.checkEquivalentUnlessEmitDuplicatesUnderTest;
        return Boolean.valueOf(booleanValue == booleanValue2);
    }
}
