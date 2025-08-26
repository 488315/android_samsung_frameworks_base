package android.hardware.display;

import android.companion.virtual.IVirtualDevice;
import android.graphics.Point;
import android.hardware.SensorManager;
import android.hardware.input.HostUsiVersion;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Handler;
import android.os.IBinder;
import android.util.IntArray;
import android.util.SparseArray;
import android.view.Display;
import android.view.DisplayInfo;
import android.view.SurfaceControl;
import android.window.DisplayWindowPolicyController;
import android.window.ScreenCapture;
import com.android.internal.display.BrightnessSynchronizer;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

/* loaded from: classes2.dex */
public abstract class DisplayManagerInternal {
    public static final int HBM_FREEZE_MODE = 2;
    public static final int NONFREEZING = 0;
    public static final int NON_HBM_FREEZE_MODE = 1;
    public static final int REFRESH_RATE_LIMIT_HIGH_BRIGHTNESS_MODE = 1;

    public interface DisplayGroupListener {
        void onDisplayGroupAdded(int i);

        void onDisplayGroupChanged(int i);

        void onDisplayGroupRemoved(int i);
    }

    public interface DisplayOffloader {
        boolean allowAutoBrightnessInDoze();

        void cancelBlockScreenOn();

        void onBlockingScreenOn(Runnable runnable);

        boolean startOffload();

        void stopOffload();
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

    public interface DisplayStateListener {
        public static final int TYPE_DEFAULT_DISPLAY = 1;
        public static final int TYPE_EXTRA_BUILT_IN_DISPLAY = 2;
        public static final int TYPE_OTHER_DISPLAY = -1;

        default void onFinish(int i, int i2, int i3) {
        }

        default void onStart(int i, int i2, int i3) {
        }
    }

    public interface DisplayTransactionListener {
        void onDisplayTransaction(SurfaceControl.Transaction transaction);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface RefreshRateLimitType {
    }

    public abstract void clearOldDisplayDevice();

    public abstract void clearTopologies();

    public abstract int createSpegVirtualDisplay(String str, int i, IVirtualDisplayCallback iVirtualDisplayCallback);

    public abstract int createVirtualDisplay(VirtualDisplayConfig virtualDisplayConfig, IVirtualDisplayCallback iVirtualDisplayCallback, IVirtualDevice iVirtualDevice, DisplayWindowPolicyController displayWindowPolicyController, String str);

    public abstract AmbientLightSensorData getAmbientLightSensorData(int i);

    public abstract int[] getBrightnessLearningMaxLimitCount();

    public abstract float getCurrentScreenBrightness();

    public abstract IntArray getDisplayGroupIds();

    public abstract int getDisplayIdToMirror(int i);

    public abstract IntArray getDisplayIds();

    public abstract SparseArray<int[]> getDisplayIdsByGroupsIds();

    public abstract int[] getDisplayIdsForGroup(int i);

    public abstract DisplayInfo getDisplayInfo(int i);

    public abstract SurfaceControl.DisplayPrimaries getDisplayNativePrimaries(int i);

    public abstract Point getDisplayPosition(int i);

    public abstract Point getDisplaySurfaceDefaultSize(int i);

    public abstract DisplayWindowPolicyController getDisplayWindowPolicyController(int i);

    public abstract DisplayedContentSample getDisplayedContentSample(int i, long j, long j2);

    public abstract DisplayedContentSamplingAttributes getDisplayedContentSamplingAttributes(int i);

    public abstract int getGroupIdForDisplay(int i);

    public abstract HostUsiVersion getHostUsiVersion(int i);

    public abstract float getLastAutomaticScreenBrightness();

    public abstract long getLastUserSetScreenBrightnessTime();

    public abstract void getNonOverrideDisplayInfo(int i, DisplayInfo displayInfo);

    public abstract Set<DisplayInfo> getPossibleDisplayInfo(int i);

    public abstract IBinder getRealDisplayToken(int i);

