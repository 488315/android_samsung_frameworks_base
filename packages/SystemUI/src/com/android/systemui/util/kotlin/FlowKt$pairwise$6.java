package com.android.systemui.util.kotlin;

import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final /* synthetic */ class FlowKt$pairwise$6 extends AdaptedFunctionReference implements Function3 {
    public static final FlowKt$pairwise$6 INSTANCE = new FlowKt$pairwise$6();

    public FlowKt$pairwise$6() {
        super(3, WithPrev.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Continuation continuation) {
        Object pairwise$lambda$1;
        pairwise$lambda$1 = FlowKt.pairwise$lambda$1(obj, obj2, continuation);
        return pairwise$lambda$1;
    }
}
