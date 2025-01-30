package android.hardware.display;

import android.companion.virtual.IVirtualDevice;
import android.graphics.Point;
import android.hardware.SensorManager;
import android.hardware.input.HostUsiVersion;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.p009os.Handler;
import android.p009os.IBinder;
import android.util.IntArray;
import android.util.SparseArray;
import android.view.Display;
import android.view.DisplayInfo;
import android.view.SurfaceControl;
import android.window.DisplayWindowPolicyController;
import android.window.ScreenCapture;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import java.util.Set;

/* loaded from: classes2.dex */
public abstract class DisplayManagerInternal {
  public static final int REFRESH_RATE_LIMIT_HIGH_BRIGHTNESS_MODE = 1;

  public interface DisplayBrightnessListener {
    void onChanged(float f);
  }

  public interface DisplayGroupListener {
    void onDisplayGroupAdded(int i);

    void onDisplayGroupChanged(int i);

    void onDisplayGroupRemoved(int i);
  }

  public interface DisplayPowerCallbacks {
    void acquireSuspendBlocker(String str);

    void onDefaultDisplayStateChange(int i);

    void onDisplayStateChange(boolean z, boolean z2);

    void onProximityNegative();

    void onProximityPositive();

    void onStateChanged();

    void releaseSuspendBlocker(String str);
  }

  public interface DisplayTransactionListener {
    void onDisplayTransaction(SurfaceControl.Transaction transaction);
  }

  @Retention(RetentionPolicy.SOURCE)
  public @interface RefreshRateLimitType {}

  public abstract void clearOldDisplayDevice();

  public abstract int createSpegVirtualDisplay(
      String str, int i, IVirtualDisplayCallback iVirtualDisplayCallback);

  public abstract int createVirtualDisplay(
      VirtualDisplayConfig virtualDisplayConfig,
      IVirtualDisplayCallback iVirtualDisplayCallback,
      IVirtualDevice iVirtualDevice,
      DisplayWindowPolicyController displayWindowPolicyController,
      String str);

  public abstract int[] getBrightnessLearningMaxLimitCount();

  public abstract float getCurrentScreenBrightness();

  public abstract IntArray getDisplayGroupIds();

  public abstract int getDisplayIdToMirror(int i);

  public abstract DisplayInfo getDisplayInfo(int i);

  public abstract SurfaceControl.DisplayPrimaries getDisplayNativePrimaries(int i);

  public abstract Point getDisplayPosition(int i);

  public abstract Point getDisplaySurfaceDefaultSize(int i);

  public abstract DisplayWindowPolicyController getDisplayWindowPolicyController(int i);

  public abstract DisplayedContentSample getDisplayedContentSample(int i, long j, long j2);

  public abstract DisplayedContentSamplingAttributes getDisplayedContentSamplingAttributes(int i);

  public abstract HostUsiVersion getHostUsiVersion(int i);

  public abstract float getLastAutomaticScreenBrightness();

  public abstract long getLastUserSetScreenBrightnessTime();

  public abstract void getNonOverrideDisplayInfo(int i, DisplayInfo displayInfo);

  public abstract Set<DisplayInfo> getPossibleDisplayInfo(int i);

  public abstract IBinder getRealDisplayToken(int i);

  public abstract SurfaceControl.RefreshRateRange getRefreshRateForDisplayAndSensor(
      int i, String str, String str2);

  public abstract List<RefreshRateLimitation> getRefreshRateLimitations(int i);

  public abstract int getRefreshRateSwitchingType();

  public abstract void hideCutoutForFoldable(boolean z);

  public abstract void ignoreProximitySensorUntilChanged();

  public abstract void initPowerManagement(
      DisplayPowerCallbacks displayPowerCallbacks, Handler handler, SensorManager sensorManager);

  public abstract boolean isDualSwitchEnabled();

  public abstract boolean isProximitySensorAvailable();

  public abstract void onEarlyInteractivityChange(boolean z);

  public abstract void onOverlayChanged();

  public abstract void performTraversal(
      SurfaceControl.Transaction transaction, SparseArray<SurfaceControl.Transaction> sparseArray);

  public abstract void persistBrightnessTrackerState();

  public abstract float registerDisplayBrightnessListener(
      DisplayBrightnessListener displayBrightnessListener);

  public abstract void registerDisplayGroupListener(DisplayGroupListener displayGroupListener);

  public abstract void registerDisplayStateListener(DisplayStateListener displayStateListener);

  public abstract void registerDisplayTransactionListener(
      DisplayTransactionListener displayTransactionListener);

  public abstract boolean requestPowerState(
      int i, DisplayPowerRequest displayPowerRequest, boolean z);

