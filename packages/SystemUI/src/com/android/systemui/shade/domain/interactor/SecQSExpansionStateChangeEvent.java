package com.android.systemui.shade.domain.interactor;

import defpackage.MoveResult$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
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
        return MoveResult$$ExternalSyntheticOutline0.m(new StringBuilder("SecQSExpansionStateChangeEvent(expanded="), this.expanded, ")");
    }
}
