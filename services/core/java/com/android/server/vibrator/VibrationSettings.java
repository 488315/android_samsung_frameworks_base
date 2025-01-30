package com.android.server.vibrator;

import android.R;
import android.app.ActivityManager;
import android.app.UidObserver;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.p000pm.PackageManagerInternal;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Handler;
import android.os.PowerManager;
import android.os.PowerManagerInternal;
import android.os.PowerSaveState;
import android.os.RemoteException;
import android.os.VibrationAttributes;
import android.os.VibrationEffect;
import android.os.vibrator.VibrationConfig;
import android.provider.Settings;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.proto.ProtoOutputStream;
import com.android.server.LocalServices;
import com.android.server.companion.virtual.VirtualDeviceManagerInternal;
import com.samsung.android.server.vibrator.VibratorHelper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* loaded from: classes3.dex */
public final class VibrationSettings {
  public AudioManager mAudioManager;
  public boolean mBatterySaverMode;
  public final Context mContext;
  public SparseIntArray mCurrentVibrationIntensities;
  public final VibrationCustomSettings mCustomSettings;
  public final SparseArray mFallbackEffects;
  public final List mListeners;
  public final Object mLock;
  public PowerManagerInternal mPowerManagerInternal;
  public int mRingerMode;
  final SettingsBroadcastReceiver mSettingChangeReceiver;
  final SettingsContentObserver mSettingObserver;
  public final String mSystemUiPackage;
  final MyUidObserver mUidObserver;
  public boolean mVibrateInputDevices;
  public boolean mVibrateOn;
  public final VibrationConfig mVibrationConfig;
  public final VirtualDeviceListener mVirtualDeviceListener;
  public static final Set BACKGROUND_PROCESS_USAGE_ALLOWLIST =
      new HashSet(Arrays.asList(33, 17, 49, 65, 50, 34));
  public static final Set BATTERY_SAVER_USAGE_ALLOWLIST =
      new HashSet(Arrays.asList(33, 17, 65, 34, 50));
  public static final Set SYSTEM_VIBRATION_SCREEN_OFF_USAGE_ALLOWLIST =
      new HashSet(Arrays.asList(18, 66, 34, 50));
  public static final Set POWER_MANAGER_SLEEP_REASON_ALLOWLIST = new HashSet(Arrays.asList(9, 2));
  public static final IntentFilter USER_SWITCHED_INTENT_FILTER =
      new IntentFilter("android.intent.action.USER_SWITCHED");
  public static final IntentFilter INTERNAL_RINGER_MODE_CHANGED_INTENT_FILTER =
      new IntentFilter("android.media.INTERNAL_RINGER_MODE_CHANGED_ACTION");

  public interface OnVibratorSettingsChanged {
    void onChange();
  }

  public final int toIntensity(int i, int i2) {
    return (i < 0 || i > 3) ? i2 : i;
  }

  public VibrationSettings(
      Context context, Handler handler, VibratorManagerService vibratorManagerService) {
    this(context, handler, new VibrationConfig(context.getResources()), vibratorManagerService);
  }

