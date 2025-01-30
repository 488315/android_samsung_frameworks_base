package android.p009os;

import android.annotation.SystemApi;
import android.app.PropertyInvalidatedCache;
import android.app.WallpaperManager;
import android.content.Context;
import android.service.dreams.Sandman;
import android.sysprop.InitProperties;
import android.telephony.ims.SipDelegateImsConfiguration;
import android.text.format.DateFormat;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.util.proto.ProtoOutputStream;
import com.android.internal.C4337R;
import com.android.internal.display.BrightnessSynchronizer;
import com.android.internal.location.GpsNetInitiatedHandler;
import com.android.internal.util.Preconditions;
import com.samsung.android.media.AudioParameter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicLong;

/* loaded from: classes3.dex */
public final class PowerManager {

  @Deprecated public static final int ACQUIRE_CAUSES_WAKEUP = 268435456;
  public static final String ACTION_DEVICE_IDLE_MODE_CHANGED =
      "android.os.action.DEVICE_IDLE_MODE_CHANGED";
  public static final String ACTION_DEVICE_LIGHT_IDLE_MODE_CHANGED =
      "android.os.action.LIGHT_DEVICE_IDLE_MODE_CHANGED";
  public static final String ACTION_ENHANCED_DISCHARGE_PREDICTION_CHANGED =
      "android.os.action.ENHANCED_DISCHARGE_PREDICTION_CHANGED";

  @Deprecated
  public static final String ACTION_LIGHT_DEVICE_IDLE_MODE_CHANGED =
      "android.os.action.LIGHT_DEVICE_IDLE_MODE_CHANGED";

  public static final String ACTION_LOW_POWER_STANDBY_ENABLED_CHANGED =
      "android.os.action.LOW_POWER_STANDBY_ENABLED_CHANGED";
  public static final String ACTION_LOW_POWER_STANDBY_POLICY_CHANGED =
      "android.os.action.LOW_POWER_STANDBY_POLICY_CHANGED";

  @SystemApi
  public static final String ACTION_LOW_POWER_STANDBY_PORTS_CHANGED =
      "android.os.action.LOW_POWER_STANDBY_PORTS_CHANGED";

  public static final String ACTION_POWER_SAVE_MODE_CHANGED =
      "android.os.action.POWER_SAVE_MODE_CHANGED";
  public static final String ACTION_POWER_SAVE_MODE_CHANGED_INTERNAL =
      "android.os.action.POWER_SAVE_MODE_CHANGED_INTERNAL";
  public static final String ACTION_POWER_SAVE_TEMP_WHITELIST_CHANGED =
      "android.os.action.POWER_SAVE_TEMP_WHITELIST_CHANGED";
  public static final String ACTION_POWER_SAVE_WHITELIST_CHANGED =
      "android.os.action.POWER_SAVE_WHITELIST_CHANGED";
  public static final String ACTION_SCREEN_OFF_BY_PROXIMITY =
      "android.intent.action.ACTION_SCREEN_OFF_BY_PROXIMITY";
  public static final String ACTION_SCREEN_ON_BY_PROXIMITY =
      "android.intent.action.ACTION_SCREEN_ON_BY_PROXIMITY";
  public static final int BRIGHTNESS_CONSTRAINT_TYPE_DEFAULT = 2;
  public static final int BRIGHTNESS_CONSTRAINT_TYPE_DIM = 3;
  public static final int BRIGHTNESS_CONSTRAINT_TYPE_DOZE = 4;
  public static final int BRIGHTNESS_CONSTRAINT_TYPE_MAXIMUM = 1;
  public static final int BRIGHTNESS_CONSTRAINT_TYPE_MINIMUM = 0;
  public static final int BRIGHTNESS_DEFAULT = -1;
  public static final int BRIGHTNESS_INVALID = -1;
  public static final float BRIGHTNESS_INVALID_FLOAT = Float.NaN;
  public static final float BRIGHTNESS_MAX = 1.0f;
  public static final float BRIGHTNESS_MIN = 0.0f;
  public static final int BRIGHTNESS_OFF = 0;
  public static final float BRIGHTNESS_OFF_FLOAT = 0.0f;
  public static final int BRIGHTNESS_ON = 255;
  private static final String CACHE_KEY_IS_INTERACTIVE_PROPERTY = "cache_key.is_interactive";
  private static final String CACHE_KEY_IS_POWER_SAVE_MODE_PROPERTY =
      "cache_key.is_power_save_mode";
  public static final int DOZE_WAKE_LOCK = 64;
  public static final int DRAW_WAKE_LOCK = 128;
  public static final int DUAL_SCREEN_STATE_INNER = 0;
  public static final int DUAL_SCREEN_STATE_OUTER = 1;
  public static final int DUAL_SCREEN_STATE_UNKNOWN = -1;
  public static final String FEATURE_WAKE_ON_LAN_IN_LOW_POWER_STANDBY =
      "com.android.lowpowerstandby.WAKE_ON_LAN";

  @Deprecated public static final int FULL_WAKE_LOCK = 26;
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
  public static final int GO_TO_SLEEP_REASON_MAX = 25;
  public static final int GO_TO_SLEEP_REASON_MIN = 0;
  public static final int GO_TO_SLEEP_REASON_PALM_TOUCH_DOWN = 24;
  public static final int GO_TO_SLEEP_REASON_POWER_BUTTON = 4;
  public static final int GO_TO_SLEEP_REASON_PROXIMITY = 18;
  public static final int GO_TO_SLEEP_REASON_PUT_DOWN_MOTION = 22;
  public static final int GO_TO_SLEEP_REASON_QUIESCENT = 10;
  public static final int GO_TO_SLEEP_REASON_SLEEP_BUTTON = 6;
  public static final int GO_TO_SLEEP_REASON_TIMEOUT = 2;
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

  @SystemApi public static final int POWER_SAVE_MODE_TRIGGER_DYNAMIC = 1;

