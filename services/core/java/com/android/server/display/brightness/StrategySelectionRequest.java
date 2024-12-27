package com.android.server.display.brightness;

import android.hardware.display.DisplayManagerInternal;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class StrategySelectionRequest {
    public DisplayManagerInternal.DisplayOffloadSession mDisplayOffloadSession;
    public DisplayManagerInternal.DisplayPowerRequest mDisplayPowerRequest;
    public float mLastUserSetScreenBrightness;
    public int mTargetDisplayState;
    public boolean mUserSetBrightnessChanged;

    public final boolean equals(Object obj) {
        if (!(obj instanceof StrategySelectionRequest)) {
            return false;
        }
        StrategySelectionRequest strategySelectionRequest = (StrategySelectionRequest) obj;
        return Objects.equals(this.mDisplayPowerRequest, strategySelectionRequest.mDisplayPowerRequest) && this.mTargetDisplayState == strategySelectionRequest.mTargetDisplayState && this.mLastUserSetScreenBrightness == strategySelectionRequest.mLastUserSetScreenBrightness && this.mUserSetBrightnessChanged == strategySelectionRequest.mUserSetBrightnessChanged && this.mDisplayOffloadSession.equals(strategySelectionRequest.mDisplayOffloadSession);
    }

    public final int hashCode() {
        return Objects.hash(this.mDisplayPowerRequest, Integer.valueOf(this.mTargetDisplayState), Float.valueOf(this.mLastUserSetScreenBrightness), Boolean.valueOf(this.mUserSetBrightnessChanged), this.mDisplayOffloadSession);
    }
}
