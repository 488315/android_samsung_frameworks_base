package com.android.systemui.keyguard.ui.binder;

import android.os.VibrationEffect;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;

/* loaded from: classes2.dex */
public final class KeyguardBottomAreaVibrations {
    public static final VibrationEffect Activated;
    public static final VibrationEffect Deactivated;
    public static final KeyguardBottomAreaVibrations INSTANCE = new KeyguardBottomAreaVibrations();
    public static final VibrationEffect Shake;
    public static final float ShakeAnimationCycles;
    public static final long ShakeAnimationDuration;

    static {
        Duration.Companion companion = Duration.Companion;
        ShakeAnimationDuration = DurationKt.toDuration(300, DurationUnit.MILLISECONDS);
        ShakeAnimationCycles = 5.0f;
        VibrationEffect.Composition compositionStartComposition = VibrationEffect.startComposition();
        int iM3456getInWholeMillisecondsimpl = (int) (Duration.m3456getInWholeMillisecondsimpl(r0) / (2 * 5.0f));
        int i = ((int) 5.0f) * 2;
        for (int i2 = 0; i2 < i; i2++) {
            compositionStartComposition.addPrimitive(7, 0.3f, iM3456getInWholeMillisecondsimpl);
        }
        Shake = compositionStartComposition.compose();
        Activated = VibrationEffect.startComposition().addPrimitive(7, 0.6f, 0).addPrimitive(4, 0.1f, 0).compose();
        Deactivated = VibrationEffect.startComposition().addPrimitive(7, 0.6f, 0).addPrimitive(6, 0.1f, 0).compose();
    }

    private KeyguardBottomAreaVibrations() {
    }
}
