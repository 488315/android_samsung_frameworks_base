package com.android.server.chimera;

/* loaded from: classes.dex */
public interface SettingRepository {
    void enableAppCacheReclaim(boolean z);

    void enableAppsIdleKill(boolean z);

    void enableConservativeMode(boolean z);

    void enableCustomMode(boolean z, boolean z2);

    void enableDynamicTargetFree(boolean z);

    void enableGc(boolean z);

    void enableNativeProcessesIdleKill(boolean z);

    void enableQuickReclaim(boolean z);

    void enableReclaimPageCache(boolean z);

    void initialize();

    boolean isAppCacheReclaimEnable();

    boolean isAppsIdleKillEnabled();

    boolean isConservativeDefault();

    boolean isConservativeMode();

    boolean isCustomMode();

    boolean isDynamicTargetFreeEnabled();

    boolean isFastMadviseEnable();

    boolean isGcEnabled();

    boolean isNativeProcessesIdleKillEnabled();

    boolean isPSITrackerEnabled();

    boolean isQuickReclaimEnable();

    boolean isReclaimPageCacheEnabled();
}
