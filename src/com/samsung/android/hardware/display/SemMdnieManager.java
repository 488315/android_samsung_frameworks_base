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

    public SemMdnieManager(ISemMdnieManager iSemMdnieManager) {
        if (iSemMdnieManager == null) {
            Slog.i(TAG, "In Constructor Stub-Service(ISemMdnieManager) is null");
        }
        this.mService = iSemMdnieManager;
    }

    public int getScreenMode() {
        ISemMdnieManager iSemMdnieManager = this.mService;
        if (iSemMdnieManager == null) {
            return RETURN_ERROR;
        }
        try {
            return iSemMdnieManager.getScreenMode();
        } catch (RemoteException unused) {
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
        } catch (RemoteException unused) {
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
        } catch (RemoteException unused) {
            return false;
        }
    }

    public int[] getSupportedScreenMode() {
        int[] iArr = new int[0];
        ISemMdnieManager iSemMdnieManager = this.mService;
        if (iSemMdnieManager != null) {
            try {
                return iSemMdnieManager.getSupportedScreenMode();
            } catch (RemoteException unused) {
            }
        }
        return iArr;
    }

    public boolean isContentModeSupported() {
        ISemMdnieManager iSemMdnieManager = this.mService;
        if (iSemMdnieManager == null) {
            return false;
        }
        try {
            return iSemMdnieManager.isContentModeSupported();
        } catch (RemoteException unused) {
            return false;
        }
    }

    public int[] getSupportedContentMode() {
        int[] iArr = new int[0];
        ISemMdnieManager iSemMdnieManager = this.mService;
        if (iSemMdnieManager != null) {
            try {
                return iSemMdnieManager.getSupportedContentMode();
            } catch (RemoteException unused) {
            }
        }
        return iArr;
    }

    public boolean setScreenMode(int i) {
        ISemMdnieManager iSemMdnieManager = this.mService;
        if (iSemMdnieManager == null) {
            return false;
        }
        try {
            return iSemMdnieManager.setScreenMode(i);
        } catch (RemoteException unused) {
            return false;
        }
    }

    public boolean setContentMode(int i) {
        ISemMdnieManager iSemMdnieManager = this.mService;
        if (iSemMdnieManager == null) {
            return false;
        }
        try {
            return iSemMdnieManager.setContentMode(i);
        } catch (RemoteException unused) {
            return false;
        }
    }

    public boolean setWhiteRGB(int i, int i2, int i3, int i4, int i5, int i6) {
        ISemMdnieManager iSemMdnieManager = this.mService;
        if (iSemMdnieManager == null) {
            return false;
        }
        try {
            return iSemMdnieManager.setWhiteRGB(i, i2, i3, i4, i5, i6);
        } catch (RemoteException unused) {
            return false;
        }
    }

    public void setEadMode(int i, int i2, int[] iArr) {
        try {
            ISemMdnieManager iSemMdnieManager = this.mService;
            if (iSemMdnieManager != null) {
                iSemMdnieManager.setEadMode(i, i2, iArr);
            }
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void setEadModeSub(int i, int i2, int[] iArr) {
        try {
            ISemMdnieManager iSemMdnieManager = this.mService;
            if (iSemMdnieManager != null) {
                iSemMdnieManager.setEadModeSub(i, i2, iArr);
            }
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public boolean isNightModeSupported() {
        ISemMdnieManager iSemMdnieManager = this.mService;
        if (iSemMdnieManager == null) {
            return false;
        }
        try {
            return iSemMdnieManager.isNightModeSupported();
        } catch (RemoteException unused) {
            return false;
        }
    }

    public boolean enableNightMode(int i) {
        ISemMdnieManager iSemMdnieManager = this.mService;
        if (iSemMdnieManager == null) {
            return false;
        }
        try {
            return iSemMdnieManager.setNightMode(true, i);
        } catch (RemoteException unused) {
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
        } catch (RemoteException unused) {
            return false;
        }
    }

    public boolean setNightModeBlock(boolean z) {
        ISemMdnieManager iSemMdnieManager = this.mService;
        if (iSemMdnieManager == null) {
            return false;
        }
        try {
            return iSemMdnieManager.setNightModeBlock(z);
        } catch (RemoteException unused) {
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
        } catch (RemoteException unused) {
            return false;
        }
    }

    public boolean setNightModeStep(int i) {
        ISemMdnieManager iSemMdnieManager = this.mService;
        if (iSemMdnieManager == null) {
            return false;
        }
        try {
            return iSemMdnieManager.setNightModeStep(i);
        } catch (RemoteException unused) {
            return false;
        }
    }

    public boolean setNightModeCct(int i) {
        ISemMdnieManager iSemMdnieManager = this.mService;
        if (iSemMdnieManager == null) {
            return false;
        }
        try {
            return iSemMdnieManager.setNightModeCct(i);
        } catch (RemoteException unused) {
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
        } catch (RemoteException unused) {
            return RETURN_ERROR;
        }
    }

    public int getNightModeCct() {
        ISemMdnieManager iSemMdnieManager = this.mService;
        if (iSemMdnieManager == null) {
            return RETURN_ERROR;
        }
        try {
            return iSemMdnieManager.getNightModeCct();
        } catch (RemoteException unused) {
            return RETURN_ERROR;
        }
    }

    public boolean setNightMode(boolean z, int i) {
        ISemMdnieManager iSemMdnieManager = this.mService;
        if (iSemMdnieManager == null) {
            return false;
        }
        try {
            return iSemMdnieManager.setNightMode(z, i);
        } catch (RemoteException unused) {
            return false;
        }
    }

    public void setExtraDimMode(int i) {
        try {
            ISemMdnieManager iSemMdnieManager = this.mService;
            if (iSemMdnieManager != null) {
                iSemMdnieManager.setExtraDimMode(i);
            }
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void setHighBrightnessMode(int i, int i2, int i3) {
        try {
            ISemMdnieManager iSemMdnieManager = this.mService;
            if (iSemMdnieManager != null) {
                iSemMdnieManager.setHighBrightnessMode(i, i2, i3);
            }
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public boolean setHighDynamicRangeMode(boolean z) {
        ISemMdnieManager iSemMdnieManager = this.mService;
        if (iSemMdnieManager == null) {
            return false;
        }
        try {
            return iSemMdnieManager.setHighDynamicRangeMode(z);
        } catch (RemoteException unused) {
            return false;
        }
    }

    public boolean setColorFadeNightDim(boolean z) {
        ISemMdnieManager iSemMdnieManager = this.mService;
        if (iSemMdnieManager == null) {
            return false;
        }
        try {
            return iSemMdnieManager.setColorFadeNightDim(z);
        } catch (RemoteException unused) {
            return false;
        }
    }

    public boolean setColorVision(boolean z, int i, int i2) {
        ISemMdnieManager iSemMdnieManager = this.mService;
        if (iSemMdnieManager == null) {
            return false;
        }
        try {
            return iSemMdnieManager.setColorVision(z, i, i2);
        } catch (RemoteException unused) {
            return false;
        }
    }

    public boolean setmDNIeColorBlind(boolean z, int[] iArr) {
        ISemMdnieManager iSemMdnieManager = this.mService;
        if (iSemMdnieManager == null) {
            return false;
        }
        try {
            return iSemMdnieManager.setmDNIeColorBlind(z, iArr);
        } catch (RemoteException unused) {
            return false;
        }
    }

    public boolean setmDNIeNegative(boolean z) {
        ISemMdnieManager iSemMdnieManager = this.mService;
        if (iSemMdnieManager == null) {
            return false;
        }
        try {
            return iSemMdnieManager.setmDNIeNegative(z);
        } catch (RemoteException unused) {
            return false;
        }
    }

    public boolean setmDNIeScreenCurtain(boolean z) {
        ISemMdnieManager iSemMdnieManager = this.mService;
        if (iSemMdnieManager == null) {
            return false;
        }
        try {
            return iSemMdnieManager.setmDNIeScreenCurtain(z);
        } catch (RemoteException unused) {
            return false;
        }
    }

    public boolean setmDNIeEmergencyMode(boolean z) {
        ISemMdnieManager iSemMdnieManager = this.mService;
        if (iSemMdnieManager == null) {
            return false;
        }
        try {
            return iSemMdnieManager.setmDNIeEmergencyMode(z);
        } catch (RemoteException unused) {
            return false;
        }
    }

    public boolean setmDNIeAccessibilityMode(int i, boolean z) {
        ISemMdnieManager iSemMdnieManager = this.mService;
        if (iSemMdnieManager == null) {
            return false;
        }
        try {
            return iSemMdnieManager.setmDNIeAccessibilityMode(i, z);
        } catch (RemoteException unused) {
            return false;
        }
    }

    public boolean setLightNotificationMode(boolean z) {
        ISemMdnieManager iSemMdnieManager = this.mService;
        if (iSemMdnieManager == null) {
            return false;
        }
        try {
            return iSemMdnieManager.setLightNotificationMode(z);
        } catch (RemoteException unused) {
            return false;
        }
    }

    public void updateAlwaysOnDisplay(boolean z, int i) {
        try {
            ISemMdnieManager iSemMdnieManager = this.mService;
            if (iSemMdnieManager != null) {
                iSemMdnieManager.updateAlwaysOnDisplay(z, i);
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
        } catch (RemoteException unused) {
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
        } catch (RemoteException unused) {
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
        } catch (RemoteException unused) {
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
        } catch (RemoteException unused) {
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
        } catch (RemoteException unused) {
            return false;
        }
    }

    private void onError(Exception exc) {
        Slog.e(TAG, "Error SemMdnieManager", exc);
    }
}
