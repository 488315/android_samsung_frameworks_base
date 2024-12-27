package com.android.systemui.volume.panel.component.spatial.domain;

import com.android.systemui.volume.panel.component.spatial.domain.interactor.SpatialAudioComponentInteractor;
import com.android.systemui.volume.panel.domain.ComponentAvailabilityCriteria;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SpatialAudioAvailabilityCriteria implements ComponentAvailabilityCriteria {
    public final SpatialAudioComponentInteractor interactor;

    public SpatialAudioAvailabilityCriteria(SpatialAudioComponentInteractor spatialAudioComponentInteractor) {
        this.interactor = spatialAudioComponentInteractor;
    }

    @Override // com.android.systemui.volume.panel.domain.ComponentAvailabilityCriteria
    public final Flow isAvailable() {
        SpatialAudioComponentInteractor spatialAudioComponentInteractor = this.interactor;
        return new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(spatialAudioComponentInteractor.isAvailable, spatialAudioComponentInteractor.isEnabled, new SpatialAudioAvailabilityCriteria$isAvailable$1(null));
    }
}
