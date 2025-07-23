package android.os;

import android.annotation.SystemApi;
import android.app.PropertyInvalidatedCache;
import android.app.WallpaperManager;
import android.content.Context;
import android.os.IPowerManager;
import android.os.IScreenTimeoutPolicyListener;
import android.os.IThermalHeadroomListener;
import android.os.IThermalStatusListener;
import android.os.IWakeLockCallback;
import android.os.PowerManager;
import android.service.dreams.Sandman;
import android.telephony.ims.SipDelegateImsConfiguration;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.util.proto.ProtoOutputStream;
import com.android.internal.R;
import com.android.internal.display.BrightnessSynchronizer;
import com.android.internal.hidden_from_bootclasspath.android.companion.virtualdevice.flags.Flags;
import com.android.internal.util.Preconditions;
import com.samsung.android.media.AudioParameter;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicLong;

/* loaded from: classes3.dex */
public final class PowerManager {

    @Deprecated
    public static final int ACQUIRE_CAUSES_WAKEUP = 268435456;
    public static final String ACTION_DEVICE_IDLE_MODE_CHANGED = "android.os.action.DEVICE_IDLE_MODE_CHANGED";
    public static final String ACTION_DEVICE_LIGHT_IDLE_MODE_CHANGED = "android.os.action.LIGHT_DEVICE_IDLE_MODE_CHANGED";
    public static final String ACTION_ENHANCED_DISCHARGE_PREDICTION_CHANGED = "android.os.action.ENHANCED_DISCHARGE_PREDICTION_CHANGED";

    @Deprecated
    public static final String ACTION_LIGHT_DEVICE_IDLE_MODE_CHANGED = "android.os.action.LIGHT_DEVICE_IDLE_MODE_CHANGED";
    public static final String ACTION_LOW_POWER_STANDBY_ENABLED_CHANGED = "android.os.action.LOW_POWER_STANDBY_ENABLED_CHANGED";
    public static final String ACTION_LOW_POWER_STANDBY_POLICY_CHANGED = "android.os.action.LOW_POWER_STANDBY_POLICY_CHANGED";

    @SystemApi
    public static final String ACTION_LOW_POWER_STANDBY_PORTS_CHANGED = "android.os.action.LOW_POWER_STANDBY_PORTS_CHANGED";
    public static final String ACTION_POWER_SAVE_MODE_CHANGED = "android.os.action.POWER_SAVE_MODE_CHANGED";
    public static final String ACTION_POWER_SAVE_MODE_CHANGED_INTERNAL = "android.os.action.POWER_SAVE_MODE_CHANGED_INTERNAL";
    public static final String ACTION_POWER_SAVE_TEMP_WHITELIST_CHANGED = "android.os.action.POWER_SAVE_TEMP_WHITELIST_CHANGED";
    public static final String ACTION_POWER_SAVE_WHITELIST_CHANGED = "android.os.action.POWER_SAVE_WHITELIST_CHANGED";
    public static final String ACTION_SCREEN_OFF_BY_PROXIMITY = "android.intent.action.ACTION_SCREEN_OFF_BY_PROXIMITY";
    public static final String ACTION_SCREEN_ON_BY_PROXIMITY = "android.intent.action.ACTION_SCREEN_ON_BY_PROXIMITY";
    public static final int BRIGHTNESS_CONSTRAINT_TYPE_DEFAULT = 2;
    public static final int BRIGHTNESS_CONSTRAINT_TYPE_DIM = 3;
    public static final int BRIGHTNESS_CONSTRAINT_TYPE_MAXIMUM = 1;
    public static final int BRIGHTNESS_CONSTRAINT_TYPE_MINIMUM = 0;
    public static final int BRIGHTNESS_DEFAULT = -1;
    public static final int BRIGHTNESS_INVALID = -1;
    public static final float BRIGHTNESS_INVALID_FLOAT = Float.NaN;
    public static final float BRIGHTNESS_MAX = 1.0f;
    public static final float BRIGHTNESS_MIN = 0.0f;
    public static final int BRIGHTNESS_OFF = 0;
    public static final float BRIGHTNESS_OFF_FLOAT = -1.0f;
    public static final int BRIGHTNESS_ON = 255;
    private static final String CACHE_KEY_IS_INTERACTIVE_API = "is_interactive";
    private static final String CACHE_KEY_IS_POWER_SAVE_MODE_API = "is_power_save_mode";
    public static final int DOZE_WAKE_LOCK = 64;
    public static final int DRAW_WAKE_LOCK = 128;
    public static final int DUAL_SCREEN_STATE_INNER = 0;
    public static final int DUAL_SCREEN_STATE_OUTER = 1;
    public static final int DUAL_SCREEN_STATE_UNKNOWN = -1;
    public static final String FEATURE_WAKE_ON_LAN_IN_LOW_POWER_STANDBY = "com.android.lowpowerstandby.WAKE_ON_LAN";

    @Deprecated
    public static final int FULL_WAKE_LOCK = 26;
    public static final int GO_TO_SLEEP_FLAG_NO_DOZE = 1;
    public static final int GO_TO_SLEEP_FLAG_SOFT_SLEEP = 2;
    public static final int GO_TO_SLEEP_REASON_ACCESSIBILITY = 7;
    public static final int GO_TO_SLEEP_REASON_APPLICATION = 0;
    public static final int GO_TO_SLEEP_REASON_COVER_CLOSE = 20;
    public static final int GO_TO_SLEEP_REASON_DEVICE_ADMIN = 1;
    public static final int GO_TO_SLEEP_REASON_DEVICE_FOLD = 13;
    public static final int GO_TO_SLEEP_REASON_DEX_DUAL_DEFAULT_SCREEN_OFF = 21;
    public static final int GO_TO_SLEEP_REASON_DISPLAY_GROUPS_TURNED_OFF = 12;
    public static final int GO_TO_SLEEP_REASON_DISPLAY_GROUP_REMOVED = 11;
    public static final int GO_TO_SLEEP_REASON_DOUBLE_TAP = 23;
    public static final int GO_TO_SLEEP_REASON_EXTERNAL_KEYBOARD_META_L = 25;
    public static final int GO_TO_SLEEP_REASON_FORCE_SUSPEND = 8;
    public static final int GO_TO_SLEEP_REASON_HDMI = 5;
    public static final int GO_TO_SLEEP_REASON_INATTENTIVE = 9;
    public static final int GO_TO_SLEEP_REASON_KEEP_SCREEN_OFF = 19;
    public static final int GO_TO_SLEEP_REASON_LID_SWITCH = 3;
    public static final int GO_TO_SLEEP_REASON_MAX = 26;
    public static final int GO_TO_SLEEP_REASON_MIN = 0;
    public static final int GO_TO_SLEEP_REASON_PALM_TOUCH_DOWN = 24;
    public static final int GO_TO_SLEEP_REASON_POWER_BUTTON = 4;
    public static final int GO_TO_SLEEP_REASON_PROXIMITY = 18;
    public static final int GO_TO_SLEEP_REASON_PUT_DOWN_MOTION = 22;
    public static final int GO_TO_SLEEP_REASON_QUIESCENT = 10;
    public static final int GO_TO_SLEEP_REASON_SLEEP_BUTTON = 6;
    public static final int GO_TO_SLEEP_REASON_TIMEOUT = 2;
    public static final int GO_TO_SLEEP_REASON_UNKNOWN = 14;
    public static final int GO_TO_SLEEP_REASON_WAKE_UP_PREVENTION_ENABLED = 26;
    public static final int LOCATION_MODE_ALL_DISABLED_WHEN_SCREEN_OFF = 2;
    public static final int LOCATION_MODE_FOREGROUND_ONLY = 3;
    public static final int LOCATION_MODE_GPS_DISABLED_WHEN_SCREEN_OFF = 1;
    public static final int LOCATION_MODE_NO_CHANGE = 0;
    public static final int LOCATION_MODE_THROTTLE_REQUESTS_WHEN_SCREEN_OFF = 4;
    public static final int LOW_POWER_STANDBY_ALLOWED_REASON_ONGOING_CALL = 4;
    public static final int LOW_POWER_STANDBY_ALLOWED_REASON_TEMP_POWER_SAVE_ALLOWLIST = 2;
    public static final int LOW_POWER_STANDBY_ALLOWED_REASON_VOICE_INTERACTION = 1;
    private static final int MAX_CACHE_ENTRIES = 1;
    public static final int MAX_LOCATION_MODE = 4;
    public static final int MAX_SOUND_TRIGGER_MODE = 2;
    private static final int MINIMUM_HEADROOM_TIME_MILLIS = 500;
    public static final int MIN_LOCATION_MODE = 0;
    public static final int MIN_SOUND_TRIGGER_MODE = 0;
    public static final int ON_AFTER_RELEASE = 536870912;
    public static final int PARTIAL_WAKE_LOCK = 1;

    @SystemApi
    public static final int POWER_SAVE_MODE_TRIGGER_DYNAMIC = 1;

