package com.android.server.display.brightness.strategy;

import com.android.server.display.DisplayBrightnessState;
import com.android.server.display.brightness.StrategyExecutionRequest;
import com.android.server.display.brightness.StrategySelectionNotifyRequest;

import java.io.PrintWriter;

public interface DisplayBrightnessStrategy {
    void dump(PrintWriter printWriter);

    String getName();

    int getReason();

    void strategySelectionPostProcessor(
            StrategySelectionNotifyRequest strategySelectionNotifyRequest);

    DisplayBrightnessState updateBrightness(StrategyExecutionRequest strategyExecutionRequest);
}
