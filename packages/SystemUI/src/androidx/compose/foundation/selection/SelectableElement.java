package androidx.compose.foundation.selection;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.foundation.IndicationNodeFactory;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ModifierNodeElement;
import androidx.compose.ui.node.SemanticsModifierNodeKt;
import androidx.compose.ui.semantics.Role;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class SelectableElement extends ModifierNodeElement<SelectableNode> {
    public final boolean enabled;
    public final IndicationNodeFactory indicationNodeFactory;
    public final MutableInteractionSource interactionSource;
    public final Function0 onClick;
    public final Role role;
    public final boolean selected;

    public /* synthetic */ SelectableElement(boolean z, MutableInteractionSource mutableInteractionSource, IndicationNodeFactory indicationNodeFactory, boolean z2, Role role, Function0 function0, DefaultConstructorMarker defaultConstructorMarker) {
        this(z, mutableInteractionSource, indicationNodeFactory, z2, role, function0);
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final Modifier.Node create() {
        return new SelectableNode(this.selected, this.interactionSource, this.indicationNodeFactory, this.enabled, this.role, this.onClick, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || SelectableElement.class != obj.getClass()) {
            return false;
        }
        SelectableElement selectableElement = (SelectableElement) obj;
        return this.selected == selectableElement.selected && Intrinsics.areEqual(this.interactionSource, selectableElement.interactionSource) && Intrinsics.areEqual(this.indicationNodeFactory, selectableElement.indicationNodeFactory) && this.enabled == selectableElement.enabled && Intrinsics.areEqual(this.role, selectableElement.role) && this.onClick == selectableElement.onClick;
    }

    public final int hashCode() {
        int hashCode = Boolean.hashCode(this.selected) * 31;
        MutableInteractionSource mutableInteractionSource = this.interactionSource;
        int hashCode2 = (hashCode + (mutableInteractionSource != null ? mutableInteractionSource.hashCode() : 0)) * 31;
        IndicationNodeFactory indicationNodeFactory = this.indicationNodeFactory;
        int m = TransitionData$$ExternalSyntheticOutline0.m((hashCode2 + (indicationNodeFactory != null ? indicationNodeFactory.hashCode() : 0)) * 31, 31, this.enabled);
        Role role = this.role;
        return this.onClick.hashCode() + ((m + (role != null ? Integer.hashCode(role.value) : 0)) * 31);
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final void update(Modifier.Node node) {
        SelectableNode selectableNode = (SelectableNode) node;
        boolean z = selectableNode.selected;
        boolean z2 = this.selected;
        if (z != z2) {
            selectableNode.selected = z2;
            SemanticsModifierNodeKt.invalidateSemantics(selectableNode);
        }
        selectableNode.m18updateCommonQzZPfjk(this.interactionSource, this.indicationNodeFactory, this.enabled, null, this.role, this.onClick);
    }

    private SelectableElement(boolean z, MutableInteractionSource mutableInteractionSource, IndicationNodeFactory indicationNodeFactory, boolean z2, Role role, Function0 function0) {
        this.selected = z;
        this.interactionSource = mutableInteractionSource;
        this.indicationNodeFactory = indicationNodeFactory;
        this.enabled = z2;
        this.role = role;
        this.onClick = function0;
    }
}
