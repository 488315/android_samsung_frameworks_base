package com.android.server.display.brightness.clamper;

import android.R;
import android.content.Context;
import android.content.res.Resources;
import android.hardware.display.DisplayManagerInternal;
import android.os.PowerManager;
import android.util.IndentingPrintWriter;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.am.KillPolicyManager$$ExternalSyntheticOutline0;
import com.android.server.display.brightness.BrightnessUtils;
import java.io.PrintWriter;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class DisplayDimModifier extends BrightnessModifier {
    public final float mScreenBrightnessDimConfig;
    public final float mScreenBrightnessMinimumDimAmount;

    public DisplayDimModifier(Context context) {
        PowerManager powerManager = (PowerManager) context.getSystemService(PowerManager.class);
        Objects.requireNonNull(powerManager);
        Resources resources = context.getResources();
        this.mScreenBrightnessDimConfig = BrightnessUtils.clampAbsoluteBrightness(powerManager.getBrightnessConstraint(3));
        this.mScreenBrightnessMinimumDimAmount = resources.getFloat(R.dimen.conversation_avatar_size_group_expanded);
    }

    @Override // com.android.server.display.brightness.clamper.BrightnessModifier
    public final void dump(PrintWriter printWriter) {
        StringBuilder m = KillPolicyManager$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(printWriter, "DisplayDimModifier:", "  mScreenBrightnessDimConfig="), this.mScreenBrightnessDimConfig, printWriter, "  mScreenBrightnessMinimumDimAmount=");
        m.append(this.mScreenBrightnessMinimumDimAmount);
        printWriter.println(m.toString());
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "    ");
        indentingPrintWriter.println("BrightnessModifier:");
        indentingPrintWriter.println("  mApplied=" + this.mApplied);
    }

    @Override // com.android.server.display.brightness.clamper.BrightnessModifier
    public final float getBrightnessAdjusted(float f, DisplayManagerInternal.DisplayPowerRequest displayPowerRequest) {
        return Math.max(Math.min(f - this.mScreenBrightnessMinimumDimAmount, this.mScreenBrightnessDimConfig), FullScreenMagnificationGestureHandler.MAX_SCALE);
    }

    @Override // com.android.server.display.brightness.clamper.BrightnessModifier
    public final int getModifier() {
        return 1;
    }

    @Override // com.android.server.display.brightness.clamper.BrightnessModifier
    public final void setAmbientLux(float f) {
    }

    @Override // com.android.server.display.brightness.clamper.BrightnessModifier
    public final boolean shouldApply(DisplayManagerInternal.DisplayPowerRequest displayPowerRequest) {
        return displayPowerRequest.policy == 2;
    }

    @Override // com.android.server.display.brightness.clamper.BrightnessModifier
    public final boolean shouldListenToLightSensor() {
        return false;
    }
}
