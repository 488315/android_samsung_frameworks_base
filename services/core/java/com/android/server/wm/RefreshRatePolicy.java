package com.android.server.wm;

import android.view.Display;
import android.view.DisplayInfo;
import android.view.SurfaceControl;
import com.android.server.display.DisplayPowerController2;
import com.samsung.android.hardware.display.RefreshRateConfig;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes3.dex */
public class RefreshRatePolicy {
  public final Display.Mode mDefaultMode;
  public final DisplayInfo mDisplayInfo;
  public final HighRefreshRateDenylist mHighRefreshRateDenylist;
  public Display.Mode mLowRefreshRateMode;
  public float mMaxSupportedRefreshRate;
  public float mMinSupportedRefreshRate;
  public RefreshRatePolicyLogger mRefreshRatePolicyLogger;
  public final WindowManagerService mWmService;
  public final PackageRefreshRate mNonHighRefreshRatePackages = new PackageRefreshRate();
  public final ConcurrentHashMap mFixedRefreshRatePackages = new ConcurrentHashMap();
  public boolean mRestrictHighRefreshRate = false;
  public AtomicBoolean mReportedRestrictHighRefreshRate = new AtomicBoolean(false);

  public class PackageRefreshRate {
    public final HashMap mPackages = new HashMap();

    public PackageRefreshRate() {}

    public void add(String str, float f, float f2) {
      this.mPackages.put(
          str,
          new SurfaceControl.RefreshRateRange(
              Math.max(RefreshRatePolicy.this.mMinSupportedRefreshRate, f),
              Math.min(RefreshRatePolicy.this.mMaxSupportedRefreshRate, f2)));
    }

    public SurfaceControl.RefreshRateRange get(String str) {
      return (SurfaceControl.RefreshRateRange) this.mPackages.get(str);
    }

    public void remove(String str) {
      this.mPackages.remove(str);
    }
  }

  public RefreshRatePolicy(
      WindowManagerService windowManagerService,
      DisplayInfo displayInfo,
      HighRefreshRateDenylist highRefreshRateDenylist) {
    this.mRefreshRatePolicyLogger = new RefreshRatePolicyLogger();
    this.mDisplayInfo = displayInfo;
    Display.Mode defaultMode = displayInfo.getDefaultMode();
    this.mDefaultMode = defaultMode;
    this.mLowRefreshRateMode = findLowRefreshRateMode(displayInfo, defaultMode);
    this.mHighRefreshRateDenylist = highRefreshRateDenylist;
    this.mWmService = windowManagerService;
    if (CoreRune.FW_VRR_SYSTEM_HISTORY) {
      this.mRefreshRatePolicyLogger = new RefreshRatePolicyLogger();
    }
  }

  public final Display.Mode findLowRefreshRateMode(DisplayInfo displayInfo, Display.Mode mode) {
    float[] defaultRefreshRates = displayInfo.getDefaultRefreshRates();
    float refreshRate = mode.getRefreshRate();
    this.mMinSupportedRefreshRate = refreshRate;
    this.mMaxSupportedRefreshRate = refreshRate;
    for (int length = defaultRefreshRates.length - 1; length >= 0; length--) {
      this.mMinSupportedRefreshRate =
          Math.min(this.mMinSupportedRefreshRate, defaultRefreshRates[length]);
      this.mMaxSupportedRefreshRate =
          Math.max(this.mMaxSupportedRefreshRate, defaultRefreshRates[length]);
      float f = defaultRefreshRates[length];
      if (f >= 60.0f && ((CoreRune.FW_VRR_POLICY && refreshRate < 60.0f) || f < refreshRate)) {
        refreshRate = f;
      }
    }
    return displayInfo.findDefaultModeByRefreshRate(refreshRate);
  }

  public void addRefreshRateRangeForPackage(String str, float f, float f2) {
    this.mNonHighRefreshRatePackages.add(str, f, f2);
    this.mWmService.requestTraversal();
  }

  public void removeRefreshRateRangeForPackage(String str) {
    this.mNonHighRefreshRatePackages.remove(str);
    this.mWmService.requestTraversal();
  }

  public void addFixedRefreshRatePackage(String str, int i) {
    this.mFixedRefreshRatePackages.put(str, Integer.valueOf(i));
    this.mWmService.requestTraversal();
  }

  public void removeFixedRefreshRatePackage(String str) {
    this.mFixedRefreshRatePackages.remove(str);
    this.mWmService.requestTraversal();
  }

  public final float getRefreshRateFromFixedRefreshRatePackages(WindowState windowState) {
    DisplayInfo displayInfo;
    String owningPackage = windowState.getOwningPackage();
    if (!this.mFixedRefreshRatePackages.containsKey(owningPackage)
        || (displayInfo = windowState.getDisplayInfo()) == null) {
      return DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
    }
    int intValue = ((Integer) this.mFixedRefreshRatePackages.get(owningPackage)).intValue();
    for (Display.Mode mode : displayInfo.supportedModes) {
      if (intValue == mode.getModeId()) {
        return mode.getRefreshRate();
      }
    }
    return DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
  }

