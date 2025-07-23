package com.android.settingslib.volume.data.repository;

import android.media.IVolumeController;
import com.android.settingslib.volume.data.model.VolumeControllerEvent;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlySharedFlow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ProducingVolumeController extends IVolumeController.Stub {
    public final ReadonlySharedFlow events;
    public final SharedFlowImpl mutableEvents;

    public ProducingVolumeController() {
        SharedFlowImpl MutableSharedFlow$default = SharedFlowKt.MutableSharedFlow$default(0, 32, null, 5);
        this.mutableEvents = MutableSharedFlow$default;
        this.events = FlowKt.asSharedFlow(MutableSharedFlow$default);
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
