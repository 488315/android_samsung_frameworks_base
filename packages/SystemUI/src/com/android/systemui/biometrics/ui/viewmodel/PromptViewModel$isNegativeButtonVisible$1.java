package com.android.systemui.biometrics.ui.viewmodel;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function5;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class PromptViewModel$isNegativeButtonVisible$1 extends SuspendLambda implements Function5 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ boolean Z$0;
    int label;

    public PromptViewModel$isNegativeButtonVisible$1(Continuation continuation) {
        super(5, continuation);
    }

    @Override // kotlin.jvm.functions.Function5
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        boolean booleanValue = ((Boolean) obj4).booleanValue();
        PromptViewModel$isNegativeButtonVisible$1 promptViewModel$isNegativeButtonVisible$1 = new PromptViewModel$isNegativeButtonVisible$1((Continuation) obj5);
        promptViewModel$isNegativeButtonVisible$1.L$0 = (PromptSize) obj;
        promptViewModel$isNegativeButtonVisible$1.L$1 = (PromptAuthState) obj3;
        promptViewModel$isNegativeButtonVisible$1.Z$0 = booleanValue;
        return promptViewModel$isNegativeButtonVisible$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return Boolean.valueOf((!PromptSizeKt.isNotSmall((PromptSize) this.L$0) || ((PromptAuthState) this.L$1).isAuthenticated || this.Z$0) ? false : true);
    }
}
