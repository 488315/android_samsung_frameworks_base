package androidx.compose.ui.node;

import androidx.compose.ui.geometry.MutableRect;
import androidx.compose.ui.graphics.Canvas;
import androidx.compose.ui.graphics.ReusableGraphicsLayerScope;
import androidx.compose.ui.graphics.layer.GraphicsLayer;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface OwnedLayer {
    void destroy();

    void drawLayer(Canvas canvas, GraphicsLayer graphicsLayer);

    /* renamed from: getUnderlyingMatrix-sQKQjiQ, reason: not valid java name */
    float[] mo682getUnderlyingMatrixsQKQjiQ();

    void invalidate();

    /* renamed from: inverseTransform-58bKbWc, reason: not valid java name */
    void mo683inverseTransform58bKbWc(float[] fArr);

    /* renamed from: isInLayer-k-4lQ0M, reason: not valid java name */
    boolean mo684isInLayerk4lQ0M(long j);

    void mapBounds(MutableRect mutableRect, boolean z);

    /* renamed from: mapOffset-8S9VItk, reason: not valid java name */
    long mo685mapOffset8S9VItk(long j, boolean z);

    /* renamed from: move--gyyYBs, reason: not valid java name */
    void mo686movegyyYBs(long j);

    /* renamed from: resize-ozmzZPI, reason: not valid java name */
    void mo687resizeozmzZPI(long j);

    void reuseLayer(Function2 function2, Function0 function0);

    /* renamed from: transform-58bKbWc, reason: not valid java name */
    void mo688transform58bKbWc(float[] fArr);

    void updateDisplayList();

    void updateLayerProperties(ReusableGraphicsLayerScope reusableGraphicsLayerScope);
}
