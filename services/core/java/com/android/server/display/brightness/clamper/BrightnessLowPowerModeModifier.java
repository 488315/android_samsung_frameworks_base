package com.android.server.display.brightness.clamper;

import android.hardware.display.DisplayManagerInternal;
import android.util.IndentingPrintWriter;
import android.view.Display;

import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;

import java.io.PrintWriter;

public final class BrightnessLowPowerModeModifier extends BrightnessModifier {
    @Override // com.android.server.display.brightness.clamper.BrightnessModifier
    public final void dump(PrintWriter printWriter) {
        printWriter.println("BrightnessLowPowerModeModifier:");
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "    ");
        indentingPrintWriter.println("BrightnessModifier:");
        indentingPrintWriter.println("  mApplied=" + this.mApplied);
    }

    @Override // com.android.server.display.brightness.clamper.BrightnessModifier
    public final float getBrightnessAdjusted(
            float f, DisplayManagerInternal.DisplayPowerRequest displayPowerRequest) {
        return Math.max(
                Math.min(displayPowerRequest.screenLowPowerBrightnessFactor, 1.0f) * f,
                FullScreenMagnificationGestureHandler.MAX_SCALE);
    }

    @Override // com.android.server.display.brightness.clamper.BrightnessModifier
    public final int getModifier() {
        return 2;
    }

    @Override // com.android.server.display.brightness.clamper.BrightnessModifier
    public final void setAmbientLux(float f) {}

    @Override // com.android.server.display.brightness.clamper.BrightnessModifier
    public final boolean shouldApply(
            DisplayManagerInternal.DisplayPowerRequest displayPowerRequest) {
        return displayPowerRequest.lowPowerMode;
    }

    @Override // com.android.server.display.brightness.clamper.BrightnessModifier
    public final boolean shouldApply(
            DisplayManagerInternal.DisplayPowerRequest displayPowerRequest,
            BrightnessModifierRequest brightnessModifierRequest) {
        return (!displayPowerRequest.lowPowerMode
                        || brightnessModifierRequest.mAwakenFromDozingInAutoBrightness
                        || Display.isDozeState(brightnessModifierRequest.mDisplayState))
                ? false
                : true;
    }

    @Override // com.android.server.display.brightness.clamper.BrightnessModifier
    public final boolean shouldListenToLightSensor() {
        return false;
    }
}
