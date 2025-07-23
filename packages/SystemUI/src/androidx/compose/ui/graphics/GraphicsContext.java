package androidx.compose.ui.graphics;

import androidx.compose.ui.graphics.layer.GraphicsLayer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface GraphicsContext {
    GraphicsLayer createGraphicsLayer();

    void releaseGraphicsLayer(GraphicsLayer graphicsLayer);
}
