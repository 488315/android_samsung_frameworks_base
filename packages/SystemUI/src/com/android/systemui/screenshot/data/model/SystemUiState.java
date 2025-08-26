package com.android.systemui.screenshot.data.model;

import defpackage.MoveResult$$ExternalSyntheticOutline0;

/* loaded from: classes2.dex */
public final class SystemUiState {
    public final boolean shadeExpanded;

    public SystemUiState(boolean z) {
        this.shadeExpanded = z;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof SystemUiState) && this.shadeExpanded == ((SystemUiState) obj).shadeExpanded;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.shadeExpanded);
    }

    public final String toString() {
        return MoveResult$$ExternalSyntheticOutline0.m(new StringBuilder("SystemUiState(shadeExpanded="), this.shadeExpanded, ")");
    }
}
