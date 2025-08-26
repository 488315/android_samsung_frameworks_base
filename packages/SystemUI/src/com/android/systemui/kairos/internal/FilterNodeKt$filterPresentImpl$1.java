package com.android.systemui.kairos.internal;

import com.android.systemui.kairos.internal.store.Single;
import com.android.systemui.kairos.util.Maybe;
import kotlin.jvm.functions.Function3;

/* loaded from: classes2.dex */
public final class FilterNodeKt$filterPresentImpl$1 implements Function3 {
    public static final FilterNodeKt$filterPresentImpl$1 INSTANCE = new FilterNodeKt$filterPresentImpl$1();

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        Maybe maybe = (Maybe) obj2;
        ((Number) obj3).intValue();
        return maybe instanceof Maybe.Present ? new Single(((Maybe.Present) maybe).value) : new Single();
    }
}