    public abstract SurfaceControl.RefreshRateRange getRefreshRateForDisplayAndSensor(int i, String str, String str2);

    public abstract List<RefreshRateLimitation> getRefreshRateLimitations(int i);

    public abstract int getRefreshRateSwitchingType();

    public abstract void hideCutoutForFoldable(boolean z);

    public abstract void ignoreProximitySensorUntilChanged();

    public abstract void initPowerManagement(DisplayPowerCallbacks displayPowerCallbacks, Handler handler, SensorManager sensorManager);

    public abstract boolean isChangingPreferredMode();

    public abstract boolean isDisplayReadyForMirroring(int i);

    public abstract boolean isLoadedUserPreferredResolution();

    public abstract boolean isProximitySensorAvailable(int i);

    public abstract void onDisplayBelongToTopologyChanged(int i, boolean z);

    public abstract void onEarlyInteractivityChange(boolean z);

    public abstract void onExternalDesktopModeChanged(int i);

    public abstract void onOverlayChanged();

    public abstract void onPresentation(int i, boolean z);

    public abstract void performTraversal(SurfaceControl.Transaction transaction, SparseArray<SurfaceControl.Transaction> sparseArray);

    public abstract void persistBrightnessTrackerState();

    public abstract float registerDisplayBrightnessListener(DisplayBrightnessListener displayBrightnessListener);

    public abstract void registerDisplayGroupListener(DisplayGroupListener displayGroupListener);

    public abstract DisplayOffloadSession registerDisplayOffloader(int i, DisplayOffloader displayOffloader);

    public abstract void registerDisplayStateListener(DisplayStateListener displayStateListener);

    public abstract void registerDisplayTransactionListener(DisplayTransactionListener displayTransactionListener);

    public abstract void reloadTopologies(int i);

    public abstract boolean requestPowerState(int i, DisplayPowerRequest displayPowerRequest, boolean z);

    public abstract void setChangingPreferredMode(boolean z);

    public abstract void setDisplayAccessUIDs(SparseArray<IntArray> sparseArray);

    public abstract void setDisplayInfoOverrideFromWindowManager(int i, DisplayInfo displayInfo);

    public abstract void setDisplayOffsets(int i, int i2, int i3);

    public abstract void setDisplayProperties(int i, boolean z, float f, int i2, float f2, float f3, boolean z2, boolean z3, boolean z4);

    public abstract void setDisplayScalingDisabled(int i, boolean z);

    public abstract void setDisplayStateOverride(IBinder iBinder, int i, int i2);

    public abstract boolean setDisplayedContentSamplingEnabled(int i, boolean z, int i2, int i3);

    public abstract void setForceListenProcess(int i);

    public abstract int setFreezeBrightnessMode(boolean z);

    public abstract void setScreenBrightnessOverrideFromWindowManager(SparseArray<DisplayBrightnessOverrideRequest> sparseArray);

    public abstract void setUserPreferredDisplayMode(int i, Display.Mode mode);

    public abstract void setWindowManagerMirroring(int i, boolean z);

    public abstract void stylusGestureStarted(long j);

    public abstract ScreenCapture.ScreenshotHardwareBuffer systemScreenshot(int i);

    public abstract void unregisterDisplayBrightnessListener(DisplayBrightnessListener displayBrightnessListener);

    public abstract void unregisterDisplayGroupListener(DisplayGroupListener displayGroupListener);

    public abstract void unregisterDisplayStateListener(DisplayStateListener displayStateListener);

    public abstract void unregisterDisplayTransactionListener(DisplayTransactionListener displayTransactionListener);

    public abstract ScreenCapture.ScreenshotHardwareBuffer userScreenshot(int i);

    public static class DisplayBrightnessOverrideRequest {
        public float brightness = Float.NaN;
        public String screenBrightnessOverridePackage = "";
        public CharSequence tag;

