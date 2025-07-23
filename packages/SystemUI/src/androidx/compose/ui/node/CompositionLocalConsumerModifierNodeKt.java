package androidx.compose.ui.node;

import androidx.compose.runtime.CompositionLocal;
import androidx.compose.runtime.CompositionLocalMapKt;
import androidx.compose.runtime.internal.PersistentCompositionLocalHashMap;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.internal.InlineClassHelperKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class CompositionLocalConsumerModifierNodeKt {
    /* JADX WARN: Multi-variable type inference failed */
    public static final Object currentValueOf(CompositionLocalConsumerModifierNode compositionLocalConsumerModifierNode, CompositionLocal compositionLocal) {
        if (!((Modifier.Node) compositionLocalConsumerModifierNode).node.isAttached) {
            InlineClassHelperKt.throwIllegalStateException("Cannot read CompositionLocal because the Modifier node is not currently attached.");
        }
        PersistentCompositionLocalHashMap persistentCompositionLocalHashMap = (PersistentCompositionLocalHashMap) DelegatableNodeKt.requireLayoutNode(compositionLocalConsumerModifierNode).compositionLocalMap;
        persistentCompositionLocalHashMap.getClass();
        return CompositionLocalMapKt.read(persistentCompositionLocalHashMap, compositionLocal);
    }
}
