package com.android.compose.gesture;

import androidx.compose.foundation.OverscrollEffect;
import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.ui.node.DelegatingNode;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