  public abstract void setDisplayAccessUIDs(SparseArray<IntArray> sparseArray);

  public abstract void setDisplayInfoOverrideFromWindowManager(int i, DisplayInfo displayInfo);

  public abstract void setDisplayOffsets(int i, int i2, int i3);

  public abstract void setDisplayProperties(
      int i, boolean z, float f, int i2, float f2, float f3, boolean z2, boolean z3, boolean z4);

  public abstract void setDisplayScalingDisabled(int i, boolean z);

  public abstract void setDisplayStateLimit(IBinder iBinder, int i);

  public abstract boolean setDisplayedContentSamplingEnabled(int i, boolean z, int i2, int i3);

  public abstract void setForceListenProcess(int i);

  public abstract void setWindowManagerMirroring(int i, boolean z);

  public abstract void switchOverlay();

  public abstract ScreenCapture.ScreenshotHardwareBuffer systemScreenshot(int i);

  public abstract void unregisterDisplayBrightnessListener(
      DisplayBrightnessListener displayBrightnessListener);

  public abstract void unregisterDisplayGroupListener(DisplayGroupListener displayGroupListener);

  public abstract void unregisterDisplayStateListener(DisplayStateListener displayStateListener);

  public abstract void unregisterDisplayTransactionListener(
      DisplayTransactionListener displayTransactionListener);

  public abstract void updateDesktopDisplayState(boolean z, int i);

  public abstract int updateDexDisplayState(boolean z);

  public abstract void updateDualOverlayState();

  public abstract void updateOverlayState();

  public abstract ScreenCapture.ScreenshotHardwareBuffer userScreenshot(int i);

  public static class DisplayPowerRequest {
    public static final int POLICY_BRIGHT = 3;
    public static final int POLICY_DIM = 2;
    public static final int POLICY_DOZE = 1;
    public static final int POLICY_OFF = 0;
    public float autoBrightnessLowerLimit;
    public float autoBrightnessUpperLimit;
    public int batteryLevel;
    public boolean batteryLevelCritical;
    public boolean blockScreenOn;
    public boolean boostScreenBrightness;
    public int brightnessLimitByCover;
    public boolean coverClosed;
    public int coverType;
    public float dozeScreenBrightness;
    public int dozeScreenState;
    public int dualScreenPolicy;
    public boolean earlyWakeUp;
    public boolean forceLcdBacklightOffEnabled;
    public boolean forceSlowChange;
    public boolean freezeBrightnessMode;
    public boolean hbmBlock;
    public boolean isOutdoorMode;
    public boolean isPowered;
    public int lastGoToSleepReason;
    public int lastWakeUpReason;
    public boolean lcdFlashMode;
    public boolean lowPowerMode;
    public boolean mFTAMode;
    public float maxBrightness;
    public float minBrightness;
    public int policy;
    public int proximityNegativeDebounce;
    public int proximityPositiveDebounce;
    public float screenAutoBrightnessAdjustmentOverride;
    public float screenBrightnessOverride;
    public float screenBrightnessScaleFactor;
    public boolean screenCurtainEnabled;
    public float screenLowPowerBrightnessFactor;
    public boolean useProximitySensor;

    public DisplayPowerRequest() {
      this.autoBrightnessLowerLimit = -1.0f;
      this.autoBrightnessUpperLimit = -1.0f;
      this.maxBrightness = -1.0f;
      this.minBrightness = -1.0f;
      this.lastGoToSleepReason = 0;
      this.proximityPositiveDebounce = -1;
      this.proximityNegativeDebounce = -1;
      this.brightnessLimitByCover = -1;
      this.batteryLevel = -1;
      this.dualScreenPolicy = -1;
      this.lastWakeUpReason = 0;
      this.lcdFlashMode = false;
      this.isOutdoorMode = false;
      this.screenBrightnessScaleFactor = 1.0f;
      this.forceLcdBacklightOffEnabled = false;
      this.policy = 3;
      this.useProximitySensor = false;
      this.screenBrightnessOverride = Float.NaN;
      this.screenAutoBrightnessAdjustmentOverride = Float.NaN;
      this.screenLowPowerBrightnessFactor = 0.5f;
      this.blockScreenOn = false;
      this.dozeScreenBrightness = Float.NaN;
      this.dozeScreenState = 0;
    }

    public DisplayPowerRequest(DisplayPowerRequest other) {
      this.autoBrightnessLowerLimit = -1.0f;
      this.autoBrightnessUpperLimit = -1.0f;
      this.maxBrightness = -1.0f;
      this.minBrightness = -1.0f;
      this.lastGoToSleepReason = 0;
      this.proximityPositiveDebounce = -1;
      this.proximityNegativeDebounce = -1;
      this.brightnessLimitByCover = -1;
      this.batteryLevel = -1;
      this.dualScreenPolicy = -1;
      this.lastWakeUpReason = 0;
      this.lcdFlashMode = false;
      this.isOutdoorMode = false;
      this.screenBrightnessScaleFactor = 1.0f;
      this.forceLcdBacklightOffEnabled = false;
      copyFrom(other);
    }

