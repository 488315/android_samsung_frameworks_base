package com.android.server.pm.local;

import android.content.pm.SigningDetails;
import android.os.Binder;
import android.os.Build;
import android.os.ReconcileSdkDataArgs;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.apk.ApkSignatureVerifier;

import com.android.server.pm.Computer;
import com.android.server.pm.Installer;
import com.android.server.pm.PackageManagerLocal;
import com.android.server.pm.PackageManagerService;
import com.android.server.pm.PackageManagerTracedLock;
import com.android.server.pm.pkg.PackageState;
import com.android.server.pm.pkg.PackageStateInternal;
import com.android.server.pm.snapshot.PackageDataSnapshot;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class PackageManagerLocalImpl implements PackageManagerLocal {
    public final PackageManagerService mService;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class BaseSnapshotImpl implements AutoCloseable {
        public boolean mClosed;
        public Computer mSnapshot;

        public BaseSnapshotImpl(PackageDataSnapshot packageDataSnapshot) {
            this.mSnapshot = (Computer) packageDataSnapshot;
        }

        public void checkClosed() {
            if (this.mClosed) {
                throw new IllegalStateException("Snapshot already closed");
            }
        }

        @Override // java.lang.AutoCloseable
        public void close() {
            this.mClosed = true;
            this.mSnapshot = null;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class FilteredSnapshotImpl extends BaseSnapshotImpl
            implements PackageManagerLocal.FilteredSnapshot {
        public final int mCallingUid;
        public Map mFilteredPackageStates;
        public final UnfilteredSnapshotImpl mParentSnapshot;
        public final int mUserId;

        public FilteredSnapshotImpl(
                int i,
                UserHandle userHandle,
                PackageDataSnapshot packageDataSnapshot,
                UnfilteredSnapshotImpl unfilteredSnapshotImpl) {
            super(packageDataSnapshot);
            this.mCallingUid = i;
            this.mUserId = userHandle.getIdentifier();
            this.mParentSnapshot = unfilteredSnapshotImpl;
        }

        @Override // com.android.server.pm.local.PackageManagerLocalImpl.BaseSnapshotImpl
        public final void checkClosed() {
            UnfilteredSnapshotImpl unfilteredSnapshotImpl = this.mParentSnapshot;
            if (unfilteredSnapshotImpl != null) {
                unfilteredSnapshotImpl.checkClosed();
            }
            super.checkClosed();
        }

        @Override // com.android.server.pm.local.PackageManagerLocalImpl.BaseSnapshotImpl,
                  // java.lang.AutoCloseable
        public final void close() {
            super.close();
            this.mFilteredPackageStates = null;
        }

        @Override // com.android.server.pm.PackageManagerLocal.FilteredSnapshot
        public final PackageState getPackageState(String str) {
            checkClosed();
            return this.mSnapshot.getPackageStateFiltered(this.mCallingUid, this.mUserId, str);
        }

        @Override // com.android.server.pm.PackageManagerLocal.FilteredSnapshot
        public final Map getPackageStates() {
            checkClosed();
            if (this.mFilteredPackageStates == null) {
                ArrayMap packageStates = this.mSnapshot.getPackageStates();
                ArrayMap arrayMap = new ArrayMap();
                int size = packageStates.size();
                for (int i = 0; i < size; i++) {
                    PackageStateInternal packageStateInternal =
                            (PackageStateInternal) packageStates.valueAt(i);
                    if (!this.mSnapshot.shouldFilterApplication(
                            packageStateInternal, this.mCallingUid, this.mUserId)) {
                        arrayMap.put((String) packageStates.keyAt(i), packageStateInternal);
                    }
                }
                this.mFilteredPackageStates = Collections.unmodifiableMap(arrayMap);
            }
            return this.mFilteredPackageStates;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class UnfilteredSnapshotImpl extends BaseSnapshotImpl
            implements PackageManagerLocal.UnfilteredSnapshot {
        public Map mCachedUnmodifiableDisabledSystemPackageStates;
        public Map mCachedUnmodifiablePackageStates;
        public Map mCachedUnmodifiableSharedUsers;

        @Override // com.android.server.pm.local.PackageManagerLocalImpl.BaseSnapshotImpl,
                  // java.lang.AutoCloseable
        public final void close() {
            super.close();
            this.mCachedUnmodifiablePackageStates = null;
            this.mCachedUnmodifiableDisabledSystemPackageStates = null;
        }

        @Override // com.android.server.pm.PackageManagerLocal.UnfilteredSnapshot
        public final PackageManagerLocal.FilteredSnapshot filtered(int i, UserHandle userHandle) {
            return new FilteredSnapshotImpl(i, userHandle, this.mSnapshot, this);
        }

        @Override // com.android.server.pm.PackageManagerLocal.UnfilteredSnapshot
        public final Map getDisabledSystemPackageStates() {
            checkClosed();
            if (this.mCachedUnmodifiableDisabledSystemPackageStates == null) {
                this.mCachedUnmodifiableDisabledSystemPackageStates =
                        Collections.unmodifiableMap(
                                this.mSnapshot.getDisabledSystemPackageStates());
            }
            return this.mCachedUnmodifiableDisabledSystemPackageStates;
        }

        @Override // com.android.server.pm.PackageManagerLocal.UnfilteredSnapshot
        public final Map getPackageStates() {
            checkClosed();
            if (this.mCachedUnmodifiablePackageStates == null) {
                this.mCachedUnmodifiablePackageStates =
                        Collections.unmodifiableMap(this.mSnapshot.getPackageStates());
            }
            return this.mCachedUnmodifiablePackageStates;
        }

        @Override // com.android.server.pm.PackageManagerLocal.UnfilteredSnapshot
        public final Map getSharedUsers() {
            checkClosed();
            if (this.mCachedUnmodifiableSharedUsers == null) {
                this.mCachedUnmodifiableSharedUsers =
                        Collections.unmodifiableMap(this.mSnapshot.getSharedUsers());
            }
            return this.mCachedUnmodifiableSharedUsers;
        }
    }

    public PackageManagerLocalImpl(PackageManagerService packageManagerService) {
        this.mService = packageManagerService;
    }

    @Override // com.android.server.pm.PackageManagerLocal
    public final void addOverrideSigningDetails(
            SigningDetails signingDetails, SigningDetails signingDetails2) {
        if (!Build.isDebuggable()) {
            throw new SecurityException("This test API is only available on debuggable builds");
        }
        ApkSignatureVerifier.addOverrideSigningDetails(signingDetails, signingDetails2);
    }

    @Override // com.android.server.pm.PackageManagerLocal
    public final void clearOverrideSigningDetails() {
        if (!Build.isDebuggable()) {
            throw new SecurityException("This test API is only available on debuggable builds");
        }
        ApkSignatureVerifier.clearOverrideSigningDetails();
    }

    @Override // com.android.server.pm.PackageManagerLocal
    public final void reconcileSdkData(
            String str, String str2, List list, int i, int i2, int i3, String str3, int i4) {
        PackageManagerService packageManagerService = this.mService;
        packageManagerService.getClass();
        int i5 = Installer.$r8$clinit;
        ReconcileSdkDataArgs reconcileSdkDataArgs = new ReconcileSdkDataArgs();
        reconcileSdkDataArgs.uuid = str;
        reconcileSdkDataArgs.packageName = str2;
        reconcileSdkDataArgs.subDirNames = list;
        reconcileSdkDataArgs.userId = i;
        reconcileSdkDataArgs.appId = i2;
        reconcileSdkDataArgs.seInfo = str3;
        reconcileSdkDataArgs.flags = i4;
        reconcileSdkDataArgs.previousAppId = i3;
        try {
            PackageManagerTracedLock packageManagerTracedLock = packageManagerService.mInstallLock;
            packageManagerTracedLock.mLock.lock();
            try {
                Installer installer = packageManagerService.mInstaller;
                if (installer.checkBeforeRemote()) {
                    try {
                        installer.mInstalld.reconcileSdkData(reconcileSdkDataArgs);
                    } catch (Exception e) {
                        Installer.InstallerException.from(e);
                        throw null;
                    }
                }
                packageManagerTracedLock.close();
            } finally {
            }
        } catch (Installer.InstallerException e2) {
            throw new IOException(e2.getMessage());
        }
    }

    @Override // com.android.server.pm.PackageManagerLocal
    public final void removeOverrideSigningDetails(SigningDetails signingDetails) {
        if (!Build.isDebuggable()) {
            throw new SecurityException("This test API is only available on debuggable builds");
        }
        ApkSignatureVerifier.removeOverrideSigningDetails(signingDetails);
    }

    @Override // com.android.server.pm.PackageManagerLocal
    public final PackageManagerLocal.FilteredSnapshot withFilteredSnapshot() {
        return new FilteredSnapshotImpl(
                Binder.getCallingUid(),
                Binder.getCallingUserHandle(),
                this.mService.snapshotComputer(false),
                null);
    }

    @Override // com.android.server.pm.PackageManagerLocal
    public final PackageManagerLocal.FilteredSnapshot withFilteredSnapshot(
            int i, UserHandle userHandle) {
        return new FilteredSnapshotImpl(i, userHandle, this.mService.snapshotComputer(false), null);
    }

    @Override // com.android.server.pm.PackageManagerLocal
    public final PackageManagerLocal.UnfilteredSnapshot withUnfilteredSnapshot() {
        return new UnfilteredSnapshotImpl(this.mService.snapshotComputer(false));
    }
}
