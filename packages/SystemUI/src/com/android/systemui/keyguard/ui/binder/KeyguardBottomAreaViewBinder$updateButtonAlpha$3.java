package com.android.systemui.keyguard.ui.binder;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class KeyguardBottomAreaViewBinder$updateButtonAlpha$3 extends SuspendLambda implements Function3 {
    /* synthetic */ float F$0;
    /* synthetic */ boolean Z$0;
    int label;

    public KeyguardBottomAreaViewBinder$updateButtonAlpha$3(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        float floatValue = ((Number) obj2).floatValue();
        KeyguardBottomAreaViewBinder$updateButtonAlpha$3 keyguardBottomAreaViewBinder$updateButtonAlpha$3 = new KeyguardBottomAreaViewBinder$updateButtonAlpha$3((Continuation) obj3);
        keyguardBottomAreaViewBinder$updateButtonAlpha$3.Z$0 = booleanValue;
        keyguardBottomAreaViewBinder$updateButtonAlpha$3.F$0 = floatValue;
        return keyguardBottomAreaViewBinder$updateButtonAlpha$3.invokeSuspend(Unit.INSTANCE);
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
            f = 0.3f;
        }
        return new Float(f);
    }
}
