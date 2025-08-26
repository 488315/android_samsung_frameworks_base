package com.android.systemui.statusbar.chips.ui.viewmodel;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* loaded from: classes3.dex */
final class OngoingActivityChipsViewModel$isScreenReasonablyLarge$1 extends SuspendLambda implements Function3 {
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    int label;

    public OngoingActivityChipsViewModel$isScreenReasonablyLarge$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean zBooleanValue = ((Boolean) obj).booleanValue();
        boolean zBooleanValue2 = ((Boolean) obj2).booleanValue();
        OngoingActivityChipsViewModel$isScreenReasonablyLarge$1 ongoingActivityChipsViewModel$isScreenReasonablyLarge$1 = new OngoingActivityChipsViewModel$isScreenReasonablyLarge$1((Continuation) obj3);
        ongoingActivityChipsViewModel$isScreenReasonablyLarge$1.Z$0 = zBooleanValue;
        ongoingActivityChipsViewModel$isScreenReasonablyLarge$1.Z$1 = zBooleanValue2;
        return ongoingActivityChipsViewModel$isScreenReasonablyLarge$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return Boolean.valueOf(this.Z$0 || this.Z$1);
    }
}
