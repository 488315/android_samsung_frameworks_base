package androidx.compose.foundation.layout;

import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.platform.InspectableValueKt;

/* loaded from: classes.dex */
public final class BoxScopeInstance implements BoxScope {
    public static final BoxScopeInstance INSTANCE = new BoxScopeInstance();

    private BoxScopeInstance() {
    }

    @Override // androidx.compose.foundation.layout.BoxScope
    public final Modifier align(Modifier modifier, Alignment alignment) {
        return modifier.then(new BoxChildDataElement(alignment, false, InspectableValueKt.NoInspectorInfo));
    }

    @Override // androidx.compose.foundation.layout.BoxScope
    public final Modifier matchParentSize(Modifier.Companion companion) {
        Alignment.Companion.getClass();
        BoxChildDataElement boxChildDataElement = new BoxChildDataElement(Alignment.Companion.Center, true, InspectableValueKt.NoInspectorInfo);
        companion.getClass();
        return boxChildDataElement;
    }
}
