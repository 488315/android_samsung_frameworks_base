package androidx.compose.ui.input.pointer.util;

import java.util.Arrays;

/* loaded from: classes.dex */
public final class PointerIdArray {
    public long[] internalArray = new long[2];
    public int size;

    public final void add(long j) {
        if (contains(j)) {
            return;
        }
        int i = this.size;
        long[] jArrCopyOf = this.internalArray;
        if (i >= jArrCopyOf.length) {
            jArrCopyOf = Arrays.copyOf(jArrCopyOf, Math.max(i + 1, jArrCopyOf.length * 2));
            this.internalArray = jArrCopyOf;
        }
        jArrCopyOf[i] = j;
        if (i >= this.size) {
            this.size = i + 1;
        }
    }

    public final boolean contains(long j) {
        int i = this.size;
        for (int i2 = 0; i2 < i; i2++) {
            if (this.internalArray[i2] == j) {
                return true;
            }
        }
        return false;
    }

    public final void remove(long j) {
        int i = this.size;
        int i2 = 0;
        while (i2 < i) {
            if (j == this.internalArray[i2]) {
                int i3 = this.size - 1;
                while (i2 < i3) {
                    long[] jArr = this.internalArray;
                    int i4 = i2 + 1;
                    jArr[i2] = jArr[i4];
                    i2 = i4;
                }
                this.size--;
                return;
            }
            i2++;
        }
    }
}
