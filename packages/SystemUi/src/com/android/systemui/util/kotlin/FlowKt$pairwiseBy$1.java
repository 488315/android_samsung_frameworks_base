package com.android.systemui.util.kotlin;

import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.util.kotlin.FlowKt$pairwiseBy$1", m277f = "Flow.kt", m278l = {45}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
final class FlowKt$pairwiseBy$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Flow $this_pairwiseBy;
    final /* synthetic */ Function3 $transform;
    private /* synthetic */ Object L$0;
    int label;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.util.kotlin.FlowKt$pairwiseBy$1$1 */
    public final class C35851 implements FlowCollector {
        public final /* synthetic */ FlowCollector $$this$flow;
        public final /* synthetic */ Object $noVal;
        public final /* synthetic */ Ref$ObjectRef $previousValue;
        public final /* synthetic */ Function3 $transform;

        public C35851(Ref$ObjectRef<Object> ref$ObjectRef, Object obj, FlowCollector flowCollector, Function3 function3) {
            this.$previousValue = ref$ObjectRef;
            this.$noVal = obj;
            this.$$this$flow = flowCollector;
            this.$transform = function3;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:19:0x007f A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:20:0x0080  */
        /* JADX WARN: Removed duplicated region for block: B:21:0x0049  */
        /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
        @Override // kotlinx.coroutines.flow.FlowCollector
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object emit(Object obj, Continuation continuation) {
            FlowKt$pairwiseBy$1$1$emit$1 flowKt$pairwiseBy$1$1$emit$1;
            int i;
            C35851 c35851;
            FlowCollector flowCollector;
            Object obj2;
            T t;
            if (continuation instanceof FlowKt$pairwiseBy$1$1$emit$1) {
                flowKt$pairwiseBy$1$1$emit$1 = (FlowKt$pairwiseBy$1$1$emit$1) continuation;
                int i2 = flowKt$pairwiseBy$1$1$emit$1.label;
                if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                    flowKt$pairwiseBy$1$1$emit$1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                    Object obj3 = flowKt$pairwiseBy$1$1$emit$1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    i = flowKt$pairwiseBy$1$1$emit$1.label;
                    if (i != 0) {
                        ResultKt.throwOnFailure(obj3);
                        Ref$ObjectRef ref$ObjectRef = this.$previousValue;
                        t = obj;
                        if (!Intrinsics.areEqual(ref$ObjectRef.element, this.$noVal)) {
                            Object obj4 = ref$ObjectRef.element;
                            flowKt$pairwiseBy$1$1$emit$1.L$0 = this;
                            flowKt$pairwiseBy$1$1$emit$1.L$1 = obj;
                            FlowCollector flowCollector2 = this.$$this$flow;
                            flowKt$pairwiseBy$1$1$emit$1.L$2 = flowCollector2;
                            flowKt$pairwiseBy$1$1$emit$1.label = 1;
                            obj3 = this.$transform.invoke(obj4, obj, flowKt$pairwiseBy$1$1$emit$1);
                            if (obj3 == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                            c35851 = this;
                            flowCollector = flowCollector2;
                            obj2 = obj;
                            flowKt$pairwiseBy$1$1$emit$1.L$0 = c35851;
                            flowKt$pairwiseBy$1$1$emit$1.L$1 = obj2;
                            flowKt$pairwiseBy$1$1$emit$1.L$2 = null;
                            flowKt$pairwiseBy$1$1$emit$1.label = 2;
                            if (flowCollector.emit(obj3, flowKt$pairwiseBy$1$1$emit$1) != coroutineSingletons) {
                            }
                        }
                    } else if (i == 1) {
                        flowCollector = (FlowCollector) flowKt$pairwiseBy$1$1$emit$1.L$2;
                        Object obj5 = flowKt$pairwiseBy$1$1$emit$1.L$1;
                        c35851 = (C35851) flowKt$pairwiseBy$1$1$emit$1.L$0;
                        ResultKt.throwOnFailure(obj3);
                        obj2 = obj5;
                        flowKt$pairwiseBy$1$1$emit$1.L$0 = c35851;
                        flowKt$pairwiseBy$1$1$emit$1.L$1 = obj2;
                        flowKt$pairwiseBy$1$1$emit$1.L$2 = null;
                        flowKt$pairwiseBy$1$1$emit$1.label = 2;
                        if (flowCollector.emit(obj3, flowKt$pairwiseBy$1$1$emit$1) != coroutineSingletons) {
                            return coroutineSingletons;
                        }
                        this = c35851;
                        t = obj2;
                    } else {
                        if (i != 2) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        Object obj6 = flowKt$pairwiseBy$1$1$emit$1.L$1;
                        C35851 c358512 = (C35851) flowKt$pairwiseBy$1$1$emit$1.L$0;
                        ResultKt.throwOnFailure(obj3);
                        t = obj6;
                        this = c358512;
                    }
                    this.$previousValue.element = t;
                    return Unit.INSTANCE;
                }
            }
            flowKt$pairwiseBy$1$1$emit$1 = new FlowKt$pairwiseBy$1$1$emit$1(this, continuation);
            Object obj32 = flowKt$pairwiseBy$1$1$emit$1.result;
            CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
            i = flowKt$pairwiseBy$1$1$emit$1.label;
            if (i != 0) {
            }
            this.$previousValue.element = t;
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FlowKt$pairwiseBy$1(Flow flow, Function3 function3, Continuation<? super FlowKt$pairwiseBy$1> continuation) {
        super(2, continuation);
        this.$this_pairwiseBy = flow;
        this.$transform = function3;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        FlowKt$pairwiseBy$1 flowKt$pairwiseBy$1 = new FlowKt$pairwiseBy$1(this.$this_pairwiseBy, this.$transform, continuation);
        flowKt$pairwiseBy$1.L$0 = obj;
        return flowKt$pairwiseBy$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FlowKt$pairwiseBy$1) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [T, java.lang.Object] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            ?? obj2 = new Object();
            Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
            ref$ObjectRef.element = obj2;
            Flow flow = this.$this_pairwiseBy;
            C35851 c35851 = new C35851(ref$ObjectRef, obj2, flowCollector, this.$transform);
            this.label = 1;
            if (flow.collect(c35851, this) == coroutineSingletons) {
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