  public VibrationSettings(
      Context context,
      Handler handler,
      VibrationConfig vibrationConfig,
      VibratorManagerService vibratorManagerService) {
    this.mLock = new Object();
    this.mListeners = new ArrayList();
    this.mCurrentVibrationIntensities = new SparseIntArray();
    this.mContext = context;
    this.mVibrationConfig = vibrationConfig;
    SettingsContentObserver settingsContentObserver = new SettingsContentObserver(handler);
    this.mSettingObserver = settingsContentObserver;
    this.mUidObserver = new MyUidObserver();
    this.mSettingChangeReceiver = new SettingsBroadcastReceiver();
    this.mVirtualDeviceListener = new VirtualDeviceListener();
    this.mSystemUiPackage =
        ((PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class))
            .getSystemUiServiceComponent()
            .getPackageName();
    VibrationEffect createEffectFromResource = createEffectFromResource(17236412);
    VibrationEffect createEffectFromResource2 =
        createEffectFromResource(R.array.device_state_notification_power_save_contents);
    VibrationEffect createEffectFromResource3 =
        createEffectFromResource(R.array.vendor_required_apps_managed_device);
    VibrationEffect createEffectFromResource4 =
        createEffectFromResource(R.array.config_safeModeEnabledVibePattern);
    SparseArray sparseArray = new SparseArray();
    this.mFallbackEffects = sparseArray;
    sparseArray.put(0, createEffectFromResource);
    sparseArray.put(1, createEffectFromResource2);
    sparseArray.put(2, createEffectFromResource4);
    sparseArray.put(5, createEffectFromResource3);
    sparseArray.put(21, VibrationEffect.get(2, false));
    this.mCustomSettings =
        new VibrationCustomSettings(context, settingsContentObserver, vibratorManagerService);
    update();
  }

  public void onSystemReady() {
    PowerManagerInternal powerManagerInternal =
        (PowerManagerInternal) LocalServices.getService(PowerManagerInternal.class);
    AudioManager audioManager = (AudioManager) this.mContext.getSystemService(AudioManager.class);
    int ringerModeInternal = audioManager.getRingerModeInternal();
    synchronized (this.mLock) {
      this.mPowerManagerInternal = powerManagerInternal;
      this.mAudioManager = audioManager;
      this.mRingerMode = ringerModeInternal;
    }
    try {
      ActivityManager.getService().registerUidObserver(this.mUidObserver, 3, -1, (String) null);
    } catch (RemoteException unused) {
    }
    powerManagerInternal.registerLowPowerModeObserver(
        new PowerManagerInternal
            .LowPowerModeListener() { // from class: com.android.server.vibrator.VibrationSettings.1
          public int getServiceType() {
            return 2;
          }

          public void onLowPowerModeChanged(PowerSaveState powerSaveState) {
            boolean z;
            synchronized (VibrationSettings.this.mLock) {
              z = powerSaveState.batterySaverEnabled != VibrationSettings.this.mBatterySaverMode;
              VibrationSettings.this.mBatterySaverMode = powerSaveState.batterySaverEnabled;
              VibrationSettings.this.mBatterySaverMode = false;
            }
            if (z) {
              VibrationSettings.this.notifyListeners();
            }
          }
        });
    VirtualDeviceManagerInternal virtualDeviceManagerInternal =
        (VirtualDeviceManagerInternal) LocalServices.getService(VirtualDeviceManagerInternal.class);
    if (virtualDeviceManagerInternal != null) {
      virtualDeviceManagerInternal.registerVirtualDisplayListener(this.mVirtualDeviceListener);
      virtualDeviceManagerInternal.registerAppsOnVirtualDeviceListener(this.mVirtualDeviceListener);
    }
    registerSettingsChangeReceiver(USER_SWITCHED_INTENT_FILTER);
    registerSettingsChangeReceiver(INTERNAL_RINGER_MODE_CHANGED_INTENT_FILTER);
    registerSettingsObserver(Settings.System.getUriFor("vibrate_input_devices"));
    registerSettingsObserver(Settings.System.getUriFor("vibrate_on"));
    registerSettingsObserver(Settings.System.getUriFor("haptic_feedback_enabled"));
    registerSettingsObserver(Settings.System.getUriFor("alarm_vibration_intensity"));
    registerSettingsObserver(Settings.System.getUriFor("haptic_feedback_intensity"));
    registerSettingsObserver(Settings.System.getUriFor("hardware_haptic_feedback_intensity"));
    registerSettingsObserver(Settings.System.getUriFor("media_vibration_intensity"));
    registerSettingsObserver(Settings.System.getUriFor("notification_vibration_intensity"));
    registerSettingsObserver(Settings.System.getUriFor("ring_vibration_intensity"));
    this.mCustomSettings.onSystemReady();
    this.mCustomSettings.registerCustomSettingsObserver();
    this.mCustomSettings.updateSupportedHalFeature();
    this.mCustomSettings.setDefaultRingtoneVibrationSepIndex();
    update();
  }

