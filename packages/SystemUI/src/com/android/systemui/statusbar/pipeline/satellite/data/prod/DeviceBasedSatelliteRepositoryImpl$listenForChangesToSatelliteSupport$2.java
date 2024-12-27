package com.android.systemui.statusbar.pipeline.satellite.data.prod;

import android.telephony.satellite.SatelliteManager;
import com.android.systemui.statusbar.pipeline.satellite.data.prod.SatelliteSupport;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.StateFlowImpl;

final class DeviceBasedSatelliteRepositoryImpl$listenForChangesToSatelliteSupport$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ SatelliteManager $sm;
    int label;
    final /* synthetic */ DeviceBasedSatelliteRepositoryImpl this$0;

    public DeviceBasedSatelliteRepositoryImpl$listenForChangesToSatelliteSupport$2(DeviceBasedSatelliteRepositoryImpl deviceBasedSatelliteRepositoryImpl, SatelliteManager satelliteManager, Continuation continuation) {
        super(2, continuation);
        this.this$0 = deviceBasedSatelliteRepositoryImpl;
        this.$sm = satelliteManager;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new DeviceBasedSatelliteRepositoryImpl$listenForChangesToSatelliteSupport$2(this.this$0, this.$sm, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DeviceBasedSatelliteRepositoryImpl$listenForChangesToSatelliteSupport$2) create((Unit) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final DeviceBasedSatelliteRepositoryImpl deviceBasedSatelliteRepositoryImpl = this.this$0;
            Flow flow = deviceBasedSatelliteRepositoryImpl.satelliteIsSupportedCallback;
            final SatelliteManager satelliteManager = this.$sm;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl$listenForChangesToSatelliteSupport$2.1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    boolean booleanValue = ((Boolean) obj2).booleanValue();
                    DeviceBasedSatelliteRepositoryImpl deviceBasedSatelliteRepositoryImpl2 = DeviceBasedSatelliteRepositoryImpl.this;
                    if (booleanValue) {
                        ((StateFlowImpl) deviceBasedSatelliteRepositoryImpl2.getSatelliteSupport()).updateState(null, new SatelliteSupport.Supported(satelliteManager));
                    } else {
                        ((StateFlowImpl) deviceBasedSatelliteRepositoryImpl2.getSatelliteSupport()).setValue(SatelliteSupport.NotSupported.INSTANCE);
                    }
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (flow.collect(flowCollector, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
