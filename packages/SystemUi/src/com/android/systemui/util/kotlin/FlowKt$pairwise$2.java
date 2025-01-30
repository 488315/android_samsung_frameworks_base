package com.android.systemui.util.kotlin;

import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
final /* synthetic */ class FlowKt$pairwise$2 extends AdaptedFunctionReference implements Function3 {
    public static final FlowKt$pairwise$2 INSTANCE = new FlowKt$pairwise$2();

    public FlowKt$pairwise$2() {
        super(3, WithPrev.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        return new WithPrev(obj, obj2);
    }
}