  public void addListener(OnVibratorSettingsChanged onVibratorSettingsChanged) {
    synchronized (this.mLock) {
      if (!this.mListeners.contains(onVibratorSettingsChanged)) {
        this.mListeners.add(onVibratorSettingsChanged);
      }
    }
  }

  public int getRampStepDuration() {
    return this.mVibrationConfig.getRampStepDurationMs();
  }

  public int getRampDownDuration() {
    return this.mVibrationConfig.getRampDownDurationMs();
  }

  public int getDefaultIntensity(int i) {
    return this.mVibrationConfig.getDefaultVibrationIntensity(i);
  }

  public int getCurrentIntensity(int i) {
    int i2;
    int defaultIntensity = getDefaultIntensity(i);
    synchronized (this.mLock) {
      i2 = this.mCurrentVibrationIntensities.get(i, defaultIntensity);
    }
    return i2;
  }

  public VibrationEffect getFallbackEffect(int i) {
    return (VibrationEffect) this.mFallbackEffects.get(i);
  }

  public boolean shouldVibrateInputDevices() {
    return this.mVibrateInputDevices;
  }

  public Vibration.Status shouldIgnoreVibration(Vibration.CallerInfo callerInfo) {
    int usage = callerInfo.attrs.getUsage();
    synchronized (this.mLock) {
      if (!this.mUidObserver.isUidForeground(callerInfo.uid)
          && !BACKGROUND_PROCESS_USAGE_ALLOWLIST.contains(Integer.valueOf(usage))
          && !callerInfo.attrs.hasTag("ALLOWED_IN_BACKGROUND_PROCESS")) {
        return Vibration.Status.IGNORED_BACKGROUND;
      }
      if (this.mVirtualDeviceListener.isAppOrDisplayOnAnyVirtualDevice(
          callerInfo.uid, callerInfo.displayId)) {
        return Vibration.Status.IGNORED_FROM_VIRTUAL_DEVICE;
      }
      if (this.mBatterySaverMode
          && !BATTERY_SAVER_USAGE_ALLOWLIST.contains(Integer.valueOf(usage))) {
        return Vibration.Status.IGNORED_FOR_POWER;
      }
      if (!callerInfo.attrs.isFlagSet(2)
          && getCurrentIntensity(usage) == 0
          && !callerInfo.attrs.hasTag("INTENSITY_MAX")
          && !callerInfo.attrs.hasTag("INTENSITY_MIN")
          && !callerInfo.attrs.hasTag("INDIVIDUAL_INTENSITY")) {
        return Vibration.Status.IGNORED_FOR_SETTINGS;
      }
      if (callerInfo.attrs.isFlagSet(1) || shouldVibrateForRingerModeLocked(usage)) {
        return null;
      }
      if (callerInfo.attrs.hasTag("VIBRATE_CALL")) {
        return null;
      }
      return Vibration.Status.IGNORED_FOR_RINGER_MODE;
    }
  }

  public boolean shouldCancelVibrationOnScreenOff(Vibration.CallerInfo callerInfo, long j) {
    PowerManagerInternal powerManagerInternal;
    synchronized (this.mLock) {
      powerManagerInternal = this.mPowerManagerInternal;
    }
    if (powerManagerInternal != null) {
      PowerManager.SleepData lastGoToSleep = powerManagerInternal.getLastGoToSleep();
      if (lastGoToSleep.goToSleepUptimeMillis < j
          || POWER_MANAGER_SLEEP_REASON_ALLOWLIST.contains(
              Integer.valueOf(lastGoToSleep.goToSleepReason))) {
        Slog.d(
            "VibrationSettings",
            "Ignoring screen off event triggered at uptime "
                + lastGoToSleep.goToSleepUptimeMillis
                + " for reason "
                + PowerManager.sleepReasonToString(lastGoToSleep.goToSleepReason));
        return false;
      }
    }
    if (!SYSTEM_VIBRATION_SCREEN_OFF_USAGE_ALLOWLIST.contains(
        Integer.valueOf(callerInfo.attrs.getUsage()))) {
      return true;
    }
    int i = callerInfo.uid;
    return (i == 1000 || i == 0 || this.mSystemUiPackage.equals(callerInfo.opPkg)) ? false : true;
  }

