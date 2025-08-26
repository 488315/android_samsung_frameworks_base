package com.android.systemui.kairos.internal;

import com.android.systemui.kairos.internal.store.MapK;
import com.android.systemui.kairos.internal.store.Single;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function3;

/* loaded from: classes2.dex */
public final class MuxPromptKt$switchPromptImplSingle$2 implements Function3 {
    public static final MuxPromptKt$switchPromptImplSingle$2 INSTANCE = new MuxPromptKt$switchPromptImplSingle$2();

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        ((Number) obj3).intValue();
        return ((PullNode) MapsKt__MapsKt.getValue(Unit.INSTANCE, (Single) ((MapK) obj2))).getPushEvent((EvalScope) obj);
    }
}
