package com.android.systemui.keyguard.ui.viewmodel;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

final class KeyguardQuickAffordancesCombinedViewModel$shadeExpansionAlpha$1 extends SuspendLambda implements Function3 {
    /* synthetic */ float F$0;
    /* synthetic */ boolean Z$0;
    int label;

    public KeyguardQuickAffordancesCombinedViewModel$shadeExpansionAlpha$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        float floatValue = ((Number) obj2).floatValue();
        KeyguardQuickAffordancesCombinedViewModel$shadeExpansionAlpha$1 keyguardQuickAffordancesCombinedViewModel$shadeExpansionAlpha$1 = new KeyguardQuickAffordancesCombinedViewModel$shadeExpansionAlpha$1((Continuation) obj3);
        keyguardQuickAffordancesCombinedViewModel$shadeExpansionAlpha$1.Z$0 = booleanValue;
        keyguardQuickAffordancesCombinedViewModel$shadeExpansionAlpha$1.F$0 = floatValue;
        return keyguardQuickAffordancesCombinedViewModel$shadeExpansionAlpha$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return new Float(this.Z$0 ? 1 - this.F$0 : 0.0f);
    }
}
