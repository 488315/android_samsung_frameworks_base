package androidx.compose.ui.layout;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.LayoutAwareModifierNode;
import androidx.compose.ui.unit.IntSize;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class OnSizeChangedNode extends Modifier.Node implements LayoutAwareModifierNode {
    public Function1 onSizeChanged;
    public long previousSize;
    public final boolean shouldAutoInvalidate = true;

    public OnSizeChangedNode(Function1 function1) {
        this.onSizeChanged = function1;
        long j = Integer.MIN_VALUE;
        IntSize.Companion companion = IntSize.Companion;
        this.previousSize = (j & 4294967295L) | (j << 32);
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final boolean getShouldAutoInvalidate() {
        return this.shouldAutoInvalidate;
    }

    @Override // androidx.compose.ui.node.LayoutAwareModifierNode
    /* renamed from: onRemeasured-ozmzZPI */
    public final void mo49onRemeasuredozmzZPI(long j) {
        if (IntSize.m861equalsimpl0(this.previousSize, j)) {
            return;
        }
        this.onSizeChanged.mo779invoke(IntSize.m859boximpl(j));
        this.previousSize = j;
    }
}
