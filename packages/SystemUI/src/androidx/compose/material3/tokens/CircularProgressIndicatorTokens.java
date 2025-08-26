package androidx.compose.material3.tokens;

import androidx.compose.ui.unit.Dp;

/* loaded from: classes.dex */
public final class CircularProgressIndicatorTokens {
    public static final CircularProgressIndicatorTokens INSTANCE = new CircularProgressIndicatorTokens();
    public static final float Size;
    public static final float TrackActiveSpace;
    public static final float TrackThickness;

    static {
        float f = (float) 4.0d;
        Dp.Companion companion = Dp.Companion;
        Size = (float) 40.0d;
        TrackActiveSpace = f;
        TrackThickness = f;
    }

    private CircularProgressIndicatorTokens() {
    }
}
