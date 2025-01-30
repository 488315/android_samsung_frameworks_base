package com.android.server.pm;

import android.app.AppGlobals;
import android.app.role.RoleManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ResolveInfo;
import android.content.pm.SharedLibraryInfo;
import android.content.pm.dex.ArtManager;
import android.os.BatteryManager;
import android.os.BatteryManagerInternal;
import android.os.Binder;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;
import android.util.Slog;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.IndentingPrintWriter;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.LocalManagerRegistry;
import com.android.server.LocalServices;
import com.android.server.PinnerService;
import com.android.server.SystemServerInitThreadPool$$ExternalSyntheticLambda1;
import com.android.server.art.ArtManagerLocal;
import com.android.server.art.DexUseManagerLocal;
import com.android.server.art.model.BatchDexoptParams;
import com.android.server.art.model.DexoptParams;
import com.android.server.art.model.DexoptResult;
import com.android.server.art.model.OperationProgress;
import com.android.server.pm.ApexManager;
import com.android.server.pm.CompilerStats;
import com.android.server.pm.Installer;
import com.android.server.pm.PackageDexOptimizer;
import com.android.server.pm.PackageManagerLocal;
import com.android.server.pm.dex.DexManager;
import com.android.server.pm.dex.DexoptOptions;
import com.android.server.pm.parsing.pkg.AndroidPackageInternal;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.PackageState;
import com.android.server.pm.pkg.PackageStateInternal;
import com.android.server.wm.WindowManagerInternal;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.os.SemTemperatureManager;
import dalvik.system.DexFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.ToLongFunction;

/* loaded from: classes3.dex */
public final class DexOptHelper {
    public static final boolean LOW_DEBUG = SystemProperties.get("ro.boot.debug_level", "0x4f4c").equals("0x4f4c");
    public static final String[] designatedPkgs = {"com.samsung.android.messaging", "com.samsung.android.dialer", "com.sec.android.app.myfiles", "com.sec.android.gallery3d", "com.sec.android.app.camera", KnoxCustomManagerService.SAMSUNG_HONEYBOARD_PKG_NAME};
    public static boolean sArtManagerLocalIsInitialized = false;
    public volatile long mBootDexoptStartTime;
    public final PackageManagerService mPm;

    public static /* synthetic */ boolean lambda$getPackagesForDexopt$8(PackageStateInternal packageStateInternal) {
        return true;
    }

    public static /* synthetic */ boolean lambda$getPackagesForDexopt$9(PackageStateInternal packageStateInternal) {
        return true;
    }

    public DexOptHelper(PackageManagerService packageManagerService) {
        this.mPm = packageManagerService;
    }

