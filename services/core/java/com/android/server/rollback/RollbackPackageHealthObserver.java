package com.android.server.rollback;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.content.pm.VersionedPackage;
import android.content.rollback.PackageRollbackInfo;
import android.content.rollback.RollbackInfo;
import android.content.rollback.RollbackManager;
import android.os.Environment;
import android.os.FileUtils;
import android.os.Handler;
import android.os.PowerManager;
import android.os.SystemProperties;
import android.sysprop.CrashRecoveryProperties;
import android.util.ArraySet;
import android.util.Slog;
import android.util.SparseArray;
import com.android.internal.hidden_from_bootclasspath.android.crashrecovery.flags.Flags;
import com.android.internal.util.Preconditions;
import com.android.server.KnoxCaptureInputFilter$$ExternalSyntheticOutline0;
import com.android.server.PackageWatchdog;
import com.android.server.pm.ApexManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

public final class RollbackPackageHealthObserver implements PackageWatchdog.PackageHealthObserver {
    public final ApexManager mApexManager;
    public final Context mContext;
    public final File mLastStagedRollbackIdsFile;
    public boolean mTwoPhaseRollbackEnabled;
    public final File mTwoPhaseRollbackEnabledFile;
    public final Set mPendingStagedRollbackIds = new ArraySet();
    public final Handler mHandler = new Handler(KnoxCaptureInputFilter$$ExternalSyntheticOutline0.m("RollbackPackageHealthObserver").getLooper());

    public RollbackPackageHealthObserver(Context context, ApexManager apexManager) {
        this.mContext = context;
        File file = new File(Environment.getDataDirectory(), "rollback-observer");
        file.mkdirs();
        this.mLastStagedRollbackIdsFile = new File(file, "last-staged-rollback-ids");
        File file2 = new File(file, "two-phase-rollback-enabled");
        this.mTwoPhaseRollbackEnabledFile = file2;
        PackageWatchdog.getInstance(context).registerHealthObserver(this);
        this.mApexManager = apexManager;
        boolean z = false;
        if (!SystemProperties.getBoolean("sys.boot_completed", false)) {
            this.mTwoPhaseRollbackEnabled = false;
            writeBoolean(file2, false);
            return;
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(file2);
            try {
                boolean z2 = fileInputStream.read() == 1;
                fileInputStream.close();
                z = z2;
            } finally {
            }
        } catch (IOException unused) {
        }
        this.mTwoPhaseRollbackEnabled = z;
    }

    public static RollbackInfo getRollbackForPackage(VersionedPackage versionedPackage, List list) {
        if (versionedPackage == null) {
            return null;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            RollbackInfo rollbackInfo = (RollbackInfo) it.next();
            for (PackageRollbackInfo packageRollbackInfo : rollbackInfo.getPackages()) {
                if (packageRollbackInfo.getVersionRolledBackFrom().equals(versionedPackage)) {
                    return rollbackInfo;
                }
                if (packageRollbackInfo.isApkInApex() && packageRollbackInfo.getVersionRolledBackFrom().getPackageName().equals(versionedPackage.getPackageName())) {
                    return rollbackInfo;
                }
            }
        }
        return null;
    }

