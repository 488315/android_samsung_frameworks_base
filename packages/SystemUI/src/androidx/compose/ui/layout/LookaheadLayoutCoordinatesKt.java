package androidx.compose.ui.layout;

import androidx.compose.ui.node.LayoutNode;
import androidx.compose.ui.node.LookaheadDelegate;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class LookaheadLayoutCoordinatesKt {
    public static final LookaheadDelegate getRootLookaheadDelegate(LookaheadDelegate lookaheadDelegate) {
        LayoutNode layoutNode = lookaheadDelegate.coordinator.layoutNode;
        while (true) {
            LayoutNode parent$ui_release = layoutNode.getParent$ui_release();
            if ((parent$ui_release != null ? parent$ui_release.lookaheadRoot : null) == null) {
                LookaheadDelegate lookaheadDelegate2 = layoutNode.nodes.outerCoordinator.getLookaheadDelegate();
                lookaheadDelegate2.getClass();
                return lookaheadDelegate2;
            }
            LayoutNode parent$ui_release2 = layoutNode.getParent$ui_release();
            LayoutNode layoutNode2 = parent$ui_release2 != null ? parent$ui_release2.lookaheadRoot : null;
            layoutNode2.getClass();
            if (layoutNode2.isVirtualLookaheadRoot) {
                layoutNode = layoutNode.getParent$ui_release();
                layoutNode.getClass();
            } else {
                LayoutNode parent$ui_release3 = layoutNode.getParent$ui_release();
                parent$ui_release3.getClass();
                layoutNode = parent$ui_release3.lookaheadRoot;
                layoutNode.getClass();
            }
        }
    }
}
