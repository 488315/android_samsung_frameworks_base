package androidx.compose.material3;

import androidx.compose.material3.tokens.DividerTokens;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;

/* loaded from: classes.dex */
public final class DividerDefaults {
    public static final DividerDefaults INSTANCE = new DividerDefaults();
    public static final float Thickness;

    static {
        DividerTokens.INSTANCE.getClass();
        Thickness = DividerTokens.Thickness;
    }

    private DividerDefaults() {
    }

    public static long getColor(Composer composer) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.material3.DividerDefaults.<get-color> (Divider.kt:118)");
        }
        DividerTokens.INSTANCE.getClass();
        long value = ColorSchemeKt.getValue(DividerTokens.Color, composer);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return value;
    }
}
