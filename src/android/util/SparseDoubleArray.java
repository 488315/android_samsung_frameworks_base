package android.util;

import android.hardware.scontext.SContextConstants;

/* loaded from: classes4.dex */
public class SparseDoubleArray implements Cloneable {
    private SparseLongArray mValues;

    public SparseDoubleArray() {
        this(0);
    }

    public SparseDoubleArray(int i) {
        this.mValues = new SparseLongArray(i);
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public SparseDoubleArray m5538clone() {
        try {
            SparseDoubleArray sparseDoubleArray = (SparseDoubleArray) super.clone();
            try {
                sparseDoubleArray.mValues = this.mValues.m5540clone();
                return sparseDoubleArray;
            } catch (CloneNotSupportedException unused) {
                return sparseDoubleArray;
            }
        } catch (CloneNotSupportedException unused2) {
            return null;
        }
    }

    public double get(int i) {
        return get(i, SContextConstants.ENVIRONMENT_VALUE_UNKNOWN);
    }

    public double get(int i, double d) {
        int iIndexOfKey = this.mValues.indexOfKey(i);
        return iIndexOfKey < 0 ? d : valueAt(iIndexOfKey);
    }

    public void put(int i, double d) {
        this.mValues.put(i, Double.doubleToRawLongBits(d));
    }

    public void incrementValue(int i, double d) {
        put(i, get(i) + d);
    }

    public int size() {
        return this.mValues.size();
    }

    public int indexOfKey(int i) {
        return this.mValues.indexOfKey(i);
    }

    public int keyAt(int i) {
        return this.mValues.keyAt(i);
    }

    public double valueAt(int i) {
        return Double.longBitsToDouble(this.mValues.valueAt(i));
    }

    public void setValueAt(int i, double d) {
        this.mValues.setValueAt(i, Double.doubleToRawLongBits(d));
    }

    public void removeAt(int i) {
        this.mValues.removeAt(i);
    }

    public void delete(int i) {
        this.mValues.delete(i);
    }

    public void clear() {
        this.mValues.clear();
    }

    public String toString() {
        if (size() <= 0) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder(size() * 34);
        sb.append('{');
        for (int i = 0; i < size(); i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(keyAt(i));
            sb.append('=');
            sb.append(valueAt(i));
        }
        sb.append('}');
        return sb.toString();
    }
}
