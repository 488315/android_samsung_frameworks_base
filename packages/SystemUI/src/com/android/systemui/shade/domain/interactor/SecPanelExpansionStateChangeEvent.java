package com.android.systemui.shade.domain.interactor;

import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;

public final class SecPanelExpansionStateChangeEvent {
    public final int panelExpansionState;

    public SecPanelExpansionStateChangeEvent(int i) {
        this.panelExpansionState = i;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof SecPanelExpansionStateChangeEvent) && this.panelExpansionState == ((SecPanelExpansionStateChangeEvent) obj).panelExpansionState;
    }

    public final int hashCode() {
        return Integer.hashCode(this.panelExpansionState);
    }

    public final String toString() {
        return Anchor$$ExternalSyntheticOutline0.m(this.panelExpansionState, ")", new StringBuilder("SecPanelExpansionStateChangeEvent(panelExpansionState="));
    }
}
