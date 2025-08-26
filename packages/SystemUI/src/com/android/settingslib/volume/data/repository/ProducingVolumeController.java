package com.android.settingslib.volume.data.repository;

import android.media.IVolumeController;
import com.android.settingslib.volume.data.model.VolumeControllerEvent;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlySharedFlow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;

/* loaded from: classes.dex */
public final class ProducingVolumeController extends IVolumeController.Stub {
    public final ReadonlySharedFlow events;
    public final SharedFlowImpl mutableEvents;

    public ProducingVolumeController() {
        SharedFlowImpl sharedFlowImplMutableSharedFlow$default = SharedFlowKt.MutableSharedFlow$default(0, 32, null, 5);
        this.mutableEvents = sharedFlowImplMutableSharedFlow$default;
        this.events = FlowKt.asSharedFlow(sharedFlowImplMutableSharedFlow$default);
    }

    public final void dismiss() {
        this.mutableEvents.tryEmit(VolumeControllerEvent.Dismiss.INSTANCE);
    }

    public final void displayCsdWarning(int i, int i2) {
        this.mutableEvents.tryEmit(new VolumeControllerEvent.DisplayCsdWarning(i, i2));
    }

    public final void displaySafeVolumeWarning(int i) {
        this.mutableEvents.tryEmit(new VolumeControllerEvent.DisplaySafeVolumeWarning(i));
    }

    public final void displayVolumeLimiterToast() {
        this.mutableEvents.tryEmit(VolumeControllerEvent.displayVolumeLimiterToast.INSTANCE);
    }

    public final void masterMuteChanged(int i) {
        this.mutableEvents.tryEmit(new VolumeControllerEvent.MasterMuteChanged(i));
    }

    public final void setA11yMode(int i) {
        this.mutableEvents.tryEmit(new VolumeControllerEvent.SetA11yMode(i));
    }

    public final void setLayoutDirection(int i) {
        this.mutableEvents.tryEmit(new VolumeControllerEvent.SetLayoutDirection(i));
    }

    public final void volumeChanged(int i, int i2) {
        this.mutableEvents.tryEmit(new VolumeControllerEvent.VolumeChanged(i, i2));
    }
}
