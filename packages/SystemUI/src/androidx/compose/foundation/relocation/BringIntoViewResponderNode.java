package androidx.compose.foundation.relocation;

import androidx.compose.foundation.gestures.ContentInViewNode;
import androidx.compose.foundation.internal.InlineClassHelperKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.layout.LayoutCoordinates;
import androidx.compose.ui.node.DelegatableNodeKt;
import androidx.compose.ui.node.LayoutAwareModifierNode;
import androidx.compose.ui.node.NodeCoordinator;
import androidx.compose.ui.relocation.BringIntoViewModifierNode;
import androidx.compose.ui.unit.IntSize;
import kotlin.Unit;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.CoroutineScopeKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class BringIntoViewResponderNode extends Modifier.Node implements BringIntoViewModifierNode, LayoutAwareModifierNode {
    public boolean hasBeenPlaced;
    public final BringIntoViewResponder responder;

    public BringIntoViewResponderNode(BringIntoViewResponder bringIntoViewResponder) {
        this.responder = bringIntoViewResponder;
    }

    public static final Rect access$bringIntoView$localRect(BringIntoViewResponderNode bringIntoViewResponderNode, LayoutCoordinates layoutCoordinates, Function0 function0) {
        Rect rect;
        if (bringIntoViewResponderNode.isAttached && bringIntoViewResponderNode.hasBeenPlaced) {
            NodeCoordinator requireLayoutCoordinates = DelegatableNodeKt.requireLayoutCoordinates(bringIntoViewResponderNode);
            if (!layoutCoordinates.isAttached()) {
                layoutCoordinates = null;
            }
            if (layoutCoordinates != null && (rect = (Rect) function0.invoke()) != null) {
                return rect.m410translatek4lQ0M(requireLayoutCoordinates.localBoundingBoxOf(layoutCoordinates, false).m409getTopLeftF1C5BW0());
            }
        }
        return null;
    }

    @Override // androidx.compose.ui.relocation.BringIntoViewModifierNode
    public final Object bringIntoView(final NodeCoordinator nodeCoordinator, final Function0 function0, ContinuationImpl continuationImpl) {
        Object coroutineScope = CoroutineScopeKt.coroutineScope(new BringIntoViewResponderNode$bringIntoView$2(this, nodeCoordinator, function0, new Function0() { // from class: androidx.compose.foundation.relocation.BringIntoViewResponderNode$bringIntoView$parentRect$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Rect access$bringIntoView$localRect = BringIntoViewResponderNode.access$bringIntoView$localRect(BringIntoViewResponderNode.this, nodeCoordinator, function0);
                if (access$bringIntoView$localRect == null) {
                    return null;
                }
                ContentInViewNode contentInViewNode = (ContentInViewNode) BringIntoViewResponderNode.this.responder;
                long j = contentInViewNode.viewportSize;
                IntSize.Companion.getClass();
                if (IntSize.m861equalsimpl0(j, 0L)) {
                    InlineClassHelperKt.throwIllegalStateException("Expected BringIntoViewRequester to not be used before parents are placed.");
                }
                long m65relocationOffsetBMxPBkI = contentInViewNode.m65relocationOffsetBMxPBkI(access$bringIntoView$localRect, contentInViewNode.viewportSize) ^ (-9223372034707292160L);
                Offset.Companion companion = Offset.Companion;
                return access$bringIntoView$localRect.m410translatek4lQ0M(m65relocationOffsetBMxPBkI);
            }
        }, null), continuationImpl);
        return coroutineScope == CoroutineSingletons.COROUTINE_SUSPENDED ? coroutineScope : Unit.INSTANCE;
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final boolean getShouldAutoInvalidate() {
        return false;
    }

    @Override // androidx.compose.ui.node.LayoutAwareModifierNode
    public final void onPlaced(LayoutCoordinates layoutCoordinates) {
        this.hasBeenPlaced = true;
    }
}
