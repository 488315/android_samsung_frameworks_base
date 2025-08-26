package com.android.compose.modifiers;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.Outline;
import androidx.compose.ui.graphics.OutlineKt;
import androidx.compose.ui.graphics.RectangleShapeKt;
import androidx.compose.ui.graphics.Shape;
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.node.DrawModifierNode;
import androidx.compose.ui.node.DrawModifierNodeKt;
import androidx.compose.ui.node.LayoutNodeDrawScope;
import androidx.compose.ui.node.ObserverModifierNode;
import androidx.compose.ui.node.ObserverModifierNodeKt;
import androidx.compose.ui.unit.LayoutDirection;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class BackgroundNode extends Modifier.Node implements DrawModifierNode, ObserverModifierNode {
    public Function0 alpha;
    public Function0 color;
    public LayoutDirection lastLayoutDirection;
    public Outline lastOutline;
    public Shape lastShape;
    public long lastSize;
    public Shape shape;
    public Outline tmpOutline;

    public BackgroundNode(Function0 function0, Function0 function02, Shape shape) {
        this.color = function0;
        this.alpha = function02;
        this.shape = shape;
        Size.Companion.getClass();
        this.lastSize = Size.Unspecified;
    }

    @Override // androidx.compose.ui.node.DrawModifierNode
    public final void draw(final LayoutNodeDrawScope layoutNodeDrawScope) {
        Outline outline;
        if (this.shape == RectangleShapeKt.RectangleShape) {
            DrawScope.m541drawRectnJ9OG0$default(layoutNodeDrawScope, ((Color) this.color.invoke()).value, 0L, 0L, ((Number) this.alpha.invoke()).floatValue(), null, null, 0, 118);
        } else {
            CanvasDrawScope canvasDrawScope = layoutNodeDrawScope.canvasDrawScope;
            if (Size.m416equalsimpl0(canvasDrawScope.mo547getSizeNHjbRc(), this.lastSize) && layoutNodeDrawScope.getLayoutDirection() == this.lastLayoutDirection && Intrinsics.areEqual(this.lastShape, this.shape)) {
                outline = this.lastOutline;
                outline.getClass();
            } else {
                ObserverModifierNodeKt.observeReads(this, new Function0() { // from class: com.android.compose.modifiers.BackgroundNode$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        BackgroundNode backgroundNode = this.f$0;
                        Shape shape = backgroundNode.shape;
                        LayoutNodeDrawScope layoutNodeDrawScope2 = layoutNodeDrawScope;
                        backgroundNode.tmpOutline = shape.mo41createOutlinePq9zytI(layoutNodeDrawScope2.canvasDrawScope.mo547getSizeNHjbRc(), layoutNodeDrawScope2.getLayoutDirection(), layoutNodeDrawScope2);
                        return Unit.INSTANCE;
                    }
                });
                outline = this.tmpOutline;
                this.tmpOutline = null;
            }
            this.lastOutline = outline;
            this.lastSize = canvasDrawScope.mo547getSizeNHjbRc();
            this.lastLayoutDirection = layoutNodeDrawScope.getLayoutDirection();
            this.lastShape = this.shape;
            outline.getClass();
            OutlineKt.m492drawOutlinewDX37Ww$default(layoutNodeDrawScope, outline, ((Color) this.color.invoke()).value, ((Number) this.alpha.invoke()).floatValue(), null, 56);
        }
        layoutNodeDrawScope.drawContent();
    }

    @Override // androidx.compose.ui.node.ObserverModifierNode
    public final void onObservedReadsChanged() {
        Size.Companion.getClass();
        this.lastSize = Size.Unspecified;
        this.lastLayoutDirection = null;
        this.lastOutline = null;
        this.lastShape = null;
        DrawModifierNodeKt.invalidateDraw(this);
    }
}