  @SystemApi public static final int POWER_SAVE_MODE_TRIGGER_PERCENTAGE = 0;
  public static final int PRE_IDLE_TIMEOUT_MODE_LONG = 1;
  public static final int PRE_IDLE_TIMEOUT_MODE_NORMAL = 0;
  public static final int PRE_IDLE_TIMEOUT_MODE_SHORT = 2;
  public static final int PROXIMITY_SCREEN_OFF_WAKE_LOCK = 32;
  public static final float RAMP_SPEED_INVALID_FLOAT = Float.NaN;
  public static final String REBOOT_QUIESCENT = "quiescent";
  public static final String REBOOT_RECOVERY = "recovery";
  public static final String REBOOT_RECOVERY_UPDATE = "recovery-update";
  public static final String REBOOT_REQUESTED_BY_DEVICE_OWNER = "deviceowner";
  public static final String REBOOT_SAFE_MODE = "safemode";

  @SystemApi public static final String REBOOT_USERSPACE = "userspace";
  public static final int RELEASE_FLAG_TIMEOUT = 65536;
  public static final int RELEASE_FLAG_WAIT_FOR_NO_PROXIMITY = 1;

  @Deprecated public static final int SCREEN_BRIGHT_WAKE_LOCK = 10;

  @Deprecated public static final int SCREEN_DIM_WAKE_LOCK = 6;
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
  public static final String SILENT_RESET_EXCEPTION_MSG =
      "NPE by silent reset. It's normal operation caused by device care";
  public static final String SILENT_RESET_PARAM = "silent.sec";

  @SystemApi public static final int SOUND_TRIGGER_MODE_ALL_DISABLED = 2;

  @SystemApi public static final int SOUND_TRIGGER_MODE_ALL_ENABLED = 0;

  @SystemApi public static final int SOUND_TRIGGER_MODE_CRITICAL_ONLY = 1;
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

  @SystemApi public static final int USER_ACTIVITY_EVENT_ACCESSIBILITY = 3;
  public static final int USER_ACTIVITY_EVENT_ATTENTION = 4;

  @SystemApi public static final int USER_ACTIVITY_EVENT_BUTTON = 1;
  public static final int USER_ACTIVITY_EVENT_DEVICE_STATE = 6;
  public static final int USER_ACTIVITY_EVENT_FACE_DOWN = 5;

  @SystemApi public static final int USER_ACTIVITY_EVENT_OTHER = 0;

  @SystemApi public static final int USER_ACTIVITY_EVENT_TOUCH = 2;
  public static final int USER_ACTIVITY_FLAG_HOVER = 8192;
  public static final int USER_ACTIVITY_FLAG_IME = 4096;

  @SystemApi public static final int USER_ACTIVITY_FLAG_INDIRECT = 2;
  public static final int USER_ACTIVITY_FLAG_INTERNALKEY = 32768;
  public static final int USER_ACTIVITY_FLAG_NAVIBAR = 16384;

  @SystemApi public static final int USER_ACTIVITY_FLAG_NO_CHANGE_LIGHTS = 1;
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
  public static final int WAKE_REASON_DOCK = 105;
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
  final Context mContext;
  final Handler mHandler;
  private final PropertyInvalidatedCache<Integer, Boolean> mInteractiveCache;
  private PowerExemptionManager mPowerExemptionManager;
  private final PropertyInvalidatedCache<Void, Boolean> mPowerSaveModeCache;
  final IPowerManager mService;
  final IThermalService mThermalService;
  private final ArrayMap<OnThermalStatusChangedListener, IThermalStatusListener> mListenerMap =
      new ArrayMap<>();
  private final AtomicLong mLastHeadroomUpdate = new AtomicLong(0);

  @Retention(RetentionPolicy.SOURCE)
  public @interface AutoPowerSaveModeTriggers {}

  @Retention(RetentionPolicy.SOURCE)
  public @interface BrightnessConstraint {}

  @Retention(RetentionPolicy.SOURCE)
  public @interface GoToSleepReason {}

  @Retention(RetentionPolicy.SOURCE)
  public @interface LocationPowerSaveMode {}

  @Retention(RetentionPolicy.SOURCE)
  public @interface LowPowerStandbyAllowedReason {}

  public interface OnThermalStatusChangedListener {
    void onThermalStatusChanged(int i);
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
  public @interface ShutdownReason {}

  @Retention(RetentionPolicy.SOURCE)
  public @interface SoundTriggerPowerSaveMode {}

  @Retention(RetentionPolicy.SOURCE)
  public @interface ThermalStatus {}

  @Retention(RetentionPolicy.SOURCE)
  public @interface UserActivityEvent {}

  public interface WakeLockStateListener {
    void onStateChanged(boolean z);
  }

  @Retention(RetentionPolicy.SOURCE)
  public @interface WakeReason {}

  public static String userActivityEventToString(int userActivityEvent) {
    switch (userActivityEvent) {
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
        return Integer.toString(userActivityEvent);
    }
  }

  public static String userActivityFlagsToString(int userActivityFlags) {
    String result = (userActivityFlags & 1) != 0 ? " NO_CHANGE_LIGHTS" : "";
    if ((userActivityFlags & 2) != 0) {
      result = result + " INDIRECT";
    }
    if ((userActivityFlags & 4096) != 0) {
      result = result + " IME";
    }
    if ((userActivityFlags & 8192) != 0) {
      result = result + " HOVER";
    }
    if ((userActivityFlags & 16384) != 0) {
      result = result + " NAVIBAR";
    }
    if ((32768 & userActivityFlags) != 0) {
      return result + " INTERNALKEY";
    }
    return result;
  }

  public static String sleepReasonToString(int sleepReason) {
    switch (sleepReason) {
      case 0:
        return "application";
      case 1:
        return "device_admin";
      case 2:
        return GpsNetInitiatedHandler.NI_INTENT_KEY_TIMEOUT;
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
      case 15:
      case 16:
      case 17:
      default:
        return Integer.toString(sleepReason);
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
    }
  }

  public static String wakeReasonToString(int wakeReason) {
    switch (wakeReason) {
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
      case 101:
        return "camera_lens_cover";
      case 102:
        return "spen";
      case 103:
        return "cover_open";
      case 104:
        return "dream";
      case 105:
        return AudioParameter.VALUE_DOCK;
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
      default:
        return Integer.toString(wakeReason);
    }
  }

