package android.util;

import android.hardware.scontext.SContextConstants;

/* loaded from: classes4.dex */
public class SparseDoubleArray implements Cloneable {
    private SparseLongArray mValues;

    public SparseDoubleArray() {
        this(0);
    }

    public SparseDoubleArray(int initialCapacity) {
        this.mValues = new SparseLongArray(initialCapacity);
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public SparseDoubleArray m5236clone() {
        SparseDoubleArray clone = null;
        try {
            clone = (SparseDoubleArray) super.clone();
            clone.mValues = this.mValues.m5238clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            return clone;
        }
    }

    public double get(int key) {
        return get(key, SContextConstants.ENVIRONMENT_VALUE_UNKNOWN);
    }

    public double get(int key, double valueIfKeyNotFound) {
        int index = this.mValues.indexOfKey(key);
        if (index < 0) {
            return valueIfKeyNotFound;
        }
        return valueAt(index);
    }

    public void put(int key, double value) {
        this.mValues.put(key, Double.doubleToRawLongBits(value));
    }

    public void incrementValue(int key, double summand) {
        double oldValue = get(key);
        put(key, oldValue + summand);
    }

    public int size() {
        return this.mValues.size();
    }

    public int indexOfKey(int key) {
        return this.mValues.indexOfKey(key);
    }

    public int keyAt(int index) {
        return this.mValues.keyAt(index);
    }

    public double valueAt(int index) {
        return Double.longBitsToDouble(this.mValues.valueAt(index));
    }

    public void setValueAt(int index, double value) {
        this.mValues.setValueAt(index, Double.doubleToRawLongBits(value));
    }

    public void removeAt(int index) {
        this.mValues.removeAt(index);
    }

    public void delete(int key) {
        this.mValues.delete(key);
    }

    public void clear() {
        this.mValues.clear();
    }

    public String toString() {
        if (size() <= 0) {
            return "{}";
        }
        StringBuilder buffer = new StringBuilder(size() * 34);
        buffer.append('{');
        for (int i = 0; i < size(); i++) {
            if (i > 0) {
                buffer.append(", ");
            }
            int key = keyAt(i);
            buffer.append(key);
            buffer.append('=');
            double value = valueAt(i);
            buffer.append(value);
        }
        buffer.append('}');
        return buffer.toString();
    }
}
