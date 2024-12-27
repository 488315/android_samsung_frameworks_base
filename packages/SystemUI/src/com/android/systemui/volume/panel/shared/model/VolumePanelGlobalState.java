package com.android.systemui.volume.panel.shared.model;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;

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
