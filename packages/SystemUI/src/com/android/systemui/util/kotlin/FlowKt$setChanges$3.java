package com.android.systemui.util.kotlin;

import java.util.Set;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final /* synthetic */ class FlowKt$setChanges$3 extends AdaptedFunctionReference implements Function3 {
    public static final FlowKt$setChanges$3 INSTANCE = new FlowKt$setChanges$3();

    public FlowKt$setChanges$3() {
        super(3, SetChanges.class, "<init>", "<init>(Ljava/util/Set;Ljava/util/Set;)V", 4);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Set<Object> set, Set<Object> set2, Continuation continuation) {
        Object changes$lambda$4;
        changes$lambda$4 = FlowKt.setChanges$lambda$4(set, set2, continuation);
        return changes$lambda$4;
    }
}