  public int getPreferredModeId(WindowState windowState) {
    DisplayInfo displayInfo;
    Display.Mode mode;
    int i = windowState.mAttrs.preferredDisplayModeId;
    if (i <= 0) {
      return 0;
    }
    if (windowState.isAnimationRunningSelfOrParent()) {
      Display.Mode[] modeArr = this.mDisplayInfo.supportedModes;
      int length = modeArr.length;
      int i2 = 0;
      while (true) {
        if (i2 >= length) {
          mode = null;
          break;
        }
        mode = modeArr[i2];
        if (i == mode.getModeId()) {
          break;
        }
        i2++;
      }
      if (mode != null) {
        int physicalWidth = mode.getPhysicalWidth();
        int physicalHeight = mode.getPhysicalHeight();
        if ((physicalWidth != this.mDefaultMode.getPhysicalWidth()
                || physicalHeight != this.mDefaultMode.getPhysicalHeight())
            && physicalWidth == this.mDisplayInfo.getNaturalWidth()
            && physicalHeight == this.mDisplayInfo.getNaturalHeight()) {
          return i;
        }
      }
      return 0;
    }
    String owningPackage = CoreRune.FW_VRR_POLICY ? windowState.getOwningPackage() : "";
    if (CoreRune.FW_VRR_FIXED_REFRESH_RATE_PACKAGE
        && this.mFixedRefreshRatePackages.containsKey(owningPackage)) {
      return ((Integer) this.mFixedRefreshRatePackages.get(owningPackage)).intValue();
    }
    if (CoreRune.FW_VRR_LOW_REFRESH_RATE_LIST
        && isFocusedLowRefreshRatePackage(windowState, owningPackage)) {
      return this.mLowRefreshRateMode.getModeId();
    }
    if (CoreRune.FW_VRR_HRR_CHINA_DELTA
        && i != 0
        && isHighRefreshRatePackage(owningPackage)
        && (displayInfo = windowState.getDisplayInfo()) != null) {
      for (Display.Mode mode2 : displayInfo.supportedModes) {
        if (i == mode2.getModeId() && mode2.getRefreshRate() >= 119.99f) {
          return 0;
        }
      }
    }
    return i;
  }

  public int calculatePriority(WindowState windowState) {
    boolean isFocused = windowState.isFocused();
    int preferredModeId = getPreferredModeId(windowState);
    if (!isFocused && preferredModeId > 0) {
      return 2;
    }
    if (isFocused && preferredModeId == 0) {
      return 1;
    }
    return (!isFocused || preferredModeId <= 0) ? -1 : 0;
  }

  public class FrameRateVote {
    public int mCompatibility;
    public float mRefreshRate;

    public FrameRateVote() {
      reset();
    }

    public boolean update(float f, int i) {
      if (refreshRateEquals(f) && this.mCompatibility == i) {
        return false;
      }
      this.mRefreshRate = f;
      this.mCompatibility = i;
      return true;
    }

    public boolean reset() {
      return update(DisplayPowerController2.RATE_FROM_DOZE_TO_ON, 0);
    }

    public boolean equals(Object obj) {
      if (!(obj instanceof FrameRateVote)) {
        return false;
      }
      FrameRateVote frameRateVote = (FrameRateVote) obj;
      return refreshRateEquals(frameRateVote.mRefreshRate)
          && this.mCompatibility == frameRateVote.mCompatibility;
    }

    public int hashCode() {
      return Objects.hash(Float.valueOf(this.mRefreshRate), Integer.valueOf(this.mCompatibility));
    }

    public String toString() {
      return "mRefreshRate=" + this.mRefreshRate + ", mCompatibility=" + this.mCompatibility;
    }

    public final boolean refreshRateEquals(float f) {
      float f2 = this.mRefreshRate;
      return f2 <= f + 0.01f && f2 >= f - 0.01f;
    }
  }

  public boolean updateFrameRateVote(WindowState windowState) {
    int i;
    int refreshRateSwitchingType =
        this.mWmService.mDisplayManagerInternal.getRefreshRateSwitchingType();
    if (refreshRateSwitchingType == 0) {
      return windowState.mFrameRateVote.reset();
    }
    if (windowState.isAnimationRunningSelfOrParent()) {
      return windowState.mFrameRateVote.reset();
    }
    if (refreshRateSwitchingType != 3 && (i = windowState.mAttrs.preferredDisplayModeId) > 0) {
      for (Display.Mode mode : this.mDisplayInfo.supportedModes) {
        if (i == mode.getModeId()) {
          if (CoreRune.FW_VRR_HRR_CHINA_DELTA
              && mode.getRefreshRate() >= 119.99f
              && isHighRefreshRatePackage(windowState.getOwningPackage())) {
            return windowState.mFrameRateVote.reset();
          }
          return windowState.mFrameRateVote.update(mode.getRefreshRate(), 100);
        }
      }
    }
    float f = windowState.mAttrs.preferredRefreshRate;
    if (f > DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
      return windowState.mFrameRateVote.update(f, 0);
    }
    if (refreshRateSwitchingType != 3) {
      if (this.mHighRefreshRateDenylist.isDenylisted(windowState.getOwningPackage())) {
        return windowState.mFrameRateVote.update(this.mLowRefreshRateMode.getRefreshRate(), 100);
      }
    }
    return windowState.mFrameRateVote.reset();
  }

