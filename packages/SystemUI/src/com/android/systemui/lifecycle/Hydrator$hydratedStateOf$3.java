package com.android.systemui.lifecycle;

import androidx.compose.runtime.MutableState;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class Hydrator$hydratedStateOf$3 extends ExclusiveActivatable {
    public final /* synthetic */ MutableState $mutableState;
    public final /* synthetic */ Flow $source;
    public final /* synthetic */ String $traceName;
    public final /* synthetic */ Hydrator this$0;

    public Hydrator$hydratedStateOf$3(Flow flow, String str, MutableState<Object> mutableState, Hydrator hydrator) {
        this.$source = flow;
        this.$traceName = str;
        this.$mutableState = mutableState;
        this.this$0 = hydrator;
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0055, code lost:
    
        if (kotlinx.coroutines.DelayKt.awaitCancellation(r0) != r1) goto L22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0057, code lost:
    
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x004c, code lost:
    
        if (r7.$source.collect(r8, r0) == r1) goto L21;
     */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0036  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    @Override // com.android.systemui.lifecycle.ExclusiveActivatable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object onActivated(kotlin.coroutines.Continuation r8) {
        /*
            r7 = this;
            boolean r0 = r8 instanceof com.android.systemui.lifecycle.Hydrator$hydratedStateOf$3$onActivated$1
            if (r0 == 0) goto L13
            r0 = r8
            com.android.systemui.lifecycle.Hydrator$hydratedStateOf$3$onActivated$1 r0 = (com.android.systemui.lifecycle.Hydrator$hydratedStateOf$3$onActivated$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.lifecycle.Hydrator$hydratedStateOf$3$onActivated$1 r0 = new com.android.systemui.lifecycle.Hydrator$hydratedStateOf$3$onActivated$1
            r0.<init>(r7, r8)
        L18:
            java.lang.Object r8 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 2
            r4 = 1
            if (r2 == 0) goto L36
            if (r2 == r4) goto L32
            if (r2 == r3) goto L2e
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L2e:
            kotlin.ResultKt.throwOnFailure(r8)
            goto L58
        L32:
            kotlin.ResultKt.throwOnFailure(r8)
            goto L4f
        L36:
            kotlin.ResultKt.throwOnFailure(r8)
            com.android.systemui.lifecycle.Hydrator$hydratedStateOf$3$onActivated$2 r8 = new com.android.systemui.lifecycle.Hydrator$hydratedStateOf$3$onActivated$2
            com.android.systemui.lifecycle.Hydrator r2 = r7.this$0
            java.lang.String r5 = r7.$traceName
            androidx.compose.runtime.MutableState r6 = r7.$mutableState
            r8.<init>()
            r0.label = r4
            kotlinx.coroutines.flow.Flow r7 = r7.$source
            java.lang.Object r7 = r7.collect(r8, r0)
            if (r7 != r1) goto L4f
            goto L57
        L4f:
            r0.label = r3
            kotlin.coroutines.intrinsics.CoroutineSingletons r7 = kotlinx.coroutines.DelayKt.awaitCancellation(r0)
            if (r7 != r1) goto L58
        L57:
            return r1
        L58:
            kotlin.KotlinNothingValueException r7 = new kotlin.KotlinNothingValueException
            r7.<init>()
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.lifecycle.Hydrator$hydratedStateOf$3.onActivated(kotlin.coroutines.Continuation):java.lang.Object");
    }
}
