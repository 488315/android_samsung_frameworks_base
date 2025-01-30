package androidx.arch.core.internal;

import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class SafeIterableMap implements Iterable {
    public Entry mEnd;
    public final WeakHashMap mIterators = new WeakHashMap();
    public int mSize = 0;
    public Entry mStart;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class AscendingIterator extends ListIterator {
        public AscendingIterator(Entry entry, Entry entry2) {
            super(entry, entry2);
        }

        @Override // androidx.arch.core.internal.SafeIterableMap.ListIterator
        public final Entry backward(Entry entry) {
            return entry.mPrevious;
        }

        @Override // androidx.arch.core.internal.SafeIterableMap.ListIterator
        public final Entry forward(Entry entry) {
            return entry.mNext;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class DescendingIterator extends ListIterator {
        public DescendingIterator(Entry entry, Entry entry2) {
            super(entry, entry2);
        }

        @Override // androidx.arch.core.internal.SafeIterableMap.ListIterator
        public final Entry backward(Entry entry) {
            return entry.mNext;
        }

        @Override // androidx.arch.core.internal.SafeIterableMap.ListIterator
        public final Entry forward(Entry entry) {
            return entry.mPrevious;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Entry implements Map.Entry {
        public final Object mKey;
        public Entry mNext;
        public Entry mPrevious;
        public final Object mValue;

        public Entry(Object obj, Object obj2) {
            this.mKey = obj;
            this.mValue = obj2;
        }

        @Override // java.util.Map.Entry
        public final boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Entry)) {
                return false;
            }
            Entry entry = (Entry) obj;
            return this.mKey.equals(entry.mKey) && this.mValue.equals(entry.mValue);
        }

        @Override // java.util.Map.Entry
        public final Object getKey() {
            return this.mKey;
        }

        @Override // java.util.Map.Entry
        public final Object getValue() {
            return this.mValue;
        }

        @Override // java.util.Map.Entry
        public final int hashCode() {
            return this.mValue.hashCode() ^ this.mKey.hashCode();
        }

        @Override // java.util.Map.Entry
        public final Object setValue(Object obj) {
            throw new UnsupportedOperationException("An entry modification is not supported");
        }

        public final String toString() {
            return this.mKey + "=" + this.mValue;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class IteratorWithAdditions extends SupportRemove implements Iterator {
        public boolean mBeforeStart = true;
        public Entry mCurrent;

        public IteratorWithAdditions() {
        }

        @Override // java.util.Iterator
        public final boolean hasNext() {
            if (this.mBeforeStart) {
                return SafeIterableMap.this.mStart != null;
            }
            Entry entry = this.mCurrent;
            return (entry == null || entry.mNext == null) ? false : true;
        }

        @Override // java.util.Iterator
        public final Object next() {
            if (this.mBeforeStart) {
                this.mBeforeStart = false;
                this.mCurrent = SafeIterableMap.this.mStart;
            } else {
                Entry entry = this.mCurrent;
                this.mCurrent = entry != null ? entry.mNext : null;
            }
            return this.mCurrent;
        }

        @Override // androidx.arch.core.internal.SafeIterableMap.SupportRemove
        public final void supportRemove(Entry entry) {
            Entry entry2 = this.mCurrent;
            if (entry == entry2) {
                Entry entry3 = entry2.mPrevious;
                this.mCurrent = entry3;
                this.mBeforeStart = entry3 == null;
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class ListIterator extends SupportRemove implements Iterator {
        public Entry mExpectedEnd;
        public Entry mNext;

        public ListIterator(Entry entry, Entry entry2) {
            this.mExpectedEnd = entry2;
            this.mNext = entry;
        }

        public abstract Entry backward(Entry entry);

        public abstract Entry forward(Entry entry);

        @Override // java.util.Iterator
        public final boolean hasNext() {
            return this.mNext != null;
        }

        @Override // java.util.Iterator
        public final Object next() {
            Entry entry = this.mNext;
            Entry entry2 = this.mExpectedEnd;
            this.mNext = (entry == entry2 || entry2 == null) ? null : forward(entry);
            return entry;
        }

        @Override // androidx.arch.core.internal.SafeIterableMap.SupportRemove
        public final void supportRemove(Entry entry) {
            Entry entry2 = null;
            if (this.mExpectedEnd == entry && entry == this.mNext) {
                this.mNext = null;
                this.mExpectedEnd = null;
            }
            Entry entry3 = this.mExpectedEnd;
            if (entry3 == entry) {
                this.mExpectedEnd = backward(entry3);
            }
            Entry entry4 = this.mNext;
            if (entry4 == entry) {
                Entry entry5 = this.mExpectedEnd;
                if (entry4 != entry5 && entry5 != null) {
                    entry2 = forward(entry4);
                }
                this.mNext = entry2;
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class SupportRemove {
        public abstract void supportRemove(Entry entry);
    }

    /* JADX WARN: Code restructure failed: missing block: B:31:0x0048, code lost:
    
        if (r1.hasNext() != false) goto L28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0050, code lost:
    
        if (((androidx.arch.core.internal.SafeIterableMap.ListIterator) r6).hasNext() != false) goto L28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:?, code lost:
    
        return true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0054, code lost:
    
        return false;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SafeIterableMap)) {
            return false;
        }
        SafeIterableMap safeIterableMap = (SafeIterableMap) obj;
        if (this.mSize != safeIterableMap.mSize) {
            return false;
        }
        Iterator it = iterator();
        Iterator it2 = safeIterableMap.iterator();
        while (true) {
            ListIterator listIterator = (ListIterator) it;
            if (!listIterator.hasNext()) {
                break;
            }
            ListIterator listIterator2 = (ListIterator) it2;
            if (!listIterator2.hasNext()) {
                break;
            }
            Map.Entry entry = (Map.Entry) listIterator.next();
            Object next = listIterator2.next();
            if ((entry != null || next == null) && (entry == null || entry.equals(next))) {
            }
        }
        return false;
    }

    public Entry get(Object obj) {
        Entry entry = this.mStart;
        while (entry != null && !entry.mKey.equals(obj)) {
            entry = entry.mNext;
        }
        return entry;
    }

    public final int hashCode() {
        Iterator it = iterator();
        int i = 0;
        while (true) {
            ListIterator listIterator = (ListIterator) it;
            if (!listIterator.hasNext()) {
                return i;
            }
            i += ((Map.Entry) listIterator.next()).hashCode();
        }
    }

    @Override // java.lang.Iterable
    public final Iterator iterator() {
        AscendingIterator ascendingIterator = new AscendingIterator(this.mStart, this.mEnd);
        this.mIterators.put(ascendingIterator, Boolean.FALSE);
        return ascendingIterator;
    }

    public Object putIfAbsent(Object obj, Object obj2) {
        Entry entry = get(obj);
        if (entry != null) {
            return entry.mValue;
        }
        Entry entry2 = new Entry(obj, obj2);
        this.mSize++;
        Entry entry3 = this.mEnd;
        if (entry3 == null) {
            this.mStart = entry2;
            this.mEnd = entry2;
            return null;
        }
        entry3.mNext = entry2;
        entry2.mPrevious = entry3;
        this.mEnd = entry2;
        return null;
    }

    public Object remove(Object obj) {
        Entry entry = get(obj);
        if (entry == null) {
            return null;
        }
        this.mSize--;
        if (!this.mIterators.isEmpty()) {
            Iterator it = this.mIterators.keySet().iterator();
            while (it.hasNext()) {
                ((SupportRemove) it.next()).supportRemove(entry);
            }
        }
        Entry entry2 = entry.mPrevious;
        if (entry2 != null) {
            entry2.mNext = entry.mNext;
        } else {
            this.mStart = entry.mNext;
        }
        Entry entry3 = entry.mNext;
        if (entry3 != null) {
            entry3.mPrevious = entry2;
        } else {
            this.mEnd = entry2;
        }
        entry.mNext = null;
        entry.mPrevious = null;
        return entry.mValue;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("[");
        Iterator it = iterator();
        while (true) {
            ListIterator listIterator = (ListIterator) it;
            if (!listIterator.hasNext()) {
                sb.append("]");
                return sb.toString();
            }
            sb.append(((Map.Entry) listIterator.next()).toString());
            if (listIterator.hasNext()) {
                sb.append(", ");
            }
        }
    }
}
