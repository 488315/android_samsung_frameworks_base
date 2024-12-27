package com.android.server.pm;

import android.app.AppGlobals;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ResolveInfo;
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
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.util.Slog;

import com.android.internal.logging.MetricsLogger;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.LocalManagerRegistry;
import com.android.server.LocalServices;
import com.android.server.PinnerService;
import com.android.server.SystemServerInitThreadPool$$ExternalSyntheticLambda0;
import com.android.server.art.ArtManagerLocal;
import com.android.server.art.DexUseManagerLocal;
import com.android.server.art.model.BatchDexoptParams;
import com.android.server.art.model.DexoptParams;
import com.android.server.art.model.DexoptResult;
import com.android.server.audio.AudioService$$ExternalSyntheticOutline0;
import com.android.server.devicepolicy.PersonalAppsSuspensionHelper$$ExternalSyntheticOutline0;
import com.android.server.pm.DexOptHelper.BgDexOptHelper.MyHandler;
import com.android.server.pm.dex.DexManager;
import com.android.server.pm.dex.DexoptOptions;
import com.android.server.pm.dex.PackageDexUsage;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.PackageState;
import com.android.server.pm.pkg.PackageStateInternal;

import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.os.SemTemperatureManager;
import com.samsung.android.rune.CoreRune;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class DexOptHelper {
    public static final String[] designatedPkgs = {
        "com.samsung.android.messaging",
        "com.samsung.android.dialer",
        "com.sec.android.app.myfiles",
        "com.sec.android.gallery3d",
        "com.sec.android.app.camera",
        KnoxCustomManagerService.SAMSUNG_HONEYBOARD_PKG_NAME
    };
    public static boolean sArtManagerLocalIsInitialized;
    public volatile long mBootDexoptStartTime;
    public final PackageManagerService mPm;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class BgDexOptHelper implements ArtManagerLocal.DexoptDoneCallback {
        public static int SurfaceTemperatureThreshold = 0;
        public static int abortCount = 0;
        public static boolean enableTemperaturePolicy = false;
        public static int initialSurfaceTemperature = 0;
        public static boolean policySelector = true;
        public ArtManagerLocal mArtManager;
        public Context mContext;
        public MyHandler mHandler;
        public volatile boolean mObserveStarted;

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class MyHandler extends Handler {
            public MyHandler(Handler handler) {
                super(handler.getLooper());
            }

            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                if (!BgDexOptHelper.this.reachedToThermalThrottleLevel()) {
                    if (BgDexOptHelper.this.mObserveStarted) {
                        MyHandler myHandler = BgDexOptHelper.this.mHandler;
                        myHandler.sendMessageDelayed(myHandler.obtainMessage(1), 2000L);
                        return;
                    }
                    return;
                }
                PackageManagerServiceUtils.logCriticalInfo(
                        4, "Canceling bg dexopt by thermal throttling");
                BgDexOptHelper bgDexOptHelper = BgDexOptHelper.this;
                bgDexOptHelper.getClass();
                Slog.d("BgDexOptHelper", "Cancel bg dexopt job");
                bgDexOptHelper.mArtManager.cancelBackgroundDexoptJob();
                BgDexOptHelper bgDexOptHelper2 = BgDexOptHelper.this;
                bgDexOptHelper2.getClass();
                Slog.d("BgDexOptHelper", "Stop observing thermal");
                bgDexOptHelper2.mObserveStarted = false;
                if (bgDexOptHelper2.mHandler.hasMessages(1)) {
                    bgDexOptHelper2.mHandler.removeMessages(1);
                }
            }
        }

        public final boolean isBatteryFullyCharged() {
            int batteryLevel =
                    ((BatteryManagerInternal)
                                    LocalServices.getService(BatteryManagerInternal.class))
                            .getBatteryLevel();
            if (policySelector && batteryLevel <= 80) {
                Slog.d("BgDexOptHelper", "Not fully charged. Optimize only recently used packages");
                return true;
            }
            BatteryManager batteryManager =
                    (BatteryManager) this.mContext.getSystemService(BatteryManager.class);
            if (batteryLevel < 100) {
                return batteryManager != null && batteryManager.computeChargeTimeRemaining() <= 0;
            }
            return true;
        }

        public final void onDexoptDone(DexoptResult dexoptResult) {
            if (dexoptResult.getReason().equals("bg-dexopt")) {
                Slog.d("BgDexOptHelper", "Bg dexopt finished");
                Slog.d("BgDexOptHelper", "Stop observing thermal");
                this.mObserveStarted = false;
                if (this.mHandler.hasMessages(1)) {
                    this.mHandler.removeMessages(1);
                }
                this.mArtManager.removeDexoptDoneCallback(this);
            }
        }

        public final boolean reachedToThermalThrottleLevel() {
            if (enableTemperaturePolicy) {
                SemTemperatureManager.Thermistor thermistor =
                        SemTemperatureManager.getThermistor(9);
                int temperature = thermistor != null ? thermistor.getTemperature() : -1;
                if (temperature <= SurfaceTemperatureThreshold) {
                    return false;
                }
                StringBuilder m =
                        BatteryService$$ExternalSyntheticOutline0.m(
                                temperature, "Aborted by thermal: ", " (initial : ");
                m.append(initialSurfaceTemperature);
                m.append(" threshold : ");
                m.append(SurfaceTemperatureThreshold);
                m.append(")");
                PackageManagerServiceUtils.logCriticalInfo(4, m.toString());
                return true;
            }
            SemTemperatureManager.Thermistor thermistor2 = SemTemperatureManager.getThermistor(9);
            if (thermistor2 == null) {
                return false;
            }
            int temperature2 = thermistor2.getTemperature();
            int i = isBatteryFullyCharged() ? 400 : 375;
            boolean z = temperature2 >= i;
            if (z) {
                StringBuilder m2 =
                        ArrayUtils$$ExternalSyntheticOutline0.m(
                                temperature2,
                                i,
                                "Current temperature: ",
                                ", criteria: ",
                                ", reached: ");
                m2.append(z);
                PackageManagerServiceUtils.logCriticalInfo(4, m2.toString());
            }
            return z;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DexoptDoneHandler implements ArtManagerLocal.DexoptDoneCallback {
        public DexoptDoneHandler() {}

        public final void onDexoptDone(DexoptResult dexoptResult) {
            String reason = dexoptResult.getReason();
            reason.getClass();
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
                    final DexOptHelper dexOptHelper = DexOptHelper.this;
                    int seconds =
                            (int)
                                    TimeUnit.NANOSECONDS.toSeconds(
                                            System.nanoTime() - dexOptHelper.mBootDexoptStartTime);
                    PackageManagerService packageManagerService = dexOptHelper.mPm;
                    Computer snapshotComputer = packageManagerService.snapshotComputer();
                    MetricsLogger.histogram(
                            packageManagerService.mContext, "opt_dialog_num_dexopted", i);
                    MetricsLogger.histogram(
                            packageManagerService.mContext, "opt_dialog_num_skipped", i2);
                    MetricsLogger.histogram(
                            packageManagerService.mContext, "opt_dialog_num_failed", i3);
                    Context context = packageManagerService.mContext;
                    final ArrayList arrayList = new ArrayList();
                    PackageManagerService.forEachPackageState(
                            snapshotComputer,
                            new Consumer() { // from class:
                                             // com.android.server.pm.DexOptHelper$$ExternalSyntheticLambda12
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    DexOptHelper dexOptHelper2 = DexOptHelper.this;
                                    ArrayList arrayList2 = arrayList;
                                    PackageStateInternal packageStateInternal =
                                            (PackageStateInternal) obj;
                                    dexOptHelper2.getClass();
                                    AndroidPackage pkg = packageStateInternal.getPkg();
                                    if (pkg == null
                                            || !dexOptHelper2.mPm.mPackageDexOptimizer
                                                    .canOptimizePackage(pkg)) {
                                        return;
                                    }
                                    arrayList2.add(packageStateInternal.getPackageName());
                                }
                            });
                    MetricsLogger.histogram(context, "opt_dialog_num_total", arrayList.size());
                    MetricsLogger.histogram(
                            packageManagerService.mContext, "opt_dialog_time_s", seconds);
                    break;
            }
            for (DexoptResult.PackageDexoptResult packageDexoptResult :
                    dexoptResult.getPackageDexoptResults()) {
                CompilerStats.PackageStats orCreatePackageStats =
                        DexOptHelper.this.mPm.mCompilerStats.getOrCreatePackageStats(
                                packageDexoptResult.getPackageName());
                for (DexoptResult.DexContainerFileDexoptResult dexContainerFileDexoptResult :
                        packageDexoptResult.getDexContainerFileDexoptResults()) {
                    orCreatePackageStats.setCompileTime(
                            dexContainerFileDexoptResult.getDex2oatWallTimeMillis(),
                            dexContainerFileDexoptResult.getDexContainerFile());
                }
            }
            PackageManagerTracedLock packageManagerTracedLock = DexOptHelper.this.mPm.mLock;
            boolean z = PackageManagerService.DEBUG_COMPRESSION;
            synchronized (packageManagerTracedLock) {
                try {
                    PackageManagerService packageManagerService2 = DexOptHelper.this.mPm;
                    packageManagerService2.mPackageUsage.maybeWriteAsync(
                            packageManagerService2.mSettings.mPackages);
                    DexOptHelper.this.mPm.mCompilerStats.maybeWriteAsync(null);
                } catch (Throwable th) {
                    boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                    throw th;
                }
            }
            if (dexoptResult.getReason().equals("inactive")) {
                for (DexoptResult.PackageDexoptResult packageDexoptResult2 :
                        dexoptResult.getPackageDexoptResults()) {
                    if (packageDexoptResult2.getStatus() == 20) {
                        long j = 0;
                        long j2 = 0;
                        for (DexoptResult.DexContainerFileDexoptResult
                                dexContainerFileDexoptResult2 :
                                        packageDexoptResult2.getDexContainerFileDexoptResults()) {
                            long length =
                                    new File(dexContainerFileDexoptResult2.getDexContainerFile())
                                            .length();
                            j2 += dexContainerFileDexoptResult2.getSizeBytes() + length;
                            j += dexContainerFileDexoptResult2.getSizeBeforeBytes() + length;
                        }
                        FrameworkStatsLog.write(
                                128, packageDexoptResult2.getPackageName(), j, j2, false);
                    }
                }
            }
            ArraySet arraySet = new ArraySet();
            for (DexoptResult.PackageDexoptResult packageDexoptResult3 :
                    dexoptResult.getPackageDexoptResults()) {
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

    public DexOptHelper(PackageManagerService packageManagerService) {
        this.mPm = packageManagerService;
    }

    public static void applyPackageFilter(
            Computer computer,
            Predicate predicate,
            Collection collection,
            Collection collection2,
            List list,
            PackageManagerService packageManagerService) {
        ArrayList arrayList = (ArrayList) collection2;
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            PackageStateInternal packageStateInternal = (PackageStateInternal) it.next();
            if (predicate.test(packageStateInternal)) {
                ((ArrayList) list).add(packageStateInternal);
            }
        }
        if (packageManagerService.mPackageUsage.mIsHistoricalPackageUsageAvailable) {
            Collections.sort(list, new DexOptHelper$$ExternalSyntheticLambda13());
        }
        arrayList.removeAll(list);
        ArrayList arrayList2 = (ArrayList) list;
        Iterator it2 = arrayList2.iterator();
        while (it2.hasNext()) {
            PackageStateInternal packageStateInternal2 = (PackageStateInternal) it2.next();
            ArrayList arrayList3 = (ArrayList) collection;
            arrayList3.add(packageStateInternal2);
            List findSharedNonSystemLibraries =
                    computer.findSharedNonSystemLibraries(packageStateInternal2);
            if (!findSharedNonSystemLibraries.isEmpty()) {
                findSharedNonSystemLibraries.removeAll(collection);
                arrayList3.addAll(findSharedNonSystemLibraries);
                arrayList.removeAll(findSharedNonSystemLibraries);
            }
        }
        arrayList2.clear();
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
        throw new IllegalArgumentException(
                "DexoptResult for "
                        + ((DexoptResult.PackageDexoptResult)
                                        dexoptResult.getPackageDexoptResults().get(0))
                                .getPackageName()
                        + " has unsupported status "
                        + finalStatus);
    }

    public static ArtManagerLocal getArtManagerLocal() {
        try {
            return (ArtManagerLocal) LocalManagerRegistry.getManagerOrThrow(ArtManagerLocal.class);
        } catch (LocalManagerRegistry.ManagerNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static DexUseManagerLocal getDexUseManagerLocal() {
        try {
            return (DexUseManagerLocal)
                    LocalManagerRegistry.getManagerOrThrow(DexUseManagerLocal.class);
        } catch (LocalManagerRegistry.ManagerNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static List getPackagesForDexopt(
            Collection collection, PackageManagerService packageManagerService) {
        List list;
        Predicate predicate;
        Predicate predicate2;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList(collection);
        arrayList2.removeIf(PackageManagerServiceUtils.REMOVE_IF_NULL_PKG);
        arrayList2.removeIf(PackageManagerServiceUtils.REMOVE_IF_APEX_PKG);
        ArrayList arrayList3 = new ArrayList(arrayList2.size());
        Computer snapshotComputer = packageManagerService.snapshotComputer();
        final int i = 0;
        applyPackageFilter(
                snapshotComputer,
                new Predicate() { // from class:
                                  // com.android.server.pm.DexOptHelper$$ExternalSyntheticLambda3
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        PackageStateInternal packageStateInternal = (PackageStateInternal) obj;
                        switch (i) {
                            case 0:
                                return packageStateInternal.isSystem();
                            case 1:
                                return "android".equals(packageStateInternal.getPackageName());
                            case 2:
                                return packageStateInternal.getPkg().isCoreApp();
                            default:
                                return true;
                        }
                    }
                },
                arrayList,
                arrayList2,
                arrayList3,
                packageManagerService);
        final int i2 = 2;
        applyPackageFilter(
                snapshotComputer,
                new Predicate() { // from class:
                                  // com.android.server.pm.DexOptHelper$$ExternalSyntheticLambda3
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        PackageStateInternal packageStateInternal = (PackageStateInternal) obj;
                        switch (i2) {
                            case 0:
                                return packageStateInternal.isSystem();
                            case 1:
                                return "android".equals(packageStateInternal.getPackageName());
                            case 2:
                                return packageStateInternal.getPkg().isCoreApp();
                            default:
                                return true;
                        }
                    }
                },
                arrayList,
                arrayList2,
                arrayList3,
                packageManagerService);
        try {
            list =
                    AppGlobals.getPackageManager()
                            .queryIntentReceivers(
                                    new Intent("android.intent.action.PRE_BOOT_COMPLETED"),
                                    (String) null,
                                    0L,
                                    0)
                            .getList();
        } catch (RemoteException unused) {
            list = null;
        }
        final ArraySet arraySet = new ArraySet();
        if (list != null) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                arraySet.add(((ResolveInfo) it.next()).activityInfo.packageName);
            }
        }
        final int i3 = 0;
        applyPackageFilter(
                snapshotComputer,
                new Predicate() { // from class:
                                  // com.android.server.pm.DexOptHelper$$ExternalSyntheticLambda5
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        int i4 = i3;
                        Object obj2 = arraySet;
                        switch (i4) {
                            case 0:
                                return ((ArraySet) obj2)
                                        .contains(((PackageStateInternal) obj).getPackageName());
                            default:
                                String packageName = ((PackageStateInternal) obj).getPackageName();
                                PackageDexUsage.PackageUseInfo packageUseInfo =
                                        ((DexManager) obj2)
                                                .mPackageDexUsage.getPackageUseInfo(packageName);
                                if (packageUseInfo == null) {
                                    packageUseInfo =
                                            new PackageDexUsage.PackageUseInfo(packageName);
                                }
                                return !((HashMap) packageUseInfo.mPrimaryCodePaths).isEmpty();
                        }
                    }
                },
                arrayList,
                arrayList2,
                arrayList3,
                packageManagerService);
        final DexManager dexManager = packageManagerService.mDexManager;
        final int i4 = 1;
        applyPackageFilter(
                snapshotComputer,
                new Predicate() { // from class:
                                  // com.android.server.pm.DexOptHelper$$ExternalSyntheticLambda5
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        int i42 = i4;
                        Object obj2 = dexManager;
                        switch (i42) {
                            case 0:
                                return ((ArraySet) obj2)
                                        .contains(((PackageStateInternal) obj).getPackageName());
                            default:
                                String packageName = ((PackageStateInternal) obj).getPackageName();
                                PackageDexUsage.PackageUseInfo packageUseInfo =
                                        ((DexManager) obj2)
                                                .mPackageDexUsage.getPackageUseInfo(packageName);
                                if (packageUseInfo == null) {
                                    packageUseInfo =
                                            new PackageDexUsage.PackageUseInfo(packageName);
                                }
                                return !((HashMap) packageUseInfo.mPrimaryCodePaths).isEmpty();
                        }
                    }
                },
                arrayList,
                arrayList2,
                arrayList3,
                packageManagerService);
        if (!arrayList2.isEmpty()) {
            PackageUsage packageUsage = packageManagerService.mPackageUsage;
            if (packageUsage.mIsHistoricalPackageUsageAvailable) {
                Log.i("PackageManager", "Looking at historical package use");
                PackageStateInternal packageStateInternal =
                        (PackageStateInternal)
                                Collections.max(
                                        arrayList2,
                                        Comparator.comparingLong(
                                                new DexOptHelper$$ExternalSyntheticLambda7()));
                Log.i(
                        "PackageManager",
                        "Taking package "
                                + packageStateInternal.getPackageName()
                                + " as reference in time use");
                long latestForegroundPackageUseTimeInMills =
                        packageStateInternal
                                .getTransientState()
                                .getLatestForegroundPackageUseTimeInMills();
                if (latestForegroundPackageUseTimeInMills != 0) {
                    final long j = latestForegroundPackageUseTimeInMills - 2764800000L;
                    predicate2 =
                            new Predicate() { // from class:
                                              // com.android.server.pm.DexOptHelper$$ExternalSyntheticLambda8
                                @Override // java.util.function.Predicate
                                public final boolean test(Object obj) {
                                    return ((PackageStateInternal) obj)
                                                    .getTransientState()
                                                    .getLatestForegroundPackageUseTimeInMills()
                                            >= j;
                                }
                            };
                } else {
                    final int i5 = 3;
                    predicate2 =
                            new Predicate() { // from class:
                                              // com.android.server.pm.DexOptHelper$$ExternalSyntheticLambda3
                                @Override // java.util.function.Predicate
                                public final boolean test(Object obj) {
                                    PackageStateInternal packageStateInternal2 =
                                            (PackageStateInternal) obj;
                                    switch (i5) {
                                        case 0:
                                            return packageStateInternal2.isSystem();
                                        case 1:
                                            return "android"
                                                    .equals(packageStateInternal2.getPackageName());
                                        case 2:
                                            return packageStateInternal2.getPkg().isCoreApp();
                                        default:
                                            return true;
                                    }
                                }
                            };
                }
                if (packageUsage.mIsHistoricalPackageUsageAvailable) {
                    Collections.sort(arrayList2, new DexOptHelper$$ExternalSyntheticLambda13());
                }
                predicate = predicate2;
                applyPackageFilter(
                        snapshotComputer,
                        predicate,
                        arrayList,
                        arrayList2,
                        arrayList3,
                        packageManagerService);
                final int i6 = 1;
                arrayList.removeIf(
                        new Predicate() { // from class:
                                          // com.android.server.pm.DexOptHelper$$ExternalSyntheticLambda3
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                PackageStateInternal packageStateInternal2 =
                                        (PackageStateInternal) obj;
                                switch (i6) {
                                    case 0:
                                        return packageStateInternal2.isSystem();
                                    case 1:
                                        return "android"
                                                .equals(packageStateInternal2.getPackageName());
                                    case 2:
                                        return packageStateInternal2.getPkg().isCoreApp();
                                    default:
                                        return true;
                                }
                            }
                        });
                Log.i("PackageManager", "Packages to be dexopted: " + packagesToString(arrayList));
                Log.i(
                        "PackageManager",
                        "Packages skipped from dexopt: " + packagesToString(arrayList2));
                return arrayList;
            }
        }
        final int i7 = 3;
        predicate =
                new Predicate() { // from class:
                                  // com.android.server.pm.DexOptHelper$$ExternalSyntheticLambda3
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        PackageStateInternal packageStateInternal2 = (PackageStateInternal) obj;
                        switch (i7) {
                            case 0:
                                return packageStateInternal2.isSystem();
                            case 1:
                                return "android".equals(packageStateInternal2.getPackageName());
                            case 2:
                                return packageStateInternal2.getPkg().isCoreApp();
                            default:
                                return true;
                        }
                    }
                };
        applyPackageFilter(
                snapshotComputer,
                predicate,
                arrayList,
                arrayList2,
                arrayList3,
                packageManagerService);
        final int i62 = 1;
        arrayList.removeIf(
                new Predicate() { // from class:
                                  // com.android.server.pm.DexOptHelper$$ExternalSyntheticLambda3
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        PackageStateInternal packageStateInternal2 = (PackageStateInternal) obj;
                        switch (i62) {
                            case 0:
                                return packageStateInternal2.isSystem();
                            case 1:
                                return "android".equals(packageStateInternal2.getPackageName());
                            case 2:
                                return packageStateInternal2.getPkg().isCoreApp();
                            default:
                                return true;
                        }
                    }
                });
        Log.i("PackageManager", "Packages to be dexopted: " + packagesToString(arrayList));
        Log.i("PackageManager", "Packages skipped from dexopt: " + packagesToString(arrayList2));
        return arrayList;
    }

    public static boolean hasBcpApexesChanged() {
        List list;
        String str = System.getenv("BOOTCLASSPATH");
        if (TextUtils.isEmpty(str)) {
            Log.e("PackageManager", "Unable to get BOOTCLASSPATH");
            list = List.of();
        } else {
            ArrayList arrayList = new ArrayList();
            for (String str2 : str.split(":")) {
                Path path = Paths.get(str2, new String[0]);
                if (path.getNameCount() >= 2 && path.getName(0).toString().equals("apex")) {
                    arrayList.add(path.getName(1).toString());
                }
            }
            list = arrayList;
        }
        HashSet hashSet = new HashSet(list);
        for (ApexManager.ActiveApexInfo activeApexInfo :
                ApexManager.getInstance().getActiveApexInfos()) {
            if (hashSet.contains(activeApexInfo.apexModuleName)
                    && activeApexInfo.activeApexChanged) {
                return true;
            }
        }
        return false;
    }

    public static void initializeArtManagerLocal(
            final Context context, final PackageManagerService packageManagerService) {
        final ArtManagerLocal artManagerLocal = new ArtManagerLocal(context);
        SystemServerInitThreadPool$$ExternalSyntheticLambda0
                systemServerInitThreadPool$$ExternalSyntheticLambda0 =
                        new SystemServerInitThreadPool$$ExternalSyntheticLambda0();
        DexOptHelper dexOptHelper = packageManagerService.mDexOptHelper;
        Objects.requireNonNull(dexOptHelper);
        artManagerLocal.addDexoptDoneCallback(
                false,
                systemServerInitThreadPool$$ExternalSyntheticLambda0,
                dexOptHelper.new DexoptDoneHandler());
        if (!CoreRune.IS_DEBUG_LEVEL_LOW) {
            final int i = 0;
            artManagerLocal.addDexoptDoneCallback(
                    false,
                    new SystemServerInitThreadPool$$ExternalSyntheticLambda0(),
                    new ArtManagerLocal.DexoptDoneCallback() { // from class:
                        // com.android.server.pm.DexOptHelper$$ExternalSyntheticLambda0
                        /* JADX WARN: Code restructure failed: missing block: B:60:0x00d5, code lost:

                           if (r0 == null) goto L45;
                        */
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public final void onDexoptDone(
                                com.android.server.art.model.DexoptResult r10) {
                            /*
                                r9 = this;
                                int r9 = r1
                                switch(r9) {
                                    case 0: goto L9f;
                                    default: goto L5;
                                }
                            L5:
                                java.lang.String r9 = r10.getReason()
                                java.lang.String r0 = "bg-dexopt"
                                boolean r9 = r9.equals(r0)
                                r0 = 4
                                r1 = 30
                                r2 = 0
                                if (r9 == 0) goto L68
                                java.util.List r9 = r10.getPackageDexoptResults()
                                java.util.Iterator r9 = r9.iterator()
                                r3 = r2
                                r4 = r3
                                r5 = r4
                                r6 = r5
                            L21:
                                boolean r7 = r9.hasNext()
                                if (r7 == 0) goto L4c
                                java.lang.Object r7 = r9.next()
                                com.android.server.art.model.DexoptResult$PackageDexoptResult r7 = (com.android.server.art.model.DexoptResult.PackageDexoptResult) r7
                                int r7 = r7.getStatus()
                                r8 = 10
                                if (r7 == r8) goto L49
                                r8 = 20
                                if (r7 == r8) goto L46
                                if (r7 == r1) goto L43
                                r8 = 40
                                if (r7 == r8) goto L40
                                goto L21
                            L40:
                                int r6 = r6 + 1
                                goto L21
                            L43:
                                int r5 = r5 + 1
                                goto L21
                            L46:
                                int r3 = r3 + 1
                                goto L21
                            L49:
                                int r4 = r4 + 1
                                goto L21
                            L4c:
                                java.lang.String r9 = "bg-dexopt dexopted: "
                                java.lang.String r7 = ", skipped: "
                                java.lang.String r8 = ", failed: "
                                java.lang.StringBuilder r9 = com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0.m(r3, r4, r9, r7, r8)
                                r9.append(r5)
                                java.lang.String r3 = ", cancelled: "
                                r9.append(r3)
                                r9.append(r6)
                                java.lang.String r9 = r9.toString()
                                com.android.server.pm.PackageManagerServiceUtils.logCriticalInfo(r0, r9)
                            L68:
                                java.lang.String r9 = r10.getReason()
                                java.lang.String r3 = "install"
                                boolean r9 = r9.equals(r3)
                                if (r9 == 0) goto L9e
                                int r9 = r10.getFinalStatus()
                                if (r9 != r1) goto L9e
                                java.lang.StringBuilder r9 = new java.lang.StringBuilder
                                java.lang.String r1 = "[INSTALL] Dexopt for "
                                r9.<init>(r1)
                                java.util.List r10 = r10.getPackageDexoptResults()
                                java.lang.Object r10 = r10.get(r2)
                                com.android.server.art.model.DexoptResult$PackageDexoptResult r10 = (com.android.server.art.model.DexoptResult.PackageDexoptResult) r10
                                java.lang.String r10 = r10.getPackageName()
                                r9.append(r10)
                                java.lang.String r10 = " failed"
                                r9.append(r10)
                                java.lang.String r9 = r9.toString()
                                com.android.server.pm.PackageManagerServiceUtils.logCriticalInfo(r0, r9)
                            L9e:
                                return
                            L9f:
                                int r9 = r10.getFinalStatus()
                                r10 = 30
                                if (r9 != r10) goto Le4
                                java.lang.String r9 = "PackageManager"
                                java.lang.String r10 = "Call saveDexOptLog"
                                android.util.Slog.i(r9, r10)
                                java.lang.String r10 = "rm /data/log/dexoptfail_log"
                                java.lang.Runtime r0 = java.lang.Runtime.getRuntime()     // Catch: java.io.IOException -> Lb9
                                r0.exec(r10)     // Catch: java.io.IOException -> Lb9
                                goto Lbd
                            Lb9:
                                r10 = move-exception
                                r10.printStackTrace()
                            Lbd:
                                java.lang.String r10 = "logcat -v raw -b main,system -t 3000 -f /data/log/dexoptfail_log"
                                r0 = 0
                                java.lang.Runtime r1 = java.lang.Runtime.getRuntime()     // Catch: java.lang.Throwable -> Lcf java.lang.Throwable -> Ld1
                                java.lang.Process r0 = r1.exec(r10)     // Catch: java.lang.Throwable -> Lcf java.lang.Throwable -> Ld1
                                r0.waitFor()     // Catch: java.lang.Throwable -> Lcf java.lang.Throwable -> Ld1
                            Lcb:
                                r0.destroy()     // Catch: java.lang.Exception -> Ld8
                                goto Ld8
                            Lcf:
                                r9 = move-exception
                                goto Lde
                            Ld1:
                                r10 = move-exception
                                r10.printStackTrace()     // Catch: java.lang.Throwable -> Lcf
                                if (r0 == 0) goto Ld8
                                goto Lcb
                            Ld8:
                                java.lang.String r10 = "End saveDexOptLog"
                                android.util.Slog.i(r9, r10)
                                goto Le4
                            Lde:
                                if (r0 == 0) goto Le3
                                r0.destroy()     // Catch: java.lang.Exception -> Le3
                            Le3:
                                throw r9
                            Le4:
                                return
                            */
                            throw new UnsupportedOperationException(
                                    "Method not decompiled:"
                                        + " com.android.server.pm.DexOptHelper$$ExternalSyntheticLambda0.onDexoptDone(com.android.server.art.model.DexoptResult):void");
                        }
                    });
        }
        final int i2 = 1;
        artManagerLocal.addDexoptDoneCallback(
                false,
                new SystemServerInitThreadPool$$ExternalSyntheticLambda0(),
                new ArtManagerLocal.DexoptDoneCallback() { // from class:
                    // com.android.server.pm.DexOptHelper$$ExternalSyntheticLambda0
                    public final void onDexoptDone(DexoptResult dexoptResult) {
                        /*
                            this = this;
                            int r9 = r1
                            switch(r9) {
                                case 0: goto L9f;
                                default: goto L5;
                            }
                        L5:
                            java.lang.String r9 = r10.getReason()
                            java.lang.String r0 = "bg-dexopt"
                            boolean r9 = r9.equals(r0)
                            r0 = 4
                            r1 = 30
                            r2 = 0
                            if (r9 == 0) goto L68
                            java.util.List r9 = r10.getPackageDexoptResults()
                            java.util.Iterator r9 = r9.iterator()
                            r3 = r2
                            r4 = r3
                            r5 = r4
                            r6 = r5
                        L21:
                            boolean r7 = r9.hasNext()
                            if (r7 == 0) goto L4c
                            java.lang.Object r7 = r9.next()
                            com.android.server.art.model.DexoptResult$PackageDexoptResult r7 = (com.android.server.art.model.DexoptResult.PackageDexoptResult) r7
                            int r7 = r7.getStatus()
                            r8 = 10
                            if (r7 == r8) goto L49
                            r8 = 20
                            if (r7 == r8) goto L46
                            if (r7 == r1) goto L43
                            r8 = 40
                            if (r7 == r8) goto L40
                            goto L21
                        L40:
                            int r6 = r6 + 1
                            goto L21
                        L43:
                            int r5 = r5 + 1
                            goto L21
                        L46:
                            int r3 = r3 + 1
                            goto L21
                        L49:
                            int r4 = r4 + 1
                            goto L21
                        L4c:
                            java.lang.String r9 = "bg-dexopt dexopted: "
                            java.lang.String r7 = ", skipped: "
                            java.lang.String r8 = ", failed: "
                            java.lang.StringBuilder r9 = com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0.m(r3, r4, r9, r7, r8)
                            r9.append(r5)
                            java.lang.String r3 = ", cancelled: "
                            r9.append(r3)
                            r9.append(r6)
                            java.lang.String r9 = r9.toString()
                            com.android.server.pm.PackageManagerServiceUtils.logCriticalInfo(r0, r9)
                        L68:
                            java.lang.String r9 = r10.getReason()
                            java.lang.String r3 = "install"
                            boolean r9 = r9.equals(r3)
                            if (r9 == 0) goto L9e
                            int r9 = r10.getFinalStatus()
                            if (r9 != r1) goto L9e
                            java.lang.StringBuilder r9 = new java.lang.StringBuilder
                            java.lang.String r1 = "[INSTALL] Dexopt for "
                            r9.<init>(r1)
                            java.util.List r10 = r10.getPackageDexoptResults()
                            java.lang.Object r10 = r10.get(r2)
                            com.android.server.art.model.DexoptResult$PackageDexoptResult r10 = (com.android.server.art.model.DexoptResult.PackageDexoptResult) r10
                            java.lang.String r10 = r10.getPackageName()
                            r9.append(r10)
                            java.lang.String r10 = " failed"
                            r9.append(r10)
                            java.lang.String r9 = r9.toString()
                            com.android.server.pm.PackageManagerServiceUtils.logCriticalInfo(r0, r9)
                        L9e:
                            return
                        L9f:
                            int r9 = r10.getFinalStatus()
                            r10 = 30
                            if (r9 != r10) goto Le4
                            java.lang.String r9 = "PackageManager"
                            java.lang.String r10 = "Call saveDexOptLog"
                            android.util.Slog.i(r9, r10)
                            java.lang.String r10 = "rm /data/log/dexoptfail_log"
                            java.lang.Runtime r0 = java.lang.Runtime.getRuntime()     // Catch: java.io.IOException -> Lb9
                            r0.exec(r10)     // Catch: java.io.IOException -> Lb9
                            goto Lbd
                        Lb9:
                            r10 = move-exception
                            r10.printStackTrace()
                        Lbd:
                            java.lang.String r10 = "logcat -v raw -b main,system -t 3000 -f /data/log/dexoptfail_log"
                            r0 = 0
                            java.lang.Runtime r1 = java.lang.Runtime.getRuntime()     // Catch: java.lang.Throwable -> Lcf java.lang.Throwable -> Ld1
                            java.lang.Process r0 = r1.exec(r10)     // Catch: java.lang.Throwable -> Lcf java.lang.Throwable -> Ld1
                            r0.waitFor()     // Catch: java.lang.Throwable -> Lcf java.lang.Throwable -> Ld1
                        Lcb:
                            r0.destroy()     // Catch: java.lang.Exception -> Ld8
                            goto Ld8
                        Lcf:
                            r9 = move-exception
                            goto Lde
                        Ld1:
                            r10 = move-exception
                            r10.printStackTrace()     // Catch: java.lang.Throwable -> Lcf
                            if (r0 == 0) goto Ld8
                            goto Lcb
                        Ld8:
                            java.lang.String r10 = "End saveDexOptLog"
                            android.util.Slog.i(r9, r10)
                            goto Le4
                        Lde:
                            if (r0 == 0) goto Le3
                            r0.destroy()     // Catch: java.lang.Exception -> Le3
                        Le3:
                            throw r9
                        Le4:
                            return
                        */
                        throw new UnsupportedOperationException(
                                "Method not decompiled:"
                                    + " com.android.server.pm.DexOptHelper$$ExternalSyntheticLambda0.onDexoptDone(com.android.server.art.model.DexoptResult):void");
                    }
                });
        artManagerLocal.setBatchDexoptStartCallback(
                new SystemServerInitThreadPool$$ExternalSyntheticLambda0(),
                new ArtManagerLocal.BatchDexoptStartCallback() { // from class:
                    // com.android.server.pm.DexOptHelper$$ExternalSyntheticLambda2
                    public final void onBatchDexoptStart(
                            PackageManagerLocal.FilteredSnapshot filteredSnapshot,
                            String str,
                            List list,
                            BatchDexoptParams.Builder builder,
                            CancellationSignal cancellationSignal) {
                        List<ResolveInfo> list2;
                        Iterator it;
                        Context context2 = context;
                        PackageManagerService packageManagerService2 = packageManagerService;
                        ArtManagerLocal artManagerLocal2 = artManagerLocal;
                        if (str.equals("bg-dexopt")) {
                            PackageManagerServiceUtils.logCriticalInfo(4, "DEXOPT for bg-dexopt");
                            Handler handler = packageManagerService2.mInjector.getHandler();
                            DexOptHelper.BgDexOptHelper bgDexOptHelper =
                                    new DexOptHelper.BgDexOptHelper();
                            bgDexOptHelper.mObserveStarted = false;
                            bgDexOptHelper.mContext = context2;
                            bgDexOptHelper.mHandler = bgDexOptHelper.new MyHandler(handler);
                            bgDexOptHelper.mArtManager = artManagerLocal2;
                            if (!bgDexOptHelper.isBatteryFullyCharged()) {
                                PackageManagerServiceUtils.logCriticalInfo(
                                        4,
                                        "Can't run bg dexopt by not fully charged: "
                                                + ((BatteryManagerInternal)
                                                                LocalServices.getService(
                                                                        BatteryManagerInternal
                                                                                .class))
                                                        .getBatteryLevel()
                                                + " abortCount: "
                                                + DexOptHelper.BgDexOptHelper.abortCount);
                                int i3 = DexOptHelper.BgDexOptHelper.abortCount + 1;
                                DexOptHelper.BgDexOptHelper.abortCount = i3;
                                DexOptHelper.BgDexOptHelper.policySelector = false;
                                if (i3 > 3) {
                                    DexOptHelper.BgDexOptHelper.abortCount = 0;
                                    DexOptHelper.BgDexOptHelper.policySelector = true;
                                }
                            } else if (bgDexOptHelper.reachedToThermalThrottleLevel()) {
                                PackageManagerServiceUtils.logCriticalInfo(
                                        4, "Can't run bg dexopt by thermal throttling");
                            } else {
                                ArrayList arrayList = new ArrayList();
                                Computer snapshotComputer =
                                        packageManagerService2.snapshotComputer();
                                Slog.d("PackageManager", "Reordering packages based on priority");
                                if (((BatteryManagerInternal)
                                                        LocalServices.getService(
                                                                BatteryManagerInternal.class))
                                                .getBatteryLevel()
                                        <= 80) {
                                    PackageManagerServiceUtils.logCriticalInfo(
                                            4,
                                            "Not fully charged. Optimize top-priority packages"
                                                + " only");
                                    SemTemperatureManager.Thermistor thermistor =
                                            SemTemperatureManager.getThermistor(9);
                                    int temperature =
                                            thermistor != null ? thermistor.getTemperature() : -1;
                                    DexOptHelper.BgDexOptHelper.initialSurfaceTemperature =
                                            temperature;
                                    if (temperature <= 360) {
                                        DexOptHelper.BgDexOptHelper.SurfaceTemperatureThreshold =
                                                temperature + 10;
                                    } else if (temperature <= 370) {
                                        DexOptHelper.BgDexOptHelper.SurfaceTemperatureThreshold =
                                                temperature + 8;
                                    } else if (temperature <= 380) {
                                        DexOptHelper.BgDexOptHelper.SurfaceTemperatureThreshold =
                                                temperature + 5;
                                    } else if (temperature <= 390) {
                                        DexOptHelper.BgDexOptHelper.SurfaceTemperatureThreshold =
                                                temperature + 2;
                                    } else {
                                        DexOptHelper.BgDexOptHelper.SurfaceTemperatureThreshold =
                                                400;
                                    }
                                    StringBuilder sb = new StringBuilder("SurfaceTemperature [");
                                    sb.append(
                                            DexOptHelper.BgDexOptHelper.initialSurfaceTemperature);
                                    sb.append("] threashold [");
                                    AudioService$$ExternalSyntheticOutline0.m(
                                            sb,
                                            DexOptHelper.BgDexOptHelper.SurfaceTemperatureThreshold,
                                            "]",
                                            "PackageManager");
                                    DexOptHelper.BgDexOptHelper.enableTemperaturePolicy = true;
                                    int i4 = DexOptHelper.BgDexOptHelper.abortCount + 1;
                                    DexOptHelper.BgDexOptHelper.abortCount = i4;
                                    DexOptHelper.BgDexOptHelper.policySelector = false;
                                    if (i4 > 3) {
                                        DexOptHelper.BgDexOptHelper.abortCount = 0;
                                        DexOptHelper.BgDexOptHelper.policySelector = true;
                                    }
                                    int i5 =
                                            DexOptHelper.BgDexOptHelper.initialSurfaceTemperature
                                                            <= 390
                                                    ? -1
                                                    : 5;
                                    long millis = TimeUnit.DAYS.toMillis(3L);
                                    ArrayList arrayList2 = new ArrayList();
                                    PriorityQueue priorityQueue =
                                            new PriorityQueue(
                                                    Map.Entry.comparingByValue(
                                                            Comparator.reverseOrder()));
                                    ArrayMap arrayMap = new ArrayMap();
                                    long currentTimeMillis = System.currentTimeMillis();
                                    Iterator it2 =
                                            new ArrayList(
                                                            snapshotComputer
                                                                    .getPackageStates()
                                                                    .values())
                                                    .iterator();
                                    while (it2.hasNext()) {
                                        PackageStateInternal packageStateInternal =
                                                (PackageStateInternal) it2.next();
                                        if (packageStateInternal.getPkg() != null) {
                                            long latestForegroundPackageUseTimeInMills =
                                                    packageStateInternal
                                                            .getTransientState()
                                                            .getLatestForegroundPackageUseTimeInMills();
                                            if (currentTimeMillis
                                                            - latestForegroundPackageUseTimeInMills
                                                    < millis) {
                                                it = it2;
                                                arrayMap.put(
                                                        packageStateInternal.getPackageName(),
                                                        Long.valueOf(
                                                                latestForegroundPackageUseTimeInMills));
                                            } else {
                                                it = it2;
                                            }
                                            it2 = it;
                                        }
                                    }
                                    for (String str2 : arrayMap.keySet()) {
                                        priorityQueue.offer(
                                                new AbstractMap.SimpleEntry(
                                                        str2, (Long) arrayMap.get(str2)));
                                    }
                                    ArrayList arrayList3 =
                                            new ArrayList(Arrays.asList("com.google.android.gms"));
                                    int i6 = i5;
                                    while (!priorityQueue.isEmpty() && (i6 == -1 || i6 > 0)) {
                                        String str3 =
                                                (String)
                                                        ((Map.Entry) priorityQueue.poll()).getKey();
                                        if (arrayList3.contains(str3)) {
                                            DualAppManagerService$$ExternalSyntheticOutline0.m(
                                                    "skipped because it's an exception package : ",
                                                    str3,
                                                    "PackageManager");
                                        } else {
                                            arrayList2.add(str3);
                                            if (i6 > 0) {
                                                i6--;
                                            }
                                        }
                                    }
                                    arrayList.addAll(arrayList2);
                                    PackageManagerServiceUtils.logCriticalInfo(
                                            4,
                                            "Target Packages (size : "
                                                    + arrayList.size()
                                                    + ") "
                                                    + String.join(",", arrayList));
                                } else {
                                    Set unusedPackages =
                                            snapshotComputer.getUnusedPackages(
                                                    TimeUnit.DAYS.toMillis(10L));
                                    Intent m =
                                            PersonalAppsSuspensionHelper$$ExternalSyntheticOutline0
                                                    .m(
                                                            "android.intent.action.MAIN",
                                                            "android.intent.category.LAUNCHER");
                                    ArraySet arraySet = new ArraySet();
                                    try {
                                        list2 =
                                                AppGlobals.getPackageManager()
                                                        .queryIntentActivities(
                                                                m, m.getType(), 0L, 0)
                                                        .getList();
                                    } catch (RemoteException unused) {
                                        list2 = null;
                                    }
                                    if (list2 != null) {
                                        for (ResolveInfo resolveInfo : list2) {
                                            if (list.contains(
                                                    resolveInfo.activityInfo.packageName)) {
                                                arraySet.add(resolveInfo.activityInfo.packageName);
                                            }
                                        }
                                    }
                                    ArrayList arrayList4 = new ArrayList(list);
                                    arrayList4.removeAll(unusedPackages);
                                    ArrayList arrayList5 = new ArrayList(arrayList4);
                                    arrayList5.retainAll(arraySet);
                                    arrayList4.removeAll(arrayList5);
                                    arrayList5.addAll(arrayList4);
                                    ArrayList arrayList6 = new ArrayList(list);
                                    arrayList6.retainAll(arraySet);
                                    arrayList6.removeAll(arrayList5);
                                    ArrayList arrayList7 = new ArrayList(list);
                                    arrayList7.removeAll(arrayList5);
                                    arrayList7.removeAll(arrayList6);
                                    arrayList.addAll(arrayList5);
                                    arrayList.addAll(arrayList6);
                                    arrayList.addAll(arrayList7);
                                    Slog.d(
                                            "PackageManager",
                                            "Optimizable Packages(size : "
                                                    + list.size()
                                                    + ") "
                                                    + String.join(",", list));
                                    Slog.d(
                                            "PackageManager",
                                            "Recently used Packages(size : "
                                                    + arrayList5.size()
                                                    + ") "
                                                    + String.join(",", arrayList5));
                                    Slog.d(
                                            "PackageManager",
                                            "Executable Packages(size : "
                                                    + arrayList6.size()
                                                    + ") "
                                                    + String.join(",", arrayList6));
                                    Slog.d(
                                            "PackageManager",
                                            "Remaining Packages(size : "
                                                    + arrayList7.size()
                                                    + ") "
                                                    + String.join(",", arrayList7));
                                }
                                builder.setPackages(arrayList)
                                        .setDexoptParams(
                                                new DexoptParams.Builder("bg-dexopt").build());
                                Slog.d("BgDexOptHelper", "Start observing thermal");
                                bgDexOptHelper.mObserveStarted = true;
                                DexOptHelper.BgDexOptHelper.MyHandler myHandler =
                                        bgDexOptHelper.mHandler;
                                myHandler.sendMessageDelayed(myHandler.obtainMessage(1), 2000L);
                                bgDexOptHelper.mArtManager.addDexoptDoneCallback(
                                        false,
                                        new SystemServerInitThreadPool$$ExternalSyntheticLambda0(),
                                        bgDexOptHelper);
                            }
                            cancellationSignal.cancel();
                        }
                        if (str.equals("boot-after-mainline-update")) {
                            PackageManagerServiceUtils.logCriticalInfo(
                                    4, "DEXOPT for boot-after-mainline-update");
                            ArrayList arrayList8 =
                                    new ArrayList(Arrays.asList(DexOptHelper.designatedPkgs));
                            DexoptParams build =
                                    new DexoptParams.Builder("boot-after-mainline-update")
                                            .setCompilerFilter("speed-profile")
                                            .build();
                            arrayList8.addAll(list);
                            builder.setPackages(arrayList8).setDexoptParams(build);
                        }
                    }
                });
        LocalManagerRegistry.addManager(ArtManagerLocal.class, artManagerLocal);
        sArtManagerLocalIsInitialized = true;
        context.registerReceiver(
                new BroadcastReceiver() { // from class: com.android.server.pm.DexOptHelper.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        context2.unregisterReceiver(this);
                        artManagerLocal.scheduleBackgroundDexoptJob();
                    }
                },
                new IntentFilter("android.intent.action.LOCKED_BOOT_COMPLETED"));
    }

    public static String packagesToString(List list) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (true) {
            ArrayList arrayList = (ArrayList) list;
            if (i >= arrayList.size()) {
                return sb.toString();
            }
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append(((PackageStateInternal) arrayList.get(i)).getPackageName());
            i++;
        }
    }

    public static int performDexOptWithArtService(DexoptOptions dexoptOptions, int i) {
        String str = dexoptOptions.mPackageName;
        PackageManagerLocal.FilteredSnapshot withFilteredSnapshot =
                PackageManagerServiceUtils.getPackageManagerLocal().withFilteredSnapshot();
        try {
            PackageState packageState = withFilteredSnapshot.getPackageState(str);
            if (packageState == null) {
                withFilteredSnapshot.close();
                return -1;
            }
            if (packageState.getAndroidPackage() == null) {
                withFilteredSnapshot.close();
                return -1;
            }
            int convertToDexOptResult =
                    convertToDexOptResult(
                            getArtManagerLocal()
                                    .dexoptPackage(
                                            withFilteredSnapshot,
                                            str,
                                            dexoptOptions.convertToDexoptParams(i)));
            withFilteredSnapshot.close();
            return convertToDexOptResult;
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

    public final boolean performDexOpt(DexoptOptions dexoptOptions) {
        int performDexOptWithArtService;
        Computer snapshotComputer = this.mPm.snapshotComputer();
        if (snapshotComputer.getInstantAppPackageName(Binder.getCallingUid()) != null) {
            return false;
        }
        int callingUserId = UserHandle.getCallingUserId();
        String str = dexoptOptions.mPackageName;
        if (snapshotComputer.isInstantApp(str, callingUserId)) {
            return false;
        }
        AndroidPackage androidPackage = snapshotComputer.getPackage(str);
        if (androidPackage != null && androidPackage.isApex()) {
            return true;
        }
        if ((dexoptOptions.mFlags & 8) != 0) {
            performDexOptWithArtService = performDexOptWithArtService(dexoptOptions, 0);
        } else {
            Trace.traceBegin(16384L, "dexopt");
            try {
                performDexOptWithArtService = performDexOptWithArtService(dexoptOptions, 4);
            } finally {
                Trace.traceEnd(16384L);
            }
        }
        return performDexOptWithArtService != -1;
    }
}
