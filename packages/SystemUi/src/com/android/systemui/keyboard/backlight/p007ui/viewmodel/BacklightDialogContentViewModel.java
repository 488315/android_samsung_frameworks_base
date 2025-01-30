package com.android.systemui.keyboard.backlight.p007ui.viewmodel;

import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class BacklightDialogContentViewModel {
    public final int currentValue;
    public final int maxValue;

    public BacklightDialogContentViewModel(int i, int i2) {
        this.currentValue = i;
        this.maxValue = i2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BacklightDialogContentViewModel)) {
            return false;
        }
        BacklightDialogContentViewModel backlightDialogContentViewModel = (BacklightDialogContentViewModel) obj;
        return this.currentValue == backlightDialogContentViewModel.currentValue && this.maxValue == backlightDialogContentViewModel.maxValue;
    }

    public final int hashCode() {
        return Integer.hashCode(this.maxValue) + (Integer.hashCode(this.currentValue) * 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("BacklightDialogContentViewModel(currentValue=");
        sb.append(this.currentValue);
        sb.append(", maxValue=");
        return ConstraintWidget$$ExternalSyntheticOutline0.m19m(sb, this.maxValue, ")");
    }
}
