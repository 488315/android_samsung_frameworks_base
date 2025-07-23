package com.android.systemui.keyguard.domain.interactor;

import android.content.Context;
import com.android.systemui.keyguard.shared.model.KeyguardSurfaceBehindModel;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class KeyguardSurfaceBehindInteractor$viewParams$1 extends SuspendLambda implements Function4 {
    final /* synthetic */ Context $context;
    final /* synthetic */ Lazy $inWindowLauncherUnlockAnimationInteractor;
    final /* synthetic */ SwipeToDismissInteractor $swipeToDismissInteractor;
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    /* synthetic */ boolean Z$2;
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
        boolean booleanValue = ((Boolean) obj).booleanValue();
        boolean booleanValue2 = ((Boolean) obj2).booleanValue();
        boolean booleanValue3 = ((Boolean) obj3).booleanValue();
        KeyguardSurfaceBehindInteractor$viewParams$1 keyguardSurfaceBehindInteractor$viewParams$1 = new KeyguardSurfaceBehindInteractor$viewParams$1(this.$inWindowLauncherUnlockAnimationInteractor, this.$context, this.$swipeToDismissInteractor, (Continuation) obj4);
        keyguardSurfaceBehindInteractor$viewParams$1.Z$0 = booleanValue;
        keyguardSurfaceBehindInteractor$viewParams$1.Z$1 = booleanValue2;
        keyguardSurfaceBehindInteractor$viewParams$1.Z$2 = booleanValue3;
        return keyguardSurfaceBehindInteractor$viewParams$1.invokeSuspend(Unit.INSTANCE);
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
        boolean z3 = this.Z$2;
        if (!z) {
            return new KeyguardSurfaceBehindModel(z2 ? 1.0f : 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 30, null);
        }
        if (z3) {
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
