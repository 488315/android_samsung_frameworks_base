package androidx.compose.material3;

import androidx.compose.material3.tokens.SnackbarTokens;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.ui.graphics.Shape;

/* loaded from: classes.dex */
public final class SnackbarDefaults {
    public static final SnackbarDefaults INSTANCE = new SnackbarDefaults();

    private SnackbarDefaults() {
    }

    public static long getActionContentColor(Composer composer) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.material3.SnackbarDefaults.<get-actionContentColor> (Snackbar.kt:431)");
        }
        SnackbarTokens.INSTANCE.getClass();
        long value = ColorSchemeKt.getValue(SnackbarTokens.ActionLabelTextColor, composer);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return value;
    }

    public static long getColor(Composer composer) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.material3.SnackbarDefaults.<get-color> (Snackbar.kt:419)");
        }
        SnackbarTokens.INSTANCE.getClass();
        long value = ColorSchemeKt.getValue(SnackbarTokens.ContainerColor, composer);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return value;
    }

    public static long getContentColor(Composer composer) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.material3.SnackbarDefaults.<get-contentColor> (Snackbar.kt:423)");
        }
        SnackbarTokens.INSTANCE.getClass();
        long value = ColorSchemeKt.getValue(SnackbarTokens.SupportingTextColor, composer);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return value;
    }

    public static long getDismissActionContentColor(Composer composer) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.material3.SnackbarDefaults.<get-dismissActionContentColor> (Snackbar.kt:435)");
        }
        SnackbarTokens.INSTANCE.getClass();
        long value = ColorSchemeKt.getValue(SnackbarTokens.IconColor, composer);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return value;
    }

    public static Shape getShape(Composer composer) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.material3.SnackbarDefaults.<get-shape> (Snackbar.kt:415)");
        }
        SnackbarTokens.INSTANCE.getClass();
        Shape value = ShapesKt.getValue(SnackbarTokens.ContainerShape, composer);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return value;
    }
}
