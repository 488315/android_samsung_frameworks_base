package androidx.collection;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.markers.KMappedMarker;
import kotlin.jvm.internal.markers.KMutableSet;
import kotlin.sequences.SequenceBuilderIterator;
import kotlin.sequences.SequencesKt__SequenceBuilderKt;

/* loaded from: classes.dex */
public final class MutableSetWrapper extends SetWrapper implements Set, KMutableSet {
    public final MutableScatterSet parent;

    /* renamed from: androidx.collection.MutableSetWrapper$iterator$1, reason: invalid class name */
    public final class AnonymousClass1 implements Iterator, KMappedMarker {
        public int current = -1;
        public final SequenceBuilderIterator iterator;

        public AnonymousClass1() {
            this.iterator = SequencesKt__SequenceBuilderKt.iterator(new MutableSetWrapper$iterator$1$iterator$1(MutableSetWrapper.this, this, null));
        }

        @Override // java.util.Iterator
        public final boolean hasNext() {
            return this.iterator.hasNext();
        }

        @Override // java.util.Iterator
        public final Object next() {
            return this.iterator.next();
        }

        @Override // java.util.Iterator
        public final void remove() {
            int i = this.current;
            if (i != -1) {
                MutableSetWrapper.this.parent.removeElementAt(i);
                this.current = -1;
            }
        }
    }

    public MutableSetWrapper(MutableScatterSet mutableScatterSet) {
        super(mutableScatterSet);
        this.parent = mutableScatterSet;
    }

    @Override // androidx.collection.SetWrapper, java.util.Set, java.util.Collection
    public final boolean add(Object obj) {
        return this.parent.add(obj);
    }

    @Override // androidx.collection.SetWrapper, java.util.Set, java.util.Collection
    public final boolean addAll(Collection collection) {
        MutableScatterSet mutableScatterSet = this.parent;
        int i = mutableScatterSet._size;
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            mutableScatterSet.plusAssign(it.next());
        }
        return i != mutableScatterSet._size;
    }

    @Override // androidx.collection.SetWrapper, java.util.Set, java.util.Collection
    public final void clear() {
        this.parent.clear();
    }

    @Override // androidx.collection.SetWrapper, java.util.Set, java.util.Collection, java.lang.Iterable
    public final Iterator iterator() {
        return new AnonymousClass1();
    }

    @Override // androidx.collection.SetWrapper, java.util.Set, java.util.Collection
    public final boolean remove(Object obj) {
        return this.parent.remove(obj);
    }

    @Override // androidx.collection.SetWrapper, java.util.Set, java.util.Collection
    public final boolean removeAll(Collection collection) {
        MutableScatterSet mutableScatterSet = this.parent;
        int i = mutableScatterSet._size;
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            mutableScatterSet.minusAssign(it.next());
        }
        return i != mutableScatterSet._size;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x004e  */
    @Override // androidx.collection.SetWrapper, java.util.Set, java.util.Collection
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean retainAll(Collection collection) {
        MutableScatterSet mutableScatterSet = this.parent;
        Object[] objArr = mutableScatterSet.elements;
        int i = mutableScatterSet._size;
        long[] jArr = mutableScatterSet.metadata;
        int length = jArr.length - 2;
        if (length >= 0) {
            int i2 = 0;
            while (true) {
                long j = jArr[i2];
                if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                    int i3 = 8 - ((~(i2 - length)) >>> 31);
                    for (int i4 = 0; i4 < i3; i4++) {
                        if ((255 & j) < 128) {
                            int i5 = (i2 << 3) + i4;
                            if (!CollectionsKt___CollectionsKt.contains(collection, objArr[i5])) {
                                mutableScatterSet.removeElementAt(i5);
                            }
                        }
                        j >>= 8;
                    }
                    if (i3 != 8) {
                        break;
                    }
                    if (i2 == length) {
                        break;
                    }
                    i2++;
                }
            }
        }
        return i != mutableScatterSet._size;
    }
}
