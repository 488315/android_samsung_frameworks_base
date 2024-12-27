package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.keyguard.shared.model.KeyguardState;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function5;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class WindowManagerLockscreenVisibilityInteractor$usingKeyguardGoingAwayAnimation$2 extends SuspendLambda implements Function5 {
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    /* synthetic */ boolean Z$2;
    int label;

    public WindowManagerLockscreenVisibilityInteractor$usingKeyguardGoingAwayAnimation$2(Continuation continuation) {
        super(5, continuation);
    }

    @Override // kotlin.jvm.functions.Function5
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        boolean booleanValue2 = ((Boolean) obj3).booleanValue();
        boolean booleanValue3 = ((Boolean) obj4).booleanValue();
        WindowManagerLockscreenVisibilityInteractor$usingKeyguardGoingAwayAnimation$2 windowManagerLockscreenVisibilityInteractor$usingKeyguardGoingAwayAnimation$2 = new WindowManagerLockscreenVisibilityInteractor$usingKeyguardGoingAwayAnimation$2((Continuation) obj5);
        windowManagerLockscreenVisibilityInteractor$usingKeyguardGoingAwayAnimation$2.Z$0 = booleanValue;
        windowManagerLockscreenVisibilityInteractor$usingKeyguardGoingAwayAnimation$2.L$0 = (KeyguardState) obj2;
        windowManagerLockscreenVisibilityInteractor$usingKeyguardGoingAwayAnimation$2.Z$1 = booleanValue2;
        windowManagerLockscreenVisibilityInteractor$usingKeyguardGoingAwayAnimation$2.Z$2 = booleanValue3;
        return windowManagerLockscreenVisibilityInteractor$usingKeyguardGoingAwayAnimation$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = this.Z$0;
        KeyguardState keyguardState = (KeyguardState) this.L$0;
        boolean z2 = true;
        boolean z3 = this.Z$1 || this.Z$2;
        if (!z && (keyguardState != KeyguardState.GONE || !z3)) {
            z2 = false;
        }
        return Boolean.valueOf(z2);
    }
}
