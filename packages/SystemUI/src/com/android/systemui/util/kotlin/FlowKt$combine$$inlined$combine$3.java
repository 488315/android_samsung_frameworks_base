package com.android.systemui.util.kotlin;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function8;
import kotlin.jvm.internal.Lambda;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.internal.CombineKt;

public final class FlowKt$combine$$inlined$combine$3 implements Flow {
    final /* synthetic */ Flow[] $flows$inlined;
    final /* synthetic */ Function8 $transform$inlined$1;

    /* renamed from: com.android.systemui.util.kotlin.FlowKt$combine$$inlined$combine$3$2, reason: invalid class name */
    public final class AnonymousClass2 extends Lambda implements Function0 {
        final /* synthetic */ Flow[] $flows;

        public AnonymousClass2(Flow[] flowArr) {
            super(0);
            this.$flows = flowArr;
        }

        @Override // kotlin.jvm.functions.Function0
        public final Object[] invoke() {
            return new Object[this.$flows.length];
        }
    }

    /* renamed from: com.android.systemui.util.kotlin.FlowKt$combine$$inlined$combine$3$3, reason: invalid class name */
    public final class AnonymousClass3 extends SuspendLambda implements Function3 {
        final /* synthetic */ Function8 $transform$inlined;
        private /* synthetic */ Object L$0;
        /* synthetic */ Object L$1;
        int label;

        public AnonymousClass3(Continuation continuation, Function8 function8) {
            super(3, continuation);
            this.$transform$inlined = function8;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            FlowCollector flowCollector;
            Object invoke;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                Object[] objArr = (Object[]) this.L$1;
                Function8 function8 = this.$transform$inlined;
                Object obj2 = objArr[0];
                Object obj3 = objArr[1];
                Object obj4 = objArr[2];
                Object obj5 = objArr[3];
                Object obj6 = objArr[4];
                Object obj7 = objArr[5];
                Object obj8 = objArr[6];
                this.L$0 = flowCollector;
                this.label = 1;
                ComposableLambdaImpl composableLambdaImpl = (ComposableLambdaImpl) function8;
                composableLambdaImpl.getClass();
                invoke = composableLambdaImpl.invoke(obj2, obj3, obj4, obj5, obj6, obj7, (Composer) obj8, ((Number) this).intValue());
                if (invoke == coroutineSingletons) {
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
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
                invoke = obj;
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(invoke, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
            return Unit.INSTANCE;
        }

        public final Object invokeSuspend$$forInline(Object obj) {
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            Object[] objArr = (Object[]) this.L$1;
            Function8 function8 = this.$transform$inlined;
            Object obj2 = objArr[0];
            Object obj3 = objArr[1];
            Object obj4 = objArr[2];
            Object obj5 = objArr[3];
            Object obj6 = objArr[4];
            Object obj7 = objArr[5];
            Object obj8 = objArr[6];
            ComposableLambdaImpl composableLambdaImpl = (ComposableLambdaImpl) function8;
            composableLambdaImpl.getClass();
            flowCollector.emit(composableLambdaImpl.invoke(obj2, obj3, obj4, obj5, obj6, obj7, (Composer) obj8, ((Number) this).intValue()), this);
            return Unit.INSTANCE;
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(FlowCollector flowCollector, Object[] objArr, Continuation continuation) {
            AnonymousClass3 anonymousClass3 = new AnonymousClass3(continuation, this.$transform$inlined);
            anonymousClass3.L$0 = flowCollector;
            anonymousClass3.L$1 = objArr;
            return anonymousClass3.invokeSuspend(Unit.INSTANCE);
        }
    }

    public FlowKt$combine$$inlined$combine$3(Flow[] flowArr, Function8 function8) {
        this.$flows$inlined = flowArr;
        this.$transform$inlined$1 = function8;
    }

    @Override // kotlinx.coroutines.flow.Flow
    public Object collect(FlowCollector flowCollector, Continuation continuation) {
        Flow[] flowArr = this.$flows$inlined;
        Object combineInternal = CombineKt.combineInternal(flowArr, new AnonymousClass2(flowArr), new AnonymousClass3(null, this.$transform$inlined$1), flowCollector, continuation);
        return combineInternal == CoroutineSingletons.COROUTINE_SUSPENDED ? combineInternal : Unit.INSTANCE;
    }

    public Object collect$$forInline(FlowCollector flowCollector, Continuation continuation) {
        new ContinuationImpl(continuation) { // from class: com.android.systemui.util.kotlin.FlowKt$combine$$inlined$combine$3.1
            int label;
            /* synthetic */ Object result;

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                this.result = obj;
                this.label |= Integer.MIN_VALUE;
                return FlowKt$combine$$inlined$combine$3.this.collect(null, this);
            }
        };
        Flow[] flowArr = this.$flows$inlined;
        CombineKt.combineInternal(flowArr, new AnonymousClass2(flowArr), new AnonymousClass3(null, this.$transform$inlined$1), flowCollector, continuation);
        return Unit.INSTANCE;
    }
}
