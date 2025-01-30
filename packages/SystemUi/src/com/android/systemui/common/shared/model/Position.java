package com.android.systemui.common.shared.model;

import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class Position {

    /* renamed from: x */
    public final int f239x;

    /* renamed from: y */
    public final int f240y;

    public Position(int i, int i2) {
        this.f239x = i;
        this.f240y = i2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Position)) {
            return false;
        }
        Position position = (Position) obj;
        return this.f239x == position.f239x && this.f240y == position.f240y;
    }

    public final int hashCode() {
        return Integer.hashCode(this.f240y) + (Integer.hashCode(this.f239x) * 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("Position(x=");
        sb.append(this.f239x);
        sb.append(", y=");
        return ConstraintWidget$$ExternalSyntheticOutline0.m19m(sb, this.f240y, ")");
    }
}
