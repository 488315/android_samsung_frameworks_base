package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.shared.model.StatusBarState;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class AccessibilityActionsViewModel$isOnKeyguard$2 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    int label;

    public AccessibilityActionsViewModel$isOnKeyguard$2(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        AccessibilityActionsViewModel$isOnKeyguard$2 accessibilityActionsViewModel$isOnKeyguard$2 = new AccessibilityActionsViewModel$isOnKeyguard$2((Continuation) obj3);
        accessibilityActionsViewModel$isOnKeyguard$2.Z$0 = booleanValue;
        accessibilityActionsViewModel$isOnKeyguard$2.L$0 = (StatusBarState) obj2;
        return accessibilityActionsViewModel$isOnKeyguard$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return Boolean.valueOf(this.Z$0 && ((StatusBarState) this.L$0) == StatusBarState.KEYGUARD);
    }
}
