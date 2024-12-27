package com.android.server.bgslotmanager;

import android.os.SystemProperties;
import android.util.Slog;

import com.android.server.am.ActivityManagerConstants;
import com.android.server.am.DynamicHiddenApp;

import com.samsung.android.knox.analytics.activation.ActivationMonitor;

public final class BGSlotManager {
    public DynamicHiddenApp mDynamicHiddenApp;
    public final MemInfoGetter mInfo;
    public static int MIN_CACHED_APPS = BgAppPropManager.getSlmkPropertyInt("dha_cached_min", "4");
    public static int MAX_CACHED_APPS = BgAppPropManager.getSlmkPropertyInt("cached_max", "512");
    public static int MIN_EMPTY_APPS = BgAppPropManager.getSlmkPropertyInt("dha_empty_min", "8");
    public static int MAX_EMPTY_APPS = BgAppPropManager.getSlmkPropertyInt("dha_empty_max", "24");
    public static int BONUS_MAX_CACHED_APPS_PER_SWAP = 0;
    public static final int STATIC_MAX_EMPTY_FOR_OVER_8GB =
            BgAppPropManager.getSlmkPropertyInt("dha_empty_limit", "32");
    public static final boolean IS_CHINA_MODEL =
            "china"
                    .equals(
                            SystemProperties.get(ActivationMonitor.COUNTRY_CODE_PROPERTY, "")
                                    .toLowerCase());
    public static final boolean HRT_MaxCached_Enable =
            BgAppPropManager.getSlmkPropertyBool("dha_hrt_max_enable", "true");
    public final int CHN_REDUCE_CACHED =
            BgAppPropManager.getSlmkPropertyInt("reduce_chn_cached_max", "0");
    public int originCachedMax = MAX_CACHED_APPS;
    public int originCachedMin = MIN_CACHED_APPS;
    public int originEmptyMax = MAX_EMPTY_APPS;
    public int originEmptyMin = MIN_EMPTY_APPS;
    public int BGSlotState = 0;

    public BGSlotManager(MemInfoGetter memInfoGetter) {
        this.mInfo = memInfoGetter;
    }

    public final void changeBGSlot() {
        int i = this.originCachedMax;
        int i2 = this.originCachedMin;
        int i3 = this.originEmptyMax;
        int i4 = this.BGSlotState;
        if ((i4 & 1) > 0) {
            int i5 = i3 / 2;
            int i6 = this.originEmptyMin;
            i3 = i5 > i6 ? i3 / 2 : i6;
            i2 = 2;
        }
        int i7 = (i4 & 2) > 0 ? -4 : 0;
        if ((i4 & 4) > 0) {
            i = 4;
            i3 = 16;
            i7 = 0;
        }
        if ((i4 & 8) > 0) {
            i7 += 6;
        }
        Slog.i(
                "DynamicHiddenApp_BGSlotManager",
                "Before ChangeBGSlot CachedMax: "
                        + MAX_CACHED_APPS
                        + ", CachedMin: "
                        + MIN_CACHED_APPS
                        + ", EmptyMax: "
                        + MAX_EMPTY_APPS);
        MAX_CACHED_APPS = Math.max(0, i + i7 + BONUS_MAX_CACHED_APPS_PER_SWAP);
        MIN_CACHED_APPS = Math.max(0, i2);
        MAX_EMPTY_APPS = Math.max(0, i3);
        Slog.i(
                "DynamicHiddenApp_BGSlotManager",
                "After ChangeBGSlot CachedMax: "
                        + MAX_CACHED_APPS
                        + ", CachedMin: "
                        + MIN_CACHED_APPS
                        + ", EmptyMax: "
                        + MAX_EMPTY_APPS);
        updateDefaultCachedMAX();
    }

    public final void setBonusMaxCachedAppsPerSwap() {
        int i;
        MemInfoGetter memInfoGetter = this.mInfo;
        memInfoGetter.mInfoInner.readLightMemInfo();
        int swapTotalSizeKb = (int) (memInfoGetter.mInfoInner.getSwapTotalSizeKb() / 1024);
        int[][] iArr = memInfoGetter.memoryMBToGBPool;
        int length = iArr.length;
        int i2 = 0;
        while (true) {
            if (i2 >= length) {
                i = 0;
                break;
            }
            int[] iArr2 = iArr[i2];
            if (swapTotalSizeKb > iArr2[0]) {
                i = iArr2[1];
                break;
            }
            i2++;
        }
        if (i >= 8) {
            BONUS_MAX_CACHED_APPS_PER_SWAP = 6;
        } else if (i >= 6) {
            BONUS_MAX_CACHED_APPS_PER_SWAP = 2;
        } else {
            BONUS_MAX_CACHED_APPS_PER_SWAP = 0;
        }
        changeBGSlot();
    }

    public final void updateDefaultCachedMAX() {
        int i = MAX_CACHED_APPS;
        int i2 = MAX_EMPTY_APPS;
        DynamicHiddenApp dynamicHiddenApp = this.mDynamicHiddenApp;
        dynamicHiddenApp.getClass();
        ActivityManagerConstants.DEFAULT_MAX_CACHED_PROCESSES = i + i2;
        ActivityManagerConstants.EMPTY_RATE = 1.0f - (i / (i + i2));
        ActivityManagerConstants activityManagerConstants = dynamicHiddenApp.mConstants;
        if (activityManagerConstants != null) {
            activityManagerConstants.MAX_CACHED_PROCESSES =
                    ActivityManagerConstants.DEFAULT_MAX_CACHED_PROCESSES;
            activityManagerConstants.updateMaxCachedProcesses();
        }
    }
}
