package com.android.server.wm;

import android.content.pm.LauncherActivityInfo;
import android.content.pm.LauncherApps;
import android.os.UserHandle;
import android.util.Slog;
import com.android.server.display.DisplayPowerController2;
import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.server.util.SafetySystemService;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public class BoundsCompatStatusLogger implements CoreSaStatusLoggingService.CoreSaStatusLogger {
  public static final boolean DEBUG = CoreRune.SAFE_DEBUG;
  public static final String TAG = "BoundsCompatStatusLogger";
  public ActivityTaskManagerService mAtmService;
  public final HashMap mSettings;

  public abstract class LazyHolder {
    public static final BoundsCompatStatusLogger sLogger = new BoundsCompatStatusLogger();
  }

  public interface StatusCollector {
    default boolean collectIfNeededLocked(int i, String str) {
      return true;
    }

    void initializeLocked();
  }

  public static BoundsCompatStatusLogger get() {
    return LazyHolder.sLogger;
  }

  /* JADX WARN: Removed duplicated region for block: B:11:0x0028  */
  /* JADX WARN: Removed duplicated region for block: B:12:0x0029 A[Catch: all -> 0x0032, TRY_LEAVE, TryCatch #0 {all -> 0x0032, blocks: (B:6:0x000e, B:12:0x0029, B:16:0x001a), top: B:5:0x000e }] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public static boolean executeShellCommand(String str, String[] strArr, PrintWriter printWriter) {
    char c;
    if (DEBUG && "-bcsl".equals(str)) {
      try {
        String str2 = strArr[0];
        if (str2.hashCode() == 317649683 && str2.equals("maintenance")) {
          c = 0;
          if (c != 0) {
            get().onStatusLogging();
            return true;
          }
        }
        c = 65535;
        if (c != 0) {}
      } catch (Throwable unused) {
        Slog.e(TAG, "Failed to execute command=" + str + ", opt=" + strArr[0]);
      }
    }
    return false;
  }

  public BoundsCompatStatusLogger() {
    this.mSettings = new HashMap();
  }

  public void onSystemReady(ActivityTaskManagerService activityTaskManagerService) {
    this.mAtmService = activityTaskManagerService;
    CoreSaStatusLoggingService.registerCoreSaStatusLogger(this);
  }

  @Override // com.android.server.wm.CoreSaStatusLoggingService.CoreSaStatusLogger
  public String getName() {
    return TAG;
  }

  /* JADX WARN: Multi-variable type inference failed */
  @Override // com.android.server.wm.CoreSaStatusLoggingService.CoreSaStatusLogger
  public void onStatusLogging() {
    try {
      ArrayList arrayList = new ArrayList();
      Object[] objArr = 0;
      Object[] objArr2 = 0;
      if (CoreRune.FW_FIXED_ASPECT_RATIO_MODE) {
        arrayList.add(new FixedAspectRatioStatusCollector());
      }
      if (CoreRune.FW_ORIENTATION_CONTROL_WITH_ASPECT_RATIO) {
        arrayList.add(new OrientationControlStatusCollector());
      }
      if (CoreRune.FW_BOUNDS_COMPAT_ALIGNMENT_CONTROL) {
        arrayList.add(new AlignmentStatusCollector());
      }
      collectAndSendStatus(arrayList);
    } catch (Throwable th) {
      Slog.e(TAG, "Failed to logBoundsCompatStatus", th);
    }
  }

  public final void collectAndSendStatus(List list) {
    LauncherApps launcherApps;
    if (list.isEmpty()
        || (launcherApps = (LauncherApps) SafetySystemService.getSystemService(LauncherApps.class))
            == null) {
      return;
    }
    int userId = this.mAtmService.mContext.getUserId();
    List<LauncherActivityInfo> activityList =
        launcherApps.getActivityList(null, UserHandle.of(userId));
    synchronized (this.mAtmService.mGlobalLockWithoutBoost) {
      this.mSettings.clear();
      Iterator it = list.iterator();
      while (it.hasNext()) {
        StatusCollector statusCollector = (StatusCollector) it.next();
        statusCollector.initializeLocked();
        Iterator<LauncherActivityInfo> it2 = activityList.iterator();
        while (it2.hasNext()
            && !statusCollector.collectIfNeededLocked(
                userId, it2.next().getApplicationInfo().packageName)) {}
      }
      if (!this.mSettings.isEmpty()) {
        HashMap hashMap = new HashMap();
        for (Map.Entry entry : this.mSettings.entrySet()) {
          String str = (String) entry.getKey();
          String sb = ((StringBuilder) entry.getValue()).toString();
          if (DEBUG) {
            Slog.d(TAG, "collectAndSendStatus: settingsId=" + str + ", value=" + sb);
          }
          hashMap.put(str, sb);
        }
        CoreSaLogger.sendSaLoggingBroadcastForBasicSetting(this.mAtmService.mContext, hashMap);
      }
    }
  }

  public final StringBuilder createSettingLocked(String str) {
    StringBuilder sb = new StringBuilder();
    this.mSettings.put(str, sb);
    return sb;
  }

  public final StringBuilder getSettingLocked(String str) {
    StringBuilder sb = (StringBuilder) this.mSettings.get(str);
    return sb != null ? sb : createSettingLocked(str);
  }

  public final void putSettingLocked(String str, String str2) {
    StringBuilder settingLocked = getSettingLocked(str);
    if (!settingLocked.toString().isEmpty()) {
      settingLocked.append(", ");
    }
    settingLocked.append(str2);
  }

  public class FixedAspectRatioStatusCollector implements StatusCollector {
    public FixedAspectRatioStatusCollector() {}

    @Override // com.android.server.wm.BoundsCompatStatusLogger.StatusCollector
    public void initializeLocked() {
      BoundsCompatStatusLogger.this.createSettingLocked("519402");
      BoundsCompatStatusLogger.this.createSettingLocked("519403");
    }

    @Override // com.android.server.wm.BoundsCompatStatusLogger.StatusCollector
    public boolean collectIfNeededLocked(int i, String str) {
      float mergedChange =
          BoundsCompatStatusLogger.this.mAtmService.mExt.mFixedAspectRatioController
              .getMergedChange(i, str);
      if (mergedChange == -1.0f || mergedChange == DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
        return false;
      }
      if (mergedChange == 1.7777778f) {
        BoundsCompatStatusLogger.this.putSettingLocked("519402", str);
        return false;
      }
      if (mergedChange != 1.3333334f) {
        return false;
      }
      BoundsCompatStatusLogger.this.putSettingLocked("519403", str);
      return false;
    }
  }

  public class OrientationControlStatusCollector implements StatusCollector {
    public OrientationControlStatusCollector() {}

    @Override // com.android.server.wm.BoundsCompatStatusLogger.StatusCollector
    public void initializeLocked() {
      BoundsCompatStatusLogger.this.createSettingLocked("LVPA01");
      BoundsCompatStatusLogger.this.createSettingLocked("LVPA02");
    }

    @Override // com.android.server.wm.BoundsCompatStatusLogger.StatusCollector
    public boolean collectIfNeededLocked(int i, String str) {
      int policy =
          BoundsCompatStatusLogger.this.mAtmService.mExt.mOrientationController.getPolicy(i, str);
      if (policy == 7) {
        BoundsCompatStatusLogger.this.putSettingLocked("LVPA02", str);
        return false;
      }
      if (policy != 31) {
        return false;
      }
      BoundsCompatStatusLogger.this.putSettingLocked("LVPA01", str);
      return false;
    }
  }

  public class AlignmentStatusCollector implements StatusCollector {
    public AlignmentStatusCollector() {}

    @Override // com.android.server.wm.BoundsCompatStatusLogger.StatusCollector
    public void initializeLocked() {
      int boundsCompatAlignment =
          BoundsCompatStatusLogger.this.mAtmService.getBoundsCompatAlignment();
      int i = boundsCompatAlignment & 112;
      String str = "Center";
      BoundsCompatStatusLogger.this.putSettingLocked(
          "519306", i == 48 ? "Top" : i == 16 ? "Center" : "Bottom");
      int i2 = boundsCompatAlignment & 7;
      BoundsCompatStatusLogger boundsCompatStatusLogger = BoundsCompatStatusLogger.this;
      if (i2 == 3) {
        str = "Left";
      } else if (i2 != 1) {
        str = "Right";
      }
      boundsCompatStatusLogger.putSettingLocked("519305", str);
    }
  }
}
