package com.android.systemui.statusbar.layout;

import android.graphics.Rect;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class BoundsPair {
    public final Rect end;
    public final Rect start;

    public BoundsPair(Rect rect, Rect rect2) {
        this.start = rect;
        this.end = rect2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BoundsPair)) {
            return false;
        }
        BoundsPair boundsPair = (BoundsPair) obj;
        return Intrinsics.areEqual(this.start, boundsPair.start) && Intrinsics.areEqual(this.end, boundsPair.end);
    }

    public final int hashCode() {
        return this.end.hashCode() + (this.start.hashCode() * 31);
    }

    public final String toString() {
        return "BoundsPair(start=" + this.start + ", end=" + this.end + ")";
    }
}
