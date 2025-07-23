package androidx.compose.runtime.snapshots;

import androidx.compose.runtime.internal.Thread_jvmKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class TransparentObserverSnapshot extends Snapshot {
    public final boolean mergeParentObservers;
    public final boolean ownsPreviousSnapshot;
    public final Snapshot previousSnapshot;
    public Function1 readObserver;
    public final long threadId;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TransparentObserverSnapshot(Snapshot snapshot, Function1 function1, boolean z, boolean z2) {
        super(0L, SnapshotIdSet.EMPTY, (DefaultConstructorMarker) null);
        Function1 readObserver;
        Function1 function12 = SnapshotKt.emptyLambda;
        SnapshotIdSet.Companion.getClass();
        this.previousSnapshot = snapshot;
        this.mergeParentObservers = z;
        this.ownsPreviousSnapshot = z2;
        this.readObserver = SnapshotKt.mergedReadObserver(function1, z, (snapshot == null || (readObserver = snapshot.getReadObserver()) == null) ? SnapshotKt.globalSnapshot.readObserver : readObserver);
        this.threadId = Thread_jvmKt.currentThreadId();
    }

    @Override // androidx.compose.runtime.snapshots.Snapshot
    public final void dispose() {
        Snapshot snapshot;
        this.disposed = true;
        if (!this.ownsPreviousSnapshot || (snapshot = this.previousSnapshot) == null) {
            return;
        }
        snapshot.dispose();
    }

    public final Snapshot getCurrentSnapshot() {
        Snapshot snapshot = this.previousSnapshot;
        return snapshot == null ? SnapshotKt.globalSnapshot : snapshot;
    }

    @Override // androidx.compose.runtime.snapshots.Snapshot
    public final SnapshotIdSet getInvalid$runtime_release() {
        return getCurrentSnapshot().getInvalid$runtime_release();
    }

    @Override // androidx.compose.runtime.snapshots.Snapshot
    public final Function1 getReadObserver() {
        return this.readObserver;
    }

    @Override // androidx.compose.runtime.snapshots.Snapshot
    public final boolean getReadOnly() {
        return getCurrentSnapshot().getReadOnly();
    }

    @Override // androidx.compose.runtime.snapshots.Snapshot
    public final long getSnapshotId() {
        return getCurrentSnapshot().getSnapshotId();
    }

    @Override // androidx.compose.runtime.snapshots.Snapshot
    public final Function1 getWriteObserver$runtime_release() {
        return null;
    }

    @Override // androidx.compose.runtime.snapshots.Snapshot
    public final void nestedActivated$runtime_release() {
        SnapshotStateMapKt.unsupported();
        throw null;
    }

    @Override // androidx.compose.runtime.snapshots.Snapshot
    public final void nestedDeactivated$runtime_release() {
        SnapshotStateMapKt.unsupported();
        throw null;
    }

    @Override // androidx.compose.runtime.snapshots.Snapshot
    public final void notifyObjectsInitialized$runtime_release() {
        getCurrentSnapshot().notifyObjectsInitialized$runtime_release();
    }

    @Override // androidx.compose.runtime.snapshots.Snapshot
    public final void recordModified$runtime_release(StateObject stateObject) {
        getCurrentSnapshot().recordModified$runtime_release(stateObject);
    }

    @Override // androidx.compose.runtime.snapshots.Snapshot
    public final Snapshot takeNestedSnapshot(Function1 function1) {
        Function1 mergedReadObserver = SnapshotKt.mergedReadObserver(function1, true, this.readObserver);
        return !this.mergeParentObservers ? SnapshotKt.createTransparentSnapshotWithNoParentReadObserver(getCurrentSnapshot().takeNestedSnapshot(null), mergedReadObserver, true) : getCurrentSnapshot().takeNestedSnapshot(mergedReadObserver);
    }
}
