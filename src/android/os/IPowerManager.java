package android.os;

import android.hardware.usb.UsbManager;
import android.os.IScreenTimeoutPolicyListener;
import android.os.IWakeLockCallback;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public interface IPowerManager extends IInterface {
    public static final int GO_TO_SLEEP_FLAG_NO_DOZE = 1;
    public static final int GO_TO_SLEEP_REASON_ACCESSIBILITY = 7;
    public static final int GO_TO_SLEEP_REASON_APPLICATION = 0;
    public static final int GO_TO_SLEEP_REASON_FORCE_SUSPEND = 8;
    public static final int GO_TO_SLEEP_REASON_HDMI = 5;
    public static final int GO_TO_SLEEP_REASON_INATTENTIVE = 9;
    public static final int GO_TO_SLEEP_REASON_LID_SWITCH = 3;
    public static final int GO_TO_SLEEP_REASON_MAX = 10;
    public static final int GO_TO_SLEEP_REASON_MIN = 0;
    public static final int GO_TO_SLEEP_REASON_POWER_BUTTON = 4;
    public static final int GO_TO_SLEEP_REASON_QUIESCENT = 10;
    public static final int GO_TO_SLEEP_REASON_SLEEP_BUTTON = 6;
    public static final int GO_TO_SLEEP_REASON_TIMEOUT = 2;
    public static final int LOCATION_MODE_ALL_DISABLED_WHEN_SCREEN_OFF = 2;
    public static final int LOCATION_MODE_FOREGROUND_ONLY = 3;
    public static final int LOCATION_MODE_GPS_DISABLED_WHEN_SCREEN_OFF = 1;
    public static final int LOCATION_MODE_NO_CHANGE = 0;
    public static final int LOCATION_MODE_THROTTLE_REQUESTS_WHEN_SCREEN_OFF = 4;
    public static final int MAX_LOCATION_MODE = 4;
    public static final int MIN_LOCATION_MODE = 0;

    public static class Default implements IPowerManager {
        @Override // android.os.IPowerManager
        public void acquireLowPowerStandbyPorts(IBinder iBinder, List<LowPowerStandbyPortDescription> list) throws RemoteException {
        }

        @Override // android.os.IPowerManager
        public void acquireWakeLock(IBinder iBinder, int i, String str, String str2, WorkSource workSource, String str3, int i2, IWakeLockCallback iWakeLockCallback) throws RemoteException {
        }

        @Override // android.os.IPowerManager
        public void acquireWakeLockAsync(IBinder iBinder, int i, String str, String str2, WorkSource workSource, String str3) throws RemoteException {
        }

        @Override // android.os.IPowerManager
        public void acquireWakeLockWithUid(IBinder iBinder, int i, String str, String str2, int i2, int i3, IWakeLockCallback iWakeLockCallback) throws RemoteException {
        }

        @Override // android.os.IPowerManager
        public void addAdaptiveScreenOffTimeoutConfig(List<AdaptiveScreenOffTimeoutConfig> list) throws RemoteException {
        }

        @Override // android.os.IPowerManager
        public void addScreenTimeoutPolicyListener(int i, IScreenTimeoutPolicyListener iScreenTimeoutPolicyListener) throws RemoteException {
        }

        @Override // android.os.IPowerManager
        public boolean areAutoPowerSaveModesEnabled() throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.os.IPowerManager
        public void boostScreenBrightness(long j) throws RemoteException {
        }

        @Override // android.os.IPowerManager
        public void crash(String str) throws RemoteException {
        }

        @Override // android.os.IPowerManager
        public void forceLowPowerStandbyActive(boolean z) throws RemoteException {
        }

        @Override // android.os.IPowerManager
        public boolean forceSuspend() throws RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public List<LowPowerStandbyPortDescription> getActiveLowPowerStandbyPorts() throws RemoteException {
            return null;
        }

        @Override // android.os.IPowerManager
        public List<AdaptiveScreenOffTimeoutConfig> getAdaptiveScreenOffTimeoutConfig() throws RemoteException {
            return null;
        }

        @Override // android.os.IPowerManager
        public ParcelDuration getBatteryDischargePrediction() throws RemoteException {
            return null;
        }

        @Override // android.os.IPowerManager
        public float getBrightnessConstraint(int i, int i2) throws RemoteException {
            return 0.0f;
        }

        @Override // android.os.IPowerManager
        public float getCurrentBrightness(boolean z) throws RemoteException {
            return 0.0f;
        }

        @Override // android.os.IPowerManager
        public BatterySaverPolicyConfig getFullPowerSavePolicy() throws RemoteException {
            return null;
        }

        @Override // android.os.IPowerManager
        public int getLastShutdownReason() throws RemoteException {
            return 0;
        }

        @Override // android.os.IPowerManager
        public int getLastSleepReason() throws RemoteException {
            return 0;
        }

        @Override // android.os.IPowerManager
        public long getLastUserActivityTime(int i) throws RemoteException {
            return 0L;
        }

        @Override // android.os.IPowerManager
        public LowPowerStandbyPolicy getLowPowerStandbyPolicy() throws RemoteException {
            return null;
        }

        @Override // android.os.IPowerManager
        public String getPackageNameOnScreenCurtain() throws RemoteException {
            return null;
        }

        @Override // android.os.IPowerManager
        public int getPowerSaveModeTrigger() throws RemoteException {
            return 0;
        }

        @Override // android.os.IPowerManager
        public PowerSaveState getPowerSaveState(int i) throws RemoteException {
            return null;
        }

        @Override // android.os.IPowerManager
        public String[] getWakeLockPackageList() throws RemoteException {
            return null;
        }

        @Override // android.os.IPowerManager
        public void goToSleep(long j, int i, int i2) throws RemoteException {
        }

        @Override // android.os.IPowerManager
        public void goToSleepWithDisplayId(int i, long j, int i2, int i3) throws RemoteException {
        }

        @Override // android.os.IPowerManager
        public boolean isAmbientDisplayAvailable() throws RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public boolean isAmbientDisplaySuppressed() throws RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public boolean isAmbientDisplaySuppressedForToken(String str) throws RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public boolean isAmbientDisplaySuppressedForTokenByApp(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public boolean isBatteryDischargePredictionPersonalized() throws RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public boolean isBatterySaverSupported() throws RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public boolean isDeviceIdleMode() throws RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public boolean isDisplayInteractive(int i) throws RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public boolean isDozeAfterScreenOff() throws RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public boolean isExemptFromLowPowerStandby() throws RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public boolean isFeatureAllowedInLowPowerStandby(String str) throws RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public boolean isInteractive() throws RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public boolean isInteractiveForDisplay(int i) throws RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public boolean isLightDeviceIdleMode() throws RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public boolean isLowPowerStandbyEnabled() throws RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public boolean isLowPowerStandbySupported() throws RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public boolean isPowerSaveMode() throws RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public boolean isReasonAllowedInLowPowerStandby(int i) throws RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public boolean isScreenBrightnessBoosted() throws RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public boolean isScreenCurtainEnabled() throws RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public boolean isScreenCurtainEntryAvailable() throws RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public boolean isWakeLockLevelSupported(int i) throws RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public boolean isWakeLockLevelSupportedWithDisplayId(int i, int i2) throws RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public void nap(long j) throws RemoteException {
        }

        @Override // android.os.IPowerManager
        public void reboot(boolean z, String str, boolean z2) throws RemoteException {
        }

        @Override // android.os.IPowerManager
        public void rebootSafeMode(boolean z, boolean z2) throws RemoteException {
        }

        @Override // android.os.IPowerManager
        public void releaseLowPowerStandbyPorts(IBinder iBinder) throws RemoteException {
        }

        @Override // android.os.IPowerManager
        public void releaseWakeLock(IBinder iBinder, int i) throws RemoteException {
        }

        @Override // android.os.IPowerManager
        public void releaseWakeLockAsync(IBinder iBinder, int i) throws RemoteException {
        }

        @Override // android.os.IPowerManager
        public void removeAdaptiveScreenOffTimeoutConfig(List<String> list) throws RemoteException {
        }

        @Override // android.os.IPowerManager
        public void removeScreenTimeoutPolicyListener(int i, IScreenTimeoutPolicyListener iScreenTimeoutPolicyListener) throws RemoteException {
        }

        @Override // android.os.IPowerManager
        public boolean setAdaptivePowerSaveEnabled(boolean z) throws RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public boolean setAdaptivePowerSavePolicy(BatterySaverPolicyConfig batterySaverPolicyConfig) throws RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public void setAttentionLight(boolean z, int i) throws RemoteException {
        }

        @Override // android.os.IPowerManager
        public void setAutoBrightnessLimit(int i, int i2, boolean z) throws RemoteException {
        }

        @Override // android.os.IPowerManager
        public void setBatteryDischargePrediction(ParcelDuration parcelDuration, boolean z) throws RemoteException {
        }

        @Override // android.os.IPowerManager
        public void setCoverType(int i) throws RemoteException {
        }

        @Override // android.os.IPowerManager
        public void setDozeAfterScreenOff(boolean z) throws RemoteException {
        }

        @Override // android.os.IPowerManager
        public boolean setDynamicPowerSaveHint(boolean z, int i) throws RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public void setEarlyWakeUp(boolean z) throws RemoteException {
        }

        @Override // android.os.IPowerManager
        public void setFreezingScreenBrightness(boolean z) throws RemoteException {
        }

        @Override // android.os.IPowerManager
        public boolean setFullPowerSavePolicy(BatterySaverPolicyConfig batterySaverPolicyConfig) throws RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public void setHdrBrightnessLimit(IBinder iBinder, int i, int i2) throws RemoteException {
        }

        @Override // android.os.IPowerManager
        public void setLCDFlashMode(boolean z, IBinder iBinder) throws RemoteException {
        }

        @Override // android.os.IPowerManager
        public void setLowPowerStandbyActiveDuringMaintenance(boolean z) throws RemoteException {
        }

        @Override // android.os.IPowerManager
        public void setLowPowerStandbyEnabled(boolean z) throws RemoteException {
        }

        @Override // android.os.IPowerManager
        public void setLowPowerStandbyPolicy(LowPowerStandbyPolicy lowPowerStandbyPolicy) throws RemoteException {
        }

        @Override // android.os.IPowerManager
        public void setMasterBrightnessLimit(int i, int i2, int i3) throws RemoteException {
        }

        @Override // android.os.IPowerManager
        public void setPowerBoost(int i, int i2) throws RemoteException {
        }

        @Override // android.os.IPowerManager
        public void setPowerMode(int i, boolean z) throws RemoteException {
        }

        @Override // android.os.IPowerManager
        public boolean setPowerModeChecked(int i, boolean z) throws RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public boolean setPowerSaveModeEnabled(boolean z) throws RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public void setProximityDebounceTime(IBinder iBinder, int i, int i2) throws RemoteException {
        }

        @Override // android.os.IPowerManager
        public void setScreenBrightnessScaleFactor(float f, IBinder iBinder) throws RemoteException {
        }

        @Override // android.os.IPowerManager
        public void setScreenCurtainEnabled(IBinder iBinder, boolean z, int i) throws RemoteException {
        }

        @Override // android.os.IPowerManager
        public void setStayOnSetting(int i) throws RemoteException {
        }

        @Override // android.os.IPowerManager
        public void shutdown(boolean z, String str, boolean z2) throws RemoteException {
        }

        @Override // android.os.IPowerManager
        public void suppressAmbientDisplay(String str, boolean z) throws RemoteException {
        }

        @Override // android.os.IPowerManager
        public void switchForceLcdBacklightOffState() throws RemoteException {
        }

        @Override // android.os.IPowerManager
        public void updateCoverState(boolean z) throws RemoteException {
        }

        @Override // android.os.IPowerManager
        public void updateWakeLockCallback(IBinder iBinder, IWakeLockCallback iWakeLockCallback) throws RemoteException {
        }

        @Override // android.os.IPowerManager
        public void updateWakeLockUids(IBinder iBinder, int[] iArr) throws RemoteException {
        }

        @Override // android.os.IPowerManager
        public void updateWakeLockUidsAsync(IBinder iBinder, int[] iArr) throws RemoteException {
        }

        @Override // android.os.IPowerManager
        public void updateWakeLockWorkSource(IBinder iBinder, WorkSource workSource, String str) throws RemoteException {
        }

        @Override // android.os.IPowerManager
        public void userActivity(int i, long j, int i2, int i3) throws RemoteException {
        }

        @Override // android.os.IPowerManager
        public void wakeUp(long j, int i, String str, String str2) throws RemoteException {
        }

        @Override // android.os.IPowerManager
        public void wakeUpWithDisplayId(long j, int i, String str, String str2, int i2) throws RemoteException {
        }
    }

    void acquireLowPowerStandbyPorts(IBinder iBinder, List<LowPowerStandbyPortDescription> list) throws RemoteException;

    void acquireWakeLock(IBinder iBinder, int i, String str, String str2, WorkSource workSource, String str3, int i2, IWakeLockCallback iWakeLockCallback) throws RemoteException;

    void acquireWakeLockAsync(IBinder iBinder, int i, String str, String str2, WorkSource workSource, String str3) throws RemoteException;

    void acquireWakeLockWithUid(IBinder iBinder, int i, String str, String str2, int i2, int i3, IWakeLockCallback iWakeLockCallback) throws RemoteException;

    void addAdaptiveScreenOffTimeoutConfig(List<AdaptiveScreenOffTimeoutConfig> list) throws RemoteException;

    void addScreenTimeoutPolicyListener(int i, IScreenTimeoutPolicyListener iScreenTimeoutPolicyListener) throws RemoteException;

    boolean areAutoPowerSaveModesEnabled() throws RemoteException;

    void boostScreenBrightness(long j) throws RemoteException;

    void crash(String str) throws RemoteException;

    void forceLowPowerStandbyActive(boolean z) throws RemoteException;

    boolean forceSuspend() throws RemoteException;

    List<LowPowerStandbyPortDescription> getActiveLowPowerStandbyPorts() throws RemoteException;

    List<AdaptiveScreenOffTimeoutConfig> getAdaptiveScreenOffTimeoutConfig() throws RemoteException;

    ParcelDuration getBatteryDischargePrediction() throws RemoteException;

    float getBrightnessConstraint(int i, int i2) throws RemoteException;

    float getCurrentBrightness(boolean z) throws RemoteException;

    BatterySaverPolicyConfig getFullPowerSavePolicy() throws RemoteException;

    int getLastShutdownReason() throws RemoteException;

    int getLastSleepReason() throws RemoteException;

    long getLastUserActivityTime(int i) throws RemoteException;

    LowPowerStandbyPolicy getLowPowerStandbyPolicy() throws RemoteException;

    String getPackageNameOnScreenCurtain() throws RemoteException;

    int getPowerSaveModeTrigger() throws RemoteException;

    PowerSaveState getPowerSaveState(int i) throws RemoteException;

    String[] getWakeLockPackageList() throws RemoteException;

    void goToSleep(long j, int i, int i2) throws RemoteException;

    void goToSleepWithDisplayId(int i, long j, int i2, int i3) throws RemoteException;

    boolean isAmbientDisplayAvailable() throws RemoteException;

    boolean isAmbientDisplaySuppressed() throws RemoteException;

    boolean isAmbientDisplaySuppressedForToken(String str) throws RemoteException;

    boolean isAmbientDisplaySuppressedForTokenByApp(String str, int i) throws RemoteException;

    boolean isBatteryDischargePredictionPersonalized() throws RemoteException;

    boolean isBatterySaverSupported() throws RemoteException;

    boolean isDeviceIdleMode() throws RemoteException;

    boolean isDisplayInteractive(int i) throws RemoteException;

    boolean isDozeAfterScreenOff() throws RemoteException;

    boolean isExemptFromLowPowerStandby() throws RemoteException;

    boolean isFeatureAllowedInLowPowerStandby(String str) throws RemoteException;

    boolean isInteractive() throws RemoteException;

    boolean isInteractiveForDisplay(int i) throws RemoteException;

    boolean isLightDeviceIdleMode() throws RemoteException;

    boolean isLowPowerStandbyEnabled() throws RemoteException;

    boolean isLowPowerStandbySupported() throws RemoteException;

    boolean isPowerSaveMode() throws RemoteException;

    boolean isReasonAllowedInLowPowerStandby(int i) throws RemoteException;

    boolean isScreenBrightnessBoosted() throws RemoteException;

    boolean isScreenCurtainEnabled() throws RemoteException;

    boolean isScreenCurtainEntryAvailable() throws RemoteException;

    boolean isWakeLockLevelSupported(int i) throws RemoteException;

    boolean isWakeLockLevelSupportedWithDisplayId(int i, int i2) throws RemoteException;

    void nap(long j) throws RemoteException;

    void reboot(boolean z, String str, boolean z2) throws RemoteException;

    void rebootSafeMode(boolean z, boolean z2) throws RemoteException;

    void releaseLowPowerStandbyPorts(IBinder iBinder) throws RemoteException;

    void releaseWakeLock(IBinder iBinder, int i) throws RemoteException;

    void releaseWakeLockAsync(IBinder iBinder, int i) throws RemoteException;

    void removeAdaptiveScreenOffTimeoutConfig(List<String> list) throws RemoteException;

    void removeScreenTimeoutPolicyListener(int i, IScreenTimeoutPolicyListener iScreenTimeoutPolicyListener) throws RemoteException;

    boolean setAdaptivePowerSaveEnabled(boolean z) throws RemoteException;

    boolean setAdaptivePowerSavePolicy(BatterySaverPolicyConfig batterySaverPolicyConfig) throws RemoteException;

    void setAttentionLight(boolean z, int i) throws RemoteException;

    void setAutoBrightnessLimit(int i, int i2, boolean z) throws RemoteException;

    void setBatteryDischargePrediction(ParcelDuration parcelDuration, boolean z) throws RemoteException;

    void setCoverType(int i) throws RemoteException;

    void setDozeAfterScreenOff(boolean z) throws RemoteException;

    boolean setDynamicPowerSaveHint(boolean z, int i) throws RemoteException;

    void setEarlyWakeUp(boolean z) throws RemoteException;

    void setFreezingScreenBrightness(boolean z) throws RemoteException;

    boolean setFullPowerSavePolicy(BatterySaverPolicyConfig batterySaverPolicyConfig) throws RemoteException;

    void setHdrBrightnessLimit(IBinder iBinder, int i, int i2) throws RemoteException;

    void setLCDFlashMode(boolean z, IBinder iBinder) throws RemoteException;

    void setLowPowerStandbyActiveDuringMaintenance(boolean z) throws RemoteException;

    void setLowPowerStandbyEnabled(boolean z) throws RemoteException;

    void setLowPowerStandbyPolicy(LowPowerStandbyPolicy lowPowerStandbyPolicy) throws RemoteException;

    void setMasterBrightnessLimit(int i, int i2, int i3) throws RemoteException;

    void setPowerBoost(int i, int i2) throws RemoteException;

    void setPowerMode(int i, boolean z) throws RemoteException;

    boolean setPowerModeChecked(int i, boolean z) throws RemoteException;

    boolean setPowerSaveModeEnabled(boolean z) throws RemoteException;

    void setProximityDebounceTime(IBinder iBinder, int i, int i2) throws RemoteException;

    void setScreenBrightnessScaleFactor(float f, IBinder iBinder) throws RemoteException;

    void setScreenCurtainEnabled(IBinder iBinder, boolean z, int i) throws RemoteException;

    void setStayOnSetting(int i) throws RemoteException;

    void shutdown(boolean z, String str, boolean z2) throws RemoteException;

    void suppressAmbientDisplay(String str, boolean z) throws RemoteException;

    void switchForceLcdBacklightOffState() throws RemoteException;

    void updateCoverState(boolean z) throws RemoteException;

    void updateWakeLockCallback(IBinder iBinder, IWakeLockCallback iWakeLockCallback) throws RemoteException;

    void updateWakeLockUids(IBinder iBinder, int[] iArr) throws RemoteException;

    void updateWakeLockUidsAsync(IBinder iBinder, int[] iArr) throws RemoteException;

    void updateWakeLockWorkSource(IBinder iBinder, WorkSource workSource, String str) throws RemoteException;

    void userActivity(int i, long j, int i2, int i3) throws RemoteException;

    void wakeUp(long j, int i, String str, String str2) throws RemoteException;

    void wakeUpWithDisplayId(long j, int i, String str, String str2, int i2) throws RemoteException;

    public static abstract class Stub extends Binder implements IPowerManager {
        public static final String DESCRIPTOR = "android.os.IPowerManager";
        static final int TRANSACTION_acquireLowPowerStandbyPorts = 49;
        static final int TRANSACTION_acquireWakeLock = 1;
        static final int TRANSACTION_acquireWakeLockAsync = 60;
        static final int TRANSACTION_acquireWakeLockWithUid = 2;
        static final int TRANSACTION_addAdaptiveScreenOffTimeoutConfig = 90;
        static final int TRANSACTION_addScreenTimeoutPolicyListener = 12;
        static final int TRANSACTION_areAutoPowerSaveModesEnabled = 23;
        static final int TRANSACTION_boostScreenBrightness = 59;
        static final int TRANSACTION_crash = 55;
        static final int TRANSACTION_forceLowPowerStandbyActive = 43;
        static final int TRANSACTION_forceSuspend = 72;
        static final int TRANSACTION_getActiveLowPowerStandbyPorts = 51;
        static final int TRANSACTION_getAdaptiveScreenOffTimeoutConfig = 92;
        static final int TRANSACTION_getBatteryDischargePrediction = 35;
        static final int TRANSACTION_getBrightnessConstraint = 20;
        static final int TRANSACTION_getCurrentBrightness = 76;
        static final int TRANSACTION_getFullPowerSavePolicy = 28;
        static final int TRANSACTION_getLastShutdownReason = 56;
        static final int TRANSACTION_getLastSleepReason = 57;
        static final int TRANSACTION_getLastUserActivityTime = 71;
        static final int TRANSACTION_getLowPowerStandbyPolicy = 45;
        static final int TRANSACTION_getPackageNameOnScreenCurtain = 89;
        static final int TRANSACTION_getPowerSaveModeTrigger = 33;
        static final int TRANSACTION_getPowerSaveState = 25;
        static final int TRANSACTION_getWakeLockPackageList = 94;
        static final int TRANSACTION_goToSleep = 17;
        static final int TRANSACTION_goToSleepWithDisplayId = 18;
        static final int TRANSACTION_isAmbientDisplayAvailable = 66;
        static final int TRANSACTION_isAmbientDisplaySuppressed = 69;
        static final int TRANSACTION_isAmbientDisplaySuppressedForToken = 68;
        static final int TRANSACTION_isAmbientDisplaySuppressedForTokenByApp = 70;
        static final int TRANSACTION_isBatteryDischargePredictionPersonalized = 36;
        static final int TRANSACTION_isBatterySaverSupported = 27;
        static final int TRANSACTION_isDeviceIdleMode = 37;
        static final int TRANSACTION_isDisplayInteractive = 22;
        static final int TRANSACTION_isDozeAfterScreenOff = 93;
        static final int TRANSACTION_isExemptFromLowPowerStandby = 46;
        static final int TRANSACTION_isFeatureAllowedInLowPowerStandby = 48;
        static final int TRANSACTION_isInteractive = 21;
        static final int TRANSACTION_isInteractiveForDisplay = 82;
        static final int TRANSACTION_isLightDeviceIdleMode = 38;
        static final int TRANSACTION_isLowPowerStandbyEnabled = 40;
        static final int TRANSACTION_isLowPowerStandbySupported = 39;
        static final int TRANSACTION_isPowerSaveMode = 24;
        static final int TRANSACTION_isReasonAllowedInLowPowerStandby = 47;
        static final int TRANSACTION_isScreenBrightnessBoosted = 63;
        static final int TRANSACTION_isScreenCurtainEnabled = 87;
        static final int TRANSACTION_isScreenCurtainEntryAvailable = 88;
        static final int TRANSACTION_isWakeLockLevelSupported = 10;
        static final int TRANSACTION_isWakeLockLevelSupportedWithDisplayId = 11;
        static final int TRANSACTION_nap = 19;
        static final int TRANSACTION_reboot = 52;
        static final int TRANSACTION_rebootSafeMode = 53;
        static final int TRANSACTION_releaseLowPowerStandbyPorts = 50;
        static final int TRANSACTION_releaseWakeLock = 3;
        static final int TRANSACTION_releaseWakeLockAsync = 61;
        static final int TRANSACTION_removeAdaptiveScreenOffTimeoutConfig = 91;
        static final int TRANSACTION_removeScreenTimeoutPolicyListener = 13;
        static final int TRANSACTION_setAdaptivePowerSaveEnabled = 32;
        static final int TRANSACTION_setAdaptivePowerSavePolicy = 31;
        static final int TRANSACTION_setAttentionLight = 64;
        static final int TRANSACTION_setAutoBrightnessLimit = 73;
        static final int TRANSACTION_setBatteryDischargePrediction = 34;
        static final int TRANSACTION_setCoverType = 79;
        static final int TRANSACTION_setDozeAfterScreenOff = 65;
        static final int TRANSACTION_setDynamicPowerSaveHint = 30;
        static final int TRANSACTION_setEarlyWakeUp = 83;
        static final int TRANSACTION_setFreezingScreenBrightness = 84;
        static final int TRANSACTION_setFullPowerSavePolicy = 29;
        static final int TRANSACTION_setHdrBrightnessLimit = 75;
        static final int TRANSACTION_setLCDFlashMode = 85;
        static final int TRANSACTION_setLowPowerStandbyActiveDuringMaintenance = 42;
        static final int TRANSACTION_setLowPowerStandbyEnabled = 41;
        static final int TRANSACTION_setLowPowerStandbyPolicy = 44;
        static final int TRANSACTION_setMasterBrightnessLimit = 74;
        static final int TRANSACTION_setPowerBoost = 5;
        static final int TRANSACTION_setPowerMode = 6;
        static final int TRANSACTION_setPowerModeChecked = 7;
        static final int TRANSACTION_setPowerSaveModeEnabled = 26;
        static final int TRANSACTION_setProximityDebounceTime = 81;
        static final int TRANSACTION_setScreenBrightnessScaleFactor = 80;
        static final int TRANSACTION_setScreenCurtainEnabled = 86;
        static final int TRANSACTION_setStayOnSetting = 58;
        static final int TRANSACTION_shutdown = 54;
        static final int TRANSACTION_suppressAmbientDisplay = 67;
        static final int TRANSACTION_switchForceLcdBacklightOffState = 78;
        static final int TRANSACTION_updateCoverState = 77;
        static final int TRANSACTION_updateWakeLockCallback = 9;
        static final int TRANSACTION_updateWakeLockUids = 4;
        static final int TRANSACTION_updateWakeLockUidsAsync = 62;
        static final int TRANSACTION_updateWakeLockWorkSource = 8;
        static final int TRANSACTION_userActivity = 14;
        static final int TRANSACTION_wakeUp = 15;
        static final int TRANSACTION_wakeUpWithDisplayId = 16;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 93;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IPowerManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IPowerManager)) {
                return (IPowerManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "acquireWakeLock";
                case 2:
                    return "acquireWakeLockWithUid";
                case 3:
                    return "releaseWakeLock";
                case 4:
                    return "updateWakeLockUids";
                case 5:
                    return "setPowerBoost";
                case 6:
                    return "setPowerMode";
                case 7:
                    return "setPowerModeChecked";
                case 8:
                    return "updateWakeLockWorkSource";
                case 9:
                    return "updateWakeLockCallback";
                case 10:
                    return "isWakeLockLevelSupported";
                case 11:
                    return "isWakeLockLevelSupportedWithDisplayId";
                case 12:
                    return "addScreenTimeoutPolicyListener";
                case 13:
                    return "removeScreenTimeoutPolicyListener";
                case 14:
                    return "userActivity";
                case 15:
                    return "wakeUp";
                case 16:
                    return "wakeUpWithDisplayId";
                case 17:
                    return "goToSleep";
                case 18:
                    return "goToSleepWithDisplayId";
                case 19:
                    return "nap";
                case 20:
                    return "getBrightnessConstraint";
                case 21:
                    return "isInteractive";
                case 22:
                    return "isDisplayInteractive";
                case 23:
                    return "areAutoPowerSaveModesEnabled";
                case 24:
                    return "isPowerSaveMode";
                case 25:
                    return "getPowerSaveState";
                case 26:
                    return "setPowerSaveModeEnabled";
                case 27:
                    return "isBatterySaverSupported";
                case 28:
                    return "getFullPowerSavePolicy";
                case 29:
                    return "setFullPowerSavePolicy";
                case 30:
                    return "setDynamicPowerSaveHint";
                case 31:
                    return "setAdaptivePowerSavePolicy";
                case 32:
                    return "setAdaptivePowerSaveEnabled";
                case 33:
                    return "getPowerSaveModeTrigger";
                case 34:
                    return "setBatteryDischargePrediction";
                case 35:
                    return "getBatteryDischargePrediction";
                case 36:
                    return "isBatteryDischargePredictionPersonalized";
                case 37:
                    return "isDeviceIdleMode";
                case 38:
                    return "isLightDeviceIdleMode";
                case 39:
                    return "isLowPowerStandbySupported";
                case 40:
                    return "isLowPowerStandbyEnabled";
                case 41:
                    return "setLowPowerStandbyEnabled";
                case 42:
                    return "setLowPowerStandbyActiveDuringMaintenance";
                case 43:
                    return "forceLowPowerStandbyActive";
                case 44:
                    return "setLowPowerStandbyPolicy";
                case 45:
                    return "getLowPowerStandbyPolicy";
                case 46:
                    return "isExemptFromLowPowerStandby";
                case 47:
                    return "isReasonAllowedInLowPowerStandby";
                case 48:
                    return "isFeatureAllowedInLowPowerStandby";
                case 49:
                    return "acquireLowPowerStandbyPorts";
                case 50:
                    return "releaseLowPowerStandbyPorts";
                case 51:
                    return "getActiveLowPowerStandbyPorts";
                case 52:
                    return "reboot";
                case 53:
                    return "rebootSafeMode";
                case 54:
                    return UsbManager.USB_FUNCTION_SHUTDOWN;
                case 55:
                    return "crash";
                case 56:
                    return "getLastShutdownReason";
                case 57:
                    return "getLastSleepReason";
                case 58:
                    return "setStayOnSetting";
                case 59:
                    return "boostScreenBrightness";
                case 60:
                    return "acquireWakeLockAsync";
                case 61:
                    return "releaseWakeLockAsync";
                case 62:
                    return "updateWakeLockUidsAsync";
                case 63:
                    return "isScreenBrightnessBoosted";
                case 64:
                    return "setAttentionLight";
                case 65:
                    return "setDozeAfterScreenOff";
                case 66:
                    return "isAmbientDisplayAvailable";
                case 67:
                    return "suppressAmbientDisplay";
                case 68:
                    return "isAmbientDisplaySuppressedForToken";
                case 69:
                    return "isAmbientDisplaySuppressed";
                case 70:
                    return "isAmbientDisplaySuppressedForTokenByApp";
                case 71:
                    return "getLastUserActivityTime";
                case 72:
                    return "forceSuspend";
                case 73:
                    return "setAutoBrightnessLimit";
                case 74:
                    return "setMasterBrightnessLimit";
                case 75:
                    return "setHdrBrightnessLimit";
                case 76:
                    return "getCurrentBrightness";
                case 77:
                    return "updateCoverState";
                case 78:
                    return "switchForceLcdBacklightOffState";
                case 79:
                    return "setCoverType";
                case 80:
                    return "setScreenBrightnessScaleFactor";
                case 81:
                    return "setProximityDebounceTime";
                case 82:
                    return "isInteractiveForDisplay";
                case 83:
                    return "setEarlyWakeUp";
                case 84:
                    return "setFreezingScreenBrightness";
                case 85:
                    return "setLCDFlashMode";
                case 86:
                    return "setScreenCurtainEnabled";
                case 87:
                    return "isScreenCurtainEnabled";
                case 88:
                    return "isScreenCurtainEntryAvailable";
                case 89:
                    return "getPackageNameOnScreenCurtain";
                case 90:
                    return "addAdaptiveScreenOffTimeoutConfig";
                case 91:
                    return "removeAdaptiveScreenOffTimeoutConfig";
                case 92:
                    return "getAdaptiveScreenOffTimeoutConfig";
                case 93:
                    return "isDozeAfterScreenOff";
                case 94:
                    return "getWakeLockPackageList";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    int readInt = parcel.readInt();
                    String readString = parcel.readString();
                    String readString2 = parcel.readString();
                    WorkSource workSource = (WorkSource) parcel.readTypedObject(WorkSource.CREATOR);
                    String readString3 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    IWakeLockCallback asInterface = IWakeLockCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    acquireWakeLock(readStrongBinder, readInt, readString, readString2, workSource, readString3, readInt2, asInterface);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    IBinder readStrongBinder2 = parcel.readStrongBinder();
                    int readInt3 = parcel.readInt();
                    String readString4 = parcel.readString();
                    String readString5 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    IWakeLockCallback asInterface2 = IWakeLockCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    acquireWakeLockWithUid(readStrongBinder2, readInt3, readString4, readString5, readInt4, readInt5, asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    IBinder readStrongBinder3 = parcel.readStrongBinder();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    releaseWakeLock(readStrongBinder3, readInt6);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    IBinder readStrongBinder4 = parcel.readStrongBinder();
                    int[] createIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    updateWakeLockUids(readStrongBinder4, createIntArray);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setPowerBoost(readInt7, readInt8);
                    return true;
                case 6:
                    int readInt9 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setPowerMode(readInt9, readBoolean);
                    return true;
                case 7:
                    int readInt10 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean powerModeChecked = setPowerModeChecked(readInt10, readBoolean2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(powerModeChecked);
                    return true;
                case 8:
                    IBinder readStrongBinder5 = parcel.readStrongBinder();
                    WorkSource workSource2 = (WorkSource) parcel.readTypedObject(WorkSource.CREATOR);
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    updateWakeLockWorkSource(readStrongBinder5, workSource2, readString6);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    IBinder readStrongBinder6 = parcel.readStrongBinder();
                    IWakeLockCallback asInterface3 = IWakeLockCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    updateWakeLockCallback(readStrongBinder6, asInterface3);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isWakeLockLevelSupported = isWakeLockLevelSupported(readInt11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isWakeLockLevelSupported);
                    return true;
                case 11:
                    int readInt12 = parcel.readInt();
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isWakeLockLevelSupportedWithDisplayId = isWakeLockLevelSupportedWithDisplayId(readInt12, readInt13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isWakeLockLevelSupportedWithDisplayId);
                    return true;
                case 12:
                    int readInt14 = parcel.readInt();
                    IScreenTimeoutPolicyListener asInterface4 = IScreenTimeoutPolicyListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addScreenTimeoutPolicyListener(readInt14, asInterface4);
                    return true;
                case 13:
                    int readInt15 = parcel.readInt();
                    IScreenTimeoutPolicyListener asInterface5 = IScreenTimeoutPolicyListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeScreenTimeoutPolicyListener(readInt15, asInterface5);
                    return true;
                case 14:
                    int readInt16 = parcel.readInt();
                    long readLong = parcel.readLong();
                    int readInt17 = parcel.readInt();
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    userActivity(readInt16, readLong, readInt17, readInt18);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    long readLong2 = parcel.readLong();
                    int readInt19 = parcel.readInt();
                    String readString7 = parcel.readString();
                    String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    wakeUp(readLong2, readInt19, readString7, readString8);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    long readLong3 = parcel.readLong();
                    int readInt20 = parcel.readInt();
                    String readString9 = parcel.readString();
                    String readString10 = parcel.readString();
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    wakeUpWithDisplayId(readLong3, readInt20, readString9, readString10, readInt21);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    long readLong4 = parcel.readLong();
                    int readInt22 = parcel.readInt();
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    goToSleep(readLong4, readInt22, readInt23);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    int readInt24 = parcel.readInt();
                    long readLong5 = parcel.readLong();
                    int readInt25 = parcel.readInt();
                    int readInt26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    goToSleepWithDisplayId(readInt24, readLong5, readInt25, readInt26);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    long readLong6 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    nap(readLong6);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    int readInt27 = parcel.readInt();
                    int readInt28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    float brightnessConstraint = getBrightnessConstraint(readInt27, readInt28);
                    parcel2.writeNoException();
                    parcel2.writeFloat(brightnessConstraint);
                    return true;
                case 21:
                    boolean isInteractive = isInteractive();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isInteractive);
                    return true;
                case 22:
                    int readInt29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isDisplayInteractive = isDisplayInteractive(readInt29);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDisplayInteractive);
                    return true;
                case 23:
                    boolean areAutoPowerSaveModesEnabled = areAutoPowerSaveModesEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(areAutoPowerSaveModesEnabled);
                    return true;
                case 24:
                    boolean isPowerSaveMode = isPowerSaveMode();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPowerSaveMode);
                    return true;
                case 25:
                    int readInt30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    PowerSaveState powerSaveState = getPowerSaveState(readInt30);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(powerSaveState, 1);
                    return true;
                case 26:
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean powerSaveModeEnabled = setPowerSaveModeEnabled(readBoolean3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(powerSaveModeEnabled);
                    return true;
                case 27:
                    boolean isBatterySaverSupported = isBatterySaverSupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isBatterySaverSupported);
                    return true;
                case 28:
                    BatterySaverPolicyConfig fullPowerSavePolicy = getFullPowerSavePolicy();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(fullPowerSavePolicy, 1);
                    return true;
                case 29:
                    BatterySaverPolicyConfig batterySaverPolicyConfig = (BatterySaverPolicyConfig) parcel.readTypedObject(BatterySaverPolicyConfig.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean fullPowerSavePolicy2 = setFullPowerSavePolicy(batterySaverPolicyConfig);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(fullPowerSavePolicy2);
                    return true;
                case 30:
                    boolean readBoolean4 = parcel.readBoolean();
                    int readInt31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean dynamicPowerSaveHint = setDynamicPowerSaveHint(readBoolean4, readInt31);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(dynamicPowerSaveHint);
                    return true;
                case 31:
                    BatterySaverPolicyConfig batterySaverPolicyConfig2 = (BatterySaverPolicyConfig) parcel.readTypedObject(BatterySaverPolicyConfig.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean adaptivePowerSavePolicy = setAdaptivePowerSavePolicy(batterySaverPolicyConfig2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(adaptivePowerSavePolicy);
                    return true;
                case 32:
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean adaptivePowerSaveEnabled = setAdaptivePowerSaveEnabled(readBoolean5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(adaptivePowerSaveEnabled);
                    return true;
                case 33:
                    int powerSaveModeTrigger = getPowerSaveModeTrigger();
                    parcel2.writeNoException();
                    parcel2.writeInt(powerSaveModeTrigger);
                    return true;
                case 34:
                    ParcelDuration parcelDuration = (ParcelDuration) parcel.readTypedObject(ParcelDuration.CREATOR);
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setBatteryDischargePrediction(parcelDuration, readBoolean6);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    ParcelDuration batteryDischargePrediction = getBatteryDischargePrediction();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(batteryDischargePrediction, 1);
                    return true;
                case 36:
                    boolean isBatteryDischargePredictionPersonalized = isBatteryDischargePredictionPersonalized();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isBatteryDischargePredictionPersonalized);
                    return true;
                case 37:
                    boolean isDeviceIdleMode = isDeviceIdleMode();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDeviceIdleMode);
                    return true;
                case 38:
                    boolean isLightDeviceIdleMode = isLightDeviceIdleMode();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isLightDeviceIdleMode);
                    return true;
                case 39:
                    boolean isLowPowerStandbySupported = isLowPowerStandbySupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isLowPowerStandbySupported);
                    return true;
                case 40:
                    boolean isLowPowerStandbyEnabled = isLowPowerStandbyEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isLowPowerStandbyEnabled);
                    return true;
                case 41:
                    boolean readBoolean7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setLowPowerStandbyEnabled(readBoolean7);
                    parcel2.writeNoException();
                    return true;
                case 42:
                    boolean readBoolean8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setLowPowerStandbyActiveDuringMaintenance(readBoolean8);
                    parcel2.writeNoException();
                    return true;
                case 43:
                    boolean readBoolean9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    forceLowPowerStandbyActive(readBoolean9);
                    parcel2.writeNoException();
                    return true;
                case 44:
                    LowPowerStandbyPolicy lowPowerStandbyPolicy = (LowPowerStandbyPolicy) parcel.readTypedObject(LowPowerStandbyPolicy.CREATOR);
                    parcel.enforceNoDataAvail();
                    setLowPowerStandbyPolicy(lowPowerStandbyPolicy);
                    parcel2.writeNoException();
                    return true;
                case 45:
                    LowPowerStandbyPolicy lowPowerStandbyPolicy2 = getLowPowerStandbyPolicy();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(lowPowerStandbyPolicy2, 1);
                    return true;
                case 46:
                    boolean isExemptFromLowPowerStandby = isExemptFromLowPowerStandby();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isExemptFromLowPowerStandby);
                    return true;
                case 47:
                    int readInt32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isReasonAllowedInLowPowerStandby = isReasonAllowedInLowPowerStandby(readInt32);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isReasonAllowedInLowPowerStandby);
                    return true;
                case 48:
                    String readString11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isFeatureAllowedInLowPowerStandby = isFeatureAllowedInLowPowerStandby(readString11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isFeatureAllowedInLowPowerStandby);
                    return true;
                case 49:
                    IBinder readStrongBinder7 = parcel.readStrongBinder();
                    ArrayList createTypedArrayList = parcel.createTypedArrayList(LowPowerStandbyPortDescription.CREATOR);
                    parcel.enforceNoDataAvail();
                    acquireLowPowerStandbyPorts(readStrongBinder7, createTypedArrayList);
                    parcel2.writeNoException();
                    return true;
                case 50:
                    IBinder readStrongBinder8 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    releaseLowPowerStandbyPorts(readStrongBinder8);
                    parcel2.writeNoException();
                    return true;
                case 51:
                    List<LowPowerStandbyPortDescription> activeLowPowerStandbyPorts = getActiveLowPowerStandbyPorts();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(activeLowPowerStandbyPorts, 1);
                    return true;
                case 52:
                    boolean readBoolean10 = parcel.readBoolean();
                    String readString12 = parcel.readString();
                    boolean readBoolean11 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    reboot(readBoolean10, readString12, readBoolean11);
                    parcel2.writeNoException();
                    return true;
                case 53:
                    boolean readBoolean12 = parcel.readBoolean();
                    boolean readBoolean13 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    rebootSafeMode(readBoolean12, readBoolean13);
                    parcel2.writeNoException();
                    return true;
                case 54:
                    boolean readBoolean14 = parcel.readBoolean();
                    String readString13 = parcel.readString();
                    boolean readBoolean15 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    shutdown(readBoolean14, readString13, readBoolean15);
                    parcel2.writeNoException();
                    return true;
                case 55:
                    String readString14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    crash(readString14);
                    parcel2.writeNoException();
                    return true;
                case 56:
                    int lastShutdownReason = getLastShutdownReason();
                    parcel2.writeNoException();
                    parcel2.writeInt(lastShutdownReason);
                    return true;
                case 57:
                    int lastSleepReason = getLastSleepReason();
                    parcel2.writeNoException();
                    parcel2.writeInt(lastSleepReason);
                    return true;
                case 58:
                    int readInt33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setStayOnSetting(readInt33);
                    parcel2.writeNoException();
                    return true;
                case 59:
                    long readLong7 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boostScreenBrightness(readLong7);
                    parcel2.writeNoException();
                    return true;
                case 60:
                    IBinder readStrongBinder9 = parcel.readStrongBinder();
                    int readInt34 = parcel.readInt();
                    String readString15 = parcel.readString();
                    String readString16 = parcel.readString();
                    WorkSource workSource3 = (WorkSource) parcel.readTypedObject(WorkSource.CREATOR);
                    String readString17 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    acquireWakeLockAsync(readStrongBinder9, readInt34, readString15, readString16, workSource3, readString17);
                    return true;
                case 61:
                    IBinder readStrongBinder10 = parcel.readStrongBinder();
                    int readInt35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    releaseWakeLockAsync(readStrongBinder10, readInt35);
                    return true;
                case 62:
                    IBinder readStrongBinder11 = parcel.readStrongBinder();
                    int[] createIntArray2 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    updateWakeLockUidsAsync(readStrongBinder11, createIntArray2);
                    return true;
                case 63:
                    boolean isScreenBrightnessBoosted = isScreenBrightnessBoosted();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isScreenBrightnessBoosted);
                    return true;
                case 64:
                    boolean readBoolean16 = parcel.readBoolean();
                    int readInt36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setAttentionLight(readBoolean16, readInt36);
                    parcel2.writeNoException();
                    return true;
                case 65:
                    boolean readBoolean17 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setDozeAfterScreenOff(readBoolean17);
                    parcel2.writeNoException();
                    return true;
                case 66:
                    boolean isAmbientDisplayAvailable = isAmbientDisplayAvailable();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAmbientDisplayAvailable);
                    return true;
                case 67:
                    String readString18 = parcel.readString();
                    boolean readBoolean18 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    suppressAmbientDisplay(readString18, readBoolean18);
                    parcel2.writeNoException();
                    return true;
                case 68:
                    String readString19 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isAmbientDisplaySuppressedForToken = isAmbientDisplaySuppressedForToken(readString19);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAmbientDisplaySuppressedForToken);
                    return true;
                case 69:
                    boolean isAmbientDisplaySuppressed = isAmbientDisplaySuppressed();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAmbientDisplaySuppressed);
                    return true;
                case 70:
                    String readString20 = parcel.readString();
                    int readInt37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isAmbientDisplaySuppressedForTokenByApp = isAmbientDisplaySuppressedForTokenByApp(readString20, readInt37);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAmbientDisplaySuppressedForTokenByApp);
                    return true;
                case 71:
                    int readInt38 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    long lastUserActivityTime = getLastUserActivityTime(readInt38);
                    parcel2.writeNoException();
                    parcel2.writeLong(lastUserActivityTime);
                    return true;
                case 72:
                    boolean forceSuspend = forceSuspend();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(forceSuspend);
                    return true;
                case 73:
                    int readInt39 = parcel.readInt();
                    int readInt40 = parcel.readInt();
                    boolean readBoolean19 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAutoBrightnessLimit(readInt39, readInt40, readBoolean19);
                    parcel2.writeNoException();
                    return true;
                case 74:
                    int readInt41 = parcel.readInt();
                    int readInt42 = parcel.readInt();
                    int readInt43 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setMasterBrightnessLimit(readInt41, readInt42, readInt43);
                    parcel2.writeNoException();
                    return true;
                case 75:
                    IBinder readStrongBinder12 = parcel.readStrongBinder();
                    int readInt44 = parcel.readInt();
                    int readInt45 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setHdrBrightnessLimit(readStrongBinder12, readInt44, readInt45);
                    parcel2.writeNoException();
                    return true;
                case 76:
                    boolean readBoolean20 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    float currentBrightness = getCurrentBrightness(readBoolean20);
                    parcel2.writeNoException();
                    parcel2.writeFloat(currentBrightness);
                    return true;
                case 77:
                    boolean readBoolean21 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    updateCoverState(readBoolean21);
                    parcel2.writeNoException();
                    return true;
                case 78:
                    switchForceLcdBacklightOffState();
                    parcel2.writeNoException();
                    return true;
                case 79:
                    int readInt46 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setCoverType(readInt46);
                    parcel2.writeNoException();
                    return true;
                case 80:
                    float readFloat = parcel.readFloat();
                    IBinder readStrongBinder13 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    setScreenBrightnessScaleFactor(readFloat, readStrongBinder13);
                    parcel2.writeNoException();
                    return true;
                case 81:
                    IBinder readStrongBinder14 = parcel.readStrongBinder();
                    int readInt47 = parcel.readInt();
                    int readInt48 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setProximityDebounceTime(readStrongBinder14, readInt47, readInt48);
                    parcel2.writeNoException();
                    return true;
                case 82:
                    int readInt49 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isInteractiveForDisplay = isInteractiveForDisplay(readInt49);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isInteractiveForDisplay);
                    return true;
                case 83:
                    boolean readBoolean22 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setEarlyWakeUp(readBoolean22);
                    parcel2.writeNoException();
                    return true;
                case 84:
                    boolean readBoolean23 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setFreezingScreenBrightness(readBoolean23);
                    parcel2.writeNoException();
                    return true;
                case 85:
                    boolean readBoolean24 = parcel.readBoolean();
                    IBinder readStrongBinder15 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    setLCDFlashMode(readBoolean24, readStrongBinder15);
                    parcel2.writeNoException();
                    return true;
                case 86:
                    IBinder readStrongBinder16 = parcel.readStrongBinder();
                    boolean readBoolean25 = parcel.readBoolean();
                    int readInt50 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setScreenCurtainEnabled(readStrongBinder16, readBoolean25, readInt50);
                    parcel2.writeNoException();
                    return true;
                case 87:
                    boolean isScreenCurtainEnabled = isScreenCurtainEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isScreenCurtainEnabled);
                    return true;
                case 88:
                    boolean isScreenCurtainEntryAvailable = isScreenCurtainEntryAvailable();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isScreenCurtainEntryAvailable);
                    return true;
                case 89:
                    String packageNameOnScreenCurtain = getPackageNameOnScreenCurtain();
                    parcel2.writeNoException();
                    parcel2.writeString(packageNameOnScreenCurtain);
                    return true;
                case 90:
                    ArrayList createTypedArrayList2 = parcel.createTypedArrayList(AdaptiveScreenOffTimeoutConfig.CREATOR);
                    parcel.enforceNoDataAvail();
                    addAdaptiveScreenOffTimeoutConfig(createTypedArrayList2);
                    parcel2.writeNoException();
                    return true;
                case 91:
                    ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    removeAdaptiveScreenOffTimeoutConfig(createStringArrayList);
                    parcel2.writeNoException();
                    return true;
                case 92:
                    List<AdaptiveScreenOffTimeoutConfig> adaptiveScreenOffTimeoutConfig = getAdaptiveScreenOffTimeoutConfig();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(adaptiveScreenOffTimeoutConfig, 1);
                    return true;
                case 93:
                    boolean isDozeAfterScreenOff = isDozeAfterScreenOff();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDozeAfterScreenOff);
                    return true;
                case 94:
                    String[] wakeLockPackageList = getWakeLockPackageList();
                    parcel2.writeNoException();
                    parcel2.writeStringArray(wakeLockPackageList);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IPowerManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.os.IPowerManager
            public void acquireWakeLock(IBinder iBinder, int i, String str, String str2, WorkSource workSource, String str3, int i2, IWakeLockCallback iWakeLockCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(workSource, 0);
                    obtain.writeString(str3);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iWakeLockCallback);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void acquireWakeLockWithUid(IBinder iBinder, int i, String str, String str2, int i2, int i3, IWakeLockCallback iWakeLockCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeStrongInterface(iWakeLockCallback);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void releaseWakeLock(IBinder iBinder, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void updateWakeLockUids(IBinder iBinder, int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void setPowerBoost(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void setPowerMode(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean setPowerModeChecked(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void updateWakeLockWorkSource(IBinder iBinder, WorkSource workSource, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(workSource, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void updateWakeLockCallback(IBinder iBinder, IWakeLockCallback iWakeLockCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongInterface(iWakeLockCallback);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isWakeLockLevelSupported(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isWakeLockLevelSupportedWithDisplayId(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void addScreenTimeoutPolicyListener(int i, IScreenTimeoutPolicyListener iScreenTimeoutPolicyListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iScreenTimeoutPolicyListener);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void removeScreenTimeoutPolicyListener(int i, IScreenTimeoutPolicyListener iScreenTimeoutPolicyListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iScreenTimeoutPolicyListener);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void userActivity(int i, long j, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void wakeUp(long j, int i, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void wakeUpWithDisplayId(long j, int i, String str, String str2, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i2);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void goToSleep(long j, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void goToSleepWithDisplayId(int i, long j, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void nap(long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public float getBrightnessConstraint(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readFloat();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isInteractive() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isDisplayInteractive(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean areAutoPowerSaveModesEnabled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isPowerSaveMode() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public PowerSaveState getPowerSaveState(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return (PowerSaveState) obtain2.readTypedObject(PowerSaveState.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean setPowerSaveModeEnabled(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isBatterySaverSupported() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public BatterySaverPolicyConfig getFullPowerSavePolicy() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return (BatterySaverPolicyConfig) obtain2.readTypedObject(BatterySaverPolicyConfig.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean setFullPowerSavePolicy(BatterySaverPolicyConfig batterySaverPolicyConfig) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(batterySaverPolicyConfig, 0);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean setDynamicPowerSaveHint(boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean setAdaptivePowerSavePolicy(BatterySaverPolicyConfig batterySaverPolicyConfig) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(batterySaverPolicyConfig, 0);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean setAdaptivePowerSaveEnabled(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public int getPowerSaveModeTrigger() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void setBatteryDischargePrediction(ParcelDuration parcelDuration, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(parcelDuration, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public ParcelDuration getBatteryDischargePrediction() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParcelDuration) obtain2.readTypedObject(ParcelDuration.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isBatteryDischargePredictionPersonalized() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isDeviceIdleMode() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isLightDeviceIdleMode() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isLowPowerStandbySupported() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isLowPowerStandbyEnabled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void setLowPowerStandbyEnabled(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void setLowPowerStandbyActiveDuringMaintenance(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void forceLowPowerStandbyActive(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void setLowPowerStandbyPolicy(LowPowerStandbyPolicy lowPowerStandbyPolicy) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(lowPowerStandbyPolicy, 0);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public LowPowerStandbyPolicy getLowPowerStandbyPolicy() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                    return (LowPowerStandbyPolicy) obtain2.readTypedObject(LowPowerStandbyPolicy.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isExemptFromLowPowerStandby() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isReasonAllowedInLowPowerStandby(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isFeatureAllowedInLowPowerStandby(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void acquireLowPowerStandbyPorts(IBinder iBinder, List<LowPowerStandbyPortDescription> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void releaseLowPowerStandbyPorts(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public List<LowPowerStandbyPortDescription> getActiveLowPowerStandbyPorts() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(LowPowerStandbyPortDescription.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void reboot(boolean z, String str, boolean z2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void rebootSafeMode(boolean z, boolean z2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void shutdown(boolean z, String str, boolean z2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(54, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void crash(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public int getLastShutdownReason() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(56, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public int getLastSleepReason() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void setStayOnSetting(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(58, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void boostScreenBrightness(long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(59, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void acquireWakeLockAsync(IBinder iBinder, int i, String str, String str2, WorkSource workSource, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(workSource, 0);
                    obtain.writeString(str3);
                    this.mRemote.transact(60, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void releaseWakeLockAsync(IBinder iBinder, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(61, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void updateWakeLockUidsAsync(IBinder iBinder, int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(62, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isScreenBrightnessBoosted() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(63, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void setAttentionLight(boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(64, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void setDozeAfterScreenOff(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(65, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isAmbientDisplayAvailable() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(66, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void suppressAmbientDisplay(String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(67, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isAmbientDisplaySuppressedForToken(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(68, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isAmbientDisplaySuppressed() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(69, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isAmbientDisplaySuppressedForTokenByApp(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(70, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public long getLastUserActivityTime(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(71, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean forceSuspend() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(72, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void setAutoBrightnessLimit(int i, int i2, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(73, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void setMasterBrightnessLimit(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(74, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void setHdrBrightnessLimit(IBinder iBinder, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(75, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public float getCurrentBrightness(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(76, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readFloat();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void updateCoverState(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(77, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void switchForceLcdBacklightOffState() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(78, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void setCoverType(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(79, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void setScreenBrightnessScaleFactor(float f, IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeFloat(f);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(80, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void setProximityDebounceTime(IBinder iBinder, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(81, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isInteractiveForDisplay(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(82, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void setEarlyWakeUp(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(83, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void setFreezingScreenBrightness(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(84, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void setLCDFlashMode(boolean z, IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(85, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void setScreenCurtainEnabled(IBinder iBinder, boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(86, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isScreenCurtainEnabled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(87, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isScreenCurtainEntryAvailable() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(88, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public String getPackageNameOnScreenCurtain() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(89, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void addAdaptiveScreenOffTimeoutConfig(List<AdaptiveScreenOffTimeoutConfig> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(90, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void removeAdaptiveScreenOffTimeoutConfig(List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStringList(list);
                    this.mRemote.transact(91, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public List<AdaptiveScreenOffTimeoutConfig> getAdaptiveScreenOffTimeoutConfig() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(92, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(AdaptiveScreenOffTimeoutConfig.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isDozeAfterScreenOff() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(93, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public String[] getWakeLockPackageList() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(94, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }

    public static class LowPowerStandbyPolicy implements Parcelable {
        public static final Parcelable.Creator<LowPowerStandbyPolicy> CREATOR = new Parcelable.Creator<LowPowerStandbyPolicy>() { // from class: android.os.IPowerManager.LowPowerStandbyPolicy.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public LowPowerStandbyPolicy createFromParcel(Parcel parcel) {
                LowPowerStandbyPolicy lowPowerStandbyPolicy = new LowPowerStandbyPolicy();
                lowPowerStandbyPolicy.readFromParcel(parcel);
                return lowPowerStandbyPolicy;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public LowPowerStandbyPolicy[] newArray(int i) {
                return new LowPowerStandbyPolicy[i];
            }
        };
        public List<String> allowedFeatures;
        public int allowedReasons = 0;
        public List<String> exemptPackages;
        public String identifier;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            int dataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeString(this.identifier);
            parcel.writeStringList(this.exemptPackages);
            parcel.writeInt(this.allowedReasons);
            parcel.writeStringList(this.allowedFeatures);
            int dataPosition2 = parcel.dataPosition();
            parcel.setDataPosition(dataPosition);
            parcel.writeInt(dataPosition2 - dataPosition);
            parcel.setDataPosition(dataPosition2);
        }

        public final void readFromParcel(Parcel parcel) {
            int dataPosition = parcel.dataPosition();
            int readInt = parcel.readInt();
            try {
                if (readInt < 4) {
                    throw new BadParcelableException("Parcelable too small");
                }
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.identifier = parcel.readString();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.exemptPackages = parcel.createStringArrayList();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.allowedReasons = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.allowedFeatures = parcel.createStringArrayList();
                                if (dataPosition > Integer.MAX_VALUE - readInt) {
                                    throw new BadParcelableException("Overflow in the size of parcelable");
                                }
                            } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                throw new BadParcelableException("Overflow in the size of parcelable");
                            }
                        } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } catch (Throwable th) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                throw th;
            }
        }
    }

    public static class LowPowerStandbyPortDescription implements Parcelable {
        public static final Parcelable.Creator<LowPowerStandbyPortDescription> CREATOR = new Parcelable.Creator<LowPowerStandbyPortDescription>() { // from class: android.os.IPowerManager.LowPowerStandbyPortDescription.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public LowPowerStandbyPortDescription createFromParcel(Parcel parcel) {
                LowPowerStandbyPortDescription lowPowerStandbyPortDescription = new LowPowerStandbyPortDescription();
                lowPowerStandbyPortDescription.readFromParcel(parcel);
                return lowPowerStandbyPortDescription;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public LowPowerStandbyPortDescription[] newArray(int i) {
                return new LowPowerStandbyPortDescription[i];
            }
        };
        public byte[] localAddress;
        public int protocol = 0;
        public int portMatcher = 0;
        public int portNumber = 0;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            int dataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeInt(this.protocol);
            parcel.writeInt(this.portMatcher);
            parcel.writeInt(this.portNumber);
            parcel.writeByteArray(this.localAddress);
            int dataPosition2 = parcel.dataPosition();
            parcel.setDataPosition(dataPosition);
            parcel.writeInt(dataPosition2 - dataPosition);
            parcel.setDataPosition(dataPosition2);
        }

        public final void readFromParcel(Parcel parcel) {
            int dataPosition = parcel.dataPosition();
            int readInt = parcel.readInt();
            try {
                if (readInt < 4) {
                    throw new BadParcelableException("Parcelable too small");
                }
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.protocol = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.portMatcher = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.portNumber = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.localAddress = parcel.createByteArray();
                                if (dataPosition > Integer.MAX_VALUE - readInt) {
                                    throw new BadParcelableException("Overflow in the size of parcelable");
                                }
                            } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                throw new BadParcelableException("Overflow in the size of parcelable");
                            }
                        } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } catch (Throwable th) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                throw th;
            }
        }
    }

    public static class AdaptiveScreenOffTimeoutConfig implements Parcelable {
        public static final Parcelable.Creator<AdaptiveScreenOffTimeoutConfig> CREATOR = new Parcelable.Creator<AdaptiveScreenOffTimeoutConfig>() { // from class: android.os.IPowerManager.AdaptiveScreenOffTimeoutConfig.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public AdaptiveScreenOffTimeoutConfig createFromParcel(Parcel parcel) {
                AdaptiveScreenOffTimeoutConfig adaptiveScreenOffTimeoutConfig = new AdaptiveScreenOffTimeoutConfig();
                adaptiveScreenOffTimeoutConfig.readFromParcel(parcel);
                return adaptiveScreenOffTimeoutConfig;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public AdaptiveScreenOffTimeoutConfig[] newArray(int i) {
                return new AdaptiveScreenOffTimeoutConfig[i];
            }
        };
        public String packageName;
        public long screenOffTimeout = 0;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            int dataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeString(this.packageName);
            parcel.writeLong(this.screenOffTimeout);
            int dataPosition2 = parcel.dataPosition();
            parcel.setDataPosition(dataPosition);
            parcel.writeInt(dataPosition2 - dataPosition);
            parcel.setDataPosition(dataPosition2);
        }

        public final void readFromParcel(Parcel parcel) {
            int dataPosition = parcel.dataPosition();
            int readInt = parcel.readInt();
            try {
                if (readInt < 4) {
                    throw new BadParcelableException("Parcelable too small");
                }
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.packageName = parcel.readString();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.screenOffTimeout = parcel.readLong();
                        if (dataPosition > Integer.MAX_VALUE - readInt) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } catch (Throwable th) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                throw th;
            }
        }
    }
}
