package com.android.server.pm.dex;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.content.pm.PackageInfo;
import android.content.pm.dex.ArtManager;
import android.content.pm.dex.ArtManagerInternal;
import android.content.pm.dex.DexMetadataHelper;
import android.content.pm.dex.IArtManager;
import android.content.pm.dex.ISnapshotRuntimeProfileCallback;
import android.content.pm.dex.PackageOptimizationInfo;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.system.Os;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Slog;
import com.android.internal.os.BackgroundThread;
import com.android.internal.os.RoSystemProperties;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.Preconditions;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.LocalServices;
import com.android.server.art.ArtManagerLocal;
import com.android.server.pm.DexOptHelper;
import com.android.server.pm.Installer;
import com.android.server.pm.PackageManagerLocal;
import com.android.server.pm.PackageManagerServiceCompilerMapping;
import com.android.server.pm.PackageManagerServiceUtils;
import com.android.server.pm.parsing.PackageInfoUtils;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.AndroidPackageSplit;
import com.android.server.pm.pkg.PackageState;
import dalvik.system.DexFile;
import dalvik.system.VMRuntime;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import libcore.io.IoUtils;

/* loaded from: classes3.dex */
public class ArtManagerService extends IArtManager.Stub {
    public static final boolean DEBUG = Log.isLoggable("ArtManagerService", 3);
    public final Context mContext;
    public final Handler mHandler = new Handler(BackgroundThread.getHandler().getLooper());
    public final Installer mInstaller;
    public IPackageManager mPackageManager;

    static {
        verifyTronLoggingConstants();
    }

    public ArtManagerService(Context context, Installer installer, Object obj) {
        this.mContext = context;
        this.mInstaller = installer;
        LocalServices.addService(ArtManagerInternal.class, new ArtManagerInternalImpl());
    }

    public final IPackageManager getPackageManager() {
        if (this.mPackageManager == null) {
            this.mPackageManager = IPackageManager.Stub.asInterface(ServiceManager.getService("package"));
        }
        return this.mPackageManager;
    }

