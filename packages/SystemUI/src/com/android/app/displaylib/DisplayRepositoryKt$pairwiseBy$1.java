package com.android.app.displaylib;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* loaded from: classes.dex */
final class DisplayRepositoryKt$pairwiseBy$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Flow $this_pairwiseBy;
    final /* synthetic */ Function3 $transform;
    private /* synthetic */ Object L$0;
    int label;

    /* renamed from: com.android.app.displaylib.DisplayRepositoryKt$pairwiseBy$1$1, reason: invalid class name */
    public final class AnonymousClass1 implements FlowCollector {
        public final /* synthetic */ FlowCollector $$this$flow;
        public final /* synthetic */ Object $noVal;
        public final /* synthetic */ Ref$ObjectRef $previousValue;
        public final /* synthetic */ Function3 $transform;

        public AnonymousClass1(Ref$ObjectRef<Object> ref$ObjectRef, Object obj, FlowCollector flowCollector, Function3 function3) {
            this.$previousValue = ref$ObjectRef;
            this.$noVal = obj;
            this.$$this$flow = flowCollector;
            this.$transform = function3;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:25:0x0080  */
        /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
        @Override // kotlinx.coroutines.flow.FlowCollector
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object emit(Object obj, Continuation continuation) {
            DisplayRepositoryKt$pairwiseBy$1$1$emit$1 displayRepositoryKt$pairwiseBy$1$1$emit$1;
            AnonymousClass1 anonymousClass1;
            FlowCollector flowCollector;
            Object obj2;
            T t;
            if (continuation instanceof DisplayRepositoryKt$pairwiseBy$1$1$emit$1) {
                displayRepositoryKt$pairwiseBy$1$1$emit$1 = (DisplayRepositoryKt$pairwiseBy$1$1$emit$1) continuation;
                int i = displayRepositoryKt$pairwiseBy$1$1$emit$1.label;
                if ((i & Integer.MIN_VALUE) != 0) {
                    displayRepositoryKt$pairwiseBy$1$1$emit$1.label = i - Integer.MIN_VALUE;
                } else {
                    displayRepositoryKt$pairwiseBy$1$1$emit$1 = new DisplayRepositoryKt$pairwiseBy$1$1$emit$1(this, continuation);
                }
            }
            Object objInvoke = displayRepositoryKt$pairwiseBy$1$1$emit$1.result;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i2 = displayRepositoryKt$pairwiseBy$1$1$emit$1.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(objInvoke);
                Ref$ObjectRef ref$ObjectRef = this.$previousValue;
                t = obj;
                if (!Intrinsics.areEqual(ref$ObjectRef.element, this.$noVal)) {
                    Object obj3 = ref$ObjectRef.element;
                    displayRepositoryKt$pairwiseBy$1$1$emit$1.L$0 = this;
                    displayRepositoryKt$pairwiseBy$1$1$emit$1.L$1 = obj;
                    FlowCollector flowCollector2 = this.$$this$flow;
                    displayRepositoryKt$pairwiseBy$1$1$emit$1.L$2 = flowCollector2;
                    displayRepositoryKt$pairwiseBy$1$1$emit$1.label = 1;
                    objInvoke = this.$transform.invoke(obj3, obj, displayRepositoryKt$pairwiseBy$1$1$emit$1);
                    if (objInvoke != coroutineSingletons) {
                        anonymousClass1 = this;
                        flowCollector = flowCollector2;
                        obj2 = obj;
                        displayRepositoryKt$pairwiseBy$1$1$emit$1.L$0 = anonymousClass1;
                        displayRepositoryKt$pairwiseBy$1$1$emit$1.L$1 = obj2;
                        displayRepositoryKt$pairwiseBy$1$1$emit$1.L$2 = null;
                        displayRepositoryKt$pairwiseBy$1$1$emit$1.label = 2;
                        if (flowCollector.emit(objInvoke, displayRepositoryKt$pairwiseBy$1$1$emit$1) != coroutineSingletons) {
                        }
                    }
                    return coroutineSingletons;
                }
                this.$previousValue.element = t;
                return Unit.INSTANCE;
            }
            if (i2 != 1) {
                if (i2 != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                Object obj4 = displayRepositoryKt$pairwiseBy$1$1$emit$1.L$1;
                AnonymousClass1 anonymousClass12 = (AnonymousClass1) displayRepositoryKt$pairwiseBy$1$1$emit$1.L$0;
                ResultKt.throwOnFailure(objInvoke);
                t = obj4;
                this = anonymousClass12;
                this.$previousValue.element = t;
                return Unit.INSTANCE;
            }
            flowCollector = (FlowCollector) displayRepositoryKt$pairwiseBy$1$1$emit$1.L$2;
            Object obj5 = displayRepositoryKt$pairwiseBy$1$1$emit$1.L$1;
            anonymousClass1 = (AnonymousClass1) displayRepositoryKt$pairwiseBy$1$1$emit$1.L$0;
            ResultKt.throwOnFailure(objInvoke);
            obj2 = obj5;
            displayRepositoryKt$pairwiseBy$1$1$emit$1.L$0 = anonymousClass1;
            displayRepositoryKt$pairwiseBy$1$1$emit$1.L$1 = obj2;
            displayRepositoryKt$pairwiseBy$1$1$emit$1.L$2 = null;
            displayRepositoryKt$pairwiseBy$1$1$emit$1.label = 2;
            if (flowCollector.emit(objInvoke, displayRepositoryKt$pairwiseBy$1$1$emit$1) != coroutineSingletons) {
                this = anonymousClass1;
                t = obj2;
                this.$previousValue.element = t;
                return Unit.INSTANCE;
            }
            return coroutineSingletons;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DisplayRepositoryKt$pairwiseBy$1(Flow flow, Function3 function3, Continuation continuation) {
        super(2, continuation);
        this.$this_pairwiseBy = flow;
        this.$transform = function3;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DisplayRepositoryKt$pairwiseBy$1 displayRepositoryKt$pairwiseBy$1 = new DisplayRepositoryKt$pairwiseBy$1(this.$this_pairwiseBy, this.$transform, continuation);
        displayRepositoryKt$pairwiseBy$1.L$0 = obj;
        return displayRepositoryKt$pairwiseBy$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DisplayRepositoryKt$pairwiseBy$1) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(ref$ObjectRef, obj2, flowCollector, this.$transform);
            this.label = 1;
            if (flow.collect(anonymousClass1, this) == coroutineSingletons) {
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
