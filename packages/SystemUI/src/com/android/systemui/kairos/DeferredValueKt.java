package com.android.systemui.kairos;

import com.android.systemui.kairos.internal.CompletableLazy;

/* loaded from: classes2.dex */
public abstract class DeferredValueKt {
    public static final DeferredValue deferredOf(Object obj) {
        return new DeferredValue(new CompletableLazy(obj, null, 2, null));
    }
}
