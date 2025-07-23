package androidx.compose.runtime.snapshots;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class SnapshotApplyResult {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Failure extends SnapshotApplyResult {
        public final Snapshot snapshot;

        public Failure(Snapshot snapshot) {
            super(null);
            this.snapshot = snapshot;
        }

        @Override // androidx.compose.runtime.snapshots.SnapshotApplyResult
        public final void check() {
            Snapshot snapshot = this.snapshot;
            snapshot.dispose();
            throw new SnapshotApplyConflictException(snapshot);
        }
    }

    public /* synthetic */ SnapshotApplyResult(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract void check();

    private SnapshotApplyResult() {
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Success extends SnapshotApplyResult {
        public static final Success INSTANCE = new Success();

        private Success() {
            super(null);
        }

        @Override // androidx.compose.runtime.snapshots.SnapshotApplyResult
        public final void check() {
        }
    }
}
