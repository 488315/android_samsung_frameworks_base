package com.android.systemui.statusbar.notification.collection.render;

import android.view.View;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface NodeController {
    void addChildAt(NodeController nodeController, int i);

    View getChildAt(int i);

    int getChildCount();

    String getNodeLabel();

    View getView();

    void moveChildTo(NodeController nodeController, int i);

    boolean offerToKeepInParentForAnimation();

    void onViewAdded();

    void onViewMoved();

    void onViewRemoved();

    void removeChild(NodeController nodeController, boolean z);

    boolean removeFromParentIfKeptForAnimation();

    void resetKeepInParentForAnimation();
}
