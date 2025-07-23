package androidx.compose.ui.platform;

import androidx.collection.IntObjectMap;
import androidx.collection.MutableIntSet;
import androidx.compose.ui.semantics.SemanticsConfiguration;
import androidx.compose.ui.semantics.SemanticsNode;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SemanticsNodeCopy {
    public final MutableIntSet children;
    public final SemanticsConfiguration unmergedConfig;

    public SemanticsNodeCopy(SemanticsNode semanticsNode, IntObjectMap intObjectMap) {
        this.unmergedConfig = semanticsNode.unmergedConfig;
        this.children = new MutableIntSet(SemanticsNode.getChildren$ui_release$default(4, semanticsNode).size());
        List children$ui_release$default = SemanticsNode.getChildren$ui_release$default(4, semanticsNode);
        int size = children$ui_release$default.size();
        for (int i = 0; i < size; i++) {
            SemanticsNode semanticsNode2 = (SemanticsNode) children$ui_release$default.get(i);
            if (intObjectMap.containsKey(semanticsNode2.id)) {
                this.children.add(semanticsNode2.id);
            }
        }
    }
}
