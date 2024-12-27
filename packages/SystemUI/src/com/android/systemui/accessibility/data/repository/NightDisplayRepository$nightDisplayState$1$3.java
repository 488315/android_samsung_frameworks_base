package com.android.systemui.accessibility.data.repository;

import com.android.systemui.accessibility.data.model.NightDisplayChangeEvent;
import com.android.systemui.accessibility.data.model.NightDisplayState;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class NightDisplayRepository$nightDisplayState$1$3 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    public NightDisplayRepository$nightDisplayState$1$3(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        NightDisplayRepository$nightDisplayState$1$3 nightDisplayRepository$nightDisplayState$1$3 = new NightDisplayRepository$nightDisplayState$1$3((Continuation) obj3);
        nightDisplayRepository$nightDisplayState$1$3.L$0 = (NightDisplayState) obj;
        nightDisplayRepository$nightDisplayState$1$3.L$1 = (NightDisplayChangeEvent) obj2;
        return nightDisplayRepository$nightDisplayState$1$3.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        NightDisplayState nightDisplayState = (NightDisplayState) this.L$0;
        NightDisplayChangeEvent nightDisplayChangeEvent = (NightDisplayChangeEvent) this.L$1;
        if (nightDisplayChangeEvent instanceof NightDisplayChangeEvent.OnActivatedChanged) {
            return NightDisplayState.copy$default(nightDisplayState, 0, ((NightDisplayChangeEvent.OnActivatedChanged) nightDisplayChangeEvent).isActivated, null, null, false, false, 61);
        }
        if (nightDisplayChangeEvent instanceof NightDisplayChangeEvent.OnAutoModeChanged) {
            return NightDisplayState.copy$default(nightDisplayState, ((NightDisplayChangeEvent.OnAutoModeChanged) nightDisplayChangeEvent).autoMode, false, null, null, false, false, 62);
        }
        if (nightDisplayChangeEvent instanceof NightDisplayChangeEvent.OnCustomStartTimeChanged) {
            return NightDisplayState.copy$default(nightDisplayState, 0, false, ((NightDisplayChangeEvent.OnCustomStartTimeChanged) nightDisplayChangeEvent).startTime, null, false, false, 59);
        }
        if (nightDisplayChangeEvent instanceof NightDisplayChangeEvent.OnCustomEndTimeChanged) {
            return NightDisplayState.copy$default(nightDisplayState, 0, false, null, ((NightDisplayChangeEvent.OnCustomEndTimeChanged) nightDisplayChangeEvent).endTime, false, false, 55);
        }
        if (nightDisplayChangeEvent instanceof NightDisplayChangeEvent.OnForceAutoModeChanged) {
            return NightDisplayState.copy$default(nightDisplayState, 0, false, null, null, ((NightDisplayChangeEvent.OnForceAutoModeChanged) nightDisplayChangeEvent).shouldForceAutoMode, false, 47);
        }
        if (nightDisplayChangeEvent instanceof NightDisplayChangeEvent.OnLocationEnabledChanged) {
            return NightDisplayState.copy$default(nightDisplayState, 0, false, null, null, false, ((NightDisplayChangeEvent.OnLocationEnabledChanged) nightDisplayChangeEvent).locationEnabled, 31);
        }
        throw new NoWhenBranchMatchedException();
    }
}
