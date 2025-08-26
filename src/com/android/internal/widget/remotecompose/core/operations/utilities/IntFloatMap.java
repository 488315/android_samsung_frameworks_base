package com.android.internal.widget.remotecompose.core.operations.utilities;

import java.util.Arrays;

/* loaded from: classes6.dex */
public class IntFloatMap {
    private static final int DEFAULT_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75f;
    private static final int NOT_PRESENT = Integer.MIN_VALUE;
    private int[] mKeys;
    int mSize;
    private float[] mValues;

    private int hash(int i) {
        return i;
    }

    public IntFloatMap() {
        int[] iArr = new int[16];
        this.mKeys = iArr;
        Arrays.fill(iArr, Integer.MIN_VALUE);
        this.mValues = new float[16];
    }

    public void clear() {
        Arrays.fill(this.mKeys, Integer.MIN_VALUE);
        Arrays.fill(this.mValues, Float.NaN);
        this.mSize = 0;
    }

    public boolean contains(int i) {
        return findKey(i) != -1;
    }

    public float put(int i, float f) {
        if (i == Integer.MIN_VALUE) {
            throw new IllegalArgumentException("Key cannot be NOT_PRESENT");
        }
        if (this.mSize > this.mKeys.length * 0.75f) {
            resize();
        }
        return insert(i, f);
    }

    public float get(int i) {
        int iFindKey = findKey(i);
        if (iFindKey == -1) {
            return 0.0f;
        }
        return this.mValues[iFindKey];
    }

    public int size() {
        return this.mSize;
    }

    private float insert(int i, float f) {
        int i2;
        int[] iArr;
        int i3;
        float f2;
        int iHash = hash(i);
        int length = this.mKeys.length;
        while (true) {
            i2 = iHash % length;
            iArr = this.mKeys;
            i3 = iArr[i2];
            if (i3 == Integer.MIN_VALUE || i3 == i) {
                break;
            }
            iHash = i2 + 1;
            length = iArr.length;
        }
        if (i3 == Integer.MIN_VALUE) {
            this.mSize++;
            f2 = 0.0f;
        } else {
            f2 = this.mValues[i2];
        }
        iArr[i2] = i;
        this.mValues[i2] = f;
        return f2;
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
        float[] fArr = this.mValues;
        this.mKeys = new int[iArr.length * 2];
        int i = 0;
        while (true) {
            int[] iArr2 = this.mKeys;
            if (i >= iArr2.length) {
                break;
            }
            iArr2[i] = Integer.MIN_VALUE;
            i++;
        }
        this.mValues = new float[iArr.length * 2];
        this.mSize = 0;
        for (int i2 = 0; i2 < iArr.length; i2++) {
            int i3 = iArr[i2];
            if (i3 != Integer.MIN_VALUE) {
                put(i3, fArr[i2]);
            }
        }
    }
}
