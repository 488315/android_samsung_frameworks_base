package androidx.compose.foundation;

import androidx.compose.ui.draw.DrawModifier;
import androidx.compose.ui.node.LayoutNodeDrawScope;

/* loaded from: classes.dex */
final class IndicationModifier implements DrawModifier {
    public final IndicationInstance indicationInstance;

    public IndicationModifier(IndicationInstance indicationInstance) {
        this.indicationInstance = indicationInstance;
    }

    @Override // androidx.compose.ui.draw.DrawModifier
    public final void draw(LayoutNodeDrawScope layoutNodeDrawScope) {
        this.indicationInstance.drawIndication(layoutNodeDrawScope);
    }
}
