package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.plugins.clocks.ClockController;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

final class KeyguardClockViewModel$hasCustomWeatherDataDisplay$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    int label;

    public KeyguardClockViewModel$hasCustomWeatherDataDisplay$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        KeyguardClockViewModel$hasCustomWeatherDataDisplay$1 keyguardClockViewModel$hasCustomWeatherDataDisplay$1 = new KeyguardClockViewModel$hasCustomWeatherDataDisplay$1((Continuation) obj3);
        keyguardClockViewModel$hasCustomWeatherDataDisplay$1.Z$0 = booleanValue;
        keyguardClockViewModel$hasCustomWeatherDataDisplay$1.L$0 = (ClockController) obj2;
        return keyguardClockViewModel$hasCustomWeatherDataDisplay$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = this.Z$0;
        ClockController clockController = (ClockController) this.L$0;
        boolean z2 = false;
        if (clockController != null) {
            if ((z ? clockController.getLargeClock() : clockController.getSmallClock()).getConfig().getHasCustomWeatherDataDisplay()) {
                z2 = true;
            }
        }
        return Boolean.valueOf(z2);
    }
}