  public final boolean shouldVibrateForRingerModeLocked(int i) {
    return ((i == 33 || i == 49) && this.mRingerMode == 0) ? false : true;
  }

  public void update() {
    updateSettings();
    updateRingerMode();
    notifyListeners();
  }

  public final void updateSettings() {
    synchronized (this.mLock) {
      boolean z = true;
      this.mVibrateInputDevices = loadSystemSetting("vibrate_input_devices", 0) > 0;
      if (loadSystemSetting("vibrate_on", 1) <= 0) {
        z = false;
      }
      this.mVibrateOn = z;
      int intensity =
          toIntensity(loadSystemSetting("alarm_vibration_intensity", -1), getDefaultIntensity(17));
      int defaultIntensity = getDefaultIntensity(18);
      int intensity2 =
          toIntensity(
              loadSystemSetting(VibratorHelper.getHapticFeedbackSettingName(), -1),
              defaultIntensity);
      int positiveIntensity = toPositiveIntensity(intensity2, defaultIntensity);
      int intensity3 =
          toIntensity(
              loadSystemSetting("hardware_haptic_feedback_intensity", -1), positiveIntensity);
      int intensity4 =
          toIntensity(loadSystemSetting("media_vibration_intensity", -1), getDefaultIntensity(19));
      int defaultIntensity2 = getDefaultIntensity(49);
      int intensity5 =
          toIntensity(
              loadSystemSetting(VibratorHelper.getNotificationVibrationSettingName(), -1),
              defaultIntensity2);
      int positiveIntensity2 = toPositiveIntensity(intensity5, defaultIntensity2);
      int intensity6 =
          toIntensity(
              loadSystemSetting(VibratorHelper.getRingVibrationSettingName(), -1),
              getDefaultIntensity(33));
      this.mCurrentVibrationIntensities.clear();
      this.mCurrentVibrationIntensities.put(17, intensity);
      this.mCurrentVibrationIntensities.put(49, intensity5);
      this.mCurrentVibrationIntensities.put(19, intensity4);
      this.mCurrentVibrationIntensities.put(0, intensity4);
      this.mCurrentVibrationIntensities.put(33, intensity6);
      this.mCurrentVibrationIntensities.put(65, positiveIntensity2);
      this.mCurrentVibrationIntensities.put(50, intensity3);
      this.mCurrentVibrationIntensities.put(34, intensity3);
      this.mCurrentVibrationIntensities.put(18, intensity2);
      this.mCurrentVibrationIntensities.put(66, positiveIntensity);
      updateAmplitudeLevel();
      updateCustomSettings();
    }
  }

  public final void updateRingerMode() {
    synchronized (this.mLock) {
      AudioManager audioManager = this.mAudioManager;
      this.mRingerMode = audioManager == null ? 0 : audioManager.getRingerModeInternal();
    }
  }

