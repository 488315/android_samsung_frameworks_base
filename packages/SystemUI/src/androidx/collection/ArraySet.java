package androidx.collection;

import androidx.collection.internal.ContainerHelpersKt;
import java.lang.reflect.Array;
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
import kotlin.jvm.internal.markers.KMutableCollection;
import kotlin.jvm.internal.markers.KMutableSet;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ArraySet implements Collection, Set, KMutableCollection, KMutableSet {
    public int _size;
    public Object[] array;
    public int[] hashes;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    public ArraySet() {
        this(0, 1, null);
    }

    @Override // java.util.Collection, java.util.Set
    public final boolean add(Object obj) {
        int i;
        int indexOf;
        int i2 = this._size;
        if (obj == null) {
            indexOf = ArraySetKt.indexOf(this, null, 0);
            i = 0;
        } else {
            int hashCode = obj.hashCode();
            i = hashCode;
            indexOf = ArraySetKt.indexOf(this, obj, hashCode);
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
            if (iArr2.length != 0) {
                ArraysKt___ArraysJvmKt.copyInto$default(0, iArr.length, 6, iArr, iArr2);
                ArraysKt___ArraysJvmKt.copyInto$default(objArr, this.array, 0, objArr.length, 6);
            }
        }
        if (i3 < i2) {
            int[] iArr3 = this.hashes;
            int i5 = i3 + 1;
            ArraysKt___ArraysJvmKt.copyInto(i5, i3, i2, iArr3, iArr3);
            Object[] objArr2 = this.array;
            ArraysKt___ArraysJvmKt.copyInto(objArr2, objArr2, i5, i3, i2);
        }
        int i6 = this._size;
        if (i2 == i6) {
            int[] iArr4 = this.hashes;
            if (i3 < iArr4.length) {
                iArr4[i3] = i;
                this.array[i3] = obj;
                this._size = i6 + 1;
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
        } else if (i > 0) {
            ArraysKt___ArraysJvmKt.copyInto$default(0, i, 6, arraySet.hashes, this.hashes);
            ArraysKt___ArraysJvmKt.copyInto$default(arraySet.array, this.array, 0, i, 6);
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
        return (obj == null ? ArraySetKt.indexOf(this, null, 0) : ArraySetKt.indexOf(this, obj, obj.hashCode())) >= 0;
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
                ArraysKt___ArraysJvmKt.copyInto$default(0, i2, 6, iArr, iArr2);
                ArraysKt___ArraysJvmKt.copyInto$default(objArr, this.array, 0, this._size, 6);
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
        int indexOf = obj == null ? ArraySetKt.indexOf(this, null, 0) : ArraySetKt.indexOf(this, obj, obj.hashCode());
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
            return obj;
        }
        int i3 = i2 - 1;
        int[] iArr = this.hashes;
        if (iArr.length <= 8 || i2 >= iArr.length / 3) {
            if (i < i3) {
                int i4 = i + 1;
                ArraysKt___ArraysJvmKt.copyInto(i, i4, i2, iArr, iArr);
                Object[] objArr2 = this.array;
                ArraysKt___ArraysJvmKt.copyInto(objArr2, objArr2, i, i4, i2);
            }
            this.array[i3] = null;
        } else {
            int i5 = i2 > 8 ? i2 + (i2 >> 1) : 8;
            int[] iArr2 = new int[i5];
            this.hashes = iArr2;
            this.array = new Object[i5];
            if (i > 0) {
                ArraysKt___ArraysJvmKt.copyInto$default(0, i, 6, iArr, iArr2);
                ArraysKt___ArraysJvmKt.copyInto$default(objArr, this.array, 0, i, 6);
            }
            if (i < i3) {
                int i6 = i + 1;
                ArraysKt___ArraysJvmKt.copyInto(i, i6, i2, iArr, this.hashes);
                ArraysKt___ArraysJvmKt.copyInto(objArr, this.array, i, i6, i2);
            }
        }
        if (i2 != this._size) {
            throw new ConcurrentModificationException();
        }
        this._size = i3;
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
        int i = this._size;
        if (objArr.length < i) {
            objArr = (Object[]) Array.newInstance(objArr.getClass().getComponentType(), i);
        } else if (objArr.length > i) {
            objArr[i] = null;
        }
        ArraysKt___ArraysJvmKt.copyInto(this.array, objArr, 0, 0, this._size);
        objArr.getClass();
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

    public ArraySet(Object[] objArr) {
        this(0);
        if (objArr != null) {
            ArrayIterator arrayIterator = new ArrayIterator(objArr);
            while (arrayIterator.hasNext()) {
                add(arrayIterator.next());
            }
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
}
