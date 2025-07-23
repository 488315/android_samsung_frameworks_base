package androidx.compose.foundation;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ModifierNodeElement;
import androidx.compose.ui.semantics.Role;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class ClickableElement extends ModifierNodeElement<ClickableNode> {
    public final boolean enabled;
    public final IndicationNodeFactory indicationNodeFactory;
    public final MutableInteractionSource interactionSource;
    public final Function0 onClick;
    public final String onClickLabel;
    public final Role role;

    public /* synthetic */ ClickableElement(MutableInteractionSource mutableInteractionSource, IndicationNodeFactory indicationNodeFactory, boolean z, String str, Role role, Function0 function0, DefaultConstructorMarker defaultConstructorMarker) {
        this(mutableInteractionSource, indicationNodeFactory, z, str, role, function0);
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final Modifier.Node create() {
        return new ClickableNode(this.interactionSource, this.indicationNodeFactory, this.enabled, this.onClickLabel, this.role, this.onClick, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || ClickableElement.class != obj.getClass()) {
            return false;
        }
        ClickableElement clickableElement = (ClickableElement) obj;
        return Intrinsics.areEqual(this.interactionSource, clickableElement.interactionSource) && Intrinsics.areEqual(this.indicationNodeFactory, clickableElement.indicationNodeFactory) && this.enabled == clickableElement.enabled && Intrinsics.areEqual(this.onClickLabel, clickableElement.onClickLabel) && Intrinsics.areEqual(this.role, clickableElement.role) && this.onClick == clickableElement.onClick;
    }

    public final int hashCode() {
        MutableInteractionSource mutableInteractionSource = this.interactionSource;
        int hashCode = (mutableInteractionSource != null ? mutableInteractionSource.hashCode() : 0) * 31;
        IndicationNodeFactory indicationNodeFactory = this.indicationNodeFactory;
        int m = TransitionData$$ExternalSyntheticOutline0.m((hashCode + (indicationNodeFactory != null ? indicationNodeFactory.hashCode() : 0)) * 31, 31, this.enabled);
        String str = this.onClickLabel;
        int hashCode2 = (m + (str != null ? str.hashCode() : 0)) * 31;
        Role role = this.role;
        return this.onClick.hashCode() + ((hashCode2 + (role != null ? Integer.hashCode(role.value) : 0)) * 31);
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final void update(Modifier.Node node) {
        ((ClickableNode) node).m18updateCommonQzZPfjk(this.interactionSource, this.indicationNodeFactory, this.enabled, this.onClickLabel, this.role, this.onClick);
    }

    private ClickableElement(MutableInteractionSource mutableInteractionSource, IndicationNodeFactory indicationNodeFactory, boolean z, String str, Role role, Function0 function0) {
        this.interactionSource = mutableInteractionSource;
        this.indicationNodeFactory = indicationNodeFactory;
        this.enabled = z;
        this.onClickLabel = str;
        this.role = role;
        this.onClick = function0;
    }
}