  public float getPreferredMinRefreshRate(WindowState windowState) {
    if (windowState.isAnimationRunningSelfOrParent()) {
      return DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
    }
    if (CoreRune.FW_VRR_FIXED_REFRESH_RATE_PACKAGE
        && windowState.mAttrs.preferredMinDisplayRefreshRate
            == DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
      float refreshRateFromFixedRefreshRatePackages =
          getRefreshRateFromFixedRefreshRatePackages(windowState);
      if (refreshRateFromFixedRefreshRatePackages > DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
        return refreshRateFromFixedRefreshRatePackages;
      }
    }
    float f = windowState.mAttrs.preferredMinDisplayRefreshRate;
    if (f > DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
      return f;
    }
    SurfaceControl.RefreshRateRange refreshRateRange =
        this.mNonHighRefreshRatePackages.get(windowState.getOwningPackage());
    return refreshRateRange != null
        ? refreshRateRange.min
        : DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
  }

  public float getPreferredMaxRefreshRate(WindowState windowState) {
    if (windowState.isAnimationRunningSelfOrParent()
        && (!CoreRune.FW_VRR_POLICY
            || !RefreshRateConfig.getInstance().isSwitchable()
            || !windowState.isAnimating(3, 1))) {
      return DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
    }
    if (CoreRune.FW_VRR_FIXED_REFRESH_RATE_PACKAGE) {
      float refreshRateFromFixedRefreshRatePackages =
          getRefreshRateFromFixedRefreshRatePackages(windowState);
      if (refreshRateFromFixedRefreshRatePackages > DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
        return refreshRateFromFixedRefreshRatePackages;
      }
    }
    float f = windowState.mAttrs.preferredMaxDisplayRefreshRate;
    if (f > DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
      return f;
    }
    String owningPackage = windowState.getOwningPackage();
    SurfaceControl.RefreshRateRange refreshRateRange =
        this.mNonHighRefreshRatePackages.get(owningPackage);
    if (refreshRateRange != null) {
      return refreshRateRange.max;
    }
    if (CoreRune.FW_VRR_HIGH_REFRESH_RATE_BLOCK_LIST
        && this.mWmService.mAtmService.mExt.mHighRefreshRateBlockList.contains(owningPackage)) {
      this.mRestrictHighRefreshRate = true;
      return DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
    }
    if (CoreRune.FW_VRR_NAVIGATION_LOW_REFRESH_RATE
        && isNavigationPackageWithKeepScreenOn(windowState, owningPackage)) {
      return this.mLowRefreshRateMode.getRefreshRate();
    }
    return (CoreRune.FW_VRR_LOW_REFRESH_RATE_LIST
            && isFocusedLowRefreshRatePackage(windowState, owningPackage))
        ? this.mLowRefreshRateMode.getRefreshRate()
        : DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
  }

  public final boolean isNavigationPackageWithKeepScreenOn(WindowState windowState, String str) {
    return this.mWmService.mAtmService.mExt.mNaviAppLowRefreshRateList.contains(str)
        && (windowState.mAttrs.flags & 128) != 0;
  }

  public final boolean isFocusedLowRefreshRatePackage(WindowState windowState, String str) {
    return windowState.isFocused()
        && this.mWmService.mAtmService.mExt.mLowRefreshRateList.contains(str);
  }

  public final boolean isHighRefreshRatePackage(String str) {
    return this.mWmService.mAtmService.mExt.mHighRefreshRateBlockList.contains(str);
  }

  public void resetRestrictHighRefreshRate() {
    this.mRestrictHighRefreshRate = false;
  }

  public void updateRestrictHighRefreshRate() {
    if (this.mDisplayInfo.state == 1
        || this.mReportedRestrictHighRefreshRate.getAndSet(this.mRestrictHighRefreshRate)
            == this.mRestrictHighRefreshRate) {
      return;
    }
    SurfaceControl.restrictHighRefreshRate(this.mReportedRestrictHighRefreshRate.get());
  }

  public void onDisplayInfoChanged(DisplayInfo displayInfo) {
    this.mLowRefreshRateMode = findLowRefreshRateMode(displayInfo, displayInfo.getDefaultMode());
  }

  public void dump(String str, PrintWriter printWriter) {
    printWriter.print(str);
    printWriter.println("RefreshRatePolicy");
    String str2 = str + "  ";
    printWriter.print(str2);
    printWriter.print("mLowRefreshRateMode=");
    printWriter.println(this.mLowRefreshRateMode);
    if (CoreRune.FW_VRR_SYSTEM_HISTORY) {
      this.mRefreshRatePolicyLogger.dump(str2, printWriter);
    }
  }

  public void updateLog(WindowState windowState, int i, float f, int i2) {
    this.mRefreshRatePolicyLogger.update(windowState, i, f, i2);
  }
}
