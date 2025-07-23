package androidx.compose.ui.node;

import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.autofill.AndroidAutofill$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.graphics.Canvas;
import androidx.compose.ui.graphics.ColorFilter;
import androidx.compose.ui.graphics.ImageBitmap;
import androidx.compose.ui.graphics.Path;
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope;
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope$drawContext$1;
import androidx.compose.ui.graphics.drawscope.ContentDrawScope;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.graphics.drawscope.DrawStyle;
import androidx.compose.ui.graphics.layer.GraphicsLayer;
import androidx.compose.ui.platform.AndroidComposeView;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.IntSizeKt;
import androidx.compose.ui.unit.LayoutDirection;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class LayoutNodeDrawScope implements DrawScope, ContentDrawScope {
    public final CanvasDrawScope canvasDrawScope;
    public DrawModifierNode drawNode;

    public LayoutNodeDrawScope() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    @Override // androidx.compose.ui.graphics.drawscope.DrawScope
    /* renamed from: drawArc-yD3GUKo */
    public final void mo516drawArcyD3GUKo(long j, float f, float f2, long j2, long j3, DrawStyle drawStyle, int i) {
        this.canvasDrawScope.mo516drawArcyD3GUKo(j, f, f2, j2, j3, drawStyle, i);
    }

    @Override // androidx.compose.ui.graphics.drawscope.DrawScope
    /* renamed from: drawCircle-V9BoPsw */
    public final void mo517drawCircleV9BoPsw(Brush brush, float f, long j, float f2, DrawStyle drawStyle, int i) {
        this.canvasDrawScope.mo517drawCircleV9BoPsw(brush, f, j, f2, drawStyle, i);
    }

    @Override // androidx.compose.ui.graphics.drawscope.DrawScope
    /* renamed from: drawCircle-VaOC9Bg */
    public final void mo518drawCircleVaOC9Bg(long j, float f, long j2, float f2, DrawStyle drawStyle, int i) {
        this.canvasDrawScope.mo518drawCircleVaOC9Bg(j, f, j2, f2, drawStyle, i);
    }

    public final void drawContent() {
        CanvasDrawScope canvasDrawScope = this.canvasDrawScope;
        Canvas canvas = canvasDrawScope.drawContext.getCanvas();
        DelegatableNode delegatableNode = this.drawNode;
        if (delegatableNode == null) {
            throw AndroidAutofill$$ExternalSyntheticOutline0.m("Attempting to drawContent for a `null` node. This usually means that a call to ContentDrawScope#drawContent() has been captured inside a lambda, and is being invoked outside of the draw pass. Capturing the scope this way is unsupported - if you are trying to record drawContent with graphicsLayer.record(), make sure you are using the GraphicsLayer#record function within DrawScope, instead of the member function on GraphicsLayer.");
        }
        Modifier.Node node = (Modifier.Node) delegatableNode;
        Modifier.Node node2 = node.node.child;
        if (node2 != null && (node2.aggregateChildKindSet & 4) != 0) {
            while (node2 != null) {
                int i = node2.kindSet;
                if ((i & 2) != 0) {
                    break;
                } else if ((i & 4) != 0) {
                    break;
                } else {
                    node2 = node2.child;
                }
            }
        }
        node2 = null;
        if (node2 == null) {
            NodeCoordinator m632requireCoordinator64DMado = DelegatableNodeKt.m632requireCoordinator64DMado(delegatableNode, 4);
            if (m632requireCoordinator64DMado.getTail() == node.node) {
                m632requireCoordinator64DMado = m632requireCoordinator64DMado.wrapped;
                m632requireCoordinator64DMado.getClass();
            }
            m632requireCoordinator64DMado.performDraw(canvas, canvasDrawScope.drawContext.graphicsLayer);
            return;
        }
        MutableVector mutableVector = null;
        while (node2 != null) {
            if (node2 instanceof DrawModifierNode) {
                DrawModifierNode drawModifierNode = (DrawModifierNode) node2;
                GraphicsLayer graphicsLayer = canvasDrawScope.drawContext.graphicsLayer;
                NodeCoordinator m632requireCoordinator64DMado2 = DelegatableNodeKt.m632requireCoordinator64DMado(drawModifierNode, 4);
                long m864toSizeozmzZPI = IntSizeKt.m864toSizeozmzZPI(m632requireCoordinator64DMado2.measuredSize);
                LayoutNode layoutNode = m632requireCoordinator64DMado2.layoutNode;
                layoutNode.getClass();
                ((AndroidComposeView) LayoutNodeKt.requireOwner(layoutNode)).sharedDrawScope.m644drawDirecteZhPAX0$ui_release(canvas, m864toSizeozmzZPI, m632requireCoordinator64DMado2, drawModifierNode, graphicsLayer);
            } else if ((node2.kindSet & 4) != 0 && (node2 instanceof DelegatingNode)) {
                int i2 = 0;
                for (Modifier.Node node3 = ((DelegatingNode) node2).delegate; node3 != null; node3 = node3.child) {
                    if ((node3.kindSet & 4) != 0) {
                        i2++;
                        if (i2 == 1) {
                            node2 = node3;
                        } else {
                            if (mutableVector == null) {
                                mutableVector = new MutableVector(new Modifier.Node[16], 0);
                            }
                            if (node2 != null) {
                                mutableVector.add(node2);
                                node2 = null;
                            }
                            mutableVector.add(node3);
                        }
                    }
                }
                if (i2 == 1) {
                }
            }
            node2 = DelegatableNodeKt.access$pop(mutableVector);
        }
    }

    /* renamed from: drawDirect-eZhPAX0$ui_release, reason: not valid java name */
    public final void m644drawDirecteZhPAX0$ui_release(Canvas canvas, long j, NodeCoordinator nodeCoordinator, DrawModifierNode drawModifierNode, GraphicsLayer graphicsLayer) {
        DrawModifierNode drawModifierNode2 = this.drawNode;
        this.drawNode = drawModifierNode;
        LayoutDirection layoutDirection = nodeCoordinator.layoutNode.layoutDirection;
        CanvasDrawScope canvasDrawScope = this.canvasDrawScope;
        Density density = canvasDrawScope.drawContext.getDensity();
        CanvasDrawScope$drawContext$1 canvasDrawScope$drawContext$1 = canvasDrawScope.drawContext;
        LayoutDirection layoutDirection2 = canvasDrawScope$drawContext$1.getLayoutDirection();
        Canvas canvas2 = canvasDrawScope$drawContext$1.getCanvas();
        long m526getSizeNHjbRc = canvasDrawScope$drawContext$1.m526getSizeNHjbRc();
        GraphicsLayer graphicsLayer2 = canvasDrawScope$drawContext$1.graphicsLayer;
        canvasDrawScope$drawContext$1.setDensity(nodeCoordinator);
        canvasDrawScope$drawContext$1.setLayoutDirection(layoutDirection);
        canvasDrawScope$drawContext$1.setCanvas(canvas);
        canvasDrawScope$drawContext$1.m527setSizeuvyYCjk(j);
        canvasDrawScope$drawContext$1.graphicsLayer = graphicsLayer;
        canvas.save();
        try {
            drawModifierNode.draw(this);
            canvas.restore();
            canvasDrawScope$drawContext$1.setDensity(density);
            canvasDrawScope$drawContext$1.setLayoutDirection(layoutDirection2);
            canvasDrawScope$drawContext$1.setCanvas(canvas2);
            canvasDrawScope$drawContext$1.m527setSizeuvyYCjk(m526getSizeNHjbRc);
            canvasDrawScope$drawContext$1.graphicsLayer = graphicsLayer2;
            this.drawNode = drawModifierNode2;
        } catch (Throwable th) {
            canvas.restore();
            canvasDrawScope$drawContext$1.setDensity(density);
            canvasDrawScope$drawContext$1.setLayoutDirection(layoutDirection2);
            canvasDrawScope$drawContext$1.setCanvas(canvas2);
            canvasDrawScope$drawContext$1.m527setSizeuvyYCjk(m526getSizeNHjbRc);
            canvasDrawScope$drawContext$1.graphicsLayer = graphicsLayer2;
            throw th;
        }
    }

    @Override // androidx.compose.ui.graphics.drawscope.DrawScope
    /* renamed from: drawImage-AZ2fEMs */
    public final void mo519drawImageAZ2fEMs(ImageBitmap imageBitmap, long j, long j2, long j3, float f, DrawStyle drawStyle, ColorFilter colorFilter, int i, int i2) {
        this.canvasDrawScope.mo519drawImageAZ2fEMs(imageBitmap, j, j2, j3, f, drawStyle, colorFilter, i, i2);
    }

    @Override // androidx.compose.ui.graphics.drawscope.DrawScope
    /* renamed from: drawLine-NGM6Ib0 */
    public final void mo520drawLineNGM6Ib0(long j, long j2, long j3, float f, int i, float f2, int i2) {
        this.canvasDrawScope.mo520drawLineNGM6Ib0(j, j2, j3, f, i, f2, i2);
    }

    @Override // androidx.compose.ui.graphics.drawscope.DrawScope
    /* renamed from: drawPath-GBMwjPU */
    public final void mo521drawPathGBMwjPU(Path path, Brush brush, float f, DrawStyle drawStyle, int i) {
        this.canvasDrawScope.mo521drawPathGBMwjPU(path, brush, f, drawStyle, i);
    }

    @Override // androidx.compose.ui.graphics.drawscope.DrawScope
    /* renamed from: drawPath-LG529CI */
    public final void mo522drawPathLG529CI(Path path, long j, float f, DrawStyle drawStyle, int i) {
        this.canvasDrawScope.mo522drawPathLG529CI(path, j, f, drawStyle, i);
    }

    /* renamed from: drawRect-AsUm42w, reason: not valid java name */
    public final void m645drawRectAsUm42w(Brush brush, long j, long j2, float f, DrawStyle drawStyle, int i) {
        CanvasDrawScope canvasDrawScope = this.canvasDrawScope;
        int i2 = (int) (j >> 32);
        int i3 = (int) (j & 4294967295L);
        canvasDrawScope.drawParams.canvas.drawRect(Float.intBitsToFloat(i2), Float.intBitsToFloat(i3), Float.intBitsToFloat((int) (j2 >> 32)) + Float.intBitsToFloat(i2), Float.intBitsToFloat((int) (j2 & 4294967295L)) + Float.intBitsToFloat(i3), CanvasDrawScope.m514configurePaintswdJneE$default(canvasDrawScope, brush, drawStyle, f, null, i));
    }

    @Override // androidx.compose.ui.graphics.drawscope.DrawScope
    /* renamed from: drawRect-n-J9OG0 */
    public final void mo523drawRectnJ9OG0(long j, long j2, long j3, float f, DrawStyle drawStyle, ColorFilter colorFilter, int i) {
        this.canvasDrawScope.mo523drawRectnJ9OG0(j, j2, j3, f, drawStyle, colorFilter, i);
    }

    @Override // androidx.compose.ui.graphics.drawscope.DrawScope
    /* renamed from: drawRoundRect-ZuiqVtQ */
    public final void mo524drawRoundRectZuiqVtQ(Brush brush, long j, long j2, long j3, float f, DrawStyle drawStyle, int i) {
        this.canvasDrawScope.mo524drawRoundRectZuiqVtQ(brush, j, j2, j3, f, drawStyle, i);
    }

    @Override // androidx.compose.ui.graphics.drawscope.DrawScope
    /* renamed from: drawRoundRect-u-Aw5IA */
    public final void mo525drawRoundRectuAw5IA(long j, long j2, long j3, long j4, DrawStyle drawStyle, float f, int i) {
        this.canvasDrawScope.mo525drawRoundRectuAw5IA(j, j2, j3, j4, drawStyle, f, i);
    }

    @Override // androidx.compose.ui.graphics.drawscope.DrawScope
    /* renamed from: getCenter-F1C5BW0 */
    public final long mo544getCenterF1C5BW0() {
        return this.canvasDrawScope.mo544getCenterF1C5BW0();
    }

    @Override // androidx.compose.ui.unit.Density
    public final float getDensity() {
        return this.canvasDrawScope.getDensity();
    }

    @Override // androidx.compose.ui.graphics.drawscope.DrawScope
    public final CanvasDrawScope$drawContext$1 getDrawContext() {
        return this.canvasDrawScope.drawContext;
    }

    @Override // androidx.compose.ui.unit.FontScaling
    public final float getFontScale() {
        return this.canvasDrawScope.getFontScale();
    }

    @Override // androidx.compose.ui.graphics.drawscope.DrawScope
    public final LayoutDirection getLayoutDirection() {
        return this.canvasDrawScope.drawParams.layoutDirection;
    }

    @Override // androidx.compose.ui.graphics.drawscope.DrawScope
    /* renamed from: getSize-NH-jbRc */
    public final long mo545getSizeNHjbRc() {
        return this.canvasDrawScope.mo545getSizeNHjbRc();
    }

    /* renamed from: record-JVtK1S4, reason: not valid java name */
    public final void m646recordJVtK1S4(long j, GraphicsLayer graphicsLayer, final Function1 function1) {
        final DrawModifierNode drawModifierNode = this.drawNode;
        graphicsLayer.m547recordmLhObY(this, getLayoutDirection(), j, new Function1() { // from class: androidx.compose.ui.node.LayoutNodeDrawScope$record$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                LayoutNodeDrawScope$record$1 layoutNodeDrawScope$record$1;
                Density density;
                LayoutDirection layoutDirection;
                Canvas canvas;
                long m526getSizeNHjbRc;
                GraphicsLayer graphicsLayer2;
                Function1 function12;
                Density density2;
                LayoutDirection layoutDirection2;
                Canvas canvas2;
                long m526getSizeNHjbRc2;
                DrawScope drawScope = (DrawScope) obj;
                LayoutNodeDrawScope layoutNodeDrawScope = LayoutNodeDrawScope.this;
                DrawModifierNode drawModifierNode2 = layoutNodeDrawScope.drawNode;
                CanvasDrawScope canvasDrawScope = layoutNodeDrawScope.canvasDrawScope;
                layoutNodeDrawScope.drawNode = drawModifierNode;
                try {
                    density = drawScope.getDrawContext().getDensity();
                    layoutDirection = drawScope.getDrawContext().getLayoutDirection();
                    canvas = drawScope.getDrawContext().getCanvas();
                    m526getSizeNHjbRc = drawScope.getDrawContext().m526getSizeNHjbRc();
                    graphicsLayer2 = drawScope.getDrawContext().graphicsLayer;
                    function12 = function1;
                    density2 = canvasDrawScope.drawContext.getDensity();
                    layoutDirection2 = canvasDrawScope.drawContext.getLayoutDirection();
                    canvas2 = canvasDrawScope.drawContext.getCanvas();
                    m526getSizeNHjbRc2 = canvasDrawScope.drawContext.m526getSizeNHjbRc();
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    CanvasDrawScope$drawContext$1 canvasDrawScope$drawContext$1 = canvasDrawScope.drawContext;
                    try {
                        GraphicsLayer graphicsLayer3 = canvasDrawScope$drawContext$1.graphicsLayer;
                        canvasDrawScope$drawContext$1.setDensity(density);
                        canvasDrawScope$drawContext$1.setLayoutDirection(layoutDirection);
                        canvasDrawScope$drawContext$1.setCanvas(canvas);
                        canvasDrawScope$drawContext$1.m527setSizeuvyYCjk(m526getSizeNHjbRc);
                        canvasDrawScope$drawContext$1.graphicsLayer = graphicsLayer2;
                        canvas.save();
                        try {
                            function12.mo779invoke(layoutNodeDrawScope);
                            canvas.restore();
                            CanvasDrawScope$drawContext$1 canvasDrawScope$drawContext$12 = canvasDrawScope.drawContext;
                            canvasDrawScope$drawContext$12.setDensity(density2);
                            canvasDrawScope$drawContext$12.setLayoutDirection(layoutDirection2);
                            canvasDrawScope$drawContext$12.setCanvas(canvas2);
                            canvasDrawScope$drawContext$12.m527setSizeuvyYCjk(m526getSizeNHjbRc2);
                            canvasDrawScope$drawContext$12.graphicsLayer = graphicsLayer3;
                            LayoutNodeDrawScope.this.drawNode = drawModifierNode2;
                            return Unit.INSTANCE;
                        } catch (Throwable th2) {
                            layoutNodeDrawScope$record$1 = this;
                            drawModifierNode2 = drawModifierNode2;
                            try {
                                canvas.restore();
                                CanvasDrawScope$drawContext$1 canvasDrawScope$drawContext$13 = canvasDrawScope.drawContext;
                                canvasDrawScope$drawContext$13.setDensity(density2);
                                canvasDrawScope$drawContext$13.setLayoutDirection(layoutDirection2);
                                canvasDrawScope$drawContext$13.setCanvas(canvas2);
                                canvasDrawScope$drawContext$13.m527setSizeuvyYCjk(m526getSizeNHjbRc2);
                                canvasDrawScope$drawContext$13.graphicsLayer = graphicsLayer3;
                                throw th2;
                            } catch (Throwable th3) {
                                th = th3;
                                LayoutNodeDrawScope.this.drawNode = drawModifierNode2;
                                throw th;
                            }
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        layoutNodeDrawScope$record$1 = this;
                        drawModifierNode2 = drawModifierNode2;
                    }
                } catch (Throwable th5) {
                    th = th5;
                    drawModifierNode2 = drawModifierNode2;
                    layoutNodeDrawScope$record$1 = this;
                    LayoutNodeDrawScope.this.drawNode = drawModifierNode2;
                    throw th;
                }
            }
        });
    }

    @Override // androidx.compose.ui.unit.Density
    /* renamed from: roundToPx--R2X_6o, reason: not valid java name */
    public final int mo647roundToPxR2X_6o(long j) {
        return this.canvasDrawScope.mo647roundToPxR2X_6o(j);
    }

    @Override // androidx.compose.ui.unit.Density
    /* renamed from: roundToPx-0680j_4 */
    public final int mo51roundToPx0680j_4(float f) {
        return this.canvasDrawScope.mo51roundToPx0680j_4(f);
    }

    @Override // androidx.compose.ui.unit.FontScaling
    /* renamed from: toDp-GaN1DYA */
    public final float mo52toDpGaN1DYA(long j) {
        return this.canvasDrawScope.mo52toDpGaN1DYA(j);
    }

    @Override // androidx.compose.ui.unit.Density
    /* renamed from: toDp-u2uoSUM */
    public final float mo53toDpu2uoSUM(float f) {
        return this.canvasDrawScope.mo53toDpu2uoSUM(f);
    }

    @Override // androidx.compose.ui.unit.Density
    /* renamed from: toDpSize-k-rfVVM */
    public final long mo55toDpSizekrfVVM(long j) {
        return this.canvasDrawScope.mo55toDpSizekrfVVM(j);
    }

    @Override // androidx.compose.ui.unit.Density
    /* renamed from: toPx--R2X_6o */
    public final float mo56toPxR2X_6o(long j) {
        return this.canvasDrawScope.mo56toPxR2X_6o(j);
    }

    @Override // androidx.compose.ui.unit.Density
    /* renamed from: toPx-0680j_4 */
    public final float mo57toPx0680j_4(float f) {
        return this.canvasDrawScope.getDensity() * f;
    }

    @Override // androidx.compose.ui.unit.Density
    /* renamed from: toSize-XkaWNTQ */
    public final long mo58toSizeXkaWNTQ(long j) {
        return this.canvasDrawScope.mo58toSizeXkaWNTQ(j);
    }

    @Override // androidx.compose.ui.unit.FontScaling
    /* renamed from: toSp-0xMU5do */
    public final long mo59toSp0xMU5do(float f) {
        return this.canvasDrawScope.mo59toSp0xMU5do(f);
    }

    @Override // androidx.compose.ui.unit.Density
    /* renamed from: toSp-kPz2Gy4 */
    public final long mo60toSpkPz2Gy4(float f) {
        return this.canvasDrawScope.mo60toSpkPz2Gy4(f);
    }

    public LayoutNodeDrawScope(CanvasDrawScope canvasDrawScope) {
        this.canvasDrawScope = canvasDrawScope;
    }

    @Override // androidx.compose.ui.unit.Density
    /* renamed from: toDp-u2uoSUM */
    public final float mo54toDpu2uoSUM(int i) {
        return this.canvasDrawScope.mo54toDpu2uoSUM(i);
    }

    public /* synthetic */ LayoutNodeDrawScope(CanvasDrawScope canvasDrawScope, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? new CanvasDrawScope() : canvasDrawScope);
    }
}
