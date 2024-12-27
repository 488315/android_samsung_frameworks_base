package com.android.server.display.brightness.strategy;

import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.chimera.AggressivePolicyHandler$$ExternalSyntheticOutline0;
import com.android.server.display.DisplayBrightnessState;
import com.android.server.display.brightness.BrightnessReason;
import com.android.server.display.brightness.StrategyExecutionRequest;
import com.android.server.display.brightness.StrategySelectionNotifyRequest;
import com.android.server.display.feature.DisplayManagerFlags;

import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class OffloadBrightnessStrategy implements DisplayBrightnessStrategy {
    public final DisplayManagerFlags mDisplayManagerFlags;
    public float mOffloadScreenBrightness = Float.NaN;

    public OffloadBrightnessStrategy(DisplayManagerFlags displayManagerFlags) {
        this.mDisplayManagerFlags = displayManagerFlags;
    }

    @Override // com.android.server.display.brightness.strategy.DisplayBrightnessStrategy
    public final void dump(PrintWriter printWriter) {
        AggressivePolicyHandler$$ExternalSyntheticOutline0.m(
                BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(
                        printWriter, "OffloadBrightnessStrategy:", "  mOffloadScreenBrightness:"),
                this.mOffloadScreenBrightness,
                printWriter);
    }

    @Override // com.android.server.display.brightness.strategy.DisplayBrightnessStrategy
    public final String getName() {
        return "OffloadBrightnessStrategy";
    }

    @Override // com.android.server.display.brightness.strategy.DisplayBrightnessStrategy
    public final int getReason() {
        return 11;
    }

    @Override // com.android.server.display.brightness.strategy.DisplayBrightnessStrategy
    public final void strategySelectionPostProcessor(
            StrategySelectionNotifyRequest strategySelectionNotifyRequest) {
        DisplayBrightnessStrategy displayBrightnessStrategy =
                strategySelectionNotifyRequest.mSelectedDisplayBrightnessStrategy;
        if (displayBrightnessStrategy.getName().equals("OffloadBrightnessStrategy")
                || displayBrightnessStrategy.getName().equals("InvalidBrightnessStrategy")) {
            return;
        }
        this.mOffloadScreenBrightness = Float.NaN;
    }

    @Override // com.android.server.display.brightness.strategy.DisplayBrightnessStrategy
    public final DisplayBrightnessState updateBrightness(
            StrategyExecutionRequest strategyExecutionRequest) {
        float f = this.mOffloadScreenBrightness;
        if (this.mDisplayManagerFlags.mRefactorDisplayPowerController.isEnabled()) {
            this.mOffloadScreenBrightness = Float.NaN;
        }
        BrightnessReason brightnessReason = new BrightnessReason();
        DisplayBrightnessState.Builder builder = new DisplayBrightnessState.Builder();
        builder.mBrightness = f;
        builder.mSdrBrightness = f;
        builder.mBrightnessReason = brightnessReason;
        builder.mDisplayBrightnessStrategyName = "OffloadBrightnessStrategy";
        builder.mIsSlowChange = false;
        builder.mShouldUpdateScreenBrightnessSetting = true;
        return new DisplayBrightnessState(builder);
    }
}
