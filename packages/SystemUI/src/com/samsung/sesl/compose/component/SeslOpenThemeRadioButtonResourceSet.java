package com.samsung.sesl.compose.component;

import androidx.compose.ui.graphics.painter.Painter;
import com.android.systemui.media.mediaoutput.entity.AudioMirroringDevice$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes4.dex */
public final class SeslOpenThemeRadioButtonResourceSet {
    public final Painter radioButtonDisabledOff;
    public final Painter radioButtonDisabledOn;
    public final Painter radioButtonSelected;
    public final Painter radioButtonUnselected;

    public SeslOpenThemeRadioButtonResourceSet(Painter painter, Painter painter2, Painter painter3, Painter painter4) {
        this.radioButtonSelected = painter;
        this.radioButtonUnselected = painter2;
        this.radioButtonDisabledOn = painter3;
        this.radioButtonDisabledOff = painter4;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SeslOpenThemeRadioButtonResourceSet)) {
            return false;
        }
        SeslOpenThemeRadioButtonResourceSet seslOpenThemeRadioButtonResourceSet = (SeslOpenThemeRadioButtonResourceSet) obj;
        return Intrinsics.areEqual(this.radioButtonSelected, seslOpenThemeRadioButtonResourceSet.radioButtonSelected) && Intrinsics.areEqual(this.radioButtonUnselected, seslOpenThemeRadioButtonResourceSet.radioButtonUnselected) && Intrinsics.areEqual(this.radioButtonDisabledOn, seslOpenThemeRadioButtonResourceSet.radioButtonDisabledOn) && Intrinsics.areEqual(this.radioButtonDisabledOff, seslOpenThemeRadioButtonResourceSet.radioButtonDisabledOff);
    }

    public final int hashCode() {
        return this.radioButtonDisabledOff.hashCode() + AudioMirroringDevice$$ExternalSyntheticOutline0.m(this.radioButtonDisabledOn, AudioMirroringDevice$$ExternalSyntheticOutline0.m(this.radioButtonUnselected, this.radioButtonSelected.hashCode() * 31, 31), 31);
    }

    public final String toString() {
        return "SeslOpenThemeRadioButtonResourceSet(radioButtonSelected=" + this.radioButtonSelected + ", radioButtonUnselected=" + this.radioButtonUnselected + ", radioButtonDisabledOn=" + this.radioButtonDisabledOn + ", radioButtonDisabledOff=" + this.radioButtonDisabledOff + ")";
    }
}
