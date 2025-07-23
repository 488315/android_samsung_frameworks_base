package com.android.systemui.kairos.internal;

import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.collections.ArrayDeque;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class DeferScopeKt$deferScope$scope$1 implements DeferScope {
    public final ArrayDeque deferrals = new ArrayDeque();

    @Override // com.android.systemui.kairos.internal.DeferScope
    public final void deferAction(Function0 function0) {
        this.deferrals.addLast(function0);
    }

    @Override // com.android.systemui.kairos.internal.DeferScope
    public final Lazy deferAsync(Function0 function0) {
        final Lazy lazy = LazyKt__LazyJVMKt.lazy(function0);
        this.deferrals.addLast(new Function0() { // from class: com.android.systemui.kairos.internal.DeferScopeKt$deferScope$scope$1$deferAsync$1$1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Lazy.this.getValue();
                return Unit.INSTANCE;
            }
        });
        return lazy;
    }

    public final void drainDeferrals() {
        while (true) {
            ArrayDeque arrayDeque = this.deferrals;
            if (arrayDeque.isEmpty()) {
                return;
            } else {
                ((Function0) arrayDeque.removeFirst()).invoke();
            }
        }
    }
}
