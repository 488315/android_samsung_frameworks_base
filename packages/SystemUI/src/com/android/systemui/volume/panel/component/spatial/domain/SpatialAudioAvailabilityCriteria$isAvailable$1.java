package com.android.systemui.volume.panel.component.spatial.domain;

import com.android.systemui.volume.panel.component.spatial.domain.model.SpatialAudioAvailabilityModel;
import com.android.systemui.volume.panel.component.spatial.domain.model.SpatialAudioEnabledModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

final class SpatialAudioAvailabilityCriteria$isAvailable$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    public SpatialAudioAvailabilityCriteria$isAvailable$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        SpatialAudioAvailabilityCriteria$isAvailable$1 spatialAudioAvailabilityCriteria$isAvailable$1 = new SpatialAudioAvailabilityCriteria$isAvailable$1((Continuation) obj3);
        spatialAudioAvailabilityCriteria$isAvailable$1.L$0 = (SpatialAudioAvailabilityModel) obj;
        spatialAudioAvailabilityCriteria$isAvailable$1.L$1 = (SpatialAudioEnabledModel) obj2;
        return spatialAudioAvailabilityCriteria$isAvailable$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        SpatialAudioAvailabilityModel spatialAudioAvailabilityModel = (SpatialAudioAvailabilityModel) this.L$0;
        SpatialAudioEnabledModel spatialAudioEnabledModel = (SpatialAudioEnabledModel) this.L$1;
        boolean z = false;
        if ((spatialAudioAvailabilityModel instanceof SpatialAudioAvailabilityModel.SpatialAudio) && !(spatialAudioEnabledModel instanceof SpatialAudioEnabledModel.Unknown)) {
            z = true;
        }
        return Boolean.valueOf(z);
    }
}
