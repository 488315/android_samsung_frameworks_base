package com.android.systemui.kairos.internal;

import kotlin.Pair;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class Init {
    public final Function1 block;
    public final CompletableLazy cache = new CompletableLazy(null, 0 == true ? 1 : 0, 3, 0 == true ? 1 : 0);

    public Init(String str, Function1 function1) {
        this.block = function1;
    }

    public final Object connect(NetworkScope networkScope) {
        CompletableLazy completableLazy = this.cache;
        if (!completableLazy.isInitialized()) {
            Object mo779invoke = this.block.mo779invoke(networkScope);
            completableLazy.setValue(new Pair(networkScope.getNetworkId(), mo779invoke));
            return mo779invoke;
        }
        Pair pair = (Pair) completableLazy.getValue();
        Object component1 = pair.component1();
        Object component2 = pair.component2();
        if (Intrinsics.areEqual(component1, networkScope.getNetworkId())) {
            return component2;
        }
        throw new IllegalStateException("Network mismatch");
    }
}
