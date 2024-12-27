package com.android.systemui.keyguard.domain.interactor;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.DelayKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class KeyguardInteractor$alternateBouncerShowing$1 extends SuspendLambda implements Function3 {
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    int label;

    public KeyguardInteractor$alternateBouncerShowing$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        boolean booleanValue2 = ((Boolean) obj2).booleanValue();
        KeyguardInteractor$alternateBouncerShowing$1 keyguardInteractor$alternateBouncerShowing$1 = new KeyguardInteractor$alternateBouncerShowing$1((Continuation) obj3);
        keyguardInteractor$alternateBouncerShowing$1.Z$0 = booleanValue;
        keyguardInteractor$alternateBouncerShowing$1.Z$1 = booleanValue2;
        return keyguardInteractor$alternateBouncerShowing$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        boolean z;
        boolean z2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            z = this.Z$0;
            if (this.Z$1) {
                this.Z$0 = z;
                this.label = 1;
                if (DelayKt.delay(600L, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                z2 = z;
            }
            return Boolean.valueOf(z);
        }
        if (i != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        z2 = this.Z$0;
        ResultKt.throwOnFailure(obj);
        z = z2;
        return Boolean.valueOf(z);
    }
}
