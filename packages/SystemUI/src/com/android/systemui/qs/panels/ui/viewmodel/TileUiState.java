package com.android.systemui.qs.panels.ui.viewmodel;

import android.graphics.drawable.Drawable;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class TileUiState {
    public final AccessibilityUiState accessibilityUiState;
    public final boolean handlesLongClick;
    public final boolean handlesSecondaryClick;
    public final String label;
    public final String secondaryLabel;
    public final Drawable sideDrawable;
    public final int state;

    public TileUiState(String str, String str2, int i, boolean z, boolean z2, Drawable drawable, AccessibilityUiState accessibilityUiState) {
        this.label = str;
        this.secondaryLabel = str2;
        this.state = i;
        this.handlesLongClick = z;
        this.handlesSecondaryClick = z2;
        this.sideDrawable = drawable;
        this.accessibilityUiState = accessibilityUiState;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TileUiState)) {
            return false;
        }
        TileUiState tileUiState = (TileUiState) obj;
        return Intrinsics.areEqual(this.label, tileUiState.label) && Intrinsics.areEqual(this.secondaryLabel, tileUiState.secondaryLabel) && this.state == tileUiState.state && this.handlesLongClick == tileUiState.handlesLongClick && this.handlesSecondaryClick == tileUiState.handlesSecondaryClick && Intrinsics.areEqual(this.sideDrawable, tileUiState.sideDrawable) && Intrinsics.areEqual(this.accessibilityUiState, tileUiState.accessibilityUiState);
    }

    public final int hashCode() {
        int m = TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(ReorderTile$$ExternalSyntheticOutline0.m(this.state, PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.label.hashCode() * 31, 31, this.secondaryLabel), 31), 31, this.handlesLongClick), 31, this.handlesSecondaryClick);
        Drawable drawable = this.sideDrawable;
        return this.accessibilityUiState.hashCode() + ((m + (drawable == null ? 0 : drawable.hashCode())) * 31);
    }

    public final String toString() {
        return "TileUiState(label=" + this.label + ", secondaryLabel=" + this.secondaryLabel + ", state=" + this.state + ", handlesLongClick=" + this.handlesLongClick + ", handlesSecondaryClick=" + this.handlesSecondaryClick + ", sideDrawable=" + this.sideDrawable + ", accessibilityUiState=" + this.accessibilityUiState + ")";
    }
}