  public String toString() {
    String str;
    synchronized (this.mLock) {
      StringBuilder sb = new StringBuilder("{");
      for (int i = 0; i < this.mCurrentVibrationIntensities.size(); i++) {
        int keyAt = this.mCurrentVibrationIntensities.keyAt(i);
        int valueAt = this.mCurrentVibrationIntensities.valueAt(i);
        sb.append(VibrationAttributes.usageToString(keyAt));
        sb.append("=(");
        sb.append(intensityToString(valueAt));
        sb.append(",default:");
        sb.append(intensityToString(getDefaultIntensity(keyAt)));
        sb.append("), ");
      }
      sb.append('}');
      str =
          "VibrationSettings{mVibratorConfig="
              + this.mVibrationConfig
              + ", mVibrateInputDevices="
              + this.mVibrateInputDevices
              + ", mBatterySaverMode="
              + this.mBatterySaverMode
              + ", mVibrateOn="
              + this.mVibrateOn
              + ", mVibrationIntensities="
              + ((Object) sb)
              + ", mProcStatesCache="
              + this.mUidObserver.mProcStatesCache
              + '}';
    }
    return str;
  }

  public void dumpProto(ProtoOutputStream protoOutputStream) {
    synchronized (this.mLock) {
      protoOutputStream.write(1133871366168L, this.mVibrateOn);
      protoOutputStream.write(1133871366150L, this.mBatterySaverMode);
      protoOutputStream.write(1120986464274L, getCurrentIntensity(17));
      protoOutputStream.write(1120986464275L, getDefaultIntensity(17));
      protoOutputStream.write(1120986464278L, getCurrentIntensity(50));
      protoOutputStream.write(1120986464279L, getDefaultIntensity(50));
      protoOutputStream.write(1120986464263L, getCurrentIntensity(18));
      protoOutputStream.write(1120986464264L, getDefaultIntensity(18));
      protoOutputStream.write(1120986464276L, getCurrentIntensity(19));
      protoOutputStream.write(1120986464277L, getDefaultIntensity(19));
      protoOutputStream.write(1120986464265L, getCurrentIntensity(49));
      protoOutputStream.write(1120986464266L, getDefaultIntensity(49));
      protoOutputStream.write(1120986464267L, getCurrentIntensity(33));
      protoOutputStream.write(1120986464268L, getDefaultIntensity(33));
    }
  }

  public final void notifyListeners() {
    ArrayList arrayList;
    synchronized (this.mLock) {
      arrayList = new ArrayList(this.mListeners);
    }
    Iterator it = arrayList.iterator();
    while (it.hasNext()) {
      ((OnVibratorSettingsChanged) it.next()).onChange();
    }
  }

  public static String intensityToString(int i) {
    if (i == 0) {
      return "OFF";
    }
    if (i == 1) {
      return "LOW";
    }
    if (i == 2) {
      return "MEDIUM";
    }
    if (i == 3) {
      return "HIGH";
    }
    return "UNKNOWN INTENSITY " + i;
  }

  public final int toPositiveIntensity(int i, int i2) {
    return i == 0 ? i2 : toIntensity(i, i2);
  }

  public final int loadSystemSetting(String str, int i) {
    return Settings.System.getIntForUser(this.mContext.getContentResolver(), str, i, -2);
  }

  public final void registerSettingsObserver(Uri uri) {
    this.mContext
        .getContentResolver()
        .registerContentObserver(uri, true, this.mSettingObserver, -1);
  }

  public final void registerSettingsChangeReceiver(IntentFilter intentFilter) {
    this.mContext.registerReceiver(this.mSettingChangeReceiver, intentFilter, 2);
  }

  public final VibrationEffect createEffectFromResource(int i) {
    return createEffectFromTimings(getLongIntArray(this.mContext.getResources(), i));
  }

  public static VibrationEffect createEffectFromTimings(long[] jArr) {
    if (jArr == null || jArr.length == 0) {
      return null;
    }
    if (jArr.length == 1) {
      return VibrationEffect.createOneShot(jArr[0], -1);
    }
    return VibrationEffect.createWaveform(jArr, -1);
  }

  public static long[] getLongIntArray(Resources resources, int i) {
    int[] intArray = resources.getIntArray(i);
    if (intArray == null) {
      return null;
    }
    long[] jArr = new long[intArray.length];
    for (int i2 = 0; i2 < intArray.length; i2++) {
      jArr[i2] = intArray[i2];
    }
    return jArr;
  }

