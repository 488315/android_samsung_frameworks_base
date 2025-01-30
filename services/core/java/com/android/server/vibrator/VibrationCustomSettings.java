package com.android.server.vibrator;

import android.R;
import android.content.Context;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.util.Slog;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.samsung.android.server.vibrator.VibratorHelper;
import com.samsung.android.vibrator.VibRune;
import com.samsung.android.view.SemWindowManager;
import java.util.Arrays;

/* loaded from: classes3.dex */
public class VibrationCustomSettings {
  public AudioManager mAudioManager;
  public final Context mContext;
  public final VibratorManagerService mService;
  public final VibrationSettings.SettingsContentObserver mSettingObserver;
  public final Object mLock = new Object();
  public boolean mIsEnableIntensity = false;
  public boolean mIsHapticSupported = false;
  public boolean mIsHapticEngineSupported = false;
  public int[] LEVEL_TO_MAGNITUDE = new int[10];
  public int[] LEVEL_TO_TOUCH_MAGNITUDE = new int[10];
  public int[] LEVEL_TO_FORCE_MAGNITUDE = new int[10];
  public int mTouchMagnitude = 9999;
  public int mCallMagnitude = 9999;
  public int mNotiMagnitude = 9999;
  public int mForceMagnitude = 9999;
  public int mMaxMagnitude = 9999;
  public int mMinMagnitude = 9999;
  public int mMediaMagnitude = 9999;
  public int mMotorType = 0;
  public boolean mOnlyWatchConnected = false;
  public final VibratorHelper mVibratorHelper = VibratorHelper.getInstance();

  public static int[] loadMagnitudeList(boolean z, int[] iArr, int[] iArr2) {
    return z ? iArr : iArr2;
  }

  public static int[] loadTouchMagnitudeList(boolean z, int[] iArr, int[] iArr2) {
    return z ? iArr : iArr2;
  }

  public final VibratorController getDefaultVibratorController() {
    return this.mService.getDefaultVibratorController();
  }

  public final int loadSystemSetting(String str, int i) {
    return Settings.System.getIntForUser(this.mContext.getContentResolver(), str, i, -2);
  }

  public final void registerSettingsObserver(Uri uri) {
    this.mContext
        .getContentResolver()
        .registerContentObserver(uri, true, this.mSettingObserver, -1);
  }

  public VibrationCustomSettings(
      Context context,
      VibrationSettings.SettingsContentObserver settingsContentObserver,
      VibratorManagerService vibratorManagerService) {
    this.mContext = context;
    this.mSettingObserver = settingsContentObserver;
    this.mService = vibratorManagerService;
  }

  public void onSystemReady() {
    AudioManager audioManager = (AudioManager) this.mContext.getSystemService(AudioManager.class);
    synchronized (this.mLock) {
      this.mAudioManager = audioManager;
    }
  }

  public void bootCompleteReady() {
    registerFoldStateListener();
  }

  public void updateCustomSettings() {
    int length = this.LEVEL_TO_MAGNITUDE.length - 1;
    int length2 = this.LEVEL_TO_TOUCH_MAGNITUDE.length - 1;
    int length3 = this.LEVEL_TO_FORCE_MAGNITUDE.length - 1;
    if (length < 2 || length2 < 2) {
      Slog.d(
          "VibratorManagerService",
          "magnitudeMaxLevel : " + length + ", touchMagnitudeMaxLevel : " + length2);
      return;
    }
    int loadSystemSetting = loadSystemSetting("VIB_FEEDBACK_MAGNITUDE", length2);
    if (loadSystemSetting <= length2) {
      loadSystemSetting = this.LEVEL_TO_TOUCH_MAGNITUDE[loadSystemSetting];
    }
    this.mTouchMagnitude = loadSystemSetting;
    int loadSystemSetting2 = loadSystemSetting("VIB_RECVCALL_MAGNITUDE", length);
    if (loadSystemSetting2 <= length) {
      loadSystemSetting2 = this.LEVEL_TO_MAGNITUDE[loadSystemSetting2];
    }
    this.mCallMagnitude = loadSystemSetting2;
    int loadSystemSetting3 = loadSystemSetting("SEM_VIBRATION_NOTIFICATION_INTENSITY", length);
    if (loadSystemSetting3 <= length) {
      loadSystemSetting3 = this.LEVEL_TO_MAGNITUDE[loadSystemSetting3];
    }
    this.mNotiMagnitude = loadSystemSetting3;
    if (length3 < 2) {
      Log.e("VibratorManagerService", "forceTouchMagnitudeMaxLevel : " + length3);
      return;
    }
    int loadSystemSetting4 = loadSystemSetting("SEM_VIBRATION_FORCE_TOUCH_INTENSITY", length3);
    if (loadSystemSetting4 <= length3) {
      loadSystemSetting4 = this.LEVEL_TO_FORCE_MAGNITUDE[loadSystemSetting4];
    }
    this.mForceMagnitude = loadSystemSetting4;
    int loadSystemSetting5 = loadSystemSetting("media_vibration_intensity", length);
    if (loadSystemSetting5 <= length) {
      loadSystemSetting5 = this.LEVEL_TO_MAGNITUDE[loadSystemSetting5];
    }
    this.mMediaMagnitude = loadSystemSetting5;
  }

