package androidx.compose.runtime.snapshots;

/* loaded from: classes.dex */
public abstract class StateRecord {
    public StateRecord next;
    public long snapshotId;

    public StateRecord(long j) {
        this.snapshotId = j;
    }

    public abstract void assign(StateRecord stateRecord);

    public abstract StateRecord create();

    public StateRecord create(long j) {
        StateRecord stateRecordCreate = create();
        stateRecordCreate.snapshotId = j;
        return stateRecordCreate;
    }

    public StateRecord() {
        this(SnapshotKt.currentSnapshot().getSnapshotId());
    }

    public StateRecord(int i) {
        this(i);
    }
}
