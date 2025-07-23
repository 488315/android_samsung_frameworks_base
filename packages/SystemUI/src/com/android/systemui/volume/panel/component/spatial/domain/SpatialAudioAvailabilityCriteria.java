package com.android.systemui.volume.panel.component.spatial.domain;

import com.android.systemui.volume.panel.component.spatial.domain.interactor.SpatialAudioComponentInteractor;
import com.android.systemui.volume.panel.domain.ComponentAvailabilityCriteria;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
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