    public static String getPrebuildProfilePath(AndroidPackage androidPackage) {
        return androidPackage.getBaseApkPath() + ".prof";
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0106  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0102 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int[] performDexOptUpgrade(List list, int i, boolean z) {
        boolean z2;
        Installer.checkLegacyDexoptDisabled();
        list.size();
        Iterator it = list.iterator();
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        while (it.hasNext()) {
            PackageStateInternal packageStateInternal = (PackageStateInternal) it.next();
            AndroidPackage androidPackage = packageStateInternal.getAndroidPackage();
            if ((this.mPm.isFirstBoot() || this.mPm.isDeviceUpgrading()) && packageStateInternal.isSystem()) {
                File file = new File(getPrebuildProfilePath(androidPackage));
                if (file.exists()) {
                    try {
                        if (!this.mPm.mInstaller.copySystemProfile(file.getAbsolutePath(), androidPackage.getUid(), androidPackage.getPackageName(), ArtManager.getProfileName((String) null))) {
                            Log.e("PackageManager", "Installer failed to copy system profile!");
                        }
                    } catch (Installer.InstallerException | RuntimeException e) {
                        Log.e("PackageManager", "Failed to copy profile " + file.getAbsolutePath() + " ", e);
                    }
                } else {
                    PackageSetting disabledSystemPkgLPr = this.mPm.mSettings.getDisabledSystemPkgLPr(androidPackage.getPackageName());
                    if (disabledSystemPkgLPr != null && disabledSystemPkgLPr.getPkg().isStub()) {
                        File file2 = new File(getPrebuildProfilePath(disabledSystemPkgLPr.getPkg()).replace("-Stub", ""));
                        if (file2.exists()) {
                            try {
                            } catch (Installer.InstallerException | RuntimeException e2) {
                                Log.e("PackageManager", "Failed to copy profile " + file2.getAbsolutePath() + " ", e2);
                            }
                            if (!this.mPm.mInstaller.copySystemProfile(file2.getAbsolutePath(), androidPackage.getUid(), androidPackage.getPackageName(), ArtManager.getProfileName((String) null))) {
                                Log.e("PackageManager", "Failed to copy system profile for stub package!");
                            } else {
                                z2 = true;
                                if (this.mPm.mPackageDexOptimizer.canOptimizePackage(androidPackage)) {
                                    int i5 = z2 ? 9 : i;
                                    if (SystemProperties.getBoolean("pm.precompile_layouts", false)) {
                                        this.mPm.mArtManagerService.compileLayouts(packageStateInternal, androidPackage);
                                    }
                                    int i6 = z ? 4 : 0;
                                    String compilerFilterForReason = PackageManagerServiceCompilerMapping.getCompilerFilterForReason(i5);
                                    if (DexFile.isProfileGuidedCompilerFilter(compilerFilterForReason)) {
                                        i6 |= 1;
                                    }
                                    if (i == 0) {
                                        i6 |= 1024;
                                    }
                                    int performDexOptTraced = performDexOptTraced(new DexoptOptions(androidPackage.getPackageName(), i5, compilerFilterForReason, null, i6));
                                    if (performDexOptTraced == -1) {
                                        i4++;
                                    } else if (performDexOptTraced != 0) {
                                        if (performDexOptTraced == 1) {
                                            i2++;
                                        } else if (performDexOptTraced != 2) {
                                            Log.e("PackageManager", "Unexpected dexopt return code " + performDexOptTraced);
                                        }
                                    }
                                }
                                i3++;
                            }
                        }
                    }
                }
            }
            z2 = false;
            if (this.mPm.mPackageDexOptimizer.canOptimizePackage(androidPackage)) {
            }
            i3++;
        }
        return new int[]{i2, i3, i4};
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't find top splitter block for handler:B:26:0x0096
        	at jadx.core.utils.BlockUtils.getTopSplitterForHandler(BlockUtils.java:1179)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.collectHandlerRegions(ExcHandlersRegionMaker.java:53)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.process(ExcHandlersRegionMaker.java:38)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:27)
        */
    public final void checkAndDexOptSystemUi(int r11) {
        /*
            r10 = this;
            com.android.server.pm.PackageManagerService r0 = r10.mPm
            com.android.server.pm.Computer r0 = r0.snapshotComputer()
            com.android.server.pm.PackageManagerService r1 = r10.mPm
            android.content.Context r1 = r1.mContext
            r2 = 17039418(0x104003a, float:2.4244734E-38)
            java.lang.String r1 = r1.getString(r2)
            com.android.server.pm.pkg.AndroidPackage r0 = r0.getPackage(r1)
            if (r0 != 0) goto L33
            java.lang.String r10 = "PackageManager"
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            java.lang.String r0 = "System UI package "
            r11.append(r0)
            r11.append(r1)
            java.lang.String r0 = " is not found for dexopting"
            r11.append(r0)
            java.lang.String r11 = r11.toString()
            android.util.Log.w(r10, r11)
            return
        L33:
            java.lang.String r2 = com.android.server.pm.PackageManagerServiceCompilerMapping.getCompilerFilterForReason(r11)
            java.lang.String r3 = "dalvik.vm.systemuicompilerfilter"
            java.lang.String r2 = android.os.SystemProperties.get(r3, r2)
            boolean r3 = dalvik.system.DexFile.isProfileGuidedCompilerFilter(r2)
            if (r3 == 0) goto Lb5
            java.lang.String r3 = "verify"
            java.io.File r4 = new java.io.File
            java.lang.String r5 = getPrebuildProfilePath(r0)
            r4.<init>(r5)
            boolean r5 = r4.exists()
            if (r5 == 0) goto Lb4
            com.android.server.pm.PackageManagerService r5 = r10.mPm     // Catch: java.lang.Throwable -> L99
            java.lang.Object r5 = r5.mInstallLock     // Catch: java.lang.Throwable -> L99
            monitor-enter(r5)     // Catch: java.lang.Throwable -> L99
            com.android.server.pm.PackageManagerService r6 = r10.mPm     // Catch: java.lang.Throwable -> L96
            com.android.server.pm.Installer r6 = r6.mInstaller     // Catch: java.lang.Throwable -> L96
            java.lang.String r7 = r4.getAbsolutePath()     // Catch: java.lang.Throwable -> L96
            int r8 = r0.getUid()     // Catch: java.lang.Throwable -> L96
            java.lang.String r0 = r0.getPackageName()     // Catch: java.lang.Throwable -> L96
            r9 = 0
            java.lang.String r9 = android.content.pm.dex.ArtManager.getProfileName(r9)     // Catch: java.lang.Throwable -> L96
            boolean r0 = r6.copySystemProfile(r7, r8, r0, r9)     // Catch: java.lang.Throwable -> L96
            if (r0 == 0) goto L76
            goto L91
        L76:
            java.lang.String r0 = "PackageManager"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L96
            r2.<init>()     // Catch: java.lang.Throwable -> L96
            java.lang.String r6 = "Failed to copy profile "
            r2.append(r6)     // Catch: java.lang.Throwable -> L96
            java.lang.String r6 = r4.getAbsolutePath()     // Catch: java.lang.Throwable -> L96
            r2.append(r6)     // Catch: java.lang.Throwable -> L96
            java.lang.String r2 = r2.toString()     // Catch: java.lang.Throwable -> L96
            android.util.Log.e(r0, r2)     // Catch: java.lang.Throwable -> L96
            r2 = r3
        L91:
            monitor-exit(r5)     // Catch: java.lang.Throwable -> L93
            goto Lb5
        L93:
            r0 = move-exception
            r3 = r2
            goto L97
        L96:
            r0 = move-exception
        L97:
            monitor-exit(r5)     // Catch: java.lang.Throwable -> L96
            throw r0     // Catch: java.lang.Throwable -> L99 java.lang.Throwable -> L99
        L99:
            r0 = move-exception
            java.lang.String r2 = "PackageManager"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "Failed to copy profile "
            r5.append(r6)
            java.lang.String r4 = r4.getAbsolutePath()
            r5.append(r4)
            java.lang.String r4 = r5.toString()
            android.util.Log.e(r2, r4, r0)
        Lb4:
            r2 = r3
        Lb5:
            r10.performDexoptPackage(r1, r11, r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.DexOptHelper.checkAndDexOptSystemUi(int):void");
    }

    public final void dexoptLauncher(int i) {
        Computer snapshotComputer = this.mPm.snapshotComputer();
        for (String str : ((RoleManager) this.mPm.mContext.getSystemService(RoleManager.class)).getRoleHolders("android.app.role.HOME")) {
            if (snapshotComputer.getPackage(str) == null) {
                Log.w("PackageManager", "Launcher package " + str + " is not found for dexopting");
            } else {
                performDexoptPackage(str, i, "speed-profile");
            }
        }
    }

    public final void performDexoptPackage(String str, int i, String str2) {
        Installer.checkLegacyDexoptDisabled();
        performDexOptTraced(new DexoptOptions(str, i, str2, null, DexFile.isProfileGuidedCompilerFilter(str2) ? 1 : 0));
    }

    public void performPackageDexOptUpgradeIfNeeded() {
        int i;
        PackageManagerServiceUtils.enforceSystemOrRoot("Only the system can request package update");
        if (this.mPm.isFirstBoot()) {
            i = 0;
        } else if (this.mPm.isDeviceUpgrading()) {
            i = 1;
        } else if (!hasBcpApexesChanged()) {
            return;
        } else {
            i = 13;
        }
        Log.i("PackageManager", "Starting boot dexopt for reason " + DexoptOptions.convertToArtServiceDexoptReason(i));
        long nanoTime = System.nanoTime();
        if (useArtService()) {
            this.mBootDexoptStartTime = nanoTime;
            getArtManagerLocal().onBoot(DexoptOptions.convertToArtServiceDexoptReason(i), Executors.newSingleThreadExecutor(), new Consumer() { // from class: com.android.server.pm.DexOptHelper$$ExternalSyntheticLambda11
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    DexOptHelper.this.lambda$performPackageDexOptUpgradeIfNeeded$0((OperationProgress) obj);
                }
            });
            if (i == 1) {
                PackageManagerServiceUtils.logCriticalInfo(4, "DEXOPT to compile designatedPkgs for boot-after-ota");
                for (String str : designatedPkgs) {
                    PackageManagerLocal.FilteredSnapshot withFilteredSnapshot = PackageManagerServiceUtils.getPackageManagerLocal().withFilteredSnapshot();
                    try {
                        getArtManagerLocal().dexoptPackage(withFilteredSnapshot, str, new DexoptParams.Builder("boot-after-ota").setCompilerFilter("speed-profile").build());
                        if (withFilteredSnapshot != null) {
                            withFilteredSnapshot.close();
                        }
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
                }
            }
        } else {
            try {
                checkAndDexOptSystemUi(i);
                dexoptLauncher(i);
                if (i != 1 && i != 0) {
                    return;
                }
                int[] performDexOptUpgrade = performDexOptUpgrade(getPackagesForDexopt(this.mPm.snapshotComputer().getPackageStates().values(), this.mPm), i, false);
                reportBootDexopt(nanoTime, performDexOptUpgrade[0], performDexOptUpgrade[1], performDexOptUpgrade[2]);
            } catch (Installer.LegacyDexoptDisabledException e) {
                throw new RuntimeException(e);
            }
        }
        PackageManagerServiceUtils.logCriticalInfo(4, "Finish boot dexopt for " + DexoptOptions.convertToArtServiceDexoptReason(i) + " takes " + TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - nanoTime) + "ms");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$performPackageDexOptUpgradeIfNeeded$0(OperationProgress operationProgress) {
        ((WindowManagerInternal) this.mPm.mInjector.getLocalService(WindowManagerInternal.class)).showBootDialog(operationProgress.getPercentage());
    }

    public final void reportBootDexopt(long j, int i, int i2, int i3) {
        int seconds = (int) TimeUnit.NANOSECONDS.toSeconds(System.nanoTime() - j);
        Computer snapshotComputer = this.mPm.snapshotComputer();
        MetricsLogger.histogram(this.mPm.mContext, "opt_dialog_num_dexopted", i);
        MetricsLogger.histogram(this.mPm.mContext, "opt_dialog_num_skipped", i2);
        MetricsLogger.histogram(this.mPm.mContext, "opt_dialog_num_failed", i3);
        MetricsLogger.histogram(this.mPm.mContext, "opt_dialog_num_total", getOptimizablePackages(snapshotComputer).size());
        MetricsLogger.histogram(this.mPm.mContext, "opt_dialog_time_s", seconds);
    }

    public List getOptimizablePackages(Computer computer) {
        final ArrayList arrayList = new ArrayList();
        this.mPm.forEachPackageState(computer, new Consumer() { // from class: com.android.server.pm.DexOptHelper$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                DexOptHelper.this.lambda$getOptimizablePackages$1(arrayList, (PackageStateInternal) obj);
            }
        });
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getOptimizablePackages$1(ArrayList arrayList, PackageStateInternal packageStateInternal) {
        AndroidPackageInternal pkg = packageStateInternal.getPkg();
        if (pkg == null || !this.mPm.mPackageDexOptimizer.canOptimizePackage(pkg)) {
            return;
        }
        arrayList.add(packageStateInternal.getPackageName());
    }

    public boolean performDexOpt(DexoptOptions dexoptOptions) {
        int performDexOptWithStatus;
        Computer snapshotComputer = this.mPm.snapshotComputer();
        if (snapshotComputer.getInstantAppPackageName(Binder.getCallingUid()) != null || snapshotComputer.isInstantApp(dexoptOptions.getPackageName(), UserHandle.getCallingUserId())) {
            return false;
        }
        AndroidPackage androidPackage = snapshotComputer.getPackage(dexoptOptions.getPackageName());
        if (androidPackage != null && androidPackage.isApex()) {
            return true;
        }
        if (dexoptOptions.isDexoptOnlySecondaryDex()) {
            if (useArtService()) {
                performDexOptWithStatus = performDexOptWithArtService(dexoptOptions, 0);
            } else {
                try {
                    return this.mPm.getDexManager().dexoptSecondaryDex(dexoptOptions);
                } catch (Installer.LegacyDexoptDisabledException e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            performDexOptWithStatus = performDexOptWithStatus(dexoptOptions);
        }
        return performDexOptWithStatus != -1;
    }

    public int performDexOptWithStatus(DexoptOptions dexoptOptions) {
        return performDexOptTraced(dexoptOptions);
    }

    public final int performDexOptTraced(DexoptOptions dexoptOptions) {
        Trace.traceBegin(16384L, "dexopt");
        try {
            return performDexOptInternal(dexoptOptions);
        } finally {
            Trace.traceEnd(16384L);
        }
    }

    public final int performDexOptInternal(DexoptOptions dexoptOptions) {
        if (useArtService()) {
            return performDexOptWithArtService(dexoptOptions, 4);
        }
        synchronized (this.mPm.mLock) {
            AndroidPackage androidPackage = (AndroidPackage) this.mPm.mPackages.get(dexoptOptions.getPackageName());
            PackageSetting packageLPr = this.mPm.mSettings.getPackageLPr(dexoptOptions.getPackageName());
            if (androidPackage != null && packageLPr != null) {
                if (androidPackage.isApex()) {
                    return 0;
                }
                this.mPm.getPackageUsage().maybeWriteAsync(this.mPm.mSettings.getPackagesLocked());
                this.mPm.mCompilerStats.maybeWriteAsync();
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    try {
                        return performDexOptInternalWithDependenciesLI(androidPackage, packageLPr, dexoptOptions);
                    } catch (Installer.LegacyDexoptDisabledException e) {
                        throw new RuntimeException(e);
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
            return -1;
        }
    }

    public final int performDexOptWithArtService(DexoptOptions dexoptOptions, int i) {
        PackageManagerLocal.FilteredSnapshot withFilteredSnapshot = PackageManagerServiceUtils.getPackageManagerLocal().withFilteredSnapshot();
        try {
            PackageState packageState = withFilteredSnapshot.getPackageState(dexoptOptions.getPackageName());
            if (packageState != null) {
                if (packageState.getAndroidPackage() != null) {
                    int convertToDexOptResult = convertToDexOptResult(getArtManagerLocal().dexoptPackage(withFilteredSnapshot, dexoptOptions.getPackageName(), dexoptOptions.convertToDexoptParams(i)));
                    withFilteredSnapshot.close();
                    return convertToDexOptResult;
                }
                withFilteredSnapshot.close();
                return -1;
            }
            withFilteredSnapshot.close();
            return -1;
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
    }

    public final int performDexOptInternalWithDependenciesLI(AndroidPackage androidPackage, PackageStateInternal packageStateInternal, DexoptOptions dexoptOptions) {
        PackageDexOptimizer packageDexOptimizer;
        if ("android".equals(androidPackage.getPackageName())) {
            throw new IllegalArgumentException("Cannot dexopt the system server");
        }
        if (dexoptOptions.isForce()) {
            packageDexOptimizer = new PackageDexOptimizer.ForcedUpdatePackageDexOptimizer(this.mPm.mPackageDexOptimizer);
        } else {
            packageDexOptimizer = this.mPm.mPackageDexOptimizer;
        }
        List<SharedLibraryInfo> findSharedLibraries = SharedLibraryUtils.findSharedLibraries(packageStateInternal);
        String[] appDexInstructionSets = InstructionSets.getAppDexInstructionSets(packageStateInternal.getPrimaryCpuAbi(), packageStateInternal.getSecondaryCpuAbi());
        if (!findSharedLibraries.isEmpty()) {
            DexoptOptions dexoptOptions2 = new DexoptOptions(dexoptOptions.getPackageName(), dexoptOptions.getCompilationReason(), dexoptOptions.getCompilerFilter(), dexoptOptions.getSplitName(), dexoptOptions.getFlags() | 64);
            for (SharedLibraryInfo sharedLibraryInfo : findSharedLibraries) {
                Computer snapshotComputer = this.mPm.snapshotComputer();
                AndroidPackage androidPackage2 = snapshotComputer.getPackage(sharedLibraryInfo.getPackageName());
                PackageStateInternal packageStateInternal2 = snapshotComputer.getPackageStateInternal(sharedLibraryInfo.getPackageName());
                if (androidPackage2 != null && packageStateInternal2 != null) {
                    packageDexOptimizer.performDexOpt(androidPackage2, packageStateInternal2, appDexInstructionSets, this.mPm.getOrCreateCompilerPackageStats(androidPackage2), this.mPm.getDexManager().getPackageUseInfoOrDefault(androidPackage2.getPackageName()), dexoptOptions2);
                }
            }
        }
        return packageDexOptimizer.performDexOpt(androidPackage, packageStateInternal, appDexInstructionSets, this.mPm.getOrCreateCompilerPackageStats(androidPackage), this.mPm.getDexManager().getPackageUseInfoOrDefault(androidPackage.getPackageName()), dexoptOptions);
    }

    public void forceDexOpt(Computer computer, String str) {
        PackageManagerServiceUtils.enforceSystemOrRoot("forceDexOpt");
        PackageStateInternal packageStateInternal = computer.getPackageStateInternal(str);
        AndroidPackageInternal pkg = packageStateInternal == null ? null : packageStateInternal.getPkg();
        if (packageStateInternal == null || pkg == null) {
            throw new IllegalArgumentException("Unknown package: " + str);
        }
        if (pkg.isApex()) {
            throw new IllegalArgumentException("Can't dexopt APEX package: " + str);
        }
        Trace.traceBegin(16384L, "dexopt");
        int performDexOptInternalWithDependenciesLI = performDexOptInternalWithDependenciesLI(pkg, packageStateInternal, new DexoptOptions(str, 12, PackageManagerServiceCompilerMapping.getDefaultCompilerFilter(), null, 6));
        Trace.traceEnd(16384L);
        if (performDexOptInternalWithDependenciesLI == 1) {
            return;
        }
        throw new IllegalStateException("Failed to dexopt: " + performDexOptInternalWithDependenciesLI);
    }

    public boolean performDexOptMode(Computer computer, String str, String str2, boolean z, boolean z2, String str3) {
        if (!PackageManagerServiceUtils.isSystemOrRootOrShell() && !isCallerInstallerForPackage(computer, str)) {
            throw new SecurityException("performDexOptMode");
        }
        int i = (z2 ? 4 : 0) | (z ? 2 : 0);
        if (DexFile.isProfileGuidedCompilerFilter(str2)) {
            i |= 1;
        }
        return performDexOpt(new DexoptOptions(str, 12, str2, str3, i));
    }

    public final boolean isCallerInstallerForPackage(Computer computer, String str) {
        PackageStateInternal packageStateInternal;
        PackageStateInternal packageStateInternal2 = computer.getPackageStateInternal(str);
        return (packageStateInternal2 == null || (packageStateInternal = computer.getPackageStateInternal(packageStateInternal2.getInstallSource().mInstallerPackageName)) == null || packageStateInternal.getPkg().getUid() != Binder.getCallingUid()) ? false : true;
    }

    public boolean performDexOptSecondary(String str, String str2, boolean z) {
        return performDexOpt(new DexoptOptions(str, 12, str2, null, (z ? 2 : 0) | 13));
    }

    public static List getPackagesForDexopt(Collection collection, PackageManagerService packageManagerService) {
        return getPackagesForDexopt(collection, packageManagerService, false);
    }

    public static List getPackagesForDexopt(Collection collection, PackageManagerService packageManagerService, boolean z) {
        Predicate predicate;
        Predicate predicate2;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList(collection);
        arrayList2.removeIf(PackageManagerServiceUtils.REMOVE_IF_NULL_PKG);
        arrayList2.removeIf(PackageManagerServiceUtils.REMOVE_IF_APEX_PKG);
        ArrayList arrayList3 = new ArrayList(arrayList2.size());
        Computer snapshotComputer = packageManagerService.snapshotComputer();
        applyPackageFilter(snapshotComputer, new Predicate() { // from class: com.android.server.pm.DexOptHelper$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean isSystem;
                isSystem = ((PackageStateInternal) obj).isSystem();
                return isSystem;
            }
        }, arrayList, arrayList2, arrayList3, packageManagerService);
        applyPackageFilter(snapshotComputer, new Predicate() { // from class: com.android.server.pm.DexOptHelper$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$getPackagesForDexopt$3;
                lambda$getPackagesForDexopt$3 = DexOptHelper.lambda$getPackagesForDexopt$3((PackageStateInternal) obj);
                return lambda$getPackagesForDexopt$3;
            }
        }, arrayList, arrayList2, arrayList3, packageManagerService);
        final ArraySet packageNamesForIntent = getPackageNamesForIntent(new Intent("android.intent.action.PRE_BOOT_COMPLETED"), 0);
        applyPackageFilter(snapshotComputer, new Predicate() { // from class: com.android.server.pm.DexOptHelper$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$getPackagesForDexopt$4;
                lambda$getPackagesForDexopt$4 = DexOptHelper.lambda$getPackagesForDexopt$4(packageNamesForIntent, (PackageStateInternal) obj);
                return lambda$getPackagesForDexopt$4;
            }
        }, arrayList, arrayList2, arrayList3, packageManagerService);
        final DexManager dexManager = packageManagerService.getDexManager();
        applyPackageFilter(snapshotComputer, new Predicate() { // from class: com.android.server.pm.DexOptHelper$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$getPackagesForDexopt$5;
                lambda$getPackagesForDexopt$5 = DexOptHelper.lambda$getPackagesForDexopt$5(DexManager.this, (PackageStateInternal) obj);
                return lambda$getPackagesForDexopt$5;
            }
        }, arrayList, arrayList2, arrayList3, packageManagerService);
        if (!arrayList2.isEmpty() && packageManagerService.isHistoricalPackageUsageAvailable()) {
            if (z) {
                Log.i("PackageManager", "Looking at historical package use");
            }
            PackageStateInternal packageStateInternal = (PackageStateInternal) Collections.max(arrayList2, Comparator.comparingLong(new ToLongFunction() { // from class: com.android.server.pm.DexOptHelper$$ExternalSyntheticLambda5
                @Override // java.util.function.ToLongFunction
                public final long applyAsLong(Object obj) {
                    long lambda$getPackagesForDexopt$6;
                    lambda$getPackagesForDexopt$6 = DexOptHelper.lambda$getPackagesForDexopt$6((PackageStateInternal) obj);
                    return lambda$getPackagesForDexopt$6;
                }
            }));
            if (z) {
                Log.i("PackageManager", "Taking package " + packageStateInternal.getPackageName() + " as reference in time use");
            }
            long latestForegroundPackageUseTimeInMills = packageStateInternal.getTransientState().getLatestForegroundPackageUseTimeInMills();
            if (latestForegroundPackageUseTimeInMills != 0) {
                final long j = latestForegroundPackageUseTimeInMills - 2764800000L;
                predicate2 = new Predicate() { // from class: com.android.server.pm.DexOptHelper$$ExternalSyntheticLambda6
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        boolean lambda$getPackagesForDexopt$7;
                        lambda$getPackagesForDexopt$7 = DexOptHelper.lambda$getPackagesForDexopt$7(j, (PackageStateInternal) obj);
                        return lambda$getPackagesForDexopt$7;
                    }
                };
            } else {
                predicate2 = new Predicate() { // from class: com.android.server.pm.DexOptHelper$$ExternalSyntheticLambda7
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        boolean lambda$getPackagesForDexopt$8;
                        lambda$getPackagesForDexopt$8 = DexOptHelper.lambda$getPackagesForDexopt$8((PackageStateInternal) obj);
                        return lambda$getPackagesForDexopt$8;
                    }
                };
            }
            sortPackagesByUsageDate(arrayList2, packageManagerService);
            predicate = predicate2;
        } else {
            predicate = new Predicate() { // from class: com.android.server.pm.DexOptHelper$$ExternalSyntheticLambda8
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean lambda$getPackagesForDexopt$9;
                    lambda$getPackagesForDexopt$9 = DexOptHelper.lambda$getPackagesForDexopt$9((PackageStateInternal) obj);
                    return lambda$getPackagesForDexopt$9;
                }
            };
        }
        applyPackageFilter(snapshotComputer, predicate, arrayList, arrayList2, arrayList3, packageManagerService);
        arrayList.removeIf(new Predicate() { // from class: com.android.server.pm.DexOptHelper$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$getPackagesForDexopt$10;
                lambda$getPackagesForDexopt$10 = DexOptHelper.lambda$getPackagesForDexopt$10((PackageStateInternal) obj);
                return lambda$getPackagesForDexopt$10;
            }
        });
        if (z) {
            Log.i("PackageManager", "Packages to be dexopted: " + packagesToString(arrayList));
            Log.i("PackageManager", "Packages skipped from dexopt: " + packagesToString(arrayList2));
        }
        return arrayList;
    }

    public static /* synthetic */ boolean lambda$getPackagesForDexopt$3(PackageStateInternal packageStateInternal) {
        return packageStateInternal.getPkg().isCoreApp();
    }

    public static /* synthetic */ boolean lambda$getPackagesForDexopt$4(ArraySet arraySet, PackageStateInternal packageStateInternal) {
        return arraySet.contains(packageStateInternal.getPackageName());
    }

    public static /* synthetic */ boolean lambda$getPackagesForDexopt$5(DexManager dexManager, PackageStateInternal packageStateInternal) {
        return dexManager.getPackageUseInfoOrDefault(packageStateInternal.getPackageName()).isAnyCodePathUsedByOtherApps();
    }

    public static /* synthetic */ long lambda$getPackagesForDexopt$6(PackageStateInternal packageStateInternal) {
        return packageStateInternal.getTransientState().getLatestForegroundPackageUseTimeInMills();
    }

    public static /* synthetic */ boolean lambda$getPackagesForDexopt$7(long j, PackageStateInternal packageStateInternal) {
        return packageStateInternal.getTransientState().getLatestForegroundPackageUseTimeInMills() >= j;
    }

    public static /* synthetic */ boolean lambda$getPackagesForDexopt$10(PackageStateInternal packageStateInternal) {
        return "android".equals(packageStateInternal.getPackageName());
    }

    public static void applyPackageFilter(Computer computer, Predicate predicate, Collection collection, Collection collection2, List list, PackageManagerService packageManagerService) {
        Iterator it = collection2.iterator();
        while (it.hasNext()) {
            PackageStateInternal packageStateInternal = (PackageStateInternal) it.next();
            if (predicate.test(packageStateInternal)) {
                list.add(packageStateInternal);
            }
        }
        sortPackagesByUsageDate(list, packageManagerService);
        collection2.removeAll(list);
        Iterator it2 = list.iterator();
        while (it2.hasNext()) {
            PackageStateInternal packageStateInternal2 = (PackageStateInternal) it2.next();
            collection.add(packageStateInternal2);
            List findSharedNonSystemLibraries = computer.findSharedNonSystemLibraries(packageStateInternal2);
            if (!findSharedNonSystemLibraries.isEmpty()) {
                findSharedNonSystemLibraries.removeAll(collection);
                collection.addAll(findSharedNonSystemLibraries);
                collection2.removeAll(findSharedNonSystemLibraries);
            }
        }
        list.clear();
    }

    public static void sortPackagesByUsageDate(List list, PackageManagerService packageManagerService) {
        if (packageManagerService.isHistoricalPackageUsageAvailable()) {
            Collections.sort(list, new Comparator() { // from class: com.android.server.pm.DexOptHelper$$ExternalSyntheticLambda10
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    int lambda$sortPackagesByUsageDate$11;
                    lambda$sortPackagesByUsageDate$11 = DexOptHelper.lambda$sortPackagesByUsageDate$11((PackageStateInternal) obj, (PackageStateInternal) obj2);
                    return lambda$sortPackagesByUsageDate$11;
                }
            });
        }
    }

    public static /* synthetic */ int lambda$sortPackagesByUsageDate$11(PackageStateInternal packageStateInternal, PackageStateInternal packageStateInternal2) {
        return Long.compare(packageStateInternal2.getTransientState().getLatestForegroundPackageUseTimeInMills(), packageStateInternal.getTransientState().getLatestForegroundPackageUseTimeInMills());
    }

    public static ArraySet getPackageNamesForIntent(Intent intent, int i) {
        List list;
        try {
            list = AppGlobals.getPackageManager().queryIntentReceivers(intent, (String) null, 0L, i).getList();
        } catch (RemoteException unused) {
            list = null;
        }
        ArraySet arraySet = new ArraySet();
        if (list != null) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                arraySet.add(((ResolveInfo) it.next()).activityInfo.packageName);
            }
        }
        return arraySet;
    }

    public static String packagesToString(List list) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append(((PackageStateInternal) list.get(i)).getPackageName());
        }
        return sb.toString();
    }

    public static void requestCopyPreoptedFiles() {
        if (SystemProperties.getInt("ro.cp_system_other_odex", 0) == 1) {
            SystemProperties.set("sys.cppreopt", "requested");
            long uptimeMillis = SystemClock.uptimeMillis();
            long j = 100000 + uptimeMillis;
            long j2 = uptimeMillis;
            while (true) {
                if (SystemProperties.get("sys.cppreopt").equals("finished")) {
                    break;
                }
                try {
                    Thread.sleep(100L);
                } catch (InterruptedException unused) {
                }
                j2 = SystemClock.uptimeMillis();
                if (j2 > j) {
                    SystemProperties.set("sys.cppreopt", "timed-out");
                    Slog.wtf("PackageManager", "cppreopt did not finish!");
                    break;
                }
            }
            Slog.i("PackageManager", "cppreopts took " + (j2 - uptimeMillis) + " ms");
        }
    }

    public void controlDexOptBlocking(boolean z) {
        this.mPm.mPackageDexOptimizer.controlDexOptBlocking(z);
    }

    public static void dumpDexoptState(IndentingPrintWriter indentingPrintWriter, String str) {
        PackageManagerLocal.FilteredSnapshot withFilteredSnapshot = PackageManagerServiceUtils.getPackageManagerLocal().withFilteredSnapshot();
        try {
            if (str != null) {
                try {
                    getArtManagerLocal().dumpPackage(indentingPrintWriter, withFilteredSnapshot, str);
                } catch (IllegalArgumentException e) {
                    indentingPrintWriter.println(e);
                }
            } else {
                getArtManagerLocal().dump(indentingPrintWriter, withFilteredSnapshot);
            }
            if (withFilteredSnapshot != null) {
                withFilteredSnapshot.close();
            }
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
    }

    public static List getBcpApexes() {
        String str = System.getenv("BOOTCLASSPATH");
        if (TextUtils.isEmpty(str)) {
            Log.e("PackageManager", "Unable to get BOOTCLASSPATH");
            return List.of();
        }
        ArrayList arrayList = new ArrayList();
        for (String str2 : str.split(XmlUtils.STRING_ARRAY_SEPARATOR)) {
            Path path = Paths.get(str2, new String[0]);
            if (path.getNameCount() >= 2 && path.getName(0).toString().equals("apex")) {
                arrayList.add(path.getName(1).toString());
            }
        }
        return arrayList;
    }

    public static boolean hasBcpApexesChanged() {
        HashSet hashSet = new HashSet(getBcpApexes());
        for (ApexManager.ActiveApexInfo activeApexInfo : ApexManager.getInstance().getActiveApexInfos()) {
            if (hashSet.contains(activeApexInfo.apexModuleName) && activeApexInfo.activeApexChanged) {
                return true;
            }
        }
        return false;
    }

    public static boolean useArtService() {
        return SystemProperties.getBoolean("dalvik.vm.useartservice", false);
    }

    public static DexUseManagerLocal getDexUseManagerLocal() {
        if (!useArtService()) {
            return null;
        }
        try {
            return (DexUseManagerLocal) LocalManagerRegistry.getManagerOrThrow(DexUseManagerLocal.class);
        } catch (LocalManagerRegistry.ManagerNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public class DexoptDoneHandler implements ArtManagerLocal.DexoptDoneCallback {
        public DexoptDoneHandler() {
        }

        public void onDexoptDone(DexoptResult dexoptResult) {
            String reason = dexoptResult.getReason();
            reason.hashCode();
            switch (reason) {
                case "boot-after-mainline-update":
                case "boot-after-ota":
                case "first-boot":
                    Iterator it = dexoptResult.getPackageDexoptResults().iterator();
                    int i = 0;
                    int i2 = 0;
                    int i3 = 0;
                    while (it.hasNext()) {
                        int status = ((DexoptResult.PackageDexoptResult) it.next()).getStatus();
                        if (status == 10) {
                            i2++;
                        } else if (status == 20) {
                            i++;
                        } else if (status == 30) {
                            i3++;
                        }
                    }
                    DexOptHelper dexOptHelper = DexOptHelper.this;
                    dexOptHelper.reportBootDexopt(dexOptHelper.mBootDexoptStartTime, i, i2, i3);
                    break;
            }
            for (DexoptResult.PackageDexoptResult packageDexoptResult : dexoptResult.getPackageDexoptResults()) {
                CompilerStats.PackageStats orCreateCompilerPackageStats = DexOptHelper.this.mPm.getOrCreateCompilerPackageStats(packageDexoptResult.getPackageName());
                for (DexoptResult.DexContainerFileDexoptResult dexContainerFileDexoptResult : packageDexoptResult.getDexContainerFileDexoptResults()) {
                    orCreateCompilerPackageStats.setCompileTime(dexContainerFileDexoptResult.getDexContainerFile(), dexContainerFileDexoptResult.getDex2oatWallTimeMillis());
                }
            }
            synchronized (DexOptHelper.this.mPm.mLock) {
                DexOptHelper.this.mPm.getPackageUsage().maybeWriteAsync(DexOptHelper.this.mPm.mSettings.getPackagesLocked());
                DexOptHelper.this.mPm.mCompilerStats.maybeWriteAsync();
            }
            if (dexoptResult.getReason().equals("inactive")) {
                for (DexoptResult.PackageDexoptResult packageDexoptResult2 : dexoptResult.getPackageDexoptResults()) {
                    if (packageDexoptResult2.getStatus() == 20) {
                        long j = 0;
                        long j2 = 0;
                        for (DexoptResult.DexContainerFileDexoptResult dexContainerFileDexoptResult2 : packageDexoptResult2.getDexContainerFileDexoptResults()) {
                            long length = new File(dexContainerFileDexoptResult2.getDexContainerFile()).length();
                            j2 += dexContainerFileDexoptResult2.getSizeBytes() + length;
                            j += dexContainerFileDexoptResult2.getSizeBeforeBytes() + length;
                        }
                        FrameworkStatsLog.write(128, packageDexoptResult2.getPackageName(), j, j2, false);
                    }
                }
            }
            ArraySet arraySet = new ArraySet();
            for (DexoptResult.PackageDexoptResult packageDexoptResult3 : dexoptResult.getPackageDexoptResults()) {
                if (packageDexoptResult3.hasUpdatedArtifacts()) {
                    arraySet.add(packageDexoptResult3.getPackageName());
                }
            }
            if (arraySet.isEmpty()) {
                return;
            }
            ((PinnerService) LocalServices.getService(PinnerService.class)).update(arraySet, false);
        }
    }

    public static void initializeArtManagerLocal(final Context context, final PackageManagerService packageManagerService) {
        if (useArtService()) {
            final ArtManagerLocal artManagerLocal = new ArtManagerLocal(context);
            SystemServerInitThreadPool$$ExternalSyntheticLambda1 systemServerInitThreadPool$$ExternalSyntheticLambda1 = new SystemServerInitThreadPool$$ExternalSyntheticLambda1();
            DexOptHelper dexOptHelper = packageManagerService.getDexOptHelper();
            Objects.requireNonNull(dexOptHelper);
            artManagerLocal.addDexoptDoneCallback(false, systemServerInitThreadPool$$ExternalSyntheticLambda1, new DexoptDoneHandler());
            if (!LOW_DEBUG) {
                artManagerLocal.addDexoptDoneCallback(false, new SystemServerInitThreadPool$$ExternalSyntheticLambda1(), new ArtManagerLocal.DexoptDoneCallback() { // from class: com.android.server.pm.DexOptHelper$$ExternalSyntheticLambda12
                    public final void onDexoptDone(DexoptResult dexoptResult) {
                        DexOptHelper.lambda$initializeArtManagerLocal$12(dexoptResult);
                    }
                });
            }
            artManagerLocal.addDexoptDoneCallback(false, new SystemServerInitThreadPool$$ExternalSyntheticLambda1(), new ArtManagerLocal.DexoptDoneCallback() { // from class: com.android.server.pm.DexOptHelper$$ExternalSyntheticLambda13
                public final void onDexoptDone(DexoptResult dexoptResult) {
                    DexOptHelper.lambda$initializeArtManagerLocal$13(dexoptResult);
                }
            });
            artManagerLocal.setBatchDexoptStartCallback(new SystemServerInitThreadPool$$ExternalSyntheticLambda1(), new ArtManagerLocal.BatchDexoptStartCallback() { // from class: com.android.server.pm.DexOptHelper$$ExternalSyntheticLambda14
                public final void onBatchDexoptStart(PackageManagerLocal.FilteredSnapshot filteredSnapshot, String str, List list, BatchDexoptParams.Builder builder, CancellationSignal cancellationSignal) {
                    DexOptHelper.lambda$initializeArtManagerLocal$14(context, packageManagerService, artManagerLocal, filteredSnapshot, str, list, builder, cancellationSignal);
                }
            });
            LocalManagerRegistry.addManager(ArtManagerLocal.class, artManagerLocal);
            sArtManagerLocalIsInitialized = true;
            context.registerReceiver(new BroadcastReceiver() { // from class: com.android.server.pm.DexOptHelper.1
                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context2, Intent intent) {
                    context2.unregisterReceiver(this);
                    artManagerLocal.scheduleBackgroundDexoptJob();
                }
            }, new IntentFilter("android.intent.action.LOCKED_BOOT_COMPLETED"));
        }
    }

    public static /* synthetic */ void lambda$initializeArtManagerLocal$12(DexoptResult dexoptResult) {
        if (dexoptResult.getFinalStatus() == 30) {
            saveDexOptLog();
        }
    }

    public static /* synthetic */ void lambda$initializeArtManagerLocal$13(DexoptResult dexoptResult) {
        if (dexoptResult.getReason().equals("bg-dexopt")) {
            Iterator it = dexoptResult.getPackageDexoptResults().iterator();
            int i = 0;
            int i2 = 0;
            int i3 = 0;
            int i4 = 0;
            while (it.hasNext()) {
                int status = ((DexoptResult.PackageDexoptResult) it.next()).getStatus();
                if (status == 10) {
                    i2++;
                } else if (status == 20) {
                    i++;
                } else if (status == 30) {
                    i3++;
                } else if (status == 40) {
                    i4++;
                }
            }
            PackageManagerServiceUtils.logCriticalInfo(4, "bg-dexopt dexopted: " + i + ", skipped: " + i2 + ", failed: " + i3 + ", cancelled: " + i4);
        }
        if (dexoptResult.getReason().equals("install") && dexoptResult.getFinalStatus() == 30) {
            PackageManagerServiceUtils.logCriticalInfo(4, "[INSTALL] Dexopt for " + ((DexoptResult.PackageDexoptResult) dexoptResult.getPackageDexoptResults().get(0)).getPackageName() + " failed");
        }
    }

    public static /* synthetic */ void lambda$initializeArtManagerLocal$14(Context context, PackageManagerService packageManagerService, ArtManagerLocal artManagerLocal, PackageManagerLocal.FilteredSnapshot filteredSnapshot, String str, List list, BatchDexoptParams.Builder builder, CancellationSignal cancellationSignal) {
        if (str.equals("bg-dexopt")) {
            PackageManagerServiceUtils.logCriticalInfo(4, "DEXOPT for bg-dexopt");
            BgDexOptHelper bgDexOptHelper = new BgDexOptHelper(context, packageManagerService.mInjector.getHandler(), artManagerLocal);
            if (!bgDexOptHelper.canRunBgDexOpt()) {
                cancellationSignal.cancel();
            } else {
                List reorderPkgListBasedOnPriority = reorderPkgListBasedOnPriority(packageManagerService, bgDexOptHelper, list);
                builder.setPackages(reorderPkgListBasedOnPriority).setDexoptParams(new DexoptParams.Builder("bg-dexopt").build());
                bgDexOptHelper.startObserveThermal();
            }
        }
        if (str.equals("boot-after-ota")) {
            PackageManagerServiceUtils.logCriticalInfo(4, "DEXOPT for boot-after-ota");
        } else if (str.equals("first-boot")) {
            PackageManagerServiceUtils.logCriticalInfo(4, "DEXOPT for first-boot");
        }
        if (str.equals("boot-after-mainline-update")) {
            PackageManagerServiceUtils.logCriticalInfo(4, "DEXOPT for boot-after-mainline-update");
            ArrayList arrayList = new ArrayList(Arrays.asList(designatedPkgs));
            DexoptParams build = new DexoptParams.Builder("boot-after-mainline-update").setCompilerFilter("speed-profile").build();
            arrayList.addAll(list);
            builder.setPackages(arrayList).setDexoptParams(build);
        }
    }

    public static List reorderPkgListBasedOnPriority(PackageManagerService packageManagerService, BgDexOptHelper bgDexOptHelper, List list) {
        ArrayList arrayList = new ArrayList();
        Computer snapshotComputer = packageManagerService.snapshotComputer();
        Slog.d("PackageManager", "Reordering packages based on priority");
        if (bgDexOptHelper.getBatteryLevel() <= 80) {
            PackageManagerServiceUtils.logCriticalInfo(4, "Not fully charged. Optimize top-priority packages only");
            BgDexOptHelper.initSurfaceTemperature();
            BgDexOptHelper.setTemperaturePolicy(true);
            BgDexOptHelper.toggleBatteryLevelPolicy();
            arrayList.addAll(snapshotComputer.getTopPriorityPackages(TimeUnit.DAYS.toMillis(3L), BgDexOptHelper.getInitialSurfaceTemperature() > 390 ? 5 : -1));
            PackageManagerServiceUtils.logCriticalInfo(4, "Target Packages (size : " + arrayList.size() + ") " + String.join(",", arrayList));
            return arrayList;
        }
        Set unusedPackages = snapshotComputer.getUnusedPackages(TimeUnit.DAYS.toMillis(10L));
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.LAUNCHER");
        ArraySet arraySet = new ArraySet();
        for (ResolveInfo resolveInfo : packageManagerService.getIPackageManager().queryIntentActivities(intent, intent.getType(), 0L, 0).getList()) {
            if (list.contains(resolveInfo.activityInfo.packageName)) {
                arraySet.add(resolveInfo.activityInfo.packageName);
            }
        }
        ArrayList arrayList2 = new ArrayList(list);
        arrayList2.removeAll(unusedPackages);
        ArrayList arrayList3 = new ArrayList(arrayList2);
        arrayList3.retainAll(arraySet);
        arrayList2.removeAll(arrayList3);
        arrayList3.addAll(arrayList2);
        ArrayList arrayList4 = new ArrayList(list);
        arrayList4.retainAll(arraySet);
        arrayList4.removeAll(arrayList3);
        ArrayList arrayList5 = new ArrayList(list);
        arrayList5.removeAll(arrayList3);
        arrayList5.removeAll(arrayList4);
        arrayList.addAll(arrayList3);
        arrayList.addAll(arrayList4);
        arrayList.addAll(arrayList5);
        Slog.d("PackageManager", "Optimizable Packages(size : " + list.size() + ") " + String.join(",", list));
        Slog.d("PackageManager", "Recently used Packages(size : " + arrayList3.size() + ") " + String.join(",", arrayList3));
        Slog.d("PackageManager", "Executable Packages(size : " + arrayList4.size() + ") " + String.join(",", arrayList4));
        Slog.d("PackageManager", "Remaining Packages(size : " + arrayList5.size() + ") " + String.join(",", arrayList5));
        return arrayList;
    }

    public class BgDexOptHelper implements ArtManagerLocal.DexoptDoneCallback {
        public static int SurfaceTemperatureThreshold = 0;
        public static int abortCount = 0;
        public static boolean enableTemperaturePolicy = false;
        public static int initialSurfaceTemperature = 0;
        public static boolean policySelector = true;
        public ArtManagerLocal mArtManager;
        public Context mContext;
        public MyHandler mHandler;
        public volatile boolean mObserveStarted = false;

        public BgDexOptHelper(Context context, Handler handler, ArtManagerLocal artManagerLocal) {
            this.mContext = context;
            this.mHandler = new MyHandler(handler);
            this.mArtManager = artManagerLocal;
        }

        public void startObserveThermal() {
            Slog.d("BgDexOptHelper", "Start observing thermal");
            this.mObserveStarted = true;
            scheduleObserveThermal();
            this.mArtManager.addDexoptDoneCallback(false, new SystemServerInitThreadPool$$ExternalSyntheticLambda1(), this);
        }

        public void onDexoptDone(DexoptResult dexoptResult) {
            if (dexoptResult.getReason().equals("bg-dexopt")) {
                Slog.d("BgDexOptHelper", "Bg dexopt finished");
                stopObserveThermal();
                this.mArtManager.removeDexoptDoneCallback(this);
            }
        }

        public static void toggleBatteryLevelPolicy() {
            int i = abortCount + 1;
            abortCount = i;
            policySelector = false;
            if (i > 3) {
                abortCount = 0;
                policySelector = true;
            }
        }

        public static int getAbortCount() {
            return abortCount;
        }

        public static void initSurfaceTemperature() {
            int skinTemperature = getSkinTemperature();
            initialSurfaceTemperature = skinTemperature;
            if (skinTemperature <= 360) {
                SurfaceTemperatureThreshold = skinTemperature + 10;
            } else if (skinTemperature <= 370) {
                SurfaceTemperatureThreshold = skinTemperature + 8;
            } else if (skinTemperature <= 380) {
                SurfaceTemperatureThreshold = skinTemperature + 5;
            } else if (skinTemperature <= 390) {
                SurfaceTemperatureThreshold = skinTemperature + 2;
            } else {
                SurfaceTemperatureThreshold = 400;
            }
            Log.d("PackageManager", "SurfaceTemperature [" + initialSurfaceTemperature + "] threashold [" + SurfaceTemperatureThreshold + "]");
        }

        public static int getSkinTemperature() {
            SemTemperatureManager.Thermistor thermistor = SemTemperatureManager.getThermistor(9);
            if (thermistor != null) {
                return thermistor.getTemperature();
            }
            return -1;
        }

        public static void setTemperaturePolicy(boolean z) {
            enableTemperaturePolicy = z;
        }

        public static boolean getTemperaturePolicy() {
            return enableTemperaturePolicy;
        }

        public static int getInitialSurfaceTemperature() {
            return initialSurfaceTemperature;
        }

        public static boolean exceedSurfaceTemperatureThreshold() {
            int skinTemperature = getSkinTemperature();
            if (skinTemperature <= SurfaceTemperatureThreshold) {
                return false;
            }
            PackageManagerServiceUtils.logCriticalInfo(4, "Aborted by thermal: " + skinTemperature + " (initial : " + initialSurfaceTemperature + " threshold : " + SurfaceTemperatureThreshold + ")");
            return true;
        }

        public void stopObserveThermal() {
            Slog.d("BgDexOptHelper", "Stop observing thermal");
            this.mObserveStarted = false;
            unscheduleObserveThermal();
        }

        public final boolean isBatteryFullyCharged() {
            int batteryLevel = getBatteryLevel();
            if (policySelector && batteryLevel <= 80) {
                Slog.d("BgDexOptHelper", "Not fully charged. Optimize only recently used packages");
                return true;
            }
            BatteryManager batteryManager = (BatteryManager) this.mContext.getSystemService(BatteryManager.class);
            if (batteryLevel < 100) {
                return batteryManager != null && batteryManager.computeChargeTimeRemaining() <= 0;
            }
            return true;
        }

        public final int getBatteryLevel() {
            return ((BatteryManagerInternal) LocalServices.getService(BatteryManagerInternal.class)).getBatteryLevel();
        }

        public boolean canRunBgDexOpt() {
            if (!isBatteryFullyCharged()) {
                PackageManagerServiceUtils.logCriticalInfo(4, "Can't run bg dexopt by not fully charged: " + getBatteryLevel() + " abortCount: " + getAbortCount());
                toggleBatteryLevelPolicy();
                return false;
            }
            if (!reachedToThermalThrottleLevel()) {
                return true;
            }
            PackageManagerServiceUtils.logCriticalInfo(4, "Can't run bg dexopt by thermal throttling");
            return false;
        }

        public final boolean reachedToThermalThrottleLevel() {
            if (getTemperaturePolicy()) {
                return exceedSurfaceTemperatureThreshold();
            }
            SemTemperatureManager.Thermistor thermistor = SemTemperatureManager.getThermistor(9);
            if (thermistor == null) {
                return false;
            }
            int temperature = thermistor.getTemperature();
            int i = isBatteryFullyCharged() ? 400 : 375;
            boolean z = temperature >= i;
            if (z) {
                PackageManagerServiceUtils.logCriticalInfo(4, "Current temperature: " + temperature + ", criteria: " + i + ", reached: " + z);
            }
            return z;
        }

        public final void scheduleObserveThermal() {
            MyHandler myHandler = this.mHandler;
            myHandler.sendMessageDelayed(myHandler.obtainMessage(1), 2000L);
        }

        public final void unscheduleObserveThermal() {
            if (this.mHandler.hasMessages(1)) {
                this.mHandler.removeMessages(1);
            }
        }

        public final void cancelBackgroundDexoptJob() {
            Slog.d("BgDexOptHelper", "Cancel bg dexopt job");
            this.mArtManager.cancelBackgroundDexoptJob();
        }

        public final class MyHandler extends Handler {
            public MyHandler(Handler handler) {
                super(handler.getLooper());
            }

            @Override // android.os.Handler
            public void handleMessage(Message message) {
                if (BgDexOptHelper.this.reachedToThermalThrottleLevel()) {
                    PackageManagerServiceUtils.logCriticalInfo(4, "Canceling bg dexopt by thermal throttling");
                    BgDexOptHelper.this.cancelBackgroundDexoptJob();
                    BgDexOptHelper.this.stopObserveThermal();
                } else if (BgDexOptHelper.this.mObserveStarted) {
                    BgDexOptHelper.this.scheduleObserveThermal();
                }
            }
        }
    }

    public static void saveDexOptLog() {
        Slog.i("PackageManager", "Call saveDexOptLog");
        try {
            Runtime.getRuntime().exec("rm /data/log/dexoptfail_log");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Runtime.getRuntime().exec("logcat -v raw -b main,system -t 3000 -f /data/log/dexoptfail_log").waitFor();
        } catch (IOException | InterruptedException e2) {
            e2.printStackTrace();
        }
        Slog.i("PackageManager", "End saveDexOptLog");
    }

    public static boolean artManagerLocalIsInitialized() {
        return sArtManagerLocalIsInitialized;
    }

    public static ArtManagerLocal getArtManagerLocal() {
        try {
            return (ArtManagerLocal) LocalManagerRegistry.getManagerOrThrow(ArtManagerLocal.class);
        } catch (LocalManagerRegistry.ManagerNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static int convertToDexOptResult(DexoptResult dexoptResult) {
        int finalStatus = dexoptResult.getFinalStatus();
        if (finalStatus == 10) {
            return 0;
        }
        if (finalStatus == 20) {
            return 1;
        }
        if (finalStatus == 30) {
            return -1;
        }
        if (finalStatus == 40) {
            return 2;
        }
        throw new IllegalArgumentException("DexoptResult for " + ((DexoptResult.PackageDexoptResult) dexoptResult.getPackageDexoptResults().get(0)).getPackageName() + " has unsupported status " + finalStatus);
    }
}
