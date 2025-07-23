package androidx.compose.foundation.text.selection;

import androidx.compose.ui.graphics.AndroidCanvas;
import androidx.compose.ui.graphics.AndroidImageBitmap;
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class HandleImageCache {
    public static final HandleImageCache INSTANCE = new HandleImageCache();
    public static AndroidCanvas canvas;
    public static CanvasDrawScope canvasDrawScope;
    public static AndroidImageBitmap imageBitmap;

    private HandleImageCache() {
    }
}
