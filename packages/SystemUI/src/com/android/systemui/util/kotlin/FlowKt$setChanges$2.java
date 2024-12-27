package com.android.systemui.util.kotlin;

import java.util.Set;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;

final /* synthetic */ class FlowKt$setChanges$2 extends AdaptedFunctionReference implements Function3 {
    public static final FlowKt$setChanges$2 INSTANCE = new FlowKt$setChanges$2();

    public FlowKt$setChanges$2() {
        super(3, SetChanges.class, "<init>", "<init>(Ljava/util/Set;Ljava/util/Set;)V", 4);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Set<Object> set, Set<Object> set2, Continuation continuation) {
        Object changes$lambda$4;
        changes$lambda$4 = FlowKt.setChanges$lambda$4(set, set2, continuation);
        return changes$lambda$4;
    }
}
