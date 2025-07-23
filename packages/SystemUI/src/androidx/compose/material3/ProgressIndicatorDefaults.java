package androidx.compose.material3;

import androidx.compose.animation.core.SpringSpec;
import androidx.compose.material3.tokens.CircularProgressIndicatorTokens;
import androidx.compose.material3.tokens.LinearProgressIndicatorTokens;
import androidx.compose.ui.graphics.StrokeCap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ProgressIndicatorDefaults {
    public static final int CircularIndeterminateStrokeCap;
    public static final float CircularIndicatorTrackGapSize;
    public static final float CircularStrokeWidth;
    public static final ProgressIndicatorDefaults INSTANCE = new ProgressIndicatorDefaults();

    static {
        CircularProgressIndicatorTokens circularProgressIndicatorTokens = CircularProgressIndicatorTokens.INSTANCE;
        circularProgressIndicatorTokens.getClass();
        CircularStrokeWidth = CircularProgressIndicatorTokens.TrackThickness;
        StrokeCap.Companion companion = StrokeCap.Companion;
        companion.getClass();
        companion.getClass();
        companion.getClass();
        CircularIndeterminateStrokeCap = StrokeCap.Round;
        LinearProgressIndicatorTokens linearProgressIndicatorTokens = LinearProgressIndicatorTokens.INSTANCE;
        linearProgressIndicatorTokens.getClass();
        linearProgressIndicatorTokens.getClass();
        circularProgressIndicatorTokens.getClass();
        CircularIndicatorTrackGapSize = CircularProgressIndicatorTokens.TrackActiveSpace;
        new SpringSpec(1.0f, 50.0f, Float.valueOf(0.001f));
    }

    private ProgressIndicatorDefaults() {
    }
}
