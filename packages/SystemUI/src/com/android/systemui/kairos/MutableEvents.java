package com.android.systemui.kairos;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.systemui.kairos.internal.InputNode;
import com.android.systemui.kairos.internal.Network;
import com.android.systemui.kairos.internal.util.UtilKt;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.internal.Reflection;
import kotlinx.coroutines.CoroutineScopeKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class MutableEvents extends Events {
    public final InputNode impl;
    public final Network network;
    public final AtomicReference storage;

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ MutableEvents(com.android.systemui.kairos.internal.Network r1, com.android.systemui.kairos.internal.InputNode r2, int r3, kotlin.jvm.internal.DefaultConstructorMarker r4) {
        /*
            r0 = this;
            r3 = r3 & 2
            if (r3 == 0) goto Lb
            com.android.systemui.kairos.internal.InputNode r2 = new com.android.systemui.kairos.internal.InputNode
            r3 = 3
            r4 = 0
            r2.<init>(r4, r4, r3, r4)
        Lb:
            r0.<init>(r1, r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.kairos.MutableEvents.<init>(com.android.systemui.kairos.internal.Network, com.android.systemui.kairos.internal.InputNode, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    public final Object emit(Object obj, Continuation continuation) {
        Object coroutineScope = CoroutineScopeKt.coroutineScope(new MutableEvents$emit$2(this, obj, null), continuation);
        return coroutineScope == CoroutineSingletons.COROUTINE_SUSPENDED ? coroutineScope : Unit.INSTANCE;
    }

    public final String toString() {
        return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(Reflection.getOrCreateKotlinClass(MutableEvents.class).getSimpleName(), "@", UtilKt.getHashString(this));
    }

    public MutableEvents(Network network, InputNode inputNode) {
        super(null);
        this.network = network;
        this.impl = inputNode;
        this.storage = new AtomicReference(null);
    }
}
