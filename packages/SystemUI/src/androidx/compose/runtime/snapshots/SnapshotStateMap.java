package androidx.compose.runtime.snapshots;

import androidx.compose.runtime.external.kotlinx.collections.immutable.PersistentMap;
import androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableMap.PersistentHashMap;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMutableMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SnapshotStateMap<K, V> implements StateObject, Map<K, V>, KMutableMap {
    public final Set entries;
    public StateMapStateRecord firstStateRecord;
    public final Set keys;
    public final Collection values;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class StateMapStateRecord<K, V> extends StateRecord {
        public PersistentMap map;
        public int modification;

        public StateMapStateRecord(long j, PersistentMap<K, ? extends V> persistentMap) {
            super(j);
            this.map = persistentMap;
        }

        @Override // androidx.compose.runtime.snapshots.StateRecord
        public final void assign(StateRecord stateRecord) {
            StateMapStateRecord stateMapStateRecord = (StateMapStateRecord) stateRecord;
            synchronized (SnapshotStateMapKt.sync) {
                this.map = stateMapStateRecord.map;
                this.modification = stateMapStateRecord.modification;
                Unit unit = Unit.INSTANCE;
            }
        }

        @Override // androidx.compose.runtime.snapshots.StateRecord
        public final StateRecord create() {
            return new StateMapStateRecord(SnapshotKt.currentSnapshot().getSnapshotId(), this.map);
        }

        @Override // androidx.compose.runtime.snapshots.StateRecord
        public final StateRecord create(long j) {
            return new StateMapStateRecord(j, this.map);
        }
    }

    public SnapshotStateMap() {
        PersistentHashMap.Companion.getClass();
        PersistentHashMap persistentHashMap = PersistentHashMap.EMPTY;
        Snapshot currentSnapshot = SnapshotKt.currentSnapshot();
        StateMapStateRecord stateMapStateRecord = new StateMapStateRecord(currentSnapshot.getSnapshotId(), persistentHashMap);
        if (!(currentSnapshot instanceof GlobalSnapshot)) {
            stateMapStateRecord.next = new StateMapStateRecord(1, persistentHashMap);
        }
        this.firstStateRecord = stateMapStateRecord;
        this.entries = new SnapshotMapEntrySet(this);
        this.keys = new SnapshotMapKeySet(this);
        this.values = new SnapshotMapValueSet(this);
    }

    public static final boolean access$attemptUpdate(SnapshotStateMap snapshotStateMap, StateMapStateRecord stateMapStateRecord, int i, PersistentMap persistentMap) {
        boolean z;
        snapshotStateMap.getClass();
        synchronized (SnapshotStateMapKt.sync) {
            int i2 = stateMapStateRecord.modification;
            if (i2 == i) {
                stateMapStateRecord.map = persistentMap;
                z = true;
                stateMapStateRecord.modification = i2 + 1;
            } else {
                z = false;
            }
        }
        return z;
    }

    @Override // java.util.Map
    public final void clear() {
        Snapshot currentSnapshot;
        StateMapStateRecord stateMapStateRecord = (StateMapStateRecord) SnapshotKt.current(this.firstStateRecord);
        PersistentHashMap.Companion.getClass();
        PersistentHashMap persistentHashMap = PersistentHashMap.EMPTY;
        if (persistentHashMap != stateMapStateRecord.map) {
            StateMapStateRecord stateMapStateRecord2 = this.firstStateRecord;
            synchronized (SnapshotKt.lock) {
                Snapshot.Companion.getClass();
                currentSnapshot = SnapshotKt.currentSnapshot();
                StateMapStateRecord stateMapStateRecord3 = (StateMapStateRecord) SnapshotKt.writableRecord(stateMapStateRecord2, this, currentSnapshot);
                synchronized (SnapshotStateMapKt.sync) {
                    stateMapStateRecord3.map = persistentHashMap;
                    stateMapStateRecord3.modification++;
                }
            }
            SnapshotKt.notifyWrite(currentSnapshot, this);
        }
    }

    @Override // java.util.Map
    public final boolean containsKey(Object obj) {
        return getReadable$runtime_release().map.containsKey(obj);
    }

    @Override // java.util.Map
    public final boolean containsValue(Object obj) {
        return getReadable$runtime_release().map.containsValue(obj);
    }

    @Override // java.util.Map
    public final Set entrySet() {
        return this.entries;
    }

    @Override // java.util.Map
    public final Object get(Object obj) {
        return getReadable$runtime_release().map.get(obj);
    }

    @Override // androidx.compose.runtime.snapshots.StateObject
    public final StateRecord getFirstStateRecord() {
        return this.firstStateRecord;
    }

    public final StateMapStateRecord getReadable$runtime_release() {
        return (StateMapStateRecord) SnapshotKt.readable(this.firstStateRecord, this);
    }

    @Override // java.util.Map
    public final boolean isEmpty() {
        return getReadable$runtime_release().map.isEmpty();
    }

    @Override // java.util.Map
    public final Set keySet() {
        return this.keys;
    }

    @Override // androidx.compose.runtime.snapshots.StateObject
    public final void prependStateRecord(StateRecord stateRecord) {
        this.firstStateRecord = (StateMapStateRecord) stateRecord;
    }

    @Override // java.util.Map
    public final Object put(Object obj, Object obj2) {
        PersistentMap persistentMap;
        int i;
        V put;
        Snapshot currentSnapshot;
        boolean access$attemptUpdate;
        do {
            synchronized (SnapshotStateMapKt.sync) {
                StateMapStateRecord stateMapStateRecord = (StateMapStateRecord) SnapshotKt.current(this.firstStateRecord);
                persistentMap = stateMapStateRecord.map;
                i = stateMapStateRecord.modification;
                Unit unit = Unit.INSTANCE;
            }
            persistentMap.getClass();
            PersistentMap.Builder builder = persistentMap.builder();
            put = builder.put(obj, obj2);
            PersistentMap build = builder.build();
            if (Intrinsics.areEqual(build, persistentMap)) {
                break;
            }
            StateMapStateRecord stateMapStateRecord2 = this.firstStateRecord;
            synchronized (SnapshotKt.lock) {
                Snapshot.Companion.getClass();
                currentSnapshot = SnapshotKt.currentSnapshot();
                access$attemptUpdate = access$attemptUpdate(this, (StateMapStateRecord) SnapshotKt.writableRecord(stateMapStateRecord2, this, currentSnapshot), i, build);
            }
            SnapshotKt.notifyWrite(currentSnapshot, this);
        } while (!access$attemptUpdate);
        return put;
    }

    @Override // java.util.Map
    public final void putAll(Map map) {
        PersistentMap persistentMap;
        int i;
        Snapshot currentSnapshot;
        boolean access$attemptUpdate;
        do {
            synchronized (SnapshotStateMapKt.sync) {
                StateMapStateRecord stateMapStateRecord = (StateMapStateRecord) SnapshotKt.current(this.firstStateRecord);
                persistentMap = stateMapStateRecord.map;
                i = stateMapStateRecord.modification;
                Unit unit = Unit.INSTANCE;
            }
            persistentMap.getClass();
            PersistentMap.Builder builder = persistentMap.builder();
            builder.putAll(map);
            PersistentMap build = builder.build();
            if (Intrinsics.areEqual(build, persistentMap)) {
                return;
            }
            StateMapStateRecord stateMapStateRecord2 = this.firstStateRecord;
            synchronized (SnapshotKt.lock) {
                Snapshot.Companion.getClass();
                currentSnapshot = SnapshotKt.currentSnapshot();
                access$attemptUpdate = access$attemptUpdate(this, (StateMapStateRecord) SnapshotKt.writableRecord(stateMapStateRecord2, this, currentSnapshot), i, build);
            }
            SnapshotKt.notifyWrite(currentSnapshot, this);
        } while (!access$attemptUpdate);
    }

    @Override // java.util.Map
    public final Object remove(Object obj) {
        PersistentMap persistentMap;
        int i;
        V remove;
        Snapshot currentSnapshot;
        boolean access$attemptUpdate;
        do {
            synchronized (SnapshotStateMapKt.sync) {
                StateMapStateRecord stateMapStateRecord = (StateMapStateRecord) SnapshotKt.current(this.firstStateRecord);
                persistentMap = stateMapStateRecord.map;
                i = stateMapStateRecord.modification;
                Unit unit = Unit.INSTANCE;
            }
            persistentMap.getClass();
            PersistentMap.Builder builder = persistentMap.builder();
            remove = builder.remove(obj);
            PersistentMap build = builder.build();
            if (Intrinsics.areEqual(build, persistentMap)) {
                break;
            }
            StateMapStateRecord stateMapStateRecord2 = this.firstStateRecord;
            synchronized (SnapshotKt.lock) {
                Snapshot.Companion.getClass();
                currentSnapshot = SnapshotKt.currentSnapshot();
                access$attemptUpdate = access$attemptUpdate(this, (StateMapStateRecord) SnapshotKt.writableRecord(stateMapStateRecord2, this, currentSnapshot), i, build);
            }
            SnapshotKt.notifyWrite(currentSnapshot, this);
        } while (!access$attemptUpdate);
        return remove;
    }

    @Override // java.util.Map
    public final int size() {
        return getReadable$runtime_release().map.size();
    }

    public final String toString() {
        return "SnapshotStateMap(value=" + ((StateMapStateRecord) SnapshotKt.current(this.firstStateRecord)).map + ")@" + hashCode();
    }

    @Override // java.util.Map
    public final Collection values() {
        return this.values;
    }
}
