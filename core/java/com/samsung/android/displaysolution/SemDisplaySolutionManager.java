package com.samsung.android.displaysolution;

import android.p009os.RemoteException;
import android.util.Slog;

/* loaded from: classes5.dex */
public final class SemDisplaySolutionManager {
  private static long RETURN_ERROR = -1;
  private static float RETURN_ERROR_F = -1.0f;
  private static int RETURN_ERROR_I = -1;
  public static final int SUPPORT_CHANGABLE_NORMAL_AUTO_BRIGHTNESS = 2;
  public static final int SUPPORT_CHANGABLE_NUMBER_AUTO_BRIGHTNESS = 1;
  public static final int SUPPORT_ONLY_MANUAL_BRIGHTNESS = 0;
  public static final int SUPPORT_PERSONAL_AUTOBRIGHTNESS_CONTROL = 3;
  public static final int SUPPORT_PERSONAL_AUTOBRIGHTNESS_CONTROL_V3 = 4;
  public static final int SUPPORT_PERSONAL_AUTOBRIGHTNESS_CONTROL_V4 = 5;
  private static final String TAG = "SemDisplaySolutionManager";
  final ISemDisplaySolutionManager mService;

  public SemDisplaySolutionManager(ISemDisplaySolutionManager service) {
    if (service == null) {
      Slog.m113d(TAG, "In Constructor Stub-Service(ISemDisplaySolutionManager) is null");
    }
    this.mService = service;
  }

  public boolean getVideoModeEnable() {
    ISemDisplaySolutionManager iSemDisplaySolutionManager = this.mService;
    if (iSemDisplaySolutionManager == null) {
      return false;
    }
    try {
      return iSemDisplaySolutionManager.getVideoModeEnable();
    } catch (RemoteException e) {
      return false;
    }
  }

  public boolean getGalleryModeEnable() {
    ISemDisplaySolutionManager iSemDisplaySolutionManager = this.mService;
    if (iSemDisplaySolutionManager == null) {
      return false;
    }
    try {
      return iSemDisplaySolutionManager.getGalleryModeEnable();
    } catch (RemoteException e) {
      return false;
    }
  }

  public boolean getCameraModeEnable() {
    ISemDisplaySolutionManager iSemDisplaySolutionManager = this.mService;
    if (iSemDisplaySolutionManager == null) {
      return false;
    }
    try {
      return iSemDisplaySolutionManager.getCameraModeEnable();
    } catch (RemoteException e) {
      return false;
    }
  }

  public boolean getDouAppModeEnable() {
    ISemDisplaySolutionManager iSemDisplaySolutionManager = this.mService;
    if (iSemDisplaySolutionManager == null) {
      return false;
    }
    try {
      return iSemDisplaySolutionManager.getDouAppModeEnable();
    } catch (RemoteException e) {
      return false;
    }
  }

  public boolean getAutoCurrentLimitOffModeEnabled() {
    ISemDisplaySolutionManager iSemDisplaySolutionManager = this.mService;
    if (iSemDisplaySolutionManager == null) {
      return false;
    }
    try {
      return iSemDisplaySolutionManager.getAutoCurrentLimitOffModeEnabled();
    } catch (RemoteException e) {
      return false;
    }
  }

  public String getOnPixelRatioValueForPMS() {
    ISemDisplaySolutionManager iSemDisplaySolutionManager = this.mService;
    if (iSemDisplaySolutionManager == null) {
      return "";
    }
    try {
      return iSemDisplaySolutionManager.getOnPixelRatioValueForPMS();
    } catch (RemoteException e) {
      return "";
    }
  }

  public int getVideoEnhancerSettingState(String name) {
    ISemDisplaySolutionManager iSemDisplaySolutionManager = this.mService;
    if (iSemDisplaySolutionManager == null) {
      return RETURN_ERROR_I;
    }
    try {
      return iSemDisplaySolutionManager.getVideoEnhancerSettingState(name);
    } catch (RemoteException e) {
      return RETURN_ERROR_I;
    }
  }

  public float getFingerPrintBacklightValue(int brightnessNits) {
    ISemDisplaySolutionManager iSemDisplaySolutionManager = this.mService;
    if (iSemDisplaySolutionManager == null) {
      return RETURN_ERROR_F;
    }
    try {
      return iSemDisplaySolutionManager.getFingerPrintBacklightValue(brightnessNits);
    } catch (RemoteException e) {
      return RETURN_ERROR_F;
    }
  }

  public float getAlphaMaskLevel(
      float CurrentPlatformBrightnessValue, float FingerPrintPlatformValue, float br_ctrl) {
    ISemDisplaySolutionManager iSemDisplaySolutionManager = this.mService;
    if (iSemDisplaySolutionManager == null) {
      return RETURN_ERROR_F;
    }
    try {
      return iSemDisplaySolutionManager.getAlphaMaskLevel(
          CurrentPlatformBrightnessValue, FingerPrintPlatformValue, br_ctrl);
    } catch (RemoteException e) {
      return RETURN_ERROR_F;
    }
  }

