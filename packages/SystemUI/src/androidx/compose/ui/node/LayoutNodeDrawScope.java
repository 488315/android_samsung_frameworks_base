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

/* loaded from: classes.dex */
public final class LayoutNodeDrawScope implements DrawScope, ContentDrawScope {
    public final CanvasDrawScope canvasDrawScope;
    public DrawModifierNode drawNode;

    /* JADX WARN: Multi-variable type inference failed */
    public LayoutNodeDrawScope() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    @Override // androidx.compose.ui.graphics.drawscope.DrawScope
    /* renamed from: drawArc-yD3GUKo */
    public final void mo518drawArcyD3GUKo(long j, float f, float f2, long j2, long j3, DrawStyle drawStyle, int i) {
        this.canvasDrawScope.mo518drawArcyD3GUKo(j, f, f2, j2, j3, drawStyle, i);
    }

    @Override // androidx.compose.ui.graphics.drawscope.DrawScope
    /* renamed from: drawCircle-V9BoPsw */
    public final void mo519drawCircleV9BoPsw(Brush brush, float f, long j, float f2, DrawStyle drawStyle, int i) {
        this.canvasDrawScope.mo519drawCircleV9BoPsw(brush, f, j, f2, drawStyle, i);
    }

    @Override // androidx.compose.ui.graphics.drawscope.DrawScope
    /* renamed from: drawCircle-VaOC9Bg */
    public final void mo520drawCircleVaOC9Bg(long j, float f, long j2, float f2, DrawStyle drawStyle, int i) {
        this.canvasDrawScope.mo520drawCircleVaOC9Bg(j, f, j2, f2, drawStyle, i);
    }

    public final void drawContent() {
        CanvasDrawScope canvasDrawScope = this.canvasDrawScope;
        Canvas canvas = canvasDrawScope.drawContext.getCanvas();
        DelegatableNode delegatableNode = this.drawNode;
        if (delegatableNode == null) {
            throw AndroidAutofill$$ExternalSyntheticOutline0.m("Attempting to drawContent for a `null` node. This usually means that a call to ContentDrawScope#drawContent() has been captured inside a lambda, and is being invoked outside of the draw pass. Capturing the scope this way is unsupported - if you are trying to record drawContent with graphicsLayer.record(), make sure you are using the GraphicsLayer#record function within DrawScope, instead of the member function on GraphicsLayer.");
        }
        Modifier.Node node = (Modifier.Node) delegatableNode;
        Modifier.Node nodeAccess$pop = node.node.child;
        if (nodeAccess$pop == null || (nodeAccess$pop.aggregateChildKindSet & 4) == 0) {
            nodeAccess$pop = null;
        } else {
            while (nodeAccess$pop != null) {
                int i = nodeAccess$pop.kindSet;
                if ((i & 2) != 0) {
                    break;
                } else if ((i & 4) != 0) {
                    break;
                } else {
                    nodeAccess$pop = nodeAccess$pop.child;
                }
            }
            nodeAccess$pop = null;
        }
        if (nodeAccess$pop == null) {
            NodeCoordinator nodeCoordinatorM634requireCoordinator64DMado = DelegatableNodeKt.m634requireCoordinator64DMado(delegatableNode, 4);
            if (nodeCoordinatorM634requireCoordinator64DMado.getTail() == node.node) {
                nodeCoordinatorM634requireCoordinator64DMado = nodeCoordinatorM634requireCoordinator64DMado.wrapped;
                nodeCoordinatorM634requireCoordinator64DMado.getClass();
            }
            nodeCoordinatorM634requireCoordinator64DMado.performDraw(canvas, canvasDrawScope.drawContext.graphicsLayer);
            return;
        }
        MutableVector mutableVector = null;
        while (nodeAccess$pop != null) {
            if (nodeAccess$pop instanceof DrawModifierNode) {
                DrawModifierNode drawModifierNode = (DrawModifierNode) nodeAccess$pop;
                GraphicsLayer graphicsLayer = canvasDrawScope.drawContext.graphicsLayer;
                NodeCoordinator nodeCoordinatorM634requireCoordinator64DMado2 = DelegatableNodeKt.m634requireCoordinator64DMado(drawModifierNode, 4);
                long jM866toSizeozmzZPI = IntSizeKt.m866toSizeozmzZPI(nodeCoordinatorM634requireCoordinator64DMado2.measuredSize);
                LayoutNode layoutNode = nodeCoordinatorM634requireCoordinator64DMado2.layoutNode;
                layoutNode.getClass();
                ((AndroidComposeView) LayoutNodeKt.requireOwner(layoutNode)).sharedDrawScope.m646drawDirecteZhPAX0$ui_release(canvas, jM866toSizeozmzZPI, nodeCoordinatorM634requireCoordinator64DMado2, drawModifierNode, graphicsLayer);
            } else if ((nodeAccess$pop.kindSet & 4) != 0 && (nodeAccess$pop instanceof DelegatingNode)) {
                int i2 = 0;
                for (Modifier.Node node2 = ((DelegatingNode) nodeAccess$pop).delegate; node2 != null; node2 = node2.child) {
                    if ((node2.kindSet & 4) != 0) {
                        i2++;
                        if (i2 == 1) {
                            nodeAccess$pop = node2;
                        } else {
                            if (mutableVector == null) {
                                mutableVector = new MutableVector(new Modifier.Node[16], 0);
                            }
                            if (nodeAccess$pop != null) {
                                mutableVector.add(nodeAccess$pop);
                                nodeAccess$pop = null;
                            }
                            mutableVector.add(node2);
                        }
                    }
                }
                if (i2 == 1) {
                }
            }
            nodeAccess$pop = DelegatableNodeKt.access$pop(mutableVector);
        }
    }

