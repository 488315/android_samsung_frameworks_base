package com.android.systemui.keyguard.domain.interactor;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class WindowManagerLockscreenVisibilityInteractor$surfaceBehindVisibility$2$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    int label;

    public WindowManagerLockscreenVisibilityInteractor$surfaceBehindVisibility$2$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean booleanValue = ((Boolean) obj2).booleanValue();
        WindowManagerLockscreenVisibilityInteractor$surfaceBehindVisibility$2$1 windowManagerLockscreenVisibilityInteractor$surfaceBehindVisibility$2$1 = new WindowManagerLockscreenVisibilityInteractor$surfaceBehindVisibility$2$1((Continuation) obj3);
        windowManagerLockscreenVisibilityInteractor$surfaceBehindVisibility$2$1.L$0 = (Boolean) obj;
        windowManagerLockscreenVisibilityInteractor$surfaceBehindVisibility$2$1.Z$0 = booleanValue;
        return windowManagerLockscreenVisibilityInteractor$surfaceBehindVisibility$2$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Boolean bool = (Boolean) this.L$0;
        boolean z = this.Z$0;
        if (bool != null) {
            z = bool.booleanValue();
        }
        return Boolean.valueOf(z);
    }
}
