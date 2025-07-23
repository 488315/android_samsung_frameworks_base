package com.android.systemui.util.kotlin;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function7;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.internal.CombineKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class FlowKt$combine$$inlined$combine$1 implements Flow {
    final /* synthetic */ Flow[] $flows$inlined;
    final /* synthetic */ Function7 $transform$inlined$1;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.util.kotlin.FlowKt$combine$$inlined$combine$1$2, reason: invalid class name */
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
    /* renamed from: com.android.systemui.util.kotlin.FlowKt$combine$$inlined$combine$1$3, reason: invalid class name */
    public final class AnonymousClass3 extends SuspendLambda implements Function3 {
        final /* synthetic */ Function7 $transform$inlined;
        private /* synthetic */ Object L$0;
        /* synthetic */ Object L$1;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass3(Continuation continuation, Function7 function7) {
            super(3, continuation);
            this.$transform$inlined = function7;
        }

        /* JADX WARN: Code restructure failed: missing block: B:13:0x0054, code lost:
        
            if (r1.emit(r13, r11) == r0) goto L15;
         */
        /* JADX WARN: Code restructure failed: missing block: B:14:0x0056, code lost:
        
            return r0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:16:0x0048, code lost:
        
            if (r13 == r0) goto L15;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r13) {
            /*
                r12 = this;
                kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                int r1 = r12.label
                r2 = 2
                r3 = 1
                if (r1 == 0) goto L21
                if (r1 == r3) goto L18
                if (r1 != r2) goto L10
                kotlin.ResultKt.throwOnFailure(r13)
                goto L57
            L10:
                java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
                java.lang.String r13 = "call to 'resume' before 'invoke' with coroutine"
                r12.<init>(r13)
                throw r12
            L18:
                java.lang.Object r1 = r12.L$0
                kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
                kotlin.ResultKt.throwOnFailure(r13)
                r11 = r12
                goto L4b
            L21:
                kotlin.ResultKt.throwOnFailure(r13)
                java.lang.Object r13 = r12.L$0
                r1 = r13
                kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
                java.lang.Object r13 = r12.L$1
                java.lang.Object[] r13 = (java.lang.Object[]) r13
                kotlin.jvm.functions.Function7 r4 = r12.$transform$inlined
                r5 = 0
                r5 = r13[r5]
                r6 = r13[r3]
                r7 = r13[r2]
                r8 = 3
                r8 = r13[r8]
                r9 = 4
                r9 = r13[r9]
                r10 = 5
                r10 = r13[r10]
                r12.L$0 = r1
                r12.label = r3
                r11 = r12
                java.lang.Object r13 = r4.invoke(r5, r6, r7, r8, r9, r10, r11)
                if (r13 != r0) goto L4b
                goto L56
            L4b:
                r12 = 0
                r11.L$0 = r12
                r11.label = r2
                java.lang.Object r12 = r1.emit(r13, r11)
                if (r12 != r0) goto L57
            L56:
                return r0
            L57:
                kotlin.Unit r12 = kotlin.Unit.INSTANCE
                return r12
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.util.kotlin.FlowKt$combine$$inlined$combine$1.AnonymousClass3.invokeSuspend(java.lang.Object):java.lang.Object");
        }

        public final Object invokeSuspend$$forInline(Object obj) {
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            Object[] objArr = (Object[]) this.L$1;
            flowCollector.emit(this.$transform$inlined.invoke(objArr[0], objArr[1], objArr[2], objArr[3], objArr[4], objArr[5], this), this);
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

    public FlowKt$combine$$inlined$combine$1(Flow[] flowArr, Function7 function7) {
        this.$flows$inlined = flowArr;
        this.$transform$inlined$1 = function7;
    }

    @Override // kotlinx.coroutines.flow.Flow
    public Object collect(FlowCollector flowCollector, Continuation continuation) {
        Flow[] flowArr = this.$flows$inlined;
        Object combineInternal = CombineKt.combineInternal(flowArr, new AnonymousClass2(flowArr), new AnonymousClass3(null, this.$transform$inlined$1), flowCollector, continuation);
        return combineInternal == CoroutineSingletons.COROUTINE_SUSPENDED ? combineInternal : Unit.INSTANCE;
    }

    public Object collect$$forInline(FlowCollector flowCollector, Continuation continuation) {
        new ContinuationImpl(continuation) { // from class: com.android.systemui.util.kotlin.FlowKt$combine$$inlined$combine$1.1
            int label;
            /* synthetic */ Object result;

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                this.result = obj;
                this.label |= Integer.MIN_VALUE;
                return FlowKt$combine$$inlined$combine$1.this.collect(null, this);
            }
        };
        Flow[] flowArr = this.$flows$inlined;
        CombineKt.combineInternal(flowArr, new AnonymousClass2(flowArr), new AnonymousClass3(null, this.$transform$inlined$1), flowCollector, continuation);
        return Unit.INSTANCE;
    }
}
