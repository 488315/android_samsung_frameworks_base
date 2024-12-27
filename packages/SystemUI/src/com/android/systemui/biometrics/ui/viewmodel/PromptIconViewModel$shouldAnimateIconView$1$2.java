package com.android.systemui.biometrics.ui.viewmodel;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class PromptIconViewModel$shouldAnimateIconView$1$2 extends SuspendLambda implements Function4 {
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    int label;
    final /* synthetic */ PromptIconViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PromptIconViewModel$shouldAnimateIconView$1$2(PromptIconViewModel promptIconViewModel, Continuation continuation) {
        super(4, continuation);
        this.this$0 = promptIconViewModel;
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        boolean booleanValue = ((Boolean) obj2).booleanValue();
        boolean booleanValue2 = ((Boolean) obj3).booleanValue();
        PromptIconViewModel$shouldAnimateIconView$1$2 promptIconViewModel$shouldAnimateIconView$1$2 = new PromptIconViewModel$shouldAnimateIconView$1$2(this.this$0, (Continuation) obj4);
        promptIconViewModel$shouldAnimateIconView$1$2.L$0 = (PromptAuthState) obj;
        promptIconViewModel$shouldAnimateIconView$1$2.Z$0 = booleanValue;
        promptIconViewModel$shouldAnimateIconView$1$2.Z$1 = booleanValue2;
        return promptIconViewModel$shouldAnimateIconView$1$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return Boolean.valueOf(this.Z$0 || ((PromptAuthState) this.L$0).isAuthenticated || this.Z$1 || ((Boolean) this.this$0._previousIconWasError.getValue()).booleanValue());
    }
}
