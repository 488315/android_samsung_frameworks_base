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
        /* JADX WARN: Removed duplicated region for block: B:100:0x0251  */
        /* JADX WARN: Removed duplicated region for block: B:103:0x025e  */
        /* JADX WARN: Removed duplicated region for block: B:106:0x026a  */
        /* JADX WARN: Removed duplicated region for block: B:109:0x0276  */
        /* JADX WARN: Removed duplicated region for block: B:113:0x0284  */
        /* JADX WARN: Removed duplicated region for block: B:114:0x0288  */
        /* JADX WARN: Removed duplicated region for block: B:115:0x028c  */
        /* JADX WARN: Removed duplicated region for block: B:116:0x0290  */
        /* JADX WARN: Removed duplicated region for block: B:117:0x0293  */
        /* JADX WARN: Removed duplicated region for block: B:118:0x0296  */
        /* JADX WARN: Removed duplicated region for block: B:119:0x0298  */
        /* JADX WARN: Removed duplicated region for block: B:120:0x029b  */
        /* JADX WARN: Removed duplicated region for block: B:121:0x029e  */
        /* JADX WARN: Removed duplicated region for block: B:122:0x02a1  */
        /* JADX WARN: Removed duplicated region for block: B:123:0x02a3  */
        /* JADX WARN: Removed duplicated region for block: B:124:0x02a6  */
        /* JADX WARN: Removed duplicated region for block: B:125:0x02a9  */
        /* JADX WARN: Removed duplicated region for block: B:126:0x02ab  */
        /* JADX WARN: Removed duplicated region for block: B:127:0x02ad  */
        /* JADX WARN: Removed duplicated region for block: B:128:0x02b0  */
        /* JADX WARN: Removed duplicated region for block: B:129:0x02b3  */
        /* JADX WARN: Removed duplicated region for block: B:130:0x02b6  */
        /* JADX WARN: Removed duplicated region for block: B:131:0x02b9  */
        /* JADX WARN: Removed duplicated region for block: B:132:0x02bc  */
        /* JADX WARN: Removed duplicated region for block: B:133:0x02bf  */
        /* JADX WARN: Removed duplicated region for block: B:134:0x02c1  */
        /* JADX WARN: Removed duplicated region for block: B:135:0x02c4  */
        /* JADX WARN: Removed duplicated region for block: B:136:0x02c7  */
        /* JADX WARN: Removed duplicated region for block: B:137:0x02ca  */
        /* JADX WARN: Removed duplicated region for block: B:138:0x02cc  */
        /* JADX WARN: Removed duplicated region for block: B:139:0x02cf  */
        /* JADX WARN: Removed duplicated region for block: B:13:0x00a2 A[Catch: IOException -> 0x00b3, TryCatch #1 {IOException -> 0x00b3, blocks: (B:11:0x0098, B:13:0x00a2, B:16:0x00b7, B:19:0x00c3, B:21:0x00c9), top: B:10:0x0098 }] */
        /* JADX WARN: Removed duplicated region for block: B:19:0x00c3 A[Catch: IOException -> 0x00b3, TryCatch #1 {IOException -> 0x00b3, blocks: (B:11:0x0098, B:13:0x00a2, B:16:0x00b7, B:19:0x00c3, B:21:0x00c9), top: B:10:0x0098 }] */
        /* JADX WARN: Removed duplicated region for block: B:28:0x00fb  */
        /* JADX WARN: Removed duplicated region for block: B:31:0x010e  */
        /* JADX WARN: Removed duplicated region for block: B:34:0x011d  */
        /* JADX WARN: Removed duplicated region for block: B:37:0x012c  */
        /* JADX WARN: Removed duplicated region for block: B:40:0x013b  */
        /* JADX WARN: Removed duplicated region for block: B:43:0x014a  */
        /* JADX WARN: Removed duplicated region for block: B:46:0x0159  */
        /* JADX WARN: Removed duplicated region for block: B:49:0x0167  */
        /* JADX WARN: Removed duplicated region for block: B:52:0x0176  */
        /* JADX WARN: Removed duplicated region for block: B:55:0x0184  */
        /* JADX WARN: Removed duplicated region for block: B:58:0x0192  */
        /* JADX WARN: Removed duplicated region for block: B:61:0x01a1  */
        /* JADX WARN: Removed duplicated region for block: B:64:0x01b0  */
        /* JADX WARN: Removed duplicated region for block: B:67:0x01bf  */
        /* JADX WARN: Removed duplicated region for block: B:70:0x01ce  */
        /* JADX WARN: Removed duplicated region for block: B:73:0x01da  */
        /* JADX WARN: Removed duplicated region for block: B:76:0x01e7  */
        /* JADX WARN: Removed duplicated region for block: B:79:0x01f5  */
        /* JADX WARN: Removed duplicated region for block: B:82:0x0203  */
        /* JADX WARN: Removed duplicated region for block: B:85:0x0211  */
        /* JADX WARN: Removed duplicated region for block: B:88:0x021f  */
        /* JADX WARN: Removed duplicated region for block: B:91:0x022c  */
        /* JADX WARN: Removed duplicated region for block: B:94:0x0239  */
        /* JADX WARN: Removed duplicated region for block: B:97:0x0245  */
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
    /* JADX WARN: Removed duplicated region for block: B:18:0x004a  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x004f  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x005b  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x0111  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x004c  */
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
