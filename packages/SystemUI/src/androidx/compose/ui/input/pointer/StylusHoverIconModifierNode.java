package androidx.compose.ui.input.pointer;

import androidx.compose.ui.input.pointer.PointerType;
import androidx.compose.ui.node.CompositionLocalConsumerModifierNodeKt;
import androidx.compose.ui.node.DpTouchBoundsExpansion;
import androidx.compose.ui.platform.AndroidComposeView$pointerIconService$1;
import androidx.compose.ui.platform.CompositionLocalsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class StylusHoverIconModifierNode extends HoverIconModifierNode {
    public final String traverseKey;

    public /* synthetic */ StylusHoverIconModifierNode(PointerIcon pointerIcon, boolean z, DpTouchBoundsExpansion dpTouchBoundsExpansion, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(pointerIcon, (i & 2) != 0 ? false : z, (i & 4) != 0 ? null : dpTouchBoundsExpansion);
    }

    @Override // androidx.compose.ui.input.pointer.HoverIconModifierNode
    public final void displayIcon(PointerIcon pointerIcon) {
        PointerIconService pointerIconService = (PointerIconService) CompositionLocalConsumerModifierNodeKt.currentValueOf(this, CompositionLocalsKt.LocalPointerIconService);
        if (pointerIconService != null) {
            ((AndroidComposeView$pointerIconService$1) pointerIconService).currentStylusHoverIcon = pointerIcon;
        }
    }

    @Override // androidx.compose.ui.node.TraversableNode
    public final Object getTraverseKey() {
        return this.traverseKey;
    }

    @Override // androidx.compose.ui.input.pointer.HoverIconModifierNode
    /* renamed from: isRelevantPointerType-uerMTgs */
    public final boolean mo587isRelevantPointerTypeuerMTgs(int i) {
        PointerType.Companion companion = PointerType.Companion;
        companion.getClass();
        if (i == PointerType.Stylus) {
            return true;
        }
        companion.getClass();
        return i == PointerType.Eraser;
    }

    public StylusHoverIconModifierNode(PointerIcon pointerIcon, boolean z, DpTouchBoundsExpansion dpTouchBoundsExpansion) {
        super(pointerIcon, z, dpTouchBoundsExpansion);
        this.traverseKey = "androidx.compose.ui.input.pointer.StylusHoverIcon";
    }
}
