package androidx.compose.ui.draw;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.DrawModifierNode;
import androidx.compose.ui.node.LayoutNodeDrawScope;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class DrawBackgroundModifier extends Modifier.Node implements DrawModifierNode {
    public Function1 onDraw;

    public DrawBackgroundModifier(Function1 function1) {
        this.onDraw = function1;
    }

    @Override // androidx.compose.ui.node.DrawModifierNode
    public final void draw(LayoutNodeDrawScope layoutNodeDrawScope) {
        this.onDraw.mo779invoke(layoutNodeDrawScope);
        layoutNodeDrawScope.drawContent();
    }
}
