package com.android.systemui.statusbar.pipeline.satellite.ui.viewmodel;

import com.android.systemui.statusbar.pipeline.satellite.shared.model.SatelliteConnectionState;
import com.android.systemui.statusbar.pipeline.satellite.ui.model.SatelliteIconModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;

/* loaded from: classes3.dex */
final class DeviceBasedSatelliteViewModelImpl$icon$1 extends SuspendLambda implements Function4 {
    /* synthetic */ int I$0;
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    int label;

    public DeviceBasedSatelliteViewModelImpl$icon$1(Continuation continuation) {
        super(4, continuation);
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        boolean zBooleanValue = ((Boolean) obj).booleanValue();
        int iIntValue = ((Number) obj3).intValue();
        DeviceBasedSatelliteViewModelImpl$icon$1 deviceBasedSatelliteViewModelImpl$icon$1 = new DeviceBasedSatelliteViewModelImpl$icon$1((Continuation) obj4);
        deviceBasedSatelliteViewModelImpl$icon$1.Z$0 = zBooleanValue;
        deviceBasedSatelliteViewModelImpl$icon$1.L$0 = (SatelliteConnectionState) obj2;
        deviceBasedSatelliteViewModelImpl$icon$1.I$0 = iIntValue;
        return deviceBasedSatelliteViewModelImpl$icon$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = this.Z$0;
        SatelliteConnectionState satelliteConnectionState = (SatelliteConnectionState) this.L$0;
        int i = this.I$0;
        if (!z) {
            return null;
        }
        SatelliteIconModel.INSTANCE.getClass();
        return SatelliteIconModel.fromConnectionState(satelliteConnectionState, i);
    }
}
