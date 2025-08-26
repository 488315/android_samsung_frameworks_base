package androidx.compose.foundation;

import androidx.compose.foundation.MarqueeAnimationMode;
import androidx.compose.ui.Modifier;

/* loaded from: classes.dex */
public abstract class BasicMarqueeKt {
    /* renamed from: basicMarquee-1Mj1MLw$default, reason: not valid java name */
    public static Modifier m27basicMarquee1Mj1MLw$default(Modifier modifier, int i, int i2) {
        int i3;
        if ((i2 & 1) != 0) {
            MarqueeDefaults.INSTANCE.getClass();
            i = MarqueeDefaults.Iterations;
        }
        int i4 = i;
        MarqueeAnimationMode.Companion companion = MarqueeAnimationMode.Companion;
        companion.getClass();
        MarqueeDefaults marqueeDefaults = MarqueeDefaults.INSTANCE;
        marqueeDefaults.getClass();
        int i5 = MarqueeDefaults.RepeatDelayMillis;
        if ((i2 & 8) != 0) {
            companion.getClass();
            i3 = i5;
        } else {
            i3 = 2000;
        }
        marqueeDefaults.getClass();
        MarqueeSpacing$Companion$$ExternalSyntheticLambda0 marqueeSpacing$Companion$$ExternalSyntheticLambda0 = MarqueeDefaults.Spacing;
        marqueeDefaults.getClass();
        return modifier.then(new MarqueeModifierElement(i4, 0, i5, i3, marqueeSpacing$Companion$$ExternalSyntheticLambda0, MarqueeDefaults.Velocity, null));
    }
}
