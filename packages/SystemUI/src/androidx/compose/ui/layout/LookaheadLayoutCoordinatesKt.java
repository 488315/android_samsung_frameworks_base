package androidx.compose.ui.layout;

import androidx.compose.ui.node.LayoutNode;
import androidx.compose.ui.node.LookaheadDelegate;

/* loaded from: classes.dex */
public abstract class LookaheadLayoutCoordinatesKt {
    public static final LookaheadDelegate getRootLookaheadDelegate(LookaheadDelegate lookaheadDelegate) {
        LayoutNode parent$ui_release = lookaheadDelegate.coordinator.layoutNode;
        while (true) {
            LayoutNode parent$ui_release2 = parent$ui_release.getParent$ui_release();
            if ((parent$ui_release2 != null ? parent$ui_release2.lookaheadRoot : null) == null) {
                LookaheadDelegate lookaheadDelegate2 = parent$ui_release.nodes.outerCoordinator.getLookaheadDelegate();
                lookaheadDelegate2.getClass();
                return lookaheadDelegate2;
            }
            LayoutNode parent$ui_release3 = parent$ui_release.getParent$ui_release();
            LayoutNode layoutNode = parent$ui_release3 != null ? parent$ui_release3.lookaheadRoot : null;
            layoutNode.getClass();
            if (layoutNode.isVirtualLookaheadRoot) {
                parent$ui_release = parent$ui_release.getParent$ui_release();
                parent$ui_release.getClass();
            } else {
                LayoutNode parent$ui_release4 = parent$ui_release.getParent$ui_release();
                parent$ui_release4.getClass();
                parent$ui_release = parent$ui_release4.lookaheadRoot;
                parent$ui_release.getClass();
            }
        }
    }
}
