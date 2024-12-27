package com.android.server.display.brightness.clamper;

import java.util.Objects;

public final class BrightnessModifierRequest {
    public final boolean mAwakenFromDozingInAutoBrightness;
    public final int mDisplayState;

    public BrightnessModifierRequest(int i, boolean z) {
        this.mDisplayState = i;
        this.mAwakenFromDozingInAutoBrightness = z;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || BrightnessModifierRequest.class != obj.getClass()) {
            return false;
        }
        BrightnessModifierRequest brightnessModifierRequest = (BrightnessModifierRequest) obj;
        return this.mDisplayState == brightnessModifierRequest.mDisplayState
                && this.mAwakenFromDozingInAutoBrightness
                        == brightnessModifierRequest.mAwakenFromDozingInAutoBrightness;
    }

    public final int hashCode() {
        return Objects.hash(
                Integer.valueOf(this.mDisplayState),
                Boolean.valueOf(this.mAwakenFromDozingInAutoBrightness));
    }
}
