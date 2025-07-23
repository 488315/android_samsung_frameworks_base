package com.samsung.android.displaysolution;

import android.os.RemoteException;
import android.util.Slog;

/* loaded from: classes6.dex */
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

    public SemDisplaySolutionManager(ISemDisplaySolutionManager iSemDisplaySolutionManager) {
        if (iSemDisplaySolutionManager == null) {
            Slog.d(TAG, "In Constructor Stub-Service(ISemDisplaySolutionManager) is null");
        }
        this.mService = iSemDisplaySolutionManager;
    }

    public boolean getVideoModeEnable() {
        ISemDisplaySolutionManager iSemDisplaySolutionManager = this.mService;
        if (iSemDisplaySolutionManager == null) {
            return false;
        }
        try {
            return iSemDisplaySolutionManager.getVideoModeEnable();
        } catch (RemoteException unused) {
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
        } catch (RemoteException unused) {
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
        } catch (RemoteException unused) {
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
        } catch (RemoteException unused) {
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
        } catch (RemoteException unused) {
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
        } catch (RemoteException unused) {
            return "";
        }
    }

    public int getVideoEnhancerSettingState(String str) {
        ISemDisplaySolutionManager iSemDisplaySolutionManager = this.mService;
        if (iSemDisplaySolutionManager == null) {
            return RETURN_ERROR_I;
        }
        try {
            return iSemDisplaySolutionManager.getVideoEnhancerSettingState(str);
        } catch (RemoteException unused) {
            return RETURN_ERROR_I;
        }
    }

    public float getFingerPrintBacklightValue(int i) {
        ISemDisplaySolutionManager iSemDisplaySolutionManager = this.mService;
        if (iSemDisplaySolutionManager == null) {
            return RETURN_ERROR_F;
        }
        try {
            return iSemDisplaySolutionManager.getFingerPrintBacklightValue(i);
        } catch (RemoteException unused) {
            return RETURN_ERROR_F;
        }
    }

    public float getAlphaMaskLevel(float f, float f2, float f3) {
        ISemDisplaySolutionManager iSemDisplaySolutionManager = this.mService;
        if (iSemDisplaySolutionManager == null) {
            return RETURN_ERROR_F;
        }
        try {
            return iSemDisplaySolutionManager.getAlphaMaskLevel(f, f2, f3);
        } catch (RemoteException unused) {
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
        } catch (RemoteException unused) {
            return false;
        }
    }

    public void onDetailVeiwStateChanged(boolean z) {
        try {
            ISemDisplaySolutionManager iSemDisplaySolutionManager = this.mService;
            if (iSemDisplaySolutionManager != null) {
                iSemDisplaySolutionManager.onDetailVeiwStateChanged(z);
            }
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void onAutoCurrentLimitStateChanged(boolean z) {
        try {
            ISemDisplaySolutionManager iSemDisplaySolutionManager = this.mService;
            if (iSemDisplaySolutionManager != null) {
                iSemDisplaySolutionManager.onAutoCurrentLimitStateChanged(z);
            }
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void onAutoCurrentLimitStateChangedWithBrightness(boolean z) {
        try {
            ISemDisplaySolutionManager iSemDisplaySolutionManager = this.mService;
            if (iSemDisplaySolutionManager != null) {
                iSemDisplaySolutionManager.onAutoCurrentLimitStateChangedWithBrightness(z);
            }
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void onAutoCurrentLimitStateChangedInt(int i) {
        try {
            ISemDisplaySolutionManager iSemDisplaySolutionManager = this.mService;
            if (iSemDisplaySolutionManager != null) {
                iSemDisplaySolutionManager.onAutoCurrentLimitStateChangedInt(i);
            }
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void onAutoCurrentLimitOffMode(boolean z) {
        try {
            ISemDisplaySolutionManager iSemDisplaySolutionManager = this.mService;
            if (iSemDisplaySolutionManager != null) {
                iSemDisplaySolutionManager.onAutoCurrentLimitOffMode(z);
            }
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void onBurnInPreventionDisabled(boolean z) {
        try {
            ISemDisplaySolutionManager iSemDisplaySolutionManager = this.mService;
            if (iSemDisplaySolutionManager != null) {
                iSemDisplaySolutionManager.onBurnInPreventionDisabled(z);
            }
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void setHighDynamicRangeMode(boolean z) {
        try {
            ISemDisplaySolutionManager iSemDisplaySolutionManager = this.mService;
            if (iSemDisplaySolutionManager != null) {
                iSemDisplaySolutionManager.setHighDynamicRangeMode(z);
            }
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void updateAutoBrightnessLux(int i, int i2) {
        try {
            ISemDisplaySolutionManager iSemDisplaySolutionManager = this.mService;
            if (iSemDisplaySolutionManager != null) {
                iSemDisplaySolutionManager.updateAutoBrightnessLux(i, i2);
            }
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void setIRCompensationMode(boolean z) {
        try {
            ISemDisplaySolutionManager iSemDisplaySolutionManager = this.mService;
            if (iSemDisplaySolutionManager != null) {
                iSemDisplaySolutionManager.setIRCompensationMode(z);
            }
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void setVideoModeEnable(boolean z) {
        try {
            ISemDisplaySolutionManager iSemDisplaySolutionManager = this.mService;
            if (iSemDisplaySolutionManager != null) {
                iSemDisplaySolutionManager.setVideoModeEnable(z);
            }
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void setGalleryModeEnable(boolean z) {
        try {
            ISemDisplaySolutionManager iSemDisplaySolutionManager = this.mService;
            if (iSemDisplaySolutionManager != null) {
                iSemDisplaySolutionManager.setGalleryModeEnable(z);
            }
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void setCameraModeEnable(boolean z) {
        try {
            ISemDisplaySolutionManager iSemDisplaySolutionManager = this.mService;
            if (iSemDisplaySolutionManager != null) {
                iSemDisplaySolutionManager.setCameraModeEnable(z);
            }
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void setDouAppModeEnable(boolean z) {
        try {
            ISemDisplaySolutionManager iSemDisplaySolutionManager = this.mService;
            if (iSemDisplaySolutionManager != null) {
                iSemDisplaySolutionManager.setDouAppModeEnable(z);
            }
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void setAutoCurrentLimitOffModeEnabled(boolean z) {
        try {
            ISemDisplaySolutionManager iSemDisplaySolutionManager = this.mService;
            if (iSemDisplaySolutionManager != null) {
                iSemDisplaySolutionManager.setAutoCurrentLimitOffModeEnabled(z);
            }
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void setMdnieScenarioControlServiceEnable(boolean z) {
        try {
            ISemDisplaySolutionManager iSemDisplaySolutionManager = this.mService;
            if (iSemDisplaySolutionManager != null) {
                iSemDisplaySolutionManager.setMdnieScenarioControlServiceEnable(z);
            }
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void setScreenBrightnessForPreview(int i) {
        try {
            ISemDisplaySolutionManager iSemDisplaySolutionManager = this.mService;
            if (iSemDisplaySolutionManager != null) {
                iSemDisplaySolutionManager.setScreenBrightnessForPreview(i);
            }
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void setMultipleScreenBrightness(String str) {
        try {
            ISemDisplaySolutionManager iSemDisplaySolutionManager = this.mService;
            if (iSemDisplaySolutionManager != null) {
                iSemDisplaySolutionManager.setMultipleScreenBrightness(str);
            }
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void setOnPixelRatioValueForPMS(String str) {
        try {
            ISemDisplaySolutionManager iSemDisplaySolutionManager = this.mService;
            if (iSemDisplaySolutionManager != null) {
                iSemDisplaySolutionManager.setOnPixelRatioValueForPMS(str);
            }
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void setMultipleScreenBrightnessValueForHDR(float f) {
        try {
            ISemDisplaySolutionManager iSemDisplaySolutionManager = this.mService;
            if (iSemDisplaySolutionManager != null) {
                iSemDisplaySolutionManager.setMultipleScreenBrightnessValueForHDR(f);
            }
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void setEyeComfortWeightingFactor(float f) {
        try {
            ISemDisplaySolutionManager iSemDisplaySolutionManager = this.mService;
            if (iSemDisplaySolutionManager != null) {
                iSemDisplaySolutionManager.setEyeComfortWeightingFactor(f);
            }
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void setVideoEnhancerSettingState(String str, int i) {
        try {
            ISemDisplaySolutionManager iSemDisplaySolutionManager = this.mService;
            if (iSemDisplaySolutionManager != null) {
                iSemDisplaySolutionManager.setVideoEnhancerSettingState(str, i);
            }
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void setSleepPatternBLF(String str, long j, long j2, float f) {
        try {
            ISemDisplaySolutionManager iSemDisplaySolutionManager = this.mService;
            if (iSemDisplaySolutionManager != null) {
                iSemDisplaySolutionManager.setSleepPatternBLF(str, j, j2, f);
            }
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void setBlfEnableTimeBySchedule(boolean z, int i) {
        try {
            ISemDisplaySolutionManager iSemDisplaySolutionManager = this.mService;
            if (iSemDisplaySolutionManager != null) {
                iSemDisplaySolutionManager.setBlfEnableTimeBySchedule(z, i);
            }
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void updateGlutMode(String str) {
        try {
            ISemDisplaySolutionManager iSemDisplaySolutionManager = this.mService;
            if (iSemDisplaySolutionManager != null) {
                iSemDisplaySolutionManager.updateGlutMode(str);
            }
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void updateQdcmMode(String str) {
        try {
            ISemDisplaySolutionManager iSemDisplaySolutionManager = this.mService;
            if (iSemDisplaySolutionManager != null) {
                iSemDisplaySolutionManager.updateQdcmMode(str);
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
            this.onError(e);
            return false;
        }
    }

    public void setEadIndexOffset(int i) {
        try {
            ISemDisplaySolutionManager iSemDisplaySolutionManager = this.mService;
            if (iSemDisplaySolutionManager != null) {
                iSemDisplaySolutionManager.setEadIndexOffset(i);
            }
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void setRenderIntentValue(int i) {
        try {
            ISemDisplaySolutionManager iSemDisplaySolutionManager = this.mService;
            if (iSemDisplaySolutionManager != null) {
                iSemDisplaySolutionManager.setRenderIntentValue(i);
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
            this.onError(e);
            return -1;
        }
    }

    private void onError(Exception exc) {
        Slog.e(TAG, "Error SemDisplaySolutionManager", exc);
    }
}
