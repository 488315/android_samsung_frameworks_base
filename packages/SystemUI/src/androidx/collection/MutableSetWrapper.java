package androidx.collection;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.markers.KMutableSet;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class MutableSetWrapper extends SetWrapper implements Set, KMutableSet {
    public final MutableScatterSet parent;

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
        return new MutableSetWrapper$iterator$1(this);
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

    @Override // androidx.collection.SetWrapper, java.util.Set, java.util.Collection
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
                }
                if (i2 == length) {
                    break;
                }
                i2++;
            }
        }
        return i != mutableScatterSet._size;
    }
}
