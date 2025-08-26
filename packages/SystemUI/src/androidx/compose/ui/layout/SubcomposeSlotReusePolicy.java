package androidx.compose.ui.layout;

import androidx.collection.MutableOrderedScatterSet;
import androidx.collection.MutableOrderedSetWrapper;
import androidx.collection.MutableOrderedSetWrapper.AnonymousClass1;
import androidx.collection.OrderedScatterSetKt;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Predicate;
import kotlin.jvm.internal.CollectionToArray;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.markers.KMappedMarker;

/* loaded from: classes.dex */
public interface SubcomposeSlotReusePolicy {

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
            return new MutableOrderedSetWrapper(mutableOrderedScatterSet).new AnonymousClass1();
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

        public SlotIdsSet(MutableOrderedScatterSet mutableOrderedScatterSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
            int i2 = 1;
            if ((i & 1) != 0) {
                int i3 = OrderedScatterSetKt.$r8$clinit;
                mutableOrderedScatterSet = new MutableOrderedScatterSet(0, i2, null);
            }
            this(mutableOrderedScatterSet);
        }
    }

    boolean areCompatible(Object obj, Object obj2);

    void getSlotsToRetain(SlotIdsSet slotIdsSet);
}
