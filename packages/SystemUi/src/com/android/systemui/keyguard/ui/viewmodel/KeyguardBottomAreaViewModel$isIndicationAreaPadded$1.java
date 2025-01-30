package com.android.systemui.keyguard.ui.viewmodel;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.keyguard.ui.viewmodel.KeyguardBottomAreaViewModel$isIndicationAreaPadded$1", m277f = "KeyguardBottomAreaViewModel.kt", m278l = {}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
public final class KeyguardBottomAreaViewModel$isIndicationAreaPadded$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    public KeyguardBottomAreaViewModel$isIndicationAreaPadded$1(Continuation<? super KeyguardBottomAreaViewModel$isIndicationAreaPadded$1> continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        KeyguardBottomAreaViewModel$isIndicationAreaPadded$1 keyguardBottomAreaViewModel$isIndicationAreaPadded$1 = new KeyguardBottomAreaViewModel$isIndicationAreaPadded$1((Continuation) obj3);
        keyguardBottomAreaViewModel$isIndicationAreaPadded$1.L$0 = (KeyguardQuickAffordanceViewModel) obj;
        keyguardBottomAreaViewModel$isIndicationAreaPadded$1.L$1 = (KeyguardQuickAffordanceViewModel) obj2;
        return keyguardBottomAreaViewModel$isIndicationAreaPadded$1.invokeSuspend(Unit.INSTANCE);
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
