package com.android.systemui.shade.domain.interactor;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
