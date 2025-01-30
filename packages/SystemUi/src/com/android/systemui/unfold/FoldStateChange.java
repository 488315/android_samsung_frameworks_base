package com.android.systemui.unfold;

import androidx.picker.model.viewdata.AppInfoViewData$$ExternalSyntheticOutline0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class FoldStateChange {
    public final int current;
    public final long dtMillis;
    public final int previous;

    public FoldStateChange(int i, int i2, long j) {
        this.previous = i;
        this.current = i2;
        this.dtMillis = j;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FoldStateChange)) {
            return false;
        }
        FoldStateChange foldStateChange = (FoldStateChange) obj;
        return this.previous == foldStateChange.previous && this.current == foldStateChange.current && this.dtMillis == foldStateChange.dtMillis;
    }

    public final int hashCode() {
        return Long.hashCode(this.dtMillis) + AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.current, Integer.hashCode(this.previous) * 31, 31);
    }

    public final String toString() {
        return "FoldStateChange(previous=" + this.previous + ", current=" + this.current + ", dtMillis=" + this.dtMillis + ")";
    }
}
