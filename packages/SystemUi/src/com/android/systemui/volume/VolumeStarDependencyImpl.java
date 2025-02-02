package com.android.systemui.volume;

import com.android.systemui.volume.middleware.AudioManagerController;
import com.android.systemui.volume.middleware.BixbyServiceInteractor;
import com.android.systemui.volume.middleware.BluetoothInteractor;
import com.android.systemui.volume.middleware.DeviceStateController;
import com.android.systemui.volume.middleware.JSonLogger;
import com.android.systemui.volume.middleware.SmartViewInteractor;
import com.android.systemui.volume.reducer.VolumePanelReducer;
import com.samsung.systemui.splugins.volume.ExtendableVolumePanel;
import com.samsung.systemui.splugins.volume.VolumeInfraMediator;
import com.samsung.systemui.splugins.volume.VolumePanelReducerBase;
import com.samsung.systemui.splugins.volume.VolumeStarDependency;
import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class VolumeStarDependencyImpl implements VolumeStarDependency {
    public final VolumeDependencyBase volDeps;
    public final ExtendableVolumePanel volumePanel;

    public VolumeStarDependencyImpl(VolumeDependencyBase volumeDependencyBase, ExtendableVolumePanel extendableVolumePanel) {
        this.volDeps = volumeDependencyBase;
        this.volumePanel = extendableVolumePanel;
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeStarDependency
    public final List getDefaultMiddlewares() {
        VolumeDependencyBase volumeDependencyBase = this.volDeps;
        return CollectionsKt__CollectionsKt.mutableListOf(new JSonLogger(volumeDependencyBase), new DeviceStateController(volumeDependencyBase), new AudioManagerController(volumeDependencyBase), new SmartViewInteractor(volumeDependencyBase), new BluetoothInteractor(volumeDependencyBase), new BixbyServiceInteractor(volumeDependencyBase));
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeStarDependency
    public final VolumePanelReducerBase getDefaultReducer() {
        return new VolumePanelReducer();
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeStarDependency
    public final VolumeInfraMediator getInfraMediator() {
        return new VolumeInfraMediatorImpl(this.volDeps);
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeStarDependency
    public final ExtendableVolumePanel getVolumePanel() {
        return this.volumePanel;
    }
}