    /* renamed from: drawDirect-eZhPAX0$ui_release, reason: not valid java name */
    public final void m646drawDirecteZhPAX0$ui_release(Canvas canvas, long j, NodeCoordinator nodeCoordinator, DrawModifierNode drawModifierNode, GraphicsLayer graphicsLayer) {
        DrawModifierNode drawModifierNode2 = this.drawNode;
        this.drawNode = drawModifierNode;
        LayoutDirection layoutDirection = nodeCoordinator.layoutNode.layoutDirection;
        CanvasDrawScope canvasDrawScope = this.canvasDrawScope;
        Density density = canvasDrawScope.drawContext.getDensity();
        CanvasDrawScope$drawContext$1 canvasDrawScope$drawContext$1 = canvasDrawScope.drawContext;
        LayoutDirection layoutDirection2 = canvasDrawScope$drawContext$1.getLayoutDirection();
        Canvas canvas2 = canvasDrawScope$drawContext$1.getCanvas();
        long jM528getSizeNHjbRc = canvasDrawScope$drawContext$1.m528getSizeNHjbRc();
        GraphicsLayer graphicsLayer2 = canvasDrawScope$drawContext$1.graphicsLayer;
        canvasDrawScope$drawContext$1.setDensity(nodeCoordinator);
        canvasDrawScope$drawContext$1.setLayoutDirection(layoutDirection);
        canvasDrawScope$drawContext$1.setCanvas(canvas);
        canvasDrawScope$drawContext$1.m529setSizeuvyYCjk(j);
        canvasDrawScope$drawContext$1.graphicsLayer = graphicsLayer;
        canvas.save();
        try {
            drawModifierNode.draw(this);
            canvas.restore();
            canvasDrawScope$drawContext$1.setDensity(density);
            canvasDrawScope$drawContext$1.setLayoutDirection(layoutDirection2);
            canvasDrawScope$drawContext$1.setCanvas(canvas2);
            canvasDrawScope$drawContext$1.m529setSizeuvyYCjk(jM528getSizeNHjbRc);
            canvasDrawScope$drawContext$1.graphicsLayer = graphicsLayer2;
            this.drawNode = drawModifierNode2;
        } catch (Throwable th) {
            canvas.restore();
            canvasDrawScope$drawContext$1.setDensity(density);
            canvasDrawScope$drawContext$1.setLayoutDirection(layoutDirection2);
            canvasDrawScope$drawContext$1.setCanvas(canvas2);
            canvasDrawScope$drawContext$1.m529setSizeuvyYCjk(jM528getSizeNHjbRc);
            canvasDrawScope$drawContext$1.graphicsLayer = graphicsLayer2;
            throw th;
        }
    }

