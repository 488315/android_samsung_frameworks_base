package com.android.systemui.biometrics.ui.viewmodel;

import com.android.systemui.biometrics.shared.model.BiometricModalities;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

final class PromptViewModel$isTryAgainButtonVisible$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    int label;

    public PromptViewModel$isTryAgainButtonVisible$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        PromptViewModel$isTryAgainButtonVisible$1 promptViewModel$isTryAgainButtonVisible$1 = new PromptViewModel$isTryAgainButtonVisible$1((Continuation) obj3);
        promptViewModel$isTryAgainButtonVisible$1.Z$0 = booleanValue;
        promptViewModel$isTryAgainButtonVisible$1.L$0 = (BiometricModalities) obj2;
        return promptViewModel$isTryAgainButtonVisible$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return Boolean.valueOf(this.Z$0 && ((BiometricModalities) this.L$0).getHasFaceOnly());
    }
}
