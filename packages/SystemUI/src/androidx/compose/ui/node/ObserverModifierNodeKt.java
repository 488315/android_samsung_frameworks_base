package androidx.compose.ui.node;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.platform.AndroidComposeView;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
