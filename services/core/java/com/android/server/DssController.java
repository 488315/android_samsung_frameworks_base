package com.android.server;

import android.app.GameManagerInternal;
import android.app.WindowConfiguration;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Binder;
import android.util.MergedConfiguration;
import android.util.Slog;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public class DssController {
  static final String TAG = "DssController";
  private static DssController sDssController;
  private final int DEFAULT_GAME_SIOP_LEVEL = 0;
  private final int DEFAULT_CPU_LEVEL = 0;
  private final int DEFAULT_GPU_LEVEL = 0;
  private final float DEFAULT_SCALE = 1.0f;
  private final float FLOAT_EPSILON = 0.001f;
  private final HashMap mAllowList = new HashMap();
  private final HashMap mRunningPackages = new HashMap();
  private GameManagerInternal mGameManager = null;

  public static DssController getService() {
    if (sDssController == null) {
      sDssController = new DssController();
    }
    return sDssController;
  }

  public class DssAppDate {
    public float mScale = 1.0f;
    public int mGameSiopLevel = 0;
    public int mCpuLevel = 0;
    public int mGpuLevel = 0;

    public DssAppDate() {}

    public void addPackage(float f) {
      this.mScale = f;
      this.mGameSiopLevel = 0;
      this.mCpuLevel = 0;
      this.mGpuLevel = 0;
    }

    public void addPackage(float f, int i) {
      this.mScale = f;
      this.mGameSiopLevel = i;
    }

    public void addPackage(float f, String str) {
      this.mScale = f;
      String[] split = str.split(",");
      this.mCpuLevel = Integer.parseInt(split[0]);
      this.mGpuLevel = Integer.parseInt(split[1]);
    }
  }

  public class RunningPackage {
    public float mDssScale;
    public ArrayList mFixedSizeSurfaces;
    public ArrayList mPids;

    public RunningPackage(int i, float f) {
      ArrayList arrayList = new ArrayList();
      this.mPids = arrayList;
      arrayList.add(Integer.valueOf(i));
      this.mFixedSizeSurfaces = new ArrayList();
      this.mDssScale = f;
    }
  }

  public class PairConfiguration {
    public Rect mAppGlobal;
    public Rect mAppOverride;
    public Consumer mBound;

    public PairConfiguration(Rect rect, Rect rect2, Consumer consumer) {
      this.mAppGlobal = rect;
      this.mAppOverride = rect2;
      this.mBound = consumer;
    }
  }

  public abstract class Tools {
    public static int scaleDpiValue(int i, float f) {
      return (int) ((i * f) + 0.5f);
    }

    public static void applyDssToConfiguration(Configuration configuration, final float f) {
      int i = configuration.densityDpi;
      if (i != 0) {
        configuration.densityDpi = scaleDpiValue(i, f);
      }
      Consumer consumer = new Consumer() { // from class:
            // com.android.server.DssController$Tools$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
              DssController.Tools.lambda$applyDssToConfiguration$0(f, (Rect) obj);
            }
          };
      WindowConfiguration windowConfiguration = configuration.windowConfiguration;
      consumer.accept(windowConfiguration.getAppBounds());
      consumer.accept(windowConfiguration.getMaxBounds());
      consumer.accept(windowConfiguration.getBounds());
    }

    public static /* synthetic */ void lambda$applyDssToConfiguration$0(float f, Rect rect) {
      if (rect != null) {
        applyScaleToCompatFrame(rect, f);
      }
    }

    public static void applyDssToMergedConfiguration(
        MergedConfiguration mergedConfiguration, final float f) {
      int i = mergedConfiguration.getGlobalConfiguration().densityDpi;
      int i2 = mergedConfiguration.getOverrideConfiguration().densityDpi;
      Configuration configuration =
          new Configuration(mergedConfiguration.getOverrideConfiguration());
      if (i2 != 0) {
        configuration.densityDpi = scaleDpiValue(i2, f);
      } else if (i != 0) {
        configuration.densityDpi = scaleDpiValue(i, f);
      }
      WindowConfiguration windowConfiguration =
          mergedConfiguration.getGlobalConfiguration().windowConfiguration;
      WindowConfiguration windowConfiguration2 =
          mergedConfiguration.getOverrideConfiguration().windowConfiguration;
      final WindowConfiguration windowConfiguration3 = configuration.windowConfiguration;
      PairConfiguration pairConfiguration =
          new PairConfiguration(
              windowConfiguration.getAppBounds(),
              windowConfiguration2.getAppBounds(),
              new Consumer() { // from class:
                // com.android.server.DssController$Tools$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                  windowConfiguration3.setAppBounds((Rect) obj);
                }
              });
      PairConfiguration pairConfiguration2 =
          new PairConfiguration(
              windowConfiguration.getMaxBounds(),
              windowConfiguration2.getMaxBounds(),
              new Consumer() { // from class:
                // com.android.server.DssController$Tools$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                  windowConfiguration3.setMaxBounds((Rect) obj);
                }
              });
      PairConfiguration pairConfiguration3 =
          new PairConfiguration(
              windowConfiguration.getBounds(),
              windowConfiguration2.getBounds(),
              new Consumer() { // from class:
                // com.android.server.DssController$Tools$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                  windowConfiguration3.setBounds((Rect) obj);
                }
              });
      ArrayList arrayList = new ArrayList();
      arrayList.add(pairConfiguration);
      arrayList.add(pairConfiguration2);
      arrayList.add(pairConfiguration3);
      arrayList.forEach(
          new Consumer() { // from class:
            // com.android.server.DssController$Tools$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
              DssController.Tools.lambda$applyDssToMergedConfiguration$4(
                  f, (DssController.PairConfiguration) obj);
            }
          });
      mergedConfiguration.setOverrideConfiguration(configuration);
    }

    public static /* synthetic */ void lambda$applyDssToMergedConfiguration$4(
        float f, PairConfiguration pairConfiguration) {
      Rect rect;
      Rect rect2 = pairConfiguration.mAppGlobal;
      Rect rect3 = pairConfiguration.mAppOverride;
      if (rect3 != null) {
        rect = new Rect(rect3);
      } else {
        rect = rect2 != null ? new Rect(rect2) : null;
      }
      if (rect != null) {
        applyScaleToCompatFrame(rect, f);
        pairConfiguration.mBound.accept(rect);
      }
    }

    public static void applyScaleToCompatFrame(Rect rect, float f) {
      int i = -rect.left;
      int i2 = -rect.top;
      rect.offset(i, i2);
      rect.scale(f);
      rect.offset(-((int) ((i * f) + 0.5f)), -((int) ((i2 * f) + 0.5f)));
    }
  }

  public synchronized float getDssForPackage(String str) {
    HashMap hashMap;
    hashMap = this.mAllowList;
    return (hashMap == null || !hashMap.containsKey(str))
        ? 1.0f
        : ((DssAppDate) this.mAllowList.get(str)).mScale;
  }

  public synchronized void setDssForPackage(String str, float f) {
    if (Math.abs(f - 1.0f) < 0.001f) {
      removePackage(str);
    } else {
      addPackageData(str, f);
    }
  }

  public synchronized int getGameSiopLevel(String str) {
    if (!this.mAllowList.containsKey(str)) {
      return 0;
    }
    return ((DssAppDate) this.mAllowList.get(str)).mGameSiopLevel;
  }

  public synchronized String getGameSiopLevelString(String str) {
    if (!this.mAllowList.containsKey(str)) {
      return "0,0";
    }
    DssAppDate dssAppDate = (DssAppDate) this.mAllowList.get(str);
    return dssAppDate.mCpuLevel + "," + dssAppDate.mGpuLevel;
  }

  public synchronized void addPackageData(String str, float f) {
    String str2;
    DssAppDate dssAppDate = new DssAppDate();
    dssAppDate.addPackage(f);
    this.mAllowList.put(str, dssAppDate);
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      if (this.mGameManager == null) {
        this.mGameManager =
            (GameManagerInternal) LocalServices.getService(GameManagerInternal.class);
      }
      GameManagerInternal gameManagerInternal = this.mGameManager;
      if (gameManagerInternal != null) {
        gameManagerInternal.updateResolutionScalingFactorInternal(str, f);
        StringBuilder sb = new StringBuilder();
        sb.append("Google Screen Compat ");
        if (f != 1.0f) {
          str2 = "on with scalingFactor = " + f;
        } else {
          str2 = "off";
        }
        sb.append(str2);
        sb.append(" for package: ");
        sb.append(str);
        Slog.d(TAG, sb.toString());
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public synchronized void addPackageData(String str, float f, int i) {
    DssAppDate dssAppDate = new DssAppDate();
    dssAppDate.addPackage(f, i);
    this.mAllowList.put(str, dssAppDate);
  }

  public synchronized void addPackageData(String str, float f, String str2) {
    DssAppDate dssAppDate = new DssAppDate();
    dssAppDate.addPackage(f, str2);
    this.mAllowList.put(str, dssAppDate);
  }

  public synchronized void removePackage(String str) {
    if (this.mAllowList.containsKey(str)) {
      this.mAllowList.remove(str);
    }
  }

  public synchronized void showAllDSSInfo() {
    for (Map.Entry entry : this.mAllowList.entrySet()) {
      Slog.i(
          TAG,
          "DSS PackageName : "
              + ((String) entry.getKey())
              + ",  Scale : "
              + ((DssAppDate) entry.getValue()).mScale
              + ", GameSiopLevel :"
              + ((DssAppDate) entry.getValue()).mGameSiopLevel
              + ", CpuLevel :"
              + ((DssAppDate) entry.getValue()).mCpuLevel
              + ", GpuLevel :"
              + ((DssAppDate) entry.getValue()).mGpuLevel);
      if (this.mRunningPackages.containsKey(entry.getKey())) {
        RunningPackage runningPackage =
            (RunningPackage) this.mRunningPackages.get((String) entry.getKey());
        String valueOf = String.valueOf(runningPackage.mPids.get(0));
        for (int i = 1; i < runningPackage.mPids.size(); i++) {
          valueOf = (valueOf + ", ") + String.valueOf(runningPackage.mPids.get(i));
        }
        String str = "";
        for (int i2 = 0; i2 < runningPackage.mFixedSizeSurfaces.size(); i2++) {
          if (i2 != 0) {
            str = str + ", ";
          }
          str = str + ((String) runningPackage.mFixedSizeSurfaces.get(i2));
        }
        StringBuilder sb = new StringBuilder();
        sb.append("\tRunning Package - Scale: ");
        sb.append(runningPackage.mDssScale);
        sb.append(",\tPIDs: {");
        sb.append(valueOf);
        sb.append("}");
        sb.append(
            runningPackage.mFixedSizeSurfaces.isEmpty()
                ? ""
                : ",\tFixed Size Surfaces: {" + str + "}");
        Slog.i(TAG, sb.toString());
      }
    }
  }

  public synchronized StringBuilder getAllowList() {
    StringBuilder sb;
    sb = new StringBuilder();
    ArrayList arrayList = new ArrayList();
    ArrayList arrayList2 = new ArrayList();
    for (Map.Entry entry : this.mAllowList.entrySet()) {
      if (Math.abs(((DssAppDate) entry.getValue()).mScale - 0.5f) < 0.001f) {
        arrayList.add((String) entry.getKey());
      } else if (Math.abs(((DssAppDate) entry.getValue()).mScale - 0.75f) < 0.001f) {
        arrayList2.add((String) entry.getKey());
      }
    }
    sb.append("DSS 0.5 Group\n");
    sb.append("=============\n");
    Iterator it = arrayList.iterator();
    while (it.hasNext()) {
      sb.append((String) it.next());
      sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
    }
    sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
    sb.append("DSS 0.75 Group\n");
    sb.append("=============\n");
    Iterator it2 = arrayList2.iterator();
    while (it2.hasNext()) {
      sb.append((String) it2.next());
      sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
    }
    sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
    return sb;
  }

  private String pidToPkg(int i) {
    for (Map.Entry entry : this.mRunningPackages.entrySet()) {
      if (((RunningPackage) entry.getValue()).mPids.contains(Integer.valueOf(i))) {
        return (String) entry.getKey();
      }
    }
    return null;
  }

  public synchronized boolean isScaledApp(int i) {
    return isScaledApp(pidToPkg(i));
  }

  public synchronized boolean isScaledApp(String str) {
    if (this.mRunningPackages.containsKey(str)) {
      return ((RunningPackage) this.mRunningPackages.get(str)).mDssScale != 1.0f;
    }
    return false;
  }

  public synchronized float getScalingFactor(int i) {
    return getScalingFactor(pidToPkg(i));
  }

  public synchronized float getScalingFactor(String str) {
    return this.mRunningPackages.containsKey(str)
        ? ((RunningPackage) this.mRunningPackages.get(str)).mDssScale
        : 1.0f;
  }

  public synchronized boolean usesOwnResolution(String str) {
    boolean z;
    RunningPackage runningPackage = (RunningPackage) this.mRunningPackages.get(str);
    if (runningPackage != null) {
      z = runningPackage.mFixedSizeSurfaces.isEmpty() ? false : true;
    }
    return z;
  }

  public synchronized void setUsesOwnResolution(int i, String str, boolean z) {
    String pidToPkg = pidToPkg(i);
    if (!this.mRunningPackages.containsKey(pidToPkg)) {
      Slog.wtf(TAG, "Trying to setUsesOwnResolution(" + z + ") for a package that isn't running!");
      return;
    }
    ArrayList arrayList = ((RunningPackage) this.mRunningPackages.get(pidToPkg)).mFixedSizeSurfaces;
    if (z) {
      arrayList.add(str);
    } else {
      arrayList.remove(str);
    }
  }

  public synchronized float onApplicationStarted(String str, int i, boolean z) {
    float f;
    if (this.mRunningPackages.containsKey(str)) {
      RunningPackage runningPackage = (RunningPackage) this.mRunningPackages.get(str);
      runningPackage.mPids.add(Integer.valueOf(i));
      f = runningPackage.mDssScale;
    } else {
      HashMap hashMap = this.mAllowList;
      float f2 = 1.0f;
      if (hashMap != null && hashMap.containsKey(str) && !z) {
        f2 = ((DssAppDate) this.mAllowList.get(str)).mScale;
      }
      this.mRunningPackages.put(str, new RunningPackage(i, f2));
      f = f2;
    }
    return f;
  }

  public synchronized void onApplicationStopped(String str, int i) {
    RunningPackage runningPackage = (RunningPackage) this.mRunningPackages.get(str);
    if (runningPackage == null) {
      return;
    }
    if (runningPackage.mPids.size() <= 1) {
      if (runningPackage.mPids.size() == 1
          && ((Integer) runningPackage.mPids.get(0)).intValue() != i) {
        Slog.wtf(TAG, "Stopped pid does not match the started pid recorded by DssController!");
      }
      this.mRunningPackages.remove(str);
    } else {
      runningPackage.mPids.remove(Integer.valueOf(i));
    }
  }

  public Configuration createScaledConfiguration(Configuration configuration, String str) {
    if (!isScaledApp(str)) {
      return configuration;
    }
    float scalingFactor = getScalingFactor(str);
    Configuration configuration2 = new Configuration(configuration);
    Tools.applyDssToConfiguration(configuration2, scalingFactor);
    return configuration2;
  }

  public void scaleExistingConfiguration(Configuration configuration, String str) {
    if (isScaledApp(str)) {
      Tools.applyDssToConfiguration(configuration, getScalingFactor(str));
    }
  }

  public void scaleExistingMergedConfiguration(
      MergedConfiguration mergedConfiguration, String str) {
    if (isScaledApp(str)) {
      Tools.applyDssToMergedConfiguration(mergedConfiguration, getScalingFactor(str));
    }
  }
}
