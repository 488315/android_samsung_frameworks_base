package androidx.collection;

import androidx.collection.internal.ContainerHelpersKt;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Set;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ArraySet.kt */
/* loaded from: classes.dex */
public final class ArraySet<E> implements Collection<E>, Set<E> {
    private int _size;
    private int[] hashes = ContainerHelpersKt.EMPTY_INTS;
    private Object[] array = ContainerHelpersKt.EMPTY_OBJECTS;

    /* compiled from: ArraySet.kt */
    private final class ElementIterator extends IndexBasedArrayIterator<E> {
        public ElementIterator() {
            super(ArraySet.this._size);
        }

        @Override // androidx.collection.IndexBasedArrayIterator
        protected final E elementAt(int i) {
            return ArraySet.this.valueAt(i);
        }

        @Override // androidx.collection.IndexBasedArrayIterator
        protected final void removeAt(int i) {
            ArraySet.this.removeAt(i);
        }
    }

    private final int indexOf(int i, Object obj) {
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
    public final boolean add(E e) {
        int i;
        int indexOf;
        int i2 = this._size;
        if (e == null) {
            indexOf = indexOf(0, null);
            i = 0;
        } else {
            int hashCode = e.hashCode();
            i = hashCode;
            indexOf = indexOf(hashCode, e);
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
                ArraysKt.copyInto(0, 0, iArr.length, iArr, iArr2);
                ArraysKt.copyInto$default(objArr, this.array, 0, objArr.length, 6);
            }
        }
        if (i3 < i2) {
            int[] iArr3 = this.hashes;
            int i5 = i3 + 1;
            ArraysKt.copyInto(i5, i3, i2, iArr3, iArr3);
            Object[] objArr2 = this.array;
            ArraysKt.copyInto(objArr2, objArr2, i5, i3, i2);
        }
        int i6 = this._size;
        if (i2 == i6) {
            int[] iArr4 = this.hashes;
            if (i3 < iArr4.length) {
                iArr4[i3] = i;
                this.array[i3] = e;
                this._size = i6 + 1;
                return true;
            }
        }
        throw new ConcurrentModificationException();
    }

    @Override // java.util.Collection, java.util.Set
    public final boolean addAll(Collection<? extends E> elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        int size = elements.size() + this._size;
        int i = this._size;
        int[] iArr = this.hashes;
        boolean z = false;
        if (iArr.length < size) {
            Object[] objArr = this.array;
            int[] iArr2 = new int[size];
            this.hashes = iArr2;
            this.array = new Object[size];
            if (i > 0) {
                ArraysKt.copyInto(0, 0, i, iArr, iArr2);
                ArraysKt.copyInto$default(objArr, this.array, 0, this._size, 6);
            }
        }
        if (this._size != i) {
            throw new ConcurrentModificationException();
        }
        Iterator<? extends E> it = elements.iterator();
        while (it.hasNext()) {
            z |= add(it.next());
        }
        return z;
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
    public final boolean containsAll(Collection<? extends Object> elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        Iterator<? extends Object> it = elements.iterator();
        while (it.hasNext()) {
            if (!contains(it.next())) {
                return false;
            }
        }
        return true;
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

    @Override // java.util.Collection, java.util.Set
    public final boolean isEmpty() {
        return this._size <= 0;
    }

    @Override // java.util.Collection, java.lang.Iterable, java.util.Set
    public final Iterator<E> iterator() {
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
    public final boolean removeAll(Collection<? extends Object> elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        Iterator<? extends Object> it = elements.iterator();
        boolean z = false;
        while (it.hasNext()) {
            z |= remove(it.next());
        }
        return z;
    }

    public final void removeAt(int i) {
        int i2 = this._size;
        Object[] objArr = this.array;
        Object obj = objArr[i];
        if (i2 <= 1) {
            clear();
            return;
        }
        int i3 = i2 - 1;
        int[] iArr = this.hashes;
        if (iArr.length <= 8 || i2 >= iArr.length / 3) {
            if (i < i3) {
                int i4 = i + 1;
                int i5 = i3 + 1;
                ArraysKt.copyInto(i, i4, i5, iArr, iArr);
                Object[] objArr2 = this.array;
                ArraysKt.copyInto(objArr2, objArr2, i, i4, i5);
            }
            this.array[i3] = null;
        } else {
            int i6 = i2 > 8 ? i2 + (i2 >> 1) : 8;
            int[] iArr2 = new int[i6];
            this.hashes = iArr2;
            this.array = new Object[i6];
            if (i > 0) {
                ArraysKt.copyInto(0, 0, i, iArr, iArr2);
                ArraysKt.copyInto$default(objArr, this.array, 0, i, 6);
            }
            if (i < i3) {
                int i7 = i + 1;
                int i8 = i3 + 1;
                ArraysKt.copyInto(i, i7, i8, iArr, this.hashes);
                ArraysKt.copyInto(objArr, this.array, i, i7, i8);
            }
        }
        if (i2 != this._size) {
            throw new ConcurrentModificationException();
        }
        this._size = i3;
    }

    @Override // java.util.Collection, java.util.Set
    public final boolean retainAll(Collection<? extends Object> elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        boolean z = false;
        for (int i = this._size - 1; -1 < i; i--) {
            if (!CollectionsKt.contains(elements, this.array[i])) {
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
        return ArraysKt.copyOfRange(this._size, this.array);
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
        String sb2 = sb.toString();
        Intrinsics.checkNotNullExpressionValue(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    public final E valueAt(int i) {
        return (E) this.array[i];
    }

    @Override // java.util.Collection, java.util.Set
    public final <T> T[] toArray(T[] array) {
        Intrinsics.checkNotNullParameter(array, "array");
        int length = array.length;
        int i = this._size;
        if (length < i) {
            return (T[]) ArraysKt.copyOfRange(i, this.array);
        }
        ArraysKt.copyInto(this.array, array, 0, 0, i);
        int length2 = array.length;
        int i2 = this._size;
        if (length2 <= i2) {
            return array;
        }
        array[i2] = null;
        return array;
    }
}
