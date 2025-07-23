package com.android.systemui.kairos;

import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.systemui.kairos.internal.CompletableLazy;
import com.android.systemui.kairos.internal.InputNode;
import com.android.systemui.kairos.internal.Network;
import com.android.systemui.kairos.internal.util.UtilKt;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.UnaryOperator;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Pair;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Reflection;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class CoalescingMutableEvents extends Events {
    public final Function2 coalesce;
    public final Function0 getInitialValue;
    public final InputNode impl;
    public final String name;
    public final Network network;
    public final AtomicReference storage;

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ CoalescingMutableEvents(java.lang.String r7, kotlin.jvm.functions.Function2 r8, com.android.systemui.kairos.internal.Network r9, kotlin.jvm.functions.Function0 r10, com.android.systemui.kairos.internal.InputNode r11, int r12, kotlin.jvm.internal.DefaultConstructorMarker r13) {
        /*
            r6 = this;
            r12 = r12 & 16
            if (r12 == 0) goto Lb
            com.android.systemui.kairos.internal.InputNode r11 = new com.android.systemui.kairos.internal.InputNode
            r12 = 3
            r13 = 0
            r11.<init>(r13, r13, r12, r13)
        Lb:
            r0 = r6
            r1 = r7
            r2 = r8
            r3 = r9
            r4 = r10
            r5 = r11
            r0.<init>(r1, r2, r3, r4, r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.kairos.CoalescingMutableEvents.<init>(java.lang.String, kotlin.jvm.functions.Function2, com.android.systemui.kairos.internal.Network, kotlin.jvm.functions.Function0, com.android.systemui.kairos.internal.InputNode, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    public final void emit(final Object obj) {
        if (((Boolean) ((Pair) this.storage.getAndUpdate(new UnaryOperator() { // from class: com.android.systemui.kairos.CoalescingMutableEvents$emit$1
            @Override // java.util.function.Function
            public final Object apply(Object obj2) {
                return new Pair(Boolean.TRUE, new CompletableLazy(CoalescingMutableEvents.this.coalesce.invoke((Lazy) ((Pair) obj2).component2(), obj), null, 2, null));
            }
        })).component1()).booleanValue()) {
            return;
        }
        String str = this.name;
        String m = str != null ? ContentInViewNode$Request$$ExternalSyntheticOutline0.m("(", str, ")") : null;
        if (m == null) {
            m = "";
        }
        this.network.transaction(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("CoalescingMutableEvents", m, ".emit"), new CoalescingMutableEvents$emit$3(this, null));
    }

    public final String toString() {
        return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(Reflection.getOrCreateKotlinClass(CoalescingMutableEvents.class).getSimpleName(), "@", UtilKt.getHashString(this));
    }

    public CoalescingMutableEvents(String str, Function2 function2, Network network, Function0 function0, InputNode inputNode) {
        super(null);
        this.name = str;
        this.coalesce = function2;
        this.network = network;
        this.getInitialValue = function0;
        this.impl = inputNode;
        this.storage = new AtomicReference(new Pair(Boolean.FALSE, LazyKt__LazyJVMKt.lazy(new CoalescingMutableEvents$$ExternalSyntheticLambda0(this, 0))));
    }
}