        public void copyFrom(DisplayBrightnessOverrideRequest displayBrightnessOverrideRequest) {
            this.brightness = displayBrightnessOverrideRequest.brightness;
            this.tag = displayBrightnessOverrideRequest.tag;
            this.screenBrightnessOverridePackage = displayBrightnessOverrideRequest.screenBrightnessOverridePackage;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof DisplayBrightnessOverrideRequest) {
                DisplayBrightnessOverrideRequest displayBrightnessOverrideRequest = (DisplayBrightnessOverrideRequest) obj;
                if (Float.compare(this.brightness, displayBrightnessOverrideRequest.brightness) == 0 && Objects.equals(this.tag, displayBrightnessOverrideRequest.tag) && Objects.equals(this.screenBrightnessOverridePackage, displayBrightnessOverrideRequest.screenBrightnessOverridePackage)) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(Float.valueOf(this.brightness), this.tag, this.screenBrightnessOverridePackage);
        }

        public String toString() {
            return "br:" + brightnessToString(this.brightness) + " tag:" + ((Object) this.tag) + " pkg:" + this.screenBrightnessOverridePackage;
        }

        private String brightnessToString(float f) {
            return String.format(Locale.US, "%d(%.2f)", Integer.valueOf(BrightnessSynchronizer.brightnessFloatToInt(f)), Float.valueOf(f));
        }
    }

    public static class DisplayPowerRequest {
        public static final int POLICY_BRIGHT = 3;
        public static final int POLICY_DIM = 2;
        public static final int POLICY_DOZE = 1;
        public static final int POLICY_MAX = 3;
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
        public int dozeScreenStateReason;
        public boolean earlyWakeUp;
        public boolean forceLcdBacklightOffEnabled;
        public boolean forceSlowChange;
        public boolean hbmBlock;
        public float hdrMaxBrightness;
        public boolean isOutdoorMode;
        public boolean isPowered;
        public int lastGoToSleepReason;
        public int lastWakeUpReason;
        public boolean lcdFlashMode;
        public boolean lowPowerMode;
        public float maxBrightness;
        public float minBrightness;
        public int policy;
        public int policyReason;
        public int proximityNegativeDebounce;
        public int proximityPositiveDebounce;
        public float screenAutoBrightnessAdjustmentOverride;
        public float screenBrightnessOverride;
        public CharSequence screenBrightnessOverrideTag;
        public float screenBrightnessScaleFactor;
        public boolean screenCurtainEnabled;
        public float screenLowPowerBrightnessFactor;
        public boolean useNormalBrightnessForDoze;
        public boolean useProximitySensor;

        public int hashCode() {
            return 0;
        }

        public DisplayPowerRequest() {
            this.autoBrightnessLowerLimit = -1.0f;
            this.autoBrightnessUpperLimit = -1.0f;
            this.maxBrightness = -1.0f;
            this.minBrightness = -1.0f;
            this.hdrMaxBrightness = -1.0f;
            this.lastGoToSleepReason = 0;
            this.proximityPositiveDebounce = -1;
            this.proximityNegativeDebounce = -1;
            this.brightnessLimitByCover = -1;
            this.batteryLevel = -1;
            this.lastWakeUpReason = 0;
            this.lcdFlashMode = false;
            this.isOutdoorMode = false;
            this.screenBrightnessScaleFactor = 1.0f;
            this.forceLcdBacklightOffEnabled = false;
            this.policy = 3;
            this.policyReason = 1;
            this.useProximitySensor = false;
            this.screenBrightnessOverride = Float.NaN;
            this.screenAutoBrightnessAdjustmentOverride = Float.NaN;
            this.screenLowPowerBrightnessFactor = 0.5f;
            this.blockScreenOn = false;
            this.dozeScreenBrightness = Float.NaN;
            this.dozeScreenState = 0;
            this.dozeScreenStateReason = 0;
        }

