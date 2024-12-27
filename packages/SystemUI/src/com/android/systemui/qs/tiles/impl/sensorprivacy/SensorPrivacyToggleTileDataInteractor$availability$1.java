package com.android.systemui.qs.tiles.impl.sensorprivacy;

import com.android.systemui.statusbar.policy.IndividualSensorPrivacyControllerImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.flow.FlowCollector;

final class SensorPrivacyToggleTileDataInteractor$availability$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ SensorPrivacyToggleTileDataInteractor this$0;

    public SensorPrivacyToggleTileDataInteractor$availability$1(SensorPrivacyToggleTileDataInteractor sensorPrivacyToggleTileDataInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = sensorPrivacyToggleTileDataInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        SensorPrivacyToggleTileDataInteractor$availability$1 sensorPrivacyToggleTileDataInteractor$availability$1 = new SensorPrivacyToggleTileDataInteractor$availability$1(this.this$0, continuation);
        sensorPrivacyToggleTileDataInteractor$availability$1.L$0 = obj;
        return sensorPrivacyToggleTileDataInteractor$availability$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SensorPrivacyToggleTileDataInteractor$availability$1) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        FlowCollector flowCollector;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            flowCollector = (FlowCollector) this.L$0;
            SensorPrivacyToggleTileDataInteractor sensorPrivacyToggleTileDataInteractor = this.this$0;
            this.L$0 = flowCollector;
            this.label = 1;
            if (((IndividualSensorPrivacyControllerImpl) sensorPrivacyToggleTileDataInteractor.privacyController).mSensorPrivacyManager.supportsSensorToggle(sensorPrivacyToggleTileDataInteractor.sensorId)) {
                obj = BuildersKt.withContext(sensorPrivacyToggleTileDataInteractor.bgCoroutineContext, new SensorPrivacyToggleTileDataInteractor$isSensorDeviceConfigSet$2(sensorPrivacyToggleTileDataInteractor, null), this);
            } else {
                obj = Boolean.FALSE;
            }
            if (obj == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                if (i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            flowCollector = (FlowCollector) this.L$0;
            ResultKt.throwOnFailure(obj);
        }
        this.L$0 = null;
        this.label = 2;
        if (flowCollector.emit(obj, this) == coroutineSingletons) {
            return coroutineSingletons;
        }
        return Unit.INSTANCE;
    }
}
