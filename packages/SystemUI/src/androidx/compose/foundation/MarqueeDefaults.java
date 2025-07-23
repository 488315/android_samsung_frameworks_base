package androidx.compose.foundation;

import androidx.compose.ui.unit.Dp;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
