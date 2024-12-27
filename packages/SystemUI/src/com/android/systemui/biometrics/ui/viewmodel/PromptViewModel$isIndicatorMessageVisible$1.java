package com.android.systemui.biometrics.ui.viewmodel;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class PromptViewModel$isIndicatorMessageVisible$1 extends SuspendLambda implements Function4 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    public PromptViewModel$isIndicatorMessageVisible$1(Continuation continuation) {
        super(4, continuation);
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        PromptViewModel$isIndicatorMessageVisible$1 promptViewModel$isIndicatorMessageVisible$1 = new PromptViewModel$isIndicatorMessageVisible$1((Continuation) obj4);
        promptViewModel$isIndicatorMessageVisible$1.L$0 = (PromptSize) obj;
        promptViewModel$isIndicatorMessageVisible$1.L$1 = (PromptMessage) obj3;
        return promptViewModel$isIndicatorMessageVisible$1.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:9:0x0034, code lost:
    
        if ((!kotlin.text.StringsKt__StringsJVMKt.isBlank(r1 instanceof com.android.systemui.biometrics.ui.viewmodel.PromptMessage.Error ? ((com.android.systemui.biometrics.ui.viewmodel.PromptMessage.Error) r1).errorMessage : r1 instanceof com.android.systemui.biometrics.ui.viewmodel.PromptMessage.Help ? ((com.android.systemui.biometrics.ui.viewmodel.PromptMessage.Help) r1).helpMessage : "")) != false) goto L17;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r2) {
        /*
            r1 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r0 = r1.label
            if (r0 != 0) goto L3d
            kotlin.ResultKt.throwOnFailure(r2)
            java.lang.Object r2 = r1.L$0
            com.android.systemui.biometrics.ui.viewmodel.PromptSize r2 = (com.android.systemui.biometrics.ui.viewmodel.PromptSize) r2
            java.lang.Object r1 = r1.L$1
            com.android.systemui.biometrics.ui.viewmodel.PromptMessage r1 = (com.android.systemui.biometrics.ui.viewmodel.PromptMessage) r1
            boolean r2 = com.android.systemui.biometrics.ui.viewmodel.PromptSizeKt.isMedium(r2)
            if (r2 == 0) goto L37
            r1.getClass()
            boolean r2 = r1 instanceof com.android.systemui.biometrics.ui.viewmodel.PromptMessage.Error
            if (r2 == 0) goto L23
            com.android.systemui.biometrics.ui.viewmodel.PromptMessage$Error r1 = (com.android.systemui.biometrics.ui.viewmodel.PromptMessage.Error) r1
            java.lang.String r1 = r1.errorMessage
            goto L2e
        L23:
            boolean r2 = r1 instanceof com.android.systemui.biometrics.ui.viewmodel.PromptMessage.Help
            if (r2 == 0) goto L2c
            com.android.systemui.biometrics.ui.viewmodel.PromptMessage$Help r1 = (com.android.systemui.biometrics.ui.viewmodel.PromptMessage.Help) r1
            java.lang.String r1 = r1.helpMessage
            goto L2e
        L2c:
            java.lang.String r1 = ""
        L2e:
            boolean r1 = kotlin.text.StringsKt__StringsJVMKt.isBlank(r1)
            r2 = 1
            r1 = r1 ^ r2
            if (r1 == 0) goto L37
            goto L38
        L37:
            r2 = 0
        L38:
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r2)
            return r1
        L3d:
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r1.<init>(r2)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$isIndicatorMessageVisible$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
