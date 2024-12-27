package com.android.server.pm.dex;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.IPackageManager;
import android.content.pm.dex.ArtManagerInternal;
import android.content.pm.dex.IArtManager;
import android.content.pm.dex.ISnapshotRuntimeProfileCallback;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.util.Log;
import android.util.Slog;

import com.android.internal.os.BackgroundThread;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;

import libcore.io.IoUtils;

public final class ArtManagerService extends IArtManager.Stub {
    public static final boolean DEBUG = Log.isLoggable("ArtManagerService", 3);
    public final Context mContext;
    public final Handler mHandler = new Handler(BackgroundThread.getHandler().getLooper());
    public IPackageManager mPackageManager;

    public final class ArtManagerInternalImpl extends ArtManagerInternal {
        /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
        java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
        	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
        	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final android.content.pm.dex.PackageOptimizationInfo getPackageOptimizationInfo(
                android.content.pm.ApplicationInfo r23,
                java.lang.String r24,
                java.lang.String r25) {
            /*
                Method dump skipped, instructions count: 900
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.pm.dex.ArtManagerService.ArtManagerInternalImpl.getPackageOptimizationInfo(android.content.pm.ApplicationInfo,"
                        + " java.lang.String,"
                        + " java.lang.String):android.content.pm.dex.PackageOptimizationInfo");
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0029, code lost:

       throw new java.lang.IllegalArgumentException("Compilation reason not configured for TRON logging: ".concat(r1));
    */
    static {
        /*
            java.lang.String r0 = "ArtManagerService"
            r1 = 3
            boolean r0 = android.util.Log.isLoggable(r0, r1)
            com.android.server.pm.dex.ArtManagerService.DEBUG = r0
            r0 = 0
        La:
            java.lang.String[] r1 = com.android.server.pm.PackageManagerServiceCompilerMapping.REASON_STRINGS
            r2 = 15
            if (r0 >= r2) goto L2a
            r1 = r1[r0]
            int r2 = getCompilationReasonTronValue(r1)
            if (r2 == 0) goto L1e
            r3 = 1
            if (r2 == r3) goto L1e
            int r0 = r0 + 1
            goto La
        L1e:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r2 = "Compilation reason not configured for TRON logging: "
            java.lang.String r1 = r2.concat(r1)
            r0.<init>(r1)
            throw r0
        L2a:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.dex.ArtManagerService.<clinit>():void");
    }

    public ArtManagerService(Context context) {
        this.mContext = context;
        LocalServices.addService(ArtManagerInternal.class, new ArtManagerInternalImpl());
    }

    public static int getCompilationReasonTronValue(String str) {
        str.getClass();
        switch (str) {
            case "bg-dexopt":
                return 5;
            case "install-fast-dm":
                return 15;
            case "ab-ota":
                return 6;
            case "prebuilt":
                return 23;
            case "boot-after-mainline-update":
                return 25;
            case "install-bulk-secondary-dm":
                return 17;
            case "shared":
                return 8;
            case "boot-after-ota":
                return 20;
            case "install-bulk-dm":
                return 16;
            case "first-boot":
                return 2;
            case "vdex":
                return 24;
            case "install-bulk-secondary":
                return 12;
            case "inactive":
                return 7;
            case "error":
                return 0;
            case "cmdline":
                return 22;
            case "install-dm":
                return 9;
            case "install-bulk-secondary-downgraded":
                return 14;
            case "install-bulk-downgraded-dm":
                return 18;
            case "install-bulk-secondary-downgraded-dm":
                return 19;
            case "post-boot":
                return 21;
            case "install":
                return 4;
            case "install-bulk":
                return 11;
            case "install-fast":
                return 10;
            case "install-bulk-downgraded":
                return 13;
            default:
                return 1;
        }
    }

    public final boolean checkAndroidPermissions(int i, String str) {
        this.mContext.enforceCallingOrSelfPermission(
                "android.permission.READ_RUNTIME_PROFILES", "ArtManagerService");
        int noteOp =
                ((AppOpsManager) this.mContext.getSystemService(AppOpsManager.class))
                        .noteOp(43, i, str);
        if (noteOp != 0) {
            if (noteOp != 3) {
                return false;
            }
            this.mContext.enforceCallingOrSelfPermission(
                    "android.permission.PACKAGE_USAGE_STATS", "ArtManagerService");
        }
        return true;
    }

    public final boolean isRuntimeProfilingEnabled(int i, String str) {
        int callingUid = Binder.getCallingUid();
        if (callingUid != 2000 && !checkAndroidPermissions(callingUid, str)) {
            return false;
        }
        if (i == 0) {
            return true;
        }
        if (i == 1) {
            return (Build.IS_USERDEBUG || Build.IS_ENG)
                    && SystemProperties.getBoolean(
                            "persist.device_config.runtime_native_boot.profilebootclasspath",
                            SystemProperties.getBoolean("dalvik.vm.profilebootclasspath", false));
        }
        throw new IllegalArgumentException(
                VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Invalid profile type:"));
    }

    public final void postError(
            final int i,
            final ISnapshotRuntimeProfileCallback iSnapshotRuntimeProfileCallback,
            final String str) {
        if (DEBUG) {
            Slog.d(
                    "ArtManagerService",
                    "Failed to snapshot profile for " + str + " with error: " + i);
        }
        this.mHandler.post(
                new Runnable() { // from class:
                                 // com.android.server.pm.dex.ArtManagerService$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        ISnapshotRuntimeProfileCallback iSnapshotRuntimeProfileCallback2 =
                                iSnapshotRuntimeProfileCallback;
                        int i2 = i;
                        String str2 = str;
                        try {
                            iSnapshotRuntimeProfileCallback2.onError(i2);
                        } catch (RemoteException | RuntimeException e) {
                            Slog.w(
                                    "ArtManagerService",
                                    "Failed to callback after profile snapshot for " + str2,
                                    e);
                        }
                    }
                });
    }

    public final void postSuccess(
            final ISnapshotRuntimeProfileCallback iSnapshotRuntimeProfileCallback,
            final ParcelFileDescriptor parcelFileDescriptor,
            final String str) {
        if (DEBUG) {
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                    "Successfully snapshot profile for ", str, "ArtManagerService");
        }
        this.mHandler.post(
                new Runnable() { // from class:
                    // com.android.server.pm.dex.ArtManagerService$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        ParcelFileDescriptor parcelFileDescriptor2 = parcelFileDescriptor;
                        ISnapshotRuntimeProfileCallback iSnapshotRuntimeProfileCallback2 =
                                iSnapshotRuntimeProfileCallback;
                        String str2 = str;
                        try {
                            try {
                                if (parcelFileDescriptor2.getFileDescriptor().valid()) {
                                    iSnapshotRuntimeProfileCallback2.onSuccess(
                                            parcelFileDescriptor2);
                                } else {
                                    Slog.wtf(
                                            "ArtManagerService",
                                            "The snapshot FD became invalid before posting the"
                                                + " result for "
                                                    + str2);
                                    iSnapshotRuntimeProfileCallback2.onError(2);
                                }
                            } catch (RemoteException | RuntimeException e) {
                                Slog.w(
                                        "ArtManagerService",
                                        "Failed to call onSuccess after profile snapshot for "
                                                + str2,
                                        e);
                            }
                            IoUtils.closeQuietly(parcelFileDescriptor2);
                        } catch (Throwable th) {
                            IoUtils.closeQuietly(parcelFileDescriptor2);
                            throw th;
                        }
                    }
                });
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0038, code lost:

       if ((r2.applicationInfo.flags & 2) != 2) goto L21;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void snapshotRuntimeProfile(
            int r10,
            java.lang.String r11,
            java.lang.String r12,
            android.content.pm.dex.ISnapshotRuntimeProfileCallback r13,
            java.lang.String r14) {
        /*
            Method dump skipped, instructions count: 285
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.dex.ArtManagerService.snapshotRuntimeProfile(int,"
                    + " java.lang.String, java.lang.String,"
                    + " android.content.pm.dex.ISnapshotRuntimeProfileCallback,"
                    + " java.lang.String):void");
    }
}
