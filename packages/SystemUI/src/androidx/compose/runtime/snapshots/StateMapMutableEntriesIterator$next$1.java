package androidx.compose.runtime.snapshots;

import java.util.ConcurrentModificationException;
import java.util.Map;
import kotlin.jvm.internal.markers.KMutableMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class StateMapMutableEntriesIterator$next$1 implements Map.Entry<Object, Object>, KMutableMap.Entry {
    public final Object key;
    public final /* synthetic */ StateMapMutableEntriesIterator this$0;
    public Object value;

    public StateMapMutableEntriesIterator$next$1(StateMapMutableEntriesIterator<Object, Object> stateMapMutableEntriesIterator) {
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
