package androidx.compose.foundation;

import androidx.compose.ui.node.LayoutNodeDrawScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class NoIndicationInstance implements IndicationInstance {
    public static final NoIndicationInstance INSTANCE = new NoIndicationInstance();

    private NoIndicationInstance() {
    }

    @Override // androidx.compose.foundation.IndicationInstance
    public final void drawIndication(LayoutNodeDrawScope layoutNodeDrawScope) {
        layoutNodeDrawScope.drawContent();
    }
}
