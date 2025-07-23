package androidx.compose.foundation;

import androidx.compose.ui.draw.DrawModifier;
import androidx.compose.ui.node.LayoutNodeDrawScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
