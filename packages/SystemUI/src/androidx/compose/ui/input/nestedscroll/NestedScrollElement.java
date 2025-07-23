package androidx.compose.ui.input.nestedscroll;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ModifierNodeElement;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class NestedScrollElement extends ModifierNodeElement<NestedScrollNode> {
    public final NestedScrollConnection connection;
    public final NestedScrollDispatcher dispatcher;

    public NestedScrollElement(NestedScrollConnection nestedScrollConnection, NestedScrollDispatcher nestedScrollDispatcher) {
        this.connection = nestedScrollConnection;
        this.dispatcher = nestedScrollDispatcher;
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final Modifier.Node create() {
        return new NestedScrollNode(this.connection, this.dispatcher);
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof NestedScrollElement)) {
            return false;
        }
        NestedScrollElement nestedScrollElement = (NestedScrollElement) obj;
        return Intrinsics.areEqual(nestedScrollElement.connection, this.connection) && Intrinsics.areEqual(nestedScrollElement.dispatcher, this.dispatcher);
    }

    public final int hashCode() {
        int hashCode = this.connection.hashCode() * 31;
        NestedScrollDispatcher nestedScrollDispatcher = this.dispatcher;
        return hashCode + (nestedScrollDispatcher != null ? nestedScrollDispatcher.hashCode() : 0);
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final void update(Modifier.Node node) {
        NestedScrollNode nestedScrollNode = (NestedScrollNode) node;
        nestedScrollNode.connection = this.connection;
        NestedScrollDispatcher nestedScrollDispatcher = nestedScrollNode.resolvedDispatcher;
        if (nestedScrollDispatcher.nestedScrollNode == nestedScrollNode) {
            nestedScrollDispatcher.nestedScrollNode = null;
        }
        NestedScrollDispatcher nestedScrollDispatcher2 = this.dispatcher;
        if (nestedScrollDispatcher2 == null) {
            nestedScrollNode.resolvedDispatcher = new NestedScrollDispatcher();
        } else if (!nestedScrollDispatcher2.equals(nestedScrollDispatcher)) {
            nestedScrollNode.resolvedDispatcher = nestedScrollDispatcher2;
        }
        if (nestedScrollNode.isAttached) {
            nestedScrollNode.updateDispatcherFields();
        }
    }
}
