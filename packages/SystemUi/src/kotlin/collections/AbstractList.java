package kotlin.collections;

import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0;
import com.android.systemui.AbstractC0970x34f4116a;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.RandomAccess;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class AbstractList extends AbstractCollection implements List {
    public static final Companion Companion = new Companion(null);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static void checkElementIndex$kotlin_stdlib(int i, int i2) {
            if (i < 0 || i >= i2) {
                throw new IndexOutOfBoundsException(AbstractC0970x34f4116a.m94m("index: ", i, ", size: ", i2));
            }
        }

        public static void checkPositionIndex$kotlin_stdlib(int i, int i2) {
            if (i < 0 || i > i2) {
                throw new IndexOutOfBoundsException(AbstractC0970x34f4116a.m94m("index: ", i, ", size: ", i2));
            }
        }

        public static void checkRangeIndexes$kotlin_stdlib(int i, int i2, int i3) {
            if (i < 0 || i2 > i3) {
                StringBuilder m45m = GridLayoutManager$$ExternalSyntheticOutline0.m45m("fromIndex: ", i, ", toIndex: ", i2, ", size: ");
                m45m.append(i3);
                throw new IndexOutOfBoundsException(m45m.toString());
            }
            if (i > i2) {
                throw new IllegalArgumentException(AbstractC0970x34f4116a.m94m("fromIndex: ", i, " > toIndex: ", i2));
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class IteratorImpl implements Iterator, KMappedMarker {
        public int index;

        public IteratorImpl() {
        }

        @Override // java.util.Iterator
        public final boolean hasNext() {
            return this.index < AbstractList.this.getSize();
        }

        @Override // java.util.Iterator
        public final Object next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            AbstractList abstractList = AbstractList.this;
            int i = this.index;
            this.index = i + 1;
            return abstractList.get(i);
        }

        @Override // java.util.Iterator
        public final void remove() {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ListIteratorImpl extends IteratorImpl implements ListIterator {
        public ListIteratorImpl(int i) {
            super();
            Companion companion = AbstractList.Companion;
            int size = AbstractList.this.getSize();
            companion.getClass();
            Companion.checkPositionIndex$kotlin_stdlib(i, size);
            this.index = i;
        }

        @Override // java.util.ListIterator
        public final void add(Object obj) {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }

        @Override // java.util.ListIterator
        public final boolean hasPrevious() {
            return this.index > 0;
        }

        @Override // java.util.ListIterator
        public final int nextIndex() {
            return this.index;
        }

        @Override // java.util.ListIterator
        public final Object previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            AbstractList abstractList = AbstractList.this;
            int i = this.index - 1;
            this.index = i;
            return abstractList.get(i);
        }

        @Override // java.util.ListIterator
        public final int previousIndex() {
            return this.index - 1;
        }

        @Override // java.util.ListIterator
        public final void set(Object obj) {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SubList extends AbstractList implements RandomAccess {
        public final int _size;
        public final int fromIndex;
        public final AbstractList list;

        public SubList(AbstractList abstractList, int i, int i2) {
            this.list = abstractList;
            this.fromIndex = i;
            Companion companion = AbstractList.Companion;
            int size = abstractList.getSize();
            companion.getClass();
            Companion.checkRangeIndexes$kotlin_stdlib(i, i2, size);
            this._size = i2 - i;
        }

        @Override // java.util.List
        public final Object get(int i) {
            Companion companion = AbstractList.Companion;
            int i2 = this._size;
            companion.getClass();
            Companion.checkElementIndex$kotlin_stdlib(i, i2);
            return this.list.get(this.fromIndex + i);
        }

        @Override // kotlin.collections.AbstractCollection
        public final int getSize() {
            return this._size;
        }
    }

    @Override // java.util.List
    public final void add(int i, Object obj) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.List
    public final boolean addAll(int i, Collection collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Collection, java.util.List
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof List)) {
            return false;
        }
        Collection collection = (Collection) obj;
        Companion.getClass();
        if (size() == collection.size()) {
            Iterator it = collection.iterator();
            Iterator<E> it2 = iterator();
            while (it2.hasNext()) {
                if (!Intrinsics.areEqual(it2.next(), it.next())) {
                }
            }
            return true;
        }
        return false;
    }

    @Override // java.util.Collection, java.util.List
    public final int hashCode() {
        Companion.getClass();
        Iterator<E> it = iterator();
        int i = 1;
        while (it.hasNext()) {
            Object next = it.next();
            i = (i * 31) + (next != null ? next.hashCode() : 0);
        }
        return i;
    }

    @Override // java.util.List
    public int indexOf(Object obj) {
        Iterator it = iterator();
        int i = 0;
        while (it.hasNext()) {
            if (Intrinsics.areEqual(it.next(), obj)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    @Override // java.util.Collection, java.lang.Iterable, java.util.List
    public Iterator iterator() {
        return new IteratorImpl();
    }

    @Override // java.util.List
    public int lastIndexOf(Object obj) {
        ListIterator listIterator = listIterator(size());
        while (listIterator.hasPrevious()) {
            if (Intrinsics.areEqual(listIterator.previous(), obj)) {
                return listIterator.nextIndex();
            }
        }
        return -1;
    }

    @Override // java.util.List
    public final ListIterator listIterator() {
        return new ListIteratorImpl(0);
    }

    @Override // java.util.List
    public final Object remove(int i) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.List
    public final Object set(int i, Object obj) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.List
    public final List subList(int i, int i2) {
        return new SubList(this, i, i2);
    }

    @Override // java.util.List
    public final ListIterator listIterator(int i) {
        return new ListIteratorImpl(i);
    }
}