    public boolean isBrightOrDim() {
      int i = this.policy;
      return i == 3 || i == 2;
    }

    public void copyFrom(DisplayPowerRequest other) {
      this.policy = other.policy;
      this.useProximitySensor = other.useProximitySensor;
      this.screenBrightnessOverride = other.screenBrightnessOverride;
      this.screenAutoBrightnessAdjustmentOverride = other.screenAutoBrightnessAdjustmentOverride;
      this.screenLowPowerBrightnessFactor = other.screenLowPowerBrightnessFactor;
      this.blockScreenOn = other.blockScreenOn;
      this.lowPowerMode = other.lowPowerMode;
      this.boostScreenBrightness = other.boostScreenBrightness;
      this.dozeScreenBrightness = other.dozeScreenBrightness;
      this.dozeScreenState = other.dozeScreenState;
      this.autoBrightnessLowerLimit = other.autoBrightnessLowerLimit;
      this.autoBrightnessUpperLimit = other.autoBrightnessUpperLimit;
      this.forceSlowChange = other.forceSlowChange;
      this.maxBrightness = other.maxBrightness;
      this.minBrightness = other.minBrightness;
      this.lastGoToSleepReason = other.lastGoToSleepReason;
      this.proximityPositiveDebounce = other.proximityPositiveDebounce;
      this.proximityNegativeDebounce = other.proximityNegativeDebounce;
      this.coverClosed = other.coverClosed;
      this.coverType = other.coverType;
      this.brightnessLimitByCover = other.brightnessLimitByCover;
      this.mFTAMode = other.mFTAMode;
      this.batteryLevel = other.batteryLevel;
      this.lcdFlashMode = other.lcdFlashMode;
      this.isOutdoorMode = other.isOutdoorMode;
      this.screenBrightnessScaleFactor = other.screenBrightnessScaleFactor;
      this.forceLcdBacklightOffEnabled = other.forceLcdBacklightOffEnabled;
      this.batteryLevelCritical = other.batteryLevelCritical;
      this.isPowered = other.isPowered;
      this.hbmBlock = other.hbmBlock;
      this.freezeBrightnessMode = other.freezeBrightnessMode;
      this.earlyWakeUp = other.earlyWakeUp;
      this.dualScreenPolicy = other.dualScreenPolicy;
      this.lastWakeUpReason = other.lastWakeUpReason;
      this.screenCurtainEnabled = other.screenCurtainEnabled;
    }

    public boolean equals(Object o) {
      return (o instanceof DisplayPowerRequest) && equals((DisplayPowerRequest) o);
    }

    public boolean equals(DisplayPowerRequest other) {
      return other != null
          && this.policy == other.policy
          && this.useProximitySensor == other.useProximitySensor
          && floatEquals(this.screenBrightnessOverride, other.screenBrightnessOverride)
          && floatEquals(
              this.screenAutoBrightnessAdjustmentOverride,
              other.screenAutoBrightnessAdjustmentOverride)
          && this.screenLowPowerBrightnessFactor == other.screenLowPowerBrightnessFactor
          && this.blockScreenOn == other.blockScreenOn
          && this.lowPowerMode == other.lowPowerMode
          && this.boostScreenBrightness == other.boostScreenBrightness
          && floatEquals(this.dozeScreenBrightness, other.dozeScreenBrightness)
          && this.dozeScreenState == other.dozeScreenState
          && floatEquals(this.autoBrightnessLowerLimit, other.autoBrightnessLowerLimit)
          && floatEquals(this.autoBrightnessUpperLimit, other.autoBrightnessUpperLimit)
          && this.forceSlowChange == other.forceSlowChange
          && floatEquals(this.maxBrightness, other.maxBrightness)
          && floatEquals(this.minBrightness, other.minBrightness)
          && this.lastGoToSleepReason == other.lastGoToSleepReason
          && this.proximityPositiveDebounce == other.proximityPositiveDebounce
          && this.proximityNegativeDebounce == other.proximityNegativeDebounce
          && this.coverClosed == other.coverClosed
          && this.coverType == other.coverType
          && this.brightnessLimitByCover == other.brightnessLimitByCover
          && this.mFTAMode == other.mFTAMode
          && this.batteryLevel == other.batteryLevel
          && this.lcdFlashMode == other.lcdFlashMode
          && this.isOutdoorMode == other.isOutdoorMode
          && this.screenBrightnessScaleFactor == other.screenBrightnessScaleFactor
          && this.forceLcdBacklightOffEnabled == other.forceLcdBacklightOffEnabled
          && this.hbmBlock == other.hbmBlock
          && this.freezeBrightnessMode == other.freezeBrightnessMode
          && this.batteryLevelCritical == other.batteryLevelCritical
          && this.isPowered == other.isPowered
          && this.earlyWakeUp == other.earlyWakeUp
          && this.dualScreenPolicy == other.dualScreenPolicy
          && this.lastWakeUpReason == other.lastWakeUpReason
          && this.screenCurtainEnabled == other.screenCurtainEnabled;
    }

