package com.android.systemui.keyguard.ui.viewmodel;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class KeyguardIndicationAreaViewModel$isIndicationAreaPadded$2 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    public KeyguardIndicationAreaViewModel$isIndicationAreaPadded$2(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        KeyguardIndicationAreaViewModel$isIndicationAreaPadded$2 keyguardIndicationAreaViewModel$isIndicationAreaPadded$2 = new KeyguardIndicationAreaViewModel$isIndicationAreaPadded$2((Continuation) obj3);
        keyguardIndicationAreaViewModel$isIndicationAreaPadded$2.L$0 = (KeyguardQuickAffordanceViewModel) obj;
        keyguardIndicationAreaViewModel$isIndicationAreaPadded$2.L$1 = (KeyguardQuickAffordanceViewModel) obj2;
        return keyguardIndicationAreaViewModel$isIndicationAreaPadded$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return Boolean.valueOf(((KeyguardQuickAffordanceViewModel) this.L$0).isVisible || ((KeyguardQuickAffordanceViewModel) this.L$1).isVisible);
    }
}
