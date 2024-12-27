package com.android.systemui.surfaceeffects.turbulencenoise;

import com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseController;

public final class TurbulenceNoiseController$playEaseOutAnimation$1 implements Runnable {
    public final /* synthetic */ TurbulenceNoiseController this$0;

    public TurbulenceNoiseController$playEaseOutAnimation$1(TurbulenceNoiseController turbulenceNoiseController) {
        this.this$0 = turbulenceNoiseController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.this$0.setState(TurbulenceNoiseController.Companion.AnimationState.NOT_PLAYING);
    }
}
