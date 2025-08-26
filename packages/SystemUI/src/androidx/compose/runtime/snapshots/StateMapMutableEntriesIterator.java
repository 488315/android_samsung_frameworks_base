package androidx.compose.runtime.snapshots;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import kotlin.jvm.internal.markers.KMappedMarker;
import kotlin.jvm.internal.markers.KMutableMap;

/* loaded from: classes.dex */
final class StateMapMutableEntriesIterator<K, V> extends StateMapMutableIterator<K, V> implements Iterator<Map.Entry<K, V>>, KMappedMarker {

    /* renamed from: androidx.compose.runtime.snapshots.StateMapMutableEntriesIterator$next$1, reason: invalid class name */
    public final class AnonymousClass1 implements Map.Entry<Object, Object>, KMutableMap.Entry {
        public final Object key;
        public final /* synthetic */ StateMapMutableEntriesIterator this$0;
        public Object value;

        public AnonymousClass1(StateMapMutableEntriesIterator<Object, Object> stateMapMutableEntriesIterator) {
            this.this$0 = stateMapMutableEntriesIterator;
            Map.Entry entry = stateMapMutableEntriesIterator.current;
            entry.getClass();
            this.key = entry.getKey();
            Map.Entry entry2 = stateMapMutableEntriesIterator.current;
            entry2.getClass();
            this.value = entry2.getValue();
        }

        @Override // java.util.Map.Entry
        public final Object getKey() {
            return this.key;
        }

        @Override // java.util.Map.Entry
        public final Object getValue() {
            return this.value;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.Map.Entry
        public final Object setValue(Object obj) {
            StateMapMutableEntriesIterator stateMapMutableEntriesIterator = this.this$0;
            if (stateMapMutableEntriesIterator.map.getReadable$runtime_release().modification != stateMapMutableEntriesIterator.modification) {
                throw new ConcurrentModificationException();
            }
            Object obj2 = this.value;
            stateMapMutableEntriesIterator.map.put(this.key, obj);
            this.value = obj;
            return obj2;
        }
    }

    public StateMapMutableEntriesIterator(SnapshotStateMap<K, V> snapshotStateMap, Iterator<? extends Map.Entry<? extends K, ? extends V>> it) {
        super(snapshotStateMap, it);
    }

    @Override // java.util.Iterator
    public final Object next() {
        advance();
        if (this.current != null) {
            return new AnonymousClass1(this);
        }
        throw new IllegalStateException();
    }
}