  public boolean isMdnieScenarioControlServiceEnabled() {
    ISemDisplaySolutionManager iSemDisplaySolutionManager = this.mService;
    if (iSemDisplaySolutionManager == null) {
      return false;
    }
    try {
      return iSemDisplaySolutionManager.isMdnieScenarioControlServiceEnabled();
    } catch (RemoteException e) {
      return false;
    }
  }

  public void onDetailVeiwStateChanged(boolean enable) {
    try {
      ISemDisplaySolutionManager iSemDisplaySolutionManager = this.mService;
      if (iSemDisplaySolutionManager != null) {
        iSemDisplaySolutionManager.onDetailVeiwStateChanged(enable);
      }
    } catch (RemoteException e) {
      onError(e);
    }
  }

  public void onAutoCurrentLimitStateChanged(boolean enable) {
    try {
      ISemDisplaySolutionManager iSemDisplaySolutionManager = this.mService;
      if (iSemDisplaySolutionManager != null) {
        iSemDisplaySolutionManager.onAutoCurrentLimitStateChanged(enable);
      }
    } catch (RemoteException e) {
      onError(e);
    }
  }

  public void onAutoCurrentLimitStateChangedWithBrightness(boolean enable) {
    try {
      ISemDisplaySolutionManager iSemDisplaySolutionManager = this.mService;
      if (iSemDisplaySolutionManager != null) {
        iSemDisplaySolutionManager.onAutoCurrentLimitStateChangedWithBrightness(enable);
      }
    } catch (RemoteException e) {
      onError(e);
    }
  }

  public void onAutoCurrentLimitStateChangedInt(int value) {
    try {
      ISemDisplaySolutionManager iSemDisplaySolutionManager = this.mService;
      if (iSemDisplaySolutionManager != null) {
        iSemDisplaySolutionManager.onAutoCurrentLimitStateChangedInt(value);
      }
    } catch (RemoteException e) {
      onError(e);
    }
  }

  public void onAutoCurrentLimitOffMode(boolean enable) {
    try {
      ISemDisplaySolutionManager iSemDisplaySolutionManager = this.mService;
      if (iSemDisplaySolutionManager != null) {
        iSemDisplaySolutionManager.onAutoCurrentLimitOffMode(enable);
      }
    } catch (RemoteException e) {
      onError(e);
    }
  }

  public void onBurnInPreventionDisabled(boolean enable) {
    try {
      ISemDisplaySolutionManager iSemDisplaySolutionManager = this.mService;
      if (iSemDisplaySolutionManager != null) {
        iSemDisplaySolutionManager.onBurnInPreventionDisabled(enable);
      }
    } catch (RemoteException e) {
      onError(e);
    }
  }

  public void setHighDynamicRangeMode(boolean enable) {
    try {
      ISemDisplaySolutionManager iSemDisplaySolutionManager = this.mService;
      if (iSemDisplaySolutionManager != null) {
        iSemDisplaySolutionManager.setHighDynamicRangeMode(enable);
      }
    } catch (RemoteException e) {
      onError(e);
    }
  }

  public void setIRCompensationMode(boolean enable) {
    try {
      ISemDisplaySolutionManager iSemDisplaySolutionManager = this.mService;
      if (iSemDisplaySolutionManager != null) {
        iSemDisplaySolutionManager.setIRCompensationMode(enable);
      }
    } catch (RemoteException e) {
      onError(e);
    }
  }

  public void setVideoModeEnable(boolean enable) {
    try {
      ISemDisplaySolutionManager iSemDisplaySolutionManager = this.mService;
      if (iSemDisplaySolutionManager != null) {
        iSemDisplaySolutionManager.setVideoModeEnable(enable);
      }
    } catch (RemoteException e) {
      onError(e);
    }
  }

  public void setGalleryModeEnable(boolean enable) {
    try {
      ISemDisplaySolutionManager iSemDisplaySolutionManager = this.mService;
      if (iSemDisplaySolutionManager != null) {
        iSemDisplaySolutionManager.setGalleryModeEnable(enable);
      }
    } catch (RemoteException e) {
      onError(e);
    }
  }

  public void setCameraModeEnable(boolean enable) {
    try {
      ISemDisplaySolutionManager iSemDisplaySolutionManager = this.mService;
      if (iSemDisplaySolutionManager != null) {
        iSemDisplaySolutionManager.setCameraModeEnable(enable);
      }
    } catch (RemoteException e) {
      onError(e);
    }
  }

  public void setDouAppModeEnable(boolean enable) {
    try {
      ISemDisplaySolutionManager iSemDisplaySolutionManager = this.mService;
      if (iSemDisplaySolutionManager != null) {
        iSemDisplaySolutionManager.setDouAppModeEnable(enable);
      }
    } catch (RemoteException e) {
      onError(e);
    }
  }

