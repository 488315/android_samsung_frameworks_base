package com.android.systemui.statusbar.notification.collection.render;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class NodeControllerKt {
    public static final void treeSpecToStrHelper(NodeSpec nodeSpec, StringBuilder sb, String str) {
        NodeSpecImpl nodeSpecImpl = (NodeSpecImpl) nodeSpec;
        sb.append(str + "{" + nodeSpecImpl.controller.getNodeLabel() + "}\n");
        if (!((ArrayList) nodeSpecImpl.children).isEmpty()) {
            String m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, "  ");
            Iterator it = ((ArrayList) nodeSpecImpl.children).iterator();
            while (it.hasNext()) {
                treeSpecToStrHelper((NodeSpec) it.next(), sb, m);
            }
        }
    }
}