    @Override // androidx.compose.ui.graphics.drawscope.DrawScope
    /* renamed from: drawImage-AZ2fEMs */
    public final void mo521drawImageAZ2fEMs(ImageBitmap imageBitmap, long j, long j2, long j3, float f, DrawStyle drawStyle, ColorFilter colorFilter, int i, int i2) {
        this.canvasDrawScope.mo521drawImageAZ2fEMs(imageBitmap, j, j2, j3, f, drawStyle, colorFilter, i, i2);
    }

    @Override // androidx.compose.ui.graphics.drawscope.DrawScope
    /* renamed from: drawLine-NGM6Ib0 */
    public final void mo522drawLineNGM6Ib0(long j, long j2, long j3, float f, int i, float f2, int i2) {
        this.canvasDrawScope.mo522drawLineNGM6Ib0(j, j2, j3, f, i, f2, i2);
    }

    @Override // androidx.compose.ui.graphics.drawscope.DrawScope
    /* renamed from: drawPath-GBMwjPU */
    public final void mo523drawPathGBMwjPU(Path path, Brush brush, float f, DrawStyle drawStyle, int i) {
        this.canvasDrawScope.mo523drawPathGBMwjPU(path, brush, f, drawStyle, i);
    }

    @Override // androidx.compose.ui.graphics.drawscope.DrawScope
    /* renamed from: drawPath-LG529CI */
    public final void mo524drawPathLG529CI(Path path, long j, float f, DrawStyle drawStyle, int i) {
        this.canvasDrawScope.mo524drawPathLG529CI(path, j, f, drawStyle, i);
    }

    /* renamed from: drawRect-AsUm42w, reason: not valid java name */
    public final void m647drawRectAsUm42w(Brush brush, long j, long j2, float f, DrawStyle drawStyle, int i) {
        CanvasDrawScope canvasDrawScope = this.canvasDrawScope;
        int i2 = (int) (j >> 32);
        int i3 = (int) (j & 4294967295L);
        canvasDrawScope.drawParams.canvas.drawRect(Float.intBitsToFloat(i2), Float.intBitsToFloat(i3), Float.intBitsToFloat((int) (j2 >> 32)) + Float.intBitsToFloat(i2), Float.intBitsToFloat((int) (j2 & 4294967295L)) + Float.intBitsToFloat(i3), CanvasDrawScope.m516configurePaintswdJneE$default(canvasDrawScope, brush, drawStyle, f, null, i));
    }

    @Override // androidx.compose.ui.graphics.drawscope.DrawScope
    /* renamed from: drawRect-n-J9OG0 */
    public final void mo525drawRectnJ9OG0(long j, long j2, long j3, float f, DrawStyle drawStyle, ColorFilter colorFilter, int i) {
        this.canvasDrawScope.mo525drawRectnJ9OG0(j, j2, j3, f, drawStyle, colorFilter, i);
    }

    @Override // androidx.compose.ui.graphics.drawscope.DrawScope
    /* renamed from: drawRoundRect-ZuiqVtQ */
    public final void mo526drawRoundRectZuiqVtQ(Brush brush, long j, long j2, long j3, float f, DrawStyle drawStyle, int i) {
        this.canvasDrawScope.mo526drawRoundRectZuiqVtQ(brush, j, j2, j3, f, drawStyle, i);
    }

    @Override // androidx.compose.ui.graphics.drawscope.DrawScope
    /* renamed from: drawRoundRect-u-Aw5IA */
    public final void mo527drawRoundRectuAw5IA(long j, long j2, long j3, long j4, DrawStyle drawStyle, float f, int i) {
        this.canvasDrawScope.mo527drawRoundRectuAw5IA(j, j2, j3, j4, drawStyle, f, i);
    }

