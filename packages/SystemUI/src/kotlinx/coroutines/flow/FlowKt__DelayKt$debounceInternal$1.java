package kotlinx.coroutines.flow;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Ref$LongRef;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerCoroutine;
import kotlinx.coroutines.channels.ReceiveChannel;
import kotlinx.coroutines.flow.internal.NullSurrogateKt;
import kotlinx.coroutines.internal.Symbol;
import kotlinx.coroutines.selects.OnTimeoutKt;
import kotlinx.coroutines.selects.SelectClause1Impl;
import kotlinx.coroutines.selects.SelectImplementation;
import kotlinx.coroutines.selects.SelectImplementation.ClauseData;

/* loaded from: classes4.dex */
final class FlowKt__DelayKt$debounceInternal$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ Flow $this_debounceInternal;
    final /* synthetic */ Function1 $timeoutMillisSelector;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    Object L$2;
    Object L$3;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FlowKt__DelayKt$debounceInternal$1(Function1 function1, Flow flow, Continuation continuation) {
        super(3, continuation);
        this.$timeoutMillisSelector = function1;
        this.$this_debounceInternal = flow;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        FlowKt__DelayKt$debounceInternal$1 flowKt__DelayKt$debounceInternal$1 = new FlowKt__DelayKt$debounceInternal$1(this.$timeoutMillisSelector, this.$this_debounceInternal, (Continuation) obj3);
        flowKt__DelayKt$debounceInternal$1.L$0 = (CoroutineScope) obj;
        flowKt__DelayKt$debounceInternal$1.L$1 = (FlowCollector) obj2;
        return flowKt__DelayKt$debounceInternal$1.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x0099, code lost:
    
        if (r8.emit(r9, r18) == r1) goto L40;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x00fb, code lost:
    
        if (r10.complete(r18) == r1) goto L40;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x0020, code lost:
    
        if (r10.doSelectSuspend(r18) == r1) goto L40;
     */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0062  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x009e A[PHI: r2 r6 r7 r8
      0x009e: PHI (r2v4 kotlin.jvm.internal.Ref$LongRef) = (r2v6 kotlin.jvm.internal.Ref$LongRef), (r2v8 kotlin.jvm.internal.Ref$LongRef), (r2v8 kotlin.jvm.internal.Ref$LongRef) binds: [B:28:0x009c, B:15:0x0069, B:21:0x0084] A[DONT_GENERATE, DONT_INLINE]
      0x009e: PHI (r6v3 kotlin.jvm.internal.Ref$ObjectRef) = 
      (r6v13 kotlin.jvm.internal.Ref$ObjectRef)
      (r6v14 kotlin.jvm.internal.Ref$ObjectRef)
      (r6v14 kotlin.jvm.internal.Ref$ObjectRef)
     binds: [B:28:0x009c, B:15:0x0069, B:21:0x0084] A[DONT_GENERATE, DONT_INLINE]
      0x009e: PHI (r7v3 kotlinx.coroutines.channels.ReceiveChannel) = 
      (r7v4 kotlinx.coroutines.channels.ReceiveChannel)
      (r7v5 kotlinx.coroutines.channels.ReceiveChannel)
      (r7v5 kotlinx.coroutines.channels.ReceiveChannel)
     binds: [B:28:0x009c, B:15:0x0069, B:21:0x0084] A[DONT_GENERATE, DONT_INLINE]
      0x009e: PHI (r8v2 kotlinx.coroutines.flow.FlowCollector) = 
      (r8v3 kotlinx.coroutines.flow.FlowCollector)
      (r8v4 kotlinx.coroutines.flow.FlowCollector)
      (r8v4 kotlinx.coroutines.flow.FlowCollector)
     binds: [B:28:0x009c, B:15:0x0069, B:21:0x0084] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00b9  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00f2  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00f7  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00fe  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        FlowCollector flowCollector;
        Ref$ObjectRef ref$ObjectRef;
        ReceiveChannel receiveChannel;
        Ref$LongRef ref$LongRef;
        Ref$ObjectRef ref$ObjectRef2;
        SelectImplementation selectImplementation;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            FlowCollector flowCollector2 = (FlowCollector) this.L$1;
            ProducerCoroutine producerCoroutineProduce$default = ProduceKt.produce$default(coroutineScope, new FlowKt__DelayKt$debounceInternal$1$values$1(this.$this_debounceInternal, null));
            flowCollector = flowCollector2;
            ref$ObjectRef = new Ref$ObjectRef();
            receiveChannel = producerCoroutineProduce$default;
            if (ref$ObjectRef.element == NullSurrogateKt.DONE) {
            }
        } else if (i == 1) {
            ref$LongRef = (Ref$LongRef) this.L$3;
            ref$ObjectRef = (Ref$ObjectRef) this.L$2;
            receiveChannel = (ReceiveChannel) this.L$1;
            flowCollector = (FlowCollector) this.L$0;
            ResultKt.throwOnFailure(obj);
            ref$ObjectRef.element = null;
            Ref$ObjectRef ref$ObjectRef3 = ref$ObjectRef;
            Ref$LongRef ref$LongRef2 = ref$LongRef;
            ref$ObjectRef2 = ref$ObjectRef3;
            selectImplementation = new SelectImplementation(getContext());
            if (ref$ObjectRef2.element != 0) {
            }
            SelectClause1Impl onReceiveCatching = receiveChannel.getOnReceiveCatching();
            selectImplementation.register(selectImplementation.new ClauseData(onReceiveCatching.clauseObject, onReceiveCatching.regFunc, onReceiveCatching.processResFunc, null, new FlowKt__DelayKt$debounceInternal$1$3$2(ref$ObjectRef2, flowCollector, null), onReceiveCatching.onCancellationConstructor), false);
            this.L$0 = flowCollector;
            this.L$1 = receiveChannel;
            this.L$2 = ref$ObjectRef2;
            this.L$3 = null;
            this.label = 2;
            if (!(selectImplementation.state.value instanceof SelectImplementation.ClauseData)) {
            }
            ref$ObjectRef = ref$ObjectRef2;
            if (ref$ObjectRef.element == NullSurrogateKt.DONE) {
            }
        } else {
            if (i != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ref$ObjectRef2 = (Ref$ObjectRef) this.L$2;
            ReceiveChannel receiveChannel2 = (ReceiveChannel) this.L$1;
            FlowCollector flowCollector3 = (FlowCollector) this.L$0;
            ResultKt.throwOnFailure(obj);
            flowCollector = flowCollector3;
            receiveChannel = receiveChannel2;
            ref$ObjectRef = ref$ObjectRef2;
            if (ref$ObjectRef.element == NullSurrogateKt.DONE) {
                ref$LongRef = new Ref$LongRef();
                Object obj2 = ref$ObjectRef.element;
                if (obj2 != null) {
                    Function1 function1 = this.$timeoutMillisSelector;
                    Symbol symbol = NullSurrogateKt.NULL;
                    if (obj2 == symbol) {
                        obj2 = null;
                    }
                    long jLongValue = ((Number) function1.mo781invoke(obj2)).longValue();
                    ref$LongRef.element = jLongValue;
                    if (jLongValue < 0) {
                        throw new IllegalArgumentException("Debounce timeout should not be negative");
                    }
                    if (jLongValue != 0) {
                        Ref$ObjectRef ref$ObjectRef32 = ref$ObjectRef;
                        Ref$LongRef ref$LongRef22 = ref$LongRef;
                        ref$ObjectRef2 = ref$ObjectRef32;
                        selectImplementation = new SelectImplementation(getContext());
                        if (ref$ObjectRef2.element != 0) {
                            OnTimeoutKt.onTimeout(selectImplementation, ref$LongRef22.element, new FlowKt__DelayKt$debounceInternal$1$3$1(flowCollector, ref$ObjectRef2, null));
                        }
                        SelectClause1Impl onReceiveCatching2 = receiveChannel.getOnReceiveCatching();
                        selectImplementation.register(selectImplementation.new ClauseData(onReceiveCatching2.clauseObject, onReceiveCatching2.regFunc, onReceiveCatching2.processResFunc, null, new FlowKt__DelayKt$debounceInternal$1$3$2(ref$ObjectRef2, flowCollector, null), onReceiveCatching2.onCancellationConstructor), false);
                        this.L$0 = flowCollector;
                        this.L$1 = receiveChannel;
                        this.L$2 = ref$ObjectRef2;
                        this.L$3 = null;
                        this.label = 2;
                        if (!(selectImplementation.state.value instanceof SelectImplementation.ClauseData)) {
                        }
                        ref$ObjectRef = ref$ObjectRef2;
                    } else {
                        Object obj3 = ref$ObjectRef.element;
                        if (obj3 == symbol) {
                            obj3 = null;
                        }
                        this.L$0 = flowCollector;
                        this.L$1 = receiveChannel;
                        this.L$2 = ref$ObjectRef;
                        this.L$3 = ref$LongRef;
                        this.label = 1;
                    }
                    return coroutineSingletons;
                }
                if (ref$ObjectRef.element == NullSurrogateKt.DONE) {
                    return Unit.INSTANCE;
                }
            }
        }
    }
}
