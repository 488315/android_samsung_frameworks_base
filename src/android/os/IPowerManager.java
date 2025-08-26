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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IPowerManager)) {
                return (IPowerManager) iInterfaceQueryLocalInterface;
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
                    IBinder strongBinder = parcel.readStrongBinder();
                    int i3 = parcel.readInt();
                    String string = parcel.readString();
                    String string2 = parcel.readString();
                    WorkSource workSource = (WorkSource) parcel.readTypedObject(WorkSource.CREATOR);
                    String string3 = parcel.readString();
                    int i4 = parcel.readInt();
                    IWakeLockCallback iWakeLockCallbackAsInterface = IWakeLockCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    acquireWakeLock(strongBinder, i3, string, string2, workSource, string3, i4, iWakeLockCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    IBinder strongBinder2 = parcel.readStrongBinder();
                    int i5 = parcel.readInt();
                    String string4 = parcel.readString();
                    String string5 = parcel.readString();
                    int i6 = parcel.readInt();
                    int i7 = parcel.readInt();
                    IWakeLockCallback iWakeLockCallbackAsInterface2 = IWakeLockCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    acquireWakeLockWithUid(strongBinder2, i5, string4, string5, i6, i7, iWakeLockCallbackAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    IBinder strongBinder3 = parcel.readStrongBinder();
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    releaseWakeLock(strongBinder3, i8);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    IBinder strongBinder4 = parcel.readStrongBinder();
                    int[] iArrCreateIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    updateWakeLockUids(strongBinder4, iArrCreateIntArray);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int i9 = parcel.readInt();
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setPowerBoost(i9, i10);
                    return true;
                case 6:
                    int i11 = parcel.readInt();
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setPowerMode(i11, z);
                    return true;
                case 7:
                    int i12 = parcel.readInt();
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean powerModeChecked = setPowerModeChecked(i12, z2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(powerModeChecked);
                    return true;
                case 8:
                    IBinder strongBinder5 = parcel.readStrongBinder();
                    WorkSource workSource2 = (WorkSource) parcel.readTypedObject(WorkSource.CREATOR);
                    String string6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    updateWakeLockWorkSource(strongBinder5, workSource2, string6);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    IBinder strongBinder6 = parcel.readStrongBinder();
                    IWakeLockCallback iWakeLockCallbackAsInterface3 = IWakeLockCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    updateWakeLockCallback(strongBinder6, iWakeLockCallbackAsInterface3);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsWakeLockLevelSupported = isWakeLockLevelSupported(i13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsWakeLockLevelSupported);
                    return true;
                case 11:
                    int i14 = parcel.readInt();
                    int i15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsWakeLockLevelSupportedWithDisplayId = isWakeLockLevelSupportedWithDisplayId(i14, i15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsWakeLockLevelSupportedWithDisplayId);
                    return true;
                case 12:
                    int i16 = parcel.readInt();
                    IScreenTimeoutPolicyListener iScreenTimeoutPolicyListenerAsInterface = IScreenTimeoutPolicyListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addScreenTimeoutPolicyListener(i16, iScreenTimeoutPolicyListenerAsInterface);
                    return true;
                case 13:
                    int i17 = parcel.readInt();
                    IScreenTimeoutPolicyListener iScreenTimeoutPolicyListenerAsInterface2 = IScreenTimeoutPolicyListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeScreenTimeoutPolicyListener(i17, iScreenTimeoutPolicyListenerAsInterface2);
                    return true;
                case 14:
                    int i18 = parcel.readInt();
                    long j = parcel.readLong();
                    int i19 = parcel.readInt();
                    int i20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    userActivity(i18, j, i19, i20);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    long j2 = parcel.readLong();
                    int i21 = parcel.readInt();
                    String string7 = parcel.readString();
                    String string8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    wakeUp(j2, i21, string7, string8);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    long j3 = parcel.readLong();
                    int i22 = parcel.readInt();
                    String string9 = parcel.readString();
                    String string10 = parcel.readString();
                    int i23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    wakeUpWithDisplayId(j3, i22, string9, string10, i23);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    long j4 = parcel.readLong();
                    int i24 = parcel.readInt();
                    int i25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    goToSleep(j4, i24, i25);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    int i26 = parcel.readInt();
                    long j5 = parcel.readLong();
                    int i27 = parcel.readInt();
                    int i28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    goToSleepWithDisplayId(i26, j5, i27, i28);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    long j6 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    nap(j6);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    int i29 = parcel.readInt();
                    int i30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    float brightnessConstraint = getBrightnessConstraint(i29, i30);
                    parcel2.writeNoException();
                    parcel2.writeFloat(brightnessConstraint);
                    return true;
                case 21:
                    boolean zIsInteractive = isInteractive();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsInteractive);
                    return true;
                case 22:
                    int i31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsDisplayInteractive = isDisplayInteractive(i31);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsDisplayInteractive);
                    return true;
                case 23:
                    boolean zAreAutoPowerSaveModesEnabled = areAutoPowerSaveModesEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAreAutoPowerSaveModesEnabled);
                    return true;
                case 24:
                    boolean zIsPowerSaveMode = isPowerSaveMode();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsPowerSaveMode);
                    return true;
                case 25:
                    int i32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    PowerSaveState powerSaveState = getPowerSaveState(i32);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(powerSaveState, 1);
                    return true;
                case 26:
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean powerSaveModeEnabled = setPowerSaveModeEnabled(z3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(powerSaveModeEnabled);
                    return true;
                case 27:
                    boolean zIsBatterySaverSupported = isBatterySaverSupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsBatterySaverSupported);
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
                    boolean z4 = parcel.readBoolean();
                    int i33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean dynamicPowerSaveHint = setDynamicPowerSaveHint(z4, i33);
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
                    boolean z5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean adaptivePowerSaveEnabled = setAdaptivePowerSaveEnabled(z5);
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
                    boolean z6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setBatteryDischargePrediction(parcelDuration, z6);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    ParcelDuration batteryDischargePrediction = getBatteryDischargePrediction();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(batteryDischargePrediction, 1);
                    return true;
                case 36:
                    boolean zIsBatteryDischargePredictionPersonalized = isBatteryDischargePredictionPersonalized();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsBatteryDischargePredictionPersonalized);
                    return true;
                case 37:
                    boolean zIsDeviceIdleMode = isDeviceIdleMode();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsDeviceIdleMode);
                    return true;
                case 38:
                    boolean zIsLightDeviceIdleMode = isLightDeviceIdleMode();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsLightDeviceIdleMode);
                    return true;
                case 39:
                    boolean zIsLowPowerStandbySupported = isLowPowerStandbySupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsLowPowerStandbySupported);
                    return true;
                case 40:
                    boolean zIsLowPowerStandbyEnabled = isLowPowerStandbyEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsLowPowerStandbyEnabled);
                    return true;
                case 41:
                    boolean z7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setLowPowerStandbyEnabled(z7);
                    parcel2.writeNoException();
                    return true;
                case 42:
                    boolean z8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setLowPowerStandbyActiveDuringMaintenance(z8);
                    parcel2.writeNoException();
                    return true;
                case 43:
                    boolean z9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    forceLowPowerStandbyActive(z9);
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
                    boolean zIsExemptFromLowPowerStandby = isExemptFromLowPowerStandby();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsExemptFromLowPowerStandby);
                    return true;
                case 47:
                    int i34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsReasonAllowedInLowPowerStandby = isReasonAllowedInLowPowerStandby(i34);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsReasonAllowedInLowPowerStandby);
                    return true;
                case 48:
                    String string11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsFeatureAllowedInLowPowerStandby = isFeatureAllowedInLowPowerStandby(string11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsFeatureAllowedInLowPowerStandby);
                    return true;
                case 49:
                    IBinder strongBinder7 = parcel.readStrongBinder();
                    ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(LowPowerStandbyPortDescription.CREATOR);
                    parcel.enforceNoDataAvail();
                    acquireLowPowerStandbyPorts(strongBinder7, arrayListCreateTypedArrayList);
                    parcel2.writeNoException();
                    return true;
                case 50:
                    IBinder strongBinder8 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    releaseLowPowerStandbyPorts(strongBinder8);
                    parcel2.writeNoException();
                    return true;
                case 51:
                    List<LowPowerStandbyPortDescription> activeLowPowerStandbyPorts = getActiveLowPowerStandbyPorts();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(activeLowPowerStandbyPorts, 1);
                    return true;
                case 52:
                    boolean z10 = parcel.readBoolean();
                    String string12 = parcel.readString();
                    boolean z11 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    reboot(z10, string12, z11);
                    parcel2.writeNoException();
                    return true;
                case 53:
                    boolean z12 = parcel.readBoolean();
                    boolean z13 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    rebootSafeMode(z12, z13);
                    parcel2.writeNoException();
                    return true;
                case 54:
                    boolean z14 = parcel.readBoolean();
                    String string13 = parcel.readString();
                    boolean z15 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    shutdown(z14, string13, z15);
                    parcel2.writeNoException();
                    return true;
                case 55:
                    String string14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    crash(string14);
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
                    int i35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setStayOnSetting(i35);
                    parcel2.writeNoException();
                    return true;
                case 59:
                    long j7 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boostScreenBrightness(j7);
                    parcel2.writeNoException();
                    return true;
                case 60:
                    IBinder strongBinder9 = parcel.readStrongBinder();
                    int i36 = parcel.readInt();
                    String string15 = parcel.readString();
                    String string16 = parcel.readString();
                    WorkSource workSource3 = (WorkSource) parcel.readTypedObject(WorkSource.CREATOR);
                    String string17 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    acquireWakeLockAsync(strongBinder9, i36, string15, string16, workSource3, string17);
                    return true;
                case 61:
                    IBinder strongBinder10 = parcel.readStrongBinder();
                    int i37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    releaseWakeLockAsync(strongBinder10, i37);
                    return true;
                case 62:
                    IBinder strongBinder11 = parcel.readStrongBinder();
                    int[] iArrCreateIntArray2 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    updateWakeLockUidsAsync(strongBinder11, iArrCreateIntArray2);
                    return true;
                case 63:
                    boolean zIsScreenBrightnessBoosted = isScreenBrightnessBoosted();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsScreenBrightnessBoosted);
                    return true;
                case 64:
                    boolean z16 = parcel.readBoolean();
                    int i38 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setAttentionLight(z16, i38);
                    parcel2.writeNoException();
                    return true;
                case 65:
                    boolean z17 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setDozeAfterScreenOff(z17);
                    parcel2.writeNoException();
                    return true;
                case 66:
                    boolean zIsAmbientDisplayAvailable = isAmbientDisplayAvailable();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsAmbientDisplayAvailable);
                    return true;
                case 67:
                    String string18 = parcel.readString();
                    boolean z18 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    suppressAmbientDisplay(string18, z18);
                    parcel2.writeNoException();
                    return true;
                case 68:
                    String string19 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsAmbientDisplaySuppressedForToken = isAmbientDisplaySuppressedForToken(string19);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsAmbientDisplaySuppressedForToken);
                    return true;
                case 69:
                    boolean zIsAmbientDisplaySuppressed = isAmbientDisplaySuppressed();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsAmbientDisplaySuppressed);
                    return true;
                case 70:
                    String string20 = parcel.readString();
                    int i39 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsAmbientDisplaySuppressedForTokenByApp = isAmbientDisplaySuppressedForTokenByApp(string20, i39);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsAmbientDisplaySuppressedForTokenByApp);
                    return true;
                case 71:
                    int i40 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    long lastUserActivityTime = getLastUserActivityTime(i40);
                    parcel2.writeNoException();
                    parcel2.writeLong(lastUserActivityTime);
                    return true;
                case 72:
                    boolean zForceSuspend = forceSuspend();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zForceSuspend);
                    return true;
                case 73:
                    int i41 = parcel.readInt();
                    int i42 = parcel.readInt();
                    boolean z19 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAutoBrightnessLimit(i41, i42, z19);
                    parcel2.writeNoException();
                    return true;
                case 74:
                    int i43 = parcel.readInt();
                    int i44 = parcel.readInt();
                    int i45 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setMasterBrightnessLimit(i43, i44, i45);
                    parcel2.writeNoException();
                    return true;
                case 75:
                    IBinder strongBinder12 = parcel.readStrongBinder();
                    int i46 = parcel.readInt();
                    int i47 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setHdrBrightnessLimit(strongBinder12, i46, i47);
                    parcel2.writeNoException();
                    return true;
                case 76:
                    boolean z20 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    float currentBrightness = getCurrentBrightness(z20);
                    parcel2.writeNoException();
                    parcel2.writeFloat(currentBrightness);
                    return true;
                case 77:
                    boolean z21 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    updateCoverState(z21);
                    parcel2.writeNoException();
                    return true;
                case 78:
                    switchForceLcdBacklightOffState();
                    parcel2.writeNoException();
                    return true;
                case 79:
                    int i48 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setCoverType(i48);
                    parcel2.writeNoException();
                    return true;
                case 80:
                    float f = parcel.readFloat();
                    IBinder strongBinder13 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    setScreenBrightnessScaleFactor(f, strongBinder13);
                    parcel2.writeNoException();
                    return true;
                case 81:
                    IBinder strongBinder14 = parcel.readStrongBinder();
                    int i49 = parcel.readInt();
                    int i50 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setProximityDebounceTime(strongBinder14, i49, i50);
                    parcel2.writeNoException();
                    return true;
                case 82:
                    int i51 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsInteractiveForDisplay = isInteractiveForDisplay(i51);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsInteractiveForDisplay);
                    return true;
                case 83:
                    boolean z22 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setEarlyWakeUp(z22);
                    parcel2.writeNoException();
                    return true;
                case 84:
                    boolean z23 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setFreezingScreenBrightness(z23);
                    parcel2.writeNoException();
                    return true;
                case 85:
                    boolean z24 = parcel.readBoolean();
                    IBinder strongBinder15 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    setLCDFlashMode(z24, strongBinder15);
                    parcel2.writeNoException();
                    return true;
                case 86:
                    IBinder strongBinder16 = parcel.readStrongBinder();
                    boolean z25 = parcel.readBoolean();
                    int i52 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setScreenCurtainEnabled(strongBinder16, z25, i52);
                    parcel2.writeNoException();
                    return true;
                case 87:
                    boolean zIsScreenCurtainEnabled = isScreenCurtainEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsScreenCurtainEnabled);
                    return true;
                case 88:
                    boolean zIsScreenCurtainEntryAvailable = isScreenCurtainEntryAvailable();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsScreenCurtainEntryAvailable);
                    return true;
                case 89:
                    String packageNameOnScreenCurtain = getPackageNameOnScreenCurtain();
                    parcel2.writeNoException();
                    parcel2.writeString(packageNameOnScreenCurtain);
                    return true;
                case 90:
                    ArrayList arrayListCreateTypedArrayList2 = parcel.createTypedArrayList(AdaptiveScreenOffTimeoutConfig.CREATOR);
                    parcel.enforceNoDataAvail();
                    addAdaptiveScreenOffTimeoutConfig(arrayListCreateTypedArrayList2);
                    parcel2.writeNoException();
                    return true;
                case 91:
                    ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    removeAdaptiveScreenOffTimeoutConfig(arrayListCreateStringArrayList);
                    parcel2.writeNoException();
                    return true;
                case 92:
                    List<AdaptiveScreenOffTimeoutConfig> adaptiveScreenOffTimeoutConfig = getAdaptiveScreenOffTimeoutConfig();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(adaptiveScreenOffTimeoutConfig, 1);
                    return true;
                case 93:
                    boolean zIsDozeAfterScreenOff = isDozeAfterScreenOff();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsDozeAfterScreenOff);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeTypedObject(workSource, 0);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeStrongInterface(iWakeLockCallback);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void acquireWakeLockWithUid(IBinder iBinder, int i, String str, String str2, int i2, int i3, IWakeLockCallback iWakeLockCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeStrongInterface(iWakeLockCallback);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void releaseWakeLock(IBinder iBinder, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void updateWakeLockUids(IBinder iBinder, int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeIntArray(iArr);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void setPowerBoost(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void setPowerMode(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean setPowerModeChecked(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void updateWakeLockWorkSource(IBinder iBinder, WorkSource workSource, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(workSource, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void updateWakeLockCallback(IBinder iBinder, IWakeLockCallback iWakeLockCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeStrongInterface(iWakeLockCallback);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isWakeLockLevelSupported(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isWakeLockLevelSupportedWithDisplayId(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void addScreenTimeoutPolicyListener(int i, IScreenTimeoutPolicyListener iScreenTimeoutPolicyListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iScreenTimeoutPolicyListener);
                    this.mRemote.transact(12, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void removeScreenTimeoutPolicyListener(int i, IScreenTimeoutPolicyListener iScreenTimeoutPolicyListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iScreenTimeoutPolicyListener);
                    this.mRemote.transact(13, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void userActivity(int i, long j, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void wakeUp(long j, int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void wakeUpWithDisplayId(long j, int i, String str, String str2, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void goToSleep(long j, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void goToSleepWithDisplayId(int i, long j, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void nap(long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public float getBrightnessConstraint(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readFloat();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isInteractive() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isDisplayInteractive(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean areAutoPowerSaveModesEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isPowerSaveMode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public PowerSaveState getPowerSaveState(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (PowerSaveState) parcelObtain2.readTypedObject(PowerSaveState.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean setPowerSaveModeEnabled(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isBatterySaverSupported() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public BatterySaverPolicyConfig getFullPowerSavePolicy() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (BatterySaverPolicyConfig) parcelObtain2.readTypedObject(BatterySaverPolicyConfig.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean setFullPowerSavePolicy(BatterySaverPolicyConfig batterySaverPolicyConfig) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(batterySaverPolicyConfig, 0);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean setDynamicPowerSaveHint(boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean setAdaptivePowerSavePolicy(BatterySaverPolicyConfig batterySaverPolicyConfig) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(batterySaverPolicyConfig, 0);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean setAdaptivePowerSaveEnabled(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public int getPowerSaveModeTrigger() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void setBatteryDischargePrediction(ParcelDuration parcelDuration, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(parcelDuration, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public ParcelDuration getBatteryDischargePrediction() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParcelDuration) parcelObtain2.readTypedObject(ParcelDuration.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isBatteryDischargePredictionPersonalized() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isDeviceIdleMode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isLightDeviceIdleMode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isLowPowerStandbySupported() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isLowPowerStandbyEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(40, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void setLowPowerStandbyEnabled(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(41, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void setLowPowerStandbyActiveDuringMaintenance(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(42, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void forceLowPowerStandbyActive(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(43, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void setLowPowerStandbyPolicy(LowPowerStandbyPolicy lowPowerStandbyPolicy) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(lowPowerStandbyPolicy, 0);
                    this.mRemote.transact(44, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public LowPowerStandbyPolicy getLowPowerStandbyPolicy() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(45, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (LowPowerStandbyPolicy) parcelObtain2.readTypedObject(LowPowerStandbyPolicy.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isExemptFromLowPowerStandby() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(46, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isReasonAllowedInLowPowerStandby(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(47, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isFeatureAllowedInLowPowerStandby(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(48, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void acquireLowPowerStandbyPorts(IBinder iBinder, List<LowPowerStandbyPortDescription> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(49, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void releaseLowPowerStandbyPorts(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(50, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public List<LowPowerStandbyPortDescription> getActiveLowPowerStandbyPorts() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(51, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(LowPowerStandbyPortDescription.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void reboot(boolean z, String str, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z2);
                    this.mRemote.transact(52, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void rebootSafeMode(boolean z, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    this.mRemote.transact(53, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void shutdown(boolean z, String str, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z2);
                    this.mRemote.transact(54, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void crash(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(55, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public int getLastShutdownReason() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(56, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public int getLastSleepReason() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(57, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void setStayOnSetting(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(58, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void boostScreenBrightness(long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(59, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void acquireWakeLockAsync(IBinder iBinder, int i, String str, String str2, WorkSource workSource, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeTypedObject(workSource, 0);
                    parcelObtain.writeString(str3);
                    this.mRemote.transact(60, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void releaseWakeLockAsync(IBinder iBinder, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(61, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void updateWakeLockUidsAsync(IBinder iBinder, int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeIntArray(iArr);
                    this.mRemote.transact(62, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isScreenBrightnessBoosted() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(63, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void setAttentionLight(boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(64, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void setDozeAfterScreenOff(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(65, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isAmbientDisplayAvailable() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(66, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void suppressAmbientDisplay(String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(67, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isAmbientDisplaySuppressedForToken(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(68, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isAmbientDisplaySuppressed() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(69, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isAmbientDisplaySuppressedForTokenByApp(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(70, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public long getLastUserActivityTime(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(71, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean forceSuspend() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(72, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void setAutoBrightnessLimit(int i, int i2, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(73, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void setMasterBrightnessLimit(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(74, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void setHdrBrightnessLimit(IBinder iBinder, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(75, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public float getCurrentBrightness(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(76, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readFloat();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void updateCoverState(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(77, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void switchForceLcdBacklightOffState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(78, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void setCoverType(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(79, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void setScreenBrightnessScaleFactor(float f, IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeFloat(f);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(80, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void setProximityDebounceTime(IBinder iBinder, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(81, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isInteractiveForDisplay(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(82, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void setEarlyWakeUp(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(83, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void setFreezingScreenBrightness(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(84, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void setLCDFlashMode(boolean z, IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(85, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void setScreenCurtainEnabled(IBinder iBinder, boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(86, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isScreenCurtainEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(87, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isScreenCurtainEntryAvailable() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(88, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public String getPackageNameOnScreenCurtain() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(89, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void addAdaptiveScreenOffTimeoutConfig(List<AdaptiveScreenOffTimeoutConfig> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(90, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void removeAdaptiveScreenOffTimeoutConfig(List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(91, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public List<AdaptiveScreenOffTimeoutConfig> getAdaptiveScreenOffTimeoutConfig() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(92, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(AdaptiveScreenOffTimeoutConfig.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isDozeAfterScreenOff() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(93, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public String[] getWakeLockPackageList() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(94, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
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
            int iDataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeString(this.identifier);
            parcel.writeStringList(this.exemptPackages);
            parcel.writeInt(this.allowedReasons);
            parcel.writeStringList(this.allowedFeatures);
            int iDataPosition2 = parcel.dataPosition();
            parcel.setDataPosition(iDataPosition);
            parcel.writeInt(iDataPosition2 - iDataPosition);
            parcel.setDataPosition(iDataPosition2);
        }

        public final void readFromParcel(Parcel parcel) {
            int iDataPosition = parcel.dataPosition();
            int i = parcel.readInt();
            try {
                if (i < 4) {
                    throw new BadParcelableException("Parcelable too small");
                }
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.identifier = parcel.readString();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.exemptPackages = parcel.createStringArrayList();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.allowedReasons = parcel.readInt();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.allowedFeatures = parcel.createStringArrayList();
                                if (iDataPosition > Integer.MAX_VALUE - i) {
                                    throw new BadParcelableException("Overflow in the size of parcelable");
                                }
                            } else if (iDataPosition > Integer.MAX_VALUE - i) {
                                throw new BadParcelableException("Overflow in the size of parcelable");
                            }
                        } else if (iDataPosition > Integer.MAX_VALUE - i) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (iDataPosition > Integer.MAX_VALUE - i) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (iDataPosition > Integer.MAX_VALUE - i) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(iDataPosition + i);
            } catch (Throwable th) {
                if (iDataPosition > Integer.MAX_VALUE - i) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(iDataPosition + i);
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
            int iDataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeInt(this.protocol);
            parcel.writeInt(this.portMatcher);
            parcel.writeInt(this.portNumber);
            parcel.writeByteArray(this.localAddress);
            int iDataPosition2 = parcel.dataPosition();
            parcel.setDataPosition(iDataPosition);
            parcel.writeInt(iDataPosition2 - iDataPosition);
            parcel.setDataPosition(iDataPosition2);
        }

        public final void readFromParcel(Parcel parcel) {
            int iDataPosition = parcel.dataPosition();
            int i = parcel.readInt();
            try {
                if (i < 4) {
                    throw new BadParcelableException("Parcelable too small");
                }
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.protocol = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.portMatcher = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.portNumber = parcel.readInt();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.localAddress = parcel.createByteArray();
                                if (iDataPosition > Integer.MAX_VALUE - i) {
                                    throw new BadParcelableException("Overflow in the size of parcelable");
                                }
                            } else if (iDataPosition > Integer.MAX_VALUE - i) {
                                throw new BadParcelableException("Overflow in the size of parcelable");
                            }
                        } else if (iDataPosition > Integer.MAX_VALUE - i) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (iDataPosition > Integer.MAX_VALUE - i) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (iDataPosition > Integer.MAX_VALUE - i) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(iDataPosition + i);
            } catch (Throwable th) {
                if (iDataPosition > Integer.MAX_VALUE - i) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(iDataPosition + i);
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
            int iDataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeString(this.packageName);
            parcel.writeLong(this.screenOffTimeout);
            int iDataPosition2 = parcel.dataPosition();
            parcel.setDataPosition(iDataPosition);
            parcel.writeInt(iDataPosition2 - iDataPosition);
            parcel.setDataPosition(iDataPosition2);
        }

        public final void readFromParcel(Parcel parcel) {
            int iDataPosition = parcel.dataPosition();
            int i = parcel.readInt();
            try {
                if (i < 4) {
                    throw new BadParcelableException("Parcelable too small");
                }
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.packageName = parcel.readString();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.screenOffTimeout = parcel.readLong();
                        if (iDataPosition > Integer.MAX_VALUE - i) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (iDataPosition > Integer.MAX_VALUE - i) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (iDataPosition > Integer.MAX_VALUE - i) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(iDataPosition + i);
            } catch (Throwable th) {
                if (iDataPosition > Integer.MAX_VALUE - i) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(iDataPosition + i);
                throw th;
            }
        }
    }
}
