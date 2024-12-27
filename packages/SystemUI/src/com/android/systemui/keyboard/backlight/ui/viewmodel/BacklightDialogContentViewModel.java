package com.android.systemui.keyboard.backlight.ui.viewmodel;

import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
        return Anchor$$ExternalSyntheticOutline0.m(this.maxValue, ")", sb);
    }
}