  final class SettingsContentObserver extends ContentObserver {
    public SettingsContentObserver(Handler handler) {
      super(handler);
    }

    @Override // android.database.ContentObserver
    public void onChange(boolean z) {
      VibrationSettings.this.updateSettings();
      VibrationSettings.this.notifyListeners();
    }
  }

  final class SettingsBroadcastReceiver extends BroadcastReceiver {
    public SettingsBroadcastReceiver() {}

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
      String action = intent.getAction();
      if ("android.intent.action.USER_SWITCHED".equals(action)) {
        VibrationSettings.this.update();
      } else if ("android.media.INTERNAL_RINGER_MODE_CHANGED_ACTION".equals(action)) {
        VibrationSettings.this.updateRingerMode();
        VibrationSettings.this.notifyListeners();
      }
    }
  }

  final class MyUidObserver extends UidObserver {
    public final SparseArray mProcStatesCache = new SparseArray();

    public MyUidObserver() {}

    public boolean isUidForeground(int i) {
      return ((Integer) this.mProcStatesCache.get(i, 6)).intValue() <= 6;
    }

    public void onUidGone(int i, boolean z) {
      this.mProcStatesCache.delete(i);
    }

    public void onUidStateChanged(int i, int i2, long j, int i3) {
      this.mProcStatesCache.put(i, Integer.valueOf(i2));
    }
  }

  public final class VirtualDeviceListener
      implements VirtualDeviceManagerInternal.VirtualDisplayListener,
          VirtualDeviceManagerInternal.AppsOnVirtualDeviceListener {
    public final Set mVirtualDisplays = new HashSet();
    public final Set mAppsOnVirtualDevice = new HashSet();

    public VirtualDeviceListener() {}

    @Override // com.android.server.companion.virtual.VirtualDeviceManagerInternal.VirtualDisplayListener
    public void onVirtualDisplayCreated(int i) {
      synchronized (VibrationSettings.this.mLock) {
        this.mVirtualDisplays.add(Integer.valueOf(i));
      }
    }

    @Override // com.android.server.companion.virtual.VirtualDeviceManagerInternal.VirtualDisplayListener
    public void onVirtualDisplayRemoved(int i) {
      synchronized (VibrationSettings.this.mLock) {
        this.mVirtualDisplays.remove(Integer.valueOf(i));
      }
    }

    @Override // com.android.server.companion.virtual.VirtualDeviceManagerInternal.AppsOnVirtualDeviceListener
    public void onAppsOnAnyVirtualDeviceChanged(Set set) {
      synchronized (VibrationSettings.this.mLock) {
        this.mAppsOnVirtualDevice.clear();
        this.mAppsOnVirtualDevice.addAll(set);
      }
    }

    public boolean isAppOrDisplayOnAnyVirtualDevice(int i, int i2) {
      if (i2 == 0) {
        return false;
      }
      synchronized (VibrationSettings.this.mLock) {
        if (i2 == -1) {
          return this.mAppsOnVirtualDevice.contains(Integer.valueOf(i));
        }
        return this.mVirtualDisplays.contains(Integer.valueOf(i2));
      }
    }
  }

  public void bootCompleteReady() {
    this.mCustomSettings.bootCompleteReady();
  }

  public int getCurrentMagnitude(int i) {
    return this.mCustomSettings.getCurrentMagnitude(i);
  }

  public int getMaxMagnitude() {
    return this.mCustomSettings.getMaxMagnitude();
  }

  public int getMinMagnitude() {
    return this.mCustomSettings.getMinMagnitude();
  }

  public void updateAmplitudeLevel() {
    this.mCustomSettings.updateAmplitudeLevel();
  }

  public void updateCustomSettings() {
    this.mCustomSettings.updateCustomSettings();
  }

  public String addCustomDump() {
    return this.mCustomSettings.addCustomDump();
  }
}
