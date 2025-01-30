package kotlinx.coroutines.flow;

import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$BooleanRef;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class FlowKt__LimitKt$dropWhile$1$1 implements FlowCollector {
    public final /* synthetic */ Ref$BooleanRef $matched;
    public final /* synthetic */ Function2 $predicate;
    public final /* synthetic */ FlowCollector $this_unsafeFlow;

    public FlowKt__LimitKt$dropWhile$1$1(Ref$BooleanRef ref$BooleanRef, FlowCollector flowCollector, Function2 function2) {
        this.$matched = ref$BooleanRef;
        this.$this_unsafeFlow = flowCollector;
        this.$predicate = function2;
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0071  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0088  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0043  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0023  */
    @Override // kotlinx.coroutines.flow.FlowCollector
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object emit(Object obj, Continuation continuation) {
        FlowKt__LimitKt$dropWhile$1$1$emit$1 flowKt__LimitKt$dropWhile$1$1$emit$1;
        int i;
        if (continuation instanceof FlowKt__LimitKt$dropWhile$1$1$emit$1) {
            flowKt__LimitKt$dropWhile$1$1$emit$1 = (FlowKt__LimitKt$dropWhile$1$1$emit$1) continuation;
            int i2 = flowKt__LimitKt$dropWhile$1$1$emit$1.label;
            if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                flowKt__LimitKt$dropWhile$1$1$emit$1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                Object obj2 = flowKt__LimitKt$dropWhile$1$1$emit$1.result;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                i = flowKt__LimitKt$dropWhile$1$1$emit$1.label;
                if (i != 0) {
                    ResultKt.throwOnFailure(obj2);
                    if (this.$matched.element) {
                        flowKt__LimitKt$dropWhile$1$1$emit$1.label = 1;
                        if (this.$this_unsafeFlow.emit(obj, flowKt__LimitKt$dropWhile$1$1$emit$1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                        return Unit.INSTANCE;
                    }
                    flowKt__LimitKt$dropWhile$1$1$emit$1.L$0 = this;
                    flowKt__LimitKt$dropWhile$1$1$emit$1.L$1 = obj;
                    flowKt__LimitKt$dropWhile$1$1$emit$1.label = 2;
                    obj2 = this.$predicate.invoke(obj, flowKt__LimitKt$dropWhile$1$1$emit$1);
                    if (obj2 == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                    if (!((Boolean) obj2).booleanValue()) {
                    }
                } else {
                    if (i == 1) {
                        ResultKt.throwOnFailure(obj2);
                        return Unit.INSTANCE;
                    }
                    if (i == 2) {
                        obj = flowKt__LimitKt$dropWhile$1$1$emit$1.L$1;
                        this = (FlowKt__LimitKt$dropWhile$1$1) flowKt__LimitKt$dropWhile$1$1$emit$1.L$0;
                        ResultKt.throwOnFailure(obj2);
                        if (!((Boolean) obj2).booleanValue()) {
                            return Unit.INSTANCE;
                        }
                        this.$matched.element = true;
                        flowKt__LimitKt$dropWhile$1$1$emit$1.L$0 = null;
                        flowKt__LimitKt$dropWhile$1$1$emit$1.L$1 = null;
                        flowKt__LimitKt$dropWhile$1$1$emit$1.label = 3;
                        if (this.$this_unsafeFlow.emit(obj, flowKt__LimitKt$dropWhile$1$1$emit$1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i != 3) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                }
                return Unit.INSTANCE;
            }
        }
        flowKt__LimitKt$dropWhile$1$1$emit$1 = new FlowKt__LimitKt$dropWhile$1$1$emit$1(this, continuation);
        Object obj22 = flowKt__LimitKt$dropWhile$1$1$emit$1.result;
        CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        i = flowKt__LimitKt$dropWhile$1$1$emit$1.label;
        if (i != 0) {
        }
        return Unit.INSTANCE;
    }
}
