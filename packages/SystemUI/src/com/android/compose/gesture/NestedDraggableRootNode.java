package com.android.compose.gesture;

import androidx.compose.foundation.OverscrollEffect;
import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.ui.node.DelegatingNode;

/* loaded from: classes.dex */
public final class NestedDraggableRootNode extends DelegatingNode {
    public NestedDraggableNode delegateNode;

    public NestedDraggableRootNode(NestedDraggable nestedDraggable, Orientation orientation, OverscrollEffect overscrollEffect, boolean z, boolean z2) {
        NestedDraggableNode nestedDraggableNode;
        if (z) {
            nestedDraggableNode = new NestedDraggableNode(nestedDraggable, orientation, overscrollEffect, z2);
            delegate(nestedDraggableNode);
        } else {
            nestedDraggableNode = null;
        }
        this.delegateNode = nestedDraggableNode;
    }
}