    @Override // androidx.compose.ui.graphics.drawscope.DrawScope
    /* renamed from: getCenter-F1C5BW0 */
    public final long mo546getCenterF1C5BW0() {
        return this.canvasDrawScope.mo546getCenterF1C5BW0();
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
    public final long mo547getSizeNHjbRc() {
        return this.canvasDrawScope.mo547getSizeNHjbRc();
    }

    /* renamed from: record-JVtK1S4, reason: not valid java name */
    public final void m648recordJVtK1S4(long j, GraphicsLayer graphicsLayer, final Function1 function1) {
        final DrawModifierNode drawModifierNode = this.drawNode;
        graphicsLayer.m549recordmLhObY(this, getLayoutDirection(), j, new Function1() { // from class: androidx.compose.ui.node.LayoutNodeDrawScope$record$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) throws Throwable {
                LayoutNodeDrawScope$record$1 layoutNodeDrawScope$record$1;
                DrawScope drawScope = (DrawScope) obj;
                LayoutNodeDrawScope layoutNodeDrawScope = this.this$0;
                DrawModifierNode drawModifierNode2 = layoutNodeDrawScope.drawNode;
                CanvasDrawScope canvasDrawScope = layoutNodeDrawScope.canvasDrawScope;
                layoutNodeDrawScope.drawNode = drawModifierNode;
                try {
                    Density density = drawScope.getDrawContext().getDensity();
                    LayoutDirection layoutDirection = drawScope.getDrawContext().getLayoutDirection();
                    Canvas canvas = drawScope.getDrawContext().getCanvas();
                    long jM528getSizeNHjbRc = drawScope.getDrawContext().m528getSizeNHjbRc();
                    GraphicsLayer graphicsLayer2 = drawScope.getDrawContext().graphicsLayer;
                    Function1 function12 = function1;
                    Density density2 = canvasDrawScope.drawContext.getDensity();
                    LayoutDirection layoutDirection2 = canvasDrawScope.drawContext.getLayoutDirection();
                    Canvas canvas2 = canvasDrawScope.drawContext.getCanvas();
                    long jM528getSizeNHjbRc2 = canvasDrawScope.drawContext.m528getSizeNHjbRc();
                    try {
                        CanvasDrawScope$drawContext$1 canvasDrawScope$drawContext$1 = canvasDrawScope.drawContext;
                        try {
                            GraphicsLayer graphicsLayer3 = canvasDrawScope$drawContext$1.graphicsLayer;
                            canvasDrawScope$drawContext$1.setDensity(density);
                            canvasDrawScope$drawContext$1.setLayoutDirection(layoutDirection);
                            canvasDrawScope$drawContext$1.setCanvas(canvas);
                            canvasDrawScope$drawContext$1.m529setSizeuvyYCjk(jM528getSizeNHjbRc);
                            canvasDrawScope$drawContext$1.graphicsLayer = graphicsLayer2;
                            canvas.save();
                            try {
                                function12.mo781invoke(layoutNodeDrawScope);
                                canvas.restore();
                                CanvasDrawScope$drawContext$1 canvasDrawScope$drawContext$12 = canvasDrawScope.drawContext;
                                canvasDrawScope$drawContext$12.setDensity(density2);
                                canvasDrawScope$drawContext$12.setLayoutDirection(layoutDirection2);
                                canvasDrawScope$drawContext$12.setCanvas(canvas2);
                                canvasDrawScope$drawContext$12.m529setSizeuvyYCjk(jM528getSizeNHjbRc2);
                                canvasDrawScope$drawContext$12.graphicsLayer = graphicsLayer3;
                                this.this$0.drawNode = drawModifierNode2;
                                return Unit.INSTANCE;
                            } catch (Throwable th) {
                                layoutNodeDrawScope$record$1 = this;
                                drawModifierNode2 = drawModifierNode2;
                                try {
                                    canvas.restore();
                                    CanvasDrawScope$drawContext$1 canvasDrawScope$drawContext$13 = canvasDrawScope.drawContext;
                                    canvasDrawScope$drawContext$13.setDensity(density2);
                                    canvasDrawScope$drawContext$13.setLayoutDirection(layoutDirection2);
                                    canvasDrawScope$drawContext$13.setCanvas(canvas2);
                                    canvasDrawScope$drawContext$13.m529setSizeuvyYCjk(jM528getSizeNHjbRc2);
                                    canvasDrawScope$drawContext$13.graphicsLayer = graphicsLayer3;
                                    throw th;
                                } catch (Throwable th2) {
                                    th = th2;
                                    layoutNodeDrawScope$record$1.this$0.drawNode = drawModifierNode2;
                                    throw th;
                                }
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            layoutNodeDrawScope$record$1 = this;
                            drawModifierNode2 = drawModifierNode2;
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        drawModifierNode2 = drawModifierNode2;
                        layoutNodeDrawScope$record$1 = this;
                        layoutNodeDrawScope$record$1.this$0.drawNode = drawModifierNode2;
                        throw th;
                    }
                } catch (Throwable th5) {
                    th = th5;
                }
            }
        });
    }

    @Override // androidx.compose.ui.unit.Density
    /* renamed from: roundToPx--R2X_6o, reason: not valid java name */
    public final int mo649roundToPxR2X_6o(long j) {
        return this.canvasDrawScope.mo649roundToPxR2X_6o(j);
    }

    @Override // androidx.compose.ui.unit.Density
    /* renamed from: roundToPx-0680j_4 */
    public final int mo52roundToPx0680j_4(float f) {
        return this.canvasDrawScope.mo52roundToPx0680j_4(f);
    }

    @Override // androidx.compose.ui.unit.FontScaling
    /* renamed from: toDp-GaN1DYA */
    public final float mo53toDpGaN1DYA(long j) {
        return this.canvasDrawScope.mo53toDpGaN1DYA(j);
    }

    @Override // androidx.compose.ui.unit.Density
    /* renamed from: toDp-u2uoSUM */
    public final float mo54toDpu2uoSUM(float f) {
        return this.canvasDrawScope.mo54toDpu2uoSUM(f);
    }

    @Override // androidx.compose.ui.unit.Density
    /* renamed from: toDpSize-k-rfVVM */
    public final long mo56toDpSizekrfVVM(long j) {
        return this.canvasDrawScope.mo56toDpSizekrfVVM(j);
    }

    @Override // androidx.compose.ui.unit.Density
    /* renamed from: toPx--R2X_6o */
    public final float mo57toPxR2X_6o(long j) {
        return this.canvasDrawScope.mo57toPxR2X_6o(j);
    }

    @Override // androidx.compose.ui.unit.Density
    /* renamed from: toPx-0680j_4 */
    public final float mo58toPx0680j_4(float f) {
        return this.canvasDrawScope.getDensity() * f;
    }

    @Override // androidx.compose.ui.unit.Density
    /* renamed from: toSize-XkaWNTQ */
    public final long mo59toSizeXkaWNTQ(long j) {
        return this.canvasDrawScope.mo59toSizeXkaWNTQ(j);
    }

    @Override // androidx.compose.ui.unit.FontScaling
    /* renamed from: toSp-0xMU5do */
    public final long mo60toSp0xMU5do(float f) {
        return this.canvasDrawScope.mo60toSp0xMU5do(f);
    }

    @Override // androidx.compose.ui.unit.Density
    /* renamed from: toSp-kPz2Gy4 */
    public final long mo61toSpkPz2Gy4(float f) {
        return this.canvasDrawScope.mo61toSpkPz2Gy4(f);
    }

    public LayoutNodeDrawScope(CanvasDrawScope canvasDrawScope) {
        this.canvasDrawScope = canvasDrawScope;
    }

    @Override // androidx.compose.ui.unit.Density
    /* renamed from: toDp-u2uoSUM */
    public final float mo55toDpu2uoSUM(int i) {
        return this.canvasDrawScope.mo55toDpu2uoSUM(i);
    }

    public /* synthetic */ LayoutNodeDrawScope(CanvasDrawScope canvasDrawScope, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? new CanvasDrawScope() : canvasDrawScope);
    }
}
