package androidx.compose.runtime;

import defpackage.ReorderTile$$ExternalSyntheticOutline0;

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
