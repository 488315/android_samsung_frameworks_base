package com.android.systemui.statusbar.notification.collection.render;

public final class ShadeNode {
    public final NodeController controller;
    public ShadeNode parent;

    public ShadeNode(NodeController nodeController) {
        this.controller = nodeController;
    }
}
