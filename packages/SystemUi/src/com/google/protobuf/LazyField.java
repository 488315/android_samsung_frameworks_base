package com.google.protobuf;

import java.util.Iterator;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class LazyField extends LazyFieldLite {
    public final MessageLite defaultInstance;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class LazyEntry implements Map.Entry {
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
            return lazyField.getValue();
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class LazyIterator implements Iterator {
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

    @Override // com.google.protobuf.LazyFieldLite
    public final boolean equals(Object obj) {
        return getValue().equals(obj);
    }

    public final MessageLite getValue() {
        return getValue(this.defaultInstance);
    }

    @Override // com.google.protobuf.LazyFieldLite
    public final int hashCode() {
        return getValue().hashCode();
    }

    public final String toString() {
        return getValue().toString();
    }
}
