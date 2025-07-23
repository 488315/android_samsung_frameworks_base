package androidx.compose.runtime.snapshots;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import kotlin.Unit;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
abstract class StateMapMutableIterator<K, V> {
    public Map.Entry current;
    public final Iterator iterator;
    public final SnapshotStateMap map;
    public int modification;
    public Map.Entry next;

    public StateMapMutableIterator(SnapshotStateMap<K, V> snapshotStateMap, Iterator<? extends Map.Entry<? extends K, ? extends V>> it) {
        this.map = snapshotStateMap;
        this.iterator = it;
        this.modification = snapshotStateMap.getReadable$runtime_release().modification;
        advance();
    }

    public final void advance() {
        this.current = this.next;
        this.next = this.iterator.hasNext() ? (Map.Entry) this.iterator.next() : null;
    }

    public final boolean hasNext() {
        return this.next != null;
    }

    public final void remove() {
        SnapshotStateMap snapshotStateMap = this.map;
        if (snapshotStateMap.getReadable$runtime_release().modification != this.modification) {
            throw new ConcurrentModificationException();
        }
        Map.Entry entry = this.current;
        if (entry == null) {
            throw new IllegalStateException();
        }
        snapshotStateMap.remove(entry.getKey());
        this.current = null;
        Unit unit = Unit.INSTANCE;
        this.modification = snapshotStateMap.getReadable$runtime_release().modification;
    }
}
