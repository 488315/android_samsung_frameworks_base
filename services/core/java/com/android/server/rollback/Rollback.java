package com.android.server.rollback;

import android.content.pm.PackageManagerInternal;
import android.content.pm.VersionedPackage;
import android.content.rollback.PackageRollbackInfo;
import android.content.rollback.RollbackInfo;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.os.Handler;
import android.os.Looper;
import android.util.SparseIntArray;

import com.android.internal.hidden_from_bootclasspath.android.content.pm.Flags;
import com.android.internal.util.IndentingPrintWriter;
import com.android.internal.util.Preconditions;

import java.io.File;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class Rollback {
    public final RollbackInfo info;
    public final File mBackupDir;
    public final SparseIntArray mExtensionVersions;
    public final Handler mHandler;
    public final String mInstallerPackageName;
    public final int mOriginalSessionId;
    public final int[] mPackageSessionIds;
    public boolean mRestoreUserDataInProgress;
    public long mRollbackLifetimeMillis;
    public int mState;
    public String mStateDescription;
    public Instant mTimestamp;
    public final int mUserId;

    public Rollback(
            int i,
            File file,
            int i2,
            boolean z,
            int i3,
            String str,
            int[] iArr,
            SparseIntArray sparseIntArray) {
        this.mStateDescription = "";
        this.mRestoreUserDataInProgress = false;
        this.mRollbackLifetimeMillis = 0L;
        this.info = new RollbackInfo(i, new ArrayList(), z, new ArrayList(), -1, 0);
        this.mUserId = i3;
        this.mInstallerPackageName = str;
        this.mBackupDir = file;
        this.mOriginalSessionId = i2;
        this.mState = 0;
        this.mTimestamp = Instant.now();
        this.mPackageSessionIds = iArr != null ? iArr : new int[0];
        this.mExtensionVersions = sparseIntArray;
        this.mHandler = Looper.myLooper() != null ? new Handler(Looper.myLooper()) : null;
    }

    public Rollback(
            RollbackInfo rollbackInfo,
            File file,
            Instant instant,
            int i,
            int i2,
            String str,
            boolean z,
            int i3,
            String str2,
            SparseIntArray sparseIntArray) {
        this.mRollbackLifetimeMillis = 0L;
        this.info = rollbackInfo;
        this.mUserId = i3;
        this.mInstallerPackageName = str2;
        this.mBackupDir = file;
        this.mTimestamp = instant;
        this.mOriginalSessionId = i;
        this.mState = i2;
        this.mStateDescription = str;
        this.mRestoreUserDataInProgress = z;
        this.mExtensionVersions = sparseIntArray;
        this.mPackageSessionIds = new int[0];
        this.mHandler = Looper.myLooper() != null ? new Handler(Looper.myLooper()) : null;
    }

    public static boolean extensionVersionReductionWouldViolateConstraint(
            SparseIntArray sparseIntArray, PackageManagerInternal packageManagerInternal) {
        if (sparseIntArray.size() == 0) {
            return false;
        }
        List list = packageManagerInternal.getPackageList(null).mPackageNames;
        for (int i = 0; i < list.size(); i++) {
            SparseIntArray minExtensionVersions =
                    packageManagerInternal
                            .getPackage((String) list.get(i))
                            .getMinExtensionVersions();
            if (minExtensionVersions != null) {
                for (int i2 = 0; i2 < sparseIntArray.size(); i2++) {
                    if (sparseIntArray.valueAt(i2)
                            < minExtensionVersions.get(sparseIntArray.keyAt(i2), -1)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public final boolean allPackagesEnabled() {
        assertInWorkerThread();
        Iterator it = this.info.getPackages().iterator();
        int i = 0;
        while (it.hasNext()) {
            if (!((PackageRollbackInfo) it.next()).isApkInApex()) {
                i++;
            }
        }
        return i == this.mPackageSessionIds.length;
    }

    public final void assertInWorkerThread() {
        Handler handler = this.mHandler;
        Preconditions.checkState(handler == null || handler.getLooper().isCurrentThread());
    }

    public final void dump(IndentingPrintWriter indentingPrintWriter) {
        assertInWorkerThread();
        indentingPrintWriter.println(this.info.getRollbackId() + ":");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("-state: ".concat(getStateAsString()));
        indentingPrintWriter.println("-stateDescription: " + this.mStateDescription);
        StringBuilder sb = new StringBuilder("-timestamp: ");
        assertInWorkerThread();
        sb.append(this.mTimestamp);
        indentingPrintWriter.println(sb.toString());
        StringBuilder sb2 = new StringBuilder("-rollbackLifetimeMillis: ");
        assertInWorkerThread();
        sb2.append(this.mRollbackLifetimeMillis);
        indentingPrintWriter.println(sb2.toString());
        if (Flags.recoverabilityDetection()) {
            indentingPrintWriter.println(
                    "-rollbackImpactLevel: " + this.info.getRollbackImpactLevel());
        }
        indentingPrintWriter.println("-isStaged: " + this.info.isStaged());
        indentingPrintWriter.println("-originalSessionId: " + this.mOriginalSessionId);
        indentingPrintWriter.println("-packages:");
        indentingPrintWriter.increaseIndent();
        for (PackageRollbackInfo packageRollbackInfo : this.info.getPackages()) {
            indentingPrintWriter.println(
                    packageRollbackInfo.getPackageName()
                            + " "
                            + packageRollbackInfo.getVersionRolledBackFrom().getLongVersionCode()
                            + " -> "
                            + packageRollbackInfo.getVersionRolledBackTo().getLongVersionCode()
                            + " ["
                            + packageRollbackInfo.getRollbackDataPolicy()
                            + "]");
        }
        indentingPrintWriter.decreaseIndent();
        if (isCommitted()) {
            indentingPrintWriter.println("-causePackages:");
            indentingPrintWriter.increaseIndent();
            for (VersionedPackage versionedPackage : this.info.getCausePackages()) {
                indentingPrintWriter.println(
                        versionedPackage.getPackageName()
                                + " "
                                + versionedPackage.getLongVersionCode());
            }
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println(
                    "-committedSessionId: " + this.info.getCommittedSessionId());
        }
        if (this.mExtensionVersions.size() > 0) {
            indentingPrintWriter.println("-extensionVersions:");
            indentingPrintWriter.increaseIndent();
            indentingPrintWriter.println(this.mExtensionVersions.toString());
            indentingPrintWriter.decreaseIndent();
        }
        indentingPrintWriter.decreaseIndent();
    }

    public final List getPackageNames() {
        assertInWorkerThread();
        ArrayList arrayList = new ArrayList();
        Iterator it = this.info.getPackages().iterator();
        while (it.hasNext()) {
            arrayList.add(((PackageRollbackInfo) it.next()).getPackageName());
        }
        return arrayList;
    }

    public final String getStateAsString() {
        assertInWorkerThread();
        int i = this.mState;
        if (i == 0) {
            return "enabling";
        }
        if (i == 1) {
            return "available";
        }
        if (i == 3) {
            return "committed";
        }
        if (i == 4) {
            return "deleted";
        }
        throw new AssertionError(
                VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Invalid rollback state: "));
    }

    public final boolean isAvailable() {
        assertInWorkerThread();
        return this.mState == 1;
    }

    public final boolean isCommitted() {
        assertInWorkerThread();
        return this.mState == 3;
    }

    public final boolean isEnabling() {
        assertInWorkerThread();
        return this.mState == 0;
    }

    public void setState(int i, String str) {
        assertInWorkerThread();
        this.mState = i;
        this.mStateDescription = str;
    }
}
