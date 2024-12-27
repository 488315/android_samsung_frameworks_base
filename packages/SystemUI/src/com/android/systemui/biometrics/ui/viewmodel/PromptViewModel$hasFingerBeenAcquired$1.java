package com.android.systemui.biometrics.ui.viewmodel;

import com.android.systemui.biometrics.shared.model.BiometricModalities;
import com.android.systemui.keyguard.shared.model.AcquiredFingerprintAuthenticationStatus;
import com.android.systemui.keyguard.shared.model.FingerprintAuthenticationStatus;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
final class PromptViewModel$hasFingerBeenAcquired$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    public PromptViewModel$hasFingerBeenAcquired$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        PromptViewModel$hasFingerBeenAcquired$1 promptViewModel$hasFingerBeenAcquired$1 = new PromptViewModel$hasFingerBeenAcquired$1((Continuation) obj3);
        promptViewModel$hasFingerBeenAcquired$1.L$0 = (FingerprintAuthenticationStatus) obj;
        promptViewModel$hasFingerBeenAcquired$1.L$1 = (BiometricModalities) obj2;
        return promptViewModel$hasFingerBeenAcquired$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        FingerprintAuthenticationStatus fingerprintAuthenticationStatus = (FingerprintAuthenticationStatus) this.L$0;
        return Boolean.valueOf(((BiometricModalities) this.L$1).getHasSfps() && (fingerprintAuthenticationStatus instanceof AcquiredFingerprintAuthenticationStatus) && ((AcquiredFingerprintAuthenticationStatus) fingerprintAuthenticationStatus).acquiredInfo == 7);
    }
}
