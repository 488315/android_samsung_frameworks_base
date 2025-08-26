package com.android.systemui.kairos.internal;

import com.android.systemui.kairos.util.These;
import kotlin.jvm.functions.Function3;

/* loaded from: classes2.dex */
public final class MuxDeferredKt$mergeNodes$storage$1 implements Function3 {
    public static final MuxDeferredKt$mergeNodes$storage$1 INSTANCE = new MuxDeferredKt$mergeNodes$storage$1();

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        ((Number) obj3).intValue();
        These.Companion.getClass();
        return new These.First(obj2);
    }
}
