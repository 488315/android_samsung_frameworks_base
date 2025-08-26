package com.android.systemui.statusbar.notification.collection.render;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public final class NodeSpecImpl implements NodeSpec {
    public final List children = new ArrayList();
    public final NodeController controller;
    public final NodeSpec parent;

    public NodeSpecImpl(NodeSpec nodeSpec, NodeController nodeController) {
        this.parent = nodeSpec;
        this.controller = nodeController;
    }
}
