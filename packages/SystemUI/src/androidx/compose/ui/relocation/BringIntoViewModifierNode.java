package androidx.compose.ui.relocation;

import androidx.compose.ui.node.DelegatableNode;
import androidx.compose.ui.node.NodeCoordinator;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface BringIntoViewModifierNode extends DelegatableNode {
    Object bringIntoView(NodeCoordinator nodeCoordinator, Function0 function0, ContinuationImpl continuationImpl);
}
