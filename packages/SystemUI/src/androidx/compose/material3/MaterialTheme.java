package androidx.compose.material3;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;

/* loaded from: classes.dex */
public final class MaterialTheme {
    public static final MaterialTheme INSTANCE = new MaterialTheme();

    private MaterialTheme() {
    }

    public static ColorScheme getColorScheme(Composer composer) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.material3.MaterialTheme.<get-colorScheme> (MaterialTheme.kt:120)");
        }
        ColorScheme colorScheme = (ColorScheme) ((ComposerImpl) composer).consume(ColorSchemeKt.LocalColorScheme);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return colorScheme;
    }

    public static MotionScheme getMotionScheme(Composer composer) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.material3.MaterialTheme.<get-motionScheme> (MaterialTheme.kt:141)");
        }
        MotionScheme motionScheme = (MotionScheme) ((ComposerImpl) composer).consume(MotionSchemeKt.LocalMotionScheme);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return motionScheme;
    }

    public static Shapes getShapes(Composer composer) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.material3.MaterialTheme.<get-shapes> (MaterialTheme.kt:136)");
        }
        Shapes shapes = (Shapes) ((ComposerImpl) composer).consume(ShapesKt.LocalShapes);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return shapes;
    }

    public static Typography getTypography(Composer composer) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.material3.MaterialTheme.<get-typography> (MaterialTheme.kt:128)");
        }
        Typography typography = (Typography) ((ComposerImpl) composer).consume(TypographyKt.LocalTypography);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return typography;
    }
}
