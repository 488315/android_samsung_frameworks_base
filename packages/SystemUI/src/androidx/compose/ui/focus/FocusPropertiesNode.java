package androidx.compose.ui.focus;

import androidx.compose.ui.Modifier;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class FocusPropertiesNode extends Modifier.Node implements FocusPropertiesModifierNode {
    public FocusPropertiesScope focusPropertiesScope;

    public FocusPropertiesNode(FocusPropertiesScope focusPropertiesScope) {
        this.focusPropertiesScope = focusPropertiesScope;
    }

    @Override // androidx.compose.ui.focus.FocusPropertiesModifierNode
    public final void applyFocusProperties(FocusProperties focusProperties) {
        ((FocusPropertiesKt$sam$androidx_compose_ui_focus_FocusPropertiesScope$0) this.focusPropertiesScope).function.mo779invoke(focusProperties);
    }
}
