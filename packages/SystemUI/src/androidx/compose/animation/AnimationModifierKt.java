package androidx.compose.animation;

import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.animation.core.SpringSpec;
import androidx.compose.animation.core.VisibilityThresholdsKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.draw.ClipKt;
import androidx.compose.ui.unit.IntSize;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class AnimationModifierKt {
    public static final long InvalidSize;

    static {
        long j = Integer.MIN_VALUE;
        IntSize.Companion companion = IntSize.Companion;
        InvalidSize = (j & 4294967295L) | (j << 32);
    }

    public static Modifier animateContentSize$default(Modifier modifier) {
        IntSize.Companion companion = IntSize.Companion;
        SpringSpec spring$default = AnimationSpecKt.spring$default(0.0f, 400.0f, IntSize.m859boximpl(VisibilityThresholdsKt.getVisibilityThreshold$3()), 1);
        Modifier clipToBounds = ClipKt.clipToBounds(modifier);
        Alignment.Companion.getClass();
        return clipToBounds.then(new SizeAnimationModifierElement(spring$default, Alignment.Companion.TopStart, null));
    }
}
