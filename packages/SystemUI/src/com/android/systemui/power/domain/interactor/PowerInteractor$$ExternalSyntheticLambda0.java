package com.android.systemui.power.domain.interactor;

import kotlin.jvm.functions.Function2;

/* loaded from: classes2.dex */
public final /* synthetic */ class PowerInteractor$$ExternalSyntheticLambda0 implements Function2 {
    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        boolean zBooleanValue = ((Boolean) obj).booleanValue();
        boolean zBooleanValue2 = ((Boolean) obj2).booleanValue();
        PowerInteractor$$ExternalSyntheticLambda0 powerInteractor$$ExternalSyntheticLambda0 = PowerInteractor.checkEquivalentUnlessEmitDuplicatesUnderTest;
        return Boolean.valueOf(zBooleanValue == zBooleanValue2);
    }
}
