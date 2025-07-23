package androidx.compose.runtime.snapshots;

import androidx.collection.MutableScatterSet;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class TransparentObserverMutableSnapshot extends MutableSnapshot {
    public final boolean mergeParentObservers;
    public final boolean ownsParentSnapshot;
    public final MutableSnapshot parentSnapshot;
    public Function1 readObserver;
    public final long threadId;
    public Function1 writeObserver;

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public TransparentObserverMutableSnapshot(androidx.compose.runtime.snapshots.MutableSnapshot r8, kotlin.jvm.functions.Function1 r9, kotlin.jvm.functions.Function1 r10, boolean r11, boolean r12) {
        /*
            r7 = this;
            kotlin.jvm.functions.Function1 r0 = androidx.compose.runtime.snapshots.SnapshotKt.emptyLambda
            androidx.compose.runtime.snapshots.SnapshotIdSet$Companion r0 = androidx.compose.runtime.snapshots.SnapshotIdSet.Companion
            r0.getClass()
            androidx.compose.runtime.snapshots.SnapshotIdSet r4 = androidx.compose.runtime.snapshots.SnapshotIdSet.EMPTY
            if (r8 == 0) goto L11
            kotlin.jvm.functions.Function1 r0 = r8.getReadObserver()
            if (r0 != 0) goto L15
        L11:
            androidx.compose.runtime.snapshots.GlobalSnapshot r0 = androidx.compose.runtime.snapshots.SnapshotKt.globalSnapshot
            kotlin.jvm.functions.Function1 r0 = r0.readObserver
        L15:
            kotlin.jvm.functions.Function1 r5 = androidx.compose.runtime.snapshots.SnapshotKt.mergedReadObserver(r9, r11, r0)
            if (r8 == 0) goto L21
            kotlin.jvm.functions.Function1 r9 = r8.getWriteObserver$runtime_release()
            if (r9 != 0) goto L25
        L21:
            androidx.compose.runtime.snapshots.GlobalSnapshot r9 = androidx.compose.runtime.snapshots.SnapshotKt.globalSnapshot
            kotlin.jvm.functions.Function1 r9 = r9.writeObserver
        L25:
            kotlin.jvm.functions.Function1 r6 = androidx.compose.runtime.snapshots.SnapshotKt.access$mergedWriteObserver(r10, r9)
            r2 = 0
            r1 = r7
            r1.<init>(r2, r4, r5, r6)
            r1.parentSnapshot = r8
            r1.mergeParentObservers = r11
            r1.ownsParentSnapshot = r12
            kotlin.jvm.functions.Function1 r7 = r1.readObserver
            r1.readObserver = r7
            kotlin.jvm.functions.Function1 r7 = r1.writeObserver
            r1.writeObserver = r7
            long r7 = androidx.compose.runtime.internal.Thread_jvmKt.currentThreadId()
            r1.threadId = r7
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.runtime.snapshots.TransparentObserverMutableSnapshot.<init>(androidx.compose.runtime.snapshots.MutableSnapshot, kotlin.jvm.functions.Function1, kotlin.jvm.functions.Function1, boolean, boolean):void");
    }

    @Override // androidx.compose.runtime.snapshots.MutableSnapshot
    public final SnapshotApplyResult apply() {
        return getCurrentSnapshot().apply();
    }

    @Override // androidx.compose.runtime.snapshots.MutableSnapshot, androidx.compose.runtime.snapshots.Snapshot
    public final void dispose() {
        MutableSnapshot mutableSnapshot;
        this.disposed = true;
        if (!this.ownsParentSnapshot || (mutableSnapshot = this.parentSnapshot) == null) {
            return;
        }
        mutableSnapshot.dispose();
    }

    public final MutableSnapshot getCurrentSnapshot() {
        MutableSnapshot mutableSnapshot = this.parentSnapshot;
        return mutableSnapshot == null ? SnapshotKt.globalSnapshot : mutableSnapshot;
    }

    @Override // androidx.compose.runtime.snapshots.Snapshot
    public final SnapshotIdSet getInvalid$runtime_release() {
        return getCurrentSnapshot().getInvalid$runtime_release();
    }

    @Override // androidx.compose.runtime.snapshots.MutableSnapshot
    public final MutableScatterSet getModified$runtime_release() {
        return getCurrentSnapshot().getModified$runtime_release();
    }

    @Override // androidx.compose.runtime.snapshots.MutableSnapshot, androidx.compose.runtime.snapshots.Snapshot
    public final Function1 getReadObserver() {
        return this.readObserver;
    }

    @Override // androidx.compose.runtime.snapshots.MutableSnapshot
    /* renamed from: getReadObserver$runtime_release */
    public final Function1 getReadObserver() {
        return this.readObserver;
    }

    @Override // androidx.compose.runtime.snapshots.MutableSnapshot, androidx.compose.runtime.snapshots.Snapshot
    public final boolean getReadOnly() {
        return getCurrentSnapshot().getReadOnly();
    }

    @Override // androidx.compose.runtime.snapshots.Snapshot
    public final long getSnapshotId() {
        return getCurrentSnapshot().getSnapshotId();
    }

    @Override // androidx.compose.runtime.snapshots.MutableSnapshot, androidx.compose.runtime.snapshots.Snapshot
    public final int getWriteCount$runtime_release() {
        return getCurrentSnapshot().getWriteCount$runtime_release();
    }

    @Override // androidx.compose.runtime.snapshots.MutableSnapshot, androidx.compose.runtime.snapshots.Snapshot
    public final Function1 getWriteObserver$runtime_release() {
        return this.writeObserver;
    }

    @Override // androidx.compose.runtime.snapshots.MutableSnapshot, androidx.compose.runtime.snapshots.Snapshot
    public final void nestedActivated$runtime_release() {
        SnapshotStateMapKt.unsupported();
        throw null;
    }

    @Override // androidx.compose.runtime.snapshots.MutableSnapshot, androidx.compose.runtime.snapshots.Snapshot
    public final void nestedDeactivated$runtime_release() {
        SnapshotStateMapKt.unsupported();
        throw null;
    }

    @Override // androidx.compose.runtime.snapshots.MutableSnapshot, androidx.compose.runtime.snapshots.Snapshot
    public final void notifyObjectsInitialized$runtime_release() {
        getCurrentSnapshot().notifyObjectsInitialized$runtime_release();
    }

    @Override // androidx.compose.runtime.snapshots.MutableSnapshot, androidx.compose.runtime.snapshots.Snapshot
    public final void recordModified$runtime_release(StateObject stateObject) {
        getCurrentSnapshot().recordModified$runtime_release(stateObject);
    }

    @Override // androidx.compose.runtime.snapshots.Snapshot
    public final void setInvalid$runtime_release(SnapshotIdSet snapshotIdSet) {
        SnapshotStateMapKt.unsupported();
        throw null;
    }

    @Override // androidx.compose.runtime.snapshots.MutableSnapshot
    public final void setModified(MutableScatterSet mutableScatterSet) {
        SnapshotStateMapKt.unsupported();
        throw null;
    }

    @Override // androidx.compose.runtime.snapshots.Snapshot
    public final void setSnapshotId$runtime_release(long j) {
        SnapshotStateMapKt.unsupported();
        throw null;
    }

    @Override // androidx.compose.runtime.snapshots.MutableSnapshot, androidx.compose.runtime.snapshots.Snapshot
    public final void setWriteCount$runtime_release(int i) {
        getCurrentSnapshot().setWriteCount$runtime_release(i);
    }

    @Override // androidx.compose.runtime.snapshots.MutableSnapshot
    public final MutableSnapshot takeNestedMutableSnapshot(Function1 function1, Function1 function12) {
        Function1 mergedReadObserver = SnapshotKt.mergedReadObserver(function1, true, this.readObserver);
        Function1 access$mergedWriteObserver = SnapshotKt.access$mergedWriteObserver(function12, this.writeObserver);
        return !this.mergeParentObservers ? new TransparentObserverMutableSnapshot(getCurrentSnapshot().takeNestedMutableSnapshot(null, access$mergedWriteObserver), mergedReadObserver, access$mergedWriteObserver, false, true) : getCurrentSnapshot().takeNestedMutableSnapshot(mergedReadObserver, access$mergedWriteObserver);
    }

    @Override // androidx.compose.runtime.snapshots.MutableSnapshot, androidx.compose.runtime.snapshots.Snapshot
    public final Snapshot takeNestedSnapshot(Function1 function1) {
        Function1 mergedReadObserver = SnapshotKt.mergedReadObserver(function1, true, this.readObserver);
        return !this.mergeParentObservers ? SnapshotKt.createTransparentSnapshotWithNoParentReadObserver(getCurrentSnapshot().takeNestedSnapshot(null), mergedReadObserver, true) : getCurrentSnapshot().takeNestedSnapshot(mergedReadObserver);
    }
}
