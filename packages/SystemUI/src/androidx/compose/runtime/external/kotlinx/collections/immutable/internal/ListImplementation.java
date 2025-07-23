package androidx.compose.runtime.external.kotlinx.collections.immutable.internal;

import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ListImplementation {
    static {
        new ListImplementation();
    }

    private ListImplementation() {
    }

    public static final void checkElementIndex$runtime_release(int i, int i2) {
        if (i < 0 || i >= i2) {
            throw new IndexOutOfBoundsException(ListImplementation$$ExternalSyntheticOutline0.m(i, i2, "index: ", ", size: "));
        }
    }

    public static final void checkPositionIndex$runtime_release(int i, int i2) {
        if (i < 0 || i > i2) {
            throw new IndexOutOfBoundsException(ListImplementation$$ExternalSyntheticOutline0.m(i, i2, "index: ", ", size: "));
        }
    }

    public static final void checkRangeIndexes$runtime_release(int i, int i2, int i3) {
        if (i < 0 || i2 > i3) {
            StringBuilder m = MutableObjectList$$ExternalSyntheticOutline0.m(i, i2, "fromIndex: ", ", toIndex: ", ", size: ");
            m.append(i3);
            throw new IndexOutOfBoundsException(m.toString());
        }
        if (i > i2) {
            throw new IllegalArgumentException(ListImplementation$$ExternalSyntheticOutline0.m(i, i2, "fromIndex: ", " > toIndex: "));
        }
    }
}
