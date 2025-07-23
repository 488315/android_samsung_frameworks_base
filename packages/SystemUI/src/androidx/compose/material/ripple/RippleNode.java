package androidx.compose.material.ripple;

import androidx.compose.foundation.BorderModifierNode$drawRoundRectBorder$1$$ExternalSyntheticOutline0;
import androidx.compose.foundation.interaction.InteractionSource;
import androidx.compose.foundation.interaction.PressInteraction$Press;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.ClipOp;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.ColorProducer;
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope;
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope$drawContext$1;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.node.CompositionLocalConsumerModifierNode;
import androidx.compose.ui.node.DrawModifierNode;
import androidx.compose.ui.node.LayoutNodeDrawScope;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class RippleNode extends Modifier.Node implements CompositionLocalConsumerModifierNode, DrawModifierNode {
    public final boolean bounded;
    public final ColorProducer color;
    public final InteractionSource interactionSource;
    public final float radius;
    public final Function0 rippleAlpha;
    public StateLayer stateLayer;
    public float targetRadius;

    public /* synthetic */ RippleNode(InteractionSource interactionSource, boolean z, float f, ColorProducer colorProducer, Function0 function0, DefaultConstructorMarker defaultConstructorMarker) {
        this(interactionSource, z, f, colorProducer, function0);
    }

    public abstract void addRipple$1(PressInteraction$Press pressInteraction$Press);

    @Override // androidx.compose.ui.node.DrawModifierNode
    public final void draw(LayoutNodeDrawScope layoutNodeDrawScope) {
        float mo57toPx0680j_4;
        long Color;
        float f = this.radius;
        if (Float.isNaN(f)) {
            mo57toPx0680j_4 = RippleAnimationKt.m243getRippleEndRadiuscSwnlzA(layoutNodeDrawScope, this.bounded, layoutNodeDrawScope.canvasDrawScope.mo545getSizeNHjbRc());
        } else {
            mo57toPx0680j_4 = layoutNodeDrawScope.mo57toPx0680j_4(f);
        }
        this.targetRadius = mo57toPx0680j_4;
        layoutNodeDrawScope.drawContent();
        StateLayer stateLayer = this.stateLayer;
        if (stateLayer != null) {
            float f2 = this.targetRadius;
            long mo261invoke0d7_KjU = this.color.mo261invoke0d7_KjU();
            float floatValue = ((Number) stateLayer.animatedAlpha.internalState.getValue()).floatValue();
            if (floatValue > 0.0f) {
                Color = ColorKt.Color(Color.m461getRedimpl(mo261invoke0d7_KjU), Color.m460getGreenimpl(mo261invoke0d7_KjU), Color.m458getBlueimpl(mo261invoke0d7_KjU), floatValue, Color.m459getColorSpaceimpl(mo261invoke0d7_KjU));
                if (stateLayer.bounded) {
                    CanvasDrawScope canvasDrawScope = layoutNodeDrawScope.canvasDrawScope;
                    float m417getWidthimpl = Size.m417getWidthimpl(canvasDrawScope.mo545getSizeNHjbRc());
                    float m415getHeightimpl = Size.m415getHeightimpl(canvasDrawScope.mo545getSizeNHjbRc());
                    ClipOp.Companion.getClass();
                    int i = ClipOp.Intersect;
                    CanvasDrawScope$drawContext$1 canvasDrawScope$drawContext$1 = canvasDrawScope.drawContext;
                    long m526getSizeNHjbRc = canvasDrawScope$drawContext$1.m526getSizeNHjbRc();
                    canvasDrawScope$drawContext$1.getCanvas().save();
                    canvasDrawScope$drawContext$1.transform.m528clipRectN_I0leg(0.0f, 0.0f, m417getWidthimpl, m415getHeightimpl, i);
                    DrawScope.m532drawCircleVaOC9Bg$default(layoutNodeDrawScope, Color, f2, 0L, 0.0f, null, 0, 124);
                    BorderModifierNode$drawRoundRectBorder$1$$ExternalSyntheticOutline0.m(canvasDrawScope$drawContext$1, m526getSizeNHjbRc);
                } else {
                    DrawScope.m532drawCircleVaOC9Bg$default(layoutNodeDrawScope, Color, f2, 0L, 0.0f, null, 0, 124);
                }
            }
        }
        drawRipples(layoutNodeDrawScope);
    }

    public abstract void drawRipples(LayoutNodeDrawScope layoutNodeDrawScope);

    @Override // androidx.compose.ui.Modifier.Node
    public final boolean getShouldAutoInvalidate() {
        return false;
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final void onAttach() {
        BuildersKt.launch$default(getCoroutineScope(), null, null, new RippleNode$onAttach$1(this, null), 3);
    }

    public abstract void removeRipple(PressInteraction$Press pressInteraction$Press);

    private RippleNode(InteractionSource interactionSource, boolean z, float f, ColorProducer colorProducer, Function0 function0) {
        this.interactionSource = interactionSource;
        this.bounded = z;
        this.radius = f;
        this.color = colorProducer;
        this.rippleAlpha = function0;
    }
}
