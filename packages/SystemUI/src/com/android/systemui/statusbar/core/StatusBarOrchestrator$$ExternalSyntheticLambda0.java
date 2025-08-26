package com.android.systemui.statusbar.core;

import com.android.systemui.statusbar.data.model.StatusBarMode;
import com.android.systemui.statusbar.phone.PhoneStatusBarTransitions;
import kotlin.Pair;
import kotlin.Triple;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final /* synthetic */ class StatusBarOrchestrator$$ExternalSyntheticLambda0 implements Function1 {
    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        Triple triple = (Triple) obj;
        return new Pair((PhoneStatusBarTransitions) triple.component2(), (StatusBarMode) triple.component3());
    }
}
