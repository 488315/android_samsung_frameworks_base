package com.android.server.display;

import android.text.TextUtils;

import com.android.server.display.brightness.BrightnessEvent;
import com.android.server.display.brightness.BrightnessReason;

import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class DisplayBrightnessState {
    public final float mBrightness;
    public final int mBrightnessAdjustmentFlag;
    public final BrightnessEvent mBrightnessEvent;
    public final BrightnessReason mBrightnessReason;
    public final float mCustomAnimationRate;
    public final String mDisplayBrightnessStrategyName;
    public final boolean mIsSlowChange;
    public final boolean mIsUserInitiatedChange;
    public final float mMaxBrightness;
    public final float mMinBrightness;
    public final float mSdrBrightness;
    public final boolean mShouldUpdateScreenBrightnessSetting;
    public final boolean mShouldUseAutoBrightness;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Builder {
        public float mBrightness;
        public BrightnessEvent mBrightnessEvent;
        public String mDisplayBrightnessStrategyName;
        public boolean mIsSlowChange;
        public boolean mIsUserInitiatedChange;
        public float mMaxBrightness;
        public float mMinBrightness;
        public float mSdrBrightness;
        public boolean mShouldUpdateScreenBrightnessSetting;
        public boolean mShouldUseAutoBrightness;
        public BrightnessReason mBrightnessReason = new BrightnessReason();
        public float mCustomAnimationRate = -1.0f;
        public int mBrightnessAdjustmentFlag = 0;
    }

    public DisplayBrightnessState(Builder builder) {
        this.mBrightness = builder.mBrightness;
        this.mSdrBrightness = builder.mSdrBrightness;
        this.mBrightnessReason = builder.mBrightnessReason;
        this.mDisplayBrightnessStrategyName = builder.mDisplayBrightnessStrategyName;
        this.mShouldUseAutoBrightness = builder.mShouldUseAutoBrightness;
        this.mIsSlowChange = builder.mIsSlowChange;
        this.mMaxBrightness = builder.mMaxBrightness;
        this.mMinBrightness = builder.mMinBrightness;
        this.mCustomAnimationRate = builder.mCustomAnimationRate;
        this.mShouldUpdateScreenBrightnessSetting = builder.mShouldUpdateScreenBrightnessSetting;
        this.mBrightnessEvent = builder.mBrightnessEvent;
        this.mBrightnessAdjustmentFlag = builder.mBrightnessAdjustmentFlag;
        this.mIsUserInitiatedChange = builder.mIsUserInitiatedChange;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof DisplayBrightnessState)) {
            return false;
        }
        DisplayBrightnessState displayBrightnessState = (DisplayBrightnessState) obj;
        return this.mBrightness == displayBrightnessState.mBrightness
                && this.mSdrBrightness == displayBrightnessState.mSdrBrightness
                && this.mBrightnessReason.equals(displayBrightnessState.mBrightnessReason)
                && TextUtils.equals(
                        this.mDisplayBrightnessStrategyName,
                        displayBrightnessState.mDisplayBrightnessStrategyName)
                && this.mShouldUseAutoBrightness == displayBrightnessState.mShouldUseAutoBrightness
                && this.mIsSlowChange == displayBrightnessState.mIsSlowChange
                && this.mMaxBrightness == displayBrightnessState.mMaxBrightness
                && this.mMinBrightness == displayBrightnessState.mMinBrightness
                && this.mCustomAnimationRate == displayBrightnessState.mCustomAnimationRate
                && this.mShouldUpdateScreenBrightnessSetting
                        == displayBrightnessState.mShouldUpdateScreenBrightnessSetting
                && Objects.equals(this.mBrightnessEvent, displayBrightnessState.mBrightnessEvent)
                && this.mBrightnessAdjustmentFlag
                        == displayBrightnessState.mBrightnessAdjustmentFlag
                && this.mIsUserInitiatedChange == displayBrightnessState.mIsUserInitiatedChange;
    }

    public final int hashCode() {
        return Objects.hash(
                Float.valueOf(this.mBrightness),
                Float.valueOf(this.mSdrBrightness),
                this.mBrightnessReason,
                Boolean.valueOf(this.mShouldUseAutoBrightness),
                Boolean.valueOf(this.mIsSlowChange),
                Float.valueOf(this.mMaxBrightness),
                Float.valueOf(this.mMinBrightness),
                Float.valueOf(this.mCustomAnimationRate),
                Boolean.valueOf(this.mShouldUpdateScreenBrightnessSetting),
                this.mBrightnessEvent,
                Integer.valueOf(this.mBrightnessAdjustmentFlag),
                Boolean.valueOf(this.mIsUserInitiatedChange));
    }

    public final String toString() {
        return "DisplayBrightnessState:\n    brightness:"
                + this.mBrightness
                + "\n    sdrBrightness:"
                + this.mSdrBrightness
                + "\n    brightnessReason:"
                + this.mBrightnessReason
                + "\n    shouldUseAutoBrightness:"
                + this.mShouldUseAutoBrightness
                + "\n    isSlowChange:"
                + this.mIsSlowChange
                + "\n    maxBrightness:"
                + this.mMaxBrightness
                + "\n    minBrightness:"
                + this.mMinBrightness
                + "\n    customAnimationRate:"
                + this.mCustomAnimationRate
                + "\n    shouldUpdateScreenBrightnessSetting:"
                + this.mShouldUpdateScreenBrightnessSetting
                + "\n    mBrightnessEvent:"
                + Objects.toString(this.mBrightnessEvent, "null")
                + "\n    mBrightnessAdjustmentFlag:"
                + this.mBrightnessAdjustmentFlag
                + "\n    mIsUserInitiatedChange:"
                + this.mIsUserInitiatedChange;
    }
}
