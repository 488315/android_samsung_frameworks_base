package com.android.systemui.statusbar.pipeline.satellite.ui.viewmodel;

import com.android.systemui.statusbar.pipeline.satellite.shared.model.SatelliteConnectionState;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function6;

/* loaded from: classes3.dex */
final class DeviceBasedSatelliteViewModelImpl$showIcon$1$1 extends SuspendLambda implements Function6 {
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    /* synthetic */ boolean Z$2;
    /* synthetic */ boolean Z$3;
    int label;

    public DeviceBasedSatelliteViewModelImpl$showIcon$1$1(Continuation continuation) {
        super(6, continuation);
    }

    @Override // kotlin.jvm.functions.Function6
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6) {
        boolean zBooleanValue = ((Boolean) obj).booleanValue();
        boolean zBooleanValue2 = ((Boolean) obj2).booleanValue();
        boolean zBooleanValue3 = ((Boolean) obj4).booleanValue();
        boolean zBooleanValue4 = ((Boolean) obj5).booleanValue();
        DeviceBasedSatelliteViewModelImpl$showIcon$1$1 deviceBasedSatelliteViewModelImpl$showIcon$1$1 = new DeviceBasedSatelliteViewModelImpl$showIcon$1$1((Continuation) obj6);
        deviceBasedSatelliteViewModelImpl$showIcon$1$1.Z$0 = zBooleanValue;
        deviceBasedSatelliteViewModelImpl$showIcon$1$1.Z$1 = zBooleanValue2;
        deviceBasedSatelliteViewModelImpl$showIcon$1$1.L$0 = (SatelliteConnectionState) obj3;
        deviceBasedSatelliteViewModelImpl$showIcon$1$1.Z$2 = zBooleanValue3;
        deviceBasedSatelliteViewModelImpl$showIcon$1$1.Z$3 = zBooleanValue4;
        return deviceBasedSatelliteViewModelImpl$showIcon$1$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = this.Z$0;
        boolean z2 = this.Z$1;
        SatelliteConnectionState satelliteConnectionState = (SatelliteConnectionState) this.L$0;
        boolean z3 = this.Z$2;
        boolean z4 = this.Z$3;
        boolean z5 = false;
        if (!z3 && !z4 && !z2 && (z || satelliteConnectionState == SatelliteConnectionState.On || satelliteConnectionState == SatelliteConnectionState.Connected)) {
            z5 = true;
        }
        return Boolean.valueOf(z5);
    }
}
