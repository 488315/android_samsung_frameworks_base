package kotlinx.coroutines.flow;

import android.support.v4.media.AbstractC0000x2c234b15;
import com.samsung.android.nexus.video.VideoPlayer;
import java.util.NoSuchElementException;
import java.util.concurrent.CancellationException;
import kotlin.ExceptionsKt__ExceptionsKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt$asIterable$$inlined$Iterable$1;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.functions.Function5;
import kotlin.jvm.functions.Function6;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CompletedExceptionally;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.JobSupport;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.internal.AbortFlowException;
import kotlinx.coroutines.flow.internal.ChannelFlowOperatorImpl;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;
import kotlinx.coroutines.flow.internal.ChannelLimitedFlowMerge;
import kotlinx.coroutines.flow.internal.CombineKt;
import kotlinx.coroutines.flow.internal.FusibleFlow;
import kotlinx.coroutines.flow.internal.NopCollector;
import kotlinx.coroutines.flow.internal.NullSurrogateKt;
import kotlinx.coroutines.intrinsics.UndispatchedKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class FlowKt {
    public static final ReadonlyStateFlow asStateFlow(MutableStateFlow mutableStateFlow) {
        return new ReadonlyStateFlow(mutableStateFlow, null);
    }

    public static Flow buffer$default(Flow flow, int i) {
        int i2;
        BufferOverflow bufferOverflow;
        BufferOverflow bufferOverflow2 = BufferOverflow.SUSPEND;
        if (!(i >= 0 || i == -2 || i == -1)) {
            throw new IllegalArgumentException(AbstractC0000x2c234b15.m0m("Buffer size should be non-negative, BUFFERED, or CONFLATED, but was ", i).toString());
        }
        if (i == -1) {
            bufferOverflow = BufferOverflow.DROP_OLDEST;
            i2 = 0;
        } else {
            i2 = i;
            bufferOverflow = bufferOverflow2;
        }
        return flow instanceof FusibleFlow ? FusibleFlow.DefaultImpls.fuse$default((FusibleFlow) flow, null, i2, bufferOverflow, 1) : new ChannelFlowOperatorImpl(flow, null, i2, bufferOverflow, 2, null);
    }

    public static final CallbackFlowBuilder callbackFlow(Function2 function2) {
        return new CallbackFlowBuilder(function2, null, 0, null, 14, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0065  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00ae  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0036  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object catchImpl(Continuation continuation, Flow flow, FlowCollector flowCollector) {
        FlowKt__ErrorsKt$catchImpl$1 flowKt__ErrorsKt$catchImpl$1;
        int i;
        Throwable th;
        Ref$ObjectRef ref$ObjectRef;
        Throwable th2;
        if (continuation instanceof FlowKt__ErrorsKt$catchImpl$1) {
            flowKt__ErrorsKt$catchImpl$1 = (FlowKt__ErrorsKt$catchImpl$1) continuation;
            int i2 = flowKt__ErrorsKt$catchImpl$1.label;
            if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                flowKt__ErrorsKt$catchImpl$1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                Object obj = flowKt__ErrorsKt$catchImpl$1.result;
                Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                i = flowKt__ErrorsKt$catchImpl$1.label;
                if (i != 0) {
                    ResultKt.throwOnFailure(obj);
                    Ref$ObjectRef ref$ObjectRef2 = new Ref$ObjectRef();
                    try {
                        FlowCollector flowKt__ErrorsKt$catchImpl$2 = new FlowKt__ErrorsKt$catchImpl$2(flowCollector, ref$ObjectRef2);
                        flowKt__ErrorsKt$catchImpl$1.L$0 = ref$ObjectRef2;
                        flowKt__ErrorsKt$catchImpl$1.label = 1;
                        if (flow.collect(flowKt__ErrorsKt$catchImpl$2, flowKt__ErrorsKt$catchImpl$1) == obj2) {
                            return obj2;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        ref$ObjectRef = ref$ObjectRef2;
                        th2 = (Throwable) ref$ObjectRef.element;
                        boolean z = false;
                        if (!(th2 == null && Intrinsics.areEqual(th2, th))) {
                            throw th;
                        }
                        Job job = (Job) flowKt__ErrorsKt$catchImpl$1.getContext().get(Job.Key);
                        if (job != null) {
                            JobSupport jobSupport = (JobSupport) job;
                            Object m293x8adbf455 = jobSupport.m293x8adbf455();
                            if ((m293x8adbf455 instanceof CompletedExceptionally) || ((m293x8adbf455 instanceof JobSupport.Finishing) && ((JobSupport.Finishing) m293x8adbf455).isCancelling())) {
                                z = Intrinsics.areEqual(jobSupport.getCancellationException(), th);
                            }
                        }
                        if (z) {
                            throw th;
                        }
                        if (th2 == null) {
                            return th;
                        }
                        if (th instanceof CancellationException) {
                            ExceptionsKt__ExceptionsKt.addSuppressed(th2, th);
                            throw th2;
                        }
                        ExceptionsKt__ExceptionsKt.addSuppressed(th, th2);
                        throw th;
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ref$ObjectRef = (Ref$ObjectRef) flowKt__ErrorsKt$catchImpl$1.L$0;
                    try {
                        ResultKt.throwOnFailure(obj);
                    } catch (Throwable th4) {
                        th = th4;
                        th2 = (Throwable) ref$ObjectRef.element;
                        boolean z2 = false;
                        if (!(th2 == null && Intrinsics.areEqual(th2, th))) {
                        }
                    }
                }
                return null;
            }
        }
        flowKt__ErrorsKt$catchImpl$1 = new FlowKt__ErrorsKt$catchImpl$1(continuation);
        Object obj3 = flowKt__ErrorsKt$catchImpl$1.result;
        Object obj22 = CoroutineSingletons.COROUTINE_SUSPENDED;
        i = flowKt__ErrorsKt$catchImpl$1.label;
        if (i != 0) {
        }
        return null;
    }

    public static final Object collect(Flow flow, Continuation continuation) {
        Object collect = flow.collect(NopCollector.INSTANCE, continuation);
        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
    }

    public static final Object collectLatest(Flow flow, Function2 function2, Continuation continuation) {
        Object collect = collect(buffer$default(mapLatest(flow, function2), 0), continuation);
        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
    }

    public static final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 combine(Flow flow, Flow flow2, Flow flow3, Function4 function4) {
        return new FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1(new Flow[]{flow, flow2, flow3}, function4);
    }

    public static final Flow debounce(final long j, FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1) {
        if (!(j >= 0)) {
            throw new IllegalArgumentException("Debounce timeout should not be negative".toString());
        }
        if (j == 0) {
            return flowKt__ZipKt$combine$$inlined$unsafeFlow$1;
        }
        final FlowKt__DelayKt$debounceInternal$1 flowKt__DelayKt$debounceInternal$1 = new FlowKt__DelayKt$debounceInternal$1(new Function1() { // from class: kotlinx.coroutines.flow.FlowKt__DelayKt$debounce$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Long.valueOf(j);
            }
        }, flowKt__ZipKt$combine$$inlined$unsafeFlow$1, null);
        return new Flow() { // from class: kotlinx.coroutines.flow.internal.FlowCoroutineKt$scopedFlow$$inlined$unsafeFlow$1
            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                FlowCoroutineKt$scopedFlow$1$1 flowCoroutineKt$scopedFlow$1$1 = new FlowCoroutineKt$scopedFlow$1$1(Function3.this, flowCollector, null);
                FlowCoroutine flowCoroutine = new FlowCoroutine(continuation.getContext(), continuation);
                Object startUndispatchedOrReturn = UndispatchedKt.startUndispatchedOrReturn(flowCoroutine, flowCoroutine, flowCoroutineKt$scopedFlow$1$1);
                return startUndispatchedOrReturn == CoroutineSingletons.COROUTINE_SUSPENDED ? startUndispatchedOrReturn : Unit.INSTANCE;
            }
        };
    }

    public static final Flow distinctUntilChanged(Flow flow) {
        Function1 function1 = FlowKt__DistinctKt.defaultKeySelector;
        if (flow instanceof StateFlow) {
            return flow;
        }
        Function1 function12 = FlowKt__DistinctKt.defaultKeySelector;
        Function2 function2 = FlowKt__DistinctKt.defaultAreEquivalent;
        if (flow instanceof DistinctFlowImpl) {
            DistinctFlowImpl distinctFlowImpl = (DistinctFlowImpl) flow;
            if (distinctFlowImpl.keySelector == function12 && distinctFlowImpl.areEquivalent == function2) {
                return flow;
            }
        }
        return new DistinctFlowImpl(flow, function12, function2);
    }

    public static final Object emitAll(Continuation continuation, Flow flow, FlowCollector flowCollector) {
        if (flowCollector instanceof ThrowingCollector) {
            throw ((ThrowingCollector) flowCollector).f668e;
        }
        Object collect = flow.collect(flowCollector, continuation);
        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0070  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0039  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /* JADX WARN: Type inference failed for: r2v1, types: [T, kotlinx.coroutines.internal.Symbol] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object first(Flow flow, Continuation continuation) {
        FlowKt__ReduceKt$first$1 flowKt__ReduceKt$first$1;
        Object obj;
        int i;
        Ref$ObjectRef ref$ObjectRef;
        AbortFlowException e;
        FlowCollector flowCollector;
        if (continuation instanceof FlowKt__ReduceKt$first$1) {
            flowKt__ReduceKt$first$1 = (FlowKt__ReduceKt$first$1) continuation;
            int i2 = flowKt__ReduceKt$first$1.label;
            if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                flowKt__ReduceKt$first$1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                Object obj2 = flowKt__ReduceKt$first$1.result;
                obj = CoroutineSingletons.COROUTINE_SUSPENDED;
                i = flowKt__ReduceKt$first$1.label;
                if (i != 0) {
                    ResultKt.throwOnFailure(obj2);
                    final Ref$ObjectRef ref$ObjectRef2 = new Ref$ObjectRef();
                    ref$ObjectRef2.element = NullSurrogateKt.NULL;
                    FlowCollector flowCollector2 = new FlowCollector() { // from class: kotlinx.coroutines.flow.FlowKt__ReduceKt$first$$inlined$collectWhile$1
                        /* JADX WARN: Multi-variable type inference failed */
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj3, Continuation continuation2) {
                            Ref$ObjectRef.this.element = obj3;
                            throw new AbortFlowException(this);
                        }
                    };
                    try {
                        flowKt__ReduceKt$first$1.L$0 = ref$ObjectRef2;
                        flowKt__ReduceKt$first$1.L$1 = flowCollector2;
                        flowKt__ReduceKt$first$1.label = 1;
                        if (flow.collect(flowCollector2, flowKt__ReduceKt$first$1) != obj) {
                            ref$ObjectRef = ref$ObjectRef2;
                        }
                    } catch (AbortFlowException e2) {
                        ref$ObjectRef = ref$ObjectRef2;
                        e = e2;
                        flowCollector = flowCollector2;
                        if (e.owner != flowCollector) {
                        }
                        obj = ref$ObjectRef.element;
                        if (obj == NullSurrogateKt.NULL) {
                        }
                        return obj;
                    }
                    return obj;
                }
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                flowCollector = (FlowKt__ReduceKt$first$$inlined$collectWhile$1) flowKt__ReduceKt$first$1.L$1;
                ref$ObjectRef = (Ref$ObjectRef) flowKt__ReduceKt$first$1.L$0;
                try {
                    ResultKt.throwOnFailure(obj2);
                } catch (AbortFlowException e3) {
                    e = e3;
                    if (e.owner != flowCollector) {
                        throw e;
                    }
                    obj = ref$ObjectRef.element;
                    if (obj == NullSurrogateKt.NULL) {
                    }
                    return obj;
                }
                obj = ref$ObjectRef.element;
                if (obj == NullSurrogateKt.NULL) {
                    throw new NoSuchElementException("Expected at least one element");
                }
                return obj;
            }
        }
        flowKt__ReduceKt$first$1 = new FlowKt__ReduceKt$first$1(continuation);
        Object obj22 = flowKt__ReduceKt$first$1.result;
        obj = CoroutineSingletons.COROUTINE_SUSPENDED;
        i = flowKt__ReduceKt$first$1.label;
        if (i != 0) {
        }
        obj = ref$ObjectRef.element;
        if (obj == NullSurrogateKt.NULL) {
        }
        return obj;
    }

    public static final Flow flowOn(Flow flow, CoroutineContext coroutineContext) {
        if (coroutineContext.get(Job.Key) == null) {
            return Intrinsics.areEqual(coroutineContext, EmptyCoroutineContext.INSTANCE) ? flow : flow instanceof FusibleFlow ? FusibleFlow.DefaultImpls.fuse$default((FusibleFlow) flow, coroutineContext, 0, null, 6) : new ChannelFlowOperatorImpl(flow, coroutineContext, 0, null, 12, null);
        }
        throw new IllegalArgumentException(("Flow context cannot contain job in it. Had " + coroutineContext).toString());
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object lastOrNull(FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 flowKt__TransformKt$onEach$$inlined$unsafeTransform$1, Continuation continuation) {
        FlowKt__ReduceKt$lastOrNull$1 flowKt__ReduceKt$lastOrNull$1;
        int i;
        Ref$ObjectRef ref$ObjectRef;
        if (continuation instanceof FlowKt__ReduceKt$lastOrNull$1) {
            flowKt__ReduceKt$lastOrNull$1 = (FlowKt__ReduceKt$lastOrNull$1) continuation;
            int i2 = flowKt__ReduceKt$lastOrNull$1.label;
            if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                flowKt__ReduceKt$lastOrNull$1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                Object obj = flowKt__ReduceKt$lastOrNull$1.result;
                Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                i = flowKt__ReduceKt$lastOrNull$1.label;
                if (i != 0) {
                    ResultKt.throwOnFailure(obj);
                    final Ref$ObjectRef ref$ObjectRef2 = new Ref$ObjectRef();
                    FlowCollector flowCollector = new FlowCollector() { // from class: kotlinx.coroutines.flow.FlowKt__ReduceKt$lastOrNull$2
                        /* JADX WARN: Multi-variable type inference failed */
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj3, Continuation continuation2) {
                            ref$ObjectRef2.element = obj3;
                            return Unit.INSTANCE;
                        }
                    };
                    flowKt__ReduceKt$lastOrNull$1.L$0 = ref$ObjectRef2;
                    flowKt__ReduceKt$lastOrNull$1.label = 1;
                    if (flowKt__TransformKt$onEach$$inlined$unsafeTransform$1.collect(flowCollector, flowKt__ReduceKt$lastOrNull$1) == obj2) {
                        return obj2;
                    }
                    ref$ObjectRef = ref$ObjectRef2;
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ref$ObjectRef = (Ref$ObjectRef) flowKt__ReduceKt$lastOrNull$1.L$0;
                    ResultKt.throwOnFailure(obj);
                }
                return ref$ObjectRef.element;
            }
        }
        flowKt__ReduceKt$lastOrNull$1 = new FlowKt__ReduceKt$lastOrNull$1(continuation);
        Object obj3 = flowKt__ReduceKt$lastOrNull$1.result;
        Object obj22 = CoroutineSingletons.COROUTINE_SUSPENDED;
        i = flowKt__ReduceKt$lastOrNull$1.label;
        if (i != 0) {
        }
        return ref$ObjectRef.element;
    }

    public static final void launchIn(Flow flow, CoroutineScope coroutineScope) {
        BuildersKt.launch$default(coroutineScope, null, null, new FlowKt__CollectKt$launchIn$1(flow, null), 3);
    }

    public static final ChannelFlowTransformLatest mapLatest(Flow flow, Function2 function2) {
        int i = FlowKt__MergeKt.$r8$clinit;
        return transformLatest(flow, new FlowKt__MergeKt$mapLatest$1(function2, null));
    }

    public static final ChannelLimitedFlowMerge merge(Flow... flowArr) {
        int i = FlowKt__MergeKt.$r8$clinit;
        return new ChannelLimitedFlowMerge(flowArr.length == 0 ? EmptyList.INSTANCE : new ArraysKt___ArraysKt$asIterable$$inlined$Iterable$1(flowArr), null, 0, null, 14, null);
    }

    public static final ReadonlySharedFlow shareIn(Flow flow, CoroutineScope coroutineScope, SharingStarted sharingStarted, int i) {
        SharingConfig configureSharing$FlowKt__ShareKt = FlowKt__ShareKt.configureSharing$FlowKt__ShareKt(flow, i);
        SharedFlowImpl MutableSharedFlow = SharedFlowKt.MutableSharedFlow(i, configureSharing$FlowKt__ShareKt.extraBufferCapacity, configureSharing$FlowKt__ShareKt.onBufferOverflow);
        return new ReadonlySharedFlow(MutableSharedFlow, FlowKt__ShareKt.launchSharing$FlowKt__ShareKt(coroutineScope, configureSharing$FlowKt__ShareKt.context, configureSharing$FlowKt__ShareKt.upstream, MutableSharedFlow, sharingStarted, SharedFlowKt.NO_VALUE));
    }

    public static final ReadonlyStateFlow stateIn(Flow flow, CoroutineScope coroutineScope, SharingStarted sharingStarted, Object obj) {
        SharingConfig configureSharing$FlowKt__ShareKt = FlowKt__ShareKt.configureSharing$FlowKt__ShareKt(flow, 1);
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(obj);
        return new ReadonlyStateFlow(MutableStateFlow, FlowKt__ShareKt.launchSharing$FlowKt__ShareKt(coroutineScope, configureSharing$FlowKt__ShareKt.context, configureSharing$FlowKt__ShareKt.upstream, MutableStateFlow, sharingStarted, obj));
    }

    public static final ChannelFlowTransformLatest transformLatest(Flow flow, Function3 function3) {
        int i = FlowKt__MergeKt.$r8$clinit;
        return new ChannelFlowTransformLatest(function3, flow, null, 0, null, 28, null);
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2] */
    public static final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2 combine(Flow flow, Flow flow2, Flow flow3, Flow flow4, final Function5 function5) {
        final Flow[] flowArr = {flow, flow2, flow3, flow4};
        return new Flow() { // from class: kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            @DebugMetadata(m276c = "kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2$2", m277f = "Zip.kt", m278l = {333, 262}, m279m = "invokeSuspend")
            /* renamed from: kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2$2 */
            public final class C48522 extends SuspendLambda implements Function3 {
                final /* synthetic */ Function5 $transform$inlined;
                private /* synthetic */ Object L$0;
                /* synthetic */ Object L$1;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C48522(Continuation continuation, Function5 function5) {
                    super(3, continuation);
                    this.$transform$inlined = function5;
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    C48522 c48522 = new C48522((Continuation) obj3, this.$transform$inlined);
                    c48522.L$0 = (FlowCollector) obj;
                    c48522.L$1 = (Object[]) obj2;
                    return c48522.invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    FlowCollector flowCollector;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        flowCollector = (FlowCollector) this.L$0;
                        Object[] objArr = (Object[]) this.L$1;
                        Function5 function5 = this.$transform$inlined;
                        Object obj2 = objArr[0];
                        Object obj3 = objArr[1];
                        Object obj4 = objArr[2];
                        Object obj5 = objArr[3];
                        this.L$0 = flowCollector;
                        this.label = 1;
                        obj = function5.invoke(obj2, obj3, obj4, obj5, this);
                        if (obj == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i != 1) {
                            if (i != 2) {
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                            }
                            ResultKt.throwOnFailure(obj);
                            return Unit.INSTANCE;
                        }
                        flowCollector = (FlowCollector) this.L$0;
                        ResultKt.throwOnFailure(obj);
                    }
                    this.L$0 = null;
                    this.label = 2;
                    if (flowCollector.emit(obj, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object combineInternal = CombineKt.combineInternal(flowArr, FlowKt__ZipKt$nullArrayFactory$1.INSTANCE, new C48522(null, function5), flowCollector, continuation);
                return combineInternal == CoroutineSingletons.COROUTINE_SUSPENDED ? combineInternal : Unit.INSTANCE;
            }
        };
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3] */
    public static final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3 combine(Flow flow, Flow flow2, Flow flow3, Flow flow4, Flow flow5, final Function6 function6) {
        final Flow[] flowArr = {flow, flow2, flow3, flow4, flow5};
        return new Flow() { // from class: kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            @DebugMetadata(m276c = "kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3$2", m277f = "Zip.kt", m278l = {333, 262}, m279m = "invokeSuspend")
            /* renamed from: kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3$2 */
            public final class C48532 extends SuspendLambda implements Function3 {
                final /* synthetic */ Function6 $transform$inlined;
                private /* synthetic */ Object L$0;
                /* synthetic */ Object L$1;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C48532(Continuation continuation, Function6 function6) {
                    super(3, continuation);
                    this.$transform$inlined = function6;
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    C48532 c48532 = new C48532((Continuation) obj3, this.$transform$inlined);
                    c48532.L$0 = (FlowCollector) obj;
                    c48532.L$1 = (Object[]) obj2;
                    return c48532.invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    FlowCollector flowCollector;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        flowCollector = (FlowCollector) this.L$0;
                        Object[] objArr = (Object[]) this.L$1;
                        Function6 function6 = this.$transform$inlined;
                        Object obj2 = objArr[0];
                        Object obj3 = objArr[1];
                        Object obj4 = objArr[2];
                        Object obj5 = objArr[3];
                        Object obj6 = objArr[4];
                        this.L$0 = flowCollector;
                        this.label = 1;
                        obj = function6.invoke(obj2, obj3, obj4, obj5, obj6, this);
                        if (obj == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i != 1) {
                            if (i != 2) {
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                            }
                            ResultKt.throwOnFailure(obj);
                            return Unit.INSTANCE;
                        }
                        flowCollector = (FlowCollector) this.L$0;
                        ResultKt.throwOnFailure(obj);
                    }
                    this.L$0 = null;
                    this.label = 2;
                    if (flowCollector.emit(obj, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object combineInternal = CombineKt.combineInternal(flowArr, FlowKt__ZipKt$nullArrayFactory$1.INSTANCE, new C48532(null, function6), flowCollector, continuation);
                return combineInternal == CoroutineSingletons.COROUTINE_SUSPENDED ? combineInternal : Unit.INSTANCE;
            }
        };
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x005e, code lost:
    
        if (r5.collect(r2, r0) == r1) goto L29;
     */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0071  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0085  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0041  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /* JADX WARN: Type inference failed for: r2v1, types: [T, kotlinx.coroutines.internal.Symbol] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object first(Flow flow, Function2 function2, Continuation continuation) {
        FlowKt__ReduceKt$first$3 flowKt__ReduceKt$first$3;
        Object obj;
        int i;
        Ref$ObjectRef ref$ObjectRef;
        AbortFlowException abortFlowException;
        FlowCollector flowCollector;
        Function2 function22;
        Ref$ObjectRef ref$ObjectRef2;
        if (continuation instanceof FlowKt__ReduceKt$first$3) {
            flowKt__ReduceKt$first$3 = (FlowKt__ReduceKt$first$3) continuation;
            int i2 = flowKt__ReduceKt$first$3.label;
            if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                flowKt__ReduceKt$first$3.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                Object obj2 = flowKt__ReduceKt$first$3.result;
                obj = CoroutineSingletons.COROUTINE_SUSPENDED;
                i = flowKt__ReduceKt$first$3.label;
                if (i == 0) {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    flowCollector = (FlowKt__ReduceKt$first$$inlined$collectWhile$2) flowKt__ReduceKt$first$3.L$2;
                    ref$ObjectRef2 = (Ref$ObjectRef) flowKt__ReduceKt$first$3.L$1;
                    function22 = (Function2) flowKt__ReduceKt$first$3.L$0;
                    try {
                        ResultKt.throwOnFailure(obj2);
                    } catch (AbortFlowException e) {
                        ref$ObjectRef = ref$ObjectRef2;
                        function2 = function22;
                        abortFlowException = e;
                    }
                    obj = ref$ObjectRef2.element;
                    if (obj == NullSurrogateKt.NULL) {
                        throw new NoSuchElementException("Expected at least one element matching the predicate " + function22);
                    }
                    return obj;
                }
                ResultKt.throwOnFailure(obj2);
                ref$ObjectRef = new Ref$ObjectRef();
                ref$ObjectRef.element = NullSurrogateKt.NULL;
                FlowCollector flowKt__ReduceKt$first$$inlined$collectWhile$2 = new FlowKt__ReduceKt$first$$inlined$collectWhile$2(function2, ref$ObjectRef);
                try {
                    flowKt__ReduceKt$first$3.L$0 = function2;
                    flowKt__ReduceKt$first$3.L$1 = ref$ObjectRef;
                    flowKt__ReduceKt$first$3.L$2 = flowKt__ReduceKt$first$$inlined$collectWhile$2;
                    flowKt__ReduceKt$first$3.label = 1;
                } catch (AbortFlowException e2) {
                    abortFlowException = e2;
                    flowCollector = flowKt__ReduceKt$first$$inlined$collectWhile$2;
                }
                if (abortFlowException.owner != flowCollector) {
                    throw abortFlowException;
                }
                function22 = function2;
                ref$ObjectRef2 = ref$ObjectRef;
                obj = ref$ObjectRef2.element;
                if (obj == NullSurrogateKt.NULL) {
                }
                return obj;
            }
        }
        flowKt__ReduceKt$first$3 = new FlowKt__ReduceKt$first$3(continuation);
        Object obj22 = flowKt__ReduceKt$first$3.result;
        obj = CoroutineSingletons.COROUTINE_SUSPENDED;
        i = flowKt__ReduceKt$first$3.label;
        if (i == 0) {
        }
        if (abortFlowException.owner != flowCollector) {
        }
        function22 = function2;
        ref$ObjectRef2 = ref$ObjectRef;
        obj = ref$ObjectRef2.element;
        if (obj == NullSurrogateKt.NULL) {
        }
        return obj;
    }
}
