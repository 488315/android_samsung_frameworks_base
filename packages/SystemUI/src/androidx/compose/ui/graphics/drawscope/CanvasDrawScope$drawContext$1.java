package androidx.compose.ui.graphics.drawscope;

import androidx.compose.ui.graphics.Canvas;
import androidx.compose.ui.graphics.layer.GraphicsLayer;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.LayoutDirection;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class CanvasDrawScope$drawContext$1 implements DrawContext {
    public GraphicsLayer graphicsLayer;
    public final /* synthetic */ CanvasDrawScope this$0;
    public final CanvasDrawScopeKt$asDrawTransform$1 transform = new CanvasDrawScopeKt$asDrawTransform$1(this);

    public CanvasDrawScope$drawContext$1(CanvasDrawScope canvasDrawScope) {
        this.this$0 = canvasDrawScope;
    }

    public final Canvas getCanvas() {
        return this.this$0.drawParams.canvas;
    }

    public final Density getDensity() {
        return this.this$0.drawParams.density;
    }

    public final LayoutDirection getLayoutDirection() {
        return this.this$0.drawParams.layoutDirection;
    }

    /* renamed from: getSize-NH-jbRc, reason: not valid java name */
    public final long m526getSizeNHjbRc() {
        return this.this$0.drawParams.size;
    }

    public final void setCanvas(Canvas canvas) {
        this.this$0.drawParams.canvas = canvas;
    }

    public final void setDensity(Density density) {
        this.this$0.drawParams.density = density;
    }

    public final void setLayoutDirection(LayoutDirection layoutDirection) {
        this.this$0.drawParams.layoutDirection = layoutDirection;
    }

    /* renamed from: setSize-uvyYCjk, reason: not valid java name */
    public final void m527setSizeuvyYCjk(long j) {
        this.this$0.drawParams.size = j;
    }
}
