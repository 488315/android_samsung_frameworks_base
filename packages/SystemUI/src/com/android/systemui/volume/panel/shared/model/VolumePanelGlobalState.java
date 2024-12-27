package com.android.systemui.volume.panel.shared.model;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class VolumePanelGlobalState {
    public final boolean isVisible;

    public VolumePanelGlobalState(boolean z) {
        this.isVisible = z;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof VolumePanelGlobalState) && this.isVisible == ((VolumePanelGlobalState) obj).isVisible;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.isVisible);
    }

    public final String toString() {
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(new StringBuilder("VolumePanelGlobalState(isVisible="), this.isVisible, ")");
    }
}
