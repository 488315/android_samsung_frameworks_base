package com.android.systemui.kairos;

import com.android.systemui.kairos.internal.Network;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class LocalNetwork implements KairosNetwork {
    public final Events endSignal;
    public final Network network;
    public final CoroutineScope scope;

    public LocalNetwork(Network network, CoroutineScope coroutineScope, Events events) {
        this.network = network;
        this.scope = coroutineScope;
        this.endSignal = events;
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x007c, code lost:
    
        if (r0.joinOrCancel((kotlinx.coroutines.Job) r1, r2) != r3) goto L22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x007e, code lost:
    
        return r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x006f, code lost:
    
        if (r1 == r3) goto L21;
     */
    /* JADX WARN: Removed duplicated region for block: B:20:0x003f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0027  */
    @Override // com.android.systemui.kairos.KairosNetwork
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object activateSpec(kotlin.jvm.functions.Function1 r17, kotlin.coroutines.Continuation r18) {
        /*
            r16 = this;
            r0 = r16
            r1 = r18
            boolean r2 = r1 instanceof com.android.systemui.kairos.LocalNetwork$activateSpec$1
            if (r2 == 0) goto L17
            r2 = r1
            com.android.systemui.kairos.LocalNetwork$activateSpec$1 r2 = (com.android.systemui.kairos.LocalNetwork$activateSpec$1) r2
            int r3 = r2.label
            r4 = -2147483648(0xffffffff80000000, float:-0.0)
            r5 = r3 & r4
            if (r5 == 0) goto L17
            int r3 = r3 - r4
            r2.label = r3
            goto L1c
        L17:
            com.android.systemui.kairos.LocalNetwork$activateSpec$1 r2 = new com.android.systemui.kairos.LocalNetwork$activateSpec$1
            r2.<init>(r0, r1)
        L1c:
            java.lang.Object r1 = r2.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r3 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r4 = r2.label
            r5 = 0
            r6 = 2
            r7 = 1
            if (r4 == 0) goto L3f
            if (r4 == r7) goto L37
            if (r4 != r6) goto L2f
            kotlin.ResultKt.throwOnFailure(r1)
            goto L7f
        L2f:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L37:
            java.lang.Object r0 = r2.L$0
            com.android.systemui.kairos.LocalNetwork r0 = (com.android.systemui.kairos.LocalNetwork) r0
            kotlin.ResultKt.throwOnFailure(r1)
            goto L72
        L3f:
            kotlin.ResultKt.throwOnFailure(r1)
            com.android.systemui.kairos.CoalescingMutableEvents r8 = new com.android.systemui.kairos.CoalescingMutableEvents
            com.android.systemui.kairos.LocalNetwork$$ExternalSyntheticLambda0 r10 = new com.android.systemui.kairos.LocalNetwork$$ExternalSyntheticLambda0
            r10.<init>()
            com.android.systemui.kairos.LocalNetwork$$ExternalSyntheticLambda1 r12 = new com.android.systemui.kairos.LocalNetwork$$ExternalSyntheticLambda1
            r12.<init>()
            r14 = 16
            r15 = 0
            r9 = 0
            com.android.systemui.kairos.internal.Network r11 = r0.network
            r13 = 0
            r8.<init>(r9, r10, r11, r12, r13, r14, r15)
            com.android.systemui.kairos.LocalNetwork$activateSpec$2 r1 = new com.android.systemui.kairos.LocalNetwork$activateSpec$2
            r4 = r17
            r1.<init>(r0, r8, r4, r5)
            com.android.systemui.kairos.internal.Network r4 = r0.network
            java.lang.String r8 = "KairosNetwork.activateSpec"
            kotlinx.coroutines.CompletableDeferredImpl r1 = r4.transaction(r8, r1)
            r2.L$0 = r0
            r2.label = r7
            java.lang.Object r1 = r0.awaitOrCancel(r1, r2)
            if (r1 != r3) goto L72
            goto L7e
        L72:
            kotlinx.coroutines.Job r1 = (kotlinx.coroutines.Job) r1
            r2.L$0 = r5
            r2.label = r6
            java.lang.Object r0 = r0.joinOrCancel(r1, r2)
            if (r0 != r3) goto L7f
        L7e:
            return r3
        L7f:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.kairos.LocalNetwork.activateSpec(kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0035  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object awaitOrCancel(kotlinx.coroutines.CompletableDeferredImpl r5, kotlin.coroutines.jvm.internal.ContinuationImpl r6) {
        /*
            r4 = this;
            boolean r0 = r6 instanceof com.android.systemui.kairos.LocalNetwork$awaitOrCancel$1
            if (r0 == 0) goto L13
            r0 = r6
            com.android.systemui.kairos.LocalNetwork$awaitOrCancel$1 r0 = (com.android.systemui.kairos.LocalNetwork$awaitOrCancel$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.kairos.LocalNetwork$awaitOrCancel$1 r0 = new com.android.systemui.kairos.LocalNetwork$awaitOrCancel$1
            r0.<init>(r4, r6)
        L18:
            java.lang.Object r4 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r6 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r0.label
            r2 = 1
            if (r1 == 0) goto L35
            if (r1 != r2) goto L2d
            java.lang.Object r5 = r0.L$0
            kotlinx.coroutines.Deferred r5 = (kotlinx.coroutines.Deferred) r5
            kotlin.ResultKt.throwOnFailure(r4)     // Catch: java.util.concurrent.CancellationException -> L2b
            return r4
        L2b:
            r4 = move-exception
            goto L44
        L2d:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L35:
            kotlin.ResultKt.throwOnFailure(r4)
            r0.L$0 = r5     // Catch: java.util.concurrent.CancellationException -> L2b
            r0.label = r2     // Catch: java.util.concurrent.CancellationException -> L2b
            java.lang.Object r4 = r5.awaitInternal(r0)     // Catch: java.util.concurrent.CancellationException -> L2b
            if (r4 != r6) goto L43
            return r6
        L43:
            return r4
        L44:
            kotlinx.coroutines.JobSupport r5 = (kotlinx.coroutines.JobSupport) r5
            r5.cancelInternal(r4)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.kairos.LocalNetwork.awaitOrCancel(kotlinx.coroutines.CompletableDeferredImpl, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0035  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object joinOrCancel(kotlinx.coroutines.Job r5, kotlin.coroutines.jvm.internal.ContinuationImpl r6) {
        /*
            r4 = this;
            boolean r0 = r6 instanceof com.android.systemui.kairos.LocalNetwork$joinOrCancel$1
            if (r0 == 0) goto L13
            r0 = r6
            com.android.systemui.kairos.LocalNetwork$joinOrCancel$1 r0 = (com.android.systemui.kairos.LocalNetwork$joinOrCancel$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.kairos.LocalNetwork$joinOrCancel$1 r0 = new com.android.systemui.kairos.LocalNetwork$joinOrCancel$1
            r0.<init>(r4, r6)
        L18:
            java.lang.Object r4 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r6 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r0.label
            r2 = 1
            if (r1 == 0) goto L35
            if (r1 != r2) goto L2d
            java.lang.Object r5 = r0.L$0
            kotlinx.coroutines.Job r5 = (kotlinx.coroutines.Job) r5
            kotlin.ResultKt.throwOnFailure(r4)     // Catch: java.util.concurrent.CancellationException -> L2b
            goto L43
        L2b:
            r4 = move-exception
            goto L46
        L2d:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L35:
            kotlin.ResultKt.throwOnFailure(r4)
            r0.L$0 = r5     // Catch: java.util.concurrent.CancellationException -> L2b
            r0.label = r2     // Catch: java.util.concurrent.CancellationException -> L2b
            java.lang.Object r4 = r5.join(r0)     // Catch: java.util.concurrent.CancellationException -> L2b
            if (r4 != r6) goto L43
            return r6
        L43:
            kotlin.Unit r4 = kotlin.Unit.INSTANCE
            return r4
        L46:
            r5.cancel(r4)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.kairos.LocalNetwork.joinOrCancel(kotlinx.coroutines.Job, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    @Override // com.android.systemui.kairos.KairosNetwork
    public final Object transact(Function1 function1, Continuation continuation) {
        return awaitOrCancel(this.network.transaction("KairosNetwork.transact", new LocalNetwork$transact$2(function1, null)), (ContinuationImpl) continuation);
    }
}
