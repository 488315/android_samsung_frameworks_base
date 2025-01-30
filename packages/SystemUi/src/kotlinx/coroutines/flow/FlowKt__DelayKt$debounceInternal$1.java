package kotlinx.coroutines.flow;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Ref$LongRef;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.AbstractCoroutine;
import kotlinx.coroutines.CoroutineContextKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.channels.ChannelKt;
import kotlinx.coroutines.channels.ProducerCoroutine;
import kotlinx.coroutines.channels.ReceiveChannel;
import kotlinx.coroutines.flow.internal.NullSurrogateKt;
import kotlinx.coroutines.internal.Symbol;
import kotlinx.coroutines.selects.SelectBuilderImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "kotlinx.coroutines.flow.FlowKt__DelayKt$debounceInternal$1", m277f = "Delay.kt", m278l = {222, 355}, m279m = "invokeSuspend")
/* loaded from: classes3.dex */
final class FlowKt__DelayKt$debounceInternal$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ Flow $this_debounceInternal;
    final /* synthetic */ Function1 $timeoutMillisSelector;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    Object L$2;
    Object L$3;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FlowKt__DelayKt$debounceInternal$1(Function1 function1, Flow flow, Continuation<? super FlowKt__DelayKt$debounceInternal$1> continuation) {
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

    /* JADX WARN: Can't wrap try/catch for region: R(9:55|26|30|31|32|(1:34)|35|36|(1:38)) */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x00f4, code lost:
    
        r14 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x00f9, code lost:
    
        if (r8.trySelect() == false) goto L44;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00fb, code lost:
    
        r9 = kotlin.Result.$r8$clinit;
        r8.resumeWith(new kotlin.Result.Failure(r14));
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x0108, code lost:
    
        if ((r14 instanceof java.util.concurrent.CancellationException) == false) goto L46;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x010a, code lost:
    
        r9 = r8.getResult();
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x0110, code lost:
    
        if ((r9 instanceof kotlinx.coroutines.CompletedExceptionally) == false) goto L50;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x0118, code lost:
    
        kotlinx.coroutines.CoroutineExceptionHandlerKt.handleCoroutineException(r14, r8.getContext());
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00dd A[Catch: all -> 0x00f4, TryCatch #0 {all -> 0x00f4, blocks: (B:32:0x00d9, B:34:0x00dd, B:35:0x00e7), top: B:31:0x00d9 }] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0127 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0128  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0074  */
    /* JADX WARN: Type inference failed for: r6v12 */
    /* JADX WARN: Type inference failed for: r6v13 */
    /* JADX WARN: Type inference failed for: r6v14 */
    /* JADX WARN: Type inference failed for: r6v3, types: [java.lang.Object, kotlinx.coroutines.channels.ReceiveChannel] */
    /* JADX WARN: Type inference failed for: r6v5 */
    /* JADX WARN: Type inference failed for: r6v6, types: [java.lang.Object] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:37:0x0125 -> B:6:0x006e). Please report as a decompilation issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        FlowCollector flowCollector;
        Ref$ObjectRef ref$ObjectRef;
        Ref$LongRef ref$LongRef;
        ?? r6;
        Object result;
        FlowCollector flowCollector2;
        ReceiveChannel receiveChannel;
        Ref$ObjectRef ref$ObjectRef2;
        Ref$LongRef ref$LongRef2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            flowCollector = (FlowCollector) this.L$1;
            FlowKt__DelayKt$debounceInternal$1$values$1 flowKt__DelayKt$debounceInternal$1$values$1 = new FlowKt__DelayKt$debounceInternal$1$values$1(this.$this_debounceInternal, null);
            EmptyCoroutineContext emptyCoroutineContext = EmptyCoroutineContext.INSTANCE;
            BufferOverflow bufferOverflow = BufferOverflow.SUSPEND;
            CoroutineStart coroutineStart = CoroutineStart.DEFAULT;
            AbstractCoroutine producerCoroutine = new ProducerCoroutine(CoroutineContextKt.newCoroutineContext(coroutineScope, emptyCoroutineContext), ChannelKt.Channel$default(0, bufferOverflow, 4));
            producerCoroutine.start(coroutineStart, producerCoroutine, flowKt__DelayKt$debounceInternal$1$values$1);
            ref$ObjectRef = new Ref$ObjectRef();
            r6 = producerCoroutine;
        } else if (i == 1) {
            ref$LongRef2 = (Ref$LongRef) this.L$3;
            ref$ObjectRef2 = (Ref$ObjectRef) this.L$2;
            receiveChannel = (ReceiveChannel) this.L$1;
            flowCollector2 = (FlowCollector) this.L$0;
            ResultKt.throwOnFailure(obj);
            ref$ObjectRef2.element = null;
            ref$LongRef = ref$LongRef2;
            ref$ObjectRef = ref$ObjectRef2;
            r6 = receiveChannel;
            flowCollector = flowCollector2;
            this.L$0 = flowCollector;
            this.L$1 = r6;
            this.L$2 = ref$ObjectRef;
            this.L$3 = ref$LongRef;
            this.label = 2;
            SelectBuilderImpl selectBuilderImpl = new SelectBuilderImpl(this);
            if (ref$ObjectRef.element != null) {
                selectBuilderImpl.onTimeout(ref$LongRef.element, new FlowKt__DelayKt$debounceInternal$1$3$1(flowCollector, ref$ObjectRef, null));
            }
            r6.getOnReceiveCatching().registerSelectClause1(selectBuilderImpl, new FlowKt__DelayKt$debounceInternal$1$3$2(ref$ObjectRef, flowCollector, null));
            result = selectBuilderImpl.getResult();
            CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
            r6 = r6;
            if (result == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ref$ObjectRef = (Ref$ObjectRef) this.L$2;
            ReceiveChannel receiveChannel2 = (ReceiveChannel) this.L$1;
            flowCollector = (FlowCollector) this.L$0;
            ResultKt.throwOnFailure(obj);
            r6 = receiveChannel2;
        }
        if (ref$ObjectRef.element == NullSurrogateKt.DONE) {
            ref$LongRef = new Ref$LongRef();
            Object obj2 = ref$ObjectRef.element;
            if (obj2 != null) {
                Function1 function1 = this.$timeoutMillisSelector;
                Symbol symbol = NullSurrogateKt.NULL;
                if (obj2 == symbol) {
                    obj2 = null;
                }
                long longValue = ((Number) function1.invoke(obj2)).longValue();
                ref$LongRef.element = longValue;
                if (!(longValue >= 0)) {
                    throw new IllegalArgumentException("Debounce timeout should not be negative".toString());
                }
                if (longValue == 0) {
                    Object obj3 = ref$ObjectRef.element;
                    if (obj3 == symbol) {
                        obj3 = null;
                    }
                    this.L$0 = flowCollector;
                    this.L$1 = r6;
                    this.L$2 = ref$ObjectRef;
                    this.L$3 = ref$LongRef;
                    this.label = 1;
                    if (flowCollector.emit(obj3, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                    flowCollector2 = flowCollector;
                    receiveChannel = r6;
                    ref$ObjectRef2 = ref$ObjectRef;
                    ref$LongRef2 = ref$LongRef;
                    ref$ObjectRef2.element = null;
                    ref$LongRef = ref$LongRef2;
                    ref$ObjectRef = ref$ObjectRef2;
                    r6 = receiveChannel;
                    flowCollector = flowCollector2;
                }
            }
            this.L$0 = flowCollector;
            this.L$1 = r6;
            this.L$2 = ref$ObjectRef;
            this.L$3 = ref$LongRef;
            this.label = 2;
            SelectBuilderImpl selectBuilderImpl2 = new SelectBuilderImpl(this);
            if (ref$ObjectRef.element != null) {
            }
            r6.getOnReceiveCatching().registerSelectClause1(selectBuilderImpl2, new FlowKt__DelayKt$debounceInternal$1$3$2(ref$ObjectRef, flowCollector, null));
            result = selectBuilderImpl2.getResult();
            CoroutineSingletons coroutineSingletons22 = CoroutineSingletons.COROUTINE_SUSPENDED;
            r6 = r6;
            if (result == coroutineSingletons) {
            }
            if (ref$ObjectRef.element == NullSurrogateKt.DONE) {
                return Unit.INSTANCE;
            }
        }
    }
}
