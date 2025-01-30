package com.android.systemui.statusbar.phone;

import android.graphics.Rect;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
