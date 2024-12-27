package com.android.server.pm;

import android.content.Context;
import android.content.pm.IOtaDexopt;
import android.content.pm.PackageManagerInternal;
import android.os.Environment;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.os.ServiceManager;
import android.os.ShellCallback;
import android.os.storage.StorageManager;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Slog;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.pm.parsing.pkg.PackageImpl;
import com.android.internal.util.ArrayUtils;
import com.android.server.LocalServices;
import com.android.server.pm.Installer;
import com.android.server.pm.PackageManagerService;
import com.android.server.pm.dex.DexManager;
import com.android.server.pm.dex.DexoptOptions;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.PackageStateInternal;
import dalvik.system.BlockGuard;
import java.io.File;
import java.io.FileDescriptor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public final class OtaDexoptService extends IOtaDexopt.Stub {
    public long availableSpaceAfterBulkDelete;
    public long availableSpaceAfterDexopt;
    public long availableSpaceBefore;
    public int completeSize;
    public int dexoptCommandCountExecuted;
    public int dexoptCommandCountTotal;
    public int importantPackageCount;
    public final Context mContext;
    public List mDexoptCommands;
    public final PackageManagerService mPackageManagerService;
    public final MetricsLogger metricsLogger = new MetricsLogger();
    public long otaDexoptTimeStart;
    public int otherPackageCount;

    public final class OTADexoptPackageDexOptimizer extends PackageDexOptimizer {
        @Override // com.android.server.pm.PackageDexOptimizer
        public final int adjustDexoptFlags(int i) {
            return i | 64;
        }

        @Override // com.android.server.pm.PackageDexOptimizer
        public final int adjustDexoptNeeded(int i) {
            if (i == 0) {
                return -3;
            }
            return i;
        }
    }

    public OtaDexoptService(Context context, PackageManagerService packageManagerService) {
        this.mContext = context;
        this.mPackageManagerService = packageManagerService;
    }

    public static int inMegabytes(long j) {
        long j2 = j / 1048576;
        if (j2 <= 2147483647L) {
            return (int) j2;
        }
        Log.w("OTADexopt", "Recording " + j2 + "MB of free space, overflowing range");
        return Integer.MAX_VALUE;
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [android.os.IBinder, com.android.server.pm.OtaDexoptService] */
    public static void main(Context context, PackageManagerService packageManagerService) {
        ?? otaDexoptService = new OtaDexoptService(context, packageManagerService);
        ServiceManager.addService("otadexopt", (IBinder) otaDexoptService);
        Installer installer = packageManagerService.mInstaller;
        if (otaDexoptService.mDexoptCommands != null) {
            throw new IllegalStateException("Should not be ota-dexopting when trying to move.");
        }
        if (!otaDexoptService.mPackageManagerService.isDeviceUpgrading()) {
            Slog.d("OTADexopt", "No upgrade, skipping A/B artifacts check.");
            return;
        }
        ArrayMap packageStates = ((PackageManagerService.PackageManagerInternalImpl) ((PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class))).mService.snapshotComputer().getPackageStates();
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        OtaDexoptService otaDexoptService2 = otaDexoptService;
        while (i2 < packageStates.size()) {
            PackageStateInternal packageStateInternal = (PackageStateInternal) packageStates.valueAt(i2);
            PackageImpl pkg = packageStateInternal.getPkg();
            if (pkg != null && otaDexoptService2.mPackageManagerService.mPackageDexOptimizer.canOptimizePackage(pkg)) {
                if (pkg.getPath() == null) {
                    Slog.w("OTADexopt", "Package " + pkg + " can be optimized but has null codePath");
                } else if (!pkg.getPath().startsWith("/system") && !pkg.getPath().startsWith("/vendor") && !pkg.getPath().startsWith("/product") && !pkg.getPath().startsWith("/system_ext")) {
                    String[] appDexInstructionSets = InstructionSets.getAppDexInstructionSets(packageStateInternal.getPrimaryCpuAbi(), packageStateInternal.getSecondaryCpuAbi());
                    PackageImpl packageImpl = pkg;
                    ArrayList arrayList = new ArrayList();
                    if (packageImpl.isDeclaredHavingCode()) {
                        arrayList.add(packageImpl.getBaseApkPath());
                    }
                    String[] splitCodePaths = packageImpl.getSplitCodePaths();
                    if (!ArrayUtils.isEmpty(splitCodePaths)) {
                        for (int i4 = 0; i4 < splitCodePaths.length; i4++) {
                            if ((packageImpl.getSplitFlags()[i4] & 4) != 0) {
                                arrayList.add(splitCodePaths[i4]);
                            }
                        }
                    }
                    String[] dexCodeInstructionSets = InstructionSets.getDexCodeInstructionSets(appDexInstructionSets);
                    String packageName = pkg.getPackageName();
                    int length = dexCodeInstructionSets.length;
                    int i5 = 0;
                    otaDexoptService2 = otaDexoptService2;
                    while (i5 < length) {
                        String str = dexCodeInstructionSets[i5];
                        Iterator it = arrayList.iterator();
                        int i6 = i3;
                        int i7 = i;
                        int i8 = i6;
                        OtaDexoptService otaDexoptService3 = otaDexoptService2;
                        while (it.hasNext()) {
                            String str2 = (String) it.next();
                            OtaDexoptService otaDexoptService4 = otaDexoptService3;
                            ArrayMap arrayMap = packageStates;
                            String[] strArr = dexCodeInstructionSets;
                            String absolutePath = new File(new File(pkg.getPath()), "oat").getAbsolutePath();
                            int i9 = i8 + 1;
                            try {
                                if (installer.checkBeforeRemote()) {
                                    BlockGuard.getVmPolicy().onPathAccess(str2);
                                    BlockGuard.getVmPolicy().onPathAccess(absolutePath);
                                    try {
                                        installer.mInstalld.moveAb(packageName, str2, str, absolutePath);
                                    } catch (Exception e) {
                                        Installer.InstallerException.from(e);
                                        throw null;
                                    }
                                }
                                i7++;
                            } catch (Installer.InstallerException unused) {
                            }
                            i8 = i9;
                            otaDexoptService3 = otaDexoptService4;
                            dexCodeInstructionSets = strArr;
                            packageStates = arrayMap;
                        }
                        i5++;
                        int i10 = i7;
                        i3 = i8;
                        i = i10;
                        otaDexoptService2 = otaDexoptService3;
                    }
                }
            }
            i2++;
            packageStates = packageStates;
            otaDexoptService2 = otaDexoptService2;
        }
        Slog.i("OTADexopt", "Moved " + i + "/" + i3);
    }

    public final synchronized void cleanup() {
        Log.i("OTADexopt", "Cleaning up OTA Dexopt state.");
        this.mDexoptCommands = null;
        this.availableSpaceAfterDexopt = getAvailableSpace();
        performMetricsLogging();
    }

    public final synchronized void dexoptNextPackage() {
        throw new UnsupportedOperationException();
    }

    public final synchronized List generatePackageDexopts(AndroidPackage androidPackage, PackageStateInternal packageStateInternal, int i) {
        final ArrayList arrayList;
        arrayList = new ArrayList();
        OTADexoptPackageDexOptimizer oTADexoptPackageDexOptimizer = new OTADexoptPackageDexOptimizer(new Installer(this.mContext) { // from class: com.android.server.pm.OtaDexoptService.1
            public static void encodeParameter(StringBuilder sb, Object obj) {
                sb.append(' ');
                if (obj == null) {
                    sb.append('!');
                    return;
                }
                String valueOf = String.valueOf(obj);
                if (valueOf.indexOf(0) == -1 && valueOf.indexOf(32) == -1 && !"!".equals(valueOf)) {
                    sb.append(valueOf);
                } else {
                    throw new IllegalArgumentException("Invalid argument while executing " + obj);
                }
            }

            @Override // com.android.server.pm.Installer
            public final boolean dexopt(String str, int i2, String str2, String str3, int i3, String str4, int i4, String str5, String str6, String str7, String str8, int i5, String str9, String str10, String str11) {
                StringBuilder sb = new StringBuilder();
                if ((i4 & 32) != 0) {
                    throw new IllegalArgumentException("Invalid OTA dexopt call for secondary dex");
                }
                sb.append("10 dexopt");
                encodeParameter(sb, str);
                encodeParameter(sb, Integer.valueOf(i2));
                encodeParameter(sb, str2);
                encodeParameter(sb, str3);
                encodeParameter(sb, Integer.valueOf(i3));
                encodeParameter(sb, str4);
                encodeParameter(sb, Integer.valueOf(i4));
                encodeParameter(sb, str5);
                encodeParameter(sb, str6);
                encodeParameter(sb, str7);
                encodeParameter(sb, str8);
                encodeParameter(sb, Boolean.FALSE);
                encodeParameter(sb, Integer.valueOf(i5));
                encodeParameter(sb, str9);
                encodeParameter(sb, str10);
                encodeParameter(sb, str11);
                arrayList.add(sb.toString());
                return true;
            }
        }, this.mPackageManagerService.mInstallLock, this.mContext, "*otadexopt*");
        try {
            DexManager dexManager = this.mPackageManagerService.mDexManager;
            if (dexManager.mPackageDexUsage.getPackageUseInfo(androidPackage.getPackageName()) == null) {
                new HashMap();
                new HashMap();
            }
            oTADexoptPackageDexOptimizer.performDexOpt(androidPackage, packageStateInternal, new DexoptOptions(i, 4, androidPackage.getPackageName()));
        } catch (Installer.LegacyDexoptDisabledException e) {
            Slog.wtf("OTADexopt", e);
        }
        return arrayList;
    }

    public final long getAvailableSpace() {
        long storageLowBytes = StorageManager.from(this.mContext).getStorageLowBytes(Environment.getDataDirectory());
        if (storageLowBytes != 0) {
            return Environment.getDataDirectory().getUsableSpace() - storageLowBytes;
        }
        throw new IllegalStateException("Invalid low memory threshold");
    }

    public final synchronized float getProgress() {
        if (this.completeSize == 0) {
            return 1.0f;
        }
        int size = ((ArrayList) this.mDexoptCommands).size();
        return (r1 - size) / this.completeSize;
    }

    public final synchronized boolean isDone() {
        List list;
        list = this.mDexoptCommands;
        if (list == null) {
            throw new IllegalStateException("done() called before prepare()");
        }
        return list.isEmpty();
    }

    public final synchronized String nextDexoptCommand() {
        List list = this.mDexoptCommands;
        if (list == null) {
            throw new IllegalStateException("dexoptNextPackage() called before prepare()");
        }
        if (list.isEmpty()) {
            return "(all done)";
        }
        String str = (String) this.mDexoptCommands.remove(0);
        if (getAvailableSpace() > 0) {
            this.dexoptCommandCountExecuted++;
            Log.d("OTADexopt", "Next command: " + str);
            return str;
        }
        Log.w("OTADexopt", "Not enough space for OTA dexopt, stopping with " + (this.mDexoptCommands.size() + 1) + " commands left.");
        this.mDexoptCommands.clear();
        return "(no free space)";
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void onShellCommand(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, FileDescriptor fileDescriptor3, String[] strArr, ShellCallback shellCallback, ResultReceiver resultReceiver) {
        new OtaDexoptShellCommand(this).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
    }

    public final void performMetricsLogging() {
        long nanoTime = System.nanoTime();
        this.metricsLogger.histogram("ota_dexopt_available_space_before_mb", inMegabytes(this.availableSpaceBefore));
        this.metricsLogger.histogram("ota_dexopt_available_space_after_bulk_delete_mb", inMegabytes(this.availableSpaceAfterBulkDelete));
        this.metricsLogger.histogram("ota_dexopt_available_space_after_dexopt_mb", inMegabytes(this.availableSpaceAfterDexopt));
        this.metricsLogger.histogram("ota_dexopt_num_important_packages", this.importantPackageCount);
        this.metricsLogger.histogram("ota_dexopt_num_other_packages", this.otherPackageCount);
        this.metricsLogger.histogram("ota_dexopt_num_commands", this.dexoptCommandCountTotal);
        this.metricsLogger.histogram("ota_dexopt_num_commands_executed", this.dexoptCommandCountExecuted);
        this.metricsLogger.histogram("ota_dexopt_time_s", (int) TimeUnit.NANOSECONDS.toSeconds(nanoTime - this.otaDexoptTimeStart));
    }

    public final synchronized void prepare() {
        try {
            if (this.mDexoptCommands != null) {
                throw new IllegalStateException("already called prepare()");
            }
            OtaDexoptService$$ExternalSyntheticLambda0 otaDexoptService$$ExternalSyntheticLambda0 = new OtaDexoptService$$ExternalSyntheticLambda0();
            Collection values = this.mPackageManagerService.snapshotComputer().getPackageStates().values();
            List packagesForDexopt = DexOptHelper.getPackagesForDexopt(values, this.mPackageManagerService);
            ArrayList arrayList = (ArrayList) packagesForDexopt;
            arrayList.removeIf(otaDexoptService$$ExternalSyntheticLambda0);
            ArrayList arrayList2 = new ArrayList(values);
            arrayList2.removeAll(packagesForDexopt);
            arrayList2.removeIf(PackageManagerServiceUtils.REMOVE_IF_NULL_PKG);
            arrayList2.removeIf(PackageManagerServiceUtils.REMOVE_IF_APEX_PKG);
            arrayList2.removeIf(otaDexoptService$$ExternalSyntheticLambda0);
            this.mDexoptCommands = new ArrayList((values.size() * 3) / 2);
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                PackageStateInternal packageStateInternal = (PackageStateInternal) it.next();
                ((ArrayList) this.mDexoptCommands).addAll(generatePackageDexopts(packageStateInternal.getPkg(), packageStateInternal, 10));
            }
            Iterator it2 = arrayList2.iterator();
            while (it2.hasNext()) {
                PackageStateInternal packageStateInternal2 = (PackageStateInternal) it2.next();
                if (packageStateInternal2.getPkg().isCoreApp()) {
                    throw new IllegalStateException("Found a core app that's not important");
                }
                ((ArrayList) this.mDexoptCommands).addAll(generatePackageDexopts(packageStateInternal2.getPkg(), packageStateInternal2, 0));
            }
            this.completeSize = ((ArrayList) this.mDexoptCommands).size();
            long availableSpace = getAvailableSpace();
            if (availableSpace < 1073741824) {
                Log.i("OTADexopt", "Low on space, deleting oat files in an attempt to free up space: " + DexOptHelper.packagesToString(arrayList2));
                Iterator it3 = arrayList2.iterator();
                while (it3.hasNext()) {
                    PackageStateInternal packageStateInternal3 = (PackageStateInternal) it3.next();
                    PackageManagerService packageManagerService = this.mPackageManagerService;
                    String packageName = packageStateInternal3.getPackageName();
                    packageManagerService.getClass();
                    PackageManagerService.deleteOatArtifactsOfPackage(packageName);
                }
            }
            long availableSpace2 = getAvailableSpace();
            int size = arrayList.size();
            int size2 = arrayList2.size();
            this.availableSpaceBefore = availableSpace;
            this.availableSpaceAfterBulkDelete = availableSpace2;
            this.availableSpaceAfterDexopt = 0L;
            this.importantPackageCount = size;
            this.otherPackageCount = size2;
            this.dexoptCommandCountTotal = ((ArrayList) this.mDexoptCommands).size();
            this.dexoptCommandCountExecuted = 0;
            this.otaDexoptTimeStart = System.nanoTime();
            try {
                Log.d("OTADexopt", "A/B OTA: lastUsed time = " + ((PackageStateInternal) Collections.max(packagesForDexopt, Comparator.comparingLong(new OtaDexoptService$$ExternalSyntheticLambda1()))).getTransientState().getLatestForegroundPackageUseTimeInMills());
                Log.d("OTADexopt", "A/B OTA: deprioritized packages:");
                Iterator it4 = arrayList2.iterator();
                while (it4.hasNext()) {
                    PackageStateInternal packageStateInternal4 = (PackageStateInternal) it4.next();
                    Log.d("OTADexopt", "  " + packageStateInternal4.getPackageName() + " - " + packageStateInternal4.getTransientState().getLatestForegroundPackageUseTimeInMills());
                }
            } catch (RuntimeException unused) {
            }
        } catch (Throwable th) {
            throw th;
        }
    }
}
