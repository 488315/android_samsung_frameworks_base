package kotlinx.coroutines.flow;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
final class FlowKt__DelayKt$timeoutInternal$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ Flow $this_timeoutInternal;
    final /* synthetic */ long $timeout;
    long J$0;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FlowKt__DelayKt$timeoutInternal$1(long j, Flow flow, Continuation continuation) {
        super(3, continuation);
        this.$timeout = j;
        this.$this_timeoutInternal = flow;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        FlowKt__DelayKt$timeoutInternal$1 flowKt__DelayKt$timeoutInternal$1 = new FlowKt__DelayKt$timeoutInternal$1(this.$timeout, this.$this_timeoutInternal, (Continuation) obj3);
        flowKt__DelayKt$timeoutInternal$1.L$0 = (CoroutineScope) obj;
        flowKt__DelayKt$timeoutInternal$1.L$1 = (FlowCollector) obj2;
        return flowKt__DelayKt$timeoutInternal$1.invokeSuspend(Unit.INSTANCE);
    }

    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions count limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        */
    /* JADX WARN: Removed duplicated region for block: B:13:0x00b3  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x00c2  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x00ae  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r20) {
        /*
            r19 = this;
            r0 = r19
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 0
            r4 = 1
            r5 = 0
            if (r2 == 0) goto L26
            if (r2 != r4) goto L1e
            long r6 = r0.J$0
            java.lang.Object r2 = r0.L$1
            kotlinx.coroutines.channels.ReceiveChannel r2 = (kotlinx.coroutines.channels.ReceiveChannel) r2
            java.lang.Object r8 = r0.L$0
            kotlinx.coroutines.flow.FlowCollector r8 = (kotlinx.coroutines.flow.FlowCollector) r8
            kotlin.ResultKt.throwOnFailure(r20)
            r9 = r20
            goto Lba
        L1e:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L26:
            kotlin.ResultKt.throwOnFailure(r20)
            java.lang.Object r2 = r0.L$0
            kotlinx.coroutines.CoroutineScope r2 = (kotlinx.coroutines.CoroutineScope) r2
            java.lang.Object r6 = r0.L$1
            kotlinx.coroutines.flow.FlowCollector r6 = (kotlinx.coroutines.flow.FlowCollector) r6
            long r7 = r0.$timeout
            kotlin.time.Duration$Companion r9 = kotlin.time.Duration.Companion
            r9.getClass()
            r9 = 0
            int r7 = kotlin.time.Duration.m3435compareToLRDsOJo(r7, r9)
            if (r7 <= 0) goto Lc5
            kotlinx.coroutines.flow.Flow r7 = r0.$this_timeoutInternal
            r8 = 2
            kotlinx.coroutines.flow.Flow r10 = kotlinx.coroutines.flow.FlowKt.buffer$default(r7, r3, r8)
            boolean r7 = r10 instanceof kotlinx.coroutines.flow.internal.ChannelFlow
            if (r7 == 0) goto L4f
            r7 = r10
            kotlinx.coroutines.flow.internal.ChannelFlow r7 = (kotlinx.coroutines.flow.internal.ChannelFlow) r7
            goto L50
        L4f:
            r7 = r5
        L50:
            if (r7 != 0) goto L5e
            kotlinx.coroutines.flow.internal.ChannelFlowOperatorImpl r9 = new kotlinx.coroutines.flow.internal.ChannelFlowOperatorImpl
            r14 = 14
            r15 = 0
            r11 = 0
            r12 = 0
            r13 = 0
            r9.<init>(r10, r11, r12, r13, r14, r15)
            r7 = r9
        L5e:
            kotlinx.coroutines.channels.ReceiveChannel r2 = r7.produceImpl(r2)
            long r7 = r0.$timeout
            r17 = r7
            r8 = r6
            r6 = r17
        L69:
            kotlinx.coroutines.selects.SelectImplementation r10 = new kotlinx.coroutines.selects.SelectImplementation
            kotlin.coroutines.CoroutineContext r9 = r0.getContext()
            r10.<init>(r9)
            kotlinx.coroutines.selects.SelectClause1Impl r9 = r2.getOnReceiveCatching()
            kotlinx.coroutines.flow.FlowKt__DelayKt$timeoutInternal$1$1$1 r15 = new kotlinx.coroutines.flow.FlowKt__DelayKt$timeoutInternal$1$1$1
            r15.<init>(r8, r5)
            kotlinx.coroutines.selects.SelectImplementation$ClauseData r11 = new kotlinx.coroutines.selects.SelectImplementation$ClauseData
            r12 = r11
            java.lang.Object r11 = r9.clauseObject
            kotlin.jvm.functions.Function3 r13 = r9.onCancellationConstructor
            r14 = r12
            kotlin.jvm.functions.Function3 r12 = r9.regFunc
            kotlin.jvm.functions.Function3 r9 = r9.processResFunc
            r16 = r13
            r13 = r9
            r9 = r14
            r14 = 0
            r9.<init>(r11, r12, r13, r14, r15, r16)
            r10.register(r9, r3)
            kotlinx.coroutines.flow.FlowKt__DelayKt$timeoutInternal$1$1$2 r9 = new kotlinx.coroutines.flow.FlowKt__DelayKt$timeoutInternal$1$1$2
            r9.<init>(r6, r5)
            long r11 = kotlinx.coroutines.DelayKt.m3450toDelayMillisLRDsOJo(r6)
            kotlinx.coroutines.selects.OnTimeoutKt.onTimeout(r10, r11, r9)
            r0.L$0 = r8
            r0.L$1 = r2
            r0.J$0 = r6
            r0.label = r4
            kotlinx.atomicfu.AtomicRef r9 = r10.state
            java.lang.Object r9 = r9.value
            boolean r9 = r9 instanceof kotlinx.coroutines.selects.SelectImplementation.ClauseData
            if (r9 == 0) goto Lb3
            java.lang.Object r9 = r10.complete(r0)
            goto Lb7
        Lb3:
            java.lang.Object r9 = r10.doSelectSuspend(r0)
        Lb7:
            if (r9 != r1) goto Lba
            return r1
        Lba:
            java.lang.Boolean r9 = (java.lang.Boolean) r9
            boolean r9 = r9.booleanValue()
            if (r9 != 0) goto L69
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
            return r0
        Lc5:
            kotlinx.coroutines.TimeoutCancellationException r0 = new kotlinx.coroutines.TimeoutCancellationException
            java.lang.String r1 = "Timed out immediately"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__DelayKt$timeoutInternal$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
