package com.samsung.android.displaysolution;

import android.os.RemoteException;
import android.util.Slog;

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
            Slog.d(TAG, "In Constructor Stub-Service(ISemDisplaySolutionManager) is null");
        }
        this.mService = service;
    }

    public boolean getVideoModeEnable() {
        if (this.mService == null) {
            return false;
        }
        try {
            return this.mService.getVideoModeEnable();
        } catch (RemoteException e) {
            return false;
        }
    }

    public boolean getGalleryModeEnable() {
        if (this.mService == null) {
            return false;
        }
        try {
            return this.mService.getGalleryModeEnable();
        } catch (RemoteException e) {
            return false;
        }
    }

    public boolean getCameraModeEnable() {
        if (this.mService == null) {
            return false;
        }
        try {
            return this.mService.getCameraModeEnable();
        } catch (RemoteException e) {
            return false;
        }
    }

    public boolean getDouAppModeEnable() {
        if (this.mService == null) {
            return false;
        }
        try {
            return this.mService.getDouAppModeEnable();
        } catch (RemoteException e) {
            return false;
        }
    }

    public boolean getAutoCurrentLimitOffModeEnabled() {
        if (this.mService == null) {
            return false;
        }
        try {
            return this.mService.getAutoCurrentLimitOffModeEnabled();
        } catch (RemoteException e) {
            return false;
        }
    }

    public String getOnPixelRatioValueForPMS() {
        if (this.mService == null) {
            return "";
        }
        try {
            return this.mService.getOnPixelRatioValueForPMS();
        } catch (RemoteException e) {
            return "";
        }
    }

    public int getVideoEnhancerSettingState(String name) {
        if (this.mService == null) {
            return RETURN_ERROR_I;
        }
        try {
            return this.mService.getVideoEnhancerSettingState(name);
        } catch (RemoteException e) {
            return RETURN_ERROR_I;
        }
    }

    public float getFingerPrintBacklightValue(int brightnessNits) {
        if (this.mService == null) {
            return RETURN_ERROR_F;
        }
        try {
            return this.mService.getFingerPrintBacklightValue(brightnessNits);
        } catch (RemoteException e) {
            return RETURN_ERROR_F;
        }
    }

    public float getAlphaMaskLevel(
            float CurrentPlatformBrightnessValue, float FingerPrintPlatformValue, float br_ctrl) {
        if (this.mService == null) {
            return RETURN_ERROR_F;
        }
        try {
            return this.mService.getAlphaMaskLevel(
                    CurrentPlatformBrightnessValue, FingerPrintPlatformValue, br_ctrl);
        } catch (RemoteException e) {
            return RETURN_ERROR_F;
        }
    }

    public boolean isMdnieScenarioControlServiceEnabled() {
        if (this.mService == null) {
            return false;
        }
        try {
            return this.mService.isMdnieScenarioControlServiceEnabled();
        } catch (RemoteException e) {
            return false;
        }
    }

    public void onDetailVeiwStateChanged(boolean enable) {
        try {
            if (this.mService != null) {
                this.mService.onDetailVeiwStateChanged(enable);
            }
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void onAutoCurrentLimitStateChanged(boolean enable) {
        try {
            if (this.mService != null) {
                this.mService.onAutoCurrentLimitStateChanged(enable);
            }
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void onAutoCurrentLimitStateChangedWithBrightness(boolean enable) {
        try {
            if (this.mService != null) {
                this.mService.onAutoCurrentLimitStateChangedWithBrightness(enable);
            }
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void onAutoCurrentLimitStateChangedInt(int value) {
        try {
            if (this.mService != null) {
                this.mService.onAutoCurrentLimitStateChangedInt(value);
            }
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void onAutoCurrentLimitOffMode(boolean enable) {
        try {
            if (this.mService != null) {
                this.mService.onAutoCurrentLimitOffMode(enable);
            }
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void onBurnInPreventionDisabled(boolean enable) {
        try {
            if (this.mService != null) {
                this.mService.onBurnInPreventionDisabled(enable);
            }
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void setHighDynamicRangeMode(boolean enable) {
        try {
            if (this.mService != null) {
                this.mService.setHighDynamicRangeMode(enable);
            }
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void updateAutoBrightnessLux(int id, int lux) {
        try {
            if (this.mService != null) {
                this.mService.updateAutoBrightnessLux(id, lux);
            }
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void setIRCompensationMode(boolean enable) {
        try {
            if (this.mService != null) {
                this.mService.setIRCompensationMode(enable);
            }
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void setVideoModeEnable(boolean enable) {
        try {
            if (this.mService != null) {
                this.mService.setVideoModeEnable(enable);
            }
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void setGalleryModeEnable(boolean enable) {
        try {
            if (this.mService != null) {
                this.mService.setGalleryModeEnable(enable);
            }
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void setCameraModeEnable(boolean enable) {
        try {
            if (this.mService != null) {
                this.mService.setCameraModeEnable(enable);
            }
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void setDouAppModeEnable(boolean enable) {
        try {
            if (this.mService != null) {
                this.mService.setDouAppModeEnable(enable);
            }
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void setAutoCurrentLimitOffModeEnabled(boolean enable) {
        try {
            if (this.mService != null) {
                this.mService.setAutoCurrentLimitOffModeEnabled(enable);
            }
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void setMdnieScenarioControlServiceEnable(boolean enable) {
        try {
            if (this.mService != null) {
                this.mService.setMdnieScenarioControlServiceEnable(enable);
            }
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void setScreenBrightnessForPreview(int settingValue) {
        try {
            if (this.mService != null) {
                this.mService.setScreenBrightnessForPreview(settingValue);
            }
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void setMultipleScreenBrightness(String name) {
        try {
            if (this.mService != null) {
                this.mService.setMultipleScreenBrightness(name);
            }
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void setOnPixelRatioValueForPMS(String value) {
        try {
            if (this.mService != null) {
                this.mService.setOnPixelRatioValueForPMS(value);
            }
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void setMultipleScreenBrightnessValueForHDR(float scalefactorValueHDR) {
        try {
            if (this.mService != null) {
                this.mService.setMultipleScreenBrightnessValueForHDR(scalefactorValueHDR);
            }
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void setEyeComfortWeightingFactor(float scaleValue) {
        try {
            if (this.mService != null) {
                this.mService.setEyeComfortWeightingFactor(scaleValue);
            }
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void setVideoEnhancerSettingState(String name, int state) {
        try {
            if (this.mService != null) {
                this.mService.setVideoEnhancerSettingState(name, state);
            }
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void setSleepPatternBLF(
            String mWeekType, long mBedtime, long mWakeupTime, float mConfidence) {
        try {
            if (this.mService != null) {
                this.mService.setSleepPatternBLF(mWeekType, mBedtime, mWakeupTime, mConfidence);
            }
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void setBlfEnableTimeBySchedule(boolean enable, int index) {
        try {
            if (this.mService != null) {
                this.mService.setBlfEnableTimeBySchedule(enable, index);
            }
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public void setmDNIeModeState(String mode) {
        try {
            if (this.mService != null) {
                this.mService.setmDNIeModeState(mode);
            }
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public boolean isBlueLightFilterScheduledTime() {
        try {
            if (this.mService != null) {
                return this.mService.isBlueLightFilterScheduledTime();
            }
            return false;
        } catch (RemoteException e) {
            onError(e);
            return false;
        }
    }

    public void setEadIndexOffset(int offset) {
        try {
            if (this.mService != null) {
                this.mService.setEadIndexOffset(offset);
            }
        } catch (RemoteException e) {
            onError(e);
        }
    }

    public int getBlfAdaptiveCurrentIndex() {
        try {
            if (this.mService != null) {
                return this.mService.getBlfAdaptiveCurrentIndex();
            }
            return -1;
        } catch (RemoteException e) {
            onError(e);
            return -1;
        }
    }

    private void onError(Exception e) {
        Slog.e(TAG, "Error SemDisplaySolutionManager", e);
    }
}
