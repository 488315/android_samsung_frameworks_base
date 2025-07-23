package com.android.systemui.volume.panel.shared.model;

import defpackage.MoveResult$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
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
        return MoveResult$$ExternalSyntheticOutline0.m(new StringBuilder("VolumePanelGlobalState(isVisible="), this.isVisible, ")");
    }
}
