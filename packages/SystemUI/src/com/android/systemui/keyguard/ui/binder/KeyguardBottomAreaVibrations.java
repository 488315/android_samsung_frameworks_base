package com.android.systemui.keyguard.ui.binder;

import android.os.VibrationEffect;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        VibrationEffect.Composition startComposition = VibrationEffect.startComposition();
        int m3437getInWholeMillisecondsimpl = (int) (Duration.m3437getInWholeMillisecondsimpl(r0) / (2 * 5.0f));
        int i = ((int) 5.0f) * 2;
        for (int i2 = 0; i2 < i; i2++) {
            startComposition.addPrimitive(7, 0.3f, m3437getInWholeMillisecondsimpl);
        }
        Shake = startComposition.compose();
        Activated = VibrationEffect.startComposition().addPrimitive(7, 0.6f, 0).addPrimitive(4, 0.1f, 0).compose();
        Deactivated = VibrationEffect.startComposition().addPrimitive(7, 0.6f, 0).addPrimitive(6, 0.1f, 0).compose();
    }

    private KeyguardBottomAreaVibrations() {
    }
}
