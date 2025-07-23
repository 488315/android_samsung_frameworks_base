package androidx.collection;

import androidx.collection.internal.ContainerHelpersKt;
import androidx.collection.internal.RuntimeHelpersKt;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Map;
import kotlin.collections.ArraysKt___ArraysJvmKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class SimpleArrayMap {
    public Object[] array;
    public int[] hashes;
    public int size;

    public SimpleArrayMap() {
        this(0, 1, null);
    }

    public final int __restricted$indexOfValue(Object obj) {
        int i = this.size * 2;
        Object[] objArr = this.array;
        if (obj == null) {
            for (int i2 = 1; i2 < i; i2 += 2) {
                if (objArr[i2] == null) {
                    return i2 >> 1;
                }
            }
            return -1;
        }
        for (int i3 = 1; i3 < i; i3 += 2) {
            if (obj.equals(objArr[i3])) {
                return i3 >> 1;
            }
        }
        return -1;
    }

    public final void clear() {
        if (this.size > 0) {
            this.hashes = ContainerHelpersKt.EMPTY_INTS;
            this.array = ContainerHelpersKt.EMPTY_OBJECTS;
            this.size = 0;
        }
        if (this.size > 0) {
            throw new ConcurrentModificationException();
        }
    }

    public boolean containsKey(Object obj) {
        return indexOfKey(obj) >= 0;
    }

    public boolean containsValue(Object obj) {
        return __restricted$indexOfValue(obj) >= 0;
    }

    public final void ensureCapacity(int i) {
        int i2 = this.size;
        int[] iArr = this.hashes;
        if (iArr.length < i) {
            this.hashes = Arrays.copyOf(iArr, i);
            this.array = Arrays.copyOf(this.array, i * 2);
        }
        if (this.size != i2) {
            throw new ConcurrentModificationException();
        }
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        try {
            if (obj instanceof SimpleArrayMap) {
                int i = this.size;
                if (i != ((SimpleArrayMap) obj).size) {
                    return false;
                }
                SimpleArrayMap simpleArrayMap = (SimpleArrayMap) obj;
                for (int i2 = 0; i2 < i; i2++) {
                    Object keyAt = keyAt(i2);
                    Object valueAt = valueAt(i2);
                    Object obj2 = simpleArrayMap.get(keyAt);
                    if (valueAt == null) {
                        if (obj2 != null || !simpleArrayMap.containsKey(keyAt)) {
                            return false;
                        }
                    } else if (!valueAt.equals(obj2)) {
                        return false;
                    }
                }
                return true;
            }
            if (!(obj instanceof Map) || this.size != ((Map) obj).size()) {
                return false;
            }
            int i3 = this.size;
            for (int i4 = 0; i4 < i3; i4++) {
                Object keyAt2 = keyAt(i4);
                Object valueAt2 = valueAt(i4);
                Object obj3 = ((Map) obj).get(keyAt2);
                if (valueAt2 == null) {
                    if (obj3 != null || !((Map) obj).containsKey(keyAt2)) {
                        return false;
                    }
                } else if (!valueAt2.equals(obj3)) {
                    return false;
                }
            }
            return true;
        } catch (ClassCastException | NullPointerException unused) {
        }
        return false;
    }

    public Object get(Object obj) {
        int indexOfKey = indexOfKey(obj);
        if (indexOfKey >= 0) {
            return this.array[(indexOfKey << 1) + 1];
        }
        return null;
    }

    public final Object getOrDefault(Object obj, Object obj2) {
        int indexOfKey = indexOfKey(obj);
        return indexOfKey >= 0 ? this.array[(indexOfKey << 1) + 1] : obj2;
    }

    public final int hashCode() {
        int[] iArr = this.hashes;
        Object[] objArr = this.array;
        int i = this.size;
        int i2 = 1;
        int i3 = 0;
        int i4 = 0;
        while (i3 < i) {
            Object obj = objArr[i2];
            i4 += (obj != null ? obj.hashCode() : 0) ^ iArr[i3];
            i3++;
            i2 += 2;
        }
        return i4;
    }

    public final int indexOf(int i, Object obj) {
        int i2 = this.size;
        if (i2 == 0) {
            return -1;
        }
        int binarySearch = ContainerHelpersKt.binarySearch(i2, i, this.hashes);
        if (binarySearch < 0 || Intrinsics.areEqual(obj, this.array[binarySearch << 1])) {
            return binarySearch;
        }
        int i3 = binarySearch + 1;
        while (i3 < i2 && this.hashes[i3] == i) {
            if (Intrinsics.areEqual(obj, this.array[i3 << 1])) {
                return i3;
            }
            i3++;
        }
        for (int i4 = binarySearch - 1; i4 >= 0 && this.hashes[i4] == i; i4--) {
            if (Intrinsics.areEqual(obj, this.array[i4 << 1])) {
                return i4;
            }
        }
        return ~i3;
    }

    public final int indexOfKey(Object obj) {
        return obj == null ? indexOfNull() : indexOf(obj.hashCode(), obj);
    }

    public final int indexOfNull() {
        int i = this.size;
        if (i == 0) {
            return -1;
        }
        int binarySearch = ContainerHelpersKt.binarySearch(i, 0, this.hashes);
        if (binarySearch < 0 || this.array[binarySearch << 1] == null) {
            return binarySearch;
        }
        int i2 = binarySearch + 1;
        while (i2 < i && this.hashes[i2] == 0) {
            if (this.array[i2 << 1] == null) {
                return i2;
            }
            i2++;
        }
        for (int i3 = binarySearch - 1; i3 >= 0 && this.hashes[i3] == 0; i3--) {
            if (this.array[i3 << 1] == null) {
                return i3;
            }
        }
        return ~i2;
    }

    public final boolean isEmpty() {
        return this.size <= 0;
    }

    public final Object keyAt(int i) {
        boolean z = false;
        if (i >= 0 && i < this.size) {
            z = true;
        }
        if (z) {
            return this.array[i << 1];
        }
        RuntimeHelpersKt.throwIllegalArgumentException("Expected index to be within 0..size()-1, but was " + i);
        throw null;
    }

    public final Object put(Object obj, Object obj2) {
        int i = this.size;
        int hashCode = obj != null ? obj.hashCode() : 0;
        int indexOf = obj != null ? indexOf(hashCode, obj) : indexOfNull();
        if (indexOf >= 0) {
            int i2 = (indexOf << 1) + 1;
            Object[] objArr = this.array;
            Object obj3 = objArr[i2];
            objArr[i2] = obj2;
            return obj3;
        }
        int i3 = ~indexOf;
        int[] iArr = this.hashes;
        if (i >= iArr.length) {
            int i4 = 8;
            if (i >= 8) {
                i4 = (i >> 1) + i;
            } else if (i < 4) {
                i4 = 4;
            }
            this.hashes = Arrays.copyOf(iArr, i4);
            this.array = Arrays.copyOf(this.array, i4 << 1);
            if (i != this.size) {
                throw new ConcurrentModificationException();
            }
        }
        if (i3 < i) {
            int[] iArr2 = this.hashes;
            int i5 = i3 + 1;
            ArraysKt___ArraysJvmKt.copyInto(i5, i3, i, iArr2, iArr2);
            Object[] objArr2 = this.array;
            ArraysKt___ArraysJvmKt.copyInto(objArr2, objArr2, i5 << 1, i3 << 1, this.size << 1);
        }
        int i6 = this.size;
        if (i == i6) {
            int[] iArr3 = this.hashes;
            if (i3 < iArr3.length) {
                iArr3[i3] = hashCode;
                Object[] objArr3 = this.array;
                int i7 = i3 << 1;
                objArr3[i7] = obj;
                objArr3[i7 + 1] = obj2;
                this.size = i6 + 1;
                return null;
            }
        }
        throw new ConcurrentModificationException();
    }

    public final Object putIfAbsent(Object obj, Object obj2) {
        Object obj3 = get(obj);
        return obj3 == null ? put(obj, obj2) : obj3;
    }

    public Object remove(Object obj) {
        int indexOfKey = indexOfKey(obj);
        if (indexOfKey >= 0) {
            return removeAt(indexOfKey);
        }
        return null;
    }

    public final Object removeAt(int i) {
        if (!(i >= 0 && i < this.size)) {
            RuntimeHelpersKt.throwIllegalArgumentException("Expected index to be within 0..size()-1, but was " + i);
            throw null;
        }
        Object[] objArr = this.array;
        int i2 = i << 1;
        Object obj = objArr[i2 + 1];
        int i3 = this.size;
        if (i3 <= 1) {
            clear();
            return obj;
        }
        int i4 = i3 - 1;
        int[] iArr = this.hashes;
        if (iArr.length <= 8 || i3 >= iArr.length / 3) {
            if (i < i4) {
                int i5 = i + 1;
                ArraysKt___ArraysJvmKt.copyInto(i, i5, i3, iArr, iArr);
                Object[] objArr2 = this.array;
                ArraysKt___ArraysJvmKt.copyInto(objArr2, objArr2, i2, i5 << 1, i3 << 1);
            }
            Object[] objArr3 = this.array;
            int i6 = i4 << 1;
            objArr3[i6] = null;
            objArr3[i6 + 1] = null;
        } else {
            int i7 = i3 > 8 ? i3 + (i3 >> 1) : 8;
            this.hashes = Arrays.copyOf(iArr, i7);
            this.array = Arrays.copyOf(this.array, i7 << 1);
            if (i3 != this.size) {
                throw new ConcurrentModificationException();
            }
            if (i > 0) {
                ArraysKt___ArraysJvmKt.copyInto(0, 0, i, iArr, this.hashes);
                ArraysKt___ArraysJvmKt.copyInto(objArr, this.array, 0, 0, i2);
            }
            if (i < i4) {
                int i8 = i + 1;
                ArraysKt___ArraysJvmKt.copyInto(i, i8, i3, iArr, this.hashes);
                ArraysKt___ArraysJvmKt.copyInto(objArr, this.array, i2, i8 << 1, i3 << 1);
            }
        }
        if (i3 != this.size) {
            throw new ConcurrentModificationException();
        }
        this.size = i4;
        return obj;
    }

    public final Object replace(Object obj, Object obj2) {
        int indexOfKey = indexOfKey(obj);
        if (indexOfKey >= 0) {
            return setValueAt(indexOfKey, obj2);
        }
        return null;
    }

    public final Object setValueAt(int i, Object obj) {
        boolean z = false;
        if (i >= 0 && i < this.size) {
            z = true;
        }
        if (!z) {
            RuntimeHelpersKt.throwIllegalArgumentException("Expected index to be within 0..size()-1, but was " + i);
            throw null;
        }
        int i2 = (i << 1) + 1;
        Object[] objArr = this.array;
        Object obj2 = objArr[i2];
        objArr[i2] = obj;
        return obj2;
    }

    public final int size() {
        return this.size;
    }

    public final String toString() {
        if (isEmpty()) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder(this.size * 28);
        sb.append('{');
        int i = this.size;
        for (int i2 = 0; i2 < i; i2++) {
            if (i2 > 0) {
                sb.append(", ");
            }
            Object keyAt = keyAt(i2);
            if (keyAt != sb) {
                sb.append(keyAt);
            } else {
                sb.append("(this Map)");
            }
            sb.append('=');
            Object valueAt = valueAt(i2);
            if (valueAt != sb) {
                sb.append(valueAt);
            } else {
                sb.append("(this Map)");
            }
        }
        sb.append('}');
        return sb.toString();
    }

    public final Object valueAt(int i) {
        boolean z = false;
        if (i >= 0 && i < this.size) {
            z = true;
        }
        if (z) {
            return this.array[(i << 1) + 1];
        }
        RuntimeHelpersKt.throwIllegalArgumentException("Expected index to be within 0..size()-1, but was " + i);
        throw null;
    }

    public SimpleArrayMap(int i) {
        this.hashes = i == 0 ? ContainerHelpersKt.EMPTY_INTS : new int[i];
        this.array = i == 0 ? ContainerHelpersKt.EMPTY_OBJECTS : new Object[i << 1];
    }

    public final boolean remove(Object obj, Object obj2) {
        int indexOfKey = indexOfKey(obj);
        if (indexOfKey < 0 || !Intrinsics.areEqual(obj2, valueAt(indexOfKey))) {
            return false;
        }
        removeAt(indexOfKey);
        return true;
    }

    public final boolean replace(Object obj, Object obj2, Object obj3) {
        int indexOfKey = indexOfKey(obj);
        if (indexOfKey < 0 || !Intrinsics.areEqual(obj2, valueAt(indexOfKey))) {
            return false;
        }
        setValueAt(indexOfKey, obj3);
        return true;
    }

    public /* synthetic */ SimpleArrayMap(int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 0 : i);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public SimpleArrayMap(androidx.collection.SimpleArrayMap r5) {
        /*
            r4 = this;
            r0 = 0
            r1 = 1
            r2 = 0
            r4.<init>(r0, r1, r2)
            if (r5 == 0) goto L39
            int r1 = r5.size
            int r2 = r4.size
            int r2 = r2 + r1
            r4.ensureCapacity(r2)
            int r2 = r4.size
            if (r2 != 0) goto L29
            if (r1 <= 0) goto L39
            int[] r2 = r5.hashes
            int[] r3 = r4.hashes
            kotlin.collections.ArraysKt___ArraysJvmKt.copyInto(r0, r0, r1, r2, r3)
            java.lang.Object[] r5 = r5.array
            java.lang.Object[] r2 = r4.array
            int r3 = r1 << 1
            kotlin.collections.ArraysKt___ArraysJvmKt.copyInto(r5, r2, r0, r0, r3)
            r4.size = r1
            return
        L29:
            if (r0 >= r1) goto L39
            java.lang.Object r2 = r5.keyAt(r0)
            java.lang.Object r3 = r5.valueAt(r0)
            r4.put(r2, r3)
            int r0 = r0 + 1
            goto L29
        L39:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.collection.SimpleArrayMap.<init>(androidx.collection.SimpleArrayMap):void");
    }
}
