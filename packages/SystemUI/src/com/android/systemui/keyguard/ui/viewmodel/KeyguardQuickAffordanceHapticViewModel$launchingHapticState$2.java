package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordanceHapticViewModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class KeyguardQuickAffordanceHapticViewModel$launchingHapticState$2 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    public KeyguardQuickAffordanceHapticViewModel$launchingHapticState$2(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        KeyguardQuickAffordanceHapticViewModel$launchingHapticState$2 keyguardQuickAffordanceHapticViewModel$launchingHapticState$2 = new KeyguardQuickAffordanceHapticViewModel$launchingHapticState$2((Continuation) obj3);
        keyguardQuickAffordanceHapticViewModel$launchingHapticState$2.L$0 = (String) obj;
        keyguardQuickAffordanceHapticViewModel$launchingHapticState$2.L$1 = (KeyguardQuickAffordanceConfig.LaunchingFromTriggeredResult) obj2;
        return keyguardQuickAffordanceHapticViewModel$launchingHapticState$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        String str = (String) this.L$0;
        KeyguardQuickAffordanceConfig.LaunchingFromTriggeredResult launchingFromTriggeredResult = (KeyguardQuickAffordanceConfig.LaunchingFromTriggeredResult) this.L$1;
        if (str != null) {
            if (str.equals(launchingFromTriggeredResult != null ? launchingFromTriggeredResult.configKey : null) && launchingFromTriggeredResult != null && launchingFromTriggeredResult.launched) {
                return KeyguardQuickAffordanceHapticViewModel.HapticState.LAUNCH;
            }
        }
        return KeyguardQuickAffordanceHapticViewModel.HapticState.NO_HAPTICS;
    }
}
