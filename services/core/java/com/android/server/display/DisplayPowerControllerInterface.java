package com.android.server.display;

import android.content.pm.ParceledListSlice;
import android.hardware.SensorEvent;
import android.hardware.display.BrightnessConfiguration;
import android.hardware.display.BrightnessInfo;
import android.hardware.display.DisplayManagerInternal;
import java.io.PrintWriter;

/* loaded from: classes2.dex */
public interface DisplayPowerControllerInterface {
    void addBrightnessWeights(float f, float f2, float f3, float f4);

    void addDisplayBrightnessFollower(DisplayPowerControllerInterface displayPowerControllerInterface);

    void clearAdaptiveBrightnessLongtermModelBuilder();

    int convertToBrightness(float f);

    void doShortTermReset();

    void dump(PrintWriter printWriter);

    float getAdaptiveBrightness(float f);

    String getAmbientBrightnessInfo(float f);

    ParceledListSlice getAmbientBrightnessStats(int i);

    BrightnessConfiguration getAppliedBackupConfiguration(BrightnessConfiguration brightnessConfiguration);

    ParceledListSlice getBrightnessEvents(int i, boolean z);

    BrightnessInfo getBrightnessInfo();

    int[] getBrightnessLearningMaxLimitCount();

    float getCurrentScreenBrightness();

    BrightnessConfiguration getDefaultBrightnessConfiguration();

    int getDisplayId();

    float getLastAutomaticScreenBrightness();

    long getLastUserSetScreenBrightnessTime();

    int getLeadDisplayId();

    float getScreenBrightnessSetting();

    void ignoreProximitySensorUntilChanged();

    void injectLux(SensorEvent sensorEvent);

    boolean isProximitySensorAvailable();

    void onBootCompleted();

    void onDisplayChanged(HighBrightnessModeMetadata highBrightnessModeMetadata, int i);

    void onScreenBrightnessSettingTimeChanged();

    void onSwitchUser(int i);

    void persistBrightnessTrackerState();

    void removeDisplayBrightnessFollower(DisplayPowerControllerInterface displayPowerControllerInterface);

    boolean requestPowerState(DisplayManagerInternal.DisplayPowerRequest displayPowerRequest, boolean z);

    void restartAdaptiveBrightnessLongtermModelBuilderFromBnr();

    void setActualDisplayState(int i);

    void setAmbientColorTemperatureOverride(float f);

    void setAutoBrightnessLoggingEnabled(boolean z);

    void setAutomaticScreenBrightnessMode(boolean z);

    void setBrightness(float f);

    void setBrightnessConfiguration(BrightnessConfiguration brightnessConfiguration, boolean z);

    void setBrightnessToFollow(float f, float f2, float f3);

    void setDisplayWhiteBalanceLoggingEnabled(boolean z);

    void setHdrRampRate(float f, float f2);

    void setRampSpeedToFollower(float f, float f2);

    void setTemporaryAutoBrightnessAdjustment(float f);

    void setTemporaryBrightness(float f);

    void setTemporaryBrightnessForSlowChange(float f, boolean z);

    void setTestModeEnabled(boolean z);

    void stop();
}
