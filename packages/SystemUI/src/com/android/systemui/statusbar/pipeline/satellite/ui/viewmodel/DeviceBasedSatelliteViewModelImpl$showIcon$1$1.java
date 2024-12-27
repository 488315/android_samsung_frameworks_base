package com.android.systemui.statusbar.pipeline.satellite.ui.viewmodel;

import com.android.systemui.statusbar.pipeline.satellite.shared.model.SatelliteConnectionState;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function5;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
final class DeviceBasedSatelliteViewModelImpl$showIcon$1$1 extends SuspendLambda implements Function5 {
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    /* synthetic */ boolean Z$2;
    int label;

    public DeviceBasedSatelliteViewModelImpl$showIcon$1$1(Continuation continuation) {
        super(5, continuation);
    }

    @Override // kotlin.jvm.functions.Function5
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        boolean booleanValue2 = ((Boolean) obj3).booleanValue();
        boolean booleanValue3 = ((Boolean) obj4).booleanValue();
        DeviceBasedSatelliteViewModelImpl$showIcon$1$1 deviceBasedSatelliteViewModelImpl$showIcon$1$1 = new DeviceBasedSatelliteViewModelImpl$showIcon$1$1((Continuation) obj5);
        deviceBasedSatelliteViewModelImpl$showIcon$1$1.Z$0 = booleanValue;
        deviceBasedSatelliteViewModelImpl$showIcon$1$1.L$0 = (SatelliteConnectionState) obj2;
        deviceBasedSatelliteViewModelImpl$showIcon$1$1.Z$1 = booleanValue2;
        deviceBasedSatelliteViewModelImpl$showIcon$1$1.Z$2 = booleanValue3;
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
        SatelliteConnectionState satelliteConnectionState = (SatelliteConnectionState) this.L$0;
        boolean z2 = this.Z$1;
        boolean z3 = this.Z$2;
        boolean z4 = false;
        if (!z2 && !z3 && (z || satelliteConnectionState == SatelliteConnectionState.On || satelliteConnectionState == SatelliteConnectionState.Connected)) {
            z4 = true;
        }
        return Boolean.valueOf(z4);
    }
}
