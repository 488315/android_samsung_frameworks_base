package com.android.server.pm;

import android.apex.ApexInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManagerNative;
import android.content.pm.IStagedApexObserver;
import android.content.pm.PackageInfo;
import android.content.pm.StagedApexInfo;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Slog;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.pm.PackageInstallerSession;
import com.android.server.pm.StagingManager;
import java.util.ArrayList;
import java.util.Arrays;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class PackageManagerNative extends IPackageManagerNative.Stub {
    public final PackageManagerService mPm;

    public PackageManagerNative(PackageManagerService packageManagerService) {
        this.mPm = packageManagerService;
    }

    public final String getInstallerForPackage(String str) {
        Computer snapshotComputer = this.mPm.snapshotComputer();
        int userId = UserHandle.getUserId(Binder.getCallingUid());
        String installerPackageName = snapshotComputer.getInstallerPackageName(userId, str);
        if (!TextUtils.isEmpty(installerPackageName)) {
            return installerPackageName;
        }
        ApplicationInfo applicationInfo = snapshotComputer.getApplicationInfo(str, 0L, userId);
        return (applicationInfo == null || (applicationInfo.flags & 1) == 0) ? "" : "preload";
    }

    public final int getLocationFlags(String str) {
        ApplicationInfo applicationInfo = this.mPm.snapshotComputer().getApplicationInfo(str, 0L, UserHandle.getUserId(Binder.getCallingUid()));
        if (applicationInfo == null) {
            throw new RemoteException(ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("Couldn't get ApplicationInfo for package ", str));
        }
        return (applicationInfo.isSystemApp() ? 1 : 0) | (applicationInfo.isVendor() ? 2 : 0) | (applicationInfo.isProduct() ? 4 : 0);
    }

    public final String getModuleMetadataPackageName() {
        ModuleInfoProvider moduleInfoProvider = this.mPm.mModuleInfoProvider;
        if (moduleInfoProvider.mMetadataLoaded) {
            return moduleInfoProvider.mPackageName;
        }
        throw new IllegalStateException("Call to getVersion before metadata loaded");
    }

    public final String[] getNamesForUids(int[] iArr) {
        String[] strArr;
        String[] strArr2 = null;
        if (iArr != null) {
            try {
                if (iArr.length != 0) {
                    String[] namesForUids = this.mPm.snapshotComputer().getNamesForUids(iArr);
                    if (namesForUids != null) {
                        strArr2 = namesForUids;
                    } else {
                        try {
                            strArr2 = new String[iArr.length];
                        } catch (Throwable th) {
                            th = th;
                            String[] strArr3 = strArr2;
                            strArr2 = namesForUids;
                            strArr = strArr3;
                            Slog.e("PackageManager", "uids: " + Arrays.toString(iArr));
                            Slog.e("PackageManager", "names: " + Arrays.toString(strArr2));
                            BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(new StringBuilder("results: "), Arrays.toString(strArr), "PackageManager");
                            Slog.e("PackageManager", "throwing exception", th);
                            throw th;
                        }
                    }
                    for (int length = strArr2.length - 1; length >= 0; length--) {
                        if (strArr2[length] == null) {
                            strArr2[length] = "";
                        }
                    }
                    return strArr2;
                }
            } catch (Throwable th2) {
                th = th2;
                strArr = null;
            }
        }
        return null;
    }

    public final int getPackageUid(String str, long j, int i) {
        return this.mPm.snapshotComputer().getPackageUid(str, j, i);
    }

    public final StagedApexInfo getStagedApexInfo(String str) {
        ApexInfo apexInfo;
        StagingManager stagingManager = this.mPm.mInstallerService.mStagingManager;
        synchronized (stagingManager.mStagedSessions) {
            for (int i = 0; i < stagingManager.mStagedSessions.size(); i++) {
                try {
                    PackageInstallerSession.StagedSession stagedSession = (PackageInstallerSession.StagedSession) ((StagingManager.StagedSession) stagingManager.mStagedSessions.valueAt(i));
                    if (stagedSession.isSessionReady() && !PackageInstallerSession.this.isDestroyed() && !PackageInstallerSession.this.hasParentSessionId() && stagedSession.containsApexSession() && (apexInfo = (ApexInfo) stagingManager.getStagedApexInfos(stagedSession).get(str)) != null) {
                        StagedApexInfo stagedApexInfo = new StagedApexInfo();
                        stagedApexInfo.moduleName = apexInfo.moduleName;
                        stagedApexInfo.diskImagePath = apexInfo.modulePath;
                        stagedApexInfo.versionCode = apexInfo.versionCode;
                        stagedApexInfo.versionName = apexInfo.versionName;
                        stagedApexInfo.hasClassPathJars = apexInfo.hasClassPathJars;
                        return stagedApexInfo;
                    }
                } finally {
                }
            }
            return null;
        }
    }

    public final String[] getStagedApexModuleNames() {
        return (String[]) ((ArrayList) this.mPm.mInstallerService.mStagingManager.getStagedApexModuleNames()).toArray(new String[0]);
    }

    public final int getTargetSdkVersionForPackage(String str) {
        int targetSdkVersion = this.mPm.snapshotComputer().getTargetSdkVersion(str);
        if (targetSdkVersion != -1) {
            return targetSdkVersion;
        }
        throw new RemoteException(ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("Couldn't get targetSdkVersion for package ", str));
    }

    public final long getVersionCodeForPackage(String str) {
        try {
            PackageInfo packageInfo = this.mPm.snapshotComputer().getPackageInfo(str, 0L, UserHandle.getUserId(Binder.getCallingUid()));
            if (packageInfo != null) {
                return packageInfo.getLongVersionCode();
            }
        } catch (Exception unused) {
        }
        return 0L;
    }

    public final boolean hasSha256SigningCertificate(String str, byte[] bArr) {
        return this.mPm.snapshotComputer().hasSigningCertificate(str, bArr, 1);
    }

    public final boolean hasSystemFeature(String str, int i) {
        return this.mPm.hasSystemFeature(str, i);
    }

    public final boolean[] isAudioPlaybackCaptureAllowed(String[] strArr) {
        int userId = UserHandle.getUserId(Binder.getCallingUid());
        Computer snapshotComputer = this.mPm.snapshotComputer();
        int length = strArr.length;
        boolean[] zArr = new boolean[length];
        for (int i = length - 1; i >= 0; i--) {
            ApplicationInfo applicationInfo = snapshotComputer.getApplicationInfo(strArr[i], 0L, userId);
            zArr[i] = applicationInfo != null && applicationInfo.isAudioPlaybackCaptureAllowed();
        }
        return zArr;
    }

    public final boolean isPackageDebuggable(String str) {
        ApplicationInfo applicationInfo = this.mPm.snapshotComputer().getApplicationInfo(str, 0L, UserHandle.getCallingUserId());
        if (applicationInfo != null) {
            return (applicationInfo.flags & 2) != 0;
        }
        throw new RemoteException(ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("Couldn't get debug flag for package ", str));
    }

    public final void registerStagedApexObserver(IStagedApexObserver iStagedApexObserver) {
        StagingManager stagingManager = this.mPm.mInstallerService.mStagingManager;
        stagingManager.getClass();
        if (iStagedApexObserver == null) {
            return;
        }
        if (iStagedApexObserver.asBinder() != null) {
            try {
                iStagedApexObserver.asBinder().linkToDeath(new IBinder.DeathRecipient() { // from class: com.android.server.pm.StagingManager.1
                    public final /* synthetic */ IStagedApexObserver val$observer;

                    public AnonymousClass1(IStagedApexObserver iStagedApexObserver2) {
                        r2 = iStagedApexObserver2;
                    }

                    @Override // android.os.IBinder.DeathRecipient
                    public final void binderDied() {
                        synchronized (StagingManager.this.mStagedApexObservers) {
                            ((ArrayList) StagingManager.this.mStagedApexObservers).remove(r2);
                        }
                    }
                }, 0);
            } catch (RemoteException e) {
                Slog.w("StagingManager", e.getMessage());
            }
        }
        synchronized (stagingManager.mStagedApexObservers) {
            ((ArrayList) stagingManager.mStagedApexObservers).add(iStagedApexObserver2);
        }
    }

    public final void unregisterStagedApexObserver(IStagedApexObserver iStagedApexObserver) {
        StagingManager stagingManager = this.mPm.mInstallerService.mStagingManager;
        synchronized (stagingManager.mStagedApexObservers) {
            ((ArrayList) stagingManager.mStagedApexObservers).remove(iStagedApexObserver);
        }
    }
}
