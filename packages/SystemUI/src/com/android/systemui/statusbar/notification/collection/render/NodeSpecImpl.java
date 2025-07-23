package com.android.systemui.statusbar.notification.collection.render;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
