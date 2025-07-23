package androidx.compose.ui.platform;

import android.view.ViewGroup;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.graphics.RectHelper_androidKt;
import androidx.compose.ui.layout.LayoutCoordinatesKt;
import androidx.compose.ui.node.NodeCoordinator;
import androidx.compose.ui.relocation.BringIntoViewModifierNode;
import kotlin.Unit;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class BringIntoViewOnScreenResponderNode extends Modifier.Node implements BringIntoViewModifierNode {
    public ViewGroup view;

    public BringIntoViewOnScreenResponderNode(ViewGroup viewGroup) {
        this.view = viewGroup;
    }

    @Override // androidx.compose.ui.relocation.BringIntoViewModifierNode
    public final Object bringIntoView(NodeCoordinator nodeCoordinator, Function0 function0, ContinuationImpl continuationImpl) {
        long positionInRoot = LayoutCoordinatesKt.positionInRoot(nodeCoordinator);
        Rect rect = (Rect) function0.invoke();
        Rect m410translatek4lQ0M = rect != null ? rect.m410translatek4lQ0M(positionInRoot) : null;
        if (m410translatek4lQ0M != null) {
            this.view.requestRectangleOnScreen(RectHelper_androidKt.toAndroidRect(m410translatek4lQ0M), false);
        }
        return Unit.INSTANCE;
    }
}
