package com.android.internal.widget.remotecompose.core.operations.utilities;

import java.util.Arrays;

/* loaded from: classes6.dex */
public class IntIntMap {
    private static final int DEFAULT_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75f;
    private static final int NOT_PRESENT = Integer.MIN_VALUE;
    private int[] mKeys;
    int mSize;
    private int[] mValues;

    private int hash(int i) {
        return i;
    }

    public IntIntMap() {
        int[] iArr = new int[16];
        this.mKeys = iArr;
        Arrays.fill(iArr, Integer.MIN_VALUE);
        this.mValues = new int[16];
    }

    public void clear() {
        Arrays.fill(this.mKeys, Integer.MIN_VALUE);
        Arrays.fill(this.mValues, 0);
        this.mSize = 0;
    }

    public boolean contains(int i) {
        return findKey(i) != -1;
    }

    public int put(int i, int i2) {
        if (i == Integer.MIN_VALUE) {
            throw new IllegalArgumentException("Key cannot be NOT_PRESENT");
        }
        if (this.mSize > this.mKeys.length * 0.75f) {
            resize();
        }
        return insert(i, i2);
    }

    public int get(int i) {
        int iFindKey = findKey(i);
        if (iFindKey == -1) {
            return 0;
        }
        return this.mValues[iFindKey];
    }

    public int size() {
        return this.mSize;
    }

    private int insert(int i, int i2) {
        int i3;
        int[] iArr;
        int i4;
        int i5;
        int iHash = hash(i);
        int length = this.mKeys.length;
        while (true) {
            i3 = iHash % length;
            iArr = this.mKeys;
            i4 = iArr[i3];
            if (i4 == Integer.MIN_VALUE || i4 == i) {
                break;
            }
            iHash = i3 + 1;
            length = iArr.length;
        }
        if (i4 == Integer.MIN_VALUE) {
            this.mSize++;
            i5 = 0;
        } else {
            i5 = this.mValues[i3];
        }
        iArr[i3] = i;
        this.mValues[i3] = i2;
        return i5;
    }

    private int findKey(int i) {
        int iHash = hash(i);
        int length = this.mKeys.length;
        while (true) {
            int i2 = iHash % length;
            int[] iArr = this.mKeys;
            int i3 = iArr[i2];
            if (i3 == Integer.MIN_VALUE) {
                return -1;
            }
            if (i3 == i) {
                return i2;
            }
            iHash = i2 + 1;
            length = iArr.length;
        }
    }

    private void resize() {
        int[] iArr = this.mKeys;
        int[] iArr2 = this.mValues;
        this.mKeys = new int[iArr.length * 2];
        int i = 0;
        while (true) {
            int[] iArr3 = this.mKeys;
            if (i >= iArr3.length) {
                break;
            }
            iArr3[i] = Integer.MIN_VALUE;
            i++;
        }
        this.mValues = new int[iArr.length * 2];
        this.mSize = 0;
        for (int i2 = 0; i2 < iArr.length; i2++) {
            int i3 = iArr[i2];
            if (i3 != Integer.MIN_VALUE) {
                put(i3, iArr2[i2]);
            }
        }
    }
}
