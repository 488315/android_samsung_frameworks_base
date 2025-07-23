package com.android.systemui.deviceentry.domain.interactor;

import kotlin.Triple;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class DeviceEntrySourceInteractor$$ExternalSyntheticLambda0 implements Function1 {
    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        Triple triple = (Triple) obj;
        return Boolean.valueOf(((Boolean) triple.component1()).booleanValue() || !((Boolean) triple.component2()).booleanValue() || ((Boolean) triple.component3()).booleanValue());
    }
}
