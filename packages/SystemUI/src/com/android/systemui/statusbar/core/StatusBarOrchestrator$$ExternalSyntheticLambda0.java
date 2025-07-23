package com.android.systemui.statusbar.core;

import com.android.systemui.statusbar.data.model.StatusBarMode;
import com.android.systemui.statusbar.phone.PhoneStatusBarTransitions;
import kotlin.Pair;
import kotlin.Triple;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class StatusBarOrchestrator$$ExternalSyntheticLambda0 implements Function1 {
    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        Triple triple = (Triple) obj;
        return new Pair((PhoneStatusBarTransitions) triple.component2(), (StatusBarMode) triple.component3());
    }
}