  public static class WakeData {
    public final long sleepDurationRealtime;
    public final int wakeReason;
    public final long wakeTime;

    public WakeData(long wakeTime, int wakeReason, long sleepDurationRealtime) {
      this.wakeTime = wakeTime;
      this.wakeReason = wakeReason;
      this.sleepDurationRealtime = sleepDurationRealtime;
    }

    public boolean equals(Object o) {
      if (!(o instanceof WakeData)) {
        return false;
      }
      WakeData other = (WakeData) o;
      return this.wakeTime == other.wakeTime
          && this.wakeReason == other.wakeReason
          && this.sleepDurationRealtime == other.sleepDurationRealtime;
    }

    public int hashCode() {
      return Objects.hash(
          Long.valueOf(this.wakeTime),
          Integer.valueOf(this.wakeReason),
          Long.valueOf(this.sleepDurationRealtime));
    }
  }

  public static class SleepData {
    public final int goToSleepReason;
    public final long goToSleepUptimeMillis;

    public SleepData(long goToSleepUptimeMillis, int goToSleepReason) {
      this.goToSleepUptimeMillis = goToSleepUptimeMillis;
      this.goToSleepReason = goToSleepReason;
    }

    public boolean equals(Object o) {
      if (!(o instanceof SleepData)) {
        return false;
      }
      SleepData other = (SleepData) o;
      return this.goToSleepUptimeMillis == other.goToSleepUptimeMillis
          && this.goToSleepReason == other.goToSleepReason;
    }

    public int hashCode() {
      return Objects.hash(
          Long.valueOf(this.goToSleepUptimeMillis), Integer.valueOf(this.goToSleepReason));
    }
  }

  public static String locationPowerSaveModeToString(int mode) {
    switch (mode) {
      case 0:
        return "NO_CHANGE";
      case 1:
        return "GPS_DISABLED_WHEN_SCREEN_OFF";
      case 2:
        return "ALL_DISABLED_WHEN_SCREEN_OFF";
      case 3:
        return "FOREGROUND_ONLY";
      case 4:
        return "THROTTLE_REQUESTS_WHEN_SCREEN_OFF";
      default:
        return Integer.toString(mode);
    }
  }

  public PowerManager(
      Context context, IPowerManager service, IThermalService thermalService, Handler handler) {
    int i = 1;
    this.mPowerSaveModeCache =
        new PropertyInvalidatedCache<Void, Boolean>(
            i, CACHE_KEY_IS_POWER_SAVE_MODE_PROPERTY) { // from class: android.os.PowerManager.1
          @Override // android.app.PropertyInvalidatedCache
          public Boolean recompute(Void query) {
            try {
              return Boolean.valueOf(PowerManager.this.mService.isPowerSaveMode());
            } catch (RemoteException e) {
              throw e.rethrowFromSystemServer();
            }
          }
        };
    this.mInteractiveCache =
        new PropertyInvalidatedCache<Integer, Boolean>(
            i, CACHE_KEY_IS_INTERACTIVE_PROPERTY) { // from class: android.os.PowerManager.2
          @Override // android.app.PropertyInvalidatedCache
          public Boolean recompute(Integer displayId) {
            try {
              if (displayId == null) {
                return Boolean.valueOf(PowerManager.this.mService.isInteractive());
              }
              return Boolean.valueOf(
                  PowerManager.this.mService.isDisplayInteractive(displayId.intValue()));
            } catch (RemoteException e) {
              throw e.rethrowFromSystemServer();
            }
          }
        };
    this.mContext = context;
    this.mService = service;
    this.mThermalService = thermalService;
    this.mHandler = handler;
  }

  private PowerExemptionManager getPowerExemptionManager() {
    if (this.mPowerExemptionManager == null) {
      this.mPowerExemptionManager =
          (PowerExemptionManager) this.mContext.getSystemService(PowerExemptionManager.class);
    }
    return this.mPowerExemptionManager;
  }

  public int getMinimumScreenBrightnessSetting() {
    return this.mContext
        .getResources()
        .getInteger(C4337R.integer.config_screenBrightnessSettingMinimum);
  }

  public int getMaximumScreenBrightnessSetting() {
    return this.mContext
        .getResources()
        .getInteger(C4337R.integer.config_screenBrightnessSettingMaximum);
  }

  public int semGetMinimumScreenBrightnessSetting() {
    return getMinimumScreenBrightnessSetting();
  }

  public int semGetMaximumScreenBrightnessSetting() {
    return getMaximumScreenBrightnessSetting();
  }

  public int getDefaultScreenBrightnessSetting() {
    return this.mContext
        .getResources()
        .getInteger(C4337R.integer.config_screenBrightnessSettingDefault);
  }

  public int getMaximumScreenBrightnessExtended() {
    return this.mContext
        .getResources()
        .getInteger(C4337R.integer.config_screenBrightnessExtendedMaximum);
  }

  public int semGetMaximumSubScreenBrightnessSetting() {
    return -1;
  }

  public boolean semIsSubAutoBrightnessSupported() {
    return this.mContext
        .getResources()
        .getBoolean(C4337R.bool.config_cover_automatic_brightness_available);
  }

