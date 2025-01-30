package com.samsung.systemui.splugins.volume;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface ExtendableVolumePanel {
    VolumePanelState getVolumePanelCurrentState();

    void recreateVolumePanelForNewConfig();

    void restoreToDefaultStore();

    void setActionObserver(VolumeObserver<VolumePanelAction> volumeObserver);

    void setStateObservable(VolumeObservable<VolumePanelState> volumeObservable);
}