    public static List getRollbacksAvailableForImpactLevel(final int i, List list) {
        return list.stream().filter(new Predicate() { // from class: com.android.server.rollback.RollbackPackageHealthObserver$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((RollbackInfo) obj).getRollbackImpactLevel() == i;
            }
        }).toList();
    }

    public static boolean isRebootlessApex(RollbackInfo rollbackInfo) {
        if (rollbackInfo.isStaged()) {
            return false;
        }
        Iterator it = rollbackInfo.getPackages().iterator();
        while (it.hasNext()) {
            if (((PackageRollbackInfo) it.next()).isApex()) {
                return true;
            }
        }
        return false;
    }

    public static SparseArray readStagedRollbackIds(File file) {
        SparseArray sparseArray = new SparseArray();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    return sparseArray;
                }
                String[] split = readLine.trim().split(",");
                String str = split[0];
                String str2 = "";
                if (split.length > 1) {
                    str2 = split[1];
                }
                sparseArray.put(Integer.parseInt(str), str2);
            }
        } catch (Exception unused) {
            return new SparseArray();
        }
    }

    public static void writeBoolean(File file, boolean z) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            try {
                fileOutputStream.write(z ? 1 : 0);
                fileOutputStream.flush();
                FileUtils.sync(fileOutputStream);
                fileOutputStream.close();
            } finally {
            }
        } catch (IOException unused) {
        }
    }

    public final void assertInWorkerThread() {
        Preconditions.checkState(this.mHandler.getLooper().isCurrentThread());
    }

    @Override // com.android.server.PackageWatchdog.PackageHealthObserver
    public final boolean execute(final VersionedPackage versionedPackage, final int i, int i2) {
        boolean recoverabilityDetection = Flags.recoverabilityDetection();
        Handler handler = this.mHandler;
        if (recoverabilityDetection) {
            List availableRollbacks = getAvailableRollbacks();
            if (i == 1) {
                handler.post(new RollbackPackageHealthObserver$$ExternalSyntheticLambda1(this, availableRollbacks, i, 1));
                return true;
            }
            List rollbacksAvailableForImpactLevel = getRollbacksAvailableForImpactLevel(0, availableRollbacks);
            final RollbackInfo rollbackForPackage = getRollbackForPackage(versionedPackage, rollbacksAvailableForImpactLevel);
            if (rollbackForPackage != null) {
                final int i3 = 0;
                handler.post(new Runnable(this) { // from class: com.android.server.rollback.RollbackPackageHealthObserver$$ExternalSyntheticLambda11
                    public final /* synthetic */ RollbackPackageHealthObserver f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        switch (i3) {
                            case 0:
                                this.f$0.rollbackPackage(rollbackForPackage, versionedPackage, i);
                                break;
                            default:
                                this.f$0.rollbackPackage(rollbackForPackage, versionedPackage, i);
                                break;
                        }
                    }
                });
            } else if (!rollbacksAvailableForImpactLevel.isEmpty()) {
                handler.post(new RollbackPackageHealthObserver$$ExternalSyntheticLambda1(this, availableRollbacks, i, 2));
            }
        } else {
            if (i == 1) {
                final int i4 = 0;
                handler.post(new Runnable(this) { // from class: com.android.server.rollback.RollbackPackageHealthObserver$$ExternalSyntheticLambda13
                    public final /* synthetic */ RollbackPackageHealthObserver f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        switch (i4) {
                            case 0:
                                this.f$0.rollbackAll(i);
                                break;
                            default:
                                this.f$0.rollbackAll(i);
                                break;
                        }
                    }
                });
                return true;
            }
            final RollbackInfo availableRollback = getAvailableRollback(versionedPackage);
            if (availableRollback != null) {
                final int i5 = 1;
                handler.post(new Runnable(this) { // from class: com.android.server.rollback.RollbackPackageHealthObserver$$ExternalSyntheticLambda11
                    public final /* synthetic */ RollbackPackageHealthObserver f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        switch (i5) {
                            case 0:
                                this.f$0.rollbackPackage(availableRollback, versionedPackage, i);
                                break;
                            default:
                                this.f$0.rollbackPackage(availableRollback, versionedPackage, i);
                                break;
                        }
                    }
                });
            } else {
                final int i6 = 1;
                handler.post(new Runnable(this) { // from class: com.android.server.rollback.RollbackPackageHealthObserver$$ExternalSyntheticLambda13
                    public final /* synthetic */ RollbackPackageHealthObserver f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        switch (i6) {
                            case 0:
                                this.f$0.rollbackAll(i);
                                break;
                            default:
                                this.f$0.rollbackAll(i);
                                break;
                        }
                    }
                });
            }
        }
        return true;
    }

    @Override // com.android.server.PackageWatchdog.PackageHealthObserver
    public final boolean executeBootLoopMitigation(int i) {
        if (!Flags.recoverabilityDetection()) {
            return false;
        }
        List availableRollbacks = getAvailableRollbacks();
        int orElse = availableRollbacks.stream().mapToInt(new RollbackPackageHealthObserver$$ExternalSyntheticLambda3()).min().orElse(-1);
        Handler handler = this.mHandler;
        if (orElse == 0) {
            handler.post(new RollbackPackageHealthObserver$$ExternalSyntheticLambda1(this, availableRollbacks, 0));
        } else if (orElse == 1 && !SystemProperties.getBoolean("persist.device_config.configuration.disable_high_impact_rollback", false)) {
            handler.post(new RollbackPackageHealthObserver$$ExternalSyntheticLambda1(this, availableRollbacks, 3));
        }
        return true;
    }

    public final RollbackInfo getAvailableRollback(VersionedPackage versionedPackage) {
        for (RollbackInfo rollbackInfo : ((RollbackManager) this.mContext.getSystemService(RollbackManager.class)).getAvailableRollbacks()) {
            for (PackageRollbackInfo packageRollbackInfo : rollbackInfo.getPackages()) {
                if (packageRollbackInfo.getVersionRolledBackFrom().equals(versionedPackage)) {
                    return rollbackInfo;
                }
                if (packageRollbackInfo.isApkInApex() && packageRollbackInfo.getVersionRolledBackFrom().getPackageName().equals(versionedPackage.getPackageName())) {
                    return rollbackInfo;
                }
            }
        }
        return null;
    }

    public final List getAvailableRollbacks() {
        return ((RollbackManager) this.mContext.getSystemService(RollbackManager.class)).getAvailableRollbacks();
    }

    public Handler getHandler() {
        return this.mHandler;
    }

    @Override // com.android.server.PackageWatchdog.PackageHealthObserver
    public final String getName() {
        return "rollback-observer";
    }

    @Override // com.android.server.PackageWatchdog.PackageHealthObserver
    public final boolean mayObservePackage(String str) {
        if (getAvailableRollbacks().isEmpty()) {
            return false;
        }
        try {
            return (this.mContext.getPackageManager().getApplicationInfo(str, 0).flags & 9) == 9;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    @Override // com.android.server.PackageWatchdog.PackageHealthObserver
    public final int onBootLoop(int i) {
        if (!Flags.recoverabilityDetection()) {
            return 0;
        }
        List availableRollbacks = getAvailableRollbacks();
        if (availableRollbacks.isEmpty()) {
            return 0;
        }
        int orElse = availableRollbacks.stream().mapToInt(new RollbackPackageHealthObserver$$ExternalSyntheticLambda3()).min().orElse(-1);
        if (orElse != 0) {
            return (orElse == 1 && !SystemProperties.getBoolean("persist.device_config.configuration.disable_high_impact_rollback", false)) ? 90 : 0;
        }
        return 70;
    }

    @Override // com.android.server.PackageWatchdog.PackageHealthObserver
    public final int onHealthCheckFailed(VersionedPackage versionedPackage, int i, int i2) {
        if (Flags.recoverabilityDetection()) {
            List rollbacksAvailableForImpactLevel = getRollbacksAvailableForImpactLevel(0, getAvailableRollbacks());
            if (!rollbacksAvailableForImpactLevel.isEmpty()) {
                if (i != 1 && getRollbackForPackage(versionedPackage, rollbacksAvailableForImpactLevel) == null) {
                    return 70;
                }
                return 30;
            }
            return 0;
        }
        boolean z = !((RollbackManager) this.mContext.getSystemService(RollbackManager.class)).getAvailableRollbacks().isEmpty();
        if ((i != 1 || !z) && getAvailableRollback(versionedPackage) == null) {
            if (z) {
                return 70;
            }
            return 0;
        }
        return 30;
    }

    public final void rollbackAll(int i) {
        assertInWorkerThread();
        List<RollbackInfo> availableRollbacks = ((RollbackManager) this.mContext.getSystemService(RollbackManager.class)).getAvailableRollbacks();
        if (useTwoPhaseRollback(availableRollbacks)) {
            return;
        }
        Slog.i("RollbackPackageHealthObserver", "Rolling back all available rollbacks");
        for (RollbackInfo rollbackInfo : availableRollbacks) {
            if (rollbackInfo.isStaged()) {
                ((ArraySet) this.mPendingStagedRollbackIds).add(Integer.valueOf(rollbackInfo.getRollbackId()));
            }
        }
        for (RollbackInfo rollbackInfo2 : availableRollbacks) {
            rollbackPackage(rollbackInfo2, ((PackageRollbackInfo) rollbackInfo2.getPackages().get(0)).getVersionRolledBackFrom(), i);
        }
    }

    public final void rollbackAllLowImpact(int i, List list) {
        assertInWorkerThread();
        List<RollbackInfo> rollbacksAvailableForImpactLevel = getRollbacksAvailableForImpactLevel(0, list);
        if (useTwoPhaseRollback(rollbacksAvailableForImpactLevel)) {
            return;
        }
        Slog.i("RollbackPackageHealthObserver", "Rolling back all available low impact rollbacks");
        for (RollbackInfo rollbackInfo : rollbacksAvailableForImpactLevel) {
            if (rollbackInfo.isStaged()) {
                ((ArraySet) this.mPendingStagedRollbackIds).add(Integer.valueOf(rollbackInfo.getRollbackId()));
            }
        }
        for (RollbackInfo rollbackInfo2 : rollbacksAvailableForImpactLevel) {
            rollbackPackage(rollbackInfo2, ((PackageRollbackInfo) rollbackInfo2.getPackages().get(0)).getVersionRolledBackFrom(), i);
        }
    }

    /* JADX WARN: Type inference failed for: r10v7, types: [com.android.server.rollback.RollbackPackageHealthObserver$$ExternalSyntheticLambda6] */
    public final void rollbackPackage(final RollbackInfo rollbackInfo, VersionedPackage versionedPackage, int i) {
        VersionedPackage versionedPackage2;
        assertInWorkerThread();
        RollbackManager rollbackManager = (RollbackManager) this.mContext.getSystemService(RollbackManager.class);
        final int mapFailureReasonToMetric = WatchdogRollbackLogger.mapFailureReasonToMetric(i);
        final String packageName = i == 1 ? SystemProperties.get("sys.init.updatable_crashing_process_name", "") : versionedPackage.getPackageName();
        String packageName2 = versionedPackage.getPackageName();
        String activeApexPackageNameContainingPackage = this.mApexManager.getActiveApexPackageNameContainingPackage(packageName2);
        if (activeApexPackageNameContainingPackage != null) {
            packageName2 = activeApexPackageNameContainingPackage;
        }
        if (this.mContext.getPackageManager().getModuleInfo(packageName2, 0) != null) {
            versionedPackage2 = WatchdogRollbackLogger.getLogPackage(this.mContext, versionedPackage);
            final VersionedPackage versionedPackage3 = versionedPackage2;
            WatchdogRollbackLogger.logEvent(versionedPackage3, 1, mapFailureReasonToMetric, packageName);
            final ?? r10 = new Consumer() { // from class: com.android.server.rollback.RollbackPackageHealthObserver$$ExternalSyntheticLambda6
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    RollbackPackageHealthObserver rollbackPackageHealthObserver = RollbackPackageHealthObserver.this;
                    RollbackInfo rollbackInfo2 = rollbackInfo;
                    VersionedPackage versionedPackage4 = versionedPackage3;
                    int i2 = mapFailureReasonToMetric;
                    String str = packageName;
                    rollbackPackageHealthObserver.assertInWorkerThread();
                    if (((Intent) obj).getIntExtra("android.content.rollback.extra.STATUS", 1) != 0) {
                        WatchdogRollbackLogger.logEvent(versionedPackage4, 3, i2, str);
                    } else if (rollbackInfo2.isStaged()) {
                        int rollbackId = rollbackInfo2.getRollbackId();
                        rollbackPackageHealthObserver.assertInWorkerThread();
                        File file = rollbackPackageHealthObserver.mLastStagedRollbackIdsFile;
                        try {
                            FileOutputStream fileOutputStream = new FileOutputStream(file, true);
                            PrintWriter printWriter = new PrintWriter(fileOutputStream);
                            printWriter.append((CharSequence) String.valueOf(rollbackId)).append((CharSequence) ",").append((CharSequence) (versionedPackage4 != null ? versionedPackage4.getPackageName() : ""));
                            printWriter.println();
                            printWriter.flush();
                            FileUtils.sync(fileOutputStream);
                            printWriter.close();
                        } catch (IOException e) {
                            Slog.e("RollbackPackageHealthObserver", "Failed to save last staged rollback id", e);
                            file.delete();
                        }
                        WatchdogRollbackLogger.logEvent(versionedPackage4, 4, i2, str);
                    } else {
                        WatchdogRollbackLogger.logEvent(versionedPackage4, 2, i2, str);
                    }
                    if (rollbackInfo2.isStaged()) {
                        int rollbackId2 = rollbackInfo2.getRollbackId();
                        rollbackPackageHealthObserver.assertInWorkerThread();
                        ((ArraySet) rollbackPackageHealthObserver.mPendingStagedRollbackIds).remove(Integer.valueOf(rollbackId2));
                        rollbackPackageHealthObserver.assertInWorkerThread();
                        if (((ArraySet) rollbackPackageHealthObserver.mPendingStagedRollbackIds).isEmpty()) {
                            CrashRecoveryProperties.attemptingReboot(Boolean.TRUE);
                            ((PowerManager) rollbackPackageHealthObserver.mContext.getSystemService(PowerManager.class)).reboot("Rollback staged install");
                        }
                    }
                }
            };
            rollbackManager.commitRollback(rollbackInfo.getRollbackId(), Collections.singletonList(versionedPackage), new IntentSender(new LocalIntentReceiver(new Consumer() { // from class: com.android.server.rollback.RollbackPackageHealthObserver$$ExternalSyntheticLambda7
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    RollbackPackageHealthObserver.this.mHandler.post(new RollbackPackageHealthObserver$$ExternalSyntheticLambda0((RollbackPackageHealthObserver$$ExternalSyntheticLambda6) r10, (Intent) obj));
                }
            }).mLocalSender));
        }
        versionedPackage2 = null;
        final VersionedPackage versionedPackage32 = versionedPackage2;
        WatchdogRollbackLogger.logEvent(versionedPackage32, 1, mapFailureReasonToMetric, packageName);
        final RollbackPackageHealthObserver$$ExternalSyntheticLambda6 r102 = new Consumer() { // from class: com.android.server.rollback.RollbackPackageHealthObserver$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                RollbackPackageHealthObserver rollbackPackageHealthObserver = RollbackPackageHealthObserver.this;
                RollbackInfo rollbackInfo2 = rollbackInfo;
                VersionedPackage versionedPackage4 = versionedPackage32;
                int i2 = mapFailureReasonToMetric;
                String str = packageName;
                rollbackPackageHealthObserver.assertInWorkerThread();
                if (((Intent) obj).getIntExtra("android.content.rollback.extra.STATUS", 1) != 0) {
                    WatchdogRollbackLogger.logEvent(versionedPackage4, 3, i2, str);
                } else if (rollbackInfo2.isStaged()) {
                    int rollbackId = rollbackInfo2.getRollbackId();
                    rollbackPackageHealthObserver.assertInWorkerThread();
                    File file = rollbackPackageHealthObserver.mLastStagedRollbackIdsFile;
                    try {
                        FileOutputStream fileOutputStream = new FileOutputStream(file, true);
                        PrintWriter printWriter = new PrintWriter(fileOutputStream);
                        printWriter.append((CharSequence) String.valueOf(rollbackId)).append((CharSequence) ",").append((CharSequence) (versionedPackage4 != null ? versionedPackage4.getPackageName() : ""));
                        printWriter.println();
                        printWriter.flush();
                        FileUtils.sync(fileOutputStream);
                        printWriter.close();
                    } catch (IOException e) {
                        Slog.e("RollbackPackageHealthObserver", "Failed to save last staged rollback id", e);
                        file.delete();
                    }
                    WatchdogRollbackLogger.logEvent(versionedPackage4, 4, i2, str);
                } else {
                    WatchdogRollbackLogger.logEvent(versionedPackage4, 2, i2, str);
                }
                if (rollbackInfo2.isStaged()) {
                    int rollbackId2 = rollbackInfo2.getRollbackId();
                    rollbackPackageHealthObserver.assertInWorkerThread();
                    ((ArraySet) rollbackPackageHealthObserver.mPendingStagedRollbackIds).remove(Integer.valueOf(rollbackId2));
                    rollbackPackageHealthObserver.assertInWorkerThread();
                    if (((ArraySet) rollbackPackageHealthObserver.mPendingStagedRollbackIds).isEmpty()) {
                        CrashRecoveryProperties.attemptingReboot(Boolean.TRUE);
                        ((PowerManager) rollbackPackageHealthObserver.mContext.getSystemService(PowerManager.class)).reboot("Rollback staged install");
                    }
                }
            }
        };
        rollbackManager.commitRollback(rollbackInfo.getRollbackId(), Collections.singletonList(versionedPackage), new IntentSender(new LocalIntentReceiver(new Consumer() { // from class: com.android.server.rollback.RollbackPackageHealthObserver$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                RollbackPackageHealthObserver.this.mHandler.post(new RollbackPackageHealthObserver$$ExternalSyntheticLambda0((RollbackPackageHealthObserver$$ExternalSyntheticLambda6) r102, (Intent) obj));
            }
        }).mLocalSender));
    }

    public final boolean useTwoPhaseRollback(List list) {
        assertInWorkerThread();
        if (!this.mTwoPhaseRollbackEnabled) {
            return false;
        }
        Slog.i("RollbackPackageHealthObserver", "Rolling back all rebootless APEX rollbacks");
        Iterator it = list.iterator();
        boolean z = false;
        while (it.hasNext()) {
            RollbackInfo rollbackInfo = (RollbackInfo) it.next();
            if (isRebootlessApex(rollbackInfo)) {
                rollbackPackage(rollbackInfo, ((PackageRollbackInfo) rollbackInfo.getPackages().get(0)).getVersionRolledBackFrom(), 1);
                z = true;
            }
        }
        return z;
    }
}
