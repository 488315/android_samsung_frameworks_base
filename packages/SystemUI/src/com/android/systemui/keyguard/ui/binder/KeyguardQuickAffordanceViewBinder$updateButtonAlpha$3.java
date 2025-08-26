package com.android.systemui.keyguard.ui.binder;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* loaded from: classes2.dex */
final class KeyguardQuickAffordanceViewBinder$updateButtonAlpha$3 extends SuspendLambda implements Function3 {
    /* synthetic */ float F$0;
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ KeyguardQuickAffordanceViewBinder this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardQuickAffordanceViewBinder$updateButtonAlpha$3(KeyguardQuickAffordanceViewBinder keyguardQuickAffordanceViewBinder, Continuation continuation) {
        super(3, continuation);
        this.this$0 = keyguardQuickAffordanceViewBinder;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean zBooleanValue = ((Boolean) obj).booleanValue();
        float fFloatValue = ((Number) obj2).floatValue();
        KeyguardQuickAffordanceViewBinder$updateButtonAlpha$3 keyguardQuickAffordanceViewBinder$updateButtonAlpha$3 = new KeyguardQuickAffordanceViewBinder$updateButtonAlpha$3(this.this$0, (Continuation) obj3);
        keyguardQuickAffordanceViewBinder$updateButtonAlpha$3.Z$0 = zBooleanValue;
        keyguardQuickAffordanceViewBinder$updateButtonAlpha$3.F$0 = fFloatValue;
        return keyguardQuickAffordanceViewBinder$updateButtonAlpha$3.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = this.Z$0;
        float f = this.F$0;
        if (z) {
            this.this$0.getClass();
            f = 0.3f;
        }
        return new Float(f);
    }
}
