package androidx.compose.ui.focus;

import androidx.compose.ui.ComposeUiFlags;
import androidx.compose.ui.node.DelegatableNodeKt;
import androidx.compose.ui.node.LayoutNode;
import androidx.compose.ui.node.NodeCoordinator;
import androidx.compose.ui.platform.AndroidComposeView;

/* loaded from: classes.dex */
public abstract class FocusTargetNodeKt {
    public static final FocusTransactionManager getFocusTransactionManager(FocusTargetNode focusTargetNode) {
        LayoutNode layoutNode;
        AndroidComposeView androidComposeView;
        FocusOwnerImpl focusOwnerImpl;
        NodeCoordinator nodeCoordinator = focusTargetNode.node.coordinator;
        if (nodeCoordinator == null || (layoutNode = nodeCoordinator.layoutNode) == null || (androidComposeView = layoutNode.owner) == null || (focusOwnerImpl = androidComposeView.focusOwner) == null) {
            return null;
        }
        return focusOwnerImpl.focusTransactionManager;
    }

    public static final void invalidateFocusTarget(FocusTargetNode focusTargetNode) {
        FocusInvalidationManager focusInvalidationManager = ((AndroidComposeView) DelegatableNodeKt.requireOwner(focusTargetNode)).focusOwner.focusInvalidationManager;
        focusInvalidationManager.getClass();
        if (!ComposeUiFlags.isTrackFocusEnabled) {
            focusInvalidationManager.scheduleInvalidationLegacy(focusInvalidationManager.focusTargetNodesLegacy, focusTargetNode);
        } else {
            if (!focusInvalidationManager.focusTargetNodes.add(focusTargetNode) || focusInvalidationManager.isInvalidationScheduled) {
                return;
            }
            focusInvalidationManager.onRequestApplyChangesListener.mo781invoke(new FocusInvalidationManager$setUpOnRequestApplyChangesListener$1(focusInvalidationManager));
            focusInvalidationManager.isInvalidationScheduled = true;
        }
    }

    public static final FocusTransactionManager requireTransactionManager(FocusTargetNode focusTargetNode) {
        return ((AndroidComposeView) DelegatableNodeKt.requireOwner(focusTargetNode)).focusOwner.focusTransactionManager;
    }
}
