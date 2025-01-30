package com.android.systemui.statusbar.pipeline.satellite.p027ui.viewmodel;

import com.android.systemui.statusbar.pipeline.airplane.data.repository.AirplaneModeRepository;
import com.android.systemui.statusbar.pipeline.airplane.data.repository.AirplaneModeRepositoryImpl;
import com.android.systemui.statusbar.pipeline.satellite.domain.interactor.DeviceBasedSatelliteInteractor;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.satellite.ui.viewmodel.DeviceBasedSatelliteViewModel$special$$inlined$flatMapLatest$1", m277f = "DeviceBasedSatelliteViewModel.kt", m278l = {190}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
public final class DeviceBasedSatelliteViewModel$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ AirplaneModeRepository $airplaneModeRepository$inlined;
    final /* synthetic */ DeviceBasedSatelliteInteractor $interactor$inlined;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeviceBasedSatelliteViewModel$special$$inlined$flatMapLatest$1(Continuation continuation, DeviceBasedSatelliteInteractor deviceBasedSatelliteInteractor, AirplaneModeRepository airplaneModeRepository) {
        super(3, continuation);
        this.$interactor$inlined = deviceBasedSatelliteInteractor;
        this.$airplaneModeRepository$inlined = airplaneModeRepository;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        DeviceBasedSatelliteViewModel$special$$inlined$flatMapLatest$1 deviceBasedSatelliteViewModel$special$$inlined$flatMapLatest$1 = new DeviceBasedSatelliteViewModel$special$$inlined$flatMapLatest$1((Continuation) obj3, this.$interactor$inlined, this.$airplaneModeRepository$inlined);
        deviceBasedSatelliteViewModel$special$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        deviceBasedSatelliteViewModel$special$$inlined$flatMapLatest$1.L$1 = obj2;
        return deviceBasedSatelliteViewModel$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            Flow flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = !((Boolean) this.L$1).booleanValue() ? new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE) : new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(this.$interactor$inlined.isSatelliteAllowed, ((AirplaneModeRepositoryImpl) this.$airplaneModeRepository$inlined).isAirplaneMode, new DeviceBasedSatelliteViewModel$shouldShowIcon$1$1(null));
            this.label = 1;
            if (FlowKt.emitAll(this, flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2, flowCollector) == coroutineSingletons) {
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
