package com.android.systemui.volume.dialog.sliders.domain.model;

import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public final class VolumeDialogSlidersModel {
    public final List floatingSliders;
    public final VolumeDialogSliderType slider;

    public VolumeDialogSlidersModel(VolumeDialogSliderType volumeDialogSliderType, List<? extends VolumeDialogSliderType> list) {
        this.slider = volumeDialogSliderType;
        this.floatingSliders = list;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof VolumeDialogSlidersModel)) {
            return false;
        }
        VolumeDialogSlidersModel volumeDialogSlidersModel = (VolumeDialogSlidersModel) obj;
        return Intrinsics.areEqual(this.slider, volumeDialogSlidersModel.slider) && Intrinsics.areEqual(this.floatingSliders, volumeDialogSlidersModel.floatingSliders);
    }

    public final int hashCode() {
        return this.floatingSliders.hashCode() + (this.slider.hashCode() * 31);
    }

    public final String toString() {
        return "VolumeDialogSlidersModel(slider=" + this.slider + ", floatingSliders=" + this.floatingSliders + ")";
    }
}
