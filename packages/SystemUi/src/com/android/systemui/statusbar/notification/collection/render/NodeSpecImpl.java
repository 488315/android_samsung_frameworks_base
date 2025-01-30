package com.android.systemui.statusbar.notification.collection.render;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NodeSpecImpl implements NodeSpec {
    public final List children = new ArrayList();
    public final NodeController controller;
    public final NodeSpec parent;

    public NodeSpecImpl(NodeSpec nodeSpec, NodeController nodeController) {
        this.parent = nodeSpec;
        this.controller = nodeController;
    }
}
