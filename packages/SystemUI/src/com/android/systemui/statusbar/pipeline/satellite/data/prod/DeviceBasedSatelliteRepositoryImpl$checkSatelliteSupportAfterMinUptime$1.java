package com.android.systemui.statusbar.pipeline.satellite.data.prod;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

final class DeviceBasedSatelliteRepositoryImpl$checkSatelliteSupportAfterMinUptime$1 extends ContinuationImpl {
    Object L$0;
    Object L$1;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ DeviceBasedSatelliteRepositoryImpl this$0;

    public DeviceBasedSatelliteRepositoryImpl$checkSatelliteSupportAfterMinUptime$1(DeviceBasedSatelliteRepositoryImpl deviceBasedSatelliteRepositoryImpl, Continuation continuation) {
        super(continuation);
        this.this$0 = deviceBasedSatelliteRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return DeviceBasedSatelliteRepositoryImpl.access$checkSatelliteSupportAfterMinUptime(this.this$0, null, this);
    }
}
