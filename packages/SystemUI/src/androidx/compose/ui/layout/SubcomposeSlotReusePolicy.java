package androidx.compose.ui.layout;

import androidx.collection.MutableOrderedScatterSet;
import androidx.collection.MutableOrderedSetWrapper;
import androidx.collection.MutableOrderedSetWrapper$iterator$1;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Predicate;
import kotlin.jvm.internal.CollectionToArray;
import kotlin.jvm.internal.markers.KMappedMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface SubcomposeSlotReusePolicy {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class SlotIdsSet implements Collection<Object>, KMappedMarker {
        public final MutableOrderedScatterSet set;

        public SlotIdsSet() {
            this(null, 1, null);
        }

        @Override // java.util.Collection
        public final boolean add(Object obj) {
            return this.set.add(obj);
        }

        @Override // java.util.Collection
        public final boolean addAll(Collection<? extends Object> collection) {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }

        @Override // java.util.Collection
        public final void clear() {
            this.set.clear();
        }

        @Override // java.util.Collection
        public final boolean contains(Object obj) {
            return this.set.contains(obj);
        }

        @Override // java.util.Collection
        public final boolean containsAll(Collection collection) {
            Iterator it = collection.iterator();
            while (it.hasNext()) {
                if (!this.set.contains(it.next())) {
                    return false;
                }
            }
            return true;
        }

        @Override // java.util.Collection
        public final boolean isEmpty() {
            return this.set._size == 0;
        }

        @Override // java.util.Collection, java.lang.Iterable
        public final Iterator iterator() {
            MutableOrderedScatterSet mutableOrderedScatterSet = this.set;
            mutableOrderedScatterSet.getClass();
            return new MutableOrderedSetWrapper$iterator$1(new MutableOrderedSetWrapper(mutableOrderedScatterSet));
        }

        @Override // java.util.Collection
        public final boolean remove(Object obj) {
            return this.set.remove(obj);
        }

        @Override // java.util.Collection
        public final boolean removeAll(Collection collection) {
            return this.set.remove(collection);
        }

        @Override // java.util.Collection
        public final boolean removeIf(Predicate<? super Object> predicate) {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }

        @Override // java.util.Collection
        public final boolean retainAll(Collection collection) {
            return this.set.retainAll(collection);
        }

        @Override // java.util.Collection
        public final int size() {
            return this.set._size;
        }

        @Override // java.util.Collection
        public final Object[] toArray() {
            return CollectionToArray.toArray(this);
        }

        public SlotIdsSet(MutableOrderedScatterSet mutableOrderedScatterSet) {
            this.set = mutableOrderedScatterSet;
        }

        @Override // java.util.Collection
        public final Object[] toArray(Object[] objArr) {
            return CollectionToArray.toArray(this, objArr);
        }

        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public SlotIdsSet(androidx.collection.MutableOrderedScatterSet r2, int r3, kotlin.jvm.internal.DefaultConstructorMarker r4) {
            /*
                r1 = this;
                r4 = 1
                r3 = r3 & r4
                if (r3 == 0) goto Ld
                int r2 = androidx.collection.OrderedScatterSetKt.$r8$clinit
                androidx.collection.MutableOrderedScatterSet r2 = new androidx.collection.MutableOrderedScatterSet
                r3 = 0
                r0 = 0
                r2.<init>(r0, r4, r3)
            Ld:
                r1.<init>(r2)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.layout.SubcomposeSlotReusePolicy.SlotIdsSet.<init>(androidx.collection.MutableOrderedScatterSet, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
        }
    }

    boolean areCompatible(Object obj, Object obj2);

    void getSlotsToRetain(SlotIdsSet slotIdsSet);
}
