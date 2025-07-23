package androidx.compose.foundation.draganddrop;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ModifierNodeElement;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class LegacyDragAndDropSourceWithDefaultShadowElement extends ModifierNodeElement<LegacyDragSourceNodeWithDefaultPainter> {
    public final Function2 dragAndDropSourceHandler;

    public LegacyDragAndDropSourceWithDefaultShadowElement(Function2 function2) {
        this.dragAndDropSourceHandler = function2;
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final Modifier.Node create() {
        return new LegacyDragSourceNodeWithDefaultPainter(this.dragAndDropSourceHandler);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof LegacyDragAndDropSourceWithDefaultShadowElement)) {
            return false;
        }
        return Intrinsics.areEqual(this.dragAndDropSourceHandler, ((LegacyDragAndDropSourceWithDefaultShadowElement) obj).dragAndDropSourceHandler);
    }

    public final int hashCode() {
        return this.dragAndDropSourceHandler.hashCode();
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final void update(Modifier.Node node) {
        ((LegacyDragSourceNodeWithDefaultPainter) node).dragAndDropSourceHandler = this.dragAndDropSourceHandler;
    }
}
