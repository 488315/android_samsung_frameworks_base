package com.android.systemui.keyguard;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class KeyguardSecLegacyUnlockAnimationControllerImpl$playUnlockAnimation$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ KeyguardSecLegacyUnlockAnimationControllerImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardSecLegacyUnlockAnimationControllerImpl$playUnlockAnimation$1(KeyguardSecLegacyUnlockAnimationControllerImpl keyguardSecLegacyUnlockAnimationControllerImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = keyguardSecLegacyUnlockAnimationControllerImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new KeyguardSecLegacyUnlockAnimationControllerImpl$playUnlockAnimation$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((KeyguardSecLegacyUnlockAnimationControllerImpl$playUnlockAnimation$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            this.label = 1;
            if (DelayKt.delay(500L, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        KeyguardSecLegacyUnlockAnimationControllerImpl keyguardSecLegacyUnlockAnimationControllerImpl = this.this$0;
        int i2 = KeyguardSecLegacyUnlockAnimationControllerImpl.$r8$clinit;
        keyguardSecLegacyUnlockAnimationControllerImpl.getClass();
        Log.d("KeyguardUnlock", "playUnlockAnimationForLauncher");
        keyguardSecLegacyUnlockAnimationControllerImpl.mainExecutor.execute(new KeyguardSecLegacyUnlockAnimationControllerImpl$playUnlockAnimationForLauncher$1(keyguardSecLegacyUnlockAnimationControllerImpl));
        return Unit.INSTANCE;
    }
}
