package androidx.compose.foundation.lazy.layout;

import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.animation.core.SpringSpec;
import androidx.compose.animation.core.VisibilityThresholdsKt;
import androidx.compose.ui.unit.IntOffset;

/* loaded from: classes.dex */
public abstract class LazyLayoutItemAnimationKt {
    public static final SpringSpec InterruptionSpec;

    static {
        IntOffset.Companion companion = IntOffset.Companion;
        InterruptionSpec = AnimationSpecKt.spring$default(0.0f, 400.0f, IntOffset.m849boximpl(VisibilityThresholdsKt.getVisibilityThreshold()), 1);
    }
}
