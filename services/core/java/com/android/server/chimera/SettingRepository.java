package com.android.server.chimera;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.SystemProperties;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.ExplicitHealthCheckController$$ExternalSyntheticOutline0;
import com.android.server.am.MARsPolicyManager;
import com.samsung.android.rune.CoreRune;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class SettingRepository {
    public final int mDefaultCachedMax;
    public boolean mIsConservativeMode;
    public boolean mIsCustomMode;
    public final boolean mIsDefaultConservativeMode;
    public boolean mIsDynamicTargetFreeEnabled;
    public int mLastCachedMax;
    public final SystemRepository mSystemRepository;
    public boolean mQuickReclaimEnable = SystemProperties.getBoolean("ro.slmk.chimera.quickreclaim_enable", false);
    public boolean mIsPSITrackerEnable = true;
    public boolean mIsAppCacheReclaimEnable = false;
    public boolean mIsFastMadviseEnable = false;
    public boolean mIsSubProcEnable = true;

    public SettingRepository(SystemRepository systemRepository) {
        this.mDefaultCachedMax = 0;
        this.mLastCachedMax = 0;
        this.mSystemRepository = systemRepository;
        systemRepository.getClass();
        int parseInt = Integer.parseInt(SystemProperties.get("ro.slmk.dha_cached_max", "16"));
        this.mDefaultCachedMax = parseInt;
        this.mLastCachedMax = parseInt;
        systemRepository.getClass();
        boolean equals = SystemProperties.get("ro.slmk.use_bg_keeping_policy", "false").equals("true");
        this.mIsConservativeMode = equals;
        this.mIsDefaultConservativeMode = equals;
    }

    public final void enableCustomMode(boolean z, boolean z2) {
        this.mSystemRepository.getClass();
        if ("true".equals(SystemProperties.get("persist.config.chimera.enable", "false").split(",")[0])) {
            if (z) {
                SystemProperties.set("persist.config.chimera.enable", "true,".concat(z2 ? "CC" : "CA"));
            } else {
                SystemProperties.set("persist.config.chimera.enable", "true");
            }
            initialize();
        }
    }

    public final void initialize() {
        SystemRepository systemRepository = this.mSystemRepository;
        systemRepository.getClass();
        String str = SystemProperties.get("persist.config.chimera.enable", "false");
        this.mIsCustomMode = false;
        this.mIsConservativeMode = this.mIsDefaultConservativeMode;
        this.mIsAppCacheReclaimEnable = BatteryService$$ExternalSyntheticOutline0.m45m("/proc/proc_caches_reclaim");
        this.mIsFastMadviseEnable = CoreRune.FAST_MADVISE_ENABLED;
        boolean z = MARsPolicyManager.MARs_ENABLE;
        MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.getClass();
        this.mIsSubProcEnable = MARsPolicyManager.isChinaPolicyEnabled();
        String[] split = str.split(",");
        boolean z2 = this.mIsConservativeMode;
        List<PackageInfo> installedPackages = systemRepository.mContext.getPackageManager().getInstalledPackages(0);
        if (installedPackages != null) {
            Iterator<PackageInfo> it = installedPackages.iterator();
            while (it.hasNext()) {
                if ("com.samsung.android.memoryguardian".equals(it.next().packageName)) {
                    boolean equals = "true".equals(split[0]);
                    int i = this.mDefaultCachedMax;
                    if (equals && split.length > 1) {
                        String str2 = split[1];
                        if ("CC".equals(str2)) {
                            this.mIsConservativeMode = true;
                            this.mIsCustomMode = true;
                            i = ChimeraCommonUtil.getRamSizeGb() <= 6 ? (int) (i * 1.5d) : i * 2;
                            if (i > 48) {
                                i = 48;
                            }
                        } else if ("CA".equals(str2)) {
                            this.mIsConservativeMode = false;
                            this.mIsCustomMode = true;
                        }
                    }
                    if (this.mLastCachedMax != i || z2 != this.mIsConservativeMode) {
                        Intent m = ExplicitHealthCheckController$$ExternalSyntheticOutline0.m("com.android.server.am.BROADCAST_SET_LMKD_PARAMETER_INTENT", "android");
                        if (this.mLastCachedMax != i) {
                            m.putExtra("ro.slmk.dha_cached_max", Integer.toString(i));
                            this.mLastCachedMax = i;
                        }
                        boolean z3 = this.mIsConservativeMode;
                        if (z2 != z3) {
                            m.putExtra("ro.slmk.use_bg_keeping_policy", z3 ? "1" : "0");
                        }
                        systemRepository.mContext.sendBroadcast(m);
                    }
                    this.mIsPSITrackerEnable = Boolean.parseBoolean(SystemProperties.get("ro.slmk.psitracker_enable", "true"));
                    return;
                }
            }
        }
    }
}
