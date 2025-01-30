package kotlinx.coroutines.flow;

import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.flow.internal.AbortFlowException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class FlowKt__ReduceKt$first$$inlined$collectWhile$2 implements FlowCollector {
    public final /* synthetic */ Function2 $predicate$inlined;
    public final /* synthetic */ Ref$ObjectRef $result$inlined;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    @DebugMetadata(m276c = "kotlinx.coroutines.flow.FlowKt__ReduceKt$first$$inlined$collectWhile$2", m277f = "Reduce.kt", m278l = {142}, m279m = "emit")
    /* renamed from: kotlinx.coroutines.flow.FlowKt__ReduceKt$first$$inlined$collectWhile$2$1 */
    public final class C48451 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        public C48451(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
            return FlowKt__ReduceKt$first$$inlined$collectWhile$2.this.emit(null, this);
        }
    }

    public FlowKt__ReduceKt$first$$inlined$collectWhile$2(Function2 function2, Ref$ObjectRef ref$ObjectRef) {
        this.$predicate$inlined = function2;
        this.$result$inlined = ref$ObjectRef;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:12:0x004f  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0056  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0059  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0035  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    @Override // kotlinx.coroutines.flow.FlowCollector
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object emit(Object obj, Continuation continuation) {
        C48451 c48451;
        Object obj2;
        int i;
        boolean z;
        T t;
        if (continuation instanceof C48451) {
            c48451 = (C48451) continuation;
            int i2 = c48451.label;
            if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                c48451.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                obj2 = c48451.result;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                i = c48451.label;
                z = true;
                if (i != 0) {
                    ResultKt.throwOnFailure(obj2);
                    c48451.L$0 = this;
                    c48451.L$1 = obj;
                    c48451.label = 1;
                    obj2 = this.$predicate$inlined.invoke(obj, c48451);
                    t = obj;
                    if (obj2 == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    Object obj3 = c48451.L$1;
                    this = (FlowKt__ReduceKt$first$$inlined$collectWhile$2) c48451.L$0;
                    ResultKt.throwOnFailure(obj2);
                    t = obj3;
                }
                if (((Boolean) obj2).booleanValue()) {
                    this.$result$inlined.element = t;
                    z = false;
                }
                if (z) {
                    throw new AbortFlowException(this);
                }
                return Unit.INSTANCE;
            }
        }
        c48451 = new C48451(continuation);
        obj2 = c48451.result;
        CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        i = c48451.label;
        z = true;
        if (i != 0) {
        }
        if (((Boolean) obj2).booleanValue()) {
        }
        if (z) {
        }
    }
}
