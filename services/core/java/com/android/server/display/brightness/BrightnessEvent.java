package com.android.server.display.brightness;

import android.hardware.display.BrightnessInfo;
import android.hardware.display.DisplayManagerInternal;
import android.os.SystemClock;
import android.view.Display;

import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.display.config.DisplayBrightnessMappingConfig;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class BrightnessEvent {
    public static final SimpleDateFormat FORMAT = new SimpleDateFormat("MM-dd HH:mm:ss.SSS");
    public int mAdjustmentFlags;
    public int mAutoBrightnessMode;
    public boolean mAutomaticBrightnessEnabled;
    public float mBrightness;
    public String mDisplayBrightnessStrategyName;
    public int mDisplayId;
    public int mDisplayPolicy;
    public int mDisplayState;
    public int mFlags;
    public float mHbmMax;
    public int mHbmMode;
    public float mInitialBrightness;
    public float mLux;
    public String mPhysicalDisplayId;
    public float mPowerFactor;
    public float mPreThresholdBrightness;
    public float mPreThresholdLux;
    public int mRbcStrength;
    public BrightnessReason mReason = new BrightnessReason();
    public float mRecommendedBrightness;
    public float mThermalMax;
    public long mTime;
    public boolean mWasShortTermModelActive;

    public BrightnessEvent(int i) {
        this.mDisplayId = i;
        reset();
    }

    public BrightnessEvent(BrightnessEvent brightnessEvent) {
        copyFrom(brightnessEvent);
    }

    public final void copyFrom(BrightnessEvent brightnessEvent) {
        this.mReason.set(brightnessEvent.mReason);
        this.mDisplayId = brightnessEvent.mDisplayId;
        this.mPhysicalDisplayId = brightnessEvent.mPhysicalDisplayId;
        this.mDisplayState = brightnessEvent.mDisplayState;
        this.mDisplayPolicy = brightnessEvent.mDisplayPolicy;
        this.mTime = brightnessEvent.mTime;
        this.mLux = brightnessEvent.mLux;
        this.mPreThresholdLux = brightnessEvent.mPreThresholdLux;
        this.mInitialBrightness = brightnessEvent.mInitialBrightness;
        this.mBrightness = brightnessEvent.mBrightness;
        this.mRecommendedBrightness = brightnessEvent.mRecommendedBrightness;
        this.mPreThresholdBrightness = brightnessEvent.mPreThresholdBrightness;
        this.mHbmMode = brightnessEvent.mHbmMode;
        this.mHbmMax = brightnessEvent.mHbmMax;
        this.mRbcStrength = brightnessEvent.mRbcStrength;
        this.mThermalMax = brightnessEvent.mThermalMax;
        this.mPowerFactor = brightnessEvent.mPowerFactor;
        this.mWasShortTermModelActive = brightnessEvent.mWasShortTermModelActive;
        this.mFlags = brightnessEvent.mFlags;
        this.mAdjustmentFlags = brightnessEvent.mAdjustmentFlags;
        this.mAutomaticBrightnessEnabled = brightnessEvent.mAutomaticBrightnessEnabled;
        this.mDisplayBrightnessStrategyName = brightnessEvent.mDisplayBrightnessStrategyName;
        this.mAutoBrightnessMode = brightnessEvent.mAutoBrightnessMode;
    }

    public final boolean equalsMainData(BrightnessEvent brightnessEvent) {
        return this.mReason.equals(brightnessEvent.mReason)
                && this.mDisplayId == brightnessEvent.mDisplayId
                && this.mPhysicalDisplayId.equals(brightnessEvent.mPhysicalDisplayId)
                && this.mDisplayState == brightnessEvent.mDisplayState
                && this.mDisplayPolicy == brightnessEvent.mDisplayPolicy
                && Float.floatToRawIntBits(this.mLux)
                        == Float.floatToRawIntBits(brightnessEvent.mLux)
                && Float.floatToRawIntBits(this.mPreThresholdLux)
                        == Float.floatToRawIntBits(brightnessEvent.mPreThresholdLux)
                && Float.floatToRawIntBits(this.mBrightness)
                        == Float.floatToRawIntBits(brightnessEvent.mBrightness)
                && Float.floatToRawIntBits(this.mRecommendedBrightness)
                        == Float.floatToRawIntBits(brightnessEvent.mRecommendedBrightness)
                && Float.floatToRawIntBits(this.mPreThresholdBrightness)
                        == Float.floatToRawIntBits(brightnessEvent.mPreThresholdBrightness)
                && this.mHbmMode == brightnessEvent.mHbmMode
                && Float.floatToRawIntBits(this.mHbmMax)
                        == Float.floatToRawIntBits(brightnessEvent.mHbmMax)
                && this.mRbcStrength == brightnessEvent.mRbcStrength
                && Float.floatToRawIntBits(this.mThermalMax)
                        == Float.floatToRawIntBits(brightnessEvent.mThermalMax)
                && Float.floatToRawIntBits(this.mPowerFactor)
                        == Float.floatToRawIntBits(brightnessEvent.mPowerFactor)
                && this.mWasShortTermModelActive == brightnessEvent.mWasShortTermModelActive
                && this.mFlags == brightnessEvent.mFlags
                && this.mAdjustmentFlags == brightnessEvent.mAdjustmentFlags
                && this.mAutomaticBrightnessEnabled == brightnessEvent.mAutomaticBrightnessEnabled
                && this.mDisplayBrightnessStrategyName.equals(
                        brightnessEvent.mDisplayBrightnessStrategyName)
                && this.mAutoBrightnessMode == brightnessEvent.mAutoBrightnessMode;
    }

    public String flagsToString() {
        StringBuilder sb = new StringBuilder();
        sb.append((this.mFlags & 8) != 0 ? "user_set " : "");
        sb.append((this.mFlags & 1) != 0 ? "rbc " : "");
        sb.append((this.mFlags & 2) != 0 ? "invalid_lux " : "");
        sb.append((this.mFlags & 4) != 0 ? "doze_scale " : "");
        sb.append((this.mFlags & 32) != 0 ? "low_power_mode " : "");
        return sb.toString();
    }

    public final void reset() {
        this.mReason = new BrightnessReason();
        this.mTime = SystemClock.uptimeMillis();
        this.mPhysicalDisplayId = "";
        this.mDisplayState = 0;
        this.mDisplayPolicy = 0;
        this.mLux = FullScreenMagnificationGestureHandler.MAX_SCALE;
        this.mPreThresholdLux = FullScreenMagnificationGestureHandler.MAX_SCALE;
        this.mInitialBrightness = Float.NaN;
        this.mBrightness = Float.NaN;
        this.mRecommendedBrightness = Float.NaN;
        this.mPreThresholdBrightness = Float.NaN;
        this.mHbmMode = 0;
        this.mHbmMax = 1.0f;
        this.mRbcStrength = 0;
        this.mThermalMax = 1.0f;
        this.mPowerFactor = 1.0f;
        this.mWasShortTermModelActive = false;
        this.mFlags = 0;
        this.mAdjustmentFlags = 0;
        this.mAutomaticBrightnessEnabled = true;
        this.mDisplayBrightnessStrategyName = "";
        this.mAutoBrightnessMode = 0;
    }

    public final void setAdjustmentFlags(int i) {
        this.mAdjustmentFlags = i;
    }

    public final void setAutomaticBrightnessEnabled(boolean z) {
        this.mAutomaticBrightnessEnabled = z;
    }

    public final void setBrightness(float f) {
        this.mBrightness = f;
    }

    public final void setDisplayBrightnessStrategyName(String str) {
        this.mDisplayBrightnessStrategyName = str;
    }

    public final void setDisplayPolicy(int i) {
        this.mDisplayPolicy = i;
    }

    public final void setDisplayState(int i) {
        this.mDisplayState = i;
    }

    public final void setHbmMax(float f) {
        this.mHbmMax = f;
    }

    public final void setHbmMode(int i) {
        this.mHbmMode = i;
    }

    public final void setInitialBrightness(float f) {
        this.mInitialBrightness = f;
    }

    public final void setPhysicalDisplayId(String str) {
        this.mPhysicalDisplayId = str;
    }

    public final void setPowerFactor(float f) {
        this.mPowerFactor = f;
    }

    public final void setRbcStrength() {
        this.mRbcStrength = -1;
    }

    public final void setReason(BrightnessReason brightnessReason) {
        this.mReason = brightnessReason;
    }

    public final void setTime(long j) {
        this.mTime = j;
    }

    public final void setWasShortTermModelActive(boolean z) {
        this.mWasShortTermModelActive = z;
    }

    public final String toString() {
        return toString(true);
    }

    public final String toString(boolean z) {
        String str;
        StringBuilder sb = new StringBuilder();
        if (z) {
            str = FORMAT.format(new Date(this.mTime)) + " - ";
        } else {
            str = "";
        }
        sb.append(str);
        sb.append("BrightnessEvent: disp=");
        sb.append(this.mDisplayId);
        sb.append(", physDisp=");
        sb.append(this.mPhysicalDisplayId);
        sb.append(", displayState=");
        sb.append(Display.stateToString(this.mDisplayState));
        sb.append(", displayPolicy=");
        sb.append(DisplayManagerInternal.DisplayPowerRequest.policyToString(this.mDisplayPolicy));
        sb.append(", brt=");
        sb.append(this.mBrightness);
        sb.append((this.mFlags & 8) != 0 ? "(user_set)" : "");
        sb.append(", initBrt=");
        sb.append(this.mInitialBrightness);
        sb.append(", rcmdBrt=");
        sb.append(this.mRecommendedBrightness);
        sb.append(", preBrt=");
        sb.append(this.mPreThresholdBrightness);
        sb.append(", lux=");
        sb.append(this.mLux);
        sb.append(", preLux=");
        sb.append(this.mPreThresholdLux);
        sb.append(", hbmMax=");
        sb.append(this.mHbmMax);
        sb.append(", hbmMode=");
        sb.append(BrightnessInfo.hbmToString(this.mHbmMode));
        sb.append(", rbcStrength=");
        sb.append(this.mRbcStrength);
        sb.append(", thrmMax=");
        sb.append(this.mThermalMax);
        sb.append(", powerFactor=");
        sb.append(this.mPowerFactor);
        sb.append(", wasShortTermModelActive=");
        sb.append(this.mWasShortTermModelActive);
        sb.append(", flags=");
        sb.append(flagsToString());
        sb.append(", reason=");
        sb.append(this.mReason.toString(this.mAdjustmentFlags));
        sb.append(", autoBrightness=");
        sb.append(this.mAutomaticBrightnessEnabled);
        sb.append(", strategy=");
        sb.append(this.mDisplayBrightnessStrategyName);
        sb.append(", autoBrightnessMode=");
        sb.append(
                DisplayBrightnessMappingConfig.autoBrightnessModeToString(
                        this.mAutoBrightnessMode));
        return sb.toString();
    }
}
