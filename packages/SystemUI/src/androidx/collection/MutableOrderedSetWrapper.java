package androidx.collection;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import kotlin.jvm.internal.markers.KMutableSet;
import kotlin.sequences.SequenceBuilderIterator;
import kotlin.sequences.SequencesKt__SequenceBuilderKt;

/* loaded from: classes.dex */
public final class MutableOrderedSetWrapper extends OrderedSetWrapper implements Set, KMutableSet {
    public final MutableOrderedScatterSet parent;

    /* renamed from: androidx.collection.MutableOrderedSetWrapper$iterator$1, reason: invalid class name */
    public final class AnonymousClass1 implements Iterator, KMappedMarker {
        public int current = -1;
        public final SequenceBuilderIterator iterator;

        public AnonymousClass1() {
            this.iterator = SequencesKt__SequenceBuilderKt.iterator(new MutableOrderedSetWrapper$iterator$1$iterator$1(MutableOrderedSetWrapper.this, this, null));
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
                MutableOrderedSetWrapper.this.parent.removeElementAt(i);
                this.current = -1;
            }
        }
    }

    public MutableOrderedSetWrapper(MutableOrderedScatterSet mutableOrderedScatterSet) {
        super(mutableOrderedScatterSet);
        this.parent = mutableOrderedScatterSet;
    }

    @Override // androidx.collection.OrderedSetWrapper, java.util.Set, java.util.Collection
    public final boolean add(Object obj) {
        return this.parent.add(obj);
    }

    @Override // androidx.collection.OrderedSetWrapper, java.util.Set, java.util.Collection
    public final boolean addAll(Collection collection) {
        MutableOrderedScatterSet mutableOrderedScatterSet = this.parent;
        int i = mutableOrderedScatterSet._size;
        for (Object obj : collection) {
            int iFindAbsoluteInsertIndex = mutableOrderedScatterSet.findAbsoluteInsertIndex(obj);
            mutableOrderedScatterSet.elements[iFindAbsoluteInsertIndex] = obj;
            long[] jArr = mutableOrderedScatterSet.nodes;
            int i2 = mutableOrderedScatterSet.head;
            jArr[iFindAbsoluteInsertIndex] = (i2 & 2147483647L) | 4611686016279904256L;
            if (i2 != Integer.MAX_VALUE) {
                jArr[i2] = ((iFindAbsoluteInsertIndex & 2147483647L) << 31) | (jArr[i2] & (-4611686016279904257L));
            }
            mutableOrderedScatterSet.head = iFindAbsoluteInsertIndex;
            if (mutableOrderedScatterSet.tail == Integer.MAX_VALUE) {
                mutableOrderedScatterSet.tail = iFindAbsoluteInsertIndex;
            }
        }
        return i != mutableOrderedScatterSet._size;
    }

    @Override // androidx.collection.OrderedSetWrapper, java.util.Set, java.util.Collection
    public final void clear() {
        this.parent.clear();
    }

    @Override // androidx.collection.OrderedSetWrapper, java.util.Set, java.util.Collection, java.lang.Iterable
    public final Iterator iterator() {
        return new AnonymousClass1();
    }

    @Override // androidx.collection.OrderedSetWrapper, java.util.Set, java.util.Collection
    public final boolean remove(Object obj) {
        return this.parent.remove(obj);
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x007d, code lost:
    
        r18 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0086, code lost:
    
        if (((r9 & ((~r9) << 6)) & (-9187201950435737472L)) == 0) goto L22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0088, code lost:
    
        r15 = -1;
     */
    @Override // androidx.collection.OrderedSetWrapper, java.util.Set, java.util.Collection
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean removeAll(Collection collection) {
        int i;
        int iNumberOfTrailingZeros;
        MutableOrderedScatterSet mutableOrderedScatterSet = this.parent;
        int i2 = mutableOrderedScatterSet._size;
        Iterator it = collection.iterator();
        while (true) {
            int i3 = 1;
            int i4 = 0;
            if (!it.hasNext()) {
                break;
            }
            Object next = it.next();
            int iHashCode = (next != null ? next.hashCode() : 0) * (-862048943);
            int i5 = iHashCode ^ (iHashCode << 16);
            int i6 = i5 & 127;
            int i7 = mutableOrderedScatterSet._capacity;
            int i8 = (i5 >>> 7) & i7;
            while (true) {
                long[] jArr = mutableOrderedScatterSet.metadata;
                int i9 = i8 >> 3;
                int i10 = (i8 & 7) << 3;
                long j = ((jArr[i9 + i3] << (64 - i10)) & ((-i10) >> 63)) | (jArr[i9] >>> i10);
                long j2 = (i6 * 72340172838076673L) ^ j;
                long j3 = (~j2) & (j2 - 72340172838076673L) & (-9187201950435737472L);
                while (true) {
                    if (j3 == 0) {
                        break;
                    }
                    iNumberOfTrailingZeros = ((Long.numberOfTrailingZeros(j3) >> 3) + i8) & i7;
                    int i11 = i3;
                    if (Intrinsics.areEqual(mutableOrderedScatterSet.elements[iNumberOfTrailingZeros], next)) {
                        break;
                    }
                    j3 &= j3 - 1;
                    i3 = i11;
                }
                i4 += 8;
                i8 = (i8 + i4) & i7;
                i3 = i;
            }
            if (iNumberOfTrailingZeros >= 0) {
                mutableOrderedScatterSet.removeElementAt(iNumberOfTrailingZeros);
            }
        }
        return i2 != mutableOrderedScatterSet._size;
    }

    @Override // androidx.collection.OrderedSetWrapper, java.util.Set, java.util.Collection
    public final boolean retainAll(Collection collection) {
        return this.parent.retainAll(collection);
    }
}
