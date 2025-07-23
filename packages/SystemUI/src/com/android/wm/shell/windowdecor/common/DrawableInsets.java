package com.android.wm.shell.windowdecor.common;

import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DrawableInsets {
    public final int b;
    public final int l;
    public final int r;
    public final int t;

    public DrawableInsets(int i, int i2, int i3, int i4) {
        this.l = i;
        this.t = i2;
        this.r = i3;
        this.b = i4;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DrawableInsets)) {
            return false;
        }
        DrawableInsets drawableInsets = (DrawableInsets) obj;
        return this.l == drawableInsets.l && this.t == drawableInsets.t && this.r == drawableInsets.r && this.b == drawableInsets.b;
    }

    public final int hashCode() {
        return Integer.hashCode(this.b) + ReorderTile$$ExternalSyntheticOutline0.m(this.r, ReorderTile$$ExternalSyntheticOutline0.m(this.t, Integer.hashCode(this.l) * 31, 31), 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("DrawableInsets(l=");
        sb.append(this.l);
        sb.append(", t=");
        sb.append(this.t);
        sb.append(", r=");
        sb.append(this.r);
        sb.append(", b=");
        return ReorderTile$$ExternalSyntheticOutline0.m(this.b, ")", sb);
    }

    public /* synthetic */ DrawableInsets(int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this((i3 & 1) != 0 ? 0 : i, (i3 & 2) != 0 ? 0 : i2);
    }

    public DrawableInsets(int i, int i2) {
        this(i2, i, i2, i);
    }

    public /* synthetic */ DrawableInsets(int i, int i2, int i3, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this((i4 & 1) != 0 ? 0 : i, (i4 & 2) != 0 ? 0 : i2, (i4 & 4) != 0 ? 0 : i3);
    }

    public DrawableInsets(int i, int i2, int i3) {
        this(i2, i, i3, i);
    }
}
