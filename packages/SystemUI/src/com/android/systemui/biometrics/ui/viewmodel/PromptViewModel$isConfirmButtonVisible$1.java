package com.android.systemui.biometrics.ui.viewmodel;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class PromptViewModel$isConfirmButtonVisible$1 extends SuspendLambda implements Function4 {
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    int label;

    public PromptViewModel$isConfirmButtonVisible$1(Continuation continuation) {
        super(4, continuation);
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        boolean booleanValue = ((Boolean) obj3).booleanValue();
        PromptViewModel$isConfirmButtonVisible$1 promptViewModel$isConfirmButtonVisible$1 = new PromptViewModel$isConfirmButtonVisible$1((Continuation) obj4);
        promptViewModel$isConfirmButtonVisible$1.L$0 = (PromptSize) obj;
        promptViewModel$isConfirmButtonVisible$1.Z$0 = booleanValue;
        return promptViewModel$isConfirmButtonVisible$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return Boolean.valueOf(PromptSizeKt.isNotSmall((PromptSize) this.L$0) && this.Z$0);
    }
}
