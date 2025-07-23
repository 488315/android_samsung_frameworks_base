package androidx.compose.foundation.layout;

import androidx.compose.ui.Modifier;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class FlowRowScopeInstance implements RowScope, FlowRowScope {
    public static final FlowRowScopeInstance INSTANCE = new FlowRowScopeInstance();
    public final /* synthetic */ RowScopeInstance $$delegate_0 = RowScopeInstance.INSTANCE;

    private FlowRowScopeInstance() {
    }

    @Override // androidx.compose.foundation.layout.RowScope
    public final Modifier weight(Modifier modifier, float f, boolean z) {
        return this.$$delegate_0.weight(modifier, f, z);
    }
}
