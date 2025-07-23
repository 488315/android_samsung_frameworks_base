package androidx.compose.foundation.relocation;

import androidx.compose.ui.Modifier;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class BringIntoViewRequesterNode extends Modifier.Node {
    public BringIntoViewRequester requester;

    public BringIntoViewRequesterNode(BringIntoViewRequester bringIntoViewRequester) {
        this.requester = bringIntoViewRequester;
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final boolean getShouldAutoInvalidate() {
        return false;
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final void onAttach() {
        BringIntoViewRequester bringIntoViewRequester = this.requester;
        if (bringIntoViewRequester instanceof BringIntoViewRequesterImpl) {
            ((BringIntoViewRequesterImpl) bringIntoViewRequester).nodes.remove(this);
        }
        if (bringIntoViewRequester instanceof BringIntoViewRequesterImpl) {
            ((BringIntoViewRequesterImpl) bringIntoViewRequester).nodes.add(this);
        }
        this.requester = bringIntoViewRequester;
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final void onDetach() {
        BringIntoViewRequester bringIntoViewRequester = this.requester;
        if (bringIntoViewRequester instanceof BringIntoViewRequesterImpl) {
            ((BringIntoViewRequesterImpl) bringIntoViewRequester).nodes.remove(this);
        }
    }
}
