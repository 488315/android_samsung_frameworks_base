package kotlin.collections.builders;

import java.io.NotSerializableException;
import java.io.Serializable;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.RandomAccess;
import kotlin.collections.AbstractList;
import kotlin.collections.AbstractMutableList;
import kotlin.collections.ArraysKt__ArraysJVMKt;
import kotlin.collections.ArraysKt___ArraysJvmKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;

/* loaded from: classes4.dex */
public final class ListBuilder<E> extends AbstractMutableList implements List<E>, RandomAccess, Serializable {
    public static final ListBuilder Empty;
    private E[] backing;
    private boolean isReadOnly;
    private int length;

    public final class BuilderSubList<E> extends AbstractMutableList implements List<E>, RandomAccess, Serializable {
        private E[] backing;
        private int length;
        private final int offset;
        private final BuilderSubList<E> parent;
        private final ListBuilder<E> root;

        public final class Itr implements ListIterator, KMappedMarker {
            public int expectedModCount;
            public int index;
            public int lastIndex = -1;
            public final BuilderSubList list;

            public Itr(BuilderSubList<Object> builderSubList, int i) {
                this.list = builderSubList;
                this.index = i;
                this.expectedModCount = ((AbstractList) builderSubList).modCount;
            }

            @Override // java.util.ListIterator
            public final void add(Object obj) {
                checkForComodification$2();
                BuilderSubList builderSubList = this.list;
                int i = this.index;
                this.index = i + 1;
                builderSubList.add(i, obj);
                this.lastIndex = -1;
                this.expectedModCount = ((AbstractList) this.list).modCount;
            }

            public final void checkForComodification$2() {
                if (((AbstractList) this.list.root).modCount != this.expectedModCount) {
                    throw new ConcurrentModificationException();
                }
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
                checkForComodification$2();
                if (this.index >= this.list.length) {
                    throw new NoSuchElementException();
                }
                int i = this.index;
                this.index = i + 1;
                this.lastIndex = i;
                return this.list.backing[this.list.offset + this.lastIndex];
            }

            @Override // java.util.ListIterator
            public final int nextIndex() {
                return this.index;
            }

            @Override // java.util.ListIterator
            public final Object previous() {
                checkForComodification$2();
                int i = this.index;
                if (i <= 0) {
                    throw new NoSuchElementException();
                }
                int i2 = i - 1;
                this.index = i2;
                this.lastIndex = i2;
                return this.list.backing[this.list.offset + this.lastIndex];
            }

            @Override // java.util.ListIterator
            public final int previousIndex() {
                return this.index - 1;
            }

            @Override // java.util.ListIterator, java.util.Iterator
            public final void remove() {
                checkForComodification$2();
                int i = this.lastIndex;
                if (i == -1) {
                    throw new IllegalStateException("Call next() or previous() before removing element from the iterator.");
                }
                this.list.removeAt(i);
                this.index = this.lastIndex;
                this.lastIndex = -1;
                this.expectedModCount = ((AbstractList) this.list).modCount;
            }

            @Override // java.util.ListIterator
            public final void set(Object obj) {
                checkForComodification$2();
                int i = this.lastIndex;
                if (i == -1) {
                    throw new IllegalStateException("Call next() or previous() before replacing element from the iterator.");
                }
                this.list.set(i, obj);
            }
        }

        public BuilderSubList(E[] eArr, int i, int i2, BuilderSubList<E> builderSubList, ListBuilder<E> listBuilder) {
            this.backing = eArr;
            this.offset = i;
            this.length = i2;
            this.parent = builderSubList;
            this.root = listBuilder;
            ((AbstractList) this).modCount = ((AbstractList) listBuilder).modCount;
        }

        private final Object writeReplace() throws NotSerializableException {
            if (((ListBuilder) this.root).isReadOnly) {
                return new SerializedCollection(this, 0);
            }
            throw new NotSerializableException("The list cannot be serialized while it is being built.");
        }

