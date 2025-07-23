package com.android.systemui.util.kotlin;

import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final /* synthetic */ class FlowKt$pairwise$3 extends AdaptedFunctionReference implements Function3 {
    public static final FlowKt$pairwise$3 INSTANCE = new FlowKt$pairwise$3();

    public FlowKt$pairwise$3() {
        super(3, WithPrev.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Continuation continuation) {
        Object pairwise$lambda$0;
        pairwise$lambda$0 = FlowKt.pairwise$lambda$0(obj, obj2, continuation);
        return pairwise$lambda$0;
    }
}
