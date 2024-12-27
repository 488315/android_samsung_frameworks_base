package com.android.server.pm;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.DataLoaderParams;
import android.content.pm.IPackageInstallObserver2;
import android.content.pm.PackageInfoLite;
import android.content.pm.PackageInstaller;
import android.content.pm.ResolveInfo;
import android.content.pm.SigningDetails;
import android.content.pm.parsing.PackageLite;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.ArraySet;
import android.util.Slog;

import com.android.internal.util.FrameworkStatsLog;

import java.io.File;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class VerifyingSession {
    public final int mDataLoaderType;
    public final int mInstallFlags;
    public final int mInstallReason;
    public final InstallSource mInstallSource;
    public final boolean mIsInherit;
    public final boolean mIsStaged;
    public final IPackageInstallObserver2 mObserver;
    public final OriginInfo mOriginInfo;
    public final String mPackageAbiOverride;
    public final PackageLite mPackageLite;
    public MultiPackageVerifyingSession mParentVerifyingSession;
    public final PackageManagerService mPm;
    public final long mRequiredInstalledVersionCode;
    public final int mSessionId;
    public final SigningDetails mSigningDetails;
    public final UserHandle mUser;
    public final boolean mUserActionRequired;
    public final int mUserActionRequiredType;
    public final VerificationInfo mVerificationInfo;
    public boolean mWaitForEnableRollbackToComplete;
    public boolean mWaitForIntegrityVerificationToComplete;
    public boolean mWaitForVerificationToComplete;
    public final int sessionFlags;
    public int mRet = 1;
    public String mErrorMessage = null;

    public VerifyingSession(
            UserHandle userHandle,
            File file,
            String str,
            PackageSessionVerifier.AnonymousClass1 anonymousClass1,
            PackageInstaller.SessionParams sessionParams,
            InstallSource installSource,
            int i,
            SigningDetails signingDetails,
            int i2,
            PackageLite packageLite,
            boolean z,
            PackageManagerService packageManagerService) {
        this.sessionFlags = 0;
        this.mPm = packageManagerService;
        this.mUser = userHandle;
        if (file != null) {
            this.mOriginInfo = new OriginInfo(file, true, false, null);
        } else {
            this.mOriginInfo = new OriginInfo(null, true, false, str);
        }
        this.mObserver = anonymousClass1;
        this.mInstallFlags = sessionParams.installFlags;
        this.mInstallSource = installSource;
        this.mPackageAbiOverride = sessionParams.abiOverride;
        this.mVerificationInfo =
                new VerificationInfo(
                        sessionParams.originatingUri,
                        sessionParams.referrerUri,
                        sessionParams.originatingUid,
                        i);
        this.mSigningDetails = signingDetails;
        this.mRequiredInstalledVersionCode = sessionParams.requiredInstalledVersionCode;
        DataLoaderParams dataLoaderParams = sessionParams.dataLoaderParams;
        this.mDataLoaderType = dataLoaderParams != null ? dataLoaderParams.getType() : 0;
        this.mSessionId = i2;
        this.sessionFlags = sessionParams.sessionFlags;
        this.mPackageLite = packageLite;
        this.mUserActionRequired = z;
        this.mUserActionRequiredType = sessionParams.requireUserAction;
        this.mIsInherit = sessionParams.mode == 2;
        this.mIsStaged = sessionParams.isStaged;
        this.mInstallReason = sessionParams.installReason;
    }

    public static ComponentName matchComponentForVerifier(String str, List list) {
        ActivityInfo activityInfo;
        int size = list.size();
        int i = 0;
        while (true) {
            if (i >= size) {
                activityInfo = null;
                break;
            }
            ResolveInfo resolveInfo = (ResolveInfo) list.get(i);
            ActivityInfo activityInfo2 = resolveInfo.activityInfo;
            if (activityInfo2 != null && str.equals(activityInfo2.packageName)) {
                activityInfo = resolveInfo.activityInfo;
                break;
            }
            i++;
        }
        if (activityInfo == null) {
            return null;
        }
        return new ComponentName(activityInfo.packageName, activityInfo.name);
    }

    public final int getDefaultVerificationResponse() {
        PackageManagerService packageManagerService = this.mPm;
        if (packageManagerService.mUserManager.hasUserRestriction(
                "ensure_verify_apps", this.mUser.getIdentifier())) {
            return -1;
        }
        return Settings.Global.getInt(
                packageManagerService.mContext.getContentResolver(),
                "verifier_default_response",
                1);
    }

    public final void handleReturnCode() {
        int i;
        String str;
        if (this.mWaitForVerificationToComplete
                || this.mWaitForIntegrityVerificationToComplete
                || this.mWaitForEnableRollbackToComplete) {
            return;
        }
        MultiPackageVerifyingSession multiPackageVerifyingSession = this.mParentVerifyingSession;
        if (multiPackageVerifyingSession != null) {
            ((ArraySet) multiPackageVerifyingSession.mVerificationState).add(this);
            if (((ArraySet) multiPackageVerifyingSession.mVerificationState).size()
                    == multiPackageVerifyingSession.mChildVerifyingSessions.size()) {
                Iterator it =
                        ((ArraySet) multiPackageVerifyingSession.mVerificationState).iterator();
                while (true) {
                    if (!it.hasNext()) {
                        i = 1;
                        str = null;
                        break;
                    } else {
                        VerifyingSession verifyingSession = (VerifyingSession) it.next();
                        i = verifyingSession.mRet;
                        if (i != 1) {
                            str = verifyingSession.mErrorMessage;
                            break;
                        }
                    }
                }
                try {
                    multiPackageVerifyingSession.mObserver.onPackageInstalled(
                            (String) null, i, str, new Bundle());
                } catch (RemoteException unused) {
                    Slog.i("PackageManager", "Observer no longer exists.");
                }
            }
        } else {
            try {
                this.mObserver.onPackageInstalled(
                        (String) null, this.mRet, this.mErrorMessage, new Bundle());
            } catch (RemoteException unused2) {
                Slog.i("PackageManager", "Observer no longer exists.");
            }
        }
        int i2 = this.mRet;
        if (i2 != 1) {
            FrameworkStatsLog.write(
                    524,
                    this.mSessionId,
                    (String) null,
                    -1,
                    (int[]) null,
                    (int[]) null,
                    (int[]) null,
                    (int[]) null,
                    i2,
                    0,
                    0L,
                    0L,
                    (int[]) null,
                    (long[]) null,
                    0L,
                    0,
                    this.mInstallSource.mInstallerPackageUid,
                    -1,
                    this.mDataLoaderType,
                    this.mUserActionRequiredType,
                    (this.mInstallFlags & 2048) != 0,
                    false,
                    false,
                    this.mIsInherit,
                    false,
                    false,
                    this.mIsStaged);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:208:0x059a, code lost:

       if (r9.contains(r2.packageName) != false) goto L211;
    */
    /* JADX WARN: Removed duplicated region for block: B:100:0x072c  */
    /* JADX WARN: Removed duplicated region for block: B:102:0x0262  */
    /* JADX WARN: Removed duplicated region for block: B:155:0x04ba  */
    /* JADX WARN: Removed duplicated region for block: B:157:0x04c3 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:213:0x05b3  */
    /* JADX WARN: Removed duplicated region for block: B:243:0x021e  */
    /* JADX WARN: Removed duplicated region for block: B:275:0x06d4  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0188  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x0219  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x025e  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x06dd  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x06e7  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x06f4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void handleStartVerify() {
        /*
            Method dump skipped, instructions count: 1957
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.VerifyingSession.handleStartVerify():void");
    }

    public final boolean isAdbVerificationEnabled(
            PackageInfoLite packageInfoLite, int i, boolean z) {
        PackageManagerService packageManagerService = this.mPm;
        boolean z2 =
                Settings.Global.getInt(
                                packageManagerService.mContext.getContentResolver(),
                                "verifier_verify_adb_installs",
                                1)
                        != 0;
        if (packageManagerService.isUserRestricted(i, "ensure_verify_apps")) {
            if (!z2) {
                Slog.w(
                        "PackageManager",
                        "Force verification of ADB install because of user restriction.");
            }
            return true;
        }
        if (!z2) {
            return false;
        }
        if (z) {
            if (packageManagerService
                            .snapshotComputer()
                            .getPackageStateInternal(packageInfoLite.packageName)
                    != null) {
                return !packageInfoLite.debuggable;
            }
        }
        return true;
    }

    public final void populateInstallerExtras(Intent intent) {
        intent.putExtra(
                "android.content.pm.extra.VERIFICATION_INSTALLER_PACKAGE",
                this.mInstallSource.mInitiatingPackageName);
        VerificationInfo verificationInfo = this.mVerificationInfo;
        if (verificationInfo != null) {
            Uri uri = verificationInfo.mOriginatingUri;
            if (uri != null) {
                intent.putExtra("android.intent.extra.ORIGINATING_URI", uri);
            }
            Uri uri2 = verificationInfo.mReferrer;
            if (uri2 != null) {
                intent.putExtra("android.intent.extra.REFERRER", uri2);
            }
            int i = verificationInfo.mOriginatingUid;
            if (i >= 0) {
                intent.putExtra("android.intent.extra.ORIGINATING_UID", i);
            }
            int i2 = verificationInfo.mInstallerUid;
            if (i2 >= 0) {
                intent.putExtra("android.content.pm.extra.VERIFICATION_INSTALLER_UID", i2);
            }
        }
    }

    public final void setReturnCode(int i, String str) {
        if (this.mRet == 1) {
            this.mRet = i;
            this.mErrorMessage = str;
        }
    }

    public final String toString() {
        return "VerifyingSession{"
                + Integer.toHexString(System.identityHashCode(this))
                + " file="
                + this.mOriginInfo.mFile
                + "}";
    }
}
