package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.plugins.clocks.ClockController;
import com.android.systemui.plugins.clocks.ClockFaceConfig;
import com.android.systemui.plugins.clocks.ClockFaceController;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* loaded from: classes2.dex */
final class KeyguardClockViewModel$hasCustomPositionUpdatedAnimation$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    int label;

    public KeyguardClockViewModel$hasCustomPositionUpdatedAnimation$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean zBooleanValue = ((Boolean) obj2).booleanValue();
        KeyguardClockViewModel$hasCustomPositionUpdatedAnimation$1 keyguardClockViewModel$hasCustomPositionUpdatedAnimation$1 = new KeyguardClockViewModel$hasCustomPositionUpdatedAnimation$1((Continuation) obj3);
        keyguardClockViewModel$hasCustomPositionUpdatedAnimation$1.L$0 = (ClockController) obj;
        keyguardClockViewModel$hasCustomPositionUpdatedAnimation$1.Z$0 = zBooleanValue;
        return keyguardClockViewModel$hasCustomPositionUpdatedAnimation$1.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0027  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        boolean z;
        ClockFaceController largeClock;
        ClockFaceConfig config;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        ClockController clockController = (ClockController) this.L$0;
        if (!this.Z$0 || clockController == null || (largeClock = clockController.getLargeClock()) == null || (config = largeClock.getConfig()) == null) {
            z = false;
        } else {
            z = true;
            if (!config.getHasCustomPositionUpdatedAnimation()) {
            }
        }
        return Boolean.valueOf(z);
    }
}
