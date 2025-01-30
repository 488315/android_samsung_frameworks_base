package com.android.server.chimera;

import android.content.Intent;
import com.samsung.android.rune.CoreRune;
import java.io.File;

/* loaded from: classes.dex */
public class SettingRepositoryDefault implements SettingRepository {
    public int mDefaultCachedMax;
    public boolean mIsConservativeMode;
    public boolean mIsCustomMode;
    public boolean mIsDefaultConservativeMode;
    public boolean mIsDynamicTargetFreeEnabled;
    public int mLastCachedMax;
    public SystemRepository mSystemRepository;
    public boolean mIsReclaimPageCacheEnable = false;
    public boolean mIsGcEnable = false;
    public boolean mQuickReclaimEnable = true;
    public boolean mIsAppsIdleKillEnable = true;
    public boolean mIsNativeProcessesIdleKillEnable = true;
    public boolean mIsPSITrackerEnable = true;
    public boolean mIsAppCacheReclaimEnable = false;
    public boolean mIsFastMadviseEnable = false;

    public SettingRepositoryDefault(SystemRepository systemRepository) {
        this.mDefaultCachedMax = 0;
        this.mLastCachedMax = 0;
        this.mSystemRepository = systemRepository;
        int parseInt = Integer.parseInt(systemRepository.getSystemProperty("ro.slmk.dha_cached_max", "16"));
        this.mDefaultCachedMax = parseInt;
        this.mLastCachedMax = parseInt;
        boolean equals = this.mSystemRepository.getSystemProperty("ro.slmk.use_bg_keeping_policy", "false").equals("true");
        this.mIsConservativeMode = equals;
        this.mIsDefaultConservativeMode = equals;
    }

    @Override // com.android.server.chimera.SettingRepository
    public void initialize() {
        String systemProperty = this.mSystemRepository.getSystemProperty("persist.config.chimera.enable", "false");
        this.mIsCustomMode = false;
        this.mIsConservativeMode = this.mIsDefaultConservativeMode;
        this.mIsAppCacheReclaimEnable = isProcCachesReclaimExist();
        this.mIsFastMadviseEnable = CoreRune.FAST_MADVISE_ENABLED;
        String[] split = systemProperty.split(",");
        boolean z = this.mIsConservativeMode;
        if (this.mSystemRepository.isPackageInstalled("com.samsung.android.memoryguardian")) {
            int i = this.mDefaultCachedMax;
            if ("true".equals(split[0]) && split.length > 1) {
                String str = split[1];
                if ("CC".equals(str)) {
                    this.mIsConservativeMode = true;
                    this.mIsCustomMode = true;
                    i = ChimeraCommonUtil.getRamSizeGb() <= 6 ? (int) (i * 1.5d) : i * 2;
                    if (i > 48) {
                        i = 48;
                    }
                } else if ("CA".equals(str)) {
                    this.mIsConservativeMode = false;
                    this.mIsCustomMode = true;
                }
            }
            if (this.mLastCachedMax != i || z != this.mIsConservativeMode) {
                Intent intent = new Intent("com.android.server.am.BROADCAST_SET_LMKD_PARAMETER_INTENT");
                intent.setPackage("android");
                if (this.mLastCachedMax != i) {
                    intent.putExtra("ro.slmk.dha_cached_max", Integer.toString(i));
                    this.mLastCachedMax = i;
                }
                boolean z2 = this.mIsConservativeMode;
                if (z != z2) {
                    intent.putExtra("ro.slmk.use_bg_keeping_policy", z2 ? "1" : "0");
                }
                this.mSystemRepository.sendBroadcast(intent);
            }
            this.mIsPSITrackerEnable = Boolean.parseBoolean(this.mSystemRepository.getSystemProperty("ro.slmk.psitracker_enable", "true"));
        }
    }

    @Override // com.android.server.chimera.SettingRepository
    public boolean isDynamicTargetFreeEnabled() {
        return this.mIsDynamicTargetFreeEnabled;
    }

