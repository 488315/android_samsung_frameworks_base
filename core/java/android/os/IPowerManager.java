package android.os;

import android.hardware.usb.UsbManager;

import java.util.List;

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

    void acquireLowPowerStandbyPorts(IBinder iBinder, List<LowPowerStandbyPortDescription> list)
            throws RemoteException;

    void acquireWakeLock(
            IBinder iBinder,
            int i,
            String str,
            String str2,
            WorkSource workSource,
            String str3,
            int i2,
            IWakeLockCallback iWakeLockCallback)
            throws RemoteException;

    void acquireWakeLockAsync(
            IBinder iBinder, int i, String str, String str2, WorkSource workSource, String str3)
            throws RemoteException;

    void acquireWakeLockWithUid(
            IBinder iBinder,
            int i,
            String str,
            String str2,
            int i2,
            int i3,
            IWakeLockCallback iWakeLockCallback)
            throws RemoteException;

    void addAdaptiveScreenOffTimeoutConfig(List<AdaptiveScreenOffTimeoutConfig> list)
            throws RemoteException;

    boolean areAutoPowerSaveModesEnabled() throws RemoteException;

    void boostScreenBrightness(long j) throws RemoteException;

    void crash(String str) throws RemoteException;

    void forceLowPowerStandbyActive(boolean z) throws RemoteException;

    boolean forceSuspend() throws RemoteException;

    List<LowPowerStandbyPortDescription> getActiveLowPowerStandbyPorts() throws RemoteException;

    List<AdaptiveScreenOffTimeoutConfig> getAdaptiveScreenOffTimeoutConfig() throws RemoteException;

    ParcelDuration getBatteryDischargePrediction() throws RemoteException;

    float getBrightnessConstraint(int i) throws RemoteException;

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

    void nap(long j) throws RemoteException;

    void reboot(boolean z, String str, boolean z2) throws RemoteException;

    void rebootSafeMode(boolean z, boolean z2) throws RemoteException;

    void releaseLowPowerStandbyPorts(IBinder iBinder) throws RemoteException;

    void releaseWakeLock(IBinder iBinder, int i) throws RemoteException;

    void releaseWakeLockAsync(IBinder iBinder, int i) throws RemoteException;

    void removeAdaptiveScreenOffTimeoutConfig(List<String> list) throws RemoteException;

    boolean setAdaptivePowerSaveEnabled(boolean z) throws RemoteException;

    boolean setAdaptivePowerSavePolicy(BatterySaverPolicyConfig batterySaverPolicyConfig)
            throws RemoteException;

    void setAttentionLight(boolean z, int i) throws RemoteException;

    void setAutoBrightnessLimit(int i, int i2, boolean z) throws RemoteException;

    void setBatteryDischargePrediction(ParcelDuration parcelDuration, boolean z)
            throws RemoteException;

    void setCoverType(int i) throws RemoteException;

    void setDozeAfterScreenOff(boolean z) throws RemoteException;

    boolean setDynamicPowerSaveHint(boolean z, int i) throws RemoteException;

    void setEarlyWakeUp(boolean z) throws RemoteException;

    void setFreezingScreenBrightness(boolean z) throws RemoteException;

    boolean setFullPowerSavePolicy(BatterySaverPolicyConfig batterySaverPolicyConfig)
            throws RemoteException;

    void setHdrBrightnessLimit(IBinder iBinder, int i, int i2) throws RemoteException;

    void setLCDFlashMode(boolean z, IBinder iBinder) throws RemoteException;

    void setLowPowerStandbyActiveDuringMaintenance(boolean z) throws RemoteException;

    void setLowPowerStandbyEnabled(boolean z) throws RemoteException;

    void setLowPowerStandbyPolicy(LowPowerStandbyPolicy lowPowerStandbyPolicy)
            throws RemoteException;

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

    void updateWakeLockCallback(IBinder iBinder, IWakeLockCallback iWakeLockCallback)
            throws RemoteException;

    void updateWakeLockUids(IBinder iBinder, int[] iArr) throws RemoteException;

    void updateWakeLockUidsAsync(IBinder iBinder, int[] iArr) throws RemoteException;

    void updateWakeLockWorkSource(IBinder iBinder, WorkSource workSource, String str)
            throws RemoteException;

    void userActivity(int i, long j, int i2, int i3) throws RemoteException;

    void wakeUp(long j, int i, String str, String str2) throws RemoteException;

    public static class Default implements IPowerManager {
        @Override // android.os.IPowerManager
        public void acquireWakeLock(
                IBinder lock,
                int flags,
                String tag,
                String packageName,
                WorkSource ws,
                String historyTag,
                int displayId,
                IWakeLockCallback callback)
                throws RemoteException {}

        @Override // android.os.IPowerManager
        public void acquireWakeLockWithUid(
                IBinder lock,
                int flags,
                String tag,
                String packageName,
                int uidtoblame,
                int displayId,
                IWakeLockCallback callback)
                throws RemoteException {}

        @Override // android.os.IPowerManager
        public void releaseWakeLock(IBinder lock, int flags) throws RemoteException {}

        @Override // android.os.IPowerManager
        public void updateWakeLockUids(IBinder lock, int[] uids) throws RemoteException {}

        @Override // android.os.IPowerManager
        public void setPowerBoost(int boost, int durationMs) throws RemoteException {}

        @Override // android.os.IPowerManager
        public void setPowerMode(int mode, boolean enabled) throws RemoteException {}

        @Override // android.os.IPowerManager
        public boolean setPowerModeChecked(int mode, boolean enabled) throws RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public void updateWakeLockWorkSource(IBinder lock, WorkSource ws, String historyTag)
                throws RemoteException {}

        @Override // android.os.IPowerManager
        public void updateWakeLockCallback(IBinder lock, IWakeLockCallback callback)
                throws RemoteException {}

        @Override // android.os.IPowerManager
        public boolean isWakeLockLevelSupported(int level) throws RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public void userActivity(int displayId, long time, int event, int flags)
                throws RemoteException {}

        @Override // android.os.IPowerManager
        public void wakeUp(long time, int reason, String details, String opPackageName)
                throws RemoteException {}

        @Override // android.os.IPowerManager
        public void goToSleep(long time, int reason, int flags) throws RemoteException {}

        @Override // android.os.IPowerManager
        public void goToSleepWithDisplayId(int displayId, long time, int reason, int flags)
                throws RemoteException {}

        @Override // android.os.IPowerManager
        public void nap(long time) throws RemoteException {}

        @Override // android.os.IPowerManager
        public float getBrightnessConstraint(int constraint) throws RemoteException {
            return 0.0f;
        }

        @Override // android.os.IPowerManager
        public boolean isInteractive() throws RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public boolean isDisplayInteractive(int displayId) throws RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public boolean areAutoPowerSaveModesEnabled() throws RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public boolean isPowerSaveMode() throws RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public PowerSaveState getPowerSaveState(int serviceType) throws RemoteException {
            return null;
        }

        @Override // android.os.IPowerManager
        public boolean setPowerSaveModeEnabled(boolean mode) throws RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public boolean isBatterySaverSupported() throws RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public BatterySaverPolicyConfig getFullPowerSavePolicy() throws RemoteException {
            return null;
        }

        @Override // android.os.IPowerManager
        public boolean setFullPowerSavePolicy(BatterySaverPolicyConfig config)
                throws RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public boolean setDynamicPowerSaveHint(boolean powerSaveHint, int disableThreshold)
                throws RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public boolean setAdaptivePowerSavePolicy(BatterySaverPolicyConfig config)
                throws RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public boolean setAdaptivePowerSaveEnabled(boolean enabled) throws RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public int getPowerSaveModeTrigger() throws RemoteException {
            return 0;
        }

        @Override // android.os.IPowerManager
        public void setBatteryDischargePrediction(
                ParcelDuration timeRemaining, boolean isCustomized) throws RemoteException {}

        @Override // android.os.IPowerManager
        public ParcelDuration getBatteryDischargePrediction() throws RemoteException {
            return null;
        }

        @Override // android.os.IPowerManager
        public boolean isBatteryDischargePredictionPersonalized() throws RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public boolean isDeviceIdleMode() throws RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public boolean isLightDeviceIdleMode() throws RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public boolean isLowPowerStandbySupported() throws RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public boolean isLowPowerStandbyEnabled() throws RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public void setLowPowerStandbyEnabled(boolean enabled) throws RemoteException {}

        @Override // android.os.IPowerManager
        public void setLowPowerStandbyActiveDuringMaintenance(boolean activeDuringMaintenance)
                throws RemoteException {}

        @Override // android.os.IPowerManager
        public void forceLowPowerStandbyActive(boolean active) throws RemoteException {}

        @Override // android.os.IPowerManager
        public void setLowPowerStandbyPolicy(LowPowerStandbyPolicy policy) throws RemoteException {}

        @Override // android.os.IPowerManager
        public LowPowerStandbyPolicy getLowPowerStandbyPolicy() throws RemoteException {
            return null;
        }

        @Override // android.os.IPowerManager
        public boolean isExemptFromLowPowerStandby() throws RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public boolean isReasonAllowedInLowPowerStandby(int reason) throws RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public boolean isFeatureAllowedInLowPowerStandby(String feature) throws RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public void acquireLowPowerStandbyPorts(
                IBinder token, List<LowPowerStandbyPortDescription> ports) throws RemoteException {}

        @Override // android.os.IPowerManager
        public void releaseLowPowerStandbyPorts(IBinder token) throws RemoteException {}

        @Override // android.os.IPowerManager
        public List<LowPowerStandbyPortDescription> getActiveLowPowerStandbyPorts()
                throws RemoteException {
            return null;
        }

        @Override // android.os.IPowerManager
        public void reboot(boolean confirm, String reason, boolean wait) throws RemoteException {}

        @Override // android.os.IPowerManager
        public void rebootSafeMode(boolean confirm, boolean wait) throws RemoteException {}

        @Override // android.os.IPowerManager
        public void shutdown(boolean confirm, String reason, boolean wait) throws RemoteException {}

        @Override // android.os.IPowerManager
        public void crash(String message) throws RemoteException {}

        @Override // android.os.IPowerManager
        public int getLastShutdownReason() throws RemoteException {
            return 0;
        }

        @Override // android.os.IPowerManager
        public int getLastSleepReason() throws RemoteException {
            return 0;
        }

        @Override // android.os.IPowerManager
        public void setStayOnSetting(int val) throws RemoteException {}

        @Override // android.os.IPowerManager
        public void boostScreenBrightness(long time) throws RemoteException {}

        @Override // android.os.IPowerManager
        public void acquireWakeLockAsync(
                IBinder lock,
                int flags,
                String tag,
                String packageName,
                WorkSource ws,
                String historyTag)
                throws RemoteException {}

        @Override // android.os.IPowerManager
        public void releaseWakeLockAsync(IBinder lock, int flags) throws RemoteException {}

        @Override // android.os.IPowerManager
        public void updateWakeLockUidsAsync(IBinder lock, int[] uids) throws RemoteException {}

        @Override // android.os.IPowerManager
        public boolean isScreenBrightnessBoosted() throws RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public void setAttentionLight(boolean on, int color) throws RemoteException {}

        @Override // android.os.IPowerManager
        public void setDozeAfterScreenOff(boolean on) throws RemoteException {}

        @Override // android.os.IPowerManager
        public boolean isAmbientDisplayAvailable() throws RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public void suppressAmbientDisplay(String token, boolean suppress) throws RemoteException {}

        @Override // android.os.IPowerManager
        public boolean isAmbientDisplaySuppressedForToken(String token) throws RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public boolean isAmbientDisplaySuppressed() throws RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public boolean isAmbientDisplaySuppressedForTokenByApp(String token, int appUid)
                throws RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public long getLastUserActivityTime(int userId) throws RemoteException {
            return 0L;
        }

        @Override // android.os.IPowerManager
        public boolean forceSuspend() throws RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public void setAutoBrightnessLimit(int lowerLimit, int upperLimit, boolean slowChange)
                throws RemoteException {}

        @Override // android.os.IPowerManager
        public void setMasterBrightnessLimit(
                int lowerLimit, int upperLimit, int brightnessLimitPeriod) throws RemoteException {}

        @Override // android.os.IPowerManager
        public void setHdrBrightnessLimit(IBinder lock, int upperLimit, int brightnsesLimitPeriod)
                throws RemoteException {}

        @Override // android.os.IPowerManager
        public float getCurrentBrightness(boolean ratio) throws RemoteException {
            return 0.0f;
        }

        @Override // android.os.IPowerManager
        public void updateCoverState(boolean closed) throws RemoteException {}

        @Override // android.os.IPowerManager
        public void switchForceLcdBacklightOffState() throws RemoteException {}

        @Override // android.os.IPowerManager
        public void setCoverType(int coverType) throws RemoteException {}

        @Override // android.os.IPowerManager
        public void setScreenBrightnessScaleFactor(float scaleFactor, IBinder binder)
                throws RemoteException {}

        @Override // android.os.IPowerManager
        public void setProximityDebounceTime(IBinder lock, int positive, int negative)
                throws RemoteException {}

        @Override // android.os.IPowerManager
        public boolean isInteractiveForDisplay(int displayId) throws RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public void setEarlyWakeUp(boolean enable) throws RemoteException {}

        @Override // android.os.IPowerManager
        public void setFreezingScreenBrightness(boolean freezing) throws RemoteException {}

        @Override // android.os.IPowerManager
        public void setLCDFlashMode(boolean eanble, IBinder binder) throws RemoteException {}

        @Override // android.os.IPowerManager
        public void setScreenCurtainEnabled(IBinder token, boolean enable, int displayState)
                throws RemoteException {}

        @Override // android.os.IPowerManager
        public boolean isScreenCurtainEnabled() throws RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public boolean isScreenCurtainEntryAvailable() throws RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public String getPackageNameOnScreenCurtain() throws RemoteException {
            return null;
        }

        @Override // android.os.IPowerManager
        public void addAdaptiveScreenOffTimeoutConfig(List<AdaptiveScreenOffTimeoutConfig> config)
                throws RemoteException {}

        @Override // android.os.IPowerManager
        public void removeAdaptiveScreenOffTimeoutConfig(List<String> list)
                throws RemoteException {}

        @Override // android.os.IPowerManager
        public List<AdaptiveScreenOffTimeoutConfig> getAdaptiveScreenOffTimeoutConfig()
                throws RemoteException {
            return null;
        }

        @Override // android.os.IPowerManager
        public boolean isDozeAfterScreenOff() throws RemoteException {
            return false;
        }

        @Override // android.os.IPowerManager
        public String[] getWakeLockPackageList() throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IPowerManager {
        public static final String DESCRIPTOR = "android.os.IPowerManager";
        static final int TRANSACTION_acquireLowPowerStandbyPorts = 45;
        static final int TRANSACTION_acquireWakeLock = 1;
        static final int TRANSACTION_acquireWakeLockAsync = 56;
        static final int TRANSACTION_acquireWakeLockWithUid = 2;
        static final int TRANSACTION_addAdaptiveScreenOffTimeoutConfig = 86;
        static final int TRANSACTION_areAutoPowerSaveModesEnabled = 19;
        static final int TRANSACTION_boostScreenBrightness = 55;
        static final int TRANSACTION_crash = 51;
        static final int TRANSACTION_forceLowPowerStandbyActive = 39;
        static final int TRANSACTION_forceSuspend = 68;
        static final int TRANSACTION_getActiveLowPowerStandbyPorts = 47;
        static final int TRANSACTION_getAdaptiveScreenOffTimeoutConfig = 88;
        static final int TRANSACTION_getBatteryDischargePrediction = 31;
        static final int TRANSACTION_getBrightnessConstraint = 16;
        static final int TRANSACTION_getCurrentBrightness = 72;
        static final int TRANSACTION_getFullPowerSavePolicy = 24;
        static final int TRANSACTION_getLastShutdownReason = 52;
        static final int TRANSACTION_getLastSleepReason = 53;
        static final int TRANSACTION_getLastUserActivityTime = 67;
        static final int TRANSACTION_getLowPowerStandbyPolicy = 41;
        static final int TRANSACTION_getPackageNameOnScreenCurtain = 85;
        static final int TRANSACTION_getPowerSaveModeTrigger = 29;
        static final int TRANSACTION_getPowerSaveState = 21;
        static final int TRANSACTION_getWakeLockPackageList = 90;
        static final int TRANSACTION_goToSleep = 13;
        static final int TRANSACTION_goToSleepWithDisplayId = 14;
        static final int TRANSACTION_isAmbientDisplayAvailable = 62;
        static final int TRANSACTION_isAmbientDisplaySuppressed = 65;
        static final int TRANSACTION_isAmbientDisplaySuppressedForToken = 64;
        static final int TRANSACTION_isAmbientDisplaySuppressedForTokenByApp = 66;
        static final int TRANSACTION_isBatteryDischargePredictionPersonalized = 32;
        static final int TRANSACTION_isBatterySaverSupported = 23;
        static final int TRANSACTION_isDeviceIdleMode = 33;
        static final int TRANSACTION_isDisplayInteractive = 18;
        static final int TRANSACTION_isDozeAfterScreenOff = 89;
        static final int TRANSACTION_isExemptFromLowPowerStandby = 42;
        static final int TRANSACTION_isFeatureAllowedInLowPowerStandby = 44;
        static final int TRANSACTION_isInteractive = 17;
        static final int TRANSACTION_isInteractiveForDisplay = 78;
        static final int TRANSACTION_isLightDeviceIdleMode = 34;
        static final int TRANSACTION_isLowPowerStandbyEnabled = 36;
        static final int TRANSACTION_isLowPowerStandbySupported = 35;
        static final int TRANSACTION_isPowerSaveMode = 20;
        static final int TRANSACTION_isReasonAllowedInLowPowerStandby = 43;
        static final int TRANSACTION_isScreenBrightnessBoosted = 59;
        static final int TRANSACTION_isScreenCurtainEnabled = 83;
        static final int TRANSACTION_isScreenCurtainEntryAvailable = 84;
        static final int TRANSACTION_isWakeLockLevelSupported = 10;
        static final int TRANSACTION_nap = 15;
        static final int TRANSACTION_reboot = 48;
        static final int TRANSACTION_rebootSafeMode = 49;
        static final int TRANSACTION_releaseLowPowerStandbyPorts = 46;
        static final int TRANSACTION_releaseWakeLock = 3;
        static final int TRANSACTION_releaseWakeLockAsync = 57;
        static final int TRANSACTION_removeAdaptiveScreenOffTimeoutConfig = 87;
        static final int TRANSACTION_setAdaptivePowerSaveEnabled = 28;
        static final int TRANSACTION_setAdaptivePowerSavePolicy = 27;
        static final int TRANSACTION_setAttentionLight = 60;
        static final int TRANSACTION_setAutoBrightnessLimit = 69;
        static final int TRANSACTION_setBatteryDischargePrediction = 30;
        static final int TRANSACTION_setCoverType = 75;
        static final int TRANSACTION_setDozeAfterScreenOff = 61;
        static final int TRANSACTION_setDynamicPowerSaveHint = 26;
        static final int TRANSACTION_setEarlyWakeUp = 79;
        static final int TRANSACTION_setFreezingScreenBrightness = 80;
        static final int TRANSACTION_setFullPowerSavePolicy = 25;
        static final int TRANSACTION_setHdrBrightnessLimit = 71;
        static final int TRANSACTION_setLCDFlashMode = 81;
        static final int TRANSACTION_setLowPowerStandbyActiveDuringMaintenance = 38;
        static final int TRANSACTION_setLowPowerStandbyEnabled = 37;
        static final int TRANSACTION_setLowPowerStandbyPolicy = 40;
        static final int TRANSACTION_setMasterBrightnessLimit = 70;
        static final int TRANSACTION_setPowerBoost = 5;
        static final int TRANSACTION_setPowerMode = 6;
        static final int TRANSACTION_setPowerModeChecked = 7;
        static final int TRANSACTION_setPowerSaveModeEnabled = 22;
        static final int TRANSACTION_setProximityDebounceTime = 77;
        static final int TRANSACTION_setScreenBrightnessScaleFactor = 76;
        static final int TRANSACTION_setScreenCurtainEnabled = 82;
        static final int TRANSACTION_setStayOnSetting = 54;
        static final int TRANSACTION_shutdown = 50;
        static final int TRANSACTION_suppressAmbientDisplay = 63;
        static final int TRANSACTION_switchForceLcdBacklightOffState = 74;
        static final int TRANSACTION_updateCoverState = 73;
        static final int TRANSACTION_updateWakeLockCallback = 9;
        static final int TRANSACTION_updateWakeLockUids = 4;
        static final int TRANSACTION_updateWakeLockUidsAsync = 58;
        static final int TRANSACTION_updateWakeLockWorkSource = 8;
        static final int TRANSACTION_userActivity = 11;
        static final int TRANSACTION_wakeUp = 12;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IPowerManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IPowerManager)) {
                return (IPowerManager) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
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
                    return "userActivity";
                case 12:
                    return "wakeUp";
                case 13:
                    return "goToSleep";
                case 14:
                    return "goToSleepWithDisplayId";
                case 15:
                    return "nap";
                case 16:
                    return "getBrightnessConstraint";
                case 17:
                    return "isInteractive";
                case 18:
                    return "isDisplayInteractive";
                case 19:
                    return "areAutoPowerSaveModesEnabled";
                case 20:
                    return "isPowerSaveMode";
                case 21:
                    return "getPowerSaveState";
                case 22:
                    return "setPowerSaveModeEnabled";
                case 23:
                    return "isBatterySaverSupported";
                case 24:
                    return "getFullPowerSavePolicy";
                case 25:
                    return "setFullPowerSavePolicy";
                case 26:
                    return "setDynamicPowerSaveHint";
                case 27:
                    return "setAdaptivePowerSavePolicy";
                case 28:
                    return "setAdaptivePowerSaveEnabled";
                case 29:
                    return "getPowerSaveModeTrigger";
                case 30:
                    return "setBatteryDischargePrediction";
                case 31:
                    return "getBatteryDischargePrediction";
                case 32:
                    return "isBatteryDischargePredictionPersonalized";
                case 33:
                    return "isDeviceIdleMode";
                case 34:
                    return "isLightDeviceIdleMode";
                case 35:
                    return "isLowPowerStandbySupported";
                case 36:
                    return "isLowPowerStandbyEnabled";
                case 37:
                    return "setLowPowerStandbyEnabled";
                case 38:
                    return "setLowPowerStandbyActiveDuringMaintenance";
                case 39:
                    return "forceLowPowerStandbyActive";
                case 40:
                    return "setLowPowerStandbyPolicy";
                case 41:
                    return "getLowPowerStandbyPolicy";
                case 42:
                    return "isExemptFromLowPowerStandby";
                case 43:
                    return "isReasonAllowedInLowPowerStandby";
                case 44:
                    return "isFeatureAllowedInLowPowerStandby";
                case 45:
                    return "acquireLowPowerStandbyPorts";
                case 46:
                    return "releaseLowPowerStandbyPorts";
                case 47:
                    return "getActiveLowPowerStandbyPorts";
                case 48:
                    return "reboot";
                case 49:
                    return "rebootSafeMode";
                case 50:
                    return UsbManager.USB_FUNCTION_SHUTDOWN;
                case 51:
                    return "crash";
                case 52:
                    return "getLastShutdownReason";
                case 53:
                    return "getLastSleepReason";
                case 54:
                    return "setStayOnSetting";
                case 55:
                    return "boostScreenBrightness";
                case 56:
                    return "acquireWakeLockAsync";
                case 57:
                    return "releaseWakeLockAsync";
                case 58:
                    return "updateWakeLockUidsAsync";
                case 59:
                    return "isScreenBrightnessBoosted";
                case 60:
                    return "setAttentionLight";
                case 61:
                    return "setDozeAfterScreenOff";
                case 62:
                    return "isAmbientDisplayAvailable";
                case 63:
                    return "suppressAmbientDisplay";
                case 64:
                    return "isAmbientDisplaySuppressedForToken";
                case 65:
                    return "isAmbientDisplaySuppressed";
                case 66:
                    return "isAmbientDisplaySuppressedForTokenByApp";
                case 67:
                    return "getLastUserActivityTime";
                case 68:
                    return "forceSuspend";
                case 69:
                    return "setAutoBrightnessLimit";
                case 70:
                    return "setMasterBrightnessLimit";
                case 71:
                    return "setHdrBrightnessLimit";
                case 72:
                    return "getCurrentBrightness";
                case 73:
                    return "updateCoverState";
                case 74:
                    return "switchForceLcdBacklightOffState";
                case 75:
                    return "setCoverType";
                case 76:
                    return "setScreenBrightnessScaleFactor";
                case 77:
                    return "setProximityDebounceTime";
                case 78:
                    return "isInteractiveForDisplay";
                case 79:
                    return "setEarlyWakeUp";
                case 80:
                    return "setFreezingScreenBrightness";
                case 81:
                    return "setLCDFlashMode";
                case 82:
                    return "setScreenCurtainEnabled";
                case 83:
                    return "isScreenCurtainEnabled";
                case 84:
                    return "isScreenCurtainEntryAvailable";
                case 85:
                    return "getPackageNameOnScreenCurtain";
                case 86:
                    return "addAdaptiveScreenOffTimeoutConfig";
                case 87:
                    return "removeAdaptiveScreenOffTimeoutConfig";
                case 88:
                    return "getAdaptiveScreenOffTimeoutConfig";
                case 89:
                    return "isDozeAfterScreenOff";
                case 90:
                    return "getWakeLockPackageList";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    IBinder _arg0 = data.readStrongBinder();
                    int _arg1 = data.readInt();
                    String _arg2 = data.readString();
                    String _arg3 = data.readString();
                    WorkSource _arg4 = (WorkSource) data.readTypedObject(WorkSource.CREATOR);
                    String _arg5 = data.readString();
                    int _arg6 = data.readInt();
                    IWakeLockCallback _arg7 =
                            IWakeLockCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    acquireWakeLock(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6, _arg7);
                    reply.writeNoException();
                    return true;
                case 2:
                    IBinder _arg02 = data.readStrongBinder();
                    int _arg12 = data.readInt();
                    String _arg22 = data.readString();
                    String _arg32 = data.readString();
                    int _arg42 = data.readInt();
                    int _arg52 = data.readInt();
                    IWakeLockCallback _arg62 =
                            IWakeLockCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    acquireWakeLockWithUid(_arg02, _arg12, _arg22, _arg32, _arg42, _arg52, _arg62);
                    reply.writeNoException();
                    return true;
                case 3:
                    IBinder _arg03 = data.readStrongBinder();
                    int _arg13 = data.readInt();
                    data.enforceNoDataAvail();
                    releaseWakeLock(_arg03, _arg13);
                    reply.writeNoException();
                    return true;
                case 4:
                    IBinder _arg04 = data.readStrongBinder();
                    int[] _arg14 = data.createIntArray();
                    data.enforceNoDataAvail();
                    updateWakeLockUids(_arg04, _arg14);
                    reply.writeNoException();
                    return true;
                case 5:
                    int _arg05 = data.readInt();
                    int _arg15 = data.readInt();
                    data.enforceNoDataAvail();
                    setPowerBoost(_arg05, _arg15);
                    return true;
                case 6:
                    int _arg06 = data.readInt();
                    boolean _arg16 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setPowerMode(_arg06, _arg16);
                    return true;
                case 7:
                    int _arg07 = data.readInt();
                    boolean _arg17 = data.readBoolean();
                    data.enforceNoDataAvail();
                    boolean _result = setPowerModeChecked(_arg07, _arg17);
                    reply.writeNoException();
                    reply.writeBoolean(_result);
                    return true;
                case 8:
                    IBinder _arg08 = data.readStrongBinder();
                    WorkSource _arg18 = (WorkSource) data.readTypedObject(WorkSource.CREATOR);
                    String _arg23 = data.readString();
                    data.enforceNoDataAvail();
                    updateWakeLockWorkSource(_arg08, _arg18, _arg23);
                    reply.writeNoException();
                    return true;
                case 9:
                    IBinder _arg09 = data.readStrongBinder();
                    IWakeLockCallback _arg19 =
                            IWakeLockCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    updateWakeLockCallback(_arg09, _arg19);
                    reply.writeNoException();
                    return true;
                case 10:
                    int _arg010 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result2 = isWakeLockLevelSupported(_arg010);
                    reply.writeNoException();
                    reply.writeBoolean(_result2);
                    return true;
                case 11:
                    int _arg011 = data.readInt();
                    long _arg110 = data.readLong();
                    int _arg24 = data.readInt();
                    int _arg33 = data.readInt();
                    data.enforceNoDataAvail();
                    userActivity(_arg011, _arg110, _arg24, _arg33);
                    reply.writeNoException();
                    return true;
                case 12:
                    long _arg012 = data.readLong();
                    int _arg111 = data.readInt();
                    String _arg25 = data.readString();
                    String _arg34 = data.readString();
                    data.enforceNoDataAvail();
                    wakeUp(_arg012, _arg111, _arg25, _arg34);
                    reply.writeNoException();
                    return true;
                case 13:
                    long _arg013 = data.readLong();
                    int _arg112 = data.readInt();
                    int _arg26 = data.readInt();
                    data.enforceNoDataAvail();
                    goToSleep(_arg013, _arg112, _arg26);
                    reply.writeNoException();
                    return true;
                case 14:
                    int _arg014 = data.readInt();
                    long _arg113 = data.readLong();
                    int _arg27 = data.readInt();
                    int _arg35 = data.readInt();
                    data.enforceNoDataAvail();
                    goToSleepWithDisplayId(_arg014, _arg113, _arg27, _arg35);
                    reply.writeNoException();
                    return true;
                case 15:
                    long _arg015 = data.readLong();
                    data.enforceNoDataAvail();
                    nap(_arg015);
                    reply.writeNoException();
                    return true;
                case 16:
                    int _arg016 = data.readInt();
                    data.enforceNoDataAvail();
                    float _result3 = getBrightnessConstraint(_arg016);
                    reply.writeNoException();
                    reply.writeFloat(_result3);
                    return true;
                case 17:
                    boolean _result4 = isInteractive();
                    reply.writeNoException();
                    reply.writeBoolean(_result4);
                    return true;
                case 18:
                    int _arg017 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result5 = isDisplayInteractive(_arg017);
                    reply.writeNoException();
                    reply.writeBoolean(_result5);
                    return true;
                case 19:
                    boolean _result6 = areAutoPowerSaveModesEnabled();
                    reply.writeNoException();
                    reply.writeBoolean(_result6);
                    return true;
                case 20:
                    boolean _result7 = isPowerSaveMode();
                    reply.writeNoException();
                    reply.writeBoolean(_result7);
                    return true;
                case 21:
                    int _arg018 = data.readInt();
                    data.enforceNoDataAvail();
                    PowerSaveState _result8 = getPowerSaveState(_arg018);
                    reply.writeNoException();
                    reply.writeTypedObject(_result8, 1);
                    return true;
                case 22:
                    boolean _arg019 = data.readBoolean();
                    data.enforceNoDataAvail();
                    boolean _result9 = setPowerSaveModeEnabled(_arg019);
                    reply.writeNoException();
                    reply.writeBoolean(_result9);
                    return true;
                case 23:
                    boolean _result10 = isBatterySaverSupported();
                    reply.writeNoException();
                    reply.writeBoolean(_result10);
                    return true;
                case 24:
                    BatterySaverPolicyConfig _result11 = getFullPowerSavePolicy();
                    reply.writeNoException();
                    reply.writeTypedObject(_result11, 1);
                    return true;
                case 25:
                    BatterySaverPolicyConfig _arg020 =
                            (BatterySaverPolicyConfig)
                                    data.readTypedObject(BatterySaverPolicyConfig.CREATOR);
                    data.enforceNoDataAvail();
                    boolean _result12 = setFullPowerSavePolicy(_arg020);
                    reply.writeNoException();
                    reply.writeBoolean(_result12);
                    return true;
                case 26:
                    boolean _arg021 = data.readBoolean();
                    int _arg114 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result13 = setDynamicPowerSaveHint(_arg021, _arg114);
                    reply.writeNoException();
                    reply.writeBoolean(_result13);
                    return true;
                case 27:
                    BatterySaverPolicyConfig _arg022 =
                            (BatterySaverPolicyConfig)
                                    data.readTypedObject(BatterySaverPolicyConfig.CREATOR);
                    data.enforceNoDataAvail();
                    boolean _result14 = setAdaptivePowerSavePolicy(_arg022);
                    reply.writeNoException();
                    reply.writeBoolean(_result14);
                    return true;
                case 28:
                    boolean _arg023 = data.readBoolean();
                    data.enforceNoDataAvail();
                    boolean _result15 = setAdaptivePowerSaveEnabled(_arg023);
                    reply.writeNoException();
                    reply.writeBoolean(_result15);
                    return true;
                case 29:
                    int _result16 = getPowerSaveModeTrigger();
                    reply.writeNoException();
                    reply.writeInt(_result16);
                    return true;
                case 30:
                    ParcelDuration _arg024 =
                            (ParcelDuration) data.readTypedObject(ParcelDuration.CREATOR);
                    boolean _arg115 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setBatteryDischargePrediction(_arg024, _arg115);
                    reply.writeNoException();
                    return true;
                case 31:
                    ParcelDuration _result17 = getBatteryDischargePrediction();
                    reply.writeNoException();
                    reply.writeTypedObject(_result17, 1);
                    return true;
                case 32:
                    boolean _result18 = isBatteryDischargePredictionPersonalized();
                    reply.writeNoException();
                    reply.writeBoolean(_result18);
                    return true;
                case 33:
                    boolean _result19 = isDeviceIdleMode();
                    reply.writeNoException();
                    reply.writeBoolean(_result19);
                    return true;
                case 34:
                    boolean _result20 = isLightDeviceIdleMode();
                    reply.writeNoException();
                    reply.writeBoolean(_result20);
                    return true;
                case 35:
                    boolean _result21 = isLowPowerStandbySupported();
                    reply.writeNoException();
                    reply.writeBoolean(_result21);
                    return true;
                case 36:
                    boolean _result22 = isLowPowerStandbyEnabled();
                    reply.writeNoException();
                    reply.writeBoolean(_result22);
                    return true;
                case 37:
                    boolean _arg025 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setLowPowerStandbyEnabled(_arg025);
                    reply.writeNoException();
                    return true;
                case 38:
                    boolean _arg026 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setLowPowerStandbyActiveDuringMaintenance(_arg026);
                    reply.writeNoException();
                    return true;
                case 39:
                    boolean _arg027 = data.readBoolean();
                    data.enforceNoDataAvail();
                    forceLowPowerStandbyActive(_arg027);
                    reply.writeNoException();
                    return true;
                case 40:
                    LowPowerStandbyPolicy _arg028 =
                            (LowPowerStandbyPolicy)
                                    data.readTypedObject(LowPowerStandbyPolicy.CREATOR);
                    data.enforceNoDataAvail();
                    setLowPowerStandbyPolicy(_arg028);
                    reply.writeNoException();
                    return true;
                case 41:
                    LowPowerStandbyPolicy _result23 = getLowPowerStandbyPolicy();
                    reply.writeNoException();
                    reply.writeTypedObject(_result23, 1);
                    return true;
                case 42:
                    boolean _result24 = isExemptFromLowPowerStandby();
                    reply.writeNoException();
                    reply.writeBoolean(_result24);
                    return true;
                case 43:
                    int _arg029 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result25 = isReasonAllowedInLowPowerStandby(_arg029);
                    reply.writeNoException();
                    reply.writeBoolean(_result25);
                    return true;
                case 44:
                    String _arg030 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result26 = isFeatureAllowedInLowPowerStandby(_arg030);
                    reply.writeNoException();
                    reply.writeBoolean(_result26);
                    return true;
                case 45:
                    IBinder _arg031 = data.readStrongBinder();
                    List<LowPowerStandbyPortDescription> _arg116 =
                            data.createTypedArrayList(LowPowerStandbyPortDescription.CREATOR);
                    data.enforceNoDataAvail();
                    acquireLowPowerStandbyPorts(_arg031, _arg116);
                    reply.writeNoException();
                    return true;
                case 46:
                    IBinder _arg032 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    releaseLowPowerStandbyPorts(_arg032);
                    reply.writeNoException();
                    return true;
                case 47:
                    List<LowPowerStandbyPortDescription> _result27 =
                            getActiveLowPowerStandbyPorts();
                    reply.writeNoException();
                    reply.writeTypedList(_result27, 1);
                    return true;
                case 48:
                    boolean _arg033 = data.readBoolean();
                    String _arg117 = data.readString();
                    boolean _arg28 = data.readBoolean();
                    data.enforceNoDataAvail();
                    reboot(_arg033, _arg117, _arg28);
                    reply.writeNoException();
                    return true;
                case 49:
                    boolean _arg034 = data.readBoolean();
                    boolean _arg118 = data.readBoolean();
                    data.enforceNoDataAvail();
                    rebootSafeMode(_arg034, _arg118);
                    reply.writeNoException();
                    return true;
                case 50:
                    boolean _arg035 = data.readBoolean();
                    String _arg119 = data.readString();
                    boolean _arg29 = data.readBoolean();
                    data.enforceNoDataAvail();
                    shutdown(_arg035, _arg119, _arg29);
                    reply.writeNoException();
                    return true;
                case 51:
                    String _arg036 = data.readString();
                    data.enforceNoDataAvail();
                    crash(_arg036);
                    reply.writeNoException();
                    return true;
                case 52:
                    int _result28 = getLastShutdownReason();
                    reply.writeNoException();
                    reply.writeInt(_result28);
                    return true;
                case 53:
                    int _result29 = getLastSleepReason();
                    reply.writeNoException();
                    reply.writeInt(_result29);
                    return true;
                case 54:
                    int _arg037 = data.readInt();
                    data.enforceNoDataAvail();
                    setStayOnSetting(_arg037);
                    reply.writeNoException();
                    return true;
                case 55:
                    long _arg038 = data.readLong();
                    data.enforceNoDataAvail();
                    boostScreenBrightness(_arg038);
                    reply.writeNoException();
                    return true;
                case 56:
                    IBinder _arg039 = data.readStrongBinder();
                    int _arg120 = data.readInt();
                    String _arg210 = data.readString();
                    String _arg36 = data.readString();
                    WorkSource _arg43 = (WorkSource) data.readTypedObject(WorkSource.CREATOR);
                    String _arg53 = data.readString();
                    data.enforceNoDataAvail();
                    acquireWakeLockAsync(_arg039, _arg120, _arg210, _arg36, _arg43, _arg53);
                    return true;
                case 57:
                    IBinder _arg040 = data.readStrongBinder();
                    int _arg121 = data.readInt();
                    data.enforceNoDataAvail();
                    releaseWakeLockAsync(_arg040, _arg121);
                    return true;
                case 58:
                    IBinder _arg041 = data.readStrongBinder();
                    int[] _arg122 = data.createIntArray();
                    data.enforceNoDataAvail();
                    updateWakeLockUidsAsync(_arg041, _arg122);
                    return true;
                case 59:
                    boolean _result30 = isScreenBrightnessBoosted();
                    reply.writeNoException();
                    reply.writeBoolean(_result30);
                    return true;
                case 60:
                    boolean _arg042 = data.readBoolean();
                    int _arg123 = data.readInt();
                    data.enforceNoDataAvail();
                    setAttentionLight(_arg042, _arg123);
                    reply.writeNoException();
                    return true;
                case 61:
                    boolean _arg043 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setDozeAfterScreenOff(_arg043);
                    reply.writeNoException();
                    return true;
                case 62:
                    boolean _result31 = isAmbientDisplayAvailable();
                    reply.writeNoException();
                    reply.writeBoolean(_result31);
                    return true;
                case 63:
                    String _arg044 = data.readString();
                    boolean _arg124 = data.readBoolean();
                    data.enforceNoDataAvail();
                    suppressAmbientDisplay(_arg044, _arg124);
                    reply.writeNoException();
                    return true;
                case 64:
                    String _arg045 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result32 = isAmbientDisplaySuppressedForToken(_arg045);
                    reply.writeNoException();
                    reply.writeBoolean(_result32);
                    return true;
                case 65:
                    boolean _result33 = isAmbientDisplaySuppressed();
                    reply.writeNoException();
                    reply.writeBoolean(_result33);
                    return true;
                case 66:
                    String _arg046 = data.readString();
                    int _arg125 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result34 = isAmbientDisplaySuppressedForTokenByApp(_arg046, _arg125);
                    reply.writeNoException();
                    reply.writeBoolean(_result34);
                    return true;
                case 67:
                    int _arg047 = data.readInt();
                    data.enforceNoDataAvail();
                    long _result35 = getLastUserActivityTime(_arg047);
                    reply.writeNoException();
                    reply.writeLong(_result35);
                    return true;
                case 68:
                    boolean _result36 = forceSuspend();
                    reply.writeNoException();
                    reply.writeBoolean(_result36);
                    return true;
                case 69:
                    int _arg048 = data.readInt();
                    int _arg126 = data.readInt();
                    boolean _arg211 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setAutoBrightnessLimit(_arg048, _arg126, _arg211);
                    reply.writeNoException();
                    return true;
                case 70:
                    int _arg049 = data.readInt();
                    int _arg127 = data.readInt();
                    int _arg212 = data.readInt();
                    data.enforceNoDataAvail();
                    setMasterBrightnessLimit(_arg049, _arg127, _arg212);
                    reply.writeNoException();
                    return true;
                case 71:
                    IBinder _arg050 = data.readStrongBinder();
                    int _arg128 = data.readInt();
                    int _arg213 = data.readInt();
                    data.enforceNoDataAvail();
                    setHdrBrightnessLimit(_arg050, _arg128, _arg213);
                    reply.writeNoException();
                    return true;
                case 72:
                    boolean _arg051 = data.readBoolean();
                    data.enforceNoDataAvail();
                    float _result37 = getCurrentBrightness(_arg051);
                    reply.writeNoException();
                    reply.writeFloat(_result37);
                    return true;
                case 73:
                    boolean _arg052 = data.readBoolean();
                    data.enforceNoDataAvail();
                    updateCoverState(_arg052);
                    reply.writeNoException();
                    return true;
                case 74:
                    switchForceLcdBacklightOffState();
                    reply.writeNoException();
                    return true;
                case 75:
                    int _arg053 = data.readInt();
                    data.enforceNoDataAvail();
                    setCoverType(_arg053);
                    reply.writeNoException();
                    return true;
                case 76:
                    float _arg054 = data.readFloat();
                    IBinder _arg129 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    setScreenBrightnessScaleFactor(_arg054, _arg129);
                    reply.writeNoException();
                    return true;
                case 77:
                    IBinder _arg055 = data.readStrongBinder();
                    int _arg130 = data.readInt();
                    int _arg214 = data.readInt();
                    data.enforceNoDataAvail();
                    setProximityDebounceTime(_arg055, _arg130, _arg214);
                    reply.writeNoException();
                    return true;
                case 78:
                    int _arg056 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result38 = isInteractiveForDisplay(_arg056);
                    reply.writeNoException();
                    reply.writeBoolean(_result38);
                    return true;
                case 79:
                    boolean _arg057 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setEarlyWakeUp(_arg057);
                    reply.writeNoException();
                    return true;
                case 80:
                    boolean _arg058 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setFreezingScreenBrightness(_arg058);
                    reply.writeNoException();
                    return true;
                case 81:
                    boolean _arg059 = data.readBoolean();
                    IBinder _arg131 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    setLCDFlashMode(_arg059, _arg131);
                    reply.writeNoException();
                    return true;
                case 82:
                    IBinder _arg060 = data.readStrongBinder();
                    boolean _arg132 = data.readBoolean();
                    int _arg215 = data.readInt();
                    data.enforceNoDataAvail();
                    setScreenCurtainEnabled(_arg060, _arg132, _arg215);
                    reply.writeNoException();
                    return true;
                case 83:
                    boolean _result39 = isScreenCurtainEnabled();
                    reply.writeNoException();
                    reply.writeBoolean(_result39);
                    return true;
                case 84:
                    boolean _result40 = isScreenCurtainEntryAvailable();
                    reply.writeNoException();
                    reply.writeBoolean(_result40);
                    return true;
                case 85:
                    String _result41 = getPackageNameOnScreenCurtain();
                    reply.writeNoException();
                    reply.writeString(_result41);
                    return true;
                case 86:
                    List<AdaptiveScreenOffTimeoutConfig> _arg061 =
                            data.createTypedArrayList(AdaptiveScreenOffTimeoutConfig.CREATOR);
                    data.enforceNoDataAvail();
                    addAdaptiveScreenOffTimeoutConfig(_arg061);
                    reply.writeNoException();
                    return true;
                case 87:
                    List<String> _arg062 = data.createStringArrayList();
                    data.enforceNoDataAvail();
                    removeAdaptiveScreenOffTimeoutConfig(_arg062);
                    reply.writeNoException();
                    return true;
                case 88:
                    List<AdaptiveScreenOffTimeoutConfig> _result42 =
                            getAdaptiveScreenOffTimeoutConfig();
                    reply.writeNoException();
                    reply.writeTypedList(_result42, 1);
                    return true;
                case 89:
                    boolean _result43 = isDozeAfterScreenOff();
                    reply.writeNoException();
                    reply.writeBoolean(_result43);
                    return true;
                case 90:
                    String[] _result44 = getWakeLockPackageList();
                    reply.writeNoException();
                    reply.writeStringArray(_result44);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IPowerManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.os.IPowerManager
            public void acquireWakeLock(
                    IBinder lock,
                    int flags,
                    String tag,
                    String packageName,
                    WorkSource ws,
                    String historyTag,
                    int displayId,
                    IWakeLockCallback callback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(lock);
                    _data.writeInt(flags);
                    _data.writeString(tag);
                    _data.writeString(packageName);
                    _data.writeTypedObject(ws, 0);
                    _data.writeString(historyTag);
                    _data.writeInt(displayId);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void acquireWakeLockWithUid(
                    IBinder lock,
                    int flags,
                    String tag,
                    String packageName,
                    int uidtoblame,
                    int displayId,
                    IWakeLockCallback callback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(lock);
                    _data.writeInt(flags);
                    _data.writeString(tag);
                    _data.writeString(packageName);
                    _data.writeInt(uidtoblame);
                    _data.writeInt(displayId);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void releaseWakeLock(IBinder lock, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(lock);
                    _data.writeInt(flags);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void updateWakeLockUids(IBinder lock, int[] uids) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(lock);
                    _data.writeIntArray(uids);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void setPowerBoost(int boost, int durationMs) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(boost);
                    _data.writeInt(durationMs);
                    this.mRemote.transact(5, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void setPowerMode(int mode, boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(mode);
                    _data.writeBoolean(enabled);
                    this.mRemote.transact(6, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean setPowerModeChecked(int mode, boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(mode);
                    _data.writeBoolean(enabled);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void updateWakeLockWorkSource(IBinder lock, WorkSource ws, String historyTag)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(lock);
                    _data.writeTypedObject(ws, 0);
                    _data.writeString(historyTag);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void updateWakeLockCallback(IBinder lock, IWakeLockCallback callback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(lock);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isWakeLockLevelSupported(int level) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(level);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void userActivity(int displayId, long time, int event, int flags)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeLong(time);
                    _data.writeInt(event);
                    _data.writeInt(flags);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void wakeUp(long time, int reason, String details, String opPackageName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(time);
                    _data.writeInt(reason);
                    _data.writeString(details);
                    _data.writeString(opPackageName);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void goToSleep(long time, int reason, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(time);
                    _data.writeInt(reason);
                    _data.writeInt(flags);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void goToSleepWithDisplayId(int displayId, long time, int reason, int flags)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeLong(time);
                    _data.writeInt(reason);
                    _data.writeInt(flags);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void nap(long time) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(time);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public float getBrightnessConstraint(int constraint) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(constraint);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                    float _result = _reply.readFloat();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isInteractive() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isDisplayInteractive(int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean areAutoPowerSaveModesEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(19, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isPowerSaveMode() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(20, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public PowerSaveState getPowerSaveState(int serviceType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(serviceType);
                    this.mRemote.transact(21, _data, _reply, 0);
                    _reply.readException();
                    PowerSaveState _result =
                            (PowerSaveState) _reply.readTypedObject(PowerSaveState.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean setPowerSaveModeEnabled(boolean mode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(mode);
                    this.mRemote.transact(22, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isBatterySaverSupported() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(23, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public BatterySaverPolicyConfig getFullPowerSavePolicy() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(24, _data, _reply, 0);
                    _reply.readException();
                    BatterySaverPolicyConfig _result =
                            (BatterySaverPolicyConfig)
                                    _reply.readTypedObject(BatterySaverPolicyConfig.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean setFullPowerSavePolicy(BatterySaverPolicyConfig config)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(config, 0);
                    this.mRemote.transact(25, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean setDynamicPowerSaveHint(boolean powerSaveHint, int disableThreshold)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(powerSaveHint);
                    _data.writeInt(disableThreshold);
                    this.mRemote.transact(26, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean setAdaptivePowerSavePolicy(BatterySaverPolicyConfig config)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(config, 0);
                    this.mRemote.transact(27, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean setAdaptivePowerSaveEnabled(boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(enabled);
                    this.mRemote.transact(28, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public int getPowerSaveModeTrigger() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(29, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void setBatteryDischargePrediction(
                    ParcelDuration timeRemaining, boolean isCustomized) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(timeRemaining, 0);
                    _data.writeBoolean(isCustomized);
                    this.mRemote.transact(30, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public ParcelDuration getBatteryDischargePrediction() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(31, _data, _reply, 0);
                    _reply.readException();
                    ParcelDuration _result =
                            (ParcelDuration) _reply.readTypedObject(ParcelDuration.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isBatteryDischargePredictionPersonalized() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(32, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isDeviceIdleMode() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(33, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isLightDeviceIdleMode() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(34, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isLowPowerStandbySupported() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(35, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isLowPowerStandbyEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(36, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void setLowPowerStandbyEnabled(boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(enabled);
                    this.mRemote.transact(37, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void setLowPowerStandbyActiveDuringMaintenance(boolean activeDuringMaintenance)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(activeDuringMaintenance);
                    this.mRemote.transact(38, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void forceLowPowerStandbyActive(boolean active) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(active);
                    this.mRemote.transact(39, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void setLowPowerStandbyPolicy(LowPowerStandbyPolicy policy)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(policy, 0);
                    this.mRemote.transact(40, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public LowPowerStandbyPolicy getLowPowerStandbyPolicy() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(41, _data, _reply, 0);
                    _reply.readException();
                    LowPowerStandbyPolicy _result =
                            (LowPowerStandbyPolicy)
                                    _reply.readTypedObject(LowPowerStandbyPolicy.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isExemptFromLowPowerStandby() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(42, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isReasonAllowedInLowPowerStandby(int reason) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(reason);
                    this.mRemote.transact(43, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isFeatureAllowedInLowPowerStandby(String feature)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(feature);
                    this.mRemote.transact(44, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void acquireLowPowerStandbyPorts(
                    IBinder token, List<LowPowerStandbyPortDescription> ports)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeTypedList(ports, 0);
                    this.mRemote.transact(45, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void releaseLowPowerStandbyPorts(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    this.mRemote.transact(46, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public List<LowPowerStandbyPortDescription> getActiveLowPowerStandbyPorts()
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(47, _data, _reply, 0);
                    _reply.readException();
                    List<LowPowerStandbyPortDescription> _result =
                            _reply.createTypedArrayList(LowPowerStandbyPortDescription.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void reboot(boolean confirm, String reason, boolean wait)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(confirm);
                    _data.writeString(reason);
                    _data.writeBoolean(wait);
                    this.mRemote.transact(48, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void rebootSafeMode(boolean confirm, boolean wait) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(confirm);
                    _data.writeBoolean(wait);
                    this.mRemote.transact(49, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void shutdown(boolean confirm, String reason, boolean wait)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(confirm);
                    _data.writeString(reason);
                    _data.writeBoolean(wait);
                    this.mRemote.transact(50, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void crash(String message) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(message);
                    this.mRemote.transact(51, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public int getLastShutdownReason() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(52, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public int getLastSleepReason() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(53, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void setStayOnSetting(int val) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(val);
                    this.mRemote.transact(54, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void boostScreenBrightness(long time) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(time);
                    this.mRemote.transact(55, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void acquireWakeLockAsync(
                    IBinder lock,
                    int flags,
                    String tag,
                    String packageName,
                    WorkSource ws,
                    String historyTag)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(lock);
                    _data.writeInt(flags);
                    _data.writeString(tag);
                    _data.writeString(packageName);
                    _data.writeTypedObject(ws, 0);
                    _data.writeString(historyTag);
                    this.mRemote.transact(56, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void releaseWakeLockAsync(IBinder lock, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(lock);
                    _data.writeInt(flags);
                    this.mRemote.transact(57, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void updateWakeLockUidsAsync(IBinder lock, int[] uids) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(lock);
                    _data.writeIntArray(uids);
                    this.mRemote.transact(58, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isScreenBrightnessBoosted() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(59, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void setAttentionLight(boolean on, int color) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(on);
                    _data.writeInt(color);
                    this.mRemote.transact(60, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void setDozeAfterScreenOff(boolean on) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(on);
                    this.mRemote.transact(61, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isAmbientDisplayAvailable() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(62, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void suppressAmbientDisplay(String token, boolean suppress)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(token);
                    _data.writeBoolean(suppress);
                    this.mRemote.transact(63, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isAmbientDisplaySuppressedForToken(String token) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(token);
                    this.mRemote.transact(64, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isAmbientDisplaySuppressed() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(65, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isAmbientDisplaySuppressedForTokenByApp(String token, int appUid)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(token);
                    _data.writeInt(appUid);
                    this.mRemote.transact(66, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public long getLastUserActivityTime(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(67, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean forceSuspend() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(68, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void setAutoBrightnessLimit(int lowerLimit, int upperLimit, boolean slowChange)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(lowerLimit);
                    _data.writeInt(upperLimit);
                    _data.writeBoolean(slowChange);
                    this.mRemote.transact(69, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void setMasterBrightnessLimit(
                    int lowerLimit, int upperLimit, int brightnessLimitPeriod)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(lowerLimit);
                    _data.writeInt(upperLimit);
                    _data.writeInt(brightnessLimitPeriod);
                    this.mRemote.transact(70, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void setHdrBrightnessLimit(
                    IBinder lock, int upperLimit, int brightnsesLimitPeriod)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(lock);
                    _data.writeInt(upperLimit);
                    _data.writeInt(brightnsesLimitPeriod);
                    this.mRemote.transact(71, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public float getCurrentBrightness(boolean ratio) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(ratio);
                    this.mRemote.transact(72, _data, _reply, 0);
                    _reply.readException();
                    float _result = _reply.readFloat();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void updateCoverState(boolean closed) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(closed);
                    this.mRemote.transact(73, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void switchForceLcdBacklightOffState() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(74, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void setCoverType(int coverType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(coverType);
                    this.mRemote.transact(75, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void setScreenBrightnessScaleFactor(float scaleFactor, IBinder binder)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeFloat(scaleFactor);
                    _data.writeStrongBinder(binder);
                    this.mRemote.transact(76, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void setProximityDebounceTime(IBinder lock, int positive, int negative)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(lock);
                    _data.writeInt(positive);
                    _data.writeInt(negative);
                    this.mRemote.transact(77, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isInteractiveForDisplay(int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    this.mRemote.transact(78, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void setEarlyWakeUp(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(79, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void setFreezingScreenBrightness(boolean freezing) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(freezing);
                    this.mRemote.transact(80, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void setLCDFlashMode(boolean eanble, IBinder binder) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(eanble);
                    _data.writeStrongBinder(binder);
                    this.mRemote.transact(81, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void setScreenCurtainEnabled(IBinder token, boolean enable, int displayState)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeBoolean(enable);
                    _data.writeInt(displayState);
                    this.mRemote.transact(82, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isScreenCurtainEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(83, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isScreenCurtainEntryAvailable() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(84, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public String getPackageNameOnScreenCurtain() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(85, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void addAdaptiveScreenOffTimeoutConfig(
                    List<AdaptiveScreenOffTimeoutConfig> config) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedList(config, 0);
                    this.mRemote.transact(86, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public void removeAdaptiveScreenOffTimeoutConfig(List<String> list)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStringList(list);
                    this.mRemote.transact(87, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public List<AdaptiveScreenOffTimeoutConfig> getAdaptiveScreenOffTimeoutConfig()
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(88, _data, _reply, 0);
                    _reply.readException();
                    List<AdaptiveScreenOffTimeoutConfig> _result =
                            _reply.createTypedArrayList(AdaptiveScreenOffTimeoutConfig.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public boolean isDozeAfterScreenOff() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(89, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IPowerManager
            public String[] getWakeLockPackageList() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(90, _data, _reply, 0);
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 89;
        }
    }

    public static class LowPowerStandbyPolicy implements Parcelable {
        public static final Parcelable.Creator<LowPowerStandbyPolicy> CREATOR =
                new Parcelable.Creator<
                        LowPowerStandbyPolicy>() { // from class:
                                                   // android.os.IPowerManager.LowPowerStandbyPolicy.1
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public LowPowerStandbyPolicy createFromParcel(Parcel _aidl_source) {
                        LowPowerStandbyPolicy _aidl_out = new LowPowerStandbyPolicy();
                        _aidl_out.readFromParcel(_aidl_source);
                        return _aidl_out;
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public LowPowerStandbyPolicy[] newArray(int _aidl_size) {
                        return new LowPowerStandbyPolicy[_aidl_size];
                    }
                };
        public List<String> allowedFeatures;
        public int allowedReasons = 0;
        public List<String> exemptPackages;
        public String identifier;

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel _aidl_parcel, int _aidl_flag) {
            int _aidl_start_pos = _aidl_parcel.dataPosition();
            _aidl_parcel.writeInt(0);
            _aidl_parcel.writeString(this.identifier);
            _aidl_parcel.writeStringList(this.exemptPackages);
            _aidl_parcel.writeInt(this.allowedReasons);
            _aidl_parcel.writeStringList(this.allowedFeatures);
            int _aidl_end_pos = _aidl_parcel.dataPosition();
            _aidl_parcel.setDataPosition(_aidl_start_pos);
            _aidl_parcel.writeInt(_aidl_end_pos - _aidl_start_pos);
            _aidl_parcel.setDataPosition(_aidl_end_pos);
        }

        public final void readFromParcel(Parcel _aidl_parcel) {
            int _aidl_start_pos = _aidl_parcel.dataPosition();
            int _aidl_parcelable_size = _aidl_parcel.readInt();
            try {
                if (_aidl_parcelable_size < 4) {
                    throw new BadParcelableException("Parcelable too small");
                }
                if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
                    if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                    _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
                    return;
                }
                this.identifier = _aidl_parcel.readString();
                if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
                    if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                    _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
                    return;
                }
                this.exemptPackages = _aidl_parcel.createStringArrayList();
                if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
                    if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                    _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
                    return;
                }
                this.allowedReasons = _aidl_parcel.readInt();
                if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
                    if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                    _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
                } else {
                    this.allowedFeatures = _aidl_parcel.createStringArrayList();
                    if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                    _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
                }
            } catch (Throwable th) {
                if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
                throw th;
            }
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }
    }

    public static class LowPowerStandbyPortDescription implements Parcelable {
        public static final Parcelable.Creator<LowPowerStandbyPortDescription> CREATOR =
                new Parcelable.Creator<
                        LowPowerStandbyPortDescription>() { // from class:
                                                            // android.os.IPowerManager.LowPowerStandbyPortDescription.1
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public LowPowerStandbyPortDescription createFromParcel(Parcel _aidl_source) {
                        LowPowerStandbyPortDescription _aidl_out =
                                new LowPowerStandbyPortDescription();
                        _aidl_out.readFromParcel(_aidl_source);
                        return _aidl_out;
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public LowPowerStandbyPortDescription[] newArray(int _aidl_size) {
                        return new LowPowerStandbyPortDescription[_aidl_size];
                    }
                };
        public byte[] localAddress;
        public int protocol = 0;
        public int portMatcher = 0;
        public int portNumber = 0;

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel _aidl_parcel, int _aidl_flag) {
            int _aidl_start_pos = _aidl_parcel.dataPosition();
            _aidl_parcel.writeInt(0);
            _aidl_parcel.writeInt(this.protocol);
            _aidl_parcel.writeInt(this.portMatcher);
            _aidl_parcel.writeInt(this.portNumber);
            _aidl_parcel.writeByteArray(this.localAddress);
            int _aidl_end_pos = _aidl_parcel.dataPosition();
            _aidl_parcel.setDataPosition(_aidl_start_pos);
            _aidl_parcel.writeInt(_aidl_end_pos - _aidl_start_pos);
            _aidl_parcel.setDataPosition(_aidl_end_pos);
        }

        public final void readFromParcel(Parcel _aidl_parcel) {
            int _aidl_start_pos = _aidl_parcel.dataPosition();
            int _aidl_parcelable_size = _aidl_parcel.readInt();
            try {
                if (_aidl_parcelable_size < 4) {
                    throw new BadParcelableException("Parcelable too small");
                }
                if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
                    if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                    _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
                    return;
                }
                this.protocol = _aidl_parcel.readInt();
                if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
                    if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                    _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
                    return;
                }
                this.portMatcher = _aidl_parcel.readInt();
                if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
                    if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                    _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
                    return;
                }
                this.portNumber = _aidl_parcel.readInt();
                if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
                    if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                    _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
                } else {
                    this.localAddress = _aidl_parcel.createByteArray();
                    if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                    _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
                }
            } catch (Throwable th) {
                if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
                throw th;
            }
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }
    }

    public static class AdaptiveScreenOffTimeoutConfig implements Parcelable {
        public static final Parcelable.Creator<AdaptiveScreenOffTimeoutConfig> CREATOR =
                new Parcelable.Creator<
                        AdaptiveScreenOffTimeoutConfig>() { // from class:
                                                            // android.os.IPowerManager.AdaptiveScreenOffTimeoutConfig.1
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public AdaptiveScreenOffTimeoutConfig createFromParcel(Parcel _aidl_source) {
                        AdaptiveScreenOffTimeoutConfig _aidl_out =
                                new AdaptiveScreenOffTimeoutConfig();
                        _aidl_out.readFromParcel(_aidl_source);
                        return _aidl_out;
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public AdaptiveScreenOffTimeoutConfig[] newArray(int _aidl_size) {
                        return new AdaptiveScreenOffTimeoutConfig[_aidl_size];
                    }
                };
        public String packageName;
        public long screenOffTimeout = 0;

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel _aidl_parcel, int _aidl_flag) {
            int _aidl_start_pos = _aidl_parcel.dataPosition();
            _aidl_parcel.writeInt(0);
            _aidl_parcel.writeString(this.packageName);
            _aidl_parcel.writeLong(this.screenOffTimeout);
            int _aidl_end_pos = _aidl_parcel.dataPosition();
            _aidl_parcel.setDataPosition(_aidl_start_pos);
            _aidl_parcel.writeInt(_aidl_end_pos - _aidl_start_pos);
            _aidl_parcel.setDataPosition(_aidl_end_pos);
        }

        public final void readFromParcel(Parcel _aidl_parcel) {
            int _aidl_start_pos = _aidl_parcel.dataPosition();
            int _aidl_parcelable_size = _aidl_parcel.readInt();
            try {
                if (_aidl_parcelable_size < 4) {
                    throw new BadParcelableException("Parcelable too small");
                }
                if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
                    if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                    _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
                    return;
                }
                this.packageName = _aidl_parcel.readString();
                if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
                    if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                    _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
                } else {
                    this.screenOffTimeout = _aidl_parcel.readLong();
                    if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                    _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
                }
            } catch (Throwable th) {
                if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
                throw th;
            }
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }
    }
}
