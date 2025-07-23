package androidx.compose.runtime.snapshots;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        StateRecord create = create();
        create.snapshotId = j;
        return create;
    }

    public StateRecord() {
        this(SnapshotKt.currentSnapshot().getSnapshotId());
    }

    public StateRecord(int i) {
        this(i);
    }
}