        @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
        public final boolean add(Object obj) {
            checkIsMutable();
            checkForComodification$4();
            addAtInternal(this.offset + this.length, obj);
            return true;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public final boolean addAll(Collection collection) {
            checkIsMutable();
            checkForComodification$4();
            int size = collection.size();
            addAllInternal(this.offset + this.length, collection, size);
            return size > 0;
        }

        public final void addAllInternal(int i, Collection collection, int i2) {
            ((AbstractList) this).modCount++;
            BuilderSubList<E> builderSubList = this.parent;
            if (builderSubList != null) {
                builderSubList.addAllInternal(i, collection, i2);
            } else {
                ListBuilder<E> listBuilder = this.root;
                ListBuilder listBuilder2 = ListBuilder.Empty;
                listBuilder.addAllInternal$1(i, collection, i2);
            }
            this.backing = (E[]) ((ListBuilder) this.root).backing;
            this.length += i2;
        }

        public final void addAtInternal(int i, Object obj) {
            ((AbstractList) this).modCount++;
            BuilderSubList<E> builderSubList = this.parent;
            if (builderSubList != null) {
                builderSubList.addAtInternal(i, obj);
            } else {
                ListBuilder<E> listBuilder = this.root;
                ListBuilder listBuilder2 = ListBuilder.Empty;
                listBuilder.addAtInternal$1(i, obj);
            }
            this.backing = (E[]) ((ListBuilder) this.root).backing;
            this.length++;
        }

        public final void checkForComodification$4() {
            if (((AbstractList) this.root).modCount != ((AbstractList) this).modCount) {
                throw new ConcurrentModificationException();
            }
        }

        public final void checkIsMutable() {
            if (((ListBuilder) this.root).isReadOnly) {
                throw new UnsupportedOperationException();
            }
        }

        @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
        public final void clear() {
            checkIsMutable();
            checkForComodification$4();
            removeRangeInternal(this.offset, this.length);
        }

        @Override // java.util.AbstractList, java.util.Collection, java.util.List
        public final boolean equals(Object obj) {
            checkForComodification$4();
            if (obj == this) {
                return true;
            }
            if (obj instanceof List) {
                return ListBuilderKt.access$subarrayContentEquals(this.backing, this.offset, this.length, (List) obj);
            }
            return false;
        }

        @Override // java.util.AbstractList, java.util.List
        public final Object get(int i) {
            checkForComodification$4();
            AbstractList.Companion companion = kotlin.collections.AbstractList.Companion;
            int i2 = this.length;
            companion.getClass();
            AbstractList.Companion.checkElementIndex$kotlin_stdlib(i, i2);
            return this.backing[this.offset + i];
        }

        @Override // kotlin.collections.AbstractMutableList
        public final int getSize() {
            checkForComodification$4();
            return this.length;
        }

        @Override // java.util.AbstractList, java.util.Collection, java.util.List
        public final int hashCode() {
            checkForComodification$4();
            E[] eArr = this.backing;
            int i = this.offset;
            int i2 = this.length;
            int iHashCode = 1;
            for (int i3 = 0; i3 < i2; i3++) {
                E e = eArr[i + i3];
                iHashCode = (iHashCode * 31) + (e != null ? e.hashCode() : 0);
            }
            return iHashCode;
        }

        @Override // java.util.AbstractList, java.util.List
        public final int indexOf(Object obj) {
            checkForComodification$4();
            for (int i = 0; i < this.length; i++) {
                if (Intrinsics.areEqual(this.backing[this.offset + i], obj)) {
                    return i;
                }
            }
            return -1;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public final boolean isEmpty() {
            checkForComodification$4();
            return this.length == 0;
        }

        @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.List
        public final Iterator iterator() {
            return listIterator(0);
        }

        @Override // java.util.AbstractList, java.util.List
        public final int lastIndexOf(Object obj) {
            checkForComodification$4();
            for (int i = this.length - 1; i >= 0; i--) {
                if (Intrinsics.areEqual(this.backing[this.offset + i], obj)) {
                    return i;
                }
            }
            return -1;
        }

        @Override // java.util.AbstractList, java.util.List
        public final ListIterator listIterator() {
            return listIterator(0);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public final boolean remove(Object obj) {
            checkIsMutable();
            checkForComodification$4();
            int iIndexOf = indexOf(obj);
            if (iIndexOf >= 0) {
                removeAt(iIndexOf);
            }
            return iIndexOf >= 0;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public final boolean removeAll(Collection collection) {
            checkIsMutable();
            checkForComodification$4();
            return retainOrRemoveAllInternal(false, collection, this.offset, this.length) > 0;
        }

        @Override // kotlin.collections.AbstractMutableList
        public final Object removeAt(int i) {
            checkIsMutable();
            checkForComodification$4();
            AbstractList.Companion companion = kotlin.collections.AbstractList.Companion;
            int i2 = this.length;
            companion.getClass();
            AbstractList.Companion.checkElementIndex$kotlin_stdlib(i, i2);
            return removeAtInternal(this.offset + i);
        }

        public final Object removeAtInternal(int i) {
            Object objRemoveAtInternal$1;
            ((java.util.AbstractList) this).modCount++;
            BuilderSubList<E> builderSubList = this.parent;
            if (builderSubList != null) {
                objRemoveAtInternal$1 = builderSubList.removeAtInternal(i);
            } else {
                ListBuilder<E> listBuilder = this.root;
                ListBuilder listBuilder2 = ListBuilder.Empty;
                objRemoveAtInternal$1 = listBuilder.removeAtInternal$1(i);
            }
            this.length--;
            return objRemoveAtInternal$1;
        }

        public final void removeRangeInternal(int i, int i2) {
            if (i2 > 0) {
                ((java.util.AbstractList) this).modCount++;
            }
            BuilderSubList<E> builderSubList = this.parent;
            if (builderSubList != null) {
                builderSubList.removeRangeInternal(i, i2);
            } else {
                ListBuilder<E> listBuilder = this.root;
                ListBuilder listBuilder2 = ListBuilder.Empty;
                listBuilder.removeRangeInternal$1(i, i2);
            }
            this.length -= i2;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public final boolean retainAll(Collection collection) {
            checkIsMutable();
            checkForComodification$4();
            return retainOrRemoveAllInternal(true, collection, this.offset, this.length) > 0;
        }

        public final int retainOrRemoveAllInternal(boolean z, Collection collection, int i, int i2) {
            int iRetainOrRemoveAllInternal$1;
            BuilderSubList<E> builderSubList = this.parent;
            if (builderSubList != null) {
                iRetainOrRemoveAllInternal$1 = builderSubList.retainOrRemoveAllInternal(z, collection, i, i2);
            } else {
                ListBuilder<E> listBuilder = this.root;
                ListBuilder listBuilder2 = ListBuilder.Empty;
                iRetainOrRemoveAllInternal$1 = listBuilder.retainOrRemoveAllInternal$1(z, collection, i, i2);
            }
            if (iRetainOrRemoveAllInternal$1 > 0) {
                ((java.util.AbstractList) this).modCount++;
            }
            this.length -= iRetainOrRemoveAllInternal$1;
            return iRetainOrRemoveAllInternal$1;
        }

        @Override // java.util.AbstractList, java.util.List
        public final Object set(int i, Object obj) {
            checkIsMutable();
            checkForComodification$4();
            AbstractList.Companion companion = kotlin.collections.AbstractList.Companion;
            int i2 = this.length;
            companion.getClass();
            AbstractList.Companion.checkElementIndex$kotlin_stdlib(i, i2);
            Object[] objArr = this.backing;
            int i3 = this.offset + i;
            Object obj2 = objArr[i3];
            objArr[i3] = obj;
            return obj2;
        }

        @Override // java.util.AbstractList, java.util.List
        public final List subList(int i, int i2) {
            AbstractList.Companion companion = kotlin.collections.AbstractList.Companion;
            int i3 = this.length;
            companion.getClass();
            AbstractList.Companion.checkRangeIndexes$kotlin_stdlib(i, i2, i3);
            return new BuilderSubList(this.backing, this.offset + i, i2 - i, this, this.root);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public final Object[] toArray(Object[] objArr) {
            checkForComodification$4();
            int length = objArr.length;
            int i = this.length;
            if (length < i) {
                E[] eArr = this.backing;
                int i2 = this.offset;
                return Arrays.copyOfRange(eArr, i2, i + i2, objArr.getClass());
            }
            E[] eArr2 = this.backing;
            int i3 = this.offset;
            ArraysKt___ArraysJvmKt.copyInto(eArr2, objArr, 0, i3, i + i3);
            int i4 = this.length;
            if (i4 < objArr.length) {
                objArr[i4] = null;
            }
            return objArr;
        }

        @Override // java.util.AbstractCollection
        public final String toString() {
            checkForComodification$4();
            return ListBuilderKt.access$subarrayContentToString(this.backing, this.offset, this.length, this);
        }

        @Override // java.util.AbstractList, java.util.List
        public final ListIterator listIterator(int i) {
            checkForComodification$4();
            AbstractList.Companion companion = kotlin.collections.AbstractList.Companion;
            int i2 = this.length;
            companion.getClass();
            AbstractList.Companion.checkPositionIndex$kotlin_stdlib(i, i2);
            return new Itr(this, i);
        }

        @Override // java.util.AbstractList, java.util.List
        public final void add(int i, Object obj) {
            checkIsMutable();
            checkForComodification$4();
            AbstractList.Companion companion = kotlin.collections.AbstractList.Companion;
            int i2 = this.length;
            companion.getClass();
            AbstractList.Companion.checkPositionIndex$kotlin_stdlib(i, i2);
            addAtInternal(this.offset + i, obj);
        }

        @Override // java.util.AbstractList, java.util.List
        public final boolean addAll(int i, Collection collection) {
            checkIsMutable();
            checkForComodification$4();
            AbstractList.Companion companion = kotlin.collections.AbstractList.Companion;
            int i2 = this.length;
            companion.getClass();
            AbstractList.Companion.checkPositionIndex$kotlin_stdlib(i, i2);
            int size = collection.size();
            addAllInternal(this.offset + i, collection, size);
            return size > 0;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public final Object[] toArray() {
            checkForComodification$4();
            E[] eArr = this.backing;
            int i = this.offset;
            int i2 = this.length + i;
            ArraysKt__ArraysJVMKt.copyOfRangeToIndexCheck(i2, eArr.length);
            return Arrays.copyOfRange(eArr, i, i2);
        }
    }

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final class Itr implements ListIterator, KMappedMarker {
        public int expectedModCount;
        public int index;
        public int lastIndex = -1;
        public final ListBuilder list;

        public Itr(ListBuilder<Object> listBuilder, int i) {
            this.list = listBuilder;
            this.index = i;
            this.expectedModCount = ((java.util.AbstractList) listBuilder).modCount;
        }

        @Override // java.util.ListIterator
        public final void add(Object obj) {
            checkForComodification$3();
            ListBuilder listBuilder = this.list;
            int i = this.index;
            this.index = i + 1;
            listBuilder.add(i, obj);
            this.lastIndex = -1;
            this.expectedModCount = ((java.util.AbstractList) this.list).modCount;
        }

        public final void checkForComodification$3() {
            if (((java.util.AbstractList) this.list).modCount != this.expectedModCount) {
                throw new ConcurrentModificationException();
            }
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
            checkForComodification$3();
            if (this.index >= this.list.length) {
                throw new NoSuchElementException();
            }
            int i = this.index;
            this.index = i + 1;
            this.lastIndex = i;
            return this.list.backing[this.lastIndex];
        }

        @Override // java.util.ListIterator
        public final int nextIndex() {
            return this.index;
        }

        @Override // java.util.ListIterator
        public final Object previous() {
            checkForComodification$3();
            int i = this.index;
            if (i <= 0) {
                throw new NoSuchElementException();
            }
            int i2 = i - 1;
            this.index = i2;
            this.lastIndex = i2;
            return this.list.backing[this.lastIndex];
        }

        @Override // java.util.ListIterator
        public final int previousIndex() {
            return this.index - 1;
        }

        @Override // java.util.ListIterator, java.util.Iterator
        public final void remove() {
            checkForComodification$3();
            int i = this.lastIndex;
            if (i == -1) {
                throw new IllegalStateException("Call next() or previous() before removing element from the iterator.");
            }
            this.list.removeAt(i);
            this.index = this.lastIndex;
            this.lastIndex = -1;
            this.expectedModCount = ((java.util.AbstractList) this.list).modCount;
        }

        @Override // java.util.ListIterator
        public final void set(Object obj) {
            checkForComodification$3();
            int i = this.lastIndex;
            if (i == -1) {
                throw new IllegalStateException("Call next() or previous() before replacing element from the iterator.");
            }
            this.list.set(i, obj);
        }
    }

    static {
        new Companion(null);
        ListBuilder listBuilder = new ListBuilder(0);
        listBuilder.isReadOnly = true;
        Empty = listBuilder;
    }

    public ListBuilder() {
        this(0, 1, null);
    }

    private final Object writeReplace() throws NotSerializableException {
        if (this.isReadOnly) {
            return new SerializedCollection(this, 0);
        }
        throw new NotSerializableException("The list cannot be serialized while it is being built.");
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean add(Object obj) {
        checkIsMutable$1();
        addAtInternal$1(this.length, obj);
        return true;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean addAll(Collection collection) {
        checkIsMutable$1();
        int size = collection.size();
        addAllInternal$1(this.length, collection, size);
        return size > 0;
    }

    public final void addAllInternal$1(int i, Collection collection, int i2) {
        ((java.util.AbstractList) this).modCount++;
        insertAtInternal(i, i2);
        Iterator<E> it = collection.iterator();
        for (int i3 = 0; i3 < i2; i3++) {
            this.backing[i + i3] = it.next();
        }
    }

    public final void addAtInternal$1(int i, Object obj) {
        ((java.util.AbstractList) this).modCount++;
        insertAtInternal(i, 1);
        ((E[]) this.backing)[i] = obj;
    }

    public final ListBuilder build() {
        checkIsMutable$1();
        this.isReadOnly = true;
        return this.length > 0 ? this : Empty;
    }

    public final void checkIsMutable$1() {
        if (this.isReadOnly) {
            throw new UnsupportedOperationException();
        }
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final void clear() {
        checkIsMutable$1();
        removeRangeInternal$1(0, this.length);
    }

    @Override // java.util.AbstractList, java.util.Collection, java.util.List
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof List) {
            if (ListBuilderKt.access$subarrayContentEquals(this.backing, 0, this.length, (List) obj)) {
                return true;
            }
        }
        return false;
    }

    @Override // java.util.AbstractList, java.util.List
    public final Object get(int i) {
        AbstractList.Companion companion = kotlin.collections.AbstractList.Companion;
        int i2 = this.length;
        companion.getClass();
        AbstractList.Companion.checkElementIndex$kotlin_stdlib(i, i2);
        return this.backing[i];
    }

    @Override // kotlin.collections.AbstractMutableList
    public final int getSize() {
        return this.length;
    }

    @Override // java.util.AbstractList, java.util.Collection, java.util.List
    public final int hashCode() {
        E[] eArr = this.backing;
        int i = this.length;
        int iHashCode = 1;
        for (int i2 = 0; i2 < i; i2++) {
            E e = eArr[i2];
            iHashCode = (iHashCode * 31) + (e != null ? e.hashCode() : 0);
        }
        return iHashCode;
    }

    @Override // java.util.AbstractList, java.util.List
    public final int indexOf(Object obj) {
        for (int i = 0; i < this.length; i++) {
            if (Intrinsics.areEqual(this.backing[i], obj)) {
                return i;
            }
        }
        return -1;
    }

    public final void insertAtInternal(int i, int i2) {
        int i3 = this.length + i2;
        if (i3 < 0) {
            throw new OutOfMemoryError();
        }
        E[] eArr = this.backing;
        if (i3 > eArr.length) {
            AbstractList.Companion companion = kotlin.collections.AbstractList.Companion;
            int length = eArr.length;
            companion.getClass();
            this.backing = (E[]) Arrays.copyOf(this.backing, AbstractList.Companion.newCapacity$kotlin_stdlib(length, i3));
        }
        E[] eArr2 = this.backing;
        ArraysKt___ArraysJvmKt.copyInto(eArr2, eArr2, i + i2, i, this.length);
        this.length += i2;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean isEmpty() {
        return this.length == 0;
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.List
    public final Iterator iterator() {
        return listIterator(0);
    }

    @Override // java.util.AbstractList, java.util.List
    public final int lastIndexOf(Object obj) {
        for (int i = this.length - 1; i >= 0; i--) {
            if (Intrinsics.areEqual(this.backing[i], obj)) {
                return i;
            }
        }
        return -1;
    }

    @Override // java.util.AbstractList, java.util.List
    public final ListIterator listIterator() {
        return listIterator(0);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean remove(Object obj) {
        checkIsMutable$1();
        int iIndexOf = indexOf(obj);
        if (iIndexOf >= 0) {
            removeAt(iIndexOf);
        }
        return iIndexOf >= 0;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean removeAll(Collection collection) {
        checkIsMutable$1();
        return retainOrRemoveAllInternal$1(false, collection, 0, this.length) > 0;
    }

    @Override // kotlin.collections.AbstractMutableList
    public final Object removeAt(int i) {
        checkIsMutable$1();
        AbstractList.Companion companion = kotlin.collections.AbstractList.Companion;
        int i2 = this.length;
        companion.getClass();
        AbstractList.Companion.checkElementIndex$kotlin_stdlib(i, i2);
        return removeAtInternal$1(i);
    }

    public final Object removeAtInternal$1(int i) {
        ((java.util.AbstractList) this).modCount++;
        E[] eArr = this.backing;
        E e = eArr[i];
        ArraysKt___ArraysJvmKt.copyInto(eArr, eArr, i, i + 1, this.length);
        E[] eArr2 = this.backing;
        int i2 = this.length;
        eArr2[i2 - 1] = null;
        this.length = i2 - 1;
        return e;
    }

    public final void removeRangeInternal$1(int i, int i2) {
        if (i2 > 0) {
            ((java.util.AbstractList) this).modCount++;
        }
        E[] eArr = this.backing;
        ArraysKt___ArraysJvmKt.copyInto(eArr, eArr, i, i + i2, this.length);
        E[] eArr2 = this.backing;
        int i3 = this.length;
        ListBuilderKt.resetRange(i3 - i2, i3, eArr2);
        this.length -= i2;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean retainAll(Collection collection) {
        checkIsMutable$1();
        return retainOrRemoveAllInternal$1(true, collection, 0, this.length) > 0;
    }

    public final int retainOrRemoveAllInternal$1(boolean z, Collection collection, int i, int i2) {
        int i3 = 0;
        int i4 = 0;
        while (i3 < i2) {
            int i5 = i + i3;
            if (collection.contains(this.backing[i5]) == z) {
                E[] eArr = this.backing;
                i3++;
                eArr[i4 + i] = eArr[i5];
                i4++;
            } else {
                i3++;
            }
        }
        int i6 = i2 - i4;
        E[] eArr2 = this.backing;
        ArraysKt___ArraysJvmKt.copyInto(eArr2, eArr2, i + i4, i2 + i, this.length);
        E[] eArr3 = this.backing;
        int i7 = this.length;
        ListBuilderKt.resetRange(i7 - i6, i7, eArr3);
        if (i6 > 0) {
            ((java.util.AbstractList) this).modCount++;
        }
        this.length -= i6;
        return i6;
    }

    @Override // java.util.AbstractList, java.util.List
    public final Object set(int i, Object obj) {
        checkIsMutable$1();
        AbstractList.Companion companion = kotlin.collections.AbstractList.Companion;
        int i2 = this.length;
        companion.getClass();
        AbstractList.Companion.checkElementIndex$kotlin_stdlib(i, i2);
        Object[] objArr = this.backing;
        Object obj2 = objArr[i];
        objArr[i] = obj;
        return obj2;
    }

    @Override // java.util.AbstractList, java.util.List
    public final List subList(int i, int i2) {
        AbstractList.Companion companion = kotlin.collections.AbstractList.Companion;
        int i3 = this.length;
        companion.getClass();
        AbstractList.Companion.checkRangeIndexes$kotlin_stdlib(i, i2, i3);
        return new BuilderSubList(this.backing, i, i2 - i, null, this);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final Object[] toArray(Object[] objArr) {
        int length = objArr.length;
        int i = this.length;
        if (length < i) {
            return Arrays.copyOfRange(this.backing, 0, i, objArr.getClass());
        }
        ArraysKt___ArraysJvmKt.copyInto(this.backing, objArr, 0, 0, i);
        int i2 = this.length;
        if (i2 < objArr.length) {
            objArr[i2] = null;
        }
        return objArr;
    }

    @Override // java.util.AbstractCollection
    public final String toString() {
        return ListBuilderKt.access$subarrayContentToString(this.backing, 0, this.length, this);
    }

    public ListBuilder(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("capacity must be non-negative.");
        }
        this.backing = (E[]) new Object[i];
    }

    @Override // java.util.AbstractList, java.util.List
    public final ListIterator listIterator(int i) {
        AbstractList.Companion companion = kotlin.collections.AbstractList.Companion;
        int i2 = this.length;
        companion.getClass();
        AbstractList.Companion.checkPositionIndex$kotlin_stdlib(i, i2);
        return new Itr(this, i);
    }

    @Override // java.util.AbstractList, java.util.List
    public final void add(int i, Object obj) {
        checkIsMutable$1();
        AbstractList.Companion companion = kotlin.collections.AbstractList.Companion;
        int i2 = this.length;
        companion.getClass();
        AbstractList.Companion.checkPositionIndex$kotlin_stdlib(i, i2);
        addAtInternal$1(i, obj);
    }

    @Override // java.util.AbstractList, java.util.List
    public final boolean addAll(int i, Collection collection) {
        checkIsMutable$1();
        AbstractList.Companion companion = kotlin.collections.AbstractList.Companion;
        int i2 = this.length;
        companion.getClass();
        AbstractList.Companion.checkPositionIndex$kotlin_stdlib(i, i2);
        int size = collection.size();
        addAllInternal$1(i, collection, size);
        return size > 0;
    }

    public /* synthetic */ ListBuilder(int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 10 : i);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final Object[] toArray() {
        E[] eArr = this.backing;
        int i = this.length;
        ArraysKt__ArraysJVMKt.copyOfRangeToIndexCheck(i, eArr.length);
        return Arrays.copyOfRange(eArr, 0, i);
    }
}
