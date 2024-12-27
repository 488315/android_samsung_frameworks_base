package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.ui.viewmodel.KeyguardClockViewModel;
import com.android.systemui.plugins.clocks.ClockConfig;
import com.android.systemui.plugins.clocks.ClockController;
import com.android.systemui.shade.shared.model.ShadeMode;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function5;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class KeyguardClockViewModel$currentClockLayout$1 extends SuspendLambda implements Function5 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    int label;

    public KeyguardClockViewModel$currentClockLayout$1(Continuation continuation) {
        super(5, continuation);
    }

    @Override // kotlin.jvm.functions.Function5
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        boolean booleanValue2 = ((Boolean) obj2).booleanValue();
        KeyguardClockViewModel$currentClockLayout$1 keyguardClockViewModel$currentClockLayout$1 = new KeyguardClockViewModel$currentClockLayout$1((Continuation) obj5);
        keyguardClockViewModel$currentClockLayout$1.Z$0 = booleanValue;
        keyguardClockViewModel$currentClockLayout$1.Z$1 = booleanValue2;
        keyguardClockViewModel$currentClockLayout$1.L$0 = (ShadeMode) obj3;
        keyguardClockViewModel$currentClockLayout$1.L$1 = (ClockController) obj4;
        return keyguardClockViewModel$currentClockLayout$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        ClockConfig config;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = this.Z$0;
        boolean z2 = this.Z$1;
        ShadeMode shadeMode = (ShadeMode) this.L$0;
        ClockController clockController = (ClockController) this.L$1;
        boolean areEqual = Intrinsics.areEqual(shadeMode, ShadeMode.Split.INSTANCE);
        return (clockController == null || (config = clockController.getConfig()) == null || !config.getUseCustomClockScene()) ? (areEqual && z2) ? KeyguardClockViewModel.ClockLayout.LARGE_CLOCK : (areEqual && z) ? KeyguardClockViewModel.ClockLayout.SPLIT_SHADE_LARGE_CLOCK : areEqual ? KeyguardClockViewModel.ClockLayout.SPLIT_SHADE_SMALL_CLOCK : z ? KeyguardClockViewModel.ClockLayout.LARGE_CLOCK : KeyguardClockViewModel.ClockLayout.SMALL_CLOCK : (areEqual && z2) ? KeyguardClockViewModel.ClockLayout.WEATHER_LARGE_CLOCK : (areEqual && z) ? KeyguardClockViewModel.ClockLayout.SPLIT_SHADE_WEATHER_LARGE_CLOCK : areEqual ? KeyguardClockViewModel.ClockLayout.SPLIT_SHADE_SMALL_CLOCK : z ? KeyguardClockViewModel.ClockLayout.WEATHER_LARGE_CLOCK : KeyguardClockViewModel.ClockLayout.SMALL_CLOCK;
    }
}
