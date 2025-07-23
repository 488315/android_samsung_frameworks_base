package androidx.compose.ui.viewinterop;

import androidx.compose.ui.node.LayoutNode;
import androidx.compose.ui.unit.Density;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class AndroidViewHolder$layoutNode$1$2 extends Lambda implements Function1 {
    final /* synthetic */ LayoutNode $layoutNode;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AndroidViewHolder$layoutNode$1$2(LayoutNode layoutNode) {
        super(1);
        this.$layoutNode = layoutNode;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        this.$layoutNode.setDensity$1((Density) obj);
        return Unit.INSTANCE;
    }
}
