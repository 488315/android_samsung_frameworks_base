package androidx.compose.runtime.snapshots;

import androidx.collection.MutableScatterSet;
import androidx.compose.runtime.internal.SnapshotThreadLocal;
import androidx.compose.runtime.internal.Thread_jvmKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class Snapshot {
    public static final Companion Companion = new Companion(null);
    public boolean disposed;
    public SnapshotIdSet invalid;
    public int pinningTrackingHandle;
    public long snapshotId;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static Snapshot getCurrentThreadSnapshot() {
            return (Snapshot) SnapshotKt.threadSnapshot.get();
        }

        public static Snapshot makeCurrentNonObservable(Snapshot snapshot) {
            if (snapshot instanceof TransparentObserverMutableSnapshot) {
                TransparentObserverMutableSnapshot transparentObserverMutableSnapshot = (TransparentObserverMutableSnapshot) snapshot;
                if (transparentObserverMutableSnapshot.threadId == Thread_jvmKt.currentThreadId()) {
                    transparentObserverMutableSnapshot.readObserver = null;
                    return snapshot;
                }
            }
            if (snapshot instanceof TransparentObserverSnapshot) {
                TransparentObserverSnapshot transparentObserverSnapshot = (TransparentObserverSnapshot) snapshot;
                if (transparentObserverSnapshot.threadId == Thread_jvmKt.currentThreadId()) {
                    transparentObserverSnapshot.readObserver = null;
                    return snapshot;
                }
            }
            Snapshot createTransparentSnapshotWithNoParentReadObserver = SnapshotKt.createTransparentSnapshotWithNoParentReadObserver(snapshot, null, false);
            createTransparentSnapshotWithNoParentReadObserver.makeCurrent();
            return createTransparentSnapshotWithNoParentReadObserver;
        }

        public static Object observe(Function1 function1, Function0 function0) {
            Snapshot transparentObserverMutableSnapshot;
            if (function1 == null) {
                return function0.invoke();
            }
            Snapshot snapshot = (Snapshot) SnapshotKt.threadSnapshot.get();
            if (snapshot instanceof TransparentObserverMutableSnapshot) {
                TransparentObserverMutableSnapshot transparentObserverMutableSnapshot2 = (TransparentObserverMutableSnapshot) snapshot;
                if (transparentObserverMutableSnapshot2.threadId == Thread_jvmKt.currentThreadId()) {
                    Function1 function12 = transparentObserverMutableSnapshot2.readObserver;
                    Function1 function13 = transparentObserverMutableSnapshot2.writeObserver;
                    try {
                        ((TransparentObserverMutableSnapshot) snapshot).readObserver = SnapshotKt.mergedReadObserver(function1, true, function12);
                        ((TransparentObserverMutableSnapshot) snapshot).writeObserver = function13;
                        return function0.invoke();
                    } finally {
                        transparentObserverMutableSnapshot2.readObserver = function12;
                        transparentObserverMutableSnapshot2.writeObserver = function13;
                    }
                }
            }
            if (snapshot == null || (snapshot instanceof MutableSnapshot)) {
                transparentObserverMutableSnapshot = new TransparentObserverMutableSnapshot(snapshot instanceof MutableSnapshot ? (MutableSnapshot) snapshot : null, function1, null, true, false);
            } else {
                if (function1 == null) {
                    return function0.invoke();
                }
                transparentObserverMutableSnapshot = snapshot.takeNestedSnapshot(function1);
            }
            try {
                Snapshot makeCurrent = transparentObserverMutableSnapshot.makeCurrent();
                try {
                    Object invoke = function0.invoke();
                    Snapshot.restoreCurrent(makeCurrent);
                    return invoke;
                } catch (Throwable th) {
                    Snapshot.restoreCurrent(makeCurrent);
                    throw th;
                }
            } finally {
                transparentObserverMutableSnapshot.dispose();
            }
        }

        public static Snapshot$Companion$$ExternalSyntheticLambda0 registerApplyObserver(Function2 function2) {
            SnapshotKt.advanceGlobalSnapshot(SnapshotKt.emptyLambda);
            synchronized (SnapshotKt.lock) {
                SnapshotKt.applyObservers = CollectionsKt___CollectionsKt.plus(SnapshotKt.applyObservers, function2);
                Unit unit = Unit.INSTANCE;
            }
            return new Snapshot$Companion$$ExternalSyntheticLambda0(function2);
        }

        public static void restoreNonObservable(Snapshot snapshot, Snapshot snapshot2, Function1 function1) {
            if (snapshot != snapshot2) {
                snapshot2.getClass();
                Snapshot.restoreCurrent(snapshot);
                snapshot2.dispose();
            } else if (snapshot instanceof TransparentObserverMutableSnapshot) {
                ((TransparentObserverMutableSnapshot) snapshot).readObserver = function1;
            } else if (snapshot instanceof TransparentObserverSnapshot) {
                ((TransparentObserverSnapshot) snapshot).readObserver = function1;
            } else {
                throw new IllegalStateException(("Non-transparent snapshot was reused: " + snapshot).toString());
            }
        }

        public static void sendApplyNotifications() {
            boolean z;
            synchronized (SnapshotKt.lock) {
                MutableScatterSet mutableScatterSet = SnapshotKt.globalSnapshot.modified;
                z = false;
                if (mutableScatterSet != null) {
                    if (mutableScatterSet.isNotEmpty()) {
                        z = true;
                    }
                }
            }
            if (z) {
                SnapshotKt.advanceGlobalSnapshot(SnapshotKt.emptyLambda);
            }
        }

        public static MutableSnapshot takeMutableSnapshot(Function1 function1, Function1 function12) {
            MutableSnapshot takeNestedMutableSnapshot;
            Snapshot currentSnapshot = SnapshotKt.currentSnapshot();
            MutableSnapshot mutableSnapshot = currentSnapshot instanceof MutableSnapshot ? (MutableSnapshot) currentSnapshot : null;
            if (mutableSnapshot == null || (takeNestedMutableSnapshot = mutableSnapshot.takeNestedMutableSnapshot(function1, function12)) == null) {
                throw new IllegalStateException("Cannot create a mutable snapshot of an read-only snapshot");
            }
            return takeNestedMutableSnapshot;
        }

        private Companion() {
        }
    }

    public /* synthetic */ Snapshot(int i, SnapshotIdSet snapshotIdSet, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, snapshotIdSet);
    }

    public static void restoreCurrent(Snapshot snapshot) {
        SnapshotKt.threadSnapshot.set(snapshot);
    }

    public final void closeAndReleasePinning$runtime_release() {
        synchronized (SnapshotKt.lock) {
            closeLocked$runtime_release();
            releasePinnedSnapshotsForCloseLocked$runtime_release();
            Unit unit = Unit.INSTANCE;
        }
    }

    public void closeLocked$runtime_release() {
        SnapshotKt.openSnapshots = SnapshotKt.openSnapshots.clear(getSnapshotId());
    }

    public void dispose() {
        this.disposed = true;
        synchronized (SnapshotKt.lock) {
            releasePinnedSnapshotLocked$runtime_release();
            Unit unit = Unit.INSTANCE;
        }
    }

    public SnapshotIdSet getInvalid$runtime_release() {
        return this.invalid;
    }

    public abstract Function1 getReadObserver();

    public abstract boolean getReadOnly();

    public long getSnapshotId() {
        return this.snapshotId;
    }

    public int getWriteCount$runtime_release() {
        return 0;
    }

    public abstract Function1 getWriteObserver$runtime_release();

    public final Snapshot makeCurrent() {
        SnapshotThreadLocal snapshotThreadLocal = SnapshotKt.threadSnapshot;
        Snapshot snapshot = (Snapshot) snapshotThreadLocal.get();
        snapshotThreadLocal.set(this);
        return snapshot;
    }

    public abstract void nestedActivated$runtime_release();

    public abstract void nestedDeactivated$runtime_release();

    public abstract void notifyObjectsInitialized$runtime_release();

    public abstract void recordModified$runtime_release(StateObject stateObject);

    public final void releasePinnedSnapshotLocked$runtime_release() {
        int i = this.pinningTrackingHandle;
        if (i >= 0) {
            SnapshotKt.releasePinningLocked(i);
            this.pinningTrackingHandle = -1;
        }
    }

    public void releasePinnedSnapshotsForCloseLocked$runtime_release() {
        releasePinnedSnapshotLocked$runtime_release();
    }

    public void setInvalid$runtime_release(SnapshotIdSet snapshotIdSet) {
        this.invalid = snapshotIdSet;
    }

    public void setSnapshotId$runtime_release(long j) {
        this.snapshotId = j;
    }

    public void setWriteCount$runtime_release(int i) {
        throw new IllegalStateException("Updating write count is not supported for this snapshot");
    }

    public abstract Snapshot takeNestedSnapshot(Function1 function1);

    public /* synthetic */ Snapshot(long j, SnapshotIdSet snapshotIdSet, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, snapshotIdSet);
    }

    private Snapshot(long j, SnapshotIdSet snapshotIdSet) {
        int i;
        long j2;
        int numberOfTrailingZeros;
        this.invalid = snapshotIdSet;
        this.snapshotId = j;
        Function1 function1 = SnapshotKt.emptyLambda;
        if (j != 0) {
            SnapshotIdSet invalid$runtime_release = getInvalid$runtime_release();
            long[] jArr = invalid$runtime_release.belowBound;
            if (jArr != null) {
                j = jArr[0];
            } else {
                long j3 = invalid$runtime_release.lowerSet;
                if (j3 != 0) {
                    j2 = invalid$runtime_release.lowerBound;
                    numberOfTrailingZeros = Long.numberOfTrailingZeros(j3);
                } else {
                    long j4 = invalid$runtime_release.upperSet;
                    if (j4 != 0) {
                        j2 = invalid$runtime_release.lowerBound + 64;
                        numberOfTrailingZeros = Long.numberOfTrailingZeros(j4);
                    }
                }
                j = j2 + numberOfTrailingZeros;
            }
            synchronized (SnapshotKt.lock) {
                i = SnapshotKt.pinningTable.add(j);
            }
        } else {
            i = -1;
        }
        this.pinningTrackingHandle = i;
    }

    private Snapshot(int i, SnapshotIdSet snapshotIdSet) {
        this(i, snapshotIdSet, (DefaultConstructorMarker) null);
    }
}
