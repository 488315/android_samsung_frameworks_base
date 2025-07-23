package com.google.dexmaker.dx.util;

import java.util.Arrays;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class FixedSizeList extends MutabilityControl implements ToHuman {
    public final Object[] arr;

    public FixedSizeList(int i) {
        super(i != 0);
        try {
            this.arr = new Object[i];
        } catch (NegativeArraySizeException unused) {
            throw new IllegalArgumentException("size < 0");
        }
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return Arrays.equals(this.arr, ((FixedSizeList) obj).arr);
    }

    public final int hashCode() {
        return Arrays.hashCode(this.arr);
    }

    @Override // com.google.dexmaker.dx.util.ToHuman
    public final String toHuman() {
        String name = getClass().getName();
        return toString0(name.substring(name.lastIndexOf(46) + 1) + '{', true);
    }

    public final String toString() {
        String name = getClass().getName();
        return toString0(name.substring(name.lastIndexOf(46) + 1) + '{', false);
    }

    public final String toString0(String str, boolean z) {
        Object[] objArr = this.arr;
        int length = objArr.length;
        StringBuffer stringBuffer = new StringBuffer((length * 10) + 10);
        if (str != null) {
            stringBuffer.append(str);
        }
        for (int i = 0; i < length; i++) {
            if (i != 0) {
                stringBuffer.append(", ");
            }
            if (z) {
                stringBuffer.append(((ToHuman) objArr[i]).toHuman());
            } else {
                stringBuffer.append(objArr[i]);
            }
        }
        stringBuffer.append("}");
        return stringBuffer.toString();
    }
}