  public void registerCustomSettingsObserver() {
    registerSettingsObserver(Settings.System.getUriFor("VIB_FEEDBACK_MAGNITUDE"));
    registerSettingsObserver(Settings.System.getUriFor("hardware_haptic_feedback_intensity"));
    registerSettingsObserver(Settings.System.getUriFor("SEM_VIBRATION_NOTIFICATION_INTENSITY"));
    registerSettingsObserver(Settings.System.getUriFor("VIB_RECVCALL_MAGNITUDE"));
  }

  public void updateSupportedHalFeature() {
    this.mMotorType = this.mService.getSupportedMotorType();
    initializeVibrationState();
    if (getDefaultVibratorController() != null) {
      this.mIsHapticEngineSupported = getDefaultVibratorController().supportsHapticEngine();
      Log.e(
          "VibratorManagerService", "mIsHapticEngineSupported : " + this.mIsHapticEngineSupported);
    }
  }

  public void setDefaultRingtoneVibrationSepIndex() {
    if (Settings.System.getInt(
            this.mContext.getContentResolver(), "ringtone_vibration_sep_index_2", 0)
        == 0) {
      Settings.System.putInt(
          this.mContext.getContentResolver(),
          "ringtone_vibration_sep_index_2",
          this.mContext.getResources().getInteger(R.integer.device_idle_flex_time_short_ms));
    }
  }

