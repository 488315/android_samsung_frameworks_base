package com.samsung.sesl.compose.component;

import androidx.compose.ui.graphics.painter.Painter;
import com.android.systemui.media.mediaoutput.entity.AudioMirroringDevice$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes4.dex */
public final class SeslOpenCheckboxResourceSet {
    public final Painter checkboxDisabledOff;
    public final Painter checkboxDisabledOn;
    public final Painter checkboxSelected;
    public final Painter checkboxUnselected;

    public SeslOpenCheckboxResourceSet(Painter painter, Painter painter2, Painter painter3, Painter painter4) {
        this.checkboxSelected = painter;
        this.checkboxUnselected = painter2;
        this.checkboxDisabledOn = painter3;
        this.checkboxDisabledOff = painter4;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SeslOpenCheckboxResourceSet)) {
            return false;
        }
        SeslOpenCheckboxResourceSet seslOpenCheckboxResourceSet = (SeslOpenCheckboxResourceSet) obj;
        return Intrinsics.areEqual(this.checkboxSelected, seslOpenCheckboxResourceSet.checkboxSelected) && Intrinsics.areEqual(this.checkboxUnselected, seslOpenCheckboxResourceSet.checkboxUnselected) && Intrinsics.areEqual(this.checkboxDisabledOn, seslOpenCheckboxResourceSet.checkboxDisabledOn) && Intrinsics.areEqual(this.checkboxDisabledOff, seslOpenCheckboxResourceSet.checkboxDisabledOff);
    }

    public final int hashCode() {
        return this.checkboxDisabledOff.hashCode() + AudioMirroringDevice$$ExternalSyntheticOutline0.m(this.checkboxDisabledOn, AudioMirroringDevice$$ExternalSyntheticOutline0.m(this.checkboxUnselected, this.checkboxSelected.hashCode() * 31, 31), 31);
    }

    public final String toString() {
        return "SeslOpenCheckboxResourceSet(checkboxSelected=" + this.checkboxSelected + ", checkboxUnselected=" + this.checkboxUnselected + ", checkboxDisabledOn=" + this.checkboxDisabledOn + ", checkboxDisabledOff=" + this.checkboxDisabledOff + ")";
    }
}
