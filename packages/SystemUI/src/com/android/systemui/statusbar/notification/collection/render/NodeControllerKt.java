package com.android.systemui.statusbar.notification.collection.render;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class NodeControllerKt {
    public static final void treeSpecToStrHelper(NodeSpec nodeSpec, StringBuilder sb, String str) {
        NodeSpecImpl nodeSpecImpl = (NodeSpecImpl) nodeSpec;
        sb.append(str + "{" + nodeSpecImpl.controller.getNodeLabel() + "}\n");
        if (((ArrayList) nodeSpecImpl.children).isEmpty()) {
            return;
        }
        String m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, "  ");
        ArrayList arrayList = (ArrayList) nodeSpecImpl.children;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            treeSpecToStrHelper((NodeSpec) obj, sb, m);
        }
    }
}
