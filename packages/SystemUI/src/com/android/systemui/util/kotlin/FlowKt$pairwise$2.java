package com.android.systemui.util.kotlin;

import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
final /* synthetic */ class FlowKt$pairwise$2 extends AdaptedFunctionReference implements Function3 {
    public static final FlowKt$pairwise$2 INSTANCE = new FlowKt$pairwise$2();

    public FlowKt$pairwise$2() {
        super(3, WithPrev.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Continuation continuation) {
        Object pairwise$lambda$0;
        pairwise$lambda$0 = FlowKt.pairwise$lambda$0(obj, obj2, continuation);
        return pairwise$lambda$0;
    }
}
