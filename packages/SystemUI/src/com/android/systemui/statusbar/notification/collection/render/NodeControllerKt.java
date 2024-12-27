package com.android.systemui.statusbar.notification.collection.render;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
