package androidx.compose.runtime;

import defpackage.ReorderTile$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class Anchor {
    public int location;

    public Anchor(int i) {
        this.location = i;
    }

    public final boolean getValid() {
        return this.location != Integer.MIN_VALUE;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("{ location = ");
        return ReorderTile$$ExternalSyntheticOutline0.m(this.location, " }", sb);
    }
}
