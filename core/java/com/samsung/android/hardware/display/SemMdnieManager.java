package com.samsung.android.hardware.display;

import android.os.RemoteException;
import android.util.Slog;

/* loaded from: classes5.dex */
public final class SemMdnieManager {
  public static final int CONTENT_MODE_BROWSER = 8;
  public static final int CONTENT_MODE_CAMERA = 4;
  public static final int CONTENT_MODE_DMB = 20;
  public static final int CONTENT_MODE_EBOOK = 9;
  public static final int CONTENT_MODE_GALLERY = 6;
  public static final int CONTENT_MODE_GAME_HIGH = 13;
  public static final int CONTENT_MODE_GAME_LOW = 11;
  public static final int CONTENT_MODE_GAME_MID = 12;
  public static final int CONTENT_MODE_UI = 0;
  public static final int CONTENT_MODE_VIDEO = 1;
  public static final int CONTENT_MODE_VIDEO_ENHANCER = 14;
  public static final int CONTENT_MODE_VIDEO_ENHANCER_2 = 15;
  public static final int MDNIE_SUPPORT_BLUE_FILTER = 4096;
  public static final int MDNIE_SUPPORT_COLOR_ADJUSTMENT = 2048;
  public static final int MDNIE_SUPPORT_CONTENT_GAME_MODE = 2;
  public static final int MDNIE_SUPPORT_CONTENT_MODE = 1;
  public static final int MDNIE_SUPPORT_CONTENT_SWA_MODE = 8;
  public static final int MDNIE_SUPPORT_CONTENT_VIDEO_ENGANCE_MODE = 4;
  public static final int MDNIE_SUPPORT_GRAYSCALE = 512;
  public static final int MDNIE_SUPPORT_HDR = 16384;
  public static final int MDNIE_SUPPORT_HMT = 8192;
  public static final int MDNIE_SUPPORT_LIGHT_NOTIFICATION = 32768;
  public static final int MDNIE_SUPPORT_NEGATIVE = 256;
  public static final int MDNIE_SUPPORT_READING_MODE = 32;
  public static final int MDNIE_SUPPORT_SCREENCURTAIN = 1024;
  public static final int MDNIE_SUPPORT_SCREEN_MODE = 16;
  private static int RETURN_ERROR = -1;
  public static final int SCREEN_MODE_ADAPTIVE = 4;
  public static final int SCREEN_MODE_AMOLED_CINEMA = 0;
  public static final int SCREEN_MODE_AMOLED_PHOTO = 1;
  public static final int SCREEN_MODE_BASIC = 2;
  public static final int SCREEN_MODE_NATURAL = 3;
  public static final int SCREEN_MODE_READING = 5;
  private static final String TAG = "SemMdnieManager";
  final ISemMdnieManager mService;

  public SemMdnieManager(ISemMdnieManager service) {
    if (service == null) {
      Slog.m117i(TAG, "In Constructor Stub-Service(ISemMdnieManager) is null");
    }
    this.mService = service;
  }

  public int getScreenMode() {
    ISemMdnieManager iSemMdnieManager = this.mService;
    if (iSemMdnieManager == null) {
      return RETURN_ERROR;
    }
    try {
      return iSemMdnieManager.getScreenMode();
    } catch (RemoteException e) {
      return RETURN_ERROR;
    }
  }

  public int getContentMode() {
    ISemMdnieManager iSemMdnieManager = this.mService;
    if (iSemMdnieManager == null) {
      return RETURN_ERROR;
    }
    try {
      return iSemMdnieManager.getContentMode();
    } catch (RemoteException e) {
      return RETURN_ERROR;
    }
  }

  public boolean isScreenModeSupported() {
    ISemMdnieManager iSemMdnieManager = this.mService;
    if (iSemMdnieManager == null) {
      return false;
    }
    try {
      return iSemMdnieManager.isScreenModeSupported();
    } catch (RemoteException e) {
      return false;
    }
  }

  public int[] getSupportedScreenMode() {
    int[] emptyArray = new int[0];
    ISemMdnieManager iSemMdnieManager = this.mService;
    if (iSemMdnieManager == null) {
      return emptyArray;
    }
    try {
      return iSemMdnieManager.getSupportedScreenMode();
    } catch (RemoteException e) {
      return emptyArray;
    }
  }

  public boolean isContentModeSupported() {
    ISemMdnieManager iSemMdnieManager = this.mService;
    if (iSemMdnieManager == null) {
      return false;
    }
    try {
      return iSemMdnieManager.isContentModeSupported();
    } catch (RemoteException e) {
      return false;
    }
  }

