package androidx.compose.ui.semantics;

import androidx.collection.IntObjectMap;
import androidx.collection.MutableObjectList;
import androidx.compose.ui.node.LayoutNode;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SemanticsOwner {
    public final MutableObjectList listeners = new MutableObjectList(2);
    public final EmptySemanticsModifier outerSemanticsNode;
    public final LayoutNode rootNode;

    public SemanticsOwner(LayoutNode layoutNode, EmptySemanticsModifier emptySemanticsModifier, IntObjectMap intObjectMap) {
        this.rootNode = layoutNode;
        this.outerSemanticsNode = emptySemanticsModifier;
    }

    public final SemanticsNode getUnmergedRootSemanticsNode() {
        return new SemanticsNode(this.outerSemanticsNode, false, this.rootNode, new SemanticsConfiguration());
    }
}
