package androidx.collection;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import kotlin.jvm.internal.markers.KMutableSet;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class MutableOrderedSetWrapper extends OrderedSetWrapper implements Set, KMutableSet {
    public final MutableOrderedScatterSet parent;

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
            int findAbsoluteInsertIndex = mutableOrderedScatterSet.findAbsoluteInsertIndex(obj);
            mutableOrderedScatterSet.elements[findAbsoluteInsertIndex] = obj;
            long[] jArr = mutableOrderedScatterSet.nodes;
            int i2 = mutableOrderedScatterSet.head;
            jArr[findAbsoluteInsertIndex] = (i2 & 2147483647L) | 4611686016279904256L;
            if (i2 != Integer.MAX_VALUE) {
                jArr[i2] = ((findAbsoluteInsertIndex & 2147483647L) << 31) | (jArr[i2] & (-4611686016279904257L));
            }
            mutableOrderedScatterSet.head = findAbsoluteInsertIndex;
            if (mutableOrderedScatterSet.tail == Integer.MAX_VALUE) {
                mutableOrderedScatterSet.tail = findAbsoluteInsertIndex;
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
        return new MutableOrderedSetWrapper$iterator$1(this);
    }

    @Override // androidx.collection.OrderedSetWrapper, java.util.Set, java.util.Collection
    public final boolean remove(Object obj) {
        return this.parent.remove(obj);
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x007d, code lost:
    
        r18 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0086, code lost:
    
        if (((r9 & ((~r9) << 6)) & (-9187201950435737472L)) == 0) goto L22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0088, code lost:
    
        r15 = -1;
     */
    @Override // androidx.collection.OrderedSetWrapper, java.util.Set, java.util.Collection
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean removeAll(java.util.Collection r19) {
        /*
            r18 = this;
            r0 = r18
            androidx.collection.MutableOrderedScatterSet r0 = r0.parent
            r1 = r19
            java.lang.Iterable r1 = (java.lang.Iterable) r1
            int r2 = r0._size
            java.util.Iterator r1 = r1.iterator()
        Le:
            boolean r3 = r1.hasNext()
            r4 = 1
            r5 = 0
            if (r3 == 0) goto L97
            java.lang.Object r3 = r1.next()
            if (r3 == 0) goto L21
            int r6 = r3.hashCode()
            goto L22
        L21:
            r6 = r5
        L22:
            r7 = -862048943(0xffffffffcc9e2d51, float:-8.293031E7)
            int r6 = r6 * r7
            int r7 = r6 << 16
            r6 = r6 ^ r7
            r7 = r6 & 127(0x7f, float:1.78E-43)
            int r8 = r0._capacity
            int r6 = r6 >>> 7
            r6 = r6 & r8
        L30:
            long[] r9 = r0.metadata
            int r10 = r6 >> 3
            r11 = r6 & 7
            int r11 = r11 << 3
            r12 = r9[r10]
            long r12 = r12 >>> r11
            int r10 = r10 + r4
            r9 = r9[r10]
            int r14 = 64 - r11
            long r9 = r9 << r14
            long r14 = (long) r11
            long r14 = -r14
            r11 = 63
            long r14 = r14 >> r11
            long r9 = r9 & r14
            long r9 = r9 | r12
            long r11 = (long) r7
            r13 = 72340172838076673(0x101010101010101, double:7.748604185489348E-304)
            long r11 = r11 * r13
            long r11 = r11 ^ r9
            long r13 = r11 - r13
            long r11 = ~r11
            long r11 = r11 & r13
            r13 = -9187201950435737472(0x8080808080808080, double:-2.937446524422997E-306)
            long r11 = r11 & r13
        L5a:
            r15 = 0
            int r17 = (r11 > r15 ? 1 : (r11 == r15 ? 0 : -1))
            if (r17 == 0) goto L7d
            int r15 = java.lang.Long.numberOfTrailingZeros(r11)
            int r15 = r15 >> 3
            int r15 = r15 + r6
            r15 = r15 & r8
            r18 = r4
            java.lang.Object[] r4 = r0.elements
            r4 = r4[r15]
            boolean r4 = kotlin.jvm.internal.Intrinsics.areEqual(r4, r3)
            if (r4 == 0) goto L75
            goto L89
        L75:
            r15 = 1
            long r15 = r11 - r15
            long r11 = r11 & r15
            r4 = r18
            goto L5a
        L7d:
            r18 = r4
            long r11 = ~r9
            r4 = 6
            long r11 = r11 << r4
            long r9 = r9 & r11
            long r9 = r9 & r13
            int r4 = (r9 > r15 ? 1 : (r9 == r15 ? 0 : -1))
            if (r4 == 0) goto L90
            r15 = -1
        L89:
            if (r15 < 0) goto Le
            r0.removeElementAt(r15)
            goto Le
        L90:
            int r5 = r5 + 8
            int r6 = r6 + r5
            r6 = r6 & r8
            r4 = r18
            goto L30
        L97:
            r18 = r4
            int r0 = r0._size
            if (r2 == r0) goto L9e
            return r18
        L9e:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.collection.MutableOrderedSetWrapper.removeAll(java.util.Collection):boolean");
    }

    @Override // androidx.collection.OrderedSetWrapper, java.util.Set, java.util.Collection
    public final boolean retainAll(Collection collection) {
        return this.parent.retainAll(collection);
    }
}
