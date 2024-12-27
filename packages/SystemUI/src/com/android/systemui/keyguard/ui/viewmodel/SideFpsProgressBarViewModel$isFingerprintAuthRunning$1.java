package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.biometrics.shared.model.AuthenticationReason;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

final class SideFpsProgressBarViewModel$isFingerprintAuthRunning$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    int label;

    public SideFpsProgressBarViewModel$isFingerprintAuthRunning$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        SideFpsProgressBarViewModel$isFingerprintAuthRunning$1 sideFpsProgressBarViewModel$isFingerprintAuthRunning$1 = new SideFpsProgressBarViewModel$isFingerprintAuthRunning$1((Continuation) obj3);
        sideFpsProgressBarViewModel$isFingerprintAuthRunning$1.Z$0 = booleanValue;
        sideFpsProgressBarViewModel$isFingerprintAuthRunning$1.L$0 = (AuthenticationReason) obj2;
        return sideFpsProgressBarViewModel$isFingerprintAuthRunning$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return Boolean.valueOf(this.Z$0 || Intrinsics.areEqual((AuthenticationReason) this.L$0, AuthenticationReason.BiometricPromptAuthentication.INSTANCE));
    }
}
