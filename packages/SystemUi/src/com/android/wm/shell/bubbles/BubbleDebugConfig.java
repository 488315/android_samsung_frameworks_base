package com.android.wm.shell.bubbles;

import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class BubbleDebugConfig {
    /* JADX WARN: Multi-variable type inference failed */
    public static String formatBubblesString(List list, BubbleViewProvider bubbleViewProvider) {
        StringBuilder sb = new StringBuilder();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Bubble bubble = (Bubble) it.next();
            if (bubble == null) {
                sb.append("   <null> !!!!!\n");
            } else {
                sb.append(String.format("%s Bubble{act=%12d, showInShade=%d, key=%s}\n", (bubbleViewProvider != null && bubbleViewProvider.getKey() != "Overflow" && bubble == bubbleViewProvider) != false ? "=>" : "  ", Long.valueOf(bubble.getLastActivity()), Integer.valueOf(bubble.showInShade() ? 1 : 0), bubble.mKey));
            }
        }
        return sb.toString();
    }
}
