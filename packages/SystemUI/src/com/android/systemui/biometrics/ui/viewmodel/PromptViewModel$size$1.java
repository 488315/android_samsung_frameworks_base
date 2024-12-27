package com.android.systemui.biometrics.ui.viewmodel;

import com.android.systemui.biometrics.shared.model.BiometricModalities;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function6;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class PromptViewModel$size$1 extends SuspendLambda implements Function6 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    /* synthetic */ boolean Z$2;
    int label;

    public PromptViewModel$size$1(Continuation continuation) {
        super(6, continuation);
    }

    @Override // kotlin.jvm.functions.Function6
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        boolean booleanValue2 = ((Boolean) obj2).booleanValue();
        boolean booleanValue3 = ((Boolean) obj4).booleanValue();
        PromptViewModel$size$1 promptViewModel$size$1 = new PromptViewModel$size$1((Continuation) obj6);
        promptViewModel$size$1.Z$0 = booleanValue;
        promptViewModel$size$1.Z$1 = booleanValue2;
        promptViewModel$size$1.L$0 = (BiometricModalities) obj3;
        promptViewModel$size$1.Z$2 = booleanValue3;
        promptViewModel$size$1.L$1 = (FingerprintStartMode) obj5;
        return promptViewModel$size$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = this.Z$0;
        boolean z2 = this.Z$1;
        BiometricModalities biometricModalities = (BiometricModalities) this.L$0;
        boolean z3 = this.Z$2;
        return z ? PromptSize.LARGE : z2 ? PromptSize.MEDIUM : (!biometricModalities.getHasFaceOnly() || z3) ? (biometricModalities.getHasFaceAndFingerprint() && !z3 && ((FingerprintStartMode) this.L$1) == FingerprintStartMode.Pending) ? PromptSize.SMALL : PromptSize.MEDIUM : PromptSize.SMALL;
    }
}
