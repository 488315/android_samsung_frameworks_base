package kotlinx.coroutines.flow;

import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class FlowKt__MergeKt$flattenConcat$1$1 implements FlowCollector {
    public final /* synthetic */ FlowCollector $this_unsafeFlow;

    public FlowKt__MergeKt$flattenConcat$1$1(FlowCollector flowCollector) {
        this.$this_unsafeFlow = flowCollector;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    @Override // kotlinx.coroutines.flow.FlowCollector
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object emit(Flow flow, Continuation continuation) {
        FlowKt__MergeKt$flattenConcat$1$1$emit$1 flowKt__MergeKt$flattenConcat$1$1$emit$1;
        int i;
        if (continuation instanceof FlowKt__MergeKt$flattenConcat$1$1$emit$1) {
            flowKt__MergeKt$flattenConcat$1$1$emit$1 = (FlowKt__MergeKt$flattenConcat$1$1$emit$1) continuation;
            int i2 = flowKt__MergeKt$flattenConcat$1$1$emit$1.label;
            if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                flowKt__MergeKt$flattenConcat$1$1$emit$1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                Object obj = flowKt__MergeKt$flattenConcat$1$1$emit$1.result;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                i = flowKt__MergeKt$flattenConcat$1$1$emit$1.label;
                if (i != 0) {
                    ResultKt.throwOnFailure(obj);
                    flowKt__MergeKt$flattenConcat$1$1$emit$1.label = 1;
                    if (FlowKt.emitAll(flowKt__MergeKt$flattenConcat$1$1$emit$1, flow, this.$this_unsafeFlow) == coroutineSingletons) {
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
        flowKt__MergeKt$flattenConcat$1$1$emit$1 = new FlowKt__MergeKt$flattenConcat$1$1$emit$1(this, continuation);
        Object obj2 = flowKt__MergeKt$flattenConcat$1$1$emit$1.result;
        CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        i = flowKt__MergeKt$flattenConcat$1$1$emit$1.label;
        if (i != 0) {
        }
        return Unit.INSTANCE;
    }
}
