package com.android.systemui.keyguard.animator;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

final class KeyguardEditModeAnimatorController$actionDown$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ KeyguardEditModeAnimatorController this$0;

    public KeyguardEditModeAnimatorController$actionDown$1(KeyguardEditModeAnimatorController keyguardEditModeAnimatorController, Continuation continuation) {
        super(2, continuation);
        this.this$0 = keyguardEditModeAnimatorController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        KeyguardEditModeAnimatorController$actionDown$1 keyguardEditModeAnimatorController$actionDown$1 = new KeyguardEditModeAnimatorController$actionDown$1(this.this$0, continuation);
        keyguardEditModeAnimatorController$actionDown$1.L$0 = obj;
        return keyguardEditModeAnimatorController$actionDown$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((KeyguardEditModeAnimatorController$actionDown$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r14) {
        /*
            Method dump skipped, instructions count: 319
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.animator.KeyguardEditModeAnimatorController$actionDown$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
