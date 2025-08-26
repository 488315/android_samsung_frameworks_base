package androidx.compose.runtime.snapshots;

/* loaded from: classes.dex */
public final class SnapshotApplyConflictException extends Exception {
    private final Snapshot snapshot;

    public SnapshotApplyConflictException(Snapshot snapshot) {
        this.snapshot = snapshot;
    }
}
