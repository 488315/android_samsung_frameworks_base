package com.android.systemui.bouncer.domain.interactor;

import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class BouncerInteractor$$ExternalSyntheticLambda0 implements Function1 {
    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        float floatValue = ((Float) obj).floatValue();
        int i = BouncerInteractor.$r8$clinit;
        return Integer.valueOf((int) (floatValue * 100.0f));
    }
}
