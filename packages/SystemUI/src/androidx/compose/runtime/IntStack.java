package androidx.compose.runtime;

import java.util.Arrays;

/* loaded from: classes.dex */
public final class IntStack {
    public int[] slots = new int[10];
    public int tos;

    public final int peekOr(int i) {
        int i2 = this.tos - 1;
        return i2 >= 0 ? this.slots[i2] : i;
    }

    public final int pop() {
        int[] iArr = this.slots;
        int i = this.tos - 1;
        this.tos = i;
        return iArr[i];
    }

    public final void push(int i) {
        int[] iArrCopyOf = this.slots;
        if (this.tos >= iArrCopyOf.length) {
            iArrCopyOf = Arrays.copyOf(iArrCopyOf, iArrCopyOf.length * 2);
            this.slots = iArrCopyOf;
        }
        int i2 = this.tos;
        this.tos = i2 + 1;
        iArrCopyOf[i2] = i;
    }
}
