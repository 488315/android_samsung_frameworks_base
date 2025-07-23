package androidx.compose.runtime;

import java.util.Arrays;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        int[] iArr = this.slots;
        if (this.tos >= iArr.length) {
            iArr = Arrays.copyOf(iArr, iArr.length * 2);
            this.slots = iArr;
        }
        int i2 = this.tos;
        this.tos = i2 + 1;
        iArr[i2] = i;
    }
}