        public DisplayPowerRequest(DisplayPowerRequest displayPowerRequest) {
            this.autoBrightnessLowerLimit = -1.0f;
            this.autoBrightnessUpperLimit = -1.0f;
            this.maxBrightness = -1.0f;
            this.minBrightness = -1.0f;
            this.hdrMaxBrightness = -1.0f;
            this.lastGoToSleepReason = 0;
            this.proximityPositiveDebounce = -1;
            this.proximityNegativeDebounce = -1;
            this.brightnessLimitByCover = -1;
            this.batteryLevel = -1;
            this.lastWakeUpReason = 0;
            this.lcdFlashMode = false;
            this.isOutdoorMode = false;
            this.screenBrightnessScaleFactor = 1.0f;
            this.forceLcdBacklightOffEnabled = false;
            copyFrom(displayPowerRequest);
        }

        public boolean isBrightOrDim() {
            int i = this.policy;
            return i == 3 || i == 2;
        }

        public void copyFrom(DisplayPowerRequest displayPowerRequest) {
            this.policy = displayPowerRequest.policy;
            this.policyReason = displayPowerRequest.policyReason;
            this.useProximitySensor = displayPowerRequest.useProximitySensor;
            this.screenBrightnessOverride = displayPowerRequest.screenBrightnessOverride;
            this.screenBrightnessOverrideTag = displayPowerRequest.screenBrightnessOverrideTag;
            this.screenAutoBrightnessAdjustmentOverride = displayPowerRequest.screenAutoBrightnessAdjustmentOverride;
            this.screenLowPowerBrightnessFactor = displayPowerRequest.screenLowPowerBrightnessFactor;
            this.blockScreenOn = displayPowerRequest.blockScreenOn;
            this.lowPowerMode = displayPowerRequest.lowPowerMode;
            this.boostScreenBrightness = displayPowerRequest.boostScreenBrightness;
            this.dozeScreenBrightness = displayPowerRequest.dozeScreenBrightness;
            this.dozeScreenState = displayPowerRequest.dozeScreenState;
            this.dozeScreenStateReason = displayPowerRequest.dozeScreenStateReason;
            this.useNormalBrightnessForDoze = displayPowerRequest.useNormalBrightnessForDoze;
            this.autoBrightnessLowerLimit = displayPowerRequest.autoBrightnessLowerLimit;
            this.autoBrightnessUpperLimit = displayPowerRequest.autoBrightnessUpperLimit;
            this.forceSlowChange = displayPowerRequest.forceSlowChange;
            this.maxBrightness = displayPowerRequest.maxBrightness;
            this.minBrightness = displayPowerRequest.minBrightness;
            this.hdrMaxBrightness = displayPowerRequest.hdrMaxBrightness;
            this.lastGoToSleepReason = displayPowerRequest.lastGoToSleepReason;
            this.proximityPositiveDebounce = displayPowerRequest.proximityPositiveDebounce;
            this.proximityNegativeDebounce = displayPowerRequest.proximityNegativeDebounce;
            this.coverClosed = displayPowerRequest.coverClosed;
            this.coverType = displayPowerRequest.coverType;
            this.brightnessLimitByCover = displayPowerRequest.brightnessLimitByCover;
            this.batteryLevel = displayPowerRequest.batteryLevel;
            this.lcdFlashMode = displayPowerRequest.lcdFlashMode;
            this.isOutdoorMode = displayPowerRequest.isOutdoorMode;
            this.screenBrightnessScaleFactor = displayPowerRequest.screenBrightnessScaleFactor;
            this.forceLcdBacklightOffEnabled = displayPowerRequest.forceLcdBacklightOffEnabled;
            this.batteryLevelCritical = displayPowerRequest.batteryLevelCritical;
            this.isPowered = displayPowerRequest.isPowered;
            this.hbmBlock = displayPowerRequest.hbmBlock;
            this.earlyWakeUp = displayPowerRequest.earlyWakeUp;
            this.lastWakeUpReason = displayPowerRequest.lastWakeUpReason;
            this.screenCurtainEnabled = displayPowerRequest.screenCurtainEnabled;
        }

