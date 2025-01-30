package com.android.server.chimera;

import android.net.util.NetworkConstants;
import android.os.SystemProperties;
import android.p005os.IInstalld;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.display.DisplayPowerController2;
import java.io.PrintWriter;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class ChimeraStrategy {
  public int mAlwaysRunningProcessQuota;
  public int mDeviceIdleAppThreshold;
  public int mDeviceIdleNativeThreshold;
  public DynamicFreeMem mDynamicFreeMem;
  public long mFixedMemFreeTarget;
  public long mMemFreeTarget;
  public int mProtectedCountOnDynamic;
  public int mProtectedCountOnHomeTrigger;
  public int mProtectedCountOnLmkdTrigger;
  public int mQuickReclaimDefaultThreshold;
  public ChimeraRecentAppManager mRecentAppManager;
  public final SettingRepository mSettingRepository;
  public final SystemRepository mSystemRepository;
  public final String PROPERTY_NAME_PROTECTED_COUNT_LMKD =
      "persist.config.chimera.protected_count_on_lmkd";
  public final String PROPERTY_NAME_PROTECTED_COUNT_HOME =
      "persist.config.chimera.protected_count_on_home";
  public final String PROPERTY_NAME_STRATEGY = "ro.slmk.chimera_strategy_%dgb";

  public abstract class DefaultParameters {
    public static int UNKNOWN_GB_PROTECTED_COUNT_ON_HOME = 32;
    public static int UNKNOWN_GB_PROTECTED_COUNT_ON_LMKD = 32;
    public static int UNKNOWN_GB_TARGET_FREE = 32768;
    public static int[][] sParameters = {
      new int[] {2, 485, 5, 3, 885, 100, 400, 500, 600},
      new int[] {3, 614, 7, 6, 1098, 150, 400, 500, 1000},
      new int[] {4, 630, 11, 8, 1406, 300, 400, 500, NetworkConstants.ETHER_MTU},
      new int[] {
        6, 1100, 19, 9, 2034, 350, 600, 500, IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES
      },
      new int[] {8, 2150, 24, 10, 2550, 400, 600, 1024, 2480},
      new int[] {
        12,
        2457,
        28,
        14,
        2857,
        500,
        1024,
        FrameworkStatsLog.APP_STANDBY_BUCKET_CHANGED__MAIN_REASON__MAIN_FORCED_BY_SYSTEM,
        IInstalld.FLAG_USE_QUOTA
      },
      new int[] {
        16,
        2457,
        28,
        14,
        2857,
        600,
        1024,
        FrameworkStatsLog.APP_STANDBY_BUCKET_CHANGED__MAIN_REASON__MAIN_FORCED_BY_SYSTEM,
        6144
      },
      new int[] {-1, -1, -1, -1, -1, -1}
    };
    public static int IDX_DYNAMIC_REENTRY = 2;
    public static int IDX_DYNAMIC_PROTECT = 1;
    public static float[][] sDynamicParameters = {
      new float[] {2.0f, 3.0f, 5.0f},
      new float[] {3.0f, 3.0f, 7.0f},
      new float[] {4.0f, 6.0f, 10.0f},
      new float[] {6.0f, 7.0f, 13.0f},
      new float[] {8.0f, 8.0f, 16.0f},
      new float[] {12.0f, 14.0f, 16.0f},
      new float[] {16.0f, 14.0f, 17.0f},
      new float[] {-1.0f, -1.0f, -1.0f}
    };

    public static int getTargetFree(int i) {
      int i2 = 0;
      while (true) {
        int[] iArr = sParameters[i2];
        int i3 = iArr[0];
        if (i3 <= 0) {
          return -1;
        }
        if (i3 >= i) {
          return iArr[1];
        }
        i2++;
      }
    }

    public static int getProtectedCountOnLmkdTrigger(int i) {
      int i2 = 0;
      while (true) {
        int[] iArr = sParameters[i2];
        int i3 = iArr[0];
        if (i3 <= 0) {
          return -1;
        }
        if (i3 >= i) {
          return iArr[2];
        }
        i2++;
      }
    }

    public static int getProtectedCountOnHomeTrigger(int i) {
      int i2 = 0;
      while (true) {
        int[] iArr = sParameters[i2];
        int i3 = iArr[0];
        if (i3 <= 0) {
          return -1;
        }
        if (i3 >= i) {
          return iArr[3];
        }
        i2++;
      }
    }

    public static float getDynamicProtectedCount(int i) {
      int i2 = 0;
      while (true) {
        float[] fArr = sDynamicParameters[i2];
        float f = fArr[0];
        if (f <= DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
          return -1.0f;
        }
        if (f >= i) {
          return fArr[IDX_DYNAMIC_PROTECT];
        }
        i2++;
      }
    }

    public static float getReentry(int i) {
      for (int i2 = 0; sParameters[i2][0] > 0; i2++) {
        float[] fArr = sDynamicParameters[i2];
        if (fArr[0] >= i) {
          return fArr[IDX_DYNAMIC_REENTRY];
        }
      }
      return -1.0f;
    }

    public static int getQuickReclaimDefaultThreshold(int i) {
      int i2 = 0;
      while (true) {
        int[] iArr = sParameters[i2];
        int i3 = iArr[0];
        if (i3 <= 0) {
          return -1;
        }
        if (i3 >= i) {
          return iArr[5];
        }
        i2++;
      }
    }

    public static int getDeviceIdleAppThreshold(int i) {
      int i2 = 0;
      while (true) {
        int[] iArr = sParameters[i2];
        int i3 = iArr[0];
        if (i3 <= 0) {
          return -1;
        }
        if (i3 >= i) {
          return iArr[6];
        }
        i2++;
      }
    }

    public static int getDeviceIdleNativeThreshold(int i) {
      int i2 = 0;
      while (true) {
        int[] iArr = sParameters[i2];
        int i3 = iArr[0];
        if (i3 <= 0) {
          return -1;
        }
        if (i3 >= i) {
          return iArr[7];
        }
        i2++;
      }
    }

    public static int getAlwaysRunningProcQuota(int i) {
      int i2 = 0;
      while (true) {
        int[] iArr = sParameters[i2];
        int i3 = iArr[0];
        if (i3 <= 0) {
          return -1;
        }
        if (i3 >= i) {
          return iArr[8];
        }
        i2++;
      }
    }
  }

  public class DynamicFreeMem {
    public long PSI_MEM_SOME_THRESHOLD_MIN;
    public long PSI_MEN_SOME_THRESHOLD_MAX;
    public float mReentry;
    public ReentryCounter mReentryCounter;
    public int ramSizeGb;
    public int mTriggerCnt = 0;
    public int mKeepCnt = 0;

    public DynamicFreeMem() {
      int ramSizeGb = ChimeraCommonUtil.getRamSizeGb();
      this.ramSizeGb = ramSizeGb;
      this.mReentry = DefaultParameters.getReentry(ramSizeGb);
      this.PSI_MEM_SOME_THRESHOLD_MIN = 0L;
      this.PSI_MEN_SOME_THRESHOLD_MAX = 0L;
      if (this.ramSizeGb < 6) {
        this.PSI_MEM_SOME_THRESHOLD_MIN =
            Integer.parseInt(SystemProperties.get("ro.slmk.psi_medium", "100")) * 1000;
        this.PSI_MEN_SOME_THRESHOLD_MAX =
            Integer.parseInt(SystemProperties.get("ro.slmk.psi_critical", "150")) * 1000;
      } else {
        this.PSI_MEM_SOME_THRESHOLD_MIN =
            Integer.parseInt(SystemProperties.get("ro.slmk.psi_medium", "60")) * 1000;
        this.PSI_MEN_SOME_THRESHOLD_MAX =
            Integer.parseInt(SystemProperties.get("ro.slmk.psi_critical", "100")) * 1000;
      }
      ReentryCounter reentryCounter = new ReentryCounter(ChimeraStrategy.this.mSystemRepository);
      this.mReentryCounter = reentryCounter;
      reentryCounter.setTargetReentryCount(this.mReentry);
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x0065, code lost:

       if (r4 > r6) goto L19;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public long updateFreeMem(long j) {
      if (this.mReentryCounter.isReentryEnabled()
          && this.mReentryCounter.getReentry() != DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
        ArrayList recentAppInfo = ChimeraStrategy.this.mRecentAppManager.getRecentAppInfo();
        long longValue = ((Long) recentAppInfo.get(4)).longValue();
        if (longValue > 0) {
          float reentry = this.mReentryCounter.getReentry();
          int i = 2;
          int i2 =
              longValue < this.PSI_MEM_SOME_THRESHOLD_MIN
                  ? 0
                  : longValue < this.PSI_MEN_SOME_THRESHOLD_MAX ? 1 : 2;
          if (i2 == 0) {
            this.mKeepCnt = 0;
            float f = this.mReentry;
            if (reentry >= f) {
              if (reentry <= f * 1.5d) {}
            }
            i = -1;
          } else if (i2 == 1) {
            float f2 = this.mReentry;
            if (reentry < f2) {
              int i3 = this.mKeepCnt + 1;
              this.mKeepCnt = i3;
              if (i3 > 8) {
                this.mKeepCnt = 0;
              }
            } else if (reentry > f2) {
              this.mKeepCnt = 0;
              i = -1;
            }
            i = 0;
          } else {
            if (reentry < this.mReentry) {
              int i4 = this.mKeepCnt + 1;
              this.mKeepCnt = i4;
              if (i4 > 5) {
                this.mKeepCnt = 0;
              } else {
                i = 0;
              }
            } else {
              i = 1;
            }
            if (longValue > this.PSI_MEN_SOME_THRESHOLD_MAX * 2) {
              i = 1;
            }
          }
          ChimeraStrategy.this.mMemFreeTarget += i * 50 * 1024;
          if (ChimeraStrategy.this.mMemFreeTarget
              > ((ChimeraCommonUtil.getRamSizeGb() * 1024) * 1024) / 3) {
            ChimeraStrategy.this.mSystemRepository.logDebug(
                "ChimeraStrategy", "over max free target");
            ChimeraStrategy.this.mMemFreeTarget =
                ((ChimeraCommonUtil.getRamSizeGb() * 1024) * 1024) / 3;
          } else if (ChimeraStrategy.this.mMemFreeTarget
              < ((ChimeraCommonUtil.getRamSizeGb() * 1024) * 1024) / 8) {
            ChimeraStrategy.this.mSystemRepository.logDebug(
                "ChimeraStrategy", "less min free target");
            ChimeraStrategy.this.mMemFreeTarget =
                ((ChimeraCommonUtil.getRamSizeGb() * 1024) * 1024) / 8;
          }
          ChimeraStrategy.this.mSystemRepository.logDebug(
              "ChimeraStrategy",
              "reentry: "
                  + reentry
                  + " memDelta: "
                  + i
                  + " psiLevel: "
                  + i2
                  + "  avgPsiSomeTotal: "
                  + longValue
                  + " full Total:"
                  + recentAppInfo.get(3)
                  + " New free target memory: "
                  + ChimeraStrategy.this.mMemFreeTarget);
        }
      }
      return ChimeraStrategy.this.mMemFreeTarget;
    }

    public final void setThresholdMin(long j) {
      this.PSI_MEM_SOME_THRESHOLD_MIN = j;
    }

    public final void setThresholdMax(long j) {
      this.PSI_MEN_SOME_THRESHOLD_MAX = j;
    }

    public final void setReentry(float f) {
      this.mReentry = f;
      this.mReentryCounter.setTargetReentryCount(f);
    }
  }

  public ChimeraStrategy(
      ChimeraRecentAppManager chimeraRecentAppManager,
      SystemRepository systemRepository,
      SettingRepository settingRepository) {
    this.mRecentAppManager = chimeraRecentAppManager;
    this.mSystemRepository = systemRepository;
    this.mSettingRepository = settingRepository;
    initializeDefaultParameters();
  }

  public final void initializeDefaultParameters() {
    int ramSizeGb = ChimeraCommonUtil.getRamSizeGb();
    updateDefaultParametersIfExist(ramSizeGb);
    long targetFree = DefaultParameters.getTargetFree(ramSizeGb) * 1024;
    this.mMemFreeTarget = targetFree;
    this.mFixedMemFreeTarget = targetFree;
    this.mProtectedCountOnLmkdTrigger =
        SystemProperties.getInt(
            "persist.config.chimera.protected_count_on_lmkd",
            DefaultParameters.getProtectedCountOnLmkdTrigger(ramSizeGb));
    int i =
        SystemProperties.getInt(
            "persist.config.chimera.protected_count_on_home",
            DefaultParameters.getProtectedCountOnHomeTrigger(ramSizeGb));
    this.mProtectedCountOnHomeTrigger = i;
    if (this.mMemFreeTarget < 0 || i < 0 || this.mProtectedCountOnLmkdTrigger < 0) {
      this.mMemFreeTarget = DefaultParameters.UNKNOWN_GB_TARGET_FREE;
      this.mProtectedCountOnHomeTrigger = DefaultParameters.UNKNOWN_GB_PROTECTED_COUNT_ON_HOME;
      this.mProtectedCountOnLmkdTrigger = DefaultParameters.UNKNOWN_GB_PROTECTED_COUNT_ON_LMKD;
    }
    this.mProtectedCountOnDynamic = (int) DefaultParameters.getDynamicProtectedCount(ramSizeGb);
    this.mQuickReclaimDefaultThreshold =
        DefaultParameters.getQuickReclaimDefaultThreshold(ramSizeGb);
    this.mDeviceIdleAppThreshold = DefaultParameters.getDeviceIdleAppThreshold(ramSizeGb);
    this.mDeviceIdleNativeThreshold = DefaultParameters.getDeviceIdleNativeThreshold(ramSizeGb);
    this.mAlwaysRunningProcessQuota = DefaultParameters.getAlwaysRunningProcQuota(ramSizeGb);
    this.mSystemRepository.logDebug(
        "ChimeraStrategy",
        "ChimeraStrategy() - ramSizeGb: "
            + ramSizeGb
            + " mMemFreeTarget: "
            + this.mMemFreeTarget
            + " mProtectedCountOnLmkdTrigger: "
            + this.mProtectedCountOnLmkdTrigger
            + " mProtectedCountOnHomeTrigger: "
            + this.mProtectedCountOnHomeTrigger
            + " mEnableDynamicFreeMem: "
            + isEnableDynamicFreeMem()
            + " mProtectedCountOnDynamic: "
            + this.mProtectedCountOnDynamic
            + " mQuickReclaimDefaultThreshold: "
            + this.mQuickReclaimDefaultThreshold
            + " mDeviceIdleAppThreshold: "
            + this.mDeviceIdleAppThreshold
            + " mDeviceIdleNativeThreshold: "
            + this.mDeviceIdleNativeThreshold
            + " mAlwaysRunningProcessQuota: "
            + this.mAlwaysRunningProcessQuota);
  }

  public final void updateDefaultParametersIfExist(int i) {
    int i2;
    int i3 = 0;
    int i4 = 0;
    while (true) {
      if (DefaultParameters.sParameters[i4][0] <= 0) {
        i2 = -1;
        break;
      } else {
        if (DefaultParameters.sParameters[i4][0] >= i) {
          i2 = DefaultParameters.sParameters[i4][0];
          i3 = i4;
          break;
        }
        i4++;
      }
    }
    if (i2 != -1) {
      String format = String.format("ro.slmk.chimera_strategy_%dgb", Integer.valueOf(i2));
      String str = SystemProperties.get(format, "");
      this.mSystemRepository.logDebug(
          "ChimeraStrategy", "updateDefaultParameters > " + format + " : " + str);
      if (TextUtils.isEmpty(str)) {
        return;
      }
      String[] split = str.split(",");
      if (split.length <= 3) {
        Log.e("ChimeraStrategy", "Chimera parameter mismatched");
        return;
      }
      for (int i5 = 1; i5 <= 3; i5++) {
        try {
          DefaultParameters.sParameters[i3][i5] = Integer.parseInt(split[i5 - 1].trim());
        } catch (Exception e) {
          Log.e("ChimeraStrategy", "Error while updating default : " + e.getMessage());
        }
      }
    }
  }

  public int getProtectedCountOnLmkdTrigger() {
    if (isEnableDynamicFreeMem()) {
      return this.mProtectedCountOnDynamic;
    }
    return this.mProtectedCountOnLmkdTrigger;
  }

  public int getProtectedCountOnHomeTrigger() {
    if (isEnableDynamicFreeMem()) {
      return this.mProtectedCountOnDynamic;
    }
    return this.mProtectedCountOnHomeTrigger;
  }

  public long getFreeMemTarget(long j) {
    DynamicFreeMem dynamicFreeMem;
    if (!isEnableDynamicFreeMem() || (dynamicFreeMem = this.mDynamicFreeMem) == null) {
      return this.mMemFreeTarget;
    }
    long updateFreeMem = dynamicFreeMem.updateFreeMem(j);
    this.mMemFreeTarget = updateFreeMem;
    return updateFreeMem;
  }

  public boolean isEnableDynamicFreeMem() {
    return this.mSettingRepository.isDynamicTargetFreeEnabled();
  }

  public void setTargetMem(long j) {
    this.mMemFreeTarget = j;
    this.mSettingRepository.enableDynamicTargetFree(false);
  }

  public int getQuickReclaimDefaultThreshold() {
    return this.mQuickReclaimDefaultThreshold;
  }

  public int getDeviceIdleAppThreshold() {
    return this.mDeviceIdleAppThreshold;
  }

  public int getDeviceIdleNativeThreshold() {
    return this.mDeviceIdleNativeThreshold;
  }

  public int getAlwaysRunningProcQuota() {
    return this.mAlwaysRunningProcessQuota;
  }

  public void dump(PrintWriter printWriter, String[] strArr) {
    ReentryCounter reentryCounter;
    if (strArr == null || strArr.length == 0) {
      return;
    }
    if ("-a".equals(strArr[0])) {
      dumpInfo(printWriter);
      return;
    }
    if (strArr.length > 0) {
      String str = strArr[0];
      if (str.equals("info")) {
        dumpInfo(printWriter);
        return;
      }
      if (str.equals("mem") && strArr.length > 1) {
        try {
          long parseLong = Long.parseLong(strArr[1]);
          setTargetMem(parseLong);
          printWriter.println("Target mem : " + parseLong);
          return;
        } catch (NumberFormatException e) {
          printWriter.println(e.getMessage());
          return;
        }
      }
      if (str.equals("set_protected_count_on_lmkd") && strArr.length > 1) {
        try {
          this.mProtectedCountOnLmkdTrigger = Integer.parseInt(strArr[1]);
        } catch (NumberFormatException e2) {
          printWriter.println(e2.getMessage());
        }
        printWriter.println("ProtectedCount On Lmkd : " + this.mProtectedCountOnLmkdTrigger);
        return;
      }
      if (str.equals("set_protected_count_on_home") && strArr.length > 1) {
        try {
          this.mProtectedCountOnHomeTrigger = Integer.parseInt(strArr[1]);
        } catch (NumberFormatException e3) {
          printWriter.println(e3.getMessage());
        }
        printWriter.println("ProtectedCount On Home : " + this.mProtectedCountOnHomeTrigger);
        return;
      }
      if (str.equals("enable_dynamic")) {
        enableDynamicTargetFree();
        printWriter.println("enable dynamic memfreetarget");
        return;
      }
      if (str.equals("dynamic_min") && strArr.length > 1) {
        if (this.mDynamicFreeMem != null) {
          long parseLong2 = Long.parseLong(strArr[1]);
          this.mDynamicFreeMem.setThresholdMin(parseLong2);
          printWriter.println("set dynamic threshold min: " + parseLong2);
          return;
        }
        return;
      }
      if (str.equals("dynamic_max") && strArr.length > 1) {
        if (this.mDynamicFreeMem != null) {
          long parseLong3 = Long.parseLong(strArr[1]);
          this.mDynamicFreeMem.setThresholdMax(parseLong3);
          printWriter.println("set dynamic threshold max: " + parseLong3);
          return;
        }
        return;
      }
      if (str.equals("get_reentry")) {
        DynamicFreeMem dynamicFreeMem = this.mDynamicFreeMem;
        if (dynamicFreeMem == null || (reentryCounter = dynamicFreeMem.mReentryCounter) == null) {
          return;
        }
        printWriter.println("get reentry: " + reentryCounter.getReentry());
        return;
      }
      if (!str.equals("set_reentry") || this.mDynamicFreeMem == null) {
        return;
      }
      float parseFloat = Float.parseFloat(strArr[1]);
      printWriter.println("set reentry: " + parseFloat);
      this.mDynamicFreeMem.setReentry(parseFloat);
    }
  }

  public final void enableDynamicTargetFree() {
    this.mSettingRepository.enableDynamicTargetFree(true);
    if (this.mDynamicFreeMem == null) {
      this.mDynamicFreeMem = new DynamicFreeMem();
    }
  }

  public void dumpInfo(PrintWriter printWriter) {
    printWriter.println("UseDynamicFreeMem: " + isEnableDynamicFreeMem());
    printWriter.println("MemFreeTarget: " + this.mMemFreeTarget);
    printWriter.println("ProtectedCountOnLmkdTrigger: " + this.mProtectedCountOnLmkdTrigger);
    printWriter.println("ProtectedCountOnHomeTrigger: " + this.mProtectedCountOnHomeTrigger);
  }
}