    private boolean floatEquals(float f1, float f2) {
      return f1 == f2 || (Float.isNaN(f1) && Float.isNaN(f2));
    }

    public int hashCode() {
      return 0;
    }

    public String toString() {
      return "policy="
          + policyToString(this.policy)
          + ", useProximitySensor="
          + this.useProximitySensor
          + ", screenBrightnessOverride="
          + this.screenBrightnessOverride
          + ", screenAutoBrightnessAdjustmentOverride="
          + this.screenAutoBrightnessAdjustmentOverride
          + ", screenLowPowerBrightnessFactor="
          + this.screenLowPowerBrightnessFactor
          + ", blockScreenOn="
          + this.blockScreenOn
          + ", lowPowerMode="
          + this.lowPowerMode
          + ", boostScreenBrightness="
          + this.boostScreenBrightness
          + ", dozeScreenBrightness="
          + this.dozeScreenBrightness
          + ", dozeScreenState="
          + Display.stateToString(this.dozeScreenState)
          + ", autoBrightnessLowerLimit="
          + this.autoBrightnessLowerLimit
          + ", autoBrightnessUpperLimit="
          + this.autoBrightnessUpperLimit
          + ", forceSlowChange="
          + this.forceSlowChange
          + ", maxBrightness="
          + this.maxBrightness
          + ", minBrightness="
          + this.minBrightness
          + ", lastGoToSleepReason="
          + this.lastGoToSleepReason
          + ", proximityPositiveDebounce="
          + this.proximityPositiveDebounce
          + ", proximityNegativeDebounce="
          + this.proximityNegativeDebounce
          + ", coverClosed="
          + this.coverClosed
          + ", coverType="
          + this.coverType
          + ", brightnessLimitByCover="
          + this.brightnessLimitByCover
          + ", FTAMode="
          + this.mFTAMode
          + ", batteryLevel = "
          + this.batteryLevel
          + ", lcdFlashMode= "
          + this.lcdFlashMode
          + ", isOutdoorMode= "
          + this.isOutdoorMode
          + ", screenBrightnessScaleFactor="
          + this.screenBrightnessScaleFactor
          + ", forceLcdBacklightOffEnabled="
          + this.forceLcdBacklightOffEnabled
          + ", batteryLevelCritical="
          + this.batteryLevelCritical
          + ", isPowered="
          + this.isPowered
          + ", hbmBlock="
          + this.hbmBlock
          + ", freezeBrightnessMode="
          + this.freezeBrightnessMode
          + ", earlyWakeUp="
          + this.earlyWakeUp
          + ", dualScreenPolicy="
          + this.dualScreenPolicy
          + ", lastWakeUpReason="
          + this.lastWakeUpReason
          + ", screenCurtainEnabled="
          + this.screenCurtainEnabled;
    }

    public static String policyToString(int policy) {
      switch (policy) {
        case 0:
          return "OFF";
        case 1:
          return "DOZE";
        case 2:
          return "DIM";
        case 3:
          return "BRIGHT";
        default:
          return Integer.toString(policy);
      }
    }
  }

  public static final class RefreshRateLimitation {
    public SurfaceControl.RefreshRateRange range;
    public int type;

    public RefreshRateLimitation(int type, float min, float max) {
      this.type = type;
      this.range = new SurfaceControl.RefreshRateRange(min, max);
    }

    public String toString() {
      return "RefreshRateLimitation("
          + this.type
          + ": "
          + this.range
          + NavigationBarInflaterView.KEY_CODE_END;
    }
  }

  public interface DisplayStateListener {
    public static final int TYPE_DEFAULT_DISPLAY = 1;
    public static final int TYPE_EXTRA_BUILT_IN_DISPLAY = 2;
    public static final int TYPE_OTHER_DISPLAY = -1;

    default void onStart(int stateLogical, int statePhysical, int displayType) {}

    default void onFinish(int stateLogical, int statePhysical, int displayType) {}
  }
}
