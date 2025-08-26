package androidx.compose.material3.tokens;

import androidx.compose.ui.unit.Dp;

/* loaded from: classes.dex */
public final class DividerTokens {
    public static final float Thickness;
    public static final DividerTokens INSTANCE = new DividerTokens();
    public static final ColorSchemeKeyTokens Color = ColorSchemeKeyTokens.OutlineVariant;

    static {
        Dp.Companion companion = Dp.Companion;
        Thickness = (float) 1.0d;
    }

    private DividerTokens() {
    }
}
