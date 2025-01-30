package kotlinx.coroutines.flow;

import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Ref$ObjectRef;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class FlowKt__TransformKt$runningFold$$inlined$unsafeFlow$1 implements Flow {
    public final /* synthetic */ Object $initial$inlined;
    public final /* synthetic */ Function3 $operation$inlined;
    public final /* synthetic */ Flow $this_runningFold$inlined;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    @DebugMetadata(m276c = "kotlinx.coroutines.flow.FlowKt__TransformKt$runningFold$$inlined$unsafeFlow$1", m277f = "Transform.kt", m278l = {114, 115}, m279m = "collect")
    /* renamed from: kotlinx.coroutines.flow.FlowKt__TransformKt$runningFold$$inlined$unsafeFlow$1$1 */
    public final class C48501 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;

        public C48501(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
            return FlowKt__TransformKt$runningFold$$inlined$unsafeFlow$1.this.collect(null, this);
        }
    }

    public FlowKt__TransformKt$runningFold$$inlined$unsafeFlow$1(Object obj, Flow flow, Function3 function3) {
        this.$initial$inlined = obj;
        this.$this_runningFold$inlined = flow;
        this.$operation$inlined = function3;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0077 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0044  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    /* JADX WARN: Type inference failed for: r2v1, types: [T, java.lang.Object] */
    @Override // kotlinx.coroutines.flow.Flow
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
        C48501 c48501;
        CoroutineSingletons coroutineSingletons;
        int i;
        Ref$ObjectRef ref$ObjectRef;
        FlowCollector flowCollector2;
        Flow flow;
        FlowKt__TransformKt$runningFold$1$1 flowKt__TransformKt$runningFold$1$1;
        if (continuation instanceof C48501) {
            c48501 = (C48501) continuation;
            int i2 = c48501.label;
            if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                c48501.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                Object obj = c48501.result;
                coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                i = c48501.label;
                if (i != 0) {
                    ResultKt.throwOnFailure(obj);
                    ref$ObjectRef = new Ref$ObjectRef();
                    ?? r2 = this.$initial$inlined;
                    ref$ObjectRef.element = r2;
                    c48501.L$0 = this;
                    c48501.L$1 = flowCollector;
                    c48501.L$2 = ref$ObjectRef;
                    c48501.label = 1;
                    Object emit = flowCollector.emit(r2, c48501);
                    flowCollector2 = flowCollector;
                    if (emit == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i != 1) {
                        if (i != 2) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj);
                        return Unit.INSTANCE;
                    }
                    Ref$ObjectRef ref$ObjectRef2 = (Ref$ObjectRef) c48501.L$2;
                    FlowCollector flowCollector3 = (FlowCollector) c48501.L$1;
                    FlowKt__TransformKt$runningFold$$inlined$unsafeFlow$1 flowKt__TransformKt$runningFold$$inlined$unsafeFlow$1 = (FlowKt__TransformKt$runningFold$$inlined$unsafeFlow$1) c48501.L$0;
                    ResultKt.throwOnFailure(obj);
                    ref$ObjectRef = ref$ObjectRef2;
                    this = flowKt__TransformKt$runningFold$$inlined$unsafeFlow$1;
                    flowCollector2 = flowCollector3;
                }
                flow = this.$this_runningFold$inlined;
                flowKt__TransformKt$runningFold$1$1 = new FlowKt__TransformKt$runningFold$1$1(ref$ObjectRef, this.$operation$inlined, flowCollector2);
                c48501.L$0 = null;
                c48501.L$1 = null;
                c48501.L$2 = null;
                c48501.label = 2;
                if (flow.collect(flowKt__TransformKt$runningFold$1$1, c48501) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                return Unit.INSTANCE;
            }
        }
        c48501 = new C48501(continuation);
        Object obj2 = c48501.result;
        coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        i = c48501.label;
        if (i != 0) {
        }
        flow = this.$this_runningFold$inlined;
        flowKt__TransformKt$runningFold$1$1 = new FlowKt__TransformKt$runningFold$1$1(ref$ObjectRef, this.$operation$inlined, flowCollector2);
        c48501.L$0 = null;
        c48501.L$1 = null;
        c48501.L$2 = null;
        c48501.label = 2;
        if (flow.collect(flowKt__TransformKt$runningFold$1$1, c48501) == coroutineSingletons) {
        }
        return Unit.INSTANCE;
    }
}
