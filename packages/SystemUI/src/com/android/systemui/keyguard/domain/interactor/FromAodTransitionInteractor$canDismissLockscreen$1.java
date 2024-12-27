package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.keyguard.shared.model.BiometricUnlockMode;
import com.android.systemui.keyguard.shared.model.BiometricUnlockModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;

final class FromAodTransitionInteractor$canDismissLockscreen$1 extends SuspendLambda implements Function4 {
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    int label;

    public FromAodTransitionInteractor$canDismissLockscreen$1(Continuation continuation) {
        super(4, continuation);
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        boolean booleanValue2 = ((Boolean) obj2).booleanValue();
        FromAodTransitionInteractor$canDismissLockscreen$1 fromAodTransitionInteractor$canDismissLockscreen$1 = new FromAodTransitionInteractor$canDismissLockscreen$1((Continuation) obj4);
        fromAodTransitionInteractor$canDismissLockscreen$1.Z$0 = booleanValue;
        fromAodTransitionInteractor$canDismissLockscreen$1.Z$1 = booleanValue2;
        fromAodTransitionInteractor$canDismissLockscreen$1.L$0 = (BiometricUnlockModel) obj3;
        return fromAodTransitionInteractor$canDismissLockscreen$1.invokeSuspend(Unit.INSTANCE);
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
        BiometricUnlockModel biometricUnlockModel = (BiometricUnlockModel) this.L$0;
        BiometricUnlockMode.Companion companion = BiometricUnlockMode.Companion;
        BiometricUnlockMode biometricUnlockMode = biometricUnlockModel.mode;
        companion.getClass();
        return Boolean.valueOf(BiometricUnlockMode.wakeAndUnlockModes.contains(biometricUnlockMode) || (!z && z2));
    }
}
