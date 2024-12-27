package com.android.systemui.surfaceeffects.turbulencenoise;

import com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseController;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
