package androidx.compose.foundation;

import androidx.compose.foundation.interaction.InteractionSource;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.node.DelegatableNode;
import androidx.compose.ui.node.DrawModifierNode;
import androidx.compose.ui.node.LayoutNodeDrawScope;
import kotlinx.coroutines.BuildersKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class DefaultDebugIndication implements IndicationNodeFactory {
    public static final DefaultDebugIndication INSTANCE = new DefaultDebugIndication();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class DefaultDebugIndicationInstance extends Modifier.Node implements DrawModifierNode {
        public final InteractionSource interactionSource;
        public boolean isFocused;
        public boolean isHovered;
        public boolean isPressed;

        public DefaultDebugIndicationInstance(InteractionSource interactionSource) {
            this.interactionSource = interactionSource;
        }

        @Override // androidx.compose.ui.node.DrawModifierNode
        public final void draw(LayoutNodeDrawScope layoutNodeDrawScope) {
            long Color;
            long Color2;
            layoutNodeDrawScope.drawContent();
            boolean z = this.isPressed;
            CanvasDrawScope canvasDrawScope = layoutNodeDrawScope.canvasDrawScope;
            if (z) {
                Color.Companion.getClass();
                Color2 = ColorKt.Color(Color.m461getRedimpl(r0), Color.m460getGreenimpl(r0), Color.m458getBlueimpl(r0), 0.3f, Color.m459getColorSpaceimpl(Color.Black));
                DrawScope.m539drawRectnJ9OG0$default(layoutNodeDrawScope, Color2, 0L, canvasDrawScope.mo545getSizeNHjbRc(), 0.0f, null, null, 0, 122);
            } else if (this.isHovered || this.isFocused) {
                Color.Companion.getClass();
                Color = ColorKt.Color(Color.m461getRedimpl(r0), Color.m460getGreenimpl(r0), Color.m458getBlueimpl(r0), 0.1f, Color.m459getColorSpaceimpl(Color.Black));
                DrawScope.m539drawRectnJ9OG0$default(layoutNodeDrawScope, Color, 0L, canvasDrawScope.mo545getSizeNHjbRc(), 0.0f, null, null, 0, 122);
            }
        }

        @Override // androidx.compose.ui.Modifier.Node
        public final void onAttach() {
            BuildersKt.launch$default(getCoroutineScope(), null, null, new DefaultDebugIndication$DefaultDebugIndicationInstance$onAttach$1(this, null), 3);
        }
    }

    private DefaultDebugIndication() {
    }

    @Override // androidx.compose.foundation.IndicationNodeFactory
    public final DelegatableNode create(InteractionSource interactionSource) {
        return new DefaultDebugIndicationInstance(interactionSource);
    }

    public final boolean equals(Object obj) {
        return obj == this;
    }

    @Override // androidx.compose.foundation.IndicationNodeFactory
    public final int hashCode() {
        return -1;
    }
}
