package com.android.systemui.kairos.internal;

import kotlin.Pair;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class Init {
    public final Function1 block;
    public final CompletableLazy cache = new CompletableLazy(null, 0 == true ? 1 : 0, 3, 0 == true ? 1 : 0);

    /* JADX WARN: Multi-variable type inference failed */
    public Init(String str, Function1 function1) {
        this.block = function1;
    }

    public final Object connect(NetworkScope networkScope) {
        CompletableLazy completableLazy = this.cache;
        if (!completableLazy.isInitialized()) {
            Object objMo781invoke = this.block.mo781invoke(networkScope);
            completableLazy.setValue(new Pair(networkScope.getNetworkId(), objMo781invoke));
            return objMo781invoke;
        }
        Pair pair = (Pair) completableLazy.getValue();
        Object objComponent1 = pair.component1();
        Object objComponent2 = pair.component2();
        if (Intrinsics.areEqual(objComponent1, networkScope.getNetworkId())) {
            return objComponent2;
        }
        throw new IllegalStateException("Network mismatch");
    }
}
