package androidx.datastore.preferences.protobuf;

import java.util.Iterator;
import java.util.Map;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class LazyField extends LazyFieldLite {
    public final MessageLite defaultInstance;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class LazyEntry implements Map.Entry {
        public final Map.Entry entry;

        @Override // java.util.Map.Entry
        public final Object getKey() {
            return this.entry.getKey();
        }

        @Override // java.util.Map.Entry
        public final Object getValue() {
            LazyField lazyField = (LazyField) this.entry.getValue();
            if (lazyField == null) {
                return null;
            }
            return lazyField.getValue(lazyField.defaultInstance);
        }

        @Override // java.util.Map.Entry
        public final Object setValue(Object obj) {
            if (!(obj instanceof MessageLite)) {
                throw new IllegalArgumentException("LazyField now only used for MessageSet, and the value of MessageSet must be an instance of MessageLite");
            }
            LazyField lazyField = (LazyField) this.entry.getValue();
            MessageLite messageLite = lazyField.value;
            lazyField.delayedBytes = null;
            lazyField.memoizedBytes = null;
            lazyField.value = (MessageLite) obj;
            return messageLite;
        }

        private LazyEntry(Map.Entry<Object, LazyField> entry) {
            this.entry = entry;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class LazyIterator implements Iterator {
        public final Iterator iterator;

        public LazyIterator(Iterator<Map.Entry<Object, Object>> it) {
            this.iterator = it;
        }

        @Override // java.util.Iterator
        public final boolean hasNext() {
            return this.iterator.hasNext();
        }

        @Override // java.util.Iterator
        public final Object next() {
            Map.Entry entry = (Map.Entry) this.iterator.next();
            return entry.getValue() instanceof LazyField ? new LazyEntry(entry) : entry;
        }

        @Override // java.util.Iterator
        public final void remove() {
            this.iterator.remove();
        }
    }

    public LazyField(MessageLite messageLite, ExtensionRegistryLite extensionRegistryLite, ByteString byteString) {
        super(extensionRegistryLite, byteString);
        this.defaultInstance = messageLite;
    }

    @Override // androidx.datastore.preferences.protobuf.LazyFieldLite
    public final boolean equals(Object obj) {
        return getValue(this.defaultInstance).equals(obj);
    }

    @Override // androidx.datastore.preferences.protobuf.LazyFieldLite
    public final int hashCode() {
        return getValue(this.defaultInstance).hashCode();
    }

    public final String toString() {
        return getValue(this.defaultInstance).toString();
    }
}