    @SystemApi
    public static final int POWER_SAVE_MODE_TRIGGER_PERCENTAGE = 0;
    public static final int PROXIMITY_SCREEN_OFF_WAKE_LOCK = 32;
    public static final float RAMP_SPEED_INVALID_FLOAT = Float.NaN;
    public static final String REBOOT_QUIESCENT = "quiescent";
    public static final String REBOOT_RECOVERY = "recovery";
    public static final String REBOOT_RECOVERY_UPDATE = "recovery-update";
    public static final String REBOOT_REQUESTED_BY_DEVICE_OWNER = "deviceowner";
    public static final String REBOOT_SAFE_MODE = "safemode";

    @SystemApi
    public static final String REBOOT_USERSPACE = "userspace";
    public static final int RELEASE_FLAG_TIMEOUT = 65536;
    public static final int RELEASE_FLAG_WAIT_FOR_NO_PROXIMITY = 1;

    @Deprecated
    public static final int SCREEN_BRIGHT_WAKE_LOCK = 10;

    @Deprecated
    public static final int SCREEN_DIM_WAKE_LOCK = 6;
    public static final int SCREEN_TIMEOUT_ACTIVE = 0;
    public static final int SCREEN_TIMEOUT_KEEP_DISPLAY_ON = 1;
    public static final int SCREEN_TIMEOUT_OVERRIDE_WAKE_LOCK = 256;
    public static final int SEM_BRIGHTNESS_INVALID = -1;
    public static final int SEM_BRIGHTNESS_ON = 255;
    public static final int SEM_GO_TO_SLEEP_REASON_DOUBLE_TAP = 23;
    public static final String SHUTDOWN_BATTERY_THERMAL_STATE = "thermal,battery";
    public static final String SHUTDOWN_BIXBY_REQUESTED = "bixbyrequest";
    public static final String SHUTDOWN_LOW_BATTERY = "battery";
    public static final int SHUTDOWN_REASON_BATTERY_THERMAL = 6;
    public static final int SHUTDOWN_REASON_LOW_BATTERY = 5;
    public static final int SHUTDOWN_REASON_REBOOT = 2;
    public static final int SHUTDOWN_REASON_SHUTDOWN = 1;
    public static final int SHUTDOWN_REASON_THERMAL_SHUTDOWN = 4;
    public static final int SHUTDOWN_REASON_UNKNOWN = 0;
    public static final int SHUTDOWN_REASON_USER_REQUESTED = 3;
    public static final String SHUTDOWN_THERMAL_STATE = "thermal";
    public static final String SHUTDOWN_USER_REQUESTED = "userrequested";
    public static final String SILENT_RESET_EXCEPTION_MSG = "NPE by silent reset. It's normal operation caused by device care";
    public static final String SILENT_RESET_PARAM = "silent.sec";

    @SystemApi
    public static final int SOUND_TRIGGER_MODE_ALL_DISABLED = 2;

    @SystemApi
    public static final int SOUND_TRIGGER_MODE_ALL_ENABLED = 0;

    @SystemApi
    public static final int SOUND_TRIGGER_MODE_CRITICAL_ONLY = 1;
    public static final int SYSTEM_WAKELOCK = Integer.MIN_VALUE;
    private static final String TAG = "PowerManager";
    public static final int THERMAL_STATUS_CRITICAL = 4;
    public static final int THERMAL_STATUS_EMERGENCY = 5;
    public static final int THERMAL_STATUS_LIGHT = 1;
    public static final int THERMAL_STATUS_MODERATE = 2;
    public static final int THERMAL_STATUS_NONE = 0;
    public static final int THERMAL_STATUS_SEVERE = 3;
    public static final int THERMAL_STATUS_SHUTDOWN = 6;
    public static final int UNIMPORTANT_FOR_LOGGING = 1073741824;

    @SystemApi
    public static final int USER_ACTIVITY_EVENT_ACCESSIBILITY = 3;
    public static final int USER_ACTIVITY_EVENT_ATTENTION = 4;

    @SystemApi
    public static final int USER_ACTIVITY_EVENT_BUTTON = 1;
    public static final int USER_ACTIVITY_EVENT_DEVICE_STATE = 6;
    public static final int USER_ACTIVITY_EVENT_FACE_DOWN = 5;

    @SystemApi
    public static final int USER_ACTIVITY_EVENT_OTHER = 0;

    @SystemApi
    public static final int USER_ACTIVITY_EVENT_TOUCH = 2;
    public static final int USER_ACTIVITY_FLAG_HOVER = 8192;
    public static final int USER_ACTIVITY_FLAG_IME = 4096;

    @SystemApi
    public static final int USER_ACTIVITY_FLAG_INDIRECT = 2;
    public static final int USER_ACTIVITY_FLAG_INTERNALKEY = 32768;
    public static final int USER_ACTIVITY_FLAG_NAVIBAR = 16384;

    @SystemApi
    public static final int USER_ACTIVITY_FLAG_NO_CHANGE_LIGHTS = 1;
    public static final int WAKE_LOCK_LEVEL_MASK = 65535;
    public static final int WAKE_REASON_APPLICATION = 2;
    public static final int WAKE_REASON_APPLICATION_WINDOW_MANAGER_TURN_ON_FLAG = 110;
    public static final int WAKE_REASON_BIOMETRIC = 17;
    public static final int WAKE_REASON_BIXBY = 112;
    public static final int WAKE_REASON_CAMERA_LAUNCH = 5;
    public static final int WAKE_REASON_CAMERA_LENS_COVER = 101;
    public static final int WAKE_REASON_COVER_OPEN = 103;
    public static final int WAKE_REASON_DEX_DUAL_DEFAULT_SCREEN_ON = 114;
    public static final int WAKE_REASON_DISPLAY_GROUP_ADDED = 10;
    public static final int WAKE_REASON_DISPLAY_GROUP_TURNED_ON = 11;
    public static final int WAKE_REASON_DOCK = 18;
    public static final int WAKE_REASON_DOUBLE_TAP = 113;
    public static final int WAKE_REASON_DREAM = 104;
    public static final int WAKE_REASON_DREAM_FINISHED = 13;
    public static final int WAKE_REASON_EAR_JACK = 106;
    public static final int WAKE_REASON_FINGERPRINT = 111;
    public static final int WAKE_REASON_GESTURE = 4;
    public static final int WAKE_REASON_HDMI = 8;
    public static final int WAKE_REASON_LID = 9;
    public static final int WAKE_REASON_LIFT = 16;
    public static final int WAKE_REASON_PLUGGED_IN = 3;
    public static final int WAKE_REASON_POWER_BUTTON = 1;
    public static final int WAKE_REASON_PROXIMITY = 109;
    public static final int WAKE_REASON_SANDMAN = 108;
    public static final int WAKE_REASON_SENSOR_CA = 107;
    public static final int WAKE_REASON_SPEN = 102;
    public static final int WAKE_REASON_TAP = 15;
    public static final int WAKE_REASON_TILT = 14;
    public static final int WAKE_REASON_UNFOLD_DEVICE = 12;
    public static final int WAKE_REASON_UNKNOWN = 0;
    public static final int WAKE_REASON_WAKE_KEY = 6;
    public static final int WAKE_REASON_WAKE_MOTION = 7;
    public static final int WAKE_REASON_WAKE_UP_PREVENTION_DISABLED = 115;
    final Context mContext;
    final Handler mHandler;
    private final PropertyInvalidatedCache<Integer, Boolean> mInteractiveCache;
    private PowerExemptionManager mPowerExemptionManager;
    private final PropertyInvalidatedCache<Void, Boolean> mPowerSaveModeCache;
    final IPowerManager mService;
    final IThermalService mThermalService;
    private final ArrayMap<OnThermalStatusChangedListener, IThermalStatusListener> mThermalStatusListenerMap = new ArrayMap<>();
    private final ArrayMap<OnThermalHeadroomChangedListener, IThermalHeadroomListener> mThermalHeadroomListenerMap = new ArrayMap<>();
    private final ArrayMap<ScreenTimeoutPolicyListener, IScreenTimeoutPolicyListener> mScreenTimeoutPolicyListeners = new ArrayMap<>();
    private final AtomicLong mLastHeadroomUpdate = new AtomicLong(0);

    @Retention(RetentionPolicy.SOURCE)
    public @interface AutoPowerSaveModeTriggers {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface BrightnessConstraint {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface GoToSleepReason {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface LocationPowerSaveMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface LowPowerStandbyAllowedReason {
    }

    public interface OnThermalHeadroomChangedListener {
        void onThermalHeadroomChanged(float f, float f2, int i, Map<Integer, Float> map);
    }

    public interface OnThermalStatusChangedListener {
        void onThermalStatusChanged(int i);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ScreenTimeoutPolicy {
    }

