package com.android.systemui.keyguard.animator;

import com.android.systemui.keyguard.KeyguardEditModeControllerImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class KeyguardEditModeAnimatorController$initAnimatorSet$1$1$onAnimationEnd$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ KeyguardEditModeAnimatorController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardEditModeAnimatorController$initAnimatorSet$1$1$onAnimationEnd$1(KeyguardEditModeAnimatorController keyguardEditModeAnimatorController, Continuation continuation) {
        super(2, continuation);
        this.this$0 = keyguardEditModeAnimatorController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new KeyguardEditModeAnimatorController$initAnimatorSet$1$1$onAnimationEnd$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((KeyguardEditModeAnimatorController$initAnimatorSet$1$1$onAnimationEnd$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            KeyguardEditModeAnimatorController keyguardEditModeAnimatorController = this.this$0;
            ((KeyguardEditModeControllerImpl) keyguardEditModeAnimatorController.keyguardEditModeController).startEditActivity(keyguardEditModeAnimatorController.keyguardTouchAnimator.getParentView$frameworks__base__packages__SystemUI__android_common__SystemUI_core().getContext(), false);
            long durationForCancelAnim = ((KeyguardEditModeControllerImpl) this.this$0.keyguardEditModeController).getDurationForCancelAnim();
            this.label = 1;
            if (DelayKt.delay(durationForCancelAnim, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        this.this$0.startCancelAnimation();
        return Unit.INSTANCE;
    }
}
