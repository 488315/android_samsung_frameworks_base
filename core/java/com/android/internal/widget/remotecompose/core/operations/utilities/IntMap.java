package com.android.internal.widget.remotecompose.core.operations.utilities;

import java.util.ArrayList;
import java.util.Arrays;

/* loaded from: classes5.dex */
public class IntMap<T> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75f;
    private static final int NOT_PRESENT = Integer.MIN_VALUE;
    private int[] mKeys = new int[16];
    int mSize;
    private ArrayList<T> mValues;

    public IntMap() {
        Arrays.fill(this.mKeys, Integer.MIN_VALUE);
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

    public T put(int key, T value) {
        if (key == Integer.MIN_VALUE) {
            throw new IllegalArgumentException("Key cannot be NOT_PRESENT");
        }
        if (this.mSize > this.mKeys.length * 0.75f) {
            resize();
        }
        return insert(key, value);
    }

    public T get(int key) {
        int index = findKey(key);
        if (index == -1) {
            return null;
        }
        return this.mValues.get(index);
    }

    public int size() {
        return this.mSize;
    }

    private T insert(int key, T value) {
        int index = hash(key) % this.mKeys.length;
        while (this.mKeys[index] != Integer.MIN_VALUE && this.mKeys[index] != key) {
            index = (index + 1) % this.mKeys.length;
        }
        T oldValue = null;
        if (this.mKeys[index] == Integer.MIN_VALUE) {
            this.mSize++;
        } else {
            oldValue = this.mValues.get(index);
        }
        this.mKeys[index] = key;
        this.mValues.set(index, value);
        return oldValue;
    }

    private int findKey(int key) {
        int index = hash(key) % this.mKeys.length;
        while (this.mKeys[index] != Integer.MIN_VALUE) {
            if (this.mKeys[index] == key) {
                return index;
            }
            index = (index + 1) % this.mKeys.length;
        }
        return -1;
    }

    private int hash(int key) {
        return key;
    }

    private void resize() {
        int[] oldKeys = this.mKeys;
        ArrayList<T> oldValues = this.mValues;
        this.mKeys = new int[oldKeys.length * 2];
        for (int i = 0; i < this.mKeys.length; i++) {
            this.mKeys[i] = Integer.MIN_VALUE;
        }
        this.mValues = new ArrayList<>(oldKeys.length * 2);
        for (int i2 = 0; i2 < oldKeys.length * 2; i2++) {
            this.mValues.add(null);
        }
        this.mSize = 0;
        for (int i3 = 0; i3 < oldKeys.length; i3++) {
            if (oldKeys[i3] != Integer.MIN_VALUE) {
                put(oldKeys[i3], oldValues.get(i3));
            }
        }
    }
}
