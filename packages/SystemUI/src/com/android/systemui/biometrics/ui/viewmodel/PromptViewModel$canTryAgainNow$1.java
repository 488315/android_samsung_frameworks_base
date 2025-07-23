package com.android.systemui.biometrics.ui.viewmodel;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function6;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
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

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return Boolean.valueOf(this.Z$0 && PromptSizeKt.isNotSmall((PromptSize) this.L$0) && this.Z$1 && !((PromptAuthState) this.L$1).isAuthenticated);
    }
}
