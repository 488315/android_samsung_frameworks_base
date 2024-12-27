package com.android.systemui.volume.panel.component.spatial.ui.viewmodel;

import com.android.systemui.volume.panel.component.button.ui.viewmodel.ButtonViewModel;
import com.android.systemui.volume.panel.component.spatial.domain.model.SpatialAudioEnabledModel;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SpatialAudioButtonViewModel {
    public final ButtonViewModel button;
    public final SpatialAudioEnabledModel model;

    public SpatialAudioButtonViewModel(SpatialAudioEnabledModel spatialAudioEnabledModel, ButtonViewModel buttonViewModel) {
        this.model = spatialAudioEnabledModel;
        this.button = buttonViewModel;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SpatialAudioButtonViewModel)) {
            return false;
        }
        SpatialAudioButtonViewModel spatialAudioButtonViewModel = (SpatialAudioButtonViewModel) obj;
        return Intrinsics.areEqual(this.model, spatialAudioButtonViewModel.model) && Intrinsics.areEqual(this.button, spatialAudioButtonViewModel.button);
    }

    public final int hashCode() {
        return this.button.hashCode() + (this.model.hashCode() * 31);
    }

    public final String toString() {
        return "SpatialAudioButtonViewModel(model=" + this.model + ", button=" + this.button + ")";
    }
}
