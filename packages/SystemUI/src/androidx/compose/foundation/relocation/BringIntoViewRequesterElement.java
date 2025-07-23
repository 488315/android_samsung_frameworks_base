package androidx.compose.foundation.relocation;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ModifierNodeElement;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class BringIntoViewRequesterElement extends ModifierNodeElement<BringIntoViewRequesterNode> {
    public final BringIntoViewRequester requester;

    public BringIntoViewRequesterElement(BringIntoViewRequester bringIntoViewRequester) {
        this.requester = bringIntoViewRequester;
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final Modifier.Node create() {
        return new BringIntoViewRequesterNode(this.requester);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof BringIntoViewRequesterElement) {
            return Intrinsics.areEqual(this.requester, ((BringIntoViewRequesterElement) obj).requester);
        }
        return false;
    }

    public final int hashCode() {
        return this.requester.hashCode();
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final void update(Modifier.Node node) {
        BringIntoViewRequesterNode bringIntoViewRequesterNode = (BringIntoViewRequesterNode) node;
        BringIntoViewRequester bringIntoViewRequester = bringIntoViewRequesterNode.requester;
        if (bringIntoViewRequester instanceof BringIntoViewRequesterImpl) {
            ((BringIntoViewRequesterImpl) bringIntoViewRequester).nodes.remove(bringIntoViewRequesterNode);
        }
        BringIntoViewRequester bringIntoViewRequester2 = this.requester;
        if (bringIntoViewRequester2 instanceof BringIntoViewRequesterImpl) {
            ((BringIntoViewRequesterImpl) bringIntoViewRequester2).nodes.add(bringIntoViewRequesterNode);
        }
        bringIntoViewRequesterNode.requester = bringIntoViewRequester2;
    }
}
