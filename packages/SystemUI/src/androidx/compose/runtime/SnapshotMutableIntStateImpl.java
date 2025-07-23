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
public class SnapshotMutableIntStateImpl extends StateObjectImpl implements MutableIntState, SnapshotMutableState<Integer> {
    public IntStateStateRecord next;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class IntStateStateRecord extends StateRecord {
        public int value;

        public IntStateStateRecord(long j, int i) {
            super(j);
            this.value = i;
        }

        @Override // androidx.compose.runtime.snapshots.StateRecord
        public final void assign(StateRecord stateRecord) {
            this.value = ((IntStateStateRecord) stateRecord).value;
        }

        @Override // androidx.compose.runtime.snapshots.StateRecord
        public final StateRecord create() {
            return create(SnapshotKt.currentSnapshot().getSnapshotId());
        }

        @Override // androidx.compose.runtime.snapshots.StateRecord
        public final StateRecord create(long j) {
            return new IntStateStateRecord(j, this.value);
        }
    }

    public SnapshotMutableIntStateImpl(int i) {
        Snapshot currentSnapshot = SnapshotKt.currentSnapshot();
        IntStateStateRecord intStateStateRecord = new IntStateStateRecord(currentSnapshot.getSnapshotId(), i);
        if (!(currentSnapshot instanceof GlobalSnapshot)) {
            intStateStateRecord.next = new IntStateStateRecord(1, i);
        }
        this.next = intStateStateRecord;
    }

    @Override // androidx.compose.runtime.MutableState
    public final Object component1() {
        return Integer.valueOf(getIntValue());
    }

    @Override // androidx.compose.runtime.MutableState
    public final Function1 component2() {
        return new Function1() { // from class: androidx.compose.runtime.SnapshotMutableIntStateImpl$component2$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                SnapshotMutableIntStateImpl.this.setIntValue(((Number) obj).intValue());
                return Unit.INSTANCE;
            }
        };
    }

    @Override // androidx.compose.runtime.snapshots.StateObject
    public final StateRecord getFirstStateRecord() {
        return this.next;
    }

    public final int getIntValue() {
        return ((IntStateStateRecord) SnapshotKt.readable(this.next, this)).value;
    }

    @Override // androidx.compose.runtime.snapshots.SnapshotMutableState
    public final SnapshotMutationPolicy getPolicy() {
        return StructuralEqualityPolicy.INSTANCE;
    }

    @Override // androidx.compose.runtime.snapshots.StateObject
    public final StateRecord mergeRecords(StateRecord stateRecord, StateRecord stateRecord2, StateRecord stateRecord3) {
        if (((IntStateStateRecord) stateRecord2).value == ((IntStateStateRecord) stateRecord3).value) {
            return stateRecord2;
        }
        return null;
    }

    @Override // androidx.compose.runtime.snapshots.StateObject
    public final void prependStateRecord(StateRecord stateRecord) {
        this.next = (IntStateStateRecord) stateRecord;
    }

    public final void setIntValue(int i) {
        Snapshot currentSnapshot;
        IntStateStateRecord intStateStateRecord = (IntStateStateRecord) SnapshotKt.current(this.next);
        if (intStateStateRecord.value != i) {
            IntStateStateRecord intStateStateRecord2 = this.next;
            synchronized (SnapshotKt.lock) {
                Snapshot.Companion.getClass();
                currentSnapshot = SnapshotKt.currentSnapshot();
                ((IntStateStateRecord) SnapshotKt.overwritableRecord(intStateStateRecord2, this, currentSnapshot, intStateStateRecord)).value = i;
                Unit unit = Unit.INSTANCE;
            }
            SnapshotKt.notifyWrite(currentSnapshot, this);
        }
    }

    public final String toString() {
        return "MutableIntState(value=" + ((IntStateStateRecord) SnapshotKt.current(this.next)).value + ")@" + hashCode();
    }
}