    public interface ScreenTimeoutPolicyListener {
        void onScreenTimeoutPolicyChanged(int i);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ServiceType {
        public static final int ANIMATION = 3;
        public static final int AOD = 14;
        public static final int BATTERY_STATS = 9;
        public static final int DATA_SAVER = 10;
        public static final int FORCE_ALL_APPS_STANDBY = 11;
        public static final int FORCE_BACKGROUND_CHECK = 12;
        public static final int FULL_BACKUP = 4;
        public static final int KEYVALUE_BACKUP = 5;
        public static final int LOCATION = 1;
        public static final int NETWORK_FIREWALL = 6;
        public static final int NIGHT_MODE = 16;
        public static final int NULL = 0;
        public static final int OPTIONAL_SENSORS = 13;
        public static final int QUICK_DOZE = 15;
        public static final int SCREEN_BRIGHTNESS = 7;
        public static final int SOUND = 8;
        public static final int VIBRATION = 2;
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ShutdownReason {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SoundTriggerPowerSaveMode {
    }

    @Target({ElementType.TYPE_USE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ThermalStatus {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface UserActivityEvent {
    }

    public interface WakeLockStateListener {
        void onStateChanged(boolean z);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface WakeReason {
    }

    public static boolean isRebootingUserspaceSupportedImpl() {
        return false;
    }

    public int semGetMaximumSubScreenBrightnessSetting() {
        return -1;
    }

    public static String userActivityEventToString(int i) {
        switch (i) {
            case 0:
                return "other";
            case 1:
                return "button";
            case 2:
                return "touch";
            case 3:
                return Context.ACCESSIBILITY_SERVICE;
            case 4:
                return Context.ATTENTION_SERVICE;
            case 5:
                return "faceDown";
            case 6:
                return "deviceState";
            default:
                return Integer.toString(i);
        }
    }

    public static String userActivityFlagsToString(int i) {
        String str;
        if ((i & 1) == 0) {
            str = "";
        } else {
            str = " NO_CHANGE_LIGHTS";
        }
        if ((i & 2) != 0) {
            str = str + " INDIRECT";
        }
        if ((i & 4096) != 0) {
            str = str + " IME";
        }
        if ((i & 8192) != 0) {
            str = str + " HOVER";
        }
        if ((i & 16384) != 0) {
            str = str + " NAVIBAR";
        }
        if ((i & 32768) == 0) {
            return str;
        }
        return str + " INTERNALKEY";
    }

    public static String sleepReasonToString(int i) {
        switch (i) {
            case 0:
                return "application";
            case 1:
                return "device_admin";
            case 2:
                return "timeout";
            case 3:
                return "lid_switch";
            case 4:
                return "power_button";
            case 5:
                return "hdmi";
            case 6:
                return "sleep_button";
            case 7:
                return Context.ACCESSIBILITY_SERVICE;
            case 8:
                return "force_suspend";
            case 9:
                return "inattentive";
            case 10:
                return REBOOT_QUIESCENT;
            case 11:
                return "display_group_removed";
            case 12:
                return "display_groups_turned_off";
            case 13:
                return "device_folded";
            case 14:
                return "unknown";
            case 15:
            case 16:
            case 17:
            default:
                return Integer.toString(i);
            case 18:
                return "proximity";
            case 19:
                return "keep_screen_off";
            case 20:
                return "cover_close";
            case 21:
                return "dex_dual_default_screen_off";
            case 22:
                return "put_down_motion";
            case 23:
                return "double_tap";
            case 24:
                return "palm_touch_down";
            case 25:
                return "external_keyboard_meta_l";
            case 26:
                return "wake_up_prevention_enabled";
        }
    }

    public static String wakeReasonToString(int i) {
        switch (i) {
            case 0:
                return "unknown";
            case 1:
                return "power_button";
            case 2:
                return "application";
            case 3:
                return "plugged_in";
            case 4:
                return "gesture";
            case 5:
                return "camera_launch";
            case 6:
                return "wake_key";
            case 7:
                return "wake_motion";
            case 8:
                return "hdmi";
            case 9:
                return "lid";
            case 10:
                return "display_group_added";
            case 11:
                return "display_group_turned_on";
            case 12:
                return "unfold_device";
            case 13:
                return "dream_finished";
            case 14:
                return WallpaperManager.SEM_ATTRIBUTE_TILT;
            case 15:
                return "tap";
            case 16:
                return "lift";
            case 17:
                return Context.BIOMETRIC_SERVICE;
            case 18:
                return AudioParameter.VALUE_DOCK;
            default:
                switch (i) {
                    case 101:
                        return "camera_lens_cover";
                    case 102:
                        return "spen";
                    case 103:
                        return "cover_open";
                    case 104:
                        return "dream";
                    default:
                        switch (i) {
                            case 106:
                                return "ear_jack";
                            case 107:
                                return "sensor_ca";
                            case 108:
                                return "sandman";
                            case 109:
                                return "proximity";
                            case 110:
                                return "application_window_manager_turn_on_flag";
                            case 111:
                                return Context.FINGERPRINT_SERVICE;
                            case 112:
                                return "bixby";
                            case 113:
                                return "double_tap";
                            case 114:
                                return "dex_dual_default_screen_on";
                            case 115:
                                return "wake_up_prevention_disabled";
                            default:
                                return Integer.toString(i);
                        }
                }
        }
    }

    public static class WakeData {
        public final long sleepDurationRealtime;
        public final int wakeReason;
        public final long wakeTime;

        public WakeData(long j, int i, long j2) {
            this.wakeTime = j;
            this.wakeReason = i;
            this.sleepDurationRealtime = j2;
        }

        public boolean equals(Object obj) {
            if (obj instanceof WakeData) {
                WakeData wakeData = (WakeData) obj;
                if (this.wakeTime == wakeData.wakeTime && this.wakeReason == wakeData.wakeReason && this.sleepDurationRealtime == wakeData.sleepDurationRealtime) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(Long.valueOf(this.wakeTime), Integer.valueOf(this.wakeReason), Long.valueOf(this.sleepDurationRealtime));
        }
    }

    public static class SleepData {
        public final int goToSleepReason;
        public final long goToSleepUptimeMillis;

        public SleepData(long j, int i) {
            this.goToSleepUptimeMillis = j;
            this.goToSleepReason = i;
        }

        public boolean equals(Object obj) {
            if (obj instanceof SleepData) {
                SleepData sleepData = (SleepData) obj;
                if (this.goToSleepUptimeMillis == sleepData.goToSleepUptimeMillis && this.goToSleepReason == sleepData.goToSleepReason) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(Long.valueOf(this.goToSleepUptimeMillis), Integer.valueOf(this.goToSleepReason));
        }
    }

    public static String locationPowerSaveModeToString(int i) {
        if (i == 0) {
            return "NO_CHANGE";
        }
        if (i == 1) {
            return "GPS_DISABLED_WHEN_SCREEN_OFF";
        }
        if (i == 2) {
            return "ALL_DISABLED_WHEN_SCREEN_OFF";
        }
        if (i == 3) {
            return "FOREGROUND_ONLY";
        }
        if (i == 4) {
            return "THROTTLE_REQUESTS_WHEN_SCREEN_OFF";
        }
        return Integer.toString(i);
    }

    private static PropertyInvalidatedCache.Args getCacheArgs(String str) {
        return new PropertyInvalidatedCache.Args("system_server").maxEntries(1).isolateUids(false).cacheNulls(false).api(str);
    }

    public PowerManager(Context context, IPowerManager iPowerManager, IThermalService iThermalService, Handler handler) {
        PropertyInvalidatedCache.QueryHandler queryHandler = null;
        this.mPowerSaveModeCache = new PropertyInvalidatedCache<Void, Boolean>(getCacheArgs(CACHE_KEY_IS_POWER_SAVE_MODE_API), CACHE_KEY_IS_POWER_SAVE_MODE_API, queryHandler) { // from class: android.os.PowerManager.1
            @Override // android.app.PropertyInvalidatedCache
            public Boolean recompute(Void r1) {
                try {
                    return Boolean.valueOf(PowerManager.this.mService.isPowerSaveMode());
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        };
        this.mInteractiveCache = new PropertyInvalidatedCache<Integer, Boolean>(getCacheArgs(CACHE_KEY_IS_INTERACTIVE_API), CACHE_KEY_IS_INTERACTIVE_API, queryHandler) { // from class: android.os.PowerManager.2
            @Override // android.app.PropertyInvalidatedCache
            public Boolean recompute(Integer num) {
                try {
                    if (num == null) {
                        return Boolean.valueOf(PowerManager.this.mService.isInteractive());
                    }
                    return Boolean.valueOf(PowerManager.this.mService.isDisplayInteractive(num.intValue()));
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        };
        this.mContext = context;
        this.mService = iPowerManager;
        this.mThermalService = iThermalService;
        this.mHandler = handler;
    }

    private PowerExemptionManager getPowerExemptionManager() {
        if (this.mPowerExemptionManager == null) {
            this.mPowerExemptionManager = (PowerExemptionManager) this.mContext.getSystemService(PowerExemptionManager.class);
        }
        return this.mPowerExemptionManager;
    }

    public int getMinimumScreenBrightnessSetting() {
        return this.mContext.getResources().getInteger(R.integer.config_screenBrightnessSettingMinimum);
    }

    public int getMaximumScreenBrightnessSetting() {
        return this.mContext.getResources().getInteger(R.integer.config_screenBrightnessSettingMaximum);
    }

    public int semGetMinimumScreenBrightnessSetting() {
        return getMinimumScreenBrightnessSetting();
    }

    public int semGetMaximumScreenBrightnessSetting() {
        return getMaximumScreenBrightnessSetting();
    }

    public int getDefaultScreenBrightnessSetting() {
        return this.mContext.getResources().getInteger(R.integer.config_screenBrightnessSettingDefault);
    }

    public int getMaximumScreenBrightnessExtended() {
        return Math.max(this.mContext.getResources().getInteger(R.integer.config_screenBrightnessExtendedMaximum), this.mContext.getResources().getInteger(R.integer.config_coverScreenBrightnessExtendedMaximum));
    }

    public boolean semIsSubAutoBrightnessSupported() {
        return this.mContext.getResources().getBoolean(R.bool.config_cover_automatic_brightness_available);
    }

    public float getBrightnessConstraint(int i) {
        return getBrightnessConstraint(0, i);
    }

    public float getBrightnessConstraint(int i, int i2) {
        try {
            return this.mService.getBrightnessConstraint(i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public WakeLock newWakeLock(int i, String str) {
        if (Flags.displayPowerManagerApis()) {
            return newWakeLock(i, str, this.mContext.getDisplayId());
        }
        validateWakeLockParameters(i, str);
        return new WakeLock(i, str, this.mContext.getOpPackageName(), -1);
    }

    public WakeLock newWakeLock(int i, String str, int i2) {
        validateWakeLockParameters(i, str);
        return new WakeLock(i, str, this.mContext.getOpPackageName(), i2);
    }

    public static void validateWakeLockParameters(int i, String str) {
        int i2 = i & 65535;
        if (i2 != 1 && i2 != 6 && i2 != 10 && i2 != 26 && i2 != 32 && i2 != 64 && i2 != 128 && i2 != 256) {
            throw new IllegalArgumentException("Must specify a valid wake lock level.");
        }
        if (str == null) {
            throw new IllegalArgumentException("The tag must not be null.");
        }
    }

    @Deprecated
    public void userActivity(long j, boolean z) {
        userActivity(j, 0, z ? 1 : 0);
    }

    @SystemApi
    public void userActivity(long j, int i, int i2) {
        try {
            this.mService.userActivity(this.mContext.getDisplayId(), j, i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void semGoToSleep(long j) {
        goToSleep(j);
    }

    public void semGoToSleep(long j, int i, int i2) {
        goToSleep(j, i, i2);
    }

    public void goToSleep(long j) {
        goToSleep(j, 0, 0);
    }

    public void goToSleep(long j, int i, int i2) {
        try {
            this.mService.goToSleep(j, i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void goToSleep(int i, long j, int i2, int i3) {
        try {
            this.mService.goToSleepWithDisplayId(i, j, i2, i3);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void semWakeUp(long j, int i, String str) {
        wakeUp(j, i, str);
    }

    @Deprecated
    public void wakeUp(long j) {
        wakeUp(j, 0, "wakeUp");
    }

    @Deprecated
    public void wakeUp(long j, String str) {
        wakeUp(j, 0, str);
    }

    public void wakeUp(long j, int i, String str) {
        try {
            this.mService.wakeUp(j, i, str, this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void wakeUp(long j, int i, String str, int i2) {
        try {
            this.mService.wakeUpWithDisplayId(j, i, str, this.mContext.getOpPackageName(), i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void nap(long j) {
        try {
            this.mService.nap(j);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void dream(long j) {
        Sandman.startDreamByUserRequest(this.mContext);
    }

    public void boostScreenBrightness(long j) {
        try {
            this.mService.boostScreenBrightness(j);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void addScreenTimeoutPolicyListener(int i, Executor executor, ScreenTimeoutPolicyListener screenTimeoutPolicyListener) {
        Objects.requireNonNull(screenTimeoutPolicyListener, "listener cannot be null");
        Objects.requireNonNull(executor, "executor cannot be null");
        Preconditions.checkArgument(!this.mScreenTimeoutPolicyListeners.containsKey(screenTimeoutPolicyListener), "Listener already registered: %s", screenTimeoutPolicyListener);
        AnonymousClass3 anonymousClass3 = new AnonymousClass3(this, executor, screenTimeoutPolicyListener);
        try {
            this.mService.addScreenTimeoutPolicyListener(i, anonymousClass3);
            this.mScreenTimeoutPolicyListeners.put(screenTimeoutPolicyListener, anonymousClass3);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.os.PowerManager$3, reason: invalid class name */
    class AnonymousClass3 extends IScreenTimeoutPolicyListener.Stub {
        final /* synthetic */ Executor val$executor;
        final /* synthetic */ ScreenTimeoutPolicyListener val$listener;

        AnonymousClass3(PowerManager powerManager, Executor executor, ScreenTimeoutPolicyListener screenTimeoutPolicyListener) {
            this.val$executor = executor;
            this.val$listener = screenTimeoutPolicyListener;
        }

        @Override // android.os.IScreenTimeoutPolicyListener
        public void onScreenTimeoutPolicyChanged(final int i) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Executor executor = this.val$executor;
                final ScreenTimeoutPolicyListener screenTimeoutPolicyListener = this.val$listener;
                executor.execute(new Runnable() { // from class: android.os.PowerManager$3$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        PowerManager.ScreenTimeoutPolicyListener.this.onScreenTimeoutPolicyChanged(i);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public void removeScreenTimeoutPolicyListener(int i, ScreenTimeoutPolicyListener screenTimeoutPolicyListener) {
        Objects.requireNonNull(screenTimeoutPolicyListener, "listener cannot be null");
        IScreenTimeoutPolicyListener iScreenTimeoutPolicyListener = this.mScreenTimeoutPolicyListeners.get(screenTimeoutPolicyListener);
        Preconditions.checkArgument(iScreenTimeoutPolicyListener != null, "Listener was not added");
        try {
            this.mService.removeScreenTimeoutPolicyListener(i, iScreenTimeoutPolicyListener);
            this.mScreenTimeoutPolicyListeners.remove(screenTimeoutPolicyListener);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isWakeLockLevelSupported(int i) {
        try {
            if (Flags.displayPowerManagerApis()) {
                return this.mService.isWakeLockLevelSupportedWithDisplayId(i, this.mContext.getDisplayId());
            }
            return this.mService.isWakeLockLevelSupported(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public boolean isScreenOn() {
        return isInteractive();
    }

    public boolean isInteractive() {
        try {
            return this.mService.isInteractiveForDisplay(this.mContext.getDisplayId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isInteractive(int i) {
        return this.mInteractiveCache.query(Integer.valueOf(i)).booleanValue();
    }

    public boolean isRebootingUserspaceSupported() {
        return isRebootingUserspaceSupportedImpl();
    }

    public void reboot(String str) {
        if (REBOOT_USERSPACE.equals(str) && !isRebootingUserspaceSupported()) {
            throw new UnsupportedOperationException("Attempted userspace reboot on a device that doesn't support it");
        }
        try {
            this.mService.reboot(false, str, true);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void rebootSafeMode() {
        try {
            this.mService.rebootSafeMode(false, true);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean areAutoPowerSaveModesEnabled() {
        try {
            return this.mService.areAutoPowerSaveModesEnabled();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isPowerSaveMode() {
        return this.mPowerSaveModeCache.query(null).booleanValue();
    }

    @SystemApi
    public boolean setPowerSaveModeEnabled(boolean z) {
        try {
            return this.mService.setPowerSaveModeEnabled(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isBatterySaverSupported() {
        try {
            return this.mService.isBatterySaverSupported();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public BatterySaverPolicyConfig getFullPowerSavePolicy() {
        try {
            return this.mService.getFullPowerSavePolicy();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean setFullPowerSavePolicy(BatterySaverPolicyConfig batterySaverPolicyConfig) {
        try {
            return this.mService.setFullPowerSavePolicy(batterySaverPolicyConfig);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean setDynamicPowerSaveHint(boolean z, int i) {
        try {
            return this.mService.setDynamicPowerSaveHint(z, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean setAdaptivePowerSavePolicy(BatterySaverPolicyConfig batterySaverPolicyConfig) {
        try {
            return this.mService.setAdaptivePowerSavePolicy(batterySaverPolicyConfig);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean setAdaptivePowerSaveEnabled(boolean z) {
        try {
            return this.mService.setAdaptivePowerSaveEnabled(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public int getPowerSaveModeTrigger() {
        try {
            return this.mService.getPowerSaveModeTrigger();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void setBatteryDischargePrediction(Duration duration, boolean z) {
        if (duration == null) {
            throw new IllegalArgumentException("time remaining must not be null");
        }
        try {
            this.mService.setBatteryDischargePrediction(new ParcelDuration(duration), z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Duration getBatteryDischargePrediction() {
        try {
            ParcelDuration batteryDischargePrediction = this.mService.getBatteryDischargePrediction();
            if (batteryDischargePrediction == null) {
                return null;
            }
            return batteryDischargePrediction.getDuration();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isBatteryDischargePredictionPersonalized() {
        try {
            return this.mService.isBatteryDischargePredictionPersonalized();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public PowerSaveState getPowerSaveState(int i) {
        try {
            return this.mService.getPowerSaveState(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getLocationPowerSaveMode() {
        PowerSaveState powerSaveState = getPowerSaveState(1);
        if (powerSaveState.batterySaverEnabled) {
            return powerSaveState.locationMode;
        }
        return 0;
    }

    public int getSoundTriggerPowerSaveMode() {
        PowerSaveState powerSaveState = getPowerSaveState(8);
        if (powerSaveState.batterySaverEnabled) {
            return powerSaveState.soundTriggerMode;
        }
        return 0;
    }

    public boolean isDeviceIdleMode() {
        try {
            return this.mService.isDeviceIdleMode();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isDeviceLightIdleMode() {
        try {
            return this.mService.isLightDeviceIdleMode();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public boolean isLightDeviceIdleMode() {
        return isDeviceLightIdleMode();
    }

    @SystemApi
    public boolean isLowPowerStandbySupported() {
        try {
            return this.mService.isLowPowerStandbySupported();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isLowPowerStandbyEnabled() {
        try {
            return this.mService.isLowPowerStandbyEnabled();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void setLowPowerStandbyEnabled(boolean z) {
        try {
            this.mService.setLowPowerStandbyEnabled(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void setLowPowerStandbyActiveDuringMaintenance(boolean z) {
        try {
            this.mService.setLowPowerStandbyActiveDuringMaintenance(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void forceLowPowerStandbyActive(boolean z) {
        try {
            this.mService.forceLowPowerStandbyActive(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void setLowPowerStandbyPolicy(LowPowerStandbyPolicy lowPowerStandbyPolicy) {
        try {
            this.mService.setLowPowerStandbyPolicy(LowPowerStandbyPolicy.toParcelable(lowPowerStandbyPolicy));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public LowPowerStandbyPolicy getLowPowerStandbyPolicy() {
        try {
            return LowPowerStandbyPolicy.fromParcelable(this.mService.getLowPowerStandbyPolicy());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isExemptFromLowPowerStandby() {
        try {
            return this.mService.isExemptFromLowPowerStandby();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isAllowedInLowPowerStandby(int i) {
        try {
            return this.mService.isReasonAllowedInLowPowerStandby(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isAllowedInLowPowerStandby(String str) {
        try {
            return this.mService.isFeatureAllowedInLowPowerStandby(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public LowPowerStandbyPortsLock newLowPowerStandbyPortsLock(List<LowPowerStandbyPortDescription> list) {
        return new LowPowerStandbyPortsLock(list);
    }

    @SystemApi
    public List<LowPowerStandbyPortDescription> getActiveLowPowerStandbyPorts() {
        try {
            return LowPowerStandbyPortDescription.fromParcelable(this.mService.getActiveLowPowerStandbyPorts());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isIgnoringBatteryOptimizations(String str) {
        return getPowerExemptionManager().isAllowListed(str, true);
    }

    public void shutdown(boolean z, String str, boolean z2) {
        try {
            this.mService.shutdown(z, str, z2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isSustainedPerformanceModeSupported() {
        return this.mContext.getResources().getBoolean(R.bool.config_sustainedPerformanceModeSupported);
    }

    public int getCurrentThermalStatus() {
        try {
            return this.mThermalService.getCurrentThermalStatus();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void addThermalStatusListener(OnThermalStatusChangedListener onThermalStatusChangedListener) {
        Objects.requireNonNull(onThermalStatusChangedListener, "Thermal status listener cannot be null");
        addThermalStatusListener(this.mContext.getMainExecutor(), onThermalStatusChangedListener);
    }

    public void addThermalStatusListener(Executor executor, OnThermalStatusChangedListener onThermalStatusChangedListener) {
        Objects.requireNonNull(onThermalStatusChangedListener, "Thermal status listener cannot be null");
        Objects.requireNonNull(executor, "Executor cannot be null");
        synchronized (this.mThermalStatusListenerMap) {
            Preconditions.checkArgument(!this.mThermalStatusListenerMap.containsKey(onThermalStatusChangedListener), "Thermal status listener already registered: %s", onThermalStatusChangedListener);
            AnonymousClass4 anonymousClass4 = new AnonymousClass4(this, executor, onThermalStatusChangedListener);
            try {
                if (this.mThermalService.registerThermalStatusListener(anonymousClass4)) {
                    this.mThermalStatusListenerMap.put(onThermalStatusChangedListener, anonymousClass4);
                } else {
                    throw new RuntimeException("Thermal status listener failed to set");
                }
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    /* renamed from: android.os.PowerManager$4, reason: invalid class name */
    class AnonymousClass4 extends IThermalStatusListener.Stub {
        final /* synthetic */ Executor val$executor;
        final /* synthetic */ OnThermalStatusChangedListener val$listener;

        AnonymousClass4(PowerManager powerManager, Executor executor, OnThermalStatusChangedListener onThermalStatusChangedListener) {
            this.val$executor = executor;
            this.val$listener = onThermalStatusChangedListener;
        }

        @Override // android.os.IThermalStatusListener
        public void onStatusChange(final int i) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Executor executor = this.val$executor;
                final OnThermalStatusChangedListener onThermalStatusChangedListener = this.val$listener;
                executor.execute(new Runnable() { // from class: android.os.PowerManager$4$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        PowerManager.OnThermalStatusChangedListener.this.onThermalStatusChanged(i);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public void removeThermalStatusListener(OnThermalStatusChangedListener onThermalStatusChangedListener) {
        Objects.requireNonNull(onThermalStatusChangedListener, "Thermal status listener cannot be null");
        synchronized (this.mThermalStatusListenerMap) {
            IThermalStatusListener iThermalStatusListener = this.mThermalStatusListenerMap.get(onThermalStatusChangedListener);
            Preconditions.checkArgument(iThermalStatusListener != null, "Thermal status listener was not added");
            try {
                if (this.mThermalService.unregisterThermalStatusListener(iThermalStatusListener)) {
                    this.mThermalStatusListenerMap.remove(onThermalStatusChangedListener);
                } else {
                    throw new RuntimeException("Failed to unregister thermal status listener");
                }
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void addThermalHeadroomListener(OnThermalHeadroomChangedListener onThermalHeadroomChangedListener) {
        Objects.requireNonNull(onThermalHeadroomChangedListener, "Thermal headroom listener cannot be null");
        addThermalHeadroomListener(this.mContext.getMainExecutor(), onThermalHeadroomChangedListener);
    }

    public void addThermalHeadroomListener(Executor executor, OnThermalHeadroomChangedListener onThermalHeadroomChangedListener) {
        Objects.requireNonNull(onThermalHeadroomChangedListener, "Thermal headroom listener cannot be null");
        Objects.requireNonNull(executor, "Executor cannot be null");
        synchronized (this.mThermalHeadroomListenerMap) {
            Preconditions.checkArgument(!this.mThermalHeadroomListenerMap.containsKey(onThermalHeadroomChangedListener), "Thermal headroom listener already registered: %s", onThermalHeadroomChangedListener);
            AnonymousClass5 anonymousClass5 = new AnonymousClass5(executor, onThermalHeadroomChangedListener);
            try {
                if (this.mThermalService.registerThermalHeadroomListener(anonymousClass5)) {
                    this.mThermalHeadroomListenerMap.put(onThermalHeadroomChangedListener, anonymousClass5);
                } else {
                    throw new RuntimeException("Thermal headroom listener failed to set");
                }
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    /* renamed from: android.os.PowerManager$5, reason: invalid class name */
    class AnonymousClass5 extends IThermalHeadroomListener.Stub {
        final /* synthetic */ Executor val$executor;
        final /* synthetic */ OnThermalHeadroomChangedListener val$listener;

        AnonymousClass5(Executor executor, OnThermalHeadroomChangedListener onThermalHeadroomChangedListener) {
            this.val$executor = executor;
            this.val$listener = onThermalHeadroomChangedListener;
        }

        @Override // android.os.IThermalHeadroomListener
        public void onHeadroomChange(final float f, final float f2, final int i, float[] fArr) throws RemoteException {
            final Map convertThresholdsToMap = PowerManager.this.convertThresholdsToMap(fArr);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Executor executor = this.val$executor;
                final OnThermalHeadroomChangedListener onThermalHeadroomChangedListener = this.val$listener;
                executor.execute(new Runnable() { // from class: android.os.PowerManager$5$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        PowerManager.OnThermalHeadroomChangedListener.this.onThermalHeadroomChanged(f, f2, i, convertThresholdsToMap);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public void removeThermalHeadroomListener(OnThermalHeadroomChangedListener onThermalHeadroomChangedListener) {
        Objects.requireNonNull(onThermalHeadroomChangedListener, "Thermal headroom listener cannot be null");
        synchronized (this.mThermalHeadroomListenerMap) {
            IThermalHeadroomListener iThermalHeadroomListener = this.mThermalHeadroomListenerMap.get(onThermalHeadroomChangedListener);
            Preconditions.checkArgument(iThermalHeadroomListener != null, "Thermal headroom listener was not added");
            try {
                if (this.mThermalService.unregisterThermalHeadroomListener(iThermalHeadroomListener)) {
                    this.mThermalHeadroomListenerMap.remove(onThermalHeadroomChangedListener);
                } else {
                    throw new RuntimeException("Failed to unregister thermal status listener");
                }
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public float getThermalHeadroom(int i) {
        if (SystemClock.elapsedRealtime() - this.mLastHeadroomUpdate.get() < 500) {
            return Float.NaN;
        }
        try {
            float thermalHeadroom = this.mThermalService.getThermalHeadroom(i);
            this.mLastHeadroomUpdate.set(SystemClock.elapsedRealtime());
            return thermalHeadroom;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Map<Integer, Float> getThermalHeadroomThresholds() {
        try {
            return convertThresholdsToMap(this.mThermalService.getThermalHeadroomThresholds());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Map<Integer, Float> convertThresholdsToMap(float[] fArr) {
        ArrayMap arrayMap = new ArrayMap(6);
        for (int i = 1; i <= 6; i++) {
            if (!Float.isNaN(fArr[i])) {
                arrayMap.put(Integer.valueOf(i), Float.valueOf(fArr[i]));
            }
        }
        return arrayMap;
    }

    public void setDozeAfterScreenOff(boolean z) {
        try {
            this.mService.setDozeAfterScreenOff(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean isAmbientDisplayAvailable() {
        try {
            return this.mService.isAmbientDisplayAvailable();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void suppressAmbientDisplay(String str, boolean z) {
        try {
            this.mService.suppressAmbientDisplay(str, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean isAmbientDisplaySuppressedForToken(String str) {
        try {
            return this.mService.isAmbientDisplaySuppressedForToken(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean isAmbientDisplaySuppressed() {
        try {
            return this.mService.isAmbientDisplaySuppressed();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isAmbientDisplaySuppressedForTokenByApp(String str, int i) {
        try {
            return this.mService.isAmbientDisplaySuppressedForTokenByApp(str, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getLastShutdownReason() {
        try {
            return this.mService.getLastShutdownReason();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getLastSleepReason() {
        try {
            return this.mService.getLastSleepReason();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean forceSuspend() {
        try {
            return this.mService.forceSuspend();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static String lowPowerStandbyAllowedReasonsToString(int i) {
        ArrayList arrayList = new ArrayList();
        if ((i & 1) != 0) {
            arrayList.add("ALLOWED_REASON_VOICE_INTERACTION");
            i &= -2;
        }
        if ((i & 2) != 0) {
            arrayList.add("ALLOWED_REASON_TEMP_POWER_SAVE_ALLOWLIST");
            i &= -3;
        }
        if ((i & 4) != 0) {
            arrayList.add("ALLOWED_REASON_ONGOING_CALL");
            i &= -5;
        }
        if (i != 0) {
            arrayList.add(String.valueOf(i));
        }
        return String.join(",", arrayList);
    }

    @SystemApi
    public static final class LowPowerStandbyPolicy {
        private final Set<String> mAllowedFeatures;
        private final int mAllowedReasons;
        private final Set<String> mExemptPackages;
        private final String mIdentifier;

        public LowPowerStandbyPolicy(String str, Set<String> set, int i, Set<String> set2) {
            Objects.requireNonNull(str);
            Objects.requireNonNull(set);
            Objects.requireNonNull(set2);
            this.mIdentifier = str;
            this.mExemptPackages = Collections.unmodifiableSet(set);
            this.mAllowedReasons = i;
            this.mAllowedFeatures = Collections.unmodifiableSet(set2);
        }

        public String getIdentifier() {
            return this.mIdentifier;
        }

        public Set<String> getExemptPackages() {
            return this.mExemptPackages;
        }

        public int getAllowedReasons() {
            return this.mAllowedReasons;
        }

        public Set<String> getAllowedFeatures() {
            return this.mAllowedFeatures;
        }

        public String toString() {
            return "Policy{mIdentifier='" + this.mIdentifier + "', mExemptPackages=" + String.join(",", this.mExemptPackages) + ", mAllowedReasons=" + PowerManager.lowPowerStandbyAllowedReasonsToString(this.mAllowedReasons) + ", mAllowedFeatures=" + String.join(",", this.mAllowedFeatures) + '}';
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof LowPowerStandbyPolicy)) {
                return false;
            }
            LowPowerStandbyPolicy lowPowerStandbyPolicy = (LowPowerStandbyPolicy) obj;
            return this.mAllowedReasons == lowPowerStandbyPolicy.mAllowedReasons && Objects.equals(this.mIdentifier, lowPowerStandbyPolicy.mIdentifier) && Objects.equals(this.mExemptPackages, lowPowerStandbyPolicy.mExemptPackages) && Objects.equals(this.mAllowedFeatures, lowPowerStandbyPolicy.mAllowedFeatures);
        }

        public int hashCode() {
            return Objects.hash(this.mIdentifier, this.mExemptPackages, Integer.valueOf(this.mAllowedReasons), this.mAllowedFeatures);
        }

        public static IPowerManager.LowPowerStandbyPolicy toParcelable(LowPowerStandbyPolicy lowPowerStandbyPolicy) {
            if (lowPowerStandbyPolicy == null) {
                return null;
            }
            IPowerManager.LowPowerStandbyPolicy lowPowerStandbyPolicy2 = new IPowerManager.LowPowerStandbyPolicy();
            lowPowerStandbyPolicy2.identifier = lowPowerStandbyPolicy.mIdentifier;
            lowPowerStandbyPolicy2.exemptPackages = new ArrayList(lowPowerStandbyPolicy.mExemptPackages);
            lowPowerStandbyPolicy2.allowedReasons = lowPowerStandbyPolicy.mAllowedReasons;
            lowPowerStandbyPolicy2.allowedFeatures = new ArrayList(lowPowerStandbyPolicy.mAllowedFeatures);
            return lowPowerStandbyPolicy2;
        }

        public static LowPowerStandbyPolicy fromParcelable(IPowerManager.LowPowerStandbyPolicy lowPowerStandbyPolicy) {
            if (lowPowerStandbyPolicy == null) {
                return null;
            }
            return new LowPowerStandbyPolicy(lowPowerStandbyPolicy.identifier, new ArraySet(lowPowerStandbyPolicy.exemptPackages), lowPowerStandbyPolicy.allowedReasons, new ArraySet(lowPowerStandbyPolicy.allowedFeatures));
        }
    }

    @SystemApi
    public static final class LowPowerStandbyPortDescription {
        public static final int MATCH_PORT_LOCAL = 1;
        public static final int MATCH_PORT_REMOTE = 2;
        public static final int PROTOCOL_TCP = 6;
        public static final int PROTOCOL_UDP = 17;
        private final InetAddress mLocalAddress;
        private final int mPortMatcher;
        private final int mPortNumber;
        private final int mProtocol;

        @Retention(RetentionPolicy.SOURCE)
        public @interface PortMatcher {
        }

        @Retention(RetentionPolicy.SOURCE)
        public @interface Protocol {
        }

        public LowPowerStandbyPortDescription(int i, int i2, int i3) {
            this.mProtocol = i;
            this.mPortMatcher = i2;
            this.mPortNumber = i3;
            this.mLocalAddress = null;
        }

        public LowPowerStandbyPortDescription(int i, int i2, int i3, InetAddress inetAddress) {
            this.mProtocol = i;
            this.mPortMatcher = i2;
            this.mPortNumber = i3;
            this.mLocalAddress = inetAddress;
        }

        private String protocolToString(int i) {
            if (i == 6) {
                return SipDelegateImsConfiguration.SIP_TRANSPORT_TCP;
            }
            if (i == 17) {
                return SipDelegateImsConfiguration.SIP_TRANSPORT_UDP;
            }
            return String.valueOf(i);
        }

        private String portMatcherToString(int i) {
            if (i == 1) {
                return "MATCH_PORT_LOCAL";
            }
            if (i == 2) {
                return "MATCH_PORT_REMOTE";
            }
            return String.valueOf(i);
        }

        public int getProtocol() {
            return this.mProtocol;
        }

        public int getPortMatcher() {
            return this.mPortMatcher;
        }

        public int getPortNumber() {
            return this.mPortNumber;
        }

        public InetAddress getLocalAddress() {
            return this.mLocalAddress;
        }

        public String toString() {
            return "PortDescription{mProtocol=" + protocolToString(this.mProtocol) + ", mPortMatcher=" + portMatcherToString(this.mPortMatcher) + ", mPortNumber=" + this.mPortNumber + ", mLocalAddress=" + this.mLocalAddress + '}';
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof LowPowerStandbyPortDescription)) {
                return false;
            }
            LowPowerStandbyPortDescription lowPowerStandbyPortDescription = (LowPowerStandbyPortDescription) obj;
            return this.mProtocol == lowPowerStandbyPortDescription.mProtocol && this.mPortMatcher == lowPowerStandbyPortDescription.mPortMatcher && this.mPortNumber == lowPowerStandbyPortDescription.mPortNumber && Objects.equals(this.mLocalAddress, lowPowerStandbyPortDescription.mLocalAddress);
        }

        public int hashCode() {
            return Objects.hash(Integer.valueOf(this.mProtocol), Integer.valueOf(this.mPortMatcher), Integer.valueOf(this.mPortNumber), this.mLocalAddress);
        }

        public static IPowerManager.LowPowerStandbyPortDescription toParcelable(LowPowerStandbyPortDescription lowPowerStandbyPortDescription) {
            if (lowPowerStandbyPortDescription == null) {
                return null;
            }
            IPowerManager.LowPowerStandbyPortDescription lowPowerStandbyPortDescription2 = new IPowerManager.LowPowerStandbyPortDescription();
            lowPowerStandbyPortDescription2.protocol = lowPowerStandbyPortDescription.mProtocol;
            lowPowerStandbyPortDescription2.portMatcher = lowPowerStandbyPortDescription.mPortMatcher;
            lowPowerStandbyPortDescription2.portNumber = lowPowerStandbyPortDescription.mPortNumber;
            InetAddress inetAddress = lowPowerStandbyPortDescription.mLocalAddress;
            if (inetAddress != null) {
                lowPowerStandbyPortDescription2.localAddress = inetAddress.getAddress();
            }
            return lowPowerStandbyPortDescription2;
        }

        public static List<IPowerManager.LowPowerStandbyPortDescription> toParcelable(List<LowPowerStandbyPortDescription> list) {
            if (list == null) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            Iterator<LowPowerStandbyPortDescription> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(toParcelable(it.next()));
            }
            return arrayList;
        }

        public static LowPowerStandbyPortDescription fromParcelable(IPowerManager.LowPowerStandbyPortDescription lowPowerStandbyPortDescription) {
            InetAddress inetAddress = null;
            if (lowPowerStandbyPortDescription == null) {
                return null;
            }
            if (lowPowerStandbyPortDescription.localAddress != null) {
                try {
                    inetAddress = InetAddress.getByAddress(lowPowerStandbyPortDescription.localAddress);
                } catch (UnknownHostException e) {
                    Log.w(PowerManager.TAG, "Address has invalid length", e);
                }
            }
            return new LowPowerStandbyPortDescription(lowPowerStandbyPortDescription.protocol, lowPowerStandbyPortDescription.portMatcher, lowPowerStandbyPortDescription.portNumber, inetAddress);
        }

        public static List<LowPowerStandbyPortDescription> fromParcelable(List<IPowerManager.LowPowerStandbyPortDescription> list) {
            if (list == null) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            Iterator<IPowerManager.LowPowerStandbyPortDescription> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(fromParcelable(it.next()));
            }
            return arrayList;
        }
    }

    @SystemApi
    public final class LowPowerStandbyPortsLock {
        private boolean mHeld;
        private final List<LowPowerStandbyPortDescription> mPorts;
        private final IBinder mToken = new Binder();

        LowPowerStandbyPortsLock(List<LowPowerStandbyPortDescription> list) {
            this.mPorts = list;
        }

        public void acquire() {
            synchronized (this.mToken) {
                try {
                    try {
                        PowerManager.this.mService.acquireLowPowerStandbyPorts(this.mToken, LowPowerStandbyPortDescription.toParcelable(this.mPorts));
                        this.mHeld = true;
                    } catch (RemoteException e) {
                        throw e.rethrowFromSystemServer();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public void release() {
            synchronized (this.mToken) {
                try {
                    try {
                        PowerManager.this.mService.releaseLowPowerStandbyPorts(this.mToken);
                        this.mHeld = false;
                    } catch (RemoteException e) {
                        throw e.rethrowFromSystemServer();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        protected void finalize() {
            synchronized (this.mToken) {
                if (this.mHeld) {
                    Log.wtf(PowerManager.TAG, "LowPowerStandbyPorts finalized while still held");
                    release();
                }
            }
        }
    }

    public void setAutoBrightnessLimit(int i, int i2, boolean z) {
        try {
            this.mService.setAutoBrightnessLimit(i, i2, z);
        } catch (RemoteException unused) {
        }
    }

    public void semSetAutoBrightnessLimit(float f, float f2) {
        try {
            this.mService.setAutoBrightnessLimit(Float.compare(f, 0.0f) >= 0 ? BrightnessSynchronizer.brightnessFloatToInt(f) : -1, Float.compare(f2, 0.0f) >= 0 ? BrightnessSynchronizer.brightnessFloatToInt(f2) : -1, false);
        } catch (RemoteException unused) {
        }
    }

    public void setMasterBrightnessLimit(int i, int i2, int i3) {
        try {
            this.mService.setMasterBrightnessLimit(i, i2, i3);
        } catch (RemoteException unused) {
        }
    }

    public void setHdrBrightnessLimit(IBinder iBinder, int i, int i2) {
        try {
            this.mService.setHdrBrightnessLimit(iBinder, i, i2);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public float getCurrentBrightness(boolean z) {
        try {
            return this.mService.getCurrentBrightness(z);
        } catch (RemoteException unused) {
            return -1.0f;
        }
    }

    @Deprecated
    public void setScreenCurtainEnabled(IBinder iBinder, boolean z) {
        setScreenCurtainEnabled(iBinder, z, z ? 3 : 0);
    }

    public void setScreenCurtainEnabled(IBinder iBinder, boolean z, int i) {
        try {
            this.mService.setScreenCurtainEnabled(iBinder, z, i);
        } catch (RemoteException unused) {
        }
    }

    public boolean isScreenCurtainEnabled() {
        try {
            return this.mService.isScreenCurtainEnabled();
        } catch (RemoteException unused) {
            return false;
        }
    }

    public String getPackageNameOnScreenCurtain() {
        try {
            return this.mService.getPackageNameOnScreenCurtain();
        } catch (RemoteException unused) {
            return null;
        }
    }

    boolean isScreenCurtainEntryAvailable() {
        try {
            return this.mService.isScreenCurtainEntryAvailable();
        } catch (RemoteException unused) {
            return false;
        }
    }

    public void setScreenBrightnessScaleFactor(float f, IBinder iBinder) {
        try {
            this.mService.setScreenBrightnessScaleFactor(f, iBinder);
        } catch (RemoteException unused) {
        }
    }

    public void setFreezingScreenBrightness(boolean z) {
        try {
            this.mService.setFreezingScreenBrightness(z);
        } catch (RemoteException unused) {
        }
    }

    public void updateCoverState(boolean z) {
        try {
            this.mService.updateCoverState(z);
        } catch (RemoteException unused) {
        }
    }

    public void setCoverType(int i) {
        try {
            this.mService.setCoverType(i);
        } catch (RemoteException unused) {
        }
    }

    public void switchForceLcdBacklightOffState() {
        try {
            this.mService.switchForceLcdBacklightOffState();
        } catch (RemoteException unused) {
        }
    }

    public void setLCDFlashMode(boolean z, IBinder iBinder) {
        try {
            this.mService.setLCDFlashMode(z, iBinder);
        } catch (RemoteException unused) {
        }
    }

    public void semSetLcdFlashModeEnabled(boolean z, IBinder iBinder) {
        try {
            this.mService.setLCDFlashMode(z, iBinder);
        } catch (RemoteException unused) {
        }
    }

    public final class WakeLock {
        private IWakeLockCallback mCallback;
        private final int mDisplayId;
        private int mExternalCount;
        private int mFlags;
        private boolean mHeld;
        private String mHistoryTag;
        private int mInternalCount;
        private WakeLockStateListener mListener;
        private final String mPackageName;
        private int mProximityNegativeDebounce;
        private int mProximityPositiveDebounce;
        private String mTag;
        private int mTagHash;
        private WorkSource mWorkSource;
        private boolean mRefCounted = true;
        private final Runnable mReleaser = new Runnable() { // from class: android.os.PowerManager$WakeLock$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                PowerManager.WakeLock.this.lambda$new$0();
            }
        };
        private final IBinder mToken = new Binder();

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$0() {
            release(65536);
        }

        WakeLock(int i, String str, String str2, int i2) {
            this.mFlags = i;
            this.mTag = str;
            this.mTagHash = str.hashCode();
            this.mPackageName = str2;
            this.mDisplayId = i2;
        }

        protected void finalize() throws Throwable {
            synchronized (this.mToken) {
                if (this.mHeld) {
                    Log.wtf(PowerManager.TAG, "WakeLock finalized while still held: " + this.mTag);
                    Trace.asyncTraceForTrackEnd(131072L, "WakeLocks", this.mTagHash);
                    try {
                        PowerManager.this.mService.releaseWakeLock(this.mToken, 0);
                    } catch (RemoteException e) {
                        throw e.rethrowFromSystemServer();
                    }
                }
            }
        }

        public void setReferenceCounted(boolean z) {
            synchronized (this.mToken) {
                this.mRefCounted = z;
            }
        }

        public void setProximityDebounceTime(int i, int i2) {
            boolean z;
            synchronized (this.mToken) {
                if ((i < -1 && i > 3000) || (i2 < -1 && i2 > 3000)) {
                    throw new IllegalArgumentException("setProximityDebounceTime: positive = " + i + ", negative = " + i2);
                }
                boolean z2 = true;
                if (this.mProximityPositiveDebounce != i) {
                    this.mProximityPositiveDebounce = i;
                    z = true;
                } else {
                    z = false;
                }
                if (this.mProximityNegativeDebounce != i2) {
                    this.mProximityNegativeDebounce = i2;
                } else {
                    z2 = z;
                }
                if (z2) {
                    try {
                        PowerManager.this.mService.setProximityDebounceTime(this.mToken, i, i2);
                    } catch (RemoteException e) {
                        throw e.rethrowFromSystemServer();
                    }
                }
            }
        }

        public void acquire() {
            synchronized (this.mToken) {
                acquireLocked();
            }
        }

        public void acquire(long j) {
            synchronized (this.mToken) {
                acquireLocked();
                PowerManager.this.mHandler.postDelayed(this.mReleaser, j);
            }
        }

        private void acquireLocked() {
            int i = this.mInternalCount + 1;
            this.mInternalCount = i;
            this.mExternalCount++;
            if (!this.mRefCounted || i == 1) {
                PowerManager.this.mHandler.removeCallbacks(this.mReleaser);
                Trace.asyncTraceForTrackBegin(131072L, "WakeLocks", this.mTag, this.mTagHash);
                try {
                    PowerManager.this.mService.acquireWakeLock(this.mToken, this.mFlags, this.mTag, this.mPackageName, this.mWorkSource, this.mHistoryTag, this.mDisplayId, this.mCallback);
                    this.mHeld = true;
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }

        public void release() {
            release(0);
        }

        public void release(int i) {
            synchronized (this.mToken) {
                int i2 = this.mInternalCount;
                if (i2 > 0) {
                    this.mInternalCount = i2 - 1;
                }
                if ((65536 & i) == 0) {
                    this.mExternalCount--;
                }
                if (!this.mRefCounted || this.mInternalCount == 0) {
                    PowerManager.this.mHandler.removeCallbacks(this.mReleaser);
                    if (this.mHeld) {
                        Trace.asyncTraceForTrackEnd(131072L, "WakeLocks", this.mTagHash);
                        try {
                            PowerManager.this.mService.releaseWakeLock(this.mToken, i);
                            this.mHeld = false;
                        } catch (RemoteException e) {
                            throw e.rethrowFromSystemServer();
                        }
                    }
                }
                if (this.mRefCounted && this.mExternalCount < 0) {
                    throw new RuntimeException("WakeLock under-locked " + this.mTag);
                }
            }
        }

        public boolean isHeld() {
            boolean z;
            synchronized (this.mToken) {
                z = this.mHeld;
            }
            return z;
        }

        public void setWorkSource(WorkSource workSource) {
            synchronized (this.mToken) {
                if (workSource != null) {
                    try {
                        if (workSource.isEmpty()) {
                            workSource = null;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                boolean z = true;
                if (workSource == null) {
                    if (this.mWorkSource == null) {
                        z = false;
                    }
                    this.mWorkSource = null;
                } else {
                    WorkSource workSource2 = this.mWorkSource;
                    if (workSource2 == null) {
                        this.mWorkSource = new WorkSource(workSource);
                    } else {
                        boolean equals = workSource2.equals(workSource);
                        z = !equals;
                        if (!equals) {
                            this.mWorkSource.set(workSource);
                        }
                    }
                }
                if (z && this.mHeld) {
                    try {
                        PowerManager.this.mService.updateWakeLockWorkSource(this.mToken, this.mWorkSource, this.mHistoryTag);
                    } catch (RemoteException e) {
                        throw e.rethrowFromSystemServer();
                    }
                }
            }
        }

        public void setTag(String str) {
            this.mTag = str;
            this.mTagHash = str.hashCode();
        }

        public String getTag() {
            return this.mTag;
        }

        public void setHistoryTag(String str) {
            this.mHistoryTag = str;
        }

        public void setUnimportantForLogging(boolean z) {
            if (z) {
                this.mFlags |= 1073741824;
            } else {
                this.mFlags &= -1073741825;
            }
        }

        public void updateUids(int[] iArr) {
            synchronized (this.mToken) {
                try {
                    try {
                        PowerManager.this.mService.updateWakeLockUids(this.mToken, iArr);
                    } catch (RemoteException e) {
                        throw e.rethrowFromSystemServer();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public String toString() {
            String str;
            synchronized (this.mToken) {
                str = "WakeLock{" + Integer.toHexString(System.identityHashCode(this)) + " held=" + this.mHeld + ", refCount=" + this.mInternalCount + "}";
            }
            return str;
        }

        public void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
            synchronized (this.mToken) {
                long start = protoOutputStream.start(j);
                protoOutputStream.write(1138166333441L, this.mTag);
                protoOutputStream.write(1138166333442L, this.mPackageName);
                protoOutputStream.write(1133871366147L, this.mHeld);
                protoOutputStream.write(1120986464260L, this.mInternalCount);
                WorkSource workSource = this.mWorkSource;
                if (workSource != null) {
                    workSource.dumpDebug(protoOutputStream, 1146756268037L);
                }
                protoOutputStream.end(start);
            }
        }

        public Runnable wrap(final Runnable runnable) {
            acquire();
            return new Runnable() { // from class: android.os.PowerManager$WakeLock$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    PowerManager.WakeLock.this.lambda$wrap$1(runnable);
                }
            };
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$wrap$1(Runnable runnable) {
            try {
                runnable.run();
            } finally {
                release();
            }
        }

        public void setStateListener(Executor executor, WakeLockStateListener wakeLockStateListener) {
            Preconditions.checkNotNull(executor, "executor cannot be null");
            synchronized (this.mToken) {
                if (wakeLockStateListener != this.mListener) {
                    this.mListener = wakeLockStateListener;
                    if (wakeLockStateListener != null) {
                        this.mCallback = new AnonymousClass1(this, executor, wakeLockStateListener);
                    } else {
                        this.mCallback = null;
                    }
                    if (this.mHeld) {
                        try {
                            PowerManager.this.mService.updateWakeLockCallback(this.mToken, this.mCallback);
                        } catch (RemoteException e) {
                            throw e.rethrowFromSystemServer();
                        }
                    }
                }
            }
        }

        /* renamed from: android.os.PowerManager$WakeLock$1, reason: invalid class name */
        class AnonymousClass1 extends IWakeLockCallback.Stub {
            final /* synthetic */ Executor val$executor;
            final /* synthetic */ WakeLockStateListener val$listener;

            AnonymousClass1(WakeLock wakeLock, Executor executor, WakeLockStateListener wakeLockStateListener) {
                this.val$executor = executor;
                this.val$listener = wakeLockStateListener;
            }

            @Override // android.os.IWakeLockCallback
            public void onStateChanged(final boolean z) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    Executor executor = this.val$executor;
                    final WakeLockStateListener wakeLockStateListener = this.val$listener;
                    executor.execute(new Runnable() { // from class: android.os.PowerManager$WakeLock$1$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            PowerManager.WakeLockStateListener.this.onStateChanged(z);
                        }
                    });
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }
    }

    public static void invalidatePowerSaveModeCaches() {
        PropertyInvalidatedCache.invalidateCache("system_server", CACHE_KEY_IS_POWER_SAVE_MODE_API);
    }

    public static void invalidateIsInteractiveCaches() {
        PropertyInvalidatedCache.invalidateCache("system_server", CACHE_KEY_IS_INTERACTIVE_API);
    }

    public long getLastUserActivityTime(int i) {
        try {
            return this.mService.getLastUserActivityTime(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setEarlyWakeUp(boolean z) {
        try {
            this.mService.setEarlyWakeUp(z);
        } catch (RemoteException unused) {
        }
    }

    public String[] getWakeLockPackageList() {
        try {
            return this.mService.getWakeLockPackageList();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static final class AdaptiveScreenOffTimeoutConfig {
        private final String mPackageName;
        private final long mScreenOffTimeout;

        public AdaptiveScreenOffTimeoutConfig(String str, long j) {
            this.mPackageName = str;
            this.mScreenOffTimeout = j;
        }

        public String getPackageName() {
            return this.mPackageName;
        }

        public long getScreenOffTimeout() {
            return this.mScreenOffTimeout;
        }

        public static IPowerManager.AdaptiveScreenOffTimeoutConfig toParcelable(AdaptiveScreenOffTimeoutConfig adaptiveScreenOffTimeoutConfig) {
            if (adaptiveScreenOffTimeoutConfig == null) {
                return null;
            }
            IPowerManager.AdaptiveScreenOffTimeoutConfig adaptiveScreenOffTimeoutConfig2 = new IPowerManager.AdaptiveScreenOffTimeoutConfig();
            adaptiveScreenOffTimeoutConfig2.packageName = adaptiveScreenOffTimeoutConfig.mPackageName;
            adaptiveScreenOffTimeoutConfig2.screenOffTimeout = adaptiveScreenOffTimeoutConfig.mScreenOffTimeout;
            return adaptiveScreenOffTimeoutConfig2;
        }

        public static List<IPowerManager.AdaptiveScreenOffTimeoutConfig> toParcelable(List<AdaptiveScreenOffTimeoutConfig> list) {
            if (list == null) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            Iterator<AdaptiveScreenOffTimeoutConfig> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(toParcelable(it.next()));
            }
            return arrayList;
        }

        public static AdaptiveScreenOffTimeoutConfig fromParcelable(IPowerManager.AdaptiveScreenOffTimeoutConfig adaptiveScreenOffTimeoutConfig) {
            if (adaptiveScreenOffTimeoutConfig == null) {
                return null;
            }
            return new AdaptiveScreenOffTimeoutConfig(adaptiveScreenOffTimeoutConfig.packageName, adaptiveScreenOffTimeoutConfig.screenOffTimeout);
        }

        public static List<AdaptiveScreenOffTimeoutConfig> fromParcelable(List<IPowerManager.AdaptiveScreenOffTimeoutConfig> list) {
            if (list == null) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            Iterator<IPowerManager.AdaptiveScreenOffTimeoutConfig> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(fromParcelable(it.next()));
            }
            return arrayList;
        }
    }

    public boolean isDozeAfterScreenOff() {
        try {
            return this.mService.isDozeAfterScreenOff();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
