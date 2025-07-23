package androidx.compose.foundation.lazy.layout;

import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.animation.core.SpringSpec;
import androidx.compose.animation.core.VisibilityThresholdsKt;
import androidx.compose.ui.unit.IntOffset;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class LazyLayoutItemAnimationKt {
    public static final SpringSpec InterruptionSpec;

    static {
        IntOffset.Companion companion = IntOffset.Companion;
        InterruptionSpec = AnimationSpecKt.spring$default(0.0f, 400.0f, IntOffset.m847boximpl(VisibilityThresholdsKt.getVisibilityThreshold()), 1);
    }
}