        public boolean equals(Object obj) {
            return (obj instanceof DisplayPowerRequest) && equals((DisplayPowerRequest) obj);
        }

        public boolean equals(DisplayPowerRequest displayPowerRequest) {
            return displayPowerRequest != null && this.policy == displayPowerRequest.policy && this.useProximitySensor == displayPowerRequest.useProximitySensor && floatEquals(this.screenBrightnessOverride, displayPowerRequest.screenBrightnessOverride) && Objects.equals(this.screenBrightnessOverrideTag, displayPowerRequest.screenBrightnessOverrideTag) && floatEquals(this.screenAutoBrightnessAdjustmentOverride, displayPowerRequest.screenAutoBrightnessAdjustmentOverride) && this.screenLowPowerBrightnessFactor == displayPowerRequest.screenLowPowerBrightnessFactor && this.blockScreenOn == displayPowerRequest.blockScreenOn && this.lowPowerMode == displayPowerRequest.lowPowerMode && this.boostScreenBrightness == displayPowerRequest.boostScreenBrightness && floatEquals(this.dozeScreenBrightness, displayPowerRequest.dozeScreenBrightness) && this.dozeScreenState == displayPowerRequest.dozeScreenState && this.dozeScreenStateReason == displayPowerRequest.dozeScreenStateReason && this.useNormalBrightnessForDoze == displayPowerRequest.useNormalBrightnessForDoze && floatEquals(this.autoBrightnessLowerLimit, displayPowerRequest.autoBrightnessLowerLimit) && floatEquals(this.autoBrightnessUpperLimit, displayPowerRequest.autoBrightnessUpperLimit) && this.forceSlowChange == displayPowerRequest.forceSlowChange && floatEquals(this.maxBrightness, displayPowerRequest.maxBrightness) && floatEquals(this.minBrightness, displayPowerRequest.minBrightness) && floatEquals(this.hdrMaxBrightness, displayPowerRequest.hdrMaxBrightness) && this.lastGoToSleepReason == displayPowerRequest.lastGoToSleepReason && this.proximityPositiveDebounce == displayPowerRequest.proximityPositiveDebounce && this.proximityNegativeDebounce == displayPowerRequest.proximityNegativeDebounce && this.coverClosed == displayPowerRequest.coverClosed && this.coverType == displayPowerRequest.coverType && this.brightnessLimitByCover == displayPowerRequest.brightnessLimitByCover && this.batteryLevel == displayPowerRequest.batteryLevel && this.lcdFlashMode == displayPowerRequest.lcdFlashMode && this.isOutdoorMode == displayPowerRequest.isOutdoorMode && this.screenBrightnessScaleFactor == displayPowerRequest.screenBrightnessScaleFactor && this.forceLcdBacklightOffEnabled == displayPowerRequest.forceLcdBacklightOffEnabled && this.hbmBlock == displayPowerRequest.hbmBlock && this.batteryLevelCritical == displayPowerRequest.batteryLevelCritical && this.isPowered == displayPowerRequest.isPowered && this.earlyWakeUp == displayPowerRequest.earlyWakeUp && this.lastWakeUpReason == displayPowerRequest.lastWakeUpReason && this.screenCurtainEnabled == displayPowerRequest.screenCurtainEnabled;
        }

        private boolean floatEquals(float f, float f2) {
            if (f != f2) {
                return Float.isNaN(f) && Float.isNaN(f2);
            }
            return true;
        }