  public void initializeVibrationState() {
    int[] iArr;
    int[] iArr2;
    boolean z;
    int[] intArray = this.mContext.getResources().getIntArray(17236329);
    int[] intArray2 = this.mContext.getResources().getIntArray(17236330);
    int[] intArray3 = this.mContext.getResources().getIntArray(17236331);
    int[] intArray4 = this.mContext.getResources().getIntArray(17236332);
    int[] intArray5 = this.mContext.getResources().getIntArray(17236334);
    int[] intArray6 = this.mContext.getResources().getIntArray(17236328);
    if (getDefaultVibratorController() != null) {
      iArr = getDefaultVibratorController().getAmplitudeList(0);
      iArr2 = getDefaultVibratorController().getAmplitudeList(1);
    } else {
      iArr = null;
      iArr2 = null;
    }
    if (VibRune.SUPPORT_HAPTIC_FEEDBACK_ON_DC_MOTOR && this.mMotorType == 1) {
      if (iArr2 != null && intArray6 != null && iArr2.length == intArray6.length) {
        Log.d("VibratorManagerService", "HAL DC touch amplitude list is used!!");
        z = true;
      }
      z = false;
    } else {
      if (iArr != null && iArr2 != null) {
        Log.d("VibratorManagerService", "HAL amplitude list is used!!");
        z = true;
      }
      z = false;
    }
    int i = this.mMotorType;
    if (i == 1) {
      if (VibRune.SUPPORT_HAPTIC_FEEDBACK_ON_DC_MOTOR) {
        this.LEVEL_TO_MAGNITUDE = loadMagnitudeList(z, iArr, intArray);
        this.LEVEL_TO_TOUCH_MAGNITUDE = loadTouchMagnitudeList(z, iArr2, intArray6);
        return;
      }
      return;
    }
    if (i == 2) {
      this.mIsEnableIntensity = true;
      this.mIsHapticSupported = true;
      this.LEVEL_TO_MAGNITUDE = loadMagnitudeList(z, iArr, intArray);
      this.LEVEL_TO_TOUCH_MAGNITUDE = loadTouchMagnitudeList(z, iArr2, iArr2);
      return;
    }
    if (i == 3) {
      this.LEVEL_TO_MAGNITUDE = loadMagnitudeList(z, iArr, intArray5);
      this.LEVEL_TO_TOUCH_MAGNITUDE = loadTouchMagnitudeList(z, iArr2, intArray5);
      this.mIsEnableIntensity = true;
      this.mIsHapticSupported = false;
      return;
    }
    if (i == 5) {
      this.LEVEL_TO_MAGNITUDE = loadMagnitudeList(z, iArr, intArray2);
      this.LEVEL_TO_TOUCH_MAGNITUDE = loadTouchMagnitudeList(z, iArr2, intArray4);
      VibRune.SET_RAM_INDEX_HAPTIC(true);
      this.mIsEnableIntensity = true;
      this.mIsHapticSupported = true;
      return;
    }
    if (i == 6 || i == 7) {
      this.LEVEL_TO_MAGNITUDE = loadMagnitudeList(z, iArr, intArray2);
      this.LEVEL_TO_TOUCH_MAGNITUDE = loadTouchMagnitudeList(z, iArr2, iArr2);
      this.mIsEnableIntensity = true;
      this.mIsHapticSupported = true;
      return;
    }
    if (i == 8 || i == 9) {
      this.LEVEL_TO_MAGNITUDE = loadMagnitudeList(z, iArr, intArray);
      this.LEVEL_TO_TOUCH_MAGNITUDE = loadTouchMagnitudeList(z, iArr2, intArray3);
      VibRune.SET_RAM_INDEX_HAPTIC(true);
      this.mIsEnableIntensity = true;
      this.mIsHapticSupported = true;
    }
  }

  public void updateAmplitudeLevel() {
    if (this.mIsEnableIntensity) {
      int[] iArr = this.LEVEL_TO_MAGNITUDE;
      int length = iArr.length - 1;
      int[] iArr2 = this.LEVEL_TO_TOUCH_MAGNITUDE;
      int length2 = iArr2.length - 1;
      if (length < 2 || length2 < 2) {
        Slog.d(
            "VibratorManagerService",
            "magnitudeMaxLevel : " + length + ", touchMagnitudeMaxLevel : " + length2);
        return;
      }
      this.mTouchMagnitude = iArr2[length2];
      int i = iArr[length];
      this.mCallMagnitude = i;
      this.mNotiMagnitude = i;
      this.mMaxMagnitude = i;
      this.mMinMagnitude = iArr[1];
      this.mMediaMagnitude = i;
      int loadSystemSetting = loadSystemSetting("VIB_FEEDBACK_MAGNITUDE", length2);
      if (loadSystemSetting <= length2) {
        loadSystemSetting = this.LEVEL_TO_TOUCH_MAGNITUDE[loadSystemSetting];
      }
      this.mTouchMagnitude = loadSystemSetting;
      int loadSystemSetting2 = loadSystemSetting("VIB_RECVCALL_MAGNITUDE", length);
      if (loadSystemSetting2 <= length) {
        loadSystemSetting2 = this.LEVEL_TO_MAGNITUDE[loadSystemSetting2];
      }
      this.mCallMagnitude = loadSystemSetting2;
      int loadSystemSetting3 = loadSystemSetting("SEM_VIBRATION_NOTIFICATION_INTENSITY", length);
      if (loadSystemSetting3 <= length) {
        loadSystemSetting3 = this.LEVEL_TO_MAGNITUDE[loadSystemSetting3];
      }
      this.mNotiMagnitude = loadSystemSetting3;
      int loadSystemSetting4 = loadSystemSetting("media_vibration_intensity", length);
      if (loadSystemSetting4 <= length) {
        loadSystemSetting4 = this.LEVEL_TO_MAGNITUDE[loadSystemSetting4];
      }
      this.mMediaMagnitude = loadSystemSetting4;
      if (this.mIsHapticEngineSupported) {
        int[] intArray = this.mContext.getResources().getIntArray(17236333);
        this.LEVEL_TO_FORCE_MAGNITUDE = intArray;
        int length3 = intArray.length - 1;
        this.mForceMagnitude = intArray[length3];
        if (length3 < 2) {
          Slog.d("VibratorManagerService", "touchForceMagnitudeMaxLevel : " + length3);
          return;
        }
        int loadSystemSetting5 = loadSystemSetting("SEM_VIBRATION_FORCE_TOUCH_INTENSITY", length3);
        if (loadSystemSetting5 <= length3) {
          loadSystemSetting5 = this.LEVEL_TO_FORCE_MAGNITUDE[loadSystemSetting5];
        }
        this.mForceMagnitude = loadSystemSetting5;
        return;
      }
      return;
    }
    if (VibRune.SUPPORT_HAPTIC_FEEDBACK_ON_DC_MOTOR && this.mMotorType == 1) {
      updateDcHapticFeedbackMagnitude();
    }
  }

