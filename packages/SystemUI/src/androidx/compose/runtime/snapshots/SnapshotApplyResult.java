package androidx.compose.runtime.snapshots;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public abstract class SnapshotApplyResult {

    public final class Failure extends SnapshotApplyResult {
        public final Snapshot snapshot;

        public Failure(Snapshot snapshot) {
            super(null);
            this.snapshot = snapshot;
        }

        @Override // androidx.compose.runtime.snapshots.SnapshotApplyResult
        public final void check() throws SnapshotApplyConflictException {
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
