package com.android.systemui.kairos.internal;

import com.android.systemui.kairos.internal.store.Single;
import com.android.systemui.kairos.util.Maybe;
import kotlin.jvm.functions.Function3;

/* loaded from: classes2.dex */
public final class MuxPromptKt$switchPromptImplSingle$switchPromptImpl$2$1 implements Function3 {
    public static final MuxPromptKt$switchPromptImplSingle$switchPromptImpl$2$1 INSTANCE = new MuxPromptKt$switchPromptImplSingle$switchPromptImpl$2$1();

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        ((Number) obj3).intValue();
        Maybe.Companion.getClass();
        return new Single(Maybe.Present.m2588boximpl((EventsImpl) obj2)).getEntries();
    }
}
