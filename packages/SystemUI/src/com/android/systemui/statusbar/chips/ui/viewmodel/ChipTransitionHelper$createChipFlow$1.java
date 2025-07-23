package com.android.systemui.statusbar.chips.ui.viewmodel;

import com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class ChipTransitionHelper$createChipFlow$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    int label;

    public ChipTransitionHelper$createChipFlow$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean booleanValue = ((Boolean) obj2).booleanValue();
        ChipTransitionHelper$createChipFlow$1 chipTransitionHelper$createChipFlow$1 = new ChipTransitionHelper$createChipFlow$1((Continuation) obj3);
        chipTransitionHelper$createChipFlow$1.L$0 = (OngoingActivityChipModel) obj;
        chipTransitionHelper$createChipFlow$1.Z$0 = booleanValue;
        return chipTransitionHelper$createChipFlow$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return this.Z$0 ? new OngoingActivityChipModel.Inactive(false, null, 2, null) : (OngoingActivityChipModel) this.L$0;
    }
}
