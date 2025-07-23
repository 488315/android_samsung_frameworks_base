package com.android.systemui.volume.dialog.sliders.ui.viewmodel;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import com.android.systemui.common.shared.model.Icon;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.ClosedFloatingPointRange;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class VolumeDialogSliderStateModel {
    public final Icon.Loaded icon;
    public final boolean isDisabled;
    public final String label;
    public final float value;
    public final ClosedFloatingPointRange valueRange;

    public VolumeDialogSliderStateModel(float f, boolean z, ClosedFloatingPointRange closedFloatingPointRange, Icon.Loaded loaded, String str) {
        this.value = f;
        this.isDisabled = z;
        this.valueRange = closedFloatingPointRange;
        this.icon = loaded;
        this.label = str;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof VolumeDialogSliderStateModel)) {
            return false;
        }
        VolumeDialogSliderStateModel volumeDialogSliderStateModel = (VolumeDialogSliderStateModel) obj;
        return Float.compare(this.value, volumeDialogSliderStateModel.value) == 0 && this.isDisabled == volumeDialogSliderStateModel.isDisabled && Intrinsics.areEqual(this.valueRange, volumeDialogSliderStateModel.valueRange) && Intrinsics.areEqual(this.icon, volumeDialogSliderStateModel.icon) && Intrinsics.areEqual(this.label, volumeDialogSliderStateModel.label);
    }

    public final int hashCode() {
        return this.label.hashCode() + ((this.icon.hashCode() + ((this.valueRange.hashCode() + TransitionData$$ExternalSyntheticOutline0.m(Float.hashCode(this.value) * 31, 31, this.isDisabled)) * 31)) * 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("VolumeDialogSliderStateModel(value=");
        sb.append(this.value);
        sb.append(", isDisabled=");
        sb.append(this.isDisabled);
        sb.append(", valueRange=");
        sb.append(this.valueRange);
        sb.append(", icon=");
        sb.append(this.icon);
        sb.append(", label=");
        return TransitionKt$$ExternalSyntheticOutline0.m(sb, this.label, ")");
    }
}
