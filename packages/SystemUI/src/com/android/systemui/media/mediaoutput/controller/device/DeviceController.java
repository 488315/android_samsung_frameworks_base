package com.android.systemui.media.mediaoutput.controller.device;

import com.android.systemui.media.mediaoutput.entity.AudioDevice;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.JobImpl;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.scheduling.DefaultScheduler;

/* loaded from: classes2.dex */
public abstract class DeviceController {
    public final SharedFlowImpl audioDevicesFlow;
    public final Lazy controllerJob$delegate = LazyKt__LazyJVMKt.lazy(new DeviceController$$ExternalSyntheticLambda0());
    public final Lazy controllerScope$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.media.mediaoutput.controller.device.DeviceController$$ExternalSyntheticLambda1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            DefaultScheduler defaultScheduler = Dispatchers.Default;
            JobImpl jobImpl = (JobImpl) this.f$0.controllerJob$delegate.getValue();
            defaultScheduler.getClass();
            return CoroutineScopeKt.CoroutineScope(CoroutineContext.DefaultImpls.plus(defaultScheduler, jobImpl));
        }
    });
    public final SharedFlowImpl devicesFlow;

    public DeviceController() {
        SharedFlowImpl sharedFlowImplMutableSharedFlow$default = SharedFlowKt.MutableSharedFlow$default(1, 0, BufferOverflow.DROP_OLDEST, 2);
        BuildersKt.launch$default(getControllerScope(), null, null, new DeviceController$devicesFlow$1$1(sharedFlowImplMutableSharedFlow$default, null), 3);
        this.devicesFlow = sharedFlowImplMutableSharedFlow$default;
        this.audioDevicesFlow = sharedFlowImplMutableSharedFlow$default;
    }

    public Unit adjustVolume(AudioDevice audioDevice, int i) {
        return Unit.INSTANCE;
    }

    public Unit cancel(AudioDevice audioDevice) {
        return Unit.INSTANCE;
    }

    public void close() {
        ((JobImpl) this.controllerJob$delegate.getValue()).cancel(null);
    }

    public Object deselect(AudioDevice audioDevice, ContinuationImpl continuationImpl) {
        return Unit.INSTANCE;
    }

    public final CoroutineScope getControllerScope() {
        return (CoroutineScope) this.controllerScope$delegate.getValue();
    }

    public Unit select(AudioDevice audioDevice, ContinuationImpl continuationImpl) {
        return Unit.INSTANCE;
    }

    public Object transfer(AudioDevice audioDevice, Continuation continuation) {
        return Unit.INSTANCE;
    }
}
