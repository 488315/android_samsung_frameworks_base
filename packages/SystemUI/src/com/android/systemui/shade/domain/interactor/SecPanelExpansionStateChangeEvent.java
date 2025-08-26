package com.android.systemui.shade.domain.interactor;

import defpackage.ReorderTile$$ExternalSyntheticOutline0;

/* loaded from: classes3.dex */
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
        return ReorderTile$$ExternalSyntheticOutline0.m(this.panelExpansionState, ")", new StringBuilder("SecPanelExpansionStateChangeEvent(panelExpansionState="));
    }
}
