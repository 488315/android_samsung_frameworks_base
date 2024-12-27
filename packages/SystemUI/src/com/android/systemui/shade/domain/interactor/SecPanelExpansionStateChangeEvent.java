package com.android.systemui.shade.domain.interactor;

import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
