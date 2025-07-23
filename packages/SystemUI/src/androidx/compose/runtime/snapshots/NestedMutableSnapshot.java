package androidx.compose.runtime.snapshots;

import androidx.collection.MutableScatterSet;
import androidx.collection.ScatterSet;
import androidx.compose.runtime.snapshots.SnapshotApplyResult;
import java.util.Arrays;
import java.util.Map;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysJvmKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class NestedMutableSnapshot extends MutableSnapshot {
    public boolean deactivated;
    public final MutableSnapshot parent;

    public NestedMutableSnapshot(long j, SnapshotIdSet snapshotIdSet, Function1 function1, Function1 function12, MutableSnapshot mutableSnapshot) {
        super(j, snapshotIdSet, function1, function12);
        this.parent = mutableSnapshot;
        mutableSnapshot.nestedActivated$runtime_release();
    }

    @Override // androidx.compose.runtime.snapshots.MutableSnapshot
    public final SnapshotApplyResult apply() {
        NestedMutableSnapshot nestedMutableSnapshot;
        MutableSnapshot mutableSnapshot = this.parent;
        if (mutableSnapshot.applied || mutableSnapshot.disposed) {
            return new SnapshotApplyResult.Failure(this);
        }
        MutableScatterSet mutableScatterSet = this.modified;
        long j = this.snapshotId;
        Map access$optimisticMerges = mutableScatterSet != null ? SnapshotKt.access$optimisticMerges(mutableSnapshot.getSnapshotId(), this, this.parent.getInvalid$runtime_release()) : null;
        Object obj = SnapshotKt.lock;
        synchronized (obj) {
            try {
                SnapshotKt.access$validateOpen(this);
                if (mutableScatterSet == null || mutableScatterSet._size == 0) {
                    nestedMutableSnapshot = this;
                    nestedMutableSnapshot.closeAndReleasePinning$runtime_release();
                } else {
                    nestedMutableSnapshot = this;
                    SnapshotApplyResult innerApplyLocked$runtime_release = nestedMutableSnapshot.innerApplyLocked$runtime_release(this.parent.getSnapshotId(), mutableScatterSet, access$optimisticMerges, this.parent.getInvalid$runtime_release());
                    if (!Intrinsics.areEqual(innerApplyLocked$runtime_release, SnapshotApplyResult.Success.INSTANCE)) {
                        return innerApplyLocked$runtime_release;
                    }
                    MutableScatterSet modified$runtime_release = nestedMutableSnapshot.parent.getModified$runtime_release();
                    if (modified$runtime_release != null) {
                        modified$runtime_release.plusAssign((ScatterSet) mutableScatterSet);
                    } else {
                        nestedMutableSnapshot.parent.setModified(mutableScatterSet);
                        nestedMutableSnapshot.modified = null;
                    }
                }
                if (nestedMutableSnapshot.parent.getSnapshotId() < j) {
                    nestedMutableSnapshot.parent.advance$runtime_release();
                }
                MutableSnapshot mutableSnapshot2 = nestedMutableSnapshot.parent;
                mutableSnapshot2.setInvalid$runtime_release(mutableSnapshot2.getInvalid$runtime_release().clear(j).andNot(nestedMutableSnapshot.previousIds));
                nestedMutableSnapshot.parent.recordPrevious$runtime_release(j);
                MutableSnapshot mutableSnapshot3 = nestedMutableSnapshot.parent;
                int i = nestedMutableSnapshot.pinningTrackingHandle;
                nestedMutableSnapshot.pinningTrackingHandle = -1;
                if (i >= 0) {
                    int[] iArr = mutableSnapshot3.previousPinnedSnapshots;
                    int length = iArr.length;
                    int[] copyOf = Arrays.copyOf(iArr, length + 1);
                    copyOf[length] = i;
                    mutableSnapshot3.previousPinnedSnapshots = copyOf;
                } else {
                    mutableSnapshot3.getClass();
                }
                MutableSnapshot mutableSnapshot4 = nestedMutableSnapshot.parent;
                SnapshotIdSet snapshotIdSet = nestedMutableSnapshot.previousIds;
                mutableSnapshot4.getClass();
                synchronized (obj) {
                    mutableSnapshot4.previousIds = mutableSnapshot4.previousIds.or(snapshotIdSet);
                    Unit unit = Unit.INSTANCE;
                    MutableSnapshot mutableSnapshot5 = nestedMutableSnapshot.parent;
                    int[] iArr2 = nestedMutableSnapshot.previousPinnedSnapshots;
                    mutableSnapshot5.getClass();
                    if (iArr2.length != 0) {
                        int[] iArr3 = mutableSnapshot5.previousPinnedSnapshots;
                        if (iArr3.length != 0) {
                            iArr2 = ArraysKt___ArraysJvmKt.plus(iArr3, iArr2);
                        }
                        mutableSnapshot5.previousPinnedSnapshots = iArr2;
                    }
                }
                nestedMutableSnapshot.applied = true;
                if (!nestedMutableSnapshot.deactivated) {
                    nestedMutableSnapshot.deactivated = true;
                    nestedMutableSnapshot.parent.nestedDeactivated$runtime_release();
                }
                return SnapshotApplyResult.Success.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // androidx.compose.runtime.snapshots.MutableSnapshot, androidx.compose.runtime.snapshots.Snapshot
    public final void dispose() {
        if (this.disposed) {
            return;
        }
        super.dispose();
        if (this.deactivated) {
            return;
        }
        this.deactivated = true;
        this.parent.nestedDeactivated$runtime_release();
    }
}
