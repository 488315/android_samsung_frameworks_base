package com.android.systemui.shade.domain.interactor;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;

public final class SecQSExpansionStateChangeEvent {
    public final boolean expanded;

    public SecQSExpansionStateChangeEvent(boolean z) {
        this.expanded = z;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof SecQSExpansionStateChangeEvent) && this.expanded == ((SecQSExpansionStateChangeEvent) obj).expanded;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.expanded);
    }

    public final String toString() {
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(new StringBuilder("SecQSExpansionStateChangeEvent(expanded="), this.expanded, ")");
    }
}
