package com.android.server.bgslotmanager;

import android.os.CountDownTimer;
import android.util.Slog;
import com.android.server.am.BGProtectManager;
import com.android.server.am.DynamicHiddenApp;
import com.android.server.wm.TaskSnapshotCache;
import com.samsung.android.knox.analytics.activation.ActivationMonitor;

/* loaded from: classes.dex */
public class BGSlotManager {
  public DynamicHiddenApp mDynamicHiddenApp;
  public MemInfoGetter mInfo;
  public static int MIN_CACHED_APPS = BgAppPropManager.getSlmkPropertyInt("dha_cached_min", "4");
  public static int MAX_CACHED_APPS = BgAppPropManager.getSlmkPropertyInt("dha_cached_max", "16");
  public static int MIN_EMPTY_APPS = BgAppPropManager.getSlmkPropertyInt("dha_empty_min", "8");
  public static int MAX_EMPTY_APPS = BgAppPropManager.getSlmkPropertyInt("dha_empty_max", "24");
  public static int BONUS_MAX_CACHED_APPS_PER_SWAP = 0;
  public static int STATIC_MAX_EMPTY_FOR_OVER_8GB =
      BgAppPropManager.getSlmkPropertyInt("dha_empty_limit", "32");
  public static final boolean IS_CHINA_MODEL =
      "china"
          .equals(
              BgAppPropManager.getSystemPropertyString(ActivationMonitor.COUNTRY_CODE_PROPERTY, "")
                  .toLowerCase());
  public static boolean HRT_MaxCached_Enable =
      BgAppPropManager.getSlmkPropertyBool("dha_hrt_max_enable", "true");
  public final int CHN_REDUCE_CACHED =
      BgAppPropManager.getSlmkPropertyInt("reduce_chn_cached_max", "0");
  public final int CHN_MAX_CACHED_APPS = 512;
  public int originCachedMax = MAX_CACHED_APPS;
  public int originCachedMin = MIN_CACHED_APPS;
  public int originEmptyMax = MAX_EMPTY_APPS;
  public int originEmptyMin = MIN_EMPTY_APPS;
  public int BGSlotState = 0;

  public BGSlotManager(MemInfoGetter memInfoGetter) {
    this.mInfo = memInfoGetter;
  }

  public void initBGSlotManager(DynamicHiddenApp dynamicHiddenApp, long j) {
    this.mDynamicHiddenApp = dynamicHiddenApp;
    boolean z = IS_CHINA_MODEL;
    if (z) {
      MAX_CACHED_APPS = 512 - this.CHN_REDUCE_CACHED;
      BGProtectManager.dha_keepempty_key = BGProtectManager.dha_keepempty_chn_key;
      Slog.d("DynamicHiddenApp_BGSlotManager", "is china model : " + z);
    }
    if (j > 6144) {
      int i = MAX_EMPTY_APPS;
      int i2 = STATIC_MAX_EMPTY_FOR_OVER_8GB;
      if (i < i2) {
        MAX_EMPTY_APPS = i2;
      }
    }
    this.originCachedMax = MAX_CACHED_APPS;
    this.originCachedMin = MIN_CACHED_APPS;
    this.originEmptyMax = MAX_EMPTY_APPS;
    this.originEmptyMin = MIN_EMPTY_APPS;
    dynamicHiddenApp.setCurTrimProcesses(
        BgAppPropManager.getSlmkPropertyInt("cur_trim_cached_num", "0"),
        BgAppPropManager.getSlmkPropertyInt("cur_trim_empty_num", "0"));
  }

  public void initBGSlotManagerPostBoot() {
    setBonusMaxCachedAppsPerSwap();
  }

