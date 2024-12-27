package com.samsung.android.hardware.display;

import android.os.RemoteException;
import android.util.Slog;

/* loaded from: classes6.dex */
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
            Slog.i(TAG, "In Constructor Stub-Service(ISemMdnieManager) is null");
        }
        this.mService = service;
    }

    public int getScreenMode() {
        if (this.mService == null) {
            return RETURN_ERROR;
        }
        try {
            return this.mService.getScreenMode();
        } catch (RemoteException e) {
            return RETURN_ERROR;
        }
    }

    public int getContentMode() {
        if (this.mService == null) {
            return RETURN_ERROR;
        }
        try {
            return this.mService.getContentMode();
        } catch (RemoteException e) {
            return RETURN_ERROR;
        }
    }

    public boolean isScreenModeSupported() {
        if (this.mService == null) {
            return false;
        }
        try {
            return this.mService.isScreenModeSupported();
        } catch (RemoteException e) {
            return false;
        }
    }

    public int[] getSupportedScreenMode() {
        int[] emptyArray = new int[0];
        if (this.mService == null) {
            return emptyArray;
        }
        try {
            return this.mService.getSupportedScreenMode();
        } catch (RemoteException e) {
            return emptyArray;
        }
    }

    public boolean isContentModeSupported() {
        if (this.mService == null) {
            return false;
        }
        try {
            return this.mService.isContentModeSupported();
        } catch (RemoteException e) {
            return false;
        }
    }

    public int[] getSupportedContentMode() {
        int[] emptyArray = new int[0];
        if (this.mService == null) {
            return emptyArray;
        }
        try {
            return this.mService.getSupportedContentMode();
        } catch (RemoteException e) {
            return emptyArray;
        }
    }

    public boolean setScreenMode(int mode) {
        if (this.mService == null) {
            return false;
        }
        try {
            return this.mService.setScreenMode(mode);
        } catch (RemoteException e) {
            return false;
        }
    }

    public boolean setContentMode(int mode) {
        if (this.mService == null) {
            return false;
        }
        try {
            return this.mService.setContentMode(mode);
        } catch (RemoteException e) {
            return false;
        }
    }

    public boolean setWhiteRGB(int red, int green, int blue, int red_sub, int green_sub, int blue_sub) {
        if (this.mService == null) {
            return false;
        }
        try {
            return this.mService.setWhiteRGB(red, green, blue, red_sub, green_sub, blue_sub);
        } catch (RemoteException e) {
            return false;
        }
    }

    public void setEadMode(int index, int[] arr) {
        try {
            if (this.mService != null) {
                this.mService.setEadMode(index, arr);
            }
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void setEadModeSub(int index, int[] arr) {
        try {
            if (this.mService != null) {
                this.mService.setEadModeSub(index, arr);
            }
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public boolean isNightModeSupported() {
        if (this.mService == null) {
            return false;
        }
        try {
            return this.mService.isNightModeSupported();
        } catch (RemoteException e) {
            return false;
        }
    }

    public boolean enableNightMode(int opacityIndex) {
        if (this.mService == null) {
            return false;
        }
        try {
            return this.mService.setNightMode(true, opacityIndex);
        } catch (RemoteException e) {
            return false;
        }
    }

    public boolean disableNightMode() {
        if (this.mService == null) {
            return false;
        }
        try {
            return this.mService.disableNightMode();
        } catch (RemoteException e) {
            return false;
        }
    }

    public boolean setNightModeBlock(boolean support) {
        if (this.mService == null) {
            return false;
        }
        try {
            return this.mService.setNightModeBlock(support);
        } catch (RemoteException e) {
            return false;
        }
    }

    public boolean getNightModeBlock() {
        if (this.mService == null) {
            return false;
        }
        try {
            return this.mService.getNightModeBlock();
        } catch (RemoteException e) {
            return false;
        }
    }

    public boolean setNightModeStep(int index) {
        if (this.mService == null) {
            return false;
        }
        try {
            return this.mService.setNightModeStep(index);
        } catch (RemoteException e) {
            return false;
        }
    }

    public int getNightModeStep() {
        if (this.mService == null) {
            return RETURN_ERROR;
        }
        try {
            return this.mService.getNightModeStep();
        } catch (RemoteException e) {
            return RETURN_ERROR;
        }
    }

    public boolean setNightMode(boolean enable, int index) {
        if (this.mService == null) {
            return false;
        }
        try {
            return this.mService.setNightMode(enable, index);
        } catch (RemoteException e) {
            return false;
        }
    }

    public void setExtraDimMode(int opacity) {
        try {
            if (this.mService != null) {
                this.mService.setExtraDimMode(opacity);
            }
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void setHighBrightnessMode(int id, int lux, int index) {
        try {
            if (this.mService != null) {
                this.mService.setHighBrightnessMode(id, lux, index);
            }
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public boolean setHighDynamicRangeMode(boolean enable) {
        if (this.mService == null) {
            return false;
        }
        try {
            return this.mService.setHighDynamicRangeMode(enable);
        } catch (RemoteException e) {
            return false;
        }
    }

    public boolean setColorFadeNightDim(boolean enable) {
        if (this.mService == null) {
            return false;
        }
        try {
            return this.mService.setColorFadeNightDim(enable);
        } catch (RemoteException e) {
            return false;
        }
    }

    public boolean setColorVision(boolean enable, int color, int level) {
        if (this.mService == null) {
            return false;
        }
        try {
            return this.mService.setColorVision(enable, color, level);
        } catch (RemoteException e) {
            return false;
        }
    }

    public boolean setmDNIeColorBlind(boolean enable, int[] result) {
        if (this.mService == null) {
            return false;
        }
        try {
            return this.mService.setmDNIeColorBlind(enable, result);
        } catch (RemoteException e) {
            return false;
        }
    }

    public boolean setmDNIeNegative(boolean enable) {
        if (this.mService == null) {
            return false;
        }
        try {
            return this.mService.setmDNIeNegative(enable);
        } catch (RemoteException e) {
            return false;
        }
    }

    public boolean setmDNIeScreenCurtain(boolean enable) {
        if (this.mService == null) {
            return false;
        }
        try {
            return this.mService.setmDNIeScreenCurtain(enable);
        } catch (RemoteException e) {
            return false;
        }
    }

    public boolean setmDNIeEmergencyMode(boolean enable) {
        if (this.mService == null) {
            return false;
        }
        try {
            return this.mService.setmDNIeEmergencyMode(enable);
        } catch (RemoteException e) {
            return false;
        }
    }

    public boolean setmDNIeAccessibilityMode(int mode, boolean enable) {
        if (this.mService == null) {
            return false;
        }
        try {
            return this.mService.setmDNIeAccessibilityMode(mode, enable);
        } catch (RemoteException e) {
            return false;
        }
    }

    public boolean setLightNotificationMode(boolean enable) {
        if (this.mService == null) {
            return false;
        }
        try {
            return this.mService.setLightNotificationMode(enable);
        } catch (RemoteException e) {
            return false;
        }
    }

    public void updateAlwaysOnDisplay(boolean enable, int aodbrightness) {
        try {
            if (this.mService != null) {
                this.mService.updateAlwaysOnDisplay(enable, aodbrightness);
            }
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public boolean afpcDataVerify() {
        if (this.mService == null) {
            return false;
        }
        try {
            return this.mService.afpcDataVerify();
        } catch (RemoteException e) {
            return false;
        }
    }

    public boolean afpcDataWrite() {
        if (this.mService == null) {
            return false;
        }
        try {
            return this.mService.afpcDataWrite();
        } catch (RemoteException e) {
            return false;
        }
    }

    public boolean afpcDataApply() {
        if (this.mService == null) {
            return false;
        }
        try {
            return this.mService.afpcDataApply();
        } catch (RemoteException e) {
            return false;
        }
    }

    public boolean afpcDataOff() {
        if (this.mService == null) {
            return false;
        }
        try {
            return this.mService.afpcDataOff();
        } catch (RemoteException e) {
            return false;
        }
    }

    public boolean afpcWorkOff() {
        if (this.mService == null) {
            return false;
        }
        try {
            return this.mService.afpcWorkOff();
        } catch (RemoteException e) {
            return false;
        }
    }

    private void onError(Exception e) {
        Slog.e(TAG, "Error SemMdnieManager", e);
    }
}
