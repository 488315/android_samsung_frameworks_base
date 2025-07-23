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
public class SnapshotMutableStateImpl<T> extends StateObjectImpl implements SnapshotMutableState<T> {
    public StateStateRecord next;
    public final SnapshotMutationPolicy policy;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class StateStateRecord<T> extends StateRecord {
        public Object value;

        public StateStateRecord(long j, T t) {
            super(j);
            this.value = t;
        }

        @Override // androidx.compose.runtime.snapshots.StateRecord
        public final void assign(StateRecord stateRecord) {
            this.value = ((StateStateRecord) stateRecord).value;
        }

        @Override // androidx.compose.runtime.snapshots.StateRecord
        public final StateRecord create() {
            return new StateStateRecord(SnapshotKt.currentSnapshot().getSnapshotId(), this.value);
        }

        @Override // androidx.compose.runtime.snapshots.StateRecord
        public final StateRecord create(long j) {
            return new StateStateRecord(SnapshotKt.currentSnapshot().getSnapshotId(), this.value);
        }
    }

    public SnapshotMutableStateImpl(T t, SnapshotMutationPolicy<T> snapshotMutationPolicy) {
        this.policy = snapshotMutationPolicy;
        Snapshot currentSnapshot = SnapshotKt.currentSnapshot();
        StateStateRecord stateStateRecord = new StateStateRecord(currentSnapshot.getSnapshotId(), t);
        if (!(currentSnapshot instanceof GlobalSnapshot)) {
            stateStateRecord.next = new StateStateRecord(1, t);
        }
        this.next = stateStateRecord;
    }

    @Override // androidx.compose.runtime.MutableState
    public final Object component1() {
        return getValue();
    }

    @Override // androidx.compose.runtime.MutableState
    public final Function1 component2() {
        return new Function1(this) { // from class: androidx.compose.runtime.SnapshotMutableStateImpl$component2$1
            final /* synthetic */ SnapshotMutableStateImpl<Object> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
                this.this$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                this.this$0.setValue(obj);
                return Unit.INSTANCE;
            }
        };
    }

    @Override // androidx.compose.runtime.snapshots.StateObject
    public final StateRecord getFirstStateRecord() {
        return this.next;
    }

    @Override // androidx.compose.runtime.snapshots.SnapshotMutableState
    public final SnapshotMutationPolicy getPolicy() {
        return this.policy;
    }

    @Override // androidx.compose.runtime.State
    public final Object getValue() {
        return ((StateStateRecord) SnapshotKt.readable(this.next, this)).value;
    }

    @Override // androidx.compose.runtime.snapshots.StateObject
    public final StateRecord mergeRecords(StateRecord stateRecord, StateRecord stateRecord2, StateRecord stateRecord3) {
        if (this.policy.equivalent(((StateStateRecord) stateRecord2).value, ((StateStateRecord) stateRecord3).value)) {
            return stateRecord2;
        }
        return null;
    }

    @Override // androidx.compose.runtime.snapshots.StateObject
    public final void prependStateRecord(StateRecord stateRecord) {
        this.next = (StateStateRecord) stateRecord;
    }

    @Override // androidx.compose.runtime.MutableState
    public final void setValue(Object obj) {
        Snapshot currentSnapshot;
        StateStateRecord stateStateRecord = (StateStateRecord) SnapshotKt.current(this.next);
        if (this.policy.equivalent(stateStateRecord.value, obj)) {
            return;
        }
        StateStateRecord stateStateRecord2 = this.next;
        synchronized (SnapshotKt.lock) {
            Snapshot.Companion.getClass();
            currentSnapshot = SnapshotKt.currentSnapshot();
            ((StateStateRecord) SnapshotKt.overwritableRecord(stateStateRecord2, this, currentSnapshot, stateStateRecord)).value = obj;
            Unit unit = Unit.INSTANCE;
        }
        SnapshotKt.notifyWrite(currentSnapshot, this);
    }

    public final String toString() {
        return "MutableState(value=" + ((StateStateRecord) SnapshotKt.current(this.next)).value + ")@" + hashCode();
    }
}