  public float getBrightnessConstraint(int constraint) {
    try {
      return this.mService.getBrightnessConstraint(constraint);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  public WakeLock newWakeLock(int levelAndFlags, String tag) {
    validateWakeLockParameters(levelAndFlags, tag);
    return new WakeLock(levelAndFlags, tag, this.mContext.getOpPackageName(), -1);
  }

  public WakeLock newWakeLock(int levelAndFlags, String tag, int displayId) {
    validateWakeLockParameters(levelAndFlags, tag);
    return new WakeLock(levelAndFlags, tag, this.mContext.getOpPackageName(), displayId);
  }

  public static void validateWakeLockParameters(int levelAndFlags, String tag) {
    switch (65535 & levelAndFlags) {
      case 1:
      case 6:
      case 10:
      case 26:
      case 32:
      case 64:
      case 128:
        if (tag == null) {
          throw new IllegalArgumentException("The tag must not be null.");
        }
        return;
      default:
        throw new IllegalArgumentException("Must specify a valid wake lock level.");
    }
  }

  @Deprecated
  public void userActivity(long j, boolean z) {
    userActivity(j, 0, z ? 1 : 0);
  }

  @SystemApi
  public void userActivity(long when, int event, int flags) {
    try {
      this.mService.userActivity(this.mContext.getDisplayId(), when, event, flags);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  public void semGoToSleep(long time) {
    goToSleep(time);
  }

  public void semGoToSleep(long time, int reason, int flags) {
    goToSleep(time, reason, flags);
  }

  public void goToSleep(long time) {
    goToSleep(time, 0, 0);
  }

  public void goToSleep(long time, int reason, int flags) {
    try {
      this.mService.goToSleep(time, reason, flags);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  public void goToSleep(int displayId, long time, int reason, int flags) {
    try {
      this.mService.goToSleepWithDisplayId(displayId, time, reason, flags);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  public void semWakeUp(long time, int reason, String details) {
    wakeUp(time, reason, details);
  }

  @Deprecated
  public void wakeUp(long time) {
    wakeUp(time, 0, "wakeUp");
  }

  @Deprecated
  public void wakeUp(long time, String details) {
    wakeUp(time, 0, details);
  }

  public void wakeUp(long time, int reason, String details) {
    try {
      this.mService.wakeUp(time, reason, details, this.mContext.getOpPackageName());
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  public void nap(long time) {
    try {
      this.mService.nap(time);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @SystemApi
  public void dream(long time) {
    Sandman.startDreamByUserRequest(this.mContext);
  }

  public void boostScreenBrightness(long time) {
    try {
      this.mService.boostScreenBrightness(time);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  public boolean isWakeLockLevelSupported(int level) {
    try {
      return this.mService.isWakeLockLevelSupported(level);
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
      int displayId = this.mContext.getDisplayId();
      return this.mService.isInteractiveForDisplay(displayId);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  public boolean isInteractive(int displayId) {
    return this.mInteractiveCache.query(Integer.valueOf(displayId)).booleanValue();
  }

  public static boolean isRebootingUserspaceSupportedImpl() {
    return InitProperties.is_userspace_reboot_supported().orElse(false).booleanValue();
  }

  public boolean isRebootingUserspaceSupported() {
    return isRebootingUserspaceSupportedImpl();
  }

  public void reboot(String reason) {
    if (REBOOT_USERSPACE.equals(reason) && !isRebootingUserspaceSupported()) {
      throw new UnsupportedOperationException(
          "Attempted userspace reboot on a device that doesn't support it");
    }
    try {
      this.mService.reboot(false, reason, true);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  public void systemReboot(String reason) {
    try {
      Log.m94d(TAG, "calling systemReboot");
      this.mService.systemReboot(reason);
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
  public boolean setPowerSaveModeEnabled(boolean mode) {
    try {
      return this.mService.setPowerSaveModeEnabled(mode);
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
  public boolean setFullPowerSavePolicy(BatterySaverPolicyConfig config) {
    try {
      return this.mService.setFullPowerSavePolicy(config);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @SystemApi
  public boolean setDynamicPowerSaveHint(boolean powerSaveHint, int disableThreshold) {
    try {
      return this.mService.setDynamicPowerSaveHint(powerSaveHint, disableThreshold);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @SystemApi
  public boolean setAdaptivePowerSavePolicy(BatterySaverPolicyConfig config) {
    try {
      return this.mService.setAdaptivePowerSavePolicy(config);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @SystemApi
  public boolean setAdaptivePowerSaveEnabled(boolean enabled) {
    try {
      return this.mService.setAdaptivePowerSaveEnabled(enabled);
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
  public void setBatteryDischargePrediction(Duration timeRemaining, boolean isPersonalized) {
    if (timeRemaining == null) {
      throw new IllegalArgumentException("time remaining must not be null");
    }
    try {
      this.mService.setBatteryDischargePrediction(
          new ParcelDuration(timeRemaining), isPersonalized);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  public Duration getBatteryDischargePrediction() {
    try {
      ParcelDuration parcelDuration = this.mService.getBatteryDischargePrediction();
      if (parcelDuration == null) {
        return null;
      }
      return parcelDuration.getDuration();
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

  public PowerSaveState getPowerSaveState(int serviceType) {
    try {
      return this.mService.getPowerSaveState(serviceType);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  public int getLocationPowerSaveMode() {
    PowerSaveState powerSaveState = getPowerSaveState(1);
    if (!powerSaveState.batterySaverEnabled) {
      return 0;
    }
    return powerSaveState.locationMode;
  }

  public int getSoundTriggerPowerSaveMode() {
    PowerSaveState powerSaveState = getPowerSaveState(8);
    if (!powerSaveState.batterySaverEnabled) {
      return 0;
    }
    return powerSaveState.soundTriggerMode;
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
  public void setLowPowerStandbyEnabled(boolean enabled) {
    try {
      this.mService.setLowPowerStandbyEnabled(enabled);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @SystemApi
  public void setLowPowerStandbyActiveDuringMaintenance(boolean activeDuringMaintenance) {
    try {
      this.mService.setLowPowerStandbyActiveDuringMaintenance(activeDuringMaintenance);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  public void forceLowPowerStandbyActive(boolean active) {
    try {
      this.mService.forceLowPowerStandbyActive(active);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @SystemApi
  public void setLowPowerStandbyPolicy(LowPowerStandbyPolicy policy) {
    try {
      this.mService.setLowPowerStandbyPolicy(LowPowerStandbyPolicy.toParcelable(policy));
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

  public boolean isAllowedInLowPowerStandby(int reason) {
    try {
      return this.mService.isReasonAllowedInLowPowerStandby(reason);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  public boolean isAllowedInLowPowerStandby(String feature) {
    try {
      return this.mService.isFeatureAllowedInLowPowerStandby(feature);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @SystemApi
  public LowPowerStandbyPortsLock newLowPowerStandbyPortsLock(
      List<LowPowerStandbyPortDescription> ports) {
    LowPowerStandbyPortsLock standbyPorts = new LowPowerStandbyPortsLock(ports);
    return standbyPorts;
  }

  @SystemApi
  public List<LowPowerStandbyPortDescription> getActiveLowPowerStandbyPorts() {
    try {
      return LowPowerStandbyPortDescription.fromParcelable(
          this.mService.getActiveLowPowerStandbyPorts());
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  public boolean isIgnoringBatteryOptimizations(String packageName) {
    return getPowerExemptionManager().isAllowListed(packageName, true);
  }

  public void shutdown(boolean confirm, String reason, boolean wait) {
    try {
      this.mService.shutdown(confirm, reason, wait);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  public boolean isSustainedPerformanceModeSupported() {
    return this.mContext
        .getResources()
        .getBoolean(C4337R.bool.config_sustainedPerformanceModeSupported);
  }

  public int getCurrentThermalStatus() {
    try {
      return this.mThermalService.getCurrentThermalStatus();
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  public void addThermalStatusListener(OnThermalStatusChangedListener listener) {
    Objects.requireNonNull(listener, "listener cannot be null");
    addThermalStatusListener(this.mContext.getMainExecutor(), listener);
  }

  public void addThermalStatusListener(Executor executor, OnThermalStatusChangedListener listener) {
    Objects.requireNonNull(listener, "listener cannot be null");
    Objects.requireNonNull(executor, "executor cannot be null");
    Preconditions.checkArgument(
        !this.mListenerMap.containsKey(listener), "Listener already registered: %s", listener);
    IThermalStatusListener internalListener =
        new IThermalStatusListenerStubC23533(executor, listener);
    try {
      if (this.mThermalService.registerThermalStatusListener(internalListener)) {
        this.mListenerMap.put(listener, internalListener);
        return;
      }
      throw new RuntimeException("Listener failed to set");
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  /* renamed from: android.os.PowerManager$3 */
  class IThermalStatusListenerStubC23533 extends IThermalStatusListener.Stub {
    final /* synthetic */ Executor val$executor;
    final /* synthetic */ OnThermalStatusChangedListener val$listener;

    IThermalStatusListenerStubC23533(
        Executor executor, OnThermalStatusChangedListener onThermalStatusChangedListener) {
      this.val$executor = executor;
      this.val$listener = onThermalStatusChangedListener;
    }

    @Override // android.p009os.IThermalStatusListener
    public void onStatusChange(final int status) {
      long token = Binder.clearCallingIdentity();
      try {
        Executor executor = this.val$executor;
        final OnThermalStatusChangedListener onThermalStatusChangedListener = this.val$listener;
        executor.execute(
            new Runnable() { // from class: android.os.PowerManager$3$$ExternalSyntheticLambda0
              @Override // java.lang.Runnable
              public final void run() {
                PowerManager.OnThermalStatusChangedListener.this.onThermalStatusChanged(status);
              }
            });
      } finally {
        Binder.restoreCallingIdentity(token);
      }
    }
  }

  public void removeThermalStatusListener(OnThermalStatusChangedListener listener) {
    Objects.requireNonNull(listener, "listener cannot be null");
    IThermalStatusListener internalListener = this.mListenerMap.get(listener);
    Preconditions.checkArgument(internalListener != null, "Listener was not added");
    try {
      if (this.mThermalService.unregisterThermalStatusListener(internalListener)) {
        this.mListenerMap.remove(listener);
        return;
      }
      throw new RuntimeException("Listener failed to remove");
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  public float getThermalHeadroom(int forecastSeconds) {
    long now = SystemClock.elapsedRealtime();
    long timeSinceLastUpdate = now - this.mLastHeadroomUpdate.get();
    if (timeSinceLastUpdate < 500) {
      return Float.NaN;
    }
    try {
      float forecast = this.mThermalService.getThermalHeadroom(forecastSeconds);
      this.mLastHeadroomUpdate.set(SystemClock.elapsedRealtime());
      return forecast;
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  public void setDozeAfterScreenOff(boolean dozeAfterScreenOf) {
    try {
      this.mService.setDozeAfterScreenOff(dozeAfterScreenOf);
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
  public void suppressAmbientDisplay(String token, boolean suppress) {
    try {
      this.mService.suppressAmbientDisplay(token, suppress);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  @SystemApi
  public boolean isAmbientDisplaySuppressedForToken(String token) {
    try {
      return this.mService.isAmbientDisplaySuppressedForToken(token);
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

  public boolean isAmbientDisplaySuppressedForTokenByApp(String token, int appUid) {
    try {
      return this.mService.isAmbientDisplaySuppressedForTokenByApp(token, appUid);
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

  public static String lowPowerStandbyAllowedReasonsToString(int allowedReasons) {
    ArrayList<String> allowedStrings = new ArrayList<>();
    if ((allowedReasons & 1) != 0) {
      allowedStrings.add("ALLOWED_REASON_VOICE_INTERACTION");
      allowedReasons &= -2;
    }
    if ((allowedReasons & 2) != 0) {
      allowedStrings.add("ALLOWED_REASON_TEMP_POWER_SAVE_ALLOWLIST");
      allowedReasons &= -3;
    }
    if ((allowedReasons & 4) != 0) {
      allowedStrings.add("ALLOWED_REASON_ONGOING_CALL");
      allowedReasons &= -5;
    }
    if (allowedReasons != 0) {
      allowedStrings.add(String.valueOf(allowedReasons));
    }
    return String.join(",", allowedStrings);
  }

  @SystemApi
  public static final class LowPowerStandbyPolicy {
    private final Set<String> mAllowedFeatures;
    private final int mAllowedReasons;
    private final Set<String> mExemptPackages;
    private final String mIdentifier;

    public LowPowerStandbyPolicy(
        String identifier,
        Set<String> exemptPackages,
        int allowedReasons,
        Set<String> allowedFeatures) {
      Objects.requireNonNull(identifier);
      Objects.requireNonNull(exemptPackages);
      Objects.requireNonNull(allowedFeatures);
      this.mIdentifier = identifier;
      this.mExemptPackages = Collections.unmodifiableSet(exemptPackages);
      this.mAllowedReasons = allowedReasons;
      this.mAllowedFeatures = Collections.unmodifiableSet(allowedFeatures);
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
      return "Policy{mIdentifier='"
          + this.mIdentifier
          + DateFormat.QUOTE
          + ", mExemptPackages="
          + String.join(",", this.mExemptPackages)
          + ", mAllowedReasons="
          + PowerManager.lowPowerStandbyAllowedReasonsToString(this.mAllowedReasons)
          + ", mAllowedFeatures="
          + String.join(",", this.mAllowedFeatures)
          + '}';
    }

    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof LowPowerStandbyPolicy)) {
        return false;
      }
      LowPowerStandbyPolicy that = (LowPowerStandbyPolicy) o;
      return this.mAllowedReasons == that.mAllowedReasons
          && Objects.equals(this.mIdentifier, that.mIdentifier)
          && Objects.equals(this.mExemptPackages, that.mExemptPackages)
          && Objects.equals(this.mAllowedFeatures, that.mAllowedFeatures);
    }

    public int hashCode() {
      return Objects.hash(
          this.mIdentifier,
          this.mExemptPackages,
          Integer.valueOf(this.mAllowedReasons),
          this.mAllowedFeatures);
    }

    public static IPowerManager.LowPowerStandbyPolicy toParcelable(LowPowerStandbyPolicy policy) {
      if (policy == null) {
        return null;
      }
      IPowerManager.LowPowerStandbyPolicy parcelablePolicy =
          new IPowerManager.LowPowerStandbyPolicy();
      parcelablePolicy.identifier = policy.mIdentifier;
      parcelablePolicy.exemptPackages = new ArrayList(policy.mExemptPackages);
      parcelablePolicy.allowedReasons = policy.mAllowedReasons;
      parcelablePolicy.allowedFeatures = new ArrayList(policy.mAllowedFeatures);
      return parcelablePolicy;
    }

    public static LowPowerStandbyPolicy fromParcelable(
        IPowerManager.LowPowerStandbyPolicy parcelablePolicy) {
      if (parcelablePolicy == null) {
        return null;
      }
      return new LowPowerStandbyPolicy(
          parcelablePolicy.identifier,
          new ArraySet(parcelablePolicy.exemptPackages),
          parcelablePolicy.allowedReasons,
          new ArraySet(parcelablePolicy.allowedFeatures));
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
    public @interface PortMatcher {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface Protocol {}

    public LowPowerStandbyPortDescription(int protocol, int portMatcher, int portNumber) {
      this.mProtocol = protocol;
      this.mPortMatcher = portMatcher;
      this.mPortNumber = portNumber;
      this.mLocalAddress = null;
    }

    public LowPowerStandbyPortDescription(
        int protocol, int portMatcher, int portNumber, InetAddress localAddress) {
      this.mProtocol = protocol;
      this.mPortMatcher = portMatcher;
      this.mPortNumber = portNumber;
      this.mLocalAddress = localAddress;
    }

    private String protocolToString(int protocol) {
      switch (protocol) {
        case 6:
          return SipDelegateImsConfiguration.SIP_TRANSPORT_TCP;
        case 17:
          return SipDelegateImsConfiguration.SIP_TRANSPORT_UDP;
        default:
          return String.valueOf(protocol);
      }
    }

    private String portMatcherToString(int portMatcher) {
      switch (portMatcher) {
        case 1:
          return "MATCH_PORT_LOCAL";
        case 2:
          return "MATCH_PORT_REMOTE";
        default:
          return String.valueOf(portMatcher);
      }
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
      return "PortDescription{mProtocol="
          + protocolToString(this.mProtocol)
          + ", mPortMatcher="
          + portMatcherToString(this.mPortMatcher)
          + ", mPortNumber="
          + this.mPortNumber
          + ", mLocalAddress="
          + this.mLocalAddress
          + '}';
    }

    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof LowPowerStandbyPortDescription)) {
        return false;
      }
      LowPowerStandbyPortDescription that = (LowPowerStandbyPortDescription) o;
      return this.mProtocol == that.mProtocol
          && this.mPortMatcher == that.mPortMatcher
          && this.mPortNumber == that.mPortNumber
          && Objects.equals(this.mLocalAddress, that.mLocalAddress);
    }

    public int hashCode() {
      return Objects.hash(
          Integer.valueOf(this.mProtocol),
          Integer.valueOf(this.mPortMatcher),
          Integer.valueOf(this.mPortNumber),
          this.mLocalAddress);
    }

    public static IPowerManager.LowPowerStandbyPortDescription toParcelable(
        LowPowerStandbyPortDescription portDescription) {
      if (portDescription == null) {
        return null;
      }
      IPowerManager.LowPowerStandbyPortDescription parcelablePortDescription =
          new IPowerManager.LowPowerStandbyPortDescription();
      parcelablePortDescription.protocol = portDescription.mProtocol;
      parcelablePortDescription.portMatcher = portDescription.mPortMatcher;
      parcelablePortDescription.portNumber = portDescription.mPortNumber;
      InetAddress inetAddress = portDescription.mLocalAddress;
      if (inetAddress != null) {
        parcelablePortDescription.localAddress = inetAddress.getAddress();
      }
      return parcelablePortDescription;
    }

    public static List<IPowerManager.LowPowerStandbyPortDescription> toParcelable(
        List<LowPowerStandbyPortDescription> portDescriptions) {
      if (portDescriptions == null) {
        return null;
      }
      ArrayList<IPowerManager.LowPowerStandbyPortDescription> result = new ArrayList<>();
      for (LowPowerStandbyPortDescription port : portDescriptions) {
        result.add(toParcelable(port));
      }
      return result;
    }

    public static LowPowerStandbyPortDescription fromParcelable(
        IPowerManager.LowPowerStandbyPortDescription parcelablePortDescription) {
      if (parcelablePortDescription == null) {
        return null;
      }
      InetAddress localAddress = null;
      if (parcelablePortDescription.localAddress != null) {
        try {
          localAddress = InetAddress.getByAddress(parcelablePortDescription.localAddress);
        } catch (UnknownHostException e) {
          Log.m103w(PowerManager.TAG, "Address has invalid length", e);
        }
      }
      return new LowPowerStandbyPortDescription(
          parcelablePortDescription.protocol,
          parcelablePortDescription.portMatcher,
          parcelablePortDescription.portNumber,
          localAddress);
    }

    public static List<LowPowerStandbyPortDescription> fromParcelable(
        List<IPowerManager.LowPowerStandbyPortDescription> portDescriptions) {
      if (portDescriptions == null) {
        return null;
      }
      ArrayList<LowPowerStandbyPortDescription> result = new ArrayList<>();
      for (IPowerManager.LowPowerStandbyPortDescription port : portDescriptions) {
        result.add(fromParcelable(port));
      }
      return result;
    }
  }

  @SystemApi
  public final class LowPowerStandbyPortsLock {
    private boolean mHeld;
    private final List<LowPowerStandbyPortDescription> mPorts;
    private final IBinder mToken = new Binder();

    LowPowerStandbyPortsLock(List<LowPowerStandbyPortDescription> ports) {
      this.mPorts = ports;
    }

    public void acquire() {
      synchronized (this.mToken) {
        try {
          try {
            PowerManager.this.mService.acquireLowPowerStandbyPorts(
                this.mToken, LowPowerStandbyPortDescription.toParcelable(this.mPorts));
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

  public void setAutoBrightnessLimit(int lowerLimit, int upperLimit) {
    try {
      this.mService.setAutoBrightnessLimit(lowerLimit, upperLimit, false);
    } catch (RemoteException e) {
    }
  }

  public void setAutoBrightnessLimit(int lowerLimit, int upperLimit, boolean slowChange) {
    try {
      this.mService.setAutoBrightnessLimit(lowerLimit, upperLimit, slowChange);
    } catch (RemoteException e) {
    }
  }

  public void semSetAutoBrightnessLimit(int lowerLimit, int upperLimit) {
    try {
      this.mService.setAutoBrightnessLimit(lowerLimit, upperLimit, false);
    } catch (RemoteException e) {
    }
  }

  public void semSetAutoBrightnessLimit(float lowerLimit, float upperLimit) {
    int lowerLimitInt;
    try {
      if (Float.compare(lowerLimit, 0.0f) >= 0) {
        lowerLimitInt = BrightnessSynchronizer.brightnessFloatToInt(lowerLimit);
      } else {
        lowerLimitInt = -1;
      }
      int upperLimitInt =
          Float.compare(upperLimit, 0.0f) >= 0
              ? BrightnessSynchronizer.brightnessFloatToInt(upperLimit)
              : -1;
      this.mService.setAutoBrightnessLimit(lowerLimitInt, upperLimitInt, false);
    } catch (RemoteException e) {
    }
  }

  public void setMasterBrightnessLimit(int lowerLimit, int upperLimit, int brightnessLimitPeriod) {
    try {
      this.mService.setMasterBrightnessLimit(lowerLimit, upperLimit, brightnessLimitPeriod);
    } catch (RemoteException e) {
    }
  }

  public float getCurrentBrightness(boolean ratio) {
    try {
      return this.mService.getCurrentBrightness(ratio);
    } catch (RemoteException e) {
      return -1.0f;
    }
  }

  @Deprecated
  public void setScreenCurtainEnabled(IBinder token, boolean enable) {
    setScreenCurtainEnabled(token, enable, enable ? 3 : 0);
  }

  public void setScreenCurtainEnabled(IBinder token, boolean enable, int displayState) {
    try {
      this.mService.setScreenCurtainEnabled(token, enable, displayState);
    } catch (RemoteException e) {
    }
  }

  public boolean isScreenCurtainEnabled() {
    try {
      return this.mService.isScreenCurtainEnabled();
    } catch (RemoteException e) {
      return false;
    }
  }

  public String getPackageNameOnScreenCurtain() {
    try {
      return this.mService.getPackageNameOnScreenCurtain();
    } catch (RemoteException e) {
      return null;
    }
  }

  boolean isScreenCurtainEntryAvailable() {
    try {
      return this.mService.isScreenCurtainEntryAvailable();
    } catch (RemoteException e) {
      return false;
    }
  }

  public void setScreenBrightnessScaleFactor(float scaleFactor, IBinder binder) {
    try {
      this.mService.setScreenBrightnessScaleFactor(scaleFactor, binder);
    } catch (RemoteException e) {
    }
  }

  public void setFreezingScreenBrightness(boolean freezing) {
    try {
      this.mService.setFreezingScreenBrightness(freezing);
    } catch (RemoteException e) {
    }
  }

  public void updateCoverState(boolean closed) {
    try {
      this.mService.updateCoverState(closed);
    } catch (RemoteException e) {
    }
  }

  public void setCoverType(int coverType) {
    try {
      this.mService.setCoverType(coverType);
    } catch (RemoteException e) {
    }
  }

  public void switchForceLcdBacklightOffState() {
    try {
      this.mService.switchForceLcdBacklightOffState();
    } catch (RemoteException e) {
    }
  }

  public void setLCDFlashMode(boolean enable, IBinder binder) {
    try {
      this.mService.setLCDFlashMode(enable, binder);
    } catch (RemoteException e) {
    }
  }

  public void semSetLcdFlashModeEnabled(boolean enable, IBinder binder) {
    try {
      this.mService.setLCDFlashMode(enable, binder);
    } catch (RemoteException e) {
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
    private final Runnable mReleaser =
        new Runnable() { // from class: android.os.PowerManager$WakeLock$$ExternalSyntheticLambda0
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

    WakeLock(int flags, String tag, String packageName, int displayId) {
      this.mFlags = flags;
      this.mTag = tag;
      this.mTagHash = tag.hashCode();
      this.mPackageName = packageName;
      this.mDisplayId = displayId;
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

    public void setReferenceCounted(boolean value) {
      synchronized (this.mToken) {
        this.mRefCounted = value;
      }
    }

    public void setProximityDebounceTime(int positive, int negative) {
      synchronized (this.mToken) {
        boolean changed = false;
        if ((positive < -1 && positive > 3000) || (negative < -1 && negative > 3000)) {
          throw new IllegalArgumentException(
              "setProximityDebounceTime: positive = " + positive + ", negative = " + negative);
        }
        if (this.mProximityPositiveDebounce != positive) {
          this.mProximityPositiveDebounce = positive;
          changed = true;
        }
        if (this.mProximityNegativeDebounce != negative) {
          this.mProximityNegativeDebounce = negative;
          changed = true;
        }
        if (changed) {
          try {
            PowerManager.this.mService.setProximityDebounceTime(this.mToken, positive, negative);
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

    public void acquire(long timeout) {
      synchronized (this.mToken) {
        acquireLocked();
        PowerManager.this.mHandler.postDelayed(this.mReleaser, timeout);
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
          PowerManager.this.mService.acquireWakeLock(
              this.mToken,
              this.mFlags,
              this.mTag,
              this.mPackageName,
              this.mWorkSource,
              this.mHistoryTag,
              this.mDisplayId,
              this.mCallback);
          this.mHeld = true;
        } catch (RemoteException e) {
          throw e.rethrowFromSystemServer();
        }
      }
    }

    public void release() {
      release(0);
    }

    public void release(int flags) {
      synchronized (this.mToken) {
        int i = this.mInternalCount;
        if (i > 0) {
          this.mInternalCount = i - 1;
        }
        if ((65536 & flags) == 0) {
          this.mExternalCount--;
        }
        if (!this.mRefCounted || this.mInternalCount == 0) {
          PowerManager.this.mHandler.removeCallbacks(this.mReleaser);
          if (this.mHeld) {
            Trace.asyncTraceForTrackEnd(131072L, "WakeLocks", this.mTagHash);
            try {
              PowerManager.this.mService.releaseWakeLock(this.mToken, flags);
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

    public void setWorkSource(WorkSource ws) {
      synchronized (this.mToken) {
        if (ws != null) {
          try {
            if (ws.isEmpty()) {
              ws = null;
            }
          } catch (Throwable th) {
            throw th;
          }
        }
        boolean changed = true;
        if (ws == null) {
          if (this.mWorkSource == null) {
            changed = false;
          }
          this.mWorkSource = null;
        } else {
          WorkSource workSource = this.mWorkSource;
          if (workSource != null) {
            changed = true ^ workSource.equals(ws);
            if (changed) {
              this.mWorkSource.set(ws);
            }
          } else {
            changed = true;
            this.mWorkSource = new WorkSource(ws);
          }
        }
        if (changed && this.mHeld) {
          try {
            PowerManager.this.mService.updateWakeLockWorkSource(
                this.mToken, this.mWorkSource, this.mHistoryTag);
          } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
          }
        }
      }
    }

    public void setTag(String tag) {
      this.mTag = tag;
      this.mTagHash = tag.hashCode();
    }

    public String getTag() {
      return this.mTag;
    }

    public void setHistoryTag(String tag) {
      this.mHistoryTag = tag;
    }

    public void setUnimportantForLogging(boolean state) {
      if (!state) {
        this.mFlags &= -1073741825;
      } else {
        this.mFlags |= 1073741824;
      }
    }

    public String toString() {
      String str;
      synchronized (this.mToken) {
        str =
            "WakeLock{"
                + Integer.toHexString(System.identityHashCode(this))
                + " held="
                + this.mHeld
                + ", refCount="
                + this.mInternalCount
                + "}";
      }
      return str;
    }

    public void dumpDebug(ProtoOutputStream proto, long fieldId) {
      synchronized (this.mToken) {
        long token = proto.start(fieldId);
        proto.write(1138166333441L, this.mTag);
        proto.write(1138166333442L, this.mPackageName);
        proto.write(1133871366147L, this.mHeld);
        proto.write(1120986464260L, this.mInternalCount);
        WorkSource workSource = this.mWorkSource;
        if (workSource != null) {
          workSource.dumpDebug(proto, 1146756268037L);
        }
        proto.end(token);
      }
    }

    public Runnable wrap(final Runnable r) {
      acquire();
      return new Runnable() { // from class:
                              // android.os.PowerManager$WakeLock$$ExternalSyntheticLambda1
        @Override // java.lang.Runnable
        public final void run() {
          PowerManager.WakeLock.this.lambda$wrap$1(r);
        }
      };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$wrap$1(Runnable r) {
      try {
        r.run();
      } finally {
        release();
      }
    }

    public void setStateListener(Executor executor, WakeLockStateListener listener) {
      Preconditions.checkNotNull(executor, "executor cannot be null");
      synchronized (this.mToken) {
        if (listener != this.mListener) {
          this.mListener = listener;
          if (listener != null) {
            this.mCallback = new IWakeLockCallbackStubC23541(executor, listener);
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

    /* renamed from: android.os.PowerManager$WakeLock$1 */
    class IWakeLockCallbackStubC23541 extends IWakeLockCallback.Stub {
      final /* synthetic */ Executor val$executor;
      final /* synthetic */ WakeLockStateListener val$listener;

      IWakeLockCallbackStubC23541(Executor executor, WakeLockStateListener wakeLockStateListener) {
        this.val$executor = executor;
        this.val$listener = wakeLockStateListener;
      }

      @Override // android.p009os.IWakeLockCallback
      public void onStateChanged(final boolean enabled) {
        long token = Binder.clearCallingIdentity();
        try {
          Executor executor = this.val$executor;
          final WakeLockStateListener wakeLockStateListener = this.val$listener;
          executor.execute(
              new Runnable() { // from class:
                               // android.os.PowerManager$WakeLock$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                  PowerManager.WakeLockStateListener.this.onStateChanged(enabled);
                }
              });
        } finally {
          Binder.restoreCallingIdentity(token);
        }
      }
    }
  }

  public static void invalidatePowerSaveModeCaches() {
    PropertyInvalidatedCache.invalidateCache(CACHE_KEY_IS_POWER_SAVE_MODE_PROPERTY);
  }

  public static void invalidateIsInteractiveCaches() {
    PropertyInvalidatedCache.invalidateCache(CACHE_KEY_IS_INTERACTIVE_PROPERTY);
  }

  public void setEarlyWakeUp(boolean enable) {
    try {
      this.mService.setEarlyWakeUp(enable);
    } catch (RemoteException e) {
    }
  }

  public long getLastUserActivityTime(int userId) {
    try {
      return this.mService.getLastUserActivityTime(userId);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  public String[] getWakeLockPackageList() {
    try {
      return this.mService.getWakeLockPackageList();
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }
}
