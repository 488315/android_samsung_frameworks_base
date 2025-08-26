package androidx.compose.ui.node;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.platform.AndroidComposeView;
import kotlin.jvm.functions.Function0;

/* loaded from: classes.dex */
public abstract class ObserverModifierNodeKt {
    /* JADX WARN: Multi-variable type inference failed */
    public static final void observeReads(Modifier.Node node, Function0 function0) {
        ObserverNodeOwnerScope observerNodeOwnerScope = node.ownerScope;
        if (observerNodeOwnerScope == null) {
            observerNodeOwnerScope = new ObserverNodeOwnerScope((ObserverModifierNode) node);
            node.ownerScope = observerNodeOwnerScope;
        }
        OwnerSnapshotObserver ownerSnapshotObserver = ((AndroidComposeView) DelegatableNodeKt.requireOwner(node)).snapshotObserver;
        ObserverNodeOwnerScope.Companion.getClass();
        ownerSnapshotObserver.observeReads$ui_release(observerNodeOwnerScope, ObserverNodeOwnerScope.OnObserveReadsChanged, function0);
    }
}
