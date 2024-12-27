package com.android.systemui.statusbar.notification.collection.render;

import android.util.Log;
import android.view.View;
import com.android.systemui.statusbar.notification.collection.PipelineDumpable;
import com.android.systemui.statusbar.notification.collection.PipelineDumper;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.stack.NotificationListContainer;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.util.Assert;

public final class RootNodeController implements NodeController, PipelineDumpable {
    public final NotificationListContainer listContainer;
    public final String nodeLabel = "<root>";
    public final View view;

    public RootNodeController(NotificationListContainer notificationListContainer, View view) {
        this.listContainer = notificationListContainer;
        this.view = view;
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public final void addChildAt(NodeController nodeController, int i) {
        View view = nodeController.getView();
        NotificationListContainer notificationListContainer = this.listContainer;
        NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayoutController.this.mView;
        notificationStackScrollLayout.getClass();
        Assert.isMainThread();
        if (view.getParent() != null && (view instanceof ExpandableView)) {
            ((ExpandableView) view).removeFromTransientContainerForAdditionTo(notificationStackScrollLayout);
        }
        notificationStackScrollLayout.addView(view, i);
        notificationStackScrollLayout.mSpeedBumpIndexDirty = true;
        notificationListContainer.getClass();
        View view2 = nodeController.getView();
        ExpandableNotificationRow expandableNotificationRow = view2 instanceof ExpandableNotificationRow ? (ExpandableNotificationRow) view2 : null;
        if (expandableNotificationRow == null) {
            return;
        }
        expandableNotificationRow.mChangingPosition = false;
    }

    @Override // com.android.systemui.statusbar.notification.collection.PipelineDumpable
    public final void dumpPipeline(PipelineDumper pipelineDumper) {
        pipelineDumper.dump(this.listContainer, "listContainer");
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public final View getChildAt(int i) {
        return NotificationStackScrollLayoutController.this.mView.getChildAt(i);
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public final int getChildCount() {
        return NotificationStackScrollLayoutController.this.mView.getChildCount();
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public final String getNodeLabel() {
        return this.nodeLabel;
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public final View getView() {
        return this.view;
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public final void moveChildTo(NodeController nodeController, int i) {
        NotificationStackScrollLayoutController.this.mView.changeViewPosition((ExpandableView) nodeController.getView(), i);
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public final boolean offerToKeepInParentForAnimation() {
        return false;
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public final void removeChild(NodeController nodeController, boolean z) {
        NotificationListContainer notificationListContainer = this.listContainer;
        if (z) {
            NotificationStackScrollLayout notificationStackScrollLayout = NotificationStackScrollLayoutController.this.mView;
            notificationStackScrollLayout.getClass();
            Assert.isMainThread();
            notificationStackScrollLayout.mChildTransferInProgress = true;
            View view = nodeController.getView();
            ExpandableNotificationRow expandableNotificationRow = view instanceof ExpandableNotificationRow ? (ExpandableNotificationRow) view : null;
            if (expandableNotificationRow != null) {
                expandableNotificationRow.mChangingPosition = true;
            }
        }
        View view2 = nodeController.getView();
        NotificationStackScrollLayoutController.NotificationListContainerImpl notificationListContainerImpl = (NotificationStackScrollLayoutController.NotificationListContainerImpl) notificationListContainer;
        NotificationStackScrollLayout notificationStackScrollLayout2 = NotificationStackScrollLayoutController.this.mView;
        notificationStackScrollLayout2.getClass();
        Assert.isMainThread();
        try {
            notificationStackScrollLayout2.removeView(view2);
        } catch (IllegalArgumentException e) {
            Log.d("StackScroller", "removeView has exception !!!!!! ");
            e.printStackTrace();
        }
        notificationStackScrollLayout2.mSpeedBumpIndexDirty = true;
        if (z) {
            NotificationStackScrollLayout notificationStackScrollLayout3 = NotificationStackScrollLayoutController.this.mView;
            notificationStackScrollLayout3.getClass();
            Assert.isMainThread();
            notificationStackScrollLayout3.mChildTransferInProgress = false;
        }
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public final boolean removeFromParentIfKeptForAnimation() {
        return false;
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public final void resetKeepInParentForAnimation() {
    }
}
