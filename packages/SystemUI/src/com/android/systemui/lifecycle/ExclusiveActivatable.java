package com.android.systemui.lifecycle;

import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.coroutines.Continuation;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class ExclusiveActivatable implements Activatable {
    public static final int $stable = 8;
    private final AtomicBoolean _isActive = new AtomicBoolean(false);

    /* JADX WARN: Removed duplicated region for block: B:18:0x0036  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0022  */
    @Override // com.android.systemui.lifecycle.Activatable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object activate(kotlin.coroutines.Continuation r6) {
        /*
            r5 = this;
            boolean r0 = r6 instanceof com.android.systemui.lifecycle.ExclusiveActivatable$activate$1
            if (r0 == 0) goto L13
            r0 = r6
            com.android.systemui.lifecycle.ExclusiveActivatable$activate$1 r0 = (com.android.systemui.lifecycle.ExclusiveActivatable$activate$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.lifecycle.ExclusiveActivatable$activate$1 r0 = new com.android.systemui.lifecycle.ExclusiveActivatable$activate$1
            r0.<init>(r5, r6)
        L18:
            java.lang.Object r6 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 0
            r4 = 1
            if (r2 == 0) goto L36
            if (r2 == r4) goto L2c
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L2c:
            java.lang.Object r5 = r0.L$0
            com.android.systemui.lifecycle.ExclusiveActivatable r5 = (com.android.systemui.lifecycle.ExclusiveActivatable) r5
            kotlin.ResultKt.throwOnFailure(r6)     // Catch: java.lang.Throwable -> L34
            goto L4c
        L34:
            r6 = move-exception
            goto L52
        L36:
            kotlin.ResultKt.throwOnFailure(r6)
            java.util.concurrent.atomic.AtomicBoolean r6 = r5._isActive
            boolean r6 = r6.compareAndSet(r3, r4)
            if (r6 == 0) goto L58
            r0.L$0 = r5     // Catch: java.lang.Throwable -> L34
            r0.label = r4     // Catch: java.lang.Throwable -> L34
            java.lang.Object r6 = r5.onActivated(r0)     // Catch: java.lang.Throwable -> L34
            if (r6 != r1) goto L4c
            return r1
        L4c:
            kotlin.KotlinNothingValueException r6 = new kotlin.KotlinNothingValueException     // Catch: java.lang.Throwable -> L34
            r6.<init>()     // Catch: java.lang.Throwable -> L34
            throw r6     // Catch: java.lang.Throwable -> L34
        L52:
            java.util.concurrent.atomic.AtomicBoolean r5 = r5._isActive
            r5.set(r3)
            throw r6
        L58:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "Cannot activate an already active ExclusiveActivatable!"
            r5.<init>(r6)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.lifecycle.ExclusiveActivatable.activate(kotlin.coroutines.Continuation):java.lang.Object");
    }

    public final boolean isActive() {
        return this._isActive.get();
    }

    public abstract Object onActivated(Continuation continuation);
}
