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

/* loaded from: classes.dex */
final class DefaultDebugIndication implements IndicationNodeFactory {
    public static final DefaultDebugIndication INSTANCE = new DefaultDebugIndication();

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
            layoutNodeDrawScope.drawContent();
            boolean z = this.isPressed;
            CanvasDrawScope canvasDrawScope = layoutNodeDrawScope.canvasDrawScope;
            if (z) {
                Color.Companion.getClass();
                long j = Color.Black;
                DrawScope.m541drawRectnJ9OG0$default(layoutNodeDrawScope, ColorKt.Color(Color.m463getRedimpl(j), Color.m462getGreenimpl(j), Color.m460getBlueimpl(j), 0.3f, Color.m461getColorSpaceimpl(j)), 0L, canvasDrawScope.mo547getSizeNHjbRc(), 0.0f, null, null, 0, 122);
            } else if (this.isHovered || this.isFocused) {
                Color.Companion.getClass();
                long j2 = Color.Black;
                DrawScope.m541drawRectnJ9OG0$default(layoutNodeDrawScope, ColorKt.Color(Color.m463getRedimpl(j2), Color.m462getGreenimpl(j2), Color.m460getBlueimpl(j2), 0.1f, Color.m461getColorSpaceimpl(j2)), 0L, canvasDrawScope.mo547getSizeNHjbRc(), 0.0f, null, null, 0, 122);
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
