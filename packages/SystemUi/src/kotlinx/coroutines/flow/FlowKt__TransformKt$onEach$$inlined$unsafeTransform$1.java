package kotlinx.coroutines.flow;

import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 implements Flow {
    public final /* synthetic */ Function2 $action$inlined;
    public final /* synthetic */ Flow $this_unsafeTransform$inlined;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1$2 */
    public final class C48492 implements FlowCollector {
        public final /* synthetic */ Function2 $action$inlined;
        public final /* synthetic */ FlowCollector $this_unsafeFlow;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        @DebugMetadata(m276c = "kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1$2", m277f = "Transform.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut, IKnoxCustomManager.Stub.TRANSACTION_setUsbConnectionType}, m279m = "emit")
        /* renamed from: kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1$2$1, reason: invalid class name */
        public final class AnonymousClass1 extends ContinuationImpl {
            Object L$0;
            Object L$1;
            int label;
            /* synthetic */ Object result;

            public AnonymousClass1(Continuation continuation) {
                super(continuation);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                this.result = obj;
                this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                return C48492.this.emit(null, this);
            }
        }

        public C48492(FlowCollector flowCollector, Function2 function2) {
            this.$this_unsafeFlow = flowCollector;
            this.$action$inlined = function2;
        }

        /* JADX WARN: Removed duplicated region for block: B:19:0x005e A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:20:0x003c  */
        /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
        @Override // kotlinx.coroutines.flow.FlowCollector
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object emit(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1;
            CoroutineSingletons coroutineSingletons;
            int i;
            FlowCollector flowCollector;
            if (continuation instanceof AnonymousClass1) {
                anonymousClass1 = (AnonymousClass1) continuation;
                int i2 = anonymousClass1.label;
                if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                    anonymousClass1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                    Object obj2 = anonymousClass1.result;
                    coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    i = anonymousClass1.label;
                    if (i != 0) {
                        ResultKt.throwOnFailure(obj2);
                        anonymousClass1.L$0 = obj;
                        FlowCollector flowCollector2 = this.$this_unsafeFlow;
                        anonymousClass1.L$1 = flowCollector2;
                        anonymousClass1.label = 1;
                        if (this.$action$inlined.invoke(obj, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                        flowCollector = flowCollector2;
                    } else {
                        if (i != 1) {
                            if (i != 2) {
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                            }
                            ResultKt.throwOnFailure(obj2);
                            return Unit.INSTANCE;
                        }
                        flowCollector = (FlowCollector) anonymousClass1.L$1;
                        obj = anonymousClass1.L$0;
                        ResultKt.throwOnFailure(obj2);
                    }
                    anonymousClass1.L$0 = null;
                    anonymousClass1.L$1 = null;
                    anonymousClass1.label = 2;
                    if (flowCollector.emit(obj, anonymousClass1) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                    return Unit.INSTANCE;
                }
            }
            anonymousClass1 = new AnonymousClass1(continuation);
            Object obj22 = anonymousClass1.result;
            coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            i = anonymousClass1.label;
            if (i != 0) {
            }
            anonymousClass1.L$0 = null;
            anonymousClass1.L$1 = null;
            anonymousClass1.label = 2;
            if (flowCollector.emit(obj, anonymousClass1) == coroutineSingletons) {
            }
            return Unit.INSTANCE;
        }
    }

    public FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(Flow flow, Function2 function2) {
        this.$this_unsafeTransform$inlined = flow;
        this.$action$inlined = function2;
    }

    @Override // kotlinx.coroutines.flow.Flow
    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
        Object collect = this.$this_unsafeTransform$inlined.collect(new C48492(flowCollector, this.$action$inlined), continuation);
        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
    }
}
