package com.android.systemui.statusbar.events;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final /* synthetic */ class MultiDisplaySystemEventChipAnimationController$$ExternalSyntheticLambda0 implements Function1 {
    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        ((SystemEventChipAnimationController) obj).stop();
        return Unit.INSTANCE;
    }
}
