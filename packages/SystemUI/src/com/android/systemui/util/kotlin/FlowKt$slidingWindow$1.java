package com.android.systemui.util.kotlin;

import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.util.time.SystemClock;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.channels.SendChannel;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* loaded from: classes3.dex */
final class FlowKt$slidingWindow$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ SystemClock $clock;
    final /* synthetic */ Flow $this_slidingWindow;
    final /* synthetic */ long $windowDuration;
    private /* synthetic */ Object L$0;
    int label;

    /* renamed from: com.android.systemui.util.kotlin.FlowKt$slidingWindow$1$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ ProducerScope $$this$channelFlow;
        final /* synthetic */ LinkedList<Pair<Duration, Object>> $buffer;
        final /* synthetic */ SystemClock $clock;
        final /* synthetic */ Flow $this_slidingWindow;
        final /* synthetic */ long $windowDuration;
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.util.kotlin.FlowKt$slidingWindow$1$2$1, reason: invalid class name */
        final class AnonymousClass1<T> implements FlowCollector {
            final /* synthetic */ ProducerScope $$this$channelFlow;
            final /* synthetic */ CoroutineScope $$this$coroutineScope;
            final /* synthetic */ LinkedList<Pair<Duration, T>> $buffer;
            final /* synthetic */ SystemClock $clock;
            final /* synthetic */ Ref$ObjectRef<Job> $windowAdvancementJob;
            final /* synthetic */ long $windowDuration;

            /* renamed from: com.android.systemui.util.kotlin.FlowKt$slidingWindow$1$2$1$2, reason: invalid class name and collision with other inner class name */
            final class C06362 extends SuspendLambda implements Function2 {
                final /* synthetic */ ProducerScope $$this$channelFlow;
                final /* synthetic */ LinkedList<Pair<Duration, Object>> $buffer;
                final /* synthetic */ SystemClock $clock;
                final /* synthetic */ long $windowDuration;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C06362(LinkedList<Pair<Duration, Object>> linkedList, SystemClock systemClock, long j, ProducerScope producerScope, Continuation continuation) {
                    super(2, continuation);
                    this.$buffer = linkedList;
                    this.$clock = systemClock;
                    this.$windowDuration = j;
                    this.$$this$channelFlow = producerScope;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new C06362(this.$buffer, this.$clock, this.$windowDuration, this.$$this$channelFlow, continuation);
                }

                /* JADX WARN: Removed duplicated region for block: B:11:0x001c  */
                /* JADX WARN: Removed duplicated region for block: B:13:0x0024  */
                /* JADX WARN: Removed duplicated region for block: B:22:0x0091 A[LOOP:0: B:20:0x008b->B:22:0x0091, LOOP_END] */
                /* JADX WARN: Removed duplicated region for block: B:26:0x00ac  */
                /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:24:0x00a9 -> B:11:0x001c). Please report as a decompilation issue!!! */
                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i != 0) {
                        if (i == 1) {
                            ResultKt.throwOnFailure(obj);
                            this.$buffer.removeFirst();
                            SendChannel sendChannel = this.$$this$channelFlow;
                            LinkedList<Pair<Duration, Object>> linkedList = this.$buffer;
                            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(linkedList, 10));
                            Iterator<T> it = linkedList.iterator();
                            while (it.hasNext()) {
                                arrayList.add(((Pair) it.next()).getSecond());
                            }
                            this.label = 2;
                            if (((ChannelCoroutine) sendChannel)._channel.send(arrayList, this) != coroutineSingletons) {
                                if (!this.$buffer.isEmpty()) {
                                    return Unit.INSTANCE;
                                }
                                Duration.Companion companion = Duration.Companion;
                                long jCurrentTimeMillis = this.$clock.currentTimeMillis();
                                DurationUnit durationUnit = DurationUnit.MILLISECONDS;
                                Duration durationM3453boximpl = Duration.m3453boximpl(Duration.m3460plusLRDsOJo(((Duration) this.$buffer.getFirst().getFirst()).rawValue, Duration.m3466unaryMinusUwyO8pc(Duration.m3460plusLRDsOJo(DurationKt.toDuration(jCurrentTimeMillis, durationUnit), Duration.m3466unaryMinusUwyO8pc(this.$windowDuration)))));
                                Duration durationM3453boximpl2 = Duration.m3453boximpl(DurationKt.toDuration(0, durationUnit));
                                if (durationM3453boximpl.compareTo(durationM3453boximpl2) < 0) {
                                    durationM3453boximpl = durationM3453boximpl2;
                                }
                                long j = durationM3453boximpl.rawValue;
                                this.label = 1;
                                if (DelayKt.m3468delayVtjQ1oo(j, this) != coroutineSingletons) {
                                    this.$buffer.removeFirst();
                                    SendChannel sendChannel2 = this.$$this$channelFlow;
                                    LinkedList<Pair<Duration, Object>> linkedList2 = this.$buffer;
                                    ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(linkedList2, 10));
                                    Iterator<T> it2 = linkedList2.iterator();
                                    while (it2.hasNext()) {
                                    }
                                    this.label = 2;
                                    if (((ChannelCoroutine) sendChannel2)._channel.send(arrayList2, this) != coroutineSingletons) {
                                    }
                                }
                            }
                            return coroutineSingletons;
                        }
                        if (i != 2) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                    }
                    ResultKt.throwOnFailure(obj);
                    if (!this.$buffer.isEmpty()) {
                    }
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
                    return ((C06362) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
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

            /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
            @Override // kotlinx.coroutines.flow.FlowCollector
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object emit(T t, Continuation continuation) {
                FlowKt$slidingWindow$1$2$1$emit$1 flowKt$slidingWindow$1$2$1$emit$1;
                if (continuation instanceof FlowKt$slidingWindow$1$2$1$emit$1) {
                    flowKt$slidingWindow$1$2$1$emit$1 = (FlowKt$slidingWindow$1$2$1$emit$1) continuation;
                    int i = flowKt$slidingWindow$1$2$1$emit$1.label;
                    if ((i & Integer.MIN_VALUE) != 0) {
                        flowKt$slidingWindow$1$2$1$emit$1.label = i - Integer.MIN_VALUE;
                    } else {
                        flowKt$slidingWindow$1$2$1$emit$1 = new FlowKt$slidingWindow$1$2$1$emit$1(this, continuation);
                    }
                }
                Object obj = flowKt$slidingWindow$1$2$1$emit$1.result;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i2 = flowKt$slidingWindow$1$2$1$emit$1.label;
                if (i2 == 0) {
                    ResultKt.throwOnFailure(obj);
                    Job job = this.$windowAdvancementJob.element;
                    if (job != null) {
                        job.cancel(null);
                    }
                    Duration.Companion companion = Duration.Companion;
                    long duration = DurationKt.toDuration(this.$clock.currentTimeMillis(), DurationUnit.MILLISECONDS);
                    this.$buffer.addLast(new Pair<>(Duration.m3453boximpl(duration), t));
                    while (!this.$buffer.isEmpty() && Duration.m3454compareToLRDsOJo(Duration.m3460plusLRDsOJo(((Duration) this.$buffer.getFirst().getFirst()).rawValue, this.$windowDuration), duration) <= 0) {
                        this.$buffer.removeFirst();
                    }
                    SendChannel sendChannel = this.$$this$channelFlow;
                    LinkedList<Pair<Duration, T>> linkedList = this.$buffer;
                    ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(linkedList, 10));
                    Iterator<T> it = linkedList.iterator();
                    while (it.hasNext()) {
                        arrayList.add(((Pair) it.next()).getSecond());
                    }
                    flowKt$slidingWindow$1$2$1$emit$1.L$0 = this;
                    flowKt$slidingWindow$1$2$1$emit$1.label = 1;
                    if (((ChannelCoroutine) sendChannel)._channel.send(arrayList, flowKt$slidingWindow$1$2$1$emit$1) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i2 != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    this = (AnonymousClass1) flowKt$slidingWindow$1$2$1$emit$1.L$0;
                    ResultKt.throwOnFailure(obj);
                }
                this.$windowAdvancementJob.element = (T) CoroutineTracingKt.launchTraced$default(this.$$this$coroutineScope, null, null, new C06362(this.$buffer, this.$clock, this.$windowDuration, this.$$this$channelFlow, null), 7);
                return Unit.INSTANCE;
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
