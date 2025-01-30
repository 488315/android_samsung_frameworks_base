package androidx.collection;

import androidx.collection.internal.ContainerHelpersKt;
import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Set;
import kotlin.collections.ArraysKt__ArraysJVMKt;
import kotlin.collections.ArraysKt___ArraysJvmKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.ArrayIterator;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMutableCollection;
import kotlin.jvm.internal.markers.KMutableSet;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ArraySet implements Collection, Set, KMutableCollection, KMutableSet {
    public int _size;
    public Object[] array;
    public int[] hashes;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ElementIterator extends IndexBasedArrayIterator {
        public ElementIterator() {
            super(ArraySet.this._size);
        }

        @Override // androidx.collection.IndexBasedArrayIterator
        public final Object elementAt(int i) {
            return ArraySet.this.array[i];
        }

        @Override // androidx.collection.IndexBasedArrayIterator
        public final void removeAt(int i) {
            ArraySet.this.removeAt(i);
        }
    }

    static {
        new Companion(null);
    }

    public ArraySet() {
        this(0, 1, null);
    }

    @Override // java.util.Collection, java.util.Set
    public final boolean add(Object obj) {
        int i;
        int indexOf;
        int i2 = this._size;
        if (obj == null) {
            indexOf = indexOf(0, null);
            i = 0;
        } else {
            int hashCode = obj.hashCode();
            i = hashCode;
            indexOf = indexOf(hashCode, obj);
        }
        if (indexOf >= 0) {
            return false;
        }
        int i3 = ~indexOf;
        int[] iArr = this.hashes;
        if (i2 >= iArr.length) {
            int i4 = 8;
            if (i2 >= 8) {
                i4 = (i2 >> 1) + i2;
            } else if (i2 < 4) {
                i4 = 4;
            }
            Object[] objArr = this.array;
            int[] iArr2 = new int[i4];
            this.hashes = iArr2;
            this.array = new Object[i4];
            if (i2 != this._size) {
                throw new ConcurrentModificationException();
            }
            if (!(iArr2.length == 0)) {
                System.arraycopy(iArr, 0, iArr2, 0, iArr.length - 0);
                ArraysKt___ArraysJvmKt.copyInto$default(objArr, this.array, 0, 0, objArr.length, 6);
            }
        }
        if (i3 < i2) {
            int[] iArr3 = this.hashes;
            int i5 = i3 + 1;
            int i6 = i2 - i3;
            System.arraycopy(iArr3, i3, iArr3, i5, i6);
            Object[] objArr2 = this.array;
            System.arraycopy(objArr2, i3, objArr2, i5, i6);
        }
        int i7 = this._size;
        if (i2 == i7) {
            int[] iArr4 = this.hashes;
            if (i3 < iArr4.length) {
                iArr4[i3] = i;
                this.array[i3] = obj;
                this._size = i7 + 1;
                return true;
            }
        }
        throw new ConcurrentModificationException();
    }

    public final void addAll(ArraySet arraySet) {
        int i = arraySet._size;
        ensureCapacity(this._size + i);
        if (this._size != 0) {
            for (int i2 = 0; i2 < i; i2++) {
                add(arraySet.array[i2]);
            }
            return;
        }
        if (i > 0) {
            System.arraycopy(arraySet.hashes, 0, this.hashes, 0, i - 0);
            ArraysKt___ArraysJvmKt.copyInto$default(arraySet.array, this.array, 0, 0, i, 6);
            if (this._size != 0) {
                throw new ConcurrentModificationException();
            }
            this._size = i;
        }
    }

    @Override // java.util.Collection, java.util.Set
    public final void clear() {
        if (this._size != 0) {
            this.hashes = ContainerHelpersKt.EMPTY_INTS;
            this.array = ContainerHelpersKt.EMPTY_OBJECTS;
            this._size = 0;
        }
        if (this._size != 0) {
            throw new ConcurrentModificationException();
        }
    }

    @Override // java.util.Collection, java.util.Set
    public final boolean contains(Object obj) {
        return (obj == null ? indexOf(0, null) : indexOf(obj.hashCode(), obj)) >= 0;
    }

    @Override // java.util.Collection, java.util.Set
    public final boolean containsAll(Collection collection) {
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            if (!contains(it.next())) {
                return false;
            }
        }
        return true;
    }

    public final void ensureCapacity(int i) {
        int i2 = this._size;
        int[] iArr = this.hashes;
        if (iArr.length < i) {
            Object[] objArr = this.array;
            int[] iArr2 = new int[i];
            this.hashes = iArr2;
            this.array = new Object[i];
            if (i2 > 0) {
                System.arraycopy(iArr, 0, iArr2, 0, i2 - 0);
                ArraysKt___ArraysJvmKt.copyInto$default(objArr, this.array, 0, 0, this._size, 6);
            }
        }
        if (this._size != i2) {
            throw new ConcurrentModificationException();
        }
    }

    @Override // java.util.Collection, java.util.Set
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Set) || this._size != ((Set) obj).size()) {
            return false;
        }
        try {
            int i = this._size;
            for (int i2 = 0; i2 < i; i2++) {
                if (!((Set) obj).contains(this.array[i2])) {
                    return false;
                }
            }
            return true;
        } catch (ClassCastException | NullPointerException unused) {
            return false;
        }
    }

    @Override // java.util.Collection, java.util.Set
    public final int hashCode() {
        int[] iArr = this.hashes;
        int i = this._size;
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            i2 += iArr[i3];
        }
        return i2;
    }

    public final int indexOf(int i, Object obj) {
        int i2 = this._size;
        if (i2 == 0) {
            return -1;
        }
        try {
            int binarySearch = ContainerHelpersKt.binarySearch(i2, i, this.hashes);
            if (binarySearch < 0) {
                return binarySearch;
            }
            if (Intrinsics.areEqual(obj, this.array[binarySearch])) {
                return binarySearch;
            }
            int i3 = binarySearch + 1;
            while (i3 < i2 && this.hashes[i3] == i) {
                if (Intrinsics.areEqual(obj, this.array[i3])) {
                    return i3;
                }
                i3++;
            }
            for (int i4 = binarySearch - 1; i4 >= 0 && this.hashes[i4] == i; i4--) {
                if (Intrinsics.areEqual(obj, this.array[i4])) {
                    return i4;
                }
            }
            return ~i3;
        } catch (IndexOutOfBoundsException unused) {
            throw new ConcurrentModificationException();
        }
    }

    @Override // java.util.Collection, java.util.Set
    public final boolean isEmpty() {
        return this._size <= 0;
    }

    @Override // java.util.Collection, java.lang.Iterable, java.util.Set
    public final Iterator iterator() {
        return new ElementIterator();
    }

    @Override // java.util.Collection, java.util.Set
    public final boolean remove(Object obj) {
        int indexOf = obj == null ? indexOf(0, null) : indexOf(obj.hashCode(), obj);
        if (indexOf < 0) {
            return false;
        }
        removeAt(indexOf);
        return true;
    }

    @Override // java.util.Collection, java.util.Set
    public final boolean removeAll(Collection collection) {
        Iterator it = collection.iterator();
        boolean z = false;
        while (it.hasNext()) {
            z |= remove(it.next());
        }
        return z;
    }

    public final Object removeAt(int i) {
        int i2 = this._size;
        Object[] objArr = this.array;
        Object obj = objArr[i];
        if (i2 <= 1) {
            clear();
        } else {
            int i3 = i2 - 1;
            int[] iArr = this.hashes;
            if (iArr.length <= 8 || i2 >= iArr.length / 3) {
                if (i < i3) {
                    int i4 = i + 1;
                    int i5 = (i3 + 1) - i4;
                    System.arraycopy(iArr, i4, iArr, i, i5);
                    Object[] objArr2 = this.array;
                    System.arraycopy(objArr2, i4, objArr2, i, i5);
                }
                this.array[i3] = null;
            } else {
                int i6 = i2 > 8 ? i2 + (i2 >> 1) : 8;
                int[] iArr2 = new int[i6];
                this.hashes = iArr2;
                this.array = new Object[i6];
                if (i > 0) {
                    System.arraycopy(iArr, 0, iArr2, 0, i - 0);
                    ArraysKt___ArraysJvmKt.copyInto$default(objArr, this.array, 0, 0, i, 6);
                }
                if (i < i3) {
                    int i7 = i + 1;
                    int i8 = (i3 + 1) - i7;
                    System.arraycopy(iArr, i7, this.hashes, i, i8);
                    System.arraycopy(objArr, i7, this.array, i, i8);
                }
            }
            if (i2 != this._size) {
                throw new ConcurrentModificationException();
            }
            this._size = i3;
        }
        return obj;
    }

    @Override // java.util.Collection, java.util.Set
    public final boolean retainAll(Collection collection) {
        boolean z = false;
        for (int i = this._size - 1; -1 < i; i--) {
            if (!CollectionsKt___CollectionsKt.contains(collection, this.array[i])) {
                removeAt(i);
                z = true;
            }
        }
        return z;
    }

    @Override // java.util.Collection, java.util.Set
    public final int size() {
        return this._size;
    }

    @Override // java.util.Collection, java.util.Set
    public final Object[] toArray() {
        Object[] objArr = this.array;
        int i = this._size;
        ArraysKt__ArraysJVMKt.copyOfRangeToIndexCheck(i, objArr.length);
        return Arrays.copyOfRange(objArr, 0, i);
    }

    public final String toString() {
        if (isEmpty()) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder(this._size * 14);
        sb.append('{');
        int i = this._size;
        for (int i2 = 0; i2 < i; i2++) {
            if (i2 > 0) {
                sb.append(", ");
            }
            Object obj = this.array[i2];
            if (obj != this) {
                sb.append(obj);
            } else {
                sb.append("(this Set)");
            }
        }
        sb.append('}');
        return sb.toString();
    }

    public ArraySet(int i) {
        this.hashes = ContainerHelpersKt.EMPTY_INTS;
        this.array = ContainerHelpersKt.EMPTY_OBJECTS;
        if (i > 0) {
            this.hashes = new int[i];
            this.array = new Object[i];
        }
    }

    @Override // java.util.Collection, java.util.Set
    public final Object[] toArray(Object[] objArr) {
        int length = objArr.length;
        int i = this._size;
        if (length < i) {
            Object[] objArr2 = this.array;
            ArraysKt__ArraysJVMKt.copyOfRangeToIndexCheck(i, objArr2.length);
            return Arrays.copyOfRange(objArr2, 0, i);
        }
        System.arraycopy(this.array, 0, objArr, 0, i - 0);
        int length2 = objArr.length;
        int i2 = this._size;
        if (length2 <= i2) {
            return objArr;
        }
        objArr[i2] = null;
        return objArr;
    }

    public /* synthetic */ ArraySet(int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 0 : i);
    }

    public ArraySet(ArraySet arraySet) {
        this(0);
        if (arraySet != null) {
            addAll(arraySet);
        }
    }

    public ArraySet(Collection<Object> collection) {
        this(0);
        if (collection != null) {
            addAll(collection);
        }
    }

    @Override // java.util.Collection, java.util.Set
    public final boolean addAll(Collection collection) {
        ensureCapacity(collection.size() + this._size);
        Iterator it = collection.iterator();
        boolean z = false;
        while (it.hasNext()) {
            z |= add(it.next());
        }
        return z;
    }

    public ArraySet(Object[] objArr) {
        this(0);
        if (objArr != null) {
            ArrayIterator arrayIterator = new ArrayIterator(objArr);
            while (arrayIterator.hasNext()) {
                add(arrayIterator.next());
            }
        }
    }
}
