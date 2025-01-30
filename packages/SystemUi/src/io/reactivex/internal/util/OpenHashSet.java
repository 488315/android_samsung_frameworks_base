package io.reactivex.internal.util;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class OpenHashSet {
    public Object[] keys;
    public final float loadFactor;
    public int mask;
    public int maxSize;
    public int size;

    public OpenHashSet() {
        this(16, 0.75f);
    }

    public final void add(Object obj) {
        Object obj2;
        Object obj3;
        Object[] objArr = this.keys;
        int i = this.mask;
        int hashCode = obj.hashCode() * (-1640531527);
        int i2 = (hashCode ^ (hashCode >>> 16)) & i;
        Object obj4 = objArr[i2];
        if (obj4 != null) {
            if (obj4.equals(obj)) {
                return;
            }
            do {
                i2 = (i2 + 1) & i;
                obj3 = objArr[i2];
                if (obj3 == null) {
                }
            } while (!obj3.equals(obj));
            return;
        }
        objArr[i2] = obj;
        int i3 = this.size + 1;
        this.size = i3;
        if (i3 < this.maxSize) {
            return;
        }
        Object[] objArr2 = this.keys;
        int length = objArr2.length;
        int i4 = length << 1;
        int i5 = i4 - 1;
        Object[] objArr3 = new Object[i4];
        while (true) {
            int i6 = i3 - 1;
            if (i3 == 0) {
                this.mask = i5;
                this.maxSize = (int) (i4 * this.loadFactor);
                this.keys = objArr3;
                return;
            }
            do {
                length--;
                obj2 = objArr2[length];
            } while (obj2 == null);
            int hashCode2 = obj2.hashCode() * (-1640531527);
            int i7 = (hashCode2 ^ (hashCode2 >>> 16)) & i5;
            if (objArr3[i7] != null) {
                do {
                    i7 = (i7 + 1) & i5;
                } while (objArr3[i7] != null);
            }
            objArr3[i7] = objArr2[length];
            i3 = i6;
        }
    }

    public final void removeEntry(int i, int i2, Object[] objArr) {
        int i3;
        Object obj;
        this.size--;
        while (true) {
            int i4 = i + 1;
            while (true) {
                i3 = i4 & i2;
                obj = objArr[i3];
                if (obj == null) {
                    objArr[i] = null;
                    return;
                }
                int hashCode = obj.hashCode() * (-1640531527);
                int i5 = (hashCode ^ (hashCode >>> 16)) & i2;
                if (i > i3) {
                    if (i >= i5 && i5 > i3) {
                        break;
                    }
                    i4 = i3 + 1;
                } else if (i < i5 && i5 <= i3) {
                    i4 = i3 + 1;
                }
            }
            objArr[i] = obj;
            i = i3;
        }
    }

    public OpenHashSet(int i) {
        this(i, 0.75f);
    }

    public OpenHashSet(int i, float f) {
        this.loadFactor = f;
        int numberOfLeadingZeros = 1 << (32 - Integer.numberOfLeadingZeros(i - 1));
        this.mask = numberOfLeadingZeros - 1;
        this.maxSize = (int) (f * numberOfLeadingZeros);
        this.keys = new Object[numberOfLeadingZeros];
    }
}
