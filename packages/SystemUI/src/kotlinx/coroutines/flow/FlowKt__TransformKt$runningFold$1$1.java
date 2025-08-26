package kotlinx.coroutines.flow;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Ref$ObjectRef;

/* loaded from: classes4.dex */
public final class FlowKt__TransformKt$runningFold$1$1 implements FlowCollector {
    public final /* synthetic */ Ref$ObjectRef $accumulator;
    public final /* synthetic */ Function3 $operation;
    public final /* synthetic */ FlowCollector $this_flow;

    public FlowKt__TransformKt$runningFold$1$1(Ref$ObjectRef<Object> ref$ObjectRef, Function3 function3, FlowCollector flowCollector) {
        this.$accumulator = ref$ObjectRef;
        this.$operation = function3;
        this.$this_flow = flowCollector;
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x006b, code lost:
    
        if (r6.emit(r7, r0) == r1) goto L22;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // kotlinx.coroutines.flow.FlowCollector
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object emit(Object obj, Continuation continuation) {
        FlowKt__TransformKt$runningFold$1$1$emit$1 flowKt__TransformKt$runningFold$1$1$emit$1;
        FlowKt__TransformKt$runningFold$1$1 flowKt__TransformKt$runningFold$1$1;
        Ref$ObjectRef ref$ObjectRef;
        T t;
        if (continuation instanceof FlowKt__TransformKt$runningFold$1$1$emit$1) {
            flowKt__TransformKt$runningFold$1$1$emit$1 = (FlowKt__TransformKt$runningFold$1$1$emit$1) continuation;
            int i = flowKt__TransformKt$runningFold$1$1$emit$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                flowKt__TransformKt$runningFold$1$1$emit$1.label = i - Integer.MIN_VALUE;
            } else {
                flowKt__TransformKt$runningFold$1$1$emit$1 = new FlowKt__TransformKt$runningFold$1$1$emit$1(this, continuation);
            }
        }
        Object obj2 = flowKt__TransformKt$runningFold$1$1$emit$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = flowKt__TransformKt$runningFold$1$1$emit$1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj2);
            Ref$ObjectRef ref$ObjectRef2 = this.$accumulator;
            Object obj3 = ref$ObjectRef2.element;
            flowKt__TransformKt$runningFold$1$1$emit$1.L$0 = this;
            flowKt__TransformKt$runningFold$1$1$emit$1.L$1 = ref$ObjectRef2;
            flowKt__TransformKt$runningFold$1$1$emit$1.label = 1;
            Object objInvoke = this.$operation.invoke(obj3, obj, flowKt__TransformKt$runningFold$1$1$emit$1);
            if (objInvoke != coroutineSingletons) {
                flowKt__TransformKt$runningFold$1$1 = this;
                ref$ObjectRef = ref$ObjectRef2;
                t = objInvoke;
            }
            return coroutineSingletons;
        }
        if (i2 != 1) {
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj2);
            return Unit.INSTANCE;
        }
        ref$ObjectRef = (Ref$ObjectRef) flowKt__TransformKt$runningFold$1$1$emit$1.L$1;
        flowKt__TransformKt$runningFold$1$1 = (FlowKt__TransformKt$runningFold$1$1) flowKt__TransformKt$runningFold$1$1$emit$1.L$0;
        ResultKt.throwOnFailure(obj2);
        t = obj2;
        ref$ObjectRef.element = t;
        FlowCollector flowCollector = flowKt__TransformKt$runningFold$1$1.$this_flow;
        Object obj4 = flowKt__TransformKt$runningFold$1$1.$accumulator.element;
        flowKt__TransformKt$runningFold$1$1$emit$1.L$0 = null;
        flowKt__TransformKt$runningFold$1$1$emit$1.L$1 = null;
        flowKt__TransformKt$runningFold$1$1$emit$1.label = 2;
    }
}
