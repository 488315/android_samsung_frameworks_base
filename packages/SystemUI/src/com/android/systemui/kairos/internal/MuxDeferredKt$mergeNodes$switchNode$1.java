package com.android.systemui.kairos.internal;

import java.util.Map;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class MuxDeferredKt$mergeNodes$switchNode$1 implements Function1 {
    public final /* synthetic */ Iterable $storage;

    public MuxDeferredKt$mergeNodes$switchNode$1(Iterable<? extends Map.Entry<Integer, ? extends EventsImpl>> iterable) {
        this.$storage = iterable;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        return this.$storage;
    }
}
