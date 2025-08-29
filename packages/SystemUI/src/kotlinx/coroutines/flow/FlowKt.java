package kotlinx.coroutines.flow;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.concurrent.CancellationException;
import kotlin.ExceptionsKt__ExceptionsKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.functions.Function5;
import kotlin.jvm.functions.Function6;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$IntRef;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CompletableDeferredImpl;
import kotlinx.coroutines.CompletableDeferredKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.JobKt;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.channels.BufferedChannel;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.internal.AbortFlowException;
import kotlinx.coroutines.flow.internal.ChannelFlowOperatorImpl;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;
import kotlinx.coroutines.flow.internal.ChannelLimitedFlowMerge;
import kotlinx.coroutines.flow.internal.FlowCoroutineKt$scopedFlow$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.internal.FusibleFlow;
import kotlinx.coroutines.flow.internal.NopCollector;
import kotlinx.coroutines.flow.internal.NullSurrogateKt;
import kotlinx.coroutines.internal.Symbol;

/* loaded from: classes4.dex */
public abstract class FlowKt {
    public static final ReadonlySharedFlow asSharedFlow(SharedFlowImpl sharedFlowImpl) {
        return new ReadonlySharedFlow(sharedFlowImpl, null);
    }

    public static final ReadonlyStateFlow asStateFlow(MutableStateFlow mutableStateFlow) {
        return new ReadonlyStateFlow(mutableStateFlow, null);
    }

    public static final Flow buffer(Flow flow, int i, BufferOverflow bufferOverflow) {
        if (i < 0 && i != -2 && i != -1) {
            throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Buffer size should be non-negative, BUFFERED, or CONFLATED, but was ").toString());
        }
        if (i == -1 && bufferOverflow != BufferOverflow.SUSPEND) {
            throw new IllegalArgumentException("CONFLATED capacity cannot be used with non-default onBufferOverflow");
        }
        if (i == -1) {
            bufferOverflow = BufferOverflow.DROP_OLDEST;
            i = 0;
        }
        int i2 = i;
        BufferOverflow bufferOverflow2 = bufferOverflow;
        return flow instanceof FusibleFlow ? FusibleFlow.DefaultImpls.fuse$default((FusibleFlow) flow, null, i2, bufferOverflow2, 1) : new ChannelFlowOperatorImpl(flow, null, i2, bufferOverflow2, 2, null);
    }

    public static /* synthetic */ Flow buffer$default(Flow flow, int i, int i2) {
        if ((i2 & 1) != 0) {
            i = -2;
        }
        return buffer(flow, i, BufferOverflow.SUSPEND);
    }

