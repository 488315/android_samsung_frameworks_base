package com.android.systemui.kairos;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.systemui.kairos.internal.util.UtilKt;
import kotlin.jvm.internal.Reflection;

/* loaded from: classes2.dex */
public final class Transactional {
    public final State impl;

    public Transactional(State state) {
        this.impl = state;
    }

    public final String toString() {
        return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(Reflection.getOrCreateKotlinClass(Transactional.class).getSimpleName(), "@", UtilKt.getHashString(this));
    }
}
