package androidx.compose.foundation;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.geometry.RoundRect;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.Outline;
import androidx.compose.ui.graphics.OutlineKt;
import androidx.compose.ui.graphics.Path;
import androidx.compose.ui.graphics.RectangleShapeKt;
import androidx.compose.ui.graphics.Shape;
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.graphics.drawscope.Fill;
import androidx.compose.ui.node.DrawModifierNode;
import androidx.compose.ui.node.DrawModifierNodeKt;
import androidx.compose.ui.node.LayoutNodeDrawScope;
import androidx.compose.ui.node.ObserverModifierNode;
import androidx.compose.ui.node.ObserverModifierNodeKt;
import androidx.compose.ui.unit.LayoutDirection;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ULong;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class BackgroundNode extends Modifier.Node implements DrawModifierNode, ObserverModifierNode {
    public float alpha;
    public Brush brush;
    public long color;
    public LayoutDirection lastLayoutDirection;
    public Outline lastOutline;
    public Shape lastShape;
    public long lastSize;
    public Shape shape;
    public Outline tmpOutline;

    public /* synthetic */ BackgroundNode(long j, Brush brush, float f, Shape shape, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, brush, f, shape);
    }

    @Override // androidx.compose.ui.node.DrawModifierNode
    public final void draw(final LayoutNodeDrawScope layoutNodeDrawScope) {
        Outline outline;
        Outline outline2;
        Brush brush;
        Path path;
        if (this.shape == RectangleShapeKt.RectangleShape) {
            long j = this.color;
            Color.Companion.getClass();
            if (!ULong.m3427equalsimpl0(j, Color.Unspecified)) {
                DrawScope.m539drawRectnJ9OG0$default(layoutNodeDrawScope, this.color, 0L, 0L, 0.0f, null, null, 0, 126);
            }
            Brush brush2 = this.brush;
            if (brush2 != null) {
                DrawScope.m538drawRectAsUm42w$default(layoutNodeDrawScope, brush2, 0L, 0L, this.alpha, null, 0, 118);
            }
        } else {
            CanvasDrawScope canvasDrawScope = layoutNodeDrawScope.canvasDrawScope;
            if (Size.m414equalsimpl0(canvasDrawScope.mo545getSizeNHjbRc(), this.lastSize) && layoutNodeDrawScope.getLayoutDirection() == this.lastLayoutDirection && Intrinsics.areEqual(this.lastShape, this.shape)) {
                outline = this.lastOutline;
                outline.getClass();
            } else {
                ObserverModifierNodeKt.observeReads(this, new Function0() { // from class: androidx.compose.foundation.BackgroundNode$getOutline$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        BackgroundNode backgroundNode = BackgroundNode.this;
                        backgroundNode.tmpOutline = backgroundNode.shape.mo40createOutlinePq9zytI(((LayoutNodeDrawScope) layoutNodeDrawScope).canvasDrawScope.mo545getSizeNHjbRc(), ((LayoutNodeDrawScope) layoutNodeDrawScope).getLayoutDirection(), layoutNodeDrawScope);
                        return Unit.INSTANCE;
                    }
                });
                outline = this.tmpOutline;
                this.tmpOutline = null;
            }
            this.lastOutline = outline;
            this.lastSize = canvasDrawScope.mo545getSizeNHjbRc();
            this.lastLayoutDirection = layoutNodeDrawScope.getLayoutDirection();
            this.lastShape = this.shape;
            outline.getClass();
            long j2 = this.color;
            Color.Companion.getClass();
            if (ULong.m3427equalsimpl0(j2, Color.Unspecified)) {
                outline2 = outline;
            } else {
                outline2 = outline;
                OutlineKt.m490drawOutlinewDX37Ww$default(layoutNodeDrawScope, outline2, this.color, 0.0f, null, 60);
            }
            Brush brush3 = this.brush;
            if (brush3 != null) {
                float f = this.alpha;
                Fill fill = Fill.INSTANCE;
                DrawScope.Companion.getClass();
                int i = DrawScope.Companion.DefaultBlendMode;
                if (outline2 instanceof Outline.Rectangle) {
                    Rect rect = ((Outline.Rectangle) outline2).rect;
                    long floatToRawIntBits = Float.floatToRawIntBits(rect.left);
                    Offset.Companion companion = Offset.Companion;
                    layoutNodeDrawScope.m645drawRectAsUm42w(brush3, (4294967295L & Float.floatToRawIntBits(rect.top)) | (floatToRawIntBits << 32), OutlineKt.size(rect), f, fill, i);
                } else {
                    if (outline2 instanceof Outline.Rounded) {
                        Outline.Rounded rounded = (Outline.Rounded) outline2;
                        brush = brush3;
                        path = rounded.roundRectPath;
                        if (path == null) {
                            RoundRect roundRect = rounded.roundRect;
                            float intBitsToFloat = Float.intBitsToFloat((int) (roundRect.bottomLeftCornerRadius >> 32));
                            long floatToRawIntBits2 = (Float.floatToRawIntBits(roundRect.left) << 32) | (Float.floatToRawIntBits(roundRect.top) & 4294967295L);
                            Offset.Companion companion2 = Offset.Companion;
                            float width = roundRect.getWidth();
                            float height = roundRect.getHeight();
                            layoutNodeDrawScope.mo524drawRoundRectZuiqVtQ(brush, floatToRawIntBits2, (Float.floatToRawIntBits(width) << 32) | (Float.floatToRawIntBits(height) & 4294967295L), (Float.floatToRawIntBits(intBitsToFloat) & 4294967295L) | (Float.floatToRawIntBits(intBitsToFloat) << 32), f, fill, i);
                        }
                    } else {
                        if (!(outline2 instanceof Outline.Generic)) {
                            throw new NoWhenBranchMatchedException();
                        }
                        Path path2 = ((Outline.Generic) outline2).path;
                        brush = brush3;
                        path = path2;
                    }
                    layoutNodeDrawScope.mo521drawPathGBMwjPU(path, brush, f, fill, i);
                }
            }
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

    private BackgroundNode(long j, Brush brush, float f, Shape shape) {
        this.color = j;
        this.brush = brush;
        this.alpha = f;
        this.shape = shape;
        Size.Companion.getClass();
        this.lastSize = Size.Unspecified;
    }
}
