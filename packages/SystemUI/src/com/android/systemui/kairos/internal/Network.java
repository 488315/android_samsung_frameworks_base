package com.android.systemui.kairos.internal;

import java.util.HashMap;
import java.util.function.Function;
import kotlin.Unit;
import kotlin.collections.ArrayDeque;
import kotlin.coroutines.ContinuationInterceptor;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CompletableDeferredImpl;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.JobKt;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.channels.BufferedChannel;
import kotlinx.coroutines.channels.ChannelKt;
import kotlinx.coroutines.sync.MutexImpl;
import kotlinx.coroutines.sync.MutexKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class Network implements NetworkScope {
    public final CoroutineScope coroutineScope;
    public volatile long epoch;
    public final Object networkId = Long.valueOf(NetworkKt.nextNetworkId.getAndIncrement());
    public final SchedulerImpl compactor = new SchedulerImpl(new Network$$ExternalSyntheticLambda0(0));
    public final SchedulerImpl scheduler = new SchedulerImpl(new Network$$ExternalSyntheticLambda0(1));
    public final TransactionStore transactionStore = new TransactionStore();
    public final ArrayDeque stateWrites = new ArrayDeque();
    public final HashMap outputsByDispatcher = new HashMap();
    public final ArrayDeque muxMovers = new ArrayDeque();
    public final ArrayDeque deactivations = new ArrayDeque();
    public final ArrayDeque outputDeactivations = new ArrayDeque();
    public final MutexImpl transactionMutex = MutexKt.Mutex$default();
    public final BufferedChannel inputScheduleChan = ChannelKt.Channel$default(0, null, null, 7);

    public Network(CoroutineScope coroutineScope) {
        this.coroutineScope = coroutineScope;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x00b2  */
    /* JADX WARN: Removed duplicated region for block: B:142:0x0329  */
    /* JADX WARN: Removed duplicated region for block: B:146:0x0055  */
    /* JADX WARN: Removed duplicated region for block: B:155:0x00a0 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:156:0x00a1  */
    /* JADX WARN: Removed duplicated region for block: B:159:0x003f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0027  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:121:0x00a1 -> B:10:0x00a7). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object doTransaction(kotlin.coroutines.jvm.internal.ContinuationImpl r19) {
        /*
            Method dump skipped, instructions count: 813
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.kairos.internal.Network.doTransaction(kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0071  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0051  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0078  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x003b  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /* JADX WARN: Type inference failed for: r6v5, types: [com.android.systemui.kairos.internal.EvalScope] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:20:0x006b -> B:10:0x006d). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object evalOutputs(com.android.systemui.kairos.internal.EvalScopeImpl r6, kotlin.coroutines.jvm.internal.ContinuationImpl r7) {
        /*
            r5 = this;
            boolean r0 = r7 instanceof com.android.systemui.kairos.internal.Network$evalOutputs$1
            if (r0 == 0) goto L13
            r0 = r7
            com.android.systemui.kairos.internal.Network$evalOutputs$1 r0 = (com.android.systemui.kairos.internal.Network$evalOutputs$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.kairos.internal.Network$evalOutputs$1 r0 = new com.android.systemui.kairos.internal.Network$evalOutputs$1
            r0.<init>(r5, r7)
        L18:
            java.lang.Object r7 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L3b
            if (r2 != r3) goto L33
            java.lang.Object r5 = r0.L$2
            kotlin.jvm.internal.Ref$BooleanRef r5 = (kotlin.jvm.internal.Ref$BooleanRef) r5
            java.lang.Object r6 = r0.L$1
            com.android.systemui.kairos.internal.EvalScope r6 = (com.android.systemui.kairos.internal.EvalScope) r6
            java.lang.Object r2 = r0.L$0
            com.android.systemui.kairos.internal.Network r2 = (com.android.systemui.kairos.internal.Network) r2
            kotlin.ResultKt.throwOnFailure(r7)
            goto L6d
        L33:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L3b:
            kotlin.ResultKt.throwOnFailure(r7)
            java.util.HashMap r7 = r5.outputsByDispatcher
            boolean r7 = r7.isEmpty()
            if (r7 == 0) goto L49
            java.lang.Boolean r5 = java.lang.Boolean.FALSE
            return r5
        L49:
            java.util.HashMap r7 = r5.outputsByDispatcher
            boolean r7 = r7.isEmpty()
            if (r7 != 0) goto L78
            kotlin.jvm.internal.Ref$BooleanRef r7 = new kotlin.jvm.internal.Ref$BooleanRef
            r7.<init>()
            com.android.systemui.kairos.internal.Network$evalOutputs$2 r2 = new com.android.systemui.kairos.internal.Network$evalOutputs$2
            r4 = 0
            r2.<init>(r5, r7, r6, r4)
            r0.L$0 = r5
            r0.L$1 = r6
            r0.L$2 = r7
            r0.label = r3
            java.lang.Object r2 = kotlinx.coroutines.CoroutineScopeKt.coroutineScope(r2, r0)
            if (r2 != r1) goto L6b
            return r1
        L6b:
            r2 = r5
            r5 = r7
        L6d:
            boolean r5 = r5.element
            if (r5 != 0) goto L76
            java.util.HashMap r5 = r2.outputsByDispatcher
            r5.clear()
        L76:
            r5 = r2
            goto L49
        L78:
            java.lang.Boolean r5 = java.lang.Boolean.TRUE
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.kairos.internal.Network.evalOutputs(com.android.systemui.kairos.internal.EvalScopeImpl, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    @Override // com.android.systemui.kairos.internal.NetworkScope
    public final SchedulerImpl getCompactor() {
        return this.compactor;
    }

    @Override // com.android.systemui.kairos.internal.NetworkScope
    public final long getEpoch() {
        return this.epoch;
    }

    @Override // com.android.systemui.kairos.internal.NetworkScope
    public final Object getNetworkId() {
        return this.networkId;
    }

    @Override // com.android.systemui.kairos.internal.NetworkScope
    public final SchedulerImpl getScheduler() {
        return this.scheduler;
    }

    @Override // com.android.systemui.kairos.internal.NetworkScope
    public final TransactionStore getTransactionStore() {
        return this.transactionStore;
    }

    /* JADX WARN: Code restructure failed: missing block: B:36:0x0118, code lost:
    
        if (kotlinx.coroutines.YieldKt.yield(r2) != r3) goto L44;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x019e, code lost:
    
        r0 = kotlin.Unit.INSTANCE;
        r1.drainDeferrals();
        r2.L$0 = r5;
        r2.L$1 = r13;
        r2.L$2 = r4;
        r2.L$3 = r6;
        r2.L$4 = null;
        r2.L$5 = null;
        r2.L$6 = null;
        r2.J$0 = r14;
        r2.J$1 = r11;
        r2.label = 5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x01bd, code lost:
    
        if (r5.doTransaction(r2) != r3) goto L67;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x01c0, code lost:
    
        r0 = r5;
        r5 = r4;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:114:0x00d0  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00f5  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0102  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x016d A[Catch: all -> 0x019a, Exception -> 0x019c, TryCatch #7 {Exception -> 0x019c, all -> 0x019a, blocks: (B:44:0x0151, B:46:0x0167, B:48:0x016d, B:54:0x019e), top: B:43:0x0151 }] */
    /* JADX WARN: Removed duplicated region for block: B:53:0x019e A[EDGE_INSN: B:53:0x019e->B:54:0x019e BREAK  A[LOOP:1: B:46:0x0167->B:50:0x0191], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:81:0x023d A[Catch: all -> 0x0247, LOOP:3: B:78:0x0234->B:81:0x023d, LOOP_END, TryCatch #3 {all -> 0x0247, blocks: (B:79:0x0234, B:81:0x023d, B:83:0x024a), top: B:78:0x0234 }] */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0257  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x002a  */
    /* JADX WARN: Type inference failed for: r4v1 */
    /* JADX WARN: Type inference failed for: r4v22 */
    /* JADX WARN: Type inference failed for: r4v27 */
    /* JADX WARN: Type inference failed for: r4v4 */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:52:0x01c0 -> B:18:0x004f). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:77:0x024f -> B:34:0x010b). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object runInputScheduler(kotlin.coroutines.jvm.internal.ContinuationImpl r22) {
        /*
            Method dump skipped, instructions count: 602
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.kairos.internal.Network.runInputScheduler(kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    @Override // com.android.systemui.kairos.internal.NetworkScope
    public final void schedule(StateSource stateSource) {
        this.stateWrites.addLast(stateSource);
    }

    @Override // com.android.systemui.kairos.internal.NetworkScope
    public final void scheduleDeactivation(Output output) {
        this.outputDeactivations.addLast(output);
    }

    @Override // com.android.systemui.kairos.internal.NetworkScope
    public final void scheduleMuxMover(MuxDeferredNode muxDeferredNode) {
        this.muxMovers.addLast(muxDeferredNode);
    }

    @Override // com.android.systemui.kairos.internal.NetworkScope
    public final void scheduleOutput(Output output) {
        CoroutineContext.Element element = (ContinuationInterceptor) output.context.get(ContinuationInterceptor.Key);
        if (element == null) {
            element = Dispatchers.Unconfined;
        }
        HashMap hashMap = this.outputsByDispatcher;
        final Network$$ExternalSyntheticLambda0 network$$ExternalSyntheticLambda0 = new Network$$ExternalSyntheticLambda0(2);
        ((ArrayDeque) hashMap.computeIfAbsent(element, new Function() { // from class: com.android.systemui.kairos.internal.NetworkKt$sam$java_util_function_Function$0
            @Override // java.util.function.Function
            public final /* synthetic */ Object apply(Object obj) {
                return Function1.this.mo779invoke(obj);
            }
        })).addLast(output);
    }

    public final CompletableDeferredImpl transaction(String str, Function2 function2) {
        CoroutineScope coroutineScope = this.coroutineScope;
        CompletableDeferredImpl completableDeferredImpl = new CompletableDeferredImpl(JobKt.getJob(coroutineScope.getCoroutineContext()));
        if (!CoroutineScopeKt.isActive(coroutineScope)) {
            completableDeferredImpl.cancel(null);
            return completableDeferredImpl;
        }
        final StandaloneCoroutine launch$default = BuildersKt.launch$default(coroutineScope, null, null, new Network$transaction$1$job$1(this, str, completableDeferredImpl, function2, null), 3);
        completableDeferredImpl.invokeOnCompletion(new Function1() { // from class: com.android.systemui.kairos.internal.Network$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Job.this.cancel(null);
                return Unit.INSTANCE;
            }
        });
        return completableDeferredImpl;
    }

    @Override // com.android.systemui.kairos.internal.NetworkScope
    public final void scheduleDeactivation(PushNode pushNode) {
        this.deactivations.addLast(pushNode);
    }

    @Override // com.android.systemui.kairos.internal.NetworkScope
    public final Network getNetwork() {
        return this;
    }
}
