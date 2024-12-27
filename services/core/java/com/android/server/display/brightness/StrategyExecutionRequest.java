package com.android.server.display.brightness;

import android.hardware.display.DisplayManagerInternal;

import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class StrategyExecutionRequest {
    public final float mCurrentScreenBrightness;
    public final DisplayManagerInternal.DisplayPowerRequest mDisplayPowerRequest;
    public final boolean mUserSetBrightnessChanged;

    public StrategyExecutionRequest(
            DisplayManagerInternal.DisplayPowerRequest displayPowerRequest, float f, boolean z) {
        this.mDisplayPowerRequest = displayPowerRequest;
        this.mCurrentScreenBrightness = f;
        this.mUserSetBrightnessChanged = z;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof StrategyExecutionRequest)) {
            return false;
        }
        StrategyExecutionRequest strategyExecutionRequest = (StrategyExecutionRequest) obj;
        return Objects.equals(
                        this.mDisplayPowerRequest, strategyExecutionRequest.mDisplayPowerRequest)
                && this.mCurrentScreenBrightness
                        == strategyExecutionRequest.mCurrentScreenBrightness
                && this.mUserSetBrightnessChanged
                        == strategyExecutionRequest.mUserSetBrightnessChanged;
    }

    public final int hashCode() {
        return Objects.hash(
                this.mDisplayPowerRequest,
                Float.valueOf(this.mCurrentScreenBrightness),
                Boolean.valueOf(this.mUserSetBrightnessChanged));
    }
}
