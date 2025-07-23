package com.android.systemui.volume.dialog.sliders.ui.viewmodel;

import com.android.systemui.volume.dialog.sliders.dagger.VolumeDialogSliderComponent;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class VolumeDialogSliderUiModel {
    public final List floatingSliderComponent;
    public final VolumeDialogSliderComponent sliderComponent;

    public VolumeDialogSliderUiModel(VolumeDialogSliderComponent volumeDialogSliderComponent, List<? extends VolumeDialogSliderComponent> list) {
        this.sliderComponent = volumeDialogSliderComponent;
        this.floatingSliderComponent = list;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof VolumeDialogSliderUiModel)) {
            return false;
        }
        VolumeDialogSliderUiModel volumeDialogSliderUiModel = (VolumeDialogSliderUiModel) obj;
        return Intrinsics.areEqual(this.sliderComponent, volumeDialogSliderUiModel.sliderComponent) && Intrinsics.areEqual(this.floatingSliderComponent, volumeDialogSliderUiModel.floatingSliderComponent);
    }

    public final int hashCode() {
        return this.floatingSliderComponent.hashCode() + (this.sliderComponent.hashCode() * 31);
    }

    public final String toString() {
        return "VolumeDialogSliderUiModel(sliderComponent=" + this.sliderComponent + ", floatingSliderComponent=" + this.floatingSliderComponent + ")";
    }
}
