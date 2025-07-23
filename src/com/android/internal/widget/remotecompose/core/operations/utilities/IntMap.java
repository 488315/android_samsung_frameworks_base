package com.android.internal.widget.remotecompose.core.operations.utilities;

import java.util.ArrayList;
import java.util.Arrays;

/* loaded from: classes6.dex */
public class IntMap<T> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75f;
    private static final int NOT_PRESENT = Integer.MIN_VALUE;
    private int[] mKeys;
    int mSize;
    private ArrayList<T> mValues;

    private int hash(int i) {
        return i;
    }

    public IntMap() {
        int[] iArr = new int[16];
        this.mKeys = iArr;
        Arrays.fill(iArr, Integer.MIN_VALUE);
        this.mValues = new ArrayList<>(16);
        for (int i = 0; i < 16; i++) {
            this.mValues.add(null);
        }
    }

    public void clear() {
        Arrays.fill(this.mKeys, Integer.MIN_VALUE);
        this.mValues.clear();
        this.mSize = 0;
    }

    public T put(int i, T t) {
        if (i == Integer.MIN_VALUE) {
            throw new IllegalArgumentException("Key cannot be NOT_PRESENT");
        }
        if (this.mSize > this.mKeys.length * 0.75f) {
            resize();
        }
        return insert(i, t);
    }

    public T get(int i) {
        int findKey = findKey(i);
        if (findKey == -1) {
            return null;
        }
        return this.mValues.get(findKey);
    }

    public int size() {
        return this.mSize;
    }

    private T insert(int i, T t) {
        int i2;
        int i3;
        T t2;
        int hash = hash(i);
        int length = this.mKeys.length;
        while (true) {
            i2 = hash % length;
            int[] iArr = this.mKeys;
            i3 = iArr[i2];
            if (i3 == Integer.MIN_VALUE || i3 == i) {
                break;
            }
            hash = i2 + 1;
            length = iArr.length;
        }
        if (i3 == Integer.MIN_VALUE) {
            this.mSize++;
            t2 = null;
        } else {
            t2 = this.mValues.get(i2);
        }
        this.mKeys[i2] = i;
        this.mValues.set(i2, t);
        return t2;
    }

    private int findKey(int i) {
        int hash = hash(i);
        int length = this.mKeys.length;
        while (true) {
            int i2 = hash % length;
            int[] iArr = this.mKeys;
            int i3 = iArr[i2];
            if (i3 == Integer.MIN_VALUE) {
                return -1;
            }
            if (i3 == i) {
                return i2;
            }
            hash = i2 + 1;
            length = iArr.length;
        }
    }

    private void resize() {
        int[] iArr = this.mKeys;
        ArrayList<T> arrayList = this.mValues;
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
        this.mValues = new ArrayList<>(iArr.length * 2);
        for (int i2 = 0; i2 < iArr.length * 2; i2++) {
            this.mValues.add(null);
        }
        this.mSize = 0;
        for (int i3 = 0; i3 < iArr.length; i3++) {
            int i4 = iArr[i3];
            if (i4 != Integer.MIN_VALUE) {
                put(i4, arrayList.get(i3));
            }
        }
    }

    public T remove(int i) {
        int hash = hash(i) % this.mKeys.length;
        int i2 = hash;
        do {
            int[] iArr = this.mKeys;
            int i3 = iArr[i2];
            if (i3 == Integer.MIN_VALUE) {
                break;
            }
            if (i3 == i) {
                T t = this.mValues.get(i2);
                this.mKeys[i2] = Integer.MIN_VALUE;
                this.mValues.set(i2, null);
                this.mSize--;
                rehashFrom((i2 + 1) % this.mKeys.length);
                return t;
            }
            i2 = (i2 + 1) % iArr.length;
        } while (i2 != hash);
        return null;
    }

    private void rehashFrom(int i) {
        while (true) {
            int i2 = this.mKeys[i];
            if (i2 == Integer.MIN_VALUE) {
                return;
            }
            T t = this.mValues.get(i);
            this.mKeys[i] = Integer.MIN_VALUE;
            this.mValues.set(i, null);
            this.mSize--;
            insert(i2, t);
            i = (i + 1) % this.mKeys.length;
        }
    }
}
