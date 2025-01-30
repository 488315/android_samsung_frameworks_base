package org.apache.commons.lang3.builder;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class HashCodeBuilder {
    public static final ThreadLocal REGISTRY = new ThreadLocal();
    public final int iConstant;
    public int iTotal;

    public HashCodeBuilder() {
        this.iConstant = 37;
        this.iTotal = 17;
    }

    /* JADX WARN: Finally extract failed */
    public static void reflectionAppend(Object obj, Class cls, HashCodeBuilder hashCodeBuilder, String[] strArr) {
        int i;
        ThreadLocal threadLocal = REGISTRY;
        Set set = (Set) threadLocal.get();
        if (set != null && set.contains(new IDKey(obj))) {
            return;
        }
        try {
            Set set2 = (Set) threadLocal.get();
            if (set2 == null) {
                set2 = new HashSet();
                threadLocal.set(set2);
            }
            set2.add(new IDKey(obj));
            Field[] declaredFields = cls.getDeclaredFields();
            Arrays.sort(declaredFields, Comparator.comparing(new HashCodeBuilder$$ExternalSyntheticLambda0()));
            AccessibleObject.setAccessible(declaredFields, true);
            for (Field field : declaredFields) {
                String name = field.getName();
                if (name == null) {
                    i = 0;
                    while (i < strArr.length) {
                        if (strArr[i] == null) {
                            break;
                        } else {
                            i++;
                        }
                    }
                    i = -1;
                } else {
                    for (int i2 = 0; i2 < strArr.length; i2++) {
                        if (name.equals(strArr[i2])) {
                            i = i2;
                            break;
                        }
                    }
                    i = -1;
                }
                if (!(i != -1) && !field.getName().contains("$") && !Modifier.isTransient(field.getModifiers()) && !Modifier.isStatic(field.getModifiers()) && !field.isAnnotationPresent(HashCodeExclude.class)) {
                    try {
                        hashCodeBuilder.append(field.get(obj));
                    } catch (IllegalAccessException unused) {
                        throw new InternalError("Unexpected IllegalAccessException");
                    }
                }
            }
            Set set3 = (Set) threadLocal.get();
            if (set3 != null) {
                set3.remove(new IDKey(obj));
                if (set3.isEmpty()) {
                    threadLocal.remove();
                }
            }
        } catch (Throwable th) {
            Set set4 = (Set) threadLocal.get();
            if (set4 != null) {
                set4.remove(new IDKey(obj));
                if (set4.isEmpty()) {
                    threadLocal.remove();
                }
            }
            throw th;
        }
    }

    public final void append(Object obj) {
        int i = this.iConstant;
        if (obj == null) {
            this.iTotal *= i;
            return;
        }
        if (!obj.getClass().isArray()) {
            this.iTotal = obj.hashCode() + (this.iTotal * i);
            return;
        }
        int i2 = 0;
        if (obj instanceof long[]) {
            long[] jArr = (long[]) obj;
            int length = jArr.length;
            while (i2 < length) {
                long j = jArr[i2];
                this.iTotal = (this.iTotal * i) + ((int) (j ^ (j >> 32)));
                i2++;
            }
            return;
        }
        if (obj instanceof int[]) {
            int[] iArr = (int[]) obj;
            int length2 = iArr.length;
            while (i2 < length2) {
                this.iTotal = (this.iTotal * i) + iArr[i2];
                i2++;
            }
            return;
        }
        if (obj instanceof short[]) {
            short[] sArr = (short[]) obj;
            int length3 = sArr.length;
            while (i2 < length3) {
                this.iTotal = (this.iTotal * i) + sArr[i2];
                i2++;
            }
            return;
        }
        if (obj instanceof char[]) {
            char[] cArr = (char[]) obj;
            int length4 = cArr.length;
            while (i2 < length4) {
                this.iTotal = (this.iTotal * i) + cArr[i2];
                i2++;
            }
            return;
        }
        if (obj instanceof byte[]) {
            byte[] bArr = (byte[]) obj;
            int length5 = bArr.length;
            while (i2 < length5) {
                this.iTotal = (this.iTotal * i) + bArr[i2];
                i2++;
            }
            return;
        }
        if (obj instanceof double[]) {
            double[] dArr = (double[]) obj;
            int length6 = dArr.length;
            while (i2 < length6) {
                long doubleToLongBits = Double.doubleToLongBits(dArr[i2]);
                this.iTotal = (this.iTotal * i) + ((int) (doubleToLongBits ^ (doubleToLongBits >> 32)));
                i2++;
            }
            return;
        }
        if (obj instanceof float[]) {
            float[] fArr = (float[]) obj;
            int length7 = fArr.length;
            while (i2 < length7) {
                this.iTotal = Float.floatToIntBits(fArr[i2]) + (this.iTotal * i);
                i2++;
            }
            return;
        }
        if (!(obj instanceof boolean[])) {
            Object[] objArr = (Object[]) obj;
            int length8 = objArr.length;
            while (i2 < length8) {
                append(objArr[i2]);
                i2++;
            }
            return;
        }
        boolean[] zArr = (boolean[]) obj;
        int length9 = zArr.length;
        while (i2 < length9) {
            this.iTotal = (this.iTotal * i) + (!zArr[i2] ? 1 : 0);
            i2++;
        }
    }

    public final int hashCode() {
        return this.iTotal;
    }

    public HashCodeBuilder(int i, int i2) {
        this.iTotal = 0;
        Object[] objArr = new Object[0];
        if (i % 2 != 0) {
            Object[] objArr2 = new Object[0];
            if (i2 % 2 != 0) {
                this.iConstant = i2;
                this.iTotal = i;
                return;
            }
            throw new IllegalArgumentException(String.format("HashCodeBuilder requires an odd multiplier", objArr2));
        }
        throw new IllegalArgumentException(String.format("HashCodeBuilder requires an odd initial value", objArr));
    }
}
