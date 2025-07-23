package kotlinx.coroutines.flow;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function6;
import kotlinx.coroutines.flow.internal.CombineKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3 implements Flow {
    public final /* synthetic */ Flow[] $flows$inlined;
    public final /* synthetic */ Function6 $transform$inlined$1;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3$2, reason: invalid class name */
    public final class AnonymousClass2 extends SuspendLambda implements Function3 {
        final /* synthetic */ Function6 $transform$inlined;
        private /* synthetic */ Object L$0;
        /* synthetic */ Object L$1;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(Continuation continuation, Function6 function6) {
            super(3, continuation);
            this.$transform$inlined = function6;
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2((Continuation) obj3, this.$transform$inlined);
            anonymousClass2.L$0 = (FlowCollector) obj;
            anonymousClass2.L$1 = (Object[]) obj2;
            return anonymousClass2.invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:13:0x0051, code lost:
        
            if (r1.emit(r12, r10) == r0) goto L15;
         */
        /* JADX WARN: Code restructure failed: missing block: B:14:0x0053, code lost:
        
            return r0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:16:0x0045, code lost:
        
            if (r12 == r0) goto L15;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r12) {
            /*
                r11 = this;
                kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                int r1 = r11.label
                r2 = 2
                r3 = 1
                if (r1 == 0) goto L21
                if (r1 == r3) goto L18
                if (r1 != r2) goto L10
                kotlin.ResultKt.throwOnFailure(r12)
                goto L54
            L10:
                java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
                java.lang.String r12 = "call to 'resume' before 'invoke' with coroutine"
                r11.<init>(r12)
                throw r11
            L18:
                java.lang.Object r1 = r11.L$0
                kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
                kotlin.ResultKt.throwOnFailure(r12)
                r10 = r11
                goto L48
            L21:
                kotlin.ResultKt.throwOnFailure(r12)
                java.lang.Object r12 = r11.L$0
                r1 = r12
                kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
                java.lang.Object r12 = r11.L$1
                java.lang.Object[] r12 = (java.lang.Object[]) r12
                kotlin.jvm.functions.Function6 r4 = r11.$transform$inlined
                r5 = 0
                r5 = r12[r5]
                r6 = r12[r3]
                r7 = r12[r2]
                r8 = 3
                r8 = r12[r8]
                r9 = 4
                r9 = r12[r9]
                r11.L$0 = r1
                r11.label = r3
                r10 = r11
                java.lang.Object r12 = r4.invoke(r5, r6, r7, r8, r9, r10)
                if (r12 != r0) goto L48
                goto L53
            L48:
                r11 = 0
                r10.L$0 = r11
                r10.label = r2
                java.lang.Object r11 = r1.emit(r12, r10)
                if (r11 != r0) goto L54
            L53:
                return r0
            L54:
                kotlin.Unit r11 = kotlin.Unit.INSTANCE
                return r11
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3.AnonymousClass2.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    public FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3(Flow[] flowArr, Function6 function6) {
        this.$flows$inlined = flowArr;
        this.$transform$inlined$1 = function6;
    }

    @Override // kotlinx.coroutines.flow.Flow
    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
        Object combineInternal = CombineKt.combineInternal(this.$flows$inlined, FlowKt__ZipKt$nullArrayFactory$1.INSTANCE, new AnonymousClass2(null, this.$transform$inlined$1), flowCollector, continuation);
        return combineInternal == CoroutineSingletons.COROUTINE_SUSPENDED ? combineInternal : Unit.INSTANCE;
    }
}