  public final void setBonusMaxCachedAppsPerSwap() {
    int swapsizeGB = this.mInfo.getSwapsizeGB();
    if (swapsizeGB >= 8) {
      BONUS_MAX_CACHED_APPS_PER_SWAP = 6;
    } else if (swapsizeGB >= 6) {
      BONUS_MAX_CACHED_APPS_PER_SWAP = 2;
    } else {
      BONUS_MAX_CACHED_APPS_PER_SWAP = 0;
    }
    changeBGSlot();
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
    MIN_CACHED_APPS = Math.max(0, i2 + 0);
    MAX_EMPTY_APPS = Math.max(0, i3 + 0);
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

  public void setCameraBGSlot() {
    this.BGSlotState |= 1;
    changeBGSlot();
  }

  public void restoreFromCameraBGSlot() {
    this.BGSlotState &= -2;
    changeBGSlot();
    Slog.i("DynamicHiddenApp_BGSlotManager", "CameraBGSlot Recovered");
  }

  public void setHighResBGSlot() {
    this.BGSlotState |= 2;
    changeBGSlot();
  }

  public void restoreFromHighResBGSlot() {
    this.BGSlotState &= -3;
    changeBGSlot();
    Slog.i("DynamicHiddenApp_BGSlotManager", "HighResBGSlot Recovered");
  }

  public void setHomeHubBGSlot() {
    this.BGSlotState |= 4;
    changeBGSlot();
  }

  public void restoreFromHomeHubBGSlot() {
    this.BGSlotState &= -5;
    changeBGSlot();
    Slog.i("DynamicHiddenApp_BGSlotManager", "HomeHubBGSlot Recovered");
  }

  public void setBGSlotByRes(int i, int i2) {
    if (HRT_MaxCached_Enable && i * i2 >= 3686400) {
      setHighResBGSlot();
    } else {
      restoreFromHighResBGSlot();
    }
  }

  public void setReentryModeBGSlot() {
    this.BGSlotState |= 8;
    changeBGSlot();
    Slog.i("DynamicHiddenApp_BGSlotManager", "ReentryModeBGSlot Set");
  }

  public void restoreFromReentryModeBGSlot() {
    this.BGSlotState &= -9;
    changeBGSlot();
    Slog.i("DynamicHiddenApp_BGSlotManager", "ReentryModeBGSlot Recovered");
  }

  public void updateDefaultCachedMAX() {
    int i = MAX_CACHED_APPS;
    this.mDynamicHiddenApp.updateMaxCachedProcessesNum(i + MAX_EMPTY_APPS, 1.0f - (i / (i + r1)));
  }

  public void runSetBonusMaxCachedAppsPerSwapTimer() {
    new CountDownTimer(
        60000L, 60000L) { // from class: com.android.server.bgslotmanager.BGSlotManager.1
      @Override // android.os.CountDownTimer
      public void onTick(long j) {}

      @Override // android.os.CountDownTimer
      public void onFinish() {
        Slog.i("DynamicHiddenApp_BGSlotManager", "swapTimer onfinish");
        BGSlotManager.this.setBonusMaxCachedAppsPerSwap();
        Slog.i(
            "DynamicHiddenApp_BGSlotManager",
            "change Max_Cached by setSwapTimer : " + BGSlotManager.MAX_CACHED_APPS);
      }
    }.start();
  }

  public void setOriginCachedMax(int i) {
    if (i > 0) {
      MAX_CACHED_APPS = i;
      this.originCachedMax = i;
      changeBGSlot();
    }
  }

  public void setOriginCachedMin(int i) {
    if (i > 0) {
      MIN_CACHED_APPS = i;
      this.originCachedMin = i;
      changeBGSlot();
    }
  }

  public void setOriginEmptyMax(int i) {
    if (i > 0) {
      MAX_EMPTY_APPS = i;
      this.originEmptyMax = i;
      changeBGSlot();
    }
  }

  public void setOriginEmptyMin(int i) {
    if (i > 0) {
      MIN_EMPTY_APPS = i;
      this.originEmptyMin = i;
      changeBGSlot();
    }
  }

  public void setTaskSnapshot(int i, int i2) {
    int i3;
    int slmkPropertyInt = BgAppPropManager.getSlmkPropertyInt("max_snapshot_num", "0");
    int physicalMemory = this.mInfo.getPhysicalMemory();
    int i4 = i * i2;
    int i5 = 2;
    int i6 = 5;
    int[][] iArr = {
      new int[] {2, 3, 3, 3},
      new int[] {3, 5, 3, 3},
      new int[] {4, 8, 4, 3},
      new int[] {6, 10, 6, 3},
      new int[] {8, 10, 8, 4},
      new int[] {12, 10, 10, 10},
      new int[] {16, 10, 10, 10}
    };
    int i7 = 0;
    if (i4 != 0 && physicalMemory != 0) {
      Slog.i("DynamicHiddenApp_BGSlotManager", "start update max task snapshot number");
      if (i4 >= 3686400) {
        i5 = 3;
      } else if (i4 < 2073600) {
        i5 = 1;
      }
      int i8 = 0;
      while (true) {
        if (i8 >= 7) {
          i3 = 0;
          break;
        }
        int[] iArr2 = iArr[i8];
        if (physicalMemory == iArr2[0]) {
          int i9 = iArr2[i5];
          Slog.i(
              "DynamicHiddenApp_BGSlotManager",
              "update max task snapshot number, physical memory: "
                  + physicalMemory
                  + ", current resolution : "
                  + i5);
          i3 = i9;
          i7 = 1;
          break;
        }
        i8++;
      }
      if (i7 == 0) {
        Slog.i(
            "DynamicHiddenApp_BGSlotManager",
            "can not update max task snapshot number, due to unidentified physical memory");
        Slog.i(
            "DynamicHiddenApp_BGSlotManager",
            "physical memory: " + physicalMemory + ", current resolution: " + i5);
      } else {
        i6 = i3;
      }
      i7 = i5;
    } else {
      Slog.i(
          "DynamicHiddenApp_BGSlotManager",
          "can not update max task snapshot number, due to resolution or physical memory");
      Slog.i(
          "DynamicHiddenApp_BGSlotManager",
          "physical memory: " + physicalMemory + ", resolution: " + i4);
    }
    if (slmkPropertyInt <= 0) {
      slmkPropertyInt = i6;
    }
    Slog.i(
        "DynamicHiddenApp_BGSlotManager", "TaskSnapShot : " + slmkPropertyInt + ", CurRes : " + i7);
    TaskSnapshotCache.setMaxSnapshot(slmkPropertyInt);
  }
}