  public final void updateDcHapticFeedbackMagnitude() {
    try {
      this.mTouchMagnitude =
          this.LEVEL_TO_TOUCH_MAGNITUDE[
              loadSystemSetting(
                  "VIB_FEEDBACK_MAGNITUDE", this.LEVEL_TO_TOUCH_MAGNITUDE.length - 1)];
    } catch (ArrayIndexOutOfBoundsException unused) {
      Log.i("VibratorManagerService", "failed to set DcHapticFeedbackMagnitude");
      this.mTouchMagnitude = this.LEVEL_TO_TOUCH_MAGNITUDE[1];
    }
  }

  public void registerFoldStateListener() {
    if (getDefaultVibratorController() == null) {
      return;
    }
    if (!getDefaultVibratorController().supportsFoldState()) {
      Log.d("VibratorManagerService", "Fold mode is not supported");
    } else {
      SemWindowManager.getInstance()
          .registerFoldStateListener(
              new SemWindowManager.FoldStateListener() { // from class:
                // com.android.server.vibrator.VibrationCustomSettings.1
                public void onFoldStateChanged(boolean z) {
                  Log.d("VibratorManagerService", "onFoldStateChanged isFolded = " + z);
                  if (VibrationCustomSettings.this.getDefaultVibratorController() != null) {
                    VibrationCustomSettings.this.getDefaultVibratorController().setFoldState(z);
                  }
                }

                public void onTableModeChanged(boolean z) {
                  Log.d("VibratorManagerService", "onTableModeChanged. isTableMode=" + z);
                }
              },
              (Handler) null);
    }
  }

  public int getCurrentMagnitude(int i) {
    if (i != 17) {
      if (i != 18) {
        if (i == 33) {
          return this.mCallMagnitude;
        }
        if (i != 34) {
          if (i == 49) {
            return this.mNotiMagnitude;
          }
          if (i == 50) {
            return this.mForceMagnitude;
          }
          return this.mMediaMagnitude;
        }
      }
      return this.mTouchMagnitude;
    }
    return this.mMaxMagnitude;
  }

  public int getMaxMagnitude() {
    return this.mMaxMagnitude;
  }

  public int getMinMagnitude() {
    return this.mMinMagnitude;
  }

  public String addCustomDump() {
    return "VibrationSettings information"
        + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE
        + "  mIsHapticSupported = "
        + this.mIsHapticSupported
        + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE
        + "  mIsEnableIntensity = "
        + this.mIsEnableIntensity
        + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE
        + "  mCallMagnitude = "
        + this.mCallMagnitude
        + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE
        + "  mNotiMagnitude = "
        + this.mNotiMagnitude
        + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE
        + "  mTouchMagnitude = "
        + this.mTouchMagnitude
        + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE
        + "  mMediaMagnitude = "
        + this.mMediaMagnitude
        + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE
        + "  mForceMagnitude = "
        + this.mForceMagnitude
        + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE
        + "  LEVEL_TO_MAGNITUDE = "
        + Arrays.toString(this.LEVEL_TO_MAGNITUDE)
        + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE
        + "  LEVEL_TO_TOUCH_MAGNITUDE = "
        + Arrays.toString(this.LEVEL_TO_TOUCH_MAGNITUDE)
        + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE;
  }
}