  public int[] getSupportedContentMode() {
    int[] emptyArray = new int[0];
    ISemMdnieManager iSemMdnieManager = this.mService;
    if (iSemMdnieManager == null) {
      return emptyArray;
    }
    try {
      return iSemMdnieManager.getSupportedContentMode();
    } catch (RemoteException e) {
      return emptyArray;
    }
  }

  public boolean setScreenMode(int mode) {
    ISemMdnieManager iSemMdnieManager = this.mService;
    if (iSemMdnieManager == null) {
      return false;
    }
    try {
      return iSemMdnieManager.setScreenMode(mode);
    } catch (RemoteException e) {
      return false;
    }
  }

  public boolean setContentMode(int mode) {
    ISemMdnieManager iSemMdnieManager = this.mService;
    if (iSemMdnieManager == null) {
      return false;
    }
    try {
      return iSemMdnieManager.setContentMode(mode);
    } catch (RemoteException e) {
      return false;
    }
  }

  public boolean setAmoledACL(int mode) {
    ISemMdnieManager iSemMdnieManager = this.mService;
    if (iSemMdnieManager == null) {
      return false;
    }
    try {
      return iSemMdnieManager.setAmoledACL(mode);
    } catch (RemoteException e) {
      return false;
    }
  }

  public boolean setWhiteRGB(
      int red, int green, int blue, int red_sub, int green_sub, int blue_sub) {
    ISemMdnieManager iSemMdnieManager = this.mService;
    if (iSemMdnieManager == null) {
      return false;
    }
    try {
      return iSemMdnieManager.setWhiteRGB(red, green, blue, red_sub, green_sub, blue_sub);
    } catch (RemoteException e) {
      return false;
    }
  }

  public boolean isNightModeSupported() {
    ISemMdnieManager iSemMdnieManager = this.mService;
    if (iSemMdnieManager == null) {
      return false;
    }
    try {
      return iSemMdnieManager.isNightModeSupported();
    } catch (RemoteException e) {
      return false;
    }
  }

  public boolean enableNightMode(int opacityIndex) {
    ISemMdnieManager iSemMdnieManager = this.mService;
    if (iSemMdnieManager == null) {
      return false;
    }
    try {
      return iSemMdnieManager.setNightMode(true, opacityIndex);
    } catch (RemoteException e) {
      return false;
    }
  }

  public boolean disableNightMode() {
    ISemMdnieManager iSemMdnieManager = this.mService;
    if (iSemMdnieManager == null) {
      return false;
    }
    try {
      return iSemMdnieManager.disableNightMode();
    } catch (RemoteException e) {
      return false;
    }
  }

  public boolean setNightModeBlock(boolean support) {
    ISemMdnieManager iSemMdnieManager = this.mService;
    if (iSemMdnieManager == null) {
      return false;
    }
    try {
      return iSemMdnieManager.setNightModeBlock(support);
    } catch (RemoteException e) {
      return false;
    }
  }

  public boolean getNightModeBlock() {
    ISemMdnieManager iSemMdnieManager = this.mService;
    if (iSemMdnieManager == null) {
      return false;
    }
    try {
      return iSemMdnieManager.getNightModeBlock();
    } catch (RemoteException e) {
      return false;
    }
  }

  public boolean setNightModeStep(int index) {
    ISemMdnieManager iSemMdnieManager = this.mService;
    if (iSemMdnieManager == null) {
      return false;
    }
    try {
      return iSemMdnieManager.setNightModeStep(index);
    } catch (RemoteException e) {
      return false;
    }
  }

  public int getNightModeStep() {
    ISemMdnieManager iSemMdnieManager = this.mService;
    if (iSemMdnieManager == null) {
      return RETURN_ERROR;
    }
    try {
      return iSemMdnieManager.getNightModeStep();
    } catch (RemoteException e) {
      return RETURN_ERROR;
    }
  }

  public boolean setNightMode(boolean enable, int index) {
    ISemMdnieManager iSemMdnieManager = this.mService;
    if (iSemMdnieManager == null) {
      return false;
    }
    try {
      return iSemMdnieManager.setNightMode(enable, index);
    } catch (RemoteException e) {
      return false;
    }
  }

  public boolean setColorFadeNightDim(boolean enable) {
    ISemMdnieManager iSemMdnieManager = this.mService;
    if (iSemMdnieManager == null) {
      return false;
    }
    try {
      return iSemMdnieManager.setColorFadeNightDim(enable);
    } catch (RemoteException e) {
      return false;
    }
  }

