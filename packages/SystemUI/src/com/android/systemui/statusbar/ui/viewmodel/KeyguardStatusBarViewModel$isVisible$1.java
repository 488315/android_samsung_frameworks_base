package com.android.systemui.statusbar.ui.viewmodel;

import com.android.systemui.keyguard.shared.model.StatusBarState;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class KeyguardStatusBarViewModel$isVisible$1 extends SuspendLambda implements Function4 {
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    int label;

    public KeyguardStatusBarViewModel$isVisible$1(Continuation continuation) {
        super(4, continuation);
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        boolean booleanValue2 = ((Boolean) obj3).booleanValue();
        KeyguardStatusBarViewModel$isVisible$1 keyguardStatusBarViewModel$isVisible$1 = new KeyguardStatusBarViewModel$isVisible$1((Continuation) obj4);
        keyguardStatusBarViewModel$isVisible$1.Z$0 = booleanValue;
        keyguardStatusBarViewModel$isVisible$1.L$0 = (StatusBarState) obj2;
        keyguardStatusBarViewModel$isVisible$1.Z$1 = booleanValue2;
        return keyguardStatusBarViewModel$isVisible$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return Boolean.valueOf((this.Z$0 || ((StatusBarState) this.L$0) != StatusBarState.KEYGUARD || this.Z$1) ? false : true);
    }
}
