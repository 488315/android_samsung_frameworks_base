package com.android.systemui.statusbar.pipeline.satellite.data;

import android.os.Bundle;
import com.android.systemui.demomode.DemoMode;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

final class DeviceBasedSatelliteRepositorySwitcher$isDemoMode$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ DeviceBasedSatelliteRepositorySwitcher this$0;

    public DeviceBasedSatelliteRepositorySwitcher$isDemoMode$1(DeviceBasedSatelliteRepositorySwitcher deviceBasedSatelliteRepositorySwitcher, Continuation continuation) {
        super(2, continuation);
        this.this$0 = deviceBasedSatelliteRepositorySwitcher;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DeviceBasedSatelliteRepositorySwitcher$isDemoMode$1 deviceBasedSatelliteRepositorySwitcher$isDemoMode$1 = new DeviceBasedSatelliteRepositorySwitcher$isDemoMode$1(this.this$0, continuation);
        deviceBasedSatelliteRepositorySwitcher$isDemoMode$1.L$0 = obj;
        return deviceBasedSatelliteRepositorySwitcher$isDemoMode$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DeviceBasedSatelliteRepositorySwitcher$isDemoMode$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final DeviceBasedSatelliteRepositorySwitcher deviceBasedSatelliteRepositorySwitcher = this.this$0;
            final ?? r1 = new DemoMode() { // from class: com.android.systemui.statusbar.pipeline.satellite.data.DeviceBasedSatelliteRepositorySwitcher$isDemoMode$1$callback$1
                @Override // com.android.systemui.demomode.DemoModeCommandReceiver
                public final void onDemoModeFinished() {
                    Job job = DeviceBasedSatelliteRepositorySwitcher.this.demoImpl.demoCommandJob;
                    if (job != null) {
                        job.cancel(null);
                    }
                    ((ChannelCoroutine) producerScope).mo2552trySendJP2dKIU(Boolean.FALSE);
                }

                @Override // com.android.systemui.demomode.DemoModeCommandReceiver
                public final void dispatchDemoCommand(Bundle bundle, String str) {
                }
            };
            this.this$0.demoModeController.addCallback((DemoMode) r1);
            final DeviceBasedSatelliteRepositorySwitcher deviceBasedSatelliteRepositorySwitcher2 = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.statusbar.pipeline.satellite.data.DeviceBasedSatelliteRepositorySwitcher$isDemoMode$1.1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    DeviceBasedSatelliteRepositorySwitcher.this.demoModeController.removeCallback((DemoMode) r1);
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, function0, this) == coroutineSingletons) {
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
