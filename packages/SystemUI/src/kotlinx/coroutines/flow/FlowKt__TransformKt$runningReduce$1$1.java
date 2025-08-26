package kotlinx.coroutines.flow;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.flow.internal.NullSurrogateKt;

/* loaded from: classes4.dex */
public final class FlowKt__TransformKt$runningReduce$1$1 implements FlowCollector {
    public final /* synthetic */ Ref$ObjectRef $accumulator;
    public final /* synthetic */ Function3 $operation;
    public final /* synthetic */ FlowCollector $this_flow;

    public FlowKt__TransformKt$runningReduce$1$1(Ref$ObjectRef<Object> ref$ObjectRef, Function3 function3, FlowCollector flowCollector) {
        this.$accumulator = ref$ObjectRef;
        this.$operation = function3;
        this.$this_flow = flowCollector;
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x0074, code lost:
    
        if (r8.emit(r7, r0) != r1) goto L27;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // kotlinx.coroutines.flow.FlowCollector
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object emit(Object obj, Continuation continuation) {
        FlowKt__TransformKt$runningReduce$1$1$emit$1 flowKt__TransformKt$runningReduce$1$1$emit$1;
        Ref$ObjectRef ref$ObjectRef;
        FlowKt__TransformKt$runningReduce$1$1 flowKt__TransformKt$runningReduce$1$1;
        Ref$ObjectRef ref$ObjectRef2;
        T t;
        if (continuation instanceof FlowKt__TransformKt$runningReduce$1$1$emit$1) {
            flowKt__TransformKt$runningReduce$1$1$emit$1 = (FlowKt__TransformKt$runningReduce$1$1$emit$1) continuation;
            int i = flowKt__TransformKt$runningReduce$1$1$emit$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                flowKt__TransformKt$runningReduce$1$1$emit$1.label = i - Integer.MIN_VALUE;
            } else {
                flowKt__TransformKt$runningReduce$1$1$emit$1 = new FlowKt__TransformKt$runningReduce$1$1$emit$1(this, continuation);
            }
        }
        Object obj2 = flowKt__TransformKt$runningReduce$1$1$emit$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = flowKt__TransformKt$runningReduce$1$1$emit$1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj2);
            ref$ObjectRef = this.$accumulator;
            Object obj3 = ref$ObjectRef.element;
            t = obj;
            if (obj3 == NullSurrogateKt.NULL) {
                ref$ObjectRef.element = t;
                FlowCollector flowCollector = this.$this_flow;
                Object obj4 = this.$accumulator.element;
                flowKt__TransformKt$runningReduce$1$1$emit$1.L$0 = null;
                flowKt__TransformKt$runningReduce$1$1$emit$1.L$1 = null;
                flowKt__TransformKt$runningReduce$1$1$emit$1.label = 2;
            } else {
                flowKt__TransformKt$runningReduce$1$1$emit$1.L$0 = this;
                flowKt__TransformKt$runningReduce$1$1$emit$1.L$1 = ref$ObjectRef;
                flowKt__TransformKt$runningReduce$1$1$emit$1.label = 1;
                Object objInvoke = this.$operation.invoke(obj3, obj, flowKt__TransformKt$runningReduce$1$1$emit$1);
                if (objInvoke != coroutineSingletons) {
                    flowKt__TransformKt$runningReduce$1$1 = this;
                    ref$ObjectRef2 = ref$ObjectRef;
                    obj2 = objInvoke;
                }
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
        ref$ObjectRef2 = (Ref$ObjectRef) flowKt__TransformKt$runningReduce$1$1$emit$1.L$1;
        flowKt__TransformKt$runningReduce$1$1 = (FlowKt__TransformKt$runningReduce$1$1) flowKt__TransformKt$runningReduce$1$1$emit$1.L$0;
        ResultKt.throwOnFailure(obj2);
        Object obj5 = obj2;
        ref$ObjectRef = ref$ObjectRef2;
        this = flowKt__TransformKt$runningReduce$1$1;
        t = obj5;
        ref$ObjectRef.element = t;
        FlowCollector flowCollector2 = this.$this_flow;
        Object obj42 = this.$accumulator.element;
        flowKt__TransformKt$runningReduce$1$1$emit$1.L$0 = null;
        flowKt__TransformKt$runningReduce$1$1$emit$1.L$1 = null;
        flowKt__TransformKt$runningReduce$1$1$emit$1.label = 2;
    }
}
