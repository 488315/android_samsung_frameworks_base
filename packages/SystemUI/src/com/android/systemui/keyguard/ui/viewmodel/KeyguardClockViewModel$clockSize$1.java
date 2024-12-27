package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.shared.model.ClockSize;
import com.android.systemui.keyguard.shared.model.ClockSizeSetting;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

final class KeyguardClockViewModel$clockSize$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    public KeyguardClockViewModel$clockSize$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        KeyguardClockViewModel$clockSize$1 keyguardClockViewModel$clockSize$1 = new KeyguardClockViewModel$clockSize$1((Continuation) obj3);
        keyguardClockViewModel$clockSize$1.L$0 = (ClockSizeSetting) obj;
        keyguardClockViewModel$clockSize$1.L$1 = (ClockSize) obj2;
        return keyguardClockViewModel$clockSize$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return ((ClockSizeSetting) this.L$0) == ClockSizeSetting.SMALL ? ClockSize.SMALL : (ClockSize) this.L$1;
    }
}
