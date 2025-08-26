package com.android.systemui.bouncer.domain.interactor;

import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public final /* synthetic */ class BouncerInteractor$$ExternalSyntheticLambda0 implements Function1 {
    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        float fFloatValue = ((Float) obj).floatValue();
        int i = BouncerInteractor.$r8$clinit;
        return Integer.valueOf((int) (fFloatValue * 100.0f));
    }
}
