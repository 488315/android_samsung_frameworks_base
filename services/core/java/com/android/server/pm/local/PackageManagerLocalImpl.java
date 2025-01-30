package com.android.server.pm.local;

import android.os.Binder;
import android.os.UserHandle;
import android.util.ArrayMap;
import com.android.server.pm.Computer;
import com.android.server.pm.PackageManagerLocal;
import com.android.server.pm.PackageManagerService;
import com.android.server.pm.pkg.PackageState;
import com.android.server.pm.pkg.PackageStateInternal;
import com.android.server.pm.snapshot.PackageDataSnapshot;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public class PackageManagerLocalImpl implements PackageManagerLocal {
    public final PackageManagerService mService;

    public PackageManagerLocalImpl(PackageManagerService packageManagerService) {
        this.mService = packageManagerService;
    }

    @Override // com.android.server.pm.PackageManagerLocal
    public void reconcileSdkData(String str, String str2, List list, int i, int i2, int i3, String str3, int i4) {
        this.mService.reconcileSdkData(str, str2, list, i, i2, i3, str3, i4);
    }

    @Override // com.android.server.pm.PackageManagerLocal
    public UnfilteredSnapshotImpl withUnfilteredSnapshot() {
        return new UnfilteredSnapshotImpl(this.mService.snapshotComputer(false));
    }

    @Override // com.android.server.pm.PackageManagerLocal
    public FilteredSnapshotImpl withFilteredSnapshot() {
        return withFilteredSnapshot(Binder.getCallingUid(), Binder.getCallingUserHandle());
    }

    @Override // com.android.server.pm.PackageManagerLocal
    public FilteredSnapshotImpl withFilteredSnapshot(int i, UserHandle userHandle) {
        return new FilteredSnapshotImpl(i, userHandle, this.mService.snapshotComputer(false), null);
    }

    public abstract class BaseSnapshotImpl implements AutoCloseable {
        public boolean mClosed;
        public Computer mSnapshot;

        public BaseSnapshotImpl(PackageDataSnapshot packageDataSnapshot) {
            this.mSnapshot = (Computer) packageDataSnapshot;
        }

        @Override // java.lang.AutoCloseable
        public void close() {
            this.mClosed = true;
            this.mSnapshot = null;
        }

        public void checkClosed() {
            if (this.mClosed) {
                throw new IllegalStateException("Snapshot already closed");
            }
        }
    }

    public class UnfilteredSnapshotImpl extends BaseSnapshotImpl implements PackageManagerLocal.UnfilteredSnapshot {
        public Map mCachedUnmodifiableDisabledSystemPackageStates;
        public Map mCachedUnmodifiablePackageStates;

        public UnfilteredSnapshotImpl(PackageDataSnapshot packageDataSnapshot) {
            super(packageDataSnapshot);
        }

        @Override // com.android.server.pm.PackageManagerLocal.UnfilteredSnapshot
        public PackageManagerLocal.FilteredSnapshot filtered(int i, UserHandle userHandle) {
            return new FilteredSnapshotImpl(i, userHandle, this.mSnapshot, this);
        }

        @Override // com.android.server.pm.PackageManagerLocal.UnfilteredSnapshot
        public Map getPackageStates() {
            checkClosed();
            if (this.mCachedUnmodifiablePackageStates == null) {
                this.mCachedUnmodifiablePackageStates = Collections.unmodifiableMap(this.mSnapshot.getPackageStates());
            }
            return this.mCachedUnmodifiablePackageStates;
        }

        @Override // com.android.server.pm.PackageManagerLocal.UnfilteredSnapshot
        public Map getDisabledSystemPackageStates() {
            checkClosed();
            if (this.mCachedUnmodifiableDisabledSystemPackageStates == null) {
                this.mCachedUnmodifiableDisabledSystemPackageStates = Collections.unmodifiableMap(this.mSnapshot.getDisabledSystemPackageStates());
            }
            return this.mCachedUnmodifiableDisabledSystemPackageStates;
        }

        @Override // com.android.server.pm.local.PackageManagerLocalImpl.BaseSnapshotImpl, java.lang.AutoCloseable
        public void close() {
            super.close();
            this.mCachedUnmodifiablePackageStates = null;
            this.mCachedUnmodifiableDisabledSystemPackageStates = null;
        }
    }

    public class FilteredSnapshotImpl extends BaseSnapshotImpl implements PackageManagerLocal.FilteredSnapshot {
        public final int mCallingUid;
        public Map mFilteredPackageStates;
        public final UnfilteredSnapshotImpl mParentSnapshot;
        public final int mUserId;

        public FilteredSnapshotImpl(int i, UserHandle userHandle, PackageDataSnapshot packageDataSnapshot, UnfilteredSnapshotImpl unfilteredSnapshotImpl) {
            super(packageDataSnapshot);
            this.mCallingUid = i;
            this.mUserId = userHandle.getIdentifier();
            this.mParentSnapshot = unfilteredSnapshotImpl;
        }

        @Override // com.android.server.pm.local.PackageManagerLocalImpl.BaseSnapshotImpl
        public void checkClosed() {
            UnfilteredSnapshotImpl unfilteredSnapshotImpl = this.mParentSnapshot;
            if (unfilteredSnapshotImpl != null) {
                unfilteredSnapshotImpl.checkClosed();
            }
            super.checkClosed();
        }

        @Override // com.android.server.pm.local.PackageManagerLocalImpl.BaseSnapshotImpl, java.lang.AutoCloseable
        public void close() {
            super.close();
            this.mFilteredPackageStates = null;
        }

        @Override // com.android.server.pm.PackageManagerLocal.FilteredSnapshot
        public PackageState getPackageState(String str) {
            checkClosed();
            return this.mSnapshot.getPackageStateFiltered(str, this.mCallingUid, this.mUserId);
        }

        @Override // com.android.server.pm.PackageManagerLocal.FilteredSnapshot
        public Map getPackageStates() {
            checkClosed();
            if (this.mFilteredPackageStates == null) {
                ArrayMap packageStates = this.mSnapshot.getPackageStates();
                ArrayMap arrayMap = new ArrayMap();
                int size = packageStates.size();
                for (int i = 0; i < size; i++) {
                    PackageStateInternal packageStateInternal = (PackageStateInternal) packageStates.valueAt(i);
                    if (!this.mSnapshot.shouldFilterApplication(packageStateInternal, this.mCallingUid, this.mUserId)) {
                        arrayMap.put((String) packageStates.keyAt(i), packageStateInternal);
                    }
                }
                this.mFilteredPackageStates = Collections.unmodifiableMap(arrayMap);
            }
            return this.mFilteredPackageStates;
        }
    }
}
