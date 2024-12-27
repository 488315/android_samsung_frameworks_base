package com.android.server.display.brightness.strategy;

import com.android.server.display.DisplayBrightnessState;
import com.android.server.display.brightness.BrightnessUtils;
import com.android.server.display.brightness.StrategyExecutionRequest;
import com.android.server.display.brightness.StrategySelectionNotifyRequest;

import java.io.PrintWriter;

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