    public static final CallbackFlowBuilder callbackFlow(Function2 function2) {
        return new CallbackFlowBuilder(function2, null, 0, null, 14, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object catchImpl(Flow flow, FlowCollector flowCollector, ContinuationImpl continuationImpl) throws Throwable {
        FlowKt__ErrorsKt$catchImpl$1 flowKt__ErrorsKt$catchImpl$1;
        Ref$ObjectRef ref$ObjectRef;
        Job job;
        CancellationException cancellationException;
        if (continuationImpl instanceof FlowKt__ErrorsKt$catchImpl$1) {
            flowKt__ErrorsKt$catchImpl$1 = (FlowKt__ErrorsKt$catchImpl$1) continuationImpl;
            int i = flowKt__ErrorsKt$catchImpl$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                flowKt__ErrorsKt$catchImpl$1.label = i - Integer.MIN_VALUE;
            } else {
                flowKt__ErrorsKt$catchImpl$1 = new FlowKt__ErrorsKt$catchImpl$1(continuationImpl);
            }
        }
        Object obj = flowKt__ErrorsKt$catchImpl$1.result;
        Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = flowKt__ErrorsKt$catchImpl$1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            Ref$ObjectRef ref$ObjectRef2 = new Ref$ObjectRef();
            try {
                FlowCollector flowKt__ErrorsKt$catchImpl$2 = new FlowKt__ErrorsKt$catchImpl$2(flowCollector, ref$ObjectRef2);
                flowKt__ErrorsKt$catchImpl$1.L$0 = ref$ObjectRef2;
                flowKt__ErrorsKt$catchImpl$1.label = 1;
                if (flow.collect(flowKt__ErrorsKt$catchImpl$2, flowKt__ErrorsKt$catchImpl$1) == obj2) {
                    return obj2;
                }
                return null;
            } catch (Throwable th) {
                th = th;
                ref$ObjectRef = ref$ObjectRef2;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ref$ObjectRef = (Ref$ObjectRef) flowKt__ErrorsKt$catchImpl$1.L$0;
            try {
                ResultKt.throwOnFailure(obj);
                return null;
            } catch (Throwable th2) {
                th = th2;
            }
        }
        Throwable th3 = (Throwable) ref$ObjectRef.element;
        if ((th3 != null && th3.equals(th)) || ((job = (Job) flowKt__ErrorsKt$catchImpl$1.getContext().get(Job.Key)) != null && job.isCancelled$1() && (cancellationException = job.getCancellationException()) != null && cancellationException.equals(th))) {
            throw th;
        }
        if (th3 == null) {
            return th;
        }
        if (th instanceof CancellationException) {
            ExceptionsKt__ExceptionsKt.addSuppressed(th3, th);
            throw th3;
        }
        ExceptionsKt__ExceptionsKt.addSuppressed(th, th3);
        throw th;
    }

    public static final Object collect(Flow flow, Continuation continuation) {
        Object objCollect = flow.collect(NopCollector.INSTANCE, continuation);
        return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
    }

    public static final Object collectLatest(Flow flow, Function2 function2, Continuation continuation) {
        Object objCollect = collect(buffer$default(mapLatest(flow, function2), 0, 2), continuation);
        return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
    }

    public static final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 combine(Flow flow, Flow flow2, Flow flow3, Function4 function4) {
        return new FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1(new Flow[]{flow, flow2, flow3}, function4);
    }

    public static final SafeFlow combineTransform(Flow flow, Flow flow2, Function4 function4) {
        return new SafeFlow(new FlowKt__ZipKt$combineTransform$$inlined$combineTransformUnsafe$FlowKt__ZipKt$2(new Flow[]{flow, flow2}, null, function4));
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object count(FlowKt__LimitKt$take$$inlined$unsafeFlow$1 flowKt__LimitKt$take$$inlined$unsafeFlow$1, ContinuationImpl continuationImpl) {
        FlowKt__CountKt$count$1 flowKt__CountKt$count$1;
        Ref$IntRef ref$IntRef;
        if (continuationImpl instanceof FlowKt__CountKt$count$1) {
            flowKt__CountKt$count$1 = (FlowKt__CountKt$count$1) continuationImpl;
            int i = flowKt__CountKt$count$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                flowKt__CountKt$count$1.label = i - Integer.MIN_VALUE;
            } else {
                flowKt__CountKt$count$1 = new FlowKt__CountKt$count$1(continuationImpl);
            }
        }
        Object obj = flowKt__CountKt$count$1.result;
        Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = flowKt__CountKt$count$1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            final Ref$IntRef ref$IntRef2 = new Ref$IntRef();
            FlowCollector flowCollector = new FlowCollector() { // from class: kotlinx.coroutines.flow.FlowKt__CountKt$count$2
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj3, Continuation continuation) {
                    ref$IntRef2.element++;
                    return Unit.INSTANCE;
                }
            };
            flowKt__CountKt$count$1.L$0 = ref$IntRef2;
            flowKt__CountKt$count$1.label = 1;
            if (flowKt__LimitKt$take$$inlined$unsafeFlow$1.collect(flowCollector, flowKt__CountKt$count$1) == obj2) {
                return obj2;
            }
            ref$IntRef = ref$IntRef2;
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ref$IntRef = (Ref$IntRef) flowKt__CountKt$count$1.L$0;
            ResultKt.throwOnFailure(obj);
        }
        return new Integer(ref$IntRef.element);
    }

    public static final Flow debounce(Flow flow, final long j) {
        if (j >= 0) {
            return j == 0 ? flow : new FlowCoroutineKt$scopedFlow$$inlined$unsafeFlow$1(new FlowKt__DelayKt$debounceInternal$1(new Function1() { // from class: kotlinx.coroutines.flow.FlowKt__DelayKt$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj) {
                    return Long.valueOf(j);
                }
            }, flow, null));
        }
        throw new IllegalArgumentException("Debounce timeout should not be negative");
    }

    /* renamed from: debounce-HG0u8IE, reason: not valid java name */
    public static final Flow m3481debounceHG0u8IE(Flow flow, long j) {
        return debounce(flow, DelayKt.m3469toDelayMillisLRDsOJo(j));
    }

    public static final Flow distinctUntilChanged(Flow flow) {
        return flow instanceof StateFlow ? flow : FlowKt__DistinctKt.distinctUntilChangedBy$FlowKt__DistinctKt(flow, FlowKt__DistinctKt.defaultKeySelector, FlowKt__DistinctKt.defaultAreEquivalent);
    }

    public static final FlowKt__LimitKt$drop$$inlined$unsafeFlow$1 drop(Flow flow) {
        return new FlowKt__LimitKt$drop$$inlined$unsafeFlow$1(flow, 1);
    }

    public static final Object emitAll(FlowCollector flowCollector, Flow flow, Continuation continuation) throws Throwable {
        if (flowCollector instanceof ThrowingCollector) {
            throw ((ThrowingCollector) flowCollector).e;
        }
        Object objCollect = flow.collect(flowCollector, continuation);
        return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0061  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x006e A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x006f  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0077  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Type inference failed for: r2v1, types: [T, kotlinx.coroutines.internal.Symbol] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object first(Flow flow, Continuation continuation) {
        FlowKt__ReduceKt$first$1 flowKt__ReduceKt$first$1;
        Ref$ObjectRef ref$ObjectRef;
        AbortFlowException e;
        FlowCollector flowCollector;
        T t;
        if (continuation instanceof FlowKt__ReduceKt$first$1) {
            flowKt__ReduceKt$first$1 = (FlowKt__ReduceKt$first$1) continuation;
            int i = flowKt__ReduceKt$first$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                flowKt__ReduceKt$first$1.label = i - Integer.MIN_VALUE;
            } else {
                flowKt__ReduceKt$first$1 = new FlowKt__ReduceKt$first$1(continuation);
            }
        }
        Object obj = flowKt__ReduceKt$first$1.result;
        Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = flowKt__ReduceKt$first$1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            final Ref$ObjectRef ref$ObjectRef2 = new Ref$ObjectRef();
            ref$ObjectRef2.element = NullSurrogateKt.NULL;
            FlowCollector flowCollector2 = new FlowCollector() { // from class: kotlinx.coroutines.flow.FlowKt__ReduceKt$first$$inlined$collectWhile$1
                /* JADX WARN: Multi-variable type inference failed */
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj3, Continuation continuation2) {
                    ref$ObjectRef2.element = obj3;
                    throw new AbortFlowException(this);
                }
            };
            try {
                flowKt__ReduceKt$first$1.L$0 = ref$ObjectRef2;
                flowKt__ReduceKt$first$1.L$1 = flowCollector2;
                flowKt__ReduceKt$first$1.label = 1;
                if (flow.collect(flowCollector2, flowKt__ReduceKt$first$1) == obj2) {
                    return obj2;
                }
                ref$ObjectRef = ref$ObjectRef2;
            } catch (AbortFlowException e2) {
                ref$ObjectRef = ref$ObjectRef2;
                e = e2;
                flowCollector = flowCollector2;
                if (e.owner == flowCollector) {
                }
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            flowCollector = (FlowKt__ReduceKt$first$$inlined$collectWhile$1) flowKt__ReduceKt$first$1.L$1;
            ref$ObjectRef = (Ref$ObjectRef) flowKt__ReduceKt$first$1.L$0;
            try {
                ResultKt.throwOnFailure(obj);
            } catch (AbortFlowException e3) {
                e = e3;
                if (e.owner == flowCollector) {
                    throw e;
                }
                JobKt.ensureActive(flowKt__ReduceKt$first$1.getContext());
                t = ref$ObjectRef.element;
                if (t == NullSurrogateKt.NULL) {
                }
            }
        }
        t = ref$ObjectRef.element;
        if (t == NullSurrogateKt.NULL) {
            return t;
        }
        throw new NoSuchElementException("Expected at least one element");
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x005d  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0067  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object firstOrNull(Flow flow, ContinuationImpl continuationImpl) {
        FlowKt__ReduceKt$firstOrNull$1 flowKt__ReduceKt$firstOrNull$1;
        Ref$ObjectRef ref$ObjectRef;
        AbortFlowException e;
        FlowCollector flowCollector;
        if (continuationImpl instanceof FlowKt__ReduceKt$firstOrNull$1) {
            flowKt__ReduceKt$firstOrNull$1 = (FlowKt__ReduceKt$firstOrNull$1) continuationImpl;
            int i = flowKt__ReduceKt$firstOrNull$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                flowKt__ReduceKt$firstOrNull$1.label = i - Integer.MIN_VALUE;
            } else {
                flowKt__ReduceKt$firstOrNull$1 = new FlowKt__ReduceKt$firstOrNull$1(continuationImpl);
            }
        }
        Object obj = flowKt__ReduceKt$firstOrNull$1.result;
        Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = flowKt__ReduceKt$firstOrNull$1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            final Ref$ObjectRef ref$ObjectRef2 = new Ref$ObjectRef();
            FlowCollector flowCollector2 = new FlowCollector() { // from class: kotlinx.coroutines.flow.FlowKt__ReduceKt$firstOrNull$$inlined$collectWhile$1
                /* JADX WARN: Multi-variable type inference failed */
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj3, Continuation continuation) {
                    ref$ObjectRef2.element = obj3;
                    throw new AbortFlowException(this);
                }
            };
            try {
                flowKt__ReduceKt$firstOrNull$1.L$0 = ref$ObjectRef2;
                flowKt__ReduceKt$firstOrNull$1.L$1 = flowCollector2;
                flowKt__ReduceKt$firstOrNull$1.label = 1;
                if (flow.collect(flowCollector2, flowKt__ReduceKt$firstOrNull$1) == obj2) {
                    return obj2;
                }
                ref$ObjectRef = ref$ObjectRef2;
            } catch (AbortFlowException e2) {
                ref$ObjectRef = ref$ObjectRef2;
                e = e2;
                flowCollector = flowCollector2;
                if (e.owner == flowCollector) {
                }
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            flowCollector = (FlowKt__ReduceKt$firstOrNull$$inlined$collectWhile$1) flowKt__ReduceKt$firstOrNull$1.L$1;
            ref$ObjectRef = (Ref$ObjectRef) flowKt__ReduceKt$firstOrNull$1.L$0;
            try {
                ResultKt.throwOnFailure(obj);
            } catch (AbortFlowException e3) {
                e = e3;
                if (e.owner == flowCollector) {
                    throw e;
                }
                JobKt.ensureActive(flowKt__ReduceKt$firstOrNull$1.getContext());
                return ref$ObjectRef.element;
            }
        }
        return ref$ObjectRef.element;
    }

    public static final Flow flowOn(Flow flow, CoroutineContext coroutineContext) {
        if (coroutineContext.get(Job.Key) != null) {
            throw new IllegalArgumentException(("Flow context cannot contain job in it. Had " + coroutineContext).toString());
        }
        if (coroutineContext.equals(EmptyCoroutineContext.INSTANCE)) {
            return flow;
        }
        if (flow instanceof FusibleFlow) {
            return FusibleFlow.DefaultImpls.fuse$default((FusibleFlow) flow, coroutineContext, 0, null, 6);
        }
        return new ChannelFlowOperatorImpl(flow, coroutineContext, 0, null, 12, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object lastOrNull(FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 flowKt__TransformKt$onEach$$inlined$unsafeTransform$1, ContinuationImpl continuationImpl) {
        FlowKt__ReduceKt$lastOrNull$1 flowKt__ReduceKt$lastOrNull$1;
        Ref$ObjectRef ref$ObjectRef;
        if (continuationImpl instanceof FlowKt__ReduceKt$lastOrNull$1) {
            flowKt__ReduceKt$lastOrNull$1 = (FlowKt__ReduceKt$lastOrNull$1) continuationImpl;
            int i = flowKt__ReduceKt$lastOrNull$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                flowKt__ReduceKt$lastOrNull$1.label = i - Integer.MIN_VALUE;
            } else {
                flowKt__ReduceKt$lastOrNull$1 = new FlowKt__ReduceKt$lastOrNull$1(continuationImpl);
            }
        }
        Object obj = flowKt__ReduceKt$lastOrNull$1.result;
        Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = flowKt__ReduceKt$lastOrNull$1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            final Ref$ObjectRef ref$ObjectRef2 = new Ref$ObjectRef();
            FlowCollector flowCollector = new FlowCollector() { // from class: kotlinx.coroutines.flow.FlowKt__ReduceKt$lastOrNull$2
                /* JADX WARN: Multi-variable type inference failed */
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj3, Continuation continuation) {
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
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ref$ObjectRef = (Ref$ObjectRef) flowKt__ReduceKt$lastOrNull$1.L$0;
            ResultKt.throwOnFailure(obj);
        }
        return ref$ObjectRef.element;
    }

    public static final StandaloneCoroutine launchIn(Flow flow, CoroutineScope coroutineScope) {
        return BuildersKt.launch$default(coroutineScope, null, null, new FlowKt__CollectKt$launchIn$1(flow, null), 3);
    }

    public static final ChannelFlowTransformLatest mapLatest(Flow flow, Function2 function2) {
        int i = FlowKt__MergeKt.$r8$clinit;
        return transformLatest(flow, new FlowKt__MergeKt$mapLatest$1(function2, null));
    }

    public static final ChannelLimitedFlowMerge merge(Iterable iterable) {
        int i = FlowKt__MergeKt.$r8$clinit;
        return new ChannelLimitedFlowMerge(iterable, null, 0, null, 14, null);
    }

    public static final ChannelAsFlow receiveAsFlow(BufferedChannel bufferedChannel) {
        return new ChannelAsFlow(bufferedChannel, false, null, 0, null, 28, null);
    }

    public static final ReadonlySharedFlow shareIn(Flow flow, CoroutineScope coroutineScope, SharingStarted sharingStarted, int i) {
        SharingConfig sharingConfigConfigureSharing$FlowKt__ShareKt = FlowKt__ShareKt.configureSharing$FlowKt__ShareKt(flow, i);
        SharedFlowImpl sharedFlowImplMutableSharedFlow = SharedFlowKt.MutableSharedFlow(i, sharingConfigConfigureSharing$FlowKt__ShareKt.extraBufferCapacity, sharingConfigConfigureSharing$FlowKt__ShareKt.onBufferOverflow);
        Symbol symbol = SharedFlowKt.NO_VALUE;
        SharingStarted.Companion.getClass();
        return new ReadonlySharedFlow(sharedFlowImplMutableSharedFlow, BuildersKt.launch(coroutineScope, sharingConfigConfigureSharing$FlowKt__ShareKt.context, Intrinsics.areEqual(sharingStarted, SharingStarted.Companion.Eagerly) ? CoroutineStart.DEFAULT : CoroutineStart.UNDISPATCHED, new FlowKt__ShareKt$launchSharing$1(sharingStarted, sharingConfigConfigureSharing$FlowKt__ShareKt.upstream, sharedFlowImplMutableSharedFlow, symbol, null)));
    }

    public static final ReadonlyStateFlow stateIn(Flow flow, CoroutineScope coroutineScope, SharingStarted sharingStarted, Object obj) {
        SharingConfig sharingConfigConfigureSharing$FlowKt__ShareKt = FlowKt__ShareKt.configureSharing$FlowKt__ShareKt(flow, 1);
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(obj);
        SharingStarted.Companion.getClass();
        return new ReadonlyStateFlow(stateFlowImplMutableStateFlow, BuildersKt.launch(coroutineScope, sharingConfigConfigureSharing$FlowKt__ShareKt.context, Intrinsics.areEqual(sharingStarted, SharingStarted.Companion.Eagerly) ? CoroutineStart.DEFAULT : CoroutineStart.UNDISPATCHED, new FlowKt__ShareKt$launchSharing$1(sharingStarted, sharingConfigConfigureSharing$FlowKt__ShareKt.upstream, stateFlowImplMutableStateFlow, obj, null)));
    }

    public static final FlowKt__LimitKt$take$$inlined$unsafeFlow$1 take(Flow flow, int i) {
        if (i > 0) {
            return new FlowKt__LimitKt$take$$inlined$unsafeFlow$1(flow, i);
        }
        throw new IllegalArgumentException(ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(i, "Requested element count ", " should be positive").toString());
    }

    /* renamed from: timeout-HG0u8IE, reason: not valid java name */
    public static final FlowCoroutineKt$scopedFlow$$inlined$unsafeFlow$1 m3482timeoutHG0u8IE(ChannelLimitedFlowMerge channelLimitedFlowMerge, long j) {
        return new FlowCoroutineKt$scopedFlow$$inlined$unsafeFlow$1(new FlowKt__DelayKt$timeoutInternal$1(j, channelLimitedFlowMerge, null));
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object toCollection(Flow flow, final Collection collection, ContinuationImpl continuationImpl) {
        FlowKt__CollectionKt$toCollection$1 flowKt__CollectionKt$toCollection$1;
        if (continuationImpl instanceof FlowKt__CollectionKt$toCollection$1) {
            flowKt__CollectionKt$toCollection$1 = (FlowKt__CollectionKt$toCollection$1) continuationImpl;
            int i = flowKt__CollectionKt$toCollection$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                flowKt__CollectionKt$toCollection$1.label = i - Integer.MIN_VALUE;
            } else {
                flowKt__CollectionKt$toCollection$1 = new FlowKt__CollectionKt$toCollection$1(continuationImpl);
            }
        }
        Object obj = flowKt__CollectionKt$toCollection$1.result;
        Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = flowKt__CollectionKt$toCollection$1.label;
        if (i2 != 0) {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            Collection collection2 = (Collection) flowKt__CollectionKt$toCollection$1.L$0;
            ResultKt.throwOnFailure(obj);
            return collection2;
        }
        ResultKt.throwOnFailure(obj);
        FlowCollector flowCollector = new FlowCollector() { // from class: kotlinx.coroutines.flow.FlowKt__CollectionKt$toCollection$2
            @Override // kotlinx.coroutines.flow.FlowCollector
            public final Object emit(Object obj3, Continuation continuation) {
                collection.add(obj3);
                return Unit.INSTANCE;
            }
        };
        flowKt__CollectionKt$toCollection$1.L$0 = collection;
        flowKt__CollectionKt$toCollection$1.label = 1;
        return flow.collect(flowCollector, flowKt__CollectionKt$toCollection$1) == obj2 ? obj2 : collection;
    }

    public static final ChannelFlowTransformLatest transformLatest(Flow flow, Function3 function3) {
        int i = FlowKt__MergeKt.$r8$clinit;
        return new ChannelFlowTransformLatest(function3, flow, null, 0, null, 28, null);
    }

    public static final SafeFlow transformWhile(Flow flow, Function3 function3) {
        return new SafeFlow(new FlowKt__LimitKt$transformWhile$1(flow, function3, null));
    }

    public static final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2 combine(Flow flow, Flow flow2, Flow flow3, Flow flow4, Function5 function5) {
        return new FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2(new Flow[]{flow, flow2, flow3, flow4}, function5);
    }

    public static final DistinctFlowImpl distinctUntilChanged(Flow flow, Function2 function2) {
        FlowKt__DistinctKt$$ExternalSyntheticLambda0 flowKt__DistinctKt$$ExternalSyntheticLambda0 = FlowKt__DistinctKt.defaultKeySelector;
        TypeIntrinsics.beforeCheckcastToFunctionOfArity(2, function2);
        return FlowKt__DistinctKt.distinctUntilChangedBy$FlowKt__DistinctKt(flow, flowKt__DistinctKt$$ExternalSyntheticLambda0, function2);
    }

    public static final ChannelLimitedFlowMerge merge(Flow... flowArr) {
        int i = FlowKt__MergeKt.$r8$clinit;
        return merge(ArraysKt___ArraysKt.asIterable(flowArr));
    }

    public static final SafeFlow combineTransform(Flow flow, Flow flow2, Flow flow3, Flow flow4, Function6 function6) {
        return new SafeFlow(new FlowKt__ZipKt$combineTransform$$inlined$combineTransformUnsafe$FlowKt__ZipKt$4(new Flow[]{flow, flow2, flow3, flow4}, null, function6));
    }

    public static final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3 combine(Flow flow, Flow flow2, Flow flow3, Flow flow4, Flow flow5, Function6 function6) {
        return new FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3(new Flow[]{flow, flow2, flow3, flow4, flow5}, function6);
    }

    public static final Object stateIn(Flow flow, CoroutineScope coroutineScope, ContinuationImpl continuationImpl) {
        SharingConfig sharingConfigConfigureSharing$FlowKt__ShareKt = FlowKt__ShareKt.configureSharing$FlowKt__ShareKt(flow, 1);
        CompletableDeferredImpl completableDeferredImplCompletableDeferred$default = CompletableDeferredKt.CompletableDeferred$default();
        BuildersKt.launch$default(coroutineScope, sharingConfigConfigureSharing$FlowKt__ShareKt.context, null, new FlowKt__ShareKt$launchSharingDeferred$1(sharingConfigConfigureSharing$FlowKt__ShareKt.upstream, completableDeferredImplCompletableDeferred$default, null), 2);
        Object objAwaitInternal = completableDeferredImplCompletableDeferred$default.awaitInternal(continuationImpl);
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        return objAwaitInternal;
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0061  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x006e A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x006f  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0077  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Type inference failed for: r2v1, types: [T, kotlinx.coroutines.internal.Symbol] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object first(Flow flow, Function2 function2, Continuation continuation) {
        FlowKt__ReduceKt$first$3 flowKt__ReduceKt$first$3;
        Ref$ObjectRef ref$ObjectRef;
        AbortFlowException e;
        FlowCollector flowCollector;
        T t;
        if (continuation instanceof FlowKt__ReduceKt$first$3) {
            flowKt__ReduceKt$first$3 = (FlowKt__ReduceKt$first$3) continuation;
            int i = flowKt__ReduceKt$first$3.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                flowKt__ReduceKt$first$3.label = i - Integer.MIN_VALUE;
            } else {
                flowKt__ReduceKt$first$3 = new FlowKt__ReduceKt$first$3(continuation);
            }
        }
        Object obj = flowKt__ReduceKt$first$3.result;
        Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = flowKt__ReduceKt$first$3.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            Ref$ObjectRef ref$ObjectRef2 = new Ref$ObjectRef();
            ref$ObjectRef2.element = NullSurrogateKt.NULL;
            FlowCollector flowKt__ReduceKt$first$$inlined$collectWhile$2 = new FlowKt__ReduceKt$first$$inlined$collectWhile$2(function2, ref$ObjectRef2);
            try {
                flowKt__ReduceKt$first$3.L$0 = ref$ObjectRef2;
                flowKt__ReduceKt$first$3.L$1 = flowKt__ReduceKt$first$$inlined$collectWhile$2;
                flowKt__ReduceKt$first$3.label = 1;
                if (flow.collect(flowKt__ReduceKt$first$$inlined$collectWhile$2, flowKt__ReduceKt$first$3) == obj2) {
                    return obj2;
                }
                ref$ObjectRef = ref$ObjectRef2;
            } catch (AbortFlowException e2) {
                ref$ObjectRef = ref$ObjectRef2;
                e = e2;
                flowCollector = flowKt__ReduceKt$first$$inlined$collectWhile$2;
                if (e.owner != flowCollector) {
                }
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            flowCollector = (FlowKt__ReduceKt$first$$inlined$collectWhile$2) flowKt__ReduceKt$first$3.L$1;
            ref$ObjectRef = (Ref$ObjectRef) flowKt__ReduceKt$first$3.L$0;
            try {
                ResultKt.throwOnFailure(obj);
            } catch (AbortFlowException e3) {
                e = e3;
                if (e.owner != flowCollector) {
                    JobKt.ensureActive(flowKt__ReduceKt$first$3.getContext());
                    t = ref$ObjectRef.element;
                    if (t == NullSurrogateKt.NULL) {
                    }
                } else {
                    throw e;
                }
            }
        }
        t = ref$ObjectRef.element;
        if (t == NullSurrogateKt.NULL) {
            return t;
        }
        throw new NoSuchElementException("Expected at least one element matching the predicate");
    }
}