  public void setAutoCurrentLimitOffModeEnabled(boolean enable) {
    try {
      ISemDisplaySolutionManager iSemDisplaySolutionManager = this.mService;
      if (iSemDisplaySolutionManager != null) {
        iSemDisplaySolutionManager.setAutoCurrentLimitOffModeEnabled(enable);
      }
    } catch (RemoteException e) {
      onError(e);
    }
  }

  public void setMdnieScenarioControlServiceEnable(boolean enable) {
    try {
      ISemDisplaySolutionManager iSemDisplaySolutionManager = this.mService;
      if (iSemDisplaySolutionManager != null) {
        iSemDisplaySolutionManager.setMdnieScenarioControlServiceEnable(enable);
      }
    } catch (RemoteException e) {
      onError(e);
    }
  }

  public void setScreenBrightnessForPreview(int settingValue) {
    try {
      ISemDisplaySolutionManager iSemDisplaySolutionManager = this.mService;
      if (iSemDisplaySolutionManager != null) {
        iSemDisplaySolutionManager.setScreenBrightnessForPreview(settingValue);
      }
    } catch (RemoteException e) {
      onError(e);
    }
  }

  public void setMultipleScreenBrightness(String name) {
    try {
      ISemDisplaySolutionManager iSemDisplaySolutionManager = this.mService;
      if (iSemDisplaySolutionManager != null) {
        iSemDisplaySolutionManager.setMultipleScreenBrightness(name);
      }
    } catch (RemoteException e) {
      onError(e);
    }
  }

  public void setOnPixelRatioValueForPMS(String value) {
    try {
      ISemDisplaySolutionManager iSemDisplaySolutionManager = this.mService;
      if (iSemDisplaySolutionManager != null) {
        iSemDisplaySolutionManager.setOnPixelRatioValueForPMS(value);
      }
    } catch (RemoteException e) {
      onError(e);
    }
  }

  public void setMultipleScreenBrightnessValueForHDR(float scalefactorValueHDR) {
    try {
      ISemDisplaySolutionManager iSemDisplaySolutionManager = this.mService;
      if (iSemDisplaySolutionManager != null) {
        iSemDisplaySolutionManager.setMultipleScreenBrightnessValueForHDR(scalefactorValueHDR);
      }
    } catch (RemoteException e) {
      onError(e);
    }
  }

  public void setEyeComfortWeightingFactor(float scaleValue) {
    try {
      ISemDisplaySolutionManager iSemDisplaySolutionManager = this.mService;
      if (iSemDisplaySolutionManager != null) {
        iSemDisplaySolutionManager.setEyeComfortWeightingFactor(scaleValue);
      }
    } catch (RemoteException e) {
      onError(e);
    }
  }

  public void setVideoEnhancerSettingState(String name, int state) {
    try {
      ISemDisplaySolutionManager iSemDisplaySolutionManager = this.mService;
      if (iSemDisplaySolutionManager != null) {
        iSemDisplaySolutionManager.setVideoEnhancerSettingState(name, state);
      }
    } catch (RemoteException e) {
      onError(e);
    }
  }

  public void setSleepPatternBLF(
      String mWeekType, long mBedtime, long mWakeupTime, float mConfidence) {
    try {
      ISemDisplaySolutionManager iSemDisplaySolutionManager = this.mService;
      if (iSemDisplaySolutionManager != null) {
        iSemDisplaySolutionManager.setSleepPatternBLF(
            mWeekType, mBedtime, mWakeupTime, mConfidence);
      }
    } catch (RemoteException e) {
      onError(e);
    }
  }

  public void setBlfEnableTimeBySchedule(boolean enable, int index) {
    try {
      ISemDisplaySolutionManager iSemDisplaySolutionManager = this.mService;
      if (iSemDisplaySolutionManager != null) {
        iSemDisplaySolutionManager.setBlfEnableTimeBySchedule(enable, index);
      }
    } catch (RemoteException e) {
      onError(e);
    }
  }

  public boolean isBlueLightFilterScheduledTime() {
    try {
      ISemDisplaySolutionManager iSemDisplaySolutionManager = this.mService;
      if (iSemDisplaySolutionManager != null) {
        return iSemDisplaySolutionManager.isBlueLightFilterScheduledTime();
      }
      return false;
    } catch (RemoteException e) {
      onError(e);
      return false;
    }
  }

  public void setEadIndexOffset(int offset) {
    try {
      ISemDisplaySolutionManager iSemDisplaySolutionManager = this.mService;
      if (iSemDisplaySolutionManager != null) {
        iSemDisplaySolutionManager.setEadIndexOffset(offset);
      }
    } catch (RemoteException e) {
      onError(e);
    }
  }

  public int getBlfAdaptiveCurrentIndex() {
    try {
      ISemDisplaySolutionManager iSemDisplaySolutionManager = this.mService;
      if (iSemDisplaySolutionManager != null) {
        return iSemDisplaySolutionManager.getBlfAdaptiveCurrentIndex();
      }
      return -1;
    } catch (RemoteException e) {
      onError(e);
      return -1;
    }
  }

  private void onError(Exception e) {
    Slog.m116e(TAG, "Error SemDisplaySolutionManager", e);
  }
}
