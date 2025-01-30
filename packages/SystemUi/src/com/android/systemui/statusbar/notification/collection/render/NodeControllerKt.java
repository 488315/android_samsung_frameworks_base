package com.android.systemui.statusbar.notification.collection.render;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class NodeControllerKt {
    public static final void treeSpecToStrHelper(NodeSpec nodeSpec, StringBuilder sb, String str) {
        NodeSpecImpl nodeSpecImpl = (NodeSpecImpl) nodeSpec;
        sb.append(str + "{" + nodeSpecImpl.controller.getNodeLabel() + "}\n");
        ArrayList arrayList = (ArrayList) nodeSpecImpl.children;
        if (!arrayList.isEmpty()) {
            String m14m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(str, "  ");
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                treeSpecToStrHelper((NodeSpec) it.next(), sb, m14m);
            }
        }
    }
}
