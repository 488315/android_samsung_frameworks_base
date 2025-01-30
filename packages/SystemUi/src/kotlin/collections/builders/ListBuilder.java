package kotlin.collections.builders;

import java.io.NotSerializableException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.RandomAccess;
import kotlin.collections.AbstractList;
import kotlin.collections.AbstractMutableList;
import kotlin.collections.ArrayDeque;
import kotlin.collections.ArraysKt__ArraysJVMKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class ListBuilder<E> extends AbstractMutableList implements RandomAccess, Serializable {
    private E[] array;
    private final ListBuilder<E> backing;
    private boolean isReadOnly;
    private int length;
    private int offset;
    private final ListBuilder<E> root;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Itr implements ListIterator, KMappedMarker {
        public int index;
        public int lastIndex = -1;
        public final ListBuilder list;

        public Itr(ListBuilder<Object> listBuilder, int i) {
            this.list = listBuilder;
            this.index = i;
        }

        @Override // java.util.ListIterator
        public final void add(Object obj) {
            ListBuilder listBuilder = this.list;
            int i = this.index;
            this.index = i + 1;
            listBuilder.add(i, obj);
            this.lastIndex = -1;
        }

        @Override // java.util.ListIterator, java.util.Iterator
        public final boolean hasNext() {
            return this.index < this.list.length;
        }

        @Override // java.util.ListIterator
        public final boolean hasPrevious() {
            return this.index > 0;
        }

        @Override // java.util.ListIterator, java.util.Iterator
        public final Object next() {
            if (this.index >= this.list.length) {
                throw new NoSuchElementException();
            }
            int i = this.index;
            this.index = i + 1;
            this.lastIndex = i;
            return this.list.array[this.list.offset + this.lastIndex];
        }

        @Override // java.util.ListIterator
        public final int nextIndex() {
            return this.index;
        }

        @Override // java.util.ListIterator
        public final Object previous() {
            int i = this.index;
            if (i <= 0) {
                throw new NoSuchElementException();
            }
            int i2 = i - 1;
            this.index = i2;
            this.lastIndex = i2;
            return this.list.array[this.list.offset + this.lastIndex];
        }

        @Override // java.util.ListIterator
        public final int previousIndex() {
            return this.index - 1;
        }

        @Override // java.util.ListIterator, java.util.Iterator
        public final void remove() {
            int i = this.lastIndex;
            if (!(i != -1)) {
                throw new IllegalStateException("Call next() or previous() before removing element from the iterator.".toString());
            }
            this.list.removeAt(i);
            this.index = this.lastIndex;
            this.lastIndex = -1;
        }

        @Override // java.util.ListIterator
        public final void set(Object obj) {
            int i = this.lastIndex;
            if (!(i != -1)) {
                throw new IllegalStateException("Call next() or previous() before replacing element from the iterator.".toString());
            }
            this.list.set(i, obj);
        }
    }

    private ListBuilder(E[] eArr, int i, int i2, boolean z, ListBuilder<E> listBuilder, ListBuilder<E> listBuilder2) {
        this.array = eArr;
        this.offset = i;
        this.length = i2;
        this.isReadOnly = z;
        this.backing = listBuilder;
        this.root = listBuilder2;
    }

    private final Object writeReplace() {
        ListBuilder<E> listBuilder;
        if (this.isReadOnly || ((listBuilder = this.root) != null && listBuilder.isReadOnly)) {
            return new SerializedCollection(this, 0);
        }
        throw new NotSerializableException("The list cannot be serialized while it is being built.");
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean add(Object obj) {
        checkIsMutable();
        addAtInternal(this.offset + this.length, obj);
        return true;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean addAll(Collection collection) {
        checkIsMutable();
        int size = collection.size();
        addAllInternal(this.offset + this.length, collection, size);
        return size > 0;
    }

    public final void addAllInternal(int i, Collection collection, int i2) {
        ListBuilder<E> listBuilder = this.backing;
        if (listBuilder != null) {
            listBuilder.addAllInternal(i, collection, i2);
            this.array = this.backing.array;
            this.length += i2;
        } else {
            insertAtInternal(i, i2);
            Iterator<E> it = collection.iterator();
            for (int i3 = 0; i3 < i2; i3++) {
                this.array[i + i3] = it.next();
            }
        }
    }

    public final void addAtInternal(int i, Object obj) {
        ListBuilder<E> listBuilder = this.backing;
        if (listBuilder == null) {
            insertAtInternal(i, 1);
            ((E[]) this.array)[i] = obj;
        } else {
            listBuilder.addAtInternal(i, obj);
            this.array = this.backing.array;
            this.length++;
        }
    }

    public final void build() {
        if (this.backing != null) {
            throw new IllegalStateException();
        }
        checkIsMutable();
        this.isReadOnly = true;
    }

    public final void checkIsMutable() {
        ListBuilder<E> listBuilder;
        if (this.isReadOnly || ((listBuilder = this.root) != null && listBuilder.isReadOnly)) {
            throw new UnsupportedOperationException();
        }
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final void clear() {
        checkIsMutable();
        removeRangeInternal(this.offset, this.length);
    }

    /* JADX WARN: Removed duplicated region for block: B:16:? A[RETURN, SYNTHETIC] */
    @Override // java.util.AbstractList, java.util.Collection, java.util.List
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean equals(Object obj) {
        boolean z;
        if (obj == this) {
            return true;
        }
        if (obj instanceof List) {
            List list = (List) obj;
            E[] eArr = this.array;
            int i = this.offset;
            int i2 = this.length;
            if (i2 == list.size()) {
                for (int i3 = 0; i3 < i2; i3++) {
                    if (Intrinsics.areEqual(eArr[i + i3], list.get(i3))) {
                    }
                }
                z = true;
                if (z) {
                    return true;
                }
            }
            z = false;
            if (z) {
            }
        }
        return false;
    }

    @Override // java.util.AbstractList, java.util.List
    public final Object get(int i) {
        AbstractList.Companion companion = AbstractList.Companion;
        int i2 = this.length;
        companion.getClass();
        AbstractList.Companion.checkElementIndex$kotlin_stdlib(i, i2);
        return this.array[this.offset + i];
    }

    @Override // kotlin.collections.AbstractMutableList
    public final int getSize() {
        return this.length;
    }

    @Override // java.util.AbstractList, java.util.Collection, java.util.List
    public final int hashCode() {
        E[] eArr = this.array;
        int i = this.offset;
        int i2 = this.length;
        int i3 = 1;
        for (int i4 = 0; i4 < i2; i4++) {
            E e = eArr[i + i4];
            i3 = (i3 * 31) + (e != null ? e.hashCode() : 0);
        }
        return i3;
    }

    @Override // java.util.AbstractList, java.util.List
    public final int indexOf(Object obj) {
        for (int i = 0; i < this.length; i++) {
            if (Intrinsics.areEqual(this.array[this.offset + i], obj)) {
                return i;
            }
        }
        return -1;
    }

    public final void insertAtInternal(int i, int i2) {
        int i3 = this.length + i2;
        if (this.backing != null) {
            throw new IllegalStateException();
        }
        if (i3 < 0) {
            throw new OutOfMemoryError();
        }
        E[] eArr = this.array;
        if (i3 > eArr.length) {
            ArrayDeque.Companion companion = ArrayDeque.Companion;
            int length = eArr.length;
            companion.getClass();
            int i4 = length + (length >> 1);
            if (i4 - i3 < 0) {
                i4 = i3;
            }
            if (i4 - 2147483639 > 0) {
                i4 = i3 > 2147483639 ? Integer.MAX_VALUE : 2147483639;
            }
            this.array = (E[]) Arrays.copyOf(this.array, i4);
        }
        E[] eArr2 = this.array;
        System.arraycopy(eArr2, i, eArr2, i + i2, (this.offset + this.length) - i);
        this.length += i2;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean isEmpty() {
        return this.length == 0;
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.List
    public final Iterator iterator() {
        return new Itr(this, 0);
    }

    @Override // java.util.AbstractList, java.util.List
    public final int lastIndexOf(Object obj) {
        for (int i = this.length - 1; i >= 0; i--) {
            if (Intrinsics.areEqual(this.array[this.offset + i], obj)) {
                return i;
            }
        }
        return -1;
    }

    @Override // java.util.AbstractList, java.util.List
    public final ListIterator listIterator() {
        return new Itr(this, 0);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean remove(Object obj) {
        checkIsMutable();
        int indexOf = indexOf(obj);
        if (indexOf >= 0) {
            removeAt(indexOf);
        }
        return indexOf >= 0;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean removeAll(Collection collection) {
        checkIsMutable();
        return retainOrRemoveAllInternal(false, collection, this.offset, this.length) > 0;
    }

    @Override // kotlin.collections.AbstractMutableList
    public final Object removeAt(int i) {
        checkIsMutable();
        AbstractList.Companion companion = AbstractList.Companion;
        int i2 = this.length;
        companion.getClass();
        AbstractList.Companion.checkElementIndex$kotlin_stdlib(i, i2);
        return removeAtInternal(this.offset + i);
    }

    public final Object removeAtInternal(int i) {
        ListBuilder<E> listBuilder = this.backing;
        if (listBuilder != null) {
            this.length--;
            return listBuilder.removeAtInternal(i);
        }
        E[] eArr = this.array;
        E e = eArr[i];
        int i2 = i + 1;
        System.arraycopy(eArr, i2, eArr, i, (this.offset + this.length) - i2);
        E[] eArr2 = this.array;
        int i3 = this.offset;
        int i4 = this.length;
        eArr2[(i3 + i4) - 1] = null;
        this.length = i4 - 1;
        return e;
    }

    public final void removeRangeInternal(int i, int i2) {
        ListBuilder<E> listBuilder = this.backing;
        if (listBuilder != null) {
            listBuilder.removeRangeInternal(i, i2);
        } else {
            E[] eArr = this.array;
            int i3 = i + i2;
            System.arraycopy(eArr, i3, eArr, i, this.length - i3);
            E[] eArr2 = this.array;
            int i4 = this.length;
            ListBuilderKt.resetRange(i4 - i2, i4, eArr2);
        }
        this.length -= i2;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean retainAll(Collection collection) {
        checkIsMutable();
        return retainOrRemoveAllInternal(true, collection, this.offset, this.length) > 0;
    }

    public final int retainOrRemoveAllInternal(boolean z, Collection collection, int i, int i2) {
        ListBuilder<E> listBuilder = this.backing;
        if (listBuilder != null) {
            int retainOrRemoveAllInternal = listBuilder.retainOrRemoveAllInternal(z, collection, i, i2);
            this.length -= retainOrRemoveAllInternal;
            return retainOrRemoveAllInternal;
        }
        int i3 = 0;
        int i4 = 0;
        while (i3 < i2) {
            int i5 = i + i3;
            if (collection.contains(this.array[i5]) == z) {
                E[] eArr = this.array;
                i3++;
                eArr[i4 + i] = eArr[i5];
                i4++;
            } else {
                i3++;
            }
        }
        int i6 = i2 - i4;
        E[] eArr2 = this.array;
        int i7 = i2 + i;
        System.arraycopy(eArr2, i7, eArr2, i + i4, this.length - i7);
        E[] eArr3 = this.array;
        int i8 = this.length;
        ListBuilderKt.resetRange(i8 - i6, i8, eArr3);
        this.length -= i6;
        return i6;
    }

    @Override // java.util.AbstractList, java.util.List
    public final Object set(int i, Object obj) {
        checkIsMutable();
        AbstractList.Companion companion = AbstractList.Companion;
        int i2 = this.length;
        companion.getClass();
        AbstractList.Companion.checkElementIndex$kotlin_stdlib(i, i2);
        Object[] objArr = this.array;
        int i3 = this.offset + i;
        Object obj2 = objArr[i3];
        objArr[i3] = obj;
        return obj2;
    }

    @Override // java.util.AbstractList, java.util.List
    public final List subList(int i, int i2) {
        AbstractList.Companion companion = AbstractList.Companion;
        int i3 = this.length;
        companion.getClass();
        AbstractList.Companion.checkRangeIndexes$kotlin_stdlib(i, i2, i3);
        E[] eArr = this.array;
        int i4 = this.offset + i;
        int i5 = i2 - i;
        boolean z = this.isReadOnly;
        ListBuilder<E> listBuilder = this.root;
        return new ListBuilder(eArr, i4, i5, z, this, listBuilder == null ? this : listBuilder);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final Object[] toArray(Object[] objArr) {
        int length = objArr.length;
        int i = this.length;
        if (length < i) {
            E[] eArr = this.array;
            int i2 = this.offset;
            return Arrays.copyOfRange(eArr, i2, i + i2, objArr.getClass());
        }
        E[] eArr2 = this.array;
        int i3 = this.offset;
        System.arraycopy(eArr2, i3, objArr, 0, (i + i3) - i3);
        int length2 = objArr.length;
        int i4 = this.length;
        if (length2 > i4) {
            objArr[i4] = null;
        }
        return objArr;
    }

    @Override // java.util.AbstractCollection
    public final String toString() {
        E[] eArr = this.array;
        int i = this.offset;
        int i2 = this.length;
        StringBuilder sb = new StringBuilder((i2 * 3) + 2);
        sb.append("[");
        for (int i3 = 0; i3 < i2; i3++) {
            if (i3 > 0) {
                sb.append(", ");
            }
            sb.append(eArr[i + i3]);
        }
        sb.append("]");
        return sb.toString();
    }

    @Override // java.util.AbstractList, java.util.List
    public final ListIterator listIterator(int i) {
        AbstractList.Companion companion = AbstractList.Companion;
        int i2 = this.length;
        companion.getClass();
        AbstractList.Companion.checkPositionIndex$kotlin_stdlib(i, i2);
        return new Itr(this, i);
    }

    @Override // java.util.AbstractList, java.util.List
    public final void add(int i, Object obj) {
        checkIsMutable();
        AbstractList.Companion companion = AbstractList.Companion;
        int i2 = this.length;
        companion.getClass();
        AbstractList.Companion.checkPositionIndex$kotlin_stdlib(i, i2);
        addAtInternal(this.offset + i, obj);
    }

    @Override // java.util.AbstractList, java.util.List
    public final boolean addAll(int i, Collection collection) {
        checkIsMutable();
        AbstractList.Companion companion = AbstractList.Companion;
        int i2 = this.length;
        companion.getClass();
        AbstractList.Companion.checkPositionIndex$kotlin_stdlib(i, i2);
        int size = collection.size();
        addAllInternal(this.offset + i, collection, size);
        return size > 0;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final Object[] toArray() {
        E[] eArr = this.array;
        int i = this.offset;
        int i2 = this.length + i;
        ArraysKt__ArraysJVMKt.copyOfRangeToIndexCheck(i2, eArr.length);
        return Arrays.copyOfRange(eArr, i, i2);
    }

    public ListBuilder() {
        this(10);
    }

    public ListBuilder(int i) {
        this(ListBuilderKt.arrayOfUninitializedElements(i), 0, 0, false, null, null);
    }
}
