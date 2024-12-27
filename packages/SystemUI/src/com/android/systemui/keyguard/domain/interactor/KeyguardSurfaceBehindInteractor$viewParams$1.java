package com.android.systemui.keyguard.domain.interactor;

import android.content.Context;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.KeyguardSurfaceBehindModel;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import com.android.systemui.shade.data.repository.FlingInfo;
import com.android.systemui.util.kotlin.UtilsKt;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import dagger.Lazy;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class KeyguardSurfaceBehindInteractor$viewParams$1 extends SuspendLambda implements Function4 {
    final /* synthetic */ Context $context;
    final /* synthetic */ Lazy $inWindowLauncherUnlockAnimationInteractor;
    final /* synthetic */ SwipeToDismissInteractor $swipeToDismissInteractor;
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ boolean Z$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardSurfaceBehindInteractor$viewParams$1(Lazy lazy, Context context, SwipeToDismissInteractor swipeToDismissInteractor, Continuation continuation) {
        super(4, continuation);
        this.$inWindowLauncherUnlockAnimationInteractor = lazy;
        this.$context = context;
        this.$swipeToDismissInteractor = swipeToDismissInteractor;
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        boolean booleanValue = ((Boolean) obj3).booleanValue();
        KeyguardSurfaceBehindInteractor$viewParams$1 keyguardSurfaceBehindInteractor$viewParams$1 = new KeyguardSurfaceBehindInteractor$viewParams$1(this.$inWindowLauncherUnlockAnimationInteractor, this.$context, this.$swipeToDismissInteractor, (Continuation) obj4);
        keyguardSurfaceBehindInteractor$viewParams$1.L$0 = (TransitionStep) obj;
        keyguardSurfaceBehindInteractor$viewParams$1.L$1 = (KeyguardState) obj2;
        keyguardSurfaceBehindInteractor$viewParams$1.Z$0 = booleanValue;
        return keyguardSurfaceBehindInteractor$viewParams$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        TransitionStep transitionStep = (TransitionStep) this.L$0;
        KeyguardState keyguardState = (KeyguardState) this.L$1;
        boolean z = this.Z$0;
        KeyguardState keyguardState2 = transitionStep.to;
        KeyguardState keyguardState3 = KeyguardState.GONE;
        if (keyguardState2 != keyguardState3 || keyguardState == keyguardState3) {
            WindowManagerLockscreenVisibilityInteractor.Companion.getClass();
            KeyguardState.Companion.getClass();
            return new KeyguardSurfaceBehindModel(true ^ (keyguardState != keyguardState3) ? 1.0f : 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 30, null);
        }
        if (z) {
            return new KeyguardSurfaceBehindModel(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 30, null);
        }
        if (((InWindowLauncherUnlockAnimationInteractor) this.$inWindowLauncherUnlockAnimationInteractor.get()).isLauncherUnderneath()) {
            return new KeyguardSurfaceBehindModel(1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 30, null);
        }
        float px = UtilsKt.toPx(IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend, this.$context);
        FlingInfo flingInfo = (FlingInfo) this.$swipeToDismissInteractor.dismissFling.$$delegate_0.getValue();
        return new KeyguardSurfaceBehindModel(1.0f, 0.0f, 0.0f, px, flingInfo != null ? flingInfo.velocity : 0.0f);
    }
}
