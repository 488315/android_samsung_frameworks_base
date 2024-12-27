package com.android.server.display.brightness.clamper;

import android.hardware.display.DisplayManagerInternal;
import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class BrightnessModifier {
    public boolean mApplied = false;

    public abstract void dump(PrintWriter printWriter);

    public abstract float getBrightnessAdjusted(float f, DisplayManagerInternal.DisplayPowerRequest displayPowerRequest);

    public abstract int getModifier();

    public abstract void setAmbientLux(float f);

    public abstract boolean shouldApply(DisplayManagerInternal.DisplayPowerRequest displayPowerRequest);

    public boolean shouldApply(DisplayManagerInternal.DisplayPowerRequest displayPowerRequest, BrightnessModifierRequest brightnessModifierRequest) {
        return shouldApply(displayPowerRequest);
    }

    public abstract boolean shouldListenToLightSensor();

    public void stop() {
    }
}
