package com.android.server.display.brightness.strategy;

import com.android.server.display.DisplayBrightnessState;
import com.android.server.display.brightness.StrategyExecutionRequest;
import com.android.server.display.brightness.StrategySelectionNotifyRequest;
import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public interface DisplayBrightnessStrategy {
    void dump(PrintWriter printWriter);

    String getName();

    int getReason();

    void strategySelectionPostProcessor(StrategySelectionNotifyRequest strategySelectionNotifyRequest);

    DisplayBrightnessState updateBrightness(StrategyExecutionRequest strategyExecutionRequest);
}
