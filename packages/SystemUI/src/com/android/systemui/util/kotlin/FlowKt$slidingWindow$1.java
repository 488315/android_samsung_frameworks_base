package com.android.systemui.util.kotlin;

import com.android.systemui.util.time.SystemClock;
import java.util.LinkedList;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlin.time.Duration;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class FlowKt$slidingWindow$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ SystemClock $clock;
    final /* synthetic */ Flow $this_slidingWindow;
    final /* synthetic */ long $windowDuration;
    private /* synthetic */ Object L$0;
    int label;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.util.kotlin.FlowKt$slidingWindow$1$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ ProducerScope $$this$channelFlow;
        final /* synthetic */ LinkedList<Pair<Duration, Object>> $buffer;
        final /* synthetic */ SystemClock $clock;
        final /* synthetic */ Flow $this_slidingWindow;
        final /* synthetic */ long $windowDuration;
        private /* synthetic */ Object L$0;
        int label;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.util.kotlin.FlowKt$slidingWindow$1$2$1, reason: invalid class name */
        final class AnonymousClass1<T> implements FlowCollector {
            final /* synthetic */ ProducerScope $$this$channelFlow;
            final /* synthetic */ CoroutineScope $$this$coroutineScope;
            final /* synthetic */ LinkedList<Pair<Duration, T>> $buffer;
            final /* synthetic */ SystemClock $clock;
            final /* synthetic */ Ref$ObjectRef<Job> $windowAdvancementJob;
            final /* synthetic */ long $windowDuration;

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.util.kotlin.FlowKt$slidingWindow$1$2$1$2, reason: invalid class name and collision with other inner class name */
            final class C04142 extends SuspendLambda implements Function2 {
                final /* synthetic */ ProducerScope $$this$channelFlow;
                final /* synthetic */ LinkedList<Pair<Duration, Object>> $buffer;
                final /* synthetic */ SystemClock $clock;
                final /* synthetic */ long $windowDuration;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C04142(LinkedList<Pair<Duration, Object>> linkedList, SystemClock systemClock, long j, ProducerScope producerScope, Continuation continuation) {
                    super(2, continuation);
                    this.$buffer = linkedList;
                    this.$clock = systemClock;
                    this.$windowDuration = j;
                    this.$$this$channelFlow = producerScope;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new C04142(this.$buffer, this.$clock, this.$windowDuration, this.$$this$channelFlow, continuation);
                }

                /* JADX WARN: Code restructure failed: missing block: B:15:0x00a9, code lost:
                
                    if (((kotlinx.coroutines.channels.ChannelCoroutine) r9)._channel.send(r4, r8) == r0) goto L25;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:22:0x0070, code lost:
                
                    if (kotlinx.coroutines.DelayKt.m3449delayVtjQ1oo(r4, r8) == r0) goto L25;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:25:0x00ab, code lost:
                
                    return r0;
                 */
                /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:14:0x00a9 -> B:15:0x001c). Please report as a decompilation issue!!! */
                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object invokeSuspend(java.lang.Object r9) {
                    /*
                        r8 = this;
                        kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r1 = r8.label
                        r2 = 2
                        r3 = 1
                        if (r1 == 0) goto L19
                        if (r1 == r3) goto L15
                        if (r1 != r2) goto Ld
                        goto L19
                    Ld:
                        java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
                        java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
                        r8.<init>(r9)
                        throw r8
                    L15:
                        kotlin.ResultKt.throwOnFailure(r9)
                        goto L73
                    L19:
                        kotlin.ResultKt.throwOnFailure(r9)
                    L1c:
                        java.util.LinkedList<kotlin.Pair<kotlin.time.Duration, java.lang.Object>> r9 = r8.$buffer
                        boolean r9 = r9.isEmpty()
                        if (r9 != 0) goto Lac
                        kotlin.time.Duration$Companion r9 = kotlin.time.Duration.Companion
                        com.android.systemui.util.time.SystemClock r9 = r8.$clock
                        long r4 = r9.currentTimeMillis()
                        kotlin.time.DurationUnit r9 = kotlin.time.DurationUnit.MILLISECONDS
                        long r4 = kotlin.time.DurationKt.toDuration(r4, r9)
                        long r6 = r8.$windowDuration
                        long r6 = kotlin.time.Duration.m3447unaryMinusUwyO8pc(r6)
                        long r4 = kotlin.time.Duration.m3441plusLRDsOJo(r4, r6)
                        java.util.LinkedList<kotlin.Pair<kotlin.time.Duration, java.lang.Object>> r1 = r8.$buffer
                        java.lang.Object r1 = r1.getFirst()
                        kotlin.Pair r1 = (kotlin.Pair) r1
                        java.lang.Object r1 = r1.getFirst()
                        kotlin.time.Duration r1 = (kotlin.time.Duration) r1
                        long r6 = r1.rawValue
                        long r4 = kotlin.time.Duration.m3447unaryMinusUwyO8pc(r4)
                        long r4 = kotlin.time.Duration.m3441plusLRDsOJo(r6, r4)
                        kotlin.time.Duration r1 = kotlin.time.Duration.m3434boximpl(r4)
                        r4 = 0
                        long r4 = kotlin.time.DurationKt.toDuration(r4, r9)
                        kotlin.time.Duration r9 = kotlin.time.Duration.m3434boximpl(r4)
                        int r4 = r1.compareTo(r9)
                        if (r4 >= 0) goto L68
                        r1 = r9
                    L68:
                        long r4 = r1.rawValue
                        r8.label = r3
                        java.lang.Object r9 = kotlinx.coroutines.DelayKt.m3449delayVtjQ1oo(r4, r8)
                        if (r9 != r0) goto L73
                        goto Lab
                    L73:
                        java.util.LinkedList<kotlin.Pair<kotlin.time.Duration, java.lang.Object>> r9 = r8.$buffer
                        r9.removeFirst()
                        kotlinx.coroutines.channels.ProducerScope r9 = r8.$$this$channelFlow
                        java.util.LinkedList<kotlin.Pair<kotlin.time.Duration, java.lang.Object>> r1 = r8.$buffer
                        java.util.ArrayList r4 = new java.util.ArrayList
                        r5 = 10
                        int r5 = kotlin.collections.CollectionsKt__IterablesKt.collectionSizeOrDefault(r1, r5)
                        r4.<init>(r5)
                        java.util.Iterator r1 = r1.iterator()
                    L8b:
                        boolean r5 = r1.hasNext()
                        if (r5 == 0) goto L9f
                        java.lang.Object r5 = r1.next()
                        kotlin.Pair r5 = (kotlin.Pair) r5
                        java.lang.Object r5 = r5.getSecond()
                        r4.add(r5)
                        goto L8b
                    L9f:
                        r8.label = r2
                        kotlinx.coroutines.channels.ChannelCoroutine r9 = (kotlinx.coroutines.channels.ChannelCoroutine) r9
                        kotlinx.coroutines.channels.Channel r9 = r9._channel
                        java.lang.Object r9 = r9.send(r4, r8)
                        if (r9 != r0) goto L1c
                    Lab:
                        return r0
                    Lac:
                        kotlin.Unit r8 = kotlin.Unit.INSTANCE
                        return r8
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.util.kotlin.FlowKt$slidingWindow$1.AnonymousClass2.AnonymousClass1.C04142.invokeSuspend(java.lang.Object):java.lang.Object");
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
                    return ((C04142) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
                }
            }

            public AnonymousClass1(Ref$ObjectRef<Job> ref$ObjectRef, SystemClock systemClock, LinkedList<Pair<Duration, T>> linkedList, long j, ProducerScope producerScope, CoroutineScope coroutineScope) {
                this.$windowAdvancementJob = ref$ObjectRef;
                this.$clock = systemClock;
                this.$buffer = linkedList;
                this.$windowDuration = j;
                this.$$this$channelFlow = producerScope;
                this.$$this$coroutineScope = coroutineScope;
            }

            /* JADX WARN: Removed duplicated region for block: B:15:0x0035  */
            /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
            @Override // kotlinx.coroutines.flow.FlowCollector
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final java.lang.Object emit(T r12, kotlin.coroutines.Continuation r13) {
                /*
                    r11 = this;
                    boolean r0 = r13 instanceof com.android.systemui.util.kotlin.FlowKt$slidingWindow$1$2$1$emit$1
                    if (r0 == 0) goto L13
                    r0 = r13
                    com.android.systemui.util.kotlin.FlowKt$slidingWindow$1$2$1$emit$1 r0 = (com.android.systemui.util.kotlin.FlowKt$slidingWindow$1$2$1$emit$1) r0
                    int r1 = r0.label
                    r2 = -2147483648(0xffffffff80000000, float:-0.0)
                    r3 = r1 & r2
                    if (r3 == 0) goto L13
                    int r1 = r1 - r2
                    r0.label = r1
                    goto L18
                L13:
                    com.android.systemui.util.kotlin.FlowKt$slidingWindow$1$2$1$emit$1 r0 = new com.android.systemui.util.kotlin.FlowKt$slidingWindow$1$2$1$emit$1
                    r0.<init>(r11, r13)
                L18:
                    java.lang.Object r13 = r0.result
                    kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                    int r2 = r0.label
                    r3 = 0
                    r4 = 1
                    if (r2 == 0) goto L35
                    if (r2 != r4) goto L2d
                    java.lang.Object r11 = r0.L$0
                    com.android.systemui.util.kotlin.FlowKt$slidingWindow$1$2$1 r11 = (com.android.systemui.util.kotlin.FlowKt$slidingWindow$1.AnonymousClass2.AnonymousClass1) r11
                    kotlin.ResultKt.throwOnFailure(r13)
                    goto Lbf
                L2d:
                    java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
                    java.lang.String r12 = "call to 'resume' before 'invoke' with coroutine"
                    r11.<init>(r12)
                    throw r11
                L35:
                    kotlin.ResultKt.throwOnFailure(r13)
                    kotlin.jvm.internal.Ref$ObjectRef<kotlinx.coroutines.Job> r13 = r11.$windowAdvancementJob
                    T r13 = r13.element
                    kotlinx.coroutines.Job r13 = (kotlinx.coroutines.Job) r13
                    if (r13 == 0) goto L43
                    r13.cancel(r3)
                L43:
                    kotlin.time.Duration$Companion r13 = kotlin.time.Duration.Companion
                    com.android.systemui.util.time.SystemClock r13 = r11.$clock
                    long r5 = r13.currentTimeMillis()
                    kotlin.time.DurationUnit r13 = kotlin.time.DurationUnit.MILLISECONDS
                    long r5 = kotlin.time.DurationKt.toDuration(r5, r13)
                    java.util.LinkedList<kotlin.Pair<kotlin.time.Duration, T>> r13 = r11.$buffer
                    kotlin.time.Duration r2 = kotlin.time.Duration.m3434boximpl(r5)
                    kotlin.Pair r7 = new kotlin.Pair
                    r7.<init>(r2, r12)
                    r13.addLast(r7)
                L5f:
                    java.util.LinkedList<kotlin.Pair<kotlin.time.Duration, T>> r12 = r11.$buffer
                    boolean r12 = r12.isEmpty()
                    if (r12 != 0) goto L89
                    java.util.LinkedList<kotlin.Pair<kotlin.time.Duration, T>> r12 = r11.$buffer
                    java.lang.Object r12 = r12.getFirst()
                    kotlin.Pair r12 = (kotlin.Pair) r12
                    java.lang.Object r12 = r12.getFirst()
                    kotlin.time.Duration r12 = (kotlin.time.Duration) r12
                    long r12 = r12.rawValue
                    long r7 = r11.$windowDuration
                    long r12 = kotlin.time.Duration.m3441plusLRDsOJo(r12, r7)
                    int r12 = kotlin.time.Duration.m3435compareToLRDsOJo(r12, r5)
                    if (r12 > 0) goto L89
                    java.util.LinkedList<kotlin.Pair<kotlin.time.Duration, T>> r12 = r11.$buffer
                    r12.removeFirst()
                    goto L5f
                L89:
                    kotlinx.coroutines.channels.ProducerScope r12 = r11.$$this$channelFlow
                    java.util.LinkedList<kotlin.Pair<kotlin.time.Duration, T>> r13 = r11.$buffer
                    java.util.ArrayList r2 = new java.util.ArrayList
                    r5 = 10
                    int r5 = kotlin.collections.CollectionsKt__IterablesKt.collectionSizeOrDefault(r13, r5)
                    r2.<init>(r5)
                    java.util.Iterator r13 = r13.iterator()
                L9c:
                    boolean r5 = r13.hasNext()
                    if (r5 == 0) goto Lb0
                    java.lang.Object r5 = r13.next()
                    kotlin.Pair r5 = (kotlin.Pair) r5
                    java.lang.Object r5 = r5.getSecond()
                    r2.add(r5)
                    goto L9c
                Lb0:
                    r0.L$0 = r11
                    r0.label = r4
                    kotlinx.coroutines.channels.ChannelCoroutine r12 = (kotlinx.coroutines.channels.ChannelCoroutine) r12
                    kotlinx.coroutines.channels.Channel r12 = r12._channel
                    java.lang.Object r12 = r12.send(r2, r0)
                    if (r12 != r1) goto Lbf
                    return r1
                Lbf:
                    kotlin.jvm.internal.Ref$ObjectRef<kotlinx.coroutines.Job> r12 = r11.$windowAdvancementJob
                    kotlinx.coroutines.CoroutineScope r13 = r11.$$this$coroutineScope
                    com.android.systemui.util.kotlin.FlowKt$slidingWindow$1$2$1$2 r4 = new com.android.systemui.util.kotlin.FlowKt$slidingWindow$1$2$1$2
                    java.util.LinkedList<kotlin.Pair<kotlin.time.Duration, T>> r5 = r11.$buffer
                    com.android.systemui.util.time.SystemClock r6 = r11.$clock
                    long r7 = r11.$windowDuration
                    kotlinx.coroutines.channels.ProducerScope r9 = r11.$$this$channelFlow
                    r10 = 0
                    r4.<init>(r5, r6, r7, r9, r10)
                    r11 = 7
                    kotlinx.coroutines.StandaloneCoroutine r11 = com.android.app.tracing.coroutines.CoroutineTracingKt.launchTraced$default(r13, r3, r3, r4, r11)
                    r12.element = r11
                    kotlin.Unit r11 = kotlin.Unit.INSTANCE
                    return r11
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.util.kotlin.FlowKt$slidingWindow$1.AnonymousClass2.AnonymousClass1.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(Flow flow, SystemClock systemClock, LinkedList<Pair<Duration, Object>> linkedList, long j, ProducerScope producerScope, Continuation continuation) {
            super(2, continuation);
            this.$this_slidingWindow = flow;
            this.$clock = systemClock;
            this.$buffer = linkedList;
            this.$windowDuration = j;
            this.$$this$channelFlow = producerScope;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$this_slidingWindow, this.$clock, this.$buffer, this.$windowDuration, this.$$this$channelFlow, continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
                Flow flow = this.$this_slidingWindow;
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(ref$ObjectRef, this.$clock, this.$buffer, this.$windowDuration, this.$$this$channelFlow, coroutineScope);
                this.label = 1;
                if (flow.collect(anonymousClass1, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FlowKt$slidingWindow$1(long j, Flow flow, SystemClock systemClock, Continuation continuation) {
        super(2, continuation);
        this.$windowDuration = j;
        this.$this_slidingWindow = flow;
        this.$clock = systemClock;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        FlowKt$slidingWindow$1 flowKt$slidingWindow$1 = new FlowKt$slidingWindow$1(this.$windowDuration, this.$this_slidingWindow, this.$clock, continuation);
        flowKt$slidingWindow$1.L$0 = obj;
        return flowKt$slidingWindow$1;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            ProducerScope producerScope = (ProducerScope) this.L$0;
            long j = this.$windowDuration;
            Duration.Companion companion = Duration.Companion;
            if (j <= 0) {
                throw new IllegalArgumentException("Window duration must be positive");
            }
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$this_slidingWindow, this.$clock, new LinkedList(), this.$windowDuration, producerScope, null);
            this.label = 1;
            if (CoroutineScopeKt.coroutineScope(anonymousClass2, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(ProducerScope producerScope, Continuation continuation) {
        return ((FlowKt$slidingWindow$1) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }
}
