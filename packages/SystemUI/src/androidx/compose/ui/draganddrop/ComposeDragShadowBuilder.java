package androidx.compose.ui.draganddrop;

import android.graphics.Canvas;
import android.graphics.Point;
import android.view.View;
import androidx.compose.ui.graphics.AndroidCanvas;
import androidx.compose.ui.graphics.AndroidCanvas_androidKt;
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.LayoutDirection;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ComposeDragShadowBuilder extends View.DragShadowBuilder {
    public final long decorationSize;
    public final Density density;
    public final Function1 drawDragDecoration;

    public /* synthetic */ ComposeDragShadowBuilder(Density density, long j, Function1 function1, DefaultConstructorMarker defaultConstructorMarker) {
        this(density, j, function1);
    }

    @Override // android.view.View.DragShadowBuilder
    public final void onDrawShadow(Canvas canvas) {
        CanvasDrawScope canvasDrawScope = new CanvasDrawScope();
        Density density = this.density;
        long j = this.decorationSize;
        LayoutDirection layoutDirection = LayoutDirection.Ltr;
        Canvas canvas2 = AndroidCanvas_androidKt.EmptyCanvas;
        AndroidCanvas androidCanvas = new AndroidCanvas();
        androidCanvas.internalCanvas = canvas;
        Function1 function1 = this.drawDragDecoration;
        CanvasDrawScope.DrawParams drawParams = canvasDrawScope.drawParams;
        Density density2 = drawParams.density;
        LayoutDirection layoutDirection2 = drawParams.layoutDirection;
        androidx.compose.ui.graphics.Canvas canvas3 = drawParams.canvas;
        long j2 = drawParams.size;
        drawParams.density = density;
        drawParams.layoutDirection = layoutDirection;
        drawParams.canvas = androidCanvas;
        drawParams.size = j;
        androidCanvas.save();
        function1.mo779invoke(canvasDrawScope);
        androidCanvas.restore();
        drawParams.density = density2;
        drawParams.layoutDirection = layoutDirection2;
        drawParams.canvas = canvas3;
        drawParams.size = j2;
    }

    @Override // android.view.View.DragShadowBuilder
    public final void onProvideShadowMetrics(Point point, Point point2) {
        Density density = this.density;
        point.set(density.mo51roundToPx0680j_4(density.mo53toDpu2uoSUM(Float.intBitsToFloat((int) (this.decorationSize >> 32)))), density.mo51roundToPx0680j_4(density.mo53toDpu2uoSUM(Float.intBitsToFloat((int) (this.decorationSize & 4294967295L)))));
        point2.set(point.x / 2, point.y / 2);
    }

    private ComposeDragShadowBuilder(Density density, long j, Function1 function1) {
        this.density = density;
        this.decorationSize = j;
        this.drawDragDecoration = function1;
    }
}