    @Override // com.android.server.chimera.SettingRepository
    public void enableDynamicTargetFree(boolean z) {
        this.mIsDynamicTargetFreeEnabled = z;
    }

    @Override // com.android.server.chimera.SettingRepository
    public boolean isConservativeMode() {
        return this.mIsConservativeMode;
    }

    @Override // com.android.server.chimera.SettingRepository
    public void enableConservativeMode(boolean z) {
        this.mIsConservativeMode = z;
    }

    @Override // com.android.server.chimera.SettingRepository
    public boolean isReclaimPageCacheEnabled() {
        return this.mIsReclaimPageCacheEnable;
    }

    @Override // com.android.server.chimera.SettingRepository
    public void enableReclaimPageCache(boolean z) {
        this.mIsReclaimPageCacheEnable = z;
    }

    @Override // com.android.server.chimera.SettingRepository
    public boolean isGcEnabled() {
        return this.mIsGcEnable;
    }

    @Override // com.android.server.chimera.SettingRepository
    public void enableGc(boolean z) {
        this.mIsGcEnable = z;
    }

    @Override // com.android.server.chimera.SettingRepository
    public boolean isCustomMode() {
        return this.mIsCustomMode;
    }

    @Override // com.android.server.chimera.SettingRepository
    public void enableCustomMode(boolean z, boolean z2) {
        if ("true".equals(this.mSystemRepository.getSystemProperty("persist.config.chimera.enable", "false").split(",")[0])) {
            if (z) {
                SystemRepository systemRepository = this.mSystemRepository;
                StringBuilder sb = new StringBuilder();
                sb.append("true,");
                sb.append(z2 ? "CC" : "CA");
                systemRepository.setSystemProperty("persist.config.chimera.enable", sb.toString());
            } else {
                this.mSystemRepository.setSystemProperty("persist.config.chimera.enable", "true");
            }
            initialize();
        }
    }

    @Override // com.android.server.chimera.SettingRepository
    public boolean isConservativeDefault() {
        return this.mSystemRepository.getSystemProperty("ro.slmk.use_bg_keeping_policy", "false").equals("true");
    }

    @Override // com.android.server.chimera.SettingRepository
    public boolean isQuickReclaimEnable() {
        return this.mQuickReclaimEnable;
    }

    @Override // com.android.server.chimera.SettingRepository
    public void enableQuickReclaim(boolean z) {
        this.mQuickReclaimEnable = z;
    }

    @Override // com.android.server.chimera.SettingRepository
    public boolean isAppsIdleKillEnabled() {
        return this.mIsAppsIdleKillEnable;
    }

    @Override // com.android.server.chimera.SettingRepository
    public void enableAppsIdleKill(boolean z) {
        this.mIsAppsIdleKillEnable = z;
    }

    @Override // com.android.server.chimera.SettingRepository
    public boolean isNativeProcessesIdleKillEnabled() {
        return this.mIsNativeProcessesIdleKillEnable;
    }

    @Override // com.android.server.chimera.SettingRepository
    public void enableNativeProcessesIdleKill(boolean z) {
        this.mIsNativeProcessesIdleKillEnable = z;
    }

    @Override // com.android.server.chimera.SettingRepository
    public boolean isPSITrackerEnabled() {
        return this.mIsPSITrackerEnable;
    }

    @Override // com.android.server.chimera.SettingRepository
    public boolean isAppCacheReclaimEnable() {
        return this.mIsAppCacheReclaimEnable;
    }

    @Override // com.android.server.chimera.SettingRepository
    public void enableAppCacheReclaim(boolean z) {
        this.mIsAppCacheReclaimEnable = z;
    }

    public boolean isProcCachesReclaimExist() {
        return new File("/proc/proc_caches_reclaim").exists();
    }

    @Override // com.android.server.chimera.SettingRepository
    public boolean isFastMadviseEnable() {
        return this.mIsFastMadviseEnable;
    }
}
