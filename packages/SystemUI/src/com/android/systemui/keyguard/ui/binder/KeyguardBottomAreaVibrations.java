package com.android.systemui.keyguard.ui.binder;

import android.os.VibrationEffect;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class KeyguardBottomAreaVibrations {
    public static final VibrationEffect Activated;
    public static final VibrationEffect Deactivated;
    public static final KeyguardBottomAreaVibrations INSTANCE = new KeyguardBottomAreaVibrations();
    public static final VibrationEffect Shake;
    public static final long ShakeAnimationDuration;

    static {
        Duration.Companion companion = Duration.Companion;
        ShakeAnimationDuration = DurationKt.toDuration(300, DurationUnit.MILLISECONDS);
        VibrationEffect.Composition startComposition = VibrationEffect.startComposition();
        int m2538getInWholeMillisecondsimpl = (int) (Duration.m2538getInWholeMillisecondsimpl(r0) / 10.0f);
        for (int i = 0; i < 10; i++) {
            startComposition.addPrimitive(7, 0.3f, m2538getInWholeMillisecondsimpl);
        }
        Shake = startComposition.compose();
        Activated = VibrationEffect.startComposition().addPrimitive(7, 0.6f, 0).addPrimitive(4, 0.1f, 0).compose();
        Deactivated = VibrationEffect.startComposition().addPrimitive(7, 0.6f, 0).addPrimitive(6, 0.1f, 0).compose();
    }

    private KeyguardBottomAreaVibrations() {
    }
}
