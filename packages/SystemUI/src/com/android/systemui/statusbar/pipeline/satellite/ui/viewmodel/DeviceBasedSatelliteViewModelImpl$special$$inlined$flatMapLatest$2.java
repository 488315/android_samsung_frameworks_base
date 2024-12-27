package com.android.systemui.statusbar.pipeline.satellite.ui.viewmodel;

import com.android.systemui.statusbar.pipeline.airplane.data.repository.AirplaneModeRepository;
import com.android.systemui.statusbar.pipeline.airplane.data.repository.AirplaneModeRepositoryImpl;
import com.android.systemui.statusbar.pipeline.satellite.domain.interactor.DeviceBasedSatelliteInteractor;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

public final class DeviceBasedSatelliteViewModelImpl$special$$inlined$flatMapLatest$2 extends SuspendLambda implements Function3 {
    final /* synthetic */ AirplaneModeRepository $airplaneModeRepository$inlined;
    final /* synthetic */ DeviceBasedSatelliteInteractor $interactor$inlined;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ DeviceBasedSatelliteViewModelImpl this$0;

    public DeviceBasedSatelliteViewModelImpl$special$$inlined$flatMapLatest$2(Continuation continuation, DeviceBasedSatelliteViewModelImpl deviceBasedSatelliteViewModelImpl, DeviceBasedSatelliteInteractor deviceBasedSatelliteInteractor, AirplaneModeRepository airplaneModeRepository) {
        super(3, continuation);
        this.this$0 = deviceBasedSatelliteViewModelImpl;
        this.$interactor$inlined = deviceBasedSatelliteInteractor;
        this.$airplaneModeRepository$inlined = airplaneModeRepository;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        DeviceBasedSatelliteViewModelImpl$special$$inlined$flatMapLatest$2 deviceBasedSatelliteViewModelImpl$special$$inlined$flatMapLatest$2 = new DeviceBasedSatelliteViewModelImpl$special$$inlined$flatMapLatest$2((Continuation) obj3, this.this$0, this.$interactor$inlined, this.$airplaneModeRepository$inlined);
        deviceBasedSatelliteViewModelImpl$special$$inlined$flatMapLatest$2.L$0 = (FlowCollector) obj;
        deviceBasedSatelliteViewModelImpl$special$$inlined$flatMapLatest$2.L$1 = obj2;
        return deviceBasedSatelliteViewModelImpl$special$$inlined$flatMapLatest$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Flow combine;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            if (((Boolean) this.L$1).booleanValue()) {
                ReadonlyStateFlow readonlyStateFlow = this.this$0.shouldShowIconForOosAfterHysteresis;
                DeviceBasedSatelliteInteractor deviceBasedSatelliteInteractor = this.$interactor$inlined;
                combine = FlowKt.combine(readonlyStateFlow, deviceBasedSatelliteInteractor.connectionState, deviceBasedSatelliteInteractor.isWifiActive, ((AirplaneModeRepositoryImpl) this.$airplaneModeRepository$inlined).isAirplaneMode, new DeviceBasedSatelliteViewModelImpl$showIcon$1$1(null));
            } else {
                combine = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE);
            }
            this.label = 1;
            if (FlowKt.emitAll(this, combine, flowCollector) == coroutineSingletons) {
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
