package com.samsung.sesl.compose.foundation;

import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ModifierNodeElement;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes4.dex */
public final class SeslTouchableModifierElement extends ModifierNodeElement<SeslTouchableNode> {
    public final boolean enabled;
    public final MutableInteractionSource interactionSource;
    public final Function1 onTouchDown;

    public SeslTouchableModifierElement(boolean z, MutableInteractionSource mutableInteractionSource, Function1 function1) {
        this.enabled = z;
        this.interactionSource = mutableInteractionSource;
        this.onTouchDown = function1;
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final Modifier.Node create() {
        return new SeslTouchableNode(this.enabled, this.interactionSource, this.onTouchDown);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SeslTouchableModifierElement)) {
            return false;
        }
        SeslTouchableModifierElement seslTouchableModifierElement = (SeslTouchableModifierElement) obj;
        return this.enabled == seslTouchableModifierElement.enabled && Intrinsics.areEqual(this.interactionSource, seslTouchableModifierElement.interactionSource) && Intrinsics.areEqual(this.onTouchDown, seslTouchableModifierElement.onTouchDown);
    }

    public final int hashCode() {
        return this.onTouchDown.hashCode() + ((this.interactionSource.hashCode() + (Boolean.hashCode(this.enabled) * 31)) * 31);
    }

    public final String toString() {
        return "SeslTouchableModifierElement(enabled=" + this.enabled + ", interactionSource=" + this.interactionSource + ", onTouchDown=" + this.onTouchDown + ")";
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final void update(Modifier.Node node) {
        SeslTouchableNode seslTouchableNode = (SeslTouchableNode) node;
        seslTouchableNode.enabled = this.enabled;
        seslTouchableNode.interactionSource = this.interactionSource;
        seslTouchableNode.onTouchDown = this.onTouchDown;
    }
}
