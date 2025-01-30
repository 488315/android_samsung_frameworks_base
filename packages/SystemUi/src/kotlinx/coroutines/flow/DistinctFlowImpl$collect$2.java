package kotlinx.coroutines.flow;

import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.flow.internal.NullSurrogateKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class DistinctFlowImpl$collect$2 implements FlowCollector {
    public final /* synthetic */ FlowCollector $collector;
    public final /* synthetic */ Ref$ObjectRef $previousKey;
    public final /* synthetic */ DistinctFlowImpl this$0;

    public DistinctFlowImpl$collect$2(DistinctFlowImpl distinctFlowImpl, Ref$ObjectRef<Object> ref$ObjectRef, FlowCollector flowCollector) {
        this.this$0 = distinctFlowImpl;
        this.$previousKey = ref$ObjectRef;
        this.$collector = flowCollector;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /* JADX WARN: Type inference failed for: r2v2, types: [T, java.lang.Object] */
    @Override // kotlinx.coroutines.flow.FlowCollector
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object emit(Object obj, Continuation continuation) {
        DistinctFlowImpl$collect$2$emit$1 distinctFlowImpl$collect$2$emit$1;
        int i;
        if (continuation instanceof DistinctFlowImpl$collect$2$emit$1) {
            distinctFlowImpl$collect$2$emit$1 = (DistinctFlowImpl$collect$2$emit$1) continuation;
            int i2 = distinctFlowImpl$collect$2$emit$1.label;
            if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                distinctFlowImpl$collect$2$emit$1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                Object obj2 = distinctFlowImpl$collect$2$emit$1.result;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                i = distinctFlowImpl$collect$2$emit$1.label;
                if (i != 0) {
                    ResultKt.throwOnFailure(obj2);
                    DistinctFlowImpl distinctFlowImpl = this.this$0;
                    ?? invoke = distinctFlowImpl.keySelector.invoke(obj);
                    Ref$ObjectRef ref$ObjectRef = this.$previousKey;
                    T t = ref$ObjectRef.element;
                    if (t != NullSurrogateKt.NULL && ((Boolean) distinctFlowImpl.areEquivalent.invoke(t, invoke)).booleanValue()) {
                        return Unit.INSTANCE;
                    }
                    ref$ObjectRef.element = invoke;
                    distinctFlowImpl$collect$2$emit$1.label = 1;
                    if (this.$collector.emit(obj, distinctFlowImpl$collect$2$emit$1) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj2);
                }
                return Unit.INSTANCE;
            }
        }
        distinctFlowImpl$collect$2$emit$1 = new DistinctFlowImpl$collect$2$emit$1(this, continuation);
        Object obj22 = distinctFlowImpl$collect$2$emit$1.result;
        CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        i = distinctFlowImpl$collect$2$emit$1.label;
        if (i != 0) {
        }
        return Unit.INSTANCE;
    }
}
