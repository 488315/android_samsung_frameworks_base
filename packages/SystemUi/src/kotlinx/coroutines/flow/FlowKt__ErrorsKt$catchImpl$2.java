package kotlinx.coroutines.flow;

import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.internal.Ref$ObjectRef;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class FlowKt__ErrorsKt$catchImpl$2 implements FlowCollector {
    public final /* synthetic */ FlowCollector $collector;
    public final /* synthetic */ Ref$ObjectRef $fromDownstream;

    public FlowKt__ErrorsKt$catchImpl$2(FlowCollector flowCollector, Ref$ObjectRef<Throwable> ref$ObjectRef) {
        this.$collector = flowCollector;
        this.$fromDownstream = ref$ObjectRef;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0021  */
    /* JADX WARN: Type inference failed for: r4v4, types: [java.lang.Object, kotlin.Unit] */
    /* JADX WARN: Type inference failed for: r5v1, types: [T, java.lang.Throwable] */
    @Override // kotlinx.coroutines.flow.FlowCollector
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object emit(Object obj, Continuation continuation) {
        FlowKt__ErrorsKt$catchImpl$2$emit$1 flowKt__ErrorsKt$catchImpl$2$emit$1;
        int i;
        try {
            if (continuation instanceof FlowKt__ErrorsKt$catchImpl$2$emit$1) {
                flowKt__ErrorsKt$catchImpl$2$emit$1 = (FlowKt__ErrorsKt$catchImpl$2$emit$1) continuation;
                int i2 = flowKt__ErrorsKt$catchImpl$2$emit$1.label;
                if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                    flowKt__ErrorsKt$catchImpl$2$emit$1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                    Object obj2 = flowKt__ErrorsKt$catchImpl$2$emit$1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    i = flowKt__ErrorsKt$catchImpl$2$emit$1.label;
                    if (i != 0) {
                        ResultKt.throwOnFailure(obj2);
                        FlowCollector flowCollector = this.$collector;
                        flowKt__ErrorsKt$catchImpl$2$emit$1.L$0 = this;
                        flowKt__ErrorsKt$catchImpl$2$emit$1.label = 1;
                        if (flowCollector.emit(obj, flowKt__ErrorsKt$catchImpl$2$emit$1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    this = Unit.INSTANCE;
                    return this;
                }
            }
            if (i != 0) {
            }
            this = Unit.INSTANCE;
            return this;
        } catch (Throwable th) {
            this.$fromDownstream.element = th;
            throw th;
        }
        flowKt__ErrorsKt$catchImpl$2$emit$1 = new FlowKt__ErrorsKt$catchImpl$2$emit$1(this, continuation);
        Object obj22 = flowKt__ErrorsKt$catchImpl$2$emit$1.result;
        CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        i = flowKt__ErrorsKt$catchImpl$2$emit$1.label;
    }
}
