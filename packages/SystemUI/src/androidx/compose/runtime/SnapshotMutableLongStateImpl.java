package androidx.compose.runtime;

import androidx.compose.runtime.snapshots.GlobalSnapshot;
import androidx.compose.runtime.snapshots.Snapshot;
import androidx.compose.runtime.snapshots.SnapshotKt;
import androidx.compose.runtime.snapshots.SnapshotMutableState;
import androidx.compose.runtime.snapshots.StateObjectImpl;
import androidx.compose.runtime.snapshots.StateRecord;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class SnapshotMutableLongStateImpl extends StateObjectImpl implements MutableLongState, SnapshotMutableState<Long> {
    public LongStateStateRecord next;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class LongStateStateRecord extends StateRecord {
        public long value;

        public LongStateStateRecord(long j, long j2) {
            super(j);
            this.value = j2;
        }

        @Override // androidx.compose.runtime.snapshots.StateRecord
        public final void assign(StateRecord stateRecord) {
            this.value = ((LongStateStateRecord) stateRecord).value;
        }

        @Override // androidx.compose.runtime.snapshots.StateRecord
        public final StateRecord create() {
            return create(SnapshotKt.currentSnapshot().getSnapshotId());
        }

        @Override // androidx.compose.runtime.snapshots.StateRecord
        public final StateRecord create(long j) {
            return new LongStateStateRecord(j, this.value);
        }
    }

    public SnapshotMutableLongStateImpl(long j) {
        Snapshot currentSnapshot = SnapshotKt.currentSnapshot();
        LongStateStateRecord longStateStateRecord = new LongStateStateRecord(currentSnapshot.getSnapshotId(), j);
        if (!(currentSnapshot instanceof GlobalSnapshot)) {
            longStateStateRecord.next = new LongStateStateRecord(1, j);
        }
        this.next = longStateStateRecord;
    }

    @Override // androidx.compose.runtime.MutableState
    public final Object component1() {
        return Long.valueOf(getLongValue());
    }

    @Override // androidx.compose.runtime.MutableState
    public final Function1 component2() {
        return new Function1() { // from class: androidx.compose.runtime.SnapshotMutableLongStateImpl$component2$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                SnapshotMutableLongStateImpl.this.setLongValue(((Number) obj).longValue());
                return Unit.INSTANCE;
            }
        };
    }

    @Override // androidx.compose.runtime.snapshots.StateObject
    public final StateRecord getFirstStateRecord() {
        return this.next;
    }

    public final long getLongValue() {
        return ((LongStateStateRecord) SnapshotKt.readable(this.next, this)).value;
    }

    @Override // androidx.compose.runtime.snapshots.SnapshotMutableState
    public final SnapshotMutationPolicy getPolicy() {
        return StructuralEqualityPolicy.INSTANCE;
    }

    @Override // androidx.compose.runtime.snapshots.StateObject
    public final StateRecord mergeRecords(StateRecord stateRecord, StateRecord stateRecord2, StateRecord stateRecord3) {
        if (((LongStateStateRecord) stateRecord2).value == ((LongStateStateRecord) stateRecord3).value) {
            return stateRecord2;
        }
        return null;
    }

    @Override // androidx.compose.runtime.snapshots.StateObject
    public final void prependStateRecord(StateRecord stateRecord) {
        this.next = (LongStateStateRecord) stateRecord;
    }

    public final void setLongValue(long j) {
        Snapshot currentSnapshot;
        LongStateStateRecord longStateStateRecord = (LongStateStateRecord) SnapshotKt.current(this.next);
        if (longStateStateRecord.value != j) {
            LongStateStateRecord longStateStateRecord2 = this.next;
            synchronized (SnapshotKt.lock) {
                Snapshot.Companion.getClass();
                currentSnapshot = SnapshotKt.currentSnapshot();
                ((LongStateStateRecord) SnapshotKt.overwritableRecord(longStateStateRecord2, this, currentSnapshot, longStateStateRecord)).value = j;
                Unit unit = Unit.INSTANCE;
            }
            SnapshotKt.notifyWrite(currentSnapshot, this);
        }
    }

    public final String toString() {
        return "MutableLongState(value=" + ((LongStateStateRecord) SnapshotKt.current(this.next)).value + ")@" + hashCode();
    }
}
