package com.android.systemui.biometrics.ui.viewmodel;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function5;

/* loaded from: classes.dex */
final class PromptViewModel$isCredentialButtonVisible$1 extends SuspendLambda implements Function5 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ boolean Z$0;
    int label;

    public PromptViewModel$isCredentialButtonVisible$1(Continuation continuation) {
        super(5, continuation);
    }

    @Override // kotlin.jvm.functions.Function5
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        boolean zBooleanValue = ((Boolean) obj4).booleanValue();
        PromptViewModel$isCredentialButtonVisible$1 promptViewModel$isCredentialButtonVisible$1 = new PromptViewModel$isCredentialButtonVisible$1((Continuation) obj5);
        promptViewModel$isCredentialButtonVisible$1.L$0 = (PromptSize) obj;
        promptViewModel$isCredentialButtonVisible$1.L$1 = (PromptAuthState) obj3;
        promptViewModel$isCredentialButtonVisible$1.Z$0 = zBooleanValue;
        return promptViewModel$isCredentialButtonVisible$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return Boolean.valueOf(PromptSizeKt.isMedium((PromptSize) this.L$0) && !((PromptAuthState) this.L$1).isAuthenticated && this.Z$0);
    }
}
