package com.android.server.display.brightness;

import android.hardware.display.DisplayManagerInternal;

import com.android.server.display.brightness.strategy.DisplayBrightnessStrategy;

import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class StrategySelectionNotifyRequest {
    public final boolean mAllowAutoBrightnessWhileDozingConfig;
    public final DisplayManagerInternal.DisplayPowerRequest mDisplayPowerRequest;
    public final boolean mIsAutoBrightnessEnabled;
    public final float mLastUserSetScreenBrightness;
    public final DisplayBrightnessStrategy mSelectedDisplayBrightnessStrategy;
    public final int mTargetDisplayState;
    public final boolean mUserSetBrightnessChanged;

    public StrategySelectionNotifyRequest(
            DisplayManagerInternal.DisplayPowerRequest displayPowerRequest,
            int i,
            DisplayBrightnessStrategy displayBrightnessStrategy,
            float f,
            boolean z,
            boolean z2,
            boolean z3) {
        this.mDisplayPowerRequest = displayPowerRequest;
        this.mTargetDisplayState = i;
        this.mSelectedDisplayBrightnessStrategy = displayBrightnessStrategy;
        this.mLastUserSetScreenBrightness = f;
        this.mUserSetBrightnessChanged = z;
        this.mAllowAutoBrightnessWhileDozingConfig = z2;
        this.mIsAutoBrightnessEnabled = z3;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof StrategySelectionNotifyRequest)) {
            return false;
        }
        StrategySelectionNotifyRequest strategySelectionNotifyRequest =
                (StrategySelectionNotifyRequest) obj;
        return strategySelectionNotifyRequest.mSelectedDisplayBrightnessStrategy
                        == this.mSelectedDisplayBrightnessStrategy
                && Objects.equals(
                        this.mDisplayPowerRequest,
                        strategySelectionNotifyRequest.mDisplayPowerRequest)
                && this.mTargetDisplayState == strategySelectionNotifyRequest.mTargetDisplayState
                && this.mUserSetBrightnessChanged
                        == strategySelectionNotifyRequest.mUserSetBrightnessChanged
                && this.mLastUserSetScreenBrightness
                        == strategySelectionNotifyRequest.mLastUserSetScreenBrightness
                && this.mAllowAutoBrightnessWhileDozingConfig
                        == strategySelectionNotifyRequest.mAllowAutoBrightnessWhileDozingConfig
                && this.mIsAutoBrightnessEnabled
                        == strategySelectionNotifyRequest.mIsAutoBrightnessEnabled;
    }

    public final int hashCode() {
        return Objects.hash(
                this.mSelectedDisplayBrightnessStrategy,
                this.mDisplayPowerRequest,
                Integer.valueOf(this.mTargetDisplayState),
                Boolean.valueOf(this.mUserSetBrightnessChanged),
                Float.valueOf(this.mLastUserSetScreenBrightness),
                Boolean.valueOf(this.mAllowAutoBrightnessWhileDozingConfig),
                Boolean.valueOf(this.mIsAutoBrightnessEnabled));
    }

    public final String toString() {
        return "StrategySelectionNotifyRequest: mDisplayPowerRequest="
                + this.mDisplayPowerRequest
                + " mTargetDisplayState="
                + this.mTargetDisplayState
                + " mSelectedDisplayBrightnessStrategy="
                + this.mSelectedDisplayBrightnessStrategy
                + " mLastUserSetScreenBrightness="
                + this.mLastUserSetScreenBrightness
                + " mUserSetBrightnessChanged="
                + this.mUserSetBrightnessChanged
                + " mAllowAutoBrightnessWhileDozingConfig="
                + this.mAllowAutoBrightnessWhileDozingConfig
                + " mIsAutoBrightnessEnabled="
                + this.mIsAutoBrightnessEnabled;
    }
}
