package androidx.compose.ui.graphics;

import androidx.compose.runtime.RememberObserver;
import androidx.compose.ui.graphics.layer.GraphicsLayer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class GraphicsContextObserver implements RememberObserver {
    public final GraphicsContext graphicsContext;
    public final GraphicsLayer graphicsLayer;

    public GraphicsContextObserver(GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
        this.graphicsLayer = graphicsContext.createGraphicsLayer();
    }

    @Override // androidx.compose.runtime.RememberObserver
    public final void onAbandoned() {
        this.graphicsContext.releaseGraphicsLayer(this.graphicsLayer);
    }

    @Override // androidx.compose.runtime.RememberObserver
    public final void onForgotten() {
        this.graphicsContext.releaseGraphicsLayer(this.graphicsLayer);
    }

    @Override // androidx.compose.runtime.RememberObserver
    public final void onRemembered() {
    }
}
