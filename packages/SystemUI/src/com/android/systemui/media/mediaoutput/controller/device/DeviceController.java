package com.android.systemui.media.mediaoutput.controller.device;

import com.android.systemui.media.mediaoutput.entity.AudioDevice;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.JobImpl;
import kotlinx.coroutines.JobKt;
import kotlinx.coroutines.MainCoroutineDispatcher;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.internal.MainDispatcherLoader;
import kotlinx.coroutines.scheduling.DefaultScheduler;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public abstract class DeviceController {
    public final SharedFlowImpl audioDevicesFlow;
    public final Lazy controllerJob$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.media.mediaoutput.controller.device.DeviceController$controllerJob$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return JobKt.Job$default();
        }
    });
    public final Lazy controllerScope$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.media.mediaoutput.controller.device.DeviceController$controllerScope$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            DefaultScheduler defaultScheduler = Dispatchers.Default;
            MainCoroutineDispatcher mainCoroutineDispatcher = MainDispatcherLoader.dispatcher;
            JobImpl jobImpl = (JobImpl) DeviceController.this.controllerJob$delegate.getValue();
            mainCoroutineDispatcher.getClass();
            return CoroutineScopeKt.CoroutineScope(CoroutineContext.DefaultImpls.plus(mainCoroutineDispatcher, jobImpl));
        }
    });
    public final SharedFlowImpl devicesFlow;

    public DeviceController() {
        SharedFlowImpl MutableSharedFlow$default = SharedFlowKt.MutableSharedFlow$default(1, 0, BufferOverflow.DROP_OLDEST, 2);
        BuildersKt.launch$default(getControllerScope(), null, null, new DeviceController$devicesFlow$1$1(MutableSharedFlow$default, null), 3);
        this.devicesFlow = MutableSharedFlow$default;
        this.audioDevicesFlow = MutableSharedFlow$default;
    }

    public void close() {
        ((JobImpl) this.controllerJob$delegate.getValue()).cancel(null);
    }

    public final CoroutineScope getControllerScope() {
        return (CoroutineScope) this.controllerScope$delegate.getValue();
    }

    public void cancel(AudioDevice audioDevice) {
    }

    public void deselect(AudioDevice audioDevice) {
    }

    public void select(AudioDevice audioDevice) {
    }

    public void transfer(AudioDevice audioDevice) {
    }

    public void adjustVolume(AudioDevice audioDevice, int i) {
    }
}
