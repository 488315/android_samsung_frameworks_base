package com.android.systemui.biometrics.ui.viewmodel;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function6;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class PromptViewModel$canTryAgainNow$1 extends SuspendLambda implements Function6 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    int label;

    public PromptViewModel$canTryAgainNow$1(Continuation continuation) {
        super(6, continuation);
    }

    @Override // kotlin.jvm.functions.Function6
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        boolean booleanValue2 = ((Boolean) obj5).booleanValue();
        PromptViewModel$canTryAgainNow$1 promptViewModel$canTryAgainNow$1 = new PromptViewModel$canTryAgainNow$1((Continuation) obj6);
        promptViewModel$canTryAgainNow$1.Z$0 = booleanValue;
        promptViewModel$canTryAgainNow$1.L$0 = (PromptSize) obj2;
        promptViewModel$canTryAgainNow$1.L$1 = (PromptAuthState) obj4;
        promptViewModel$canTryAgainNow$1.Z$1 = booleanValue2;
        return promptViewModel$canTryAgainNow$1.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:9:0x0023, code lost:
    
        if ((!r1.isAuthenticated) != false) goto L13;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r3) {
        /*
            r2 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r0 = r2.label
            if (r0 != 0) goto L2c
            kotlin.ResultKt.throwOnFailure(r3)
            boolean r3 = r2.Z$0
            java.lang.Object r0 = r2.L$0
            com.android.systemui.biometrics.ui.viewmodel.PromptSize r0 = (com.android.systemui.biometrics.ui.viewmodel.PromptSize) r0
            java.lang.Object r1 = r2.L$1
            com.android.systemui.biometrics.ui.viewmodel.PromptAuthState r1 = (com.android.systemui.biometrics.ui.viewmodel.PromptAuthState) r1
            boolean r2 = r2.Z$1
            if (r3 == 0) goto L26
            boolean r3 = com.android.systemui.biometrics.ui.viewmodel.PromptSizeKt.isNotSmall(r0)
            if (r3 == 0) goto L26
            if (r2 == 0) goto L26
            boolean r2 = r1.isAuthenticated
            r3 = 1
            r2 = r2 ^ r3
            if (r2 == 0) goto L26
            goto L27
        L26:
            r3 = 0
        L27:
            java.lang.Boolean r2 = java.lang.Boolean.valueOf(r3)
            return r2
        L2c:
            java.lang.IllegalStateException r2 = new java.lang.IllegalStateException
            java.lang.String r3 = "call to 'resume' before 'invoke' with coroutine"
            r2.<init>(r3)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$canTryAgainNow$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
