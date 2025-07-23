package androidx.collection;

import androidx.appcompat.widget.AbsActionBarView$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class ScatterMapKt {
    public static final long[] EmptyGroup = {-9187201950435737345L, -1};
    public static final MutableScatterMap EmptyScatterMap = new MutableScatterMap(0);

    public static final int loadedCapacity(int i) {
        if (i == 7) {
            return 6;
        }
        return i - (i / 8);
    }

    public static final MutableScatterMap mutableScatterMapOf() {
        return new MutableScatterMap(0, 1, null);
    }

    public static final int nextCapacity(int i) {
        if (i == 0) {
            return 6;
        }
        return (i * 2) + 1;
    }

    public static final int normalizeCapacity(int i) {
        if (i > 0) {
            return (-1) >>> Integer.numberOfLeadingZeros(i);
        }
        return 0;
    }

    public static final int unloadedCapacity(int i) {
        if (i == 7) {
            return 8;
        }
        return AbsActionBarView$$ExternalSyntheticOutline0.m(i, 1, 7, i);
    }
}