  public boolean setColorVision(boolean enable, int color, int level) {
    ISemMdnieManager iSemMdnieManager = this.mService;
    if (iSemMdnieManager == null) {
      return false;
    }
    try {
      return iSemMdnieManager.setColorVision(enable, color, level);
    } catch (RemoteException e) {
      return false;
    }
  }

  public boolean setmDNIeColorBlind(boolean enable, int[] result) {
    ISemMdnieManager iSemMdnieManager = this.mService;
    if (iSemMdnieManager == null) {
      return false;
    }
    try {
      return iSemMdnieManager.setmDNIeColorBlind(enable, result);
    } catch (RemoteException e) {
      return false;
    }
  }

  public boolean setmDNIeNegative(boolean enable) {
    ISemMdnieManager iSemMdnieManager = this.mService;
    if (iSemMdnieManager == null) {
      return false;
    }
    try {
      return iSemMdnieManager.setmDNIeNegative(enable);
    } catch (RemoteException e) {
      return false;
    }
  }

  public boolean setmDNIeScreenCurtain(boolean enable) {
    ISemMdnieManager iSemMdnieManager = this.mService;
    if (iSemMdnieManager == null) {
      return false;
    }
    try {
      return iSemMdnieManager.setmDNIeScreenCurtain(enable);
    } catch (RemoteException e) {
      return false;
    }
  }

  public boolean setmDNIeEmergencyMode(boolean enable) {
    ISemMdnieManager iSemMdnieManager = this.mService;
    if (iSemMdnieManager == null) {
      return false;
    }
    try {
      return iSemMdnieManager.setmDNIeEmergencyMode(enable);
    } catch (RemoteException e) {
      return false;
    }
  }

  public boolean setmDNIeAccessibilityMode(int mode, boolean enable) {
    ISemMdnieManager iSemMdnieManager = this.mService;
    if (iSemMdnieManager == null) {
      return false;
    }
    try {
      return iSemMdnieManager.setmDNIeAccessibilityMode(mode, enable);
    } catch (RemoteException e) {
      return false;
    }
  }

  public boolean setLightNotificationMode(boolean enable) {
    ISemMdnieManager iSemMdnieManager = this.mService;
    if (iSemMdnieManager == null) {
      return false;
    }
    try {
      return iSemMdnieManager.setLightNotificationMode(enable);
    } catch (RemoteException e) {
      return false;
    }
  }

  public void updateAlwaysOnDisplay(boolean enable, int aodbrightness) {
    try {
      ISemMdnieManager iSemMdnieManager = this.mService;
      if (iSemMdnieManager != null) {
        iSemMdnieManager.updateAlwaysOnDisplay(enable, aodbrightness);
      }
    } catch (RemoteException e) {
      onError(e);
    }
  }

  public boolean afpcDataVerify() {
    ISemMdnieManager iSemMdnieManager = this.mService;
    if (iSemMdnieManager == null) {
      return false;
    }
    try {
      return iSemMdnieManager.afpcDataVerify();
    } catch (RemoteException e) {
      return false;
    }
  }

  public boolean afpcDataWrite() {
    ISemMdnieManager iSemMdnieManager = this.mService;
    if (iSemMdnieManager == null) {
      return false;
    }
    try {
      return iSemMdnieManager.afpcDataWrite();
    } catch (RemoteException e) {
      return false;
    }
  }

  public boolean afpcDataApply() {
    ISemMdnieManager iSemMdnieManager = this.mService;
    if (iSemMdnieManager == null) {
      return false;
    }
    try {
      return iSemMdnieManager.afpcDataApply();
    } catch (RemoteException e) {
      return false;
    }
  }

  public boolean afpcDataOff() {
    ISemMdnieManager iSemMdnieManager = this.mService;
    if (iSemMdnieManager == null) {
      return false;
    }
    try {
      return iSemMdnieManager.afpcDataOff();
    } catch (RemoteException e) {
      return false;
    }
  }

  public boolean afpcWorkOff() {
    ISemMdnieManager iSemMdnieManager = this.mService;
    if (iSemMdnieManager == null) {
      return false;
    }
    try {
      return iSemMdnieManager.afpcWorkOff();
    } catch (RemoteException e) {
      return false;
    }
  }

  private void onError(Exception e) {
    Slog.m116e(TAG, "Error SemMdnieManager", e);
  }
}
