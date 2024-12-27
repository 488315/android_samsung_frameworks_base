package com.android.server.am;

import android.util.Slog;

import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.server.LocalManagerRegistry;
import com.android.server.pm.DexOptHelper;
import com.android.server.pm.PackageManagerLocal;
import com.android.server.pm.dex.DexoptOptions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class CrashDetectionAndOptimization {
    public static final List dexOptimizedPackages = new ArrayList();
    public static final List crashPackages = new ArrayList();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class CrashPackage {
        public int crashCount;
        public long mTimeStamp;
        public String packageName;
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DexOptimizationThread extends Thread {
        public final int maxPossibleCount = 4;
        public final ProcessRecord processRecord;

        public DexOptimizationThread(ProcessRecord processRecord) {
            this.processRecord = processRecord;
        }

        public static void doForceReDexOpt(CrashPackage crashPackage, String str) {
            String str2 = crashPackage.packageName;
            ArrayList arrayList = (ArrayList) CrashDetectionAndOptimization.dexOptimizedPackages;
            if (arrayList.size() >= 8) {
                arrayList.remove(0);
            }
            arrayList.add(str);
            try {
                PackageManagerLocal.FilteredSnapshot withFilteredSnapshot =
                        ((PackageManagerLocal)
                                        LocalManagerRegistry.getManager(PackageManagerLocal.class))
                                .withFilteredSnapshot();
                try {
                    Slog.i("CRASH_DEXOPT", "Try to re-compile: " + str2);
                    if (DexOptHelper.getArtManagerLocal()
                                    .dexoptPackage(
                                            withFilteredSnapshot,
                                            str2,
                                            new DexoptOptions(24, 1031, str2, "speed-profile", null)
                                                    .convertToDexoptParams(0))
                                    .getFinalStatus()
                            == 30) {
                        Slog.i("CRASH_DEXOPT", "dexopt fail: " + str2);
                    }
                    if (withFilteredSnapshot != null) {
                        withFilteredSnapshot.close();
                    }
                } finally {
                }
            } catch (Exception e) {
                StringBuilder m =
                        DumpUtils$$ExternalSyntheticOutline0.m(
                                "Assume processing was successful and ignore the package: ",
                                str2,
                                " (");
                m.append(e.getMessage());
                m.append(")");
                Slog.i("CRASH_DEXOPT", m.toString());
            }
            ((ArrayList) CrashDetectionAndOptimization.crashPackages).remove(crashPackage);
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            String str;
            if (CrashDetectionAndOptimization.crashPackages == null) {
                return;
            }
            String str2 = this.processRecord.info.packageName;
            Iterator it =
                    ((ArrayList) CrashDetectionAndOptimization.dexOptimizedPackages).iterator();
            while (it.hasNext()) {
                if (((String) it.next()).contains(str2)) {
                    return;
                }
            }
            List list = CrashDetectionAndOptimization.crashPackages;
            synchronized (list) {
                try {
                    ((ArrayList) list).isEmpty();
                    long currentTimeMillis = System.currentTimeMillis();
                    Iterator it2 = ((ArrayList) list).iterator();
                    while (true) {
                        if (it2.hasNext()) {
                            CrashPackage crashPackage = (CrashPackage) it2.next();
                            if (crashPackage != null
                                    && (str = crashPackage.packageName) != null
                                    && str.equals(str2)) {
                                if ((currentTimeMillis - crashPackage.mTimeStamp) / 1000 >= 240) {
                                    ((ArrayList) CrashDetectionAndOptimization.crashPackages)
                                            .remove(crashPackage);
                                } else {
                                    int i = crashPackage.crashCount + 1;
                                    crashPackage.crashCount = i;
                                    if (i >= this.maxPossibleCount) {
                                        doForceReDexOpt(crashPackage, str2);
                                    }
                                }
                            }
                        } else {
                            List list2 = CrashDetectionAndOptimization.crashPackages;
                            if (((ArrayList) list2).size() == 8) {
                                ((ArrayList) list2).remove(0);
                            } else if (((ArrayList) list2).size() < 8) {
                                CrashPackage crashPackage2 = new CrashPackage();
                                crashPackage2.mTimeStamp = -1L;
                                crashPackage2.crashCount = 0;
                                crashPackage2.mTimeStamp = System.currentTimeMillis();
                                crashPackage2.packageName = str2;
                                crashPackage2.crashCount = 1;
                                ((ArrayList) list2).add(crashPackage2);
                            }
                        }
                    }
                } finally {
                }
            }
        }
    }
}
