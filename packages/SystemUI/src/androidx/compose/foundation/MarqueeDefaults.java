package androidx.compose.foundation;

import androidx.compose.ui.unit.Dp;

/* loaded from: classes.dex */
public final class MarqueeDefaults {
    public static final MarqueeDefaults INSTANCE = new MarqueeDefaults();
    public static final int Iterations = 3;
    public static final int RepeatDelayMillis = 1200;
    public static final MarqueeSpacing$Companion$$ExternalSyntheticLambda0 Spacing;
    public static final float Velocity;

    static {
        MarqueeSpacing.Companion.getClass();
        Spacing = new MarqueeSpacing$Companion$$ExternalSyntheticLambda0();
        Dp.Companion companion = Dp.Companion;
        Velocity = 30;
    }

    private MarqueeDefaults() {
    }
}
