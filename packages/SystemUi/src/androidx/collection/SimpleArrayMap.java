package androidx.collection;

import android.support.v4.media.AbstractC0000x2c234b15;
import androidx.collection.internal.ContainerHelpersKt;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Map;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class SimpleArrayMap {
    public Object[] array;
    public int[] hashes;
    public int size;

    public SimpleArrayMap() {
        this(0, 1, null);
    }

    private final int indexOf(int i, Object obj) {
        int i2 = this.size;
        if (i2 == 0) {
            return -1;
        }
        int binarySearch = ContainerHelpersKt.binarySearch(i2, i, this.hashes);
        if (binarySearch < 0) {
            return binarySearch;
        }
        if (Intrinsics.areEqual(obj, this.array[binarySearch << 1])) {
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

    private final int indexOfNull() {
        int i = this.size;
        if (i == 0) {
            return -1;
        }
        int binarySearch = ContainerHelpersKt.binarySearch(i, 0, this.hashes);
        if (binarySearch < 0) {
            return binarySearch;
        }
        if (this.array[binarySearch << 1] == null) {
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
            if (Intrinsics.areEqual(obj, objArr[i3])) {
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
                    } else if (!Intrinsics.areEqual(valueAt, obj2)) {
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
                } else if (!Intrinsics.areEqual(valueAt2, obj3)) {
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

    public final int indexOfKey(Object obj) {
        return obj == null ? indexOfNull() : indexOf(obj.hashCode(), obj);
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
        throw new IllegalArgumentException(AbstractC0000x2c234b15.m0m("Expected index to be within 0..size()-1, but was ", i).toString());
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
            System.arraycopy(iArr2, i3, iArr2, i5, i - i3);
            Object[] objArr2 = this.array;
            int i6 = i3 << 1;
            System.arraycopy(objArr2, i6, objArr2, i5 << 1, (this.size << 1) - i6);
        }
        int i7 = this.size;
        if (i == i7) {
            int[] iArr3 = this.hashes;
            if (i3 < iArr3.length) {
                iArr3[i3] = hashCode;
                Object[] objArr3 = this.array;
                int i8 = i3 << 1;
                objArr3[i8] = obj;
                objArr3[i8 + 1] = obj2;
                this.size = i7 + 1;
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
            throw new IllegalArgumentException(AbstractC0000x2c234b15.m0m("Expected index to be within 0..size()-1, but was ", i).toString());
        }
        Object[] objArr = this.array;
        int i2 = i << 1;
        Object obj = objArr[i2 + 1];
        int i3 = this.size;
        if (i3 <= 1) {
            clear();
        } else {
            int i4 = i3 - 1;
            int[] iArr = this.hashes;
            if (iArr.length <= 8 || i3 >= iArr.length / 3) {
                if (i < i4) {
                    int i5 = i + 1;
                    int i6 = i4 + 1;
                    System.arraycopy(iArr, i5, iArr, i, i6 - i5);
                    Object[] objArr2 = this.array;
                    int i7 = i5 << 1;
                    System.arraycopy(objArr2, i7, objArr2, i2, (i6 << 1) - i7);
                }
                Object[] objArr3 = this.array;
                int i8 = i4 << 1;
                objArr3[i8] = null;
                objArr3[i8 + 1] = null;
            } else {
                int i9 = i3 > 8 ? i3 + (i3 >> 1) : 8;
                this.hashes = Arrays.copyOf(iArr, i9);
                this.array = Arrays.copyOf(this.array, i9 << 1);
                if (i3 != this.size) {
                    throw new ConcurrentModificationException();
                }
                if (i > 0) {
                    System.arraycopy(iArr, 0, this.hashes, 0, i + 0);
                    System.arraycopy(objArr, 0, this.array, 0, i2 + 0);
                }
                if (i < i4) {
                    int i10 = i + 1;
                    int i11 = i4 + 1;
                    System.arraycopy(iArr, i10, this.hashes, i, i11 - i10);
                    int i12 = i10 << 1;
                    System.arraycopy(objArr, i12, this.array, i2, (i11 << 1) - i12);
                }
            }
            if (i3 != this.size) {
                throw new ConcurrentModificationException();
            }
            this.size = i4;
        }
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
            throw new IllegalArgumentException(AbstractC0000x2c234b15.m0m("Expected index to be within 0..size()-1, but was ", i).toString());
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
        throw new IllegalArgumentException(AbstractC0000x2c234b15.m0m("Expected index to be within 0..size()-1, but was ", i).toString());
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
    */
    public SimpleArrayMap(SimpleArrayMap simpleArrayMap) {
        this(r2, 1, null);
        int i = 0;
        if (simpleArrayMap != null) {
            int i2 = simpleArrayMap.size;
            ensureCapacity(this.size + i2);
            if (this.size != 0) {
                while (i < i2) {
                    put(simpleArrayMap.keyAt(i), simpleArrayMap.valueAt(i));
                    i++;
                }
            } else if (i2 > 0) {
                System.arraycopy(simpleArrayMap.hashes, 0, this.hashes, 0, i2 + 0);
                System.arraycopy(simpleArrayMap.array, 0, this.array, 0, (i2 << 1) - 0);
                this.size = i2;
            }
        }
    }
}
