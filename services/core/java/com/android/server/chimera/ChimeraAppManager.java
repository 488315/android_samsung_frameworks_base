package com.android.server.chimera;

import android.app.usage.UsageStatsManager;
import android.os.SystemProperties;
import android.util.ArrayMap;

import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class ChimeraAppManager {
    public static final String CEM_PKG_PROTECTED_INTERVAL_DEFAULT = String.valueOf(60000);
    public final ChimeraAppClassifier mAppClassifier;
    public final int mCemPkgProtectedIntervalMs;
    public final Map mStandbyInfoMap;
    public final SystemRepository mSystemRepository;
    public final Map mReclaimApps = new ArrayMap();
    public final Map mGcApps = new ArrayMap();
    public final Set mForegroundG3ProcList = new HashSet();

    public ChimeraAppManager(SystemRepository systemRepository) {
        this.mAppClassifier = null;
        this.mStandbyInfoMap = new ArrayMap();
        this.mAppClassifier = new ChimeraAppClassifier(systemRepository);
        this.mSystemRepository = systemRepository;
        systemRepository.getClass();
        this.mCemPkgProtectedIntervalMs =
                Integer.parseInt(
                        SystemProperties.get(
                                "persist.sys.chimera_cem_pkg_protected_interval_ms",
                                CEM_PKG_PROTECTED_INTERVAL_DEFAULT));
        UsageStatsManager usageStatsManager =
                (UsageStatsManager)
                        systemRepository.mContext.getSystemService(UsageStatsManager.class);
        this.mStandbyInfoMap =
                usageStatsManager == null
                        ? new HashMap()
                        : usageStatsManager.getAppStandbyBuckets();
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.util.List getAppsToKill(
            com.android.server.chimera.SkipReasonLogger r33,
            int r34,
            com.android.server.chimera.ChimeraCommonUtil.TriggerSource r35,
            java.util.List r36) {
        /*
            Method dump skipped, instructions count: 1602
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.chimera.ChimeraAppManager.getAppsToKill(com.android.server.chimera.SkipReasonLogger,"
                    + " int, com.android.server.chimera.ChimeraCommonUtil$TriggerSource,"
                    + " java.util.List):java.util.List");
    }

    public final void logSkip(
            SystemRepository.RunningAppProcessInfo runningAppProcessInfo, String str) {
        StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(str, " ");
        m.append(runningAppProcessInfo.pid);
        m.append(" ");
        m.append(runningAppProcessInfo.processName);
        String sb = m.toString();
        this.mSystemRepository.getClass();
        SystemRepository.logDebug("ChimeraAppManager", sb);
    }
}