        public String toString() {
            return "policy=" + policyToString(this.policy) + ", useProximitySensor=" + this.useProximitySensor + ", screenBrightnessOverride=" + this.screenBrightnessOverride + ", screenAutoBrightnessAdjustmentOverride=" + this.screenAutoBrightnessAdjustmentOverride + ", screenLowPowerBrightnessFactor=" + this.screenLowPowerBrightnessFactor + ", blockScreenOn=" + this.blockScreenOn + ", lowPowerMode=" + this.lowPowerMode + ", boostScreenBrightness=" + this.boostScreenBrightness + ", dozeScreenBrightness=" + this.dozeScreenBrightness + ", dozeScreenState=" + Display.stateToString(this.dozeScreenState) + ", dozeScreenStateReason=" + Display.stateReasonToString(this.dozeScreenStateReason) + ", useNormalBrightnessForDoze=" + this.useNormalBrightnessForDoze + ", autoBrightnessLowerLimit=" + this.autoBrightnessLowerLimit + ", autoBrightnessUpperLimit=" + this.autoBrightnessUpperLimit + ", forceSlowChange=" + this.forceSlowChange + ", maxBrightness=" + this.maxBrightness + ", minBrightness=" + this.minBrightness + ", hdrMaxBrightness=" + this.hdrMaxBrightness + ", lastGoToSleepReason=" + this.lastGoToSleepReason + ", proximityPositiveDebounce=" + this.proximityPositiveDebounce + ", proximityNegativeDebounce=" + this.proximityNegativeDebounce + ", coverClosed=" + this.coverClosed + ", coverType=" + this.coverType + ", brightnessLimitByCover=" + this.brightnessLimitByCover + ", batteryLevel = " + this.batteryLevel + ", lcdFlashMode= " + this.lcdFlashMode + ", isOutdoorMode= " + this.isOutdoorMode + ", screenBrightnessScaleFactor=" + this.screenBrightnessScaleFactor + ", forceLcdBacklightOffEnabled=" + this.forceLcdBacklightOffEnabled + ", batteryLevelCritical=" + this.batteryLevelCritical + ", isPowered=" + this.isPowered + ", hbmBlock=" + this.hbmBlock + ", earlyWakeUp=" + this.earlyWakeUp + ", lastWakeUpReason=" + this.lastWakeUpReason + ", screenCurtainEnabled=" + this.screenCurtainEnabled;
        }

        public static String policyToString(int i) {
            if (i == 0) {
                return "OFF";
            }
            if (i == 1) {
                return "DOZE";
            }
            if (i == 2) {
                return "DIM";
            }
            if (i == 3) {
                return "BRIGHT";
            }
            return Integer.toString(i);
        }
    }

    public static final class RefreshRateLimitation {
        public SurfaceControl.RefreshRateRange range;
        public int type;

        public RefreshRateLimitation(int i, float f, float f2) {
            this(i, new SurfaceControl.RefreshRateRange(f, f2));
        }

        public RefreshRateLimitation(int i, SurfaceControl.RefreshRateRange refreshRateRange) {
            this.type = i;
            this.range = refreshRateRange;
        }

        public String toString() {
            return "RefreshRateLimitation(" + this.type + ": " + this.range + NavigationBarInflaterView.KEY_CODE_END;
        }
    }

    public static final class AmbientLightSensorData {
        public String sensorName;
        public String sensorType;

        public AmbientLightSensorData(String str, String str2) {
            this.sensorName = str;
            this.sensorType = str2;
        }

        public String toString() {
            return "AmbientLightSensorData(" + this.sensorName + ", " + this.sensorType + NavigationBarInflaterView.KEY_CODE_END;
        }
    }

    public interface DisplayOffloadSession {
        boolean allowAutoBrightnessInDoze();

        boolean blockScreenOn(Runnable runnable);

        void cancelBlockScreenOn();

        float[] getAutoBrightnessLevels(int i);

        float[] getAutoBrightnessLuxLevels(int i);

        float getBrightness();

        float getDozeBrightness();

        boolean isActive();

        void setDozeStateOverride(int i);

        void updateBrightness(float f);

        static boolean isSupportedOffloadState(int i) {
            return Display.isSuspendedState(i);
        }
    }

    public interface DisplayBrightnessListener {
        default void onChanged(float f) {
        }

        default void onChanged(int i, float f) {
            if (i == 0) {
                onChanged(f);
            }
        }
    }
}
