package com.android.systemui.statusbar.notification.collection.render;

import android.view.LayoutInflater;
import android.view.View;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class MediaContainerController implements NodeController {
    public final String nodeLabel = "MediaContainer";

    public MediaContainerController(LayoutInflater layoutInflater) {
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public final void addChildAt(NodeController nodeController, int i) {
        throw new RuntimeException("Not supported");
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public final View getChildAt(int i) {
        throw new RuntimeException("Not supported");
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public final int getChildCount() {
        return 0;
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public final String getNodeLabel() {
        return this.nodeLabel;
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public final View getView() {
        Intrinsics.checkNotNull(null);
        throw null;
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public final void moveChildTo(NodeController nodeController, int i) {
        throw new RuntimeException("Not supported");
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public final boolean offerToKeepInParentForAnimation() {
        return false;
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public final void removeChild(NodeController nodeController, boolean z) {
        throw new RuntimeException("Not supported");
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public final boolean removeFromParentIfKeptForAnimation() {
        return false;
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public final void onViewAdded() {
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public final void onViewMoved() {
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public final void onViewRemoved() {
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.NodeController
    public final void resetKeepInParentForAnimation() {
    }
}
