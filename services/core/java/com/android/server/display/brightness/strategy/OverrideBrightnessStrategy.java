package com.android.server.display.brightness.strategy;

import com.android.server.display.DisplayBrightnessState;
import com.android.server.display.brightness.BrightnessUtils;
import com.android.server.display.brightness.StrategyExecutionRequest;
import com.android.server.display.brightness.StrategySelectionNotifyRequest;

import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class OverrideBrightnessStrategy implements DisplayBrightnessStrategy {
    @Override // com.android.server.display.brightness.strategy.DisplayBrightnessStrategy
    public final void dump(PrintWriter printWriter) {}

    @Override // com.android.server.display.brightness.strategy.DisplayBrightnessStrategy
    public final String getName() {
        return "OverrideBrightnessStrategy";
    }

    @Override // com.android.server.display.brightness.strategy.DisplayBrightnessStrategy
    public final int getReason() {
        return 6;
    }

    @Override // com.android.server.display.brightness.strategy.DisplayBrightnessStrategy
    public final void strategySelectionPostProcessor(
            StrategySelectionNotifyRequest strategySelectionNotifyRequest) {}

    @Override // com.android.server.display.brightness.strategy.DisplayBrightnessStrategy
    public final DisplayBrightnessState updateBrightness(
            StrategyExecutionRequest strategyExecutionRequest) {
        float f = strategyExecutionRequest.mDisplayPowerRequest.screenBrightnessOverride;
        return BrightnessUtils.constructDisplayBrightnessState(
                6, f, f, "OverrideBrightnessStrategy", false);
    }
}
