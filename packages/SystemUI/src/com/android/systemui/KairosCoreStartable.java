package com.android.systemui;

import com.android.systemui.kairos.KairosNetwork;
import com.android.systemui.kairos.KairosNetworkKt;
import com.android.systemui.kairos.RootKairosNetwork;
import dagger.Lazy;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CompletableDeferredImpl;
import kotlinx.coroutines.CompletableDeferredKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class KairosCoreStartable implements CoreStartable, KairosNetwork {
    public final Lazy activatables;
    public final CoroutineScope appScope;
    public final CompletableDeferredImpl started;
    public final RootKairosNetwork unwrappedNetwork;

    private KairosCoreStartable(CoroutineScope coroutineScope, Lazy lazy, RootKairosNetwork rootKairosNetwork) {
        this.appScope = coroutineScope;
        this.activatables = lazy;
        this.unwrappedNetwork = rootKairosNetwork;
        this.started = CompletableDeferredKt.CompletableDeferred$default();
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0060, code lost:
    
        if (r5.$$delegate_1.activateSpec(r6, r0) != r1) goto L22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0062, code lost:
    
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x004e, code lost:
    
        if (r5.started.awaitInternal(r0) == r1) goto L21;
     */
    /* JADX WARN: Removed duplicated region for block: B:20:0x003f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    @Override // com.android.systemui.kairos.KairosNetwork
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object activateSpec(kotlin.jvm.functions.Function1 r6, kotlin.coroutines.Continuation r7) {
        /*
            r5 = this;
            boolean r0 = r7 instanceof com.android.systemui.KairosCoreStartable$activateSpec$1
            if (r0 == 0) goto L13
            r0 = r7
            com.android.systemui.KairosCoreStartable$activateSpec$1 r0 = (com.android.systemui.KairosCoreStartable$activateSpec$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.KairosCoreStartable$activateSpec$1 r0 = new com.android.systemui.KairosCoreStartable$activateSpec$1
            r0.<init>(r5, r7)
        L18:
            java.lang.Object r7 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 2
            r4 = 1
            if (r2 == 0) goto L3f
            if (r2 == r4) goto L32
            if (r2 != r3) goto L2a
            kotlin.ResultKt.throwOnFailure(r7)
            goto L63
        L2a:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L32:
            java.lang.Object r5 = r0.L$1
            r6 = r5
            kotlin.jvm.functions.Function1 r6 = (kotlin.jvm.functions.Function1) r6
            java.lang.Object r5 = r0.L$0
            com.android.systemui.KairosCoreStartable r5 = (com.android.systemui.KairosCoreStartable) r5
            kotlin.ResultKt.throwOnFailure(r7)
            goto L51
        L3f:
            kotlin.ResultKt.throwOnFailure(r7)
            r0.L$0 = r5
            r0.L$1 = r6
            r0.label = r4
            kotlinx.coroutines.CompletableDeferredImpl r7 = r5.started
            java.lang.Object r7 = r7.awaitInternal(r0)
            if (r7 != r1) goto L51
            goto L62
        L51:
            com.android.systemui.kairos.RootKairosNetwork r5 = r5.unwrappedNetwork
            r7 = 0
            r0.L$0 = r7
            r0.L$1 = r7
            r0.label = r3
            com.android.systemui.kairos.LocalNetwork r5 = r5.$$delegate_1
            java.lang.Object r5 = r5.activateSpec(r6, r0)
            if (r5 != r1) goto L63
        L62:
            return r1
        L63:
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.KairosCoreStartable.activateSpec(kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        BuildersKt.launch$default(this.appScope, null, null, new KairosCoreStartable$start$1(this, null), 3);
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x004e, code lost:
    
        if (r5.started.awaitInternal(r0) == r1) goto L22;
     */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0062 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0063 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x003f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    @Override // com.android.systemui.kairos.KairosNetwork
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object transact(kotlin.jvm.functions.Function1 r6, kotlin.coroutines.Continuation r7) {
        /*
            r5 = this;
            boolean r0 = r7 instanceof com.android.systemui.KairosCoreStartable$transact$1
            if (r0 == 0) goto L13
            r0 = r7
            com.android.systemui.KairosCoreStartable$transact$1 r0 = (com.android.systemui.KairosCoreStartable$transact$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.KairosCoreStartable$transact$1 r0 = new com.android.systemui.KairosCoreStartable$transact$1
            r0.<init>(r5, r7)
        L18:
            java.lang.Object r7 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 2
            r4 = 1
            if (r2 == 0) goto L3f
            if (r2 == r4) goto L32
            if (r2 != r3) goto L2a
            kotlin.ResultKt.throwOnFailure(r7)
            return r7
        L2a:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L32:
            java.lang.Object r5 = r0.L$1
            r6 = r5
            kotlin.jvm.functions.Function1 r6 = (kotlin.jvm.functions.Function1) r6
            java.lang.Object r5 = r0.L$0
            com.android.systemui.KairosCoreStartable r5 = (com.android.systemui.KairosCoreStartable) r5
            kotlin.ResultKt.throwOnFailure(r7)
            goto L51
        L3f:
            kotlin.ResultKt.throwOnFailure(r7)
            r0.L$0 = r5
            r0.L$1 = r6
            r0.label = r4
            kotlinx.coroutines.CompletableDeferredImpl r7 = r5.started
            java.lang.Object r7 = r7.awaitInternal(r0)
            if (r7 != r1) goto L51
            goto L62
        L51:
            com.android.systemui.kairos.RootKairosNetwork r5 = r5.unwrappedNetwork
            r7 = 0
            r0.L$0 = r7
            r0.L$1 = r7
            r0.label = r3
            com.android.systemui.kairos.LocalNetwork r5 = r5.$$delegate_1
            java.lang.Object r5 = r5.transact(r6, r0)
            if (r5 != r1) goto L63
        L62:
            return r1
        L63:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.KairosCoreStartable.transact(kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public KairosCoreStartable(CoroutineScope coroutineScope, Lazy lazy) {
        this(coroutineScope, lazy, KairosNetworkKt.launchKairosNetwork$default(coroutineScope));
    }
}
