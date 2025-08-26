package androidx.compose.animation;

import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.animation.core.SpringSpec;
import androidx.compose.animation.core.VisibilityThresholdsKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.draw.ClipKt;
import androidx.compose.ui.unit.IntSize;

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
        SpringSpec springSpecSpring$default = AnimationSpecKt.spring$default(0.0f, 400.0f, IntSize.m861boximpl(VisibilityThresholdsKt.getVisibilityThreshold$3()), 1);
        Modifier modifierClipToBounds = ClipKt.clipToBounds(modifier);
        Alignment.Companion.getClass();
        return modifierClipToBounds.then(new SizeAnimationModifierElement(springSpecSpring$default, Alignment.Companion.TopStart, null));
    }
}
