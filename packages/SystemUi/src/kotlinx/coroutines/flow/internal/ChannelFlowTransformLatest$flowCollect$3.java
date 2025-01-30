package kotlinx.coroutines.flow.internal;

import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.JobSupport;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest$flowCollect$3", m277f = "Merge.kt", m278l = {27}, m279m = "invokeSuspend")
/* loaded from: classes3.dex */
final class ChannelFlowTransformLatest$flowCollect$3 extends SuspendLambda implements Function2 {
    final /* synthetic */ FlowCollector $collector;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ ChannelFlowTransformLatest this$0;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest$flowCollect$3$1 */
    public final class C48551 implements FlowCollector {
        public final /* synthetic */ CoroutineScope $$this$coroutineScope;
        public final /* synthetic */ FlowCollector $collector;
        public final /* synthetic */ Ref$ObjectRef $previousFlow;
        public final /* synthetic */ ChannelFlowTransformLatest this$0;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        @DebugMetadata(m276c = "kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest$flowCollect$3$1$2", m277f = "Merge.kt", m278l = {34}, m279m = "invokeSuspend")
        /* renamed from: kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest$flowCollect$3$1$2, reason: invalid class name */
        final class AnonymousClass2 extends SuspendLambda implements Function2 {
            final /* synthetic */ FlowCollector $collector;
            final /* synthetic */ Object $value;
            int label;
            final /* synthetic */ ChannelFlowTransformLatest this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass2(ChannelFlowTransformLatest channelFlowTransformLatest, FlowCollector flowCollector, Object obj, Continuation<? super AnonymousClass2> continuation) {
                super(2, continuation);
                this.this$0 = channelFlowTransformLatest;
                this.$collector = flowCollector;
                this.$value = obj;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass2(this.this$0, this.$collector, this.$value, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    Function3 function3 = this.this$0.transform;
                    FlowCollector flowCollector = this.$collector;
                    Object obj2 = this.$value;
                    this.label = 1;
                    if (function3.invoke(flowCollector, obj2, this) == coroutineSingletons) {
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
        }

        public C48551(Ref$ObjectRef<Job> ref$ObjectRef, CoroutineScope coroutineScope, ChannelFlowTransformLatest channelFlowTransformLatest, FlowCollector flowCollector) {
            this.$previousFlow = ref$ObjectRef;
            this.$$this$coroutineScope = coroutineScope;
            this.this$0 = channelFlowTransformLatest;
            this.$collector = flowCollector;
        }

        /* JADX WARN: Removed duplicated region for block: B:15:0x0039  */
        /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
        /* JADX WARN: Type inference failed for: r6v3, types: [T, kotlinx.coroutines.StandaloneCoroutine] */
        @Override // kotlinx.coroutines.flow.FlowCollector
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object emit(Object obj, Continuation continuation) {
            ChannelFlowTransformLatest$flowCollect$3$1$emit$1 channelFlowTransformLatest$flowCollect$3$1$emit$1;
            int i;
            if (continuation instanceof ChannelFlowTransformLatest$flowCollect$3$1$emit$1) {
                channelFlowTransformLatest$flowCollect$3$1$emit$1 = (ChannelFlowTransformLatest$flowCollect$3$1$emit$1) continuation;
                int i2 = channelFlowTransformLatest$flowCollect$3$1$emit$1.label;
                if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                    channelFlowTransformLatest$flowCollect$3$1$emit$1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                    Object obj2 = channelFlowTransformLatest$flowCollect$3$1$emit$1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    i = channelFlowTransformLatest$flowCollect$3$1$emit$1.label;
                    if (i != 0) {
                        ResultKt.throwOnFailure(obj2);
                        Job job = (Job) this.$previousFlow.element;
                        if (job != null) {
                            job.cancel(new ChildCancelledException());
                            channelFlowTransformLatest$flowCollect$3$1$emit$1.L$0 = this;
                            channelFlowTransformLatest$flowCollect$3$1$emit$1.L$1 = obj;
                            channelFlowTransformLatest$flowCollect$3$1$emit$1.L$2 = job;
                            channelFlowTransformLatest$flowCollect$3$1$emit$1.label = 1;
                            if (((JobSupport) job).join(channelFlowTransformLatest$flowCollect$3$1$emit$1) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                        }
                    } else {
                        if (i != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        obj = channelFlowTransformLatest$flowCollect$3$1$emit$1.L$1;
                        this = (C48551) channelFlowTransformLatest$flowCollect$3$1$emit$1.L$0;
                        ResultKt.throwOnFailure(obj2);
                    }
                    this.$previousFlow.element = BuildersKt.launch$default(this.$$this$coroutineScope, null, CoroutineStart.UNDISPATCHED, new AnonymousClass2(this.this$0, this.$collector, obj, null), 1);
                    return Unit.INSTANCE;
                }
            }
            channelFlowTransformLatest$flowCollect$3$1$emit$1 = new ChannelFlowTransformLatest$flowCollect$3$1$emit$1(this, continuation);
            Object obj22 = channelFlowTransformLatest$flowCollect$3$1$emit$1.result;
            CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
            i = channelFlowTransformLatest$flowCollect$3$1$emit$1.label;
            if (i != 0) {
            }
            this.$previousFlow.element = BuildersKt.launch$default(this.$$this$coroutineScope, null, CoroutineStart.UNDISPATCHED, new AnonymousClass2(this.this$0, this.$collector, obj, null), 1);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ChannelFlowTransformLatest$flowCollect$3(ChannelFlowTransformLatest channelFlowTransformLatest, FlowCollector flowCollector, Continuation<? super ChannelFlowTransformLatest$flowCollect$3> continuation) {
        super(2, continuation);
        this.this$0 = channelFlowTransformLatest;
        this.$collector = flowCollector;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        ChannelFlowTransformLatest$flowCollect$3 channelFlowTransformLatest$flowCollect$3 = new ChannelFlowTransformLatest$flowCollect$3(this.this$0, this.$collector, continuation);
        channelFlowTransformLatest$flowCollect$3.L$0 = obj;
        return channelFlowTransformLatest$flowCollect$3;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ChannelFlowTransformLatest$flowCollect$3) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
            ChannelFlowTransformLatest channelFlowTransformLatest = this.this$0;
            Flow flow = channelFlowTransformLatest.flow;
            C48551 c48551 = new C48551(ref$ObjectRef, coroutineScope, channelFlowTransformLatest, this.$collector);
            this.label = 1;
            if (flow.collect(c48551, this) == coroutineSingletons) {
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
}
