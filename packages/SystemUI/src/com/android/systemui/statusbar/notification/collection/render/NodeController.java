package com.android.systemui.statusbar.notification.collection.render;

import android.view.View;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public interface NodeController {
    default void addChildAt(NodeController nodeController, int i) {
        throw new RuntimeException("Not supported");
    }

    default View getChildAt(int i) {
        throw new RuntimeException("Not supported");
    }

    default int getChildCount() {
        return 0;
    }

    String getNodeLabel();

    View getView();

    default void moveChildTo(NodeController nodeController, int i) {
        throw new RuntimeException("Not supported");
    }

    boolean offerToKeepInParentForAnimation();

    default void removeChild(NodeController nodeController, boolean z) {
        throw new RuntimeException("Not supported");
    }

    boolean removeFromParentIfKeptForAnimation();

    void resetKeepInParentForAnimation();

    default void onViewAdded() {
    }

    default void onViewRemoved() {
    }
}
