package androidx.compose.ui.graphics.vector;

import androidx.compose.ui.graphics.AndroidCanvas;
import androidx.compose.ui.graphics.AndroidImageBitmap;
import androidx.compose.ui.graphics.ImageBitmapConfig;
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope;
import androidx.compose.ui.unit.IntSize;
import androidx.compose.ui.unit.LayoutDirection;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class DrawCache {
    public final CanvasDrawScope cacheScope;
    public AndroidCanvas cachedCanvas;
    public int config;
    public AndroidImageBitmap mCachedImage;
    public long size;

    public DrawCache() {
        LayoutDirection layoutDirection = LayoutDirection.Ltr;
        IntSize.Companion.getClass();
        this.size = 0L;
        ImageBitmapConfig.Companion.getClass();
        this.config = 0;
        this.cacheScope = new CanvasDrawScope();
    }
}
