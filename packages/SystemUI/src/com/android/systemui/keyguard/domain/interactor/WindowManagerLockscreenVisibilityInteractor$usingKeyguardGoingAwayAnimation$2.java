package com.android.systemui.keyguard.domain.interactor;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function5;

/* loaded from: classes2.dex */
final class WindowManagerLockscreenVisibilityInteractor$usingKeyguardGoingAwayAnimation$2 extends SuspendLambda implements Function5 {
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    /* synthetic */ boolean Z$2;
    /* synthetic */ boolean Z$3;
    int label;

    public WindowManagerLockscreenVisibilityInteractor$usingKeyguardGoingAwayAnimation$2(Continuation continuation) {
        super(5, continuation);
    }

    @Override // kotlin.jvm.functions.Function5
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        boolean zBooleanValue = ((Boolean) obj).booleanValue();
        boolean zBooleanValue2 = ((Boolean) obj2).booleanValue();
        boolean zBooleanValue3 = ((Boolean) obj3).booleanValue();
        boolean zBooleanValue4 = ((Boolean) obj4).booleanValue();
        WindowManagerLockscreenVisibilityInteractor$usingKeyguardGoingAwayAnimation$2 windowManagerLockscreenVisibilityInteractor$usingKeyguardGoingAwayAnimation$2 = new WindowManagerLockscreenVisibilityInteractor$usingKeyguardGoingAwayAnimation$2((Continuation) obj5);
        windowManagerLockscreenVisibilityInteractor$usingKeyguardGoingAwayAnimation$2.Z$0 = zBooleanValue;
        windowManagerLockscreenVisibilityInteractor$usingKeyguardGoingAwayAnimation$2.Z$1 = zBooleanValue2;
        windowManagerLockscreenVisibilityInteractor$usingKeyguardGoingAwayAnimation$2.Z$2 = zBooleanValue3;
        windowManagerLockscreenVisibilityInteractor$usingKeyguardGoingAwayAnimation$2.Z$3 = zBooleanValue4;
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
        boolean z2 = this.Z$1;
        boolean z3 = true;
        boolean z4 = this.Z$2 || this.Z$3;
        if (!z && (!z2 || !z4)) {
            z3 = false;
        }
        return Boolean.valueOf(z3);
    }
}
