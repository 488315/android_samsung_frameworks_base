package com.android.server.display.brightness.strategy;

import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.am.KillPolicyManager$$ExternalSyntheticOutline0;
import com.android.server.display.DisplayBrightnessState;
import com.android.server.display.brightness.BrightnessUtils;
import com.android.server.display.brightness.StrategyExecutionRequest;
import com.android.server.display.brightness.StrategySelectionNotifyRequest;

import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class FollowerBrightnessStrategy implements DisplayBrightnessStrategy {
    public float mBrightnessToFollow = Float.NaN;
    public boolean mBrightnessToFollowSlowChange = false;
    public final int mDisplayId;

    public FollowerBrightnessStrategy(int i) {
        this.mDisplayId = i;
    }

    @Override // com.android.server.display.brightness.strategy.DisplayBrightnessStrategy
    public final void dump(PrintWriter printWriter) {
        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                KillPolicyManager$$ExternalSyntheticOutline0.m(
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(
                                        printWriter,
                                        "FollowerBrightnessStrategy:",
                                        "  mDisplayId="),
                                this.mDisplayId,
                                printWriter,
                                "  mBrightnessToFollow:"),
                        this.mBrightnessToFollow,
                        printWriter,
                        "  mBrightnessToFollowSlowChange:"),
                this.mBrightnessToFollowSlowChange,
                printWriter);
    }

    @Override // com.android.server.display.brightness.strategy.DisplayBrightnessStrategy
    public final String getName() {
        return "FollowerBrightnessStrategy";
    }

    @Override // com.android.server.display.brightness.strategy.DisplayBrightnessStrategy
    public final int getReason() {
        return 10;
    }

    @Override // com.android.server.display.brightness.strategy.DisplayBrightnessStrategy
    public final void strategySelectionPostProcessor(
            StrategySelectionNotifyRequest strategySelectionNotifyRequest) {}

    @Override // com.android.server.display.brightness.strategy.DisplayBrightnessStrategy
    public final DisplayBrightnessState updateBrightness(
            StrategyExecutionRequest strategyExecutionRequest) {
        float f = this.mBrightnessToFollow;
        return BrightnessUtils.constructDisplayBrightnessState(
                10, f, f, "FollowerBrightnessStrategy", this.mBrightnessToFollowSlowChange);
    }
}
