package com.android.systemui.statusbar.pipeline.mobile.domain.interactor;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.FlowCollector;

final class MobileIconsInteractorImpl$forcingCellularValidation$2 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    int label;

    public MobileIconsInteractorImpl$forcingCellularValidation$2(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        MobileIconsInteractorImpl$forcingCellularValidation$2 mobileIconsInteractorImpl$forcingCellularValidation$2 = new MobileIconsInteractorImpl$forcingCellularValidation$2((Continuation) obj3);
        mobileIconsInteractorImpl$forcingCellularValidation$2.L$0 = (FlowCollector) obj;
        return mobileIconsInteractorImpl$forcingCellularValidation$2.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x005a A[RETURN] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r6) {
        /*
            r5 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r5.label
            r2 = 3
            r3 = 2
            r4 = 1
            if (r1 == 0) goto L2b
            if (r1 == r4) goto L23
            if (r1 == r3) goto L1b
            if (r1 != r2) goto L13
            kotlin.ResultKt.throwOnFailure(r6)
            goto L5b
        L13:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L1b:
            java.lang.Object r1 = r5.L$0
            kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
            kotlin.ResultKt.throwOnFailure(r6)
            goto L4d
        L23:
            java.lang.Object r1 = r5.L$0
            kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
            kotlin.ResultKt.throwOnFailure(r6)
            goto L40
        L2b:
            kotlin.ResultKt.throwOnFailure(r6)
            java.lang.Object r6 = r5.L$0
            kotlinx.coroutines.flow.FlowCollector r6 = (kotlinx.coroutines.flow.FlowCollector) r6
            java.lang.Boolean r1 = java.lang.Boolean.TRUE
            r5.L$0 = r6
            r5.label = r4
            java.lang.Object r1 = r6.emit(r1, r5)
            if (r1 != r0) goto L3f
            return r0
        L3f:
            r1 = r6
        L40:
            r5.L$0 = r1
            r5.label = r3
            r3 = 2000(0x7d0, double:9.88E-321)
            java.lang.Object r6 = kotlinx.coroutines.DelayKt.delay(r3, r5)
            if (r6 != r0) goto L4d
            return r0
        L4d:
            java.lang.Boolean r6 = java.lang.Boolean.FALSE
            r3 = 0
            r5.L$0 = r3
            r5.label = r2
            java.lang.Object r5 = r1.emit(r6, r5)
            if (r5 != r0) goto L5b
            return r0
        L5b:
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl$forcingCellularValidation$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
