package com.samsung.systemui.splugins.volume;

/* loaded from: classes4.dex */
public interface ExtendableVolumePanel {
    VolumePanelState getVolumePanelCurrentState();

    void recreateVolumePanelForNewConfig();

    void restoreToDefaultStore();

    void setActionObserver(VolumeObserver<VolumePanelAction> volumeObserver);

    void setStateObservable(VolumeObservable<VolumePanelState> volumeObservable);
}
