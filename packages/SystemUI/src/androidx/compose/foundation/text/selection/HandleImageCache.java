package androidx.compose.foundation.text.selection;

import androidx.compose.ui.graphics.AndroidCanvas;
import androidx.compose.ui.graphics.AndroidImageBitmap;
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope;

/* loaded from: classes.dex */
final class HandleImageCache {
    public static final HandleImageCache INSTANCE = new HandleImageCache();
    public static AndroidCanvas canvas;
    public static CanvasDrawScope canvasDrawScope;
    public static AndroidImageBitmap imageBitmap;

    private HandleImageCache() {
    }
}
