package kotlinx.coroutines.flow;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
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
import kotlin.jvm.internal.TypeIntrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CompletableDeferredImpl;
import kotlinx.coroutines.CompletableDeferredKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.channels.BufferedChannel;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.internal.ChannelFlowOperatorImpl;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;
import kotlinx.coroutines.flow.internal.ChannelLimitedFlowMerge;
import kotlinx.coroutines.flow.internal.FlowCoroutineKt$scopedFlow$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.internal.FusibleFlow;
import kotlinx.coroutines.flow.internal.NopCollector;
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
    /* JADX WARN: Removed duplicated region for block: B:30:0x0082 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0083  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0035  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object catchImpl(kotlinx.coroutines.flow.Flow r4, kotlinx.coroutines.flow.FlowCollector r5, kotlin.coroutines.jvm.internal.ContinuationImpl r6) {
        /*
            boolean r0 = r6 instanceof kotlinx.coroutines.flow.FlowKt__ErrorsKt$catchImpl$1
            if (r0 == 0) goto L13
            r0 = r6
            kotlinx.coroutines.flow.FlowKt__ErrorsKt$catchImpl$1 r0 = (kotlinx.coroutines.flow.FlowKt__ErrorsKt$catchImpl$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            kotlinx.coroutines.flow.FlowKt__ErrorsKt$catchImpl$1 r0 = new kotlinx.coroutines.flow.FlowKt__ErrorsKt$catchImpl$1
            r0.<init>(r6)
        L18:
            java.lang.Object r6 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L35
            if (r2 != r3) goto L2d
            java.lang.Object r4 = r0.L$0
            kotlin.jvm.internal.Ref$ObjectRef r4 = (kotlin.jvm.internal.Ref$ObjectRef) r4
            kotlin.ResultKt.throwOnFailure(r6)     // Catch: java.lang.Throwable -> L2b
            goto L4d
        L2b:
            r5 = move-exception
            goto L51
        L2d:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L35:
            kotlin.ResultKt.throwOnFailure(r6)
            kotlin.jvm.internal.Ref$ObjectRef r6 = new kotlin.jvm.internal.Ref$ObjectRef
            r6.<init>()
            kotlinx.coroutines.flow.FlowKt__ErrorsKt$catchImpl$2 r2 = new kotlinx.coroutines.flow.FlowKt__ErrorsKt$catchImpl$2     // Catch: java.lang.Throwable -> L4f
            r2.<init>(r5, r6)     // Catch: java.lang.Throwable -> L4f
            r0.L$0 = r6     // Catch: java.lang.Throwable -> L4f
            r0.label = r3     // Catch: java.lang.Throwable -> L4f
            java.lang.Object r4 = r4.collect(r2, r0)     // Catch: java.lang.Throwable -> L4f
            if (r4 != r1) goto L4d
            return r1
        L4d:
            r4 = 0
            return r4
        L4f:
            r5 = move-exception
            r4 = r6
        L51:
            T r4 = r4.element
            java.lang.Throwable r4 = (java.lang.Throwable) r4
            if (r4 == 0) goto L5d
            boolean r6 = r4.equals(r5)
            if (r6 != 0) goto L7f
        L5d:
            kotlin.coroutines.CoroutineContext r6 = r0.getContext()
            kotlinx.coroutines.Job$Key r0 = kotlinx.coroutines.Job.Key
            kotlin.coroutines.CoroutineContext$Element r6 = r6.get(r0)
            kotlinx.coroutines.Job r6 = (kotlinx.coroutines.Job) r6
            if (r6 == 0) goto L80
            boolean r0 = r6.isCancelled$1()
            if (r0 != 0) goto L72
            goto L80
        L72:
            java.util.concurrent.CancellationException r6 = r6.getCancellationException()
            if (r6 == 0) goto L80
            boolean r6 = r6.equals(r5)
            if (r6 != 0) goto L7f
            goto L80
        L7f:
            throw r5
        L80:
            if (r4 != 0) goto L83
            return r5
        L83:
            boolean r6 = r5 instanceof java.util.concurrent.CancellationException
            if (r6 == 0) goto L8b
            kotlin.ExceptionsKt__ExceptionsKt.addSuppressed(r4, r5)
            throw r4
        L8b:
            kotlin.ExceptionsKt__ExceptionsKt.addSuppressed(r5, r4)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt.catchImpl(kotlinx.coroutines.flow.Flow, kotlinx.coroutines.flow.FlowCollector, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    public static final Object collect(Flow flow, Continuation continuation) {
        Object collect = flow.collect(NopCollector.INSTANCE, continuation);
        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
    }

    public static final Object collectLatest(Flow flow, Function2 function2, Continuation continuation) {
        Object collect = collect(buffer$default(mapLatest(flow, function2), 0, 2), continuation);
        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
    }

    public static final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 combine(Flow flow, Flow flow2, Flow flow3, Function4 function4) {
        return new FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1(new Flow[]{flow, flow2, flow3}, function4);
    }

    public static final SafeFlow combineTransform(Flow flow, Flow flow2, Function4 function4) {
        return new SafeFlow(new FlowKt__ZipKt$combineTransform$$inlined$combineTransformUnsafe$FlowKt__ZipKt$2(new Flow[]{flow, flow2}, null, function4));
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object count(kotlinx.coroutines.flow.FlowKt__LimitKt$take$$inlined$unsafeFlow$1 r4, kotlin.coroutines.jvm.internal.ContinuationImpl r5) {
        /*
            boolean r0 = r5 instanceof kotlinx.coroutines.flow.FlowKt__CountKt$count$1
            if (r0 == 0) goto L13
            r0 = r5
            kotlinx.coroutines.flow.FlowKt__CountKt$count$1 r0 = (kotlinx.coroutines.flow.FlowKt__CountKt$count$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            kotlinx.coroutines.flow.FlowKt__CountKt$count$1 r0 = new kotlinx.coroutines.flow.FlowKt__CountKt$count$1
            r0.<init>(r5)
        L18:
            java.lang.Object r5 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L33
            if (r2 != r3) goto L2b
            java.lang.Object r4 = r0.L$0
            kotlin.jvm.internal.Ref$IntRef r4 = (kotlin.jvm.internal.Ref$IntRef) r4
            kotlin.ResultKt.throwOnFailure(r5)
            goto L4c
        L2b:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L33:
            kotlin.ResultKt.throwOnFailure(r5)
            kotlin.jvm.internal.Ref$IntRef r5 = new kotlin.jvm.internal.Ref$IntRef
            r5.<init>()
            kotlinx.coroutines.flow.FlowKt__CountKt$count$2 r2 = new kotlinx.coroutines.flow.FlowKt__CountKt$count$2
            r2.<init>()
            r0.L$0 = r5
            r0.label = r3
            java.lang.Object r4 = r4.collect(r2, r0)
            if (r4 != r1) goto L4b
            return r1
        L4b:
            r4 = r5
        L4c:
            int r4 = r4.element
            java.lang.Integer r5 = new java.lang.Integer
            r5.<init>(r4)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt.count(kotlinx.coroutines.flow.FlowKt__LimitKt$take$$inlined$unsafeFlow$1, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    public static final Flow debounce(Flow flow, final long j) {
        if (j >= 0) {
            return j == 0 ? flow : new FlowCoroutineKt$scopedFlow$$inlined$unsafeFlow$1(new FlowKt__DelayKt$debounceInternal$1(new Function1() { // from class: kotlinx.coroutines.flow.FlowKt__DelayKt$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj) {
                    return Long.valueOf(j);
                }
            }, flow, null));
        }
        throw new IllegalArgumentException("Debounce timeout should not be negative");
    }

    /* renamed from: debounce-HG0u8IE, reason: not valid java name */
    public static final Flow m3462debounceHG0u8IE(Flow flow, long j) {
        return debounce(flow, DelayKt.m3450toDelayMillisLRDsOJo(j));
    }

    public static final Flow distinctUntilChanged(Flow flow) {
        return flow instanceof StateFlow ? flow : FlowKt__DistinctKt.distinctUntilChangedBy$FlowKt__DistinctKt(flow, FlowKt__DistinctKt.defaultKeySelector, FlowKt__DistinctKt.defaultAreEquivalent);
    }

    public static final FlowKt__LimitKt$drop$$inlined$unsafeFlow$1 drop(Flow flow) {
        return new FlowKt__LimitKt$drop$$inlined$unsafeFlow$1(flow, 1);
    }

    public static final Object emitAll(FlowCollector flowCollector, Flow flow, Continuation continuation) {
        if (flowCollector instanceof ThrowingCollector) {
            throw ((ThrowingCollector) flowCollector).e;
        }
        Object collect = flow.collect(flowCollector, continuation);
        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x006e A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:16:0x006f  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0061  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0077  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0039  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /* JADX WARN: Type inference failed for: r2v1, types: [T, kotlinx.coroutines.internal.Symbol] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object first(kotlinx.coroutines.flow.Flow r4, kotlin.coroutines.Continuation r5) {
        /*
            boolean r0 = r5 instanceof kotlinx.coroutines.flow.FlowKt__ReduceKt$first$1
            if (r0 == 0) goto L13
            r0 = r5
            kotlinx.coroutines.flow.FlowKt__ReduceKt$first$1 r0 = (kotlinx.coroutines.flow.FlowKt__ReduceKt$first$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            kotlinx.coroutines.flow.FlowKt__ReduceKt$first$1 r0 = new kotlinx.coroutines.flow.FlowKt__ReduceKt$first$1
            r0.<init>(r5)
        L18:
            java.lang.Object r5 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L39
            if (r2 != r3) goto L31
            java.lang.Object r4 = r0.L$1
            kotlinx.coroutines.flow.FlowKt__ReduceKt$first$$inlined$collectWhile$1 r4 = (kotlinx.coroutines.flow.FlowKt__ReduceKt$first$$inlined$collectWhile$1) r4
            java.lang.Object r1 = r0.L$0
            kotlin.jvm.internal.Ref$ObjectRef r1 = (kotlin.jvm.internal.Ref$ObjectRef) r1
            kotlin.ResultKt.throwOnFailure(r5)     // Catch: kotlinx.coroutines.flow.internal.AbortFlowException -> L2f
            goto L68
        L2f:
            r5 = move-exception
            goto L5d
        L31:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L39:
            kotlin.ResultKt.throwOnFailure(r5)
            kotlin.jvm.internal.Ref$ObjectRef r5 = new kotlin.jvm.internal.Ref$ObjectRef
            r5.<init>()
            kotlinx.coroutines.internal.Symbol r2 = kotlinx.coroutines.flow.internal.NullSurrogateKt.NULL
            r5.element = r2
            kotlinx.coroutines.flow.FlowKt__ReduceKt$first$$inlined$collectWhile$1 r2 = new kotlinx.coroutines.flow.FlowKt__ReduceKt$first$$inlined$collectWhile$1
            r2.<init>()
            r0.L$0 = r5     // Catch: kotlinx.coroutines.flow.internal.AbortFlowException -> L59
            r0.L$1 = r2     // Catch: kotlinx.coroutines.flow.internal.AbortFlowException -> L59
            r0.label = r3     // Catch: kotlinx.coroutines.flow.internal.AbortFlowException -> L59
            java.lang.Object r4 = r4.collect(r2, r0)     // Catch: kotlinx.coroutines.flow.internal.AbortFlowException -> L59
            if (r4 != r1) goto L57
            return r1
        L57:
            r1 = r5
            goto L68
        L59:
            r4 = move-exception
            r1 = r5
            r5 = r4
            r4 = r2
        L5d:
            java.lang.Object r2 = r5.owner
            if (r2 != r4) goto L77
            kotlin.coroutines.CoroutineContext r4 = r0.getContext()
            kotlinx.coroutines.JobKt.ensureActive(r4)
        L68:
            T r4 = r1.element
            kotlinx.coroutines.internal.Symbol r5 = kotlinx.coroutines.flow.internal.NullSurrogateKt.NULL
            if (r4 == r5) goto L6f
            return r4
        L6f:
            java.util.NoSuchElementException r4 = new java.util.NoSuchElementException
            java.lang.String r5 = "Expected at least one element"
            r4.<init>(r5)
            throw r4
        L77:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt.first(kotlinx.coroutines.flow.Flow, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x005d  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0067  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0039  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object firstOrNull(kotlinx.coroutines.flow.Flow r4, kotlin.coroutines.jvm.internal.ContinuationImpl r5) {
        /*
            boolean r0 = r5 instanceof kotlinx.coroutines.flow.FlowKt__ReduceKt$firstOrNull$1
            if (r0 == 0) goto L13
            r0 = r5
            kotlinx.coroutines.flow.FlowKt__ReduceKt$firstOrNull$1 r0 = (kotlinx.coroutines.flow.FlowKt__ReduceKt$firstOrNull$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            kotlinx.coroutines.flow.FlowKt__ReduceKt$firstOrNull$1 r0 = new kotlinx.coroutines.flow.FlowKt__ReduceKt$firstOrNull$1
            r0.<init>(r5)
        L18:
            java.lang.Object r5 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L39
            if (r2 != r3) goto L31
            java.lang.Object r4 = r0.L$1
            kotlinx.coroutines.flow.FlowKt__ReduceKt$firstOrNull$$inlined$collectWhile$1 r4 = (kotlinx.coroutines.flow.FlowKt__ReduceKt$firstOrNull$$inlined$collectWhile$1) r4
            java.lang.Object r1 = r0.L$0
            kotlin.jvm.internal.Ref$ObjectRef r1 = (kotlin.jvm.internal.Ref$ObjectRef) r1
            kotlin.ResultKt.throwOnFailure(r5)     // Catch: kotlinx.coroutines.flow.internal.AbortFlowException -> L2f
            goto L64
        L2f:
            r5 = move-exception
            goto L59
        L31:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L39:
            kotlin.ResultKt.throwOnFailure(r5)
            kotlin.jvm.internal.Ref$ObjectRef r5 = new kotlin.jvm.internal.Ref$ObjectRef
            r5.<init>()
            kotlinx.coroutines.flow.FlowKt__ReduceKt$firstOrNull$$inlined$collectWhile$1 r2 = new kotlinx.coroutines.flow.FlowKt__ReduceKt$firstOrNull$$inlined$collectWhile$1
            r2.<init>()
            r0.L$0 = r5     // Catch: kotlinx.coroutines.flow.internal.AbortFlowException -> L55
            r0.L$1 = r2     // Catch: kotlinx.coroutines.flow.internal.AbortFlowException -> L55
            r0.label = r3     // Catch: kotlinx.coroutines.flow.internal.AbortFlowException -> L55
            java.lang.Object r4 = r4.collect(r2, r0)     // Catch: kotlinx.coroutines.flow.internal.AbortFlowException -> L55
            if (r4 != r1) goto L53
            return r1
        L53:
            r1 = r5
            goto L64
        L55:
            r4 = move-exception
            r1 = r5
            r5 = r4
            r4 = r2
        L59:
            java.lang.Object r2 = r5.owner
            if (r2 != r4) goto L67
            kotlin.coroutines.CoroutineContext r4 = r0.getContext()
            kotlinx.coroutines.JobKt.ensureActive(r4)
        L64:
            T r4 = r1.element
            return r4
        L67:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt.firstOrNull(kotlinx.coroutines.flow.Flow, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
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

    /* JADX WARN: Removed duplicated region for block: B:15:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object lastOrNull(kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 r4, kotlin.coroutines.jvm.internal.ContinuationImpl r5) {
        /*
            boolean r0 = r5 instanceof kotlinx.coroutines.flow.FlowKt__ReduceKt$lastOrNull$1
            if (r0 == 0) goto L13
            r0 = r5
            kotlinx.coroutines.flow.FlowKt__ReduceKt$lastOrNull$1 r0 = (kotlinx.coroutines.flow.FlowKt__ReduceKt$lastOrNull$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            kotlinx.coroutines.flow.FlowKt__ReduceKt$lastOrNull$1 r0 = new kotlinx.coroutines.flow.FlowKt__ReduceKt$lastOrNull$1
            r0.<init>(r5)
        L18:
            java.lang.Object r5 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L33
            if (r2 != r3) goto L2b
            java.lang.Object r4 = r0.L$0
            kotlin.jvm.internal.Ref$ObjectRef r4 = (kotlin.jvm.internal.Ref$ObjectRef) r4
            kotlin.ResultKt.throwOnFailure(r5)
            goto L4c
        L2b:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L33:
            kotlin.ResultKt.throwOnFailure(r5)
            kotlin.jvm.internal.Ref$ObjectRef r5 = new kotlin.jvm.internal.Ref$ObjectRef
            r5.<init>()
            kotlinx.coroutines.flow.FlowKt__ReduceKt$lastOrNull$2 r2 = new kotlinx.coroutines.flow.FlowKt__ReduceKt$lastOrNull$2
            r2.<init>()
            r0.L$0 = r5
            r0.label = r3
            java.lang.Object r4 = r4.collect(r2, r0)
            if (r4 != r1) goto L4b
            return r1
        L4b:
            r4 = r5
        L4c:
            T r4 = r4.element
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt.lastOrNull(kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
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
        SharingConfig configureSharing$FlowKt__ShareKt = FlowKt__ShareKt.configureSharing$FlowKt__ShareKt(flow, i);
        SharedFlowImpl MutableSharedFlow = SharedFlowKt.MutableSharedFlow(i, configureSharing$FlowKt__ShareKt.extraBufferCapacity, configureSharing$FlowKt__ShareKt.onBufferOverflow);
        Symbol symbol = SharedFlowKt.NO_VALUE;
        SharingStarted.Companion.getClass();
        return new ReadonlySharedFlow(MutableSharedFlow, BuildersKt.launch(coroutineScope, configureSharing$FlowKt__ShareKt.context, Intrinsics.areEqual(sharingStarted, SharingStarted.Companion.Eagerly) ? CoroutineStart.DEFAULT : CoroutineStart.UNDISPATCHED, new FlowKt__ShareKt$launchSharing$1(sharingStarted, configureSharing$FlowKt__ShareKt.upstream, MutableSharedFlow, symbol, null)));
    }

    public static final ReadonlyStateFlow stateIn(Flow flow, CoroutineScope coroutineScope, SharingStarted sharingStarted, Object obj) {
        SharingConfig configureSharing$FlowKt__ShareKt = FlowKt__ShareKt.configureSharing$FlowKt__ShareKt(flow, 1);
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(obj);
        SharingStarted.Companion.getClass();
        return new ReadonlyStateFlow(MutableStateFlow, BuildersKt.launch(coroutineScope, configureSharing$FlowKt__ShareKt.context, Intrinsics.areEqual(sharingStarted, SharingStarted.Companion.Eagerly) ? CoroutineStart.DEFAULT : CoroutineStart.UNDISPATCHED, new FlowKt__ShareKt$launchSharing$1(sharingStarted, configureSharing$FlowKt__ShareKt.upstream, MutableStateFlow, obj, null)));
    }

    public static final FlowKt__LimitKt$take$$inlined$unsafeFlow$1 take(Flow flow, int i) {
        if (i > 0) {
            return new FlowKt__LimitKt$take$$inlined$unsafeFlow$1(flow, i);
        }
        throw new IllegalArgumentException(ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(i, "Requested element count ", " should be positive").toString());
    }

    /* renamed from: timeout-HG0u8IE, reason: not valid java name */
    public static final FlowCoroutineKt$scopedFlow$$inlined$unsafeFlow$1 m3463timeoutHG0u8IE(ChannelLimitedFlowMerge channelLimitedFlowMerge, long j) {
        return new FlowCoroutineKt$scopedFlow$$inlined$unsafeFlow$1(new FlowKt__DelayKt$timeoutInternal$1(j, channelLimitedFlowMerge, null));
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object toCollection(kotlinx.coroutines.flow.Flow r4, final java.util.Collection r5, kotlin.coroutines.jvm.internal.ContinuationImpl r6) {
        /*
            boolean r0 = r6 instanceof kotlinx.coroutines.flow.FlowKt__CollectionKt$toCollection$1
            if (r0 == 0) goto L13
            r0 = r6
            kotlinx.coroutines.flow.FlowKt__CollectionKt$toCollection$1 r0 = (kotlinx.coroutines.flow.FlowKt__CollectionKt$toCollection$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            kotlinx.coroutines.flow.FlowKt__CollectionKt$toCollection$1 r0 = new kotlinx.coroutines.flow.FlowKt__CollectionKt$toCollection$1
            r0.<init>(r6)
        L18:
            java.lang.Object r6 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L33
            if (r2 != r3) goto L2b
            java.lang.Object r4 = r0.L$0
            java.util.Collection r4 = (java.util.Collection) r4
            kotlin.ResultKt.throwOnFailure(r6)
            return r4
        L2b:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L33:
            kotlin.ResultKt.throwOnFailure(r6)
            kotlinx.coroutines.flow.FlowKt__CollectionKt$toCollection$2 r6 = new kotlinx.coroutines.flow.FlowKt__CollectionKt$toCollection$2
            r6.<init>()
            r0.L$0 = r5
            r0.label = r3
            java.lang.Object r4 = r4.collect(r6, r0)
            if (r4 != r1) goto L46
            return r1
        L46:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt.toCollection(kotlinx.coroutines.flow.Flow, java.util.Collection, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
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
        SharingConfig configureSharing$FlowKt__ShareKt = FlowKt__ShareKt.configureSharing$FlowKt__ShareKt(flow, 1);
        CompletableDeferredImpl CompletableDeferred$default = CompletableDeferredKt.CompletableDeferred$default();
        BuildersKt.launch$default(coroutineScope, configureSharing$FlowKt__ShareKt.context, null, new FlowKt__ShareKt$launchSharingDeferred$1(configureSharing$FlowKt__ShareKt.upstream, CompletableDeferred$default, null), 2);
        Object awaitInternal = CompletableDeferred$default.awaitInternal(continuationImpl);
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        return awaitInternal;
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x006e A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:16:0x006f  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0061  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0077  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0039  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /* JADX WARN: Type inference failed for: r2v1, types: [T, kotlinx.coroutines.internal.Symbol] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object first(kotlinx.coroutines.flow.Flow r4, kotlin.jvm.functions.Function2 r5, kotlin.coroutines.Continuation r6) {
        /*
            boolean r0 = r6 instanceof kotlinx.coroutines.flow.FlowKt__ReduceKt$first$3
            if (r0 == 0) goto L13
            r0 = r6
            kotlinx.coroutines.flow.FlowKt__ReduceKt$first$3 r0 = (kotlinx.coroutines.flow.FlowKt__ReduceKt$first$3) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            kotlinx.coroutines.flow.FlowKt__ReduceKt$first$3 r0 = new kotlinx.coroutines.flow.FlowKt__ReduceKt$first$3
            r0.<init>(r6)
        L18:
            java.lang.Object r6 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L39
            if (r2 != r3) goto L31
            java.lang.Object r4 = r0.L$1
            kotlinx.coroutines.flow.FlowKt__ReduceKt$first$$inlined$collectWhile$2 r4 = (kotlinx.coroutines.flow.FlowKt__ReduceKt$first$$inlined$collectWhile$2) r4
            java.lang.Object r5 = r0.L$0
            kotlin.jvm.internal.Ref$ObjectRef r5 = (kotlin.jvm.internal.Ref$ObjectRef) r5
            kotlin.ResultKt.throwOnFailure(r6)     // Catch: kotlinx.coroutines.flow.internal.AbortFlowException -> L2f
            goto L68
        L2f:
            r6 = move-exception
            goto L5d
        L31:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L39:
            kotlin.ResultKt.throwOnFailure(r6)
            kotlin.jvm.internal.Ref$ObjectRef r6 = new kotlin.jvm.internal.Ref$ObjectRef
            r6.<init>()
            kotlinx.coroutines.internal.Symbol r2 = kotlinx.coroutines.flow.internal.NullSurrogateKt.NULL
            r6.element = r2
            kotlinx.coroutines.flow.FlowKt__ReduceKt$first$$inlined$collectWhile$2 r2 = new kotlinx.coroutines.flow.FlowKt__ReduceKt$first$$inlined$collectWhile$2
            r2.<init>(r5, r6)
            r0.L$0 = r6     // Catch: kotlinx.coroutines.flow.internal.AbortFlowException -> L59
            r0.L$1 = r2     // Catch: kotlinx.coroutines.flow.internal.AbortFlowException -> L59
            r0.label = r3     // Catch: kotlinx.coroutines.flow.internal.AbortFlowException -> L59
            java.lang.Object r4 = r4.collect(r2, r0)     // Catch: kotlinx.coroutines.flow.internal.AbortFlowException -> L59
            if (r4 != r1) goto L57
            return r1
        L57:
            r5 = r6
            goto L68
        L59:
            r4 = move-exception
            r5 = r6
            r6 = r4
            r4 = r2
        L5d:
            java.lang.Object r1 = r6.owner
            if (r1 != r4) goto L77
            kotlin.coroutines.CoroutineContext r4 = r0.getContext()
            kotlinx.coroutines.JobKt.ensureActive(r4)
        L68:
            T r4 = r5.element
            kotlinx.coroutines.internal.Symbol r5 = kotlinx.coroutines.flow.internal.NullSurrogateKt.NULL
            if (r4 == r5) goto L6f
            return r4
        L6f:
            java.util.NoSuchElementException r4 = new java.util.NoSuchElementException
            java.lang.String r5 = "Expected at least one element matching the predicate"
            r4.<init>(r5)
            throw r4
        L77:
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt.first(kotlinx.coroutines.flow.Flow, kotlin.jvm.functions.Function2, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
