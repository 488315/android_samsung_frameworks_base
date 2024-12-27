package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.plugins.clocks.ClockController;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

final class KeyguardClockViewModel$hasCustomPositionUpdatedAnimation$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    int label;

    public KeyguardClockViewModel$hasCustomPositionUpdatedAnimation$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean booleanValue = ((Boolean) obj2).booleanValue();
        KeyguardClockViewModel$hasCustomPositionUpdatedAnimation$1 keyguardClockViewModel$hasCustomPositionUpdatedAnimation$1 = new KeyguardClockViewModel$hasCustomPositionUpdatedAnimation$1((Continuation) obj3);
        keyguardClockViewModel$hasCustomPositionUpdatedAnimation$1.L$0 = (ClockController) obj;
        keyguardClockViewModel$hasCustomPositionUpdatedAnimation$1.Z$0 = booleanValue;
        return keyguardClockViewModel$hasCustomPositionUpdatedAnimation$1.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0024, code lost:
    
        if (r1.getHasCustomPositionUpdatedAnimation() == true) goto L15;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r2) {
        /*
            r1 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r0 = r1.label
            if (r0 != 0) goto L2d
            kotlin.ResultKt.throwOnFailure(r2)
            java.lang.Object r2 = r1.L$0
            com.android.systemui.plugins.clocks.ClockController r2 = (com.android.systemui.plugins.clocks.ClockController) r2
            boolean r1 = r1.Z$0
            if (r1 == 0) goto L27
            if (r2 == 0) goto L27
            com.android.systemui.plugins.clocks.ClockFaceController r1 = r2.getLargeClock()
            if (r1 == 0) goto L27
            com.android.systemui.plugins.clocks.ClockFaceConfig r1 = r1.getConfig()
            if (r1 == 0) goto L27
            boolean r1 = r1.getHasCustomPositionUpdatedAnimation()
            r2 = 1
            if (r1 != r2) goto L27
            goto L28
        L27:
            r2 = 0
        L28:
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r2)
            return r1
        L2d:
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r1.<init>(r2)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.viewmodel.KeyguardClockViewModel$hasCustomPositionUpdatedAnimation$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
