package com.android.systemui.util.kotlin;

import com.android.systemui.util.time.SystemClock;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$LongRef;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.channels.SendChannel;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

final class FlowKt$throttle$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ SystemClock $clock;
    final /* synthetic */ long $periodMs;
    final /* synthetic */ Flow $this_throttle;
    private /* synthetic */ Object L$0;
    int label;

    /* renamed from: com.android.systemui.util.kotlin.FlowKt$throttle$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ ProducerScope $$this$channelFlow;
        final /* synthetic */ SystemClock $clock;
        final /* synthetic */ long $periodMs;
        final /* synthetic */ Flow $this_throttle;
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.util.kotlin.FlowKt$throttle$1$1$1, reason: invalid class name and collision with other inner class name */
        final class C02801<T> implements FlowCollector {
            final /* synthetic */ ProducerScope $$this$channelFlow;
            final /* synthetic */ CoroutineScope $$this$coroutineScope;
            final /* synthetic */ SystemClock $clock;
            final /* synthetic */ Ref$ObjectRef<Job> $delayJob;
            final /* synthetic */ CoroutineScope $outerScope;
            final /* synthetic */ long $periodMs;
            final /* synthetic */ Ref$LongRef $previousEmitTimeMs;
            final /* synthetic */ Ref$ObjectRef<Job> $sendJob;

            /* renamed from: com.android.systemui.util.kotlin.FlowKt$throttle$1$1$1$1, reason: invalid class name and collision with other inner class name */
            final class C02811 extends SuspendLambda implements Function2 {
                final /* synthetic */ ProducerScope $$this$channelFlow;
                final /* synthetic */ SystemClock $clock;
                final /* synthetic */ Object $it;
                final /* synthetic */ CoroutineScope $outerScope;
                final /* synthetic */ Ref$LongRef $previousEmitTimeMs;
                final /* synthetic */ Ref$ObjectRef<Job> $sendJob;
                final /* synthetic */ long $timeUntilNextEmit;
                int label;

                /* renamed from: com.android.systemui.util.kotlin.FlowKt$throttle$1$1$1$1$1, reason: invalid class name and collision with other inner class name */
                final class C02821 extends SuspendLambda implements Function2 {
                    final /* synthetic */ ProducerScope $$this$channelFlow;
                    final /* synthetic */ SystemClock $clock;
                    final /* synthetic */ Object $it;
                    final /* synthetic */ Ref$LongRef $previousEmitTimeMs;
                    int label;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    public C02821(ProducerScope producerScope, Object obj, Ref$LongRef ref$LongRef, SystemClock systemClock, Continuation continuation) {
                        super(2, continuation);
                        this.$$this$channelFlow = producerScope;
                        this.$it = obj;
                        this.$previousEmitTimeMs = ref$LongRef;
                        this.$clock = systemClock;
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Continuation create(Object obj, Continuation continuation) {
                        return new C02821(this.$$this$channelFlow, this.$it, this.$previousEmitTimeMs, this.$clock, continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                        int i = this.label;
                        if (i == 0) {
                            ResultKt.throwOnFailure(obj);
                            SendChannel sendChannel = this.$$this$channelFlow;
                            Object obj2 = this.$it;
                            this.label = 1;
                            if (((ChannelCoroutine) sendChannel)._channel.send(obj2, this) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                        } else {
                            if (i != 1) {
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                            }
                            ResultKt.throwOnFailure(obj);
                        }
                        this.$previousEmitTimeMs.element = this.$clock.elapsedRealtime();
                        return Unit.INSTANCE;
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
                        return ((C02821) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
                    }
                }

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C02811(long j, Ref$ObjectRef<Job> ref$ObjectRef, CoroutineScope coroutineScope, ProducerScope producerScope, Object obj, Ref$LongRef ref$LongRef, SystemClock systemClock, Continuation continuation) {
                    super(2, continuation);
                    this.$timeUntilNextEmit = j;
                    this.$sendJob = ref$ObjectRef;
                    this.$outerScope = coroutineScope;
                    this.$$this$channelFlow = producerScope;
                    this.$it = obj;
                    this.$previousEmitTimeMs = ref$LongRef;
                    this.$clock = systemClock;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new C02811(this.$timeUntilNextEmit, this.$sendJob, this.$outerScope, this.$$this$channelFlow, this.$it, this.$previousEmitTimeMs, this.$clock, continuation);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        long j = this.$timeUntilNextEmit;
                        this.label = 1;
                        if (DelayKt.delay(j, this) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj);
                    }
                    this.$sendJob.element = (T) BuildersKt.launch$default(this.$outerScope, null, CoroutineStart.UNDISPATCHED, new C02821(this.$$this$channelFlow, this.$it, this.$previousEmitTimeMs, this.$clock, null), 1);
                    return Unit.INSTANCE;
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
                    return ((C02811) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
                }
            }

            public C02801(Ref$ObjectRef<Job> ref$ObjectRef, Ref$ObjectRef<Job> ref$ObjectRef2, SystemClock systemClock, Ref$LongRef ref$LongRef, long j, CoroutineScope coroutineScope, ProducerScope producerScope, CoroutineScope coroutineScope2) {
                this.$delayJob = ref$ObjectRef;
                this.$sendJob = ref$ObjectRef2;
                this.$clock = systemClock;
                this.$previousEmitTimeMs = ref$LongRef;
                this.$periodMs = j;
                this.$$this$coroutineScope = coroutineScope;
                this.$$this$channelFlow = producerScope;
                this.$outerScope = coroutineScope2;
            }

            /* JADX WARN: Removed duplicated region for block: B:21:0x0090  */
            /* JADX WARN: Removed duplicated region for block: B:22:0x00b1  */
            /* JADX WARN: Removed duplicated region for block: B:26:0x004d  */
            /* JADX WARN: Removed duplicated region for block: B:8:0x0027  */
            @Override // kotlinx.coroutines.flow.FlowCollector
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final java.lang.Object emit(T r19, kotlin.coroutines.Continuation r20) {
                /*
                    Method dump skipped, instructions count: 206
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.util.kotlin.FlowKt$throttle$1.AnonymousClass1.C02801.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(Flow flow, SystemClock systemClock, long j, ProducerScope producerScope, Continuation continuation) {
            super(2, continuation);
            this.$this_throttle = flow;
            this.$clock = systemClock;
            this.$periodMs = j;
            this.$$this$channelFlow = producerScope;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$this_throttle, this.$clock, this.$periodMs, this.$$this$channelFlow, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                Ref$LongRef ref$LongRef = new Ref$LongRef();
                Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
                Ref$ObjectRef ref$ObjectRef2 = new Ref$ObjectRef();
                Flow flow = this.$this_throttle;
                C02801 c02801 = new C02801(ref$ObjectRef, ref$ObjectRef2, this.$clock, ref$LongRef, this.$periodMs, coroutineScope, this.$$this$channelFlow, coroutineScope);
                this.label = 1;
                if (flow.collect(c02801, this) == coroutineSingletons) {
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
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FlowKt$throttle$1(Flow flow, SystemClock systemClock, long j, Continuation continuation) {
        super(2, continuation);
        this.$this_throttle = flow;
        this.$clock = systemClock;
        this.$periodMs = j;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        FlowKt$throttle$1 flowKt$throttle$1 = new FlowKt$throttle$1(this.$this_throttle, this.$clock, this.$periodMs, continuation);
        flowKt$throttle$1.L$0 = obj;
        return flowKt$throttle$1;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$this_throttle, this.$clock, this.$periodMs, (ProducerScope) this.L$0, null);
            this.label = 1;
            if (CoroutineScopeKt.coroutineScope(anonymousClass1, this) == coroutineSingletons) {
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
        return ((FlowKt$throttle$1) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }
}