    public final boolean checkAndroidPermissions(int i, String str) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.READ_RUNTIME_PROFILES", "ArtManagerService");
        int noteOp = ((AppOpsManager) this.mContext.getSystemService(AppOpsManager.class)).noteOp(43, i, str);
        if (noteOp != 0) {
            if (noteOp != 3) {
                return false;
            }
            this.mContext.enforceCallingOrSelfPermission("android.permission.PACKAGE_USAGE_STATS", "ArtManagerService");
        }
        return true;
    }

    public final boolean checkShellPermissions(int i, String str, int i2) {
        PackageInfo packageInfo;
        if (i2 != 2000) {
            return false;
        }
        if (RoSystemProperties.DEBUGGABLE) {
            return true;
        }
        if (i == 1) {
            return false;
        }
        try {
            packageInfo = getPackageManager().getPackageInfo(str, 0L, 0);
        } catch (RemoteException unused) {
            packageInfo = null;
        }
        return packageInfo != null && (packageInfo.applicationInfo.flags & 2) == 2;
    }

    public void snapshotRuntimeProfile(int i, String str, String str2, ISnapshotRuntimeProfileCallback iSnapshotRuntimeProfileCallback, String str3) {
        int callingUid = Binder.getCallingUid();
        if (!checkShellPermissions(i, str, callingUid) && !checkAndroidPermissions(callingUid, str3)) {
            try {
                iSnapshotRuntimeProfileCallback.onError(2);
                return;
            } catch (RemoteException unused) {
                return;
            }
        }
        Objects.requireNonNull(iSnapshotRuntimeProfileCallback);
        boolean z = i == 1;
        if (!z) {
            Preconditions.checkStringNotEmpty(str2);
            Preconditions.checkStringNotEmpty(str);
        }
        if (!isRuntimeProfilingEnabled(i, str3)) {
            throw new IllegalStateException("Runtime profiling is not enabled for " + i);
        }
        if (DEBUG) {
            Slog.d("ArtManagerService", "Requested snapshot for " + str + XmlUtils.STRING_ARRAY_SEPARATOR + str2);
        }
        if (z) {
            snapshotBootImageProfile(iSnapshotRuntimeProfileCallback);
        } else {
            snapshotAppProfile(str, str2, iSnapshotRuntimeProfileCallback);
        }
    }

    public final void snapshotAppProfile(String str, String str2, ISnapshotRuntimeProfileCallback iSnapshotRuntimeProfileCallback) {
        PackageInfo packageInfo;
        String str3 = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(str, 0L, 0);
        } catch (RemoteException unused) {
            packageInfo = null;
        }
        if (packageInfo == null) {
            postError(iSnapshotRuntimeProfileCallback, str, 0);
            return;
        }
        boolean equals = packageInfo.applicationInfo.getBaseCodePath().equals(str2);
        String[] splitCodePaths = packageInfo.applicationInfo.getSplitCodePaths();
        if (!equals && splitCodePaths != null) {
            int length = splitCodePaths.length - 1;
            while (true) {
                if (length < 0) {
                    break;
                }
                if (splitCodePaths[length].equals(str2)) {
                    str3 = packageInfo.applicationInfo.splitNames[length];
                    equals = true;
                    break;
                }
                length--;
            }
        }
        if (!equals) {
            postError(iSnapshotRuntimeProfileCallback, str, 1);
            return;
        }
        if (DexOptHelper.useArtService()) {
            try {
                try {
                    PackageManagerLocal.FilteredSnapshot withFilteredSnapshot = PackageManagerServiceUtils.getPackageManagerLocal().withFilteredSnapshot();
                    try {
                        ParcelFileDescriptor snapshotAppProfile = DexOptHelper.getArtManagerLocal().snapshotAppProfile(withFilteredSnapshot, str, str3);
                        if (withFilteredSnapshot != null) {
                            withFilteredSnapshot.close();
                        }
                        postSuccess(str, snapshotAppProfile, iSnapshotRuntimeProfileCallback);
                        return;
                    } catch (Throwable th) {
                        if (withFilteredSnapshot != null) {
                            try {
                                withFilteredSnapshot.close();
                            } catch (Throwable th2) {
                                th.addSuppressed(th2);
                            }
                        }
                        throw th;
                    }
                } catch (IllegalStateException | ArtManagerLocal.SnapshotProfileException unused2) {
                    postError(iSnapshotRuntimeProfileCallback, str, 2);
                    return;
                }
            } catch (IllegalArgumentException unused3) {
                postError(iSnapshotRuntimeProfileCallback, str, 0);
                return;
            }
        }
        int appId = UserHandle.getAppId(packageInfo.applicationInfo.uid);
        if (appId < 0) {
            postError(iSnapshotRuntimeProfileCallback, str, 2);
            Slog.wtf("ArtManagerService", "AppId is -1 for package: " + str);
            return;
        }
        try {
            createProfileSnapshot(str, ArtManager.getProfileName(str3), str2, appId, iSnapshotRuntimeProfileCallback);
            destroyProfileSnapshot(str, ArtManager.getProfileName(str3));
        } catch (Installer.LegacyDexoptDisabledException e) {
            throw new RuntimeException(e);
        }
    }

    public final void createProfileSnapshot(String str, String str2, String str3, int i, ISnapshotRuntimeProfileCallback iSnapshotRuntimeProfileCallback) {
        try {
            if (!this.mInstaller.createProfileSnapshot(i, str, str2, str3)) {
                postError(iSnapshotRuntimeProfileCallback, str, 2);
                return;
            }
            File profileSnapshotFileForName = ArtManager.getProfileSnapshotFileForName(str, str2);
            try {
                ParcelFileDescriptor open = ParcelFileDescriptor.open(profileSnapshotFileForName, 268435456);
                if (open != null && open.getFileDescriptor().valid()) {
                    postSuccess(str, open, iSnapshotRuntimeProfileCallback);
                }
                postError(iSnapshotRuntimeProfileCallback, str, 2);
            } catch (FileNotFoundException e) {
                Slog.w("ArtManagerService", "Could not open snapshot profile for " + str + XmlUtils.STRING_ARRAY_SEPARATOR + profileSnapshotFileForName, e);
                postError(iSnapshotRuntimeProfileCallback, str, 2);
            }
        } catch (Installer.InstallerException unused) {
            postError(iSnapshotRuntimeProfileCallback, str, 2);
        }
    }

    public final void destroyProfileSnapshot(String str, String str2) {
        if (DEBUG) {
            Slog.d("ArtManagerService", "Destroying profile snapshot for" + str + XmlUtils.STRING_ARRAY_SEPARATOR + str2);
        }
        try {
            this.mInstaller.destroyProfileSnapshot(str, str2);
        } catch (Installer.InstallerException e) {
            Slog.e("ArtManagerService", "Failed to destroy profile snapshot for " + str + XmlUtils.STRING_ARRAY_SEPARATOR + str2, e);
        }
    }

    public boolean isRuntimeProfilingEnabled(int i, String str) {
        int callingUid = Binder.getCallingUid();
        if (callingUid != 2000 && !checkAndroidPermissions(callingUid, str)) {
            return false;
        }
        if (i == 0) {
            return true;
        }
        if (i == 1) {
            return (Build.IS_USERDEBUG || Build.IS_ENG) && SystemProperties.getBoolean("persist.device_config.runtime_native_boot.profilebootclasspath", SystemProperties.getBoolean("dalvik.vm.profilebootclasspath", false));
        }
        throw new IllegalArgumentException("Invalid profile type:" + i);
    }

    public final void snapshotBootImageProfile(ISnapshotRuntimeProfileCallback iSnapshotRuntimeProfileCallback) {
        if (DexOptHelper.useArtService()) {
            try {
                PackageManagerLocal.FilteredSnapshot withFilteredSnapshot = PackageManagerServiceUtils.getPackageManagerLocal().withFilteredSnapshot();
                try {
                    ParcelFileDescriptor snapshotBootImageProfile = DexOptHelper.getArtManagerLocal().snapshotBootImageProfile(withFilteredSnapshot);
                    if (withFilteredSnapshot != null) {
                        withFilteredSnapshot.close();
                    }
                    postSuccess("android", snapshotBootImageProfile, iSnapshotRuntimeProfileCallback);
                    return;
                } catch (Throwable th) {
                    if (withFilteredSnapshot != null) {
                        try {
                            withFilteredSnapshot.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            } catch (IllegalStateException | ArtManagerLocal.SnapshotProfileException unused) {
                postError(iSnapshotRuntimeProfileCallback, "android", 2);
                return;
            }
        }
        String join = String.join(XmlUtils.STRING_ARRAY_SEPARATOR, Os.getenv("BOOTCLASSPATH"), Os.getenv("SYSTEMSERVERCLASSPATH"));
        String str = Os.getenv("STANDALONE_SYSTEMSERVER_JARS");
        if (str != null) {
            join = String.join(XmlUtils.STRING_ARRAY_SEPARATOR, join, str);
        }
        try {
            createProfileSnapshot("android", "android.prof", join, -1, iSnapshotRuntimeProfileCallback);
            destroyProfileSnapshot("android", "android.prof");
        } catch (Installer.LegacyDexoptDisabledException e) {
            throw new RuntimeException(e);
        }
    }

    public final void postError(final ISnapshotRuntimeProfileCallback iSnapshotRuntimeProfileCallback, final String str, final int i) {
        if (DEBUG) {
            Slog.d("ArtManagerService", "Failed to snapshot profile for " + str + " with error: " + i);
        }
        this.mHandler.post(new Runnable() { // from class: com.android.server.pm.dex.ArtManagerService$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ArtManagerService.lambda$postError$0(iSnapshotRuntimeProfileCallback, i, str);
            }
        });
    }

    public static /* synthetic */ void lambda$postError$0(ISnapshotRuntimeProfileCallback iSnapshotRuntimeProfileCallback, int i, String str) {
        try {
            iSnapshotRuntimeProfileCallback.onError(i);
        } catch (RemoteException | RuntimeException e) {
            Slog.w("ArtManagerService", "Failed to callback after profile snapshot for " + str, e);
        }
    }

    public final void postSuccess(final String str, final ParcelFileDescriptor parcelFileDescriptor, final ISnapshotRuntimeProfileCallback iSnapshotRuntimeProfileCallback) {
        if (DEBUG) {
            Slog.d("ArtManagerService", "Successfully snapshot profile for " + str);
        }
        this.mHandler.post(new Runnable() { // from class: com.android.server.pm.dex.ArtManagerService$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                ArtManagerService.lambda$postSuccess$1(parcelFileDescriptor, iSnapshotRuntimeProfileCallback, str);
            }
        });
    }

    public static /* synthetic */ void lambda$postSuccess$1(ParcelFileDescriptor parcelFileDescriptor, ISnapshotRuntimeProfileCallback iSnapshotRuntimeProfileCallback, String str) {
        try {
            try {
                if (parcelFileDescriptor.getFileDescriptor().valid()) {
                    iSnapshotRuntimeProfileCallback.onSuccess(parcelFileDescriptor);
                } else {
                    Slog.wtf("ArtManagerService", "The snapshot FD became invalid before posting the result for " + str);
                    iSnapshotRuntimeProfileCallback.onError(2);
                }
            } catch (RemoteException | RuntimeException e) {
                Slog.w("ArtManagerService", "Failed to call onSuccess after profile snapshot for " + str, e);
            }
        } finally {
            IoUtils.closeQuietly(parcelFileDescriptor);
        }
    }

    public void prepareAppProfiles(AndroidPackage androidPackage, int i, boolean z) {
        File findDexMetadataForFile;
        int appId = UserHandle.getAppId(androidPackage.getUid());
        if (i < 0) {
            Slog.wtf("ArtManagerService", "Invalid user id: " + i);
            return;
        }
        if (appId < 0) {
            Slog.wtf("ArtManagerService", "Invalid app id: " + appId);
            return;
        }
        try {
            ArrayMap packageProfileNames = getPackageProfileNames(androidPackage);
            for (int size = packageProfileNames.size() - 1; size >= 0; size--) {
                String str = (String) packageProfileNames.keyAt(size);
                String str2 = (String) packageProfileNames.valueAt(size);
                String str3 = null;
                if (z && (findDexMetadataForFile = DexMetadataHelper.findDexMetadataForFile(new File(str))) != null) {
                    str3 = findDexMetadataForFile.getAbsolutePath();
                }
                String str4 = str3;
                synchronized (this.mInstaller) {
                    if (!this.mInstaller.prepareAppProfile(androidPackage.getPackageName(), i, appId, str2, str, str4)) {
                        Slog.e("ArtManagerService", "Failed to prepare profile for " + androidPackage.getPackageName() + XmlUtils.STRING_ARRAY_SEPARATOR + str);
                    }
                }
            }
        } catch (Installer.InstallerException e) {
            Slog.e("ArtManagerService", "Failed to prepare profile for " + androidPackage.getPackageName(), e);
        }
    }

    public void prepareAppProfiles(AndroidPackage androidPackage, int[] iArr, boolean z) {
        for (int i : iArr) {
            prepareAppProfiles(androidPackage, i, z);
        }
    }

    public void clearAppProfiles(AndroidPackage androidPackage) {
        try {
            ArrayMap packageProfileNames = getPackageProfileNames(androidPackage);
            for (int size = packageProfileNames.size() - 1; size >= 0; size--) {
                this.mInstaller.clearAppProfiles(androidPackage.getPackageName(), (String) packageProfileNames.valueAt(size));
            }
        } catch (Installer.InstallerException e) {
            Slog.w("ArtManagerService", String.valueOf(e));
        }
    }

    public void dumpProfiles(AndroidPackage androidPackage, boolean z) {
        int sharedAppGid = UserHandle.getSharedAppGid(androidPackage.getUid());
        try {
            ArrayMap packageProfileNames = getPackageProfileNames(androidPackage);
            for (int size = packageProfileNames.size() - 1; size >= 0; size--) {
                this.mInstaller.dumpProfiles(sharedAppGid, androidPackage.getPackageName(), (String) packageProfileNames.valueAt(size), (String) packageProfileNames.keyAt(size), z);
            }
        } catch (Installer.InstallerException e) {
            Slog.w("ArtManagerService", "Failed to dump profiles", e);
        }
    }

    public boolean compileLayouts(PackageState packageState, AndroidPackage androidPackage) {
        try {
            String packageName = androidPackage.getPackageName();
            String path = ((AndroidPackageSplit) androidPackage.getSplits().get(0)).getPath();
            String str = PackageInfoUtils.getDataDir(androidPackage, UserHandle.myUserId()).getAbsolutePath() + "/code_cache/compiled_view.dex";
            if (!packageState.isPrivileged() && !androidPackage.isUseEmbeddedDex() && !androidPackage.isDefaultToDeviceProtectedStorage()) {
                Log.i("PackageManager", "Compiling layouts in " + packageName + " (" + path + ") to " + str);
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    return this.mInstaller.compileLayouts(path, packageName, str, androidPackage.getUid());
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
            return false;
        } catch (Throwable th) {
            Log.e("PackageManager", "Failed to compile layouts", th);
            return false;
        }
    }

    public final ArrayMap getPackageProfileNames(AndroidPackage androidPackage) {
        ArrayMap arrayMap = new ArrayMap();
        if (androidPackage.isDeclaredHavingCode()) {
            arrayMap.put(androidPackage.getBaseApkPath(), ArtManager.getProfileName((String) null));
        }
        String[] splitCodePaths = androidPackage.getSplitCodePaths();
        int[] splitFlags = androidPackage.getSplitFlags();
        String[] splitNames = androidPackage.getSplitNames();
        if (!ArrayUtils.isEmpty(splitCodePaths)) {
            for (int i = 0; i < splitCodePaths.length; i++) {
                if ((splitFlags[i] & 4) != 0) {
                    arrayMap.put(splitCodePaths[i], ArtManager.getProfileName(splitNames[i]));
                }
            }
        }
        return arrayMap;
    }

    public static int getCompilationReasonTronValue(String str) {
        str.hashCode();
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

    public static int getCompilationFilterTronValue(String str) {
        str.hashCode();
        switch (str) {
            case "speed-profile-iorap":
                return 21;
            case "assume-verified":
                return 2;
            case "everything-profile":
                return 10;
            case "verify-iorap":
                return 17;
            case "extract-iorap":
                return 16;
            case "extract":
                return 3;
            case "speed-profile":
                return 8;
            case "run-from-apk-fallback-iorap":
                return 26;
            case "run-from-vdex-fallback":
                return 14;
            case "verify":
                return 4;
            case "space-profile-iorap":
                return 19;
            case "run-from-vdex-fallback-iorap":
                return 27;
            case "assume-verified-iorap":
                return 15;
            case "error":
                return 0;
            case "space":
                return 7;
            case "speed":
                return 9;
            case "run-from-apk-iorap":
                return 25;
            case "space-profile":
                return 6;
            case "everything":
                return 11;
            case "everything-iorap":
                return 24;
            case "quicken":
                return 5;
            case "everything-profile-iorap":
                return 23;
            case "run-from-apk":
                return 12;
            case "speed-iorap":
                return 22;
            case "space-iorap":
                return 20;
            case "quicken-iorap":
                return 18;
            case "run-from-apk-fallback":
                return 13;
            default:
                return 1;
        }
    }

    public static void verifyTronLoggingConstants() {
        String str;
        int i = 0;
        while (true) {
            String[] strArr = PackageManagerServiceCompilerMapping.REASON_STRINGS;
            if (i >= strArr.length) {
                return;
            }
            str = strArr[i];
            int compilationReasonTronValue = getCompilationReasonTronValue(str);
            if (compilationReasonTronValue == 0 || compilationReasonTronValue == 1) {
                break;
            } else {
                i++;
            }
        }
        throw new IllegalArgumentException("Compilation reason not configured for TRON logging: " + str);
    }

    public class ArtManagerInternalImpl extends ArtManagerInternal {
        public ArtManagerInternalImpl() {
        }

        /* JADX WARN: Removed duplicated region for block: B:11:0x0070  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public PackageOptimizationInfo getPackageOptimizationInfo(ApplicationInfo applicationInfo, String str, String str2) {
            String str3;
            String str4 = "error";
            if (applicationInfo.packageName.equals("android")) {
                return PackageOptimizationInfo.createWithNoInfo();
            }
            try {
                DexFile.OptimizationInfo dexFileOptimizationInfo = DexFile.getDexFileOptimizationInfo(applicationInfo.getBaseCodePath(), VMRuntime.getInstructionSet(str));
                String status = dexFileOptimizationInfo.getStatus();
                str3 = dexFileOptimizationInfo.getReason();
                str4 = status;
            } catch (FileNotFoundException e) {
                Slog.e("ArtManagerInternalImpl", "Could not get optimizations status for " + applicationInfo.getBaseCodePath(), e);
                str3 = "error";
                if (checkIorapCompiledTrace(applicationInfo.packageName, str2, applicationInfo.longVersionCode)) {
                }
                return new PackageOptimizationInfo(ArtManagerService.getCompilationFilterTronValue(str4), ArtManagerService.getCompilationReasonTronValue(str3));
            } catch (IllegalArgumentException e2) {
                Slog.wtf("ArtManagerInternalImpl", "Requested optimization status for " + applicationInfo.getBaseCodePath() + " due to an invalid abi " + str, e2);
                str3 = "error";
                if (checkIorapCompiledTrace(applicationInfo.packageName, str2, applicationInfo.longVersionCode)) {
                }
                return new PackageOptimizationInfo(ArtManagerService.getCompilationFilterTronValue(str4), ArtManagerService.getCompilationReasonTronValue(str3));
            }
            if (checkIorapCompiledTrace(applicationInfo.packageName, str2, applicationInfo.longVersionCode)) {
                str4 = str4 + "-iorap";
            }
            return new PackageOptimizationInfo(ArtManagerService.getCompilationFilterTronValue(str4), ArtManagerService.getCompilationReasonTronValue(str3));
        }

        public final boolean checkIorapCompiledTrace(String str, String str2, long j) {
            Path path = Paths.get("/data/misc/iorapd", str, Long.toString(j), str2, "compiled_traces", "compiled_trace.pb");
            try {
                boolean exists = Files.exists(path, new LinkOption[0]);
                if (ArtManagerService.DEBUG) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(path.toString());
                    sb.append(exists ? " exists" : " doesn't exist");
                    Log.d("ArtManagerInternalImpl", sb.toString());
                }
                if (!exists) {
                    return exists;
                }
                long size = Files.size(path);
                if (ArtManagerService.DEBUG) {
                    Log.d("ArtManagerInternalImpl", path.toString() + " size is " + Long.toString(size));
                }
                return size > 0;
            } catch (IOException e) {
                Log.d("ArtManagerInternalImpl", e.getMessage());
                return false;
            }
        }
    }
}
