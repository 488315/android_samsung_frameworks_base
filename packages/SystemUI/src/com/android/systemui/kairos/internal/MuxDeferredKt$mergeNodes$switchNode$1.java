package com.android.systemui.kairos.internal;

import java.util.Map;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public final class MuxDeferredKt$mergeNodes$switchNode$1 implements Function1 {
    public final /* synthetic */ Iterable $storage;

    public MuxDeferredKt$mergeNodes$switchNode$1(Iterable<? extends Map.Entry<Integer, ? extends EventsImpl>> iterable) {
        this.$storage = iterable;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        return this.$storage;
    }
}
