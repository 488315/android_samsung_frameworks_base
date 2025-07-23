package com.android.systemui.util.kotlin;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function8;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.internal.CombineKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class FlowKt$combine$$inlined$combine$3 implements Flow {
    final /* synthetic */ Flow[] $flows$inlined;
    final /* synthetic */ Function8 $transform$inlined$1;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.util.kotlin.FlowKt$combine$$inlined$combine$3$2, reason: invalid class name */
    public final class AnonymousClass2 implements Function0 {
        final /* synthetic */ Flow[] $flows;

        public AnonymousClass2(Flow[] flowArr) {
            this.$flows = flowArr;
        }

        @Override // kotlin.jvm.functions.Function0
        public final Object[] invoke() {
            return new Object[this.$flows.length];
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.util.kotlin.FlowKt$combine$$inlined$combine$3$3, reason: invalid class name */
    public final class AnonymousClass3 extends SuspendLambda implements Function3 {
        final /* synthetic */ Function8 $transform$inlined;
        private /* synthetic */ Object L$0;
        /* synthetic */ Object L$1;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass3(Continuation continuation, Function8 function8) {
            super(3, continuation);
            this.$transform$inlined = function8;
        }

        /* JADX WARN: Code restructure failed: missing block: B:13:0x0068, code lost:
        
            if (r2.emit(r4, r17) == r1) goto L15;
         */
        /* JADX WARN: Code restructure failed: missing block: B:14:0x006a, code lost:
        
            return r1;
         */
        /* JADX WARN: Code restructure failed: missing block: B:16:0x005c, code lost:
        
            if (r4 == r1) goto L15;
         */
        /* JADX WARN: Multi-variable type inference failed */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r18) {
            /*
                r17 = this;
                r0 = r17
                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                int r2 = r0.label
                r3 = 2
                r4 = 1
                if (r2 == 0) goto L24
                if (r2 == r4) goto L1a
                if (r2 != r3) goto L12
                kotlin.ResultKt.throwOnFailure(r18)
                goto L6b
            L12:
                java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
                java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
                r0.<init>(r1)
                throw r0
            L1a:
                java.lang.Object r2 = r0.L$0
                kotlinx.coroutines.flow.FlowCollector r2 = (kotlinx.coroutines.flow.FlowCollector) r2
                kotlin.ResultKt.throwOnFailure(r18)
                r4 = r18
                goto L5f
            L24:
                kotlin.ResultKt.throwOnFailure(r18)
                java.lang.Object r2 = r0.L$0
                kotlinx.coroutines.flow.FlowCollector r2 = (kotlinx.coroutines.flow.FlowCollector) r2
                java.lang.Object r5 = r0.L$1
                java.lang.Object[] r5 = (java.lang.Object[]) r5
                kotlin.jvm.functions.Function8 r6 = r0.$transform$inlined
                r7 = 0
                r9 = r5[r7]
                r10 = r5[r4]
                r11 = r5[r3]
                r7 = 3
                r12 = r5[r7]
                r7 = 4
                r13 = r5[r7]
                r7 = 5
                r14 = r5[r7]
                r7 = 6
                r5 = r5[r7]
                r0.L$0 = r2
                r0.label = r4
                r8 = r6
                androidx.compose.runtime.internal.ComposableLambdaImpl r8 = (androidx.compose.runtime.internal.ComposableLambdaImpl) r8
                r8.getClass()
                r15 = r5
                androidx.compose.runtime.Composer r15 = (androidx.compose.runtime.Composer) r15
                r4 = r0
                java.lang.Number r4 = (java.lang.Number) r4
                int r16 = r4.intValue()
                java.lang.Object r4 = r8.invoke(r9, r10, r11, r12, r13, r14, r15, r16)
                if (r4 != r1) goto L5f
                goto L6a
            L5f:
                r5 = 0
                r0.L$0 = r5
                r0.label = r3
                java.lang.Object r0 = r2.emit(r4, r0)
                if (r0 != r1) goto L6b
            L6a:
                return r1
            L6b:
                kotlin.Unit r0 = kotlin.Unit.INSTANCE
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.util.kotlin.FlowKt$combine$$inlined$combine$3.AnonymousClass3.invokeSuspend(java.lang.Object):java.lang.Object");
        }

        /* JADX WARN: Multi-variable type inference failed */
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
