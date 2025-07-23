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
public class SnapshotMutableFloatStateImpl extends StateObjectImpl implements MutableFloatState, SnapshotMutableState<Float> {
    public FloatStateStateRecord next;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class FloatStateStateRecord extends StateRecord {
        public float value;

        public FloatStateStateRecord(long j, float f) {
            super(j);
            this.value = f;
        }

        @Override // androidx.compose.runtime.snapshots.StateRecord
        public final void assign(StateRecord stateRecord) {
            this.value = ((FloatStateStateRecord) stateRecord).value;
        }

        @Override // androidx.compose.runtime.snapshots.StateRecord
        public final StateRecord create() {
            return create(SnapshotKt.currentSnapshot().getSnapshotId());
        }

        @Override // androidx.compose.runtime.snapshots.StateRecord
        public final StateRecord create(long j) {
            return new FloatStateStateRecord(j, this.value);
        }
    }

    public SnapshotMutableFloatStateImpl(float f) {
        Snapshot currentSnapshot = SnapshotKt.currentSnapshot();
        FloatStateStateRecord floatStateStateRecord = new FloatStateStateRecord(currentSnapshot.getSnapshotId(), f);
        if (!(currentSnapshot instanceof GlobalSnapshot)) {
            floatStateStateRecord.next = new FloatStateStateRecord(1, f);
        }
        this.next = floatStateStateRecord;
    }

    @Override // androidx.compose.runtime.MutableState
    public final Object component1() {
        return Float.valueOf(getFloatValue());
    }

    @Override // androidx.compose.runtime.MutableState
    public final Function1 component2() {
        return new Function1() { // from class: androidx.compose.runtime.SnapshotMutableFloatStateImpl$component2$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                SnapshotMutableFloatStateImpl.this.setFloatValue(((Number) obj).floatValue());
                return Unit.INSTANCE;
            }
        };
    }

    @Override // androidx.compose.runtime.snapshots.StateObject
    public final StateRecord getFirstStateRecord() {
        return this.next;
    }

    @Override // androidx.compose.runtime.FloatState
    public final float getFloatValue() {
        return ((FloatStateStateRecord) SnapshotKt.readable(this.next, this)).value;
    }

    @Override // androidx.compose.runtime.snapshots.SnapshotMutableState
    public final SnapshotMutationPolicy getPolicy() {
        return StructuralEqualityPolicy.INSTANCE;
    }

    @Override // androidx.compose.runtime.snapshots.StateObject
    public final StateRecord mergeRecords(StateRecord stateRecord, StateRecord stateRecord2, StateRecord stateRecord3) {
        if (((FloatStateStateRecord) stateRecord2).value == ((FloatStateStateRecord) stateRecord3).value) {
            return stateRecord2;
        }
        return null;
    }

    @Override // androidx.compose.runtime.snapshots.StateObject
    public final void prependStateRecord(StateRecord stateRecord) {
        this.next = (FloatStateStateRecord) stateRecord;
    }

    public final void setFloatValue(float f) {
        Snapshot currentSnapshot;
        FloatStateStateRecord floatStateStateRecord = (FloatStateStateRecord) SnapshotKt.current(this.next);
        if (floatStateStateRecord.value == f) {
            return;
        }
        FloatStateStateRecord floatStateStateRecord2 = this.next;
        synchronized (SnapshotKt.lock) {
            Snapshot.Companion.getClass();
            currentSnapshot = SnapshotKt.currentSnapshot();
            ((FloatStateStateRecord) SnapshotKt.overwritableRecord(floatStateStateRecord2, this, currentSnapshot, floatStateStateRecord)).value = f;
            Unit unit = Unit.INSTANCE;
        }
        SnapshotKt.notifyWrite(currentSnapshot, this);
    }

    public final String toString() {
        return "MutableFloatState(value=" + ((FloatStateStateRecord) SnapshotKt.current(this.next)).value + ")@" + hashCode();
    }
}
