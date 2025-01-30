package com.android.systemui.keyguard.p009ui.binder;

import android.os.VibrationEffect;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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
        int m2863getInWholeMillisecondsimpl = (int) (Duration.m2863getInWholeMillisecondsimpl(r0) / 10.0f);
        for (int i = 0; i < 10; i++) {
            startComposition.addPrimitive(7, 0.3f, m2863getInWholeMillisecondsimpl);
        }
        Shake = startComposition.compose();
        Activated = VibrationEffect.startComposition().addPrimitive(7, 0.6f, 0).addPrimitive(4, 0.1f, 0).compose();
        Deactivated = VibrationEffect.startComposition().addPrimitive(7, 0.6f, 0).addPrimitive(6, 0.1f, 0).compose();
    }

    private KeyguardBottomAreaVibrations() {
    }
}
