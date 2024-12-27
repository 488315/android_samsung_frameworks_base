package com.android.systemui.statusbar.notification.collection.render;

import android.view.View;

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
