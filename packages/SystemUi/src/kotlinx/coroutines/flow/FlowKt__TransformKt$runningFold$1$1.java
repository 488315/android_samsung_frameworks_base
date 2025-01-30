package kotlinx.coroutines.flow;

import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Ref$ObjectRef;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class FlowKt__TransformKt$runningFold$1$1 implements FlowCollector {
    public final /* synthetic */ Ref$ObjectRef $accumulator;
    public final /* synthetic */ Function3 $operation;
    public final /* synthetic */ FlowCollector $this_unsafeFlow;

    public FlowKt__TransformKt$runningFold$1$1(Ref$ObjectRef<Object> ref$ObjectRef, Function3 function3, FlowCollector flowCollector) {
        this.$accumulator = ref$ObjectRef;
        this.$operation = function3;
        this.$this_unsafeFlow = flowCollector;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:19:0x006d A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x003e  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    @Override // kotlinx.coroutines.flow.FlowCollector
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object emit(Object obj, Continuation continuation) {
        FlowKt__TransformKt$runningFold$1$1$emit$1 flowKt__TransformKt$runningFold$1$1$emit$1;
        CoroutineSingletons coroutineSingletons;
        int i;
        FlowKt__TransformKt$runningFold$1$1 flowKt__TransformKt$runningFold$1$1;
        Ref$ObjectRef ref$ObjectRef;
        T t;
        FlowCollector flowCollector;
        Object obj2;
        if (continuation instanceof FlowKt__TransformKt$runningFold$1$1$emit$1) {
            flowKt__TransformKt$runningFold$1$1$emit$1 = (FlowKt__TransformKt$runningFold$1$1$emit$1) continuation;
            int i2 = flowKt__TransformKt$runningFold$1$1$emit$1.label;
            if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                flowKt__TransformKt$runningFold$1$1$emit$1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                Object obj3 = flowKt__TransformKt$runningFold$1$1$emit$1.result;
                coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                i = flowKt__TransformKt$runningFold$1$1$emit$1.label;
                if (i != 0) {
                    ResultKt.throwOnFailure(obj3);
                    Ref$ObjectRef ref$ObjectRef2 = this.$accumulator;
                    Object obj4 = ref$ObjectRef2.element;
                    flowKt__TransformKt$runningFold$1$1$emit$1.L$0 = this;
                    flowKt__TransformKt$runningFold$1$1$emit$1.L$1 = ref$ObjectRef2;
                    flowKt__TransformKt$runningFold$1$1$emit$1.label = 1;
                    Object invoke = this.$operation.invoke(obj4, obj, flowKt__TransformKt$runningFold$1$1$emit$1);
                    if (invoke == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                    flowKt__TransformKt$runningFold$1$1 = this;
                    ref$ObjectRef = ref$ObjectRef2;
                    t = invoke;
                } else {
                    if (i != 1) {
                        if (i != 2) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj3);
                        return Unit.INSTANCE;
                    }
                    ref$ObjectRef = (Ref$ObjectRef) flowKt__TransformKt$runningFold$1$1$emit$1.L$1;
                    flowKt__TransformKt$runningFold$1$1 = (FlowKt__TransformKt$runningFold$1$1) flowKt__TransformKt$runningFold$1$1$emit$1.L$0;
                    ResultKt.throwOnFailure(obj3);
                    t = obj3;
                }
                ref$ObjectRef.element = t;
                flowCollector = flowKt__TransformKt$runningFold$1$1.$this_unsafeFlow;
                obj2 = flowKt__TransformKt$runningFold$1$1.$accumulator.element;
                flowKt__TransformKt$runningFold$1$1$emit$1.L$0 = null;
                flowKt__TransformKt$runningFold$1$1$emit$1.L$1 = null;
                flowKt__TransformKt$runningFold$1$1$emit$1.label = 2;
                if (flowCollector.emit(obj2, flowKt__TransformKt$runningFold$1$1$emit$1) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                return Unit.INSTANCE;
            }
        }
        flowKt__TransformKt$runningFold$1$1$emit$1 = new FlowKt__TransformKt$runningFold$1$1$emit$1(this, continuation);
        Object obj32 = flowKt__TransformKt$runningFold$1$1$emit$1.result;
        coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        i = flowKt__TransformKt$runningFold$1$1$emit$1.label;
        if (i != 0) {
        }
        ref$ObjectRef.element = t;
        flowCollector = flowKt__TransformKt$runningFold$1$1.$this_unsafeFlow;
        obj2 = flowKt__TransformKt$runningFold$1$1.$accumulator.element;
        flowKt__TransformKt$runningFold$1$1$emit$1.L$0 = null;
        flowKt__TransformKt$runningFold$1$1$emit$1.L$1 = null;
        flowKt__TransformKt$runningFold$1$1$emit$1.label = 2;
        if (flowCollector.emit(obj2, flowKt__TransformKt$runningFold$1$1$emit$1) == coroutineSingletons) {
        }
        return Unit.INSTANCE;
    }
}
